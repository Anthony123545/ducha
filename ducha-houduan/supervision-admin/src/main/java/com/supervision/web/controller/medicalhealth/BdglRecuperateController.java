package com.supervision.web.controller.medicalhealth;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.supervision.common.annotation.Log;
import com.supervision.common.core.controller.BaseController;
import com.supervision.common.core.domain.AjaxResult;
import com.supervision.common.core.domain.entity.SysUser;
import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.common.core.page.TableDataInfo;
import com.supervision.common.enums.BusinessType;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.peopleManage.domain.BdglPeople;
import com.supervision.peopleManage.service.IBdglPeopleService;
import com.supervision.system.service.ISysUserService;
import com.supervision.web.controller.peopleChuRu.BdglLeaveController;
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

import com.supervision.medicalhealth.domain.BdglRecuperate;
import com.supervision.medicalhealth.service.IBdglRecuperateService;


/**
 * 疗养管理Controller
 *
 * @author hr
 * @date 2022-02-14
 */
@RestController
@RequestMapping("/medicalhealth/recuperate")
public class BdglRecuperateController extends BaseController {
    @Autowired
    private IBdglRecuperateService bdglRecuperateService;

    @Autowired
    private IBdglPeopleService peopleService;

    @Autowired
    private ISysUserService userService;

    /**
     * 查询疗养管理列表
     * common 未1  显示审批按钮
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:recuperate:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglRecuperate bdglRecuperate) {
        startPage();
        LoginUser loginUser = getLoginUser();
        String username = loginUser.getUsername();
        List<BdglRecuperate> list = bdglRecuperateService.selectBdglRecuperateList(bdglRecuperate);
        Long userId1 = loginUser.getUserId();
        SysUser sysUser = userService.selectUserById(userId1);
        boolean falg=false;
        if(sysUser.getPermissions()!=null){
             falg = BdglLeaveController.isFalg(sysUser, "2");
        }
        c:
        for (BdglRecuperate recuperate : list) {
            boolean equals = recuperate.getPeopleState1()==0;
            if(sysUser.getPermissions()!=null&&falg&&recuperate.getStatus3().equals("1")&&recuperate.getPeopleState1()==2){
                recuperate.setCommon("5");
                continue c;
            }
            //判断是否有驳回数据   有不显示审批按钮
            if (recuperate.getStatus1().equals("2") || recuperate.getStatus2().equals("2") || recuperate.getStatus3().equals("2")) {
                continue c;
            }
            if(recuperate.getStatus3().equals("1")){
                recuperate.setCommon("0");
                continue c;
            }
            //是否展示审批按钮
            Long userId2 = loginUser.getUserId();
            Integer peopleId3 = recuperate.getPeopleId3();
            boolean b1 = userId2 .equals( Long.valueOf(peopleId3));
            if (loginUser.getUserId().equals(Long.valueOf(recuperate.getPeopleId3()))) {
                if (recuperate.getStatus3().equals("0")&&recuperate.getStatus2().equals("1")) {
                    recuperate.setCommon("1");
                    recuperate.setSpJiBie("三级");
                    continue c;

                }
            }
            if (loginUser.getUserId().longValue()==Long.valueOf(recuperate.getPeopleId2())) {
                if (recuperate.getStatus2().equals("0")&&recuperate.getStatus1().equals("1")) {
                    recuperate.setCommon("1");
                    recuperate.setSpJiBie("二级");
                    continue c;
                }
            }
            Long userId = loginUser.getUserId();
            Integer peopleId1 = recuperate.getPeopleId1();
            boolean b = loginUser.getUserId() == Long.valueOf(recuperate.getPeopleId1());
            if (loginUser.getUserId().longValue()==Long.valueOf(recuperate.getPeopleId1())) {
                if (recuperate.getStatus1().equals("0")) {
                    recuperate.setCommon("1");
                    recuperate.setSpJiBie("一级");
                    continue c;
                }
            }
        }
        return getDataTable(list);
    }


    /**
     * 导出疗养管理列表
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:recuperate:export')")
    @Log(title = "疗养管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglRecuperate bdglRecuperate) {
        List<BdglRecuperate> list = bdglRecuperateService.selectBdglRecuperateList(bdglRecuperate);
        ExcelUtil<BdglRecuperate> util = new ExcelUtil<BdglRecuperate>(BdglRecuperate.class);
        return util.exportExcel(list, "疗养管理数据");
    }

    /**
     * 获取疗养管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:recuperate:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id) {

        return AjaxResult.success(bdglRecuperateService.selectBdglRecuperateById(id));
    }

    /**
     * 新增疗养管理
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:recuperate:add')")
    @Log(title = "疗养管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglRecuperate bdglRecuperate) {
        return toAjax(bdglRecuperateService.insertBdglRecuperate(bdglRecuperate));
    }

    /**
     * 修改疗养管理
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:recuperate:edit')")
    @Log(title = "疗养管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglRecuperate bdglRecuperate) {
        if(bdglRecuperate.getPeopleState1().equals(1)){
            BdglPeople bdglPeople = peopleService.selectBdglPeopleById(Long.valueOf(bdglRecuperate.getPeopleId()));
            bdglPeople.setReign(0);
            peopleService.updateBdglPeople(bdglPeople);

        }
        if(bdglRecuperate.getStatus3().equals("1")&&!bdglRecuperate.getPeopleState1().equals(1)){
            BdglPeople bdglPeople = peopleService.selectBdglPeopleById(Long.valueOf(bdglRecuperate.getPeopleId()));
            bdglPeople.setReign(7);
            peopleService.updateBdglPeople(bdglPeople);
            bdglRecuperate.setPeopleState1(2);
        }
        return toAjax(bdglRecuperateService.updateBdglRecuperate(bdglRecuperate));
    }

    /**
     * 审批
     */
    @PutMapping("/shenPi")
    public AjaxResult shenPi(@RequestBody BdglRecuperate bdglRecuperate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        BdglRecuperate bdglRecuperate1 = bdglRecuperateService.selectBdglRecuperateById(bdglRecuperate.getId());
        //如果一级未审批  给一级审批状态赋值
        if (bdglRecuperate1.getStatus1().equals("2")) {
            bdglRecuperate1.setStatus1(bdglRecuperate.getCommon());
            bdglRecuperate1.setPeople1Remark(bdglRecuperate.getRemark());//一级审批备注
            bdglRecuperate1.setPeople1Authtime(simpleDateFormat.format(new Date()));//审批时间
            if(bdglRecuperate.getCommon().equals("1")){
                bdglRecuperate1.setStatus("一级审批通过");
            }else{
                bdglRecuperate1.setStatus("一级审批驳回");
            }
            //一级已通过  二级未审批  给二级审批状态赋值
        } else if (bdglRecuperate1.getStatus2().equals("2") && bdglRecuperate1.getStatus1().equals("1")) {
            bdglRecuperate1.setStatus2(bdglRecuperate.getCommon());
            bdglRecuperate1.setPeople2Remark(bdglRecuperate.getRemark());//二级审批备注
            bdglRecuperate1.setPeople2Authtime(simpleDateFormat.format(new Date()));//审批时间
            if(bdglRecuperate.getCommon().equals("1")){
                bdglRecuperate1.setStatus("二级审批通过");
            }else{
                bdglRecuperate1.setStatus("二级审批驳回");
            }
            //二级已通过  三级未审批  给三级审批状态赋值
        } else {
            bdglRecuperate1.setPeople3Remark(bdglRecuperate.getRemark());//三级审批备注
            bdglRecuperate1.setStatus3(bdglRecuperate.getCommon());
            bdglRecuperate1.setPeople3Authtime(simpleDateFormat.format(new Date()));//审批时间
            if(bdglRecuperate.getCommon().equals("1")){
                bdglRecuperate1.setStatus("三级审批通过");
                BdglPeople bdglPeople = peopleService.selectBdglPeopleById(Long.valueOf(bdglRecuperate1.getPeopleId()));
                bdglPeople.setReign(7);
                peopleService.updateBdglPeople(bdglPeople);
                bdglRecuperate1.setPeopleState1(2);
            }else{
                bdglRecuperate1.setStatus("三级审批驳回");
            }
        }

        return toAjax(bdglRecuperateService.updateBdglRecuperate(bdglRecuperate1));
    }

    /**
     * 删除疗养管理
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:recuperate:remove')")
    @Log(title = "疗养管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax(bdglRecuperateService.deleteBdglRecuperateByIds(ids));
    }
}
