package com.supervision.barracksManagement.mapper;

import java.util.List;
import com.supervision.barracksManagement.domain.MaintenanceRecord;

/**
 * 五小工维修记录Mapper接口
 * 
 * @author supervision
 * @date 2022-05-20
 */
public interface MaintenanceRecordMapper 
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
     * 删除五小工维修记录
     * 
     * @param id 五小工维修记录主键
     * @return 结果
     */
    public int deleteMaintenanceRecordById(Long id);

    /**
     * 批量删除五小工维修记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMaintenanceRecordByIds(Long[] ids);
}