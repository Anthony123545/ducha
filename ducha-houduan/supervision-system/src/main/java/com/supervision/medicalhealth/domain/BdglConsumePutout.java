package com.supervision.medicalhealth.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 耗材出库登记对象 bdgl_consume_putout
 * 
 * @author hr
 * @date 2022-02-12
 */
public class BdglConsumePutout extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 药品ID */
    @Excel(name = "药品ID")
    private Integer durgArchivesId;

    /** 出库数量 */
    @Excel(name = "出库数量")
    private Integer putOut;

    /** 出库位置 */
    @Excel(name = "出库位置")
    private String place;

    /** 药品用途 */
    @Excel(name = "药品用途")
    private String purpose;

    /** 出库说明 */
    @Excel(name = "出库说明")
    private String remarks;

    /** 出库时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出库时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date putTime;

    /** 领用人 */
    @Excel(name = "领用人")
    private Integer receiveId;

    /** 录入人(创建人) */
    @Excel(name = "录入人(创建人)")
    private Long adminId;

    /** 品牌 */
    @Excel(name = "品牌")
    private String durgBrand;

    /** 生产厂商 */
    @Excel(name = "生产厂商")
    private String manufacturer;

    /** 规格 */
    @Excel(name = "规格")
    private String specifications;

    /** 功能主治 */
    @Excel(name = "功能主治")
    private String productIntroduction;

    /** 编号 */
    @Excel(name = "编号")
    private String number;

    /** 库房 */
    @Excel(name = "库房")
    private Integer storeHouse;

    /** $column.columnComment */
    @Excel(name = "库房")
    private Integer unitId;

    /** 使用单位 */
    @Excel(name = "使用单位")
    private Integer unitId1;
    /** 使用单位名称 */
    @Excel(name = "使用单位名称")
    private String unitName1;

    /** 发放单位名称 */
    @Excel(name = "发放单位名称")
    private String unitName;

    /** 领用人姓名 */
    @Excel(name = "领用人姓名")
    private String peopleNamee;

    /** 耗材名称 */
    @Excel(name = "耗材名称")
    private String durgName;

    /** 创建人姓名 */
    @Excel(name = "创建人姓名")
    private String userName;

    private Long inId;

    public Long getInId() {
        return inId;
    }

    public void setInId(Long inId) {
        this.inId = inId;
    }

    public String getUnitName1() {
        return unitName1;
    }

    public void setUnitName1(String unitName1) {
        this.unitName1 = unitName1;
    }

    @Override
    public String getUnitName() {
        return unitName;
    }

    @Override
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getPeopleNamee() {
        return peopleNamee;
    }

    public void setPeopleNamee(String peopleNamee) {
        this.peopleNamee = peopleNamee;
    }

    public String getDurgName() {
        return durgName;
    }

    public void setDurgName(String durgName) {
        this.durgName = durgName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**创建时间*/
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;
    /**修改时间*/
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setDurgArchivesId(Integer durgArchivesId) 
    {
        this.durgArchivesId = durgArchivesId;
    }

    public Integer getDurgArchivesId() 
    {
        return durgArchivesId;
    }
    public void setPutOut(Integer putOut) 
    {
        this.putOut = putOut;
    }

    public Integer getPutOut() 
    {
        return putOut;
    }
    public void setPlace(String place) 
    {
        this.place = place;
    }

    public String getPlace() 
    {
        return place;
    }
    public void setPurpose(String purpose) 
    {
        this.purpose = purpose;
    }

    public String getPurpose() 
    {
        return purpose;
    }
    public void setRemarks(String remarks) 
    {
        this.remarks = remarks;
    }

    public String getRemarks() 
    {
        return remarks;
    }
    public void setPutTime(Date putTime) 
    {
        this.putTime = putTime;
    }

    public Date getPutTime() 
    {
        return putTime;
    }
    public void setReceiveId(Integer receiveId) 
    {
        this.receiveId = receiveId;
    }

    public Integer getReceiveId() 
    {
        return receiveId;
    }
    public void setAdminId(Long adminId) 
    {
        this.adminId = adminId;
    }

    public Long getAdminId() 
    {
        return adminId;
    }
    public void setDurgBrand(String durgBrand) 
    {
        this.durgBrand = durgBrand;
    }

    public String getDurgBrand() 
    {
        return durgBrand;
    }
    public void setManufacturer(String manufacturer) 
    {
        this.manufacturer = manufacturer;
    }

    public String getManufacturer() 
    {
        return manufacturer;
    }
    public void setSpecifications(String specifications) 
    {
        this.specifications = specifications;
    }

    public String getSpecifications() 
    {
        return specifications;
    }
    public void setProductIntroduction(String productIntroduction) 
    {
        this.productIntroduction = productIntroduction;
    }

    public String getProductIntroduction() 
    {
        return productIntroduction;
    }
    public void setNumber(String number) 
    {
        this.number = number;
    }

    public String getNumber() 
    {
        return number;
    }
    public void setStoreHouse(Integer storeHouse) 
    {
        this.storeHouse = storeHouse;
    }

    public Integer getStoreHouse() 
    {
        return storeHouse;
    }
    public void setUnitId(Integer unitId) 
    {
        this.unitId = unitId;
    }

    public Integer getUnitId() 
    {
        return unitId;
    }
    public void setUnitId1(Integer unitId1) 
    {
        this.unitId1 = unitId1;
    }

    public Integer getUnitId1() 
    {
        return unitId1;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("durgArchivesId", getDurgArchivesId())
            .append("putOut", getPutOut())
            .append("place", getPlace())
            .append("purpose", getPurpose())
            .append("remarks", getRemarks())
            .append("putTime", getPutTime())
            .append("receiveId", getReceiveId())
            .append("adminId", getAdminId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("durgBrand", getDurgBrand())
            .append("manufacturer", getManufacturer())
            .append("specifications", getSpecifications())
            .append("productIntroduction", getProductIntroduction())
            .append("number", getNumber())
            .append("storeHouse", getStoreHouse())
            .append("unitId", getUnitId())
            .append("unitId1", getUnitId1())
            .toString();
    }
}
