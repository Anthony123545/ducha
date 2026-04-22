package com.supervision.grassrootsregistration.mapper;

import com.supervision.grassrootsregistration.domain.BdglAttendanceRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 考勤记录Mapper接口
 *
 * @author ruoyi
 * @date 2022-10-20
 */
public interface BdglAttendanceRecordMapper
{
    /**
     * 查询考勤记录
     *
     * @param id 考勤记录主键
     * @return 考勤记录
     */
    public BdglAttendanceRecord selectBdglAttendanceRecordById(Long id);

    /**
     * 查询考勤记录列表
     *
     * @param bdglAttendanceRecord 考勤记录
     * @return 考勤记录集合
     */
    public List<BdglAttendanceRecord> selectBdglAttendanceRecordList(BdglAttendanceRecord bdglAttendanceRecord);

    /**
     * 新增考勤记录
     *
     * @param bdglAttendanceRecord 考勤记录
     * @return 结果
     */
    public int insertBdglAttendanceRecord(BdglAttendanceRecord bdglAttendanceRecord);

    /**
     * 修改考勤记录
     *
     * @param bdglAttendanceRecord 考勤记录
     * @return 结果
     */
    public int updateBdglAttendanceRecord(BdglAttendanceRecord bdglAttendanceRecord);

    /**
     * 删除考勤记录
     *
     * @param id 考勤记录主键
     * @return 结果
     */
    public int deleteBdglAttendanceRecordById(Long id);

    /**
     * 批量删除考勤记录
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglAttendanceRecordByIds(Long[] ids);

    public int batchInsertBdglAttendanceRecord(@Param("dataMap")Map<String,Object> dataMap);
    
    List<BdglAttendanceRecord> selectBdglAttendanceRecordLists(Integer stateno);
    
    BdglAttendanceRecord selectBdglAttendanceRecords(String pin);
    
    BdglAttendanceRecord select(String departureTime);
    
    BdglAttendanceRecord selectBdglAttendanceRecord(BdglAttendanceRecord bdglAttendanceRecord1);
}
