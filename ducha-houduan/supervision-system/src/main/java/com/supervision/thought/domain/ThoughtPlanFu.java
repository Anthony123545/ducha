package com.supervision.thought.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 计划内容对象 thought_plan_fu
 * 
 * @author supervision
 * @date 2022-04-09
 */
public class ThoughtPlanFu extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 计划id */
    @Excel(name = "计划id")
    private Long planId;

    /** 计划内容 */
    @Excel(name = "计划内容")
    private String planContent;

    /** 响应状态 */
    @Excel(name = "响应状态")
    private String isresponse;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setPlanId(Long planId) 
    {
        this.planId = planId;
    }

    public Long getPlanId() 
    {
        return planId;
    }
    public void setPlanContent(String planContent) 
    {
        this.planContent = planContent;
    }

    public String getPlanContent() 
    {
        return planContent;
    }

    public String getIsresponse() {
        return isresponse;
    }

    public void setIsresponse(String isresponse) {
        this.isresponse = isresponse;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("planId", getPlanId())
            .append("planContent", getPlanContent())
            .append("isresponse", getIsresponse())
            .toString();
    }
}
