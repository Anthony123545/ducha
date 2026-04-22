package com.supervision.jobTask.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;

/**
 * 工作任务计划对象 job_task_record
 * 
 * @author ruoyi
 * @date 2022-12-27
 */
public class JobTaskRecord implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 任务ID */
    private Integer id;

    /** 任务名称 */
    @Excel(name = "任务名称")
    private String workName;

    /** 任务编号 */
    @Excel(name = "任务编号")
    private String workNum;

    /** 任务类型 */
    @Excel(name = "任务类型")
    private String workType;

    /** 任务发布单位 */
    private String publishUnit;

    /** 任务发布人 */
    private String publishPeople;
    
    /** 任务附件名称 */
    private String workAnnexName;
    
    /** 任务附件 */
    private String workAnnex;

    /** 任务描述 */
    @Excel(name = "任务描述")
    private String workDetail;

    /** 任务备注 */
    @Excel(name = "任务备注")
    private String workRemark;

    /** 任务开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "任务开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date workStartTime;

    /** 任务结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "任务结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date workEndTime;

    /** 任务优先级-0紧急,1高,2中，3低 */
    @Excel(name = "任务优先级-0紧急,1高,2中，3低")
    private String workPriority;
    
    /**
     * 任务执行单位
     */
    private List<String> executorUnit;
    
    /**
     * 任务执行人
     */
    private List<String> executorPeople;
    
    /**
     * 创建者
     */
    private String createBy;
    
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    
    public List<String> getExecutorUnit() {
        return executorUnit;
    }
    
    public void setExecutorUnit(List<String> executorUnit) {
        this.executorUnit = executorUnit;
    }
    
    public String getCreateBy() {
        return createBy;
    }
    
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public List<String> getExecutorPeople() {
        return executorPeople;
    }
    
    public void setExecutorPeople(List<String> executorPeople) {
        this.executorPeople = executorPeople;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setWorkName(String workName) 
    {
        this.workName = workName;
    }

    public String getWorkName() 
    {
        return workName;
    }
    public void setWorkNum(String workNum) 
    {
        this.workNum = workNum;
    }

    public String getWorkNum() 
    {
        return workNum;
    }
    public void setWorkType(String workType) 
    {
        this.workType = workType;
    }

    public String getWorkType() 
    {
        return workType;
    }
    public void setPublishUnit(String publishUnit) 
    {
        this.publishUnit = publishUnit;
    }

    public String getPublishUnit() 
    {
        return publishUnit;
    }
    public void setPublishPeople(String publishPeople) 
    {
        this.publishPeople = publishPeople;
    }

    public String getPublishPeople() 
    {
        return publishPeople;
    }
    public void setWorkAnnex(String workAnnex) 
    {
        this.workAnnex = workAnnex;
    }

    public String getWorkAnnex() 
    {
        return workAnnex;
    }
    public void setWorkDetail(String workDetail) 
    {
        this.workDetail = workDetail;
    }

    public String getWorkDetail() 
    {
        return workDetail;
    }
    public void setWorkRemark(String workRemark) 
    {
        this.workRemark = workRemark;
    }

    public String getWorkRemark() 
    {
        return workRemark;
    }
    public void setWorkStartTime(Date workStartTime) 
    {
        this.workStartTime = workStartTime;
    }

    public Date getWorkStartTime() 
    {
        return workStartTime;
    }
    public void setWorkEndTime(Date workEndTime) 
    {
        this.workEndTime = workEndTime;
    }

    public Date getWorkEndTime() 
    {
        return workEndTime;
    }
    public void setWorkPriority(String workPriority) 
    {
        this.workPriority = workPriority;
    }

    public String getWorkPriority() 
    {
        return workPriority;
    }
    
    public String getWorkAnnexName() {
        return workAnnexName;
    }
    
    public void setWorkAnnexName(String workAnnexName) {
        this.workAnnexName = workAnnexName;
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("workName", getWorkName())
            .append("workNum", getWorkNum())
            .append("workType", getWorkType())
            .append("publishUnit", getPublishUnit())
            .append("publishPeople", getPublishPeople())
            .append("workAnnex", getWorkAnnex())
            .append("workDetail", getWorkDetail())
            .append("workRemark", getWorkRemark())
            .append("workStartTime", getWorkStartTime())
            .append("workEndTime", getWorkEndTime())
            .append("workPriority", getWorkPriority())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .toString();
    }
}
