package com.supervision.web.Hikvision.peopleDoor.web.entity;

import lombok.Data;

@Data
public class DeviceAddRequest {

    private String deviceId;   // 设备编号
    private String model;      // 型号
    private String location;   // 位置
    private String ip;         // IP
    private Integer port;      // 端口
    private String account;    // 账号
    private String password;   // 密码

    /**
     * 同步模式：
     * 0 = 不同步
     * 1 = 同步所有设备人员
     * 2 = 从指定设备同步人员
     */
    private Integer syncMode;

    /**
     * 当 syncMode = 2 时生效，表示来源设备ID
     */
    private Long syncFromDeviceId;

//    private String deviceId;
//    private String model;
//    private String location;
//    private String ip;
//    private Integer port;
//    private String account;
//    private String password;
//
//    // 同步策略
//    private Long syncFromDeviceId;   // 可选：从单个设备同步
//    private Boolean syncAll = false; // 可选：是否同步所有设备
}

