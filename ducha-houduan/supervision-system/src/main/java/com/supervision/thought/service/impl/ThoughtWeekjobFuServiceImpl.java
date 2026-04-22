package com.supervision.thought.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.thought.mapper.ThoughtWeekjobFuMapper;
import com.supervision.thought.domain.ThoughtWeekjobFu;
import com.supervision.thought.service.IThoughtWeekjobFuService;

/**
 * 周工作附Service业务层处理
 * 
 * @author supervision
 * @date 2022-04-06
 */
@Service
public class ThoughtWeekjobFuServiceImpl implements IThoughtWeekjobFuService 
{
    @Autowired
    private ThoughtWeekjobFuMapper thoughtWeekjobFuMapper;

    /**
     * 查询周工作附
     * 
     * @param id 周工作附主键
     * @return 周工作附
     */
    @Override
    public ThoughtWeekjobFu selectThoughtWeekjobFuById(Long id)
    {
        return thoughtWeekjobFuMapper.selectThoughtWeekjobFuById(id);
    }

    /**
     * 查询周工作附列表
     * 
     * @param thoughtWeekjobFu 周工作附
     * @return 周工作附
     */
    @Override
    public List<ThoughtWeekjobFu> selectThoughtWeekjobFuList(ThoughtWeekjobFu thoughtWeekjobFu)
    {
        return thoughtWeekjobFuMapper.selectThoughtWeekjobFuList(thoughtWeekjobFu);
    }

    /**
     * 新增周工作附
     * 
     * @param thoughtWeekjobFu 周工作附
     * @return 结果
     */
    @Override
    public int insertThoughtWeekjobFu(ThoughtWeekjobFu thoughtWeekjobFu)
    {
        return thoughtWeekjobFuMapper.insertThoughtWeekjobFu(thoughtWeekjobFu);
    }

    /**
     * 修改周工作附
     * 
     * @param thoughtWeekjobFu 周工作附
     * @return 结果
     */
    @Override
    public int updateThoughtWeekjobFu(ThoughtWeekjobFu thoughtWeekjobFu)
    {
        return thoughtWeekjobFuMapper.updateThoughtWeekjobFu(thoughtWeekjobFu);
    }

    /**
     * 批量删除周工作附
     * 
     * @param ids 需要删除的周工作附主键
     * @return 结果
     */
    @Override
    public int deleteThoughtWeekjobFuByIds(Long[] ids)
    {
        return thoughtWeekjobFuMapper.deleteThoughtWeekjobFuByIds(ids);
    }

    /**
     * 删除周工作附信息
     * 
     * @param id 周工作附主键
     * @return 结果
     */
    @Override
    public int deleteThoughtWeekjobFuById(Long id)
    {
        return thoughtWeekjobFuMapper.deleteThoughtWeekjobFuById(id);
    }
}
