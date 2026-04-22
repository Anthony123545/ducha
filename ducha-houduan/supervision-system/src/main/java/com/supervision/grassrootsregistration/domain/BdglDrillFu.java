package com.supervision.grassrootsregistration.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 军事训练登记簿附表对象 bdgl_drill_fu
 * 
 * @author supervision
 * @date 2022-05-20
 */
public class BdglDrillFu extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 人员姓名 */
    @Excel(name = "人员姓名")
    private String peopleName;

    /** 人员id */
    @Excel(name = "人员id")
    private Long peopleId;

    /** 状态 */
    @Excel(name = "状态")
    private String state;

    /** 参训状态 */
    @Excel(name = "参训状态")
    private String trainingStatus;

    private Long drillid;

    public Long getDrillid() {
        return drillid;
    }

    public void setDrillid(Long drillid) {
        this.drillid = drillid;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setPeopleName(String peopleName) 
    {
        this.peopleName = peopleName;
    }

    public String getPeopleName() 
    {
        return peopleName;
    }
    public void setPeopleId(Long peopleId) 
    {
        this.peopleId = peopleId;
    }

    public Long getPeopleId() 
    {
        return peopleId;
    }
    public void setState(String state) 
    {
        this.state = state;
    }

    public String getState() 
    {
        return state;
    }
    public void setTrainingStatus(String trainingStatus) 
    {
        this.trainingStatus = trainingStatus;
    }

    public String getTrainingStatus() 
    {
        return trainingStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("peopleName", getPeopleName())
            .append("peopleId", getPeopleId())
            .append("state", getState())
            .append("trainingStatus", getTrainingStatus())
            .toString();
    }
}