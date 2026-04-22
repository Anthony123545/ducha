package com.supervision.combatduty.mapper;

import java.util.List;
import com.supervision.combatduty.domain.BdglBaseduty;

/**
 * 基地值班Mapper接口
 * 
 * @author supervision
 * @date 2022-03-10
 */
public interface BdglBasedutyMapper 
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
     * 删除基地值班
     * 
     * @param id 基地值班主键
     * @return 结果
     */
    public int deleteBdglBasedutyById(Long id);

    /**
     * 批量删除基地值班
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglBasedutyByIds(Long[] ids);
}
