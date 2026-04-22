package com.supervision.monitoring.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 安防监控对象 camera_controller
 * 
 * @author supervision
 * @date 2022-04-22
 */
public class CameraController extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 摄像头名称 */
    @Excel(name = "摄像头名称")
    private String cname;

    /** 访问地址 */
    @Excel(name = "访问地址")
    private String caddr;

    /** 状态 */
    @Excel(name = "状态")
    private String cstatus;

    /** 单位id */
    @Excel(name = "单位id")
    private Long unitid;

    /** 单位名称 */
    @Excel(name = "单位名称")
    private String unitname;

    /** 存入菜单ID */
    @Excel(name = "存入菜单ID")
    private Integer powerArea;

    private Integer belongLocal;

    public Integer getBelongLocal() {
        return belongLocal;
    }

    public void setBelongLocal(Integer belongLocal) {
        this.belongLocal = belongLocal;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCname(String cname) 
    {
        this.cname = cname;
    }

    public String getCname() 
    {
        return cname;
    }
    public void setCaddr(String caddr) 
    {
        this.caddr = caddr;
    }

    public String getCaddr() 
    {
        return caddr;
    }
    public void setCstatus(String cstatus) 
    {
        this.cstatus = cstatus;
    }

    public String getCstatus() 
    {
        return cstatus;
    }
    public void setUnitid(Long unitid) 
    {
        this.unitid = unitid;
    }

    public Long getUnitid() 
    {
        return unitid;
    }
    public void setUnitname(String unitname) 
    {
        this.unitname = unitname;
    }

    public String getUnitname() 
    {
        return unitname;
    }
    public void setPowerArea(Integer powerArea) 
    {
        this.powerArea = powerArea;
    }

    public Integer getPowerArea() 
    {
        return powerArea;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("cname", getCname())
            .append("caddr", getCaddr())
            .append("cstatus", getCstatus())
            .append("createtime", getCreatetime())
            .append("unitid", getUnitid())
            .append("unitname", getUnitname())
            .append("powerArea", getPowerArea())
            .toString();
    }
}
