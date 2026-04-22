package com.supervision.grassrootsregistration.mapper;

import java.util.List;
import com.supervision.grassrootsregistration .domain.BdglMeeting;
import org.apache.ibatis.annotations.Param;

/**
 * 团支部会议登记本Mapper接口
 * 
 * @author supervision
 * @date 2022-02-26
 */
public interface BdglMeetingMapper 
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
     * 删除团支部会议登记本
     * 
     * @param id 团支部会议登记本主键
     * @return 结果
     */
    public int deleteBdglMeetingById(Integer id);

    /**
     * 批量删除团支部会议登记本
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglMeetingByIds(Integer[] ids);
    
    int selectMeetingByunitId(@Param("unitId") Integer unitId, @Param("year") String year);
    
    List<BdglMeeting> selectMeetingOnUnitList(@Param("unitId") Integer unitId, @Param("year") String year);
}
