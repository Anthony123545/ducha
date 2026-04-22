package com.supervision.militaryvehicleManagement.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 军车进出查询对象 bdgl_thebus_access_log
 * 
 * @author supervision
 * @date 2022-03-09
 */
public class BdglThebusAccessLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 车牌号 */
    @Excel(name = "车牌号")
    private Integer thebusId;

    /** 车牌号 */
    @Excel(name = "车牌号")
    private String vehiclenumber;

    /** 车辆型号 */
    @Excel(name = "车辆型号")
    private String vehiclemodel;

    /** 车位号 */
    @Excel(name = "车位号")
    private String parkingspacenumber;

    /** 车辆颜色 */
    @Excel(name = "车辆颜色")
    private String vehiclecolor;

    /** 人员单位 */
    @Excel(name = "人员单位")
    private Integer unitId;

    /** 人员姓名 */
    @Excel(name = "人员姓名")
    private Integer driverId;

    /** 联系方式 */
    @Excel(name = "联系方式")
    private String contactnumber;

    /** 人员姓名 */
    @Excel(name = "人员姓名")
    private String driverName;

    /**
     * 单位名称
     */
    @Excel(name = "单位名称")
    private String unitName;

    /** 外出时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "外出时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date waichushijian;

    /** $column.columnComment */
    @Excel(name = "外出时间")
    private Integer adminId;

    /** 返回时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "返回时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date fanhuishijian;

    /** 开闸事假 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开闸事假", width = 30, dateFormat = "yyyy-MM-dd")
    private Date cutoffTime;

    /** 进入类型 */
    @Excel(name = "进入类型")
    private String outType;

    public Date getCutoffTime() {
        return cutoffTime;
    }

    public void setCutoffTime(Date cutoffTime) {
        this.cutoffTime = cutoffTime;
    }

    public String getOutType() {
        return outType;
    }

    public void setOutType(String outType) {
        this.outType = outType;
    }

    @Override
    public String getUnitName() {
        return unitName;
    }

    @Override
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }
    public void setThebusId(Integer thebusId)
    {
        this.thebusId = thebusId;
    }

    public Integer getThebusId()
    {
        return thebusId;
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
    public void setParkingspacenumber(String parkingspacenumber)
    {
        this.parkingspacenumber = parkingspacenumber;
    }

    public String getParkingspacenumber()
    {
        return parkingspacenumber;
    }
    public void setVehiclecolor(String vehiclecolor)
    {
        this.vehiclecolor = vehiclecolor;
    }

    public String getVehiclecolor()
    {
        return vehiclecolor;
    }
    public void setUnitId(Integer unitId)
    {
        this.unitId = unitId;
    }

    public Integer getUnitId()
    {
        return unitId;
    }
    public void setDriverId(Integer driverId)
    {
        this.driverId = driverId;
    }

    public Integer getDriverId()
    {
        return driverId;
    }
    public void setContactnumber(String contactnumber)
    {
        this.contactnumber = contactnumber;
    }

    public String getContactnumber()
    {
        return contactnumber;
    }
    public void setDriverName(String driverName)
    {
        this.driverName = driverName;
    }

    public String getDriverName()
    {
        return driverName;
    }
    public void setWaichushijian(Date waichushijian)
    {
        this.waichushijian = waichushijian;
    }

    public Date getWaichushijian()
    {
        return waichushijian;
    }
    public void setAdminId(Integer adminId)
    {
        this.adminId = adminId;
    }

    public Integer getAdminId()
    {
        return adminId;
    }
    public void setFanhuishijian(Date fanhuishijian)
    {
        this.fanhuishijian = fanhuishijian;
    }

    public Date getFanhuishijian()
    {
        return fanhuishijian;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("thebusId", getThebusId())
                .append("vehiclenumber", getVehiclenumber())
                .append("vehiclemodel", getVehiclemodel())
                .append("parkingspacenumber", getParkingspacenumber())
                .append("vehiclecolor", getVehiclecolor())
                .append("unitId", getUnitId())
                .append("driverId", getDriverId())
                .append("contactnumber", getContactnumber())
                .append("driverName", getDriverName())
                .append("waichushijian", getWaichushijian())
                .append("adminId", getAdminId())
                .append("fanhuishijian", getFanhuishijian())
                .append("createtime", getCreatetime())
                .append("updatetime", getUpdatetime())
                .toString();
    }
}
