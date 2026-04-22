package com.supervision.barracksManagement.domain;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 单身干部宿舍对象 single_dormitory
 * 
 * @author gaoqiang
 * @date 2022-11-19
 */
public class SingleDormitory  implements Serializable {
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 单位名称 */
    @Excel(name = "单位名称")
    private String unitName;

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

    /** 位置 */
    @Excel(name = "位置")
    private String position;
    /**
     * 备注
     */
    @Excel(name = "备注",width = 40)
    private String remark;
    
    /**
     * 创建者
     */
    private String createBy;
    
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    
    /**
     * 更新者
     */
    private String updateBy;
    
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    
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
    public void setPosition(String position)
    {
        this.position = position;
    }

    public String getPosition()
    {
        return position;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public String getCreateBy() {
        return createBy;
    }
    
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
    
    public Date getCreateTime() {
        return createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    public String getUpdateBy() {
        return updateBy;
    }
    
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
    
    public Date getUpdateTime() {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("unitName", getUnitName())
            .append("occupant", getOccupant())
            .append("entryTime", getEntryTime())
            .append("endTime", getEndTime())
            .append("position", getPosition())
            .append("remark", getRemark())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
