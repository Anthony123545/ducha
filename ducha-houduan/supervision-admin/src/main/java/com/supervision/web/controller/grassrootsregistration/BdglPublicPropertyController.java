package com.supervision.web.controller.grassrootsregistration;

import java.util.List;

import com.supervision.common.core.domain.entity.SysRole;
import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.common.utils.StringUtils;
import com.supervision.grassrootsregistration.domain.BdglEquip;
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
import com.supervision.grassrootsregistration.domain.BdglPublicProperty;
import com.supervision.grassrootsregistration.service.IBdglPublicPropertyService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 营产，公物管理登记簿Controller
 *
 * @author supervision
 * @date 2022-02-26
 */
@RestController
@RequestMapping("/grassrootsregistration/bdglpublicproperty")
public class BdglPublicPropertyController extends BaseController
{
    @Autowired
    private IBdglPublicPropertyService bdglPublicPropertyService;
    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private ISysDeptService deptService;


    /**
     * 查询营产，公物管理登记簿列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglpublicproperty:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglPublicProperty bdglPublicProperty)
    {
        LoginUser loginUser = getLoginUser();
        List<SysRole> sysRoles = userMapper.selectRole(loginUser.getUserId());
        String role = CommonsController.getRole(sysRoles);
        //判断当前角色是否是最高权限   不是返回当前部门数据
        if(!role.equals("admin")){
            bdglPublicProperty.setUnitId(Integer.parseInt(loginUser.getDeptId().toString()));
        }
        startPage();
        List<BdglPublicProperty> list = bdglPublicPropertyService.selectBdglPublicPropertyList(bdglPublicProperty);
        return getDataTable(list);
    }

    /**
     * 领导查看删除营产，公物管理登记簿信息
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglpublicpropertys:list')")
    @GetMapping("/propertyList")
    public TableDataInfo propertyList(String year)
    {
        if (StringUtils.isNotEmpty(year)){
            startPage();
            List<SysDeptDto> list = bdglPublicPropertyService.selectPropertyList(year);
            return getDataTable(list);
        }
        List<List<SysDeptDto>> list = deptService.getMeetRecordsList();
        return getDataTable(list);
    }

    /**
     * 导出营产，公物管理登记簿列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglpublicproperty:export')")
    @Log(title = "营产，公物管理登记簿", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglPublicProperty bdglPublicProperty)
    {
        List<BdglPublicProperty> list = bdglPublicPropertyService.selectBdglPublicPropertyList(bdglPublicProperty);
        ExcelUtil<BdglPublicProperty> util = new ExcelUtil<BdglPublicProperty>(BdglPublicProperty.class);
        return util.exportExcel(list, "营产，公物管理登记簿数据");
    }

    /**
     * 获取营产，公物管理登记簿详细信息
     */
    @PreAuthorize("@ss.hasAnyPermi('grassrootsregistration:bdglpublicproperty:query,grassrootsregistration:bdglpublicpropertys:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bdglPublicPropertyService.selectBdglPublicPropertyById(id));
    }
    @GetMapping("/publiccpropertyunits")
    public AjaxResult getInfoUnit(Integer unitid,String year)
    {

        List<BdglPublicProperty> bdglEquip = bdglPublicPropertyService.selectPublicPropertyunitid(unitid,year);
        return AjaxResult.success(bdglEquip);
    }
    /**
     * 新增营产，公物管理登记簿
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglpublicproperty:add')")
    @Log(title = "营产，公物管理登记簿", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglPublicProperty bdglPublicProperty)
    {
        return toAjax(bdglPublicPropertyService.insertBdglPublicProperty(bdglPublicProperty));
    }

    /**
     * 修改营产，公物管理登记簿
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglpublicproperty:edit')")
    @Log(title = "营产，公物管理登记簿", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglPublicProperty bdglPublicProperty)
    {
        return toAjax(bdglPublicPropertyService.updateBdglPublicProperty(bdglPublicProperty));
    }

    /**
     * 删除营产，公物管理登记簿
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglpublicproperty:remove')")
    @Log(title = "营产，公物管理登记簿", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglPublicPropertyService.deleteBdglPublicPropertyByIds(ids));
    }
}
