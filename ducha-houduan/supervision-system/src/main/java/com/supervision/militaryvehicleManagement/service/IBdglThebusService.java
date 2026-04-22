package com.supervision.militaryvehicleManagement.service;

import java.util.List;
import java.util.Map;

import com.supervision.militaryvehicleManagement.domain.BdglThebus;

/**
 * 军车管理Service接口
 * 
 * @author supervision
 * @date 2022-03-09
 */
public interface IBdglThebusService 
{
    /**
     * 查询军车管理
     * 
     * @param id 军车管理主键
     * @return 军车管理
     */
    public BdglThebus selectBdglThebusById(Long id);

    /**
     * 查询军车管理列表
     * 
     * @param bdglThebus 军车管理
     * @return 军车管理集合
     */
    public List<BdglThebus> selectBdglThebusList(BdglThebus bdglThebus);

    /**
     * 新增军车管理
     * 
     * @param bdglThebus 军车管理
     * @return 结果
     */
    public int insertBdglThebus(BdglThebus bdglThebus);

    /**
     * 修改军车管理
     * 
     * @param bdglThebus 军车管理
     * @return 结果
     */
    public int updateBdglThebus(BdglThebus bdglThebus);

    /**
     * 批量删除军车管理
     * 
     * @param ids 需要删除的军车管理主键集合
     * @return 结果
     */
    public int deleteBdglThebusByIds(Long[] ids);

    /**
     * 删除军车管理信息
     * 
     * @param id 军车管理主键
     * @return 结果
     */
    public int deleteBdglThebusById(Long id);

    /**
     * 修改值班时间小于当前日期的值班车辆的状态为在位状态
     * @return 结果
     */
    public int updateBdglThebusStatus();

    List<Map<String, Object>> selectVehicleModelList();

    BdglThebus selectBdglThebusByVehicleNumber(String license);
}
