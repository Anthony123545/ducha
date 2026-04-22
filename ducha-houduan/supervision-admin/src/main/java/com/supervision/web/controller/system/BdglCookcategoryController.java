package com.supervision.web.controller.system;

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
import com.supervision.system.domain.BdglCookcategory;
import com.supervision.system.service.IBdglCookcategoryService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 食品分类Controller
 * 
 * @author supervision
 * @date 2022-03-08
 */
@RestController
@RequestMapping("/system/cookcategory")
public class BdglCookcategoryController extends BaseController
{
    @Autowired
    private IBdglCookcategoryService bdglCookcategoryService;

    /**
     * 查询食品分类列表
     */
    @PreAuthorize("@ss.hasPermi('system:cookcategory:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglCookcategory bdglCookcategory)
    {
        startPage();
        List<BdglCookcategory> list = bdglCookcategoryService.selectBdglCookcategoryList(bdglCookcategory);
        return getDataTable(list);
    }

    /**
     * 导出食品分类列表
     */
    @PreAuthorize("@ss.hasPermi('system:cookcategory:export')")
    @Log(title = "食品分类", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglCookcategory bdglCookcategory)
    {
        List<BdglCookcategory> list = bdglCookcategoryService.selectBdglCookcategoryList(bdglCookcategory);
        ExcelUtil<BdglCookcategory> util = new ExcelUtil<BdglCookcategory>(BdglCookcategory.class);
        return util.exportExcel(list, "食品分类数据");
    }

    /**
     * 获取食品分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:cookcategory:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bdglCookcategoryService.selectBdglCookcategoryById(id));
    }

    /**
     * 新增食品分类
     */
    @PreAuthorize("@ss.hasPermi('system:cookcategory:add')")
    @Log(title = "食品分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglCookcategory bdglCookcategory)
    {
        return toAjax(bdglCookcategoryService.insertBdglCookcategory(bdglCookcategory));
    }

    /**
     * 修改食品分类
     */
    @PreAuthorize("@ss.hasPermi('system:cookcategory:edit')")
    @Log(title = "食品分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglCookcategory bdglCookcategory)
    {
        return toAjax(bdglCookcategoryService.updateBdglCookcategory(bdglCookcategory));
    }

    /**
     * 删除食品分类
     */
    @PreAuthorize("@ss.hasPermi('system:cookcategory:remove')")
    @Log(title = "食品分类", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglCookcategoryService.deleteBdglCookcategoryByIds(ids));
    }
}
