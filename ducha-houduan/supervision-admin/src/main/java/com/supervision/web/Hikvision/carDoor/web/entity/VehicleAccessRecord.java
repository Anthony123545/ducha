package com.supervision.web.Hikvision.carDoor.web.entity;

import lombok.Data;
import java.util.Date;

/**
 * 车辆进出记录实体类
 * 用于记录海康威视车牌识别设备上报的车辆进出事件。
 * 对应前端“车辆进出记录”表格展示。
 */
@Data
public class VehicleAccessRecord {

    /** 主键ID，自增，用于唯一标识一条车辆通行记录 */
    private Long id;

    /** 设备ID，对应设备表中的主键，用于区分来源设备（哪台车牌识别一体机） */
    private Long deviceId;

    /** 车牌号，例如 "粤A12345"，用于识别车辆身份 */
    private String plateNumber;

    /**
     * 事件类型：
     *  - "in"：进场（车辆进入）
     *  - "out"：出场（车辆离开）
     * 具体值取决于设备上报的事件类型编码。
     */
    private String eventType;

    private String eventMessage;

    /** 抓拍时间，表示车辆被识别的具体时间点 */
    private Date captureTime;

    /** 抓拍图片URL，保存车辆进出时的图片路径（用于前端展示） */
    private String photoUrl;

    /** 备注信息，例如“识别异常”、“人工补录”、“遮挡车牌”等说明 */
    private String remark;

    /** 记录创建时间（系统插入数据库时自动生成） */
    private Date createTime;

    /** 记录最后更新时间（系统更新时自动刷新） */
    private Date updateTime;
}
