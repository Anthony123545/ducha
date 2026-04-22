package com.supervision.workingArrangements.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

import java.io.Serializable;

/**
 * 任务附件对象 bdgl_work_task_plan_anner
 * 
 * @author ruoyi
 * @date 2022-08-15
 */
public class BdglWorkTaskPlanAnner implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 附件ID */
    private Long id;

    /** 附件名称 */
    @Excel(name = "附件名称")
    private String name;

    /** 附件链接 */
    @Excel(name = "附件链接")
    private String url;

    /** 任务ID */
    @Excel(name = "任务ID")
    private Long taskPlanId;
    
    /** 任务详情ID */
    @Excel(name = "任务详情ID")
    private Long taskPlanDetailId;
    

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setUrl(String url) 
    {
        this.url = url;
    }

    public String getUrl() 
    {
        return url;
    }
    public void setTaskPlanId(Long taskPlanId) 
    {
        this.taskPlanId = taskPlanId;
    }

    public Long getTaskPlanId() 
    {
        return taskPlanId;
    }
    
    public Long getTaskPlanDetailId() {
        return taskPlanDetailId;
    }
    
    public void setTaskPlanDetailId(Long taskPlanDetailId) {
        this.taskPlanDetailId = taskPlanDetailId;
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("url", getUrl())
            .append("taskPlanId", getTaskPlanId())
            .toString();
    }
}
