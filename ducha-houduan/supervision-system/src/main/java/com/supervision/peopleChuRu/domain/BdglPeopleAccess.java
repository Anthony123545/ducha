package com.supervision.peopleChuRu.domain;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 人员出入记录对象 bdgl_people_access
 * 
 * @author supervision
 * @date 2023-07-01
 */
@Data
public class BdglPeopleAccess extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户ID */
    private Long userId;

    /** 工号 */
    @Excel(name = "工号")
    private String employeeNumber;

    /** 人员类型(员工、访客) */
    @Excel(name = "人员类型(员工、访客)")
    private String peopleType;

    /** 用户名 */
    @Excel(name = "用户名")
    private String userName;

    /** 部门ID */
    @Excel(name = "部门ID")
    private Long deptId;

    /** 部门名称 */
    @Excel(name = "部门名称")
    private String deptName;

    /** 出入类型(入营、出营) */
    @Excel(name = "出入类型(入营、出营)")
    private String action;

    /** 时间戳 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "时间戳", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }
    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getUserName()
    {
        return userName;
    }
    public void setDeptId(Long deptId)
    {
        this.deptId = deptId;
    }

    public Long getDeptId()
    {
        return deptId;
    }
    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }

    public String getDeptName()
    {
        return deptName;
    }
    public void setAction(String action)
    {
        this.action = action;
    }

    public String getAction()
    {
        return action;
    }
    public void setTime(LocalDateTime time)
    {
        this.time = time;
    }

    public LocalDateTime getTime()
    {
        return time;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
            .append("userName", getUserName())
            .append("employeeNumber", getEmployeeNumber())
            .append("peopleType", getPeopleType())
            .append("deptId", getDeptId())
            .append("deptName", getDeptName())
            .append("action", getAction())
            .append("time", getTime())
            .toString();
    }
}