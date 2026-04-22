package com.supervision.combatduty.service;

import java.util.List;
import com.supervision.combatduty.domain.BdglTaskdutyFu;

/**
 * 执行任务附表Service接口
 * 
 * @author supervision
 * @date 2022-05-06
 */
public interface IBdglTaskdutyFuService 
{
    /**
     * 查询执行任务附表
     * 
     * @param id 执行任务附表主键
     * @return 执行任务附表
     */
    public BdglTaskdutyFu selectBdglTaskdutyFuById(Long id);

    /**
     * 查询执行任务附表列表
     * 
     * @param bdglTaskdutyFu 执行任务附表
     * @return 执行任务附表集合
     */
    public List<BdglTaskdutyFu> selectBdglTaskdutyFuList(BdglTaskdutyFu bdglTaskdutyFu);

    /**
     * 新增执行任务附表
     * 
     * @param bdglTaskdutyFu 执行任务附表
     * @return 结果
     */
    public int insertBdglTaskdutyFu(BdglTaskdutyFu bdglTaskdutyFu);

    /**
     * 修改执行任务附表
     * 
     * @param bdglTaskdutyFu 执行任务附表
     * @return 结果
     */
    public int updateBdglTaskdutyFu(BdglTaskdutyFu bdglTaskdutyFu);

    /**
     * 批量删除执行任务附表
     * 
     * @param ids 需要删除的执行任务附表主键集合
     * @return 结果
     */
    public int deleteBdglTaskdutyFuByIds(Long[] ids);

    /**
     * 删除执行任务附表信息
     * 
     * @param id 执行任务附表主键
     * @return 结果
     */
    public int deleteBdglTaskdutyFuById(Long id);


    int deleteBdglTaskdutyFu(BdglTaskdutyFu bdglTaskdutyFu);
}
