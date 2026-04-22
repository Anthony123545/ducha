package com.supervision.thought.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.thought.mapper.ThoughtPlanResponsefuMapper;
import com.supervision.thought.domain.ThoughtPlanResponsefu;
import com.supervision.thought.service.IThoughtPlanResponsefuService;

/**
 * 月教育响应附件Service业务层处理
 * 
 * @author supervision
 * @date 2022-04-08
 */
@Service
public class ThoughtPlanResponsefuServiceImpl implements IThoughtPlanResponsefuService 
{
    @Autowired
    private ThoughtPlanResponsefuMapper thoughtPlanResponsefuMapper;

    /**
     * 查询月教育响应附件
     * 
     * @param id 月教育响应附件主键
     * @return 月教育响应附件
     */
    @Override
    public ThoughtPlanResponsefu selectThoughtPlanResponsefuById(Long id)
    {
        return thoughtPlanResponsefuMapper.selectThoughtPlanResponsefuById(id);
    }

    /**
     * 查询月教育响应附件列表
     * 
     * @param thoughtPlanResponsefu 月教育响应附件
     * @return 月教育响应附件
     */
    @Override
    public List<ThoughtPlanResponsefu> selectThoughtPlanResponsefuList(ThoughtPlanResponsefu thoughtPlanResponsefu)
    {
        return thoughtPlanResponsefuMapper.selectThoughtPlanResponsefuList(thoughtPlanResponsefu);
    }

    /**
     * 新增月教育响应附件
     * 
     * @param thoughtPlanResponsefu 月教育响应附件
     * @return 结果
     */
    @Override
    public int insertThoughtPlanResponsefu(ThoughtPlanResponsefu thoughtPlanResponsefu)
    {
        return thoughtPlanResponsefuMapper.insertThoughtPlanResponsefu(thoughtPlanResponsefu);
    }

    /**
     * 修改月教育响应附件
     * 
     * @param thoughtPlanResponsefu 月教育响应附件
     * @return 结果
     */
    @Override
    public int updateThoughtPlanResponsefu(ThoughtPlanResponsefu thoughtPlanResponsefu)
    {
        return thoughtPlanResponsefuMapper.updateThoughtPlanResponsefu(thoughtPlanResponsefu);
    }

    /**
     * 批量删除月教育响应附件
     * 
     * @param ids 需要删除的月教育响应附件主键
     * @return 结果
     */
    @Override
    public int deleteThoughtPlanResponsefuByIds(Long[] ids)
    {
        return thoughtPlanResponsefuMapper.deleteThoughtPlanResponsefuByIds(ids);
    }

    /**
     * 删除月教育响应附件信息
     * 
     * @param id 月教育响应附件主键
     * @return 结果
     */
    @Override
    public int deleteThoughtPlanResponsefuById(Long id)
    {
        return thoughtPlanResponsefuMapper.deleteThoughtPlanResponsefuById(id);
    }
}
