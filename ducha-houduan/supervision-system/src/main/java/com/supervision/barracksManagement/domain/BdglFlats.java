package com.supervision.barracksManagement.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 公寓住房申请人员资格审查登记对象 bdgl_flats
 * 
 * @author supervision
 * @date 2022-03-02
 */
public class BdglFlats extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 单位 */
    //@Excel(name = "单位")
    private Integer unitId;

    /** 人员 */
    //@Excel(name = "人员")
    private Integer peopleId;

    @Excel(name = "人员")
    private String peopleName;

    /** 部职别 */
    @Excel(name = "部职别")
    private String duty;

    /** 是否符合申请条件 */
    @Excel(name = "是否符合申请条件")
    private String whether;

    /** 住房待遇等级 */
    @Excel(name = "住房待遇等级")
    private String grade;

    /** 申请住房户型 */
    @Excel(name = "申请住房户型")
    private String houseType;

    /** 出生时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出生时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date birthDate;

    /** 入伍年月 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入伍年月", width = 30, dateFormat = "yyyy-MM-dd")
    private Date enlistDate;

    /** 参加工作时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "参加工作时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date jobTime;

    /** 地方普通高等学校入学时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "地方普通高等学校入学时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date entranceDate;

    /** 享受相应住房待遇职级时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "享受相应住房待遇职级时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date jobDate;

    /** 任现职级时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "任现职级时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date jobsDate;

    /** 配偶是否军队人员（含职工） */
    @Excel(name = "配偶是否军队人员", readConverterExp = "含=职工")
    private String whethers;

    /** 军衔 */
    @Excel(name = "军衔",dictType = "sys_user_rank")
    private Integer rankId;

    /** 军衔时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "军衔时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date rankDate;

    /** 登记人 */
    //@Excel(name = "登记人")
    private Integer adminId;

    /** 配偶姓名 */
    @Excel(name = "配偶姓名")
    private String pName;

    /** 配偶单位 */
    @Excel(name = "配偶单位")
    private String pUnit;

    /** 配偶户口所在地 */
    @Excel(name = "配偶户口所在地")
    private String pAccount;

    /** 结婚时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结婚时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date pTime;

    /** 配偶入伍或随军时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "配偶入伍或随军时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date pTimeOne;

    /** 配偶现住房 */
    @Excel(name = "配偶现住房")
    private String pCeace;

    /** 申请住房类别 */
    @Excel(name = "申请住房类别")
    private String type;

    //@Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createtime;
    /**修改时间*/
    //@Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatetime;



    @Override
    public String getPeopleName() {
        return peopleName;
    }

    @Override
    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName;
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
    public void setDuty(String duty) 
    {
        this.duty = duty;
    }

    public String getDuty() 
    {
        return duty;
    }
    public void setWhether(String whether) 
    {
        this.whether = whether;
    }

    public String getWhether() 
    {
        return whether;
    }
    public void setGrade(String grade) 
    {
        this.grade = grade;
    }

    public String getGrade() 
    {
        return grade;
    }
    public void setHouseType(String houseType) 
    {
        this.houseType = houseType;
    }

    public String getHouseType() 
    {
        return houseType;
    }
    public void setBirthDate(Date birthDate) 
    {
        this.birthDate = birthDate;
    }

    public Date getBirthDate() 
    {
        return birthDate;
    }
    public void setEnlistDate(Date enlistDate) 
    {
        this.enlistDate = enlistDate;
    }

    public Date getEnlistDate() 
    {
        return enlistDate;
    }
    public void setJobTime(Date jobTime) 
    {
        this.jobTime = jobTime;
    }

    public Date getJobTime() 
    {
        return jobTime;
    }
    public void setEntranceDate(Date entranceDate) 
    {
        this.entranceDate = entranceDate;
    }

    public Date getEntranceDate() 
    {
        return entranceDate;
    }
    public void setJobDate(Date jobDate) 
    {
        this.jobDate = jobDate;
    }

    public Date getJobDate() 
    {
        return jobDate;
    }
    public void setJobsDate(Date jobsDate) 
    {
        this.jobsDate = jobsDate;
    }

    public Date getJobsDate() 
    {
        return jobsDate;
    }
    public void setWhethers(String whethers) 
    {
        this.whethers = whethers;
    }

    public String getWhethers() 
    {
        return whethers;
    }
    public void setRankId(Integer rankId) 
    {
        this.rankId = rankId;
    }

    public Integer getRankId() 
    {
        return rankId;
    }
    public void setRankDate(Date rankDate) 
    {
        this.rankDate = rankDate;
    }

    public Date getRankDate() 
    {
        return rankDate;
    }
    public void setAdminId(Integer adminId) 
    {
        this.adminId = adminId;
    }

    public Integer getAdminId() 
    {
        return adminId;
    }
    public void setpName(String pName) 
    {
        this.pName = pName;
    }

    public String getpName() 
    {
        return pName;
    }
    public void setpUnit(String pUnit) 
    {
        this.pUnit = pUnit;
    }

    public String getpUnit() 
    {
        return pUnit;
    }
    public void setpAccount(String pAccount) 
    {
        this.pAccount = pAccount;
    }

    public String getpAccount() 
    {
        return pAccount;
    }
    public void setpTime(Date pTime) 
    {
        this.pTime = pTime;
    }

    public Date getpTime() 
    {
        return pTime;
    }
    public void setpTimeOne(Date pTimeOne) 
    {
        this.pTimeOne = pTimeOne;
    }

    public Date getpTimeOne() 
    {
        return pTimeOne;
    }
    public void setpCeace(String pCeace) 
    {
        this.pCeace = pCeace;
    }

    public String getpCeace() 
    {
        return pCeace;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("unitId", getUnitId())
            .append("peopleId", getPeopleId())
            .append("duty", getDuty())
            .append("whether", getWhether())
            .append("grade", getGrade())
            .append("houseType", getHouseType())
            .append("birthDate", getBirthDate())
            .append("enlistDate", getEnlistDate())
            .append("jobTime", getJobTime())
            .append("entranceDate", getEntranceDate())
            .append("jobDate", getJobDate())
            .append("jobsDate", getJobsDate())
            .append("whethers", getWhethers())
            .append("rankId", getRankId())
            .append("rankDate", getRankDate())
            .append("remark", getRemark())
            .append("createtime", getCreatetime())
            .append("updatetime", getUpdatetime())
            .append("adminId", getAdminId())
            .append("pName", getpName())
            .append("pUnit", getpUnit())
            .append("pAccount", getpAccount())
            .append("pTime", getpTime())
            .append("pTimeOne", getpTimeOne())
            .append("pCeace", getpCeace())
            .append("type", getType())
            .toString();
    }
}
