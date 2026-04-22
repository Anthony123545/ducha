package com.supervision.peopleChuRu.service;

import java.util.List;
import com.supervision.peopleChuRu.domain.BdglVisitorEnter;

/**
 * 访客进入Service接口
 * 
 * @author supervision
 * @date 2022-03-02
 */
public interface IBdglVisitorEnterService 
{
    /**
     * 查询访客进入
     * 
     * @param id 访客进入主键
     * @return 访客进入
     */
    public BdglVisitorEnter selectBdglVisitorEnterById(Long id);

    /**
     * 查询访客进入列表
     * 
     * @param bdglVisitorEnter 访客进入
     * @return 访客进入集合
     */
    public List<BdglVisitorEnter> selectBdglVisitorEnterList(BdglVisitorEnter bdglVisitorEnter);

    /**
     * 新增访客进入
     * 
     * @param bdglVisitorEnter 访客进入
     * @return 结果
     */
    public int insertBdglVisitorEnter(BdglVisitorEnter bdglVisitorEnter);

    /**
     * 修改访客进入
     * 
     * @param bdglVisitorEnter 访客进入
     * @return 结果
     */
    public int updateBdglVisitorEnter(BdglVisitorEnter bdglVisitorEnter);

    /**
     * 批量删除访客进入
     * 
     * @param ids 需要删除的访客进入主键集合
     * @return 结果
     */
    public int deleteBdglVisitorEnterByIds(Long[] ids);

    /**
     * 删除访客进入信息
     * 
     * @param id 访客进入主键
     * @return 结果
     */
    public int deleteBdglVisitorEnterById(Long id);
    
    List<BdglVisitorEnter> selectBdglVisitorEnterListes();
    
    List<BdglVisitorEnter> selectBdglVisitorEnterLists();
    
    List<BdglVisitorEnter> selectBdglVisitorEnterListsss(BdglVisitorEnter bdglVisitorEnter);

    List<BdglVisitorEnter> selectBdglVisitorEnterByPeopleId(Long id);
}