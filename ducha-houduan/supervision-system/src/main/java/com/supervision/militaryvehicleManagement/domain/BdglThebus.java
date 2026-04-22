package com.supervision.militaryvehicleManagement.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 军车管理对象 bdgl_thebus
 * 
 * @author supervision
 * @date 2022-03-09
 */
public class BdglThebus extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 车牌号 */
    @Excel(name = "车牌号")
    private String vehiclenumber;

    /** 车辆型号 */
    @Excel(name = "车辆型号",dictType = "brand")
    private String vehiclemodel;

    /** 车辆颜色 */
    @Excel(name = "车辆颜色")
    private String vehiclecolor;

    /** 车位号 */
    @Excel(name = "车位号")
    private String parkingspacenumber;

    /** 联系方式 */
    @Excel(name = "联系方式")
    private String contactnumber;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    /** 创建人ID */
    //@Excel(name = "创建人ID")
    private Long adminId;

    /** 车辆在位情况1：在位，2：值班，3：出车，4待修 */
    @Excel(name = "车辆在位情况",dictType = "military")
    private String status;

    /** 所属单位 */
    //@Excel(name = "所属单位")
    private Long unitId;
    /** 所属单位名称 */
    @Excel(name = "所属单位")
    private String unitName;

    @Excel(name = "车辆负责人名字")
    private String peopleNames;


    /** 车辆负责人 */
    //@Excel(name = "车辆负责人")
    private Long peopleId;

    /** 发动机号 */
    @Excel(name = "发动机号")
    private String engineNumber;

    /** 车架号 */
    @Excel(name = "车架号")
    private String frameNumber;

    /** 车辆来源 */
    @Excel(name = "车辆来源")
    private String source;

    /** 出厂日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出厂日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date date1;

    /** ETC品牌 */
    @Excel(name = "ETC品牌")
    private String etcBrand;

    /** 车辆类别 */
    @Excel(name = "车辆类别")
    private String chelaingleibie;

    /** ETC编码 */
    @Excel(name = "ETC编码")
    private String etcNumber;

    /** 所在位置 */
    @Excel(name = "所在位置")
    private String position;

    /** 是否上牌 */
    @Excel(name = "是否上牌",dictType = "license")
    private String isListing;

    /** 固定座位数 */
    @Excel(name = "固定座位数")
    private Integer seatCount;
    
    /** 车辆用途 */
    private String carUse;
    
    /** 审核结果 */
    @Excel(name = "审核结果")
    private String shenhejieguo;


    private Integer orders;
    
    public String getCarUse() {
        return carUse;
    }
    
    public void setCarUse(String carUse) {
        this.carUse = carUse;
    }
    
    public Integer getOrders() {
        return orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }

    public String getPeopleNames() {
        return peopleNames;
    }

    public void setPeopleNames(String peopleNames) {
        this.peopleNames = peopleNames;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setVehiclenumber(String vehiclenumber)
    {
        this.vehiclenumber = vehiclenumber;
    }

    public String getVehiclenumber()
    {
        return vehiclenumber;
    }
    public void setVehiclemodel(String vehiclemodel)
    {
        this.vehiclemodel = vehiclemodel;
    }

    public String getVehiclemodel()
    {
        return vehiclemodel;
    }
    public void setVehiclecolor(String vehiclecolor)
    {
        this.vehiclecolor = vehiclecolor;
    }

    public String getVehiclecolor()
    {
        return vehiclecolor;
    }
    public void setParkingspacenumber(String parkingspacenumber)
    {
        this.parkingspacenumber = parkingspacenumber;
    }

    public String getParkingspacenumber()
    {
        return parkingspacenumber;
    }
    public void setContactnumber(String contactnumber)
    {
        this.contactnumber = contactnumber;
    }

    public String getContactnumber()
    {
        return contactnumber;
    }
    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
    }

    public String getRemarks()
    {
        return remarks;
    }
    public void setAdminId(Long adminId)
    {
        this.adminId = adminId;
    }

    public Long getAdminId()
    {
        return adminId;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setUnitId(Long unitId)
    {
        this.unitId = unitId;
    }

    public Long getUnitId()
    {
        return unitId;
    }
    public void setPeopleId(Long peopleId)
    {
        this.peopleId = peopleId;
    }

    public Long getPeopleId()
    {
        return peopleId;
    }
    public void setEngineNumber(String engineNumber)
    {
        this.engineNumber = engineNumber;
    }

    public String getEngineNumber()
    {
        return engineNumber;
    }
    public void setFrameNumber(String frameNumber)
    {
        this.frameNumber = frameNumber;
    }

    public String getFrameNumber()
    {
        return frameNumber;
    }
    public void setSource(String source)
    {
        this.source = source;
    }

    public String getSource()
    {
        return source;
    }
    public void setDate1(Date date1)
    {
        this.date1 = date1;
    }

    public Date getDate1()
    {
        return date1;
    }
    public void setEtcBrand(String etcBrand)
    {
        this.etcBrand = etcBrand;
    }

    public String getEtcBrand()
    {
        return etcBrand;
    }
    public void setChelaingleibie(String chelaingleibie)
    {
        this.chelaingleibie = chelaingleibie;
    }

    public String getChelaingleibie()
    {
        return chelaingleibie;
    }
    public void setEtcNumber(String etcNumber)
    {
        this.etcNumber = etcNumber;
    }

    public String getEtcNumber()
    {
        return etcNumber;
    }
    public void setPosition(String position)
    {
        this.position = position;
    }

    public String getPosition()
    {
        return position;
    }
    public void setIsListing(String isListing)
    {
        this.isListing = isListing;
    }

    public String getIsListing()
    {
        return isListing;
    }
    public void setSeatCount(Integer seatCount)
    {
        this.seatCount = seatCount;
    }

    public Integer getSeatCount()
    {
        return seatCount;
    }
    public void setUnitName(String unitName)
    {
        this.unitName = unitName;
    }

    public String getUnitName()
    {
        return unitName;
    }
    public void setShenhejieguo(String shenhejieguo)
    {
        this.shenhejieguo = shenhejieguo;
    }

    public String getShenhejieguo()
    {
        return shenhejieguo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("vehiclenumber", getVehiclenumber())
                .append("vehiclemodel", getVehiclemodel())
                .append("vehiclecolor", getVehiclecolor())
                .append("parkingspacenumber", getParkingspacenumber())
                .append("contactnumber", getContactnumber())
                .append("remarks", getRemarks())
                .append("adminId", getAdminId())
                .append("createtime", getCreatetime())
                .append("updatetime", getUpdatetime())
                .append("status", getStatus())
                .append("unitId", getUnitId())
                .append("peopleId", getPeopleId())
                .append("engineNumber", getEngineNumber())
                .append("frameNumber", getFrameNumber())
                .append("source", getSource())
                .append("date1", getDate1())
                .append("etcBrand", getEtcBrand())
                .append("chelaingleibie", getChelaingleibie())
                .append("etcNumber", getEtcNumber())
                .append("position", getPosition())
                .append("isListing", getIsListing())
                .append("seatCount", getSeatCount())
                .append("unitName", getUnitName())
                .append("shenhejieguo", getShenhejieguo())
                .toString();
    }

    private List<Long> idList;

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }
}
