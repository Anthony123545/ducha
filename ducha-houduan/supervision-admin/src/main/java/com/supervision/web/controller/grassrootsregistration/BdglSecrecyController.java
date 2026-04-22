package com.supervision.web.controller.grassrootsregistration;

import java.util.HashMap;
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
import com.supervision.grassrootsregistration .domain.BdglSecrecy;
import com.supervision.grassrootsregistration .service.IBdglSecrecyService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 网络/手机/涉密载体登记本Controller
 *
 * @author supervision
 * @date 2022-02-26
 */
@RestController
@RequestMapping("/grassrootsregistration/bdglsecrecy")
public class BdglSecrecyController extends BaseController
{
    @Autowired
    private IBdglSecrecyService bdglSecrecyService;
    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private ISysDeptService deptService;

    /**
     * 查询网络/手机/涉密载体登记本列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglsecrecy:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglSecrecy bdglSecrecy)
    {
        LoginUser loginUser = getLoginUser();
        List<SysRole> sysRoles = userMapper.selectRole(loginUser.getUserId());
        String role = CommonsController.getRole(sysRoles);
        //判断当前角色是否是最高权限   不是返回当前部门数据
        if(!role.equals("admin")){
            bdglSecrecy.setUnitId(Integer.parseInt(loginUser.getDeptId().toString()));
        }
        startPage();
        List<BdglSecrecy> list = bdglSecrecyService.selectBdglSecrecyList(bdglSecrecy);
        return getDataTable(list);
    }

    /**
     * 导出网络/手机/涉密载体登记本列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglsecrecy:export')")
    @Log(title = "网络/手机/涉密载体登记本", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglSecrecy bdglSecrecy)
    {
        List<BdglSecrecy> list = bdglSecrecyService.selectBdglSecrecyList(bdglSecrecy);
        ExcelUtil<BdglSecrecy> util = new ExcelUtil<BdglSecrecy>(BdglSecrecy.class);
        return util.exportExcel(list, "网络/手机/涉密载体登记本数据");
    }

    /**
     * 获取网络/手机/涉密载体登记本详细信息
     */
    @PreAuthorize("@ss.hasAnyPermi('grassrootsregistration:bdglsecrecy:query,grassrootsregistration:bdglsecrecys:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        BdglSecrecy bdglSecrecies = bdglSecrecyService.selectBdglSecrecyById(id);
        return AjaxResult.success(bdglSecrecies);
    }

    //@PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglsecrecy:query')")
    @GetMapping("/units")
    public AjaxResult getInfoUnit(Integer unitid,String year)
    {

        List<BdglSecrecy> bdglSecrecies = bdglSecrecyService.selectBdglSecrecyBunitId(unitid,year);
        return AjaxResult.success(bdglSecrecies);
    }
    /**
     * 新增网络/手机/涉密载体登记本
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglsecrecy:add')")
    @Log(title = "网络/手机/涉密载体登记本", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglSecrecy bdglSecrecy)
    {
        LoginUser loginUser = getLoginUser();
        return toAjax(bdglSecrecyService.insertBdglSecrecy(bdglSecrecy));
    }

    /**
     * 修改网络/手机/涉密载体登记本
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglsecrecy:edit')")
    @Log(title = "网络/手机/涉密载体登记本", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglSecrecy bdglSecrecy)
    {
        return toAjax(bdglSecrecyService.updateBdglSecrecy(bdglSecrecy));
    }

    /**
     * 删除网络/手机/涉密载体登记本
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglsecrecy:remove')")
    @Log(title = "网络/手机/涉密载体登记本", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglSecrecyService.deleteBdglSecrecyByIds(ids));
    }


    /**
     * 网络/手机/涉密载体登记本数据统计
     * 获取当前部门下有数据的部门
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglsecrecys:list')")
    @GetMapping("/getSecrecyStats")
    public TableDataInfo secrecyStats(String year) {
        if (StringUtils.isNotEmpty(year)){
            startPage();
            List<SysDeptDto> list = bdglSecrecyService.selectSecrecyStatsList(year);
            return getDataTable(list);
        }
        List<List<SysDeptDto>> list = deptService.getMeetRecordsList();
        return getDataTable(list);
    }



}
