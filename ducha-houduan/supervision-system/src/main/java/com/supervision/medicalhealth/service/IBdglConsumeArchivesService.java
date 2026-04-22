package com.supervision.medicalhealth.service;

import java.util.List;
import com.supervision.medicalhealth.domain.BdglConsumeArchives;

/**
 * 耗材库存档案Service接口
 * 
 * @author hr
 * @date 2022-02-12
 */
public interface IBdglConsumeArchivesService 
{
    /**
     * 查询耗材库存档案
     * 
     * @param id 耗材库存档案主键
     * @return 耗材库存档案
     */
    public BdglConsumeArchives selectBdglConsumeArchivesById(Long id);

    /**
     * 查询耗材库存档案列表
     * 
     * @param bdglConsumeArchives 耗材库存档案
     * @return 耗材库存档案集合
     */
    public List<BdglConsumeArchives> selectBdglConsumeArchivesList(BdglConsumeArchives bdglConsumeArchives);

    /**
     * 新增耗材库存档案
     * 
     * @param bdglConsumeArchives 耗材库存档案
     * @return 结果
     */
    public int insertBdglConsumeArchives(BdglConsumeArchives bdglConsumeArchives);

    /**
     * 修改耗材库存档案
     * 
     * @param bdglConsumeArchives 耗材库存档案
     * @return 结果
     */
    public int updateBdglConsumeArchives(BdglConsumeArchives bdglConsumeArchives);

    /**
     * 批量删除耗材库存档案
     * 
     * @param ids 需要删除的耗材库存档案主键集合
     * @return 结果
     */
    public int deleteBdglConsumeArchivesByIds(Long[] ids);

    /**
     * 删除耗材库存档案信息
     * 
     * @param id 耗材库存档案主键
     * @return 结果
     */
    public int deleteBdglConsumeArchivesById(Long id);
}
