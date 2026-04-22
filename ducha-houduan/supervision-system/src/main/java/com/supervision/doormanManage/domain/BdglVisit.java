package com.supervision.doormanManage.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 人员来访登记对象 bdgl_visit
 *
 * @author supervision
 * @date 2022-03-23
 */
public class BdglVisit extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 到访单位 */
    @Excel(name = "到访单位")
    private Integer unitId;

    /** 接待人 */
    @Excel(name = "接待人")
    private Integer peopleId;

    /** 到访人员姓名 */
    @Excel(name = "到访人员姓名")
    private String name;

    /** 到访时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "到访时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date arriveTime;

    /** 离队时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "离队时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 录入人 */
    @Excel(name = "录入人")
    private Integer adminId;

    /** 来访原因 */
    @Excel(name = "来访原因")
    private String contents;

    /** 到访车牌号 */
    @Excel(name = "到访车牌号")
    private String numberPlate;

    /** 单位名称 */
    @Excel(name = "单位名称")
    private String unitName;

    /** 接待人姓名 */
    @Excel(name = "接待人姓名")
    private String peopleName;

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }
    public void setUnitId(Integer unitId)
    {
        this.unitId = unitId;
    }

    public Integer getUnitId()
    {
        return unitId;
    }
    public void setPeopleId(Integer peopleId)
    {
        this.peopleId = peopleId;
    }

    public Integer getPeopleId()
    {
        return peopleId;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setArriveTime(Date arriveTime)
    {
        this.arriveTime = arriveTime;
    }

    public Date getArriveTime()
    {
        return arriveTime;
    }
    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    public Date getEndTime()
    {
        return endTime;
    }
    public void setAdminId(Integer adminId)
    {
        this.adminId = adminId;
    }

    public Integer getAdminId()
    {
        return adminId;
    }
    public void setContents(String contents)
    {
        this.contents = contents;
    }

    public String getContents()
    {
        return contents;
    }
    public void setNumberPlate(String numberPlate)
    {
        this.numberPlate = numberPlate;
    }

    public String getNumberPlate()
    {
        return numberPlate;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("unitId", getUnitId())
                .append("peopleId", getPeopleId())
                .append("name", getName())
                .append("arriveTime", getArriveTime())
                .append("endTime", getEndTime())
                .append("adminId", getAdminId())
                .append("contents", getContents())
                .append("createtime", getCreatetime())
                .append("updatetime", getUpdatetime())
                .append("numberPlate", getNumberPlate())
                .append("unitName", getUnitName())
                .append("peopleName", getPeopleName())
                .toString();
    }
}