package com.supervision.web.noticeManage.service.impl;

import com.supervision.web.noticeManage.entity.Notice;
import com.supervision.web.noticeManage.mapper.NoticeMapper;
import com.supervision.web.noticeManage.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service

public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public boolean addNotice(Notice notice) {
        // 补全时间（前端不传也可以由数据库默认）
        LocalDateTime now = LocalDateTime.now();
        if (notice.getCreateTime() == null) {
            notice.setCreateTime(now);
        }
        notice.setUpdateTime(now);

        int rows = noticeMapper.insertNotice(notice);
        return rows > 0;
    }

    @Override
    public List<Notice> getAllNotices() {
        return noticeMapper.selectAll();
    }

    @Override
    public List<Notice> searchNotices(String title, LocalDateTime start, LocalDateTime end) {
        return noticeMapper.selectByTitleAndTimeRange(title, start, end);
    }

    @Override
    public boolean deleteNotice(Integer id) {
        int rows = noticeMapper.deleteById(id);
        return rows > 0;
    }
}
