package com.supervision.web.Hikvision.peopleDoor.web.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class EventLog {
    private Long id;
    private String personId;     // 人员ID
    private String jobNumber;    // 工号
    private String name;         // 姓名
    private String cardNumber;   // 卡号
    private String door;         // 门
    private String remoteHost;   // 远程主机地址
    private String reader;       // 读卡器
    private String detectorId;   // 探测器ID
    private String operation;    // 操作
    private String eventType;    // 事件类型
    private LocalDateTime eventTime;  // 事件发生时间
    private LocalDateTime createTime; // 记录创建时间
}
