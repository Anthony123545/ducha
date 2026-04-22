package com.supervision.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 截止日期设置
 对象 set_time
 *
 * @author ruoyi
 * @date 2023-01-10
 */
public class SetTime extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Integer id;

    /** 0放  1开 */
    @Excel(name = "0放  1开")
    private Integer time;

    /** 修改人 */
    @Excel(name = "修改人")
    private Long updateId;

    /** 修改人姓名 */
    @Excel(name = "修改人姓名")
    private String updateName;

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public void setUpdateId(Long updateId)
    {
        this.updateId = updateId;
    }

    public Long getUpdateId()
    {
        return updateId;
    }
    public void setUpdateName(String updateName)
    {
        this.updateName = updateName;
    }

    public String getUpdateName()
    {
        return updateName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("time", getTime())
                .append("updateId", getUpdateId())
                .append("updateName", getUpdateName())
                .toString();
    }
}