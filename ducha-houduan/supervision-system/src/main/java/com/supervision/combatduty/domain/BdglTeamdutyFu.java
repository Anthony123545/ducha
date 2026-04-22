package com.supervision.combatduty.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 大队值班分表对象 bdgl_teamduty_fu
 * 
 * @author supervision
 * @date 2022-05-06
 */
public class BdglTeamdutyFu extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

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


    private Long bdglTeamdutyId;

    public Long getBdglTeamdutyId() {
        return bdglTeamdutyId;
    }

    public void setBdglTeamdutyId(Long bdglTeamdutyId) {
        this.bdglTeamdutyId = bdglTeamdutyId;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("detachmentName", getDetachmentName())
            .append("detachmentId", getDetachmentId())
            .append("detachmentNumber", getDetachmentNumber())
            .append("detachmentPeopleName", getDetachmentPeopleName())
            .append("detachmentPeopleId", getDetachmentPeopleId())
            .append("detachmentPeoplePhone", getDetachmentPeoplePhone())
            .toString();
    }
}
