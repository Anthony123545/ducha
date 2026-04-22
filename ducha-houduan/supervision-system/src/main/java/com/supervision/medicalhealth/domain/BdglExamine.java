package com.supervision.medicalhealth.domain;


import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 人员体检信息对象 bdgl_examine
 *
 * @author supervision
 * @date 2022-03-19
 */
public class BdglExamine extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 单位 */
    @Excel(name = "单位")
    private Integer unitId;

    /** 姓名 */
    @Excel(name = "姓名")
    private Integer peopleId;

    /** 工作岗位 */
    @Excel(name = "工作岗位")
    private String job;

    /** 人员类别 */
    @Excel(name = "人员类别")
    private Integer classId;

    /** 体检信息 */
    @Excel(name = "体检信息")
    private String checkInformation;

    /** 体检人姓名 */
    @Excel(name = "体检人姓名")
    private String peopleName;

    /** 年龄 */
    @Excel(name = "年龄")
    private Integer age;

    /** 身份证号 */
    @Excel(name = "身份证号")
    private String idMunber;

    private int maxAge;

    private Integer minAge;


    /** 文件名称 */
    @Excel(name = "文件名称")
    private String fileName;

    /** 文件路径 */
    @Excel(name = "文件路径")
    private String filePath;

    private List<BdglExamineDetails> examineDetails;

    public List<BdglExamineDetails> getExamineDetails() {
        return examineDetails;
    }

    public void setExamineDetails(List<BdglExamineDetails> examineDetails) {
        this.examineDetails = examineDetails;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(int maxAge) {
        this.maxAge = maxAge;
    }

    public Integer getMinAge() {
        return minAge;
    }

    public void setMinAge(Integer minAge) {
        this.minAge = minAge;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getIdMunber() {
        return idMunber;
    }

    public void setIdMunber(String idMunber) {
        this.idMunber = idMunber;
    }

    private String yeardate;

    private String unitName;

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getUnitName() {
        return unitName;
    }

    @Override
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getYeardate() {
        return yeardate;
    }

    public void setYeardate(String yeardate) {
        this.yeardate = yeardate;
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
    public void setJob(String job)
    {
        this.job = job;
    }

    public String getJob()
    {
        return job;
    }
    public void setClassId(Integer classId)
    {
        this.classId = classId;
    }

    public Integer getClassId()
    {
        return classId;
    }
    public void setCheckInformation(String checkInformation)
    {
        this.checkInformation = checkInformation;
    }

    public String getCheckInformation()
    {
        return checkInformation;
    }
    public void setPeopleName(String peopleName)
    {
        this.peopleName = peopleName;
    }

    public String getPeopleName()
    {
        return peopleName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("unitId", getUnitId())
                .append("peopleId", getPeopleId())
                .append("job", getJob())
                .append("classId", getClassId())
                .append("createtime", getCreatetime())
                .append("checkInformation", getCheckInformation())
                .append("peopleName", getPeopleName())
                .toString();
    }
}