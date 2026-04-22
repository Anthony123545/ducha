package com.supervision.grassrootsregistration.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 军械装备登记簿对象 bdgl_armament
 * 
 * @author supervision
 * @date 2022-02-26
 */
public class BdglArmament extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 登记日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "登记日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date recordDate;

    /** 计量单位 */
    @Excel(name = "计量单位")
    private String metering;

    /** 交接依据 */
    @Excel(name = "交接依据")
    private String gist;

    /** 编制数量 */
    @Excel(name = "编制数量")
    private Integer prepareNumber;

    /** 现有总数 */
    @Excel(name = "现有总数")
    private Integer sum;

    /** 完好数量 */
    @Excel(name = "完好数量")
    private Integer intactNumber;

    /** 待修数量 */
    @Excel(name = "待修数量")
    private Integer repairNumber;

    /** 送修数量 */
    @Excel(name = "送修数量")
    private Integer sendNumber;

    /** 标准配套 */
    @Excel(name = "标准配套")
    private String standard;

    /** 现有配套 */
    @Excel(name = "现有配套")
    private String existing;

    /** 缺陷配套 */
    @Excel(name = "缺陷配套")
    private String flaw;

    /** 登记人 */
    @Excel(name = "登记人")
    private Integer peopleId;

    /** 核对人 */
    @Excel(name = "核对人")
    private Integer peopleId1;

    /** 单位 */
    @Excel(name = "单位")
    private Integer unitId;
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createtime;
    /**修改时间*/
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatetime;

    /** 登记人姓名 */
    @Excel(name = "登记人姓名")
    private String peopleName;

    /** 核对人姓名 */
    @Excel(name = "核对人姓名")
    private String people1Name;

    /** 单位名称 */
    @Excel(name = "单位名称")
    private String unitName;

    public String getPeopleName() {
        return peopleName;
    }

    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName;
    }

    public String getPeople1Name() {
        return people1Name;
    }

    public void setPeople1Name(String people1Name) {
        this.people1Name = people1Name;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

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
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setRecordDate(Date recordDate) 
    {
        this.recordDate = recordDate;
    }

    public Date getRecordDate() 
    {
        return recordDate;
    }
    public void setMetering(String metering) 
    {
        this.metering = metering;
    }

    public String getMetering() 
    {
        return metering;
    }
    public void setGist(String gist) 
    {
        this.gist = gist;
    }

    public String getGist() 
    {
        return gist;
    }
    public void setPrepareNumber(Integer prepareNumber) 
    {
        this.prepareNumber = prepareNumber;
    }

    public Integer getPrepareNumber() 
    {
        return prepareNumber;
    }
    public void setSum(Integer sum) 
    {
        this.sum = sum;
    }

    public Integer getSum() 
    {
        return sum;
    }
    public void setIntactNumber(Integer intactNumber) 
    {
        this.intactNumber = intactNumber;
    }

    public Integer getIntactNumber() 
    {
        return intactNumber;
    }
    public void setRepairNumber(Integer repairNumber) 
    {
        this.repairNumber = repairNumber;
    }

    public Integer getRepairNumber() 
    {
        return repairNumber;
    }
    public void setSendNumber(Integer sendNumber) 
    {
        this.sendNumber = sendNumber;
    }

    public Integer getSendNumber() 
    {
        return sendNumber;
    }
    public void setStandard(String standard) 
    {
        this.standard = standard;
    }

    public String getStandard() 
    {
        return standard;
    }
    public void setExisting(String existing) 
    {
        this.existing = existing;
    }

    public String getExisting() 
    {
        return existing;
    }
    public void setFlaw(String flaw) 
    {
        this.flaw = flaw;
    }

    public String getFlaw() 
    {
        return flaw;
    }
    public void setPeopleId(Integer peopleId) 
    {
        this.peopleId = peopleId;
    }

    public Integer getPeopleId() 
    {
        return peopleId;
    }
    public void setPeopleId1(Integer peopleId1) 
    {
        this.peopleId1 = peopleId1;
    }

    public Integer getPeopleId1() 
    {
        return peopleId1;
    }
    public void setUnitId(Integer unitId) 
    {
        this.unitId = unitId;
    }

    public Integer getUnitId() 
    {
        return unitId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("recordDate", getRecordDate())
            .append("metering", getMetering())
            .append("gist", getGist())
            .append("prepareNumber", getPrepareNumber())
            .append("sum", getSum())
            .append("intactNumber", getIntactNumber())
            .append("repairNumber", getRepairNumber())
            .append("sendNumber", getSendNumber())
            .append("standard", getStandard())
            .append("existing", getExisting())
            .append("flaw", getFlaw())
            .append("peopleId", getPeopleId())
            .append("peopleId1", getPeopleId1())
            .append("unitId", getUnitId())
            .append("createtime", getCreatetime())
            .append("updatetime", getUpdatetime())
            .toString();
    }
}
