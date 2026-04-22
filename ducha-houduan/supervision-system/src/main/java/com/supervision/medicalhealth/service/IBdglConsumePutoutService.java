package com.supervision.medicalhealth.service;

import java.util.List;
import com.supervision.medicalhealth.domain.BdglConsumePutout;

/**
 * 耗材出库登记Service接口
 * 
 * @author hr
 * @date 2022-02-12
 */
public interface IBdglConsumePutoutService 
{
    /**
     * 查询耗材出库登记
     * 
     * @param id 耗材出库登记主键
     * @return 耗材出库登记
     */
    public BdglConsumePutout selectBdglConsumePutoutById(Long id);

    /**
     * 查询耗材出库登记列表
     * 
     * @param bdglConsumePutout 耗材出库登记
     * @return 耗材出库登记集合
     */
    public List<BdglConsumePutout> selectBdglConsumePutoutList(BdglConsumePutout bdglConsumePutout);

    /**
     * 新增耗材出库登记
     * 
     * @param bdglConsumePutout 耗材出库登记
     * @return 结果
     */
    public int insertBdglConsumePutout(BdglConsumePutout bdglConsumePutout);

    /**
     * 修改耗材出库登记
     * 
     * @param bdglConsumePutout 耗材出库登记
     * @return 结果
     */
    public int updateBdglConsumePutout(BdglConsumePutout bdglConsumePutout);

    /**
     * 批量删除耗材出库登记
     * 
     * @param ids 需要删除的耗材出库登记主键集合
     * @return 结果
     */
    public int deleteBdglConsumePutoutByIds(Long[] ids);

    /**
     * 删除耗材出库登记信息
     * 
     * @param id 耗材出库登记主键
     * @return 结果
     */
    public int deleteBdglConsumePutoutById(Long id);
}
