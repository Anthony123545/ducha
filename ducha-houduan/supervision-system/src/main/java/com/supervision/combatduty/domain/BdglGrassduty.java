package com.supervision.combatduty.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;
import org.apache.ibatis.annotations.Delete;

import java.util.List;

/**
 * 基层值班对象 bdgl_grassduty
 * 
 * @author supervision
 * @date 2022-03-10
 */
public class BdglGrassduty extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 值班时间 */
    @Excel(name = "值班时间")
    private String attendedTime;

    /** 单位名称 */
    @Excel(name = "单位名称",width = 30)
    private String unitName;

    /** 单位ID */
//    @Excel(name = "单位ID")
    private Long unitId;

    /** 值班首长 */
    @Excel(name = "值班首长")
    private String chiefDutyName;

    /** 值班首长ID */
//    @Excel(name = "值班首长ID")
    private Long chiefDutyId;

    /** 值班首长职务 */
//    @Excel(name = "值班首长职务",dictType="sys_duty_hief")
    private String chiefDutyZhiwu;

    /** 值班首长手机 */
    @Excel(name = "值班首长手机")
    private String chiefDutyPhone;
    
    /** 办公电话 */
    @Excel(name = "办公电话")
    private String telephoneBg;
    
    /** 宿舍电话 */
    @Excel(name = "宿舍电话")
    private String telephoneSs;

    /** 值班员 */
    @Excel(name = "值班员")
    private String dutyOffierName;

    /** 值班员ID */
//    @Excel(name = "值班员ID")
    private Long dutyOffierId;

    /** 值班员职务 */
//    @Excel(name = "值班员职务",dictType="")
    private String dutyOffierZw;

    /** 值班员手机 */
    @Excel(name = "值班员手机")
    private String dutyOffierPhone;

    /** 值班电话 */
    @Excel(name = "值班电话")
    private String dutyPhone;

    /** 值班岗位 */
//    @Excel(name = "值班岗位")
    private String dutyGangwei;
    
    /** 值班岗位 */
    @Excel(name = "值班岗位",width = 30)
    private String dutyGangweiName;

    /**
     * 值班时间区间
     */
    private List<String> timequjian;

    /**
     * 当前状态
     */
    private String type;
    /**
     * 当前记录起始索引
     */
    private Integer pageNum;

    /**
     * 每页显示记录数
     */
    private Integer pageSize;
    
    public String getDutyGangweiName() {
        return dutyGangweiName;
    }
    
    public void setDutyGangweiName(String dutyGangweiName) {
        this.dutyGangweiName = dutyGangweiName;
    }
    
    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getTimequjian() {
        return timequjian;
    }

    public void setTimequjian(List<String> timequjian) {
        this.timequjian = timequjian;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setAttendedTime(String attendedTime) 
    {
        this.attendedTime = attendedTime;
    }

    public String getAttendedTime() 
    {
        return attendedTime;
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
    public void setChiefDutyName(String chiefDutyName) 
    {
        this.chiefDutyName = chiefDutyName;
    }

    public String getChiefDutyName() 
    {
        return chiefDutyName;
    }
    public void setChiefDutyId(Long chiefDutyId) 
    {
        this.chiefDutyId = chiefDutyId;
    }

    public Long getChiefDutyId() 
    {
        return chiefDutyId;
    }
    public void setChiefDutyZhiwu(String chiefDutyZhiwu) 
    {
        this.chiefDutyZhiwu = chiefDutyZhiwu;
    }

    public String getChiefDutyZhiwu() 
    {
        return chiefDutyZhiwu;
    }
    public void setTelephoneBg(String telephoneBg) 
    {
        this.telephoneBg = telephoneBg;
    }

    public String getTelephoneBg() 
    {
        return telephoneBg;
    }
    public void setTelephoneSs(String telephoneSs) 
    {
        this.telephoneSs = telephoneSs;
    }

    public String getTelephoneSs() 
    {
        return telephoneSs;
    }
    public void setChiefDutyPhone(String chiefDutyPhone) 
    {
        this.chiefDutyPhone = chiefDutyPhone;
    }

    public String getChiefDutyPhone() 
    {
        return chiefDutyPhone;
    }
    public void setDutyOffierName(String dutyOffierName) 
    {
        this.dutyOffierName = dutyOffierName;
    }

    public String getDutyOffierName() 
    {
        return dutyOffierName;
    }
    public void setDutyOffierId(Long dutyOffierId) 
    {
        this.dutyOffierId = dutyOffierId;
    }

    public Long getDutyOffierId() 
    {
        return dutyOffierId;
    }
    public void setDutyOffierZw(String dutyOffierZw) 
    {
        this.dutyOffierZw = dutyOffierZw;
    }

    public String getDutyOffierZw() 
    {
        return dutyOffierZw;
    }
    public void setDutyOffierPhone(String dutyOffierPhone) 
    {
        this.dutyOffierPhone = dutyOffierPhone;
    }

    public String getDutyOffierPhone() 
    {
        return dutyOffierPhone;
    }
    public void setDutyPhone(String dutyPhone) 
    {
        this.dutyPhone = dutyPhone;
    }

    public String getDutyPhone() 
    {
        return dutyPhone;
    }
    public void setDutyGangwei(String dutyGangwei) 
    {
        this.dutyGangwei = dutyGangwei;
    }

    public String getDutyGangwei() 
    {
        return dutyGangwei;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("attendedTime", getAttendedTime())
            .append("unitName", getUnitName())
            .append("unitId", getUnitId())
            .append("chiefDutyName", getChiefDutyName())
            .append("chiefDutyId", getChiefDutyId())
            .append("chiefDutyZhiwu", getChiefDutyZhiwu())
            .append("telephoneBg", getTelephoneBg())
            .append("telephoneSs", getTelephoneSs())
            .append("chiefDutyPhone", getChiefDutyPhone())
            .append("dutyOffierName", getDutyOffierName())
            .append("dutyOffierId", getDutyOffierId())
            .append("dutyOffierZw", getDutyOffierZw())
            .append("dutyOffierPhone", getDutyOffierPhone())
            .append("dutyPhone", getDutyPhone())
            .append("dutyGangwei", getDutyGangwei())
            .toString();
    }
}
