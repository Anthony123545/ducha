package com.supervision.thought.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.thought.mapper.ThoughtPlanFujianMapper;
import com.supervision.thought.domain.ThoughtPlanFujian;
import com.supervision.thought.service.IThoughtPlanFujianService;

/**
 * 月教育计划附件Service业务层处理
 * 
 * @author supervision
 * @date 2022-04-08
 */
@Service
public class ThoughtPlanFujianServiceImpl implements IThoughtPlanFujianService 
{
    @Autowired
    private ThoughtPlanFujianMapper thoughtPlanFujianMapper;

    /**
     * 查询月教育计划附件
     * 
     * @param id 月教育计划附件主键
     * @return 月教育计划附件
     */
    @Override
    public ThoughtPlanFujian selectThoughtPlanFujianById(Long id)
    {
        return thoughtPlanFujianMapper.selectThoughtPlanFujianById(id);
    }

    /**
     * 查询月教育计划附件列表
     * 
     * @param thoughtPlanFujian 月教育计划附件
     * @return 月教育计划附件
     */
    @Override
    public List<ThoughtPlanFujian> selectThoughtPlanFujianList(ThoughtPlanFujian thoughtPlanFujian)
    {
        return thoughtPlanFujianMapper.selectThoughtPlanFujianList(thoughtPlanFujian);
    }

    /**
     * 新增月教育计划附件
     * 
     * @param thoughtPlanFujian 月教育计划附件
     * @return 结果
     */
    @Override
    public int insertThoughtPlanFujian(ThoughtPlanFujian thoughtPlanFujian)
    {
        return thoughtPlanFujianMapper.insertThoughtPlanFujian(thoughtPlanFujian);
    }

    /**
     * 修改月教育计划附件
     * 
     * @param thoughtPlanFujian 月教育计划附件
     * @return 结果
     */
    @Override
    public int updateThoughtPlanFujian(ThoughtPlanFujian thoughtPlanFujian)
    {
        return thoughtPlanFujianMapper.updateThoughtPlanFujian(thoughtPlanFujian);
    }

    /**
     * 批量删除月教育计划附件
     * 
     * @param ids 需要删除的月教育计划附件主键
     * @return 结果
     */
    @Override
    public int deleteThoughtPlanFujianByIds(Long[] ids)
    {
        return thoughtPlanFujianMapper.deleteThoughtPlanFujianByIds(ids);
    }

    /**
     * 删除月教育计划附件信息
     * 
     * @param id 月教育计划附件主键
     * @return 结果
     */
    @Override
    public int deleteThoughtPlanFujianById(Long id)
    {
        return thoughtPlanFujianMapper.deleteThoughtPlanFujianById(id);
    }

    @Override
    public void deleteThoughtPlanFujian(ThoughtPlanFujian thoughtPlanFujian) {
        thoughtPlanFujianMapper.deleteThoughtPlanFujian(thoughtPlanFujian);
    }
}
