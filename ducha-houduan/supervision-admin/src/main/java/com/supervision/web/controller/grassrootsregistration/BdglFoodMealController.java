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
import com.supervision.grassrootsregistration.domain.BdglFoodMeal;
import com.supervision.grassrootsregistration.service.IBdglFoodMealService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 一日三餐Controller
 * 
 * @author supervision
 * @date 2022-03-01
 */
@RestController
@RequestMapping("/grassrootsregistration/foodMeal")
public class BdglFoodMealController extends BaseController
{
    @Autowired
    private IBdglFoodMealService bdglFoodMealService;

    /**
     * 查询一日三餐列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:foodMeal:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglFoodMeal bdglFoodMeal)
    {
        startPage();
        List<BdglFoodMeal> list = bdglFoodMealService.selectBdglFoodMealList(bdglFoodMeal);
        return getDataTable(list);
    }

    /**
     * 导出一日三餐列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:foodMeal:export')")
    @Log(title = "一日三餐", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglFoodMeal bdglFoodMeal)
    {
        List<BdglFoodMeal> list = bdglFoodMealService.selectBdglFoodMealList(bdglFoodMeal);
        ExcelUtil<BdglFoodMeal> util = new ExcelUtil<BdglFoodMeal>(BdglFoodMeal.class);
        return util.exportExcel(list, "一日三餐数据");
    }

    /**
     * 获取一日三餐详细信息
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:foodMeal:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bdglFoodMealService.selectBdglFoodMealById(id));
    }

    /**
     * 新增一日三餐
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:foodMeal:add')")
    @Log(title = "一日三餐", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglFoodMeal bdglFoodMeal)
    {
        return toAjax(bdglFoodMealService.insertBdglFoodMeal(bdglFoodMeal));
    }

    /**
     * 修改一日三餐
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:foodMeal:edit')")
    @Log(title = "一日三餐", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglFoodMeal bdglFoodMeal)
    {
        return toAjax(bdglFoodMealService.updateBdglFoodMeal(bdglFoodMeal));
    }

    /**
     * 删除一日三餐
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:foodMeal:remove')")
    @Log(title = "一日三餐", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglFoodMealService.deleteBdglFoodMealByIds(ids));
    }
}
