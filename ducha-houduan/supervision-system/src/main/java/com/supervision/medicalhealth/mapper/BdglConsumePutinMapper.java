package com.supervision.medicalhealth.mapper;

import java.util.List;
import com.supervision.medicalhealth.domain.BdglConsumePutin;

/**
 * 耗材入库登记Mapper接口
 * 
 * @author hr
 * @date 2022-02-12
 */
public interface BdglConsumePutinMapper 
{
    /**
     * 查询耗材入库登记
     * 
     * @param id 耗材入库登记主键
     * @return 耗材入库登记
     */
    public BdglConsumePutin selectBdglConsumePutinById(Long id);

    /**
     * 查询耗材入库登记列表
     * 
     * @param bdglConsumePutin 耗材入库登记
     * @return 耗材入库登记集合
     */
    public List<BdglConsumePutin> selectBdglConsumePutinList(BdglConsumePutin bdglConsumePutin);

    /**
     * 新增耗材入库登记
     * 
     * @param bdglConsumePutin 耗材入库登记
     * @return 结果
     */
    public int insertBdglConsumePutin(BdglConsumePutin bdglConsumePutin);

    /**
     * 修改耗材入库登记
     * 
     * @param bdglConsumePutin 耗材入库登记
     * @return 结果
     */
    public int updateBdglConsumePutin(BdglConsumePutin bdglConsumePutin);

    /**
     * 删除耗材入库登记
     * 
     * @param id 耗材入库登记主键
     * @return 结果
     */
    public int deleteBdglConsumePutinById(Long id);

    /**
     * 批量删除耗材入库登记
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglConsumePutinByIds(Long[] ids);

    List<BdglConsumePutin> selectGuoQiList(BdglConsumePutin bdglConsumePutin);

    List<BdglConsumePutin> selectweiGuoQiList(BdglConsumePutin bdglDurgPutin1);
}
