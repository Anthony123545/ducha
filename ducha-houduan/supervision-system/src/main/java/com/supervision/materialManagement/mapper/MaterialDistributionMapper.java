package com.supervision.materialManagement.mapper;

import java.util.List;
import com.supervision.materialManagement.domain.MaterialDistribution;

/**
 * 物资发放Mapper接口
 * 
 * @author supervision
 * @date 2022-03-08
 */
public interface MaterialDistributionMapper 
{
    /**
     * 查询物资发放
     * 
     * @param id 物资发放主键
     * @return 物资发放
     */
    public MaterialDistribution selectMaterialDistributionById(Long id);

    /**
     * 查询物资发放列表
     * 
     * @param materialDistribution 物资发放
     * @return 物资发放集合
     */
    public List<MaterialDistribution> selectMaterialDistributionList(MaterialDistribution materialDistribution);

    /**
     * 新增物资发放
     * 
     * @param materialDistribution 物资发放
     * @return 结果
     */
    public int insertMaterialDistribution(MaterialDistribution materialDistribution);

    /**
     * 修改物资发放
     * 
     * @param materialDistribution 物资发放
     * @return 结果
     */
    public int updateMaterialDistribution(MaterialDistribution materialDistribution);

    /**
     * 删除物资发放
     * 
     * @param id 物资发放主键
     * @return 结果
     */
    public int deleteMaterialDistributionById(Long id);

    /**
     * 批量删除物资发放
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMaterialDistributionByIds(Long[] ids);
}
