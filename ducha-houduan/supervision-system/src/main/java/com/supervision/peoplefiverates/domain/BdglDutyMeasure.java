package com.supervision.peoplefiverates.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 六量对象 bdgl_duty_measure
 * 
 * @author supervision
 * @date 2022-03-19
 */
public class BdglDutyMeasure extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 油料战术储备 */
    @Excel(name = "油料战术储备")
    private BigDecimal ylZscb;

    /** 油料储备阈值 */
    @Excel(name = "油料储备阈值")
    private BigDecimal ylCxyz;

    /** 油料自持能力 */
    @Excel(name = "油料自持能力")
    private BigDecimal ylZcnl;

    /** 军需物资战术储备 */
    @Excel(name = "军需物资战术储备")
    private BigDecimal jxZscb;

    /** 军需储备阈值 */
    @Excel(name = "军需储备阈值")
    private BigDecimal jxCxyz;

    /** 军需自持能力 */
    @Excel(name = "军需自持能力")
    private BigDecimal jxZcnl;

    /** 战备金战术存储 */
    @Excel(name = "战备金战术存储")
    private BigDecimal zbjZscb;

    /** 卫生物资战术储备 */
    @Excel(name = "卫生物资战术储备")
    private BigDecimal wswzZscb;

    /** 卫生物资储备阈值 */
    @Excel(name = "卫生物资储备阈值")
    private BigDecimal wswzCxyz;

    /** 弹药储存量 */
    @Excel(name = "弹药储存量")
    private BigDecimal dyCcl;

    /** 维修器材储存量 */
    @Excel(name = "维修器材储存量")
    private BigDecimal wxCcl;

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setYlZscb(BigDecimal ylZscb) 
    {
        this.ylZscb = ylZscb;
    }

    public BigDecimal getYlZscb() 
    {
        return ylZscb;
    }
    public void setYlCxyz(BigDecimal ylCxyz) 
    {
        this.ylCxyz = ylCxyz;
    }

    public BigDecimal getYlCxyz() 
    {
        return ylCxyz;
    }
    public void setYlZcnl(BigDecimal ylZcnl) 
    {
        this.ylZcnl = ylZcnl;
    }

    public BigDecimal getYlZcnl() 
    {
        return ylZcnl;
    }
    public void setJxZscb(BigDecimal jxZscb) 
    {
        this.jxZscb = jxZscb;
    }

    public BigDecimal getJxZscb() 
    {
        return jxZscb;
    }
    public void setJxCxyz(BigDecimal jxCxyz) 
    {
        this.jxCxyz = jxCxyz;
    }

    public BigDecimal getJxCxyz() 
    {
        return jxCxyz;
    }
    public void setJxZcnl(BigDecimal jxZcnl) 
    {
        this.jxZcnl = jxZcnl;
    }

    public BigDecimal getJxZcnl() 
    {
        return jxZcnl;
    }
    public void setZbjZscb(BigDecimal zbjZscb) 
    {
        this.zbjZscb = zbjZscb;
    }

    public BigDecimal getZbjZscb() 
    {
        return zbjZscb;
    }
    public void setWswzZscb(BigDecimal wswzZscb) 
    {
        this.wswzZscb = wswzZscb;
    }

    public BigDecimal getWswzZscb() 
    {
        return wswzZscb;
    }
    public void setWswzCxyz(BigDecimal wswzCxyz) 
    {
        this.wswzCxyz = wswzCxyz;
    }

    public BigDecimal getWswzCxyz() 
    {
        return wswzCxyz;
    }
    public void setDyCcl(BigDecimal dyCcl) 
    {
        this.dyCcl = dyCcl;
    }

    public BigDecimal getDyCcl() 
    {
        return dyCcl;
    }
    public void setWxCcl(BigDecimal wxCcl) 
    {
        this.wxCcl = wxCcl;
    }

    public BigDecimal getWxCcl() 
    {
        return wxCcl;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("ylZscb", getYlZscb())
            .append("ylCxyz", getYlCxyz())
            .append("ylZcnl", getYlZcnl())
            .append("jxZscb", getJxZscb())
            .append("jxCxyz", getJxCxyz())
            .append("jxZcnl", getJxZcnl())
            .append("zbjZscb", getZbjZscb())
            .append("wswzZscb", getWswzZscb())
            .append("wswzCxyz", getWswzCxyz())
            .append("dyCcl", getDyCcl())
            .append("wxCcl", getWxCcl())
            .toString();
    }
}
