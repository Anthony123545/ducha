package com.supervision.thought.service;

import java.util.List;
import com.supervision.thought.domain.ThoughtGetready;

/**
 * 教育准备会Service接口
 * 
 * @author supervision
 * @date 2022-04-06
 */
public interface IThoughtGetreadyService 
{
    /**
     * 查询教育准备会
     * 
     * @param id 教育准备会主键
     * @return 教育准备会
     */
    public ThoughtGetready selectThoughtGetreadyById(Long id);

    /**
     * 查询教育准备会列表
     * 
     * @param thoughtGetready 教育准备会
     * @return 教育准备会集合
     */
    public List<ThoughtGetready> selectThoughtGetreadyList(ThoughtGetready thoughtGetready);

    /**
     * 新增教育准备会
     * 
     * @param thoughtGetready 教育准备会
     * @return 结果
     */
    public int insertThoughtGetready(ThoughtGetready thoughtGetready);

    /**
     * 修改教育准备会
     * 
     * @param thoughtGetready 教育准备会
     * @return 结果
     */
    public int updateThoughtGetready(ThoughtGetready thoughtGetready);

    /**
     * 批量删除教育准备会
     * 
     * @param ids 需要删除的教育准备会主键集合
     * @return 结果
     */
    public int deleteThoughtGetreadyByIds(Long[] ids);

    /**
     * 删除教育准备会信息
     * 
     * @param id 教育准备会主键
     * @return 结果
     */
    public int deleteThoughtGetreadyById(Long id);
}
