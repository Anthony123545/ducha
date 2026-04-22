package com.supervision.combatduty.service;

import java.util.List;
import com.supervision.combatduty.domain.BdglRegisDuty;

/**
 * 装备登记Service接口
 * 
 * @author supervision
 * @date 2022-03-11
 */
public interface IBdglRegisDutyService 
{
    /**
     * 查询装备登记
     * 
     * @param id 装备登记主键
     * @return 装备登记
     */
    public BdglRegisDuty selectBdglRegisDutyById(Long id);

    /**
     * 查询装备登记列表
     * 
     * @param bdglRegisDuty 装备登记
     * @return 装备登记集合
     */
    public List<BdglRegisDuty> selectBdglRegisDutyList(BdglRegisDuty bdglRegisDuty);

    /**
     * 新增装备登记
     * 
     * @param bdglRegisDuty 装备登记
     * @return 结果
     */
    public int insertBdglRegisDuty(BdglRegisDuty bdglRegisDuty);

    /**
     * 修改装备登记
     * 
     * @param bdglRegisDuty 装备登记
     * @return 结果
     */
    public int updateBdglRegisDuty(BdglRegisDuty bdglRegisDuty);

    /**
     * 批量删除装备登记
     * 
     * @param ids 需要删除的装备登记主键集合
     * @return 结果
     */
    public int deleteBdglRegisDutyByIds(Long[] ids);

    /**
     * 删除装备登记信息
     * 
     * @param id 装备登记主键
     * @return 结果
     */
    public int deleteBdglRegisDutyById(Long id);

    public List<BdglRegisDuty> selectBdglRegisDutyNumber();
}
