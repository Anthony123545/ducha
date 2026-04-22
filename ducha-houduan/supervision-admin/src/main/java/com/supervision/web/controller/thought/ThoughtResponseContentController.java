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
import com.supervision.thought.domain.ThoughtResponseContent;
import com.supervision.thought.service.IThoughtResponseContentService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 响应内容Controller
 * 
 * @author supervision
 * @date 2022-04-14
 */
@RestController
@RequestMapping("/thought/content")
public class ThoughtResponseContentController extends BaseController
{
    @Autowired
    private IThoughtResponseContentService thoughtResponseContentService;

    /**
     * 查询响应内容列表
     */
    @PreAuthorize("@ss.hasPermi('thought:content:list')")
    @GetMapping("/list")
    public TableDataInfo list(ThoughtResponseContent thoughtResponseContent)
    {
        startPage();
        List<ThoughtResponseContent> list = thoughtResponseContentService.selectThoughtResponseContentList(thoughtResponseContent);
        return getDataTable(list);
    }

    /**
     * 导出响应内容列表
     */
    @PreAuthorize("@ss.hasPermi('thought:content:export')")
    @Log(title = "响应内容", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ThoughtResponseContent thoughtResponseContent)
    {
        List<ThoughtResponseContent> list = thoughtResponseContentService.selectThoughtResponseContentList(thoughtResponseContent);
        ExcelUtil<ThoughtResponseContent> util = new ExcelUtil<ThoughtResponseContent>(ThoughtResponseContent.class);
        return util.exportExcel(list, "响应内容数据");
    }

    /**
     * 获取响应内容详细信息
     */
    @PreAuthorize("@ss.hasPermi('thought:content:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(thoughtResponseContentService.selectThoughtResponseContentById(id));
    }

    /**
     * 新增响应内容
     */
    @PreAuthorize("@ss.hasPermi('thought:content:add')")
    @Log(title = "响应内容", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ThoughtResponseContent thoughtResponseContent)
    {
        return toAjax(thoughtResponseContentService.insertThoughtResponseContent(thoughtResponseContent));
    }

    /**
     * 修改响应内容
     */
    @PreAuthorize("@ss.hasPermi('thought:content:edit')")
    @Log(title = "响应内容", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ThoughtResponseContent thoughtResponseContent)
    {
        return toAjax(thoughtResponseContentService.updateThoughtResponseContent(thoughtResponseContent));
    }

    /**
     * 删除响应内容
     */
    @PreAuthorize("@ss.hasPermi('thought:content:remove')")
    @Log(title = "响应内容", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(thoughtResponseContentService.deleteThoughtResponseContentByIds(ids));
    }
}
