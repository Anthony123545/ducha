package com.supervision.web.controller.militaryvehicleManagement;

import java.util.Date;
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
import com.supervision.militaryvehicleManagement.domain.BdglDriver;
import com.supervision.militaryvehicleManagement.service.IBdglDriverService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 军车司机设置Controller
 * 
 * @author supervision
 * @date 2022-03-09
 */
@RestController
@RequestMapping("/militaryvehicleManagement/driver")
public class BdglDriverController extends BaseController
{
    @Autowired
    private IBdglDriverService bdglDriverService;

    /**
     * 查询军车司机设置列表
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:driver:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglDriver bdglDriver)
    {
        startPage();
        List<BdglDriver> list = bdglDriverService.selectBdglDriverList(bdglDriver);
        return getDataTable(list);
    }

    /**
     * 导出军车司机设置列表
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:driver:export')")
    @Log(title = "军车司机设置", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglDriver bdglDriver)
    {
        List<BdglDriver> list = bdglDriverService.selectBdglDriverList(bdglDriver);
        ExcelUtil<BdglDriver> util = new ExcelUtil<BdglDriver>(BdglDriver.class);
        return util.exportExcel(list, "军车司机设置数据");
    }

    /**
     * 获取军车司机设置详细信息
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:driver:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bdglDriverService.selectBdglDriverById(id));
    }

    /**
     * 新增军车司机设置
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:driver:add')")
    @Log(title = "军车司机设置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglDriver bdglDriver)
    {
        bdglDriver.setCreatetime(new Date());
        bdglDriver.setUpdatetime(new Date());
        return toAjax(bdglDriverService.insertBdglDriver(bdglDriver));
    }

    /**
     * 修改军车司机设置
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:driver:edit')")
    @Log(title = "军车司机设置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglDriver bdglDriver)
    {
        bdglDriver.setUpdatetime(new Date());
        return toAjax(bdglDriverService.updateBdglDriver(bdglDriver));
    }

    /**
     * 删除军车司机设置
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:driver:remove')")
    @Log(title = "军车司机设置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglDriverService.deleteBdglDriverByIds(ids));
    }
}
