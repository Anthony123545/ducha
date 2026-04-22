package com.supervision.grassrootsregistration.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 采购食材对象 bdgl_food_procurement
 *
 * @author supervision
 * @date 2022-03-11
 */
public class BdglFoodProcurement extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer id;

    /** 区别 */
    @Excel(name = "区别")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String distinction;

    /** 采购时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "采购时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Date time;

    /** 名称 */
    @Excel(name = "名称")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String name;

    /** 数量 */
    @Excel(name = "数量")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer amount;

    /** 价格 */
    @Excel(name = "价格")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private BigDecimal price;

    /** 消耗量 */
    @Excel(name = "消耗量")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private BigDecimal consumption;

    /** $column.columnComment */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer foodId;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Date creattime;

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }
    public void setDistinction(String distinction)
    {
        this.distinction = distinction;
    }

    public String getDistinction()
    {
        return distinction;
    }
    public void setTime(Date time)
    {
        this.time = time;
    }

    public Date getTime()
    {
        return time;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setAmount(Integer amount)
    {
        this.amount = amount;
    }

    public Integer getAmount()
    {
        return amount;
    }
    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    public BigDecimal getPrice()
    {
        return price;
    }
    public void setConsumption(BigDecimal consumption)
    {
        this.consumption = consumption;
    }

    public BigDecimal getConsumption()
    {
        return consumption;
    }
    public void setFoodId(Integer foodId)
    {
        this.foodId = foodId;
    }

    public Integer getFoodId()
    {
        return foodId;
    }
    public void setCreattime(Date creattime)
    {
        this.creattime = creattime;
    }

    public Date getCreattime()
    {
        return creattime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("distinction", getDistinction())
                .append("time", getTime())
                .append("name", getName())
                .append("amount", getAmount())
                .append("price", getPrice())
                .append("consumption", getConsumption())
                .append("foodId", getFoodId())
                .append("creattime", getCreattime())
                .append("updatetime", getUpdatetime())
                .toString();
    }
}