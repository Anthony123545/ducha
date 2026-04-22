package com.supervision.web.controller.barracksManagement;

import java.util.List;

import cn.hutool.core.bean.BeanUtil;
import com.supervision.barracksManagement.domain.dto.MaintenanceRecordDto;
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
import com.supervision.barracksManagement.domain.MaintenanceRecord;
import com.supervision.barracksManagement.service.IMaintenanceRecordService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 五小工维修记录Controller
 * 
 * @author supervision
 * @date 2022-05-20
 */
@RestController
@RequestMapping("/barracksManagement/maintenanceRecord")
public class MaintenanceRecordController extends BaseController
{
    @Autowired
    private IMaintenanceRecordService maintenanceRecordService;

    /**
     * 查询五小工维修记录列表
     */
    @PreAuthorize("@ss.hasPermi('barracksManagement:maintenanceRecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(MaintenanceRecord maintenanceRecord)
    {
        startPage();
        List<MaintenanceRecord> list = maintenanceRecordService.selectMaintenanceRecordList(maintenanceRecord);
        return getDataTable(list);
    }

    /**
     * 导出五小工维修记录列表
     */
    @PreAuthorize("@ss.hasPermi('barracksManagement:maintenanceRecord:export')")
    @Log(title = "五小工维修记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(MaintenanceRecord maintenanceRecord)
    {
        List<MaintenanceRecord> list = maintenanceRecordService.selectMaintenanceRecordList(maintenanceRecord);
        List<MaintenanceRecordDto> dtoList = BeanUtil.copyToList(list, MaintenanceRecordDto.class);
        ExcelUtil<MaintenanceRecordDto> util = new ExcelUtil<>(MaintenanceRecordDto.class);
        return util.exportExcel(dtoList, "五小工维修记录数据");
    }

    /**
     * 获取五小工维修记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('barracksManagement:maintenanceRecord:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(maintenanceRecordService.selectMaintenanceRecordById(id));
    }

    /**
     * 新增五小工维修记录
     */
    @PreAuthorize("@ss.hasPermi('barracksManagement:maintenanceRecord:add')")
    @Log(title = "五小工维修记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MaintenanceRecord maintenanceRecord)
    {
        return toAjax(maintenanceRecordService.insertMaintenanceRecord(maintenanceRecord));
    }

    /**
     * 修改五小工维修记录
     */
    @PreAuthorize("@ss.hasPermi('barracksManagement:maintenanceRecord:edit')")
    @Log(title = "五小工维修记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MaintenanceRecord maintenanceRecord)
    {
        return toAjax(maintenanceRecordService.updateMaintenanceRecord(maintenanceRecord));
    }

    /**
     * 删除五小工维修记录
     */
    @PreAuthorize("@ss.hasPermi('barracksManagement:maintenanceRecord:remove')")
    @Log(title = "五小工维修记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(maintenanceRecordService.deleteMaintenanceRecordByIds(ids));
    }
}