package com.supervision.combatduty.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 装备登记对象 bdgl_regis_duty
 * 
 * @author supervision
 * @date 2022-03-11
 */
public class BdglRegisDuty extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;


    /**分类统计数*/
    private int typenumber;

    /** 单位名称 */
    @Excel(name = "单位名称")
    private String unitName;

    /** 单位ID */
    @Excel(name = "单位ID")
    private Long unitId;

    /** 装备名称 */
    @Excel(name = "装备名称")
    private String equipmentName;

    /** 装备ID */
    @Excel(name = "装备ID")
    private String equipmentId;

    /** 装备数量 */
    @Excel(name = "装备数量")
    private Long equipmentNumber;

    /** 类型 */
    @Excel(name = "类型")
    private String type;

    /** 装备状态 */
    @Excel(name = "装备状态")
    private String equipmentStates;

    /** 待命位置 */
    @Excel(name = "待命位置")
    private String standbyPosition;

    /** 值班日期 */
    @Excel(name = "值班日期")
    private String attendedTime;

    /** 装备情况 */
    @Excel(name = "装备情况")
    private String remarks;

    /**是否值班*/
    private  String isonduty;
    public int getTypenumber() {
        return typenumber;
    }

    public void setTypenumber(int typenumber) {
        this.typenumber = typenumber;
    }


    public String getIsonduty() {
        return isonduty;
    }

    public void setIsonduty(String isonduty) {
        this.isonduty = isonduty;
    }

    private List<BdglRegisDuty> regisDutyList;

    public List<BdglRegisDuty> getRegisDutyList() {
        return regisDutyList;
    }

    public void setRegisDutyList(List<BdglRegisDuty> regisDutyList) {
        this.regisDutyList = regisDutyList;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUnitName(String unitName) 
    {
        this.unitName = unitName;
    }

    public String getUnitName() 
    {
        return unitName;
    }
    public void setUnitId(Long unitId) 
    {
        this.unitId = unitId;
    }

    public Long getUnitId() 
    {
        return unitId;
    }
    public void setEquipmentName(String equipmentName) 
    {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentName() 
    {
        return equipmentName;
    }
    public void setEquipmentId(String equipmentId) 
    {
        this.equipmentId = equipmentId;
    }

    public String getEquipmentId() 
    {
        return equipmentId;
    }
    public void setEquipmentNumber(Long equipmentNumber) 
    {
        this.equipmentNumber = equipmentNumber;
    }

    public Long getEquipmentNumber() 
    {
        return equipmentNumber;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }
    public void setEquipmentStates(String equipmentStates) 
    {
        this.equipmentStates = equipmentStates;
    }

    public String getEquipmentStates() 
    {
        return equipmentStates;
    }
    public void setStandbyPosition(String standbyPosition) 
    {
        this.standbyPosition = standbyPosition;
    }

    public String getStandbyPosition() 
    {
        return standbyPosition;
    }
    public void setAttendedTime(String attendedTime) 
    {
        this.attendedTime = attendedTime;
    }

    public String getAttendedTime() 
    {
        return attendedTime;
    }
    public void setRemarks(String remarks) 
    {
        this.remarks = remarks;
    }

    public String getRemarks() 
    {
        return remarks;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("unitName", getUnitName())
            .append("unitId", getUnitId())
            .append("equipmentName", getEquipmentName())
            .append("equipmentId", getEquipmentId())
            .append("equipmentNumber", getEquipmentNumber())
            .append("type", getType())
            .append("equipmentStates", getEquipmentStates())
            .append("standbyPosition", getStandbyPosition())
            .append("attendedTime", getAttendedTime())
            .append("remarks", getRemarks())
            .toString();
    }
}
