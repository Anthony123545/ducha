package com.supervision.barracksManagement.domain.dto;

import com.supervision.common.annotation.Excel;

import java.io.Serializable;

/**
 * 单身干部宿舍功能导出
 */
public class BdglSingleCadreDormDto implements Serializable {
    /** 姓名 */
    @Excel(name = "姓名")
    private String xingMing;
    
    /** 房间号 */
    @Excel(name = "房间号")
    private String roomNumber;
    
    /** 建筑面积 */
    @Excel(name = "建筑面积")
    private String area;
    
    /** 使用面积 */
    @Excel(name = "使用面积")
    private String usageArea;
    
    /** 物业费标准 */
    @Excel(name = "物业标准")
    private String propertyFeeStandard;
    
    /** 房租标准 */
    @Excel(name = "房租标准")
    private String rentStandard;
    
    /** 水费 */
    @Excel(name = "水费")
    private String waterBil;
    
    /** 月房租 */
    @Excel(name = "月房租")
    private String monthlyRent;
    
    /** 月物业费 */
    @Excel(name = "月物业费")
    private String monthlyPropertyFee;
    
    /** 总金额 */
    @Excel(name = "总金额")
    private String totalAmount;
    
    @Excel(name = "备注",width = 40)
    private String remark;
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public String getXingMing() {
        return xingMing;
    }
    
    public void setXingMing(String xingMing) {
        this.xingMing = xingMing;
    }
    
    public String getRoomNumber() {
        return roomNumber;
    }
    
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
    
    public String getArea() {
        return area;
    }
    
    public void setArea(String area) {
        this.area = area;
    }
    
    public String getUsageArea() {
        return usageArea;
    }
    
    public void setUsageArea(String usageArea) {
        this.usageArea = usageArea;
    }
    
    public String getPropertyFeeStandard() {
        return propertyFeeStandard;
    }
    
    public void setPropertyFeeStandard(String propertyFeeStandard) {
        this.propertyFeeStandard = propertyFeeStandard;
    }
    
    public String getRentStandard() {
        return rentStandard;
    }
    
    public void setRentStandard(String rentStandard) {
        this.rentStandard = rentStandard;
    }
    
    public String getWaterBil() {
        return waterBil;
    }
    
    public void setWaterBil(String waterBil) {
        this.waterBil = waterBil;
    }
    
    public String getMonthlyRent() {
        return monthlyRent;
    }
    
    public void setMonthlyRent(String monthlyRent) {
        this.monthlyRent = monthlyRent;
    }
    
    public String getMonthlyPropertyFee() {
        return monthlyPropertyFee;
    }
    
    public void setMonthlyPropertyFee(String monthlyPropertyFee) {
        this.monthlyPropertyFee = monthlyPropertyFee;
    }
    
    public String getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
