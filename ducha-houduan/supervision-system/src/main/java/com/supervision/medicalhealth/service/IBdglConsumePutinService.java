package com.supervision.medicalhealth.service;

import java.util.List;

import com.supervision.medicalhealth.domain.BdglConsumeArchives;
import com.supervision.medicalhealth.domain.BdglConsumePutin;

/**
 * 耗材入库登记Service接口
 * 
 * @author hr
 * @date 2022-02-12
 */
public interface IBdglConsumePutinService 
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
     * 批量删除耗材入库登记
     * 
     * @param ids 需要删除的耗材入库登记主键集合
     * @return 结果
     */
    public int deleteBdglConsumePutinByIds(Long[] ids);

    /**
     * 删除耗材入库登记信息
     * 
     * @param id 耗材入库登记主键
     * @return 结果
     */
    public int deleteBdglConsumePutinById(Long id);
    /**
     * 查询耗材过期情况
     * */
    List<BdglConsumePutin> selectGuoQiList(BdglConsumePutin bdglConsumePutin);
    /**
     * 过期耗材销毁
     * */
    int haoCaiXiaoHui(BdglConsumePutin bdglConsumePutin);

    BdglConsumeArchives selectlist(BdglConsumeArchives bdglConsumeArchives);

    int add(BdglConsumePutin bdglConsumePutin);

    int update(BdglConsumePutin bdglConsumePutin);
}
