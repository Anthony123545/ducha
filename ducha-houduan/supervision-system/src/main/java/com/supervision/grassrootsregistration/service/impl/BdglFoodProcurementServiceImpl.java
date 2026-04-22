package com.supervision.grassrootsregistration.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.grassrootsregistration.mapper.BdglFoodProcurementMapper;
import com.supervision.grassrootsregistration.domain.BdglFoodProcurement;
import com.supervision.grassrootsregistration.service.IBdglFoodProcurementService;

/**
 * 采购食材Service业务层处理
 * 
 * @author supervision
 * @date 2022-02-28
 */
@Service
public class BdglFoodProcurementServiceImpl implements IBdglFoodProcurementService 
{
    @Autowired
    private BdglFoodProcurementMapper bdglFoodProcurementMapper;

    /**
     * 查询采购食材
     * 
     * @param id 采购食材主键
     * @return 采购食材
     */
    @Override
    public BdglFoodProcurement selectBdglFoodProcurementById(Integer id)
    {
        return bdglFoodProcurementMapper.selectBdglFoodProcurementById(id);
    }

    /**
     * 查询采购食材列表
     * 
     * @param bdglFoodProcurement 采购食材
     * @return 采购食材
     */
    @Override
    public List<BdglFoodProcurement> selectBdglFoodProcurementList(BdglFoodProcurement bdglFoodProcurement)
    {
        return bdglFoodProcurementMapper.selectBdglFoodProcurementList(bdglFoodProcurement);
    }

    /**
     * 新增采购食材
     * 
     * @param bdglFoodProcurement 采购食材
     * @return 结果
     */
    @Override
    public int insertBdglFoodProcurement(BdglFoodProcurement bdglFoodProcurement)
    {
        return bdglFoodProcurementMapper.insertBdglFoodProcurement(bdglFoodProcurement);
    }

    /**
     * 修改采购食材
     * 
     * @param bdglFoodProcurement 采购食材
     * @return 结果
     */
    @Override
    public int updateBdglFoodProcurement(BdglFoodProcurement bdglFoodProcurement)
    {
        return bdglFoodProcurementMapper.updateBdglFoodProcurement(bdglFoodProcurement);
    }

    /**
     * 批量删除采购食材
     * 
     * @param ids 需要删除的采购食材主键
     * @return 结果
     */
    @Override
    public int deleteBdglFoodProcurementByIds(Integer[] ids)
    {
        return bdglFoodProcurementMapper.deleteBdglFoodProcurementByIds(ids);
    }

    /**
     * 删除采购食材信息
     * 
     * @param id 采购食材主键
     * @return 结果
     */
    @Override
    public int deleteBdglFoodProcurementById(Integer id)
    {
        return bdglFoodProcurementMapper.deleteBdglFoodProcurementById(id);
    }
}
