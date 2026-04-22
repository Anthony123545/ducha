package com.supervision.web.Hikvision.peopleDoor.web.entity;


import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PeopleInfo {
    private Long id;
    private String personId;        // 外键 -> PeopleFace.personId
    private Long deviceId;          // 设备ID
    private String name;            // 冗余存储人员姓名
    private Boolean longTerm;       // 是否长期有效
    private LocalDateTime validPeriod; // 有效期（longTerm=false时才生效）
    private String doorPermission;  // 门权限ID列表
    private String planTemplateId;  // 计划模板编号
    private String authMethod;      // 认证方式（人脸/指纹/密码/卡片）
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Boolean isTemporary;
}


