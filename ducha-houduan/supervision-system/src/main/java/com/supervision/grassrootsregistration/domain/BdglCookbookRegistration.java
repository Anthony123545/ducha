package com.supervision.grassrootsregistration.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 食谱登记对象 bdgl_cookbook_registration
 * 
 * @author supervision
 * @date 2022-03-02
 */
public class BdglCookbookRegistration extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Integer cookbookId;

    /** 食品分类 */
    @Excel(name = "食品分类")
    private String typelist;

    /** 一日三餐 */
    @Excel(name = "一日三餐")
    private String timelist;

    /** 星期 */
    @Excel(name = "星期")
    private String weeklist;

    /** 食品 */
    @Excel(name = "食品")
    private String cookcategoryIds;

    /** 炊事员 */
    @Excel(name = "炊事员")
    private String peopleIds;
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createtime;
    /**修改时间*/
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatetime;
    /** 食品名称 */
    @Excel(name = "食品名称")
    private String cookcategoryNames;

    /** 厨师名称 */
    @Excel(name = "厨师名称")
    private String peopleNames;

    private String[] shiWuId;

    private String[] chuShiId;


    private BdglCookbookRegistration cookbookRegistration;

    public String getCookcategoryNames() {
        return cookcategoryNames;
    }

    public void setCookcategoryNames(String cookcategoryNames) {
        this.cookcategoryNames = cookcategoryNames;
    }

    public String getPeopleNames() {
        return peopleNames;
    }

    public void setPeopleNames(String peopleNames) {
        this.peopleNames = peopleNames;
    }

    public BdglCookbookRegistration getCookbookRegistration() {
        return cookbookRegistration;
    }

    public void setCookbookRegistration(BdglCookbookRegistration cookbookRegistration) {
        this.cookbookRegistration = cookbookRegistration;
    }

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
    public void setCookbookId(Integer cookbookId) 
    {
        this.cookbookId = cookbookId;
    }

    public Integer getCookbookId() 
    {
        return cookbookId;
    }
    public void setTypelist(String typelist) 
    {
        this.typelist = typelist;
    }

    public String getTypelist() 
    {
        return typelist;
    }
    public void setTimelist(String timelist) 
    {
        this.timelist = timelist;
    }

    public String getTimelist() 
    {
        return timelist;
    }
    public void setWeeklist(String weeklist) 
    {
        this.weeklist = weeklist;
    }

    public String getWeeklist() 
    {
        return weeklist;
    }
    public void setCookcategoryIds(String cookcategoryIds) 
    {
        this.cookcategoryIds = cookcategoryIds;
    }

    public String getCookcategoryIds() 
    {
        return cookcategoryIds;
    }
    public void setPeopleIds(String peopleIds) 
    {
        this.peopleIds = peopleIds;
    }

    public String getPeopleIds() 
    {
        return peopleIds;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("cookbookId", getCookbookId())
            .append("typelist", getTypelist())
            .append("timelist", getTimelist())
            .append("weeklist", getWeeklist())
            .append("cookcategoryIds", getCookcategoryIds())
            .append("peopleIds", getPeopleIds())
            .append("createtime", getCreatetime())
            .append("updatetime", getUpdatetime())
            .toString();
    }
}
