package com.supervision.peopleChuRu.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 特殊审批记录对象 bdgl_leave_fu
 * 
 * @author supervision
 * @date 2022-03-18
 */
public class BdglLeaveFu extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 请假表id */
    @Excel(name = "请假表id")
    private Long leaveId;

    /** 审批人 */
    @Excel(name = "审批人")
    private String people;

    /** 审批状态 */
    @Excel(name = "审批状态")
    private Long spState;

    /** 审批原因 */
    @Excel(name = "审批原因")
    private String specialApproval;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setLeaveId(Long leaveId) 
    {
        this.leaveId = leaveId;
    }

    public Long getLeaveId() 
    {
        return leaveId;
    }
    public void setPeople(String people) 
    {
        this.people = people;
    }

    public String getPeople() 
    {
        return people;
    }
    public void setSpState(Long spState) 
    {
        this.spState = spState;
    }

    public Long getSpState() 
    {
        return spState;
    }
    public void setSpecialApproval(String specialApproval) 
    {
        this.specialApproval = specialApproval;
    }

    public String getSpecialApproval() 
    {
        return specialApproval;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("leaveId", getLeaveId())
            .append("people", getPeople())
            .append("spState", getSpState())
            .append("specialApproval", getSpecialApproval())
            .append("createtime", getCreatetime())
            .toString();
    }
}
