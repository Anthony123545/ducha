package com.supervision.web.Hikvision.carDoor.web.entity;


import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

/**
 * 车辆信息实体类（对应前端 formData）
 */
@Data
public class CarInfo {

    private Long id; // 主键

    // 一、车牌信息
    @NotBlank(message = "车牌号不能为空")
    private String plateNumber;      // 车牌号码

    private String plateColor;       // 车牌颜色

    private String plateType;        // 车牌类型

    // 二、车辆信息
    private String vehicleType;      // 车辆类型

    private String vehicleColor;     // 车辆颜色

    private String vehicleBrand;     // 车辆品牌

    private String engineNo;         // 发动机号

    private String emissionNo;       // 排放量号

    private String vin;              // 车架号

    private String displacement;     // 排量

    private String identifyCode;     // 识别代码

    private String fleetName;        // 车队名称

    // 三、卡片信息
    private String issuerName;       // 发卡人员

    private String issuerDeviceType; // 发卡器类型

    private String parkingType;      // 停车类型

    private String packageInfo;      // 包时信息

    // 四、车主信息
    private String ownerName;        // 车主姓名

    private String phoneNumber;      // 手机号码

    private String certificateNo;    // 证件号码

    private String address;          // 住址

    private String position;         // 职位

    private String company;          // 所属公司

    private String departmentName;   // 部门名称

    // 五、分组与权限信息
    private String groupName;        // 分组名称

    private String vehicleGroup;     // 车辆群组

    private List<Long> parkingPermission; // 场库权限（ID 列表）

    private String parkingName;      // 场库名称

    private List<LocalTime> dailyValidTime; // 每日有效// 时段

    private List<LocalDate> historyValidTime; // 历史有效期

    // 六、其他信息
    private String remarks;          // 备注

    private String dataVersion;      // 数据版本

    private Date validUntil;           // 有效期

    private String isTemporary; // 是否临时车辆

    private Date createTime;           // 创建时间

    private Date updateTime;           // 更新时间

    private Boolean enable;         // 是否启用

}

