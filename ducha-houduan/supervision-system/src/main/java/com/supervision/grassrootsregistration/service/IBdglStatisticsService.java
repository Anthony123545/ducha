package com.supervision.grassrootsregistration.service;

import java.util.List;
import com.supervision.grassrootsregistration.domain.BdglStatistics;

/**
 * 军事训练月统计班Service接口
 * 
 * @author supervision
 * @date 2022-03-21
 */
public interface IBdglStatisticsService 
{
    /**
     * 查询军事训练月统计班
     * 
     * @param id 军事训练月统计班主键
     * @return 军事训练月统计班
     */
    public BdglStatistics selectBdglStatisticsById(Integer id);

    /**
     * 查询军事训练月统计班列表
     * 
     * @param bdglStatistics 军事训练月统计班
     * @return 军事训练月统计班集合
     */
    public List<BdglStatistics> selectBdglStatisticsList(BdglStatistics bdglStatistics);

    /**
     * 新增军事训练月统计班
     * 
     * @param bdglStatistics 军事训练月统计班
     * @return 结果
     */
    public int insertBdglStatistics(BdglStatistics bdglStatistics);

    /**
     * 修改军事训练月统计班
     * 
     * @param bdglStatistics 军事训练月统计班
     * @return 结果
     */
    public int updateBdglStatistics(BdglStatistics bdglStatistics);

    /**
     * 批量删除军事训练月统计班
     * 
     * @param ids 需要删除的军事训练月统计班主键集合
     * @return 结果
     */
    public int deleteBdglStatisticsByIds(Integer[] ids);

    /**
     * 删除军事训练月统计班信息
     * 
     * @param id 军事训练月统计班主键
     * @return 结果
     */
    public int deleteBdglStatisticsById(Integer id);
}
