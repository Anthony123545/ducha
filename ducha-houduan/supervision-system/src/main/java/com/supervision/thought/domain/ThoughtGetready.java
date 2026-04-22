package com.supervision.thought.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 教育准备会对象 thought_getready
 * 
 * @author supervision
 * @date 2022-04-06
 */
public class ThoughtGetready extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 谈心人员 */
    @Excel(name = "谈心人员")
    private String talkPeople;

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

    /** 会议主题 */
    @Excel(name = "会议主题")
    private String talkTheme;

    /** 会议简介 */
    @Excel(name = "会议简介")
    private String talkIntroduction;

    /** 记录人 */
    @Excel(name = "记录人")
    private String recorder;

    /** 记录人id */
    @Excel(name = "记录人id")
    private String recorderId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTalkPeople(String talkPeople) 
    {
        this.talkPeople = talkPeople;
    }

    public String getTalkPeople() 
    {
        return talkPeople;
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
    public void setTalkTheme(String talkTheme) 
    {
        this.talkTheme = talkTheme;
    }

    public String getTalkTheme() 
    {
        return talkTheme;
    }
    public void setTalkIntroduction(String talkIntroduction) 
    {
        this.talkIntroduction = talkIntroduction;
    }

    public String getTalkIntroduction() 
    {
        return talkIntroduction;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("talkPeople", getTalkPeople())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createName", getCreateName())
            .append("createId", getCreateId())
            .append("filePath", getFilePath())
            .append("fileName", getFileName())
            .append("talkContent", getTalkContent())
            .append("talkTheme", getTalkTheme())
            .append("talkIntroduction", getTalkIntroduction())
            .append("recorder", getRecorder())
            .append("recorderId", getRecorderId())
            .toString();
    }
}
