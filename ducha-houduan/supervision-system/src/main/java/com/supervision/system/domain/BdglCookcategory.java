package com.supervision.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 食品分类对象 bdgl_cookcategory
 * 
 * @author supervision
 * @date 2022-03-08
 */
public class BdglCookcategory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 食品分类 */
    @Excel(name = "食品分类")
    private Integer typelist;

    /** 一日三餐 */
    @Excel(name = "一日三餐")
    private Integer timelist;

    /** 食品名称 */
    @Excel(name = "食品名称")
    private String name;

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setTypelist(Integer typelist) 
    {
        this.typelist = typelist;
    }

    public Integer getTypelist() 
    {
        return typelist;
    }
    public void setTimelist(Integer timelist) 
    {
        this.timelist = timelist;
    }

    public Integer getTimelist() 
    {
        return timelist;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("typelist", getTypelist())
            .append("timelist", getTimelist())
            .append("name", getName())
            .append("createtime", getCreatetime())
            .append("updatetime", getUpdatetime())
            .toString();
    }
}
