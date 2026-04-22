package com.supervision.monitoring.service;

import java.util.List;
import com.supervision.monitoring.domain.CameraCpController;

/**
 * 安防监控Service接口
 * 
 * @author supervision
 * @date 2022-05-08
 */
public interface ICameraCpControllerService 
{
    /**
     * 查询安防监控
     * 
     * @param id 安防监控主键
     * @return 安防监控
     */
    public CameraCpController selectCameraCpControllerById(Long id);

    /**
     * 查询安防监控列表
     * 
     * @param cameraCpController 安防监控
     * @return 安防监控集合
     */
    public List<CameraCpController> selectCameraCpControllerList(CameraCpController cameraCpController);

    /**
     * 新增安防监控
     * 
     * @param cameraCpController 安防监控
     * @return 结果
     */
    public int insertCameraCpController(CameraCpController cameraCpController);

    /**
     * 修改安防监控
     * 
     * @param cameraCpController 安防监控
     * @return 结果
     */
    public int updateCameraCpController(CameraCpController cameraCpController);

    /**
     * 批量删除安防监控
     * 
     * @param ids 需要删除的安防监控主键集合
     * @return 结果
     */
    public int deleteCameraCpControllerByIds(Long[] ids);

    /**
     * 删除安防监控信息
     * 
     * @param id 安防监控主键
     * @return 结果
     */
    public int deleteCameraCpControllerById(Long id);
}
