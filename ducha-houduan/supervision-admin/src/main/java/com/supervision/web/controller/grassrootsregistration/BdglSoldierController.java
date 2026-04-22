package com.supervision.web.controller.grassrootsregistration;

import java.util.List;

import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.common.core.domain.entity.SysRole;
import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.common.utils.StringUtils;
import com.supervision.grassrootsregistration.resultVo.SysDeptDto;
import com.supervision.peopleManage.domain.BdglPeople;
import com.supervision.peopleManage.service.IBdglPeopleService;
import com.supervision.system.mapper.SysUserMapper;
import com.supervision.system.service.ISysDeptService;
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
import com.supervision.grassrootsregistration .domain.BdglSoldier;
import com.supervision.grassrootsregistration .service.IBdglSoldierService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 军人委员会会议登记本Controller
 *
 * @author supervision
 * @date 2022-02-26
 */
@RestController
@RequestMapping("/grassrootsregistration/bdglsoldier")
public class BdglSoldierController extends BaseController
{
    @Autowired
    private IBdglSoldierService bdglSoldierService;
    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private ISysDeptService deptService;

    /**
     * 查询军人委员会会议登记本列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglsoldier:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglSoldier bdglSoldier)
    {
        LoginUser loginUser = getLoginUser();
        List<SysRole> sysRoles = userMapper.selectRole(loginUser.getUserId());
        String role = CommonsController.getRole(sysRoles);
        //判断当前角色是否是最高权限   不是返回当前部门数据
        if(!role.equals("admin")){
            bdglSoldier.setUnitId(Integer.parseInt(loginUser.getDeptId().toString()));
        }
        startPage();
        List<BdglSoldier> list = bdglSoldierService.selectBdglSoldierList(bdglSoldier);
        return getDataTable(list);
    }

    /**
     * 领导查询军人委员会会议登记本
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglsoldiers:list')")
    @GetMapping("/getSoldierList")
    public TableDataInfo getSoldierList(String year)
    {
        if (StringUtils.isNotEmpty(year)){
            startPage();
            List<SysDeptDto> list = bdglSoldierService.querySoldierList(year);
            return getDataTable(list);
        }
        List<List<SysDeptDto>> list = deptService.getMeetRecordsList();
        return getDataTable(list);
    }

    /**
     * 领导查询军人委员会会议登记本
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglsoldiers:list')")
    @GetMapping("/getSoldierOnUnitList")
    public TableDataInfo getSoldierOnUnitList(Integer unitId,String year)
    {
        startPage();
        List<BdglSoldier> list = bdglSoldierService.getSoldierOnUnitList(unitId,year);
        return getDataTable(list);
    }

    /**
     * 导出军人委员会会议登记本列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglsoldier:export')")
    @Log(title = "军人委员会会议登记本", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglSoldier bdglSoldier)
    {
        List<BdglSoldier> list = bdglSoldierService.selectBdglSoldierList(bdglSoldier);
        ExcelUtil<BdglSoldier> util = new ExcelUtil<BdglSoldier>(BdglSoldier.class);
        return util.exportExcel(list, "军人委员会会议登记本数据");
    }

    /**
     * 获取军人委员会会议登记本详细信息
     */
    @PreAuthorize("@ss.hasAnyPermi('grassrootsregistration:bdglsoldier:query,grassrootsregistration:bdglsoldiers:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bdglSoldierService.selectBdglSoldierById(id));
    }

    /**
     * 新增军人委员会会议登记本
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglsoldier:add')")
    @Log(title = "军人委员会会议登记本", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglSoldier bdglSoldier)
    {
        return toAjax(bdglSoldierService.insertBdglSoldier(bdglSoldier));
    }

    /**
     * 修改军人委员会会议登记本
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglsoldier:edit')")
    @Log(title = "军人委员会会议登记本", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglSoldier bdglSoldier)
    {
        return toAjax(bdglSoldierService.updateBdglSoldier(bdglSoldier));
    }

    /**
     * 删除军人委员会会议登记本
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglsoldier:remove')")
    @Log(title = "军人委员会会议登记本", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglSoldierService.deleteBdglSoldierByIds(ids));
    }
}
