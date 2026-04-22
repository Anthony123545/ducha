package com.supervision.medicalhealth.mapper;

import java.util.List;
import com.supervision.medicalhealth.domain.BdglDurgPutout;

/**
 * 药品出库登记Mapper接口
 * 
 * @author hr
 * @date 2022-02-12
 */
public interface BdglDurgPutoutMapper 
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
     * 删除药品出库登记
     * 
     * @param id 药品出库登记主键
     * @return 结果
     */
    public int deleteBdglDurgPutoutById(Long id);

    /**
     * 批量删除药品出库登记
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglDurgPutoutByIds(Long[] ids);
}
