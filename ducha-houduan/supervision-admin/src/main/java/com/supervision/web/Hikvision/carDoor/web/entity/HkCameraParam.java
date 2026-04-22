package com.supervision.web.Hikvision.carDoor.web.entity;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 海康摄像头相关参数
 */
@Data
public class HkCameraParam {

    @ApiModelProperty("已登录设备的IP地址")
    private String deviceIp;

    @ApiModelProperty("设备用户名")
    private String username;

    @ApiModelProperty("设备密码")
    private String password;

    @ApiModelProperty("端口号")
    private Short port;

}
