package com.supervision.medicalhealth.domain;

import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


import java.util.Date;

/**
 * 防疫用品库存数据对象 bdgl_antiepidemic_amount
 *
 * @author hr
 * @date 2022-02-14
 */
public class BdglAntiepidemicAmount extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 防疫品ID */
    @Excel(name = "防疫品ID")
    private Long durgArchivesId;

    /** 单位ID */
    @Excel(name = "单位ID")
    private Long unitId;

    /** 库房 */
    @Excel(name = "库房")
    private Long storeHouse;

    /** 库存数量 */
    @Excel(name = "库存数量")
    private Integer amount;

    /** 入库数量 */
    @Excel(name = "入库数量")
    private Integer putIn;

    /** 出库数量 */
    @Excel(name = "出库数量")
    private Integer putOut;

    /** 录入人 */
    @Excel(name = "录入人")
    private Long adminId;

    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createtime;
    /**修改时间*/
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatetime;

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
    public void setDurgArchivesId(Long durgArchivesId)
    {
        this.durgArchivesId = durgArchivesId;
    }

    public Long getDurgArchivesId()
    {
        return durgArchivesId;
    }
    public void setUnitId(Long unitId)
    {
        this.unitId = unitId;
    }

    public Long getUnitId()
    {
        return unitId;
    }
    public void setStoreHouse(Long storeHouse)
    {
        this.storeHouse = storeHouse;
    }

    public Long getStoreHouse()
    {
        return storeHouse;
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
            .append("durgArchivesId", getDurgArchivesId())
            .append("unitId", getUnitId())
            .append("storeHouse", getStoreHouse())
            .append("amount", getAmount())
            .append("putIn", getPutIn())
            .append("putOut", getPutOut())
            .append("adminId", getAdminId())
            .append("createtime", getCreatetime())
            .append("updatetime", getUpdatetime())
            .toString();
    }
}
