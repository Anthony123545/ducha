package com.supervision.grassrootsregistration.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 训练器材/教材登记薄对象 bdgl_equip
 *
 * @author supervision
 * @date 2022-03-03
 */
public class BdglEquip extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** 类型 */
    @Excel(name = "类型")
    private String type;

    /** 物品名称 */
    @Excel(name = "物品名称")
    private String title;

    /** 配发单位 */
    @Excel(name = "配发单位")
    private Integer unitId;

    /** 配发时间 */
    @Excel(name = "配发时间")
    private String granttime;

    /** 配发数量 */
    @Excel(name = "配发数量")
    private Long grantnum;

    /** 自筹数量 */
    @Excel(name = "自筹数量")
    private Long ownnum;

    /** 库存数量 */
    @Excel(name = "库存数量")
    private Long stocknum;

    /** 入库时间 */
    @Excel(name = "入库时间")
    private String intime;

    /** 入库数量 */
    @Excel(name = "入库数量")
    private Long innum;

    /** 入库经手人id */
    @Excel(name = "入库经手人id")
    private Integer peopleId;

    /** 出库时间 */
    @Excel(name = "出库时间")
    private String outtime;

    /** 出库数量 */
    @Excel(name = "出库数量")
    private Long outnum;

    /** 出库经手人id */
    @Excel(name = "出库经手人id")
    private Integer peopleId1;

    /** 质量情况 */
    @Excel(name = "质量情况")
    private String quality;

    /** 备注 */
    @Excel(name = "备注")
    private String info;

    /** 单位 */
    @Excel(name = "单位")
    private Long unitId1;

    /** 操作人 */
    @Excel(name = "操作人")
    private Integer adminId;

    /** 单位名称 */
    @Excel(name = "单位名称")
    private String unitName;

    /** 入库经手人 */
    @Excel(name = "入库经手人")
    private String peopleName;

    /** 出库经手人 */
    @Excel(name = "出库经手人")
    private String people1Name;
    /**配发单位名称*/
    @Excel(name = "佩服你单位名称")
    private String unitName1;


    private String typeName;

    private String year;
    private String month;
    private String day;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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

    public Long getId()
    {
        return id;
    }
    public void setType(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }
    public void setUnitId(Integer unitId)
    {
        this.unitId = unitId;
    }

    public Integer getUnitId()
    {
        return unitId;
    }
    public void setGranttime(String granttime)
    {
        this.granttime = granttime;
    }

    public String getGranttime()
    {
        return granttime;
    }
    public void setGrantnum(Long grantnum)
    {
        this.grantnum = grantnum;
    }

    public Long getGrantnum()
    {
        return grantnum;
    }
    public void setOwnnum(Long ownnum)
    {
        this.ownnum = ownnum;
    }

    public Long getOwnnum()
    {
        return ownnum;
    }
    public void setStocknum(Long stocknum)
    {
        this.stocknum = stocknum;
    }

    public Long getStocknum()
    {
        return stocknum;
    }
    public void setIntime(String intime)
    {
        this.intime = intime;
    }

    public String getIntime()
    {
        return intime;
    }
    public void setInnum(Long innum)
    {
        this.innum = innum;
    }

    public Long getInnum()
    {
        return innum;
    }
    public void setPeopleId(Integer peopleId)
    {
        this.peopleId = peopleId;
    }

    public Integer getPeopleId()
    {
        return peopleId;
    }
    public void setOuttime(String outtime)
    {
        this.outtime = outtime;
    }

    public String getOuttime()
    {
        return outtime;
    }
    public void setOutnum(Long outnum)
    {
        this.outnum = outnum;
    }

    public Long getOutnum()
    {
        return outnum;
    }

    public Integer getPeopleId1() {
        return peopleId1;
    }

    public void setPeopleId1(Integer peopleId1) {
        this.peopleId1 = peopleId1;
    }

    public void setQuality(String quality)
    {
        this.quality = quality;
    }

    public String getQuality()
    {
        return quality;
    }
    public void setInfo(String info)
    {
        this.info = info;
    }

    public String getInfo()
    {
        return info;
    }
    public void setUnitId1(Long unitId1)
    {
        this.unitId1 = unitId1;
    }

    public Long getUnitId1()
    {
        return unitId1;
    }
    public void setAdminId(Integer adminId)
    {
        this.adminId = adminId;
    }

    public Integer getAdminId()
    {
        return adminId;
    }
    public void setUnitName(String unitName)
    {
        this.unitName = unitName;
    }

    public String getUnitName()
    {
        return unitName;
    }
    public void setPeopleName(String peopleName)
    {
        this.peopleName = peopleName;
    }

    public String getPeopleName()
    {
        return peopleName;
    }
    public void setPeople1Name(String people1Name)
    {
        this.people1Name = people1Name;
    }

    public String getPeople1Name()
    {
        return people1Name;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("type", getType())
                .append("title", getTitle())
                .append("unitId", getUnitId())
                .append("granttime", getGranttime())
                .append("grantnum", getGrantnum())
                .append("ownnum", getOwnnum())
                .append("stocknum", getStocknum())
                .append("intime", getIntime())
                .append("innum", getInnum())
                .append("peopleId", getPeopleId())
                .append("outtime", getOuttime())
                .append("outnum", getOutnum())
                .append("peopleId1", getPeopleId1())
                .append("quality", getQuality())
                .append("info", getInfo())
                .append("unitId1", getUnitId1())
                .append("createtime", getCreatetime())
                .append("adminId", getAdminId())
                .append("unitName", getUnitName())
                .append("peopleName", getPeopleName())
                .append("people1Name", getPeople1Name())
                .toString();
    }
}