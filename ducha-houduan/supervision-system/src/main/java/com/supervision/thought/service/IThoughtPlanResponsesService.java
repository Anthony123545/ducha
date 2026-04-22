package com.supervision.thought.service;

import java.util.List;
import com.supervision.thought.domain.ThoughtPlanResponses;

/**
 * 月教育计划响应Service接口
 * 
 * @author supervision
 * @date 2022-04-08
 */
public interface IThoughtPlanResponsesService 
{
    /**
     * 查询月教育计划响应
     * 
     * @param id 月教育计划响应主键
     * @return 月教育计划响应
     */
    public ThoughtPlanResponses selectThoughtPlanResponsesById(Long id);

    /**
     * 查询月教育计划响应列表
     * 
     * @param thoughtPlanResponses 月教育计划响应
     * @return 月教育计划响应集合
     */
    public List<ThoughtPlanResponses> selectThoughtPlanResponsesList(ThoughtPlanResponses thoughtPlanResponses);

    /**
     * 新增月教育计划响应
     * 
     * @param thoughtPlanResponses 月教育计划响应
     * @return 结果
     */
    public int insertThoughtPlanResponses(ThoughtPlanResponses thoughtPlanResponses);

    /**
     * 修改月教育计划响应
     * 
     * @param thoughtPlanResponses 月教育计划响应
     * @return 结果
     */
    public int updateThoughtPlanResponses(ThoughtPlanResponses thoughtPlanResponses);

    /**
     * 批量删除月教育计划响应
     * 
     * @param ids 需要删除的月教育计划响应主键集合
     * @return 结果
     */
    public int deleteThoughtPlanResponsesByIds(Long[] ids);

    /**
     * 删除月教育计划响应信息
     * 
     * @param id 月教育计划响应主键
     * @return 结果
     */
    public int deleteThoughtPlanResponsesById(Long id);
}
