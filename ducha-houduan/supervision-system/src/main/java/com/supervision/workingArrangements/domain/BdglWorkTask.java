package com.supervision.workingArrangements.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;


/**
 * 工作任务对象 bdgl_work_task
 * 
 * @author supervision
 * @date 2022-03-05
 */
public class BdglWorkTask extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;
    /** 编号 */
    @Excel(name = "编号")
    private String number;

    /** 任务名称 */
    @Excel(name = "任务名称")
    private String name;

    @Excel(name = "发布人")
    private String username;

    /** 单位名称 */
    @Excel(name = "执行单位")
    private String unitName;


    /** 执行人姓名 */
    @Excel(name = "执行人姓名")
    private String peopleName;


    /** 任务类型 */
    @Excel(name = "任务类型" ,dictType = "work_type")
    private String type;

    /** 任务描述 */
    @Excel(name = "任务描述")
    private String contents;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "任务开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /** 任务结束时间 */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Excel(name = "任务结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    @Excel(name = "备注")
    private String remark;


    /** 执行人 */
    //@Excel(name = "执行人")
    private String peopleId;





    /** 任务开始时间 */
    //@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")


    /** 任务附件 */
    //@Excel(name = "任务附件")
    private String file;

    /** 任务附件文件名 */
    //@Excel(name = "任务附件文件名")
    private String fileName;



    /** 发布单位id */
    //@Excel(name = "发布单位id")
    private Integer adminId;

    /** 单位 */
    //@Excel(name = "单位")
    private String unitId;
    /** 单位集合 */
    //@Excel(name = "单位")
    private String unitIds;

    public String getUnitIds() {
        return unitIds;
    }

    public void setUnitIds(String unitIds) {
        this.unitIds = unitIds;
    }

    /** 发布单位名称 */
    //@Excel(name = "发布单位名称")
    private String unitName2;

    private String common;
    /**任务进度*/
    private String taskProgress;

    /**登录用户id*/
    private Long userId;


    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private BdglWorkProgress workProgress;

    private List<BdglWorkProgress> workProgresses;

    public List<BdglWorkProgress> getWorkProgresses() {
        return workProgresses;
    }

    public void setWorkProgresses(List<BdglWorkProgress> workProgresses) {
        this.workProgresses = workProgresses;
    }

    public BdglWorkProgress getWorkProgress() {
        return workProgress;
    }

    public void setWorkProgress(BdglWorkProgress workProgress) {
        this.workProgress = workProgress;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTaskProgress() {
        return taskProgress;
    }

    public void setTaskProgress(String taskProgress) {
        this.taskProgress = taskProgress;
    }

    public String getCommon() {
        return common;
    }

    public void setCommon(String common) {
        this.common = common;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setPeopleId(String peopleId) 
    {
        this.peopleId = peopleId;
    }

    public String getPeopleId() 
    {
        return peopleId;
    }
    public void setNumber(String number) 
    {
        this.number = number;
    }

    public String getNumber() 
    {
        return number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }

    public Date getStartTime() 
    {
        return startTime;
    }
    public void setEndTime(Date endTime) 
    {
        this.endTime = endTime;
    }

    public Date getEndTime() 
    {
        return endTime;
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
    public void setContents(String contents) 
    {
        this.contents = contents;
    }

    public String getContents() 
    {
        return contents;
    }
    public void setAdminId(Integer adminId) 
    {
        this.adminId = adminId;
    }

    public Integer getAdminId() 
    {
        return adminId;
    }

    public void setUnitName(String unitName) 
    {
        this.unitName = unitName;
    }

    public String getUnitName() 
    {
        return unitName;
    }
    public void setPeopleName(String peopleName) 
    {
        this.peopleName = peopleName;
    }

    public String getPeopleName() 
    {
        return peopleName;
    }
    public void setUnitName2(String unitName2) 
    {
        this.unitName2 = unitName2;
    }

    public String getUnitId() {
        return unitId;
    }

    public void setUnitId(String unitId) {
        this.unitId = unitId;
    }

    public String getUnitName2()
    {
        return unitName2;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("peopleId", getPeopleId())
            .append("number", getNumber())
            .append("type", getType())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("file", getFile())
            .append("fileName", getFileName())
            .append("remark", getRemark())
            .append("contents", getContents())
            .append("adminId", getAdminId())
            .append("unitId", getUnitId())
            .append("unitName", getUnitName())
            .append("createtime", getCreatetime())
            .append("updatetime", getUpdatetime())
            .append("peopleName", getPeopleName())
            .append("unitName2", getUnitName2())
            .toString();
    }
}
