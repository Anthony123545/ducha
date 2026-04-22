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
import com.supervision.thought.domain.ThoughtWeekjobFu;
import com.supervision.thought.service.IThoughtWeekjobFuService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 周工作附Controller
 * 
 * @author supervision
 * @date 2022-04-06
 */
@RestController
@RequestMapping("/thought/weekjobFu")
public class ThoughtWeekjobFuController extends BaseController
{
    @Autowired
    private IThoughtWeekjobFuService thoughtWeekjobFuService;

    /**
     * 查询周工作附列表
     */
    @PreAuthorize("@ss.hasPermi('thought:weekjobFu:list')")
    @GetMapping("/list")
    public TableDataInfo list(ThoughtWeekjobFu thoughtWeekjobFu)
    {
        startPage();
        List<ThoughtWeekjobFu> list = thoughtWeekjobFuService.selectThoughtWeekjobFuList(thoughtWeekjobFu);
        return getDataTable(list);
    }

    /**
     * 导出周工作附列表
     */
    @PreAuthorize("@ss.hasPermi('thought:weekjobFu:export')")
    @Log(title = "周工作附", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ThoughtWeekjobFu thoughtWeekjobFu)
    {
        List<ThoughtWeekjobFu> list = thoughtWeekjobFuService.selectThoughtWeekjobFuList(thoughtWeekjobFu);
        ExcelUtil<ThoughtWeekjobFu> util = new ExcelUtil<ThoughtWeekjobFu>(ThoughtWeekjobFu.class);
        return util.exportExcel(list, "周工作附数据");
    }

    /**
     * 获取周工作附详细信息
     */
    @PreAuthorize("@ss.hasPermi('thought:weekjobFu:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(thoughtWeekjobFuService.selectThoughtWeekjobFuById(id));
    }

    /**
     * 新增周工作附
     */
    @PreAuthorize("@ss.hasPermi('thought:weekjobFu:add')")
    @Log(title = "周工作附", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ThoughtWeekjobFu thoughtWeekjobFu)
    {
        return toAjax(thoughtWeekjobFuService.insertThoughtWeekjobFu(thoughtWeekjobFu));
    }

    /**
     * 修改周工作附
     */
    @PreAuthorize("@ss.hasPermi('thought:weekjobFu:edit')")
    @Log(title = "周工作附", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ThoughtWeekjobFu thoughtWeekjobFu)
    {
        return toAjax(thoughtWeekjobFuService.updateThoughtWeekjobFu(thoughtWeekjobFu));
    }

    /**
     * 删除周工作附
     */
    @PreAuthorize("@ss.hasPermi('thought:weekjobFu:remove')")
    @Log(title = "周工作附", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(thoughtWeekjobFuService.deleteThoughtWeekjobFuByIds(ids));
    }
}
