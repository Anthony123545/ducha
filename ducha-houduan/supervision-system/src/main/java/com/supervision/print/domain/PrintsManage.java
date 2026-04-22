package com.supervision.print.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

/**
 * 集中文印主对象 prints_manage
 *
 * @author supervision
 * @date 2022-03-24
 */
public class PrintsManage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 文件名称 */
    @Excel(name = "文件名称")
    private String name;

    /** 路径 */
    @Excel(name = "路径")
    private String file;

    /** 类型 */
    @Excel(name = "类型")
    private String type;

    /** 大小 */
    @Excel(name = "大小")
    private String size;

    /** 单位ID */
    @Excel(name = "单位ID")
    private Long unitId;

    /** 单位名称 */
    @Excel(name = "单位名称")
    private String unitName;

    /** 级别（密级） */
    @Excel(name = "级别", readConverterExp = "密=级")
    private Integer levelM;

    /** 发送人ID */
    @Excel(name = "发送人ID")
    private Long sendId;

    /** 发送人 */
    @Excel(name = "发送人")
    private String sendName;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date creattime;

    /** 打印说明 */
    @Excel(name = "打印说明")
    private String explainInfo;

    /** 打印状态(1 打印中  2 已打印) */
    @Excel(name = "打印状态(1 打印中  2 已打印)")
    private Integer printStatus;

    /** 接收人ID */
    @Excel(name = "接收人ID")
    private Long receiveId;

    /** 接收人 */
    @Excel(name = "接收人")
    private String receiveName;

    /** 生成路径 */
    @Excel(name = "生成路径")
    private String sFile;

    /** 打印回复信息 */
    @Excel(name = "打印回复信息")
    private String printInfo;

    /** 打印份数 */
    @Excel(name = "打印份数")
    private Long printNumber;

    /** 是否需要归还(0否1是) */
    @Excel(name = "是否需要归还(0否1是)")
    private Integer isReturn;

    /** 是否为彩打（0否1是） */
    @Excel(name = "是否为彩打", readConverterExp = "0=否1是")
    private Integer isPrintColor;

    /** 是否为双面打（0否1是） */
    @Excel(name = "是否为双面打", readConverterExp = "0=否1是")
    private Integer isPrintTwo;

    private Integer printRoom;

    private List<PrintsManage> printsManageList;

    private String remark;

    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    private Date returnTime;


    @Override
    public String getRemark() {
        return remark;
    }

    @Override
    public void setRemark(String remark) {
        this.remark = remark;
    }


    public int getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(int isdelete) {
        this.isdelete = isdelete;
    }

    private  int isdelete;

    /**
     * 是否显示确定按钮 毕波添加
     */
    private String isShow;

    public List<PrintsManage> getPrintsManageList() {
        return printsManageList;
    }

    public void setPrintsManageList(List<PrintsManage> printsManageList) {
        this.printsManageList = printsManageList;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public Integer getPrintRoom() {
        return printRoom;
    }

    public void setPrintRoom(Integer printRoom) {
        this.printRoom = printRoom;
    }
    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setFile(String file)
    {
        this.file = file;
    }

    public String getFile()
    {
        return file;
    }
    public void setType(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }
    public void setSize(String size)
    {
        this.size = size;
    }

    public String getSize()
    {
        return size;
    }
    public void setUnitId(Long unitId)
    {
        this.unitId = unitId;
    }

    public Long getUnitId()
    {
        return unitId;
    }
    public void setUnitName(String unitName)
    {
        this.unitName = unitName;
    }

    public String getUnitName()
    {
        return unitName;
    }
    public void setLevelM(Integer levelM)
    {
        this.levelM = levelM;
    }

    public Integer getLevelM()
    {
        return levelM;
    }
    public void setSendId(Long sendId)
    {
        this.sendId = sendId;
    }

    public Long getSendId()
    {
        return sendId;
    }
    public void setSendName(String sendName)
    {
        this.sendName = sendName;
    }

    public String getSendName()
    {
        return sendName;
    }
    public void setCreattime(Date creattime)
    {
        this.creattime = creattime;
    }

    public Date getCreattime()
    {
        return creattime;
    }
    public void setExplainInfo(String explainInfo)
    {
        this.explainInfo = explainInfo;
    }

    public String getExplainInfo()
    {
        return explainInfo;
    }
    public void setPrintStatus(Integer printStatus)
    {
        this.printStatus = printStatus;
    }

    public Integer getPrintStatus()
    {
        return printStatus;
    }
    public void setReceiveId(Long receiveId)
    {
        this.receiveId = receiveId;
    }

    public Long getReceiveId()
    {
        return receiveId;
    }
    public void setReceiveName(String receiveName)
    {
        this.receiveName = receiveName;
    }

    public String getReceiveName()
    {
        return receiveName;
    }
    public void setsFile(String sFile)
    {
        this.sFile = sFile;
    }

    public String getsFile()
    {
        return sFile;
    }
    public void setPrintInfo(String printInfo)
    {
        this.printInfo = printInfo;
    }

    public String getPrintInfo()
    {
        return printInfo;
    }
    public void setPrintNumber(Long printNumber)
    {
        this.printNumber = printNumber;
    }

    public Long getPrintNumber()
    {
        return printNumber;
    }
    public void setIsReturn(Integer isReturn)
    {
        this.isReturn = isReturn;
    }

    public Integer getIsReturn()
    {
        return isReturn;
    }
    public void setIsPrintColor(Integer isPrintColor)
    {
        this.isPrintColor = isPrintColor;
    }

    public Integer getIsPrintColor()
    {
        return isPrintColor;
    }
    public void setIsPrintTwo(Integer isPrintTwo)
    {
        this.isPrintTwo = isPrintTwo;
    }

    public Integer getIsPrintTwo()
    {
        return isPrintTwo;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("file", getFile())
            .append("type", getType())
            .append("size", getSize())
            .append("unitId", getUnitId())
            .append("unitName", getUnitName())
            .append("levelM", getLevelM())
            .append("sendId", getSendId())
            .append("sendName", getSendName())
            .append("creattime", getCreattime())
            .append("updatetime", getUpdatetime())
            .append("explainInfo", getExplainInfo())
            .append("printStatus", getPrintStatus())
            .append("receiveId", getReceiveId())
            .append("receiveName", getReceiveName())
            .append("sFile", getsFile())
            .append("printInfo", getPrintInfo())
            .append("printNumber", getPrintNumber())
            .append("isReturn", getIsReturn())
            .append("isPrintColor", getIsPrintColor())
            .append("isPrintTwo", getIsPrintTwo())
                .append("printRoom",getPrintRoom())
            .toString();
    }
}
