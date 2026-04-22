package com.supervision.militaryvehicleManagement.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * 用车申请对象 bdgl_thebus_apply
 *
 * @author supervision
 * @date 2022-03-04
 */
public class BdglThebusApply extends BaseEntity
{

    public String getPepelonamed() {
        return pepelonamed;
    }

    public void setPepelonamed(String pepelonamed) {
        this.pepelonamed = pepelonamed;
    }

    /** $column.columnComment */


    private Long id;

    /** 车型 */
    @Excel(name = "车型")
    private String model;

    public String getModelLabel() {
        return modelLabel;
    }

    public void setModelLabel(String modelLabel) {
        this.modelLabel = modelLabel;
    }

    private String modelLabel;

    /** 车型 */
    @Excel(name = "车辆类别")
    private String vehicleType;

    /** 单位 */
    @Excel(name = "单位")
    private Integer unitId;

    /** 人员id */
    @Excel(name = "人员id")
    private Integer peopleId;
    /**
     * 人员名称
     */
    @Excel(name = "人员名称")
    private String peopleName;
    /**
     * 带车人员名称
     */
    @Excel(name = "带车人名称")
    private  String pepelonamed;
    /** $column.columnComment */
    @Excel(name = "汽车id")
    private String thebusId;

    /** 联系方式 */
    @Excel(name = "联系方式")
    private String contact;

    /** 外出事由 */
    @Excel(name = "外出事由")
        private String cause;

    /** 用车开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "用车开始时间")
    private Date authStime;

    /** 用车结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "用车结束时间")
    private Date authEtime;

    /** 审批模板 */
    @Excel(name = "审批模板id")
    private Integer thebusThemplateId;

    /** 一级审批人 */
    @Excel(name = "一级审批人id")
    private Integer people1Id;

    /** 一级审批状态 */
    @Excel(name = "一级审批状态")
    private String people1State;

    /** 一级审批人姓名 */
    @Excel(name = "一级审批人姓名")
    private String people1Name;

    /** 一级审批备注 */
    @Excel(name = "一级审批备注")
    private String people1Remark;

    /** 一级审批时间 */
    @Excel(name = "一级审批时间")
    private Date people1Authtime;

    /** 二级审批人 */
    @Excel(name = "二级审批人id")
    private Integer people2Id;

    /** 二级审批状态 */
    @Excel(name = "二级审批状态")
    private String people2State;

    /** 整体审批状态 */
    @Excel(name = "整体审批状态")
    private String status;

    /** 二级审批备注 */
    @Excel(name = "二级审批备注")
    private String people2Remark;

    /** 二级审批时间 */
    @Excel(name = "二级审批时间")
    private Date people2Authtime;

    /** 出车状态 */
    @Excel(name = "出车状态")
    private String outstatus;

    /** 二级审批人姓名 */
    @Excel(name = "二级审批人姓名")
    private String people2Name;

    /** 操作人 */
    @Excel(name = "操作人id")
    private Integer adminId;

    /** 三级审批人 */
    @Excel(name = "三级审批人id")
    private Integer people3Id;

    /** 三级审批状态 */
    @Excel(name = "三级审批状态")
    private String people3State;

    /** 车辆司机 */
    @Excel(name = "车辆司机")
    private String driver;

    /** 三级审批备注 */
    @Excel(name = "三级审批备注")
    private String people3Remark;

    /** 车辆联系方式 */
    @Excel(name = "车辆联系方式")
    private String driverContact;

    /** 三级审批时间 */
    @Excel(name = "三级审批时间")
    private Date people3Authtime;

    /** 三级审批人姓名 */
    @Excel(name = "三级审批人姓名")
    private String people3Name;

    /** 人数 */
    @Excel(name = "人数")
    private Integer peopleCount;

    /** 营级审批人 */
    @Excel(name = "营级审批人")
    private Integer unitPeopleId;

    /** 营级审批状态 */
    @Excel(name = "营级审批状态")
    private String unitPeopleState;

    /** 营级审批备注 */
    @Excel(name = "营级审批备注")
    private String unitPeopleRemark;

    /** 初审审批状态 */
    @Excel(name = "初审审批状态")
    private String chushenState;

    /** 初审审批备注 */
    @Excel(name = "初审审批备注")
    private String chushenRemark;

    /** 出车路线 */
    @Excel(name = "出车路线")
    private String route;

    /** 出车时间 */
    @Excel(name = "出车时间")
    private Date outtime;

    /** 汽车名称 */
    @Excel(name = "汽车名称")
    private String carNames;

    /** 汽车id */
    @Excel(name = "汽车车牌号")
    private String carIds;

    /** 营级审批人姓名 */
    @Excel(name = "营级审批人姓名")
    private String unitPeopleName;

    private String common;

    private String battaliontime;

    public String getBattaliontime() {
        return battaliontime;
    }

    public void setBattaliontime(String battaliontime) {
        this.battaliontime = battaliontime;
    }

    public String getCommon() {
        return common;
    }

    public void setCommon(String common) {
        this.common = common;
    }

    /** 车辆型号 */
    private String vehiclecolor;

    public String getVehiclecolor() {
        return vehiclecolor;
    }

    public void setVehiclecolor(String vehiclecolor) {
        this.vehiclecolor = vehiclecolor;
    }
    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    /**
     * 是否通过
     */
    private String isTongguo;

    /**
     * 当前人员是否可以操作
     */
    private String isCaoZuo;

    /**
     * 是否特殊人员
     */
    private String isTeShu;

    private String shenpijiedian;

    /**
     * 营级特殊审批原因
     */
    private String yjtsYuanyin;

    /**
     * 初审特殊审批原因
     */
    private String cstsYuanyin;

    /**
     * 一级特殊审批原因
     */
    private String ojtsYuanyin;

    /**
     * 二级特殊审批原因
     */
    private String tjtsYuanyin;

    /**
     * 三级特殊审批原因
     */
    private String  sjtsYuanyin;


    /**
     * 单位名称
     */
    private String unitName;

    /**
     * 营级操作人
     */
    private String yingjicaozuoren;

    /**
     * 初审操作人
     */
    private String chushenyuan;

    /**
     * 查询状态
     */
    private String chaxunType;

    /**
     * 出车信息
     */
    private List<BdglDepartureInfo> bdglDepartureInfos;

    /**
     * 出车原因
     */
    private String bdglDepartureYuanyin;

    private String isTeshuYongChe;

    private  String placeState;

    /**
     * 用车单位-ID
     */
    private String carUnitId;

    /**
     * 备注
     */
    private String remarkInfo;

    private Date createtime;

    private Date updatetime;

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

    public String getRemarkInfo() {
        return remarkInfo;
    }

    public void setRemarkInfo(String remarkInfo) {
        this.remarkInfo = remarkInfo;
    }

    public String getCarUnitId() {
        return carUnitId;
    }

    public void setCarUnitId(String carUnitId) {
        this.carUnitId = carUnitId;
    }


    public String getPlaceState() {
        return placeState;
    }

    public void setPlaceState(String placeState) {
        this.placeState = placeState;
    }

    public String getIsTeshuYongChe() {
        return isTeshuYongChe;
    }

    public void setIsTeshuYongChe(String isTeshuYongChe) {
        this.isTeshuYongChe = isTeshuYongChe;
    }

    public String getBdglDepartureYuanyin() {
        return bdglDepartureYuanyin;
    }

    public void setBdglDepartureYuanyin(String bdglDepartureYuanyin) {
        this.bdglDepartureYuanyin = bdglDepartureYuanyin;
    }

    public String getChaxunType() {
        return chaxunType;
    }

    public void setChaxunType(String chaxunType) {
        this.chaxunType = chaxunType;
    }

    public String getYingjicaozuoren() {
        return yingjicaozuoren;
    }

    public void setYingjicaozuoren(String yingjicaozuoren) {
        this.yingjicaozuoren = yingjicaozuoren;
    }

    public String getChushenyuan() {
        return chushenyuan;
    }

    public void setChushenyuan(String chushenyuan) {
        this.chushenyuan = chushenyuan;
    }

    public String getYjtsYuanyin() {
        return yjtsYuanyin;
    }

    public void setYjtsYuanyin(String yjtsYuanyin) {
        this.yjtsYuanyin = yjtsYuanyin;
    }

    public String getCstsYuanyin() {
        return cstsYuanyin;
    }

    public void setCstsYuanyin(String cstsYuanyin) {
        this.cstsYuanyin = cstsYuanyin;
    }

    @Override
    public String getPeopleName() {
        return peopleName;
    }

    @Override
    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName;
    }

    @Override
    public String getUnitName() {
        return unitName;
    }

    @Override
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getOjtsYuanyin() {
        return ojtsYuanyin;
    }

    public void setOjtsYuanyin(String ojtsYuanyin) {
        this.ojtsYuanyin = ojtsYuanyin;
    }

    public String getTjtsYuanyin() {
        return tjtsYuanyin;
    }

    public void setTjtsYuanyin(String tjtsYuanyin) {
        this.tjtsYuanyin = tjtsYuanyin;
    }

    public String getSjtsYuanyin() {
        return sjtsYuanyin;
    }

    public void setSjtsYuanyin(String sjtsYuanyin) {
        this.sjtsYuanyin = sjtsYuanyin;
    }


    public String getShenpijiedian() {
        return shenpijiedian;
    }

    public void setShenpijiedian(String shenpijiedian) {
        this.shenpijiedian = shenpijiedian;
    }

    public List<BdglDepartureInfo> getBdglDepartureInfos() {
        return bdglDepartureInfos;
    }

    public void setBdglDepartureInfos(List<BdglDepartureInfo> bdglDepartureInfos) {
        this.bdglDepartureInfos = bdglDepartureInfos;
    }

    public String getIsTeShu() {
        return isTeShu;
    }

    public void setIsTeShu(String isTeShu) {
        this.isTeShu = isTeShu;
    }

    public String getIsCaoZuo() {
        return isCaoZuo;
    }

    public void setIsCaoZuo(String isCaoZuo) {
        this.isCaoZuo = isCaoZuo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getPeopleId() {
        return peopleId;
    }

    public void setPeopleId(Integer peopleId) {
        this.peopleId = peopleId;
    }

    public String getThebusId() {
        return thebusId;
    }

    public void setThebusId(String thebusId) {
        this.thebusId = thebusId;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public Date getAuthStime() {
        return authStime;
    }

    public void setAuthStime(Date authStime) {
        this.authStime = authStime;
    }

    public Date getAuthEtime() {
        return authEtime;
    }

    public void setAuthEtime(Date authEtime) {
        this.authEtime = authEtime;
    }

    public Integer getThebusThemplateId() {
        return thebusThemplateId;
    }

    public void setThebusThemplateId(Integer thebusThemplateId) {
        this.thebusThemplateId = thebusThemplateId;
    }

    public Integer getpeople1Id() {
        return people1Id;
    }

    public void setpeople1Id(Integer people1Id) {
        this.people1Id = people1Id;
    }

    public String getpeople1State() {
        return people1State;
    }

    public void setpeople1State(String people1State) {
        this.people1State = people1State;
    }

    public String getpeople1Name() {
        return people1Name;
    }

    public void setpeople1Name(String people1Name) {
        this.people1Name = people1Name;
    }

    public String getpeople1Remark() {
        return people1Remark;
    }

    public void setpeople1Remark(String people1Remark) {
        this.people1Remark = people1Remark;
    }

    public Date getpeople1Authtime() {
        return people1Authtime;
    }

    public void setpeople1Authtime(Date people1Authtime) {
        this.people1Authtime = people1Authtime;
    }

    public Integer getpeople2Id() {
        return people2Id;
    }

    public void setpeople2Id(Integer people2Id) {
        this.people2Id = people2Id;
    }

    public String getpeople2State() {
        return people2State;
    }

    public void setpeople2State(String people2State) {
        this.people2State = people2State;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getpeople2Remark() {
        return people2Remark;
    }

    public void setpeople2Remark(String people2Remark) {
        this.people2Remark = people2Remark;
    }

    public Date getpeople2Authtime() {
        return people2Authtime;
    }

    public Date setpeople2Authtime(Date people2Authtime) {
        this.people2Authtime = people2Authtime;
        return people2Authtime;
    }

    public String getOutstatus() {
        return outstatus;
    }

    public void setOutstatus(String outstatus) {
        this.outstatus = outstatus;
    }

    public String getpeople2Name() {
        return people2Name;
    }

    public void setpeople2Name(String people2Name) {
        this.people2Name = people2Name;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Integer getpeople3Id() {
        return people3Id;
    }

    public void setpeople3Id(Integer people3Id) {
        this.people3Id = people3Id;
    }

    public String getpeople3State() {
        return people3State;
    }

    public void setpeople3State(String people3State) {
        this.people3State = people3State;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getpeople3Remark() {
        return people3Remark;
    }

    public void setpeople3Remark(String people3Remark) {
        this.people3Remark = people3Remark;
    }

    public String getDriverContact() {
        return driverContact;
    }

    public void setDriverContact(String driverContact) {
        this.driverContact = driverContact;
    }

    public Date getpeople3Authtime() {
        return people3Authtime;
    }

    public void setpeople3Authtime(Date people3Authtime) {
        this.people3Authtime = people3Authtime;
    }

    public String getpeople3Name() {
        return people3Name;
    }

    public void setpeople3Name(String people3Name) {
        this.people3Name = people3Name;
    }

    public Integer getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(Integer peopleCount) {
        this.peopleCount = peopleCount;
    }

    public Integer getUnitPeopleId() {
        return unitPeopleId;
    }

    public void setUnitPeopleId(Integer unitPeopleId) {
        this.unitPeopleId = unitPeopleId;
    }

    public String getUnitPeopleState() {
        return unitPeopleState;
    }

    public void setUnitPeopleState(String unitPeopleState) {
        this.unitPeopleState = unitPeopleState;
    }

    public String getUnitPeopleRemark() {
        return unitPeopleRemark;
    }

    public void setUnitPeopleRemark(String unitPeopleRemark) {
        this.unitPeopleRemark = unitPeopleRemark;
    }

    public String getChushenState() {
        return chushenState;
    }

    public void setChushenState(String chushenState) {
        this.chushenState = chushenState;
    }

    public String getChushenRemark() {
        return chushenRemark;
    }

    public void setChushenRemark(String chushenRemark) {
        this.chushenRemark = chushenRemark;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public Date getOuttime() {
        return outtime;
    }

    public void setOuttime(Date outtime) {
        this.outtime = outtime;
    }

    public String getCarNames() {
        return carNames;
    }

    public void setCarNames(String carNames) {
        this.carNames = carNames;
    }

    public String getCarIds() {
        return carIds;
    }

    public void setCarIds(String carIds) {
        this.carIds = carIds;
    }

    public String getUnitPeopleName() {
        return unitPeopleName;
    }

    public void setUnitPeopleName(String unitPeopleName) {
        this.unitPeopleName = unitPeopleName;
    }

    public String getIsTongguo() {
        return isTongguo;
    }

    public void setIsTongguo(String isTongguo) {
        this.isTongguo = isTongguo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("model", getModel())
                .append("unitId", getUnitId())
                .append("peopleId", getPeopleId())
                .append("thebusId", getThebusId())
                .append("contact", getContact())
                .append("cause", getCause())
                .append("authStime", getAuthStime())
                .append("authEtime", getAuthEtime())
                .append("thebusThemplateId", getThebusThemplateId())
                .append("people1Id", getpeople1Id())
                .append("people1State", getpeople1State())
                .append("people1Name", getpeople1Name())
                .append("people1Remark", getpeople1Remark())
                .append("people1Authtime", getpeople1Authtime())
                .append("people2Id", getpeople2Id())
                .append("people2State", getpeople2State())
                .append("status", getStatus())
                .append("people2Remark", getpeople2Remark())
                .append("people2Authtime", getpeople2Authtime())
                .append("outstatus", getOutstatus())
                .append("people2Name", getpeople2Name())
                .append("adminId", getAdminId())
                .append("createtime", getCreatetime())
                .append("people3Id", getpeople3Id())
                .append("updatetime", getUpdatetime())
                .append("people3State", getpeople3State())
                .append("driver", getDriver())
                .append("people3Remark", getpeople3Remark())
                .append("driverContact", getDriverContact())
                .append("people3Authtime", getpeople3Authtime())
                .append("people3Name", getpeople3Name())
                .append("peopleCount", getPeopleCount())
                .append("unitPeopleId", getUnitPeopleId())
                .append("unitPeopleState", getUnitPeopleState())
                .append("unitPeopleRemark", getUnitPeopleRemark())
                .append("chushenState", getChushenState())
                .append("chushenRemark", getChushenRemark())
                .append("route", getRoute())
                .append("outtime", getOuttime())
                .append("carNames", getCarNames())
                .append("carIds", getCarIds())
                .append("unitPeopleName", getUnitPeopleName())
                .toString();
    }
}
