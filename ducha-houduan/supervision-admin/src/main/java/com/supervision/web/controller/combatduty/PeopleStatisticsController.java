package com.supervision.web.controller.combatduty;

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
import com.supervision.combatduty.domain.PeopleStatistics;
import com.supervision.combatduty.service.IPeopleStatisticsService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 人员变动Controller
 * 
 * @author ruoyi
 * @date 2022-08-25
 */
@RestController
@RequestMapping("/combatduty/PeopleStatistics")
public class PeopleStatisticsController extends BaseController
{
    @Autowired
    private IPeopleStatisticsService peopleStatisticsService;

    /**
     * 查询人员变动列表
     */
    @PreAuthorize("@ss.hasPermi('combatduty:PeopleStatistics:list')")
    @GetMapping("/list")
    public TableDataInfo list(PeopleStatistics peopleStatistics)
    {
        startPage();
        List<PeopleStatistics> list = peopleStatisticsService.selectPeopleStatisticsList(peopleStatistics);
        return getDataTable(list);
    }

    /**
     * 导出人员变动列表
     */
    @PreAuthorize("@ss.hasPermi('combatduty:PeopleStatistics:export')")
    @Log(title = "人员变动", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PeopleStatistics peopleStatistics)
    {
        List<PeopleStatistics> list = peopleStatisticsService.selectPeopleStatisticsList(peopleStatistics);
        ExcelUtil<PeopleStatistics> util = new ExcelUtil<PeopleStatistics>(PeopleStatistics.class);
        util.exportExcel(response, list, "人员变动数据");
    }

    /**
     * 获取人员变动详细信息
     */
    @PreAuthorize("@ss.hasPermi('combatduty:PeopleStatistics:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(peopleStatisticsService.selectPeopleStatisticsById(id));
    }

    /**
     * 新增人员变动
     */
    @PreAuthorize("@ss.hasPermi('combatduty:PeopleStatistics:add')")
    @Log(title = "人员变动", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PeopleStatistics peopleStatistics)
    {
        PeopleStatistics peopleStatistics1 = new PeopleStatistics();

        List<PeopleStatistics> list = peopleStatisticsService.selectPeopleStatisticsList(peopleStatistics1);
        //如果存在则修改
        if(list!=null&&list.size()>0){
            return toAjax(peopleStatisticsService.updatePeopleStatistics(peopleStatistics));
        }
        return toAjax(peopleStatisticsService.insertPeopleStatistics(peopleStatistics));
    }

    /**
     * 修改人员变动
     */
    @PreAuthorize("@ss.hasPermi('combatduty:PeopleStatistics:edit')")
    @Log(title = "人员变动", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PeopleStatistics peopleStatistics)
    {
        return toAjax(peopleStatisticsService.updatePeopleStatistics(peopleStatistics));
    }

    /**
     * 删除人员变动
     */
    @PreAuthorize("@ss.hasPermi('combatduty:PeopleStatistics:remove')")
    @Log(title = "人员变动", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(peopleStatisticsService.deletePeopleStatisticsByIds(ids));
    }
}