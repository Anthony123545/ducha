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
import com.supervision.thought.domain.ThoughtParliament;
import com.supervision.thought.service.IThoughtParliamentService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 党委议教会Controller
 * 
 * @author supervision
 * @date 2022-04-06
 */
@RestController
@RequestMapping("/thought/parliament")
public class ThoughtParliamentController extends BaseController
{
    @Autowired
    private IThoughtParliamentService thoughtParliamentService;
    @Autowired
    private ISysUserService userService;
    /**
     * 查询党委议教会列表
     */
    @PreAuthorize("@ss.hasPermi('thought:parliament:list')")
    @GetMapping("/list")
    public TableDataInfo list(ThoughtParliament thoughtParliament)
    {
        startPage();
        List<ThoughtParliament> list = thoughtParliamentService.selectThoughtParliamentList(thoughtParliament);
        return getDataTable(list);
    }

    /**
     * 导出党委议教会列表
     */
    @PreAuthorize("@ss.hasPermi('thought:parliament:export')")
    @Log(title = "党委议教会", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ThoughtParliament thoughtParliament)
    {
        List<ThoughtParliament> list = thoughtParliamentService.selectThoughtParliamentList(thoughtParliament);
        ExcelUtil<ThoughtParliament> util = new ExcelUtil<ThoughtParliament>(ThoughtParliament.class);
        return util.exportExcel(list, "党委议教会数据");
    }

    /**
     * 获取党委议教会详细信息
     */
    @PreAuthorize("@ss.hasPermi('thought:parliament:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(thoughtParliamentService.selectThoughtParliamentById(id));
    }

    /**
     * 新增党委议教会
     */
    @PreAuthorize("@ss.hasPermi('thought:parliament:add')")
    @Log(title = "党委议教会", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ThoughtParliament thoughtParliament)
    {

        LoginUser loginUser = getLoginUser();
        SysUser sysUser = userService.selectUserById(loginUser.getUserId());
        thoughtParliament.setCreateId(sysUser.getUserId());
        thoughtParliament.setCreateName(sysUser.getNickName());
        return toAjax(thoughtParliamentService.insertThoughtParliament(thoughtParliament));
    }

    /**
     * 修改党委议教会
     */
    @PreAuthorize("@ss.hasPermi('thought:parliament:edit')")
    @Log(title = "党委议教会", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ThoughtParliament thoughtParliament)
    {
        return toAjax(thoughtParliamentService.updateThoughtParliament(thoughtParliament));
    }

    /**
     * 删除党委议教会
     */
    @PreAuthorize("@ss.hasPermi('thought:parliament:remove')")
    @Log(title = "党委议教会", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(thoughtParliamentService.deleteThoughtParliamentByIds(ids));
    }
}
