package com.supervision.medicalhealth.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 体检信息对象 bdgl_examine_details
 * 
 * @author ruoyi
 * @date 2022-08-05
 */
public class BdglExamineDetails extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 年份 */
    @Excel(name = "年份")
    private String year;

    /** 体检信息 */
    @Excel(name = "体检信息")
    private String contents;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Integer examineId;

    /** 文件名称 */
    @Excel(name = "文件名称")
    private String fileName;

    /** 文件路径 */
    @Excel(name = "文件路径")
    private String filePath;

    /** 月份 */
    @Excel(name = "月份")
    private String month;

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setYear(String year) 
    {
        this.year = year;
    }

    public String getYear() 
    {
        return year;
    }
    public void setContents(String contents) 
    {
        this.contents = contents;
    }

    public String getContents() 
    {
        return contents;
    }
    public void setExamineId(Integer examineId) 
    {
        this.examineId = examineId;
    }

    public Integer getExamineId() 
    {
        return examineId;
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
    public void setMonth(String month) 
    {
        this.month = month;
    }

    public String getMonth() 
    {
        return month;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("year", getYear())
            .append("contents", getContents())
            .append("examineId", getExamineId())
            .append("fileName", getFileName())
            .append("filePath", getFilePath())
            .append("month", getMonth())
            .append("createTime", getCreateTime())
            .toString();
    }
}