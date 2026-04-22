package com.supervision.web.controller.thought;

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
import com.supervision.thought.domain.ThoughtPlanResponsefu;
import com.supervision.thought.service.IThoughtPlanResponsefuService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 月教育响应附件Controller
 * 
 * @author supervision
 * @date 2022-04-08
 */
@RestController
@RequestMapping("/thought/responsefu")
public class ThoughtPlanResponsefuController extends BaseController
{
    @Autowired
    private IThoughtPlanResponsefuService thoughtPlanResponsefuService;

    /**
     * 查询月教育响应附件列表
     */
    @PreAuthorize("@ss.hasPermi('thought:responsefu:list')")
    @GetMapping("/list")
    public TableDataInfo list(ThoughtPlanResponsefu thoughtPlanResponsefu)
    {
        startPage();
        List<ThoughtPlanResponsefu> list = thoughtPlanResponsefuService.selectThoughtPlanResponsefuList(thoughtPlanResponsefu);
        return getDataTable(list);
    }

    /**
     * 导出月教育响应附件列表
     */
    @PreAuthorize("@ss.hasPermi('thought:responsefu:export')")
    @Log(title = "月教育响应附件", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ThoughtPlanResponsefu thoughtPlanResponsefu)
    {
        List<ThoughtPlanResponsefu> list = thoughtPlanResponsefuService.selectThoughtPlanResponsefuList(thoughtPlanResponsefu);
        ExcelUtil<ThoughtPlanResponsefu> util = new ExcelUtil<ThoughtPlanResponsefu>(ThoughtPlanResponsefu.class);
        return util.exportExcel(list, "月教育响应附件数据");
    }

    /**
     * 获取月教育响应附件详细信息
     */
    @PreAuthorize("@ss.hasPermi('thought:responsefu:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(thoughtPlanResponsefuService.selectThoughtPlanResponsefuById(id));
    }

    /**
     * 新增月教育响应附件
     */
    @PreAuthorize("@ss.hasPermi('thought:responsefu:add')")
    @Log(title = "月教育响应附件", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ThoughtPlanResponsefu thoughtPlanResponsefu)
    {
        return toAjax(thoughtPlanResponsefuService.insertThoughtPlanResponsefu(thoughtPlanResponsefu));
    }

    /**
     * 修改月教育响应附件
     */
    @PreAuthorize("@ss.hasPermi('thought:responsefu:edit')")
    @Log(title = "月教育响应附件", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ThoughtPlanResponsefu thoughtPlanResponsefu)
    {
        return toAjax(thoughtPlanResponsefuService.updateThoughtPlanResponsefu(thoughtPlanResponsefu));
    }

    /**
     * 删除月教育响应附件
     */
    @PreAuthorize("@ss.hasPermi('thought:responsefu:remove')")
    @Log(title = "月教育响应附件", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(thoughtPlanResponsefuService.deleteThoughtPlanResponsefuByIds(ids));
    }
}
