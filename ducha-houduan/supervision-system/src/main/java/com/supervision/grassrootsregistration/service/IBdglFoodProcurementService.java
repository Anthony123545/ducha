package com.supervision.grassrootsregistration.service;

import java.util.List;
import com.supervision.grassrootsregistration.domain.BdglFoodProcurement;

/**
 * 采购食材Service接口
 * 
 * @author supervision
 * @date 2022-02-28
 */
public interface IBdglFoodProcurementService 
{
    /**
     * 查询采购食材
     * 
     * @param id 采购食材主键
     * @return 采购食材
     */
    public BdglFoodProcurement selectBdglFoodProcurementById(Integer id);

    /**
     * 查询采购食材列表
     * 
     * @param bdglFoodProcurement 采购食材
     * @return 采购食材集合
     */
    public List<BdglFoodProcurement> selectBdglFoodProcurementList(BdglFoodProcurement bdglFoodProcurement);

    /**
     * 新增采购食材
     * 
     * @param bdglFoodProcurement 采购食材
     * @return 结果
     */
    public int insertBdglFoodProcurement(BdglFoodProcurement bdglFoodProcurement);

    /**
     * 修改采购食材
     * 
     * @param bdglFoodProcurement 采购食材
     * @return 结果
     */
    public int updateBdglFoodProcurement(BdglFoodProcurement bdglFoodProcurement);

    /**
     * 批量删除采购食材
     * 
     * @param ids 需要删除的采购食材主键集合
     * @return 结果
     */
    public int deleteBdglFoodProcurementByIds(Integer[] ids);

    /**
     * 删除采购食材信息
     * 
     * @param id 采购食材主键
     * @return 结果
     */
    public int deleteBdglFoodProcurementById(Integer id);
}
