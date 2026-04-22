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
import com.supervision.thought.domain.ThoughtPlanFu;
import com.supervision.thought.service.IThoughtPlanFuService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 计划内容Controller
 * 
 * @author supervision
 * @date 2022-04-09
 */
@RestController
@RequestMapping("/thought/thoughtPlaFfu")
public class ThoughtPlanFuController extends BaseController
{
    @Autowired
    private IThoughtPlanFuService thoughtPlanFuService;

    /**
     * 查询计划内容列表
     */
    @PreAuthorize("@ss.hasPermi('thought:thoughtPlaFfu:list')")
    @GetMapping("/list")
    public TableDataInfo list(ThoughtPlanFu thoughtPlanFu)
    {
        startPage();
        List<ThoughtPlanFu> list = thoughtPlanFuService.selectThoughtPlanFuList(thoughtPlanFu);
        return getDataTable(list);
    }

    /**
     * 导出计划内容列表
     */
    @PreAuthorize("@ss.hasPermi('thought:thoughtPlaFfu:export')")
    @Log(title = "计划内容", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ThoughtPlanFu thoughtPlanFu)
    {
        List<ThoughtPlanFu> list = thoughtPlanFuService.selectThoughtPlanFuList(thoughtPlanFu);
        ExcelUtil<ThoughtPlanFu> util = new ExcelUtil<ThoughtPlanFu>(ThoughtPlanFu.class);
        return util.exportExcel(list, "计划内容数据");
    }

    /**
     * 获取计划内容详细信息
     */
    @PreAuthorize("@ss.hasPermi('thought:thoughtPlaFfu:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(thoughtPlanFuService.selectThoughtPlanFuById(id));
    }

    /**
     * 新增计划内容
     */
    @PreAuthorize("@ss.hasPermi('thought:thoughtPlaFfu:add')")
    @Log(title = "计划内容", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ThoughtPlanFu thoughtPlanFu)
    {
        return toAjax(thoughtPlanFuService.insertThoughtPlanFu(thoughtPlanFu));
    }

    /**
     * 修改计划内容
     */
    @PreAuthorize("@ss.hasPermi('thought:thoughtPlaFfu:edit')")
    @Log(title = "计划内容", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ThoughtPlanFu thoughtPlanFu)
    {
        return toAjax(thoughtPlanFuService.updateThoughtPlanFu(thoughtPlanFu));
    }

    /**
     * 删除计划内容
     */
    @PreAuthorize("@ss.hasPermi('thought:thoughtPlaFfu:remove')")
    @Log(title = "计划内容", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(thoughtPlanFuService.deleteThoughtPlanFuByIds(ids));
    }
}
