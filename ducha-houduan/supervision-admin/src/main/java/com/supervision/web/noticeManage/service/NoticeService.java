package com.supervision.web.noticeManage.service;



import com.supervision.web.noticeManage.entity.Notice;

import java.time.LocalDateTime;
import java.util.List;

public interface NoticeService {
    boolean addNotice(Notice notice);

    List<Notice> getAllNotices();

    List<Notice> searchNotices(String title, LocalDateTime start, LocalDateTime end);

    boolean deleteNotice(Integer id);

}
