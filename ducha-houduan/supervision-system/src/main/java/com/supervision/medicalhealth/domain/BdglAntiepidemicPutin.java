package com.supervision.medicalhealth.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 防疫用品入库登记对象 bdgl_antiepidemic_putin
 * 
 * @author hr
 * @date 2022-02-14
 */
public class BdglAntiepidemicPutin extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 防疫品ID */
    @Excel(name = "防疫品ID")
    private Integer durgArchivesId;

    /** 入库数量 */
    @Excel(name = "入库数量")
    private Integer putIn;

    /** 入库位置 */
    @Excel(name = "入库位置")
    private String place;

    /** 入库时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入库时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date putTime;

    /** 录入人(创建人) */
    @Excel(name = "录入人(创建人)")
    private Long adminId;

    /** 品牌 */
    @Excel(name = "品牌")
    private String durgBrand;

    /** 规格 */
    @Excel(name = "规格")
    private String specifications;

    /** $column.columnComment */
    @Excel(name = "规格")
    private Integer unitId;

    /** $column.columnComment */
    @Excel(name = "规格")
    private Integer storeHouse;
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createtime;
    /**修改时间*/
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatetime;

    /**防疫用品名称*/
    private String durgName;

    /** 过期时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "过期时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date expirationTime;

    /** 入库编号  */
    @Excel(name = "入库编号 ")
    private String putNumber;

    /**销毁状态*/
    private String state;


    /** 非正常过期状态 */
    @Excel(name = "非正常过期状态")
    private String expiredstatus;

    /** 非正常过期备注 */
    @Excel(name = "非正常过期备注")
    private String beizhu;

    private String name;

    private Integer surplus;

    private Date time;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getSurplus() {
        return surplus;
    }

    public void setSurplus(Integer surplus) {
        this.surplus = surplus;
    }

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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getPutNumber() {
        return putNumber;
    }

    public void setPutNumber(String putNumber) {
        this.putNumber = putNumber;
    }

    public String getDurgName() {
        return durgName;
    }

    public void setDurgName(String durgName) {
        this.durgName = durgName;
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
    public void setUnitId(Integer unitId) 
    {
        this.unitId = unitId;
    }

    public Integer getUnitId() 
    {
        return unitId;
    }
    public void setStoreHouse(Integer storeHouse) 
    {
        this.storeHouse = storeHouse;
    }

    public Integer getStoreHouse() 
    {
        return storeHouse;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("durgArchivesId", getDurgArchivesId())
            .append("putIn", getPutIn())
            .append("place", getPlace())
            .append("putTime", getPutTime())
            .append("adminId", getAdminId())
            .append("createtime", getCreatetime())
            .append("updatetime", getUpdatetime())
            .append("durgBrand", getDurgBrand())
            .append("specifications", getSpecifications())
            .append("unitId", getUnitId())
            .append("storeHouse", getStoreHouse())
            .toString();
    }
}
