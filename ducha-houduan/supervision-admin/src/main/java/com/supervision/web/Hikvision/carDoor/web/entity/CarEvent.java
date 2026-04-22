package com.supervision.web.Hikvision.carDoor.web.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CarEvent {

    /** 序号（主键） */
    private Long id;

    /** 车主姓名 */
    private String ownerName;

    /** 车牌号码 */
    private String plateNumber;

    /** 电话号码 */
    private String phoneNumber;

    /** 事件发生时间 */
    private LocalDateTime eventTime;

    /** 事件类型（IN=进门，OUT=出门） */
    private String eventType;

    /** 车辆类型（FIXED=固定车，AUTHORIZED=授权车） */
    private String carType;

    /** 车辆所属群组 */
    private String carGroup;
}
