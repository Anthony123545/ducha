package com.supervision.thought.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.thought.mapper.ThoughtPlanFuMapper;
import com.supervision.thought.domain.ThoughtPlanFu;
import com.supervision.thought.service.IThoughtPlanFuService;

/**
 * 计划内容Service业务层处理
 * 
 * @author supervision
 * @date 2022-04-09
 */
@Service
public class ThoughtPlanFuServiceImpl implements IThoughtPlanFuService 
{
    @Autowired
    private ThoughtPlanFuMapper thoughtPlanFuMapper;

    /**
     * 查询计划内容
     * 
     * @param id 计划内容主键
     * @return 计划内容
     */
    @Override
    public ThoughtPlanFu selectThoughtPlanFuById(Long id)
    {
        return thoughtPlanFuMapper.selectThoughtPlanFuById(id);
    }

    /**
     * 查询计划内容列表
     * 
     * @param thoughtPlanFu 计划内容
     * @return 计划内容
     */
    @Override
    public List<ThoughtPlanFu> selectThoughtPlanFuList(ThoughtPlanFu thoughtPlanFu)
    {
        return thoughtPlanFuMapper.selectThoughtPlanFuList(thoughtPlanFu);
    }

    /**
     * 新增计划内容
     * 
     * @param thoughtPlanFu 计划内容
     * @return 结果
     */
    @Override
    public int insertThoughtPlanFu(ThoughtPlanFu thoughtPlanFu)
    {
        return thoughtPlanFuMapper.insertThoughtPlanFu(thoughtPlanFu);
    }

    /**
     * 修改计划内容
     * 
     * @param thoughtPlanFu 计划内容
     * @return 结果
     */
    @Override
    public int updateThoughtPlanFu(ThoughtPlanFu thoughtPlanFu)
    {
        return thoughtPlanFuMapper.updateThoughtPlanFu(thoughtPlanFu);
    }

    /**
     * 批量删除计划内容
     * 
     * @param ids 需要删除的计划内容主键
     * @return 结果
     */
    @Override
    public int deleteThoughtPlanFuByIds(Long[] ids)
    {
        return thoughtPlanFuMapper.deleteThoughtPlanFuByIds(ids);
    }

    /**
     * 删除计划内容信息
     * 
     * @param id 计划内容主键
     * @return 结果
     */
    @Override
    public int deleteThoughtPlanFuById(Long id)
    {
        return thoughtPlanFuMapper.deleteThoughtPlanFuById(id);
    }

    @Override
    public void deleteThoughtPlanFuBy(ThoughtPlanFu thoughtPlanFu) {
        thoughtPlanFuMapper.deleteThoughtPlanFuBy(thoughtPlanFu);
    }
}
