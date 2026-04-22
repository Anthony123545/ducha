package com.supervision.bdglregular.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 检查审批不通过记录对象 bdgl_rectification_record
 * 
 * @author supervision
 * @date 2022-03-08
 */
public class BdglRectificationRecord extends BaseEntity
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

    /** 类型 */
    @Excel(name = "类型")
    private String inspectionType;

    /** 单位检查_id */
    @Excel(name = "单位检查_id")
    private Long unitCheckId;

    /** 检查单位 */
    @Excel(name = "检查单位")
    private String unitCheck;

    /** 检查时间 */
    @Excel(name = "检查时间")
    private String inspectionTime;

    /** 检查地点 */
    @Excel(name = "检查地点")
    private String inspectionSite;

    /** 分值 */
    @Excel(name = "分值")
    private BigDecimal score;

    /** 整改时间 */
    @Excel(name = "整改时间")
    private String rectificationTime;

    /** 是由 */
    @Excel(name = "是由")
    private String question;

    /** 创建人ID */
    @Excel(name = "创建人ID")
    private Long createUserId;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createUser;

    /** 负责人 */
    @Excel(name = "负责人")
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

    /** 操作状态，加分、减分 */
    @Excel(name = "操作状态，加分、减分")
    private String caozuoType;

    /** 外键ID */
    @Excel(name = "外键ID")
    private Long wjId;

    /** 负责人ID */
    @Excel(name = "负责人ID")
    private Long chargePeopleId;

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
    public void setInspectionType(String inspectionType) 
    {
        this.inspectionType = inspectionType;
    }

    public String getInspectionType() 
    {
        return inspectionType;
    }
    public void setUnitCheckId(Long unitCheckId) 
    {
        this.unitCheckId = unitCheckId;
    }

    public Long getUnitCheckId() 
    {
        return unitCheckId;
    }
    public void setUnitCheck(String unitCheck) 
    {
        this.unitCheck = unitCheck;
    }

    public String getUnitCheck() 
    {
        return unitCheck;
    }
    public void setInspectionTime(String inspectionTime) 
    {
        this.inspectionTime = inspectionTime;
    }

    public String getInspectionTime() 
    {
        return inspectionTime;
    }
    public void setInspectionSite(String inspectionSite) 
    {
        this.inspectionSite = inspectionSite;
    }

    public String getInspectionSite() 
    {
        return inspectionSite;
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
    public void setQuestion(String question) 
    {
        this.question = question;
    }

    public String getQuestion() 
    {
        return question;
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
    public void setCaozuoType(String caozuoType) 
    {
        this.caozuoType = caozuoType;
    }

    public String getCaozuoType() 
    {
        return caozuoType;
    }
    public void setWjId(Long wjId) 
    {
        this.wjId = wjId;
    }

    public Long getWjId() 
    {
        return wjId;
    }
    public void setChargePeopleId(Long chargePeopleId) 
    {
        this.chargePeopleId = chargePeopleId;
    }

    public Long getChargePeopleId() 
    {
        return chargePeopleId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("inspectedUnitId", getInspectedUnitId())
            .append("inspectedUnit", getInspectedUnit())
            .append("inspectionType", getInspectionType())
            .append("unitCheckId", getUnitCheckId())
            .append("unitCheck", getUnitCheck())
            .append("inspectionTime", getInspectionTime())
            .append("inspectionSite", getInspectionSite())
            .append("score", getScore())
            .append("rectificationTime", getRectificationTime())
            .append("question", getQuestion())
            .append("createTime", getCreateTime())
            .append("createUserId", getCreateUserId())
            .append("createUser", getCreateUser())
            .append("chargePeople", getChargePeople())
            .append("correctiveAction", getCorrectiveAction())
            .append("pictureProof", getPictureProof())
            .append("documentProof", getDocumentProof())
            .append("caozuoType", getCaozuoType())
            .append("wjId", getWjId())
            .append("chargePeopleId", getChargePeopleId())
            .toString();
    }
}
