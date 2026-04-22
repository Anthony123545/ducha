package com.supervision.grassrootsregistration.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.grassrootsregistration.mapper.BdglFoodMealMapper;
import com.supervision.grassrootsregistration.domain.BdglFoodMeal;
import com.supervision.grassrootsregistration.service.IBdglFoodMealService;

/**
 * 一日三餐Service业务层处理
 * 
 * @author supervision
 * @date 2022-03-01
 */
@Service
public class BdglFoodMealServiceImpl implements IBdglFoodMealService 
{
    @Autowired
    private BdglFoodMealMapper bdglFoodMealMapper;

    /**
     * 查询一日三餐
     * 
     * @param id 一日三餐主键
     * @return 一日三餐
     */
    @Override
    public BdglFoodMeal selectBdglFoodMealById(Integer id)
    {
        return bdglFoodMealMapper.selectBdglFoodMealById(id);
    }

    /**
     * 查询一日三餐列表
     * 
     * @param bdglFoodMeal 一日三餐
     * @return 一日三餐
     */
    @Override
    public List<BdglFoodMeal> selectBdglFoodMealList(BdglFoodMeal bdglFoodMeal)
    {
        return bdglFoodMealMapper.selectBdglFoodMealList(bdglFoodMeal);
    }

    /**
     * 新增一日三餐
     * 
     * @param bdglFoodMeal 一日三餐
     * @return 结果
     */
    @Override
    public int insertBdglFoodMeal(BdglFoodMeal bdglFoodMeal)
    {
        return bdglFoodMealMapper.insertBdglFoodMeal(bdglFoodMeal);
    }

    /**
     * 修改一日三餐
     * 
     * @param bdglFoodMeal 一日三餐
     * @return 结果
     */
    @Override
    public int updateBdglFoodMeal(BdglFoodMeal bdglFoodMeal)
    {
        return bdglFoodMealMapper.updateBdglFoodMeal(bdglFoodMeal);
    }

    /**
     * 批量删除一日三餐
     * 
     * @param ids 需要删除的一日三餐主键
     * @return 结果
     */
    @Override
    public int deleteBdglFoodMealByIds(Integer[] ids)
    {
        return bdglFoodMealMapper.deleteBdglFoodMealByIds(ids);
    }

    /**
     * 删除一日三餐信息
     * 
     * @param id 一日三餐主键
     * @return 结果
     */
    @Override
    public int deleteBdglFoodMealById(Integer id)
    {
        return bdglFoodMealMapper.deleteBdglFoodMealById(id);
    }
}
