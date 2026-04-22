package com.supervision.grassrootsregistration.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 考勤记录对象 bdgl_attendance_record
 *
 * @author ruoyi
 * @date 2022-10-20
 */
public class BdglAttendanceRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** sid */
//    @Excel(name = "sid")
    private Long sid;

    /** 人员姓名 */
    @Excel(name = "人员姓名")
    private String ename;

    /** 部门名称 */
    @Excel(name = "部门名称")
    private String deptname;

    /** 设备名称 */
    @Excel(name = "设备名称")
    private String alias;

    @Excel(name = "验证方式")
    private String verifyString;

    /** 打卡时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "打卡时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date checktime;

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    private Date starttime;

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    private Date endtime;


    /** 体温 */
    @Excel(name = "体温")
    private String temperature;

    /** 是否佩戴口罩. （0:否，1:是） */
    @Excel(name = "是否佩戴口罩. ", readConverterExp = "0=否,1=是")
    private Integer maskFlag;

    /** 考勤状态说明 */
    @Excel(name = "考勤状态说明")
    private String state;

    /** 照片信息（数据为BASE64格式） */
    @Excel(name = "照片信息")
    private String photograph;

    /** 设备序列号 */
//    @Excel(name = "设备序列号")
    private String sn;

    /** 验证方式 */
    private Integer verify;

    /** 人员编号 */
//    @Excel(name = "人员编号")
    private String pin;

    /** 部门编号 */
//    @Excel(name = "部门编号")
    private String deptnumber;

    /** 考勤状态 */
//    @Excel(name = "考勤状态")
    private Integer stateno;


    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setSid(Long sid)
    {
        this.sid = sid;
    }

    public Long getSid()
    {
        return sid;
    }
    public void setVerify(Integer verify)
    {
        this.verify = verify;
    }

    public Integer getVerify()
    {
        return verify;
    }
    public void setChecktime(Date checktime)
    {
        this.checktime = checktime;
    }

    public Date getChecktime()
    {
        return checktime;
    }
    public void setSn(String sn)
    {
        this.sn = sn;
    }

    public String getSn()
    {
        return sn;
    }
    public void setAlias(String alias)
    {
        this.alias = alias;
    }

    public String getAlias()
    {
        return alias;
    }
    public void setPin(String pin)
    {
        this.pin = pin;
    }

    public String getPin()
    {
        return pin;
    }
    public void setEname(String ename)
    {
        this.ename = ename;
    }

    public String getEname()
    {
        return ename;
    }
    public void setDeptnumber(String deptnumber)
    {
        this.deptnumber = deptnumber;
    }

    public String getDeptnumber()
    {
        return deptnumber;
    }
    public void setDeptname(String deptname)
    {
        this.deptname = deptname;
    }

    public String getDeptname()
    {
        return deptname;
    }
    public void setTemperature(String temperature)
    {
        this.temperature = temperature;
    }

    public String getTemperature()
    {
        return temperature;
    }
    public void setMaskFlag(Integer maskFlag)
    {
        this.maskFlag = maskFlag;
    }

    public Integer getMaskFlag()
    {
        return maskFlag;
    }
    public void setStateno(Integer stateno)
    {
        this.stateno = stateno;
    }

    public Integer getStateno()
    {
        return stateno;
    }
    public void setState(String state)
    {
        this.state = state;
    }

    public String getState()
    {
        return state;
    }
    public void setPhotograph(String photograph)
    {
        this.photograph = photograph;
    }

    public String getPhotograph()
    {
        return photograph;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("sid", getSid())
            .append("verify", getVerify())
            .append("checktime", getChecktime())
            .append("sn", getSn())
            .append("alias", getAlias())
            .append("pin", getPin())
            .append("ename", getEname())
            .append("deptnumber", getDeptnumber())
            .append("deptname", getDeptname())
            .append("temperature", getTemperature())
            .append("maskFlag", getMaskFlag())
            .append("stateno", getStateno())
            .append("state", getState())
            .append("photograph", getPhotograph())
            .append("createTime", getCreateTime())
            .toString();
    }

    public enum VerifyEnum{
        AA(0,"指纹或密码或卡"),
        AB(1,"指纹"),
        AC(2,"考勤号"),
        AD(3,"密码"),
        AE(4,"卡"),
        AF(5,"指纹或密码"),
        AG(6,"指纹或卡"),
        AH(7,"密码或卡"),
        AI(8,"考勤号和指纹"),
        AJ(9,"指纹和密码"),
        AK(10,"指纹和密码"),
        AL(11,"密码和卡"),
        AM(12,"指纹和密码和卡"),
        AN(13,"考勤号和指纹和密码"),
        AO(14,"指纹和卡和考勤号"),
        AP(15,"面部"),
        AQ(16,"面部加指纹"),
        AR(17,"面部加密码"),
        AS(18,"面部加卡"),
        AT(19,"面部加指纹加卡"),
        AU(20,"面部加指纹加密码");

        private Integer code;
        private String name;

        VerifyEnum(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public Integer getCode() {
            return code;
        }

        public String getName() {
            return name;
        }
    }

    private List<Long> sidList;

    private String startTime;

    private String endTime;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public List<Long> getSidList() {
        return sidList;
    }

    public void setSidList(List<Long> sidList) {
        this.sidList = sidList;
    }

    public void setVerifyString() {
        if (this.verify==null){
            return;
        }
        for (VerifyEnum value : VerifyEnum.values()) {
            if (this.verify==value.getCode().intValue()){
                this.verifyString=value.getName();
                break;
            }
        }
    }

    private Integer ipAddressId;

    @Excel(name = "识别码",targetAttr = "name",type = Excel.Type.EXPORT)
    private BdglIpaddressConfig bdglIpaddressConfig;

    public String getVerifyString() {
        return verifyString;
    }

    public void setVerifyString(String verifyString) {
        this.verifyString = verifyString;
    }

    public Integer getIpAddressId() {
        return ipAddressId;
    }

    public void setIpAddressId(Integer ipAddressId) {
        this.ipAddressId = ipAddressId;
    }

    public BdglIpaddressConfig getBdglIpaddressConfig() {
        return bdglIpaddressConfig;
    }

    public void setBdglIpaddressConfig(BdglIpaddressConfig bdglIpaddressConfig) {
        this.bdglIpaddressConfig = bdglIpaddressConfig;
    }
}
