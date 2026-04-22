package com.supervision.monitoring.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 摄像头管理对象 camera_manage
 * 
 * @author lanse
 * @date 2022-10-30
 */
public class CameraManage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 名称 */
    @Excel(name = "名称")
    private String cameraname;

    /** 单位名称 */
    @Excel(name = "单位名称")
    private String unitname;

    /** 单位地址 */
    @Excel(name = "单位地址")
    private String cameraaddress;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date cretetime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCameraname(String cameraname) 
    {
        this.cameraname = cameraname;
    }

    public String getCameraname() 
    {
        return cameraname;
    }
    public void setUnitname(String unitname) 
    {
        this.unitname = unitname;
    }

    public String getUnitname() 
    {
        return unitname;
    }
    public void setCameraaddress(String cameraaddress) 
    {
        this.cameraaddress = cameraaddress;
    }

    public String getCameraaddress() 
    {
        return cameraaddress;
    }
    public void setCretetime(Date cretetime) 
    {
        this.cretetime = cretetime;
    }

    public Date getCretetime() 
    {
        return cretetime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("cameraname", getCameraname())
            .append("unitname", getUnitname())
            .append("cameraaddress", getCameraaddress())
            .append("cretetime", getCretetime())
            .toString();
    }
}
