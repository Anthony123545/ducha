package com.supervision.thought.service;

import java.util.List;
import com.supervision.thought.domain.ThoughtWeekjob;

/**
 * 周工作Service接口
 * 
 * @author supervision
 * @date 2022-04-06
 */
public interface IThoughtWeekjobService 
{
    /**
     * 查询周工作
     * 
     * @param id 周工作主键
     * @return 周工作
     */
    public ThoughtWeekjob selectThoughtWeekjobById(Long id);

    /**
     * 查询周工作列表
     * 
     * @param thoughtWeekjob 周工作
     * @return 周工作集合
     */
    public List<ThoughtWeekjob> selectThoughtWeekjobList(ThoughtWeekjob thoughtWeekjob);

    /**
     * 新增周工作
     * 
     * @param thoughtWeekjob 周工作
     * @return 结果
     */
    public int insertThoughtWeekjob(ThoughtWeekjob thoughtWeekjob);

    /**
     * 修改周工作
     * 
     * @param thoughtWeekjob 周工作
     * @return 结果
     */
    public int updateThoughtWeekjob(ThoughtWeekjob thoughtWeekjob);

    /**
     * 批量删除周工作
     * 
     * @param ids 需要删除的周工作主键集合
     * @return 结果
     */
    public int deleteThoughtWeekjobByIds(Long[] ids);

    /**
     * 删除周工作信息
     * 
     * @param id 周工作主键
     * @return 结果
     */
    public int deleteThoughtWeekjobById(Long id);
}
