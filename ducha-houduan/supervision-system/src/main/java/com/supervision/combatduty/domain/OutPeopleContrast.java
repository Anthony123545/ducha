package com.supervision.combatduty.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 外出人员对比设置对象 out_people_contrast
 *
 * @author ruoyi
 * @date 2023-04-07
 */
public class OutPeopleContrast extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 请假人数 */
    @Excel(name = "请假人数")
    private Integer leaveNum;

    /** 学习人数 */
    @Excel(name = "学习人数")
    private Integer studyNum;

    /** 特殊人数 */
    @Excel(name = "特殊人数")
    private Integer speciaNum;

    /** 出差人数 */
    @Excel(name = "出差人数")
    private Integer businessTravelNum;

    /** 休假人数 */
    @Excel(name = "休假人数")
    private Integer vacationNum;

    /** 是否启用(0否,1是) */
    @Excel(name = "是否启用(0否,1是)")
    private String isEnable;

    /** 主键id */
    private Integer id;

    public void setLeaveNum(Integer leaveNum)
    {
        this.leaveNum = leaveNum;
    }

    public Integer getLeaveNum()
    {
        return leaveNum;
    }
    public void setStudyNum(Integer studyNum)
    {
        this.studyNum = studyNum;
    }

    public Integer getStudyNum()
    {
        return studyNum;
    }
    public void setSpeciaNum(Integer speciaNum)
    {
        this.speciaNum = speciaNum;
    }

    public Integer getSpeciaNum()
    {
        return speciaNum;
    }
    public void setBusinessTravelNum(Integer businessTravelNum)
    {
        this.businessTravelNum = businessTravelNum;
    }

    public Integer getBusinessTravelNum()
    {
        return businessTravelNum;
    }
    public void setVacationNum(Integer vacationNum)
    {
        this.vacationNum = vacationNum;
    }

    public Integer getVacationNum()
    {
        return vacationNum;
    }
    public void setIsEnable(String isEnable)
    {
        this.isEnable = isEnable;
    }

    public String getIsEnable()
    {
        return isEnable;
    }
    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("leaveNum", getLeaveNum())
                .append("studyNum", getStudyNum())
                .append("speciaNum", getSpeciaNum())
                .append("businessTravelNum", getBusinessTravelNum())
                .append("vacationNum", getVacationNum())
                .append("isEnable", getIsEnable())
                .append("id", getId())
                .toString();
    }
}