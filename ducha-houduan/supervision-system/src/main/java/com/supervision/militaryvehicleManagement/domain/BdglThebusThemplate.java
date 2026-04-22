package com.supervision.militaryvehicleManagement.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 军车审批模板对象 bdgl_thebus_themplate
 * 
 * @author supervision
 * @date 2022-03-04
 */
public class BdglThebusThemplate extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 审批模板名称 */
    @Excel(name = "审批模板名称")
    private String name;

    /** 一级审批人 */
    @Excel(name = "一级审批人")
    private Integer people1Id;

    /** 二级审批人 */
    @Excel(name = "二级审批人")
    private Integer people2Id;

    /** 三级审批人 */
    @Excel(name = "三级审批人")
    private Integer people3Id;
    /**y一级审批人姓名*/
    private String peopleName1;
    /**二级审批人姓名*/
    private String peopleName2;
    /**三级审批人姓名*/
    private String peopleName3;
    private Date createTime;
    private Date updateTime;

    @Override
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getPeopleName1() {
        return peopleName1;
    }

    public void setPeopleName1(String peopleName1) {
        this.peopleName1 = peopleName1;
    }

    public String getPeopleName2() {
        return peopleName2;
    }

    public void setPeopleName2(String peopleName2) {
        this.peopleName2 = peopleName2;
    }

    public String getPeopleName3() {
        return peopleName3;
    }

    public void setPeopleName3(String peopleName3) {
        this.peopleName3 = peopleName3;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setPeople1Id(Integer people1Id) 
    {
        this.people1Id = people1Id;
    }

    public Integer getPeople1Id() 
    {
        return people1Id;
    }
    public void setPeople2Id(Integer people2Id) 
    {
        this.people2Id = people2Id;
    }

    public Integer getPeople2Id() 
    {
        return people2Id;
    }
    public void setPeople3Id(Integer people3Id) 
    {
        this.people3Id = people3Id;
    }

    public Integer getPeople3Id() 
    {
        return people3Id;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("people1Id", getPeople1Id())
            .append("people2Id", getPeople2Id())
            .append("people3Id", getPeople3Id())
            .append("createtime", getCreatetime())
            .append("updatetime", getUpdatetime())
            .toString();
    }
}
