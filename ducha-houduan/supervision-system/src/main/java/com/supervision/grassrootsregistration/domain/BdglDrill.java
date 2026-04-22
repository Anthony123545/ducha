package com.supervision.grassrootsregistration.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 军事训练登记簿对象 bdgl_drill
 *
 * @author supervision
 * @date 2022-05-21
 */
public class BdglDrill extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Integer id;

    /** 科目内容 */
    @Excel(name = "科目内容")
    private String project;



    /** 训练开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "训练开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date drillTime;

    /** 训练模式 */
    @Excel(name = "训练模式")
    private String oughtTo;

    /** 单位 */
    @Excel(name = "单位")
    private String unitId;

    /** 文件名称 */
    @Excel(name = "文件名称")
    private String filename;

    /** 文件路径 */
    @Excel(name = "文件路径")
    private String filepath;

    /** 装备（台次） */
    @Excel(name = "装备", readConverterExp = "台=次")
    private String equipment;

    /** 弹药 */
    @Excel(name = "弹药")
    private String ammunition;

    /** 摩托时间 */
    @Excel(name = "摩托时间")
    private String mototime;

    /** 训练结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "训练结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date drillEndtime;

    /** 训练类别 */
    @Excel(name = "训练类别")
    private String trainingCategory;

    /** 训练地点 */
    @Excel(name = "训练地点")
    private String address;

    /** 创建人id */
    @Excel(name = "创建人id")
    private Long createId;

    /** 安全训练 */
    @Excel(name = "安全训练")
    private String safety;

    /** 主官训练 */
    @Excel(name = "主官训练")
    private String chiefOfficial;

    /** 针对性训练 */
    @Excel(name = "针对性训练")
    private String targeted;

    /** 系统性训练 */
    @Excel(name = "系统性训练")
    private String system;

    /** 优良人数 */
    @Excel(name = "优良人数")
    private String excellent;

    /** 及格人数 */
    @Excel(name = "及格人数")
    private Long pass;

    /** 比几个人数 */
    @Excel(name = "比几个人数")
    private String fail;

    /** 应训 */
    @Excel(name = "应训")
    private Long yingxun;

    /** 实训 */
    @Excel(name = "实训")
    private Long shixun;

    /** 参训率 */
    @Excel(name = "参训率")
    private String participationRate;

    private String common;

    private String name;

    private String unitname;

    /**训练内容*/
    private String trainingContent;

    private String type;

    public String getTrainingContent() {
        return trainingContent;
    }

    public void setTrainingContent(String trainingContent) {
        this.trainingContent = trainingContent;
    }

    public String getUnitname() {
        return unitname;
    }

    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommon() {
        return common;
    }

    public void setCommon(String common) {
        this.common = common;
    }

    private List<BdglDrillFu> drillFus;

    private String assessment;

    private List<BdglWeekworkRegister> weekworkRegisters;


    public List<BdglWeekworkRegister> getWeekworkRegisters() {
        return weekworkRegisters;
    }

    public void setWeekworkRegisters(List<BdglWeekworkRegister> weekworkRegisters) {
        this.weekworkRegisters = weekworkRegisters;
    }

    public String getAssessment() {
        return assessment;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }

    public List<BdglDrillFu> getDrillFus() {
        return drillFus;
    }

    public void setDrillFus(List<BdglDrillFu> drillFus) {
        this.drillFus = drillFus;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }
    public void setProject(String project)
    {
        this.project = project;
    }

    public String getProject()
    {
        return project;
    }
    public void setDrillTime(Date drillTime)
    {
        this.drillTime = drillTime;
    }

    public Date getDrillTime()
    {
        return drillTime;
    }
    public void setOughtTo(String oughtTo)
    {
        this.oughtTo = oughtTo;
    }

    public String getOughtTo()
    {
        return oughtTo;
    }
    public void setUnitId(String unitId)
    {
        this.unitId = unitId;
    }

    public String getUnitId()
    {
        return unitId;
    }
    public void setFilename(String filename)
    {
        this.filename = filename;
    }

    public String getFilename()
    {
        return filename;
    }
    public void setFilepath(String filepath)
    {
        this.filepath = filepath;
    }

    public String getFilepath()
    {
        return filepath;
    }
    public void setEquipment(String equipment)
    {
        this.equipment = equipment;
    }

    public String getEquipment()
    {
        return equipment;
    }
    public void setAmmunition(String ammunition)
    {
        this.ammunition = ammunition;
    }

    public String getAmmunition()
    {
        return ammunition;
    }
    public void setMototime(String mototime)
    {
        this.mototime = mototime;
    }

    public String getMototime()
    {
        return mototime;
    }
    public void setDrillEndtime(Date drillEndtime)
    {
        this.drillEndtime = drillEndtime;
    }

    public Date getDrillEndtime()
    {
        return drillEndtime;
    }
    public void setTrainingCategory(String trainingCategory)
    {
        this.trainingCategory = trainingCategory;
    }

    public String getTrainingCategory()
    {
        return trainingCategory;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getAddress()
    {
        return address;
    }
    public void setCreateId(Long createId)
    {
        this.createId = createId;
    }

    public Long getCreateId()
    {
        return createId;
    }
    public void setSafety(String safety)
    {
        this.safety = safety;
    }

    public String getSafety()
    {
        return safety;
    }
    public void setChiefOfficial(String chiefOfficial)
    {
        this.chiefOfficial = chiefOfficial;
    }

    public String getChiefOfficial()
    {
        return chiefOfficial;
    }
    public void setTargeted(String targeted)
    {
        this.targeted = targeted;
    }

    public String getTargeted()
    {
        return targeted;
    }
    public void setSystem(String system)
    {
        this.system = system;
    }

    public String getSystem()
    {
        return system;
    }

    public void setYingxun(Long yingxun)
    {
        this.yingxun = yingxun;
    }

    public Long getYingxun()
    {
        return yingxun;
    }
    public void setShixun(Long shixun)
    {
        this.shixun = shixun;
    }

    public Long getShixun()
    {
        return shixun;
    }

    public String getExcellent() {
        return excellent;
    }

    public void setExcellent(String excellent) {
        this.excellent = excellent;
    }

    public Long getPass() {
        return pass;
    }

    public void setPass(Long pass) {
        this.pass = pass;
    }

    public String getFail() {
        return fail;
    }

    public void setFail(String fail) {
        this.fail = fail;
    }

    public String getParticipationRate() {
        return participationRate;
    }

    public void setParticipationRate(String participationRate) {
        this.participationRate = participationRate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("project", getProject())
                .append("drillTime", getDrillTime())
                .append("oughtTo", getOughtTo())
                .append("remark", getRemark())
                .append("unitId", getUnitId())
                .append("filename", getFilename())
                .append("filepath", getFilepath())
                .append("equipment", getEquipment())
                .append("ammunition", getAmmunition())
                .append("mototime", getMototime())
                .append("drillEndtime", getDrillEndtime())
                .append("trainingCategory", getTrainingCategory())
                .append("address", getAddress())
                .append("createtime", getCreatetime())
                .append("createId", getCreateId())
                .append("safety", getSafety())
                .append("chiefOfficial", getChiefOfficial())
                .append("targeted", getTargeted())
                .append("system", getSystem())
                .append("excellent", getExcellent())
                .append("pass", getPass())
                .append("fail", getFail())
                .append("yingxun", getYingxun())
                .append("shixun", getShixun())
                .append("participationRate", getParticipationRate())
                .toString();
    }
}