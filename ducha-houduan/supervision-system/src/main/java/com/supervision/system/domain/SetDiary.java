package com.supervision.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 要事日记设置 对象 set_diary
 * 
 * @author ruoyi
 * @date 2022-09-14
 */
public class SetDiary extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 设置是否开启 */
    @Excel(name = "设置是否开启")
    private Integer setnum;

    /** 更新人名字 */
    @Excel(name = "更新人名字")
    private String upname;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updates;

    /** 类别（要事、会议） */
    @Excel(name = "类别", readConverterExp = "要=事、会议")
    private Integer types;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setSetnum(Integer setnum) 
    {
        this.setnum = setnum;
    }

    public Integer getSetnum() 
    {
        return setnum;
    }
    public void setUpname(String upname) 
    {
        this.upname = upname;
    }

    public String getUpname() 
    {
        return upname;
    }
    public void setUpdates(Date updates) 
    {
        this.updates = updates;
    }

    public Date getUpdates() 
    {
        return updates;
    }
    public void setTypes(Integer types) 
    {
        this.types = types;
    }

    public Integer getTypes() 
    {
        return types;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("setnum", getSetnum())
            .append("upname", getUpname())
            .append("updates", getUpdates())
            .append("types", getTypes())
            .toString();
    }
}
