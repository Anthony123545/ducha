package com.supervision.grassrootsregistration.mapper;

import java.util.List;
import com.supervision.grassrootsregistration.domain.BdglStatistics;

/**
 * 军事训练月统计班Mapper接口
 * 
 * @author supervision
 * @date 2022-03-21
 */
public interface BdglStatisticsMapper 
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
     * 删除军事训练月统计班
     * 
     * @param id 军事训练月统计班主键
     * @return 结果
     */
    public int deleteBdglStatisticsById(Integer id);

    /**
     * 批量删除军事训练月统计班
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglStatisticsByIds(Integer[] ids);
}
