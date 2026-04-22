package com.supervision.barracksManagement.service;

import java.util.List;
import com.supervision.barracksManagement.domain.MaintenanceRecord;

/**
 * 五小工维修记录Service接口
 * 
 * @author supervision
 * @date 2022-05-20
 */
public interface IMaintenanceRecordService 
{
    /**
     * 查询五小工维修记录
     * 
     * @param id 五小工维修记录主键
     * @return 五小工维修记录
     */
    public MaintenanceRecord selectMaintenanceRecordById(Long id);

    /**
     * 查询五小工维修记录列表
     * 
     * @param maintenanceRecord 五小工维修记录
     * @return 五小工维修记录集合
     */
    public List<MaintenanceRecord> selectMaintenanceRecordList(MaintenanceRecord maintenanceRecord);

    /**
     * 新增五小工维修记录
     * 
     * @param maintenanceRecord 五小工维修记录
     * @return 结果
     */
    public int insertMaintenanceRecord(MaintenanceRecord maintenanceRecord);

    /**
     * 修改五小工维修记录
     * 
     * @param maintenanceRecord 五小工维修记录
     * @return 结果
     */
    public int updateMaintenanceRecord(MaintenanceRecord maintenanceRecord);

    /**
     * 批量删除五小工维修记录
     * 
     * @param ids 需要删除的五小工维修记录主键集合
     * @return 结果
     */
    public int deleteMaintenanceRecordByIds(Long[] ids);

    /**
     * 删除五小工维修记录信息
     * 
     * @param id 五小工维修记录主键
     * @return 结果
     */
    public int deleteMaintenanceRecordById(Long id);
}