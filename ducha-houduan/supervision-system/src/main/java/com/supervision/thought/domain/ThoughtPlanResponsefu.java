package com.supervision.thought.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;
import org.apache.poi.hssf.record.chart.SeriesTextRecord;

/**
 * 月教育响应附件对象 thought_plan_responsefu
 * 
 * @author supervision
 * @date 2022-04-08
 */
public class ThoughtPlanResponsefu extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 计划id */
    @Excel(name = "计划id")
    private Long planId;

    /** 计划内容 */
    @Excel(name = "计划内容")
    private String fileName;

    /** 计划内容 */
    @Excel(name = "计划内容")
    private String filePath;

    private String url;

    private String name;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
    public void setFileName(String fileName) 
    {
        this.fileName = fileName;
    }

    public String getFileName() 
    {
        return fileName;
    }
    public void setFilePath(String filePath) 
    {
        this.filePath = filePath;
    }

    public String getFilePath() 
    {
        return filePath;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("planId", getPlanId())
            .append("fileName", getFileName())
            .append("filePath", getFilePath())
            .toString();
    }
}
