package com.supervision.medicalhealth.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 防疫用品销毁记录对象 bdgl_antiepidemic_ruin
 * 
 * @author supervision
 * @date 2022-04-07
 */
public class BdglAntiepidemicRuin extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Integer durgArchivesId;

    /** 销毁数量 */
    @Excel(name = "销毁数量")
    private String putIn;

    /** 厂家 */
    @Excel(name = "厂家")
    private String manufacturer;

    /** 规格 */
    @Excel(name = "规格")
    private String specifications;

    /** 品牌 */
    @Excel(name = "品牌")
    private String durgBrand;

    /** $column.columnComment */
    @Excel(name = "品牌")
    private Integer putinId;

    /** $column.columnComment */
    @Excel(name = "品牌")
    private Integer status;

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
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
    public void setPutIn(String putIn) 
    {
        this.putIn = putIn;
    }

    public String getPutIn() 
    {
        return putIn;
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
    public void setDurgBrand(String durgBrand) 
    {
        this.durgBrand = durgBrand;
    }

    public String getDurgBrand() 
    {
        return durgBrand;
    }
    public void setPutinId(Integer putinId) 
    {
        this.putinId = putinId;
    }

    public Integer getPutinId() 
    {
        return putinId;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("durgArchivesId", getDurgArchivesId())
            .append("putIn", getPutIn())
            .append("manufacturer", getManufacturer())
            .append("specifications", getSpecifications())
            .append("durgBrand", getDurgBrand())
            .append("createtime", getCreatetime())
            .append("putinId", getPutinId())
            .append("status", getStatus())
            .toString();
    }
}
