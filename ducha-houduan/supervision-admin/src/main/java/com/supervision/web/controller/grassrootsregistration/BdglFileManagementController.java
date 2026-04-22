package com.supervision.web.controller.grassrootsregistration;

import java.util.List;

import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.common.core.domain.entity.SysRole;
import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.common.utils.StringUtils;
import com.supervision.grassrootsregistration.domain.BdglSecrecy;
import com.supervision.grassrootsregistration.resultVo.SysDeptDto;
import com.supervision.peopleManage.domain.BdglPeople;
import com.supervision.peopleManage.service.IBdglPeopleService;
import com.supervision.system.mapper.SysUserMapper;
import com.supervision.system.service.ISysDeptService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.supervision.common.annotation.Log;
import com.supervision.common.core.controller.BaseController;
import com.supervision.common.core.domain.AjaxResult;
import com.supervision.common.enums.BusinessType;
import com.supervision.grassrootsregistration.domain.BdglFileManagement;
import com.supervision.grassrootsregistration.service.IBdglFileManagementService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 文件管理登计本Controller
 *
 * @author supervision
 * @date 2022-02-26
 */
@RestController
@RequestMapping("/grassrootsregistration/bdglfilemanagement")
public class BdglFileManagementController extends BaseController
{
    @Autowired
    private IBdglFileManagementService bdglFileManagementService;
    @Autowired
    private IBdglPeopleService bdglPeopleService;
    @Autowired
    private ISysDeptService sysDeptService;
    @Autowired
    private SysUserMapper userMapper;
    /**
     * 查询文件管理登计本列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglfilemanagement:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglFileManagement bdglFileManagement)
    {
        LoginUser loginUser = getLoginUser();
        List<SysRole> sysRoles = userMapper.selectRole(loginUser.getUserId());
        String role = CommonsController.getRole(sysRoles);
        //判断当前角色是否是最高权限   不是返回当前部门数据
        if(!role.equals("admin")){
            bdglFileManagement.setUnitId(Integer.parseInt(loginUser.getDeptId().toString()));
        }
        startPage();
        List<BdglFileManagement> list = bdglFileManagementService.selectBdglFileManagementList(bdglFileManagement);
        return getDataTable(list);
    }

    /**
     * 查询文件管理登计本列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglfilemanagements:list')")
    @GetMapping("/docList")
    public TableDataInfo docList(String year)
    {
        if (StringUtils.isNotEmpty(year)){
            startPage();
            List<SysDeptDto> list = bdglFileManagementService.selectFileDocList(year);
            return getDataTable(list);
        }
        List<List<SysDeptDto>> list = sysDeptService.getMeetRecordsList();
        return getDataTable(list);
    }

    /**
     * 导出文件管理登计本列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglfilemanagement:export')")
    @Log(title = "文件管理登计本", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglFileManagement bdglFileManagement)
    {
        List<BdglFileManagement> list = bdglFileManagementService.selectBdglFileManagementList(bdglFileManagement);
        ExcelUtil<BdglFileManagement> util = new ExcelUtil<BdglFileManagement>(BdglFileManagement.class);
        return util.exportExcel(list, "文件管理登计本数据");
    }

    /**
     * 获取文件管理登计本详细信息
     */
    @PreAuthorize("@ss.hasAnyPermi('grassrootsregistration:bdglfilemanagement:query,grassrootsregistration:bdglfilemanagements:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bdglFileManagementService.selectBdglFileManagementById(id));
    }
    @GetMapping("/fileunits")
    public AjaxResult getInfoUnit(Integer unitid,String year)
    {

        List<BdglFileManagement> dglFileManagement = bdglFileManagementService.selectBdglSecrecyBunitunitid(unitid,year);
        return AjaxResult.success(dglFileManagement);
    }

    /**
     * 新增文件管理登计本
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglfilemanagement:add')")
    @Log(title = "文件管理登计本", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglFileManagement bdglFileManagement)
    {

        return toAjax(bdglFileManagementService.insertBdglFileManagement(bdglFileManagement));
    }

    /**
     * 修改文件管理登计本
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglfilemanagement:edit')")
    @Log(title = "文件管理登计本", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglFileManagement bdglFileManagement)
    {
        return toAjax(bdglFileManagementService.updateBdglFileManagement(bdglFileManagement));
    }

    /**
     * 删除文件管理登计本
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglfilemanagement:remove')")
    @Log(title = "文件管理登计本", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglFileManagementService.deleteBdglFileManagementByIds(ids));
    }

    /**
     * 文件管理登记本详情（包含借阅记录）
     */
    @GetMapping("/detail/{id}")
    public AjaxResult detail(@PathVariable("id") Integer id) {
        BdglFileManagement file =
                bdglFileManagementService.selectBdglFileManagementDetailById(id);
        return AjaxResult.success(file);
    }
}
