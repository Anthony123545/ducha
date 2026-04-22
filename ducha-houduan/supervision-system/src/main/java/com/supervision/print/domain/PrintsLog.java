package com.supervision.print.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 集中文印记录对象 prints_log
 *
 * @author supervision
 * @date 2022-03-18
 */
public class PrintsLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 打印id */
    @Excel(name = "打印id")
    private Long printId;

    /** 打印文件名称 */
    @Excel(name = "打印文件名称")
    private String printName;

    /** 打印份数 */
    @Excel(name = "打印份数")
    private Long printNumber;

    /** 是否需要归还(0否1是) */
    //@Excel(name = "是否需要归还")
    private Integer isReturn;

    /** 文件打印人 */
    @Excel(name = "文件打印人")
    private String printfileName;

    /** 打印时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "打印时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date printTime;

    /** 归还状态(1 已归还  0未归还) */
    @Excel(name = "是否归还",dictType = "sys_statusG")
    private Integer statusG;

    /** 归还份数 */
    @Excel(name = "归还份数")
    private Long returnNumber;

    /** 打印编码信息 */
    @Excel(name = "打印编码信息")
    private String printCode;

    /** 原文件路径 */
    //@Excel(name = "原文件路径")
    private String primaryPath;

    /** 现文件路径 */
    //@Excel(name = "现文件路径")
    private String presentPath;
    @DateTimeFormat(pattern = "yyyy-MM-dd yyyy-MM-dd HH:mm:ss")
    private Date endt;
    @DateTimeFormat(pattern = "yyyy-MM-dd yyyy-MM-dd HH:mm:ss")
    private Date startt;

    public Date getEndt() {
        return endt;
    }

    public void setEndt(Date endt) {
        this.endt = endt;
    }

    public Date getStartt() {
        return startt;
    }

    public void setStartt(Date startt) {
        this.startt = startt;
    }

    public Integer getPrintRoom() {
        return printRoom;
    }

    public void setPrintRoom(Integer printRoom) {
        this.printRoom = printRoom;
    }

    private Integer printRoom;


    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    public String getExplainInfo() {
        return explainInfo;
    }

    public void setExplainInfo(String explainInfo) {
        this.explainInfo = explainInfo;
    }


    /** 打印时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "归还时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date returnTime;

    public Date getGotothree() {
        return gotothree;
    }

    public void setGotothree(Date gotothree) {
        this.gotothree = gotothree;
    }

    private Date gotothree;

    private Long unitId;

    public String getUnitIds() {
        return unitIds;
    }

    public void setUnitIds(String unitIds) {
        this.unitIds = unitIds;
    }

    private String unitIds;

    @Excel(name = "部门名称")
    private String unitName;

    @Excel(name = "密级",dictType = "level")
    private Integer levelM;
    /** 打印说明 */
    @Excel(name = "打印说明")
    private String explainInfo;
    //@Excel(name = "是否需归还")
    private String booleanReturn;

    public String getBooleanReturn() {
        return booleanReturn;
    }

    public void setBooleanReturn(String booleanReturn) {
        this.booleanReturn = booleanReturn;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    @Override
    public String getUnitName() {
        return unitName;
    }

    @Override
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Integer getLevelM() {
        return levelM;
    }

    public void setLevelM(Integer levelM) {
        this.levelM = levelM;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setPrintId(Long printId)
    {
        this.printId = printId;
    }

    public Long getPrintId()
    {
        return printId;
    }
    public void setPrintName(String printName)
    {
        this.printName = printName;
    }

    public String getPrintName()
    {
        return printName;
    }
    public void setPrintNumber(Long printNumber)
    {
        this.printNumber = printNumber;
    }

    public Long getPrintNumber()
    {
        return printNumber;
    }
    public void setIsReturn(Integer isReturn)
    {
        this.isReturn = isReturn;
    }

    public Integer getIsReturn()
    {
        return isReturn;
    }
    public void setPrintfileName(String printfileName)
    {
        this.printfileName = printfileName;
    }

    public String getPrintfileName()
    {
        return printfileName;
    }
    public void setPrintTime(Date printTime)
    {
        this.printTime = printTime;
    }

    public Date getPrintTime()
    {
        return printTime;
    }
    public void setStatusG(Integer statusG)
    {
        this.statusG = statusG;
    }

    public Integer getStatusG()
    {
        return statusG;
    }
    public void setReturnNumber(Long returnNumber)
    {
        this.returnNumber = returnNumber;
    }

    public Long getReturnNumber()
    {
        return returnNumber;
    }
    public void setPrintCode(String printCode)
    {
        this.printCode = printCode;
    }

    public String getPrintCode()
    {
        return printCode;
    }
    public void setPrimaryPath(String primaryPath)
    {
        this.primaryPath = primaryPath;
    }

    public String getPrimaryPath()
    {
        return primaryPath;
    }
    public void setPresentPath(String presentPath)
    {
        this.presentPath = presentPath;
    }

    public String getPresentPath()
    {
        return presentPath;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("printId", getPrintId())
            .append("printName", getPrintName())
            .append("printNumber", getPrintNumber())
            .append("isReturn", getIsReturn())
            .append("printfileName", getPrintfileName())
            .append("printTime", getPrintTime())
            .append("statusG", getStatusG())
            .append("returnNumber", getReturnNumber())
            .append("printCode", getPrintCode())
            .append("primaryPath", getPrimaryPath())
            .append("presentPath", getPresentPath())
                .append("printRoom",getPrintRoom())
            .toString();
    }
}
