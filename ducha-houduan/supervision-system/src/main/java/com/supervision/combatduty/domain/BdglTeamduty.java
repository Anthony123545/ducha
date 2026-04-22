package com.supervision.combatduty.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 大队值班对象 bdgl_teamduty
 * 
 * @author supervision
 * @date 2022-03-10
 */
public class BdglTeamduty extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 值班时间 */
    @Excel(name = "值班时间")
    private String attendedTime;

    /** 值班首长 */
    @Excel(name = "值班首长")
    private String chiefDutyName;

    /** 值班首长ID */
    @Excel(name = "值班首长ID")
    private Long chiefDutyId;

    /** 值班首长电话 */
    @Excel(name = "值班首长电话")
    private String chiefDutyPhone;

    /** 作战值班员 */
    @Excel(name = "作战值班员")
    private String combatDutyName;

    /** 作战值班员ID */
    @Excel(name = "作战值班员ID")
    private Long combatDutyId;

    /** 作战值班员电话 */
    @Excel(name = "作战值班员电话")
    private String combatDutyPhone;

    /** 通信值班员 */
    @Excel(name = "通信值班员")
    private String dutyOffierName;

    /** 通信值班员ID */
    @Excel(name = "通信值班员ID")
    private Long dutyOffierId;

    /** 通信值班员电话 */
    @Excel(name = "通信值班员电话")
    private String dutyOffierPhone;

    /** 机要值班员 */
    @Excel(name = "机要值班员")
    private String confidentialName;

    /** 机要值班员ID */
    @Excel(name = "机要值班员ID")
    private Long confidentialId;

    /** 机要值班员电话 */
    @Excel(name = "机要值班员电话")
    private String confidentialPhone;

    /** 政治工作部值班员 */
    @Excel(name = "政治工作部值班员")
    private String politicalName;

    /** 政治工作部值班员ID */
    @Excel(name = "政治工作部值班员ID")
    private Long politicalId;

    /** 政治工作部值班员电话 */
    @Excel(name = "政治工作部值班员电话")
    private String politicalPhone;

    /** 保障部值班员 */
    @Excel(name = "保障部值班员")
    private String guaranteeName;

    /** 保障部值班员ID */
    @Excel(name = "保障部值班员ID")
    private Long guaranteeId;

    /** 保障部值班员电话 */
    @Excel(name = "保障部值班员电话")
    private String guaranteePhone;

    /** 值班通信员 */
    @Excel(name = "值班通信员")
    private String messengerName;

    /** 值班通信员ID */
    @Excel(name = "值班通信员ID")
    private Long messengerId;

    /** 值班通信员电话 */
    @Excel(name = "值班通信员电话")
    private String messengerPhone;

    /** 值班分队 */
    @Excel(name = "值班分队")
    private String detachmentName;

    /** 值班分ID */
    @Excel(name = "值班分ID")
    private Long detachmentId;

    /** 值班分队人数 */
    @Excel(name = "值班分队人数")
    private Long detachmentNumber;

    /** 值班分队指挥员 */
    @Excel(name = "值班分队指挥员")
    private String detachmentPeopleName;

    /** 值班分队指挥员ID */
    @Excel(name = "值班分队指挥员ID")
    private Long detachmentPeopleId;

    /** 值班分队指挥员电话 */
    @Excel(name = "值班分队指挥员电话")
    private String detachmentPeoplePhone;

    /** 值班情况 */
    @Excel(name = "值班情况")
    private String zhibanqingkaung;

    /** 兵力情况 */
    @Excel(name = "兵力情况")
    private String bingliqingkuang;



    /**
     * 值班时间区间
     */
    private List<String> timequjian;

    private List<BdglTeamdutyFu> teamduties;

    public List<BdglTeamdutyFu> getTeamduties() {
        return teamduties;
    }

    public void setTeamduties(List<BdglTeamdutyFu> teamduties) {
        this.teamduties = teamduties;
    }

    public List<String> getTimequjian() {
        return timequjian;
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
    public void setChiefDutyName(String chiefDutyName)
    {
        this.chiefDutyName = chiefDutyName;
    }

    public String getChiefDutyName()
    {
        return chiefDutyName;
    }
    public void setChiefDutyId(Long chiefDutyId)
    {
        this.chiefDutyId = chiefDutyId;
    }

    public Long getChiefDutyId()
    {
        return chiefDutyId;
    }
    public void setChiefDutyPhone(String chiefDutyPhone)
    {
        this.chiefDutyPhone = chiefDutyPhone;
    }

    public String getChiefDutyPhone()
    {
        return chiefDutyPhone;
    }
    public void setCombatDutyName(String combatDutyName)
    {
        this.combatDutyName = combatDutyName;
    }

    public String getCombatDutyName()
    {
        return combatDutyName;
    }
    public void setCombatDutyId(Long combatDutyId)
    {
        this.combatDutyId = combatDutyId;
    }

    public Long getCombatDutyId()
    {
        return combatDutyId;
    }
    public void setCombatDutyPhone(String combatDutyPhone)
    {
        this.combatDutyPhone = combatDutyPhone;
    }

    public String getCombatDutyPhone()
    {
        return combatDutyPhone;
    }
    public void setDutyOffierName(String dutyOffierName)
    {
        this.dutyOffierName = dutyOffierName;
    }

    public String getDutyOffierName()
    {
        return dutyOffierName;
    }
    public void setDutyOffierId(Long dutyOffierId)
    {
        this.dutyOffierId = dutyOffierId;
    }

    public Long getDutyOffierId()
    {
        return dutyOffierId;
    }
    public void setDutyOffierPhone(String dutyOffierPhone)
    {
        this.dutyOffierPhone = dutyOffierPhone;
    }

    public String getDutyOffierPhone()
    {
        return dutyOffierPhone;
    }
    public void setConfidentialName(String confidentialName)
    {
        this.confidentialName = confidentialName;
    }

    public String getConfidentialName()
    {
        return confidentialName;
    }
    public void setConfidentialId(Long confidentialId)
    {
        this.confidentialId = confidentialId;
    }

    public Long getConfidentialId()
    {
        return confidentialId;
    }
    public void setConfidentialPhone(String confidentialPhone)
    {
        this.confidentialPhone = confidentialPhone;
    }

    public String getConfidentialPhone()
    {
        return confidentialPhone;
    }
    public void setPoliticalName(String politicalName)
    {
        this.politicalName = politicalName;
    }

    public String getPoliticalName()
    {
        return politicalName;
    }
    public void setPoliticalId(Long politicalId)
    {
        this.politicalId = politicalId;
    }

    public Long getPoliticalId()
    {
        return politicalId;
    }
    public void setPoliticalPhone(String politicalPhone)
    {
        this.politicalPhone = politicalPhone;
    }

    public String getPoliticalPhone()
    {
        return politicalPhone;
    }
    public void setGuaranteeName(String guaranteeName)
    {
        this.guaranteeName = guaranteeName;
    }

    public String getGuaranteeName()
    {
        return guaranteeName;
    }
    public void setGuaranteeId(Long guaranteeId)
    {
        this.guaranteeId = guaranteeId;
    }

    public Long getGuaranteeId()
    {
        return guaranteeId;
    }
    public void setGuaranteePhone(String guaranteePhone)
    {
        this.guaranteePhone = guaranteePhone;
    }

    public String getGuaranteePhone()
    {
        return guaranteePhone;
    }
    public void setMessengerName(String messengerName)
    {
        this.messengerName = messengerName;
    }

    public String getMessengerName()
    {
        return messengerName;
    }
    public void setMessengerId(Long messengerId)
    {
        this.messengerId = messengerId;
    }

    public Long getMessengerId()
    {
        return messengerId;
    }
    public void setMessengerPhone(String messengerPhone)
    {
        this.messengerPhone = messengerPhone;
    }

    public String getMessengerPhone()
    {
        return messengerPhone;
    }
    public void setDetachmentName(String detachmentName)
    {
        this.detachmentName = detachmentName;
    }

    public String getDetachmentName()
    {
        return detachmentName;
    }
    public void setDetachmentId(Long detachmentId)
    {
        this.detachmentId = detachmentId;
    }

    public Long getDetachmentId()
    {
        return detachmentId;
    }
    public void setDetachmentNumber(Long detachmentNumber)
    {
        this.detachmentNumber = detachmentNumber;
    }

    public Long getDetachmentNumber()
    {
        return detachmentNumber;
    }
    public void setDetachmentPeopleName(String detachmentPeopleName)
    {
        this.detachmentPeopleName = detachmentPeopleName;
    }

    public String getDetachmentPeopleName()
    {
        return detachmentPeopleName;
    }
    public void setDetachmentPeopleId(Long detachmentPeopleId)
    {
        this.detachmentPeopleId = detachmentPeopleId;
    }

    public Long getDetachmentPeopleId()
    {
        return detachmentPeopleId;
    }
    public void setDetachmentPeoplePhone(String detachmentPeoplePhone)
    {
        this.detachmentPeoplePhone = detachmentPeoplePhone;
    }

    public String getDetachmentPeoplePhone()
    {
        return detachmentPeoplePhone;
    }
    public void setZhibanqingkaung(String zhibanqingkaung)
    {
        this.zhibanqingkaung = zhibanqingkaung;
    }

    public String getZhibanqingkaung()
    {
        return zhibanqingkaung;
    }
    public void setBingliqingkuang(String bingliqingkuang)
    {
        this.bingliqingkuang = bingliqingkuang;
    }

    public String getBingliqingkuang()
    {
        return bingliqingkuang;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("attendedTime", getAttendedTime())
                .append("chiefDutyName", getChiefDutyName())
                .append("chiefDutyId", getChiefDutyId())
                .append("chiefDutyPhone", getChiefDutyPhone())
                .append("combatDutyName", getCombatDutyName())
                .append("combatDutyId", getCombatDutyId())
                .append("combatDutyPhone", getCombatDutyPhone())
                .append("dutyOffierName", getDutyOffierName())
                .append("dutyOffierId", getDutyOffierId())
                .append("dutyOffierPhone", getDutyOffierPhone())
                .append("confidentialName", getConfidentialName())
                .append("confidentialId", getConfidentialId())
                .append("confidentialPhone", getConfidentialPhone())
                .append("politicalName", getPoliticalName())
                .append("politicalId", getPoliticalId())
                .append("politicalPhone", getPoliticalPhone())
                .append("guaranteeName", getGuaranteeName())
                .append("guaranteeId", getGuaranteeId())
                .append("guaranteePhone", getGuaranteePhone())
                .append("messengerName", getMessengerName())
                .append("messengerId", getMessengerId())
                .append("messengerPhone", getMessengerPhone())
                .append("detachmentName", getDetachmentName())
                .append("detachmentId", getDetachmentId())
                .append("detachmentNumber", getDetachmentNumber())
                .append("detachmentPeopleName", getDetachmentPeopleName())
                .append("detachmentPeopleId", getDetachmentPeopleId())
                .append("detachmentPeoplePhone", getDetachmentPeoplePhone())
                .append("zhibanqingkaung", getZhibanqingkaung())
                .append("bingliqingkuang", getBingliqingkuang())
                .toString();
    }
}
