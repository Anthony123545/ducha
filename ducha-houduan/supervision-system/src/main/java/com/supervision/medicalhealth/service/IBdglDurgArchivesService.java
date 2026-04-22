package com.supervision.medicalhealth.service;

import com.supervision.medicalhealth.domain.BdglDurgArchives;

import java.util.List;
import java.util.Map;

/**
 * 药品库存档案Service接口
 * 
 * @author hr
 * @date 2022-02-12
 */
public interface IBdglDurgArchivesService 
{
    /**
     * 查询药品库存档案
     * 
     * @param id 药品库存档案主键
     * @return 药品库存档案
     */
    public BdglDurgArchives selectBdglDurgArchivesById(Long id);

    /**
     * 查询药品库存档案列表
     * 
     * @param bdglDurgArchives 药品库存档案
     * @return 药品库存档案集合
     */
    public List<BdglDurgArchives> selectBdglDurgArchivesList(BdglDurgArchives bdglDurgArchives);

    /**
     * 新增药品库存档案
     * 
     * @param bdglDurgArchives 药品库存档案
     * @return 结果
     */
    public int insertBdglDurgArchives(BdglDurgArchives bdglDurgArchives);

    /**
     * 修改药品库存档案
     * 
     * @param bdglDurgArchives 药品库存档案
     * @return 结果
     */
    public int updateBdglDurgArchives(BdglDurgArchives bdglDurgArchives);

    /**
     * 批量删除药品库存档案
     * 
     * @param ids 需要删除的药品库存档案主键集合
     * @return 结果
     */
    public int deleteBdglDurgArchivesByIds(Long[] ids);

    /**
     * 删除药品库存档案信息
     * 
     * @param id 药品库存档案主键
     * @return 结果
     */
    public int deleteBdglDurgArchivesById(Long id);
    //获取所有药品名称
    BdglDurgArchives getYaoPingList(BdglDurgArchives bdglDurgArchives);

}
