package com.supervision.grassrootsregistration.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.grassrootsregistration.mapper.BdglDrillFuMapper;
import com.supervision.grassrootsregistration.domain.BdglDrillFu;
import com.supervision.grassrootsregistration.service.IBdglDrillFuService;

/**
 * 军事训练登记簿附表Service业务层处理
 * 
 * @author supervision
 * @date 2022-05-20
 */
@Service
public class BdglDrillFuServiceImpl implements IBdglDrillFuService 
{
    @Autowired
    private BdglDrillFuMapper bdglDrillFuMapper;

    /**
     * 查询军事训练登记簿附表
     * 
     * @param id 军事训练登记簿附表主键
     * @return 军事训练登记簿附表
     */
    @Override
    public BdglDrillFu selectBdglDrillFuById(Long id)
    {
        return bdglDrillFuMapper.selectBdglDrillFuById(id);
    }

    /**
     * 查询军事训练登记簿附表列表
     * 
     * @param bdglDrillFu 军事训练登记簿附表
     * @return 军事训练登记簿附表
     */
    @Override
    public List<BdglDrillFu> selectBdglDrillFuList(BdglDrillFu bdglDrillFu)
    {
        return bdglDrillFuMapper.selectBdglDrillFuList(bdglDrillFu);
    }

    /**
     * 新增军事训练登记簿附表
     * 
     * @param bdglDrillFu 军事训练登记簿附表
     * @return 结果
     */
    @Override
    public int insertBdglDrillFu(BdglDrillFu bdglDrillFu)
    {
        return bdglDrillFuMapper.insertBdglDrillFu(bdglDrillFu);
    }

    /**
     * 修改军事训练登记簿附表
     * 
     * @param bdglDrillFu 军事训练登记簿附表
     * @return 结果
     */
    @Override
    public int updateBdglDrillFu(BdglDrillFu bdglDrillFu)
    {
        return bdglDrillFuMapper.updateBdglDrillFu(bdglDrillFu);
    }

    /**
     * 批量删除军事训练登记簿附表
     * 
     * @param ids 需要删除的军事训练登记簿附表主键
     * @return 结果
     */
    @Override
    public int deleteBdglDrillFuByIds(Long[] ids)
    {
        return bdglDrillFuMapper.deleteBdglDrillFuByIds(ids);
    }

    /**
     * 删除军事训练登记簿附表信息
     * 
     * @param id 军事训练登记簿附表主键
     * @return 结果
     */
    @Override
    public int deleteBdglDrillFuById(Long id)
    {
        return bdglDrillFuMapper.deleteBdglDrillFuById(id);
    }
}