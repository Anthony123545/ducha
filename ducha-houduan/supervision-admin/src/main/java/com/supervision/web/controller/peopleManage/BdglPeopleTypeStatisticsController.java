package com.supervision.web.controller.peopleManage;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.supervision.peopleManage.domain.BdglPeopleTypeStatistics;
import com.supervision.peopleManage.service.IBdglPeopleTypeStatisticsService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 人员类别数量Controller
 *
 * @author ruoyi
 * @date 2022-10-29
 */
@RestController
@RequestMapping("/peopleManage/typestatistics")
public class BdglPeopleTypeStatisticsController extends BaseController
{
    @Autowired
    private IBdglPeopleTypeStatisticsService bdglPeopleTypeStatisticsService;

    /**
     * 查询人员类别数量列表
     */
    @PreAuthorize("@ss.hasPermi('peopleManage:typestatistics:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglPeopleTypeStatistics bdglPeopleTypeStatistics)
    {
        startPage();
        List<BdglPeopleTypeStatistics> list = bdglPeopleTypeStatisticsService.selectBdglPeopleTypeStatisticsList(bdglPeopleTypeStatistics);
        return getDataTable(list);
    }

    /**
     * 导出人员类别数量列表
     */
    @PreAuthorize("@ss.hasPermi('peopleManage:typestatistics:export')")
    @Log(title = "人员类别数量", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BdglPeopleTypeStatistics bdglPeopleTypeStatistics)
    {
        List<BdglPeopleTypeStatistics> list = bdglPeopleTypeStatisticsService.selectBdglPeopleTypeStatisticsList(bdglPeopleTypeStatistics);
        ExcelUtil<BdglPeopleTypeStatistics> util = new ExcelUtil<BdglPeopleTypeStatistics>(BdglPeopleTypeStatistics.class);
        util.exportExcel(response, list, "人员类别数量数据");
    }

    /**
     * 获取人员类别数量详细信息
     */
    @PreAuthorize("@ss.hasPermi('peopleManage:typestatistics:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(bdglPeopleTypeStatisticsService.selectBdglPeopleTypeStatisticsById(id));
    }

    /**
     * 新增人员类别数量
     */
    @PreAuthorize("@ss.hasPermi('peopleManage:typestatistics:add')")
    @Log(title = "人员类别数量", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglPeopleTypeStatistics bdglPeopleTypeStatistics)
    {
        return toAjax(bdglPeopleTypeStatisticsService.insertBdglPeopleTypeStatistics(bdglPeopleTypeStatistics));
    }

    /**
     * 修改人员类别数量
     */
    @PreAuthorize("@ss.hasPermi('peopleManage:typestatistics:edit')")
    @Log(title = "人员类别数量", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglPeopleTypeStatistics bdglPeopleTypeStatistics)
    {
        return toAjax(bdglPeopleTypeStatisticsService.updateBdglPeopleTypeStatistics(bdglPeopleTypeStatistics));
    }

    /**
     * 删除人员类别数量
     */
    @PreAuthorize("@ss.hasPermi('peopleManage:typestatistics:remove')")
    @Log(title = "人员类别数量", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bdglPeopleTypeStatisticsService.deleteBdglPeopleTypeStatisticsByIds(ids));
    }
}
