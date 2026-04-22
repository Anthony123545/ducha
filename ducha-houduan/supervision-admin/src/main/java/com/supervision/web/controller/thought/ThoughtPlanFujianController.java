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
import com.supervision.thought.domain.ThoughtPlanFujian;
import com.supervision.thought.service.IThoughtPlanFujianService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 月教育计划附件Controller
 * 
 * @author supervision
 * @date 2022-04-08
 */
@RestController
@RequestMapping("/thought/planFujian")
public class ThoughtPlanFujianController extends BaseController
{
    @Autowired
    private IThoughtPlanFujianService thoughtPlanFujianService;

    /**
     * 查询月教育计划附件列表
     */
    @PreAuthorize("@ss.hasPermi('thought:planFujian:list')")
    @GetMapping("/list")
    public TableDataInfo list(ThoughtPlanFujian thoughtPlanFujian)
    {
        startPage();
        List<ThoughtPlanFujian> list = thoughtPlanFujianService.selectThoughtPlanFujianList(thoughtPlanFujian);
        return getDataTable(list);
    }

    /**
     * 导出月教育计划附件列表
     */
    @PreAuthorize("@ss.hasPermi('thought:planFujian:export')")
    @Log(title = "月教育计划附件", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ThoughtPlanFujian thoughtPlanFujian)
    {
        List<ThoughtPlanFujian> list = thoughtPlanFujianService.selectThoughtPlanFujianList(thoughtPlanFujian);
        ExcelUtil<ThoughtPlanFujian> util = new ExcelUtil<ThoughtPlanFujian>(ThoughtPlanFujian.class);
        return util.exportExcel(list, "月教育计划附件数据");
    }

    /**
     * 获取月教育计划附件详细信息
     */
    @PreAuthorize("@ss.hasPermi('thought:planFujian:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(thoughtPlanFujianService.selectThoughtPlanFujianById(id));
    }

    /**
     * 新增月教育计划附件
     */
    @PreAuthorize("@ss.hasPermi('thought:planFujian:add')")
    @Log(title = "月教育计划附件", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ThoughtPlanFujian thoughtPlanFujian)
    {
        return toAjax(thoughtPlanFujianService.insertThoughtPlanFujian(thoughtPlanFujian));
    }

    /**
     * 修改月教育计划附件
     */
    @PreAuthorize("@ss.hasPermi('thought:planFujian:edit')")
    @Log(title = "月教育计划附件", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ThoughtPlanFujian thoughtPlanFujian)
    {
        return toAjax(thoughtPlanFujianService.updateThoughtPlanFujian(thoughtPlanFujian));
    }

    /**
     * 删除月教育计划附件
     */
    @PreAuthorize("@ss.hasPermi('thought:planFujian:remove')")
    @Log(title = "月教育计划附件", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(thoughtPlanFujianService.deleteThoughtPlanFujianByIds(ids));
    }
}
