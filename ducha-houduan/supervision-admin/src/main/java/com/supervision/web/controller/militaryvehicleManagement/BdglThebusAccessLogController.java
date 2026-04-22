package com.supervision.web.controller.militaryvehicleManagement;

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
import com.supervision.militaryvehicleManagement.domain.BdglThebusAccessLog;
import com.supervision.militaryvehicleManagement.service.IBdglThebusAccessLogService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 军车进出查询Controller
 * 
 * @author supervision
 * @date 2022-03-09
 */
@RestController
@RequestMapping("/militaryvehicleManagement/thebusAccessLog")
public class BdglThebusAccessLogController extends BaseController
{
    @Autowired
    private IBdglThebusAccessLogService bdglThebusAccessLogService;

    /**
     * 查询军车进出查询列表
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:thebusAccessLog:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglThebusAccessLog bdglThebusAccessLog)
    {
        startPage();
        List<BdglThebusAccessLog> list = bdglThebusAccessLogService.selectBdglThebusAccessLogList(bdglThebusAccessLog);
        return getDataTable(list);
    }

    /**
     * 导出军车进出查询列表
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:thebusAccessLog:export')")
    @Log(title = "军车进出查询", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglThebusAccessLog bdglThebusAccessLog)
    {
        List<BdglThebusAccessLog> list = bdglThebusAccessLogService.selectBdglThebusAccessLogList(bdglThebusAccessLog);
        ExcelUtil<BdglThebusAccessLog> util = new ExcelUtil<BdglThebusAccessLog>(BdglThebusAccessLog.class);
        return util.exportExcel(list, "军车进出查询数据");
    }

    /**
     * 获取军车进出查询详细信息
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:thebusAccessLog:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bdglThebusAccessLogService.selectBdglThebusAccessLogById(id));
    }

    /**
     * 新增军车进出查询
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:thebusAccessLog:add')")
    @Log(title = "军车进出查询", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglThebusAccessLog bdglThebusAccessLog)
    {
        return toAjax(bdglThebusAccessLogService.insertBdglThebusAccessLog(bdglThebusAccessLog));
    }

    /**
     * 修改军车进出查询
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:thebusAccessLog:edit')")
    @Log(title = "军车进出查询", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglThebusAccessLog bdglThebusAccessLog)
    {
        return toAjax(bdglThebusAccessLogService.updateBdglThebusAccessLog(bdglThebusAccessLog));
    }

    /**
     * 删除军车进出查询
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:thebusAccessLog:remove')")
    @Log(title = "军车进出查询", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglThebusAccessLogService.deleteBdglThebusAccessLogByIds(ids));
    }
}
