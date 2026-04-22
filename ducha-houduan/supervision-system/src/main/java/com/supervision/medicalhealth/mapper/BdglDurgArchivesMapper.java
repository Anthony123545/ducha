package com.supervision.medicalhealth.mapper;

import java.util.List;
import com.supervision.medicalhealth.domain.BdglDurgArchives;

/**
 * 药品库存档案Mapper接口
 * 
 * @author hr
 * @date 2022-02-12
 */
public interface BdglDurgArchivesMapper 
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
     * 删除药品库存档案
     * 
     * @param id 药品库存档案主键
     * @return 结果
     */
    public int deleteBdglDurgArchivesById(Long id);

    /**
     * 批量删除药品库存档案
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglDurgArchivesByIds(Long[] ids);

    BdglDurgArchives selectBdglDurgArchivesByDurgNo(BdglDurgArchives bdglDurgArchive);
}
