package com.supervision.barracksManagement.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.barracksManagement.mapper.TemporaryCheckinMapper;
import com.supervision.barracksManagement.domain.TemporaryCheckin;
import com.supervision.barracksManagement.service.ITemporaryCheckinService;

/**
 * 临时来队Service业务层处理
 * 
 * @author supervision
 * @date 2022-03-07
 */
@Service
public class TemporaryCheckinServiceImpl implements ITemporaryCheckinService 
{
    @Autowired
    private TemporaryCheckinMapper temporaryCheckinMapper;

    /**
     * 查询临时来队
     * 
     * @param id 临时来队主键
     * @return 临时来队
     */
    @Override
    public TemporaryCheckin selectTemporaryCheckinById(Long id)
    {
        return temporaryCheckinMapper.selectTemporaryCheckinById(id);
    }

    /**
     * 查询临时来队列表
     * 
     * @param temporaryCheckin 临时来队
     * @return 临时来队
     */
    @Override
    public List<TemporaryCheckin> selectTemporaryCheckinList(TemporaryCheckin temporaryCheckin)
    {
        return temporaryCheckinMapper.selectTemporaryCheckinList(temporaryCheckin);
    }

    /**
     * 新增临时来队
     * 
     * @param temporaryCheckin 临时来队
     * @return 结果
     */
    @Override
    public int insertTemporaryCheckin(TemporaryCheckin temporaryCheckin)
    {
        return temporaryCheckinMapper.insertTemporaryCheckin(temporaryCheckin);
    }

    /**
     * 修改临时来队
     * 
     * @param temporaryCheckin 临时来队
     * @return 结果
     */
    @Override
    public int updateTemporaryCheckin(TemporaryCheckin temporaryCheckin)
    {
        return temporaryCheckinMapper.updateTemporaryCheckin(temporaryCheckin);
    }

    /**
     * 批量删除临时来队
     * 
     * @param ids 需要删除的临时来队主键
     * @return 结果
     */
    @Override
    public int deleteTemporaryCheckinByIds(Long[] ids)
    {
        return temporaryCheckinMapper.deleteTemporaryCheckinByIds(ids);
    }

    /**
     * 删除临时来队信息
     * 
     * @param id 临时来队主键
     * @return 结果
     */
    @Override
    public int deleteTemporaryCheckinById(Long id)
    {
        return temporaryCheckinMapper.deleteTemporaryCheckinById(id);
    }
}
