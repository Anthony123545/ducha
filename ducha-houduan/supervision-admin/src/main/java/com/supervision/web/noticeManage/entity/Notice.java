package com.supervision.web.noticeManage.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Notice {
    private Integer id;
    private String title;
    private String description;
    private String attachment; // 存放前端传过来的文件URL
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String fileType;
}
