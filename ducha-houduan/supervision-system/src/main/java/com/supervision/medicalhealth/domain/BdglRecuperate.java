package com.supervision.medicalhealth.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 疗养管理对象 bdgl_recuperate
 * 
 * @author hr
 * @date 2022-02-14
 */
public class BdglRecuperate extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private String common;

    /** $column.columnComment */
    private Integer id;

    /** 单位 */
    @Excel(name = "单位")
    private Integer unitId;

    /** 疗养人 */
    @Excel(name = "疗养人")
    private Integer peopleId;

    /** 疗养类型 */
    @Excel(name = "疗养类型")
    private String type;

    /** 疗养天数 */
    @Excel(name = "疗养天数")
    private String number;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** $column.columnComment */
    @Excel(name = "结束时间")
    private Integer unitId1;

    /** $column.columnComment */
    @Excel(name = "结束时间")
    private Integer unitId2;

    /** $column.columnComment */
    @Excel(name = "结束时间")
    private Integer unitId3;

    /** $column.columnComment */
    @Excel(name = "结束时间")
    private Integer peopleId1;

    /** $column.columnComment */
    @Excel(name = "结束时间")
    private Integer peopleId2;

    /** $column.columnComment */
    @Excel(name = "结束时间")
    private Integer peopleId3;

    /** 1通过，0不通过,2未审核 */
    @Excel(name = "1通过，0不通过,2未审核")
    private String status1;

    /** 1通过，0不通过,2未审核 */
    @Excel(name = "1通过，0不通过,2未审核")
    private String status2;

    /** 1通过，0不通过,2未审核 */
    @Excel(name = "1通过，0不通过,2未审核")
    private String status3;

    /** $column.columnComment */
    @Excel(name = "1通过，0不通过,2未审核")
    private Integer peopleState1;

    /** $column.columnComment */
    @Excel(name = "1通过，0不通过,2未审核")
    private Integer peopleState2;

    /** $column.columnComment */
    @Excel(name = "1通过，0不通过,2未审核")
    private Integer peopleState3;

    /** $column.columnComment */
    @Excel(name = "1通过，0不通过,2未审核")
    private String people1Remark;

    /** $column.columnComment */
    @Excel(name = "1通过，0不通过,2未审核")
    private String people2Remark;

    /** $column.columnComment */
    @Excel(name = "1通过，0不通过,2未审核")
    private String people3Remark;

    /** $column.columnComment */
    @Excel(name = "1通过，0不通过,2未审核")
    private String people1Authtime;

    /** $column.columnComment */
    @Excel(name = "1通过，0不通过,2未审核")
    private String people2Authtime;

    /** $column.columnComment */
    @Excel(name = "1通过，0不通过,2未审核")
    private String people3Authtime;

    /** 整体审批状态 */
    @Excel(name = "整体审批状态")
    private String status;



    /** 岗位 */
    @Excel(name = "岗位")
    private String job;
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createtime;
    /**修改时间*/
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatetime;
    /** 备注 */
    @Excel(name = "备注")
    private String remark;
    /** 单位名称 */
    @Excel(name = "单位名称")
    private String unitName;

    /** 一级审核人单位 */
    @Excel(name = "一级审核人单位")
    private String unitName1;

    /** 二级审核人单位 */
    @Excel(name = "二级审核人单位")
    private String unitName2;

    /** 三级审核人单位 */
    @Excel(name = "三级审核人单位")
    private String unitName3;

    /** 疗养人姓名 */
    @Excel(name = "疗养人姓名")
    private String peopleName;

    /** 一级审核人姓名 */
    @Excel(name = "一级审核人姓名")
    private String peopleName1;

    /** 二级审核人姓名 */
    @Excel(name = "二级审核人姓名")
    private String peopleName2;

    /** 三级审核人姓名 */
    @Excel(name = "三级审核人姓名")
    private String peopleName3;
    /**审批级别*/
    private String spJiBie;

    private String address;

    private String reasons;

    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSpJiBie() {
        return spJiBie;
    }

    public void setSpJiBie(String spJiBie) {
        this.spJiBie = spJiBie;
    }

    public String getCommon() {
        return common;
    }

    public void setCommon(String common) {
        this.common = common;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitName1() {
        return unitName1;
    }

    public void setUnitName1(String unitName1) {
        this.unitName1 = unitName1;
    }

    public String getUnitName2() {
        return unitName2;
    }

    public void setUnitName2(String unitName2) {
        this.unitName2 = unitName2;
    }

    public String getUnitName3() {
        return unitName3;
    }

    public void setUnitName3(String unitName3) {
        this.unitName3 = unitName3;
    }

    public String getPeopleName() {
        return peopleName;
    }

    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName;
    }

    public String getPeopleName1() {
        return peopleName1;
    }

    public void setPeopleName1(String peopleName1) {
        this.peopleName1 = peopleName1;
    }

    public String getPeopleName2() {
        return peopleName2;
    }

    public void setPeopleName2(String peopleName2) {
        this.peopleName2 = peopleName2;
    }

    public String getPeopleName3() {
        return peopleName3;
    }

    public void setPeopleName3(String peopleName3) {
        this.peopleName3 = peopleName3;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setUnitId(Integer unitId) 
    {
        this.unitId = unitId;
    }

    public Integer getUnitId() 
    {
        return unitId;
    }
    public void setPeopleId(Integer peopleId) 
    {
        this.peopleId = peopleId;
    }

    public Integer getPeopleId() 
    {
        return peopleId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

    public String getNumber() 
    {
        return number;
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
    public void setUnitId1(Integer unitId1) 
    {
        this.unitId1 = unitId1;
    }

    public Integer getUnitId1() 
    {
        return unitId1;
    }
    public void setUnitId2(Integer unitId2) 
    {
        this.unitId2 = unitId2;
    }

    public Integer getUnitId2() 
    {
        return unitId2;
    }
    public void setUnitId3(Integer unitId3) 
    {
        this.unitId3 = unitId3;
    }

    public Integer getUnitId3() 
    {
        return unitId3;
    }
    public void setPeopleId1(Integer peopleId1) 
    {
        this.peopleId1 = peopleId1;
    }

    public Integer getPeopleId1() 
    {
        return peopleId1;
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
    public void setStatus1(String status1) 
    {
        this.status1 = status1;
    }

    public String getStatus1() 
    {
        return status1;
    }
    public void setStatus2(String status2) 
    {
        this.status2 = status2;
    }

    public String getStatus2() 
    {
        return status2;
    }
    public void setStatus3(String status3) 
    {
        this.status3 = status3;
    }

    public String getStatus3() 
    {
        return status3;
    }
    public void setPeopleState1(Integer peopleState1) 
    {
        this.peopleState1 = peopleState1;
    }

    public Integer getPeopleState1() 
    {
        return peopleState1;
    }
    public void setPeopleState2(Integer peopleState2) 
    {
        this.peopleState2 = peopleState2;
    }

    public Integer getPeopleState2() 
    {
        return peopleState2;
    }
    public void setPeopleState3(Integer peopleState3) 
    {
        this.peopleState3 = peopleState3;
    }

    public Integer getPeopleState3() 
    {
        return peopleState3;
    }
    public void setPeople1Remark(String people1Remark) 
    {
        this.people1Remark = people1Remark;
    }

    public String getPeople1Remark() 
    {
        return people1Remark;
    }
    public void setPeople2Remark(String people2Remark) 
    {
        this.people2Remark = people2Remark;
    }

    public String getPeople2Remark() 
    {
        return people2Remark;
    }
    public void setPeople3Remark(String people3Remark) 
    {
        this.people3Remark = people3Remark;
    }

    public String getPeople3Remark() 
    {
        return people3Remark;
    }
    public void setPeople1Authtime(String people1Authtime) 
    {
        this.people1Authtime = people1Authtime;
    }

    public String getPeople1Authtime() 
    {
        return people1Authtime;
    }
    public void setPeople2Authtime(String people2Authtime) 
    {
        this.people2Authtime = people2Authtime;
    }

    public String getPeople2Authtime() 
    {
        return people2Authtime;
    }
    public void setPeople3Authtime(String people3Authtime) 
    {
        this.people3Authtime = people3Authtime;
    }

    public String getPeople3Authtime() 
    {
        return people3Authtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setJob(String job)
    {
        this.job = job;
    }

    public String getJob() 
    {
        return job;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("unitId", getUnitId())
            .append("peopleId", getPeopleId())
            .append("type", getType())
            .append("number", getNumber())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("remark", getRemark())
            .append("createtime", getCreatetime())
            .append("updatetime", getUpdatetime())
            .append("unitId1", getUnitId1())
            .append("unitId2", getUnitId2())
            .append("unitId3", getUnitId3())
            .append("peopleId1", getPeopleId1())
            .append("peopleId2", getPeopleId2())
            .append("peopleId3", getPeopleId3())
            .append("status1", getStatus1())
            .append("status2", getStatus2())
            .append("status3", getStatus3())
            .append("peopleState1", getPeopleState1())
            .append("peopleState2", getPeopleState2())
            .append("peopleState3", getPeopleState3())
            .append("people1Remark", getPeople1Remark())
            .append("people2Remark", getPeople2Remark())
            .append("people3Remark", getPeople3Remark())
            .append("people1Authtime", getPeople1Authtime())
            .append("people2Authtime", getPeople2Authtime())
            .append("people3Authtime", getPeople3Authtime())
            .append("status", getStatus())
            .append("job", getJob())
            .toString();
    }
}
