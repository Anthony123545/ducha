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
import com.supervision.grassrootsregistration.domain.BdglStatistics;
import com.supervision.grassrootsregistration.service.IBdglStatisticsService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 军事训练月统计班Controller
 * 
 * @author supervision
 * @date 2022-03-21
 */
@RestController
@RequestMapping("/grassrootsregistration/statistics")
public class BdglStatisticsController extends BaseController
{
    @Autowired
    private IBdglStatisticsService bdglStatisticsService;

    /**
     * 查询军事训练月统计班列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:statistics:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglStatistics bdglStatistics)
    {
        startPage();
        List<BdglStatistics> list = bdglStatisticsService.selectBdglStatisticsList(bdglStatistics);
        return getDataTable(list);
    }

    /**
     * 导出军事训练月统计班列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:statistics:export')")
    @Log(title = "军事训练月统计班", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglStatistics bdglStatistics)
    {
        List<BdglStatistics> list = bdglStatisticsService.selectBdglStatisticsList(bdglStatistics);
        ExcelUtil<BdglStatistics> util = new ExcelUtil<BdglStatistics>(BdglStatistics.class);
        return util.exportExcel(list, "军事训练月统计班数据");
    }

    /**
     * 获取军事训练月统计班详细信息
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:statistics:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bdglStatisticsService.selectBdglStatisticsById(id));
    }

    /**
     * 新增军事训练月统计班
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:statistics:add')")
    @Log(title = "军事训练月统计班", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglStatistics bdglStatistics)
    {
        return toAjax(bdglStatisticsService.insertBdglStatistics(bdglStatistics));
    }

    /**
     * 修改军事训练月统计班
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:statistics:edit')")
    @Log(title = "军事训练月统计班", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglStatistics bdglStatistics)
    {
        return toAjax(bdglStatisticsService.updateBdglStatistics(bdglStatistics));
    }

    /**
     * 删除军事训练月统计班
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:statistics:remove')")
    @Log(title = "军事训练月统计班", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglStatisticsService.deleteBdglStatisticsByIds(ids));
    }
}
