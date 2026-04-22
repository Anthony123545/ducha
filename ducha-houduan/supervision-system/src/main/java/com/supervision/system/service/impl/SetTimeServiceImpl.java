package com.supervision.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.system.mapper.SetTimeMapper;
import com.supervision.system.domain.SetTime;
import com.supervision.system.service.ISetTimeService;

/**
 * 截止日期设置
Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-01-10
 */
@Service
public class SetTimeServiceImpl implements ISetTimeService 
{
    @Autowired
    private SetTimeMapper setTimeMapper;

    /**
     * 查询截止日期设置

     * 
     * @param id 截止日期设置
主键
     * @return 截止日期设置

     */
    @Override
    public SetTime selectSetTimeById(Integer id)
    {
        return setTimeMapper.selectSetTimeById(id);
    }

    /**
     * 查询截止日期设置
列表
     * 
     * @param setTime 截止日期设置

     * @return 截止日期设置

     */
    @Override
    public List<SetTime> selectSetTimeList(SetTime setTime)
    {
        return setTimeMapper.selectSetTimeList(setTime);
    }

    /**
     * 新增截止日期设置

     * 
     * @param setTime 截止日期设置

     * @return 结果
     */
    @Override
    public int insertSetTime(SetTime setTime)
    {
        return setTimeMapper.insertSetTime(setTime);
    }

    /**
     * 修改截止日期设置

     * 
     * @param setTime 截止日期设置

     * @return 结果
     */
    @Override
    public int updateSetTime(SetTime setTime)
    {
        return setTimeMapper.updateSetTime(setTime);
    }

    /**
     * 批量删除截止日期设置

     * 
     * @param ids 需要删除的截止日期设置
主键
     * @return 结果
     */
    @Override
    public int deleteSetTimeByIds(Integer[] ids)
    {
        return setTimeMapper.deleteSetTimeByIds(ids);
    }

    /**
     * 删除截止日期设置
信息
     * 
     * @param id 截止日期设置
主键
     * @return 结果
     */
    @Override
    public int deleteSetTimeById(Integer id)
    {
        return setTimeMapper.deleteSetTimeById(id);
    }
}