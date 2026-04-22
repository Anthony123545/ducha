package com.supervision.thought.service;

import java.util.List;
import com.supervision.thought.domain.ThoughtPlanResponsefu;

/**
 * 月教育响应附件Service接口
 * 
 * @author supervision
 * @date 2022-04-08
 */
public interface IThoughtPlanResponsefuService 
{
    /**
     * 查询月教育响应附件
     * 
     * @param id 月教育响应附件主键
     * @return 月教育响应附件
     */
    public ThoughtPlanResponsefu selectThoughtPlanResponsefuById(Long id);

    /**
     * 查询月教育响应附件列表
     * 
     * @param thoughtPlanResponsefu 月教育响应附件
     * @return 月教育响应附件集合
     */
    public List<ThoughtPlanResponsefu> selectThoughtPlanResponsefuList(ThoughtPlanResponsefu thoughtPlanResponsefu);

    /**
     * 新增月教育响应附件
     * 
     * @param thoughtPlanResponsefu 月教育响应附件
     * @return 结果
     */
    public int insertThoughtPlanResponsefu(ThoughtPlanResponsefu thoughtPlanResponsefu);

    /**
     * 修改月教育响应附件
     * 
     * @param thoughtPlanResponsefu 月教育响应附件
     * @return 结果
     */
    public int updateThoughtPlanResponsefu(ThoughtPlanResponsefu thoughtPlanResponsefu);

    /**
     * 批量删除月教育响应附件
     * 
     * @param ids 需要删除的月教育响应附件主键集合
     * @return 结果
     */
    public int deleteThoughtPlanResponsefuByIds(Long[] ids);

    /**
     * 删除月教育响应附件信息
     * 
     * @param id 月教育响应附件主键
     * @return 结果
     */
    public int deleteThoughtPlanResponsefuById(Long id);
}
