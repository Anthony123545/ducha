package com.supervision.grassrootsregistration.domain;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 在食堂购物对象 bdgl_food_shopping
 *
 * @author supervision
 * @date 2022-03-11
 */
public class BdglFoodShopping extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer id;

    /** 姓名 */
    @Excel(name = "姓名")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer peopleId;

    /** 主食名称 */
    @Excel(name = "主食名称")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String zName;

    /** 主食重量 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Excel(name = "主食重量")
    private BigDecimal zWeight;

    /** 主食金额 */
    @Excel(name = "主食金额")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private BigDecimal zMoney;

    /** 合计 */
    @Excel(name = "合计")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private BigDecimal total;

    /** $column.columnComment */
    @Excel(name = "合计")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer foodId;

    /** 副食名称 */
    @Excel(name = "副食名称")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String fName;

    /** 副食重量 */
    @Excel(name = "副食重量")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private BigDecimal fWeight;

    /** 副食金额 */
    @Excel(name = "副食金额")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private BigDecimal fMoney;

    /** 其他名称 */
    @Excel(name = "其他名称")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String qName;

    /** 副食重量 */
    @Excel(name = "副食重量")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private BigDecimal qWeight;

    /** 主食金额 */
    @Excel(name = "主食金额")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private BigDecimal qMoney;
    
    
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String peoplename;

    public String getPeoplename() {
        return peoplename;
    }

    public void setPeoplename(String peoplename) {
        this.peoplename = peoplename;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }
    public void setPeopleId(Integer peopleId)
    {
        this.peopleId = peopleId;
    }

    public Integer getPeopleId()
    {
        return peopleId;
    }
    public void setzName(String zName)
    {
        this.zName = zName;
    }

    public String getzName()
    {
        return zName;
    }
    public void setzWeight(BigDecimal zWeight)
    {
        this.zWeight = zWeight;
    }

    public BigDecimal getzWeight()
    {
        return zWeight;
    }
    public void setzMoney(BigDecimal zMoney)
    {
        this.zMoney = zMoney;
    }

    public BigDecimal getzMoney()
    {
        return zMoney;
    }
    public void setTotal(BigDecimal total)
    {
        this.total = total;
    }

    public BigDecimal getTotal()
    {
        return total;
    }
    public void setFoodId(Integer foodId)
    {
        this.foodId = foodId;
    }

    public Integer getFoodId()
    {
        return foodId;
    }
    public void setfName(String fName)
    {
        this.fName = fName;
    }

    public String getfName()
    {
        return fName;
    }
    public void setfWeight(BigDecimal fWeight)
    {
        this.fWeight = fWeight;
    }

    public BigDecimal getfWeight()
    {
        return fWeight;
    }
    public void setfMoney(BigDecimal fMoney)
    {
        this.fMoney = fMoney;
    }

    public BigDecimal getfMoney()
    {
        return fMoney;
    }
    public void setqName(String qName)
    {
        this.qName = qName;
    }

    public String getqName()
    {
        return qName;
    }
    public void setqWeight(BigDecimal qWeight)
    {
        this.qWeight = qWeight;
    }

    public BigDecimal getqWeight()
    {
        return qWeight;
    }
    public void setqMoney(BigDecimal qMoney)
    {
        this.qMoney = qMoney;
    }

    public BigDecimal getqMoney()
    {
        return qMoney;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("peopleId", getPeopleId())
                .append("zName", getzName())
                .append("zWeight", getzWeight())
                .append("zMoney", getzMoney())
                .append("total", getTotal())
                .append("foodId", getFoodId())
                .append("fName", getfName())
                .append("fWeight", getfWeight())
                .append("fMoney", getfMoney())
                .append("qName", getqName())
                .append("qWeight", getqWeight())
                .append("qMoney", getqMoney())
                .append("createtime", getCreatetime())
                .append("updatetime", getUpdatetime())
                .toString();
    }
}