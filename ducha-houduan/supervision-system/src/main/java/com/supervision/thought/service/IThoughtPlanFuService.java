package com.supervision.thought.service;

import java.util.List;
import com.supervision.thought.domain.ThoughtPlanFu;

/**
 * 计划内容Service接口
 * 
 * @author supervision
 * @date 2022-04-09
 */
public interface IThoughtPlanFuService 
{
    /**
     * 查询计划内容
     * 
     * @param id 计划内容主键
     * @return 计划内容
     */
    public ThoughtPlanFu selectThoughtPlanFuById(Long id);

    /**
     * 查询计划内容列表
     * 
     * @param thoughtPlanFu 计划内容
     * @return 计划内容集合
     */
    public List<ThoughtPlanFu> selectThoughtPlanFuList(ThoughtPlanFu thoughtPlanFu);

    /**
     * 新增计划内容
     * 
     * @param thoughtPlanFu 计划内容
     * @return 结果
     */
    public int insertThoughtPlanFu(ThoughtPlanFu thoughtPlanFu);

    /**
     * 修改计划内容
     * 
     * @param thoughtPlanFu 计划内容
     * @return 结果
     */
    public int updateThoughtPlanFu(ThoughtPlanFu thoughtPlanFu);

    /**
     * 批量删除计划内容
     * 
     * @param ids 需要删除的计划内容主键集合
     * @return 结果
     */
    public int deleteThoughtPlanFuByIds(Long[] ids);

    /**
     * 删除计划内容信息
     * 
     * @param id 计划内容主键
     * @return 结果
     */
    public int deleteThoughtPlanFuById(Long id);

    void deleteThoughtPlanFuBy(ThoughtPlanFu thoughtPlanFu);
}
