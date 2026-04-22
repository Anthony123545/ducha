package com.supervision.medicalhealth.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 防疫用品发放登记对象 bdgl_antiepidemic_putout
 * 
 * @author hr
 * @date 2022-02-14
 */
public class BdglAntiepidemicPutout extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 防疫品ID */
    @Excel(name = "防疫品ID")
    private Integer durgArchivesId;

    /** 发放数量 */
    @Excel(name = "发放数量")
    private Integer putOut;

    /** 存放位置 */
    @Excel(name = "存放位置")
    private String place;

    /** 领用时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "领用时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date putTime;

    /** 领用人 */
    @Excel(name = "领用人")
    private Integer peopleId;

    /** 录入人(创建人) */
    @Excel(name = "录入人(创建人)")
    private Long adminId;

    /** 品牌 */
    @Excel(name = "品牌")
    private String durgBrand;

    /** 规格 */
    @Excel(name = "规格")
    private String specifications;

    /** 领用单位 */
    @Excel(name = "领用单位")
    private Integer unitId;

    /** $column.columnComment */
    @Excel(name = "领用单位")
    private Integer unitId1;

    /** $column.columnComment */
    @Excel(name = "领用单位")
    private Integer storeHouse;
    /**创建时间*/
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createtime;
    /**修改时间*/
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatetime;

    /** 领用人姓名 */
    @Excel(name = "领用人姓名")
    private String peopleName;

    /** 领用人单位 */
    @Excel(name = "领用人单位")
    private String unitName;

    /** 发放单位名称 */
    @Excel(name = "发放单位名称")
    private String unitName1;

    /**防疫用品名称*/
    private String durgName;

    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDurgName() {
        return durgName;
    }

    public void setDurgName(String durgName) {
        this.durgName = durgName;
    }

    /** 备注 */
    @Excel(name = "备注")
    private String remark;
    //入库表id
    private Long inId;

    public Long getInId() {
        return inId;
    }

    public void setInId(Long inId) {
        this.inId = inId;
    }

    @Override
    public String getPeopleName() {
        return peopleName;
    }

    @Override
    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName;
    }

    @Override
    public String getUnitName() {
        return unitName;
    }

    @Override
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitName1() {
        return unitName1;
    }

    public void setUnitName1(String unitName1) {
        this.unitName1 = unitName1;
    }

    public void setId(Long id)
    {
        this.id = id;
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

    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
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
    public void setPutTime(Date putTime) 
    {
        this.putTime = putTime;
    }

    public Date getPutTime() 
    {
        return putTime;
    }
    public void setPeopleId(Integer peopleId) 
    {
        this.peopleId = peopleId;
    }

    public Integer getPeopleId() 
    {
        return peopleId;
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
    public void setUnitId1(Integer unitId1) 
    {
        this.unitId1 = unitId1;
    }

    public Integer getUnitId1() 
    {
        return unitId1;
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
            .append("putOut", getPutOut())
            .append("place", getPlace())
            .append("putTime", getPutTime())
            .append("peopleId", getPeopleId())
            .append("adminId", getAdminId())
            .append("createtime", getCreatetime())
            .append("updatetime", getUpdatetime())
            .append("durgBrand", getDurgBrand())
            .append("specifications", getSpecifications())
            .append("unitId", getUnitId())
            .append("remark", getRemark())
            .append("unitId1", getUnitId1())
            .append("storeHouse", getStoreHouse())
            .toString();
    }
}
