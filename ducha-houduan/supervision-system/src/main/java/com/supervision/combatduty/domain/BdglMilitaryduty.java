package com.supervision.combatduty.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 军车值班对象 bdgl_militaryduty
 *
 * @author supervision
 * @date 2022-03-11
 */
public class BdglMilitaryduty extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 值班时间 */
    @Excel(name = "值班时间")
    private String attendedTime;

    /** 号码号牌ID */
    @Excel(name = "号码号牌ID")
    private String numberPlateId;

    /** 号码号牌 */
    @Excel(name = "号码号牌")
    private String numberPlateName;

    /** 车属单位ID */
    @Excel(name = "车属单位ID")
    private String carUnitId;

    /** 车属单位名称 */
    @Excel(name = "车属单位名称")
    private String carUnitName;

    /** 车辆类别 */
    @Excel(name = "车辆类别")
    private String carType;

    /** 车辆状态 */
    @Excel(name = "车辆状态")
    private String carStates;

    /** 所在位置 */
    @Excel(name = "所在位置")
    private String adress;

    /** 司机id */
    @Excel(name = "司机id")
    private String driverId;

    /** 司机名字 */
    @Excel(name = "司机名字")
    private String driverName;

    /** 联系方式 */
    @Excel(name = "联系方式")
    private String telephone;

    /** 厂牌型号 */
    @Excel(name = "厂牌型号")
    private String modelInfo;

    /** 厂牌信息 */
    @Excel(name = "厂牌信息")
    private String modelText;

    /** 车辆颜色 */
    @Excel(name = "车辆颜色")
    private String color;

    /** 司机单位 */
    @Excel(name = "司机单位")
    private String driverUnit;

    /** 司机单位ID */
    @Excel(name = "司机单位ID")
    private String driverUnitId;

    /**
     * 值班时间区间
     */
    private List<String> timequjian;

    private List<Long> numberPlateIdList;
    /** 车属单位ID */
    private List<String> unitId;
    /** 司机ID */
    private List<String> sjId;

    /** 手机号 */
    private List<String> phone;

    public void setUnitId(List<String> unitId) {
        this.unitId = unitId;
    }

    public void setSjId(List<String> sjId) {
        this.sjId = sjId;
    }

    public void setPhone(List<String> phone) {
        this.phone = phone;
    }

    public List<String> getPhone() {
        return phone;
    }

    public List<String> getUnitId() {
        return unitId;
    }


    public List<String> getSjId() {
        return sjId;
    }

    public String getCarUnitId() {
        return carUnitId;
    }

    public void setCarUnitId(String carUnitId) {
        this.carUnitId = carUnitId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getDriverUnitId() {
        return driverUnitId;
    }

    public void setDriverUnitId(String driverUnitId) {
        this.driverUnitId = driverUnitId;
    }

    public List<String> getTimequjian() {
        return timequjian;
    }

    public String getModelInfo() {
        return modelInfo;
    }

    public void setModelInfo(String modelInfo) {
        this.modelInfo = modelInfo;
    }

    public String getModelText() {
        return modelText;
    }

    public void setModelText(String modelText) {
        this.modelText = modelText;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDriverUnit() {
        return driverUnit;
    }

    public void setDriverUnit(String driverUnit) {
        this.driverUnit = driverUnit;
    }

    public void setTimequjian(List<String> timequjian) {
        this.timequjian = timequjian;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setAttendedTime(String attendedTime)
    {
        this.attendedTime = attendedTime;
    }

    public String getAttendedTime()
    {
        return attendedTime;
    }
    public void setNumberPlateId(String numberPlateId)
    {
        this.numberPlateId = numberPlateId;
    }

    public String getNumberPlateId()
    {
        return numberPlateId;
    }
    public void setNumberPlateName(String numberPlateName)
    {
        this.numberPlateName = numberPlateName;
    }

    public String getNumberPlateName()
    {
        return numberPlateName;
    }
    public void setCarUnitName(String carUnitName)
    {
        this.carUnitName = carUnitName;
    }

    public String getCarUnitName()
    {
        return carUnitName;
    }
    public void setCarType(String carType)
    {
        this.carType = carType;
    }

    public String getCarType()
    {
        return carType;
    }
    public void setCarStates(String carStates)
    {
        this.carStates = carStates;
    }

    public String getCarStates()
    {
        return carStates;
    }
    public void setAdress(String adress)
    {
        this.adress = adress;
    }

    public String getAdress()
    {
        return adress;
    }
    public void setDriverName(String driverName)
    {
        this.driverName = driverName;
    }

    public String getDriverName()
    {
        return driverName;
    }
    public void setTelephone(String telephone)
    {
        this.telephone = telephone;
    }

    public String getTelephone()
    {
        return telephone;
    }

    public List<Long> getNumberPlateIdList() {
        return numberPlateIdList;
    }

    public void setNumberPlateIdList(List<Long> numberPlateIdList) {
        this.numberPlateIdList = numberPlateIdList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("attendedTime", getAttendedTime())
            .append("numberPlateId", getNumberPlateId())
            .append("numberPlateName", getNumberPlateName())
            .append("carUnitId", getCarUnitId())
            .append("carUnitName", getCarUnitName())
            .append("carType", getCarType())
            .append("carStates", getCarStates())
            .append("adress", getAdress())
            .append("driverId", getDriverId())
            .append("driverName", getDriverName())
            .append("telephone", getTelephone())
            .toString();
    }

    private String attendedTimeGe;//值班时间大于等于

    private String attendedTimeLe;//值班时间小于等于

    public String getAttendedTimeGe() {
        return attendedTimeGe;
    }

    public void setAttendedTimeGe(String attendedTimeGe) {
        this.attendedTimeGe = attendedTimeGe;
    }

    public String getAttendedTimeLe() {
        return attendedTimeLe;
    }

    public void setAttendedTimeLe(String attendedTimeLe) {
        this.attendedTimeLe = attendedTimeLe;
    }

}
