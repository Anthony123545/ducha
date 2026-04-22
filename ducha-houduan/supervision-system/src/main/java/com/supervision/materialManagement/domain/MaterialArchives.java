package com.supervision.materialManagement.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

import java.math.BigDecimal;

/**
 * 物资档案对象 material_archives
 * 
 * @author supervision
 * @date 2022-03-08
 */
public class MaterialArchives extends BaseEntity
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

    /** 总数量 */
    @Excel(name = "总数量")
    private Long materialTotal;

    /** 库存数量 */
    @Excel(name = "库存数量")
    private Long materialInventory;

    /** 在用数量 */
    @Excel(name = "在用数量")
    private Long inUseTotal;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createName;

    /** 库房 */
    @Excel(name = "库房")
    private String warehouse;
    
    /** 入库id*/
    private Long registrationId;
    
    /** 入库id*/
    private BigDecimal price;

    /**单位id*/
    private Integer unitId;

    /**责任人id*/
    private Integer personLiableId;

    private Long createid;

    private String names;
    
    public Long getRegistrationId() {
        return registrationId;
    }
    
    public void setRegistrationId(Long registrationId) {
        this.registrationId = registrationId;
    }
    
    public String getBarcode() {
        return barcode;
    }
    
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
    
    public BigDecimal getPrice() {
        return price;
    }
    
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public Long getCreateid() {
        return createid;
    }

    public void setCreateid(Long createid) {
        this.createid = createid;
    }

    public Integer getPersonLiableId() {
        return personLiableId;
    }

    public void setPersonLiableId(Integer personLiableId) {
        this.personLiableId = personLiableId;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
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
    public void setMaterialTotal(Long materialTotal) 
    {
        this.materialTotal = materialTotal;
    }

    public Long getMaterialTotal() 
    {
        return materialTotal;
    }
    public void setMaterialInventory(Long materialInventory) 
    {
        this.materialInventory = materialInventory;
    }

    public Long getMaterialInventory() 
    {
        return materialInventory;
    }
    public void setInUseTotal(Long inUseTotal) 
    {
        this.inUseTotal = inUseTotal;
    }

    public Long getInUseTotal() 
    {
        return inUseTotal;
    }
    public void setCreateName(String createName) 
    {
        this.createName = createName;
    }

    public String getCreateName() 
    {
        return createName;
    }
    public void setWarehouse(String warehouse) 
    {
        this.warehouse = warehouse;
    }

    public String getWarehouse() 
    {
        return warehouse;
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
            .append("materialTotal", getMaterialTotal())
            .append("materialInventory", getMaterialInventory())
            .append("inUseTotal", getInUseTotal())
            .append("createName", getCreateName())
            .append("createTime", getCreateTime())
            .append("warehouse", getWarehouse())
            .toString();
    }
}
