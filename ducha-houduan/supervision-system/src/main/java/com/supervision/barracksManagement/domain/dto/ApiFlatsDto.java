package com.supervision.barracksManagement.domain.dto;


import java.io.Serializable;

/**
 * 营房态势接口对象
 */
public class ApiFlatsDto implements Serializable {
    
    private String dwmc;
    
    private Double rzl;
    
    private Integer rzzs;
    
    private Integer yfzs;
    
    private Integer isBase;
    
    private Integer isBz;
    
    private Boolean nullData;
    
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
    
    public Boolean getNullData() {
        return nullData;
    }
    
    public void setNullData(Boolean nullData) {
        this.nullData = nullData;
    }
    
    public String getDwmc() {
        return dwmc;
    }
    
    public void setDwmc(String dwmc) {
        this.dwmc = dwmc;
    }
    
    public Double getRzl() {
        return rzl;
    }
    
    public void setRzl(Double rzl) {
        this.rzl = rzl;
    }
    
    public Integer getRzzs() {
        return rzzs;
    }
    
    public void setRzzs(Integer rzzs) {
        this.rzzs = rzzs;
    }
    
    public Integer getYfzs() {
        return yfzs;
    }
    
    public void setYfzs(Integer yfzs) {
        this.yfzs = yfzs;
    }
}
