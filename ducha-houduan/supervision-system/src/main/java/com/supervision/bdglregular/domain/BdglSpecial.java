package com.supervision.bdglregular.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 专项检查对象 bdgl_special
 * 
 * @author supervision
 * @date 2022-03-07
 */
public class BdglSpecial extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 受检单位_id */
    @Excel(name = "受检单位_id")
    private Long inspectedUnitId;

    /** 受检单位 */
    @Excel(name = "受检单位")
    private String inspectedUnit;

    /** 检查时间 */
    @Excel(name = "检查时间")
    private String inspectionTime;

    /** 积分 */
    @Excel(name = "积分")
    private BigDecimal score;

    /** 整改时间 */
    @Excel(name = "整改时间")
    private String rectificationTime;

    /** 当前状态 */
    @Excel(name = "当前状态")
    private String currentState;

    /** 是由（存在问题） */
    @Excel(name = "是由", readConverterExp = "存=在问题")
    private String question;

    /** 整改负责人ID */
    @Excel(name = "整改负责人ID")
    private Long chargePeopleId;

    /** 整改负责人 */
    @Excel(name = "整改负责人")
    private String chargePeople;

    /** 整改措施 */
    @Excel(name = "整改措施")
    private String correctiveAction;

    /** 图片举证 */
    @Excel(name = "图片举证")
    private String pictureProof;

    /** 文件举证 */
    @Excel(name = "文件举证")
    private String documentProof;

    /** 创建人ID */
    @Excel(name = "创建人ID")
    private Long createUserId;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createUser;

    /**
     *审批不通过原因
     */
    private String reasonReturn;

    /**
     * 操作状态
     */
    private String caoZuoType;

    /**
     * 受检单位的父部门ID
     */
    private Long parentId;

    /**
     * 判断是不是可以整改
     */
    private String isCaoZuoDept;

    /**
     * 判断当前用户是否可以审批
     */
    private String isShenPi;

    /** 单位检查_id */
    private Long unitCheckId;

    /**
     * 排序字段
     */
    private Integer paixuValue;

    /**
     * 文件名称
     */
    private String wenjianname;


    /** 检查单位名称 */
    @Excel(name = "检查单位名称")
    private String unitCheckName;

    /** 检查类型 */
    @Excel(name = "检查类型")
    private String inspectionType;

    /** 检查地点 */
    @Excel(name = "检查地点")
    private String inspectionSite;


    public String getUnitCheckName() {
        return unitCheckName;
    }

    public void setUnitCheckName(String unitCheckName) {
        this.unitCheckName = unitCheckName;
    }

    public String getInspectionType() {
        return inspectionType;
    }

    public void setInspectionType(String inspectionType) {
        this.inspectionType = inspectionType;
    }

    public String getInspectionSite() {
        return inspectionSite;
    }

    public void setInspectionSite(String inspectionSite) {
        this.inspectionSite = inspectionSite;
    }

    public String getWenjianname() {
        return wenjianname;
    }

    public void setWenjianname(String wenjianname) {
        this.wenjianname = wenjianname;
    }

    public Integer getPaixuValue() {
        return paixuValue;
    }

    public void setPaixuValue(Integer paixuValue) {
        this.paixuValue = paixuValue;
    }

    public Long getUnitCheckId() {
        return unitCheckId;
    }

    public void setUnitCheckId(Long unitCheckId) {
        this.unitCheckId = unitCheckId;
    }

    public String getIsCaoZuoDept() {
        return isCaoZuoDept;
    }

    public void setIsCaoZuoDept(String isCaoZuoDept) {
        this.isCaoZuoDept = isCaoZuoDept;
    }

    public String getIsShenPi() {
        return isShenPi;
    }

    public void setIsShenPi(String isShenPi) {
        this.isShenPi = isShenPi;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getReasonReturn() {
        return reasonReturn;
    }

    public void setReasonReturn(String reasonReturn) {
        this.reasonReturn = reasonReturn;
    }

    public String getCaoZuoType() {
        return caoZuoType;
    }

    public void setCaoZuoType(String caoZuoType) {
        this.caoZuoType = caoZuoType;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setInspectedUnitId(Long inspectedUnitId) 
    {
        this.inspectedUnitId = inspectedUnitId;
    }

    public Long getInspectedUnitId() 
    {
        return inspectedUnitId;
    }
    public void setInspectedUnit(String inspectedUnit) 
    {
        this.inspectedUnit = inspectedUnit;
    }

    public String getInspectedUnit() 
    {
        return inspectedUnit;
    }
    public void setInspectionTime(String inspectionTime) 
    {
        this.inspectionTime = inspectionTime;
    }

    public String getInspectionTime() 
    {
        return inspectionTime;
    }
    public void setScore(BigDecimal score) 
    {
        this.score = score;
    }

    public BigDecimal getScore() 
    {
        return score;
    }
    public void setRectificationTime(String rectificationTime) 
    {
        this.rectificationTime = rectificationTime;
    }

    public String getRectificationTime() 
    {
        return rectificationTime;
    }
    public void setCurrentState(String currentState) 
    {
        this.currentState = currentState;
    }

    public String getCurrentState() 
    {
        return currentState;
    }
    public void setQuestion(String question) 
    {
        this.question = question;
    }

    public String getQuestion() 
    {
        return question;
    }
    public void setChargePeopleId(Long chargePeopleId) 
    {
        this.chargePeopleId = chargePeopleId;
    }

    public Long getChargePeopleId() 
    {
        return chargePeopleId;
    }
    public void setChargePeople(String chargePeople) 
    {
        this.chargePeople = chargePeople;
    }

    public String getChargePeople() 
    {
        return chargePeople;
    }
    public void setCorrectiveAction(String correctiveAction) 
    {
        this.correctiveAction = correctiveAction;
    }

    public String getCorrectiveAction() 
    {
        return correctiveAction;
    }
    public void setPictureProof(String pictureProof) 
    {
        this.pictureProof = pictureProof;
    }

    public String getPictureProof() 
    {
        return pictureProof;
    }
    public void setDocumentProof(String documentProof) 
    {
        this.documentProof = documentProof;
    }

    public String getDocumentProof() 
    {
        return documentProof;
    }
    public void setCreateUserId(Long createUserId) 
    {
        this.createUserId = createUserId;
    }

    public Long getCreateUserId() 
    {
        return createUserId;
    }
    public void setCreateUser(String createUser) 
    {
        this.createUser = createUser;
    }

    public String getCreateUser() 
    {
        return createUser;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("inspectedUnitId", getInspectedUnitId())
            .append("inspectedUnit", getInspectedUnit())
            .append("inspectionTime", getInspectionTime())
            .append("score", getScore())
            .append("rectificationTime", getRectificationTime())
            .append("currentState", getCurrentState())
            .append("question", getQuestion())
            .append("chargePeopleId", getChargePeopleId())
            .append("chargePeople", getChargePeople())
            .append("correctiveAction", getCorrectiveAction())
            .append("pictureProof", getPictureProof())
            .append("documentProof", getDocumentProof())
            .append("createTime", getCreateTime())
            .append("createUserId", getCreateUserId())
            .append("createUser", getCreateUser())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
