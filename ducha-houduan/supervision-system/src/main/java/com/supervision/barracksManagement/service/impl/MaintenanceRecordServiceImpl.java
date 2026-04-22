package com.supervision.barracksManagement.service.impl;

import java.util.List;
import com.supervision.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.barracksManagement.mapper.MaintenanceRecordMapper;
import com.supervision.barracksManagement.domain.MaintenanceRecord;
import com.supervision.barracksManagement.service.IMaintenanceRecordService;

/**
 * 五小工维修记录Service业务层处理
 * 
 * @author supervision
 * @date 2022-05-20
 */
@Service
public class MaintenanceRecordServiceImpl implements IMaintenanceRecordService 
{
    @Autowired
    private MaintenanceRecordMapper maintenanceRecordMapper;

    /**
     * 查询五小工维修记录
     * 
     * @param id 五小工维修记录主键
     * @return 五小工维修记录
     */
    @Override
    public MaintenanceRecord selectMaintenanceRecordById(Long id)
    {
        return maintenanceRecordMapper.selectMaintenanceRecordById(id);
    }

    /**
     * 查询五小工维修记录列表
     * 
     * @param maintenanceRecord 五小工维修记录
     * @return 五小工维修记录
     */
    @Override
    public List<MaintenanceRecord> selectMaintenanceRecordList(MaintenanceRecord maintenanceRecord)
    {
        return maintenanceRecordMapper.selectMaintenanceRecordList(maintenanceRecord);
    }

    /**
     * 新增五小工维修记录
     * 
     * @param maintenanceRecord 五小工维修记录
     * @return 结果
     */
    @Override
    public int insertMaintenanceRecord(MaintenanceRecord maintenanceRecord)
    {
        maintenanceRecord.setCreateTime(DateUtils.getNowDate());
        return maintenanceRecordMapper.insertMaintenanceRecord(maintenanceRecord);
    }

    /**
     * 修改五小工维修记录
     * 
     * @param maintenanceRecord 五小工维修记录
     * @return 结果
     */
    @Override
    public int updateMaintenanceRecord(MaintenanceRecord maintenanceRecord)
    {
        return maintenanceRecordMapper.updateMaintenanceRecord(maintenanceRecord);
    }

    /**
     * 批量删除五小工维修记录
     * 
     * @param ids 需要删除的五小工维修记录主键
     * @return 结果
     */
    @Override
    public int deleteMaintenanceRecordByIds(Long[] ids)
    {
        return maintenanceRecordMapper.deleteMaintenanceRecordByIds(ids);
    }

    /**
     * 删除五小工维修记录信息
     * 
     * @param id 五小工维修记录主键
     * @return 结果
     */
    @Override
    public int deleteMaintenanceRecordById(Long id)
    {
        return maintenanceRecordMapper.deleteMaintenanceRecordById(id);
    }
}