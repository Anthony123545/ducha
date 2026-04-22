package com.supervision.grassrootsregistration.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 营产，公物管理登记簿对象 bdgl_public_property
 * 
 * @author supervision
 * @date 2022-02-26
 */
public class BdglPublicProperty extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 规格型号 */
    @Excel(name = "规格型号")
    private String version;

    /** 数量 */
    @Excel(name = "数量")
    private Integer quantity;

    /** 单价 */
    @Excel(name = "单价")
    private BigDecimal price;

    /** 总价 */
    @Excel(name = "总价")
    private BigDecimal totalPrice;

    /** 装备时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "装备时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date equipTime;

    /** 保管人id */
    @Excel(name = "保管人id")
    private Integer peopleId;

    /** 取得方式 */
    @Excel(name = "取得方式")
    private String acquire;

    /** 质量状况 */
    @Excel(name = "质量状况")
    private String quality;

    /** 放置地点 */
    @Excel(name = "放置地点")
    private String address;

    /** 单位 */
    @Excel(name = "单位")
    private Integer unitId;
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createtime;
    /**修改时间*/
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatetime;
    /**保管人姓名*/
    private String peopleName;
    @Excel(name = "单位名称")
    private String unitName;

    /**来源*/
    private String source;
    /**借用情况*/
    private String usage;
    /**借用情况*/
    private String borrowingSituation;
    /**归还情况*/
    private String returnSituation;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    public String getBorrowingSituation() {
        return borrowingSituation;
    }

    public void setBorrowingSituation(String borrowingSituation) {
        this.borrowingSituation = borrowingSituation;
    }

    public String getReturnSituation() {
        return returnSituation;
    }

    public void setReturnSituation(String returnSituation) {
        this.returnSituation = returnSituation;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getPeopleName() {
        return peopleName;
    }

    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName;
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
    public void setVersion(String version) 
    {
        this.version = version;
    }

    public String getVersion() 
    {
        return version;
    }
    public void setQuantity(Integer quantity) 
    {
        this.quantity = quantity;
    }

    public Integer getQuantity() 
    {
        return quantity;
    }
    public void setPrice(BigDecimal price) 
    {
        this.price = price;
    }

    public BigDecimal getPrice() 
    {
        return price;
    }
    public void setTotalPrice(BigDecimal totalPrice) 
    {
        this.totalPrice = totalPrice;
    }

    public BigDecimal getTotalPrice() 
    {
        return totalPrice;
    }
    public void setEquipTime(Date equipTime) 
    {
        this.equipTime = equipTime;
    }

    public Date getEquipTime() 
    {
        return equipTime;
    }
    public void setPeopleId(Integer peopleId) 
    {
        this.peopleId = peopleId;
    }

    public Integer getPeopleId() 
    {
        return peopleId;
    }
    public void setAcquire(String acquire) 
    {
        this.acquire = acquire;
    }

    public String getAcquire() 
    {
        return acquire;
    }
    public void setQuality(String quality) 
    {
        this.quality = quality;
    }

    public String getQuality() 
    {
        return quality;
    }
    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
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
            .append("version", getVersion())
            .append("quantity", getQuantity())
            .append("price", getPrice())
            .append("totalPrice", getTotalPrice())
            .append("equipTime", getEquipTime())
            .append("peopleId", getPeopleId())
            .append("acquire", getAcquire())
            .append("quality", getQuality())
            .append("address", getAddress())
            .append("remark", getRemark())
            .append("unitId", getUnitId())
            .append("createtime", getCreatetime())
            .append("updatetime", getUpdatetime())
            .toString();
    }
}
