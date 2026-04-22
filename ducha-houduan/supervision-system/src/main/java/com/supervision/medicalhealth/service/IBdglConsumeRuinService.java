package com.supervision.medicalhealth.service;

import java.util.List;
import com.supervision.medicalhealth.domain.BdglConsumeRuin;

/**
 * 耗材销毁记录Service接口
 * 
 * @author hr
 * @date 2022-02-12
 */
public interface IBdglConsumeRuinService 
{
    /**
     * 查询耗材销毁记录
     * 
     * @param id 耗材销毁记录主键
     * @return 耗材销毁记录
     */
    public BdglConsumeRuin selectBdglConsumeRuinById(Integer id);

    /**
     * 查询耗材销毁记录列表
     * 
     * @param bdglConsumeRuin 耗材销毁记录
     * @return 耗材销毁记录集合
     */
    public List<BdglConsumeRuin> selectBdglConsumeRuinList(BdglConsumeRuin bdglConsumeRuin);

    /**
     * 新增耗材销毁记录
     * 
     * @param bdglConsumeRuin 耗材销毁记录
     * @return 结果
     */
    public int insertBdglConsumeRuin(BdglConsumeRuin bdglConsumeRuin);

    /**
     * 修改耗材销毁记录
     * 
     * @param bdglConsumeRuin 耗材销毁记录
     * @return 结果
     */
    public int updateBdglConsumeRuin(BdglConsumeRuin bdglConsumeRuin);

    /**
     * 批量删除耗材销毁记录
     * 
     * @param ids 需要删除的耗材销毁记录主键集合
     * @return 结果
     */
    public int deleteBdglConsumeRuinByIds(Integer[] ids);

    /**
     * 删除耗材销毁记录信息
     * 
     * @param id 耗材销毁记录主键
     * @return 结果
     */
    public int deleteBdglConsumeRuinById(Integer id);
}
