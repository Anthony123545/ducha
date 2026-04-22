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
import com.supervision.grassrootsregistration.domain.BdglFoodKind;
import com.supervision.grassrootsregistration.service.IBdglFoodKindService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 实物检查验收Controller
 * 
 * @author supervision
 * @date 2022-02-28
 */
@RestController
@RequestMapping("/grassrootsregistration/bdglfoodkind")
public class BdglFoodKindController extends BaseController
{
    @Autowired
    private IBdglFoodKindService bdglFoodKindService;

    /**
     * 查询实物检查验收列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglfoodkind:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglFoodKind bdglFoodKind)
    {
        startPage();
        List<BdglFoodKind> list = bdglFoodKindService.selectBdglFoodKindList(bdglFoodKind);
        return getDataTable(list);
    }

    /**
     * 导出实物检查验收列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglfoodkind:export')")
    @Log(title = "实物检查验收", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglFoodKind bdglFoodKind)
    {
        List<BdglFoodKind> list = bdglFoodKindService.selectBdglFoodKindList(bdglFoodKind);
        ExcelUtil<BdglFoodKind> util = new ExcelUtil<BdglFoodKind>(BdglFoodKind.class);
        return util.exportExcel(list, "实物检查验收数据");
    }

    /**
     * 获取实物检查验收详细信息
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglfoodkind:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bdglFoodKindService.selectBdglFoodKindById(id));
    }

    /**
     * 新增实物检查验收
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglfoodkind:add')")
    @Log(title = "实物检查验收", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglFoodKind bdglFoodKind)
    {
        return toAjax(bdglFoodKindService.insertBdglFoodKind(bdglFoodKind));
    }

    /**
     * 修改实物检查验收
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglfoodkind:edit')")
    @Log(title = "实物检查验收", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglFoodKind bdglFoodKind)
    {
        return toAjax(bdglFoodKindService.updateBdglFoodKind(bdglFoodKind));
    }

    /**
     * 删除实物检查验收
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglfoodkind:remove')")
    @Log(title = "实物检查验收", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglFoodKindService.deleteBdglFoodKindByIds(ids));
    }
}
