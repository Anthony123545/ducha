package com.supervision.medicalhealth.domain;

import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


import java.util.Date;

/**
 * 防疫用品库存档案对象 bdgl_antiepidemic_archives
 * 
 * @author hr
 * @date 2022-02-14
 */
public class BdglAntiepidemicArchives extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 防疫品名称 */
    @Excel(name = "防疫品名称")
    private String durgName;

    /** 品牌 */
    @Excel(name = "品牌")
    private String durgBrand;

    /** 规格 */
    @Excel(name = "规格")
    private String specifications;

    /** 库房 */
    @Excel(name = "库房")
    private String storeHouse;

    /** 单位 */
    @Excel(name = "单位")
    private Integer unitId;

    /** 库存数量 */
    @Excel(name = "库存数量")
    private Integer amount;

    /** 入库数量 */
    @Excel(name = "入库数量")
    private Integer putIn;

    /** 出库数量 */
    @Excel(name = "出库数量")
    private Integer putOut;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    /** 录入人(创建人) */
    @Excel(name = "录入人(创建人)")
    private Long adminId;
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createtime;
    /**修改时间*/
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatetime;
    /** 单位名称 */
    @Excel(name = "单位名称")
    private String unitName;

    /** 创建人姓名 */
    @Excel(name = "创建人姓名")
    private String userName;

    /**拼接名称*/
    private String name;

    private Integer overdueQuantity;

    private String barCode;

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public Integer getOverdueQuantity() {
        return overdueQuantity;
    }

    public void setOverdueQuantity(Integer overdueQuantity) {
        this.overdueQuantity = overdueQuantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setDurgName(String durgName) 
    {
        this.durgName = durgName;
    }

    public String getDurgName() 
    {
        return durgName;
    }
    public void setDurgBrand(String durgBrand) 
    {
        this.durgBrand = durgBrand;
    }

    public String getDurgBrand() 
    {
        return durgBrand;
    }
    public void setSpecifications(String specifications) 
    {
        this.specifications = specifications;
    }

    public String getSpecifications() 
    {
        return specifications;
    }

    public String getStoreHouse() {
        return storeHouse;
    }

    public void setStoreHouse(String storeHouse) {
        this.storeHouse = storeHouse;
    }

    public void setUnitId(Integer unitId)
    {
        this.unitId = unitId;
    }

    public Integer getUnitId() 
    {
        return unitId;
    }
    public void setAmount(Integer amount) 
    {
        this.amount = amount;
    }

    public Integer getAmount() 
    {
        return amount;
    }
    public void setPutIn(Integer putIn) 
    {
        this.putIn = putIn;
    }

    public Integer getPutIn() 
    {
        return putIn;
    }
    public void setPutOut(Integer putOut) 
    {
        this.putOut = putOut;
    }

    public Integer getPutOut() 
    {
        return putOut;
    }
    public void setRemarks(String remarks) 
    {
        this.remarks = remarks;
    }

    public String getRemarks() 
    {
        return remarks;
    }
    public void setAdminId(Long adminId) 
    {
        this.adminId = adminId;
    }

    public Long getAdminId() 
    {
        return adminId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("durgName", getDurgName())
            .append("durgBrand", getDurgBrand())
            .append("specifications", getSpecifications())
            .append("storeHouse", getStoreHouse())
            .append("unitId", getUnitId())
            .append("amount", getAmount())
            .append("putIn", getPutIn())
            .append("putOut", getPutOut())
            .append("remarks", getRemarks())
            .append("adminId", getAdminId())
            .append("createtime", getCreatetime())
            .append("updatetime", getUpdatetime())
            .toString();
    }
}
