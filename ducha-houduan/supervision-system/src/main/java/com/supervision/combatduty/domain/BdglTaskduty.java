package com.supervision.combatduty.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 执行任务对象 bdgl_taskduty
 * 
 * @author supervision
 * @date 2022-03-11
 */
public class BdglTaskduty extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 单位 */
    @Excel(name = "单位")
    private String unitName;

    /** 部门ID */
    @Excel(name = "部门ID")
    private Long unitId;

    /** 指挥员 */
    @Excel(name = "指挥员")
    private String commanderName;

    /** 指挥员ID */
    @Excel(name = "指挥员ID")
    private Long commanderId;

    /** 指挥员电话 */
    @Excel(name = "指挥员电话")
    private String commanderPhone;

    /** 参加人员 */
    @Excel(name = "参加人员")
    private String participantsName;

    /** 参加人员ID */
    @Excel(name = "参加人员ID")
    private String participantsId;

    /** 开始时间 */
    @Excel(name = "开始时间")
    private String startTime;

    /** 结束时间 */
    @Excel(name = "结束时间")
    private String endTime;

    /** 所在位置 */
    @Excel(name = "所在位置")
    private String adress;

    /** 是由 */
    @Excel(name = "是由")
    private String question;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    /**文件路径*/
    private String filepath;

    /**文件名称*/
    private String filename;

    private List<BdglTaskdutyFu> taskdutyFus;


    public List<BdglTaskdutyFu> getTaskdutyFus() {
        return taskdutyFus;
    }

    public void setTaskdutyFus(List<BdglTaskdutyFu> taskdutyFus) {
        this.taskdutyFus = taskdutyFus;
    }


    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    private List<Integer> participantsIds;

    public List<Integer> getParticipantsIds() {
        return participantsIds;
    }

    public void setParticipantsIds(List<Integer> participantsIds) {
        this.participantsIds = participantsIds;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUnitName(String unitName) 
    {
        this.unitName = unitName;
    }

    public String getUnitName() 
    {
        return unitName;
    }
    public void setUnitId(Long unitId) 
    {
        this.unitId = unitId;
    }

    public Long getUnitId() 
    {
        return unitId;
    }
    public void setCommanderName(String commanderName) 
    {
        this.commanderName = commanderName;
    }

    public String getCommanderName() 
    {
        return commanderName;
    }
    public void setCommanderId(Long commanderId) 
    {
        this.commanderId = commanderId;
    }

    public Long getCommanderId() 
    {
        return commanderId;
    }
    public void setCommanderPhone(String commanderPhone) 
    {
        this.commanderPhone = commanderPhone;
    }

    public String getCommanderPhone() 
    {
        return commanderPhone;
    }
    public void setParticipantsName(String participantsName) 
    {
        this.participantsName = participantsName;
    }

    public String getParticipantsName() 
    {
        return participantsName;
    }
    public void setParticipantsId(String participantsId) 
    {
        this.participantsId = participantsId;
    }

    public String getParticipantsId() 
    {
        return participantsId;
    }
    public void setStartTime(String startTime) 
    {
        this.startTime = startTime;
    }

    public String getStartTime() 
    {
        return startTime;
    }
    public void setEndTime(String endTime) 
    {
        this.endTime = endTime;
    }

    public String getEndTime() 
    {
        return endTime;
    }
    public void setAdress(String adress) 
    {
        this.adress = adress;
    }

    public String getAdress() 
    {
        return adress;
    }
    public void setQuestion(String question) 
    {
        this.question = question;
    }

    public String getQuestion() 
    {
        return question;
    }
    public void setRemarks(String remarks) 
    {
        this.remarks = remarks;
    }

    public String getRemarks() 
    {
        return remarks;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("unitName", getUnitName())
            .append("unitId", getUnitId())
            .append("commanderName", getCommanderName())
            .append("commanderId", getCommanderId())
            .append("commanderPhone", getCommanderPhone())
            .append("participantsName", getParticipantsName())
            .append("participantsId", getParticipantsId())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("adress", getAdress())
            .append("question", getQuestion())
            .append("remarks", getRemarks())
            .toString();
    }
}
