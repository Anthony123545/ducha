package com.supervision.phone.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 手机柜格子对象 phone_grid
 * 
 * @author supervision
 * @date 2022-04-20
 */
public class PhoneGrid extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 手机柜id */
    @Excel(name = "手机柜id")
    private Long phoneCabinetId;

    /** 所属人姓名 */
    @Excel(name = "所属人姓名")
    private String name;

    /** 格子编号 */
    @Excel(name = "格子编号")
    private Long gridNumber;

    /** 格子状态 */
    @Excel(name = "格子状态")
    private String latticeState;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setPhoneCabinetId(Long phoneCabinetId) 
    {
        this.phoneCabinetId = phoneCabinetId;
    }

    public Long getPhoneCabinetId() 
    {
        return phoneCabinetId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setGridNumber(Long gridNumber) 
    {
        this.gridNumber = gridNumber;
    }

    public Long getGridNumber() 
    {
        return gridNumber;
    }
    public void setLatticeState(String latticeState) 
    {
        this.latticeState = latticeState;
    }

    public String getLatticeState() 
    {
        return latticeState;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("phoneCabinetId", getPhoneCabinetId())
            .append("name", getName())
            .append("gridNumber", getGridNumber())
            .append("latticeState", getLatticeState())
            .toString();
    }
}
