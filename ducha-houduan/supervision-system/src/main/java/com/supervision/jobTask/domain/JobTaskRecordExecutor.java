package com.supervision.jobTask.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.supervision.common.annotation.Excel;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

/**
 * 任务与执行人关系对象 job_task_record_executor
 *
 * @author gaoqiang
 * @date 2022-12-27
 */
public class JobTaskRecordExecutor implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /** 任务ID */
    @Excel(name = "任务ID")
    private Integer workId;
    
    /** 任务执行人 */
    @Excel(name = "任务执行人")
    private String executorPeople;
    
    /** 任务进度 */
    @Excel(name = "任务进度")
    private Integer workRate;
    
    /** 任务完成时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "任务完成时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date workFinishTime;
    
    /** 任务反馈附件名称 */
    private String workFeedbackFileName;
    
    /** 任务反馈附件路径  */
    private String workFeedbackFileUrl;
    
    /** 反馈备注 */
    private String workFeedbackRemark;
    
    public void setWorkId(Integer workId)
    {
        this.workId = workId;
    }
    
    public Integer getWorkId()
    {
        return workId;
    }
    public void setExecutorPeople(String executorPeople)
    {
        this.executorPeople = executorPeople;
    }
    
    public String getExecutorPeople()
    {
        return executorPeople;
    }
    public void setWorkRate(Integer workRate)
    {
        this.workRate = workRate;
    }
    
    public Integer getWorkRate()
    {
        return workRate;
    }
    public void setWorkFinishTime(Date workFinishTime)
    {
        this.workFinishTime = workFinishTime;
    }
    
    public Date getWorkFinishTime()
    {
        return workFinishTime;
    }
    
    public String getWorkFeedbackFileName() {
        return workFeedbackFileName;
    }
    
    public void setWorkFeedbackFileName(String workFeedbackFileName) {
        this.workFeedbackFileName = workFeedbackFileName;
    }
    
    public String getWorkFeedbackFileUrl() {
        return workFeedbackFileUrl;
    }
    
    public void setWorkFeedbackFileUrl(String workFeedbackFileUrl) {
        this.workFeedbackFileUrl = workFeedbackFileUrl;
    }
    
    public String getWorkFeedbackRemark() {
        return workFeedbackRemark;
    }
    
    public void setWorkFeedbackRemark(String workFeedbackRemark) {
        this.workFeedbackRemark = workFeedbackRemark;
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("workId", getWorkId())
                .append("executorPeople", getExecutorPeople())
                .append("workRate", getWorkRate())
                .append("workFinishTime", getWorkFinishTime())
                .toString();
    }
}
