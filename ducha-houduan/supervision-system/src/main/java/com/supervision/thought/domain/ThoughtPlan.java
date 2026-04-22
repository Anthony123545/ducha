package com.supervision.thought.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 月教育计划对象 thought_plan
 * 
 * @author supervision
 * @date 2022-04-08
 */
public class ThoughtPlan extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 计划名称 */
    @Excel(name = "计划名称")
    private String planName;

    /** 计划开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "计划开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date planStartTime;

    /** 计划结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "计划结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date planEndTime;

    /** 计划简介 */
    @Excel(name = "计划简介")
    private String planIntroduction;

    /** 文件路径 */
    @Excel(name = "文件路径")
    private String filePath;

    /** 文件名称 */
    @Excel(name = "文件名称")
    private String fileName;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createName;

    /** 创建人id */
    @Excel(name = "创建人id")
    private Long createId;

    /** 需响应计划数 */
    @Excel(name = "需响应计划数")
    private Integer planNumber;

    /** 已响应计划数 */
    @Excel(name = "已响应计划数")
    private Integer responseNumber;

    /** 单位id */
    @Excel(name = "单位id")
    private Long unitId;

    /** 计划状态(0进行中,1已完成,2已审批,3已驳回) */
    @Excel(name = "计划状态(0进行中,1已完成,2已审批,3已驳回)")
    private String state;
    /**附件*/
    private List<ThoughtPlanFujian> planFujians;
    /**内容*/
    private List<ThoughtPlanFu> planFus;

    private String common;

    public String getCommon() {
        return common;
    }

    public void setCommon(String common) {
        this.common = common;
    }

    public List<ThoughtPlanFu> getPlanFus() {
        return planFus;
    }

    public void setPlanFus(List<ThoughtPlanFu> planFus) {
        this.planFus = planFus;
    }

    public List<ThoughtPlanFujian> getPlanFujians() {
        return planFujians;
    }


    public void setPlanFujians(List<ThoughtPlanFujian> planFujians) {
        this.planFujians = planFujians;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setPlanName(String planName) 
    {
        this.planName = planName;
    }

    public String getPlanName() 
    {
        return planName;
    }
    public void setPlanStartTime(Date planStartTime) 
    {
        this.planStartTime = planStartTime;
    }

    public Date getPlanStartTime() 
    {
        return planStartTime;
    }
    public void setPlanEndTime(Date planEndTime) 
    {
        this.planEndTime = planEndTime;
    }

    public Date getPlanEndTime() 
    {
        return planEndTime;
    }
    public void setPlanIntroduction(String planIntroduction) 
    {
        this.planIntroduction = planIntroduction;
    }

    public String getPlanIntroduction() 
    {
        return planIntroduction;
    }
    public void setFilePath(String filePath) 
    {
        this.filePath = filePath;
    }

    public String getFilePath() 
    {
        return filePath;
    }
    public void setFileName(String fileName) 
    {
        this.fileName = fileName;
    }

    public String getFileName() 
    {
        return fileName;
    }
    public void setCreateName(String createName) 
    {
        this.createName = createName;
    }

    public String getCreateName() 
    {
        return createName;
    }
    public void setCreateId(Long createId) 
    {
        this.createId = createId;
    }

    public Long getCreateId() 
    {
        return createId;
    }
    public void setPlanNumber(Integer planNumber) 
    {
        this.planNumber = planNumber;
    }

    public Integer getPlanNumber() 
    {
        return planNumber;
    }
    public void setResponseNumber(Integer responseNumber) 
    {
        this.responseNumber = responseNumber;
    }

    public Integer getResponseNumber() 
    {
        return responseNumber;
    }
    public void setUnitId(Long unitId) 
    {
        this.unitId = unitId;
    }

    public Long getUnitId() 
    {
        return unitId;
    }
    public void setState(String state) 
    {
        this.state = state;
    }

    public String getState() 
    {
        return state;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("planName", getPlanName())
            .append("planStartTime", getPlanStartTime())
            .append("planEndTime", getPlanEndTime())
            .append("planIntroduction", getPlanIntroduction())
            .append("filePath", getFilePath())
            .append("fileName", getFileName())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createName", getCreateName())
            .append("createId", getCreateId())
            .append("remark", getRemark())
            .append("planNumber", getPlanNumber())
            .append("responseNumber", getResponseNumber())
            .append("unitId", getUnitId())
            .append("state", getState())
            .toString();
    }
}
