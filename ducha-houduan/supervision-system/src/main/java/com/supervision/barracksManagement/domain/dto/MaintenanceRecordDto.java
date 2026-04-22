package com.supervision.barracksManagement.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.supervision.common.annotation.Excel;

import java.io.Serializable;
import java.util.Date;

/**
 * 五小工维修记录导出对象
 */
public class MaintenanceRecordDto implements Serializable {
    
    /** 单位名称 */
    @Excel(name = "单位名称")
    private String unitName;
    
    /** 负责人 */
    @Excel(name = "负责人")
    private String personCharge;
    
    /** 发现时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发现时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date time;
    
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
    
    /** 问题 */
    @Excel(name = "问题")
    private String problem;
    
    @Excel(name = "备注",width = 50)
    private String remark;
    
    public String getUnitName() {
        return unitName;
    }
    
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
    
    public String getPersonCharge() {
        return personCharge;
    }
    
    public void setPersonCharge(String personCharge) {
        this.personCharge = personCharge;
    }
    
    public Date getTime() {
        return time;
    }
    
    public void setTime(Date time) {
        this.time = time;
    }
    
    public Date getRectificationTime() {
        return rectificationTime;
    }
    
    public void setRectificationTime(Date rectificationTime) {
        this.rectificationTime = rectificationTime;
    }
    
    public String getMaterialScience() {
        return materialScience;
    }
    
    public void setMaterialScience(String materialScience) {
        this.materialScience = materialScience;
    }
    
    public String getFunding() {
        return funding;
    }
    
    public void setFunding(String funding) {
        this.funding = funding;
    }
    
    public String getProblem() {
        return problem;
    }
    
    public void setProblem(String problem) {
        this.problem = problem;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
