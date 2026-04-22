package com.supervision.monitoring.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.monitoring.mapper.CameraCpControllerMapper;
import com.supervision.monitoring.domain.CameraCpController;
import com.supervision.monitoring.service.ICameraCpControllerService;

/**
 * 安防监控Service业务层处理
 * 
 * @author supervision
 * @date 2022-05-08
 */
@Service
public class CameraCpControllerServiceImpl implements ICameraCpControllerService 
{
    @Autowired
    private CameraCpControllerMapper cameraCpControllerMapper;

    /**
     * 查询安防监控
     * 
     * @param id 安防监控主键
     * @return 安防监控
     */
    @Override
    public CameraCpController selectCameraCpControllerById(Long id)
    {
        return cameraCpControllerMapper.selectCameraCpControllerById(id);
    }

    /**
     * 查询安防监控列表
     * 
     * @param cameraCpController 安防监控
     * @return 安防监控
     */
    @Override
    public List<CameraCpController> selectCameraCpControllerList(CameraCpController cameraCpController)
    {
        return cameraCpControllerMapper.selectCameraCpControllerList(cameraCpController);
    }

    /**
     * 新增安防监控
     * 
     * @param cameraCpController 安防监控
     * @return 结果
     */
    @Override
    public int insertCameraCpController(CameraCpController cameraCpController)
    {
        return cameraCpControllerMapper.insertCameraCpController(cameraCpController);
    }

    /**
     * 修改安防监控
     * 
     * @param cameraCpController 安防监控
     * @return 结果
     */
    @Override
    public int updateCameraCpController(CameraCpController cameraCpController)
    {
        return cameraCpControllerMapper.updateCameraCpController(cameraCpController);
    }

    /**
     * 批量删除安防监控
     * 
     * @param ids 需要删除的安防监控主键
     * @return 结果
     */
    @Override
    public int deleteCameraCpControllerByIds(Long[] ids)
    {
        return cameraCpControllerMapper.deleteCameraCpControllerByIds(ids);
    }

    /**
     * 删除安防监控信息
     * 
     * @param id 安防监控主键
     * @return 结果
     */
    @Override
    public int deleteCameraCpControllerById(Long id)
    {
        return cameraCpControllerMapper.deleteCameraCpControllerById(id);
    }
}
