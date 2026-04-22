package com.supervision.grassrootsregistration.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 一周安排工作分对象 bdgl_weekwork_register
 * 
 * @author supervision
 * @date 2022-02-28
 */
public class BdglWeekworkRegister extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 星期 */
    @Excel(name = "星期")
    private String week;

    /** 时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date time;

    /** 时段 */
    @Excel(name = "时段")
    private String dateline;

    /** 内容 */
    @Excel(name = "内容")
    private String contents;

    /** 单位 */
    @Excel(name = "单位")
    private String unitId1;

    /** 参加人员 */
    @Excel(name = "参加人员")
    private String peopleIds;

    /** 地点 */
    @Excel(name = "地点")
    private String address;

    /** 时间段1 */
    @Excel(name = "时间段1")
    private String startTime;

    /** 时间段2 */
    @Excel(name = "时间段2")
    private String endTime;

    /** 主表ID */
    @Excel(name = "主表ID")
    private Integer weekworkId;

    /** 应到 */
    @Excel(name = "应到")
    private Integer shouldArrive;

    /** 实到 */
    @Excel(name = "实到")
    private Integer realTo;

    /** 到课率 */
    @Excel(name = "到课率")
    private String percentage;

    private String organizer;

    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    //排序字段
    private Integer sort;

    //单位id
    private Integer unitId;

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setWeek(String week) 
    {
        this.week = week;
    }

    public String getWeek() 
    {
        return week;
    }
    public void setTime(Date time) 
    {
        this.time = time;
    }

    public Date getTime() 
    {
        return time;
    }
    public void setDateline(String dateline) 
    {
        this.dateline = dateline;
    }

    public String getDateline() 
    {
        return dateline;
    }
    public void setContents(String contents) 
    {
        this.contents = contents;
    }

    public String getContents() 
    {
        return contents;
    }

    public String getUnitId1() {
        return unitId1;
    }

    public void setUnitId1(String unitId1) {
        this.unitId1 = unitId1;
    }

    public void setPeopleIds(String peopleIds)
    {
        this.peopleIds = peopleIds;
    }

    public String getPeopleIds() 
    {
        return peopleIds;
    }
    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }
    public void setStartTime(String startTime) 
    {
        this.startTime = startTime;
    }

    public String getStartTime() 
    {
        return startTime;
    }
    public void setEndTime(String endTime) 
    {
        this.endTime = endTime;
    }

    public String getEndTime() 
    {
        return endTime;
    }
    public void setWeekworkId(Integer weekworkId) 
    {
        this.weekworkId = weekworkId;
    }

    public Integer getWeekworkId() 
    {
        return weekworkId;
    }
    public void setShouldArrive(Integer shouldArrive) 
    {
        this.shouldArrive = shouldArrive;
    }

    public Integer getShouldArrive() 
    {
        return shouldArrive;
    }
    public void setRealTo(Integer realTo) 
    {
        this.realTo = realTo;
    }

    public Integer getRealTo() 
    {
        return realTo;
    }
    public void setPercentage(String percentage) 
    {
        this.percentage = percentage;
    }

    public String getPercentage() 
    {
        return percentage;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("week", getWeek())
            .append("time", getTime())
            .append("dateline", getDateline())
            .append("contents", getContents())
            .append("unitId1", getUnitId1())
            .append("peopleIds", getPeopleIds())
            .append("address", getAddress())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("weekworkId", getWeekworkId())
            .append("shouldArrive", getShouldArrive())
            .append("realTo", getRealTo())
            .append("percentage", getPercentage())
            .toString();
    }
}
