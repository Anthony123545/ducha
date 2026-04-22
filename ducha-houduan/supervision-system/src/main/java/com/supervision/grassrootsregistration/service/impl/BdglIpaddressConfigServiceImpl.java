package com.supervision.grassrootsregistration.service.impl;

import java.util.List;
import com.supervision.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.grassrootsregistration.mapper.BdglIpaddressConfigMapper;
import com.supervision.grassrootsregistration.domain.BdglIpaddressConfig;
import com.supervision.grassrootsregistration.service.IBdglIpaddressConfigService;

/**
 * 获取考勤记录的IP地址配置Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-11-09
 */
@Service
public class BdglIpaddressConfigServiceImpl implements IBdglIpaddressConfigService 
{
    @Autowired
    private BdglIpaddressConfigMapper bdglIpaddressConfigMapper;

    /**
     * 查询获取考勤记录的IP地址配置
     * 
     * @param id 获取考勤记录的IP地址配置主键
     * @return 获取考勤记录的IP地址配置
     */
    @Override
    public BdglIpaddressConfig selectBdglIpaddressConfigById(Integer id)
    {
        return bdglIpaddressConfigMapper.selectBdglIpaddressConfigById(id);
    }

    /**
     * 查询获取考勤记录的IP地址配置列表
     * 
     * @param bdglIpaddressConfig 获取考勤记录的IP地址配置
     * @return 获取考勤记录的IP地址配置
     */
    @Override
    public List<BdglIpaddressConfig> selectBdglIpaddressConfigList(BdglIpaddressConfig bdglIpaddressConfig)
    {
        return bdglIpaddressConfigMapper.selectBdglIpaddressConfigList(bdglIpaddressConfig);
    }

    /**
     * 新增获取考勤记录的IP地址配置
     * 
     * @param bdglIpaddressConfig 获取考勤记录的IP地址配置
     * @return 结果
     */
    @Override
    public int insertBdglIpaddressConfig(BdglIpaddressConfig bdglIpaddressConfig)
    {
        bdglIpaddressConfig.setCreateTime(DateUtils.getNowDate());
        return bdglIpaddressConfigMapper.insertBdglIpaddressConfig(bdglIpaddressConfig);
    }

    /**
     * 修改获取考勤记录的IP地址配置
     * 
     * @param bdglIpaddressConfig 获取考勤记录的IP地址配置
     * @return 结果
     */
    @Override
    public int updateBdglIpaddressConfig(BdglIpaddressConfig bdglIpaddressConfig)
    {
        bdglIpaddressConfig.setUpdateTime(DateUtils.getNowDate());
        return bdglIpaddressConfigMapper.updateBdglIpaddressConfig(bdglIpaddressConfig);
    }

    /**
     * 批量删除获取考勤记录的IP地址配置
     * 
     * @param ids 需要删除的获取考勤记录的IP地址配置主键
     * @return 结果
     */
    @Override
    public int deleteBdglIpaddressConfigByIds(Integer[] ids)
    {
        return bdglIpaddressConfigMapper.deleteBdglIpaddressConfigByIds(ids);
    }

    /**
     * 删除获取考勤记录的IP地址配置信息
     * 
     * @param id 获取考勤记录的IP地址配置主键
     * @return 结果
     */
    @Override
    public int deleteBdglIpaddressConfigById(Integer id)
    {
        return bdglIpaddressConfigMapper.deleteBdglIpaddressConfigById(id);
    }
}
