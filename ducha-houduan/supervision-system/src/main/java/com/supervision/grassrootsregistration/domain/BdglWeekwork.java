package com.supervision.grassrootsregistration.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;
import com.supervision.grassrootsregistration.domain.BdglWeekworkRegister;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;
import java.util.List;

/**
 * 一周工作安排对象 bdgl_weekwork
 *
 * @author supervision
 * @date 2022-02-28
 */
@Data
public class BdglWeekwork extends BaseEntity
{
    private Integer pageNum;

    private Integer pageSize;

    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    /** 工作标题 */
    @Excel(name = "工作标题")
    private String name;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 连值班领导 */
    @Excel(name = "连值班领导")
    private Integer peopleId;

    /** 电话 */
    @Excel(name = "电话")
    private String phone;

    /** 军事长官 */
    @Excel(name = "军事长官")
    private Long peopleId1;

    /** 政治长官 */
    @Excel(name = "政治长官")
    private Long peopleId2;

    /** 时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date time;

    /** 单位 */
    @Excel(name = "单位")
    private Integer unitId;

    /** 填写人 */
    @Excel(name = "填写人")
    private Integer adminId;
    /** 连值班领导姓名 */
    @Excel(name = "连值班领导姓名")
    private String peopleName;

    /** 军事长官姓名 */
    @Excel(name = "军事长官姓名")
    private String people1Name;

    /** 政治长官姓名 */
    @Excel(name = "政治长官姓名")
    private String people2Name;

    /** 单位名称 */
    @Excel(name = "单位名称")
    private String unitName;

    /** 填写人姓名 */
    @Excel(name = "填写人姓名")
    private String adminName;

    private String organizer;

    private String date;

    private String date1;

    private String date2;

    /** 一周安排工作分信息 */
    private List<BdglWeekworkRegister> bdglWeekworkRegisterList;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("startTime", getStartTime())
                .append("endTime", getEndTime())
                .append("peopleId", getPeopleId())
                .append("phone", getPhone())
                .append("peopleId1", getPeopleId1())
                .append("peopleId2", getPeopleId2())
                .append("time", getTime())
                .append("remark", getRemark())
                .append("unitId", getUnitId())
                .append("adminId", getAdminId())
                .append("bdglWeekworkRegisterList", getBdglWeekworkRegisterList())
                .toString();
    }
}
