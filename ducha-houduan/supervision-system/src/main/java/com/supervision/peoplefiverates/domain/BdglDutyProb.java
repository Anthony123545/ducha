package com.supervision.peoplefiverates.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 人员五率对象 bdgl_duty_prob
 * 
 * @author supervision
 * @date 2022-03-19
 */
public class BdglDutyProb extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 编制人数 */
    @Excel(name = "编制人数")
    private Integer bzrs;

    /** 实际人数 */
    @Excel(name = "实际人数")
    private Integer sjrs;

    /** 在位人数 */
    @Excel(name = "在位人数")
    private Integer zwrs;

    /** 对口人数 */
    @Excel(name = "对口人数")
    private Integer dkrs;

    /** 出动人数 */
    @Excel(name = "出动人数")
    private Integer cdrs;

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setBzrs(Integer bzrs) 
    {
        this.bzrs = bzrs;
    }

    public Integer getBzrs() 
    {
        return bzrs;
    }
    public void setSjrs(Integer sjrs) 
    {
        this.sjrs = sjrs;
    }

    public Integer getSjrs() 
    {
        return sjrs;
    }
    public void setZwrs(Integer zwrs) 
    {
        this.zwrs = zwrs;
    }

    public Integer getZwrs() 
    {
        return zwrs;
    }
    public void setDkrs(Integer dkrs) 
    {
        this.dkrs = dkrs;
    }

    public Integer getDkrs() 
    {
        return dkrs;
    }
    public void setCdrs(Integer cdrs) 
    {
        this.cdrs = cdrs;
    }

    public Integer getCdrs() 
    {
        return cdrs;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("bzrs", getBzrs())
            .append("sjrs", getSjrs())
            .append("zwrs", getZwrs())
            .append("dkrs", getDkrs())
            .append("cdrs", getCdrs())
            .toString();
    }
}
