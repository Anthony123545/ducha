package com.supervision.grassrootsregistration.service;

import java.util.List;
import com.supervision.grassrootsregistration .domain.BdglMeeting;
import com.supervision.grassrootsregistration.resultVo.SysDeptDto;

/**
 * 团支部会议登记本Service接口
 * 
 * @author supervision
 * @date 2022-02-26
 */
public interface IBdglMeetingService 
{
    /**
     * 查询团支部会议登记本
     * 
     * @param id 团支部会议登记本主键
     * @return 团支部会议登记本
     */
    public BdglMeeting selectBdglMeetingById(Integer id);

    /**
     * 查询团支部会议登记本列表
     * 
     * @param bdglMeeting 团支部会议登记本
     * @return 团支部会议登记本集合
     */
    public List<BdglMeeting> selectBdglMeetingList(BdglMeeting bdglMeeting);

    /**
     * 新增团支部会议登记本
     * 
     * @param bdglMeeting 团支部会议登记本
     * @return 结果
     */
    public int insertBdglMeeting(BdglMeeting bdglMeeting);

    /**
     * 修改团支部会议登记本
     * 
     * @param bdglMeeting 团支部会议登记本
     * @return 结果
     */
    public int updateBdglMeeting(BdglMeeting bdglMeeting);

    /**
     * 批量删除团支部会议登记本
     * 
     * @param ids 需要删除的团支部会议登记本主键集合
     * @return 结果
     */
    public int deleteBdglMeetingByIds(Integer[] ids);

    /**
     * 删除团支部会议登记本信息
     * 
     * @param id 团支部会议登记本主键
     * @return 结果
     */
    public int deleteBdglMeetingById(Integer id);
    
    List<SysDeptDto> getMeetingList(String year);
    
    List<BdglMeeting> getMeetingOnUnitList(Integer unitId,String year);
}
