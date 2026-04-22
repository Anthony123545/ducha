package com.supervision.peopleManage.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 人员政审对象 bdgl_people_examination
 * 
 * @author supervision
 * @date 2022-02-23
 */
public class BdglPeopleExamination extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 政审对象 */
    @Excel(name = "政审对象")
    private Integer peopleId;

    /** 性别 */
    @Excel(name = "性别")
    private Integer sex;

    /** 出生年月 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出生年月", width = 30, dateFormat = "yyyy-MM-dd")
    private Date birthDate;

    /** 党团情况 */
    @Excel(name = "党团情况")
    private Integer partyId;

    /** 民族 */
    @Excel(name = "民族")
    private Integer nationId;

    /** 籍贯 */
    @Excel(name = "籍贯")
    private String origin;

    /** 入伍年月 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入伍年月", width = 30, dateFormat = "yyyy-MM-dd")
    private Date enlistDate;

    /** 奖惩情况 */
    @Excel(name = "奖惩情况")
    private String prize;

    /** 入伍简介 */
    @Excel(name = "入伍简介")
    private String enlistdesc;

    /** 政治思想态度 */
    @Excel(name = "政治思想态度")
    private String politicsAttitude;

    /** 参加组织情况 */
    @Excel(name = "参加组织情况")
    private String organization;

    /** 家庭成员情况 */
    @Excel(name = "家庭成员情况")
    private String family;

    /** 社会关系情况 */
    @Excel(name = "社会关系情况")
    private String socialRelations;

    /** 配偶家庭情况 */
    @Excel(name = "配偶家庭情况")
    private String family2;

    /** 本人自我鉴定 */
    @Excel(name = "本人自我鉴定")
    private String selfIdentification;

    /** 单位审查报告 */
    @Excel(name = "单位审查报告")
    private String unitExamination;

    /** 操作人员 */
    @Excel(name = "操作人员")
    private Integer adminId;

    /** 人员名称 */
    @Excel(name = "人员名称")
    private String cName;

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
    public void setSex(Integer sex)
    {
        this.sex = sex;
    }

    public Integer getSex()
    {
        return sex;
    }
    public void setBirthDate(Date birthDate) 
    {
        this.birthDate = birthDate;
    }

    public Date getBirthDate() 
    {
        return birthDate;
    }
    public void setPartyId(Integer partyId)
    {
        this.partyId = partyId;
    }

    public Integer getPartyId()
    {
        return partyId;
    }
    public void setNationId(Integer nationId)
    {
        this.nationId = nationId;
    }

    public Integer getNationId()
    {
        return nationId;
    }
    public void setOrigin(String origin) 
    {
        this.origin = origin;
    }

    public String getOrigin() 
    {
        return origin;
    }
    public void setEnlistDate(Date enlistDate) 
    {
        this.enlistDate = enlistDate;
    }

    public Date getEnlistDate() 
    {
        return enlistDate;
    }
    public void setPrize(String prize) 
    {
        this.prize = prize;
    }

    public String getPrize() 
    {
        return prize;
    }
    public void setEnlistdesc(String enlistdesc) 
    {
        this.enlistdesc = enlistdesc;
    }

    public String getEnlistdesc() 
    {
        return enlistdesc;
    }
    public void setPoliticsAttitude(String politicsAttitude) 
    {
        this.politicsAttitude = politicsAttitude;
    }

    public String getPoliticsAttitude() 
    {
        return politicsAttitude;
    }
    public void setOrganization(String organization) 
    {
        this.organization = organization;
    }

    public String getOrganization() 
    {
        return organization;
    }
    public void setFamily(String family) 
    {
        this.family = family;
    }

    public String getFamily() 
    {
        return family;
    }
    public void setSocialRelations(String socialRelations) 
    {
        this.socialRelations = socialRelations;
    }

    public String getSocialRelations() 
    {
        return socialRelations;
    }
    public void setFamily2(String family2) 
    {
        this.family2 = family2;
    }

    public String getFamily2() 
    {
        return family2;
    }
    public void setSelfIdentification(String selfIdentification) 
    {
        this.selfIdentification = selfIdentification;
    }

    public String getSelfIdentification() 
    {
        return selfIdentification;
    }
    public void setUnitExamination(String unitExamination) 
    {
        this.unitExamination = unitExamination;
    }

    public String getUnitExamination() 
    {
        return unitExamination;
    }
    public void setAdminId(Integer adminId) 
    {
        this.adminId = adminId;
    }

    public Integer getAdminId() 
    {
        return adminId;
    }
    public void setcName(String cName) 
    {
        this.cName = cName;
    }

    public String getcName() 
    {
        return cName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("peopleId", getPeopleId())
            .append("sex", getSex())
            .append("birthDate", getBirthDate())
            .append("partyId", getPartyId())
            .append("nationId", getNationId())
            .append("origin", getOrigin())
            .append("enlistDate", getEnlistDate())
            .append("prize", getPrize())
            .append("enlistdesc", getEnlistdesc())
            .append("politicsAttitude", getPoliticsAttitude())
            .append("organization", getOrganization())
            .append("family", getFamily())
            .append("socialRelations", getSocialRelations())
            .append("family2", getFamily2())
            .append("selfIdentification", getSelfIdentification())
            .append("unitExamination", getUnitExamination())
            .append("adminId", getAdminId())
            .append("createtime", getCreatetime())
            .append("updatetime", getUpdatetime())
            .append("cName", getcName())
            .toString();
    }
}
