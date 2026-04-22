package com.supervision.grassrootsregistration.domain;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.supervision.grassrootsregistration.resultVo.BdglWeekworkRegisterVo;
import com.supervision.peopleChuRu.domain.BdglLeave;
import com.supervision.peopleChuRu.domain.BdglLeaveOne;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 要事日记对象 bdgl_diary
 *
 * @author supervision
 * @date 2022-02-26
 */
public class BdglDiary extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 单位 */
    @Excel(name = "单位")
    private Integer unitId;

    /** 值班人员 */
    @Excel(name = "值班人员")
    private Integer peopleId;

    /** 值班日期 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "值班日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date blogDate;

    /** 天气 */
    @Excel(name = "天气")
    private String weather;

    /** 编制干部 */
    @Excel(name = "编制干部")
    private Long orgcadre;

    /** 编制战士 */
    @Excel(name = "编制战士")
    private Long orgsoldier;

    /** 编制其他 */
    @Excel(name = "编制其他")
    private Long orgother;

    /** 编制文职 */
    @Excel(name = "编制文职")
    private Long orgcivilian;

    /** 现有干部 */
    @Excel(name = "现有干部")
    private Long nowcadre;

    /** 现有战士 */
    @Excel(name = "现有战士")
    private Long nowsoldier;

    /** 现有文职 */
    @Excel(name = "现有文职")
    private Long nowcivilian;

    /** 公差勤务 */
    @Excel(name = "公差勤务")
    private String tolerance;

    /** 人员调动 */
    @Excel(name = "人员调动")
    private String transfer;

    /** 装备变动 */
    @Excel(name = "装备变动")
    private String equip;

    /** 内务情况 */
    @Excel(name = "内务情况")
    private String internal;

    /** 值班组织人 */
    @Excel(name = "值班组织人")
    private String dutylead;

    /** 值班交班人 */
    @Excel(name = "值班交班人")
    private String dutya;

    /** 值班接班人 */
    @Excel(name = "值班接班人")
    private String dutyb;

    /** 值日交班人 */
    @Excel(name = "值日交班人")
    private String dutyc;

    /** 值日接班人 */
    @Excel(name = "值日接班人")
    private String dutyd;

    /** 厨房交班人 */
    @Excel(name = "厨房交班人")
    private String dutye;

    /** 厨房接班人 */
    @Excel(name = "厨房接班人")
    private String dutyf;

    /** 值班交接情况 */
    @Excel(name = "值班交接情况")
    private String dutyinfoa;

    /** 值日交接情况 */
    @Excel(name = "值日交接情况")
    private String dutyinfoc;

    /** 厨房交接情况 */
    @Excel(name = "厨房交接情况")
    private String dutyinfoe;

    /** 交接时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "交接时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dutyDate;

    /** 病号处理情况 */
    @Excel(name = "病号处理情况")
    private String invalid;

    /** 上级通知 */
    @Excel(name = "上级通知")
    private String notice;

    /** 连首长 */
    @Excel(name = "连首长")
    private Long lead;

    /** 一周工作计划合集 */
    @Excel(name = "一周工作计划合集")
    private String weekwork;

    /** 审批 */
    @Excel(name = "审批")
    private Integer examine;

    /** $column.columnComment */
    @Excel(name = "审批")
    private Integer peopleId1;

    /** 审批不通过原因 */
    @Excel(name = "审批不通过原因")
    private String contents;

    /** 创建人id */
    @Excel(name = "创建人id")
    private Long createId;

    /** 创建人姓名 */
    @Excel(name = "创建人姓名")
    private String createName;

    /**  查铺查哨  */
    private List<BdglDailyInspect> inspect;

    /** 临时来队亲属  */
    private List<BdglDiaryKinsfolk> kinsfolk;
    /**值班人姓名*/
    private String peopleName;

    /**一周训练*/
    private List<BdglWeekworkRegister> weekworkRegisters;

    @Excel(name = "！！！！！！！值班首长姓名")
    private String leadName;

    @Excel(name = "单位名称")
    private String unitName;

    private String common;

    @Excel(name = "组织人")
    private String organizer;

    private String diaryTime;

    private List<BdglLeave> bdglLeaves;

    /**
     * 封装工作安排信息
     */
    private List<BdglWeekworkRegisterVo> contextData;

    /**
     * 封装工作安排信息详细数据
     */
    private List<BdglWeekworkRegisterVo> contextData2;

    public List<BdglWeekworkRegisterVo> getContextData2() {
        return contextData2;
    }

    public void setContextData2(List<BdglWeekworkRegisterVo> contextData2) {
        this.contextData2 = contextData2;
    }

    public List<BdglWeekworkRegisterVo> getContextData() {
        return contextData;
    }

    public void setContextData(List<BdglWeekworkRegisterVo> contextData) {
        this.contextData = contextData;
    }

    public List<BdglLeave> getBdglLeaves() {
        return bdglLeaves;
    }

    public void setBdglLeaves(List<BdglLeave> bdglLeaves) {
        this.bdglLeaves = bdglLeaves;
    }

    public String getDiaryTime() {
        return diaryTime;
    }

    public void setDiaryTime(String diaryTime) {
        this.diaryTime = diaryTime;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getCommon() {
        return common;
    }

    public void setCommon(String common) {
        this.common = common;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public List<BdglDailyInspect> getInspect() {
        return inspect;
    }

    public void setInspect(List<BdglDailyInspect> inspect) {
        this.inspect = inspect;
    }

    public List<BdglDiaryKinsfolk> getKinsfolk() {
        return kinsfolk;
    }

    public void setKinsfolk(List<BdglDiaryKinsfolk> kinsfolk) {
        this.kinsfolk = kinsfolk;
    }

    public String getLeadName() {
        return leadName;
    }

    public void setLeadName(String leadName) {
        this.leadName = leadName;
    }

    public String getPeopleName() {
        return peopleName;
    }

    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName;
    }

    public List<BdglWeekworkRegister> getWeekworkRegisters() {
        return weekworkRegisters;
    }

    public void setWeekworkRegisters(List<BdglWeekworkRegister> weekworkRegisters) {
        this.weekworkRegisters = weekworkRegisters;
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
    public void setBlogDate(Date blogDate)
    {
        this.blogDate = blogDate;
    }

    public Date getBlogDate()
    {
        return blogDate;
    }
    public void setWeather(String weather)
    {
        this.weather = weather;
    }

    public String getWeather()
    {
        return weather;
    }
    public void setOrgcadre(Long orgcadre)
    {
        this.orgcadre = orgcadre;
    }

    public Long getOrgcadre()
    {
        return orgcadre;
    }
    public void setOrgsoldier(Long orgsoldier)
    {
        this.orgsoldier = orgsoldier;
    }

    public Long getOrgsoldier()
    {
        return orgsoldier;
    }
    public void setOrgcivilian(Long orgcivilian)
    {
        this.orgcivilian = orgcivilian;
    }

    public Long getOrgcivilian()
    {
        return orgcivilian;
    }
    public void setNowcadre(Long nowcadre)
    {
        this.nowcadre = nowcadre;
    }

    public Long getNowcadre()
    {
        return nowcadre;
    }
    public void setNowsoldier(Long nowsoldier)
    {
        this.nowsoldier = nowsoldier;
    }

    public Long getNowsoldier()
    {
        return nowsoldier;
    }
    public void setNowcivilian(Long nowcivilian)
    {
        this.nowcivilian = nowcivilian;
    }

    public Long getNowcivilian()
    {
        return nowcivilian;
    }
    public void setTolerance(String tolerance)
    {
        this.tolerance = tolerance;
    }

    public String getTolerance()
    {
        return tolerance;
    }
    public void setTransfer(String transfer)
    {
        this.transfer = transfer;
    }

    public String getTransfer()
    {
        return transfer;
    }
    public void setEquip(String equip)
    {
        this.equip = equip;
    }

    public String getEquip()
    {
        return equip;
    }
    public void setInternal(String internal)
    {
        this.internal = internal;
    }

    public String getInternal()
    {
        return internal;
    }
    public void setDutylead(String dutylead)
    {
        this.dutylead = dutylead;
    }

    public String getDutylead()
    {
        return dutylead;
    }
    public void setDutya(String dutya)
    {
        this.dutya = dutya;
    }

    public String getDutya()
    {
        return dutya;
    }
    public void setDutyb(String dutyb)
    {
        this.dutyb = dutyb;
    }

    public String getDutyb()
    {
        return dutyb;
    }
    public void setDutyc(String dutyc)
    {
        this.dutyc = dutyc;
    }

    public String getDutyc()
    {
        return dutyc;
    }
    public void setDutyd(String dutyd)
    {
        this.dutyd = dutyd;
    }

    public String getDutyd()
    {
        return dutyd;
    }
    public void setDutye(String dutye)
    {
        this.dutye = dutye;
    }

    public String getDutye()
    {
        return dutye;
    }
    public void setDutyf(String dutyf)
    {
        this.dutyf = dutyf;
    }

    public String getDutyf()
    {
        return dutyf;
    }
    public void setDutyinfoa(String dutyinfoa)
    {
        this.dutyinfoa = dutyinfoa;
    }

    public String getDutyinfoa()
    {
        return dutyinfoa;
    }
    public void setDutyinfoc(String dutyinfoc)
    {
        this.dutyinfoc = dutyinfoc;
    }

    public String getDutyinfoc()
    {
        return dutyinfoc;
    }
    public void setDutyinfoe(String dutyinfoe)
    {
        this.dutyinfoe = dutyinfoe;
    }

    public String getDutyinfoe()
    {
        return dutyinfoe;
    }
    public void setDutyDate(Date dutyDate)
    {
        this.dutyDate = dutyDate;
    }

    public Date getDutyDate()
    {
        return dutyDate;
    }
    public void setInvalid(String invalid)
    {
        this.invalid = invalid;
    }

    public String getInvalid()
    {
        return invalid;
    }
    public void setNotice(String notice)
    {
        this.notice = notice;
    }

    public String getNotice()
    {
        return notice;
    }
    public void setLead(Long lead)
    {
        this.lead = lead;
    }

    public Long getLead()
    {
        return lead;
    }
    public void setWeekwork(String weekwork)
    {
        this.weekwork = weekwork;
    }

    public String getWeekwork()
    {
        return weekwork;
    }
    public void setExamine(Integer examine)
    {
        this.examine = examine;
    }

    public Integer getExamine()
    {
        return examine;
    }
    public void setPeopleId1(Integer peopleId1)
    {
        this.peopleId1 = peopleId1;
    }

    public Integer getPeopleId1()
    {
        return peopleId1;
    }
    public void setContents(String contents)
    {
        this.contents = contents;
    }

    public String getContents()
    {
        return contents;
    }
    public void setCreateId(Long createId)
    {
        this.createId = createId;
    }

    public Long getCreateId()
    {
        return createId;
    }
    public void setCreateName(String createName)
    {
        this.createName = createName;
    }

    public String getCreateName()
    {
        return createName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("unitId", getUnitId())
            .append("peopleId", getPeopleId())
            .append("blogDate", getBlogDate())
            .append("weather", getWeather())
            .append("orgcadre", getOrgcadre())
            .append("orgsoldier", getOrgsoldier())
            .append("orgcivilian", getOrgcivilian())
            .append("nowcadre", getNowcadre())
            .append("nowsoldier", getNowsoldier())
            .append("nowcivilian", getNowcivilian())
            .append("tolerance", getTolerance())
            .append("transfer", getTransfer())
            .append("equip", getEquip())
            .append("internal", getInternal())
            .append("dutylead", getDutylead())
            .append("dutya", getDutya())
            .append("dutyb", getDutyb())
            .append("dutyc", getDutyc())
            .append("dutyd", getDutyd())
            .append("dutye", getDutye())
            .append("dutyf", getDutyf())
            .append("dutyinfoa", getDutyinfoa())
            .append("dutyinfoc", getDutyinfoc())
            .append("dutyinfoe", getDutyinfoe())
            .append("dutyDate", getDutyDate())
            .append("invalid", getInvalid())
            .append("notice", getNotice())
            .append("lead", getLead())
            .append("weekwork", getWeekwork())
            .append("examine", getExamine())
            .append("peopleId1", getPeopleId1())
            .append("contents", getContents())
            .append("createId", getCreateId())
            .append("createName", getCreateName())
            .toString();
    }

    private List<BdglDiaryLeave> bdglDiaryLeaveList;

    private int bdglDailyInspectLen;//查铺查哨的数据条数

    private String completionRatio;//要事日记其他项完成比例

    private List<BdglDiaryWeekworkRegister> bdglDiaryWeekworkRegisterList;//要事日记一周工作内容对象

    private List<BdglLeaveOne> bdglLeaveOneList;//单日请假

    public List<BdglDiaryLeave> getBdglDiaryLeaveList() {
        return bdglDiaryLeaveList;
    }

    public void setBdglDiaryLeaveList(List<BdglDiaryLeave> bdglDiaryLeaveList) {
        this.bdglDiaryLeaveList = bdglDiaryLeaveList;
    }

    public int getBdglDailyInspectLen() {
        return bdglDailyInspectLen;
    }

    public void setBdglDailyInspectLen(int bdglDailyInspectLen) {
        this.bdglDailyInspectLen = bdglDailyInspectLen;
    }

    public String getCompletionRatio() {
        return completionRatio;
    }

    public void setCompletionRatio(String completionRatio) {
        this.completionRatio = completionRatio;
    }

    public List<BdglDiaryWeekworkRegister> getBdglDiaryWeekworkRegisterList() {
        return bdglDiaryWeekworkRegisterList;
    }

    public void setBdglDiaryWeekworkRegisterList(List<BdglDiaryWeekworkRegister> bdglDiaryWeekworkRegisterList) {
        this.bdglDiaryWeekworkRegisterList = bdglDiaryWeekworkRegisterList;
    }

    public List<BdglLeaveOne> getBdglLeaveOneList() {
        return bdglLeaveOneList;
    }

    public void setBdglLeaveOneList(List<BdglLeaveOne> bdglLeaveOneList) {
        this.bdglLeaveOneList = bdglLeaveOneList;
    }
}
