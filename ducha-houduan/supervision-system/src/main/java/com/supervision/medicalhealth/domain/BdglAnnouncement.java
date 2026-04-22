package com.supervision.medicalhealth.domain;

import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


import java.util.Date;

/**
 * 疫情通知公告对象 bdgl_announcement
 * 
 * @author hr
 * @date 2022-02-14
 */
public class BdglAnnouncement extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 文件 */
    @Excel(name = "文件")
    private String file;

    /** 文件名称 */
    @Excel(name = "文件名称")
    private String fileName;
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createtime;
    /**修改时间*/
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatetime;

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
    public void setFile(String file) 
    {
        this.file = file;
    }

    public String getFile() 
    {
        return file;
    }
    public void setFileName(String fileName) 
    {
        this.fileName = fileName;
    }

    public String getFileName() 
    {
        return fileName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("file", getFile())
            .append("fileName", getFileName())
            .append("createtime", getCreatetime())
            .append("updatetime", getUpdatetime())
            .toString();
    }
}
