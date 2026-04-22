package com.supervision.grassrootsregistration.service.impl;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.grassrootsregistration.mapper.BdglStatisticsMapper;
import com.supervision.grassrootsregistration.domain.BdglStatistics;
import com.supervision.grassrootsregistration.service.IBdglStatisticsService;

/**
 * 军事训练月统计班Service业务层处理
 * 
 * @author supervision
 * @date 2022-03-21
 */
@Service
public class BdglStatisticsServiceImpl implements IBdglStatisticsService 
{
    @Autowired
    private BdglStatisticsMapper bdglStatisticsMapper;

    /**
     * 查询军事训练月统计班
     * 
     * @param id 军事训练月统计班主键
     * @return 军事训练月统计班
     */
    @Override
    public BdglStatistics selectBdglStatisticsById(Integer id)
    {
        return bdglStatisticsMapper.selectBdglStatisticsById(id);
    }

    /**
     * 查询军事训练月统计班列表
     * 
     * @param bdglStatistics 军事训练月统计班
     * @return 军事训练月统计班
     */
    @Override
    public List<BdglStatistics> selectBdglStatisticsList(BdglStatistics bdglStatistics)
    {
        return bdglStatisticsMapper.selectBdglStatisticsList(bdglStatistics);
    }

    /**
     * 新增军事训练月统计班
     * 
     * @param bdglStatistics 军事训练月统计班
     * @return 结果
     */
    @Override
    public int insertBdglStatistics(BdglStatistics bdglStatistics)
    {
        bdglStatistics.setCreatetime(new Date());
        return bdglStatisticsMapper.insertBdglStatistics(bdglStatistics);
    }

    /**
     * 修改军事训练月统计班
     * 
     * @param bdglStatistics 军事训练月统计班
     * @return 结果
     */
    @Override
    public int updateBdglStatistics(BdglStatistics bdglStatistics)
    {
        return bdglStatisticsMapper.updateBdglStatistics(bdglStatistics);
    }

    /**
     * 批量删除军事训练月统计班
     * 
     * @param ids 需要删除的军事训练月统计班主键
     * @return 结果
     */
    @Override
    public int deleteBdglStatisticsByIds(Integer[] ids)
    {
        return bdglStatisticsMapper.deleteBdglStatisticsByIds(ids);
    }

    /**
     * 删除军事训练月统计班信息
     * 
     * @param id 军事训练月统计班主键
     * @return 结果
     */
    @Override
    public int deleteBdglStatisticsById(Integer id)
    {
        return bdglStatisticsMapper.deleteBdglStatisticsById(id);
    }
}
