package com.supervision.barracksManagement.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.supervision.common.annotation.Excel;

import java.io.Serializable;
import java.util.Date;

/**
 * 临时来队导出对象
 */
public class TemporaryCheckinDto implements Serializable {
    /** 位置 */
    @Excel(name = "位置")
    private Integer position;
    
    /** 单位名称 */
    @Excel(name = "入住单位")
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
    
    /**备注*/
    @Excel(name = "备注",width = 40)
    private String remark;
    
    public Integer getPosition() {
        return position;
    }
    
    public void setPosition(Integer position) {
        this.position = position;
    }
    
    public String getUnitName() {
        return unitName;
    }
    
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
    
    public String getOccupant() {
        return occupant;
    }
    
    public void setOccupant(String occupant) {
        this.occupant = occupant;
    }
    
    public Date getEntryTime() {
        return entryTime;
    }
    
    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }
    
    public Date getEndTime() {
        return endTime;
    }
    
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
