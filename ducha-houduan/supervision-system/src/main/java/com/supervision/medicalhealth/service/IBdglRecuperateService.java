package com.supervision.medicalhealth.service;

import java.util.List;
import java.util.Map;

import com.supervision.medicalhealth.domain.BdglRecuperate;

/**
 * 疗养管理Service接口
 * 
 * @author hr
 * @date 2022-02-14
 */
public interface IBdglRecuperateService 
{
    /**
     * 查询疗养管理
     * 
     * @param id 疗养管理主键
     * @return 疗养管理
     */
    public BdglRecuperate selectBdglRecuperateById(Integer id);

    /**
     * 查询疗养管理列表
     * 
     * @param bdglRecuperate 疗养管理
     * @return 疗养管理集合
     */
    public List<BdglRecuperate> selectBdglRecuperateList(BdglRecuperate bdglRecuperate);

    /**
     * 新增疗养管理
     * 
     * @param bdglRecuperate 疗养管理
     * @return 结果
     */
    public int insertBdglRecuperate(BdglRecuperate bdglRecuperate);

    /**
     * 修改疗养管理
     * 
     * @param bdglRecuperate 疗养管理
     * @return 结果
     */
    public int updateBdglRecuperate(BdglRecuperate bdglRecuperate);

    /**
     * 批量删除疗养管理
     * 
     * @param ids 需要删除的疗养管理主键集合
     * @return 结果
     */
    public int deleteBdglRecuperateByIds(Integer[] ids);

    /**
     * 删除疗养管理信息
     * 
     * @param id 疗养管理主键
     * @return 结果
     */
    public int deleteBdglRecuperateById(Integer id);
}
