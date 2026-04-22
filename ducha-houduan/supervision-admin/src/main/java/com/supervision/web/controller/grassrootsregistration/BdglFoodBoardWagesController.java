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
import com.supervision.grassrootsregistration.domain.BdglFoodBoardWages;
import com.supervision.grassrootsregistration.service.IBdglFoodBoardWagesService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 交/退伙食费Controller
 * 
 * @author supervision
 * @date 2022-02-28
 */
@RestController
@RequestMapping("/grassrootsregistration/boardwages")
public class BdglFoodBoardWagesController extends BaseController
{
    @Autowired
    private IBdglFoodBoardWagesService bdglFoodBoardWagesService;

    /**
     * 查询交/退伙食费列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:boardwages:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglFoodBoardWages bdglFoodBoardWages)
    {
        startPage();
        List<BdglFoodBoardWages> list = bdglFoodBoardWagesService.selectBdglFoodBoardWagesList(bdglFoodBoardWages);
        return getDataTable(list);
    }

    /**
     * 导出交/退伙食费列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:boardwages:export')")
    @Log(title = "交/退伙食费", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglFoodBoardWages bdglFoodBoardWages)
    {
        List<BdglFoodBoardWages> list = bdglFoodBoardWagesService.selectBdglFoodBoardWagesList(bdglFoodBoardWages);
        ExcelUtil<BdglFoodBoardWages> util = new ExcelUtil<BdglFoodBoardWages>(BdglFoodBoardWages.class);
        return util.exportExcel(list, "交/退伙食费数据");
    }

    /**
     * 获取交/退伙食费详细信息
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:boardwages:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bdglFoodBoardWagesService.selectBdglFoodBoardWagesById(id));
    }

    /**
     * 新增交/退伙食费
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:boardwages:add')")
    @Log(title = "交/退伙食费", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglFoodBoardWages bdglFoodBoardWages)
    {
        return toAjax(bdglFoodBoardWagesService.insertBdglFoodBoardWages(bdglFoodBoardWages));
    }

    /**
     * 修改交/退伙食费
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:boardwages:edit')")
    @Log(title = "交/退伙食费", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglFoodBoardWages bdglFoodBoardWages)
    {
        return toAjax(bdglFoodBoardWagesService.updateBdglFoodBoardWages(bdglFoodBoardWages));
    }

    /**
     * 删除交/退伙食费
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:boardwages:remove')")
    @Log(title = "交/退伙食费", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglFoodBoardWagesService.deleteBdglFoodBoardWagesByIds(ids));
    }
}
