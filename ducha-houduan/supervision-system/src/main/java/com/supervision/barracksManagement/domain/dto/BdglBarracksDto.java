package com.supervision.barracksManagement.domain.dto;

import com.supervision.common.annotation.Excel;

import java.io.Serializable;

/**
 * 营房档案导出对象
 */
public class BdglBarracksDto implements Serializable {
    /** 坐落号 */
    @Excel(name = "坐落号")
    private String number;
    
    /** 分栋号 */
    @Excel(name = "分栋号")
    private String number1;
    
    /** 分栋名称 */
    @Excel(name = "分栋名称")
    private String name;
    
    /** 营房用途 */
    @Excel(name = "营房用途")
    private String behoof;
    
    /** 利用现状 */
    @Excel(name = "利用现状")
    private String statusQuo;
    
    /** 建筑年代 */
    @Excel(name = "建筑年代")
    private String period;
    
    /** 建筑面积 */
    @Excel(name = "建筑面积")
    private String area;
    
    /**
     * 备注
     */
    @Excel(name = "备注",width = 40)
    private String remark;
    
    public String getNumber() {
        return number;
    }
    
    public void setNumber(String number) {
        this.number = number;
    }
    
    public String getNumber1() {
        return number1;
    }
    
    public void setNumber1(String number1) {
        this.number1 = number1;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getBehoof() {
        return behoof;
    }
    
    public void setBehoof(String behoof) {
        this.behoof = behoof;
    }
    
    public String getStatusQuo() {
        return statusQuo;
    }
    
    public void setStatusQuo(String statusQuo) {
        this.statusQuo = statusQuo;
    }
    
    public String getPeriod() {
        return period;
    }
    
    public void setPeriod(String period) {
        this.period = period;
    }
    
    public String getArea() {
        return area;
    }
    
    public void setArea(String area) {
        this.area = area;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
