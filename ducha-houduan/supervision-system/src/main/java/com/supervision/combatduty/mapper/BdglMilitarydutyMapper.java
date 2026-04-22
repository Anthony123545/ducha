package com.supervision.combatduty.mapper;

import java.util.List;
import com.supervision.combatduty.domain.BdglMilitaryduty;

/**
 * 军车值班Mapper接口
 * 
 * @author supervision
 * @date 2022-03-11
 */
public interface BdglMilitarydutyMapper 
{
    /**
     * 查询军车值班
     * 
     * @param id 军车值班主键
     * @return 军车值班
     */
    public BdglMilitaryduty selectBdglMilitarydutyById(Long id);

    /**
     * 查询军车值班列表
     * 
     * @param bdglMilitaryduty 军车值班
     * @return 军车值班集合
     */
    public List<BdglMilitaryduty> selectBdglMilitarydutyList(BdglMilitaryduty bdglMilitaryduty);

    /**
     * 新增军车值班
     * 
     * @param bdglMilitaryduty 军车值班
     * @return 结果
     */
    public int insertBdglMilitaryduty(BdglMilitaryduty bdglMilitaryduty);

    /**
     * 修改军车值班
     * 
     * @param bdglMilitaryduty 军车值班
     * @return 结果
     */
    public int updateBdglMilitaryduty(BdglMilitaryduty bdglMilitaryduty);

    /**
     * 删除军车值班
     * 
     * @param id 军车值班主键
     * @return 结果
     */
    public int deleteBdglMilitarydutyById(Long id);

    /**
     * 批量删除军车值班
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglMilitarydutyByIds(Long[] ids);

    /**
     * 查询大于等于当前时间的军车值班
     * @return 结果
     */
    public List<BdglMilitaryduty> selectDutyListByAttendedTime();

}
