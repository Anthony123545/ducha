package com.supervision.thought.mapper;

import java.util.List;
import com.supervision.thought.domain.ThoughtPlanFujian;

/**
 * 月教育计划附件Mapper接口
 * 
 * @author supervision
 * @date 2022-04-08
 */
public interface ThoughtPlanFujianMapper 
{
    /**
     * 查询月教育计划附件
     * 
     * @param id 月教育计划附件主键
     * @return 月教育计划附件
     */
    public ThoughtPlanFujian selectThoughtPlanFujianById(Long id);

    /**
     * 查询月教育计划附件列表
     * 
     * @param thoughtPlanFujian 月教育计划附件
     * @return 月教育计划附件集合
     */
    public List<ThoughtPlanFujian> selectThoughtPlanFujianList(ThoughtPlanFujian thoughtPlanFujian);

    /**
     * 新增月教育计划附件
     * 
     * @param thoughtPlanFujian 月教育计划附件
     * @return 结果
     */
    public int insertThoughtPlanFujian(ThoughtPlanFujian thoughtPlanFujian);

    /**
     * 修改月教育计划附件
     * 
     * @param thoughtPlanFujian 月教育计划附件
     * @return 结果
     */
    public int updateThoughtPlanFujian(ThoughtPlanFujian thoughtPlanFujian);

    /**
     * 删除月教育计划附件
     * 
     * @param id 月教育计划附件主键
     * @return 结果
     */
    public int deleteThoughtPlanFujianById(Long id);

    /**
     * 批量删除月教育计划附件
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteThoughtPlanFujianByIds(Long[] ids);

    void deleteThoughtPlanFujian(ThoughtPlanFujian thoughtPlanFujian);
}
