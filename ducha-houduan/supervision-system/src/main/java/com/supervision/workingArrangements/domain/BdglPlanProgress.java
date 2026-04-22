package com.supervision.workingArrangements.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 进度录入对象 bdgl_plan_progress
 * 
 * @author supervision
 * @date 2022-03-10
 */
public class BdglPlanProgress extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 工作计划 */
    @Excel(name = "工作计划")
    private Integer taskId;

    /** 工作计划名称 */
    @Excel(name = "工作计划名称")
    private String taskName;

    /** 计划完成状态 */
    @Excel(name = "计划完成状态")
    private Integer stauts;

    /** 附件 */
    @Excel(name = "附件")
    private String file;

    /** 完成时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "完成时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date accomplishTime;

    /** 录入人 */
    @Excel(name = "录入人")
    private Integer adminId;

    /** 执行人 */
    @Excel(name = "执行人")
    private String peopleName;

    /** 执行人id */
    @Excel(name = "执行人id")
    private Long peopleId;

    /** 任务进度 */
    @Excel(name = "任务进度")
    private Integer schedule;

    /**任务周期*/
    private String taskCycle;

    /** $column.columnComment */
    @Excel(name = "发布人")
    private String publisher;

    /** $column.columnComment */
    @Excel(name = "发布人id")
    private Long publisherId;

    /** $column.columnComment */
    @Excel(name = "发布单位名称")
    private String unitname;

    private String filename;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }

    public String getUnitname() {
        return unitname;
    }

    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }

    public String getTaskCycle() {
        return taskCycle;
    }

    public void setTaskCycle(String taskCycle) {
        this.taskCycle = taskCycle;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setTaskId(Integer taskId) 
    {
        this.taskId = taskId;
    }

    public Integer getTaskId() 
    {
        return taskId;
    }
    public void setTaskName(String taskName) 
    {
        this.taskName = taskName;
    }

    public String getTaskName() 
    {
        return taskName;
    }
    public void setStauts(Integer stauts) 
    {
        this.stauts = stauts;
    }

    public Integer getStauts() 
    {
        return stauts;
    }
    public void setFile(String file) 
    {
        this.file = file;
    }

    public String getFile() 
    {
        return file;
    }
    public void setAccomplishTime(Date accomplishTime) 
    {
        this.accomplishTime = accomplishTime;
    }

    public Date getAccomplishTime() 
    {
        return accomplishTime;
    }
    public void setAdminId(Integer adminId) 
    {
        this.adminId = adminId;
    }

    public Integer getAdminId() 
    {
        return adminId;
    }
    public void setPeopleName(String peopleName) 
    {
        this.peopleName = peopleName;
    }

    public String getPeopleName() 
    {
        return peopleName;
    }
    public void setPeopleId(Long peopleId) 
    {
        this.peopleId = peopleId;
    }

    public Long getPeopleId() 
    {
        return peopleId;
    }
    public void setSchedule(Integer schedule) 
    {
        this.schedule = schedule;
    }

    public Integer getSchedule() 
    {
        return schedule;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("taskId", getTaskId())
            .append("taskName", getTaskName())
            .append("stauts", getStauts())
            .append("file", getFile())
            .append("remark", getRemark())
            .append("accomplishTime", getAccomplishTime())
            .append("createtime", getCreatetime())
            .append("updatetime", getUpdatetime())
            .append("adminId", getAdminId())
            .append("peopleName", getPeopleName())
            .append("peopleId", getPeopleId())
            .append("schedule", getSchedule())
            .toString();
    }
}
