package com.supervision.web.controller.monitoring;

import java.awt.*;
import java.util.List;

import com.supervision.common.core.domain.entity.SysMenu;
import com.supervision.system.service.ISysMenuService;
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
import com.supervision.monitoring.domain.CameraCpController;
import com.supervision.monitoring.service.ICameraCpControllerService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 安防监控Controller
 * 
 * @author supervision
 * @date 2022-05-08
 */
@RestController
@RequestMapping("/monitoringcp/cameracp")
public class CameraCpControllerController extends BaseController
{
    @Autowired
    private ICameraCpControllerService cameraCpControllerService;
    @Autowired
    private ISysMenuService menuService;

    /**
     * 查询安防监控列表
     */
    @PreAuthorize("@ss.hasPermi('monitoringcp:cameracp:list')")
    @GetMapping("/list")
    public TableDataInfo list(CameraCpController cameraCpController)
    {
        startPage();
        List<CameraCpController> list = cameraCpControllerService.selectCameraCpControllerList(cameraCpController);
        return getDataTable(list);
    }

    /**
     * 导出安防监控列表
     */
    @PreAuthorize("@ss.hasPermi('monitoringcp:cameracp:export')")
    @Log(title = "安防监控", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(CameraCpController cameraCpController)
    {
        List<CameraCpController> list = cameraCpControllerService.selectCameraCpControllerList(cameraCpController);
        ExcelUtil<CameraCpController> util = new ExcelUtil<CameraCpController>(CameraCpController.class);
        return util.exportExcel(list, "安防监控数据");
    }

    /**
     * 获取安防监控详细信息
     */
    @PreAuthorize("@ss.hasPermi('monitoringcp:cameracp:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(cameraCpControllerService.selectCameraCpControllerById(id));
    }

    /**
     * 新增安防监控
     */
    @PreAuthorize("@ss.hasPermi('monitoringcp:cameracp:add')")
    @Log(title = "安防监控", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CameraCpController cameraCpController)
    {

        SysMenu menu = new SysMenu();
        menu.setQuery(cameraCpController.getUnitid().toString());
        menu.setPath(cameraCpController.getUnitid().toString());
        List<SysMenu> sysMenus = menuService.selectMenuLists(menu);
        if(sysMenus.size()==0){
            //上级菜单
            menu.setParentId(2611L);
            //菜单图标
            menu.setIcon("小安防监控");
            //菜单名称
            menu.setMenuName(cameraCpController.getUnitname());
            //路由地址
            menu.setPath(cameraCpController.getUnitid().toString());
            //显示状态
            menu.setVisible("0");
            //组件路径
            menu.setComponent("monitoring/camera/index");
            //权限字符
            menu.setPerms("monitoring:camera:list");
            //路由参数
            menu.setQuery(cameraCpController.getUnitid().toString());
            //是否缓存
            menu.setIsCache("0");
            //菜单状态
            menu.setStatus("0");
            //菜单类型
            menu.setMenuType("C");
            menu.setIsFrame("1");
            menuService.insertMenu(menu);
        }
        return toAjax(cameraCpControllerService.insertCameraCpController(cameraCpController));
    }

    /**
     * 修改安防监控
     */
    @PreAuthorize("@ss.hasPermi('monitoringcp:cameracp:edit')")
    @Log(title = "安防监控", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CameraCpController cameraCpController)
    {
        return toAjax(cameraCpControllerService.updateCameraCpController(cameraCpController));
    }

    /**
     * 删除安防监控
     */
    @PreAuthorize("@ss.hasPermi('monitoringcp:cameracp:remove')")
    @Log(title = "安防监控", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(cameraCpControllerService.deleteCameraCpControllerByIds(ids));
    }
}
