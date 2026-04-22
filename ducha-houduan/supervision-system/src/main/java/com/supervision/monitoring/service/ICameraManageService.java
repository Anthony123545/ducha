package com.supervision.monitoring.service;

import java.util.List;
import com.supervision.monitoring.domain.CameraManage;

/**
 * 摄像头管理Service接口
 * 
 * @author lanse
 * @date 2022-10-30
 */
public interface ICameraManageService 
{
    /**
     * 查询摄像头管理
     * 
     * @param id 摄像头管理主键
     * @return 摄像头管理
     */
    public CameraManage selectCameraManageById(Long id);

    /**
     * 查询摄像头管理列表
     * 
     * @param cameraManage 摄像头管理
     * @return 摄像头管理集合
     */
    public List<CameraManage> selectCameraManageList(CameraManage cameraManage);

    /**
     * 新增摄像头管理
     * 
     * @param cameraManage 摄像头管理
     * @return 结果
     */
    public int insertCameraManage(CameraManage cameraManage);

    /**
     * 修改摄像头管理
     * 
     * @param cameraManage 摄像头管理
     * @return 结果
     */
    public int updateCameraManage(CameraManage cameraManage);

    /**
     * 批量删除摄像头管理
     * 
     * @param ids 需要删除的摄像头管理主键集合
     * @return 结果
     */
    public int deleteCameraManageByIds(Long[] ids);

    /**
     * 删除摄像头管理信息
     * 
     * @param id 摄像头管理主键
     * @return 结果
     */
    public int deleteCameraManageById(Long id);
}
