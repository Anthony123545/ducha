package com.supervision.web.controller.monitoring;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.supervision.monitoring.domain.CameraManage;
import com.supervision.monitoring.service.ICameraManageService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 摄像头管理Controller
 * 
 * @author lanse
 * @date 2022-10-30
 */
@RestController
@RequestMapping("/monitoring/brigadecamera")
public class CameraManageController extends BaseController
{
    @Autowired
    private ICameraManageService cameraManageService;

    /**
     * 查询摄像头管理列表
     */
    @PreAuthorize("@ss.hasPermi('monitoring:brigadecamera:list')")
    @GetMapping("/list")
    public TableDataInfo list(CameraManage cameraManage)
    {
        startPage();
        List<CameraManage> list = cameraManageService.selectCameraManageList(cameraManage);
        return getDataTable(list);
    }

    /**
     * 导出摄像头管理列表
     */
    @PreAuthorize("@ss.hasPermi('monitoring:brigadecamera:export')")
    @Log(title = "摄像头管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CameraManage cameraManage)
    {
        List<CameraManage> list = cameraManageService.selectCameraManageList(cameraManage);
        ExcelUtil<CameraManage> util = new ExcelUtil<CameraManage>(CameraManage.class);
        util.exportExcel(response, list, "摄像头管理数据");
    }

    /**
     * 获取摄像头管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('monitoring:brigadecamera:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(cameraManageService.selectCameraManageById(id));
    }

    /**
     * 新增摄像头管理
     */
    @PreAuthorize("@ss.hasPermi('monitoring:brigadecamera:add')")
    @Log(title = "摄像头管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CameraManage cameraManage)
    {
        return toAjax(cameraManageService.insertCameraManage(cameraManage));
    }

    /**
     * 修改摄像头管理
     */
    @PreAuthorize("@ss.hasPermi('monitoring:brigadecamera:edit')")
    @Log(title = "摄像头管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CameraManage cameraManage)
    {
        return toAjax(cameraManageService.updateCameraManage(cameraManage));
    }

    /**
     * 删除摄像头管理
     */
    @PreAuthorize("@ss.hasPermi('monitoring:brigadecamera:remove')")
    @Log(title = "摄像头管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(cameraManageService.deleteCameraManageByIds(ids));
    }
}
