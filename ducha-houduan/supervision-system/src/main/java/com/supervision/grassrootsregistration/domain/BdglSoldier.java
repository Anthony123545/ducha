package com.supervision.grassrootsregistration.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 军人委员会会议登记本对象 bdgl_soldier
 *
 * @author supervision
 * @date 2022-02-26
 */
public class BdglSoldier extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 召开时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "召开时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date convokeTime;

    /** 召开地点 */
    @Excel(name = "召开地点")
    private String address;

    /** 主持人 */
    @Excel(name = "主持人id")
    private Integer compereId;

    /** 主持人 */
    @Excel(name = "主持人")
    private String compereRen;

    /** 参加人 */
    @Excel(name = "参加人")
    private String joinId;

    /** 列席人 */
    @Excel(name = "列席人")
    private String peopleIds;

    /** 缺席人 */
    @Excel(name = "缺席人")
    private String absentIds;

    /** 记录人 */
    @Excel(name = "记录人id")
    private Integer peopleId;
    /** 记录人 */
    @Excel(name = "记录人")
    private String peopleJiLu;

    /** 会议议题 */
    @Excel(name = "会议议题")
    private String title;

    /** 会议内容 */
    @Excel(name = "会议内容")
    private String contents;

    /** 单位 */
    @Excel(name = "单位")
    private Integer unitId;
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createtime;
    /**修改时间*/
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updatetime;

    /**部门名称*/
    @Excel(name = "部门名称")
    private String deptName;


    public Integer getCompereId() {
        return compereId;
    }

    public void setCompereId(Integer compereId) {
        this.compereId = compereId;
    }

    public String getCompereRen() {
        return compereRen;
    }

    public void setCompereRen(String compereRen) {
        this.compereRen = compereRen;
    }

    public String getPeopleJiLu() {
        return peopleJiLu;
    }

    public void setPeopleJiLu(String peopleJiLu) {
        this.peopleJiLu = peopleJiLu;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }
    public void setConvokeTime(Date convokeTime)
    {
        this.convokeTime = convokeTime;
    }

    public Date getConvokeTime()
    {
        return convokeTime;
    }
    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getAddress()
    {
        return address;
    }

    public void setJoinId(String joinId)
    {
        this.joinId = joinId;
    }

    public String getJoinId()
    {
        return joinId;
    }
    public void setPeopleIds(String peopleIds)
    {
        this.peopleIds = peopleIds;
    }

    public String getPeopleIds()
    {
        return peopleIds;
    }
    public void setAbsentIds(String absentIds)
    {
        this.absentIds = absentIds;
    }

    public String getAbsentIds()
    {
        return absentIds;
    }
    public void setPeopleId(Integer peopleId)
    {
        this.peopleId = peopleId;
    }

    public Integer getPeopleId()
    {
        return peopleId;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }
    public void setContents(String contents)
    {
        this.contents = contents;
    }

    public String getContents()
    {
        return contents;
    }
    public void setUnitId(Integer unitId)
    {
        this.unitId = unitId;
    }

    public Integer getUnitId()
    {
        return unitId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("convokeTime", getConvokeTime())
            .append("address", getAddress())
            .append("compereId", getCompereId())
            .append("joinId", getJoinId())
            .append("peopleIds", getPeopleIds())
            .append("absentIds", getAbsentIds())
            .append("peopleId", getPeopleId())
            .append("title", getTitle())
            .append("contents", getContents())
            .append("createtime", getCreatetime())
            .append("updatetime", getUpdatetime())
            .append("unitId", getUnitId())
            .toString();
    }

    private String fileUrl;//图片地址

    private String meetingForm;//会议形式

    public String getMeetingForm() {
        return meetingForm;
    }

    public void setMeetingForm(String meetingForm) {
        this.meetingForm = meetingForm;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

}
