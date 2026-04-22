package com.supervision.militaryvehicleManagement.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 私家车管理对象 bdgl_private_car
 * 
 * @author supervision
 * @date 2022-03-04
 */
public class BdglPrivateCar extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 车牌号 */
    @Excel(name = "车牌号")
    private String vehiclenumber;

    /** 车辆型号 */
    @Excel(name = "车辆型号")
    private String vehiclemodel;

    /** 车辆颜色 */
    @Excel(name = "车辆颜色")
    private String vehiclecolor;

    /** 车辆类别 */
    @Excel(name = "车辆类别",dictType = "privateCar")
    private String vehiclecategory;

    /** 车辆所有人 */
    @Excel(name = "车辆所有人")
    private Integer peopleId;

    /** 临时车辆所有人 */
    @Excel(name = "临时车辆所有人")
    private String peopleName;

    /** 联系方式 */
    @Excel(name = "联系方式")
    private String contactnumber;

    /** 证件类型 */
    @Excel(name = "证件类型",dictType = "documentType")
    private String documenttype;

    /** 证件号 */
    @Excel(name = "证件号")
    private String certificatenumber;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    /** 创建人 */
    //@Excel(name = "创建人")
    private Long adminId;

    /** 授权开始时间 */
    @Excel(name = "授权开始时间",dateFormat = "yyyy-MM-dd")
    private Date authStime;

    /** 授权结束时间 */
    @Excel(name = "授权结束时间",dateFormat = "yyyy-MM-dd")
    private Date authEtime;

    /** 开闸事假 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    //@Excel(name = "开闸事假", width = 30, dateFormat = "yyyy-MM-dd")
    private Date cutoffTime;

    /** 进入类型 */
    //@Excel(name = "进入类型")
    private String outType;

    public String getChelaingleibie() {
        return chelaingleibie;
    }

    public void setChelaingleibie(String chelaingleibie) {
        this.chelaingleibie = chelaingleibie;
    }

    private String chelaingleibie;

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

    private String state;

    private Long unitId;

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
    public void setVehiclecategory(String vehiclecategory)
    {
        this.vehiclecategory = vehiclecategory;
    }

    public String getVehiclecategory()
    {
        return vehiclecategory;
    }
    public void setPeopleId(Integer peopleId)
    {
        this.peopleId = peopleId;
    }

    public Integer getPeopleId()
    {
        return peopleId;
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
    public void setDocumenttype(String documenttype)
    {
        this.documenttype = documenttype;
    }

    public String getDocumenttype()
    {
        return documenttype;
    }
    public void setCertificatenumber(String certificatenumber)
    {
        this.certificatenumber = certificatenumber;
    }

    public String getCertificatenumber()
    {
        return certificatenumber;
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
    public void setAuthStime(Date authStime)
    {
        this.authStime = authStime;
    }

    public Date getAuthStime()
    {
        return authStime;
    }
    public void setAuthEtime(Date authEtime)
    {
        this.authEtime = authEtime;
    }

    public Date getAuthEtime()
    {
        return authEtime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("vehiclenumber", getVehiclenumber())
                .append("vehiclemodel", getVehiclemodel())
                .append("vehiclecolor", getVehiclecolor())
                .append("vehiclecategory", getVehiclecategory())
                .append("peopleId", getPeopleId())
                .append("peopleName", getPeopleName())
                .append("contactnumber", getContactnumber())
                .append("documenttype", getDocumenttype())
                .append("certificatenumber", getCertificatenumber())
                .append("remarks", getRemarks())
                .append("adminId", getAdminId())
                .append("createtime", getCreatetime())
                .append("updatetime", getUpdatetime())
                .append("authStime", getAuthStime())
                .append("authEtime", getAuthEtime())
                .toString();
    }
}
