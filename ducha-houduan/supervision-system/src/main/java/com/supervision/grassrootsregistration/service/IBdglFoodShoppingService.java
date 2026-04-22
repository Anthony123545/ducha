package com.supervision.grassrootsregistration.service;

import java.util.List;
import com.supervision.grassrootsregistration.domain.BdglFoodShopping;

/**
 * 在食堂购物Service接口
 * 
 * @author supervision
 * @date 2022-02-28
 */
public interface IBdglFoodShoppingService 
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
     * 批量删除在食堂购物
     * 
     * @param ids 需要删除的在食堂购物主键集合
     * @return 结果
     */
    public int deleteBdglFoodShoppingByIds(Integer[] ids);

    /**
     * 删除在食堂购物信息
     * 
     * @param id 在食堂购物主键
     * @return 结果
     */
    public int deleteBdglFoodShoppingById(Integer id);
}
