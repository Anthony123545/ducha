package com.supervision.thought.service.impl;

import java.util.List;
import com.supervision.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.thought.mapper.ThoughtPlanResponsesMapper;
import com.supervision.thought.domain.ThoughtPlanResponses;
import com.supervision.thought.service.IThoughtPlanResponsesService;

/**
 * 月教育计划响应Service业务层处理
 * 
 * @author supervision
 * @date 2022-04-08
 */
@Service
public class ThoughtPlanResponsesServiceImpl implements IThoughtPlanResponsesService 
{
    @Autowired
    private ThoughtPlanResponsesMapper thoughtPlanResponsesMapper;

    /**
     * 查询月教育计划响应
     * 
     * @param id 月教育计划响应主键
     * @return 月教育计划响应
     */
    @Override
    public ThoughtPlanResponses selectThoughtPlanResponsesById(Long id)
    {
        return thoughtPlanResponsesMapper.selectThoughtPlanResponsesById(id);
    }

    /**
     * 查询月教育计划响应列表
     * 
     * @param thoughtPlanResponses 月教育计划响应
     * @return 月教育计划响应
     */
    @Override
    public List<ThoughtPlanResponses> selectThoughtPlanResponsesList(ThoughtPlanResponses thoughtPlanResponses)
    {
        return thoughtPlanResponsesMapper.selectThoughtPlanResponsesList(thoughtPlanResponses);
    }

    /**
     * 新增月教育计划响应
     * 
     * @param thoughtPlanResponses 月教育计划响应
     * @return 结果
     */
    @Override
    public int insertThoughtPlanResponses(ThoughtPlanResponses thoughtPlanResponses)
    {
        thoughtPlanResponses.setCreateTime(DateUtils.getNowDate());
        return thoughtPlanResponsesMapper.insertThoughtPlanResponses(thoughtPlanResponses);
    }

    /**
     * 修改月教育计划响应
     * 
     * @param thoughtPlanResponses 月教育计划响应
     * @return 结果
     */
    @Override
    public int updateThoughtPlanResponses(ThoughtPlanResponses thoughtPlanResponses)
    {
        thoughtPlanResponses.setUpdateTime(DateUtils.getNowDate());
        return thoughtPlanResponsesMapper.updateThoughtPlanResponses(thoughtPlanResponses);
    }

    /**
     * 批量删除月教育计划响应
     * 
     * @param ids 需要删除的月教育计划响应主键
     * @return 结果
     */
    @Override
    public int deleteThoughtPlanResponsesByIds(Long[] ids)
    {
        return thoughtPlanResponsesMapper.deleteThoughtPlanResponsesByIds(ids);
    }

    /**
     * 删除月教育计划响应信息
     * 
     * @param id 月教育计划响应主键
     * @return 结果
     */
    @Override
    public int deleteThoughtPlanResponsesById(Long id)
    {
        return thoughtPlanResponsesMapper.deleteThoughtPlanResponsesById(id);
    }
}
