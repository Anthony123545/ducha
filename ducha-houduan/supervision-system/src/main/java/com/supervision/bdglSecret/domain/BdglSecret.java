package com.supervision.bdglSecret.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 保密设备外借记录对象 bdgl_secret
 *
 * @author supervision
 * @date 2022-03-25
 */
@Data
public class BdglSecret extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Integer id;

    private String status;

    /** 分类 */
    @Excel(name = "分类")
    private Integer secrecyType;

    /** 编号 */
    @Excel(name = "编号")
    private String number;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 种类 */
    @Excel(name = "种类")
    private String kind;

    /** 型号 */
    @Excel(name = "型号")
    private String version;

    /** 密级 */
    @Excel(name = "密级")
    private String confidential;

    /** 责任人id */
    @Excel(name = "责任人id")
    private Integer peopleId;

    /** 使用人id */
    @Excel(name = "使用人id")
    private Integer peopleId1;

    /** 批准人id */
    @Excel(name = "批准人id")
    private Integer peopleId2;

    /** 起用时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "起用时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 停用时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "停用时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 移交时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "移交时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date hindOverTime;

    /** 单位 */
    @Excel(name = "单位")
    private Integer unitId;

    /** 责任人姓名 */
    @Excel(name = "责任人姓名")
    private String peopleName;

    /** 使用人姓名 */
    @Excel(name = "使用人姓名")
    private String people1Name;

    /** 批准人姓名 */
    @Excel(name = "批准人姓名")
    private String people2Name;

    /** 部门名称 */
    @Excel(name = "部门名称")
    private String deptName;

    private String remarks;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("secrecyType", getSecrecyType())
            .append("number", getNumber())
            .append("name", getName())
            .append("kind", getKind())
            .append("version", getVersion())
            .append("confidential", getConfidential())
            .append("peopleId", getPeopleId())
            .append("peopleId1", getPeopleId1())
            .append("peopleId2", getPeopleId2())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("hindOverTime", getHindOverTime())
            .append("unitId", getUnitId())
            .append("peopleName", getPeopleName())
            .append("people1Name", getPeople1Name())
            .append("people2Name", getPeople2Name())
            .append("deptName", getDeptName())
            .append("status", getStatus())
            .toString();
    }

}
