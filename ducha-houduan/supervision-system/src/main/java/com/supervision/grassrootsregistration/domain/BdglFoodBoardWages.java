package com.supervision.grassrootsregistration.domain;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 交/退伙食费对象 bdgl_food_board_wages
 * 
 * @author supervision
 * @date 2022-02-28
 */
public class BdglFoodBoardWages extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer id;

    /** 姓名 */
    @Excel(name = "姓名")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer peopleId;

    /** 类型 */
    @Excel(name = "类型")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String type;

    /** 天数 */
    @Excel(name = "天数")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer sky;

    /** 标准 */
    @Excel(name = "标准")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private BigDecimal standard;

    /** 金额 */
    @Excel(name = "金额")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private BigDecimal money;

    /** $column.columnComment */
    @Excel(name = "金额")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer foodId;

    /**人员姓名*/
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String peopleName;

    @Override
    public String getPeopleName() {
        return peopleName;
    }

    @Override
    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName;
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
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setSky(Integer sky) 
    {
        this.sky = sky;
    }

    public Integer getSky() 
    {
        return sky;
    }
    public void setStandard(BigDecimal standard) 
    {
        this.standard = standard;
    }

    public BigDecimal getStandard() 
    {
        return standard;
    }
    public void setMoney(BigDecimal money) 
    {
        this.money = money;
    }

    public BigDecimal getMoney() 
    {
        return money;
    }
    public void setFoodId(Integer foodId) 
    {
        this.foodId = foodId;
    }

    public Integer getFoodId() 
    {
        return foodId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("peopleId", getPeopleId())
            .append("type", getType())
            .append("sky", getSky())
            .append("standard", getStandard())
            .append("money", getMoney())
            .append("foodId", getFoodId())
            .toString();
    }
}
