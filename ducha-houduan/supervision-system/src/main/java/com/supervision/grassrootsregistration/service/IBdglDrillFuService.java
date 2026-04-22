package com.supervision.grassrootsregistration.service;

import java.util.List;
import com.supervision.grassrootsregistration.domain.BdglDrillFu;

/**
 * 军事训练登记簿附表Service接口
 * 
 * @author supervision
 * @date 2022-05-20
 */
public interface IBdglDrillFuService 
{
    /**
     * 查询军事训练登记簿附表
     * 
     * @param id 军事训练登记簿附表主键
     * @return 军事训练登记簿附表
     */
    public BdglDrillFu selectBdglDrillFuById(Long id);

    /**
     * 查询军事训练登记簿附表列表
     * 
     * @param bdglDrillFu 军事训练登记簿附表
     * @return 军事训练登记簿附表集合
     */
    public List<BdglDrillFu> selectBdglDrillFuList(BdglDrillFu bdglDrillFu);

    /**
     * 新增军事训练登记簿附表
     * 
     * @param bdglDrillFu 军事训练登记簿附表
     * @return 结果
     */
    public int insertBdglDrillFu(BdglDrillFu bdglDrillFu);

    /**
     * 修改军事训练登记簿附表
     * 
     * @param bdglDrillFu 军事训练登记簿附表
     * @return 结果
     */
    public int updateBdglDrillFu(BdglDrillFu bdglDrillFu);

    /**
     * 批量删除军事训练登记簿附表
     * 
     * @param ids 需要删除的军事训练登记簿附表主键集合
     * @return 结果
     */
    public int deleteBdglDrillFuByIds(Long[] ids);

    /**
     * 删除军事训练登记簿附表信息
     * 
     * @param id 军事训练登记簿附表主键
     * @return 结果
     */
    public int deleteBdglDrillFuById(Long id);
}