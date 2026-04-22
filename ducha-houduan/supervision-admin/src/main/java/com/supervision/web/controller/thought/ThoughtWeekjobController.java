package com.supervision.web.controller.thought;

import java.math.BigDecimal;
import java.util.List;

import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.common.core.domain.entity.SysUser;
import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.system.service.ISysDeptService;
import com.supervision.system.service.ISysUserService;
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
import com.supervision.thought.domain.ThoughtWeekjob;
import com.supervision.thought.service.IThoughtWeekjobService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 周工作Controller
 * 
 * @author supervision
 * @date 2022-04-06
 */
@RestController
@RequestMapping("/thought/weekjob")
public class ThoughtWeekjobController extends BaseController
{
    @Autowired
    private IThoughtWeekjobService thoughtWeekjobService;
    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysDeptService deptServicel;

    /**
     * 查询周工作列表
     */
    @PreAuthorize("@ss.hasPermi('thought:weekjob:list')")
    @GetMapping("/list")
    public TableDataInfo list(ThoughtWeekjob thoughtWeekjob)
    {
        startPage();
        List<ThoughtWeekjob> list = thoughtWeekjobService.selectThoughtWeekjobList(thoughtWeekjob);
        return getDataTable(list);
    }

    /**
     * 导出周工作列表
     */
    @PreAuthorize("@ss.hasPermi('thought:weekjob:export')")
    @Log(title = "周工作", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ThoughtWeekjob thoughtWeekjob)
    {
        List<ThoughtWeekjob> list = thoughtWeekjobService.selectThoughtWeekjobList(thoughtWeekjob);
        ExcelUtil<ThoughtWeekjob> util = new ExcelUtil<ThoughtWeekjob>(ThoughtWeekjob.class);
        return util.exportExcel(list, "周工作数据");
    }

    /**
     * 获取周工作详细信息
     */
    @PreAuthorize("@ss.hasPermi('thought:weekjob:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(thoughtWeekjobService.selectThoughtWeekjobById(id));
    }

    /**
     * 新增周工作
     */
    @PreAuthorize("@ss.hasPermi('thought:weekjob:add')")
    @Log(title = "周工作", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ThoughtWeekjob thoughtWeekjob)
    {
        LoginUser loginUser = getLoginUser();
        SysUser sysUser = userService.selectUserById(loginUser.getUserId());
        thoughtWeekjob.setCreatId(sysUser.getUserId());
        thoughtWeekjob.setCreatName(sysUser.getNickName());
        return toAjax(thoughtWeekjobService.insertThoughtWeekjob(thoughtWeekjob));
    }

    /**
     * 修改周工作
     */
    @PreAuthorize("@ss.hasPermi('thought:weekjob:edit')")
    @Log(title = "周工作", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ThoughtWeekjob thoughtWeekjob)
    {
        return toAjax(thoughtWeekjobService.updateThoughtWeekjob(thoughtWeekjob));
    }

    /**
     * 删除周工作
     */
    @PreAuthorize("@ss.hasPermi('thought:weekjob:remove')")
    @Log(title = "周工作", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {

        return toAjax(thoughtWeekjobService.deleteThoughtWeekjobByIds(ids));
    }
}
