package com.supervision.peopleChuRu.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 人员请假对象 bdgl_leave
 *
 * @author supervision
 * @date 2022-03-05
 */
@Data
public class BdglLeave extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 请假人员 */
    @Excel(name = "请假人员")
    private String peopleId;

    /** 单位 */
    @Excel(name = "单位")
    private Long unitId;

    /** 请假开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "请假开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;

    /** 请假结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "请假结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    // 新增：获取ISO格式的startTime字符串
    public String getFormattedStartTime() {
        if (startTime == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        return sdf.format(startTime);
    }

    public String getFormattedEndTime() {
        if (endTime == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        return sdf.format(endTime);
    }

    /** 请假类型 */
    @Excel(name = "请假类型")
    private String leaveType;

    /** 出行方式 */

    @Excel(name = "出行方式")
    private String travelMode;

    /** 请假原因 */
    @Excel(name = "请假原因")
    private String leaveReason;

    /** 离队时间 */
    @Excel(name = "离队时间")
    private String departureTime;

    /** 归队时间 */
    @Excel(name = "归队时间")
    private String returnTime;

    /** 归队状态 */
    @Excel(name = "归队状态")
    private String returnStatus;

    /** 是否归队 */
    @Excel(name = "是否归队")
    private String isReturn;

    /** 创建人ID */
    @Excel(name = "创建人ID")
    private Long adminId;

    /** 批准人 */
    @Excel(name = "批准人")
    private Integer peopleId1;

    /** 职务 */
    @Excel(name = "职务")
    private Integer postId;

    /** 二级审批人 */
    @Excel(name = "二级审批人")
    private Integer peopleId2;

    /** 三级审批人 */
    @Excel(name = "三级审批人")
    private Integer peopleId3;

    /** 四级审批人 */
    @Excel(name = "四级审批人")
    private Integer peopleId4;

    /** $column.columnComment */
    @Excel(name = "")
    private Integer status1;

    /** $column.columnComment */
    @Excel(name = "四级审批人")
    private Integer status2;

    /** $column.columnComment */
    @Excel(name = "四级审批人")
    private Integer status3;

    /** $column.columnComment */
    @Excel(name = "四级审批人")
    private Integer status4;

    /** $column.columnComment */
    @Excel(name = "四级审批人")
    private String contents;

    /** $column.columnComment */
    @Excel(name = "四级审批人")
    private Integer statusd;

    /** 归队状态 */
    @Excel(name = "归队状态")
    private Integer rejoin;

    /** 单位名称 */
    @Excel(name = "单位名称")
    private String unitName;

    /** 请假人姓名 */
    @Excel(name = "请假人姓名")
    private String peopleName;

    /** 车辆类型（0为私家车，1为公家车） */
    @Excel(name = "车辆类型")
    private Integer carType;

    /** 车牌号 */
    @Excel(name = "车牌号")
    private String plateNumber;
    
    /** 一级审批人姓名 */
    @Excel(name = "一级审批人姓名")
    private String peopleName1;

    /** 二级审批人姓名 */
    @Excel(name = "二级审批人姓名")
    private String peopleName2;

    /** 三级审批人姓名 */
    @Excel(name = "三级审批人姓名")
    private String peopleName3;

    /** 司机审批人姓名 */
    @Excel(name = "司机审批人姓名")
    private String peopleName4;

    /**驳回原因*/
    private String reasons;

    /**特殊审批原因*/
    private String specialApproval;

    /**特殊审批原因*/
    private List<BdglLeaveFu> bdglLeaveFus;

    private Date actualreturntime;

    private String name;//人员名称

    private String[] names;//人员名称

    /** 入口门闸 */
    @Excel(name = "入口门闸")
    private Long doorIn;
    
    /** 出口门闸 */
    @Excel(name = "出口门闸")
    private Long doorOut;
    
    /** 入口车闸 */
    @Excel(name = "入口车闸")
    private Long carIn;
    
    /** 出口车闸 */
    @Excel(name = "出口车闸")
    private Long carOut;

    public String[] getNames() {
        return names;
    }

    public void setNames(String[] names) {
        this.names = names;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getActualreturntime() {
        return actualreturntime;
    }

    public void setActualreturntime(Date actualreturntime) {
        this.actualreturntime = actualreturntime;
    }

    public List<BdglLeaveFu> getBdglLeaveFus() {
        return bdglLeaveFus;
    }

    public void setBdglLeaveFus(List<BdglLeaveFu> bdglLeaveFus) {
        this.bdglLeaveFus = bdglLeaveFus;
    }

    public String getSpecialApproval() {
        return specialApproval;
    }

    public void setSpecialApproval(String specialApproval) {
        this.specialApproval = specialApproval;
    }

    private String common;

    private String shenPiJiBie;

    public String getShenPiJiBie() {
        return shenPiJiBie;
    }

    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
    }

    public void setShenPiJiBie(String shenPiJiBie) {
        this.shenPiJiBie = shenPiJiBie;
    }

    public String getCommon() {
        return common;
    }

    public void setCommon(String common) {
        this.common = common;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getPeopleId() {
        return peopleId;
    }

    public void setPeopleId(String peopleId) {
        this.peopleId = peopleId;
    }

    public Long getId()
    {
        return id;
    }

    public void setUnitId(Long unitId)
    {
        this.unitId = unitId;
    }

    public Long getUnitId()
    {
        return unitId;
    }
    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }

    public Date getStartTime()
    {
        return startTime;
    }
    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }

    public Date getEndTime()
    {
        return endTime;
    }
    public void setLeaveType(String leaveType)
    {
        this.leaveType = leaveType;
    }

    public String getLeaveType()
    {
        return leaveType;
    }
    public void setLeaveReason(String leaveReason)
    {
        this.leaveReason = leaveReason;
    }

    public String getLeaveReason()
    {
        return leaveReason;
    }
    public void setDepartureTime(String departureTime)
    {
        this.departureTime = departureTime;
    }

    public String getDepartureTime()
    {
        return departureTime;
    }
    public void setReturnTime(String returnTime)
    {
        this.returnTime = returnTime;
    }

    public String getReturnTime()
    {
        return returnTime;
    }
    public void setReturnStatus(String returnStatus)
    {
        this.returnStatus = returnStatus;
    }

    public String getReturnStatus()
    {
        return returnStatus;
    }
    public void setIsReturn(String isReturn)
    {
        this.isReturn = isReturn;
    }

    public String getIsReturn()
    {
        return isReturn;
    }
    public void setAdminId(Long adminId)
    {
        this.adminId = adminId;
    }

    public Long getAdminId()
    {
        return adminId;
    }
    public void setPeopleId1(Integer peopleId1)
    {
        this.peopleId1 = peopleId1;
    }

    public Integer getPeopleId1()
    {
        return peopleId1;
    }
    public void setPostId(Integer postId)
    {
        this.postId = postId;
    }

    public Integer getPostId()
    {
        return postId;
    }
    public void setPeopleId2(Integer peopleId2)
    {
        this.peopleId2 = peopleId2;
    }

    public Integer getPeopleId2()
    {
        return peopleId2;
    }
    public void setPeopleId3(Integer peopleId3)
    {
        this.peopleId3 = peopleId3;
    }

    public Integer getPeopleId3()
    {
        return peopleId3;
    }
    public void setPeopleId4(Integer peopleId4)
    {
        this.peopleId4 = peopleId4;
    }

    public Integer getPeopleId4()
    {
        return peopleId4;
    }
    public void setStatus1(Integer status1)
    {
        this.status1 = status1;
    }

    public Integer getStatus1()
    {
        return status1;
    }
    public void setStatus2(Integer status2)
    {
        this.status2 = status2;
    }

    public Integer getStatus2()
    {
        return status2;
    }
    public void setStatus3(Integer status3)
    {
        this.status3 = status3;
    }

    public Integer getStatus3()
    {
        return status3;
    }
    public void setStatus4(Integer status4)
    {
        this.status4 = status4;
    }

    public Integer getStatus4()
    {
        return status4;
    }
    public void setContents(String contents)
    {
        this.contents = contents;
    }

    public String getContents()
    {
        return contents;
    }
    public void setStatusd(Integer statusd)
    {
        this.statusd = statusd;
    }

    public Integer getStatusd()
    {
        return statusd;
    }
    public void setRejoin(Integer rejoin)
    {
        this.rejoin = rejoin;
    }

    public Integer getRejoin()
    {
        return rejoin;
    }
    public void setUnitName(String unitName)
    {
        this.unitName = unitName;
    }

    public String getUnitName()
    {
        return unitName;
    }
    public void setPeopleName(String peopleName)
    {
        this.peopleName = peopleName;
    }

    public String getPeopleName()
    {
        return peopleName;
    }
    
    public void setCarType(Integer carType)
    {
        this.carType = carType;
    }

    public Integer getCarType()
    {
        return carType;
    }
    
    public void setPlateNumber(String plateNumber)
    {
        this.plateNumber = plateNumber;
    }

    public String getPlateNumber()
    {
        return plateNumber;
    }
    
    public void setPeopleName1(String peopleName1)
    {
        this.peopleName1 = peopleName1;
    }

    public String getPeopleName1()
    {
        return peopleName1;
    }
    public void setPeopleName2(String peopleName2)
    {
        this.peopleName2 = peopleName2;
    }

    public String getPeopleName2()
    {
        return peopleName2;
    }
    public void setPeopleName3(String peopleName3)
    {
        this.peopleName3 = peopleName3;
    }

    public String getPeopleName3()
    {
        return peopleName3;
    }
    public void setPeopleName4(String peopleName4)
    {
        this.peopleName4 = peopleName4;
    }

    public String getPeopleName4()
    {
        return peopleName4;
    }

    public String getTravelMode() {
        return travelMode;
    }

    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
    }

    public Long getDoorIn() {
        return doorIn;
    }

    public void setDoorIn(Long doorIn) {
        this.doorIn = doorIn;
    }

    public Long getDoorOut() {
        return doorOut;
    }

    public void setDoorOut(Long doorOut) {
        this.doorOut = doorOut;
    }

    public Long getCarIn() {
        return carIn;
    }

    public void setCarIn(Long carIn) {
        this.carIn = carIn;
    }

    public Long getCarOut() {
        return carOut;
    }

    public void setCarOut(Long carOut) {
        this.carOut = carOut;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("peopleId", getPeopleId())
                .append("unitId", getUnitId())
                .append("startTime", getStartTime())
                .append("endTime", getEndTime())
                .append("leaveType", getLeaveType())
                .append("travelMode", getTravelMode())
                .append("leaveReason", getLeaveReason())
                .append("departureTime", getDepartureTime())
                .append("returnTime", getReturnTime())
                .append("returnStatus", getReturnStatus())
                .append("isReturn", getIsReturn())
                .append("updatetime", getUpdatetime())
                .append("adminId", getAdminId())
                .append("createtime", getCreatetime())
                .append("peopleId1", getPeopleId1())
                .append("postId", getPostId())
                .append("peopleId2", getPeopleId2())
                .append("peopleId3", getPeopleId3())
                .append("peopleId4", getPeopleId4())
                .append("status1", getStatus1())
                .append("status2", getStatus2())
                .append("status3", getStatus3())
                .append("status4", getStatus4())
                .append("contents", getContents())
                .append("statusd", getStatusd())
                .append("rejoin", getRejoin())
                .append("unitName", getUnitName())
                .append("peopleName", getPeopleName())
                .append("peopleName1", getPeopleName1())
                .append("peopleName2", getPeopleName2())
                .append("peopleName3", getPeopleName3())
                .append("peopleName4", getPeopleName4())
                .toString();
    }
}