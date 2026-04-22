package com.supervision.grassrootsregistration.mapper;

import java.util.List;
import com.supervision.grassrootsregistration.domain.BdglPublicProperty;
import org.apache.ibatis.annotations.Param;

/**
 * 营产，公物管理登记簿Mapper接口
 * 
 * @author supervision
 * @date 2022-02-26
 */
public interface BdglPublicPropertyMapper 
{
    /**
     * 查询营产，公物管理登记簿
     * 
     * @param id 营产，公物管理登记簿主键
     * @return 营产，公物管理登记簿
     */
    public BdglPublicProperty selectBdglPublicPropertyById(Integer id);

    public List<BdglPublicProperty> selectPublicPropertyunitid(@Param("unitId") Integer unitId, @Param("year") String year);
    /**
     * 查询营产，公物管理登记簿列表
     * 
     * @param bdglPublicProperty 营产，公物管理登记簿
     * @return 营产，公物管理登记簿集合
     */
    public List<BdglPublicProperty> selectBdglPublicPropertyList(BdglPublicProperty bdglPublicProperty);

    /**
     * 新增营产，公物管理登记簿
     * 
     * @param bdglPublicProperty 营产，公物管理登记簿
     * @return 结果
     */
    public int insertBdglPublicProperty(BdglPublicProperty bdglPublicProperty);

    /**
     * 修改营产，公物管理登记簿
     * 
     * @param bdglPublicProperty 营产，公物管理登记簿
     * @return 结果
     */
    public int updateBdglPublicProperty(BdglPublicProperty bdglPublicProperty);

    /**
     * 删除营产，公物管理登记簿
     * 
     * @param id 营产，公物管理登记簿主键
     * @return 结果
     */
    public int deleteBdglPublicPropertyById(Integer id);

    /**
     * 批量删除营产，公物管理登记簿
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglPublicPropertyByIds(Integer[] ids);
    
    int selectPropertyCountByunitId(@Param("unitId") Integer unitId, @Param("year") String year);
}
