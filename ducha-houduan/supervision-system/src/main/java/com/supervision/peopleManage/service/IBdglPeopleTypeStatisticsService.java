package com.supervision.peopleManage.service;

import java.util.List;
import com.supervision.peopleManage.domain.BdglPeopleTypeStatistics;

/**
 * 人员类别数量Service接口
 * 
 * @author ruoyi
 * @date 2022-10-29
 */
public interface IBdglPeopleTypeStatisticsService 
{
    /**
     * 查询人员类别数量
     * 
     * @param id 人员类别数量主键
     * @return 人员类别数量
     */
    public BdglPeopleTypeStatistics selectBdglPeopleTypeStatisticsById(Long id);

    /**
     * 查询人员类别数量列表
     * 
     * @param bdglPeopleTypeStatistics 人员类别数量
     * @return 人员类别数量集合
     */
    public List<BdglPeopleTypeStatistics> selectBdglPeopleTypeStatisticsList(BdglPeopleTypeStatistics bdglPeopleTypeStatistics);

    /**
     * 新增人员类别数量
     * 
     * @param bdglPeopleTypeStatistics 人员类别数量
     * @return 结果
     */
    public int insertBdglPeopleTypeStatistics(BdglPeopleTypeStatistics bdglPeopleTypeStatistics);

    /**
     * 修改人员类别数量
     * 
     * @param bdglPeopleTypeStatistics 人员类别数量
     * @return 结果
     */
    public int updateBdglPeopleTypeStatistics(BdglPeopleTypeStatistics bdglPeopleTypeStatistics);

    /**
     * 批量删除人员类别数量
     * 
     * @param ids 需要删除的人员类别数量主键集合
     * @return 结果
     */
    public int deleteBdglPeopleTypeStatisticsByIds(Long[] ids);

    /**
     * 删除人员类别数量信息
     * 
     * @param id 人员类别数量主键
     * @return 结果
     */
    public int deleteBdglPeopleTypeStatisticsById(Long id);
}
