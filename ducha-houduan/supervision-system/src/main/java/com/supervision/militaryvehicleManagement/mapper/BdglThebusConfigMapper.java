package com.supervision.militaryvehicleManagement.mapper;

import java.util.List;
import com.supervision.militaryvehicleManagement.domain.BdglThebusConfig;

/**
 * 军车管理设置Mapper接口
 * 
 * @author supervision
 * @date 2022-03-04
 */
public interface BdglThebusConfigMapper 
{
    /**
     * 查询军车管理设置
     * 
     * @param id 军车管理设置主键
     * @return 军车管理设置
     */
    public BdglThebusConfig selectBdglThebusConfigById(Integer id);

    /**
     * 查询军车管理设置列表
     * 
     * @param bdglThebusConfig 军车管理设置
     * @return 军车管理设置集合
     */
    public List<BdglThebusConfig> selectBdglThebusConfigList(BdglThebusConfig bdglThebusConfig);

    /**
     * 新增军车管理设置
     * 
     * @param bdglThebusConfig 军车管理设置
     * @return 结果
     */
    public int insertBdglThebusConfig(BdglThebusConfig bdglThebusConfig);

    /**
     * 修改军车管理设置
     * 
     * @param bdglThebusConfig 军车管理设置
     * @return 结果
     */
    public int updateBdglThebusConfig(BdglThebusConfig bdglThebusConfig);

    /**
     * 删除军车管理设置
     * 
     * @param id 军车管理设置主键
     * @return 结果
     */
    public int deleteBdglThebusConfigById(Integer id);

    /**
     * 批量删除军车管理设置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglThebusConfigByIds(Integer[] ids);
}
