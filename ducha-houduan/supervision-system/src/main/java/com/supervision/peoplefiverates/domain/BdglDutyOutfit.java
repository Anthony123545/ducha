package com.supervision.peoplefiverates.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 装备五率对象 bdgl_duty_outfit
 * 
 * @author supervision
 * @date 2022-03-19
 */
public class BdglDutyOutfit extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 装备编制数 */
    @Excel(name = "装备编制数")
    private Integer zbbz;

    /** 装备实有数 */
    @Excel(name = "装备实有数")
    private Integer zbsy;

    /** 装备可用数 */
    @Excel(name = "装备可用数")
    private Integer zbky;

    /** 实际配套数 */
    @Excel(name = "实际配套数")
    private Integer sjpt;

    /** 编制配套数 */
    @Excel(name = "编制配套数")
    private Integer bzpt;

    /** 达到装备储存标准配套数 */
    @Excel(name = "达到装备储存标准配套数")
    private Integer cczb;

    /** 装备出动数 */
    @Excel(name = "装备出动数")
    private Integer zbcd;

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setZbbz(Integer zbbz) 
    {
        this.zbbz = zbbz;
    }

    public Integer getZbbz() 
    {
        return zbbz;
    }
    public void setZbsy(Integer zbsy) 
    {
        this.zbsy = zbsy;
    }

    public Integer getZbsy() 
    {
        return zbsy;
    }
    public void setZbky(Integer zbky) 
    {
        this.zbky = zbky;
    }

    public Integer getZbky() 
    {
        return zbky;
    }
    public void setSjpt(Integer sjpt) 
    {
        this.sjpt = sjpt;
    }

    public Integer getSjpt() 
    {
        return sjpt;
    }
    public void setBzpt(Integer bzpt) 
    {
        this.bzpt = bzpt;
    }

    public Integer getBzpt() 
    {
        return bzpt;
    }
    public void setCczb(Integer cczb) 
    {
        this.cczb = cczb;
    }

    public Integer getCczb() 
    {
        return cczb;
    }
    public void setZbcd(Integer zbcd) 
    {
        this.zbcd = zbcd;
    }

    public Integer getZbcd() 
    {
        return zbcd;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("zbbz", getZbbz())
            .append("zbsy", getZbsy())
            .append("zbky", getZbky())
            .append("sjpt", getSjpt())
            .append("bzpt", getBzpt())
            .append("cczb", getCczb())
            .append("zbcd", getZbcd())
            .toString();
    }
}
