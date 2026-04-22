package com.supervision.grassrootsregistration.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 要事日记请假对象 bdgl_diary_leave
 *
 * @author ruoyi
 * @date 2022-10-19
 */
public class BdglDiaryLeave extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 职务 */
    @Excel(name = "职务")
    private String job;

    /** 事由 */
    @Excel(name = "事由")
    private String reason;

    /** 时数或天数 */
    @Excel(name = "时数或天数")
    private String leaveDuration;

    /** 准假部门 */
    @Excel(name = "准假部门")
    private String leaveApprover;

    /** 离队时间 */
    @Excel(name = "离队时间")
    private String leaveTime;

    /** 归队时间 */
    @Excel(name = "归队时间")
    private String backTime;

    /** 超时时间 */
    @Excel(name = "超时时间")
    private String overTime;

    /** 要事日记 */
    @Excel(name = "要事日记")
    private Long bdgldiaryId;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setJob(String job)
    {
        this.job = job;
    }

    public String getJob()
    {
        return job;
    }
    public void setReason(String reason)
    {
        this.reason = reason;
    }

    public String getReason()
    {
        return reason;
    }
    public void setLeaveDuration(String leaveDuration)
    {
        this.leaveDuration = leaveDuration;
    }

    public String getLeaveDuration()
    {
        return leaveDuration;
    }
    public void setLeaveApprover(String leaveApprover)
    {
        this.leaveApprover = leaveApprover;
    }

    public String getLeaveApprover()
    {
        return leaveApprover;
    }
    public void setLeaveTime(String leaveTime)
    {
        this.leaveTime = leaveTime;
    }

    public String getLeaveTime()
    {
        return leaveTime;
    }
    public void setBackTime(String backTime)
    {
        this.backTime = backTime;
    }

    public String getBackTime()
    {
        return backTime;
    }
    public void setOverTime(String overTime)
    {
        this.overTime = overTime;
    }

    public String getOverTime()
    {
        return overTime;
    }
    public void setBdgldiaryId(Long bdgldiaryId)
    {
        this.bdgldiaryId = bdgldiaryId;
    }

    public Long getBdgldiaryId()
    {
        return bdgldiaryId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("job", getJob())
            .append("reason", getReason())
            .append("leaveDuration", getLeaveDuration())
            .append("leaveApprover", getLeaveApprover())
            .append("leaveTime", getLeaveTime())
            .append("backTime", getBackTime())
            .append("createTime", getCreateTime())
            .append("overTime", getOverTime())
            .append("bdgldiaryId", getBdgldiaryId())
            .toString();
    }
}
