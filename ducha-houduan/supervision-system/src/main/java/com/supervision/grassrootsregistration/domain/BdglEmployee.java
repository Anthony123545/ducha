package com.supervision.grassrootsregistration.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 门禁人员信息对象 bdgl_employee
 * 
 * @author ruoyi
 * @date 2023-03-13
 */
public class BdglEmployee extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 人员编号 */
    @Excel(name = "人员编号")
    private Long pin;

    /** 名字 */
    @Excel(name = "名字")
    private String name;

    /** 部门编号 */
    @Excel(name = "部门编号")
    private Long deptnumber;

    /** 部门名字 */
    @Excel(name = "部门名字")
    private String deptname;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String position;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String gender;

    /** 办公电话 */
    @Excel(name = "办公电话")
    private String telephone;

    /** 邮箱 */
    @Excel(name = "邮箱")
    private String email;

    /** 人员卡号 */
    @Excel(name = "人员卡号")
    private String card;

    /** 手机号 */
    @Excel(name = "手机号")
    private String mobile;

    /** 人员状态0在职1离职 */
    @Excel(name = "人员状态0在职1离职")
    private Long status;

    public void setPin(Long pin) 
    {
        this.pin = pin;
    }

    public Long getPin() 
    {
        return pin;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setDeptnumber(Long deptnumber) 
    {
        this.deptnumber = deptnumber;
    }

    public Long getDeptnumber() 
    {
        return deptnumber;
    }
    public void setDeptname(String deptname) 
    {
        this.deptname = deptname;
    }

    public String getDeptname() 
    {
        return deptname;
    }
    public void setPosition(String position) 
    {
        this.position = position;
    }

    public String getPosition() 
    {
        return position;
    }
    public void setGender(String gender) 
    {
        this.gender = gender;
    }

    public String getGender() 
    {
        return gender;
    }
    public void setTelephone(String telephone) 
    {
        this.telephone = telephone;
    }

    public String getTelephone() 
    {
        return telephone;
    }
    public void setEmail(String email) 
    {
        this.email = email;
    }

    public String getEmail() 
    {
        return email;
    }
    public void setCard(String card) 
    {
        this.card = card;
    }

    public String getCard() 
    {
        return card;
    }
    public void setMobile(String mobile) 
    {
        this.mobile = mobile;
    }

    public String getMobile() 
    {
        return mobile;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("pin", getPin())
            .append("name", getName())
            .append("deptnumber", getDeptnumber())
            .append("deptname", getDeptname())
            .append("position", getPosition())
            .append("gender", getGender())
            .append("telephone", getTelephone())
            .append("email", getEmail())
            .append("card", getCard())
            .append("mobile", getMobile())
            .append("status", getStatus())
            .toString();
    }
}
