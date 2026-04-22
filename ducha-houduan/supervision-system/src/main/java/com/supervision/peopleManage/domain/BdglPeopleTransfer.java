package com.supervision.peopleManage.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 调动管理对象 bdgl_people_transfer
 * 
 * @author supervision
 * @date 2022-02-23
 */
public class BdglPeopleTransfer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 调动人员id */
    @Excel(name = "调动人员id")
    private Integer peopleId;

    /** 调动类型 */
    @Excel(name = "调动类型")
    private String transferId;

    /** 调动日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "调动日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date transferDate;

    /** 调动生效日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "调动生效日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date transferTake;

    /** 调动前军衔 */
    @Excel(name = "调动前军衔")
    private String militaryRank1;

    /** 调动后军衔 */
    @Excel(name = "调动后军衔")
    private String militaryRank2;

    /** 调动前职务 */
    @Excel(name = "调动前职务")
    private String post1;

    /** 调动后职务 */
    @Excel(name = "调动后职务")
    private String post2;

    /** 调动前单位 */
    @Excel(name = "调动前单位")
    private Long unit1;

    /** 调动后单位 */
    @Excel(name = "调动后单位")
    private Long unit2;

    /** 调动后部职别 */
    @Excel(name = "调动后部职别")
    private String aduty;

    /** 调动命令号 */
    @Excel(name = "调动命令号")
    private String commandNumber;

    /** 调动原因 */
    @Excel(name = "调动原因")
    private String reason;

    /** 预留字段 */
    @Excel(name = "预留字段")
    private String field1;

    /** 调动人员姓名 */
    @Excel(name = "调动人员姓名")
    private String cName;

    private Date createtime;
    private Date updatetime;

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
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

    public void setTransferDate(Date transferDate) 
    {
        this.transferDate = transferDate;
    }

    public Date getTransferDate() 
    {
        return transferDate;
    }
    public void setTransferTake(Date transferTake) 
    {
        this.transferTake = transferTake;
    }

    public Date getTransferTake() 
    {
        return transferTake;
    }
    public void setMilitaryRank1(String militaryRank1) 
    {
        this.militaryRank1 = militaryRank1;
    }

    public String getMilitaryRank1() 
    {
        return militaryRank1;
    }
    public void setMilitaryRank2(String militaryRank2) 
    {
        this.militaryRank2 = militaryRank2;
    }

    public String getMilitaryRank2() 
    {
        return militaryRank2;
    }
    public void setPost1(String post1) 
    {
        this.post1 = post1;
    }

    public String getPost1() 
    {
        return post1;
    }
    public void setPost2(String post2) 
    {
        this.post2 = post2;
    }

    public String getPost2() 
    {
        return post2;
    }

    public String getTransferId() {
        return transferId;
    }

    public void setTransferId(String transferId) {
        this.transferId = transferId;
    }

    public Long getUnit1() {
        return unit1;
    }

    public void setUnit1(Long unit1) {
        this.unit1 = unit1;
    }

    public Long getUnit2() {
        return unit2;
    }

    public void setUnit2(Long unit2) {
        this.unit2 = unit2;
    }

    public void setAduty(String aduty)
    {
        this.aduty = aduty;
    }

    public String getAduty() 
    {
        return aduty;
    }
    public void setCommandNumber(String commandNumber) 
    {
        this.commandNumber = commandNumber;
    }

    public String getCommandNumber() 
    {
        return commandNumber;
    }
    public void setReason(String reason) 
    {
        this.reason = reason;
    }

    public String getReason() 
    {
        return reason;
    }
    public void setField1(String field1) 
    {
        this.field1 = field1;
    }

    public String getField1() 
    {
        return field1;
    }
    public void setcName(String cName) 
    {
        this.cName = cName;
    }

    public String getcName() 
    {
        return cName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("peopleId", getPeopleId())
            .append("transferId", getTransferId())
            .append("transferDate", getTransferDate())
            .append("transferTake", getTransferTake())
            .append("militaryRank1", getMilitaryRank1())
            .append("militaryRank2", getMilitaryRank2())
            .append("post1", getPost1())
            .append("post2", getPost2())
            .append("unit1", getUnit1())
            .append("unit2", getUnit2())
            .append("aduty", getAduty())
            .append("commandNumber", getCommandNumber())
            .append("reason", getReason())
            .append("remark", getRemark())
            .append("field1", getField1())
            .append("createtime", getCreatetime())
            .append("updatetime", getUpdatetime())
            .append("cName", getcName())
            .toString();
    }
}
