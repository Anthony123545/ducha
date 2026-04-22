package com.supervision.combatduty.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 基地值班对象 bdgl_baseduty
 *
 * @author supervision
 * @date 2022-03-10
 */
public class BdglBaseduty extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
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

    /** 作战值班员Id */
    @Excel(name = "作战值班员Id")
    private Long combatDutyId;

    /** 作战值班员手机号 */
    @Excel(name = "作战值班员手机号")
    private String combatDutyPhone;

    /** 总值班员 */
    @Excel(name = "总值班员")
    private String dutyOffierName;

    /** 总值班员ID */
    @Excel(name = "总值班员ID")
    private Long dutyOffierId;

    /** 总值班员电话 */
    @Excel(name = "总值班员电话")
    private String dutyOffierPhone;

    /** 值班分队1 */
    @Excel(name = "值班分队1")
    private String detachmentoneName;

    /** 值班分队1ID */
    @Excel(name = "值班分队1ID")
    private Long detachmentoneId;

    /** 值班分队1人数 */
    @Excel(name = "值班分队1人数")
    private Long detachmentoneNumber;

    /** 值班分队1指挥员 */
    @Excel(name = "值班分队1指挥员")
    private String detachmentonePeopleName;

    /** 值班分队1指挥员ID */
    @Excel(name = "值班分队1指挥员ID")
    private Long detachmentonePeopleId;

    /** 值班分队1指挥员电话 */
    @Excel(name = "值班分队1指挥员电话")
    private String detachmentonePeoplePhone;

    /** 值班分队2 */
    @Excel(name = "值班分队2")
    private String detachmentwoName;

    /** 值班分队2ID */
    @Excel(name = "值班分队2ID")
    private Long detachmentwoId;

    /** 值班分队2人数 */
    @Excel(name = "值班分队2人数")
    private Long detachmentwoNumber;

    /** 值班分队2指挥员 */
    @Excel(name = "值班分队2指挥员")
    private String detachmentwoPeopleName;

    /** 值班分队2指挥员ID */
    @Excel(name = "值班分队2指挥员ID")
    private Long detachmentwoPeopleId;

    /** 值班分队2指挥员电话 */
    @Excel(name = "值班分队2指挥员电话")
    private String detachmentwoPeoplePhone;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    /**
     * 值班时间区间
     */
    private List<String> timequjian;

    /**
     * 当前状态
     */
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
    public void setDetachmentoneName(String detachmentoneName)
    {
        this.detachmentoneName = detachmentoneName;
    }

    public String getDetachmentoneName()
    {
        return detachmentoneName;
    }
    public void setDetachmentoneId(Long detachmentoneId)
    {
        this.detachmentoneId = detachmentoneId;
    }

    public Long getDetachmentoneId()
    {
        return detachmentoneId;
    }
    public void setDetachmentoneNumber(Long detachmentoneNumber)
    {
        this.detachmentoneNumber = detachmentoneNumber;
    }

    public Long getDetachmentoneNumber()
    {
        return detachmentoneNumber;
    }
    public void setDetachmentonePeopleName(String detachmentonePeopleName)
    {
        this.detachmentonePeopleName = detachmentonePeopleName;
    }

    public String getDetachmentonePeopleName()
    {
        return detachmentonePeopleName;
    }
    public void setDetachmentonePeopleId(Long detachmentonePeopleId)
    {
        this.detachmentonePeopleId = detachmentonePeopleId;
    }

    public Long getDetachmentonePeopleId()
    {
        return detachmentonePeopleId;
    }
    public void setDetachmentonePeoplePhone(String detachmentonePeoplePhone)
    {
        this.detachmentonePeoplePhone = detachmentonePeoplePhone;
    }

    public String getDetachmentonePeoplePhone()
    {
        return detachmentonePeoplePhone;
    }
    public void setDetachmentwoName(String detachmentwoName)
    {
        this.detachmentwoName = detachmentwoName;
    }

    public String getDetachmentwoName()
    {
        return detachmentwoName;
    }
    public void setDetachmentwoId(Long detachmentwoId)
    {
        this.detachmentwoId = detachmentwoId;
    }

    public Long getDetachmentwoId()
    {
        return detachmentwoId;
    }
    public void setDetachmentwoNumber(Long detachmentwoNumber)
    {
        this.detachmentwoNumber = detachmentwoNumber;
    }

    public Long getDetachmentwoNumber()
    {
        return detachmentwoNumber;
    }
    public void setDetachmentwoPeopleName(String detachmentwoPeopleName)
    {
        this.detachmentwoPeopleName = detachmentwoPeopleName;
    }

    public String getDetachmentwoPeopleName()
    {
        return detachmentwoPeopleName;
    }
    public void setDetachmentwoPeopleId(Long detachmentwoPeopleId)
    {
        this.detachmentwoPeopleId = detachmentwoPeopleId;
    }

    public Long getDetachmentwoPeopleId()
    {
        return detachmentwoPeopleId;
    }
    public void setDetachmentwoPeoplePhone(String detachmentwoPeoplePhone)
    {
        this.detachmentwoPeoplePhone = detachmentwoPeoplePhone;
    }

    public String getDetachmentwoPeoplePhone()
    {
        return detachmentwoPeoplePhone;
    }
    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
    }

    public String getRemarks()
    {
        return remarks;
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
            .append("detachmentoneName", getDetachmentoneName())
            .append("detachmentoneId", getDetachmentoneId())
            .append("detachmentoneNumber", getDetachmentoneNumber())
            .append("detachmentonePeopleName", getDetachmentonePeopleName())
            .append("detachmentonePeopleId", getDetachmentonePeopleId())
            .append("detachmentonePeoplePhone", getDetachmentonePeoplePhone())
            .append("detachmentwoName", getDetachmentwoName())
            .append("detachmentwoId", getDetachmentwoId())
            .append("detachmentwoNumber", getDetachmentwoNumber())
            .append("detachmentwoPeopleName", getDetachmentwoPeopleName())
            .append("detachmentwoPeopleId", getDetachmentwoPeopleId())
            .append("detachmentwoPeoplePhone", getDetachmentwoPeoplePhone())
            .append("remarks", getRemarks())
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
