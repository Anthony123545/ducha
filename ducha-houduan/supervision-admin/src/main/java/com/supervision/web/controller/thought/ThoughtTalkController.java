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
import com.supervision.thought.domain.ThoughtTalk;
import com.supervision.thought.service.IThoughtTalkService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 谈心记录卡Controller
 * 
 * @author supervision
 * @date 2022-04-06
 */
@RestController
@RequestMapping("/thought/talk")
public class ThoughtTalkController extends BaseController
{
    @Autowired
    private IThoughtTalkService thoughtTalkService;
    @Autowired
    private ISysUserService userService;
    /**
     * 查询谈心记录卡列表
     */
    @PreAuthorize("@ss.hasPermi('thought:talk:list')")
    @GetMapping("/list")
    public TableDataInfo list(ThoughtTalk thoughtTalk)
    {
        startPage();
        List<ThoughtTalk> list = thoughtTalkService.selectThoughtTalkList(thoughtTalk);
        return getDataTable(list);
    }

    /**
     * 导出谈心记录卡列表
     */
    @PreAuthorize("@ss.hasPermi('thought:talk:export')")
    @Log(title = "谈心记录卡", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ThoughtTalk thoughtTalk)
    {
        List<ThoughtTalk> list = thoughtTalkService.selectThoughtTalkList(thoughtTalk);
        ExcelUtil<ThoughtTalk> util = new ExcelUtil<ThoughtTalk>(ThoughtTalk.class);
        return util.exportExcel(list, "谈心记录卡数据");
    }

    /**
     * 获取谈心记录卡详细信息
     */
    @PreAuthorize("@ss.hasPermi('thought:talk:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(thoughtTalkService.selectThoughtTalkById(id));
    }

    /**
     * 新增谈心记录卡
     */
    @PreAuthorize("@ss.hasPermi('thought:talk:add')")
    @Log(title = "谈心记录卡", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ThoughtTalk thoughtTalk)
    {

        LoginUser loginUser = getLoginUser();
        SysUser sysUser = userService.selectUserById(loginUser.getUserId());
        thoughtTalk.setCreateId(sysUser.getUserId());
        thoughtTalk.setCreateName(sysUser.getNickName());
        return toAjax(thoughtTalkService.insertThoughtTalk(thoughtTalk));
    }

    /**
     * 修改谈心记录卡
     */
    @PreAuthorize("@ss.hasPermi('thought:talk:edit')")
    @Log(title = "谈心记录卡", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ThoughtTalk thoughtTalk)
    {
        return toAjax(thoughtTalkService.updateThoughtTalk(thoughtTalk));
    }

    /**
     * 删除谈心记录卡
     */
    @PreAuthorize("@ss.hasPermi('thought:talk:remove')")
    @Log(title = "谈心记录卡", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(thoughtTalkService.deleteThoughtTalkByIds(ids));
    }
}
