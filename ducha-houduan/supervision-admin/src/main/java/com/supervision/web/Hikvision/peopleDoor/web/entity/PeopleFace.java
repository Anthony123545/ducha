package com.supervision.web.Hikvision.peopleDoor.web.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PeopleFace {
    private Long id;
    private String personId;        // 全局唯一人员ID
    private String jobNumber;       // 工号（员工有，访客为空）
    private String name;            // 姓名
    private String faceImgUrl;      // 人脸图片路径/URL
    private String featureData;     // 人脸特征数据（可选）
    private byte[] faceImg;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
