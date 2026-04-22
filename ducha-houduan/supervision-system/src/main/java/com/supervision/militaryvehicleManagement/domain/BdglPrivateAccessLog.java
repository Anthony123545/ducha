package com.supervision.militaryvehicleManagement.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 私家车进出查询对象 bdgl_private_access_log
 * 
 * @author supervision
 * @date 2022-03-09
 */
public class BdglPrivateAccessLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 车牌号 */
    //@Excel(name = "车牌号")
    private Integer privateCarId;

    /** 车牌号 */
    @Excel(name = "车牌号")
    private String vehiclenumber;

    /** 车辆类别 */
    @Excel(name = "车辆类别")
    private String vehiclecategory;

    /** 车辆型号 */
    @Excel(name = "车辆型号")
    private String vehiclemodel;

    /** 车辆颜色 */
    @Excel(name = "车辆颜色")
    private String vehiclecolor;

    /** 车辆所有人 */
    //@Excel(name = "车辆所有人")
    private Integer peopleId;

    /** 车辆所有人name */
    @Excel(name = "车辆所有人name")
    private String peopleName;

    /** 联系方式 */
    @Excel(name = "联系方式")
    private String contactnumber;

    /** 进入时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "进入时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date jinrushiajin;

    /** $column.columnComment */
    @Excel(name = "进入时间")
    private Integer adminId;

    /** 离开时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "离开时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date likaishitian;

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }
    public void setPrivateCarId(Integer privateCarId)
    {
        this.privateCarId = privateCarId;
    }

    public Integer getPrivateCarId()
    {
        return privateCarId;
    }
    public void setVehiclenumber(String vehiclenumber)
    {
        this.vehiclenumber = vehiclenumber;
    }

    public String getVehiclenumber()
    {
        return vehiclenumber;
    }
    public void setVehiclecategory(String vehiclecategory)
    {
        this.vehiclecategory = vehiclecategory;
    }

    public String getVehiclecategory()
    {
        return vehiclecategory;
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

    public Integer getPeopleId() {
        return peopleId;
    }

    public void setPeopleId(Integer peopleId) {
        this.peopleId = peopleId;
    }

    public void setPeopleName(String peopleName)
    {
        this.peopleName = peopleName;
    }

    public String getPeopleName()
    {
        return peopleName;
    }
    public void setContactnumber(String contactnumber)
    {
        this.contactnumber = contactnumber;
    }

    public String getContactnumber()
    {
        return contactnumber;
    }
    public void setJinrushiajin(Date jinrushiajin)
    {
        this.jinrushiajin = jinrushiajin;
    }

    public Date getJinrushiajin()
    {
        return jinrushiajin;
    }
    public void setAdminId(Integer adminId)
    {
        this.adminId = adminId;
    }

    public Integer getAdminId()
    {
        return adminId;
    }
    public void setLikaishitian(Date likaishitian)
    {
        this.likaishitian = likaishitian;
    }

    public Date getLikaishitian()
    {
        return likaishitian;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("privateCarId", getPrivateCarId())
                .append("vehiclenumber", getVehiclenumber())
                .append("vehiclecategory", getVehiclecategory())
                .append("vehiclemodel", getVehiclemodel())
                .append("vehiclecolor", getVehiclecolor())
                .append("peopleId", getPeopleId())
                .append("peopleName", getPeopleName())
                .append("contactnumber", getContactnumber())
                .append("jinrushiajin", getJinrushiajin())
                .append("adminId", getAdminId())
                .append("likaishitian", getLikaishitian())
                .append("createtime", getCreatetime())
                .append("updatetime", getUpdatetime())
                .toString();
    }
}
