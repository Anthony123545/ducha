package com.supervision.peopleManage.domain.dto;

import java.io.Serializable;

/**
 * 人员年龄分布统计Api
 * @author gaoqiang
 */
public class StatPersonAgeDto implements Serializable {
    
    private String dwmc;
    
    private Integer isBase;
    
    private Integer isBz;
    
    private String nlqj;
    
    private Integer rs;
    
    private Boolean nullData;
    
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
    
    public String getNlqj() {
        return nlqj;
    }
    
    public void setNlqj(String nlqj) {
        this.nlqj = nlqj;
    }
    
    public Integer getRs() {
        return rs;
    }
    
    public void setRs(Integer rs) {
        this.rs = rs;
    }
    
    public Boolean getNullData() {
        return nullData;
    }
    
    public void setNullData(Boolean nullData) {
        this.nullData = nullData;
    }
}
