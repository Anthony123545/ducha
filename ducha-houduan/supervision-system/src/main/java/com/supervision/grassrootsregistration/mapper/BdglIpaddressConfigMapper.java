package com.supervision.grassrootsregistration.mapper;

import java.util.List;
import com.supervision.grassrootsregistration.domain.BdglIpaddressConfig;

/**
 * 获取考勤记录的IP地址配置Mapper接口
 * 
 * @author ruoyi
 * @date 2022-11-09
 */
public interface BdglIpaddressConfigMapper 
{
    /**
     * 查询获取考勤记录的IP地址配置
     * 
     * @param id 获取考勤记录的IP地址配置主键
     * @return 获取考勤记录的IP地址配置
     */
    public BdglIpaddressConfig selectBdglIpaddressConfigById(Integer id);

    /**
     * 查询获取考勤记录的IP地址配置列表
     * 
     * @param bdglIpaddressConfig 获取考勤记录的IP地址配置
     * @return 获取考勤记录的IP地址配置集合
     */
    public List<BdglIpaddressConfig> selectBdglIpaddressConfigList(BdglIpaddressConfig bdglIpaddressConfig);

    /**
     * 新增获取考勤记录的IP地址配置
     * 
     * @param bdglIpaddressConfig 获取考勤记录的IP地址配置
     * @return 结果
     */
    public int insertBdglIpaddressConfig(BdglIpaddressConfig bdglIpaddressConfig);

    /**
     * 修改获取考勤记录的IP地址配置
     * 
     * @param bdglIpaddressConfig 获取考勤记录的IP地址配置
     * @return 结果
     */
    public int updateBdglIpaddressConfig(BdglIpaddressConfig bdglIpaddressConfig);

    /**
     * 删除获取考勤记录的IP地址配置
     * 
     * @param id 获取考勤记录的IP地址配置主键
     * @return 结果
     */
    public int deleteBdglIpaddressConfigById(Integer id);

    /**
     * 批量删除获取考勤记录的IP地址配置
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglIpaddressConfigByIds(Integer[] ids);
}
