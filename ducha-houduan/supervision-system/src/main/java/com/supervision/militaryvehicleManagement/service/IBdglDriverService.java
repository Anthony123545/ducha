package com.supervision.militaryvehicleManagement.service;

import java.util.List;
import com.supervision.militaryvehicleManagement.domain.BdglDriver;

/**
 * 军车司机设置Service接口
 * 
 * @author supervision
 * @date 2022-03-09
 */
public interface IBdglDriverService 
{
    /**
     * 查询军车司机设置
     * 
     * @param id 军车司机设置主键
     * @return 军车司机设置
     */
    public BdglDriver selectBdglDriverById(Integer id);

    /**
     * 查询军车司机设置列表
     * 
     * @param bdglDriver 军车司机设置
     * @return 军车司机设置集合
     */
    public List<BdglDriver> selectBdglDriverList(BdglDriver bdglDriver);

    /**
     * 新增军车司机设置
     * 
     * @param bdglDriver 军车司机设置
     * @return 结果
     */
    public int insertBdglDriver(BdglDriver bdglDriver);

    /**
     * 修改军车司机设置
     * 
     * @param bdglDriver 军车司机设置
     * @return 结果
     */
    public int updateBdglDriver(BdglDriver bdglDriver);

    /**
     * 批量删除军车司机设置
     * 
     * @param ids 需要删除的军车司机设置主键集合
     * @return 结果
     */
    public int deleteBdglDriverByIds(Integer[] ids);

    /**
     * 删除军车司机设置信息
     * 
     * @param id 军车司机设置主键
     * @return 结果
     */
    public int deleteBdglDriverById(Integer id);
}
