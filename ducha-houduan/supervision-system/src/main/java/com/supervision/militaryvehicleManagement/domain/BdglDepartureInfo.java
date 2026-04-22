package com.supervision.militaryvehicleManagement.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 出车信息对象 bdgl_departure_info
 * 
 * @author supervision
 * @date 2022-03-19
 */
public class BdglDepartureInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 厂牌型号 */
    @Excel(name = "厂牌型号")
    private String vehiclemodel;

    /** 号码号牌 */
    @Excel(name = "号码号牌")
    private String vehiclenumber;

    /** 司机名称 */
    @Excel(name = "司机名称")
    private String driverName;

    /** 司机ID */
    @Excel(name = "司机ID")
    private Long driverId;

    /** 司机名称 */
    @Excel(name = "司机名称")
    private String driverPhone;

    /** 军车管理ID */
    @Excel(name = "军车管理ID")
    private Long bdglThebusId;

    /** 用车申请ID */
    @Excel(name = "用车申请ID")
    private Long shengqingId;
    
    /**
     * 用车单位-ID
     */
    private String carUnitId;
    
    public String getCarUnitId() {
        return carUnitId;
    }
    
    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setVehiclemodel(String vehiclemodel) 
    {
        this.vehiclemodel = vehiclemodel;
    }

    public String getVehiclemodel() 
    {
        return vehiclemodel;
    }
    public void setVehiclenumber(String vehiclenumber) 
    {
        this.vehiclenumber = vehiclenumber;
    }

    public String getVehiclenumber() 
    {
        return vehiclenumber;
    }
    public void setDriverName(String driverName) 
    {
        this.driverName = driverName;
    }

    public String getDriverName() 
    {
        return driverName;
    }
    public void setDriverId(Long driverId) 
    {
        this.driverId = driverId;
    }

    public Long getDriverId() 
    {
        return driverId;
    }
    public void setDriverPhone(String driverPhone) 
    {
        this.driverPhone = driverPhone;
    }

    public String getDriverPhone() 
    {
        return driverPhone;
    }
    public void setBdglThebusId(Long bdglThebusId) 
    {
        this.bdglThebusId = bdglThebusId;
    }

    public Long getBdglThebusId() 
    {
        return bdglThebusId;
    }
    public void setShengqingId(Long shengqingId) 
    {
        this.shengqingId = shengqingId;
    }

    public Long getShengqingId() 
    {
        return shengqingId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("vehiclemodel", getVehiclemodel())
            .append("vehiclenumber", getVehiclenumber())
            .append("driverName", getDriverName())
            .append("driverId", getDriverId())
            .append("driverPhone", getDriverPhone())
            .append("bdglThebusId", getBdglThebusId())
            .append("shengqingId", getShengqingId())
            .toString();
    }
}
