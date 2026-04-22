package com.supervision.workingArrangements.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 工作任务进度录入对象 bdgl_work_progress
 * 
 * @author supervision
 * @date 2022-03-10
 */
public class BdglWorkProgress extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 工作任务 */
    @Excel(name = "工作任务")
    private Integer taskId;

    /** 工作任务名称 */
    @Excel(name = "工作任务名称")
    private String taskName;

    /** 任务完成状态 */
    @Excel(name = "任务完成状态")
    private Integer stauts;

    /** 附件 */
    @Excel(name = "附件")
    private String file;

    /** $column.columnComment */
    @Excel(name = "附件")
    private String fileName;

    /** 完成时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "完成时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date accomplishTime;

    /** 录入人 */
    @Excel(name = "录入人")
    private Integer adminId;
    /** 任务执行人id */
    @Excel(name = "任务执行人id")
    private Long peopleId;

    /** 任务执行人姓名 */
    @Excel(name = "任务执行人姓名")
    private String peopleName;

    /** 任务进度 */
    @Excel(name = "任务进度")
    private Long schedule;

    /**任务周期*/
    private String  taskCycle;

    private String unitname;

    public String getUnitname() {
        return unitname;
    }

    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }

    /** 发布人id */
    @Excel(name = "发布人")
    private String publisher;

    /** 发布人id */
    @Excel(name = "发布人id")
    private Long publisherId;

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

    public String getTaskCycle() {
        return taskCycle;
    }

    public void setTaskCycle(String taskCycle) {
        this.taskCycle = taskCycle;
    }

    public Long getPeopleId() {
        return peopleId;
    }

    public void setPeopleId(Long peopleId) {
        this.peopleId = peopleId;
    }

    @Override
    public String getPeopleName() {
        return peopleName;
    }

    @Override
    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName;
    }

    public Long getSchedule() {
        return schedule;
    }

    public void setSchedule(Long schedule) {
        this.schedule = schedule;
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
    public void setFileName(String fileName) 
    {
        this.fileName = fileName;
    }

    public String getFileName() 
    {
        return fileName;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("taskId", getTaskId())
            .append("taskName", getTaskName())
            .append("stauts", getStauts())
            .append("file", getFile())
            .append("fileName", getFileName())
            .append("remark", getRemark())
            .append("accomplishTime", getAccomplishTime())
            .append("createtime", getCreatetime())
            .append("updatetime", getUpdatetime())
            .append("adminId", getAdminId())
            .toString();
    }
}
