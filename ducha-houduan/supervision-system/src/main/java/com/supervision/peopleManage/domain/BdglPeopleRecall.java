package com.supervision.peopleManage.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 人员调出对象 bdgl_people_recall
 *
 * @author ruoyi
 * @date 2023-01-10
 */
public class BdglPeopleRecall extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 人员id */
    @Excel(name = "人员id")
    private Integer peopleId;

    /** 预留字段 */
    @Excel(name = "预留字段")
    private String filed1;

    /** 预留字段2 */
    @Excel(name = "预留字段2")
    private String filed2;

    /** 调出人员名称 */
    @Excel(name = "调出人员名称")
    private String cName;

    /** 操作id */
    @Excel(name = "操作id")
    private Integer adminId;

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private BdglPeopleRecallAuditlog bdglPeopleRecallAuditlog;

    private BdglPeople bdglPeople;

    public BdglPeople getBdglPeople() {
        return bdglPeople;
    }

    public void setBdglPeople(BdglPeople bdglPeople) {
        this.bdglPeople = bdglPeople;
    }

    public BdglPeopleRecallAuditlog getBdglPeopleRecallAuditlog() {
        return bdglPeopleRecallAuditlog;
    }

    public void setBdglPeopleRecallAuditlog(BdglPeopleRecallAuditlog bdglPeopleRecallAuditlog) {
        this.bdglPeopleRecallAuditlog = bdglPeopleRecallAuditlog;
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
    public void setFiled1(String filed1)
    {
        this.filed1 = filed1;
    }

    public String getFiled1()
    {
        return filed1;
    }
    public void setFiled2(String filed2)
    {
        this.filed2 = filed2;
    }

    public String getFiled2()
    {
        return filed2;
    }
    public void setcName(String cName)
    {
        this.cName = cName;
    }

    public String getcName()
    {
        return cName;
    }
    public void setAdminId(Integer adminId)
    {
        this.adminId = adminId;
    }

    public Integer getAdminId()
    {
        return adminId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("peopleId", getPeopleId())
            .append("filed1", getFiled1())
            .append("filed2", getFiled2())
            .append("cName", getcName())
            .append("createtime", getCreatetime())
            .append("adminId", getAdminId())
            .toString();
    }
}
