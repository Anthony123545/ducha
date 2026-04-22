package com.supervision.barracksManagement.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 五小工维修记录对象 maintenance_record
 * 
 * @author supervision
 * @date 2022-05-20
 */
public class MaintenanceRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 单位名称 */
    @Excel(name = "单位名称")
    private String unitName;

    /** 单位id */
    @Excel(name = "单位id")
    private Long unitId;

    /** 问题 */
    @Excel(name = "问题")
    private String problem;

    /** 发现时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发现时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date time;

    /** 负责人 */
    @Excel(name = "负责人")
    private String personCharge;

    /** 整改时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "整改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date rectificationTime;

    /** 材料 */
    @Excel(name = "材料")
    private String materialScience;

    /** 经费情况 */
    @Excel(name = "经费情况")
    private String funding;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
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
    public void setProblem(String problem) 
    {
        this.problem = problem;
    }

    public String getProblem() 
    {
        return problem;
    }
    public void setTime(Date time) 
    {
        this.time = time;
    }

    public Date getTime() 
    {
        return time;
    }
    public void setPersonCharge(String personCharge) 
    {
        this.personCharge = personCharge;
    }

    public String getPersonCharge() 
    {
        return personCharge;
    }
    public void setRectificationTime(Date rectificationTime) 
    {
        this.rectificationTime = rectificationTime;
    }

    public Date getRectificationTime() 
    {
        return rectificationTime;
    }
    public void setMaterialScience(String materialScience) 
    {
        this.materialScience = materialScience;
    }

    public String getMaterialScience() 
    {
        return materialScience;
    }
    public void setFunding(String funding) 
    {
        this.funding = funding;
    }

    public String getFunding() 
    {
        return funding;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("unitName", getUnitName())
            .append("unitId", getUnitId())
            .append("problem", getProblem())
            .append("time", getTime())
            .append("personCharge", getPersonCharge())
            .append("rectificationTime", getRectificationTime())
            .append("materialScience", getMaterialScience())
            .append("remark", getRemark())
            .append("funding", getFunding())
            .append("createTime", getCreateTime())
            .toString();
    }
}