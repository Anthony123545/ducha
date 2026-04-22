package com.supervision.announcement.service.impl;

import com.supervision.announcement.domain.Announcement;
import com.supervision.announcement.mapper.AnnouncementMapper;
import com.supervision.announcement.service.AnnouncementService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    @Resource
    private AnnouncementMapper announcementMapper;

    @Override
    public List<Announcement> listAll() {
        return announcementMapper.selectAnnouncementList();
    }

    @Override
    public Announcement getById(Long id) {
        return announcementMapper.selectAnnouncementById(id);
    }

    @Override
    public void add(Announcement announcement) {
        // 新增默认未发布
        if (announcement.getStatus() == null) {
            announcement.setStatus(0);
        }
        announcementMapper.insertAnnouncement(announcement);
    }

    @Override
    public void update(Announcement announcement) {
        announcementMapper.updateAnnouncement(announcement);
    }

    @Override
    public void delete(Long id) {
        announcementMapper.deleteAnnouncementById(id);
    }

    @Override
    public void publish(Long id) {
        announcementMapper.publishAnnouncement(id);
    }
}
