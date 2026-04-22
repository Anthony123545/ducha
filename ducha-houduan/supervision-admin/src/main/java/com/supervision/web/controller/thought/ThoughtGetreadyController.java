package com.supervision.web.controller.thought;

import java.util.List;

import com.supervision.common.core.domain.entity.SysUser;
import com.supervision.common.core.domain.model.LoginUser;
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
import com.supervision.thought.domain.ThoughtGetready;
import com.supervision.thought.service.IThoughtGetreadyService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 教育准备会Controller
 * 
 * @author supervision
 * @date 2022-04-06
 */
@RestController
@RequestMapping("/thought/getready")
public class ThoughtGetreadyController extends BaseController
{
    @Autowired
    private IThoughtGetreadyService thoughtGetreadyService;
    @Autowired
    private ISysUserService userService;


    /**
     * 查询教育准备会列表
     */
    @PreAuthorize("@ss.hasPermi('thought:getready:list')")
    @GetMapping("/list")
    public TableDataInfo list(ThoughtGetready thoughtGetready)
    {
        startPage();
        List<ThoughtGetready> list = thoughtGetreadyService.selectThoughtGetreadyList(thoughtGetready);
        return getDataTable(list);
    }

    /**
     * 导出教育准备会列表
     */
    @PreAuthorize("@ss.hasPermi('thought:getready:export')")
    @Log(title = "教育准备会", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ThoughtGetready thoughtGetready)
    {
        List<ThoughtGetready> list = thoughtGetreadyService.selectThoughtGetreadyList(thoughtGetready);
        ExcelUtil<ThoughtGetready> util = new ExcelUtil<ThoughtGetready>(ThoughtGetready.class);
        return util.exportExcel(list, "教育准备会数据");
    }

    /**
     * 获取教育准备会详细信息
     */
    @PreAuthorize("@ss.hasPermi('thought:getready:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(thoughtGetreadyService.selectThoughtGetreadyById(id));
    }

    /**
     * 新增教育准备会
     */
    @PreAuthorize("@ss.hasPermi('thought:getready:add')")
    @Log(title = "教育准备会", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ThoughtGetready thoughtGetready)
    {
        LoginUser loginUser = getLoginUser();
        SysUser sysUser = userService.selectUserById(loginUser.getUserId());
        thoughtGetready.setCreateId(sysUser.getUserId());
        thoughtGetready.setCreateName(sysUser.getNickName());
        return toAjax(thoughtGetreadyService.insertThoughtGetready(thoughtGetready));
    }

    /**
     * 修改教育准备会
     */
    @PreAuthorize("@ss.hasPermi('thought:getready:edit')")
    @Log(title = "教育准备会", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ThoughtGetready thoughtGetready)
    {
        return toAjax(thoughtGetreadyService.updateThoughtGetready(thoughtGetready));
    }

    /**
     * 删除教育准备会
     */
    @PreAuthorize("@ss.hasPermi('thought:getready:remove')")
    @Log(title = "教育准备会", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(thoughtGetreadyService.deleteThoughtGetreadyByIds(ids));
    }
}
