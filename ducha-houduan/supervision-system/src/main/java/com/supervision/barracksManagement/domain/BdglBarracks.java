package com.supervision.barracksManagement.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 营房档案对象 bdgl_barracks
 * 
 * @author supervision
 * @date 2022-03-02
 */
public class BdglBarracks extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 坐落号 */
    @Excel(name = "坐落号")
    private String number;

    /** 分栋号 */
    @Excel(name = "分栋号")
    private String number1;

    /** 分栋名称 */
    @Excel(name = "分栋名称")
    private String name;

    /** 营房用途 */
    @Excel(name = "营房用途")
    private String behoof;

    /** 利用现状 */
    @Excel(name = "利用现状")
    private String statusQuo;

    /** 建筑年代 */
    @Excel(name = "建筑年代")
    private String period;

    /** 建筑面积 */
    @Excel(name = "建筑面积")
    private String area;

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setNumber(String number) 
    {
        this.number = number;
    }

    public String getNumber() 
    {
        return number;
    }
    public void setNumber1(String number1) 
    {
        this.number1 = number1;
    }

    public String getNumber1() 
    {
        return number1;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setBehoof(String behoof) 
    {
        this.behoof = behoof;
    }

    public String getBehoof() 
    {
        return behoof;
    }
    public void setStatusQuo(String statusQuo) 
    {
        this.statusQuo = statusQuo;
    }

    public String getStatusQuo() 
    {
        return statusQuo;
    }
    public void setPeriod(String period) 
    {
        this.period = period;
    }

    public String getPeriod() 
    {
        return period;
    }
    public void setArea(String area) 
    {
        this.area = area;
    }

    public String getArea() 
    {
        return area;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("number", getNumber())
            .append("number1", getNumber1())
            .append("name", getName())
            .append("behoof", getBehoof())
            .append("statusQuo", getStatusQuo())
            .append("period", getPeriod())
            .append("area", getArea())
            .append("remark", getRemark())
            .append("createtime", getCreatetime())
            .append("updatetime", getUpdatetime())
            .toString();
    }
}
