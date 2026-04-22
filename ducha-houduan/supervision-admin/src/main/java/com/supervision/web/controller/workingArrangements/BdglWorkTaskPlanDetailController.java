package com.supervision.web.controller.workingArrangements;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import com.supervision.common.utils.StringUtils;
import com.supervision.workingArrangements.domain.BdglWorkTaskPlanAnner;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.supervision.common.annotation.Log;
import com.supervision.common.core.controller.BaseController;
import com.supervision.common.core.domain.AjaxResult;
import com.supervision.common.enums.BusinessType;
import com.supervision.workingArrangements.domain.BdglWorkTaskPlanDetail;
import com.supervision.workingArrangements.service.IBdglWorkTaskPlanDetailService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 任务计划执行详情Controller
 * 
 * @author ruoyi
 * @date 2022-08-15
 */
@RestController
@RequestMapping("/workingArrangements/taskplandetail")
public class BdglWorkTaskPlanDetailController extends BaseController
{
    @Autowired
    private IBdglWorkTaskPlanDetailService bdglWorkTaskPlanDetailService;

    /**
     * 查询任务计划执行详情列表
     */
    @PreAuthorize("@ss.hasPermi('workingArrangements:worktaskplan:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglWorkTaskPlanDetail workTaskPlanDetail)
    {
        startPage();
        List<BdglWorkTaskPlanDetail> list = bdglWorkTaskPlanDetailService.selectBdglWorkTaskPlanDetailList(workTaskPlanDetail);
        return getDataTable(list);
    }
    
    /**
     * 根据任务ID查询任务计划执行详情列表
     * @return
     */
    @PreAuthorize("@ss.hasPermi('workingArrangements:worktaskplan:list')")
    @GetMapping("/getBytTskPlanId/{taskPlanId}")
    public AjaxResult getBytTskPlanId(@PathVariable Long taskPlanId)
    {
        List<BdglWorkTaskPlanDetail> list = bdglWorkTaskPlanDetailService.selectWorkTaskPlanDetailListByTaskPlanId(taskPlanId);
        return AjaxResult.success(list);
    }

    /**
     * 获取任务计划执行详情详细信息
     */
    @PreAuthorize("@ss.hasPermi('workingArrangements:worktaskplan:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(bdglWorkTaskPlanDetailService.selectBdglWorkTaskPlanDetailById(id));
    }

    /**
     * 新增任务计划执行详情
     */
    @PreAuthorize("@ss.hasPermi('workingArrangements:worktaskplan:add')")
    @Log(title = "任务计划执行详情", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglWorkTaskPlanDetail workTaskPlanDetail)
    {
        return toAjax(bdglWorkTaskPlanDetailService.insertBdglWorkTaskPlanDetail(workTaskPlanDetail));
    }
    /**
     * 新增任务计划执行详情附件
     */
    @PreAuthorize("@ss.hasPermi('workingArrangements:taskplandetail:add')")
    @Log(title = "任务计划执行详情附件", businessType = BusinessType.INSERT)
    @PostMapping("/addAnnex")
    public AjaxResult addAnnex(@RequestBody BdglWorkTaskPlanDetail detail)
    {
        return toAjax(bdglWorkTaskPlanDetailService.insertBdglWorkTaskPlanDetailAnnex(detail));
    }

    /**
     * 修改任务计划执行详情
     */
    @PreAuthorize("@ss.hasPermi('workingArrangements:worktaskplan:edit')")
    @Log(title = "任务计划执行详情", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglWorkTaskPlanDetail workTaskPlanDetail)
    {
        
        return toAjax(bdglWorkTaskPlanDetailService.updateBdglWorkTaskPlanDetail(workTaskPlanDetail));
    }
    
    /**
     * 任务执行详情审核
     */
    @PreAuthorize("@ss.hasPermi('workingArrangements:worktaskplan:edit')")
    @Log(title = "任务计划执行详情审核", businessType = BusinessType.UPDATE)
    @PutMapping("/audit")
    public AjaxResult audit(@RequestBody Map<String,String> map) {
        BdglWorkTaskPlanDetail planDetail = new BdglWorkTaskPlanDetail();
        String id = map.get("id");
        String taskStatus = map.get("taskStatus");
        //审核驳回
        if (StringUtils.isNotEmpty(taskStatus) && "3".equals(taskStatus)){
            String rejectContent = map.get("rejectContent");
            planDetail.setRejectContent(rejectContent);
        }
        planDetail.setId(Long.valueOf(id));
        planDetail.setTaskStatus(Long.valueOf(taskStatus));
        
        return toAjax(bdglWorkTaskPlanDetailService.updateBdglWorkTaskPlanDetail(planDetail));
    }

    /**
     * 删除任务计划执行详情
     */
    @PreAuthorize("@ss.hasPermi('workingArrangements:worktaskplan:remove')")
    @Log(title = "任务计划执行详情", businessType = BusinessType.DELETE)
	@DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id)
    {
        return toAjax(bdglWorkTaskPlanDetailService.deleteBdglWorkTaskPlanDetailById(id));
    }
}
