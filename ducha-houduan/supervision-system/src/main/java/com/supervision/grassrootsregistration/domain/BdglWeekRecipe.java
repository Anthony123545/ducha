package com.supervision.grassrootsregistration.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 一周食谱对象 bdgl_week_recipe
 *
 * @author supervision
 * @date 2026-03-27
 */
public class BdglWeekRecipe extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 ID */
    private Integer id;

    /** 食谱标题 */
    @Excel(name = "食谱标题")
    private String title;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 司务长 ID */
    @Excel(name = "司务长 ID")
    private Long messOfficer;

    /** 班长 ID */
    @Excel(name = "班长 ID")
    private Long peopleId;

    /** 食谱内容（JSON 格式存储周一到周日的三餐内容） */
    @Excel(name = "食谱内容")
    private String recipeContent;

    /** 单位 ID */
    @Excel(name = "单位 ID")
    private Integer unitId;

    /** 单位首长签字 */
    @Excel(name = "单位首长签字")
    private String leaderSign;

    /** 军人委员会签字 */
    @Excel(name = "军人委员会签字")
    private String committeeSign;

    /** 司务长签字 */
    @Excel(name = "司务长签字")
    private String messOfficerSign;

    /** 创建时间 */
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createtime;

    /** 修改时间 */
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatetime;

    /** 司务长姓名 */
    @Excel(name = "司务长姓名")
    private String messOfficerName;

    /** 班长姓名 */
    @Excel(name = "班长姓名")
    private String peopleName;

    /** 单位名称 */
    @Excel(name = "单位名称")
    private String unitName;

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
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

    public void setMessOfficer(Long messOfficer)
    {
        this.messOfficer = messOfficer;
    }

    public Long getMessOfficer()
    {
        return messOfficer;
    }

    public void setPeopleId(Long peopleId)
    {
        this.peopleId = peopleId;
    }

    public Long getPeopleId()
    {
        return peopleId;
    }

    public void setRecipeContent(String recipeContent)
    {
        this.recipeContent = recipeContent;
    }

    public String getRecipeContent()
    {
        return recipeContent;
    }

    public void setUnitId(Integer unitId)
    {
        this.unitId = unitId;
    }

    public Integer getUnitId()
    {
        return unitId;
    }

    public void setLeaderSign(String leaderSign)
    {
        this.leaderSign = leaderSign;
    }

    public String getLeaderSign()
    {
        return leaderSign;
    }

    public void setCommitteeSign(String committeeSign)
    {
        this.committeeSign = committeeSign;
    }

    public String getCommitteeSign()
    {
        return committeeSign;
    }

    public void setMessOfficerSign(String messOfficerSign)
    {
        this.messOfficerSign = messOfficerSign;
    }

    public String getMessOfficerSign()
    {
        return messOfficerSign;
    }

    public void setCreatetime(Date createtime)
    {
        this.createtime = createtime;
    }

    public Date getCreatetime()
    {
        return createtime;
    }

    public void setUpdatetime(Date updatetime)
    {
        this.updatetime = updatetime;
    }

    public Date getUpdatetime()
    {
        return updatetime;
    }

    public void setMessOfficerName(String messOfficerName)
    {
        this.messOfficerName = messOfficerName;
    }

    public String getMessOfficerName()
    {
        return messOfficerName;
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
            .append("title", getTitle())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("messOfficer", getMessOfficer())
            .append("peopleId", getPeopleId())
            .append("recipeContent", getRecipeContent())
            .append("unitId", getUnitId())
            .append("leaderSign", getLeaderSign())
            .append("committeeSign", getCommitteeSign())
            .append("messOfficerSign", getMessOfficerSign())
            .append("createtime", getCreatetime())
            .append("updatetime", getUpdatetime())
            .append("messOfficerName", getMessOfficerName())
            .append("peopleName", getPeopleName())
            .append("unitName", getUnitName())
            .toString();
    }
}
