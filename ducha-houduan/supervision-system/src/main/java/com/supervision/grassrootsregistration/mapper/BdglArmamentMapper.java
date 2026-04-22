package com.supervision.grassrootsregistration.mapper;

import java.util.List;
import com.supervision.grassrootsregistration.domain.BdglArmament;
import org.apache.ibatis.annotations.Param;

/**
 * 军械装备登记簿Mapper接口
 * 
 * @author supervision
 * @date 2022-02-26
 */
public interface BdglArmamentMapper 
{
    /**
     * 查询军械装备登记簿
     * 
     * @param id 军械装备登记簿主键
     * @return 军械装备登记簿
     */
    public BdglArmament selectBdglArmamentById(Integer id);

    public List<BdglArmament> selectArmamentunitid(@Param("unitId") Integer unitId, @Param("year") String year);
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
     * 删除军械装备登记簿
     * 
     * @param id 军械装备登记簿主键
     * @return 结果
     */
    public int deleteBdglArmamentById(Integer id);

    /**
     * 批量删除军械装备登记簿
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglArmamentByIds(Integer[] ids);
    
    int selectlArmamentCountByunitId(@Param("unitId") Integer unitId, @Param("year") String year);
}
