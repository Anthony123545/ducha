package com.supervision.peopleManage.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 人员类别数量对象 bdgl_people_type_statistics
 *
 * @author ruoyi
 * @date 2022-10-29
 */
public class BdglPeopleTypeStatistics extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 单位id */
    @Excel(name = "单位id")
    private Long unitId;

    /** 干部编制人数 */
    @Excel(name = "干部编制人数")
    private String inpreparationGb;

    /** 士官编制人数 */
    @Excel(name = "士官编制人数")
    private String inpreparationSg;

    /** 义务兵编制人数 */
    @Excel(name = "义务兵编制人数")
    private String inpreparationYwb;

    /** 干部在位人数 */
    @Excel(name = "干部在位人数")
    private String incumbentsGb;

    /** 士官在位人数 */
    @Excel(name = "士官在位人数")
    private String incumbentsSg;

    /** 义务兵在位人数 */
    @Excel(name = "义务兵在位人数")
    private String incumbentsYwb;

    /** 干部超（缺）编人员 */
    @Excel(name = "干部超（缺）编人员")
    private String outpreparationGb;

    /** 士官超（缺）编人员 */
    @Excel(name = "士官超（缺）编人员")
    private String outpreparationSg;

    /** 义务兵超（缺）编人员 */
    @Excel(name = "义务兵超（缺）编人员")
    private String outpreparationYwb;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setUnitId(Long unitId)
    {
        this.unitId = unitId;
    }

    public Long getUnitId()
    {
        return unitId;
    }

    public String getInpreparationGb() {
        return inpreparationGb;
    }

    public void setInpreparationGb(String inpreparationGb) {
        this.inpreparationGb = inpreparationGb;
    }

    public String getInpreparationSg() {
        return inpreparationSg;
    }

    public void setInpreparationSg(String inpreparationSg) {
        this.inpreparationSg = inpreparationSg;
    }

    public String getInpreparationYwb() {
        return inpreparationYwb;
    }

    public void setInpreparationYwb(String inpreparationYwb) {
        this.inpreparationYwb = inpreparationYwb;
    }

    public String getIncumbentsGb() {
        return incumbentsGb;
    }

    public void setIncumbentsGb(String incumbentsGb) {
        this.incumbentsGb = incumbentsGb;
    }

    public String getIncumbentsSg() {
        return incumbentsSg;
    }

    public void setIncumbentsSg(String incumbentsSg) {
        this.incumbentsSg = incumbentsSg;
    }

    public String getIncumbentsYwb() {
        return incumbentsYwb;
    }

    public void setIncumbentsYwb(String incumbentsYwb) {
        this.incumbentsYwb = incumbentsYwb;
    }

    public String getOutpreparationGb() {
        return outpreparationGb;
    }

    public void setOutpreparationGb(String outpreparationGb) {
        this.outpreparationGb = outpreparationGb;
    }

    public String getOutpreparationSg() {
        return outpreparationSg;
    }

    public void setOutpreparationSg(String outpreparationSg) {
        this.outpreparationSg = outpreparationSg;
    }

    public String getOutpreparationYwb() {
        return outpreparationYwb;
    }

    public void setOutpreparationYwb(String outpreparationYwb) {
        this.outpreparationYwb = outpreparationYwb;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("unitId", getUnitId())
            .append("inpreparationGb", getInpreparationGb())
            .append("inpreparationSg", getInpreparationSg())
            .append("inpreparationYwb", getInpreparationYwb())
            .append("incumbentsGb", getIncumbentsGb())
            .append("incumbentsSg", getIncumbentsSg())
            .append("incumbentsYwb", getIncumbentsYwb())
            .append("outpreparationGb", getOutpreparationGb())
            .append("outpreparationSg", getOutpreparationSg())
            .append("outpreparationYwb", getOutpreparationYwb())
            .append("createTime", getCreateTime())
            .append("createBy", getCreateBy())
            .toString();
    }
}
