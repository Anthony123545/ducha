package com.supervision.web.controller.peopleChuRu;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import com.supervision.common.core.domain.entity.SysRole;
import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.common.utils.SecurityUtils;
import com.supervision.common.utils.StringUtils;
import com.supervision.peopleChuRu.domain.BdglLeave;
import com.supervision.peopleManage.domain.BdglPeople;
import com.supervision.peopleManage.mapper.BdglPeopleMapper;
import com.supervision.peopleManage.service.IBdglPeopleService;
import com.supervision.system.service.ISysUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.supervision.common.annotation.Log;
import com.supervision.common.core.controller.BaseController;
import com.supervision.common.core.domain.AjaxResult;
import com.supervision.common.enums.BusinessType;
import com.supervision.peopleChuRu.domain.BdglLeaveOne;
import com.supervision.peopleChuRu.service.IBdglLeaveOneService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 人员请假Controller
 * 
 * @author supervision
 * @date 2022-03-31
 */
@Transactional()
@RequestMapping("/peopleChuRu/leaveOne")
public class BdglLeaveOneController extends BaseController
{
    @Autowired
    private IBdglLeaveOneService bdglLeaveOneService;
    @Autowired
    private BdglPeopleMapper peopleService;
    
    @Autowired
    private IBdglPeopleService bdglPeopleService;
    
    @Autowired
    private ISysUserService iSysUserService;

    /**
     * 查询人员请假列表
     */
    @GetMapping("/list")
    public TableDataInfo list(BdglLeaveOne bdglLeaveOne)
    {
        List<String> roleKeylist = iSysUserService.selectUserRoleKey(getUserId());
        boolean userAdmin = true;
        for (String role : roleKeylist) {
            if ("admin".equals(role)) {
                userAdmin = false;
                break;
            }
        }
        if ("admin".equals(getUsername())){
            userAdmin = false;
        }
        if (userAdmin){
            bdglLeaveOne.setUnitId(getDeptId());
        }
        
        if(bdglLeaveOne.getName()!= null && bdglLeaveOne.getName() != "") {
            List<String> list = bdglPeopleService.selectPowerPeoleListes(bdglLeaveOne.getName());
            String str = list.stream().collect(Collectors.joining(","));
            String[] split = str.split(",");
            bdglLeaveOne.setNames(split);
        }
        startPage();
        List<BdglLeaveOne> list = bdglLeaveOneService.selectBdglLeaveOneList(bdglLeaveOne);
        for (BdglLeaveOne leaveOne : list) {
            if(leaveOne.getPeopleId()!=null&&leaveOne.getPeopleId()!=""){
                String[] split = leaveOne.getPeopleId().split(",");
                String name="";
                for (String s : split) {
                    BdglPeople bdglPeople = peopleService.selectBdglPeopleById(Long.valueOf(s));
                    if(bdglPeople!=null){

                        name+=bdglPeople.getName()+",";
                    }
                }
                if(name!=""){

                    leaveOne.setPeopleId(name.substring(0,name.length()-1));
                }
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出人员请假列表
     */
    @PreAuthorize("@ss.hasPermi('peopleChuRu:leaveOne:export')")
    @Log(title = "人员请假", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglLeaveOne bdglLeaveOne)
    {
        List<BdglLeaveOne> list = bdglLeaveOneService.selectBdglLeaveOneList(bdglLeaveOne);
        ExcelUtil<BdglLeaveOne> util = new ExcelUtil<BdglLeaveOne>(BdglLeaveOne.class);
        return util.exportExcel(list, "人员请假数据");
    }

    /**
     * 获取人员请假详细信息
     */
    @PreAuthorize("@ss.hasPermi('peopleChuRu:leaveOne:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        BdglLeaveOne leaveOne = bdglLeaveOneService.selectBdglLeaveOneById(id);
        /*if(leaveOne.getPeopleId()!=null&&leaveOne.getPeopleId()!=""){
            String[] split = leaveOne.getPeopleId().split(",");
            String name="";
            for (String s : split) {
                BdglPeople bdglPeople = peopleService.selectBdglPeopleById(Long.valueOf(s));
                if(bdglPeople!=null){
                    name+=bdglPeople.getName()+",";
                }
            }
            if(name!=""){
                leaveOne.setPeopleName(name.substring(0,name.length()-1));
            }
        }*/
        return AjaxResult.success(leaveOne);
    }

    /**
     * 按照id归队按钮
     */
    @GetMapping("/return/{id}")
    public AjaxResult hasReturnPower(@PathVariable("id") Long id) {
        BdglLeaveOne bdglLeaveOne = bdglLeaveOneService.selectBdglLeaveOneById(id);
        bdglLeaveOne.setReturnTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        bdglLeaveOne.setReturnStatus("1");
        BdglPeople bdglPeople = bdglPeopleService.selectBdglPeopleById(Long.valueOf(bdglLeaveOne.getPeopleId()));
        bdglPeople.setReign(0);
        return AjaxResult.success(bdglLeaveOneService.updateBdglLeaveOne(bdglLeaveOne));
    }
    /**
     * 新增人员请假
     */
    @PreAuthorize("@ss.hasPermi('peopleChuRu:leaveOne:add')")
    @Log(title = "人员请假", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglLeaveOne bdglLeaveOne)
    {
        return toAjax(bdglLeaveOneService.insertBdglLeaveOne(bdglLeaveOne));
    }

    /**
     * 修改人员请假
     */
    @PreAuthorize("@ss.hasPermi('peopleChuRu:leaveOne:edit')")
    @Log(title = "人员请假", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglLeaveOne bdglLeaveOne)
    {
        return toAjax(bdglLeaveOneService.updateBdglLeaveOne(bdglLeaveOne));
    }

    /**
     * 删除人员请假
     */
    @PreAuthorize("@ss.hasPermi('peopleChuRu:leaveOne:remove')")
    @Log(title = "人员请假", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bdglLeaveOneService.deleteBdglLeaveOneByIds(ids));
    }
}
