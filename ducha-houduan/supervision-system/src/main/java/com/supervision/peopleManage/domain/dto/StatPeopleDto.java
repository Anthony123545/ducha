package com.supervision.peopleManage.domain.dto;

import java.io.Serializable;

/**
 * 人员在位情况统计Api
 */
public class StatPeopleDto implements Serializable {
    
    private String dwmc;
    
    private Integer isBase;
    
    private Integer isBz;
    
    private Integer zwrs;
    
    private Integer lwrs;
    
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
    
    public Integer getZwrs() {
        return zwrs;
    }
    
    public void setZwrs(Integer zwrs) {
        this.zwrs = zwrs;
    }
    
    public Integer getLwrs() {
        return lwrs;
    }
    
    public void setLwrs(Integer lwrs) {
        this.lwrs = lwrs;
    }
    
    public Boolean getNullData() {
        return nullData;
    }
    
    public void setNullData(Boolean nullData) {
        this.nullData = nullData;
    }
}
