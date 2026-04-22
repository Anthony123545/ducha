package com.supervision.grassrootsregistration.service;

import java.util.List;
import com.supervision.grassrootsregistration.domain.BdglCookbookPrc;

/**
 * 一周工作食普图Service接口
 * 
 * @author ruoyi
 * @date 2023-03-08
 */
public interface IBdglCookbookPrcService 
{
    /**
     * 查询一周工作食普图
     * 
     * @param id 一周工作食普图主键
     * @return 一周工作食普图
     */
    public BdglCookbookPrc selectBdglCookbookPrcById(Long id);

    /**
     * 查询一周工作食普图列表
     * 
     * @param bdglCookbookPrc 一周工作食普图
     * @return 一周工作食普图集合
     */
    public List<BdglCookbookPrc> selectBdglCookbookPrcList(BdglCookbookPrc bdglCookbookPrc);

    /**
     * 新增一周工作食普图
     * 
     * @param bdglCookbookPrc 一周工作食普图
     * @return 结果
     */
    public int insertBdglCookbookPrc(BdglCookbookPrc bdglCookbookPrc);

    /**
     * 修改一周工作食普图
     * 
     * @param bdglCookbookPrc 一周工作食普图
     * @return 结果
     */
    public int updateBdglCookbookPrc(BdglCookbookPrc bdglCookbookPrc);

    /**
     * 批量删除一周工作食普图
     * 
     * @param ids 需要删除的一周工作食普图主键集合
     * @return 结果
     */
    public int deleteBdglCookbookPrcByIds(Long[] ids);

    /**
     * 删除一周工作食普图信息
     * 
     * @param id 一周工作食普图主键
     * @return 结果
     */
    public int deleteBdglCookbookPrcById(Long id);
}
