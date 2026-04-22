package com.supervision.combatduty.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.combatduty.mapper.PeopleStatisticsMapper;
import com.supervision.combatduty.domain.PeopleStatistics;
import com.supervision.combatduty.service.IPeopleStatisticsService;

/**
 * 人员变动Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-08-25
 */
@Service
public class PeopleStatisticsServiceImpl implements IPeopleStatisticsService 
{
    @Autowired
    private PeopleStatisticsMapper peopleStatisticsMapper;

    /**
     * 查询人员变动
     * 
     * @param id 人员变动主键
     * @return 人员变动
     */
    @Override
    public PeopleStatistics selectPeopleStatisticsById(Integer id)
    {
        return peopleStatisticsMapper.selectPeopleStatisticsById(id);
    }

    /**
     * 查询人员变动列表
     * 
     * @param peopleStatistics 人员变动
     * @return 人员变动
     */
    @Override
    public List<PeopleStatistics> selectPeopleStatisticsList(PeopleStatistics peopleStatistics)
    {
        return peopleStatisticsMapper.selectPeopleStatisticsList(peopleStatistics);
    }

    /**
     * 新增人员变动
     * 
     * @param peopleStatistics 人员变动
     * @return 结果
     */
    @Override
    public int insertPeopleStatistics(PeopleStatistics peopleStatistics)
    {
        return peopleStatisticsMapper.insertPeopleStatistics(peopleStatistics);
    }

    /**
     * 修改人员变动
     * 
     * @param peopleStatistics 人员变动
     * @return 结果
     */
    @Override
    public int updatePeopleStatistics(PeopleStatistics peopleStatistics)
    {
        return peopleStatisticsMapper.updatePeopleStatistics(peopleStatistics);
    }

    /**
     * 批量删除人员变动
     * 
     * @param ids 需要删除的人员变动主键
     * @return 结果
     */
    @Override
    public int deletePeopleStatisticsByIds(Integer[] ids)
    {
        return peopleStatisticsMapper.deletePeopleStatisticsByIds(ids);
    }

    /**
     * 删除人员变动信息
     * 
     * @param id 人员变动主键
     * @return 结果
     */
    @Override
    public int deletePeopleStatisticsById(Integer id)
    {
        return peopleStatisticsMapper.deletePeopleStatisticsById(id);
    }
}