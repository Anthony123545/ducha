package com.supervision.peopleChuRu.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 人员请假对象 bdgl_leave_one
 *
 * @author supervision
 * @date 2022-03-31
 */
public class BdglLeaveOne extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 请假人员 */
    @Excel(name = "请假人员")
    private String peopleId;

    /** 单位 */
    @Excel(name = "单位")
    private Long unitId;

    private String startTimeF;

    public String getStartTimeF() {
        return startTimeF;
    }

    public void setStartTimeF(String startTimeF) {
        this.startTimeF = startTimeF;
    }

    /** 请假开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "请假开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startTime;

    /** 请假结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "请假结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endTime;

    /** 请假类型 */
    @Excel(name = "请假类型")
    private String leaveType;

    /** 请假原因 */
    @Excel(name = "请假原因")
    private String leaveReason;

    /** 离队时间 */
    @Excel(name = "离队时间")
    private String departureTime;

    /** 归队时间 */
    @Excel(name = "归队时间")
    private String returnTime;

    /** 归队状态 */
    @Excel(name = "归队状态")
    private String returnStatus;

    /** 是否归队 */
    @Excel(name = "是否归队")
    private String isReturn;

    /** 创建人ID */
    @Excel(name = "创建人ID")
    private Long adminId;

    /** 批准人id */
    @Excel(name = "批准人id")
    private Integer peopleId1;

    /** 职务 */
    @Excel(name = "职务")
    private Integer postId;

    /** 二级审批人id */
    @Excel(name = "二级审批人id")
    private Integer peopleId2;

    /** 三级审批人id */
    @Excel(name = "三级审批人id")
    private Integer peopleId3;

    /** 四级审批人id */
    @Excel(name = "四级审批人id")
    private Integer peopleId4;

    /** $column.columnComment */
    @Excel(name = "四级审批人id")
    private Integer status1;

    /** $column.columnComment */
    @Excel(name = "四级审批人id")
    private Integer status2;

    /** $column.columnComment */
    @Excel(name = "四级审批人id")
    private Integer status3;

    /** $column.columnComment */
    @Excel(name = "四级审批人id")
    private Integer status4;

    /** 总审批状态 */
    @Excel(name = "总审批状态")
    private String contents;

    /** $column.columnComment */
    @Excel(name = "总审批状态")
    private Integer statusd;

    /** 归队状态 */
    @Excel(name = "归队状态")
    private Integer rejoin;

    /** 单位名称 */
    @Excel(name = "单位名称")
    private String unitName;

    /** 请假人姓名 */
    @Excel(name = "请假人姓名")
    private String peopleName;

    /** 一级审批人姓名 */
    @Excel(name = "一级审批人姓名")
    private String peopleName1;

    /** 二级审批人姓名 */
    @Excel(name = "二级审批人姓名")
    private String peopleName2;

    /** 三级审批人姓名 */
    @Excel(name = "三级审批人姓名")
    private String peopleName3;

    /** 四级审批人姓名 */
    @Excel(name = "四级审批人姓名")
    private String peopleName4;

    /** 驳回原因 */
    @Excel(name = "驳回原因")
    private String reasons;

    /** 特殊审批原因 */
    @Excel(name = "特殊审批原因")
    private String specialApproval;

    /** 实际归队时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "实际归队时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date actualreturntime;

    /** 批准部门 */
    @Excel(name = "批准部门")
    private String approvalDepartment;

    private String postName;//职务名称
    
    private String name;//人员名称
    private String[] names;//人员名称
    
    public String[] getNames() {
        return names;
    }
    
    public void setNames(String[] names) {
        this.names = names;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public String getPeopleId() {
        return peopleId;
    }

    public void setPeopleId(String peopleId) {
        this.peopleId = peopleId;
    }

    public void setUnitId(Long unitId)
    {
        this.unitId = unitId;
    }

    public Long getUnitId()
    {
        return unitId;
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
    public void setLeaveType(String leaveType)
    {
        this.leaveType = leaveType;
    }

    public String getLeaveType()
    {
        return leaveType;
    }
    public void setLeaveReason(String leaveReason)
    {
        this.leaveReason = leaveReason;
    }

    public String getLeaveReason()
    {
        return leaveReason;
    }
    public void setDepartureTime(String departureTime)
    {
        this.departureTime = departureTime;
    }

    public String getDepartureTime()
    {
        return departureTime;
    }
    public void setReturnTime(String returnTime)
    {
        this.returnTime = returnTime;
    }

    public String getReturnTime()
    {
        return returnTime;
    }
    public void setReturnStatus(String returnStatus)
    {
        this.returnStatus = returnStatus;
    }

    public String getReturnStatus()
    {
        return returnStatus;
    }
    public void setIsReturn(String isReturn)
    {
        this.isReturn = isReturn;
    }

    public String getIsReturn()
    {
        return isReturn;
    }
    public void setAdminId(Long adminId)
    {
        this.adminId = adminId;
    }

    public Long getAdminId()
    {
        return adminId;
    }
    public void setPeopleId1(Integer peopleId1)
    {
        this.peopleId1 = peopleId1;
    }

    public Integer getPeopleId1()
    {
        return peopleId1;
    }
    public void setPostId(Integer postId)
    {
        this.postId = postId;
    }

    public Integer getPostId()
    {
        return postId;
    }
    public void setPeopleId2(Integer peopleId2)
    {
        this.peopleId2 = peopleId2;
    }

    public Integer getPeopleId2()
    {
        return peopleId2;
    }
    public void setPeopleId3(Integer peopleId3)
    {
        this.peopleId3 = peopleId3;
    }

    public Integer getPeopleId3()
    {
        return peopleId3;
    }
    public void setPeopleId4(Integer peopleId4)
    {
        this.peopleId4 = peopleId4;
    }

    public Integer getPeopleId4()
    {
        return peopleId4;
    }
    public void setStatus1(Integer status1)
    {
        this.status1 = status1;
    }

    public Integer getStatus1()
    {
        return status1;
    }
    public void setStatus2(Integer status2)
    {
        this.status2 = status2;
    }

    public Integer getStatus2()
    {
        return status2;
    }
    public void setStatus3(Integer status3)
    {
        this.status3 = status3;
    }

    public Integer getStatus3()
    {
        return status3;
    }
    public void setStatus4(Integer status4)
    {
        this.status4 = status4;
    }

    public Integer getStatus4()
    {
        return status4;
    }
    public void setContents(String contents)
    {
        this.contents = contents;
    }

    public String getContents()
    {
        return contents;
    }
    public void setStatusd(Integer statusd)
    {
        this.statusd = statusd;
    }

    public Integer getStatusd()
    {
        return statusd;
    }
    public void setRejoin(Integer rejoin)
    {
        this.rejoin = rejoin;
    }

    public Integer getRejoin()
    {
        return rejoin;
    }
    public void setUnitName(String unitName)
    {
        this.unitName = unitName;
    }

    public String getUnitName()
    {
        return unitName;
    }
    public void setPeopleName(String peopleName)
    {
        this.peopleName = peopleName;
    }

    public String getPeopleName()
    {
        return peopleName;
    }
    public void setPeopleName1(String peopleName1)
    {
        this.peopleName1 = peopleName1;
    }

    public String getPeopleName1()
    {
        return peopleName1;
    }
    public void setPeopleName2(String peopleName2)
    {
        this.peopleName2 = peopleName2;
    }

    public String getPeopleName2()
    {
        return peopleName2;
    }
    public void setPeopleName3(String peopleName3)
    {
        this.peopleName3 = peopleName3;
    }

    public String getPeopleName3()
    {
        return peopleName3;
    }
    public void setPeopleName4(String peopleName4)
    {
        this.peopleName4 = peopleName4;
    }

    public String getPeopleName4()
    {
        return peopleName4;
    }
    public void setReasons(String reasons)
    {
        this.reasons = reasons;
    }

    public String getReasons()
    {
        return reasons;
    }
    public void setSpecialApproval(String specialApproval)
    {
        this.specialApproval = specialApproval;
    }

    public String getSpecialApproval()
    {
        return specialApproval;
    }
    public void setActualreturntime(Date actualreturntime)
    {
        this.actualreturntime = actualreturntime;
    }

    public Date getActualreturntime()
    {
        return actualreturntime;
    }

    public String getApprovalDepartment() {
        return approvalDepartment;
    }

    public void setApprovalDepartment(String approvalDepartment) {
        this.approvalDepartment = approvalDepartment;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("peopleId", getPeopleId())
            .append("unitId", getUnitId())
            .append("startTime", getStartTime())
            .append("endTime", getEndTime())
            .append("leaveType", getLeaveType())
            .append("leaveReason", getLeaveReason())
            .append("departureTime", getDepartureTime())
            .append("returnTime", getReturnTime())
            .append("returnStatus", getReturnStatus())
            .append("isReturn", getIsReturn())
            .append("updatetime", getUpdatetime())
            .append("adminId", getAdminId())
            .append("createtime", getCreatetime())
            .append("peopleId1", getPeopleId1())
            .append("postId", getPostId())
            .append("peopleId2", getPeopleId2())
            .append("peopleId3", getPeopleId3())
            .append("peopleId4", getPeopleId4())
            .append("status1", getStatus1())
            .append("status2", getStatus2())
            .append("status3", getStatus3())
            .append("status4", getStatus4())
            .append("contents", getContents())
            .append("statusd", getStatusd())
            .append("rejoin", getRejoin())
            .append("unitName", getUnitName())
            .append("peopleName", getPeopleName())
            .append("peopleName1", getPeopleName1())
            .append("peopleName2", getPeopleName2())
            .append("peopleName3", getPeopleName3())
            .append("peopleName4", getPeopleName4())
            .append("reasons", getReasons())
            .append("specialApproval", getSpecialApproval())
            .append("actualreturntime", getActualreturntime())
            .toString();
    }
}
