package com.supervision.jobTask.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.supervision.common.annotation.Excel;
import com.supervision.jobTask.domain.JobTaskRecordExecutor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 任务进度vo对象
 */
public class JobTaskProgressVo implements Serializable {
    
    /** 任务总进度 */
    private Integer progressRate;
    
    /** 任务发布人 */
    private String publishPeople;
    
    /** 任务开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date workStartTime;
    
    /** 任务结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date workEndTime;
    
    
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
    
    /** 任务执行详情 */
    private List<JobTaskRecordExecutor> jobTaskRecordExecutorList;
    
    public Integer getProgressRate() {
        return progressRate;
    }
    
    public void setProgressRate(Integer progressRate) {
        this.progressRate = progressRate;
    }
    
    public String getPublishPeople() {
        return publishPeople;
    }
    
    public void setPublishPeople(String publishPeople) {
        this.publishPeople = publishPeople;
    }
    
    public Date getWorkStartTime() {
        return workStartTime;
    }
    
    public void setWorkStartTime(Date workStartTime) {
        this.workStartTime = workStartTime;
    }
    
    public Date getWorkEndTime() {
        return workEndTime;
    }
    
    public void setWorkEndTime(Date workEndTime) {
        this.workEndTime = workEndTime;
    }
    
    public String getWorkAnnexName() {
        return workAnnexName;
    }
    
    public void setWorkAnnexName(String workAnnexName) {
        this.workAnnexName = workAnnexName;
    }
    
    public String getWorkAnnex() {
        return workAnnex;
    }
    
    public void setWorkAnnex(String workAnnex) {
        this.workAnnex = workAnnex;
    }
    
    public String getWorkDetail() {
        return workDetail;
    }
    
    public void setWorkDetail(String workDetail) {
        this.workDetail = workDetail;
    }
    
    public String getWorkRemark() {
        return workRemark;
    }
    
    public void setWorkRemark(String workRemark) {
        this.workRemark = workRemark;
    }
    
    public List<JobTaskRecordExecutor> getJobTaskRecordExecutorList() {
        return jobTaskRecordExecutorList;
    }
    
    public void setJobTaskRecordExecutorList(List<JobTaskRecordExecutor> jobTaskRecordExecutorList) {
        this.jobTaskRecordExecutorList = jobTaskRecordExecutorList;
    }
}
