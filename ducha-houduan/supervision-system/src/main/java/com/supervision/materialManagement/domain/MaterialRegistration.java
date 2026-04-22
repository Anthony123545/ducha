package com.supervision.materialManagement.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 物资入库登记对象 material_registration
 * 
 * @author supervision
 * @date 2022-03-08
 */
public class MaterialRegistration extends BaseEntity
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

    /** 入库数量 */
    @Excel(name = "入库数量")
    private Long putInt;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createName;

    /** 入库时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入库时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date storageTime;

    /** 入库编号 */
    @Excel(name = "入库编号")
    private String inboundNo;

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

    /** 库房(待定) */
    @Excel(name = "库房(待定)")
    private String warehouse;

    /** 档案id */
    @Excel(name = "档案id")
    private Long archivesId;

    private Long createid;

    private Long UnitId;

    private String names;

    private Long total;
    
    public String getBarcode() {
        return barcode;
    }
    
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
    
    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public Long getUnitId() {
        return UnitId;
    }

    public void setUnitId(Long unitId) {
        UnitId = unitId;
    }

    public Long getCreateid() {
        return createid;
    }

    public void setCreateid(Long createid) {
        this.createid = createid;
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
    public void setPutInt(Long putInt) 
    {
        this.putInt = putInt;
    }

    public Long getPutInt() 
    {
        return putInt;
    }
    public void setCreateName(String createName) 
    {
        this.createName = createName;
    }

    public String getCreateName() 
    {
        return createName;
    }
    public void setStorageTime(Date storageTime) 
    {
        this.storageTime = storageTime;
    }

    public Date getStorageTime() 
    {
        return storageTime;
    }
    public void setInboundNo(String inboundNo) 
    {
        this.inboundNo = inboundNo;
    }

    public String getInboundNo() 
    {
        return inboundNo;
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
            .append("putInt", getPutInt())
            .append("createName", getCreateName())
            .append("createTime", getCreateTime())
            .append("storageTime", getStorageTime())
            .append("inboundNo", getInboundNo())
            .append("storageLocation", getStorageLocation())
            .append("buyingTime", getBuyingTime())
            .append("price", getPrice())
            .append("remark", getRemark())
            .append("warehouse", getWarehouse())
            .append("archivesId", getArchivesId())
            .toString();
    }
}
