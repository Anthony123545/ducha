package com.supervision.militaryvehicleManagement.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.supervision.common.annotation.Excel;

import java.io.Serializable;
import java.util.Date;

/**
 * 私家车进出统计
 * @author gaoqiang
 */
public class PersonalCarStat implements Serializable {
    
    /** 车牌号 */
    @Excel(name = "车牌号")
    private String chePaiNum;
    /** 进入次数 */
    @Excel(name = "进入次数")
    private Long inCount;
    /** 离开次数 */
    @Excel(name = "离开次数")
    private Long outCount;
    /** 进入时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "进入时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date inTime;
    /** 离开时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "离开时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date outTime;
    
    /** 查询词 开始时间*/
    private String startTime;
    /** 查询词 结束时间*/
    private String endTime;
    public String getChePaiNum() {
        return chePaiNum;
    }
    
    public void setChePaiNum(String chePaiNum) {
        this.chePaiNum = chePaiNum;
    }
    
    public Long getInCount() {
        return inCount;
    }
    
    public void setInCount(Long inCount) {
        this.inCount = inCount;
    }
    
    public Long getOutCount() {
        return outCount;
    }
    
    public void setOutCount(Long outCount) {
        this.outCount = outCount;
    }
    
    public Date getInTime() {
        return inTime;
    }
    
    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }
    
    public Date getOutTime() {
        return outTime;
    }
    
    public void setOutTime(Date outTime) {
        this.outTime = outTime;
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
}
