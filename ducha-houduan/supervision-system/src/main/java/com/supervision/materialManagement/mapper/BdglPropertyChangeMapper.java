package com.supervision.materialManagement.mapper;

import java.util.List;
import com.supervision.materialManagement.domain.BdglPropertyChange;

/**
 * 营具物资变更Mapper接口
 * 
 * @author supervision
 * @date 2022-03-08
 */
public interface BdglPropertyChangeMapper 
{
    /**
     * 查询营具物资变更
     * 
     * @param id 营具物资变更主键
     * @return 营具物资变更
     */
    public BdglPropertyChange selectBdglPropertyChangeById(Integer id);

    /**
     * 查询营具物资变更列表
     * 
     * @param bdglPropertyChange 营具物资变更
     * @return 营具物资变更集合
     */
    public List<BdglPropertyChange> selectBdglPropertyChangeList(BdglPropertyChange bdglPropertyChange);

    /**
     * 新增营具物资变更
     * 
     * @param bdglPropertyChange 营具物资变更
     * @return 结果
     */
    public int insertBdglPropertyChange(BdglPropertyChange bdglPropertyChange);

    /**
     * 修改营具物资变更
     * 
     * @param bdglPropertyChange 营具物资变更
     * @return 结果
     */
    public int updateBdglPropertyChange(BdglPropertyChange bdglPropertyChange);

    /**
     * 删除营具物资变更
     * 
     * @param id 营具物资变更主键
     * @return 结果
     */
    public int deleteBdglPropertyChangeById(Integer id);

    /**
     * 批量删除营具物资变更
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglPropertyChangeByIds(Integer[] ids);
}
