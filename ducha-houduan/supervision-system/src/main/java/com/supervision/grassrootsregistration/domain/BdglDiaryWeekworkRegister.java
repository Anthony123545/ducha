package com.supervision.grassrootsregistration.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 要事日记一周工作内容对象 bdgl_diary_weekwork_register
 * 
 * @author ruoyi
 * @date 2022-10-31
 */
public class BdglDiaryWeekworkRegister extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 要事日记id */
    @Excel(name = "要事日记id")
    private Integer bdglDiaryId;

    /** 时段 */
    @Excel(name = "时段")
    private String dateline;

    /** 内容 */
    @Excel(name = "内容")
    private String contents;

    /** 应到 */
    @Excel(name = "应到")
    private Integer shouldArrive;

    /** 实到 */
    @Excel(name = "实到")
    private Integer realTo;

    /** 到课率 */
    @Excel(name = "到课率")
    private String percentage;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setBdglDiaryId(Integer bdglDiaryId) 
    {
        this.bdglDiaryId = bdglDiaryId;
    }

    public Integer getBdglDiaryId() 
    {
        return bdglDiaryId;
    }
    public void setDateline(String dateline) 
    {
        this.dateline = dateline;
    }

    public String getDateline() 
    {
        return dateline;
    }
    public void setContents(String contents) 
    {
        this.contents = contents;
    }

    public String getContents() 
    {
        return contents;
    }
    public void setShouldArrive(Integer shouldArrive) 
    {
        this.shouldArrive = shouldArrive;
    }

    public Integer getShouldArrive() 
    {
        return shouldArrive;
    }
    public void setRealTo(Integer realTo) 
    {
        this.realTo = realTo;
    }

    public Integer getRealTo() 
    {
        return realTo;
    }
    public void setPercentage(String percentage) 
    {
        this.percentage = percentage;
    }

    public String getPercentage() 
    {
        return percentage;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("bdglDiaryId", getBdglDiaryId())
            .append("dateline", getDateline())
            .append("contents", getContents())
            .append("shouldArrive", getShouldArrive())
            .append("realTo", getRealTo())
            .append("percentage", getPercentage())
            .append("createTime", getCreateTime())
            .toString();
    }
}
