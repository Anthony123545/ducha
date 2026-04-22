package com.supervision.phone.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 存取记录对象 phone_access
 * 
 * @author supervision
 * @date 2022-04-23
 */
public class PhoneAccess extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 格子id */
    @Excel(name = "格子id")
    private Long phoneId;

    /** 格子状态 */
    @Excel(name = "格子状态")
    private String state;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 存取时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "存取时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date time;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setPhoneId(Long phoneId) 
    {
        this.phoneId = phoneId;
    }

    public Long getPhoneId() 
    {
        return phoneId;
    }
    public void setState(String state) 
    {
        this.state = state;
    }

    public String getState() 
    {
        return state;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setTime(Date time) 
    {
        this.time = time;
    }

    public Date getTime() 
    {
        return time;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("phoneId", getPhoneId())
            .append("state", getState())
            .append("name", getName())
            .append("time", getTime())
            .toString();
    }
}
