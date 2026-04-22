package com.supervision.materialManagement.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 营具物资变更对象 bdgl_property_change
 * 
 * @author supervision
 * @date 2022-03-08
 */
public class BdglPropertyChange extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;
    
    /** 条形码编号 */
    @Excel(name = "条形码编号")
    private String barcode;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Integer propertyId;

    /** 营具名称 */
    @Excel(name = "营具名称")
    private String name;

    /** 营具编号 */
    @Excel(name = "营具编号")
    private String rukuNumber;

    /** 变更时间 */
    @Excel(name = "变更时间")
    private Date changeTime;

    /** 变更前所属单位 */
    @Excel(name = "变更前所属单位")
    private Integer beforeUnitId;

    /** 变更后所属单位 */
    @Excel(name = "变更后所属单位")
    private Integer afterUnitId;

    /** 变更前人员名称 */
    @Excel(name = "变更前人员名称")
    private Integer beforePeopleId;

    /** 变更后人员名称 */
    @Excel(name = "变更后人员名称")
    private Integer afterPeopleId;

    /** 变更前单位名称 */
    @Excel(name = "变更前单位名称")
    private String beforeUnitName;

    /** 变更后单位名称 */
    @Excel(name = "变更后单位名称")
    private String afterUnitName;

    /** 变更前人员名称 */
    @Excel(name = "变更前人员名称")
    private String beforePeopleName;

    /** 变更后人员名称 */
    @Excel(name = "变更后人员名称")
    private String afterPeopleName;
    
    public String getBarcode() {
        return barcode;
    }
    
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }
    
    public String getBeforeUnitName() {
        return beforeUnitName;
    }

    public void setBeforeUnitName(String beforeUnitName) {
        this.beforeUnitName = beforeUnitName;
    }

    public String getAfterUnitName() {
        return afterUnitName;
    }

    public void setAfterUnitName(String afterUnitName) {
        this.afterUnitName = afterUnitName;
    }

    public String getBeforePeopleName() {
        return beforePeopleName;
    }

    public void setBeforePeopleName(String beforePeopleName) {
        this.beforePeopleName = beforePeopleName;
    }

    public String getAfterPeopleName() {
        return afterPeopleName;
    }

    public void setAfterPeopleName(String afterPeopleName) {
        this.afterPeopleName = afterPeopleName;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setPropertyId(Integer propertyId) 
    {
        this.propertyId = propertyId;
    }

    public Integer getPropertyId() 
    {
        return propertyId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setRukuNumber(String rukuNumber) 
    {
        this.rukuNumber = rukuNumber;
    }

    public String getRukuNumber() 
    {
        return rukuNumber;
    }

    public Date getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }

    public void setBeforeUnitId(Integer beforeUnitId)
    {
        this.beforeUnitId = beforeUnitId;
    }

    public Integer getBeforeUnitId() 
    {
        return beforeUnitId;
    }
    public void setAfterUnitId(Integer afterUnitId) 
    {
        this.afterUnitId = afterUnitId;
    }

    public Integer getAfterUnitId() 
    {
        return afterUnitId;
    }
    public void setBeforePeopleId(Integer beforePeopleId) 
    {
        this.beforePeopleId = beforePeopleId;
    }

    public Integer getBeforePeopleId() 
    {
        return beforePeopleId;
    }
    public void setAfterPeopleId(Integer afterPeopleId) 
    {
        this.afterPeopleId = afterPeopleId;
    }

    public Integer getAfterPeopleId() 
    {
        return afterPeopleId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("propertyId", getPropertyId())
            .append("name", getName())
            .append("rukuNumber", getRukuNumber())
            .append("changeTime", getChangeTime())
            .append("beforeUnitId", getBeforeUnitId())
            .append("afterUnitId", getAfterUnitId())
            .append("beforePeopleId", getBeforePeopleId())
            .append("afterPeopleId", getAfterPeopleId())
            .append("remark", getRemark())
            .append("createtime", getCreatetime())
            .append("updatetime", getUpdatetime())
            .toString();
    }
}
