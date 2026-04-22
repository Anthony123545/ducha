package com.supervision.safety.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class CreateIssueReq {
    @NotBlank private String description;
    @NotNull  private RiskLevel riskLevel;
    @NotNull  private Date foundAt;
    private String checkTarget;    // 排查对象
    private String hazardName;     // 隐患名称
    private String hazardCategory; // 隐患类别
    private String hazardSource;   // 隐患来源
    private String dept;           // 责任单位
    private String person;         // 责任人
    // SafetyIssue.java 和 CreateIssueReq.java 都要加
    private String rectificationMeasures; // 整治措施
    private Date acceptanceTime;         // 验收时间
    private String acceptanceUnit;       // 验收单位
    private String acceptanceConclusion; // 验收结论
    private Date closeTime;              // 销账时间
    private String remark;               // 备注


}
