package com.supervision.combatduty.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 执行任务附表对象 bdgl_taskduty_fu
 * 
 * @author supervision
 * @date 2022-05-06
 */
public class BdglTaskdutyFu extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 文件名称 */
    @Excel(name = "文件名称")
    private String filename;

    /** 文件路径 */
    @Excel(name = "文件路径")
    private String filepath;

    /** 主表id */
    @Excel(name = "主表id")
    private Long bdglTaskdutyId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setFilename(String filename) 
    {
        this.filename = filename;
    }

    public String getFilename() 
    {
        return filename;
    }
    public void setFilepath(String filepath) 
    {
        this.filepath = filepath;
    }

    public String getFilepath() 
    {
        return filepath;
    }
    public void setBdglTaskdutyId(Long bdglTaskdutyId) 
    {
        this.bdglTaskdutyId = bdglTaskdutyId;
    }

    public Long getBdglTaskdutyId() 
    {
        return bdglTaskdutyId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("filename", getFilename())
            .append("filepath", getFilepath())
            .append("bdglTaskdutyId", getBdglTaskdutyId())
            .toString();
    }
}
