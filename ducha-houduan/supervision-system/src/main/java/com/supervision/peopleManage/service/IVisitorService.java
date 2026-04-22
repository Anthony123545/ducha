package com.supervision.peopleManage.service;

import java.util.List;
import com.supervision.peopleManage.domain.Visitor;

/**
 * 访客管理Service接口
 * 
 * @author supervision
 * @date 2025-11-19
 */
public interface IVisitorService 
{
    /**
     * 查询访客管理
     * 
     * @param id 访客管理ID
     * @return 访客管理
     */
    public Visitor selectVisitorById(Long id);

    /**
     * 查询访客管理列表
     * 
     * @param visitor 访客管理
     * @return 访客管理集合
     */
    public List<Visitor> selectVisitorList(Visitor visitor);

    /**
     * 新增访客管理
     * 
     * @param visitor 访客管理
     * @return 结果
     */
    public int insertVisitor(Visitor visitor);

    /**
     * 修改访客管理
     * 
     * @param visitor 访客管理
     * @return 结果
     */
    public int updateVisitor(Visitor visitor);

    /**
     * 批量删除访客管理
     * 
     * @param ids 需要删除的访客管理ID
     * @return 结果
     */
    public int deleteVisitorByIds(Long[] ids);

    /**
     * 删除访客管理信息
     * 
     * @param id 访客管理ID
     * @return 结果
     */
    public int deleteVisitorById(Long id);

    public Visitor selectVisitorByEmployeeNumber(String employeeNumber);
}