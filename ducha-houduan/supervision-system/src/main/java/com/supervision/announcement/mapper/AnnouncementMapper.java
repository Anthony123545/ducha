package com.supervision.announcement.mapper;

import com.supervision.announcement.domain.Announcement;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AnnouncementMapper {

    List<Announcement> selectAnnouncementList();

    Announcement selectAnnouncementById(@Param("id") Long id);

    int insertAnnouncement(Announcement announcement);

    int updateAnnouncement(Announcement announcement);

    int deleteAnnouncementById(@Param("id") Long id);

    int publishAnnouncement(@Param("id") Long id);
}
