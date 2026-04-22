package com.supervision.barracksManagement.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 维修申请对象 bdgl_repair
 * 
 * @author ruoyi
 * @date 2023-03-10
 */
public class BdglRepair extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 联系人 */
    @Excel(name = "联系人")
    private String contacts;

    /** 联系方式 */
    @Excel(name = "联系方式")
    private String contactInformation;

    /** 维修地址 */
    @Excel(name = "维修地址")
    private String addressUnit;

    /** 维修类型 */
    @Excel(name = "维修类型")
    private Long repairType;

    /** 具体描述 */
    @Excel(name = "具体描述")
    private String description;

    /** 完成状态 */
    @Excel(name = "完成状态")
    private Integer state;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setContacts(String contacts) 
    {
        this.contacts = contacts;
    }

    public String getContacts() 
    {
        return contacts;
    }
    public void setContactInformation(String contactInformation) 
    {
        this.contactInformation = contactInformation;
    }

    public String getContactInformation() 
    {
        return contactInformation;
    }
    public void setAddressUnit(String addressUnit) 
    {
        this.addressUnit = addressUnit;
    }

    public String getAddressUnit() 
    {
        return addressUnit;
    }
    public void setRepairType(Long repairType) 
    {
        this.repairType = repairType;
    }

    public Long getRepairType() 
    {
        return repairType;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setState(Integer state) 
    {
        this.state = state;
    }

    public Integer getState() 
    {
        return state;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("contacts", getContacts())
            .append("contactInformation", getContactInformation())
            .append("addressUnit", getAddressUnit())
            .append("repairType", getRepairType())
            .append("description", getDescription())
            .append("state", getState())
            .toString();
    }
}
