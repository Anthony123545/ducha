package com.supervision.materialManagement.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 物资发放对象 material_distribution
 *
 * @author supervision
 * @date 2022-04-08
 */
public class MaterialDistribution extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;
    
    /** 条形码编号 */
    @Excel(name = "条形码编号")
    private String barcode;

    /** 物品类型 */
    @Excel(name = "物品类型")
    private String wuPinType;

    /** 物品名称 */
    @Excel(name = "物品名称")
    private String name;

    /** 物资规格 */
    @Excel(name = "物资规格")
    private String materialSpecification;

    /** 物资型号 */
    @Excel(name = "物资型号")
    private String materialXh;

    /** 营具类型 */
    @Excel(name = "营具类型")
    private String yingJuType;

    /** 责任单位 */
    @Excel(name = "责任单位")
    private String responsibilityUnit;

    /** 责任人 */
    @Excel(name = "责任人")
    private String personLiable;

    /** 出库数量 */
    @Excel(name = "出库数量")
    private Long putOut;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createName;

    /** 发放时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发放时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date putOutTime;

    /** 存放位置 */
    @Excel(name = "存放位置")
    private String storageLocation;

    /** 购买时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "购买时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date buyingTime;

    /** 价值 */
    @Excel(name = "价值")
    private BigDecimal price;

    /** 库房 */
    @Excel(name = "库房")
    private String warehouse;

    /** 档案id */
    @Excel(name = "档案id")
    private Long archivesId;

    /** 现有数量 */
    @Excel(name = "现有数量")
    private Long total;

    /** 发放单位 */
    @Excel(name = "发放单位")
    private String outUnit;

    /** 发放对象 */
    @Excel(name = "发放对象")
    private String outPeople;

    /** 接收人 */
    @Excel(name = "接收人")
    private String recipient;

    /** 入库表id */
    @Excel(name = "入库表id")
    private Long registrationId;

    /** 责任单位id */
    @Excel(name = "责任单位id")
    private Long responsibilityUnitId;

    /** 责任人id */
    @Excel(name = "责任人id")
    private Long personLiableId;

    /** 创建人id */
    @Excel(name = "创建人id")
    private Long createId;

    /** 接受单位id */
    @Excel(name = "接受单位id")
    private Long outUnitId;

    /** 接收人id */
    @Excel(name = "接收人id")
    private Long recipientId;

    /** 发放人id */
    @Excel(name = "发放人id")
    private Long outPeopleId;

    private String common;
    
    public String getBarcode() {
        return barcode;
    }
    
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
    
    public String getCommon() {
        return common;
    }

    public void setCommon(String common) {
        this.common = common;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setWuPinType(String wuPinType)
    {
        this.wuPinType = wuPinType;
    }

    public String getWuPinType()
    {
        return wuPinType;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setMaterialSpecification(String materialSpecification)
    {
        this.materialSpecification = materialSpecification;
    }

    public String getMaterialSpecification()
    {
        return materialSpecification;
    }
    public void setMaterialXh(String materialXh)
    {
        this.materialXh = materialXh;
    }

    public String getMaterialXh()
    {
        return materialXh;
    }
    public void setYingJuType(String yingJuType)
    {
        this.yingJuType = yingJuType;
    }

    public String getYingJuType()
    {
        return yingJuType;
    }
    public void setResponsibilityUnit(String responsibilityUnit)
    {
        this.responsibilityUnit = responsibilityUnit;
    }

    public String getResponsibilityUnit()
    {
        return responsibilityUnit;
    }
    public void setPersonLiable(String personLiable)
    {
        this.personLiable = personLiable;
    }

    public String getPersonLiable()
    {
        return personLiable;
    }
    public void setPutOut(Long putOut)
    {
        this.putOut = putOut;
    }

    public Long getPutOut()
    {
        return putOut;
    }
    public void setCreateName(String createName)
    {
        this.createName = createName;
    }

    public String getCreateName()
    {
        return createName;
    }
    public void setPutOutTime(Date putOutTime)
    {
        this.putOutTime = putOutTime;
    }

    public Date getPutOutTime()
    {
        return putOutTime;
    }
    public void setStorageLocation(String storageLocation)
    {
        this.storageLocation = storageLocation;
    }

    public String getStorageLocation()
    {
        return storageLocation;
    }
    public void setBuyingTime(Date buyingTime)
    {
        this.buyingTime = buyingTime;
    }

    public Date getBuyingTime()
    {
        return buyingTime;
    }
    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    public BigDecimal getPrice()
    {
        return price;
    }
    public void setWarehouse(String warehouse)
    {
        this.warehouse = warehouse;
    }

    public String getWarehouse()
    {
        return warehouse;
    }
    public void setArchivesId(Long archivesId)
    {
        this.archivesId = archivesId;
    }

    public Long getArchivesId()
    {
        return archivesId;
    }
    public void setTotal(Long total)
    {
        this.total = total;
    }

    public Long getTotal()
    {
        return total;
    }
    public void setOutUnit(String outUnit)
    {
        this.outUnit = outUnit;
    }

    public String getOutUnit()
    {
        return outUnit;
    }
    public void setOutPeople(String outPeople)
    {
        this.outPeople = outPeople;
    }

    public String getOutPeople()
    {
        return outPeople;
    }
    public void setRecipient(String recipient)
    {
        this.recipient = recipient;
    }

    public String getRecipient()
    {
        return recipient;
    }
    public void setRegistrationId(Long registrationId)
    {
        this.registrationId = registrationId;
    }

    public Long getRegistrationId()
    {
        return registrationId;
    }
    public void setResponsibilityUnitId(Long responsibilityUnitId)
    {
        this.responsibilityUnitId = responsibilityUnitId;
    }

    public Long getResponsibilityUnitId()
    {
        return responsibilityUnitId;
    }
    public void setPersonLiableId(Long personLiableId)
    {
        this.personLiableId = personLiableId;
    }

    public Long getPersonLiableId()
    {
        return personLiableId;
    }
    public void setCreateId(Long createId)
    {
        this.createId = createId;
    }

    public Long getCreateId()
    {
        return createId;
    }
    public void setOutUnitId(Long outUnitId)
    {
        this.outUnitId = outUnitId;
    }

    public Long getOutUnitId()
    {
        return outUnitId;
    }
    public void setRecipientId(Long recipientId)
    {
        this.recipientId = recipientId;
    }

    public Long getRecipientId()
    {
        return recipientId;
    }
    public void setOutPeopleId(Long outPeopleId)
    {
        this.outPeopleId = outPeopleId;
    }

    public Long getOutPeopleId()
    {
        return outPeopleId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("wuPinType", getWuPinType())
                .append("name", getName())
                .append("materialSpecification", getMaterialSpecification())
                .append("materialXh", getMaterialXh())
                .append("yingJuType", getYingJuType())
                .append("responsibilityUnit", getResponsibilityUnit())
                .append("personLiable", getPersonLiable())
                .append("putOut", getPutOut())
                .append("createName", getCreateName())
                .append("createTime", getCreateTime())
                .append("putOutTime", getPutOutTime())
                .append("storageLocation", getStorageLocation())
                .append("buyingTime", getBuyingTime())
                .append("price", getPrice())
                .append("remark", getRemark())
                .append("warehouse", getWarehouse())
                .append("archivesId", getArchivesId())
                .append("total", getTotal())
                .append("outUnit", getOutUnit())
                .append("outPeople", getOutPeople())
                .append("recipient", getRecipient())
                .append("registrationId", getRegistrationId())
                .append("responsibilityUnitId", getResponsibilityUnitId())
                .append("personLiableId", getPersonLiableId())
                .append("createId", getCreateId())
                .append("outUnitId", getOutUnitId())
                .append("recipientId", getRecipientId())
                .append("outPeopleId", getOutPeopleId())
                .toString();
    }
}