package com.supervision.system.service.impl;

import java.util.List;

import com.supervision.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.system.mapper.BdglCookcategoryMapper;
import com.supervision.system.domain.BdglCookcategory;
import com.supervision.system.service.IBdglCookcategoryService;

/**
 * 食品分类Service业务层处理
 * 
 * @author supervision
 * @date 2022-03-08
 */
@Service
public class BdglCookcategoryServiceImpl implements IBdglCookcategoryService 
{
    @Autowired
    private BdglCookcategoryMapper bdglCookcategoryMapper;

    /**
     * 查询食品分类
     * 
     * @param id 食品分类主键
     * @return 食品分类
     */
    @Override
    public BdglCookcategory selectBdglCookcategoryById(Integer id)
    {
        return bdglCookcategoryMapper.selectBdglCookcategoryById(id);
    }

    /**
     * 查询食品分类列表
     * 
     * @param bdglCookcategory 食品分类
     * @return 食品分类
     */
    @Override
    public List<BdglCookcategory> selectBdglCookcategoryList(BdglCookcategory bdglCookcategory)
    {
        return bdglCookcategoryMapper.selectBdglCookcategoryList(bdglCookcategory);
    }

    /**
     * 新增食品分类
     * 
     * @param bdglCookcategory 食品分类
     * @return 结果
     */
    @Override
    public int insertBdglCookcategory(BdglCookcategory bdglCookcategory)
    {
        bdglCookcategory.setCreatetime(DateUtils.getNowDate());
        return bdglCookcategoryMapper.insertBdglCookcategory(bdglCookcategory);
    }

    /**
     * 修改食品分类
     * 
     * @param bdglCookcategory 食品分类
     * @return 结果
     */
    @Override
    public int updateBdglCookcategory(BdglCookcategory bdglCookcategory)
    {
        bdglCookcategory.setUpdateTime(DateUtils.getNowDate());
        return bdglCookcategoryMapper.updateBdglCookcategory(bdglCookcategory);
    }

    /**
     * 批量删除食品分类
     * 
     * @param ids 需要删除的食品分类主键
     * @return 结果
     */
    @Override
    public int deleteBdglCookcategoryByIds(Integer[] ids)
    {
        return bdglCookcategoryMapper.deleteBdglCookcategoryByIds(ids);
    }

    /**
     * 删除食品分类信息
     * 
     * @param id 食品分类主键
     * @return 结果
     */
    @Override
    public int deleteBdglCookcategoryById(Integer id)
    {
        return bdglCookcategoryMapper.deleteBdglCookcategoryById(id);
    }
}
