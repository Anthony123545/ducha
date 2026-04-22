package com.supervision.thought.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.thought.mapper.ThoughtResponseContentMapper;
import com.supervision.thought.domain.ThoughtResponseContent;
import com.supervision.thought.service.IThoughtResponseContentService;

/**
 * 响应内容Service业务层处理
 * 
 * @author supervision
 * @date 2022-04-14
 */
@Service
public class ThoughtResponseContentServiceImpl implements IThoughtResponseContentService 
{
    @Autowired
    private ThoughtResponseContentMapper thoughtResponseContentMapper;

    /**
     * 查询响应内容
     * 
     * @param id 响应内容主键
     * @return 响应内容
     */
    @Override
    public ThoughtResponseContent selectThoughtResponseContentById(Long id)
    {
        return thoughtResponseContentMapper.selectThoughtResponseContentById(id);
    }

    /**
     * 查询响应内容列表
     * 
     * @param thoughtResponseContent 响应内容
     * @return 响应内容
     */
    @Override
    public List<ThoughtResponseContent> selectThoughtResponseContentList(ThoughtResponseContent thoughtResponseContent)
    {
        return thoughtResponseContentMapper.selectThoughtResponseContentList(thoughtResponseContent);
    }

    /**
     * 新增响应内容
     * 
     * @param thoughtResponseContent 响应内容
     * @return 结果
     */
    @Override
    public int insertThoughtResponseContent(ThoughtResponseContent thoughtResponseContent)
    {
        return thoughtResponseContentMapper.insertThoughtResponseContent(thoughtResponseContent);
    }

    /**
     * 修改响应内容
     * 
     * @param thoughtResponseContent 响应内容
     * @return 结果
     */
    @Override
    public int updateThoughtResponseContent(ThoughtResponseContent thoughtResponseContent)
    {
        return thoughtResponseContentMapper.updateThoughtResponseContent(thoughtResponseContent);
    }

    /**
     * 批量删除响应内容
     * 
     * @param ids 需要删除的响应内容主键
     * @return 结果
     */
    @Override
    public int deleteThoughtResponseContentByIds(Long[] ids)
    {
        return thoughtResponseContentMapper.deleteThoughtResponseContentByIds(ids);
    }

    /**
     * 删除响应内容信息
     * 
     * @param id 响应内容主键
     * @return 结果
     */
    @Override
    public int deleteThoughtResponseContentById(Long id)
    {
        return thoughtResponseContentMapper.deleteThoughtResponseContentById(id);
    }
}
