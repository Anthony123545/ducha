package com.supervision.combatduty.service;

import java.util.List;
import com.supervision.combatduty.domain.BdglGrassduty;

/**
 * 基层值班Service接口
 * 
 * @author supervision
 * @date 2022-03-10
 */
public interface IBdglGrassdutyService 
{
    /**
     * 查询基层值班
     * 
     * @param id 基层值班主键
     * @return 基层值班
     */
    public BdglGrassduty selectBdglGrassdutyById(Long id);

    /**
     * 查询基层值班列表
     * 
     * @param bdglGrassduty 基层值班
     * @return 基层值班集合
     */
    public List<BdglGrassduty> selectBdglGrassdutyList(BdglGrassduty bdglGrassduty);

    /**
     * 新增基层值班
     * 
     * @param bdglGrassduty 基层值班
     * @return 结果
     */
    public int insertBdglGrassduty(BdglGrassduty bdglGrassduty);

    /**
     * 修改基层值班
     * 
     * @param bdglGrassduty 基层值班
     * @return 结果
     */
    public int updateBdglGrassduty(BdglGrassduty bdglGrassduty);

    /**
     * 批量删除基层值班
     * 
     * @param ids 需要删除的基层值班主键集合
     * @return 结果
     */
    public int deleteBdglGrassdutyByIds(Long[] ids);

    /**
     * 删除基层值班信息
     * 
     * @param id 基层值班主键
     * @return 结果
     */
    public int deleteBdglGrassdutyById(Long id);

    List<BdglGrassduty> selectBdglGrassdutyLists(BdglGrassduty bdglGrassduty1);
    
    List<BdglGrassduty> selectGrassdutyList(BdglGrassduty bdglGrassduty);
}
