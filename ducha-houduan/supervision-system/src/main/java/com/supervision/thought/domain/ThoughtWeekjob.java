package com.supervision.thought.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 周工作对象 thought_weekjob
 * 
 * @author supervision
 * @date 2022-04-06
 */
public class ThoughtWeekjob extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date creatTime;

    /** 创建人姓名 */
    @Excel(name = "创建人姓名")
    private String creatName;

    /** 创建人id */
    @Excel(name = "创建人id")
    private Long creatId;

    /** 单位名称 */
    @Excel(name = "单位名称")
    private String unitName;

    /** 单位id */
    @Excel(name = "单位id")
    private Long unitId;

    /** 值班领导 */
    @Excel(name = "值班领导")
    private String dutyLeader;

    /** 值班领导id */
    @Excel(name = "值班领导id")
    private Long dutyLeaderId;

    /** 电话 */
    @Excel(name = "电话")
    private String phone;

    /** 军事长官  */
    @Excel(name = "军事长官 ")
    private String militaryOfficer;

    /** 军事长官id */
    @Excel(name = "军事长官id")
    private String militaryOfficerId;

    /** 政治长官 */
    @Excel(name = "政治长官")
    private String politicalOfficer;

    /** 政治长官id */
    @Excel(name = "政治长官id")
    private Long politicalOfficerId;

    /** 工作标题 */
    @Excel(name = "工作标题")
    private String jobTitle;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    private List<ThoughtWeekjobFu> thoughtWeekjobFuList;

    public List<ThoughtWeekjobFu> getThoughtWeekjobFuList() {
        return thoughtWeekjobFuList;
    }

    public void setThoughtWeekjobFuList(List<ThoughtWeekjobFu> thoughtWeekjobFuList) {
        this.thoughtWeekjobFuList = thoughtWeekjobFuList;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCreatTime(Date creatTime) 
    {
        this.creatTime = creatTime;
    }

    public Date getCreatTime() 
    {
        return creatTime;
    }
    public void setCreatName(String creatName) 
    {
        this.creatName = creatName;
    }

    public String getCreatName() 
    {
        return creatName;
    }
    public void setCreatId(Long creatId) 
    {
        this.creatId = creatId;
    }

    public Long getCreatId() 
    {
        return creatId;
    }
    public void setUnitName(String unitName) 
    {
        this.unitName = unitName;
    }

    public String getUnitName() 
    {
        return unitName;
    }
    public void setUnitId(Long unitId) 
    {
        this.unitId = unitId;
    }

    public Long getUnitId() 
    {
        return unitId;
    }
    public void setDutyLeader(String dutyLeader) 
    {
        this.dutyLeader = dutyLeader;
    }

    public String getDutyLeader() 
    {
        return dutyLeader;
    }
    public void setDutyLeaderId(Long dutyLeaderId) 
    {
        this.dutyLeaderId = dutyLeaderId;
    }

    public Long getDutyLeaderId() 
    {
        return dutyLeaderId;
    }
    public void setPhone(String phone) 
    {
        this.phone = phone;
    }

    public String getPhone() 
    {
        return phone;
    }
    public void setMilitaryOfficer(String militaryOfficer) 
    {
        this.militaryOfficer = militaryOfficer;
    }

    public String getMilitaryOfficer() 
    {
        return militaryOfficer;
    }
    public void setMilitaryOfficerId(String militaryOfficerId) 
    {
        this.militaryOfficerId = militaryOfficerId;
    }

    public String getMilitaryOfficerId() 
    {
        return militaryOfficerId;
    }
    public void setPoliticalOfficer(String politicalOfficer) 
    {
        this.politicalOfficer = politicalOfficer;
    }

    public String getPoliticalOfficer() 
    {
        return politicalOfficer;
    }

    public Long getPoliticalOfficerId() {
        return politicalOfficerId;
    }

    public void setPoliticalOfficerId(Long politicalOfficerId) {
        this.politicalOfficerId = politicalOfficerId;
    }

    public void setJobTitle(String jobTitle)
    {
        this.jobTitle = jobTitle;
    }

    public String getJobTitle() 
    {
        return jobTitle;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("creatTime", getCreatTime())
            .append("updateTime", getUpdateTime())
            .append("creatName", getCreatName())
            .append("creatId", getCreatId())
            .append("remark", getRemark())
            .append("unitName", getUnitName())
            .append("unitId", getUnitId())
            .append("dutyLeader", getDutyLeader())
            .append("dutyLeaderId", getDutyLeaderId())
            .append("phone", getPhone())
            .append("militaryOfficer", getMilitaryOfficer())
            .append("militaryOfficerId", getMilitaryOfficerId())
            .append("politicalOfficer", getPoliticalOfficer())
            .append("political officerId", getPoliticalOfficerId())
            .append("jobTitle", getJobTitle())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .toString();
    }
}
