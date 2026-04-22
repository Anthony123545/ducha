package com.supervision.materialManagement.mapper;

import java.util.List;
import com.supervision.materialManagement.domain.MaterialArchives;

/**
 * 物资档案Mapper接口
 * 
 * @author supervision
 * @date 2022-03-08
 */
public interface MaterialArchivesMapper 
{
    /**
     * 查询物资档案
     * 
     * @param id 物资档案主键
     * @return 物资档案
     */
    public MaterialArchives selectMaterialArchivesById(Long id);

    /**
     * 查询物资档案列表
     * 
     * @param materialArchives 物资档案
     * @return 物资档案集合
     */
    public List<MaterialArchives> selectMaterialArchivesList(MaterialArchives materialArchives);

    /**
     * 新增物资档案
     * 
     * @param materialArchives 物资档案
     * @return 结果
     */
    public int insertMaterialArchives(MaterialArchives materialArchives);

    /**
     * 修改物资档案
     * 
     * @param materialArchives 物资档案
     * @return 结果
     */
    public int updateMaterialArchives(MaterialArchives materialArchives);

    /**
     * 删除物资档案
     * 
     * @param id 物资档案主键
     * @return 结果
     */
    public int deleteMaterialArchivesById(Long id);

    /**
     * 批量删除物资档案
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMaterialArchivesByIds(Long[] ids);
    
    MaterialArchives selectMaterialArchivesByBarCode(String barcode);
}
