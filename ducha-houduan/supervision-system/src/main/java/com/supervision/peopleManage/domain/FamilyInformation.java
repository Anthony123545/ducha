package com.supervision.peopleManage.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 家庭关系对象 family_information
 * 
 * @author supervision
 * @date 2022-03-01
 */
public class FamilyInformation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 人员表id */
    @Excel(name = "人员表id")
    private Long peopleId;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 与本人关系 */
    @Excel(name = "与本人关系")
    private String characterRelationship;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String phoneNumber;

    /** 单位名称 */
    @Excel(name = "单位名称")
    private String unitName;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setPeopleId(Long peopleId) 
    {
        this.peopleId = peopleId;
    }

    public Long getPeopleId() 
    {
        return peopleId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setCharacterRelationship(String characterRelationship) 
    {
        this.characterRelationship = characterRelationship;
    }

    public String getCharacterRelationship() 
    {
        return characterRelationship;
    }
    public void setPhoneNumber(String phoneNumber) 
    {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() 
    {
        return phoneNumber;
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
            .append("peopleId", getPeopleId())
            .append("name", getName())
            .append("characterRelationship", getCharacterRelationship())
            .append("phoneNumber", getPhoneNumber())
            .append("unitName", getUnitName())
            .toString();
    }
}
