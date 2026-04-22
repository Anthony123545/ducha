package com.supervision.barracksManagement.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 临时来队对象 temporary_checkin
 * 
 * @author supervision
 * @date 2022-03-07
 */
public class TemporaryCheckin extends BaseEntity
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

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date creatTime;

    /** 创建人id */
    @Excel(name = "创建人id")
    private Long creatId;

    /** 位置 */
    @Excel(name = "位置")
    private Integer position;

    /** 入住人姓名 */
    @Excel(name = "入住人姓名")
    private String occupant;

    /** 入住时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入住时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date entryTime;

    /** 退房时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "退房时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 创建时间 */
    @Excel(name = "创建时间")
    private String creatName;

    /**备注*/
    private String remark;

    /**位置2*/
    private Integer position2;

    private String common;


    public String getCommon() {
        return common;
    }

    public void setCommon(String common) {
        this.common = common;
    }


    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getPosition2() {
        return position2;
    }

    public void setPosition2(Integer position2) {
        this.position2 = position2;
    }

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }

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
    public void setCreatTime(Date creatTime) 
    {
        this.creatTime = creatTime;
    }

    public Date getCreatTime() 
    {
        return creatTime;
    }
    public void setCreatId(Long creatId) 
    {
        this.creatId = creatId;
    }

    public Long getCreatId() 
    {
        return creatId;
    }

    public void setOccupant(String occupant) 
    {
        this.occupant = occupant;
    }

    public String getOccupant() 
    {
        return occupant;
    }
    public void setEntryTime(Date entryTime) 
    {
        this.entryTime = entryTime;
    }

    public Date getEntryTime() 
    {
        return entryTime;
    }
    public void setEndTime(Date endTime) 
    {
        this.endTime = endTime;
    }

    public Date getEndTime() 
    {
        return endTime;
    }
    public void setCreatName(String creatName) 
    {
        this.creatName = creatName;
    }

    public String getCreatName() 
    {
        return creatName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("unitName", getUnitName())
            .append("unitId", getUnitId())
            .append("creatTime", getCreatTime())
            .append("creatId", getCreatId())
            .append("position", getPosition())
            .append("occupant", getOccupant())
            .append("entryTime", getEntryTime())
            .append("endTime", getEndTime())
            .append("creatName", getCreatName())
            .toString();
    }
}
