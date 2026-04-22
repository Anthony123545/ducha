package com.supervision.thought.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 党委议教会对象 thought_parliament
 * 
 * @author supervision
 * @date 2022-04-06
 */
public class ThoughtParliament extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 单位名称 */
    @Excel(name = "单位名称")
    private String unitName;

    /** 单位id */
    @Excel(name = "单位id")
    private Long unitId;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 创建人姓名 */
    @Excel(name = "创建人姓名")
    private String createName;

    /** 创建人id */
    @Excel(name = "创建人id")
    private Long createId;

    /** 文件路径 */
    @Excel(name = "文件路径")
    private String filePath;

    /** 文件名称 */
    @Excel(name = "文件名称")
    private String fileName;

    /** 会议内容 */
    @Excel(name = "会议内容")
    private String talkContent;

    /** 记录人 */
    @Excel(name = "记录人")
    private String recorder;

    /** 记录人id */
    @Excel(name = "记录人id")
    private String recorderId;

    /** 参加人 */
    @Excel(name = "参加人")
    private String participants;

    /** 参加人id */
    @Excel(name = "参加人id")
    private String participantsId;

    /** 组织人 */
    @Excel(name = "组织人")
    private String organizer;

    /** 组织人id */
    @Excel(name = "组织人id")
    private String organizerId;

    /** 议会主题 */
    @Excel(name = "议会主题")
    private String talkTheme;

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
    public void setStartTime(Date startTime) 
    {
        this.startTime = startTime;
    }

    public Date getStartTime() 
    {
        return startTime;
    }
    public void setEndTime(Date endTime) 
    {
        this.endTime = endTime;
    }

    public Date getEndTime() 
    {
        return endTime;
    }
    public void setCreateName(String createName) 
    {
        this.createName = createName;
    }

    public String getCreateName() 
    {
        return createName;
    }
    public void setCreateId(Long createId) 
    {
        this.createId = createId;
    }

    public Long getCreateId() 
    {
        return createId;
    }
    public void setFilePath(String filePath) 
    {
        this.filePath = filePath;
    }

    public String getFilePath() 
    {
        return filePath;
    }
    public void setFileName(String fileName) 
    {
        this.fileName = fileName;
    }

    public String getFileName() 
    {
        return fileName;
    }
    public void setTalkContent(String talkContent) 
    {
        this.talkContent = talkContent;
    }

    public String getTalkContent() 
    {
        return talkContent;
    }
    public void setRecorder(String recorder) 
    {
        this.recorder = recorder;
    }

    public String getRecorder() 
    {
        return recorder;
    }
    public void setRecorderId(String recorderId) 
    {
        this.recorderId = recorderId;
    }

    public String getRecorderId() 
    {
        return recorderId;
    }
    public void setParticipants(String participants) 
    {
        this.participants = participants;
    }

    public String getParticipants() 
    {
        return participants;
    }
    public void setParticipantsId(String participantsId) 
    {
        this.participantsId = participantsId;
    }

    public String getParticipantsId() 
    {
        return participantsId;
    }
    public void setOrganizer(String organizer) 
    {
        this.organizer = organizer;
    }

    public String getOrganizer() 
    {
        return organizer;
    }
    public void setOrganizerId(String organizerId) 
    {
        this.organizerId = organizerId;
    }

    public String getOrganizerId() 
    {
        return organizerId;
    }
    public void setTalkTheme(String talkTheme) 
    {
        this.talkTheme = talkTheme;
    }

    public String getTalkTheme() 
    {
        return talkTheme;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("unitName", getUnitName())
            .append("unitId", getUnitId())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createName", getCreateName())
            .append("createId", getCreateId())
            .append("filePath", getFilePath())
            .append("fileName", getFileName())
            .append("talkContent", getTalkContent())
            .append("recorder", getRecorder())
            .append("recorderId", getRecorderId())
            .append("participants", getParticipants())
            .append("participantsId", getParticipantsId())
            .append("organizer", getOrganizer())
            .append("organizerId", getOrganizerId())
            .append("talkTheme", getTalkTheme())
            .toString();
    }
}
