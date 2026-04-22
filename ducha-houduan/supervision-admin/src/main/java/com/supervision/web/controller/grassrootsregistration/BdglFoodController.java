package com.supervision.web.controller.grassrootsregistration;

import java.util.List;

import com.supervision.common.core.domain.entity.SysRole;
import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.framework.web.domain.server.Sys;
import com.supervision.system.mapper.SysUserMapper;
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
import com.supervision.grassrootsregistration.domain.BdglFood;
import com.supervision.grassrootsregistration.service.IBdglFoodService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 伙食管理登记薄Controller
 * 
 * @author supervision
 * @date 2022-02-26
 */
@RestController
@RequestMapping("/grassrootsregistration/bdglfood")
public class BdglFoodController extends BaseController
{
    @Autowired
    private IBdglFoodService bdglFoodService;
    @Autowired
    private SysUserMapper userMapper;

    /**
     * 查询伙食管理登记薄列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglfood:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglFood bdglFood)
    {
        LoginUser loginUser = getLoginUser();
        List<SysRole> sysRoles = userMapper.selectRole(loginUser.getUserId());
        String role = CommonsController.getRole(sysRoles);
        //判断当前角色是否是最高权限   不是返回当前部门数据
        if(!role.equals("admin")){
            bdglFood.setUnitId(Integer.parseInt(loginUser.getDeptId().toString()));
        }
        startPage();
        List<BdglFood> list = bdglFoodService.selectBdglFoodList(bdglFood);
        return getDataTable(list);
    }

    /**
     * 导出伙食管理登记薄列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglfood:export')")
    @Log(title = "伙食管理登记薄", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglFood bdglFood)
    {
        List<BdglFood> list = bdglFoodService.selectBdglFoodList(bdglFood);
        ExcelUtil<BdglFood> util = new ExcelUtil<BdglFood>(BdglFood.class);
        return util.exportExcel(list, "伙食管理登记薄数据");
    }

    /**
     * 获取伙食管理登记薄详细信息
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglfood:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bdglFoodService.selectBdglFoodById(id));
    }

    /**
     * 新增伙食管理登记薄
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglfood:add')")
    @Log(title = "伙食管理登记薄", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglFood bdglFood)
    {
        return toAjax(bdglFoodService.insertBdglFood(bdglFood));
    }

    /**
     * 修改伙食管理登记薄
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglfood:edit')")
    @Log(title = "伙食管理登记薄", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglFood bdglFood)
    {
        return toAjax(bdglFoodService.updateBdglFood(bdglFood));
    }

    /**
     * 删除伙食管理登记薄
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglfood:remove')")
    @Log(title = "伙食管理登记薄", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglFoodService.deleteBdglFoodByIds(ids));
    }
}
