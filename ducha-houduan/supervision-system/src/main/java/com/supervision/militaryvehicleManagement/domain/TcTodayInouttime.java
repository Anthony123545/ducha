package com.supervision.militaryvehicleManagement.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 车辆外接数据对象 tc_today_inouttime
 * 
 * @author ruoyi
 * @date 2022-05-22
 */
public class TcTodayInouttime extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long pKey;

    /** 车牌号 */
    @Excel(name = "车牌号")
    private String carno;

    /** 车辆所属人 */
    @Excel(name = "车辆所属人")
    private String cName;

    /** 进出类型 */
    @Excel(name = "进出类型")
    private String jclx;

    /** 进入时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "进入时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date inTime;

    /** 出去时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出去时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date outTime;

    /** 车辆类型 */
    @Excel(name = "车辆类型")
    private String sflx;

    /** 开闸时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开闸时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date operdate;

    public void setpKey(Long pKey) 
    {
        this.pKey = pKey;
    }

    public Long getpKey() 
    {
        return pKey;
    }
    public void setCarno(String carno) 
    {
        this.carno = carno;
    }

    public String getCarno() 
    {
        return carno;
    }
    public void setcName(String cName) 
    {
        this.cName = cName;
    }

    public String getcName() 
    {
        return cName;
    }
    public void setJclx(String jclx) 
    {
        this.jclx = jclx;
    }

    public String getJclx() 
    {
        return jclx;
    }
    public void setInTime(Date inTime) 
    {
        this.inTime = inTime;
    }

    public Date getInTime() 
    {
        return inTime;
    }
    public void setOutTime(Date outTime) 
    {
        this.outTime = outTime;
    }

    public Date getOutTime() 
    {
        return outTime;
    }
    public void setSflx(String sflx) 
    {
        this.sflx = sflx;
    }

    public String getSflx() 
    {
        return sflx;
    }
    public void setOperdate(Date operdate) 
    {
        this.operdate = operdate;
    }

    public Date getOperdate() 
    {
        return operdate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("pKey", getpKey())
            .append("carno", getCarno())
            .append("cName", getcName())
            .append("jclx", getJclx())
            .append("inTime", getInTime())
            .append("outTime", getOutTime())
            .append("sflx", getSflx())
            .append("operdate", getOperdate())
            .toString();
    }
}