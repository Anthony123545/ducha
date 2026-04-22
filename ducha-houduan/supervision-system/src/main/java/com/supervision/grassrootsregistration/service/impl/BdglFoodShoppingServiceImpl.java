package com.supervision.grassrootsregistration.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.grassrootsregistration.mapper.BdglFoodShoppingMapper;
import com.supervision.grassrootsregistration.domain.BdglFoodShopping;
import com.supervision.grassrootsregistration.service.IBdglFoodShoppingService;

/**
 * 在食堂购物Service业务层处理
 * 
 * @author supervision
 * @date 2022-02-28
 */
@Service
public class BdglFoodShoppingServiceImpl implements IBdglFoodShoppingService 
{
    @Autowired
    private BdglFoodShoppingMapper bdglFoodShoppingMapper;

    /**
     * 查询在食堂购物
     * 
     * @param id 在食堂购物主键
     * @return 在食堂购物
     */
    @Override
    public BdglFoodShopping selectBdglFoodShoppingById(Integer id)
    {
        return bdglFoodShoppingMapper.selectBdglFoodShoppingById(id);
    }

    /**
     * 查询在食堂购物列表
     * 
     * @param bdglFoodShopping 在食堂购物
     * @return 在食堂购物
     */
    @Override
    public List<BdglFoodShopping> selectBdglFoodShoppingList(BdglFoodShopping bdglFoodShopping)
    {
        return bdglFoodShoppingMapper.selectBdglFoodShoppingList(bdglFoodShopping);
    }

    /**
     * 新增在食堂购物
     * 
     * @param bdglFoodShopping 在食堂购物
     * @return 结果
     */
    @Override
    public int insertBdglFoodShopping(BdglFoodShopping bdglFoodShopping)
    {
        return bdglFoodShoppingMapper.insertBdglFoodShopping(bdglFoodShopping);
    }

    /**
     * 修改在食堂购物
     * 
     * @param bdglFoodShopping 在食堂购物
     * @return 结果
     */
    @Override
    public int updateBdglFoodShopping(BdglFoodShopping bdglFoodShopping)
    {
        return bdglFoodShoppingMapper.updateBdglFoodShopping(bdglFoodShopping);
    }

    /**
     * 批量删除在食堂购物
     * 
     * @param ids 需要删除的在食堂购物主键
     * @return 结果
     */
    @Override
    public int deleteBdglFoodShoppingByIds(Integer[] ids)
    {
        return bdglFoodShoppingMapper.deleteBdglFoodShoppingByIds(ids);
    }

    /**
     * 删除在食堂购物信息
     * 
     * @param id 在食堂购物主键
     * @return 结果
     */
    @Override
    public int deleteBdglFoodShoppingById(Integer id)
    {
        return bdglFoodShoppingMapper.deleteBdglFoodShoppingById(id);
    }
}
