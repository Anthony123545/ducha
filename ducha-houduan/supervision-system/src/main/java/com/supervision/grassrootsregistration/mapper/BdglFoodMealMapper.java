package com.supervision.grassrootsregistration.mapper;

import java.util.List;
import com.supervision.grassrootsregistration.domain.BdglFoodMeal;

/**
 * 一日三餐Mapper接口
 * 
 * @author supervision
 * @date 2022-03-01
 */
public interface BdglFoodMealMapper 
{
    /**
     * 查询一日三餐
     * 
     * @param id 一日三餐主键
     * @return 一日三餐
     */
    public BdglFoodMeal selectBdglFoodMealById(Integer id);

    /**
     * 查询一日三餐列表
     * 
     * @param bdglFoodMeal 一日三餐
     * @return 一日三餐集合
     */
    public List<BdglFoodMeal> selectBdglFoodMealList(BdglFoodMeal bdglFoodMeal);

    /**
     * 新增一日三餐
     * 
     * @param bdglFoodMeal 一日三餐
     * @return 结果
     */
    public int insertBdglFoodMeal(BdglFoodMeal bdglFoodMeal);

    /**
     * 修改一日三餐
     * 
     * @param bdglFoodMeal 一日三餐
     * @return 结果
     */
    public int updateBdglFoodMeal(BdglFoodMeal bdglFoodMeal);

    /**
     * 删除一日三餐
     * 
     * @param id 一日三餐主键
     * @return 结果
     */
    public int deleteBdglFoodMealById(Integer id);

    /**
     * 批量删除一日三餐
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglFoodMealByIds(Integer[] ids);
}
