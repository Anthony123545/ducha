package com.supervision.workingArrangements.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 任务计划执行详情对象 bdgl_work_task_plan_detail
 * 
 * @author ruoyi
 * @date 2022-08-15
 */
public class BdglWorkTaskPlanDetail extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 任务计划详情ID */
    private Long id;

    /** 单位ID */
    @Excel(name = "单位ID")
    private Long unitId;

    /** 任务执行人 */
    @Excel(name = "任务执行人")
    private Long peopleId;

    /** 任务描述 */
    @Excel(name = "任务描述")
    private String taskDescription;

    /** 任务执行情况0.待审核1.未完成2.已完成3.驳回 */
    @Excel(name = "任务执行情况0.待审核1.未完成2.已完成3.驳回")
    private Long taskStatus;

    /** 任务ID */
    @Excel(name = "任务ID")
    private Long taskPlanId;

    /** 任务反馈时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "任务反馈时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date feedbackTime;

    /** 反馈内容 */
    @Excel(name = "反馈内容")
    private String feedbackDescription;
    
    /** 任务驳回反馈内容 */
    @Excel(name = "反馈内容")
    private String rejectContent;
    
    /** 反馈附件 */
    private List<BdglWorkTaskPlanAnner> annexList;
    
    public String getRejectContent() {
        return rejectContent;
    }
    
    public void setRejectContent(String rejectContent) {
        this.rejectContent = rejectContent;
    }
    
    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUnitId(Long unitId) 
    {
        this.unitId = unitId;
    }

    public Long getUnitId() 
    {
        return unitId;
    }
    
    public Long getPeopleId() {
        return peopleId;
    }
    
    public void setPeopleId(Long peopleId) {
        this.peopleId = peopleId;
    }
    
    public void setTaskDescription(String taskDescription)
    {
        this.taskDescription = taskDescription;
    }

    public String getTaskDescription() 
    {
        return taskDescription;
    }
    public void setTaskStatus(Long taskStatus) 
    {
        this.taskStatus = taskStatus;
    }

    public Long getTaskStatus() 
    {
        return taskStatus;
    }
    public void setTaskPlanId(Long taskPlanId) 
    {
        this.taskPlanId = taskPlanId;
    }

    public Long getTaskPlanId() 
    {
        return taskPlanId;
    }
    public void setFeedbackTime(Date feedbackTime) 
    {
        this.feedbackTime = feedbackTime;
    }

    public Date getFeedbackTime() 
    {
        return feedbackTime;
    }
    public void setFeedbackDescription(String feedbackDescription) 
    {
        this.feedbackDescription = feedbackDescription;
    }

    public String getFeedbackDescription() 
    {
        return feedbackDescription;
    }
    
    public List<BdglWorkTaskPlanAnner> getAnnexList() {
        return annexList;
    }
    
    public void setAnnexList(List<BdglWorkTaskPlanAnner> annexList) {
        this.annexList = annexList;
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("unitId", getUnitId())
            .append("peopleId", getPeopleId())
            .append("taskDescription", getTaskDescription())
            .append("taskStatus", getTaskStatus())
            .append("taskPlanId", getTaskPlanId())
            .append("feedbackTime", getFeedbackTime())
            .append("feedbackDescription", getFeedbackDescription())
            .toString();
    }
}
