package com.supervision.grassrootsregistration.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 一周工作食普图对象 bdgl_cookbook_prc
 * 
 * @author ruoyi
 * @date 2023-03-08
 */
public class BdglCookbookPrc extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 图片存放地址 */
    @Excel(name = "图片存放地址")
    private String urlImage;

    /** 上传人 */
    @Excel(name = "上传人")
    private String upPeople;

    /** 上传时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "上传时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date upTime;

    /** 上传单位 */
    @Excel(name = "上传单位")
    private String upUnit;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUrlImage(String urlImage) 
    {
        this.urlImage = urlImage;
    }

    public String getUrlImage() 
    {
        return urlImage;
    }
    public void setUpPeople(String upPeople) 
    {
        this.upPeople = upPeople;
    }

    public String getUpPeople() 
    {
        return upPeople;
    }
    public void setUpTime(Date upTime) 
    {
        this.upTime = upTime;
    }

    public Date getUpTime() 
    {
        return upTime;
    }
    public void setUpUnit(String upUnit) 
    {
        this.upUnit = upUnit;
    }

    public String getUpUnit() 
    {
        return upUnit;
    }
    public void setRemarks(String remarks) 
    {
        this.remarks = remarks;
    }

    public String getRemarks() 
    {
        return remarks;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("urlImage", getUrlImage())
            .append("upPeople", getUpPeople())
            .append("upTime", getUpTime())
            .append("upUnit", getUpUnit())
            .append("remarks", getRemarks())
            .toString();
    }
}
