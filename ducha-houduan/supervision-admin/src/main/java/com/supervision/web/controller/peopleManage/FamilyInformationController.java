package com.supervision.web.controller.peopleManage;

import java.util.List;
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
import com.supervision.peopleManage.domain.FamilyInformation;
import com.supervision.peopleManage.service.IFamilyInformationService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 家庭关系Controller
 * 
 * @author supervision
 * @date 2022-03-01
 */
@RestController
@RequestMapping("/peopleManage/information")
public class FamilyInformationController extends BaseController
{
    @Autowired
    private IFamilyInformationService familyInformationService;

    /**
     * 查询家庭关系列表
     */
    @PreAuthorize("@ss.hasPermi('peopleManage:information:list')")
    @GetMapping("/list")
    public TableDataInfo list(FamilyInformation familyInformation)
    {
        startPage();
        List<FamilyInformation> list = familyInformationService.selectFamilyInformationList(familyInformation);
        return getDataTable(list);
    }

    /**
     * 导出家庭关系列表
     */
    @PreAuthorize("@ss.hasPermi('peopleManage:information:export')")
    @Log(title = "家庭关系", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(FamilyInformation familyInformation)
    {
        List<FamilyInformation> list = familyInformationService.selectFamilyInformationList(familyInformation);
        ExcelUtil<FamilyInformation> util = new ExcelUtil<FamilyInformation>(FamilyInformation.class);
        return util.exportExcel(list, "家庭关系数据");
    }

    /**
     * 获取家庭关系详细信息
     */
    @PreAuthorize("@ss.hasPermi('peopleManage:information:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(familyInformationService.selectFamilyInformationById(id));
    }

    /**
     * 新增家庭关系
     */
    @PreAuthorize("@ss.hasPermi('peopleManage:information:add')")
    @Log(title = "家庭关系", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody FamilyInformation familyInformation)
    {
        return toAjax(familyInformationService.insertFamilyInformation(familyInformation));
    }

    /**
     * 修改家庭关系
     */
    @PreAuthorize("@ss.hasPermi('peopleManage:information:edit')")
    @Log(title = "家庭关系", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody FamilyInformation familyInformation)
    {
        return toAjax(familyInformationService.updateFamilyInformation(familyInformation));
    }

    /**
     * 删除家庭关系
     */
    @PreAuthorize("@ss.hasPermi('peopleManage:information:remove')")
    @Log(title = "家庭关系", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(familyInformationService.deleteFamilyInformationByIds(ids));
    }
}
