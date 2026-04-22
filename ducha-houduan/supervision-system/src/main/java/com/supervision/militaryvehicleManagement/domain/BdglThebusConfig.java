package com.supervision.militaryvehicleManagement.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

import java.util.Date;

/**
 * 军车管理设置对象 bdgl_thebus_config
 * 
 * @author supervision
 * @date 2022-03-04
 */
public class BdglThebusConfig
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 审批模板名称 */
    @Excel(name = "审批模板名称")
    private String shenpiname;

    /** 一级审批人 */
    @Excel(name = "一级审批人")
    private String oneshenpirenname;

    /** 一级审批人ID */
    @Excel(name = "一级审批人ID")
    private Long onshenpirenid;

    /** 二级审批人 */
    @Excel(name = "二级审批人")
    private String twoshenpirenname;

    /** 二级审批人ID */
    @Excel(name = "二级审批人ID")
    private Long twoshenpirenid;

    /** 三级审批人 */
    @Excel(name = "三级审批人")
    private String threeshenpirenname;

    /** 三级审批人ID */
    @Excel(name = "三级审批人ID")
    private Long threeshenpirenid;

    /** 创建人ID */
    @Excel(name = "创建人ID")
    private Integer createid;
    
    private Date createtime;
    
    private Date updatetime;
    
    public Date getCreatetime() {
        return createtime;
    }
    
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
    
    public Date getUpdatetime() {
        return updatetime;
    }
    
    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }

    public Integer getId()
    {
        return id;
    }
    public void setShenpiname(String shenpiname)
    {
        this.shenpiname = shenpiname;
    }

    public String getShenpiname()
    {
        return shenpiname;
    }
    public void setOneshenpirenname(String oneshenpirenname)
    {
        this.oneshenpirenname = oneshenpirenname;
    }

    public String getOneshenpirenname()
    {
        return oneshenpirenname;
    }
    public void setOnshenpirenid(Long onshenpirenid)
    {
        this.onshenpirenid = onshenpirenid;
    }

    public Long getOnshenpirenid()
    {
        return onshenpirenid;
    }
    public void setTwoshenpirenname(String twoshenpirenname)
    {
        this.twoshenpirenname = twoshenpirenname;
    }

    public String getTwoshenpirenname()
    {
        return twoshenpirenname;
    }
    public void setTwoshenpirenid(Long twoshenpirenid)
    {
        this.twoshenpirenid = twoshenpirenid;
    }

    public Long getTwoshenpirenid()
    {
        return twoshenpirenid;
    }
    public void setThreeshenpirenname(String threeshenpirenname)
    {
        this.threeshenpirenname = threeshenpirenname;
    }

    public String getThreeshenpirenname()
    {
        return threeshenpirenname;
    }
    public void setThreeshenpirenid(Long threeshenpirenid)
    {
        this.threeshenpirenid = threeshenpirenid;
    }

    public Long getThreeshenpirenid()
    {
        return threeshenpirenid;
    }
    public void setCreateid(Integer createid)
    {
        this.createid = createid;
    }

    public Integer getCreateid()
    {
        return createid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("shenpiname", getShenpiname())
                .append("oneshenpirenname", getOneshenpirenname())
                .append("onshenpirenid", getOnshenpirenid())
                .append("twoshenpirenname", getTwoshenpirenname())
                .append("twoshenpirenid", getTwoshenpirenid())
                .append("threeshenpirenname", getThreeshenpirenname())
                .append("threeshenpirenid", getThreeshenpirenid())
                .append("createtime", getCreatetime())
                .append("updatetime", getUpdatetime())
                .append("createid", getCreateid())
                .toString();
    }
}
