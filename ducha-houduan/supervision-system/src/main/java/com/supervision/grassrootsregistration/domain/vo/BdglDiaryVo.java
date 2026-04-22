package com.supervision.grassrootsregistration.domain.vo;

import com.supervision.common.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;

/**
 * @author gaoqiang
 */
public class BdglDiaryVo implements Serializable {
    
    private Integer id;
    
    /** 检查人名称 */
    @Excel(name = "检查人名称")
    private String peopleName;
    
    /** 哨兵名称 */
    @Excel(name = "哨兵名称")
    private String sentryName;
    
    /** 领班员名称 */
    @Excel(name = "领班员名称")
    private String foremanName;
    
    /** 被检查单位id */
    private Integer unitId;
    
    /** 被检查单位名称 */
    @Excel(name = "被检查单位名称")
    private String unitName;
    
    /** 检查情况 */
    @Excel(name = "检查情况")
    private String dialogue;
    
    /** 检查时间 */
    @DateTimeFormat(pattern = "HH:mm:ss")
    @Excel(name = "检查时间", width = 30, dateFormat = "HH:mm:ss")
    private Time examineDate;
    
    /** 值班日期 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "值班日期", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date blogDate;
    
    /** 查询词 开始时间*/
    private String startTime;
    /** 查询词 结束时间*/
    private String endTime;
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getUnitId() {
        return unitId;
    }
    
    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }
    
    public String getUnitName() {
        return unitName;
    }
    
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
    
    public String getStartTime() {
        return startTime;
    }
    
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    
    public String getEndTime() {
        return endTime;
    }
    
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    
    public String getPeopleName() {
        return peopleName;
    }
    
    public void setPeopleName(String peopleName) {
        this.peopleName = peopleName;
    }
    
    public String getSentryName() {
        return sentryName;
    }
    
    public void setSentryName(String sentryName) {
        this.sentryName = sentryName;
    }
    
    public String getForemanName() {
        return foremanName;
    }
    
    public void setForemanName(String foremanName) {
        this.foremanName = foremanName;
    }
    
    public String getDialogue() {
        return dialogue;
    }
    
    public void setDialogue(String dialogue) {
        this.dialogue = dialogue;
    }
    
    public Time getExamineDate() {
        return examineDate;
    }
    
    public void setExamineDate(Time examineDate) {
        this.examineDate = examineDate;
    }
    
    public Date getBlogDate() {
        return blogDate;
    }
    
    public void setBlogDate(Date blogDate) {
        this.blogDate = blogDate;
    }
}
