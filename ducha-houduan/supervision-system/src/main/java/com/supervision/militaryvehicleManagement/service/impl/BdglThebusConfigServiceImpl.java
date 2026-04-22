package com.supervision.militaryvehicleManagement.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.militaryvehicleManagement.mapper.BdglThebusConfigMapper;
import com.supervision.militaryvehicleManagement.domain.BdglThebusConfig;
import com.supervision.militaryvehicleManagement.service.IBdglThebusConfigService;

/**
 * 军车管理设置Service业务层处理
 * 
 * @author supervision
 * @date 2022-03-04
 */
@Service
public class BdglThebusConfigServiceImpl implements IBdglThebusConfigService 
{
    @Autowired
    private BdglThebusConfigMapper bdglThebusConfigMapper;

    /**
     * 查询军车管理设置
     * 
     * @param id 军车管理设置主键
     * @return 军车管理设置
     */
    @Override
    public BdglThebusConfig selectBdglThebusConfigById(Integer id)
    {
        return bdglThebusConfigMapper.selectBdglThebusConfigById(id);
    }

    /**
     * 查询军车管理设置列表
     * 
     * @param bdglThebusConfig 军车管理设置
     * @return 军车管理设置
     */
    @Override
    public List<BdglThebusConfig> selectBdglThebusConfigList(BdglThebusConfig bdglThebusConfig)
    {
        return bdglThebusConfigMapper.selectBdglThebusConfigList(bdglThebusConfig);
    }

    /**
     * 新增军车管理设置
     * 
     * @param bdglThebusConfig 军车管理设置
     * @return 结果
     */
    @Override
    public int insertBdglThebusConfig(BdglThebusConfig bdglThebusConfig)
    {
        return bdglThebusConfigMapper.insertBdglThebusConfig(bdglThebusConfig);
    }

    /**
     * 修改军车管理设置
     * 
     * @param bdglThebusConfig 军车管理设置
     * @return 结果
     */
    @Override
    public int updateBdglThebusConfig(BdglThebusConfig bdglThebusConfig)
    {
        return bdglThebusConfigMapper.updateBdglThebusConfig(bdglThebusConfig);
    }

    /**
     * 批量删除军车管理设置
     * 
     * @param ids 需要删除的军车管理设置主键
     * @return 结果
     */
    @Override
    public int deleteBdglThebusConfigByIds(Integer[] ids)
    {
        return bdglThebusConfigMapper.deleteBdglThebusConfigByIds(ids);
    }

    /**
     * 删除军车管理设置信息
     * 
     * @param id 军车管理设置主键
     * @return 结果
     */
    @Override
    public int deleteBdglThebusConfigById(Integer id)
    {
        return bdglThebusConfigMapper.deleteBdglThebusConfigById(id);
    }
}
