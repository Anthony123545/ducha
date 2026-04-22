package com.supervision.regulations.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 规章制度阅读量统计对象 bdgl_file_resd
 *
 * @author supervision
 * @date 2022-03-18
 */
public class BdglFileResd extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 文件类别 */
    @Excel(name = "文件类别")
    private String documentCategory;

    /** 月份 */
    @Excel(name = "月份")
    private String month;

    /** 阅读次数 */
    @Excel(name = "阅读次数")
    private Long readNumber;

    /** 文件夹id */
    @Excel(name = "文件夹id")
    private Long directoryId;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setDocumentCategory(String documentCategory)
    {
        this.documentCategory = documentCategory;
    }

    public String getDocumentCategory()
    {
        return documentCategory;
    }
    public void setMonth(String month)
    {
        this.month = month;
    }

    public String getMonth()
    {
        return month;
    }
    public void setReadNumber(Long readNumber)
    {
        this.readNumber = readNumber;
    }

    public Long getReadNumber()
    {
        return readNumber;
    }
    public void setDirectoryId(Long directoryId)
    {
        this.directoryId = directoryId;
    }

    public Long getDirectoryId()
    {
        return directoryId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("documentCategory", getDocumentCategory())
                .append("month", getMonth())
                .append("readNumber", getReadNumber())
                .append("directoryId", getDirectoryId())
                .toString();
    }
}