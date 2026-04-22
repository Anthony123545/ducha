package com.supervision.zbqk.domain;

import com.supervision.regulations.domain.BdglFileInfo;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 战备情况对象 bdgl_zbqk_file_info
 * 
 * @author supervision
 * @date 2022-11-16
 */
public class BdglZbqkFileInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 文件名称 */
    @Excel(name = "文件名称")
    private String fileName;

    /** 阅读量 */
    @Excel(name = "阅读量")
    private Long readVolume;

    /** 页数 */
    @Excel(name = "页数")
    private Long theNumberPages;

    /** 上传人 */
    @Excel(name = "上传人")
    private String createUser;

    /** 上传人ID */
    @Excel(name = "上传人ID")
    private Long createUserid;

    /** 目录id */
    @Excel(name = "目录id")
    private Long directoryId;

    /** 目录名称 */
    @Excel(name = "目录名称")
    private String directoryName;

    /** 文件后缀 */
    @Excel(name = "文件后缀")
    private String parentDirId;

    /** 文件位置 */
    @Excel(name = "文件位置")
    private String parentDirName;

    /** 文件大小 */
    @Excel(name = "文件大小")
    private String fileSize;
    private String common;
    private List<BdglZbqkFileInfo> bdglZbqkFileInfo;

    public String getCommon() {
        return common;
    }

    public void setCommon(String common) {
        this.common = common;
    }

    public List<BdglZbqkFileInfo> getBdglZbqkFileInfo() {
        return bdglZbqkFileInfo;
    }

    public void setBdglZbqkFileInfo(List<BdglZbqkFileInfo> bdglZbqkFileInfo) {
        this.bdglZbqkFileInfo = bdglZbqkFileInfo;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setFileName(String fileName) 
    {
        this.fileName = fileName;
    }

    public String getFileName() 
    {
        return fileName;
    }
    public void setReadVolume(Long readVolume) 
    {
        this.readVolume = readVolume;
    }

    public Long getReadVolume() 
    {
        return readVolume;
    }
    public void setTheNumberPages(Long theNumberPages) 
    {
        this.theNumberPages = theNumberPages;
    }

    public Long getTheNumberPages() 
    {
        return theNumberPages;
    }
    public void setCreateUser(String createUser) 
    {
        this.createUser = createUser;
    }

    public String getCreateUser() 
    {
        return createUser;
    }
    public void setCreateUserid(Long createUserid) 
    {
        this.createUserid = createUserid;
    }

    public Long getCreateUserid() 
    {
        return createUserid;
    }
    public void setDirectoryId(Long directoryId) 
    {
        this.directoryId = directoryId;
    }

    public Long getDirectoryId() 
    {
        return directoryId;
    }
    public void setDirectoryName(String directoryName) 
    {
        this.directoryName = directoryName;
    }

    public String getDirectoryName() 
    {
        return directoryName;
    }
    public void setParentDirId(String parentDirId) 
    {
        this.parentDirId = parentDirId;
    }

    public String getParentDirId() 
    {
        return parentDirId;
    }
    public void setParentDirName(String parentDirName) 
    {
        this.parentDirName = parentDirName;
    }

    public String getParentDirName() 
    {
        return parentDirName;
    }
    public void setFileSize(String fileSize) 
    {
        this.fileSize = fileSize;
    }

    public String getFileSize() 
    {
        return fileSize;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("fileName", getFileName())
            .append("readVolume", getReadVolume())
            .append("theNumberPages", getTheNumberPages())
            .append("createUser", getCreateUser())
            .append("createUserid", getCreateUserid())
            .append("createTime", getCreateTime())
            .append("directoryId", getDirectoryId())
            .append("directoryName", getDirectoryName())
            .append("parentDirId", getParentDirId())
            .append("parentDirName", getParentDirName())
            .append("fileSize", getFileSize())
            .toString();
    }
}
