package com.supervision.thought.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 月教育计划响应对象 thought_plan_responses
 * 
 * @author supervision
 * @date 2022-04-08
 */
public class ThoughtPlanResponses extends BaseEntity
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

    /** 审批状态 */
    @Excel(name = "审批状态")
    private Integer state;

    private String common;

    /**驳回原因*/
    private String reasons;

    private List<ThoughtPlanFu> planFus;


    //响应附件表
    private List<ThoughtPlanResponsefu> planResponsefus;

    //计划内容
    private List<ThoughtResponseContent> responseContents;

    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
    }

    public List<ThoughtPlanFu> getPlanFus() {
        return planFus;
    }

    public void setPlanFus(List<ThoughtPlanFu> planFus) {
        this.planFus = planFus;
    }

    public List<ThoughtResponseContent> getResponseContents() {
        return responseContents;
    }

    public void setResponseContents(List<ThoughtResponseContent> responseContents) {
        this.responseContents = responseContents;
    }

    public String getCommon() {
        return common;
    }

    public void setCommon(String common) {
        this.common = common;
    }

    public List<ThoughtPlanResponsefu> getPlanResponsefus() {
        return planResponsefus;
    }

    public void setPlanResponsefus(List<ThoughtPlanResponsefu> planResponsefus) {
        this.planResponsefus = planResponsefus;
    }

    //主表id
    private Long planId;

    private Long unitId;

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
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
    public void setState(Integer state) 
    {
        this.state = state;
    }

    public Integer getState() 
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
            .append("state", getState())
            .toString();
    }
}
