package com.supervision.peopleManage.domain.dto;

import java.io.Serializable;

/**
 * 政治面貌统计Api
 */
public class StatPersonZzmmDto implements Serializable {
    
    private String dwmc;
    
    private Integer isBase;
    
    private Integer isBz;
    
    private Integer rs;
    
    private String zzmm;
    
    public String getDwmc() {
        return dwmc;
    }
    
    public void setDwmc(String dwmc) {
        this.dwmc = dwmc;
    }
    
    public Integer getIsBase() {
        return isBase;
    }
    
    public void setIsBase(Integer isBase) {
        this.isBase = isBase;
    }
    
    public Integer getIsBz() {
        return isBz;
    }
    
    public void setIsBz(Integer isBz) {
        this.isBz = isBz;
    }
    
    public Integer getRs() {
        return rs;
    }
    
    public void setRs(Integer rs) {
        this.rs = rs;
    }
    
    public String getZzmm() {
        return zzmm;
    }
    
    public void setZzmm(String zzmm) {
        this.zzmm = zzmm;
    }
}
