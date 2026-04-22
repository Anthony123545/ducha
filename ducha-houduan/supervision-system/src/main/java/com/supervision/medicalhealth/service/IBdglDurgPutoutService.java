package com.supervision.medicalhealth.service;

import com.supervision.medicalhealth.domain.BdglDurgPutout;

import java.util.List;

/**
 * 药品出库登记Service接口
 * 
 * @author hr
 * @date 2022-02-12
 */
public interface IBdglDurgPutoutService 
{
    /**
     * 查询药品出库登记
     * 
     * @param id 药品出库登记主键
     * @return 药品出库登记
     */
    public BdglDurgPutout selectBdglDurgPutoutById(Long id);

    /**
     * 查询药品出库登记列表
     * 
     * @param bdglDurgPutout 药品出库登记
     * @return 药品出库登记集合
     */
    public List<BdglDurgPutout> selectBdglDurgPutoutList(BdglDurgPutout bdglDurgPutout);

    /**
     * 新增药品出库登记
     * 
     * @param bdglDurgPutout 药品出库登记
     * @return 结果
     */
    public int insertBdglDurgPutout(BdglDurgPutout bdglDurgPutout);

    /**
     * 修改药品出库登记
     * 
     * @param bdglDurgPutout 药品出库登记
     * @return 结果
     */
    public int updateBdglDurgPutout(BdglDurgPutout bdglDurgPutout);

    /**
     * 批量删除药品出库登记
     * 
     * @param ids 需要删除的药品出库登记主键集合
     * @return 结果
     */
    public int deleteBdglDurgPutoutByIds(Long[] ids);

    /**
     * 删除药品出库登记信息
     * 
     * @param id 药品出库登记主键
     * @return 结果
     */
    public int deleteBdglDurgPutoutById(Long id);
}
