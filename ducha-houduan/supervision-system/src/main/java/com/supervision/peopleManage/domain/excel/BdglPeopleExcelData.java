package com.supervision.peopleManage.domain.excel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.supervision.common.annotation.Excel;
import com.supervision.peopleManage.domain.BdglPeople;
import com.supervision.peopleManage.domain.FamilyInformation;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode
public class BdglPeopleExcelData {
    private static final long serialVersionUID = 1L;

    //基本信息---------------------------------------------------------------------
    //个人信息
    /** 工号 */
    @Excel(name = "工号")
    private String employeeNumber;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 身份证号 */
    @Excel(name = "身份证号")
    private String idcard;

    /** 民族 */
    @Excel(name = "民族", dictType = "sys_familyName")
    private Integer nationId;
    
    /** 民族名称（用于导入时的临时字段）*/
    private String nationName;


    /** 性别 */
    @Excel(name = "性别", dictType = "sys_user_sex")
    private String sex;

    /** 年龄 */
    @Excel(name = "年龄")
    private Integer age;

    /** 出生年月 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出生年月", width = 30, dateFormat = "yyyy-MM-dd")
    private Date birthDate;

    /** 籍贯 */
    @Excel(name = "籍贯")
    private String origin;

    /** 家庭住址 */
    @Excel(name = "家庭住址")
    private String live;

    /** 血型 */
    @Excel(name = "血型", dictType = "bloodType")
    private String bloodType;

    /** 政治面貌 */
    @Excel(name = "政治面貌", dictType = "Caucus")
    private Integer partyId;
    
    /** 政治面貌名称（用于导入时的临时字段）*/
    private String partyName;

    /** 党团时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "党团时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date partyDate;

    /** 健康情况 */
    @Excel(name = "健康情况", dictType = "healthy")
    private String healthy;

    /** 保障卡号 */
    @Excel(name = "保障卡号")
    private String guaranteeCardNo;

    /** 工资卡号 */
    @Excel(name = "工资卡号")
    private String payrollCardNo;

    //婚姻信息
    /** 婚姻情况 */
    @Excel(name = "婚姻情况", dictType = "marryList")
    private String marryList;

    /** 子女情况 */
    @Excel(name = "子女情况", dictType = "sys_yw")
    private String childrenList;

    //学历信息
    /** 学历 */
    @Excel(name = "学历", dictType = "Degree_level")
    private Integer educationId;
    
    /** 学历名称（用于导入时的临时字段）*/
    private String educationName;

    /** 学位 */
    @Excel(name = "学位", dictType = "degree")
    private Integer degreeId;
    
    /** 学位名称（用于导入时的临时字段）*/
    private String degreeName;

    /** 入学时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入学时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date admissionTime;

    /** 专业 */
    @Excel(name = "专业", dictType = "")
    private String major;

    /** 毕业时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "毕业时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date graduationDate;

    /** 毕业院校 */
    @Excel(name = "毕业院校")
    private String school;

    //联系方式
    /** 手机型号 */
    @Excel(name = "手机型号")
    private String mobilePhoneModel;

    /** 手机号码 */
    @Excel(name = "手机号码")
    private String phone;

    /**
     * 备用电话
     */
    @Excel(name = "手机号码2")
    private String phoneStandby;

    //职务信息---------------------------------------------------------
    /** 军人证号 */
    @Excel(name = "军人证号")
    private String military;

    /** 人员类别 */
    @Excel(name = "人员类别", dictType = "personnel_type")
    private Integer classId;
    
    /** 人员类别名称（用于导入时的临时字段）*/
    private String className;

    /** 部门名称 */
    @Excel(name = "单位")
    private String deptName;

    /** 部门ID */
    private Long deptId;

    /** 部职别 */
    @Excel(name = "部职别", dictType = "")
    private String duty;

    /** 入伍年月 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入伍时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date enlistDate;

    /** 入伍地点 */
    @Excel(name = "入伍地点")
    private String enlistaddress;

    /** 职务 */
    @Excel(name = "职务层级", dictType = "post_Level")
    private Integer postId;
    
    /** 职务名称（用于导入时的临时字段）*/
    private String postName;

    /** 职务等级时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "职务等级时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date postDate;

    /** 军衔 */
    @Excel(name = "军衔", dictType = "sys_user_rank")
    private Integer rankId;
    
    /** 军衔名称（用于导入时的临时字段）*/
    private String rankName;

    /** 现军衔 */
    @Excel(name = "现军衔", dictType = "sys_user_rank")
    private String nowMilitaryRank;

    /** 军衔时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "现军衔时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date rankDate;

    /** 是否纳编 */
    @Excel(name = "是否编制", dictType = "sys_yes_no")
    private String authorizedStrength;

    /** 命令号 */
    @Excel(name = "命令号")
    private String command;

    /** 待遇级别 */
    @Excel(name = "待遇级别")
    private String treatmentLevel;

    /** 现待遇级别时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "现待遇级别时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date rankUpgradeTime;

    /** 专业是否对口 */
    @Excel(name = "专业是否对口", dictType = "sys_yes_no")
    private String aboral;

    /** 工作时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "工作时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date jobTime;

    /** 在位备注 */
    @Excel(name = "在位备注")
    public String statusrk;

    //家庭信息(目前只记录单个家庭联系方式)---------------------------------------------------------
    /** 姓名 */
    @Excel(name = "亲属姓名")
    private String familyName1;

    /** 与本人关系 */
    @Excel(name = "亲属与本人关系")
    private String characterRelationship1;

    /** 联系电话 */
    @Excel(name = "亲属联系电话")
    private String phoneNumber1;

    /** 单位名称 */
    @Excel(name = "亲属单位名称")
    private String unitName1;

    //培训经历 ---------------------------------------------------------
    @Excel(name = "培训经历")
    private String training;

    //奖惩情况 ---------------------------------------------------------
    @Excel(name = "奖惩情况")
    private String prize;

    public BdglPeople buildBdglPeople() {
        BdglPeople bdglPeople = new BdglPeople();
        bdglPeople.setEmployeeNumber(this.employeeNumber);
        bdglPeople.setName(this.name);
        bdglPeople.setIdcard(this.idcard);
        bdglPeople.setNationId(this.nationId);
        bdglPeople.setSex(this.sex);
        bdglPeople.setAge(this.age);
        bdglPeople.setBirthDate(this.birthDate);
        bdglPeople.setOrigin(this.origin);
        bdglPeople.setLive(this.live);
        bdglPeople.setBloodType(this.bloodType);
        bdglPeople.setPartyId(this.partyId);
        bdglPeople.setPartyDate(this.partyDate);
        bdglPeople.setHealthy(this.healthy);
        bdglPeople.setGuaranteeCardNo(this.guaranteeCardNo);
        bdglPeople.setPayrollCardNo(this.payrollCardNo);
        bdglPeople.setMarryList(this.marryList);
        bdglPeople.setChildrenList(this.childrenList);
        bdglPeople.setEducationId(this.educationId);
        bdglPeople.setDegreeId(this.degreeId);
        bdglPeople.setAdmissionTime(this.admissionTime);
        bdglPeople.setMajor(this.major);
        bdglPeople.setGraduationDate(this.graduationDate);
        bdglPeople.setSchool(this.school);
        bdglPeople.setMobilePhoneModel(this.mobilePhoneModel);
        bdglPeople.setPhone(this.phone);
        bdglPeople.setPhoneStandby(this.phoneStandby);
        bdglPeople.setMilitary(this.military);
        bdglPeople.setClassId(this.classId);
        bdglPeople.setDeptName(this.deptName);
        bdglPeople.setDeptId(this.deptId);
        bdglPeople.setDuty(this.duty);
        bdglPeople.setEnlistDate(this.enlistDate);
        bdglPeople.setEnlistaddress(this.enlistaddress);
        bdglPeople.setPostId(this.postId);
        bdglPeople.setPostDate(this.postDate);
        bdglPeople.setRankId(this.rankId);
        bdglPeople.setNowMilitaryRank(this.nowMilitaryRank);
        bdglPeople.setRankDate(this.rankDate);
        bdglPeople.setAuthorizedStrength(this.authorizedStrength);
        bdglPeople.setCommand(this.command);
        bdglPeople.setTreatmentLevel(this.treatmentLevel);
        bdglPeople.setRankUpgradeTime(this.rankUpgradeTime);
        bdglPeople.setAboral(this.aboral);
        bdglPeople.setJobTime(this.jobTime);
        bdglPeople.setStatusrk(this.statusrk);
        bdglPeople.setTraining(this.training);
        bdglPeople.setPrize(this.prize);

        bdglPeople.setFamilyInformation(new ArrayList<FamilyInformation>());
        FamilyInformation familyInformation = new FamilyInformation();
        familyInformation.setName(this.familyName1);
        familyInformation.setCharacterRelationship(this.characterRelationship1);
        familyInformation.setPhoneNumber(this.phoneNumber1);
        familyInformation.setUnitName(this.unitName1);
        bdglPeople.getFamilyInformation().add(familyInformation);

        return bdglPeople;
    }
}