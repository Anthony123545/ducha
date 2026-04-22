package com.supervision.grassrootsregistration.service;

import java.util.List;
import com.supervision.grassrootsregistration.domain.BdglArmament;
import com.supervision.grassrootsregistration.resultVo.SysDeptDto;

/**
 * 军械装备登记簿Service接口
 * 
 * @author supervision
 * @date 2022-02-26
 */
public interface IBdglArmamentService 
{
    /**
     * 查询军械装备登记簿
     * 
     * @param id 军械装备登记簿主键
     * @return 军械装备登记簿
     */
    public BdglArmament selectBdglArmamentById(Integer id);

    public List<BdglArmament> selectArmamentunitid(Integer unitid,String year);

    /**
     * 查询军械装备登记簿列表
     * 
     * @param bdglArmament 军械装备登记簿
     * @return 军械装备登记簿集合
     */
    public List<BdglArmament> selectBdglArmamentList(BdglArmament bdglArmament);

    /**
     * 新增军械装备登记簿
     * 
     * @param bdglArmament 军械装备登记簿
     * @return 结果
     */
    public int insertBdglArmament(BdglArmament bdglArmament);

    /**
     * 修改军械装备登记簿
     * 
     * @param bdglArmament 军械装备登记簿
     * @return 结果
     */
    public int updateBdglArmament(BdglArmament bdglArmament);

    /**
     * 批量删除军械装备登记簿
     * 
     * @param ids 需要删除的军械装备登记簿主键集合
     * @return 结果
     */
    public int deleteBdglArmamentByIds(Integer[] ids);

    /**
     * 删除军械装备登记簿信息
     * 
     * @param id 军械装备登记簿主键
     * @return 结果
     */
    public int deleteBdglArmamentById(Integer id);
    
    List<SysDeptDto> selectArmamentList(String year);
}
