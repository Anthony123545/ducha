package com.supervision.medicalhealth.mapper;

import java.util.List;
import com.supervision.medicalhealth.domain.BdglAntiepidemicRuin;

/**
 * 防疫用品销毁记录Mapper接口
 * 
 * @author supervision
 * @date 2022-04-07
 */
public interface BdglAntiepidemicRuinMapper 
{
    /**
     * 查询防疫用品销毁记录
     * 
     * @param id 防疫用品销毁记录主键
     * @return 防疫用品销毁记录
     */
    public BdglAntiepidemicRuin selectBdglAntiepidemicRuinById(Integer id);

    /**
     * 查询防疫用品销毁记录列表
     * 
     * @param bdglAntiepidemicRuin 防疫用品销毁记录
     * @return 防疫用品销毁记录集合
     */
    public List<BdglAntiepidemicRuin> selectBdglAntiepidemicRuinList(BdglAntiepidemicRuin bdglAntiepidemicRuin);

    /**
     * 新增防疫用品销毁记录
     * 
     * @param bdglAntiepidemicRuin 防疫用品销毁记录
     * @return 结果
     */
    public int insertBdglAntiepidemicRuin(BdglAntiepidemicRuin bdglAntiepidemicRuin);

    /**
     * 修改防疫用品销毁记录
     * 
     * @param bdglAntiepidemicRuin 防疫用品销毁记录
     * @return 结果
     */
    public int updateBdglAntiepidemicRuin(BdglAntiepidemicRuin bdglAntiepidemicRuin);

    /**
     * 删除防疫用品销毁记录
     * 
     * @param id 防疫用品销毁记录主键
     * @return 结果
     */
    public int deleteBdglAntiepidemicRuinById(Integer id);

    /**
     * 批量删除防疫用品销毁记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglAntiepidemicRuinByIds(Integer[] ids);
}
