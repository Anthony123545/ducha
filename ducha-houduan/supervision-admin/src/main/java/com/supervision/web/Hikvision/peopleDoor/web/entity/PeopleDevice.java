package com.supervision.web.Hikvision.peopleDoor.web.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PeopleDevice {
    private Long id;
    private String personId;        // 人员ID (对应 people_info.person_id)
    private Long deviceId;          // 设备ID (对应 device.id)
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

