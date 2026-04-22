package com.supervision.bdglSecret.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 涉密计算机台账对象 secret_account
 * 
 * @author lanse
 * @date 2022-10-30
 */
public class SecretAccount extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 单位 */
    @Excel(name = "单位")
    private String unitname;

    /** 类型 */
    @Excel(name = "类型")
    private String stype;

    /** 品牌 */
    @Excel(name = "品牌")
    private String brand;

    /** 型号 */
    @Excel(name = "型号")
    private String model;

    /** 硬盘序列号 */
    @Excel(name = "硬盘序列号")
    private String harddisknumber;

    /** MAC地址/物理地 */
    @Excel(name = "MAC地址/物理地")
    private String macaddress;

    /** 密级 */
    @Excel(name = "密级")
    private String confidentialitylevel;

    /** 保密编码 */
    @Excel(name = "保密编码")
    private String levelnumber;

    /** 责任人 */
    @Excel(name = "责任人")
    private String persoeliable;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    /** 单位ID */
    @Excel(name = "单位ID")
    private Long unitid;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUnitname(String unitname) 
    {
        this.unitname = unitname;
    }

    public String getUnitname() 
    {
        return unitname;
    }
    public void setStype(String stype) 
    {
        this.stype = stype;
    }

    public String getStype() 
    {
        return stype;
    }
    public void setBrand(String brand) 
    {
        this.brand = brand;
    }

    public String getBrand() 
    {
        return brand;
    }
    public void setModel(String model) 
    {
        this.model = model;
    }

    public String getModel() 
    {
        return model;
    }
    public void setHarddisknumber(String harddisknumber) 
    {
        this.harddisknumber = harddisknumber;
    }

    public String getHarddisknumber() 
    {
        return harddisknumber;
    }
    public void setMacaddress(String macaddress) 
    {
        this.macaddress = macaddress;
    }

    public String getMacaddress() 
    {
        return macaddress;
    }
    public void setConfidentialitylevel(String confidentialitylevel) 
    {
        this.confidentialitylevel = confidentialitylevel;
    }

    public String getConfidentialitylevel() 
    {
        return confidentialitylevel;
    }
    public void setLevelnumber(String levelnumber) 
    {
        this.levelnumber = levelnumber;
    }

    public String getLevelnumber() 
    {
        return levelnumber;
    }
    public void setpersoeliable(String persoeliable)
    {
        this.persoeliable = persoeliable;
    }

    public String getpersoeliable()
    {
        return persoeliable;
    }
    public void setRemarks(String remarks) 
    {
        this.remarks = remarks;
    }

    public String getRemarks() 
    {
        return remarks;
    }
    public void setUnitid(Long unitid) 
    {
        this.unitid = unitid;
    }

    public Long getUnitid() 
    {
        return unitid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("unitname", getUnitname())
            .append("stype", getStype())
            .append("brand", getBrand())
            .append("model", getModel())
            .append("harddisknumber", getHarddisknumber())
            .append("macaddress", getMacaddress())
            .append("confidentialitylevel", getConfidentialitylevel())
            .append("levelnumber", getLevelnumber())
            .append("persoeliable", getpersoeliable())
            .append("remarks", getRemarks())
            .append("unitid", getUnitid())
            .toString();
    }
}
