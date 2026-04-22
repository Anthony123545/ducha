package com.supervision.grassrootsregistration.mapper;

import java.util.List;
import com.supervision.grassrootsregistration.domain.BdglCookbook;

/**
 * 一周食谱Mapper接口
 * 
 * @author supervision
 * @date 2022-02-26
 */
public interface BdglCookbookMapper 
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
     * 删除一周食谱
     * 
     * @param id 一周食谱主键
     * @return 结果
     */
    public int deleteBdglCookbookById(Integer id);

    /**
     * 批量删除一周食谱
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglCookbookByIds(Integer[] ids);
}
