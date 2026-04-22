package com.supervision.medicalhealth.service;

import java.util.List;
import com.supervision.medicalhealth.domain.BdglAntiepidemicAmount;

/**
 * 防疫用品库存数据Service接口
 * 
 * @author hr
 * @date 2022-02-14
 */
public interface IBdglAntiepidemicAmountService 
{
    /**
     * 查询防疫用品库存数据
     * 
     * @param id 防疫用品库存数据主键
     * @return 防疫用品库存数据
     */
    public BdglAntiepidemicAmount selectBdglAntiepidemicAmountById(Long id);

    /**
     * 查询防疫用品库存数据列表
     * 
     * @param bdglAntiepidemicAmount 防疫用品库存数据
     * @return 防疫用品库存数据集合
     */
    public List<BdglAntiepidemicAmount> selectBdglAntiepidemicAmountList(BdglAntiepidemicAmount bdglAntiepidemicAmount);

    /**
     * 新增防疫用品库存数据
     * 
     * @param bdglAntiepidemicAmount 防疫用品库存数据
     * @return 结果
     */
    public int insertBdglAntiepidemicAmount(BdglAntiepidemicAmount bdglAntiepidemicAmount);

    /**
     * 修改防疫用品库存数据
     * 
     * @param bdglAntiepidemicAmount 防疫用品库存数据
     * @return 结果
     */
    public int updateBdglAntiepidemicAmount(BdglAntiepidemicAmount bdglAntiepidemicAmount);

    /**
     * 批量删除防疫用品库存数据
     * 
     * @param ids 需要删除的防疫用品库存数据主键集合
     * @return 结果
     */
    public int deleteBdglAntiepidemicAmountByIds(Long[] ids);

    /**
     * 删除防疫用品库存数据信息
     * 
     * @param id 防疫用品库存数据主键
     * @return 结果
     */
    public int deleteBdglAntiepidemicAmountById(Long id);
}
