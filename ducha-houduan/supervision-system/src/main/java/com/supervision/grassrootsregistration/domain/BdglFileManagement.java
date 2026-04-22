package com.supervision.grassrootsregistration.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 文件管理登计本对象 bdgl_file_management
 *
 * @author supervision
 * @date 2022-03-03
 */
public class BdglFileManagement extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 文件编号 */
    @Excel(name = "文件编号")
    private String number;

    /** 文件名称 */
    @Excel(name = "文件名称")
    private String name;

    /** 收文时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "收文时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date incomingDispatchesTime;

    /** 文件密级 */
    @Excel(name = "文件密级")
    private String confidential;

    /** 文件份数 */
    @Excel(name = "文件份数")
    private Integer copies;

    /** 签收人 */
    @Excel(name = "签收人")
    private Integer peopleId;

    /** 借阅人 */
    @Excel(name = "借阅人")
    private Integer borrowId;

    /** 借出时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "借出时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date loanTime;

    /** 归还时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "归还时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date returnTime;

    /** 注销人 */
    @Excel(name = "注销人")
    private Integer logoutId;

    /** 单位 */
    @Excel(name = "单位")
    private Integer unitId;

    /** 部门名称 */
    @Excel(name = "部门名称")
    private String deptName;

    /** 签收人姓名 */
    @Excel(name = "签收人姓名")
    private String peopleName;

    /** 借阅人姓名 */
    @Excel(name = "借阅人姓名")
    private String borrowName;

    /** 注销人姓名 */
    @Excel(name = "注销人姓名")
    private String logoutName;

    /** 备注 */
    @Excel(name = "备注")
    private String beizhu;
    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    private List<BdglFileBorrowRecord> borrowRecords;

    // ===== getter / setter =====
    public List<BdglFileBorrowRecord> getBorrowRecords() {
        return borrowRecords;
    }

    public void setBorrowRecords(List<BdglFileBorrowRecord> borrowRecords) {
        this.borrowRecords = borrowRecords;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }
    public void setNumber(String number)
    {
        this.number = number;
    }

    public String getNumber()
    {
        return number;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setIncomingDispatchesTime(Date incomingDispatchesTime)
    {
        this.incomingDispatchesTime = incomingDispatchesTime;
    }

    public Date getIncomingDispatchesTime()
    {
        return incomingDispatchesTime;
    }
    public void setConfidential(String confidential)
    {
        this.confidential = confidential;
    }

    public String getConfidential()
    {
        return confidential;
    }
    public void setCopies(Integer copies)
    {
        this.copies = copies;
    }

    public Integer getCopies()
    {
        return copies;
    }
    public void setPeopleId(Integer peopleId)
    {
        this.peopleId = peopleId;
    }

    public Integer getPeopleId()
    {
        return peopleId;
    }
    public void setBorrowId(Integer borrowId)
    {
        this.borrowId = borrowId;
    }

    public Integer getBorrowId()
    {
        return borrowId;
    }
    public void setLoanTime(Date loanTime)
    {
        this.loanTime = loanTime;
    }

    public Date getLoanTime()
    {
        return loanTime;
    }
    public void setReturnTime(Date returnTime)
    {
        this.returnTime = returnTime;
    }

    public Date getReturnTime()
    {
        return returnTime;
    }
    public void setLogoutId(Integer logoutId)
    {
        this.logoutId = logoutId;
    }

    public Integer getLogoutId()
    {
        return logoutId;
    }
    public void setUnitId(Integer unitId)
    {
        this.unitId = unitId;
    }

    public Integer getUnitId()
    {
        return unitId;
    }
    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }

    public String getDeptName()
    {
        return deptName;
    }
    public void setPeopleName(String peopleName)
    {
        this.peopleName = peopleName;
    }

    public String getPeopleName()
    {
        return peopleName;
    }
    public void setBorrowName(String borrowName)
    {
        this.borrowName = borrowName;
    }

    public String getBorrowName()
    {
        return borrowName;
    }
    public void setLogoutName(String logoutName)
    {
        this.logoutName = logoutName;
    }

    public String getLogoutName()
    {
        return logoutName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("number", getNumber())
                .append("name", getName())
                .append("incomingDispatchesTime", getIncomingDispatchesTime())
                .append("confidential", getConfidential())
                .append("copies", getCopies())
                .append("peopleId", getPeopleId())
                .append("borrowId", getBorrowId())
                .append("loanTime", getLoanTime())
                .append("returnTime", getReturnTime())
                .append("logoutId", getLogoutId())
                .append("unitId", getUnitId())
                .append("createtime", getCreatetime())
                .append("updatetime", getUpdatetime())
                .append("deptName", getDeptName())
                .append("peopleName", getPeopleName())
                .append("borrowName", getBorrowName())
                .append("logoutName", getLogoutName())
                .toString();
    }
}
