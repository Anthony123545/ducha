package com.supervision.system.controller;

import java.util.Date;
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
import com.supervision.system.domain.SetTime;
import com.supervision.system.service.ISetTimeService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 截止日期设置
Controller
 * 
 * @author ruoyi
 * @date 2023-01-10
 */
@RestController
@RequestMapping("/system/time")
public class SetTimeController extends BaseController
{
    @Autowired
    private ISetTimeService setTimeService;

    /**
     * 查询截止日期设置
列表
     */
    //@PreAuthorize("@ss.hasPermi('system:time:list')")
    @GetMapping("/list")
    public TableDataInfo list(SetTime setTime)
    {
        startPage();
        List<SetTime> list = setTimeService.selectSetTimeList(setTime);
        return getDataTable(list);
    }

    /**
     * 导出截止日期设置
列表
     */
    @PreAuthorize("@ss.hasPermi('system:time:export')")
    @Log(title = "截止日期设置 ", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SetTime setTime)
    {
        List<SetTime> list = setTimeService.selectSetTimeList(setTime);
        ExcelUtil<SetTime> util = new ExcelUtil<SetTime>(SetTime.class);
        util.exportExcel(response, list, "截止日期设置数据");
    }

    /**
     * 获取截止日期设置
详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:time:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(setTimeService.selectSetTimeById(id));
    }

    /**
     * 新增截止日期设置

     */
    @PreAuthorize("@ss.hasPermi('system:time:add')")
    @Log(title = "截止日期设置 ", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SetTime setTime)
    {
        return toAjax(setTimeService.insertSetTime(setTime));
    }

    /**
     * 修改截止日期设置

     */
    //@PreAuthorize("@ss.hasPermi('system:time:edit')")
    @Log(title = "截止日期设置 ", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SetTime setTime)
    {
        setTime.setUpdateId(getLoginUser().getUserId());
        setTime.setUpdateName(getLoginUser().getUsername());
        setTime.setUpdateTime(new Date());
        return toAjax(setTimeService.updateSetTime(setTime));
    }

    /**
     * 删除截止日期设置

     */
    @PreAuthorize("@ss.hasPermi('system:time:remove')")
    @Log(title = "截止日期设置 ", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(setTimeService.deleteSetTimeByIds(ids));
    }
}
