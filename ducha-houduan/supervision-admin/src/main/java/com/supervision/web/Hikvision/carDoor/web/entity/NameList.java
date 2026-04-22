package com.supervision.web.Hikvision.carDoor.web.entity;

import lombok.Data;
import java.util.Date;

/**
 * 车辆名单实体类
 *
 * 该类用于管理车辆授权/非授权名单，同时与海康 SDK 的 NET_DVR_VEHICLE_CONTROL_LIST_INFO
 * 结构体对应，支持下发到设备或从设备获取。
 */
@Data
public class NameList {

    /** 数据库自增主键ID，用于唯一标识一条名单记录 */
    private String id;

    /** 设备ID，关联出入口抓拍机或门禁设备 */
    private Long deviceId;

    // ========== 车牌信息 ==========

    /** 车牌号，对应 SDK 的 sLicense */
    private String licensePlate;

    /** 名单类型，：0-授权，1-非授权 */
    private Integer type;

    // ========== 有效时间 ==========

    /** 车辆名单有效起始时间，对应 SDK 的 struStartTime */
    private Date accessStartTime;

    /** 车辆名单有效结束时间，对应 SDK 的 struStopTime */
    private Date accessEndTime;

    /** 通道号，对应 dwChannel，出入口抓拍机或一体机默认 1，可选 */
    private Integer channel;

    /** 车牌类型，对应 SDK byPlateType 枚举，可选，默认 0 */
    private Integer plateType;

    /** 车牌颜色，对应 SDK byPlateColor 枚举，可选，默认 0 */
    private Integer plateColor;

    /** 卡号，对应 SDK sCardNo，可选，用于授权刷卡车辆 */
    private String cardNo;

    /** 数据创建时间，数据库自动生成 */
    private Date createTime;

    /** 数据更新时间，数据库自动维护，每次更新自动刷新 */
    private Date updateTime;

}
