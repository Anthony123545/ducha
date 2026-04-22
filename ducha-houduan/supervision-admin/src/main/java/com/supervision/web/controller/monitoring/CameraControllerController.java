package com.supervision.web.controller.monitoring;

import java.util.List;
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
import com.supervision.monitoring.domain.CameraController;
import com.supervision.monitoring.service.ICameraControllerService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 安防监控Controller
 * 
 * @author supervision
 * @date 2022-04-22
 */
@RestController
@RequestMapping("/monitoring/camera")
public class CameraControllerController extends BaseController
{
    @Autowired
    private ICameraControllerService cameraControllerService;

    /**
     * 查询安防监控列表
     */
    @PreAuthorize("@ss.hasPermi('monitoring:camera:list')")
    @GetMapping("/list")
    public TableDataInfo list(CameraController cameraController)
    {
        startPage();
        List<CameraController> list = cameraControllerService.selectCameraControllerList(cameraController);
        return getDataTable(list);
    }

    /**
     * 导出安防监控列表
     */
    @PreAuthorize("@ss.hasPermi('monitoring:camera:export')")
    @Log(title = "安防监控", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(CameraController cameraController)
    {
        List<CameraController> list = cameraControllerService.selectCameraControllerList(cameraController);
        ExcelUtil<CameraController> util = new ExcelUtil<CameraController>(CameraController.class);
        return util.exportExcel(list, "安防监控数据");
    }

    /**
     * 获取安防监控详细信息
     */
    @PreAuthorize("@ss.hasPermi('monitoring:camera:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(cameraControllerService.selectCameraControllerById(id));
    }

    /**
     * 新增安防监控
     */
    @PreAuthorize("@ss.hasPermi('monitoring:camera:add')")
    @Log(title = "安防监控", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CameraController cameraController)
    {
        return toAjax(cameraControllerService.insertCameraController(cameraController));
    }

    /**
     * 修改安防监控
     */
    @PreAuthorize("@ss.hasPermi('monitoring:camera:edit')")
    @Log(title = "安防监控", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CameraController cameraController)
    {
        return toAjax(cameraControllerService.updateCameraController(cameraController));
    }

    /**
     * 删除安防监控
     */
    @PreAuthorize("@ss.hasPermi('monitoring:camera:remove')")
    @Log(title = "安防监控", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(cameraControllerService.deleteCameraControllerByIds(ids));
    }
}
