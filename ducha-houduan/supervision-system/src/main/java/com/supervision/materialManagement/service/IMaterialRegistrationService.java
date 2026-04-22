package com.supervision.materialManagement.service;

import java.util.List;
import java.util.Map;

import com.supervision.materialManagement.domain.MaterialRegistration;

/**
 * 物资入库登记Service接口
 * 
 * @author supervision
 * @date 2022-03-08
 */
public interface IMaterialRegistrationService 
{
    /**
     * 查询物资入库登记
     * 
     * @param id 物资入库登记主键
     * @return 物资入库登记
     */
    public MaterialRegistration selectMaterialRegistrationById(Long id);

    /**
     * 查询物资入库登记列表
     * 
     * @param materialRegistration 物资入库登记
     * @return 物资入库登记集合
     */
    public List<MaterialRegistration> selectMaterialRegistrationList(MaterialRegistration materialRegistration);

    /**
     * 新增物资入库登记
     * 
     * @param materialRegistration 物资入库登记
     * @return 结果
     */
    public int insertMaterialRegistration(MaterialRegistration materialRegistration);

    /**
     * 修改物资入库登记
     * 
     * @param materialRegistration 物资入库登记
     * @return 结果
     */
    public int updateMaterialRegistration(MaterialRegistration materialRegistration);

    /**
     * 批量删除物资入库登记
     * 
     * @param ids 需要删除的物资入库登记主键集合
     * @return 结果
     */
    public int deleteMaterialRegistrationByIds(Long[] ids);

    /**
     * 删除物资入库登记信息
     * 
     * @param id 物资入库登记主键
     * @return 结果
     */
    public int deleteMaterialRegistrationById(Long id);
    
    MaterialRegistration selectMaterialRegistrationByBarCode(String barcode);


    List<Map<String, Object>> selectMaterialStorageByWarehouse(String warehouse);
}
