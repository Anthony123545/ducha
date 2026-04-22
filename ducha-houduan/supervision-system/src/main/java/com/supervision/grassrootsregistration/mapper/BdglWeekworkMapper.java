package com.supervision.grassrootsregistration.mapper;

import java.util.List;
import com.supervision.grassrootsregistration .domain.BdglWeekwork;
import com.supervision.grassrootsregistration .domain.BdglWeekworkRegister;

/**
 * 一周工作安排Mapper接口
 *
 * @author supervision
 * @date 2022-02-28
 */
public interface BdglWeekworkMapper
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
     * 删除一周工作安排
     *
     * @param id 一周工作安排主键
     * @return 结果
     */
    public int deleteBdglWeekworkById(Integer id);

    /**
     * 批量删除一周工作安排
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglWeekworkByIds(Integer[] ids);

    /**
     * 批量删除一周安排工作分
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglWeekworkRegisterByWeekworkIds(Integer[] ids);

    /**
     * 批量新增一周安排工作分
     *
     * @param bdglWeekworkRegisterList 一周安排工作分列表
     * @return 结果
     */
    public int batchBdglWeekworkRegister(List<BdglWeekworkRegister> bdglWeekworkRegisterList);


    /**
     * 通过一周工作安排主键删除一周安排工作分信息
     *
     * @param id 一周工作安排ID
     * @return 结果
     */
    public int deleteBdglWeekworkRegisterByWeekworkId(Integer id);
    /**获取当前大队一周工作安排
     * @return*/
    BdglWeekwork selectBdglWeekwork(BdglWeekwork bdglWeekwork);

    List<BdglWeekwork> selectBdglWeekworkLists(BdglWeekwork bdglWeekwork1);
}