package com.supervision.web.controller.grassrootsregistration;

import java.util.List;

import com.supervision.common.core.domain.entity.SysRole;
import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.common.utils.StringUtils;

import com.supervision.grassrootsregistration.resultVo.SysDeptDto;
import com.supervision.system.mapper.SysUserMapper;
import com.supervision.system.service.ISysDeptService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.supervision.common.annotation.Log;
import com.supervision.common.core.controller.BaseController;
import com.supervision.common.core.domain.AjaxResult;
import com.supervision.common.enums.BusinessType;
import com.supervision.grassrootsregistration.domain.BdglArmament;
import com.supervision.grassrootsregistration.service.IBdglArmamentService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 军械装备登记簿Controller
 *
 * @author supervision
 * @date 2022-02-26
 */
@RestController
@RequestMapping("/grassrootsregistration/bdglarmament")
public class BdglArmamentController extends BaseController
{
    @Autowired
    private IBdglArmamentService bdglArmamentService;
    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private ISysDeptService deptService;

    /**
     * 查询军械装备登记簿列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglarmament:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglArmament bdglArmament)
    {
        LoginUser loginUser = getLoginUser();
        List<SysRole> sysRoles = userMapper.selectRole(loginUser.getUserId());
        String role = CommonsController.getRole(sysRoles);
        //判断当前角色是否是最高权限   不是返回当前部门数据
        if(!role.equals("admin")){
            bdglArmament.setUnitId(Integer.parseInt(loginUser.getDeptId().toString()));
        }
        startPage();
        List<BdglArmament> list = bdglArmamentService.selectBdglArmamentList(bdglArmament);
        return getDataTable(list);
    }

    /**
     * 领导查询军械装备登记簿
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglarmaments:list')")
    @GetMapping("/armamentList")
    public TableDataInfo armamentList(String year)
    {
        if (StringUtils.isNotEmpty(year)){
            startPage();
            List<SysDeptDto> list = bdglArmamentService.selectArmamentList(year);
            return getDataTable(list);
        }
        List<List<SysDeptDto>> list = deptService.getMeetRecordsList();
        return getDataTable(list);
    }

    /**
     * 导出军械装备登记簿列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglarmament:export')")
    @Log(title = "军械装备登记簿", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglArmament bdglArmament)
    {
        List<BdglArmament> list = bdglArmamentService.selectBdglArmamentList(bdglArmament);
        ExcelUtil<BdglArmament> util = new ExcelUtil<BdglArmament>(BdglArmament.class);
        return util.exportExcel(list, "军械装备登记簿数据");
    }

    /**
     * 获取军械装备登记簿详细信息
     */
    @PreAuthorize("@ss.hasAnyPermi('grassrootsregistration:bdglarmament:query,grassrootsregistration:bdglarmaments:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bdglArmamentService.selectBdglArmamentById(id));
    }

    @GetMapping("/armamentunits")
    public AjaxResult getInfoUnit(Integer unitid,String year)
    {

        List<BdglArmament> bdglEquip = bdglArmamentService.selectArmamentunitid(unitid,year);
        return AjaxResult.success(bdglEquip);
    }
    /**
     * 新增军械装备登记簿
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglarmament:add')")
    @Log(title = "军械装备登记簿", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglArmament bdglArmament)
    {
        return toAjax(bdglArmamentService.insertBdglArmament(bdglArmament));
    }

    /**
     * 修改军械装备登记簿
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglarmament:edit')")
    @Log(title = "军械装备登记簿", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglArmament bdglArmament)
    {
        return toAjax(bdglArmamentService.updateBdglArmament(bdglArmament));
    }

    /**
     * 删除军械装备登记簿
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglarmament:remove')")
    @Log(title = "军械装备登记簿", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglArmamentService.deleteBdglArmamentByIds(ids));
    }
}
