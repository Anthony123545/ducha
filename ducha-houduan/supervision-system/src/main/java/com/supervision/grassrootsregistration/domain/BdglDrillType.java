package com.supervision.grassrootsregistration.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 训练类别对象 bdgl_drill_type
 * 
 * @author ruoyi
 * @date 2022-05-21
 */
public class BdglDrillType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 部门id */
    private Long id;

    /** 父部门id */
    @Excel(name = "父部门id")
    private Long parentId;

    /** 祖级列表 */
    @Excel(name = "祖级列表")
    private String ancestors;


    private List<BdglDrillType> children;

    public List<BdglDrillType> getChildren() {
        return children;
    }

    public void setChildren(List<BdglDrillType> children) {
        this.children = children;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setParentId(Long parentId) 
    {
        this.parentId = parentId;
    }

    public Long getParentId() 
    {
        return parentId;
    }
    public void setAncestors(String ancestors) 
    {
        this.ancestors = ancestors;
    }

    public String getAncestors() 
    {
        return ancestors;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("parentId", getParentId())
            .append("ancestors", getAncestors())
            .toString();
    }
}