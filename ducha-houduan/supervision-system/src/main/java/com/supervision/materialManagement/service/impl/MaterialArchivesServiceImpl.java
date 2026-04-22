package com.supervision.materialManagement.service.impl;

import java.util.List;
import com.supervision.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.materialManagement.mapper.MaterialArchivesMapper;
import com.supervision.materialManagement.domain.MaterialArchives;
import com.supervision.materialManagement.service.IMaterialArchivesService;

/**
 * 物资档案Service业务层处理
 * 
 * @author supervision
 * @date 2022-03-08
 */
@Service
public class MaterialArchivesServiceImpl implements IMaterialArchivesService 
{
    @Autowired
    private MaterialArchivesMapper materialArchivesMapper;

    /**
     * 查询物资档案
     * 
     * @param id 物资档案主键
     * @return 物资档案
     */
    @Override
    public MaterialArchives selectMaterialArchivesById(Long id)
    {
        return materialArchivesMapper.selectMaterialArchivesById(id);
    }

    /**
     * 查询物资档案列表
     * 
     * @param materialArchives 物资档案
     * @return 物资档案
     */
    @Override
    public List<MaterialArchives> selectMaterialArchivesList(MaterialArchives materialArchives)
    {
        return materialArchivesMapper.selectMaterialArchivesList(materialArchives);
    }

    /**
     * 新增物资档案
     * 
     * @param materialArchives 物资档案
     * @return 结果
     */
    @Override
    public int insertMaterialArchives(MaterialArchives materialArchives)
    {
        materialArchives.setCreateTime(DateUtils.getNowDate());
        return materialArchivesMapper.insertMaterialArchives(materialArchives);
    }

    /**
     * 修改物资档案
     * 
     * @param materialArchives 物资档案
     * @return 结果
     */
    @Override
    public int updateMaterialArchives(MaterialArchives materialArchives)
    {
        return materialArchivesMapper.updateMaterialArchives(materialArchives);
    }

    /**
     * 批量删除物资档案
     * 
     * @param ids 需要删除的物资档案主键
     * @return 结果
     */
    @Override
    public int deleteMaterialArchivesByIds(Long[] ids)
    {
        return materialArchivesMapper.deleteMaterialArchivesByIds(ids);
    }

    /**
     * 删除物资档案信息
     * 
     * @param id 物资档案主键
     * @return 结果
     */
    @Override
    public int deleteMaterialArchivesById(Long id)
    {
        return materialArchivesMapper.deleteMaterialArchivesById(id);
    }
    
    @Override
    public MaterialArchives selectMaterialArchivesByBarCode(String barcode) {
        return materialArchivesMapper.selectMaterialArchivesByBarCode(barcode);
    }
}
