package com.supervision.peopleManage.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.supervision.common.utils.DictUtils;

import java.io.Serializable;

/**
 * 离位人员情况统计Api
 */
public class StatOutPeopleDto implements Serializable {
    
    private String dwmc;
    
    private Integer isBase;
    
    private Integer isBz;
    
    private String lwlx;
    
    private Integer lwrs;
    
    private Boolean nullData;
    
    @JsonIgnore
    private Integer count;
    
    @JsonIgnore
    private Integer reign;
    
    public Integer getReign() {
        return reign;
    }
    
    public void setReign(Integer reign) {
        this.reign = reign;
    }
    
    public Integer getCount() {
        return count;
    }
    
    public void setCount(Integer count) {
        this.count = count;
    }
    
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
    
    public String getLwlx() {
        return lwlx;
    }
    
    public void setLwlx(String lwlx) {
        this.lwlx = lwlx;
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
