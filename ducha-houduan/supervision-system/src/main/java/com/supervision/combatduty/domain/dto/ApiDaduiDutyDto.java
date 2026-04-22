package com.supervision.combatduty.domain.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 大队值班Api接口对象
 * @author gaoqiang
 */
public class ApiDaduiDutyDto implements Serializable {
    
    /** 值班时间 */
    private String zbTime;
    
    /** 值班首长-手机号 */
    private String zbszNameAndPhone;
    
    /** 作战值班员-手机号 */
    private String zzzbyNameAndPhone;
    
    /** 通信值班员-手机号 */
    private String txNameAndPhone;
    
    /** 机要值班员-手机号 */
    private String jyzbyNameAndPhone;
    
    /** 政治工作部值班员-手机号 */
    private String zzbNameAndPhone;
    
    /** 保障部值班员-手机号 */
    private String bzbNameAndPhone;
    
    /** 值班通信员-手机号 */
    private String zbtxyNameAndPhone;
    
    private Integer isBz;
    
    public String getZbTime() {
        return zbTime;
    }
    
    public void setZbTime(String zbTime) {
        this.zbTime = zbTime;
    }
    
    public String getZbszNameAndPhone() {
        return zbszNameAndPhone;
    }
    
    public void setZbszNameAndPhone(String zbszNameAndPhone) {
        this.zbszNameAndPhone = zbszNameAndPhone;
    }
    
    public String getZzzbyNameAndPhone() {
        return zzzbyNameAndPhone;
    }
    
    public void setZzzbyNameAndPhone(String zzzbyNameAndPhone) {
        this.zzzbyNameAndPhone = zzzbyNameAndPhone;
    }
    
    public String getTxNameAndPhone() {
        return txNameAndPhone;
    }
    
    public void setTxNameAndPhone(String txNameAndPhone) {
        this.txNameAndPhone = txNameAndPhone;
    }
    
    public String getJyzbyNameAndPhone() {
        return jyzbyNameAndPhone;
    }
    
    public void setJyzbyNameAndPhone(String jyzbyNameAndPhone) {
        this.jyzbyNameAndPhone = jyzbyNameAndPhone;
    }
    
    public String getZzbNameAndPhone() {
        return zzbNameAndPhone;
    }
    
    public void setZzbNameAndPhone(String zzbNameAndPhone) {
        this.zzbNameAndPhone = zzbNameAndPhone;
    }
    
    public String getBzbNameAndPhone() {
        return bzbNameAndPhone;
    }
    
    public void setBzbNameAndPhone(String bzbNameAndPhone) {
        this.bzbNameAndPhone = bzbNameAndPhone;
    }
    
    public String getZbtxyNameAndPhone() {
        return zbtxyNameAndPhone;
    }
    
    public void setZbtxyNameAndPhone(String zbtxyNameAndPhone) {
        this.zbtxyNameAndPhone = zbtxyNameAndPhone;
    }
    
    public Integer getIsBz() {
        return isBz;
    }
    
    public void setIsBz(Integer isBz) {
        this.isBz = isBz;
    }
}
