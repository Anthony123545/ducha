package com.supervision.barracksManagement.service;

import java.util.List;
import com.supervision.barracksManagement.domain.BdglBarracks;

/**
 * 营房档案Service接口
 * 
 * @author supervision
 * @date 2022-03-02
 */
public interface IBdglBarracksService 
{
    /**
     * 查询营房档案
     * 
     * @param id 营房档案主键
     * @return 营房档案
     */
    public BdglBarracks selectBdglBarracksById(Integer id);

    /**
     * 查询营房档案列表
     * 
     * @param bdglBarracks 营房档案
     * @return 营房档案集合
     */
    public List<BdglBarracks> selectBdglBarracksList(BdglBarracks bdglBarracks);

    /**
     * 新增营房档案
     * 
     * @param bdglBarracks 营房档案
     * @return 结果
     */
    public int insertBdglBarracks(BdglBarracks bdglBarracks);

    /**
     * 修改营房档案
     * 
     * @param bdglBarracks 营房档案
     * @return 结果
     */
    public int updateBdglBarracks(BdglBarracks bdglBarracks);

    /**
     * 批量删除营房档案
     * 
     * @param ids 需要删除的营房档案主键集合
     * @return 结果
     */
    public int deleteBdglBarracksByIds(Integer[] ids);

    /**
     * 删除营房档案信息
     * 
     * @param id 营房档案主键
     * @return 结果
     */
    public int deleteBdglBarracksById(Integer id);
    
    Integer selectBdglBarracksCount();
}
