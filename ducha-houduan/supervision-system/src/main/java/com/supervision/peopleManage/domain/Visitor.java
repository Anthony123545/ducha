package com.supervision.peopleManage.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 访客管理对象 visitor
 *
 * @author supervision
 * @date 2025-11-19
 */
@Data
public class Visitor extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 工号 */
    @Excel(name = "工号")
    private String employeeNumber;

    /** 访客姓名 */
    @Excel(name = "访客姓名")
    private String name;

    /** 身份证号 */
    @Excel(name = "身份证号")
    private String idCard;

    /** 电话号码 */
    @Excel(name = "电话号码")
    private String phone;

    /** 所属单位 */
    @Excel(name = "所属单位")
    private String company;

    /** 车牌号(可为空) */
    @Excel(name = "车牌号")
    private String plateNumber;

    /** 头像路径 */
    @Excel(name = "头像")
    private String avatar;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getEmployeeNumber()
    {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber)
    {
        this.employeeNumber = employeeNumber;
    }

    public String getName()
    {
        return name;
    }


    public void setName(String name)
    {
        this.name = name;
    }

    public String getIdCard()
    {
        return idCard;
    }


    public void setIdCard(String idCard)
    {
        this.idCard = idCard;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getCompany()
    {
        return company;
    }


    public void setCompany(String company)
    {
        this.company = company;
    }

    public String getPlateNumber()
    {
        return plateNumber;
    }


    public void setPlateNumber(String plateNumber)
    {
        this.plateNumber = plateNumber;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("employeeNumber", getEmployeeNumber())
            .append("name", getName())
            .append("idCard", getIdCard())
            .append("phone", getPhone())
            .append("company", getCompany())
            .append("plateNumber", getPlateNumber())
            .append("avatar", getAvatar())
            .toString();
    }
}