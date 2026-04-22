package com.supervision.grassrootsregistration.resultVo;


/**
 * 自定义一周工作安排详情查看返回实体类
 * 仅用作数据封装
 */
public class BdglWeekworkRegisterVo {
    
    /** 时段 */
    private String dateline;
    
    /** 内容 */
    private String contents;
    
    /** 应到 */
    private Integer shouldArrive;
    
    /** 实到 */
    private Integer realTo;
    
    /** 到课率 */
    private String percentage;
    
    public String getDateline() {
        return dateline;
    }
    
    public void setDateline(String dateline) {
        this.dateline = dateline;
    }
    
    public String getContents() {
        return contents;
    }
    
    public void setContents(String contents) {
        this.contents = contents;
    }
    
    public Integer getShouldArrive() {
        return shouldArrive;
    }
    
    public void setShouldArrive(Integer shouldArrive) {
        this.shouldArrive = shouldArrive;
    }
    
    public Integer getRealTo() {
        return realTo;
    }
    
    public void setRealTo(Integer realTo) {
        this.realTo = realTo;
    }
    
    public String getPercentage() {
        return percentage;
    }
    
    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }
}
