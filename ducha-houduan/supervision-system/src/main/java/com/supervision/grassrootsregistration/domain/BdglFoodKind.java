package com.supervision.grassrootsregistration.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 实物检查验收对象 bdgl_food_kind
 *
 * @author supervision
 * @date 2022-03-11
 */
public class BdglFoodKind extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 区别 */
    @Excel(name = "区别")
    private String distinctions;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 重量 */
    @Excel(name = "重量")
    private BigDecimal weight;

    /** 单价 */
    @Excel(name = "单价")
    private BigDecimal unitPrice;

    /** 金额 */
    @Excel(name = "金额")
    private BigDecimal money;

    /** 购买或自产 */
    @Excel(name = "购买或自产")
    private String types;

    /** $column.columnComment */
    @Excel(name = "购买或自产")
    private Integer foodId;

    /** 采购时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "采购时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date time;

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }
    public void setDistinctions(String distinctions)
    {
        this.distinctions = distinctions;
    }

    public String getDistinctions()
    {
        return distinctions;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setWeight(BigDecimal weight)
    {
        this.weight = weight;
    }

    public BigDecimal getWeight()
    {
        return weight;
    }
    public void setUnitPrice(BigDecimal unitPrice)
    {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getUnitPrice()
    {
        return unitPrice;
    }
    public void setMoney(BigDecimal money)
    {
        this.money = money;
    }

    public BigDecimal getMoney()
    {
        return money;
    }
    public void setTypes(String types)
    {
        this.types = types;
    }

    public String getTypes()
    {
        return types;
    }
    public void setFoodId(Integer foodId)
    {
        this.foodId = foodId;
    }

    public Integer getFoodId()
    {
        return foodId;
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
                .append("distinctions", getDistinctions())
                .append("name", getName())
                .append("weight", getWeight())
                .append("unitPrice", getUnitPrice())
                .append("money", getMoney())
                .append("types", getTypes())
                .append("remark", getRemark())
                .append("foodId", getFoodId())
                .append("time", getTime())
                .append("createtime", getCreatetime())
                .append("updatetime", getUpdatetime())
                .toString();
    }
}