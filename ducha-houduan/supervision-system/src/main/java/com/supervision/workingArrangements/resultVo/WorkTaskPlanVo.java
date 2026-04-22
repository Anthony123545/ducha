package com.supervision.workingArrangements.resultVo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.supervision.common.annotation.Excel;
import com.supervision.workingArrangements.domain.BdglWorkTaskPlanAnner;
import com.supervision.workingArrangements.domain.BdglWorkTaskPlanDetail;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 任务计划基本信息视图对象 bdgl_work_task_plan
 *
 * @author ruoyi
 * @date 2022-08-15
 */
public class WorkTaskPlanVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 任务计划ID */
    private Long id;

    /** 任务名称 */
    @Excel(name = "任务名称")
    private String taskName;

    /** 任务开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "任务开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 任务结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "任务结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 任务备注 */
    @Excel(name = "任务备注")
    private String remarkInfo;

    /** 任务计划执行详情信息 */
    private List<BdglWorkTaskPlanDetail> bdglWorkTaskPlanDetailList;

    /** 任务计划执行详情信息 */
    private BdglWorkTaskPlanDetail planDetail;

    /** 任务总数 */
    private Integer taskCount;

    /** 当前进度 */
    private String taskProgress;

    /** 执行单位数 */
    private Integer unitCount;

    /** 任务附件 */
    private List<BdglWorkTaskPlanAnner> annexList;

    /** 任务发布人 */
    private String createBy;

    /** 任务修改人 */
    private String updateBy;

    /** 任务完成状态 1.未完成。2.已完成 */
    private Long taskStatus;


    public BdglWorkTaskPlanDetail getPlanDetail() {
        return planDetail;
    }

    public void setPlanDetail(BdglWorkTaskPlanDetail planDetail) {
        this.planDetail = planDetail;
    }

    public Long getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Long taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getRemarkInfo() {
        return remarkInfo;
    }

    public void setRemarkInfo(String remarkInfo) {
        this.remarkInfo = remarkInfo;
    }

    public List<BdglWorkTaskPlanDetail> getBdglWorkTaskPlanDetailList() {
        return bdglWorkTaskPlanDetailList;
    }

    public void setBdglWorkTaskPlanDetailList(List<BdglWorkTaskPlanDetail> bdglWorkTaskPlanDetailList) {
        this.bdglWorkTaskPlanDetailList = bdglWorkTaskPlanDetailList;
    }

    public Integer getTaskCount() {
        return taskCount;
    }

    public void setTaskCount(Integer taskCount) {
        this.taskCount = taskCount;
    }

    public String getTaskProgress() {
        return taskProgress;
    }

    public void setTaskProgress(String taskProgress) {
        this.taskProgress = taskProgress;
    }

    public Integer getUnitCount() {
        return unitCount;
    }

    public void setUnitCount(Integer unitCount) {
        this.unitCount = unitCount;
    }

    public List<BdglWorkTaskPlanAnner> getAnnexList() {
        return annexList;
    }

    public void setAnnexList(List<BdglWorkTaskPlanAnner> annexList) {
        this.annexList = annexList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("taskName", getTaskName())
                .append("startTime", getStartTime())
                .append("endTime", getEndTime())
                .append("remarkInfo", getRemarkInfo())
                .append("taskCount", getTaskCount())
                .append("taskProgress", getTaskProgress())
                .append("unitCount", getUnitCount())
                .append("annexList", getAnnexList())
                .append("bdglWorkTaskPlanDetailList", getBdglWorkTaskPlanDetailList())
                .toString();
    }
}
