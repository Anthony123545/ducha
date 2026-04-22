package com.supervision.web.controller.militaryvehicleManagement;

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
import com.supervision.militaryvehicleManagement.domain.BdglThebusThemplate;
import com.supervision.militaryvehicleManagement.service.IBdglThebusThemplateService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 军车审批模板Controller
 * 
 * @author supervision
 * @date 2022-03-04
 */
@RestController
@RequestMapping("/militaryvehicleManagement/thebusThemplate")
public class BdglThebusThemplateController extends BaseController
{
    @Autowired
    private IBdglThebusThemplateService bdglThebusThemplateService;

    /**
     * 查询军车审批模板列表
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:thebusThemplate:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglThebusThemplate bdglThebusThemplate)
    {
        startPage();
        List<BdglThebusThemplate> list = bdglThebusThemplateService.selectBdglThebusThemplateList(bdglThebusThemplate);
        return getDataTable(list);
    }

    /**
     * 导出军车审批模板列表
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:thebusThemplate:export')")
    @Log(title = "军车审批模板", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglThebusThemplate bdglThebusThemplate)
    {
        List<BdglThebusThemplate> list = bdglThebusThemplateService.selectBdglThebusThemplateList(bdglThebusThemplate);
        ExcelUtil<BdglThebusThemplate> util = new ExcelUtil<BdglThebusThemplate>(BdglThebusThemplate.class);
        return util.exportExcel(list, "军车审批模板数据");
    }

    /**
     * 获取军车审批模板详细信息
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:thebusThemplate:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bdglThebusThemplateService.selectBdglThebusThemplateById(id));
    }

    /**
     * 新增军车审批模板
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:thebusThemplate:add')")
    @Log(title = "军车审批模板", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglThebusThemplate bdglThebusThemplate)
    {
        return toAjax(bdglThebusThemplateService.insertBdglThebusThemplate(bdglThebusThemplate));
    }

    /**
     * 修改军车审批模板
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:thebusThemplate:edit')")
    @Log(title = "军车审批模板", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglThebusThemplate bdglThebusThemplate)
    {
        return toAjax(bdglThebusThemplateService.updateBdglThebusThemplate(bdglThebusThemplate));
    }

    /**
     * 删除军车审批模板
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:thebusThemplate:remove')")
    @Log(title = "军车审批模板", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglThebusThemplateService.deleteBdglThebusThemplateByIds(ids));
    }
}
