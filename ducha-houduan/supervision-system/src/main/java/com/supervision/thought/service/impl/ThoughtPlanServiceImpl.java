package com.supervision.thought.service.impl;

import java.util.List;
import com.supervision.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.thought.mapper.ThoughtPlanMapper;
import com.supervision.thought.domain.ThoughtPlan;
import com.supervision.thought.service.IThoughtPlanService;

/**
 * 月教育计划Service业务层处理
 * 
 * @author supervision
 * @date 2022-04-08
 */
@Service
public class ThoughtPlanServiceImpl implements IThoughtPlanService 
{
    @Autowired
    private ThoughtPlanMapper thoughtPlanMapper;

    /**
     * 查询月教育计划
     * 
     * @param id 月教育计划主键
     * @return 月教育计划
     */
    @Override
    public ThoughtPlan selectThoughtPlanById(Long id)
    {
        return thoughtPlanMapper.selectThoughtPlanById(id);
    }

    /**
     * 查询月教育计划列表
     * 
     * @param thoughtPlan 月教育计划
     * @return 月教育计划
     */
    @Override
    public List<ThoughtPlan> selectThoughtPlanList(ThoughtPlan thoughtPlan)
    {
        return thoughtPlanMapper.selectThoughtPlanList(thoughtPlan);
    }

    /**
     * 新增月教育计划
     * 
     * @param thoughtPlan 月教育计划
     * @return 结果
     */
    @Override
    public int insertThoughtPlan(ThoughtPlan thoughtPlan)
    {
        thoughtPlan.setCreateTime(DateUtils.getNowDate());
        return thoughtPlanMapper.insertThoughtPlan(thoughtPlan);
    }

    /**
     * 修改月教育计划
     * 
     * @param thoughtPlan 月教育计划
     * @return 结果
     */
    @Override
    public int updateThoughtPlan(ThoughtPlan thoughtPlan)
    {
        thoughtPlan.setUpdateTime(DateUtils.getNowDate());
        return thoughtPlanMapper.updateThoughtPlan(thoughtPlan);
    }

    /**
     * 批量删除月教育计划
     * 
     * @param ids 需要删除的月教育计划主键
     * @return 结果
     */
    @Override
    public int deleteThoughtPlanByIds(Long[] ids)
    {
        return thoughtPlanMapper.deleteThoughtPlanByIds(ids);
    }

    /**
     * 删除月教育计划信息
     * 
     * @param id 月教育计划主键
     * @return 结果
     */
    @Override
    public int deleteThoughtPlanById(Long id)
    {
        return thoughtPlanMapper.deleteThoughtPlanById(id);
    }

    @Override
    public ThoughtPlan selectNewPlan() {
        return thoughtPlanMapper.selectNewPlan();
    }
}
