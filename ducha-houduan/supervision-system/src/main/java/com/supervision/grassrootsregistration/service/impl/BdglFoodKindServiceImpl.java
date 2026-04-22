package com.supervision.grassrootsregistration.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.grassrootsregistration.mapper.BdglFoodKindMapper;
import com.supervision.grassrootsregistration.domain.BdglFoodKind;
import com.supervision.grassrootsregistration.service.IBdglFoodKindService;

/**
 * 实物检查验收Service业务层处理
 * 
 * @author supervision
 * @date 2022-02-28
 */
@Service
public class BdglFoodKindServiceImpl implements IBdglFoodKindService 
{
    @Autowired
    private BdglFoodKindMapper bdglFoodKindMapper;

    /**
     * 查询实物检查验收
     * 
     * @param id 实物检查验收主键
     * @return 实物检查验收
     */
    @Override
    public BdglFoodKind selectBdglFoodKindById(Integer id)
    {
        return bdglFoodKindMapper.selectBdglFoodKindById(id);
    }

    /**
     * 查询实物检查验收列表
     * 
     * @param bdglFoodKind 实物检查验收
     * @return 实物检查验收
     */
    @Override
    public List<BdglFoodKind> selectBdglFoodKindList(BdglFoodKind bdglFoodKind)
    {
        return bdglFoodKindMapper.selectBdglFoodKindList(bdglFoodKind);
    }

    /**
     * 新增实物检查验收
     * 
     * @param bdglFoodKind 实物检查验收
     * @return 结果
     */
    @Override
    public int insertBdglFoodKind(BdglFoodKind bdglFoodKind)
    {
        return bdglFoodKindMapper.insertBdglFoodKind(bdglFoodKind);
    }

    /**
     * 修改实物检查验收
     * 
     * @param bdglFoodKind 实物检查验收
     * @return 结果
     */
    @Override
    public int updateBdglFoodKind(BdglFoodKind bdglFoodKind)
    {
        return bdglFoodKindMapper.updateBdglFoodKind(bdglFoodKind);
    }

    /**
     * 批量删除实物检查验收
     * 
     * @param ids 需要删除的实物检查验收主键
     * @return 结果
     */
    @Override
    public int deleteBdglFoodKindByIds(Integer[] ids)
    {
        return bdglFoodKindMapper.deleteBdglFoodKindByIds(ids);
    }

    /**
     * 删除实物检查验收信息
     * 
     * @param id 实物检查验收主键
     * @return 结果
     */
    @Override
    public int deleteBdglFoodKindById(Integer id)
    {
        return bdglFoodKindMapper.deleteBdglFoodKindById(id);
    }
}
