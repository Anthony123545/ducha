package com.supervision.jobTask.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.supervision.common.annotation.Excel;

import java.io.Serializable;
import java.util.Date;

/**
 * 日历展示
 * @author gaoqiang
 */
public class JobTaskRecordVo implements Serializable {

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

    /** 任务发布人 */
    @Excel(name = "任务发布人")
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
    @Excel(name = "任务优先级",dictType = "work_priority")
    private String workPriority;

    /** 任务执行人 */
    @Excel(name = "任务执行人")
    private String executorPeople;

    /** 任务进度 */
    @Excel(name = "任务进度",suffix = "%")
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
    @Excel(name = "反馈备注")
    private String workFeedbackRemark;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getWorkNum() {
        return workNum;
    }

    public void setWorkNum(String workNum) {
        this.workNum = workNum;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
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

    public String getWorkPriority() {
        return workPriority;
    }

    public void setWorkPriority(String workPriority) {
        this.workPriority = workPriority;
    }

    public String getExecutorPeople() {
        return executorPeople;
    }

    public void setExecutorPeople(String executorPeople) {
        this.executorPeople = executorPeople;
    }

    public Integer getWorkRate() {
        return workRate;
    }

    public void setWorkRate(Integer workRate) {
        this.workRate = workRate;
    }

    public Date getWorkFinishTime() {
        return workFinishTime;
    }

    public void setWorkFinishTime(Date workFinishTime) {
        this.workFinishTime = workFinishTime;
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

    public String getPublishPeople() {
        return publishPeople;
    }

    public void setPublishPeople(String publishPeople) {
        this.publishPeople = publishPeople;
    }

}
