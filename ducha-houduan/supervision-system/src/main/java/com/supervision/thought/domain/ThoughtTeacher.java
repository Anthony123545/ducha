package com.supervision.thought.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 教案库设置对象 thought_teacher
 * 
 * @author supervision
 * @date 2022-04-11
 */
public class ThoughtTeacher extends BaseEntity
{



    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 目录 */
    @Excel(name = "目录")
    private String directory;

    /** 父目录ID */
    @Excel(name = "父目录ID")
    private Long parentId;

    /** 祖级列表 */
    @Excel(name = "祖级列表")
    private String ancestors;

    /** 子分类 */
    @Excel(name = "子分类")
    private Long subclassification;

    /** 文件数 */
    @Excel(name = "文件数")
    private Long documentsNumber;

    /** 创建人姓名 */
    @Excel(name = "创建人姓名")
    private String createUser;

    /** 创建人ID */
    @Excel(name = "创建人ID")
    private Long createUserid;

    /** 修改人姓名 */
    @Excel(name = "修改人姓名")
    private String updateUser;

    /** 修人姓名 */
    @Excel(name = "修人姓名")
    private Long updateUserid;

    /** 父级文件夹名称 */
    @Excel(name = "父级文件夹名称")
    private String parentDirectory;

    //目录级别
    private String level;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    private List<ThoughtTeacher> children;

    public List<ThoughtTeacher> getChildren() {
        return children;
    }

    public void setChildren(List<ThoughtTeacher> children) {
        this.children = children;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setDirectory(String directory) 
    {
        this.directory = directory;
    }

    public String getDirectory() 
    {
        return directory;
    }
    public void setParentId(Long parentId) 
    {
        this.parentId = parentId;
    }

    public Long getParentId() 
    {
        return parentId;
    }
    public void setAncestors(String ancestors) 
    {
        this.ancestors = ancestors;
    }

    public String getAncestors() 
    {
        return ancestors;
    }
    public void setSubclassification(Long subclassification) 
    {
        this.subclassification = subclassification;
    }

    public Long getSubclassification() 
    {
        return subclassification;
    }
    public void setDocumentsNumber(Long documentsNumber) 
    {
        this.documentsNumber = documentsNumber;
    }

    public Long getDocumentsNumber() 
    {
        return documentsNumber;
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
    public void setUpdateUser(String updateUser) 
    {
        this.updateUser = updateUser;
    }

    public String getUpdateUser() 
    {
        return updateUser;
    }
    public void setUpdateUserid(Long updateUserid) 
    {
        this.updateUserid = updateUserid;
    }

    public Long getUpdateUserid() 
    {
        return updateUserid;
    }
    public void setParentDirectory(String parentDirectory) 
    {
        this.parentDirectory = parentDirectory;
    }

    public String getParentDirectory() 
    {
        return parentDirectory;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("directory", getDirectory())
            .append("parentId", getParentId())
            .append("ancestors", getAncestors())
            .append("subclassification", getSubclassification())
            .append("documentsNumber", getDocumentsNumber())
            .append("createUser", getCreateUser())
            .append("createUserid", getCreateUserid())
            .append("createTime", getCreateTime())
            .append("updateUser", getUpdateUser())
            .append("updateUserid", getUpdateUserid())
            .append("updateTime", getUpdateTime())
            .append("parentDirectory", getParentDirectory())
            .toString();
    }
}
