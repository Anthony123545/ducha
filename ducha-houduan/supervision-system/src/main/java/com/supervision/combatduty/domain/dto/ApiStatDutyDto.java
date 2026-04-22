package com.supervision.combatduty.domain.dto;

import java.io.Serializable;

/**
 * @author 基础值班统计Api对象
 */
public class ApiStatDutyDto implements Serializable {
    
    /** 单位名称 */
    private String dwmc;
    
    /** 值班时间 */
    private String zbTime;
    
    /** 值班首长 */
    private String zbsz;
    
    /** 值班员 */
    private String zby;
    
    /** 值班岗位 */
    private String zbgw;
    
    /** 值班电话 */
    private String phone;
    
    /** 是否部站 */
    private Integer isBz;
    
    public String getDwmc() {
        return dwmc;
    }
    
    public void setDwmc(String dwmc) {
        this.dwmc = dwmc;
    }
    
    public String getZbTime() {
        return zbTime;
    }
    
    public void setZbTime(String zbTime) {
        this.zbTime = zbTime;
    }
    
    public String getZbsz() {
        return zbsz;
    }
    
    public void setZbsz(String zbsz) {
        this.zbsz = zbsz;
    }
    
    public String getZby() {
        return zby;
    }
    
    public void setZby(String zby) {
        this.zby = zby;
    }
    
    public String getZbgw() {
        return zbgw;
    }
    
    public void setZbgw(String zbgw) {
        this.zbgw = zbgw;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public Integer getIsBz() {
        return isBz;
    }
    
    public void setIsBz(Integer isBz) {
        this.isBz = isBz;
    }
}
