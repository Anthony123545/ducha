package com.supervision.medicalhealth.mapper;

import java.util.List;
import com.supervision.medicalhealth.domain.BdglAnnouncement;

/**
 * 疫情通知公告Mapper接口
 * 
 * @author hr
 * @date 2022-02-14
 */
public interface BdglAnnouncementMapper 
{
    /**
     * 查询疫情通知公告
     * 
     * @param id 疫情通知公告主键
     * @return 疫情通知公告
     */
    public BdglAnnouncement selectBdglAnnouncementById(Integer id);

    /**
     * 查询疫情通知公告列表
     * 
     * @param bdglAnnouncement 疫情通知公告
     * @return 疫情通知公告集合
     */
    public List<BdglAnnouncement> selectBdglAnnouncementList(BdglAnnouncement bdglAnnouncement);

    /**
     * 新增疫情通知公告
     * 
     * @param bdglAnnouncement 疫情通知公告
     * @return 结果
     */
    public int insertBdglAnnouncement(BdglAnnouncement bdglAnnouncement);

    /**
     * 修改疫情通知公告
     * 
     * @param bdglAnnouncement 疫情通知公告
     * @return 结果
     */
    public int updateBdglAnnouncement(BdglAnnouncement bdglAnnouncement);

    /**
     * 删除疫情通知公告
     * 
     * @param id 疫情通知公告主键
     * @return 结果
     */
    public int deleteBdglAnnouncementById(Integer id);

    /**
     * 批量删除疫情通知公告
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglAnnouncementByIds(Integer[] ids);
}
