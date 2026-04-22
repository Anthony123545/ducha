package com.supervision.monitoring.service;

import java.util.List;
import com.supervision.monitoring.domain.CameraController;

/**
 * 安防监控Service接口
 * 
 * @author supervision
 * @date 2022-04-22
 */
public interface ICameraControllerService 
{
    /**
     * 查询安防监控
     * 
     * @param id 安防监控主键
     * @return 安防监控
     */
    public CameraController selectCameraControllerById(Long id);

    /**
     * 查询安防监控列表
     * 
     * @param cameraController 安防监控
     * @return 安防监控集合
     */
    public List<CameraController> selectCameraControllerList(CameraController cameraController);

    /**
     * 新增安防监控
     * 
     * @param cameraController 安防监控
     * @return 结果
     */
    public int insertCameraController(CameraController cameraController);

    /**
     * 修改安防监控
     * 
     * @param cameraController 安防监控
     * @return 结果
     */
    public int updateCameraController(CameraController cameraController);

    /**
     * 批量删除安防监控
     * 
     * @param ids 需要删除的安防监控主键集合
     * @return 结果
     */
    public int deleteCameraControllerByIds(Long[] ids);

    /**
     * 删除安防监控信息
     * 
     * @param id 安防监控主键
     * @return 结果
     */
    public int deleteCameraControllerById(Long id);
}
