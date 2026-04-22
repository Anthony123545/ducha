package com.supervision.web.controller.workingArrangements;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.supervision.common.utils.StringUtils;
import com.supervision.workingArrangements.resultVo.WorkTaskPlanVo;
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
import com.supervision.workingArrangements.domain.BdglWorkTaskPlan;
import com.supervision.workingArrangements.service.IBdglWorkTaskPlanService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 任务计划基本信息Controller
 * 
 * @author ruoyi
 * @date 2022-08-15
 */
@RestController
@RequestMapping("/workingArrangements/worktaskplan")
public class BdglWorkTaskPlanController extends BaseController
{
    @Autowired
    private IBdglWorkTaskPlanService bdglWorkTaskPlanService;

    /**
     * 查询任务计划基本信息列表
     */
    @PreAuthorize("@ss.hasPermi('workingArrangements:worktaskplan:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglWorkTaskPlan bdglWorkTaskPlan)
    {
        startPage();
        List<WorkTaskPlanVo> voList = bdglWorkTaskPlanService.selectBdglWorkTaskPlanListVo(bdglWorkTaskPlan);
        return getDataTable(voList);
    }
    
    /**
     * 执行任务查询任务信息
     */
    @PreAuthorize("@ss.hasPermi('workingArrangements:worktaskplan:list')")
    @GetMapping("/executeList")
    public TableDataInfo getList(BdglWorkTaskPlan bdglWorkTaskPlan)
    {
        startPage();
        List<WorkTaskPlanVo> voList = bdglWorkTaskPlanService.selectWorkTaskPlanListVo(bdglWorkTaskPlan);
        return getDataTable(voList);
    }

    /**
     * 导出任务计划基本信息列表
     */
    @PreAuthorize("@ss.hasPermi('workingArrangements:worktaskplan:export')")
    @Log(title = "任务计划基本信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BdglWorkTaskPlan bdglWorkTaskPlan)
    {
        List<BdglWorkTaskPlan> list = bdglWorkTaskPlanService.selectBdglWorkTaskPlanList(bdglWorkTaskPlan);
        ExcelUtil<BdglWorkTaskPlan> util = new ExcelUtil<BdglWorkTaskPlan>(BdglWorkTaskPlan.class);
        util.exportExcel(response, list, "任务计划基本信息数据");
    }

    /**
     * 获取任务计划基本信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('workingArrangements:worktaskplan:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(bdglWorkTaskPlanService.selectBdglWorkTaskPlanById(id));
    }

    /**
     * 新增任务计划基本信息
     */
    @PreAuthorize("@ss.hasPermi('workingArrangements:worktaskplan:add')")
    @Log(title = "任务计划基本信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglWorkTaskPlan bdglWorkTaskPlan)
    {
        return toAjax(bdglWorkTaskPlanService.insertBdglWorkTaskPlan(bdglWorkTaskPlan));
    }
    
    /**
     * 修改任务计划基本信息
     */
    @PreAuthorize("@ss.hasPermi('workingArrangements:worktaskplan:edit')")
    @Log(title = "任务计划基本信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglWorkTaskPlan bdglWorkTaskPlan)
    {
        return toAjax(bdglWorkTaskPlanService.updateBdglWorkTaskPlan(bdglWorkTaskPlan));
    }

    /**
     * 删除任务计划基本信息
     */
    @PreAuthorize("@ss.hasPermi('workingArrangements:worktaskplan:remove')")
    @Log(title = "任务计划基本信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bdglWorkTaskPlanService.deleteBdglWorkTaskPlanByIds(ids));
    }
    
}
