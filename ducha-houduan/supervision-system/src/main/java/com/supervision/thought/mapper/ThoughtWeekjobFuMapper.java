package com.supervision.thought.mapper;

import java.util.List;
import com.supervision.thought.domain.ThoughtWeekjobFu;

/**
 * 周工作附Mapper接口
 * 
 * @author supervision
 * @date 2022-04-06
 */
public interface ThoughtWeekjobFuMapper 
{
    /**
     * 查询周工作附
     * 
     * @param id 周工作附主键
     * @return 周工作附
     */
    public ThoughtWeekjobFu selectThoughtWeekjobFuById(Long id);

    /**
     * 查询周工作附列表
     * 
     * @param thoughtWeekjobFu 周工作附
     * @return 周工作附集合
     */
    public List<ThoughtWeekjobFu> selectThoughtWeekjobFuList(ThoughtWeekjobFu thoughtWeekjobFu);

    /**
     * 新增周工作附
     * 
     * @param thoughtWeekjobFu 周工作附
     * @return 结果
     */
    public int insertThoughtWeekjobFu(ThoughtWeekjobFu thoughtWeekjobFu);

    /**
     * 修改周工作附
     * 
     * @param thoughtWeekjobFu 周工作附
     * @return 结果
     */
    public int updateThoughtWeekjobFu(ThoughtWeekjobFu thoughtWeekjobFu);

    /**
     * 删除周工作附
     * 
     * @param id 周工作附主键
     * @return 结果
     */
    public int deleteThoughtWeekjobFuById(Long id);

    /**
     * 批量删除周工作附
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteThoughtWeekjobFuByIds(Long[] ids);
}
