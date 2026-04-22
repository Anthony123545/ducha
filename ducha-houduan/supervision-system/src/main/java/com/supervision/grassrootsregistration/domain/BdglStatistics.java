package com.supervision.grassrootsregistration.domain;


import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 军事训练月统计班对象 bdgl_statistics
 *
 * @author supervision
 * @date 2022-05-21
 */
public class BdglStatistics extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Integer id;

    /** 单位 */
    @Excel(name = "单位")
    private Integer unitId;

    /** 标题 */
    @Excel(name = "标题")
    private String actualArrival;

    /** 单位名称 */
    @Excel(name = "单位名称")
    private String unitName;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 文件名称 */
    @Excel(name = "文件名称")
    private String fileName;

    /** 文件路径 */
    @Excel(name = "文件路径")
    private String filePath;

    /** 时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date time;

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }
    public void setUnitId(Integer unitId)
    {
        this.unitId = unitId;
    }

    public Integer getUnitId()
    {
        return unitId;
    }

    public String getActualArrival() {
        return actualArrival;
    }

    public void setActualArrival(String actualArrival) {
        this.actualArrival = actualArrival;
    }

    public void setUnitName(String unitName)
    {
        this.unitName = unitName;
    }

    public String getUnitName()
    {
        return unitName;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
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
    public void setTime(Date time)
    {
        this.time = time;
    }

    public Date getTime()
    {
        return time;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("unitId", getUnitId())
                .append("actualArrival", getActualArrival())
                .append("unitName", getUnitName())
                .append("title", getTitle())
                .append("fileName", getFileName())
                .append("filePath", getFilePath())
                .append("time", getTime())
                .toString();
    }
}