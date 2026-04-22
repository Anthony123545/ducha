package com.supervision.combatduty.service;

import java.util.List;
import com.supervision.combatduty.domain.BdglBaseduty;

/**
 * 基地值班Service接口
 * 
 * @author supervision
 * @date 2022-03-10
 */
public interface IBdglBasedutyService 
{
    /**
     * 查询基地值班
     * 
     * @param id 基地值班主键
     * @return 基地值班
     */
    public BdglBaseduty selectBdglBasedutyById(Long id);

    /**
     * 查询基地值班列表
     * 
     * @param bdglBaseduty 基地值班
     * @return 基地值班集合
     */
    public List<BdglBaseduty> selectBdglBasedutyList(BdglBaseduty bdglBaseduty);

    /**
     * 新增基地值班
     * 
     * @param bdglBaseduty 基地值班
     * @return 结果
     */
    public int insertBdglBaseduty(BdglBaseduty bdglBaseduty);

    /**
     * 修改基地值班
     * 
     * @param bdglBaseduty 基地值班
     * @return 结果
     */
    public int updateBdglBaseduty(BdglBaseduty bdglBaseduty);

    /**
     * 批量删除基地值班
     * 
     * @param ids 需要删除的基地值班主键集合
     * @return 结果
     */
    public int deleteBdglBasedutyByIds(Long[] ids);

    /**
     * 删除基地值班信息
     * 
     * @param id 基地值班主键
     * @return 结果
     */
    public int deleteBdglBasedutyById(Long id);
}
