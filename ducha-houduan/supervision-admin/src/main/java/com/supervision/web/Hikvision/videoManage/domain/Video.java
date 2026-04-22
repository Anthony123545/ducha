package com.supervision.web.Hikvision.videoManage.domain;


import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.converters.string.StringStringConverter;
import com.supervision.web.Hikvision.videoManage.util.NullableIntegerConverter;
import com.supervision.web.Hikvision.videoManage.util.NullableLongConverter;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Video {

    @ExcelProperty("主键ID")
    private Long id;

    @ExcelProperty(value = "视频名称", converter = StringStringConverter.class)
    private String name;

    @ExcelProperty(value = "视频设备类型", converter = StringStringConverter.class)
    private String deviceType;

    @ExcelProperty(value = "IP地址", converter = StringStringConverter.class)
    private String ip;

    @ExcelProperty(value = "端口号", converter = NullableIntegerConverter.class)
    private Integer port;

    @ExcelProperty(value = "用户名", converter = StringStringConverter.class)
    private String username;

    @ExcelProperty(value = "密码", converter = StringStringConverter.class)
    private String password;

    @ExcelProperty(value = "通道号", converter = NullableIntegerConverter.class)
    private Integer channel;

    @ExcelProperty(value = "通道名称", converter = StringStringConverter.class)
    private String channelName;

    @ExcelProperty(value = "码流类型", converter = NullableIntegerConverter.class)
    private Integer streamType;

    @ExcelProperty(value = "码流号", converter = StringStringConverter.class)
    private String streamCode;

    @ExcelProperty(value = "RTSP端口号", converter = NullableIntegerConverter.class)
    private Integer rtspPort;

    @ExcelProperty(value = "RTSP地址", converter = StringStringConverter.class)
    private String rtspUrl;

    @ExcelProperty(value = "所属主设备ID", converter = NullableLongConverter.class)
    private Long parentDeviceId;

    @ExcelProperty("是否启用")
    private Boolean enable;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

