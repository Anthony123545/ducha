package com.supervision.grassrootsregistration.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 网络/手机/涉密载体登记本对象 bdgl_secrecy
 * 
 * @author supervision
 * @date 2022-02-26
 */
public class BdglSecrecy extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 分类 */
    @Excel(name = "分类")
    private Integer secrecyType;

    /** 编号 */
    @Excel(name = "编号")
    private String number;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 种类 */
    @Excel(name = "种类")
    private String kind;

    /** 型号 */
    @Excel(name = "型号")
    private String version;

    /** 密级 */
    @Excel(name = "密级")
    private String confidential;

    /** 责任人 */
    @Excel(name = "责任人id")
    private Integer peopleId;

    /** 使用人 */
    @Excel(name = "使用人id")
    private Integer peopleId1;

    /** 批准人 */
    @Excel(name = "批准人id")
    private Integer peopleId2;

    /** 责任人 */
    @Excel(name = "责任人姓名")
    private String peopleName;

    /** 使用人 */
    @Excel(name = "使用人姓名")
    private String people1Name;

    /** 批准人 */
    @Excel(name = "批准人姓名")
    private String people2Name;

    /** 起用时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "起用时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 停用时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "停用时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 移交时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "移交时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date hindOverTime;

    /** 单位 */
    @Excel(name = "单位")
    private Integer unitId;
    /**部门名称*/
    @Excel(name = "部门名称")
    private String deptName;
    
    private Date createtime;
    
    public Date getCreatetime() {
        return createtime;
    }
    
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
    
    public String getPeopleName() {
        return peopleName;
    }

    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName;
    }

    public String getPeople1Name() {
        return people1Name;
    }

    public void setPeople1Name(String people1Name) {
        this.people1Name = people1Name;
    }

    public String getPeople2Name() {
        return people2Name;
    }

    public void setPeople2Name(String people2Name) {
        this.people2Name = people2Name;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
    }
    public void setSecrecyType(Integer secrecyType) 
    {
        this.secrecyType = secrecyType;
    }

    public Integer getSecrecyType() 
    {
        return secrecyType;
    }
    public void setNumber(String number) 
    {
        this.number = number;
    }

    public String getNumber() 
    {
        return number;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setKind(String kind) 
    {
        this.kind = kind;
    }

    public String getKind() 
    {
        return kind;
    }
    public void setVersion(String version) 
    {
        this.version = version;
    }

    public String getVersion() 
    {
        return version;
    }
    public void setConfidential(String confidential) 
    {
        this.confidential = confidential;
    }

    public String getConfidential() 
    {
        return confidential;
    }
    public void setPeopleId(Integer peopleId) 
    {
        this.peopleId = peopleId;
    }

    public Integer getPeopleId() 
    {
        return peopleId;
    }
    public void setPeopleId1(Integer peopleId1) 
    {
        this.peopleId1 = peopleId1;
    }

    public Integer getPeopleId1() 
    {
        return peopleId1;
    }
    public void setPeopleId2(Integer peopleId2) 
    {
        this.peopleId2 = peopleId2;
    }

    public Integer getPeopleId2() 
    {
        return peopleId2;
    }
    public void setStartTime(Date startTime) 
    {
        this.startTime = startTime;
    }

    public Date getStartTime() 
    {
        return startTime;
    }
    public void setEndTime(Date endTime) 
    {
        this.endTime = endTime;
    }

    public Date getEndTime() 
    {
        return endTime;
    }
    public void setHindOverTime(Date hindOverTime) 
    {
        this.hindOverTime = hindOverTime;
    }

    public Date getHindOverTime() 
    {
        return hindOverTime;
    }
    public void setUnitId(Integer unitId) 
    {
        this.unitId = unitId;
    }

    public Integer getUnitId() 
    {
        return unitId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("secrecyType", getSecrecyType())
            .append("number", getNumber())
            .append("name", getName())
            .append("kind", getKind())
            .append("version", getVersion())
            .append("confidential", getConfidential())
            .append("peopleId", getPeopleId())
            .append("peopleId1", getPeopleId1())
            .append("peopleId2", getPeopleId2())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("hindOverTime", getHindOverTime())
            .append("unitId", getUnitId())
            .toString();
    }
}
