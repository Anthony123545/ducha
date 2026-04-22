package com.supervision.militaryvehicleManagement.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.militaryvehicleManagement.mapper.BdglDriverMapper;
import com.supervision.militaryvehicleManagement.domain.BdglDriver;
import com.supervision.militaryvehicleManagement.service.IBdglDriverService;

/**
 * 军车司机设置Service业务层处理
 * 
 * @author supervision
 * @date 2022-03-09
 */
@Service
public class BdglDriverServiceImpl implements IBdglDriverService 
{
    @Autowired
    private BdglDriverMapper bdglDriverMapper;

    /**
     * 查询军车司机设置
     * 
     * @param id 军车司机设置主键
     * @return 军车司机设置
     */
    @Override
    public BdglDriver selectBdglDriverById(Integer id)
    {
        return bdglDriverMapper.selectBdglDriverById(id);
    }

    /**
     * 查询军车司机设置列表
     * 
     * @param bdglDriver 军车司机设置
     * @return 军车司机设置
     */
    @Override
    public List<BdglDriver> selectBdglDriverList(BdglDriver bdglDriver)
    {
        return bdglDriverMapper.selectBdglDriverList(bdglDriver);
    }

    /**
     * 新增军车司机设置
     * 
     * @param bdglDriver 军车司机设置
     * @return 结果
     */
    @Override
    public int insertBdglDriver(BdglDriver bdglDriver)
    {
        return bdglDriverMapper.insertBdglDriver(bdglDriver);
    }

    /**
     * 修改军车司机设置
     * 
     * @param bdglDriver 军车司机设置
     * @return 结果
     */
    @Override
    public int updateBdglDriver(BdglDriver bdglDriver)
    {
        return bdglDriverMapper.updateBdglDriver(bdglDriver);
    }

    /**
     * 批量删除军车司机设置
     * 
     * @param ids 需要删除的军车司机设置主键
     * @return 结果
     */
    @Override
    public int deleteBdglDriverByIds(Integer[] ids)
    {
        return bdglDriverMapper.deleteBdglDriverByIds(ids);
    }

    /**
     * 删除军车司机设置信息
     * 
     * @param id 军车司机设置主键
     * @return 结果
     */
    @Override
    public int deleteBdglDriverById(Integer id)
    {
        return bdglDriverMapper.deleteBdglDriverById(id);
    }
}
