package com.supervision.medicalhealth.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 外诊管理对象 bdgl_diagnose
 * 
 * @author hr
 * @date 2022-02-14
 */
public class BdglDiagnose extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 单位 */
    @Excel(name = "单位")
    private Integer unitId;

    /** 人员 */
    @Excel(name = "人员")
    private Integer peopleId;

    /** 地点 */
    @Excel(name = "地点")
    private String address;

    /** 外诊日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "外诊日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 时长 */
    @Excel(name = "时长")
    private String duration;

    /** 返回日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "返回日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 登记人 */
    @Excel(name = "登记人")
    private Integer adminId;

    /** 病情 */
    @Excel(name = "病情")
    private String illness;

    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createtime;
    /**修改时间*/
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatetime;

    /** 单位名称 */
    @Excel(name = "单位名称")
    private String unitName;

    /** 人员姓名 */
    @Excel(name = "人员姓名")
    private String peopleName;

    /** 登记人姓名 */
    @Excel(name = "登记人姓名")
    private String adminname;

    /** 陪护人 */
    @Excel(name = "陪护人")
    private String accompanyingPerson;

    /** 归队状态 */
    @Excel(name = "归队状态")
    private String state;

    public String getAccompanyingPerson() {
        return accompanyingPerson;
    }

    public void setAccompanyingPerson(String accompanyingPerson) {
        this.accompanyingPerson = accompanyingPerson;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String getUnitName() {
        return unitName;
    }

    @Override
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    @Override
    public String getPeopleName() {
        return peopleName;
    }

    @Override
    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName;
    }

    public String getAdminname() {
        return adminname;
    }

    public void setAdminname(String adminname) {
        this.adminname = adminname;
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

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setUnitId(Integer unitId) 
    {
        this.unitId = unitId;
    }

    public Integer getUnitId() 
    {
        return unitId;
    }
    public void setPeopleId(Integer peopleId) 
    {
        this.peopleId = peopleId;
    }

    public Integer getPeopleId() 
    {
        return peopleId;
    }
    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }
    public void setStartTime(Date startTime) 
    {
        this.startTime = startTime;
    }

    public Date getStartTime() 
    {
        return startTime;
    }
    public void setDuration(String duration) 
    {
        this.duration = duration;
    }

    public String getDuration() 
    {
        return duration;
    }
    public void setEndTime(Date endTime) 
    {
        this.endTime = endTime;
    }

    public Date getEndTime() 
    {
        return endTime;
    }
    public void setAdminId(Integer adminId) 
    {
        this.adminId = adminId;
    }

    public Integer getAdminId() 
    {
        return adminId;
    }
    public void setIllness(String illness) 
    {
        this.illness = illness;
    }

    public String getIllness() 
    {
        return illness;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("unitId", getUnitId())
            .append("peopleId", getPeopleId())
            .append("address", getAddress())
            .append("startTime", getStartTime())
            .append("duration", getDuration())
            .append("endTime", getEndTime())
            .append("adminId", getAdminId())
            .append("createtime", getCreatetime())
            .append("updatetime", getUpdatetime())
            .append("illness", getIllness())
            .toString();
    }
}
