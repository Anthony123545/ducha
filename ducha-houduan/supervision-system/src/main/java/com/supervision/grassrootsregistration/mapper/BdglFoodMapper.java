package com.supervision.grassrootsregistration.mapper;

import java.util.List;
import com.supervision.grassrootsregistration.domain.BdglFood;

/**
 * 伙食管理登记薄Mapper接口
 * 
 * @author supervision
 * @date 2022-02-26
 */
public interface BdglFoodMapper 
{
    /**
     * 查询伙食管理登记薄
     * 
     * @param id 伙食管理登记薄主键
     * @return 伙食管理登记薄
     */
    public BdglFood selectBdglFoodById(Integer id);

    /**
     * 查询伙食管理登记薄列表
     * 
     * @param bdglFood 伙食管理登记薄
     * @return 伙食管理登记薄集合
     */
    public List<BdglFood> selectBdglFoodList(BdglFood bdglFood);

    /**
     * 新增伙食管理登记薄
     * 
     * @param bdglFood 伙食管理登记薄
     * @return 结果
     */
    public int insertBdglFood(BdglFood bdglFood);

    /**
     * 修改伙食管理登记薄
     * 
     * @param bdglFood 伙食管理登记薄
     * @return 结果
     */
    public int updateBdglFood(BdglFood bdglFood);

    /**
     * 删除伙食管理登记薄
     * 
     * @param id 伙食管理登记薄主键
     * @return 结果
     */
    public int deleteBdglFoodById(Integer id);

    /**
     * 批量删除伙食管理登记薄
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglFoodByIds(Integer[] ids);
}
