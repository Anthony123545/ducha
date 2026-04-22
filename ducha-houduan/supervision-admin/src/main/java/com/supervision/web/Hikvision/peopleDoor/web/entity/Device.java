package com.supervision.web.Hikvision.peopleDoor.web.entity;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class Device {

    private Long id;                   // 系统内部设备ID

    private String serialNumber;       // 设备序列号（唯一）

    @NotBlank(message = "设备名称不能为空")
    private String name;               // 设备名称

    private String type;               // 设备类型：carDoor, peopleDoor

    private String accessPermission;   // 访问权限

    private Boolean isUpdateStatus;    // 是否更新人员进出或请假状态

    private String location;           // 设备位置描述

    @NotBlank(message = "IP不能为空")
    private String ip;                 // 设备IP

    @NotNull(message = "端口不能为空")
    private Integer port;              // 端口

    @NotBlank(message = "账号不能为空")
    private String account;            // 登录账号

    @NotBlank(message = "密码不能为空")
    private String password;           // 登录密码

    private Boolean isConnect;         // 是否已连接

    private String status;             // 在线状态：online/offline

    private String firmwareVersion;    // 固件版本

    private String doorsJson;              // 门体信息JSON字符串

    private String videosJson;           // 摄像头信息JSON字符串

    private String remark;              // 备注信息

    private Date createTime;           // 创建时间

    private Date updateTime;           // 更新时间

    private Boolean enable;         // 是否启用

}