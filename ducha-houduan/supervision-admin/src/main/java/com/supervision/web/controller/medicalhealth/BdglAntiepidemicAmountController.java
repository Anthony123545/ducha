package com.supervision.web.controller.medicalhealth;

import java.util.List;

import com.supervision.common.annotation.Log;
import com.supervision.common.core.controller.BaseController;
import com.supervision.common.core.domain.AjaxResult;
import com.supervision.common.core.page.TableDataInfo;
import com.supervision.common.enums.BusinessType;
import com.supervision.common.utils.poi.ExcelUtil;
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
import com.supervision.medicalhealth.domain.BdglAntiepidemicAmount;
import com.supervision.medicalhealth.service.IBdglAntiepidemicAmountService;


/**
 * 防疫用品库存数据Controller
 * 
 * @author hr
 * @date 2022-02-14
 */
@RestController
@RequestMapping("/medicalhealth/antiepidemicamount")
public class BdglAntiepidemicAmountController extends BaseController
{
    @Autowired
    private IBdglAntiepidemicAmountService bdglAntiepidemicAmountService;

    /**
     * 查询防疫用品库存数据列表
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:antiepidemicamount:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglAntiepidemicAmount bdglAntiepidemicAmount)
    {
        startPage();
        List<BdglAntiepidemicAmount> list = bdglAntiepidemicAmountService.selectBdglAntiepidemicAmountList(bdglAntiepidemicAmount);
        return getDataTable(list);
    }

    /**
     * 导出防疫用品库存数据列表
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:antiepidemicamount:export')")
    @Log(title = "防疫用品库存数据", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglAntiepidemicAmount bdglAntiepidemicAmount)
    {
        List<BdglAntiepidemicAmount> list = bdglAntiepidemicAmountService.selectBdglAntiepidemicAmountList(bdglAntiepidemicAmount);
        ExcelUtil<BdglAntiepidemicAmount> util = new ExcelUtil<BdglAntiepidemicAmount>(BdglAntiepidemicAmount.class);
        return util.exportExcel(list, "防疫用品库存数据数据");
    }

    /**
     * 获取防疫用品库存数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:antiepidemicamount:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(bdglAntiepidemicAmountService.selectBdglAntiepidemicAmountById(id));
    }

    /**
     * 新增防疫用品库存数据
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:antiepidemicamount:add')")
    @Log(title = "防疫用品库存数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglAntiepidemicAmount bdglAntiepidemicAmount)
    {
        return toAjax(bdglAntiepidemicAmountService.insertBdglAntiepidemicAmount(bdglAntiepidemicAmount));
    }

    /**
     * 修改防疫用品库存数据
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:antiepidemicamount:edit')")
    @Log(title = "防疫用品库存数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglAntiepidemicAmount bdglAntiepidemicAmount)
    {
        return toAjax(bdglAntiepidemicAmountService.updateBdglAntiepidemicAmount(bdglAntiepidemicAmount));
    }

    /**
     * 删除防疫用品库存数据
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:antiepidemicamount:remove')")
    @Log(title = "防疫用品库存数据", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bdglAntiepidemicAmountService.deleteBdglAntiepidemicAmountByIds(ids));
    }
}
