package com.supervision.web.controller.grassrootsregistration;

import java.util.List;

import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.common.core.domain.entity.SysRole;
import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.common.utils.StringUtils;
import com.supervision.grassrootsregistration.resultVo.SysDeptDto;
import com.supervision.peopleManage.domain.BdglPeople;
import com.supervision.peopleManage.service.IBdglPeopleService;
import com.supervision.system.mapper.SysUserMapper;
import com.supervision.system.service.ISysDeptService;
import io.swagger.models.auth.In;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.supervision.grassrootsregistration .domain.BdglParty;
import com.supervision.grassrootsregistration .service.IBdglPartyService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 党支部会议登记本Controller
 *
 * @author supervision
 * @date 2022-02-26
 */
@RestController
@RequestMapping("/grassrootsregistration/bdglparty")
public class BdglPartyController extends BaseController
{
    @Autowired
    private IBdglPartyService bdglPartyService;
    @Autowired
    private IBdglPeopleService bdglPeopleService;
    @Autowired
    private ISysDeptService sysDeptService;
    @Autowired
    private SysUserMapper userMapper;
    /**
     * 查询党支部会议登记本列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglparty:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglParty bdglParty)
    {
        LoginUser loginUser = getLoginUser();
        List<SysRole> sysRoles = userMapper.selectRole(loginUser.getUserId());
        String role = CommonsController.getRole(sysRoles);
        //判断当前角色是否是最高权限   不是返回当前部门数据
        if(!role.equals("admin")){
            bdglParty.setUnitId(Integer.parseInt(loginUser.getDeptId().toString()));
        }
        startPage();
        List<BdglParty> list = bdglPartyService.selectBdglPartyList(bdglParty);
        return getDataTable(list);
    }

    /**
     * 领导查询党支部会议登记本
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglpartys:list')")
    @GetMapping("/getPartyList")
    public TableDataInfo getPartyList(String year)
    {
        if (StringUtils.isNotEmpty(year)){
            startPage();
            List<SysDeptDto> list = bdglPartyService.getPartyList(year);
            return getDataTable(list);
        }
        List<List<SysDeptDto>> list = sysDeptService.getMeetRecordsList();
        return getDataTable(list);
    }
    /**
     * 领导查询党支部会议登记本列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglpartys:list')")
    @GetMapping("/getPartyOnUnitList")
    public TableDataInfo getPartyOnUnitList(Integer unitId,String year)
    {
        startPage();
        List<BdglParty> list = bdglPartyService.getPartyOnUnitList(unitId,year);
        return getDataTable(list);
    }

    /**
     * 导出党支部会议登记本列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglparty:export')")
    @Log(title = "党支部会议登记本", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglParty bdglParty)
    {
        List<BdglParty> list = bdglPartyService.selectBdglPartyList(bdglParty);
        ExcelUtil<BdglParty> util = new ExcelUtil<BdglParty>(BdglParty.class);
        return util.exportExcel(list, "党支部会议登记本数据");
    }

    /**
     * 获取党支部会议登记本详细信息
     */
    @PreAuthorize("@ss.hasAnyPermi('grassrootsregistration:bdglparty:query,grassrootsregistration:bdglpartys:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bdglPartyService.selectBdglPartyById(id));
    }

    /**
     * 新增党支部会议登记本
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglparty:add')")
    @Log(title = "党支部会议登记本", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglParty bdglParty)
    {
        return toAjax(bdglPartyService.insertBdglParty(bdglParty));
    }

    /**
     * 修改党支部会议登记本
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglparty:edit')")
    @Log(title = "党支部会议登记本", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglParty bdglParty)
    {
        return toAjax(bdglPartyService.updateBdglParty(bdglParty));
    }

    /**
     * 删除党支部会议登记本
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglparty:remove')")
    @Log(title = "党支部会议登记本", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglPartyService.deleteBdglPartyByIds(ids));
    }
}
