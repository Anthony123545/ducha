package com.supervision.workingArrangements.domain;

import java.util.List;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 任务计划基本信息对象 bdgl_work_task_plan
 * 
 * @author ruoyi
 * @date 2022-08-15
 */
public class BdglWorkTaskPlan extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 任务计划ID */
    private Long id;

    /** 任务名称 */
    @Excel(name = "任务名称")
    private String taskName;

    /** 任务开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "任务开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 任务结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "任务结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 任务备注 */
    @Excel(name = "任务备注")
    private String remarkInfo;

    /** 任务附件信息 */
    private List<BdglWorkTaskPlanAnner> annexList;
    
//    /** 任务详情信息 */
//    private List<BdglWorkTaskPlanDetail> detailList;
    
    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTaskName(String taskName) 
    {
        this.taskName = taskName;
    }

    public String getTaskName() 
    {
        return taskName;
    }
    public void setStartTime(Date startTime) 
    {
        this.startTime = startTime;
    }

    public Date getStartTime() 
    {
        return startTime;
    }
    public void setEndTime(Date endTime) 
    {
        this.endTime = endTime;
    }

    public Date getEndTime() 
    {
        return endTime;
    }
    public void setRemarkInfo(String remarkInfo)
    {
        this.remarkInfo = remarkInfo;
    }

    public String getRemarkInfo() 
    {
        return remarkInfo;
    }
    
    public List<BdglWorkTaskPlanAnner> getAnnexList() {
        return annexList;
    }
    
    public void setAnnexList(List<BdglWorkTaskPlanAnner> annexList) {
        this.annexList = annexList;
    }
    
//    public List<BdglWorkTaskPlanDetail> getDetailList() {
//        return detailList;
//    }
//
//    public void setDetailList(List<BdglWorkTaskPlanDetail> detailList) {
//        this.detailList = detailList;
//    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("taskName", getTaskName())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("remarkInfo", getRemarkInfo())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("annexList", getAnnexList())
            .toString();
    }
}
