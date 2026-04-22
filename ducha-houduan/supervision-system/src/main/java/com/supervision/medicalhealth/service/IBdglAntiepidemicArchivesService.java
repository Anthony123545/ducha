package com.supervision.medicalhealth.service;

import java.util.List;
import com.supervision.medicalhealth.domain.BdglAntiepidemicArchives;

/**
 * 防疫用品库存档案Service接口
 * 
 * @author hr
 * @date 2022-02-14
 */
public interface IBdglAntiepidemicArchivesService 
{
    /**
     * 查询防疫用品库存档案
     * 
     * @param id 防疫用品库存档案主键
     * @return 防疫用品库存档案
     */
    public BdglAntiepidemicArchives selectBdglAntiepidemicArchivesById(Long id);

    /**
     * 查询防疫用品库存档案列表
     * 
     * @param bdglAntiepidemicArchives 防疫用品库存档案
     * @return 防疫用品库存档案集合
     */
    public List<BdglAntiepidemicArchives> selectBdglAntiepidemicArchivesList(BdglAntiepidemicArchives bdglAntiepidemicArchives);

    /**
     * 新增防疫用品库存档案
     * 
     * @param bdglAntiepidemicArchives 防疫用品库存档案
     * @return 结果
     */
    public int insertBdglAntiepidemicArchives(BdglAntiepidemicArchives bdglAntiepidemicArchives);

    /**
     * 修改防疫用品库存档案
     * 
     * @param bdglAntiepidemicArchives 防疫用品库存档案
     * @return 结果
     */
    public int updateBdglAntiepidemicArchives(BdglAntiepidemicArchives bdglAntiepidemicArchives);

    /**
     * 批量删除防疫用品库存档案
     * 
     * @param ids 需要删除的防疫用品库存档案主键集合
     * @return 结果
     */
    public int deleteBdglAntiepidemicArchivesByIds(Long[] ids);

    /**
     * 删除防疫用品库存档案信息
     * 
     * @param id 防疫用品库存档案主键
     * @return 结果
     */
    public int deleteBdglAntiepidemicArchivesById(Long id);
}
