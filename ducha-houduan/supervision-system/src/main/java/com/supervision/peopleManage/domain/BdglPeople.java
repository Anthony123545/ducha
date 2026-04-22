package com.supervision.peopleManage.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 人员管理对象 bdgl_people
 *
 * @author supervision
 * @date 2022-01-22
 */
public class BdglPeople extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 工号 */
    @Excel(name = "工号")
    private String employeeNumber;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /**
     * 部门名称
     */
    @Excel(name = "单位名称")
    private String deptName;

    /** 性别 */
    @Excel(name = "性别",dictType ="sys_user_sex" )
    private String sex;

    /** 年龄 */
    @Excel(name = "年龄")
    private Integer age;

    /** 身份证号 */
    @Excel(name = "身份证号")
    private String idcard;

    /** 民族 */
    @Excel(name = "民族",dictType = "sys_familyName")
    private Integer nationId;

    /** 籍贯 */
    @Excel(name = "籍贯")
    private String origin;

    /** 出生年月 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出生年月", width = 30, dateFormat = "yyyy-MM-dd")
    private Date birthDate;

    /** 家庭住址 */
    @Excel(name = "家庭住址")
    private String live;

    /** 血型 */
    @Excel(name = "血型",dictType = "bloodType")
    private String bloodType;

    /** 政治面貌 */
    @Excel(name = "政治面貌",dictType = "Caucus")
    private Integer partyId;

    /** 党团时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "党团时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date partyDate;

    /** 健康情况 */
    @Excel(name = "健康情况",dictType = "healthy")
    private String healthy;

    /** 婚姻情况 */
    @Excel(name = "婚姻情况",dictType = "marryList")
    private String marryList;

    /** 专业 */
    //@Excel(name = "专业",dictType = "")
    private String major;

    /** 子女情况 */
    @Excel(name = "子女情况",dictType = "sys_yw")
    private String childrenList;

    /** 学历 */
    @Excel(name = "学历",dictType = "Degree_level")
    private Integer educationId;

    /** 学位 */
    @Excel(name = "学位",dictType = "degree")
    private Integer degreeId;

    /** 毕业时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "毕业时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date graduationDate;

    /** 毕业院校 */
    @Excel(name = "毕业院校")
    private String school;

    /** 军人证号 */
    @Excel(name = "军人证号")
    private String military;

    /** 人员类别 */
    @Excel(name = "人员类别",dictType = "personnel_type")
    private Integer classId;

    /** 0在位，1休假，2出差,3借调，4学习，5执行任务,6外诊，7其他 */
    @Excel(name = "人员状态",dictType = "people_state")
    private Integer reign;

    /** 部职别 */
    @Excel(name = "部职别" ,dictType = "")
    private String duty;

    /** 入伍年月 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入伍时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date enlistDate;

    /** 入伍地点 */
    @Excel(name = "入伍地点")
    private String enlistaddress;

    /** 职务 */
    @Excel(name = "职务层级",dictType = "post_Level")
    private Integer postId;

    /** 等级 */
    //@Excel(name = "等级",dictType = "post_Level")
    private Integer gradeId;

    /** 职务等级时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "职务等级时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date postDate;

    /** 军衔 */
    @Excel(name = "军衔",dictType = "sys_user_rank")
    private Integer rankId;

    /** 军衔时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "现军衔时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date rankDate;

    /** 命令号 */
    @Excel(name = "命令号")
    private String command;

    /** 是否纳编 */
    @Excel(name = "是否编制",dictType = "sys_yes_no")
    private String authorizedStrength;

    /** 待遇级别 */
    @Excel(name = "待遇级别")
    private String treatmentLevel;



    /** 现待遇级别时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "现待遇级别时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date rankUpgradeTime;


    /** 专业是否对口 */
    @Excel(name = "专业是否对口",dictType = "sys_yes_no")
    private String aboral;

    /** 工作时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "工作时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date jobTime;

    /** 手机型号 */
    @Excel(name = "手机型号")
    private String mobilePhoneModel;

    /** 手机号码 */
    @Excel(name = "手机号码")
    private String phone;

    /**
     * 备用电话
     */
    @Excel(name = "备用电话")
    private String phoneStandby;

    /** 保障卡号 */
    @Excel(name = "保障卡号")
    private String guaranteeCardNo;

    /** 工资卡号 */
    @Excel(name = "工资卡号")
    private String payrollCardNo;

    /** 工作岗位 */
    //@Excel(name = "工作岗位",dictType = "")
    private String job;

    /** 技术职务 */
    //@Excel(name = "技术职务",dictType = "")
    private Integer skillId;

    /** 技术职务时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    //@Excel(name = "技术职务时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date skillDate;

    /** 技术等级 */
    //@Excel(name = "技术等级",dictType = "")
    private Integer levelId;

    /** 技术等级时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    //@Excel(name = "技术等级时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date levelDate;

    /** 培训经历 */
    //@Excel(name = "培训经历")
    private String training;

    /** 简历 */
    //@Excel(name = "简历")
    private String readmeFile;

    /** 简历名称 */
    //@Excel(name = "简历名称")
    private String readmeFileName;

    /** 奖惩情况 */
    //@Excel(name = "奖惩情况")
    private String prize;



    /** 部别（单位） */
    //@Excel(name = "部别", readConverterExp = "单=位")
    private String unitId;

    /** 照片 */
    //@Excel(name = "照片")
    private String avatar;

    /** 出生地址 */
    //@Excel(name = "出生地址")
    private String address;



    /** 0在位，1调出 */
    //@Excel(name = "状态",dictType = "")
    private Integer status;

    /** $column.columnComment */
    @JsonFormat(pattern = "yyyy-MM-dd")
   // @Excel(name = "0在位，1调出", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createtimes;

    /** $column.columnComment */
    @JsonFormat(pattern = "yyyy-MM-dd")
   // @Excel(name = "0在位，1调出", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatetimes;









    /** 职务时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    //@Excel(name = "职务时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date postTime;



    /** 入学时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    //@Excel(name = "入学时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date admissionTime;








    /** 部门ID */
    //@Excel(name = "部门ID")
    private Long deptId;



    /** 用户ID */
    //@Excel(name = "用户ID")
    private Long userId;

    /** 现军衔 */
    @Excel(name = "现军衔",dictType = "sys_user_rank")
    private String nowMilitaryRank;



    /** 人员管理信息 */
    private List<BdglPeople> bdglPeopleList;

    /**
     * 人员是否对口
     */
    //@Excel(name = "专业是否对口",dictType = "sys_yes_no")
    private String isBianzhi;

    /**家庭信息*/
    private List<FamilyInformation> familyInformation;

    public String getIsBianzhi() {
        return isBianzhi;
    }

    public void setIsBianzhi(String isBianzhi) {
        this.isBianzhi = isBianzhi;
    }

    public String getNowMilitaryRank() {
        return nowMilitaryRank;
    }

    public void setNowMilitaryRank(String nowMilitaryRank) {
        this.nowMilitaryRank = nowMilitaryRank;
    }

    public String getTreatmentLevel() {
        return treatmentLevel;
    }

    public void setTreatmentLevel(String treatmentLevel) {
        this.treatmentLevel = treatmentLevel;
    }

    public Date getRankUpgradeTime() {
        return rankUpgradeTime;
    }

    public void setRankUpgradeTime(Date rankUpgradeTime) {
        this.rankUpgradeTime = rankUpgradeTime;
    }

    public List<BdglPeople> getBdglPeopleList() {
        return bdglPeopleList;
    }

    public void setBdglPeopleList(List<BdglPeople> bdglPeopleList) {
        this.bdglPeopleList = bdglPeopleList;
    }

    public List<FamilyInformation> getFamilyInformation() {
        return familyInformation;
    }

    public void setFamilyInformation(List<FamilyInformation> familyInformation) {
        this.familyInformation = familyInformation;
    }

    public String getPhoneStandby() {
        return phoneStandby;
    }

    public void setPhoneStandby(String phoneStandby) {
        this.phoneStandby = phoneStandby;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getSex()
    {
        return sex;
    }
    public void setAge(Integer age)
    {
        this.age = age;
    }

    public Integer getAge()
    {
        return age;
    }
    public void setBirthDate(Date birthDate)
    {
        this.birthDate = birthDate;
    }

    public Date getBirthDate()
    {
        return birthDate;
    }
    public void setCommand(String command)
    {
        this.command = command;
    }

    public String getCommand()
    {
        return command;
    }
    public void setDuty(String duty)
    {
        this.duty = duty;
    }

    public String getDuty()
    {
        return duty;
    }
    public void setPostId(Integer postId)
    {
        this.postId = postId;
    }

    public Integer getPostId()
    {
        return postId;
    }
    public void setGradeId(Integer gradeId)
    {
        this.gradeId = gradeId;
    }

    public Integer getGradeId()
    {
        return gradeId;
    }
    public void setPostDate(Date postDate)
    {
        this.postDate = postDate;
    }

    public Date getPostDate()
    {
        return postDate;
    }
    public void setJob(String job)
    {
        this.job = job;
    }

    public String getJob()
    {
        return job;
    }
    public void setSkillId(Integer skillId)
    {
        this.skillId = skillId;
    }

    public Integer getSkillId()
    {
        return skillId;
    }
    public void setSkillDate(Date skillDate)
    {
        this.skillDate = skillDate;
    }

    public Date getSkillDate()
    {
        return skillDate;
    }
    public void setLevelId(Integer levelId)
    {
        this.levelId = levelId;
    }

    public Integer getLevelId()
    {
        return levelId;
    }
    public void setLevelDate(Date levelDate)
    {
        this.levelDate = levelDate;
    }

    public Date getLevelDate()
    {
        return levelDate;
    }
    public void setMilitary(String military)
    {
        this.military = military;
    }

    public String getMilitary()
    {
        return military;
    }
    public void setIdcard(String idcard)
    {
        this.idcard = idcard;
    }

    public String getIdcard()
    {
        return idcard;
    }
    public void setEnlistDate(Date enlistDate)
    {
        this.enlistDate = enlistDate;
    }

    public Date getEnlistDate()
    {
        return enlistDate;
    }
    public void setEnlistaddress(String enlistaddress)
    {
        this.enlistaddress = enlistaddress;
    }

    public String getEnlistaddress()
    {
        return enlistaddress;
    }
    public void setRankId(Integer rankId)
    {
        this.rankId = rankId;
    }

    public Integer getRankId()
    {
        return rankId;
    }
    public void setRankDate(Date rankDate)
    {
        this.rankDate = rankDate;
    }

    public Date getRankDate()
    {
        return rankDate;
    }
    public void setNationId(Integer nationId)
    {
        this.nationId = nationId;
    }

    public Integer getNationId()
    {
        return nationId;
    }
    public void setOrigin(String origin)
    {
        this.origin = origin;
    }

    public String getOrigin()
    {
        return origin;
    }
    public void setClassId(Integer classId)
    {
        this.classId = classId;
    }

    public Integer getClassId()
    {
        return classId;
    }
    public void setEducationId(Integer educationId)
    {
        this.educationId = educationId;
    }

    public Integer getEducationId()
    {
        return educationId;
    }
    public void setDegreeId(Integer degreeId)
    {
        this.degreeId = degreeId;
    }

    public Integer getDegreeId()
    {
        return degreeId;
    }
    public void setGraduationDate(Date graduationDate)
    {
        this.graduationDate = graduationDate;
    }

    public Date getGraduationDate()
    {
        return graduationDate;
    }
    public void setSchool(String school)
    {
        this.school = school;
    }

    public String getSchool()
    {
        return school;
    }
    public void setPartyId(Integer partyId)
    {
        this.partyId = partyId;
    }

    public Integer getPartyId()
    {
        return partyId;
    }
    public void setPartyDate(Date partyDate)
    {
        this.partyDate = partyDate;
    }

    public Date getPartyDate()
    {
        return partyDate;
    }
    public void setTraining(String training)
    {
        this.training = training;
    }

    public String getTraining()
    {
        return training;
    }
    public void setReadmeFile(String readmeFile)
    {
        this.readmeFile = readmeFile;
    }

    public String getReadmeFile()
    {
        return readmeFile;
    }
    public void setReadmeFileName(String readmeFileName)
    {
        this.readmeFileName = readmeFileName;
    }

    public String getReadmeFileName()
    {
        return readmeFileName;
    }
    public void setPrize(String prize)
    {
        this.prize = prize;
    }

    public String getPrize()
    {
        return prize;
    }
    public void setMarryList(String marryList)
    {
        this.marryList = marryList;
    }

    public String getMarryList()
    {
        return marryList;
    }
    public void setChildrenList(String childrenList)
    {
        this.childrenList = childrenList;
    }

    public String getChildrenList()
    {
        return childrenList;
    }
    public void setUnitId(String unitId)
    {
        this.unitId = unitId;
    }

    public String getUnitId()
    {
        return unitId;
    }
    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public String getAvatar()
    {
        return avatar;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getAddress()
    {
        return address;
    }
    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getPhone()
    {
        return phone;
    }
    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Integer getStatus()
    {
        return status;
    }
    public void setCreatetimes(Date createtimes)
    {
        this.createtimes = createtimes;
    }

    public Date getCreatetimes()
    {
        return createtimes;
    }
    public void setUpdatetimes(Date updatetimes)
    {
        this.updatetimes = updatetimes;
    }

    public Date getUpdatetimes()
    {
        return updatetimes;
    }
    public void setReign(Integer reign)
    {
        this.reign = reign;
    }

    public Integer getReign()
    {
        return reign;
    }
    public void setJobTime(Date jobTime)
    {
        this.jobTime = jobTime;
    }

    public Date getJobTime()
    {
        return jobTime;
    }
    public void setHealthy(String healthy)
    {
        this.healthy = healthy;
    }

    public String getHealthy()
    {
        return healthy;
    }
    public void setBloodType(String bloodType)
    {
        this.bloodType = bloodType;
    }

    public String getBloodType()
    {
        return bloodType;
    }
    public void setPostTime(Date postTime)
    {
        this.postTime = postTime;
    }

    public Date getPostTime()
    {
        return postTime;
    }
    public void setMajor(String major)
    {
        this.major = major;
    }

    public String getMajor()
    {
        return major;
    }
    public void setAdmissionTime(Date admissionTime)
    {
        this.admissionTime = admissionTime;
    }

    public Date getAdmissionTime()
    {
        return admissionTime;
    }
    public void setMobilePhoneModel(String mobilePhoneModel)
    {
        this.mobilePhoneModel = mobilePhoneModel;
    }

    public String getMobilePhoneModel()
    {
        return mobilePhoneModel;
    }
    public void setGuaranteeCardNo(String guaranteeCardNo)
    {
        this.guaranteeCardNo = guaranteeCardNo;
    }

    public String getGuaranteeCardNo()
    {
        return guaranteeCardNo;
    }
    public void setPayrollCardNo(String payrollCardNo)
    {
        this.payrollCardNo = payrollCardNo;
    }

    public String getPayrollCardNo()
    {
        return payrollCardNo;
    }
    public void setAuthorizedStrength(String authorizedStrength)
    {
        this.authorizedStrength = authorizedStrength;
    }

    public String getAuthorizedStrength()
    {
        return authorizedStrength;
    }
    public void setAboral(String aboral)
    {
        this.aboral = aboral;
    }

    public String getAboral()
    {
        return aboral;
    }
    public void setLive(String live)
    {
        this.live = live;
    }

    public String getLive()
    {
        return live;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getStatusrk() {
        return statusrk;
    }

    public void setStatusrk(String statusrk) {
        this.statusrk = statusrk;
    }

    public String statusrk;

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("employeeNumber", getEmployeeNumber())
            .append("name", getName())
            .append("sexList", getSex())
            .append("age", getAge())
            .append("birthDate", getBirthDate())
            .append("command", getCommand())
            .append("duty", getDuty())
            .append("postId", getPostId())
            .append("gradeId", getGradeId())
            .append("postDate", getPostDate())
            .append("job", getJob())
            .append("skillId", getSkillId())
            .append("skillDate", getSkillDate())
            .append("levelId", getLevelId())
            .append("levelDate", getLevelDate())
            .append("military", getMilitary())
            .append("idcard", getIdcard())
            .append("enlistDate", getEnlistDate())
            .append("enlistaddress", getEnlistaddress())
            .append("rankId", getRankId())
            .append("rankDate", getRankDate())
            .append("nationId", getNationId())
            .append("origin", getOrigin())
            .append("classId", getClassId())
            .append("educationId", getEducationId())
            .append("degreeId", getDegreeId())
            .append("graduationDate", getGraduationDate())
            .append("school", getSchool())
            .append("partyId", getPartyId())
            .append("partyDate", getPartyDate())
            .append("training", getTraining())
            .append("readmeFile", getReadmeFile())
            .append("readmeFileName", getReadmeFileName())
            .append("prize", getPrize())
            .append("marryList", getMarryList())
            .append("childrenList", getChildrenList())
            .append("unitId", getUnitId())
            .append("avatar", getAvatar())
            .append("address", getAddress())
            .append("phone", getPhone())
            .append("status", getStatus())
            .append("createtimes", getCreatetimes())
            .append("updatetimes", getUpdatetimes())
            .append("reign", getReign())
            .append("jobTime", getJobTime())
            .append("healthy", getHealthy())
            .append("bloodType", getBloodType())
            .append("postTime", getPostTime())
            .append("major", getMajor())
            .append("admissionTime", getAdmissionTime())
            .append("mobilePhoneModel", getMobilePhoneModel())
            .append("guaranteeCardNo", getGuaranteeCardNo())
            .append("payrollCardNo", getPayrollCardNo())
            .append("authorizedStrength", getAuthorizedStrength())
            .append("aboral", getAboral())
            .append("live", getLive())
                .append("statusrk", getStatusrk())
            .toString();
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }
}
