package com.supervision.peopleManage.service.impl;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.peopleManage.mapper.VisitorMapper;
import com.supervision.peopleManage.domain.Visitor;
import com.supervision.peopleManage.service.IVisitorService;

/**
 * 访客管理Service业务层处理
 * 
 * @author supervision
 * @date 2025-11-19
 */
@Service
public class VisitorServiceImpl implements IVisitorService 
{
    @Autowired
    private VisitorMapper visitorMapper;

    /**
     * 查询访客管理
     * 
     * @param id 访客管理ID
     * @return 访客管理
     */
    @Override
    public Visitor selectVisitorById(Long id)
    {
        return visitorMapper.selectVisitorById(id);
    }

    /**
     * 查询访客管理列表
     * 
     * @param visitor 访客管理
     * @return 访客管理
     */
    @Override
    public List<Visitor> selectVisitorList(Visitor visitor)
    {
        return visitorMapper.selectVisitorList(visitor);
    }

    /**
     * 新增访客管理
     * 
     * @param visitor 访客管理
     * @return 结果
     */
    @Override
    public int insertVisitor(Visitor visitor)
    {
        return visitorMapper.insertVisitor(visitor);
    }

    /**
     * 修改访客管理
     * 
     * @param visitor 访客管理
     * @return 结果
     */
    @Override
    public int updateVisitor(Visitor visitor)
    {
        return visitorMapper.updateVisitor(visitor);
    }

    /**
     * 批量删除访客管理
     * 
     * @param ids 需要删除的访客管理ID
     * @return 结果
     */
    @Override
    public int deleteVisitorByIds(Long[] ids)
    {
        return visitorMapper.deleteVisitorByIds(Arrays.asList(ids));
    }

    /**
     * 删除访客管理信息
     * 
     * @param id 访客管理ID
     * @return 结果
     */
    @Override
    public int deleteVisitorById(Long id)
    {
        return visitorMapper.deleteVisitorByIds(Arrays.asList(new Long[]{id}));
    }

    @Override
    public Visitor selectVisitorByEmployeeNumber(String employeeNumber) {
        return  visitorMapper.selectVisitorByEmployeeNumber(employeeNumber);
    }
}