package com.supervision.barracksManagement.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 公寓房档案对象 bdgl_flat
 * 
 * @author supervision
 * @date 2022-03-02
 */
public class BdglFlat extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

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

    /** 楼房层数 */
    @Excel(name = "楼房层数")
    private String tier;

    /** 房间号 */
    @Excel(name = "房间号")
    private String roomNumber;

    /** 单元号 */
    @Excel(name = "单元号")
    private String element;

    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createtime;
    /**修改时间*/
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatetime;

    /** 姓名 */
    @Excel(name = "姓名")
    private String xingMing;

    /** 使用面积 */
    @Excel(name = "使用面积")
    private String usageArea;

    /** 物业费标准 */
    @Excel(name = "物业费标准")
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

    public String getXingMing() {
        return xingMing;
    }

    public void setXingMing(String xingMing) {
        this.xingMing = xingMing;
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

    public String getRentStandard() {
        return rentStandard;
    }

    public String getWaterBil() {
        return waterBil;
    }

    public String getMonthlyRent() {
        return monthlyRent;
    }

    public String getMonthlyPropertyFee() {
        return monthlyPropertyFee;
    }

    public String getTotalAmount() {
        return totalAmount;
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
    public void setNumber(String number) 
    {
        this.number = number;
    }

    public String getNumber() 
    {
        return number;
    }
    public void setNumber1(String number1) 
    {
        this.number1 = number1;
    }

    public String getNumber1() 
    {
        return number1;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setBehoof(String behoof) 
    {
        this.behoof = behoof;
    }

    public String getBehoof() 
    {
        return behoof;
    }
    public void setStatusQuo(String statusQuo) 
    {
        this.statusQuo = statusQuo;
    }

    public String getStatusQuo() 
    {
        return statusQuo;
    }
    public void setPeriod(String period) 
    {
        this.period = period;
    }

    public String getPeriod() 
    {
        return period;
    }
    public void setArea(String area) 
    {
        this.area = area;
    }

    public String getArea() 
    {
        return area;
    }
    public void setTier(String tier) 
    {
        this.tier = tier;
    }

    public String getTier() 
    {
        return tier;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setPropertyFeeStandard(String propertyFeeStandard) {
        this.propertyFeeStandard = propertyFeeStandard;
    }

    public void setRentStandard(String rentStandard) {
        this.rentStandard = rentStandard;
    }

    public void setWaterBil(String waterBil) {
        this.waterBil = waterBil;
    }

    public void setMonthlyRent(String monthlyRent) {
        this.monthlyRent = monthlyRent;
    }

    public void setMonthlyPropertyFee(String monthlyPropertyFee) {
        this.monthlyPropertyFee = monthlyPropertyFee;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setElement(String element)
    {
        this.element = element;
    }

    public String getElement() 
    {
        return element;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("number", getNumber())
            .append("number1", getNumber1())
            .append("name", getName())
            .append("behoof", getBehoof())
            .append("statusQuo", getStatusQuo())
            .append("period", getPeriod())
            .append("area", getArea())
            .append("remark", getRemark())
            .append("createtime", getCreatetime())
            .append("updatetime", getUpdatetime())
            .append("tier", getTier())
            .append("roomNumber", getRoomNumber())
            .append("element", getElement())
            .toString();
    }
}
