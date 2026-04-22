package com.supervision.grassrootsregistration.service;

import java.util.List;
import com.supervision.grassrootsregistration .domain.BdglWeekwork;

/**
 * 一周工作安排Service接口
 *
 * @author supervision
 * @date 2022-02-28
 */
public interface IBdglWeekworkService
{
    /**
     * 查询一周工作安排
     *
     * @param id 一周工作安排主键
     * @return 一周工作安排
     */
    public BdglWeekwork selectBdglWeekworkById(Integer id);

    /**
     * 查询一周工作安排列表
     *
     * @param bdglWeekwork 一周工作安排
     * @return 一周工作安排集合
     */
    public List<BdglWeekwork> selectBdglWeekworkList(BdglWeekwork bdglWeekwork);

    /**
     * 新增一周工作安排
     *
     * @param bdglWeekwork 一周工作安排
     * @return 结果
     */
    public int insertBdglWeekwork(BdglWeekwork bdglWeekwork);

    /**
     * 修改一周工作安排
     *
     * @param bdglWeekwork 一周工作安排
     * @return 结果
     */
    public int updateBdglWeekwork(BdglWeekwork bdglWeekwork);

    /**
     * 批量删除一周工作安排
     *
     * @param ids 需要删除的一周工作安排主键集合
     * @return 结果
     */
    public int deleteBdglWeekworkByIds(Integer[] ids);

    /**
     * 删除一周工作安排信息
     *
     * @param id 一周工作安排主键
     * @return 结果
     */
    public int deleteBdglWeekworkById(Integer id);

    List<BdglWeekwork> selectBdglWeekworkLists(BdglWeekwork bdglWeekwork1);

    BdglWeekwork selectBdglWeekwork(BdglWeekwork bdglWeekwork);
}