package com.supervision.monitoring.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.monitoring.mapper.CameraManageMapper;
import com.supervision.monitoring.domain.CameraManage;
import com.supervision.monitoring.service.ICameraManageService;

/**
 * 摄像头管理Service业务层处理
 * 
 * @author lanse
 * @date 2022-10-30
 */
@Service
public class CameraManageServiceImpl implements ICameraManageService 
{
    @Autowired
    private CameraManageMapper cameraManageMapper;

    /**
     * 查询摄像头管理
     * 
     * @param id 摄像头管理主键
     * @return 摄像头管理
     */
    @Override
    public CameraManage selectCameraManageById(Long id)
    {
        return cameraManageMapper.selectCameraManageById(id);
    }

    /**
     * 查询摄像头管理列表
     * 
     * @param cameraManage 摄像头管理
     * @return 摄像头管理
     */
    @Override
    public List<CameraManage> selectCameraManageList(CameraManage cameraManage)
    {
        return cameraManageMapper.selectCameraManageList(cameraManage);
    }

    /**
     * 新增摄像头管理
     * 
     * @param cameraManage 摄像头管理
     * @return 结果
     */
    @Override
    public int insertCameraManage(CameraManage cameraManage)
    {
        return cameraManageMapper.insertCameraManage(cameraManage);
    }

    /**
     * 修改摄像头管理
     * 
     * @param cameraManage 摄像头管理
     * @return 结果
     */
    @Override
    public int updateCameraManage(CameraManage cameraManage)
    {
        return cameraManageMapper.updateCameraManage(cameraManage);
    }

    /**
     * 批量删除摄像头管理
     * 
     * @param ids 需要删除的摄像头管理主键
     * @return 结果
     */
    @Override
    public int deleteCameraManageByIds(Long[] ids)
    {
        return cameraManageMapper.deleteCameraManageByIds(ids);
    }

    /**
     * 删除摄像头管理信息
     * 
     * @param id 摄像头管理主键
     * @return 结果
     */
    @Override
    public int deleteCameraManageById(Long id)
    {
        return cameraManageMapper.deleteCameraManageById(id);
    }
}
