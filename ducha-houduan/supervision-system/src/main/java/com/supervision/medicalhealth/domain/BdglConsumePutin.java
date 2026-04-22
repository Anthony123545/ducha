package com.supervision.medicalhealth.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 耗材入库登记对象 bdgl_consume_putin
 * 
 * @author hr
 * @date 2022-02-12
 */
public class BdglConsumePutin extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 药品ID */
    @Excel(name = "药品ID")
    private Integer durgArchivesId;

    /** 入库数量 */
    @Excel(name = "入库数量")
    private Integer putIn;

    /** 入库位置 */
    @Excel(name = "入库位置")
    private String place;

    /** 药品来源 */
    @Excel(name = "药品来源")
    private String source;

    /** 入库说明 */
    @Excel(name = "入库说明")
    private String remarks;

    /** 过期日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "过期日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date overduedate;

    /** 入库时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入库时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date putTime;

    /** 录入人(创建人) */
    @Excel(name = "录入人(创建人)")
    private Long adminId;

    /** 编号 */
    @Excel(name = "编号")
    private String number;

    /** 剩余库存 */
    @Excel(name = "剩余库存")
    private Integer surplus;

    /** 是否销毁 */
    @Excel(name = "是否销毁")
    private String status;

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

    /** 库房 */
    @Excel(name = "库房")
    private Integer storeHouse;

    /** $column.columnComment */
    @Excel(name = "库房")
    private Integer unitId;
    /**创建时间*/
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;
    /**修改时间*/
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateTime;
    /** 单位名称 */
    @Excel(name = "单位名称")
    private String unitName;

    /** 创建人姓名 */
    @Excel(name = "创建人姓名")
    private String userName;

    /** 耗材名称 */
    @Excel(name = "耗材名称")
    private String durgName;

    /** 非正常过期状态 */
    @Excel(name = "非正常过期状态")
    private String expiredstatus;

    /** 非正常过期备注 */
    @Excel(name = "非正常过期备注")
    private String beizhu;

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpiredstatus() {
        return expiredstatus;
    }

    public void setExpiredstatus(String expiredstatus) {
        this.expiredstatus = expiredstatus;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    @Override
    public String getUnitName() {
        return unitName;
    }

    @Override
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDurgName() {
        return durgName;
    }

    public void setDurgName(String durgName) {
        this.durgName = durgName;
    }

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
    public void setPutIn(Integer putIn) 
    {
        this.putIn = putIn;
    }

    public Integer getPutIn() 
    {
        return putIn;
    }
    public void setPlace(String place) 
    {
        this.place = place;
    }

    public String getPlace() 
    {
        return place;
    }
    public void setSource(String source) 
    {
        this.source = source;
    }

    public String getSource() 
    {
        return source;
    }
    public void setRemarks(String remarks) 
    {
        this.remarks = remarks;
    }

    public String getRemarks() 
    {
        return remarks;
    }
    public void setOverduedate(Date overduedate) 
    {
        this.overduedate = overduedate;
    }

    public Date getOverduedate() 
    {
        return overduedate;
    }
    public void setPutTime(Date putTime) 
    {
        this.putTime = putTime;
    }

    public Date getPutTime() 
    {
        return putTime;
    }
    public void setAdminId(Long adminId) 
    {
        this.adminId = adminId;
    }

    public Long getAdminId() 
    {
        return adminId;
    }
    public void setNumber(String number) 
    {
        this.number = number;
    }

    public String getNumber() 
    {
        return number;
    }
    public void setSurplus(Integer surplus) 
    {
        this.surplus = surplus;
    }

    public Integer getSurplus() 
    {
        return surplus;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("durgArchivesId", getDurgArchivesId())
            .append("putIn", getPutIn())
            .append("place", getPlace())
            .append("source", getSource())
            .append("remarks", getRemarks())
            .append("overduedate", getOverduedate())
            .append("putTime", getPutTime())
            .append("adminId", getAdminId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("number", getNumber())
            .append("surplus", getSurplus())
            .append("status", getStatus())
            .append("durgBrand", getDurgBrand())
            .append("manufacturer", getManufacturer())
            .append("specifications", getSpecifications())
            .append("productIntroduction", getProductIntroduction())
            .append("storeHouse", getStoreHouse())
            .append("unitId", getUnitId())
            .toString();
    }
}
