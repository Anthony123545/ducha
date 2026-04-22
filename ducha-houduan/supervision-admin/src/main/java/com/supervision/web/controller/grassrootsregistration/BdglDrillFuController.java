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
import com.supervision.grassrootsregistration.domain.BdglDrillFu;
import com.supervision.grassrootsregistration.service.IBdglDrillFuService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 军事训练登记簿附表Controller
 * 
 * @author supervision
 * @date 2022-05-20
 */
@RestController
@RequestMapping("/grassrootsregistration/drillFu")
public class BdglDrillFuController extends BaseController
{
    @Autowired
    private IBdglDrillFuService bdglDrillFuService;

    /**
     * 查询军事训练登记簿附表列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:drillFu:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglDrillFu bdglDrillFu)
    {
        startPage();
        List<BdglDrillFu> list = bdglDrillFuService.selectBdglDrillFuList(bdglDrillFu);
        return getDataTable(list);
    }

    /**
     * 导出军事训练登记簿附表列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:drillFu:export')")
    @Log(title = "军事训练登记簿附表", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglDrillFu bdglDrillFu)
    {
        List<BdglDrillFu> list = bdglDrillFuService.selectBdglDrillFuList(bdglDrillFu);
        ExcelUtil<BdglDrillFu> util = new ExcelUtil<BdglDrillFu>(BdglDrillFu.class);
        return util.exportExcel(list, "军事训练登记簿附表数据");
    }

    /**
     * 获取军事训练登记簿附表详细信息
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:drillFu:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(bdglDrillFuService.selectBdglDrillFuById(id));
    }

    /**
     * 新增军事训练登记簿附表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:drillFu:add')")
    @Log(title = "军事训练登记簿附表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglDrillFu bdglDrillFu)
    {
        return toAjax(bdglDrillFuService.insertBdglDrillFu(bdglDrillFu));
    }

    /**
     * 修改军事训练登记簿附表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:drillFu:edit')")
    @Log(title = "军事训练登记簿附表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglDrillFu bdglDrillFu)
    {
        return toAjax(bdglDrillFuService.updateBdglDrillFu(bdglDrillFu));
    }

    /**
     * 删除军事训练登记簿附表
     */
    //@PreAuthorize("@ss.hasPermi('grassrootsregistration:drillFu:remove')")
    @Log(title = "军事训练登记簿附表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable("id") Long id)
    {
        return toAjax(bdglDrillFuService.deleteBdglDrillFuById(id));
    }
}