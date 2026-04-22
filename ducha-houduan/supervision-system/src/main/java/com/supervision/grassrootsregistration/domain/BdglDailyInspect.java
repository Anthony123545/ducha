package com.supervision.grassrootsregistration.domain;

import java.sql.Time;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 查铺查哨对象 bdgl_daily_inspect
 *
 * @author supervision
 * @date 2022-02-28
 */
public class BdglDailyInspect extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 检查人id */
    @Excel(name = "检查人id")
    private Integer peopleId;

    /** $column.columnComment */
    @Excel(name = "检查人id")
    private Integer diaryId;

    /** 检查人名称 */
    @Excel(name = "检查人名称")
    private String peopleName;

    /** 哨兵id */
    @Excel(name = "哨兵id")
    private Integer sentryId;

    /** 哨兵名称 */
    @Excel(name = "哨兵名称")
    private String sentryName;

    /** 领班员id */
    @Excel(name = "领班员id")
    private Integer foremanId;

    /** 领班员名称 */
    @Excel(name = "领班员名称")
    private String foremanName;

    /** 被检查单位id */
    @Excel(name = "被检查单位id")
    private Integer unitId;

    /** 被检查单位名称 */
    @Excel(name = "被检查单位名称")
    private String testName;

    /** 检查情况 */
    @Excel(name = "检查情况")
    private String dialogue;

    /** 预留字段 */
    @Excel(name = "预留字段")
    private String field1;


    /** 检查时间 */
    @DateTimeFormat(pattern = "HH:mm:ss")
    //@Excel(name = "检查时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Time examineDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createtime;
    /**修改时间*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatetime;

    /** $column.columnComment */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    @Excel(name = "检查时间")
    private String contents;

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
    public void setPeopleName(String peopleName)
    {
        this.peopleName = peopleName;
    }

    public String getPeopleName()
    {
        return peopleName;
    }
    public void setSentryId(Integer sentryId)
    {
        this.sentryId = sentryId;
    }

    public Integer getSentryId()
    {
        return sentryId;
    }
    public void setSentryName(String sentryName)
    {
        this.sentryName = sentryName;
    }

    public String getSentryName()
    {
        return sentryName;
    }
    public void setForemanId(Integer foremanId)
    {
        this.foremanId = foremanId;
    }

    public Integer getForemanId()
    {
        return foremanId;
    }
    public void setForemanName(String foremanName)
    {
        this.foremanName = foremanName;
    }

    public String getForemanName()
    {
        return foremanName;
    }
    public void setUnitId(Integer unitId)
    {
        this.unitId = unitId;
    }

    public Integer getUnitId()
    {
        return unitId;
    }
    public void setTestName(String testName)
    {
        this.testName = testName;
    }

    public String getTestName()
    {
        return testName;
    }
    public void setDialogue(String dialogue)
    {
        this.dialogue = dialogue;
    }

    public String getDialogue()
    {
        return dialogue;
    }
    public void setField1(String field1)
    {
        this.field1 = field1;
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

    public String getField1()
    {
        return field1;
    }

    public Time getExamineDate() {
        return examineDate;
    }

    public void setExamineDate(Time examineDate) {
        this.examineDate = examineDate;
    }

    public void setContents(String contents)
    {
        this.contents = contents;
    }

    public String getContents()
    {
        return contents;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("peopleId", getPeopleId())
            .append("diaryId", getDiaryId())
            .append("peopleName", getPeopleName())
            .append("sentryId", getSentryId())
            .append("sentryName", getSentryName())
            .append("foremanId", getForemanId())
            .append("foremanName", getForemanName())
            .append("unitId", getUnitId())
            .append("testName", getTestName())
            .append("dialogue", getDialogue())
            .append("createtime", getCreatetime())
            .append("updatetime", getUpdatetime())
            .append("field1", getField1())
            .append("examineDate", getExamineDate())
            .append("contents", getContents())
            .toString();
    }

    private List<Integer> diaryIdList;

    public List<Integer> getDiaryIdList() {
        return diaryIdList;
    }

    public void setDiaryIdList(List<Integer> diaryIdList) {
        this.diaryIdList = diaryIdList;
    }
}
