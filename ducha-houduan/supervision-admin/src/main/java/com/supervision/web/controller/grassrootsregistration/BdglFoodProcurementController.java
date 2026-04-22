package com.supervision.web.controller.grassrootsregistration;

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
import com.supervision.grassrootsregistration.domain.BdglFoodProcurement;
import com.supervision.grassrootsregistration.service.IBdglFoodProcurementService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 采购食材Controller
 * 
 * @author supervision
 * @date 2022-02-28
 */
@RestController
@RequestMapping("/grassrootsregistration/procurement")
public class BdglFoodProcurementController extends BaseController
{
    @Autowired
    private IBdglFoodProcurementService bdglFoodProcurementService;

    /**
     * 查询采购食材列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:procurement:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglFoodProcurement bdglFoodProcurement)
    {
        startPage();
        List<BdglFoodProcurement> list = bdglFoodProcurementService.selectBdglFoodProcurementList(bdglFoodProcurement);
        return getDataTable(list);
    }

    /**
     * 导出采购食材列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:procurement:export')")
    @Log(title = "采购食材", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglFoodProcurement bdglFoodProcurement)
    {
        List<BdglFoodProcurement> list = bdglFoodProcurementService.selectBdglFoodProcurementList(bdglFoodProcurement);
        ExcelUtil<BdglFoodProcurement> util = new ExcelUtil<BdglFoodProcurement>(BdglFoodProcurement.class);
        return util.exportExcel(list, "采购食材数据");
    }

    /**
     * 获取采购食材详细信息
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:procurement:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bdglFoodProcurementService.selectBdglFoodProcurementById(id));
    }

    /**
     * 新增采购食材
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:procurement:add')")
    @Log(title = "采购食材", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglFoodProcurement bdglFoodProcurement)
    {
        return toAjax(bdglFoodProcurementService.insertBdglFoodProcurement(bdglFoodProcurement));
    }

    /**
     * 修改采购食材
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:procurement:edit')")
    @Log(title = "采购食材", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglFoodProcurement bdglFoodProcurement)
    {
        return toAjax(bdglFoodProcurementService.updateBdglFoodProcurement(bdglFoodProcurement));
    }

    /**
     * 删除采购食材
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:procurement:remove')")
    @Log(title = "采购食材", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglFoodProcurementService.deleteBdglFoodProcurementByIds(ids));
    }
}
