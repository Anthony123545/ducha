package com.supervision.announcement.service;

import com.supervision.announcement.domain.Announcement;

import java.util.List;

public interface AnnouncementService {

    List<Announcement> listAll();

    Announcement getById(Long id);

    void add(Announcement announcement);

    void update(Announcement announcement);

    void delete(Long id);

    void publish(Long id);
}
