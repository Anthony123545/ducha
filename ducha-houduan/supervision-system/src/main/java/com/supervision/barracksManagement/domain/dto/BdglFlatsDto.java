package com.supervision.barracksManagement.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.supervision.common.annotation.Excel;

import java.io.Serializable;
import java.util.Date;

/**
 * 营房档案对象导出对象
 */
public class BdglFlatsDto implements Serializable {
    
    /** 单位名称 */
    @Excel(name = "单位名称")
    private String unitName;
    
    @Excel(name = "人员")
    private String peopleName;
    
    /** 部职别 */
    @Excel(name = "部职别")
    private String duty;
    
    /** 军衔 */
    @Excel(name = "军衔",dictType = "sys_user_rank")
    private Integer rankId;
    
    /** 军衔时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "军衔时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date rankDate;
    
    /** 出生时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出生时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date birthDate;
    
    /** 住房待遇等级 */
    @Excel(name = "住房待遇等级")
    private String grade;
    
    /** 入伍年月 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入伍年月", width = 30, dateFormat = "yyyy-MM-dd")
    private Date enlistDate;
    
    /** 结婚时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结婚时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date pTime;
    
    /** 享受相应住房待遇职级时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "享受相应住房待遇职级时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date jobDate;
    
    /** 任现职级时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "任现职级时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date jobsDate;
    
    /** 参加工作时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "参加工作时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date jobTime;
    
    /** 地方普通高等学校入学时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "地方普通高等学校入学时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date entranceDate;
    
    /** 配偶姓名 */
    @Excel(name = "配偶姓名")
    private String pName;
    
    /** 配偶单位 */
    @Excel(name = "配偶单位")
    private String pUnit;
    
    /** 配偶现住房 */
    @Excel(name = "配偶现住房")
    private String pCeace;
    
    /** 配偶是否军队人员（含职工） */
    @Excel(name = "配偶是否军队人员", readConverterExp = "含=职工")
    private String whethers;
    
    /** 配偶户口所在地 */
    @Excel(name = "配偶户口所在地")
    private String pAccount;
    
    /** 配偶入伍或随军时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "配偶入伍或随军时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date pTimeOne;
    
    /** 申请住房类别 */
    @Excel(name = "申请住房类别")
    private String type;
    
    @Excel(name = "备注",width = 40)
    private String remark;
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    public String getUnitName() {
        return unitName;
    }
    
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
    
    public String getPeopleName() {
        return peopleName;
    }
    
    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName;
    }
    
    public String getDuty() {
        return duty;
    }
    
    public void setDuty(String duty) {
        this.duty = duty;
    }
    
    public Integer getRankId() {
        return rankId;
    }
    
    public void setRankId(Integer rankId) {
        this.rankId = rankId;
    }
    
    public Date getRankDate() {
        return rankDate;
    }
    
    public void setRankDate(Date rankDate) {
        this.rankDate = rankDate;
    }
    
    public Date getBirthDate() {
        return birthDate;
    }
    
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
    
    public String getGrade() {
        return grade;
    }
    
    public void setGrade(String grade) {
        this.grade = grade;
    }
    
    public Date getEnlistDate() {
        return enlistDate;
    }
    
    public void setEnlistDate(Date enlistDate) {
        this.enlistDate = enlistDate;
    }
    
    public Date getpTime() {
        return pTime;
    }
    
    public void setpTime(Date pTime) {
        this.pTime = pTime;
    }
    
    public Date getJobDate() {
        return jobDate;
    }
    
    public void setJobDate(Date jobDate) {
        this.jobDate = jobDate;
    }
    
    public Date getJobsDate() {
        return jobsDate;
    }
    
    public void setJobsDate(Date jobsDate) {
        this.jobsDate = jobsDate;
    }
    
    public Date getJobTime() {
        return jobTime;
    }
    
    public void setJobTime(Date jobTime) {
        this.jobTime = jobTime;
    }
    
    public Date getEntranceDate() {
        return entranceDate;
    }
    
    public void setEntranceDate(Date entranceDate) {
        this.entranceDate = entranceDate;
    }
    
    public String getpName() {
        return pName;
    }
    
    public void setpName(String pName) {
        this.pName = pName;
    }
    
    public String getpUnit() {
        return pUnit;
    }
    
    public void setpUnit(String pUnit) {
        this.pUnit = pUnit;
    }
    
    public String getpCeace() {
        return pCeace;
    }
    
    public void setpCeace(String pCeace) {
        this.pCeace = pCeace;
    }
    
    public String getWhethers() {
        return whethers;
    }
    
    public void setWhethers(String whethers) {
        this.whethers = whethers;
    }
    
    public String getpAccount() {
        return pAccount;
    }
    
    public void setpAccount(String pAccount) {
        this.pAccount = pAccount;
    }
    
    public Date getpTimeOne() {
        return pTimeOne;
    }
    
    public void setpTimeOne(Date pTimeOne) {
        this.pTimeOne = pTimeOne;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
}
