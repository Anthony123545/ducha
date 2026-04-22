package com.supervision.thought.mapper;

import java.util.List;
import com.supervision.thought.domain.ThoughtResponseContent;

/**
 * 响应内容Mapper接口
 * 
 * @author supervision
 * @date 2022-04-14
 */
public interface ThoughtResponseContentMapper 
{
    /**
     * 查询响应内容
     * 
     * @param id 响应内容主键
     * @return 响应内容
     */
    public ThoughtResponseContent selectThoughtResponseContentById(Long id);

    /**
     * 查询响应内容列表
     * 
     * @param thoughtResponseContent 响应内容
     * @return 响应内容集合
     */
    public List<ThoughtResponseContent> selectThoughtResponseContentList(ThoughtResponseContent thoughtResponseContent);

    /**
     * 新增响应内容
     * 
     * @param thoughtResponseContent 响应内容
     * @return 结果
     */
    public int insertThoughtResponseContent(ThoughtResponseContent thoughtResponseContent);

    /**
     * 修改响应内容
     * 
     * @param thoughtResponseContent 响应内容
     * @return 结果
     */
    public int updateThoughtResponseContent(ThoughtResponseContent thoughtResponseContent);

    /**
     * 删除响应内容
     * 
     * @param id 响应内容主键
     * @return 结果
     */
    public int deleteThoughtResponseContentById(Long id);

    /**
     * 批量删除响应内容
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteThoughtResponseContentByIds(Long[] ids);
}
