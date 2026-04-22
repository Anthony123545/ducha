package com.supervision.grassrootsregistration.service;

import com.supervision.grassrootsregistration.domain.BdglAttendanceRecord;

import java.util.List;

/**
 * 考勤记录Service接口
 *
 * @author ruoyi
 * @date 2022-10-20
 */
public interface IBdglAttendanceRecordService
{
    /**
     * 查询考勤记录
     *
     * @param id 考勤记录主键
     * @return 考勤记录
     */
    public BdglAttendanceRecord selectBdglAttendanceRecordById(Long id);

    public void getemployeeinfo();

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
     * 批量删除考勤记录
     *
     * @param ids 需要删除的考勤记录主键集合
     * @return 结果
     */
    public int deleteBdglAttendanceRecordByIds(Long[] ids);

    /**
     * 删除考勤记录信息
     *
     * @param id 考勤记录主键
     * @return 结果
     */
    public int deleteBdglAttendanceRecordById(Long id);

    public List<BdglAttendanceRecord> getAttendanceRecordsListByMultipleIp(BdglAttendanceRecord bdglAttendanceRecord,int type);
    
    List<BdglAttendanceRecord> selectBdglAttendanceRecordLists(Integer stateno);
    
    BdglAttendanceRecord selectBdglAttendanceRecords(String pin);
    
    BdglAttendanceRecord select(String departureTime);
    
    BdglAttendanceRecord selectBdglAttendanceRecord(BdglAttendanceRecord bdglAttendanceRecord1);
}
