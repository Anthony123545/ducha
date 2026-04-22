package com.supervision.web.controller.medicalhealth;

import java.util.Date;
import java.util.List;

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
import com.supervision.medicalhealth.domain.BdglDiagnose;
import com.supervision.medicalhealth.service.IBdglDiagnoseService;


/**
 * 外诊管理Controller
 * 
 * @author hr
 * @date 2022-02-14
 */
@RestController
@RequestMapping("/medicalhealth/diagnose")
public class BdglDiagnoseController extends BaseController
{
    @Autowired
    private IBdglDiagnoseService bdglDiagnoseService;
    @Autowired
    private ISysUserService userService;

    @Autowired
    private IBdglPeopleService peopleService;

    /**
     * 查询外诊管理列表
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:diagnose:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglDiagnose bdglDiagnose)
    {
        startPage();
        List<BdglDiagnose> list = bdglDiagnoseService.selectBdglDiagnoseList(bdglDiagnose);
        for (BdglDiagnose diagnose : list) {
            Date date = new Date();
            long time = date.getTime();
            long time1 = diagnose.getStartTime().getTime();
            if(time>=time1&&diagnose.getEndTime()==null){
                diagnose.setDuration(((time-time1)/(1000 * 60 * 60 * 24))+"");
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出外诊管理列表
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:diagnose:export')")
    @Log(title = "外诊管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglDiagnose bdglDiagnose)
    {
        List<BdglDiagnose> list = bdglDiagnoseService.selectBdglDiagnoseList(bdglDiagnose);
        ExcelUtil<BdglDiagnose> util = new ExcelUtil<BdglDiagnose>(BdglDiagnose.class);
        return util.exportExcel(list, "外诊管理数据");
    }

    /**
     * 获取外诊管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:diagnose:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        BdglDiagnose bdglDiagnose = bdglDiagnoseService.selectBdglDiagnoseById(id);
        return AjaxResult.success(bdglDiagnose);
    }

    /**
     * 新增外诊管理
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:diagnose:add')")
    @Log(title = "外诊管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglDiagnose bdglDiagnose)
    {
        LoginUser loginUser = getLoginUser();
        SysUser sysUser = userService.selectUserById(loginUser.getUserId());
        bdglDiagnose.setAdminId(Integer.parseInt(loginUser.getUserId().toString()));
        bdglDiagnose.setAdminname(sysUser.getNickName());
        return toAjax(bdglDiagnoseService.insertBdglDiagnose(bdglDiagnose));
    }

    /**
     * 修改外诊管理
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:diagnose:edit')")
    @Log(title = "外诊管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglDiagnose bdglDiagnose)
    {
        return toAjax(bdglDiagnoseService.updateBdglDiagnose(bdglDiagnose));
    }
    /**
     * 归队
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:diagnose:update')")
    @Log(title = "外诊管理", businessType = BusinessType.UPDATE)
    @PutMapping("/update")
    public AjaxResult update(@RequestBody BdglDiagnose bdglDiagnose)
    {
        BdglPeople bdglPeople = peopleService.selectBdglPeopleById(Long.valueOf(bdglDiagnose.getPeopleId()));
        bdglPeople.setReign(0);
        peopleService.updateBdglPeople(bdglPeople);
        return toAjax(bdglDiagnoseService.updateBdglDiagnose(bdglDiagnose));
    }

    /**
     * 删除外诊管理
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:diagnose:remove')")
    @Log(title = "外诊管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglDiagnoseService.deleteBdglDiagnoseByIds(ids));
    }
}
