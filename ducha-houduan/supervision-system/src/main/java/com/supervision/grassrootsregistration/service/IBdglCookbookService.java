package com.supervision.grassrootsregistration.service;

import java.util.HashMap;
import java.util.List;
import com.supervision.grassrootsregistration.domain.BdglCookbook;
import com.supervision.system.domain.BdglCookcategory;

/**
 * 一周食谱Service接口
 * 
 * @author supervision
 * @date 2022-02-26
 */
public interface IBdglCookbookService 
{
    /**
     * 查询一周食谱
     * 
     * @param id 一周食谱主键
     * @return 一周食谱
     */
    public BdglCookbook selectBdglCookbookById(Integer id);

    /**
     * 查询一周食谱列表
     * 
     * @param bdglCookbook 一周食谱
     * @return 一周食谱集合
     */
    public List<BdglCookbook> selectBdglCookbookList(BdglCookbook bdglCookbook);

    /**
     * 新增一周食谱
     * 
     * @param bdglCookbook 一周食谱
     * @return 结果
     */
    public int insertBdglCookbook(BdglCookbook bdglCookbook);

    /**
     * 修改一周食谱
     * 
     * @param bdglCookbook 一周食谱
     * @return 结果
     */
    public int updateBdglCookbook(BdglCookbook bdglCookbook);

    /**
     * 批量删除一周食谱
     * 
     * @param ids 需要删除的一周食谱主键集合
     * @return 结果
     */
    public int deleteBdglCookbookByIds(Integer[] ids);

    /**
     * 删除一周食谱信息
     * 
     * @param id 一周食谱主键
     * @return 结果
     */
    public int deleteBdglCookbookById(Integer id);

    HashMap<String, Object> getFood();
}
