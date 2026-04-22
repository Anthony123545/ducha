package com.supervision.web.controller.grassrootsregistration;

import java.util.List;

import cn.hutool.core.util.ObjectUtil;
import com.supervision.common.core.domain.entity.SysRole;
import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.common.utils.StringUtils;
import com.supervision.grassrootsregistration.resultVo.SysDeptDto;
import com.supervision.system.mapper.SysUserMapper;
import com.supervision.system.service.ISysDeptService;
import io.swagger.models.auth.In;
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
import com.supervision.grassrootsregistration .domain.BdglMeeting;
import com.supervision.grassrootsregistration .service.IBdglMeetingService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 团支部会议登记本Controller
 *
 * @author supervision
 * @date 2022-02-26
 */
@RestController
@RequestMapping("/grassrootsregistration/bdglmeeting")
public class BdglMeetingController extends BaseController
{
    @Autowired
    private IBdglMeetingService bdglMeetingService;
    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private ISysDeptService deptService;

    /**
     * 查询团支部会议登记本列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglmeeting:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglMeeting bdglMeeting)
    {
        LoginUser loginUser = getLoginUser();
        List<SysRole> sysRoles = userMapper.selectRole(loginUser.getUserId());
        String role = CommonsController.getRole(sysRoles);
        //判断当前角色是否是最高权限   不是返回当前部门数据
        if(!role.equals("admin")){
            bdglMeeting.setUnitId(Integer.parseInt(loginUser.getDeptId().toString()));
        }
        startPage();
        List<BdglMeeting> list = bdglMeetingService.selectBdglMeetingList(bdglMeeting);
        return getDataTable(list);
    }

    /**
     * 领导查询团支部会议登记本
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglmeetings:list')")
    @GetMapping("/getMeetingList")
    public TableDataInfo getMeetingList(String year)
    {
        if (StringUtils.isNotEmpty(year)){
            startPage();
            List<SysDeptDto> list = bdglMeetingService.getMeetingList(year);
            return getDataTable(list);
        }
        List<List<SysDeptDto>> list = deptService.getMeetRecordsList();
        return getDataTable(list);
    }

    /**
     * 领导查询团支部会议登记本列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglmeetings:list')")
    @GetMapping("/getMeetingOnUnitList")
    public TableDataInfo getMeetingOnUnitList(Integer unitId,String year)
    {
        startPage();
        List<BdglMeeting> list = bdglMeetingService.getMeetingOnUnitList(unitId,year);
        return getDataTable(list);
    }

    /**
     * 导出团支部会议登记本列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglmeeting:export')")
    @Log(title = "团支部会议登记本", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglMeeting bdglMeeting)
    {
        List<BdglMeeting> list = bdglMeetingService.selectBdglMeetingList(bdglMeeting);
        ExcelUtil<BdglMeeting> util = new ExcelUtil<BdglMeeting>(BdglMeeting.class);
        return util.exportExcel(list, "团支部会议登记本数据");
    }

    /**
     * 获取团支部会议登记本详细信息
     */
    @PreAuthorize("@ss.hasAnyPermi('grassrootsregistration:bdglmeeting:query,grassrootsregistration:bdglmeetings:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bdglMeetingService.selectBdglMeetingById(id));
    }

    /**
     * 新增团支部会议登记本
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglmeeting:add')")
    @Log(title = "团支部会议登记本", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglMeeting bdglMeeting)
    {
        return toAjax(bdglMeetingService.insertBdglMeeting(bdglMeeting));
    }

    /**
     * 修改团支部会议登记本
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglmeeting:edit')")
    @Log(title = "团支部会议登记本", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglMeeting bdglMeeting)
    {
        return toAjax(bdglMeetingService.updateBdglMeeting(bdglMeeting));
    }

    /**
     * 删除团支部会议登记本
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglmeeting:remove')")
    @Log(title = "团支部会议登记本", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglMeetingService.deleteBdglMeetingByIds(ids));
    }
}
