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
import com.supervision.grassrootsregistration.domain.BdglFoodShopping;
import com.supervision.grassrootsregistration.service.IBdglFoodShoppingService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 在食堂购物Controller
 * 
 * @author supervision
 * @date 2022-02-28
 */
@RestController
@RequestMapping("/grassrootsregistration/shopping")
public class BdglFoodShoppingController extends BaseController
{
    @Autowired
    private IBdglFoodShoppingService bdglFoodShoppingService;

    /**
     * 查询在食堂购物列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:shopping:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglFoodShopping bdglFoodShopping)
    {
        startPage();
        List<BdglFoodShopping> list = bdglFoodShoppingService.selectBdglFoodShoppingList(bdglFoodShopping);
        return getDataTable(list);
    }

    /**
     * 导出在食堂购物列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:shopping:export')")
    @Log(title = "在食堂购物", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglFoodShopping bdglFoodShopping)
    {
        List<BdglFoodShopping> list = bdglFoodShoppingService.selectBdglFoodShoppingList(bdglFoodShopping);
        ExcelUtil<BdglFoodShopping> util = new ExcelUtil<BdglFoodShopping>(BdglFoodShopping.class);
        return util.exportExcel(list, "在食堂购物数据");
    }

    /**
     * 获取在食堂购物详细信息
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:shopping:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bdglFoodShoppingService.selectBdglFoodShoppingById(id));
    }

    /**
     * 新增在食堂购物
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:shopping:add')")
    @Log(title = "在食堂购物", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglFoodShopping bdglFoodShopping)
    {
        return toAjax(bdglFoodShoppingService.insertBdglFoodShopping(bdglFoodShopping));
    }

    /**
     * 修改在食堂购物
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:shopping:edit')")
    @Log(title = "在食堂购物", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglFoodShopping bdglFoodShopping)
    {
        return toAjax(bdglFoodShoppingService.updateBdglFoodShopping(bdglFoodShopping));
    }

    /**
     * 删除在食堂购物
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:shopping:remove')")
    @Log(title = "在食堂购物", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglFoodShoppingService.deleteBdglFoodShoppingByIds(ids));
    }
}
