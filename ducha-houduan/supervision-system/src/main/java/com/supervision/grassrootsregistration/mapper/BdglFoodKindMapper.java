package com.supervision.grassrootsregistration.mapper;

import java.util.List;
import com.supervision.grassrootsregistration.domain.BdglFoodKind;

/**
 * 实物检查验收Mapper接口
 * 
 * @author supervision
 * @date 2022-02-28
 */
public interface BdglFoodKindMapper 
{
    /**
     * 查询实物检查验收
     * 
     * @param id 实物检查验收主键
     * @return 实物检查验收
     */
    public BdglFoodKind selectBdglFoodKindById(Integer id);

    /**
     * 查询实物检查验收列表
     * 
     * @param bdglFoodKind 实物检查验收
     * @return 实物检查验收集合
     */
    public List<BdglFoodKind> selectBdglFoodKindList(BdglFoodKind bdglFoodKind);

    /**
     * 新增实物检查验收
     * 
     * @param bdglFoodKind 实物检查验收
     * @return 结果
     */
    public int insertBdglFoodKind(BdglFoodKind bdglFoodKind);

    /**
     * 修改实物检查验收
     * 
     * @param bdglFoodKind 实物检查验收
     * @return 结果
     */
    public int updateBdglFoodKind(BdglFoodKind bdglFoodKind);

    /**
     * 删除实物检查验收
     * 
     * @param id 实物检查验收主键
     * @return 结果
     */
    public int deleteBdglFoodKindById(Integer id);

    /**
     * 批量删除实物检查验收
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglFoodKindByIds(Integer[] ids);

    int deleteBdglFoodKind(Integer id);
    
    List<BdglFoodKind> selectBdglFoodKindLists(BdglFoodKind bdglFoodKind);
}
