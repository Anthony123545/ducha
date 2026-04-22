package com.supervision.peopleChuRu.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 人员借调对象 bdgl_people_borrow
 * 
 * @author supervision
 * @date 2022-03-05
 */
public class BdglPeopleBorrow extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 借调人姓名 */
    @Excel(name = "借调人姓名")
    private String transferPeople;

    /** 借调人id */
    @Excel(name = "借调人id")
    private Long transferPeopleId;

    /** 单位 */
    @Excel(name = "单位")
    private String unitName;

    /** 单位id */
    @Excel(name = "单位id")
    private Long unitId;

    /** 借调单位名称 */
    @Excel(name = "借调单位名称")
    private String transferUnitName;

    /** 借调单位id */
    @Excel(name = "借调单位id")
    private Long transferUnitId;

    /** 借调开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "借调开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 借调结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "借调结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 借调原因 */
    @Excel(name = "借调原因")
    private String transferReason;

    /** 职位 */
    @Excel(name = "职位")
    private String jobTitle;

    /** 军衔 */
    @Excel(name = "军衔")
    private String rankId;

    /** 创建人 */
    @Excel(name = "创建人")
    private String creatName;

    /** 人员状态(借调中,归位) */
    @Excel(name = "人员状态(借调中,归位)")
    private String state;

    /**创建人id*/
    private Integer userId;

    /**控制归队权限*/
    private String common;

    public String getCommon() {
        return common;
    }

    public void setCommon(String common) {
        this.common = common;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTransferPeople(String transferPeople) 
    {
        this.transferPeople = transferPeople;
    }

    public String getTransferPeople() 
    {
        return transferPeople;
    }
    public void setTransferPeopleId(Long transferPeopleId) 
    {
        this.transferPeopleId = transferPeopleId;
    }

    public Long getTransferPeopleId() 
    {
        return transferPeopleId;
    }
    public void setUnitName(String unitName) 
    {
        this.unitName = unitName;
    }

    public String getUnitName() 
    {
        return unitName;
    }
    public void setUnitId(Long unitId) 
    {
        this.unitId = unitId;
    }

    public Long getUnitId() 
    {
        return unitId;
    }
    public void setTransferUnitName(String transferUnitName) 
    {
        this.transferUnitName = transferUnitName;
    }

    public String getTransferUnitName() 
    {
        return transferUnitName;
    }
    public void setTransferUnitId(Long transferUnitId) 
    {
        this.transferUnitId = transferUnitId;
    }

    public Long getTransferUnitId() 
    {
        return transferUnitId;
    }
    public void setStartTime(Date startTime) 
    {
        this.startTime = startTime;
    }

    public Date getStartTime() 
    {
        return startTime;
    }
    public void setEndTime(Date endTime) 
    {
        this.endTime = endTime;
    }

    public Date getEndTime() 
    {
        return endTime;
    }
    public void setTransferReason(String transferReason) 
    {
        this.transferReason = transferReason;
    }

    public String getTransferReason() 
    {
        return transferReason;
    }
    public void setJobTitle(String jobTitle) 
    {
        this.jobTitle = jobTitle;
    }

    public String getJobTitle() 
    {
        return jobTitle;
    }
    public void setRankId(String rankId) 
    {
        this.rankId = rankId;
    }

    public String getRankId() 
    {
        return rankId;
    }
    public void setCreatName(String creatName) 
    {
        this.creatName = creatName;
    }

    public String getCreatName() 
    {
        return creatName;
    }
    public void setState(String state) 
    {
        this.state = state;
    }

    public String getState() 
    {
        return state;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("transferPeople", getTransferPeople())
            .append("transferPeopleId", getTransferPeopleId())
            .append("unitName", getUnitName())
            .append("unitId", getUnitId())
            .append("transferUnitName", getTransferUnitName())
            .append("transferUnitId", getTransferUnitId())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("transferReason", getTransferReason())
            .append("jobTitle", getJobTitle())
            .append("rankId", getRankId())
            .append("createTime", getCreateTime())
            .append("creatName", getCreatName())
            .append("state", getState())
            .toString();
    }
}
