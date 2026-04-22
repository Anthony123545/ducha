package com.supervision.monitoring.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.monitoring.mapper.CameraControllerMapper;
import com.supervision.monitoring.domain.CameraController;
import com.supervision.monitoring.service.ICameraControllerService;

/**
 * 安防监控Service业务层处理
 * 
 * @author supervision
 * @date 2022-04-22
 */
@Service
public class CameraControllerServiceImpl implements ICameraControllerService 
{
    @Autowired
    private CameraControllerMapper cameraControllerMapper;

    /**
     * 查询安防监控
     * 
     * @param id 安防监控主键
     * @return 安防监控
     */
    @Override
    public CameraController selectCameraControllerById(Long id)
    {
        return cameraControllerMapper.selectCameraControllerById(id);
    }

    /**
     * 查询安防监控列表
     * 
     * @param cameraController 安防监控
     * @return 安防监控
     */
    @Override
    public List<CameraController> selectCameraControllerList(CameraController cameraController)
    {
        return cameraControllerMapper.selectCameraControllerList(cameraController);
    }

    /**
     * 新增安防监控
     * 
     * @param cameraController 安防监控
     * @return 结果
     */
    @Override
    public int insertCameraController(CameraController cameraController)
    {
        return cameraControllerMapper.insertCameraController(cameraController);
    }

    /**
     * 修改安防监控
     * 
     * @param cameraController 安防监控
     * @return 结果
     */
    @Override
    public int updateCameraController(CameraController cameraController)
    {
        return cameraControllerMapper.updateCameraController(cameraController);
    }

    /**
     * 批量删除安防监控
     * 
     * @param ids 需要删除的安防监控主键
     * @return 结果
     */
    @Override
    public int deleteCameraControllerByIds(Long[] ids)
    {
        return cameraControllerMapper.deleteCameraControllerByIds(ids);
    }

    /**
     * 删除安防监控信息
     * 
     * @param id 安防监控主键
     * @return 结果
     */
    @Override
    public int deleteCameraControllerById(Long id)
    {
        return cameraControllerMapper.deleteCameraControllerById(id);
    }
}
