package com.supervision.peopleManage.mapper;

import com.supervision.peopleManage.domain.Visitor;
import java.util.List;

/**
 * 访客管理Mapper接口
 * 
 * @author supervision
 * @date 2025-11-19
 */
public interface VisitorMapper {
    /**
     * 查询访客列表
     *
     * @param visitor 访客信息
     * @return 访客列表
     */
    List<Visitor> selectVisitorList(Visitor visitor);

    /**
     * 根据ID查询访客信息
     *
     * @param id 访客ID
     * @return 访客信息
     */
    Visitor selectVisitorById(Long id);

    /**
     * 新增访客信息
     *
     * @param visitor 访客信息
     * @return 结果
     */
    int insertVisitor(Visitor visitor);

    /**
     * 修改访客信息
     *
     * @param visitor 访客信息
     * @return 结果
     */
    int updateVisitor(Visitor visitor);

    /**
     * 删除访客信息
     *
     * @param ids 需要删除的访客ID列表
     * @return 结果
     */
    int deleteVisitorByIds(List<Long> ids);

    Visitor selectVisitorByEmployeeNumber(String employeeNumber);
}