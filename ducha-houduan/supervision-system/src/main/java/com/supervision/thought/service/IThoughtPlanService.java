package com.supervision.thought.service;

import java.util.List;
import com.supervision.thought.domain.ThoughtPlan;

/**
 * 月教育计划Service接口
 * 
 * @author supervision
 * @date 2022-04-08
 */
public interface IThoughtPlanService 
{
    /**
     * 查询月教育计划
     * 
     * @param id 月教育计划主键
     * @return 月教育计划
     */
    public ThoughtPlan selectThoughtPlanById(Long id);

    /**
     * 查询月教育计划列表
     * 
     * @param thoughtPlan 月教育计划
     * @return 月教育计划集合
     */
    public List<ThoughtPlan> selectThoughtPlanList(ThoughtPlan thoughtPlan);

    /**
     * 新增月教育计划
     * 
     * @param thoughtPlan 月教育计划
     * @return 结果
     */
    public int insertThoughtPlan(ThoughtPlan thoughtPlan);

    /**
     * 修改月教育计划
     * 
     * @param thoughtPlan 月教育计划
     * @return 结果
     */
    public int updateThoughtPlan(ThoughtPlan thoughtPlan);

    /**
     * 批量删除月教育计划
     * 
     * @param ids 需要删除的月教育计划主键集合
     * @return 结果
     */
    public int deleteThoughtPlanByIds(Long[] ids);

    /**
     * 删除月教育计划信息
     * 
     * @param id 月教育计划主键
     * @return 结果
     */
    public int deleteThoughtPlanById(Long id);

    ThoughtPlan selectNewPlan();
}
