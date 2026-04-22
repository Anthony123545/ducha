package com.supervision.grassrootsregistration.domain;

import java.sql.Time;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 临时来队亲属对象 bdgl_diary_kinsfolk
 * 
 * @author supervision
 * @date 2022-02-28
 */
public class BdglDiaryKinsfolk extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 军人姓名 */
    @Excel(name = "军人姓名")
    private Integer peopleId;

    /** $column.columnComment */
    @Excel(name = "军人姓名")
    private Integer diaryId;

    /** 家属姓名 */
    @Excel(name = "家属姓名")
    private String name;

    /** 关系 */
    @Excel(name = "关系")
    private String relation;

    /** 来队时间 */
    @DateTimeFormat(pattern = "HH:mm:ss")
    @Excel(name = "来队时间", width = 30, dateFormat = "HH:mm:ss")
    private Time comeDate;

    /** 离队时间 */
    @DateTimeFormat(pattern = "HH:mm:ss")
    @Excel(name = "离队时间", width = 30, dateFormat = "HH:mm:ss")
    private Time leaveDate;

    /** 单位 */
    @Excel(name = "单位")
    private Integer unitId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createtime;
    /**修改时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatetime;
    /** 军人姓名 */
    @Excel(name = "军人姓名")
    private String peopleName;

    /** 单位名称 */
    @Excel(name = "单位名称")
    private String unitName;


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
    public void setPeopleId(Integer peopleId) 
    {
        this.peopleId = peopleId;
    }

    public Integer getPeopleId() 
    {
        return peopleId;
    }
    public void setDiaryId(Integer diaryId) 
    {
        this.diaryId = diaryId;
    }

    public Integer getDiaryId() 
    {
        return diaryId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setRelation(String relation) 
    {
        this.relation = relation;
    }

    public Time getComeDate() {
        return comeDate;
    }

    public void setComeDate(Time comeDate) {
        this.comeDate = comeDate;
    }

    public String getRelation() {
        return relation;
    }

    public void setLeaveDate(Time leaveDate) {
        this.leaveDate = leaveDate;
    }

    public Date getLeaveDate()
    {
        return leaveDate;
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
            .append("peopleId", getPeopleId())
            .append("diaryId", getDiaryId())
            .append("name", getName())
            .append("relation", getRelation())
            .append("comeDate", getComeDate())
            .append("leaveDate", getLeaveDate())
            .append("createtime", getCreatetime())
            .append("updatetime", getUpdatetime())
            .append("unitId", getUnitId())
            .toString();
    }
}
