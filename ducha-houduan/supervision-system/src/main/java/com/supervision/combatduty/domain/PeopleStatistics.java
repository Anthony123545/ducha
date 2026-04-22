package com.supervision.combatduty.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 人员变动对象 people_statistics
 * 
 * @author ruoyi
 * @date 2022-08-25
 */
public class PeopleStatistics extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 在位总数 */
    @Excel(name = "在位总数")
    private Integer totalNumber;

    /** 纳编在位 */
    @Excel(name = "纳编在位")
    private Integer nabianPlace;

    /** 未纳编在位 */
    @Excel(name = "未纳编在位")
    private Integer notInPlace;

    /** 纳编外出 */
    @Excel(name = "纳编外出")
    private Integer nabianOut;

    /** 未纳编外出 */
    @Excel(name = "未纳编外出")
    private Integer notNabianOut;

    /** 是否切换 */
    @Excel(name = "是否切换")
    private String toggle;

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }

    public Integer getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(Integer totalNumber) {
        this.totalNumber = totalNumber;
    }

    public Integer getNabianPlace() {
        return nabianPlace;
    }

    public void setNabianPlace(Integer nabianPlace) {
        this.nabianPlace = nabianPlace;
    }

    public Integer getNotInPlace() {
        return notInPlace;
    }

    public void setNotInPlace(Integer notInPlace) {
        this.notInPlace = notInPlace;
    }

    public Integer getNabianOut() {
        return nabianOut;
    }

    public void setNabianOut(Integer nabianOut) {
        this.nabianOut = nabianOut;
    }

    public Integer getNotNabianOut() {
        return notNabianOut;
    }

    public void setNotNabianOut(Integer notNabianOut) {
        this.notNabianOut = notNabianOut;
    }

    public void setToggle(String toggle)
    {
        this.toggle = toggle;
    }

    public String getToggle() 
    {
        return toggle;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("totalNumber", getTotalNumber())
            .append("nabianPlace", getNabianPlace())
            .append("notInPlace", getNotInPlace())
            .append("nabianOut", getNabianOut())
            .append("notNabianOut", getNotNabianOut())
            .append("toggle", getToggle())
            .toString();
    }
}