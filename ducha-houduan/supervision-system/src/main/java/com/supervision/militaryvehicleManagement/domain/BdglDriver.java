package com.supervision.militaryvehicleManagement.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 军车司机设置对象 bdgl_driver
 * 
 * @author supervision
 * @date 2022-03-09
 */
public class BdglDriver
{
    private static final long serialVersionUID = 1L;

    /**   */
    private Integer id;

    /** 人员 */
    @Excel(name = "人员")
    private Integer peopleId;

    /** 单位 */
    //@Excel(name = "单位")
    private Integer unitId;

    /** 单位名称 */
    @Excel(name = "单位名称")
    private String unitName;

    /** 联系方式 */
    @Excel(name = "联系方式")
    private String contact;

    /** 驾驶证号 */
    @Excel(name = "驾驶证号")
    private String driversLicense;

    /** 生日 */
    @Excel(name = "生日")
    private String birthDate;

    /** 入伍时间 */
    @Excel(name = "入伍时间")
    private String enlistDate;

    /** 性别 */
    @Excel(name = "性别",dictType = "sys_user_sex")
    private String sexList;

    /** 军衔 */
    @Excel(name = "军衔",dictType = "sys_user_rank")
    private String rankId;

    /** 驾龄 */
    @Excel(name = "驾龄")
    private Integer drivingAge;

    /** 准驾车型 */
    @Excel(name = "准驾车型",dictType = "quasi_driving_model")
    private String drivingModel;

    /** 初领日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "初领日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date chulingriq;

    /** 核发日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "核发日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date hefariqi;

    /** 审验结果 */
    @Excel(name = "审验结果")
    private String result;

    /** 有效日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "有效日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date youxiaoriqi;

    /** 职业技能 */
    @Excel(name = "职业技能",dictType = "vocational")
    private String vocationalSkills;

    /** 驾驶员姓名 */
    @Excel(name = "驾驶员姓名")
    private String peopleName;
    
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
    
    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }
    public void setPeopleId(Integer peopleId)
    {
        this.peopleId = peopleId;
    }

    public Integer getPeopleId()
    {
        return peopleId;
    }
    public void setUnitId(Integer unitId)
    {
        this.unitId = unitId;
    }

    public Integer getUnitId()
    {
        return unitId;
    }
    public void setContact(String contact)
    {
        this.contact = contact;
    }

    public String getContact()
    {
        return contact;
    }
    public void setDriversLicense(String driversLicense)
    {
        this.driversLicense = driversLicense;
    }

    public String getDriversLicense()
    {
        return driversLicense;
    }
    public void setBirthDate(String birthDate)
    {
        this.birthDate = birthDate;
    }

    public String getBirthDate()
    {
        return birthDate;
    }
    public void setEnlistDate(String enlistDate)
    {
        this.enlistDate = enlistDate;
    }

    public String getEnlistDate()
    {
        return enlistDate;
    }
    public void setSexList(String sexList)
    {
        this.sexList = sexList;
    }

    public String getSexList()
    {
        return sexList;
    }
    public void setRankId(String rankId)
    {
        this.rankId = rankId;
    }

    public String getRankId()
    {
        return rankId;
    }

    public Integer getDrivingAge() {
        return drivingAge;
    }

    public void setDrivingAge(Integer drivingAge) {
        this.drivingAge = drivingAge;
    }

    public void setDrivingModel(String drivingModel)
    {
        this.drivingModel = drivingModel;
    }

    public String getDrivingModel()
    {
        return drivingModel;
    }
    public void setChulingriq(Date chulingriq)
    {
        this.chulingriq = chulingriq;
    }

    public Date getChulingriq()
    {
        return chulingriq;
    }
    public void setHefariqi(Date hefariqi)
    {
        this.hefariqi = hefariqi;
    }

    public Date getHefariqi()
    {
        return hefariqi;
    }
    public void setResult(String result)
    {
        this.result = result;
    }

    public String getResult()
    {
        return result;
    }
    public void setYouxiaoriqi(Date youxiaoriqi)
    {
        this.youxiaoriqi = youxiaoriqi;
    }

    public Date getYouxiaoriqi()
    {
        return youxiaoriqi;
    }
    public void setVocationalSkills(String vocationalSkills)
    {
        this.vocationalSkills = vocationalSkills;
    }

    public String getVocationalSkills()
    {
        return vocationalSkills;
    }
    public void setPeopleName(String peopleName)
    {
        this.peopleName = peopleName;
    }

    public String getPeopleName()
    {
        return peopleName;
    }
    public void setUnitName(String unitName)
    {
        this.unitName = unitName;
    }

    public String getUnitName()
    {
        return unitName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("peopleId", getPeopleId())
                .append("unitId", getUnitId())
                .append("contact", getContact())
                .append("driversLicense", getDriversLicense())
                .append("birthDate", getBirthDate())
                .append("enlistDate", getEnlistDate())
                .append("sexList", getSexList())
                .append("rankId", getRankId())
                .append("drivingAge", getDrivingAge())
                .append("drivingModel", getDrivingModel())
                .append("chulingriq", getChulingriq())
                .append("hefariqi", getHefariqi())
                .append("result", getResult())
                .append("youxiaoriqi", getYouxiaoriqi())
                .append("vocationalSkills", getVocationalSkills())
                .append("createtime", getCreatetime())
                .append("updatetime", getUpdatetime())
                .append("peopleName", getPeopleName())
                .append("unitName", getUnitName())
                .toString();
    }
}
