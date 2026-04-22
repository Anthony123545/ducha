package com.supervision.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 态势军事训练月统计情况对象 military_training
 *
 * @author ruoyi
 * @date 2022-05-21
 */
public class MilitaryTraining extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long id;

    /** 合成训练 */
    @Excel(name = "合成训练")
    private String synthesis;

    /** 专业训练 */
    @Excel(name = "专业训练")
    private String professional;

    /** 共同训练 */
    @Excel(name = "共同训练")
    private String common;

    /** 考核人次 */
    @Excel(name = "考核人次")
    private Long peopleNumber;

    /** 优良人数 */
    @Excel(name = "优良人数")
    private Long youliang;

    /** 及格人数 */
    @Excel(name = "及格人数")
    private Long jige;

    /** 未及格人数 */
    @Excel(name = "未及格人数")
    private Long weijige;

    /** 实有人数 */
    @Excel(name = "实有人数")
    private Long shiyou;

    /** 参训人数 */
    @Excel(name = "参训人数")
    private Long canxun;

    /** 未参训人数 */
    @Excel(name = "未参训人数")
    private Long weicanxun;

    /** canxunlv */
    @Excel(name = "canxunlv")
    private String canxunlv;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setSynthesis(String synthesis)
    {
        this.synthesis = synthesis;
    }

    public String getSynthesis()
    {
        return synthesis;
    }
    public void setProfessional(String professional)
    {
        this.professional = professional;
    }

    public String getProfessional()
    {
        return professional;
    }
    public void setCommon(String common)
    {
        this.common = common;
    }

    public String getCommon()
    {
        return common;
    }
    public void setPeopleNumber(Long peopleNumber)
    {
        this.peopleNumber = peopleNumber;
    }

    public Long getPeopleNumber()
    {
        return peopleNumber;
    }
    public void setYouliang(Long youliang)
    {
        this.youliang = youliang;
    }

    public Long getYouliang()
    {
        return youliang;
    }
    public void setJige(Long jige)
    {
        this.jige = jige;
    }

    public Long getJige()
    {
        return jige;
    }
    public void setWeijige(Long weijige)
    {
        this.weijige = weijige;
    }

    public Long getWeijige()
    {
        return weijige;
    }
    public void setShiyou(Long shiyou)
    {
        this.shiyou = shiyou;
    }

    public Long getShiyou()
    {
        return shiyou;
    }
    public void setCanxun(Long canxun)
    {
        this.canxun = canxun;
    }

    public Long getCanxun()
    {
        return canxun;
    }
    public void setWeicanxun(Long weicanxun)
    {
        this.weicanxun = weicanxun;
    }

    public Long getWeicanxun()
    {
        return weicanxun;
    }
    public void setCanxunlv(String canxunlv)
    {
        this.canxunlv = canxunlv;
    }

    public String getCanxunlv()
    {
        return canxunlv;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("synthesis", getSynthesis())
                .append("professional", getProfessional())
                .append("common", getCommon())
                .append("peopleNumber", getPeopleNumber())
                .append("youliang", getYouliang())
                .append("jige", getJige())
                .append("weijige", getWeijige())
                .append("createtime", getCreatetime())
                .append("shiyou", getShiyou())
                .append("canxun", getCanxun())
                .append("weicanxun", getWeicanxun())
                .append("canxunlv", getCanxunlv())
                .toString();
    }
}