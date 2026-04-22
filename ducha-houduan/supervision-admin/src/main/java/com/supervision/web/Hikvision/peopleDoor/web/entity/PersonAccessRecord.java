package com.supervision.web.Hikvision.peopleDoor.web.entity;


import lombok.Data;
import java.util.Date;

/**
 * 人员通行记录实体类
 * 用于记录海康威视人脸识别或门禁设备上传的通行事件。
 * 对应前端“人员通行记录”表格展示。
 */
@Data
public class PersonAccessRecord {

    private Long id;

    private Long deviceId;

    private int majorType;

    private int minorType;

    private int employeeNo;

    private String name;

    private int cardNo;

    /**
     * 事件类型：
     *  - "in"：进门
     *  - "out"：出门
     *  - "denied"：通行拒绝（可选）
     * 具体值取决于设备上报的事件编码。
     */
    private String eventType;

    private String eventMessage;

    private Date captureTime;

    private String photoUrl;

    private String remark;

    private Date createTime;

    private Date updateTime;
}

