package com.supervision.grassrootsregistration.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 一周食谱对象 bdgl_cookbook
 * 
 * @author supervision
 * @date 2022-02-26
 */
public class BdglCookbook extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 食谱标题 */
    @Excel(name = "食谱标题")
    private String title;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 司务长 */
    @Excel(name = "司务长")
    private Integer messOfficer;

    /** 班长 */
    @Excel(name = "班长")
    private Integer peopleId;

    /** 食谱内容 */
    @Excel(name = "食谱内容")
    private String contents;

    /** 单位 */
    @Excel(name = "单位")
    private Integer unitId;
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createtime;
    /**修改时间*/
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatetime;

    /** 司务长姓名 */
    @Excel(name = "司务长姓名")
    private String messOfficerName;

    /** 班长姓名 */
    @Excel(name = "班长姓名")
    private String peopleName;

    /** 单位名称 */
    @Excel(name = "单位名称")
    private String unitName;

    public String getMessOfficerName() {
        return messOfficerName;
    }

    public void setMessOfficerName(String messOfficerName) {
        this.messOfficerName = messOfficerName;
    }

    public String getPeopleName() {
        return peopleName;
    }

    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    private List<BdglCookbookRegistration> cookbookRegistrations;

    public List<BdglCookbookRegistration> getCookbookRegistrations() {
        return cookbookRegistrations;
    }

    public void setCookbookRegistrations(List<BdglCookbookRegistration> cookbookRegistrations) {
        this.cookbookRegistrations = cookbookRegistrations;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
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
    public void setMessOfficer(Integer messOfficer) 
    {
        this.messOfficer = messOfficer;
    }

    public Integer getMessOfficer() 
    {
        return messOfficer;
    }
    public void setPeopleId(Integer peopleId) 
    {
        this.peopleId = peopleId;
    }

    public Integer getPeopleId() 
    {
        return peopleId;
    }
    public void setContents(String contents) 
    {
        this.contents = contents;
    }

    public String getContents() 
    {
        return contents;
    }
    public void setUnitId(Integer unitId) 
    {
        this.unitId = unitId;
    }

    public Integer getUnitId() 
    {
        return unitId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("title", getTitle())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("messOfficer", getMessOfficer())
            .append("peopleId", getPeopleId())
            .append("contents", getContents())
            .append("unitId", getUnitId())
            .append("createtime", getCreatetime())
            .append("updatetime", getUpdatetime())
            .toString();
    }
}
