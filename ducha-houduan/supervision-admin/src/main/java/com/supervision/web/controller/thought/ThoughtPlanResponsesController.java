package com.supervision.web.controller.thought;

import java.util.Date;
import java.util.List;

import com.supervision.system.service.ISysUserService;
import com.supervision.thought.domain.ThoughtPlanResponsefu;
import com.supervision.thought.domain.ThoughtResponseContent;
import com.supervision.thought.service.IThoughtPlanResponsefuService;
import com.supervision.thought.service.IThoughtResponseContentService;
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
import com.supervision.thought.domain.ThoughtPlanResponses;
import com.supervision.thought.service.IThoughtPlanResponsesService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 月教育计划响应Controller
 *
 * @author supervision
 * @date 2022-04-08
 */
@RestController
@RequestMapping("/thought/responses")
public class ThoughtPlanResponsesController extends BaseController {
    @Autowired
    private IThoughtPlanResponsesService thoughtPlanResponsesService;

    @Autowired
    private IThoughtPlanResponsefuService responsefuService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private IThoughtResponseContentService responseContentService;

    /**
     * 查询月教育计划响应列表
     */
    @PreAuthorize("@ss.hasPermi('thought:responses:list')")
    @GetMapping("/list")
    public TableDataInfo list(ThoughtPlanResponses thoughtPlanResponses) {
        startPage();
        List<ThoughtPlanResponses> list = thoughtPlanResponsesService.selectThoughtPlanResponsesList(thoughtPlanResponses);
        for (ThoughtPlanResponses planResponses : list) {
            //如果当签收件大于任务结束时间  任务状态为进行中  修改任务状态为已过期
            if(planResponses.getPlanEndTime().getTime()<new Date().getTime()&&planResponses.getState()==4){
                planResponses.setState(5);
                planResponses.setState(5);
                thoughtPlanResponsesService.updateThoughtPlanResponses(planResponses);
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出月教育计划响应列表
     */
    @PreAuthorize("@ss.hasPermi('thought:responses:export')")
    @Log(title = "月教育计划响应", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ThoughtPlanResponses thoughtPlanResponses) {
        List<ThoughtPlanResponses> list = thoughtPlanResponsesService.selectThoughtPlanResponsesList(thoughtPlanResponses);
        ExcelUtil<ThoughtPlanResponses> util = new ExcelUtil<ThoughtPlanResponses>(ThoughtPlanResponses.class);
        return util.exportExcel(list, "月教育计划响应数据");
    }

    /**
     * 获取月教育计划响应详细信息
     */
    @PreAuthorize("@ss.hasPermi('thought:responses:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        ThoughtPlanResponses planResponses = thoughtPlanResponsesService.selectThoughtPlanResponsesById(id);
        ThoughtPlanResponsefu thoughtPlanResponsefu = new ThoughtPlanResponsefu();
        thoughtPlanResponsefu.setPlanId(id);
        //响应附件
        List<ThoughtPlanResponsefu> thoughtPlanResponsefus = responsefuService.selectThoughtPlanResponsefuList(thoughtPlanResponsefu);
        planResponses.setPlanResponsefus(thoughtPlanResponsefus);
        //计划内容
        ThoughtResponseContent thoughtPlanFu = new ThoughtResponseContent();
        thoughtPlanFu.setPlanId(planResponses.getId());
        List<ThoughtResponseContent> thoughtPlanFus = responseContentService.selectThoughtResponseContentList(thoughtPlanFu);
        planResponses.setResponseContents(thoughtPlanFus);

        return AjaxResult.success(planResponses);


    }

    /**
     * 新增月教育计划响应
     */
    @PreAuthorize("@ss.hasPermi('thought:responses:add')")
    @Log(title = "月教育计划响应", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ThoughtPlanResponses thoughtPlanResponses) {
        return toAjax(thoughtPlanResponsesService.insertThoughtPlanResponses(thoughtPlanResponses));
    }

    /**
     * 修改月教育计划响应
     */
    @PreAuthorize("@ss.hasPermi('thought:responses:edit')")
    @Log(title = "月教育计划响应", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ThoughtPlanResponses thoughtPlanResponses) {
        //如果状态是驳回  修改内容响应状态
        if(thoughtPlanResponses.getState().equals(3)){
            List<ThoughtResponseContent> responseContents = thoughtPlanResponses.getResponseContents();
            for (ThoughtResponseContent responseContent : responseContents) {
                responseContent.setResponseStatus( "0");
                responseContentService.updateThoughtResponseContent(responseContent);
            }
            List<ThoughtPlanResponsefu> planResponsefus = thoughtPlanResponses.getPlanResponsefus();
            for (ThoughtPlanResponsefu responsefus : planResponsefus) {
                responsefuService.deleteThoughtPlanResponsefuById(responsefus.getId());
            }
        }
        return toAjax(thoughtPlanResponsesService.updateThoughtPlanResponses(thoughtPlanResponses));
    }

    /**
     * 删除月教育计划响应
     */
    @PreAuthorize("@ss.hasPermi('thought:responses:remove')")
    @Log(title = "月教育计划响应", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(thoughtPlanResponsesService.deleteThoughtPlanResponsesByIds(ids));
    }
}
