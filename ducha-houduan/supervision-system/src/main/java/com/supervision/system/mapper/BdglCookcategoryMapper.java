package com.supervision.system.mapper;

import java.util.List;
import com.supervision.system.domain.BdglCookcategory;
import org.apache.ibatis.annotations.Param;

/**
 * 食品分类Mapper接口
 * 
 * @author supervision
 * @date 2022-03-08
 */
public interface BdglCookcategoryMapper 
{
    /**
     * 查询食品分类
     * 
     * @param id 食品分类主键
     * @return 食品分类
     */
    public BdglCookcategory selectBdglCookcategoryById(Integer id);

    /**
     * 查询食品分类列表
     * 
     * @param bdglCookcategory 食品分类
     * @return 食品分类集合
     */
    public List<BdglCookcategory> selectBdglCookcategoryList(BdglCookcategory bdglCookcategory);

    /**
     * 新增食品分类
     * 
     * @param bdglCookcategory 食品分类
     * @return 结果
     */
    public int insertBdglCookcategory(BdglCookcategory bdglCookcategory);

    /**
     * 修改食品分类
     * 
     * @param bdglCookcategory 食品分类
     * @return 结果
     */
    public int updateBdglCookcategory(BdglCookcategory bdglCookcategory);

    /**
     * 删除食品分类
     * 
     * @param id 食品分类主键
     * @return 结果
     */
    public int deleteBdglCookcategoryById(Integer id);

    /**
     * 批量删除食品分类
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglCookcategoryByIds(Integer[] ids);

    List<BdglCookcategory> select(@Param("split") String[] split);
}
