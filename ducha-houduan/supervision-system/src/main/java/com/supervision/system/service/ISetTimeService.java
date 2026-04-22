package com.supervision.system.service;

import java.util.List;
import com.supervision.system.domain.SetTime;

/**
 * 截止日期设置
Service接口
 * 
 * @author ruoyi
 * @date 2023-01-10
 */
public interface ISetTimeService 
{
    /**
     * 查询截止日期设置

     * 
     * @param id 截止日期设置
主键
     * @return 截止日期设置

     */
    public SetTime selectSetTimeById(Integer id);

    /**
     * 查询截止日期设置
列表
     * 
     * @param setTime 截止日期设置

     * @return 截止日期设置
集合
     */
    public List<SetTime> selectSetTimeList(SetTime setTime);

    /**
     * 新增截止日期设置

     * 
     * @param setTime 截止日期设置

     * @return 结果
     */
    public int insertSetTime(SetTime setTime);

    /**
     * 修改截止日期设置

     * 
     * @param setTime 截止日期设置

     * @return 结果
     */
    public int updateSetTime(SetTime setTime);

    /**
     * 批量删除截止日期设置

     * 
     * @param ids 需要删除的截止日期设置
主键集合
     * @return 结果
     */
    public int deleteSetTimeByIds(Integer[] ids);

    /**
     * 删除截止日期设置
信息
     * 
     * @param id 截止日期设置
主键
     * @return 结果
     */
    public int deleteSetTimeById(Integer id);
}