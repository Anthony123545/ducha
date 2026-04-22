package com.supervision.grassrootsregistration.mapper;

import java.util.List;
import com.supervision.grassrootsregistration.domain.BdglFoodShopping;

/**
 * 在食堂购物Mapper接口
 * 
 * @author supervision
 * @date 2022-02-28
 */
public interface BdglFoodShoppingMapper 
{
    /**
     * 查询在食堂购物
     * 
     * @param id 在食堂购物主键
     * @return 在食堂购物
     */
    public BdglFoodShopping selectBdglFoodShoppingById(Integer id);

    /**
     * 查询在食堂购物列表
     * 
     * @param bdglFoodShopping 在食堂购物
     * @return 在食堂购物集合
     */
    public List<BdglFoodShopping> selectBdglFoodShoppingList(BdglFoodShopping bdglFoodShopping);

    /**
     * 新增在食堂购物
     * 
     * @param bdglFoodShopping 在食堂购物
     * @return 结果
     */
    public int insertBdglFoodShopping(BdglFoodShopping bdglFoodShopping);

    /**
     * 修改在食堂购物
     * 
     * @param bdglFoodShopping 在食堂购物
     * @return 结果
     */
    public int updateBdglFoodShopping(BdglFoodShopping bdglFoodShopping);

    /**
     * 删除在食堂购物
     * 
     * @param id 在食堂购物主键
     * @return 结果
     */
    public int deleteBdglFoodShoppingById(Integer id);

    /**
     * 批量删除在食堂购物
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglFoodShoppingByIds(Integer[] ids);

    int deleteBdglFoodShopping(Integer foodId);

}
