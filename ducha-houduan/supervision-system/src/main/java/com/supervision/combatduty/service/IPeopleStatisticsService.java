package com.supervision.combatduty.service;

import java.util.List;
import com.supervision.combatduty.domain.PeopleStatistics;

/**
 * 人员变动Service接口
 * 
 * @author ruoyi
 * @date 2022-08-25
 */
public interface IPeopleStatisticsService 
{
    /**
     * 查询人员变动
     * 
     * @param id 人员变动主键
     * @return 人员变动
     */
    public PeopleStatistics selectPeopleStatisticsById(Integer id);

    /**
     * 查询人员变动列表
     * 
     * @param peopleStatistics 人员变动
     * @return 人员变动集合
     */
    public List<PeopleStatistics> selectPeopleStatisticsList(PeopleStatistics peopleStatistics);

    /**
     * 新增人员变动
     * 
     * @param peopleStatistics 人员变动
     * @return 结果
     */
    public int insertPeopleStatistics(PeopleStatistics peopleStatistics);

    /**
     * 修改人员变动
     * 
     * @param peopleStatistics 人员变动
     * @return 结果
     */
    public int updatePeopleStatistics(PeopleStatistics peopleStatistics);

    /**
     * 批量删除人员变动
     * 
     * @param ids 需要删除的人员变动主键集合
     * @return 结果
     */
    public int deletePeopleStatisticsByIds(Integer[] ids);

    /**
     * 删除人员变动信息
     * 
     * @param id 人员变动主键
     * @return 结果
     */
    public int deletePeopleStatisticsById(Integer id);
}