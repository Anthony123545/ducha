package com.supervision.phone.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 手机柜对象 phone_cabinet
 * 
 * @author supervision
 * @date 2022-04-20
 */
public class PhoneCabinet extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 手机柜编号 */
    @Excel(name = "手机柜编号")
    private Long number;

    /** 手机柜位置 */
    @Excel(name = "手机柜位置")
    private String address;

    /** 格子数量 */
    @Excel(name = "格子数量")
    private Long gridNumber;

    /** 存放数量 */
    @Excel(name = "存放数量")
    private Long storageQuantity;

    /** 异常数量 */
    @Excel(name = "异常数量")
    private Long exceptionsNumber;

    /** 所属单位id */
    @Excel(name = "所属单位id")
    private Long unitId;

    /** 所属单位名称 */
    @Excel(name = "所属单位名称")
    private String unitName;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setNumber(Long number) 
    {
        this.number = number;
    }

    public Long getNumber() 
    {
        return number;
    }
    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }
    public void setGridNumber(Long gridNumber) 
    {
        this.gridNumber = gridNumber;
    }

    public Long getGridNumber() 
    {
        return gridNumber;
    }
    public void setStorageQuantity(Long storageQuantity) 
    {
        this.storageQuantity = storageQuantity;
    }

    public Long getStorageQuantity() 
    {
        return storageQuantity;
    }
    public void setExceptionsNumber(Long exceptionsNumber) 
    {
        this.exceptionsNumber = exceptionsNumber;
    }

    public Long getExceptionsNumber() 
    {
        return exceptionsNumber;
    }
    public void setUnitId(Long unitId) 
    {
        this.unitId = unitId;
    }

    public Long getUnitId() 
    {
        return unitId;
    }
    public void setUnitName(String unitName) 
    {
        this.unitName = unitName;
    }

    public String getUnitName() 
    {
        return unitName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("number", getNumber())
            .append("address", getAddress())
            .append("gridNumber", getGridNumber())
            .append("storageQuantity", getStorageQuantity())
            .append("exceptionsNumber", getExceptionsNumber())
            .append("unitId", getUnitId())
            .append("unitName", getUnitName())
            .toString();
    }
}
