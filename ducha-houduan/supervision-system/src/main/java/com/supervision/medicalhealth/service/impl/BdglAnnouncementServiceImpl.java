package com.supervision.medicalhealth.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.medicalhealth.mapper.BdglAnnouncementMapper;
import com.supervision.medicalhealth.domain.BdglAnnouncement;
import com.supervision.medicalhealth.service.IBdglAnnouncementService;

/**
 * 疫情通知公告Service业务层处理
 * 
 * @author hr
 * @date 2022-02-14
 */
@Service
public class BdglAnnouncementServiceImpl implements IBdglAnnouncementService 
{
    @Autowired
    private BdglAnnouncementMapper bdglAnnouncementMapper;

    /**
     * 查询疫情通知公告
     * 
     * @param id 疫情通知公告主键
     * @return 疫情通知公告
     */
    @Override
    public BdglAnnouncement selectBdglAnnouncementById(Integer id)
    {
        return bdglAnnouncementMapper.selectBdglAnnouncementById(id);
    }

    /**
     * 查询疫情通知公告列表
     * 
     * @param bdglAnnouncement 疫情通知公告
     * @return 疫情通知公告
     */
    @Override
    public List<BdglAnnouncement> selectBdglAnnouncementList(BdglAnnouncement bdglAnnouncement)
    {
        return bdglAnnouncementMapper.selectBdglAnnouncementList(bdglAnnouncement);
    }

    /**
     * 新增疫情通知公告
     * 
     * @param bdglAnnouncement 疫情通知公告
     * @return 结果
     */
    @Override
    public int insertBdglAnnouncement(BdglAnnouncement bdglAnnouncement)
    {
        return bdglAnnouncementMapper.insertBdglAnnouncement(bdglAnnouncement);
    }

    /**
     * 修改疫情通知公告
     * 
     * @param bdglAnnouncement 疫情通知公告
     * @return 结果
     */
    @Override
    public int updateBdglAnnouncement(BdglAnnouncement bdglAnnouncement)
    {
        return bdglAnnouncementMapper.updateBdglAnnouncement(bdglAnnouncement);
    }

    /**
     * 批量删除疫情通知公告
     * 
     * @param ids 需要删除的疫情通知公告主键
     * @return 结果
     */
    @Override
    public int deleteBdglAnnouncementByIds(Integer[] ids)
    {
        return bdglAnnouncementMapper.deleteBdglAnnouncementByIds(ids);
    }

    /**
     * 删除疫情通知公告信息
     * 
     * @param id 疫情通知公告主键
     * @return 结果
     */
    @Override
    public int deleteBdglAnnouncementById(Integer id)
    {
        return bdglAnnouncementMapper.deleteBdglAnnouncementById(id);
    }
}
