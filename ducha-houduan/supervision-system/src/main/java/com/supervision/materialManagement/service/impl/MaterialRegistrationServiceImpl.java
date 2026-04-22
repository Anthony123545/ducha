package com.supervision.materialManagement.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.supervision.common.core.domain.AjaxResult;
import com.supervision.common.core.domain.entity.SysDictData;
import com.supervision.common.utils.DateUtils;
import com.supervision.materialManagement.domain.MaterialArchives;
import com.supervision.materialManagement.mapper.MaterialArchivesMapper;
import com.supervision.system.service.ISysDictDataService;
import com.supervision.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.materialManagement.mapper.MaterialRegistrationMapper;
import com.supervision.materialManagement.domain.MaterialRegistration;
import com.supervision.materialManagement.service.IMaterialRegistrationService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 物资入库登记Service业务层处理
 * 
 * @author supervision
 * @date 2022-03-08
 */
@Service
public class MaterialRegistrationServiceImpl implements IMaterialRegistrationService 
{
    @Autowired
    private MaterialRegistrationMapper materialRegistrationMapper;
    @Autowired
    private MaterialArchivesMapper materialArchivesMapper;
    @Autowired
    private ISysUserService userService;

    /**
     * 查询物资入库登记
     * 
     * @param id 物资入库登记主键
     * @return 物资入库登记
     */
    @Override
    public MaterialRegistration selectMaterialRegistrationById(Long id)
    {
        return materialRegistrationMapper.selectMaterialRegistrationById(id);
    }

    /**
     * 查询物资入库登记列表
     * 
     * @param materialRegistration 物资入库登记
     * @return 物资入库登记
     */
    @Override
    public List<MaterialRegistration> selectMaterialRegistrationList(MaterialRegistration materialRegistration)
    {
        return materialRegistrationMapper.selectMaterialRegistrationList(materialRegistration);
    }

    /**
     * 新增物资入库登记
     * 
     * @param materialRegistration 物资入库登记
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int insertMaterialRegistration(MaterialRegistration materialRegistration)
    {
        materialRegistration.setCreateTime(DateUtils.getNowDate());
        int i = materialRegistrationMapper.insertMaterialRegistration(materialRegistration);
        //同步档案库存数据
        MaterialArchives materialArchives1 = materialArchivesMapper.selectMaterialArchivesById(materialRegistration.getArchivesId());//
        materialArchives1.setMaterialInventory(materialRegistration.getPutInt()+materialArchives1.getMaterialInventory());//库存总数
        materialArchives1.setMaterialTotal(materialRegistration.getPutInt()+materialArchives1.getMaterialTotal());
        materialArchivesMapper.updateMaterialArchives(materialArchives1);
        return i;
    }

    /**
     * 修改物资入库登记
     * 
     * @param materialRegistration 物资入库登记
     * @return 结果
     */
    @Override
    public int updateMaterialRegistration(MaterialRegistration materialRegistration)
    {
        return materialRegistrationMapper.updateMaterialRegistration(materialRegistration);
    }

    /**
     * 批量删除物资入库登记
     * 
     * @param ids 需要删除的物资入库登记主键
     * @return 结果
     */
    @Override
    public int deleteMaterialRegistrationByIds(Long[] ids)
    {
        return materialRegistrationMapper.deleteMaterialRegistrationByIds(ids);
    }

    /**
     * 删除物资入库登记信息
     * 
     * @param id 物资入库登记主键
     * @return 结果
     */
    @Override
    public int deleteMaterialRegistrationById(Long id)
    {
        return materialRegistrationMapper.deleteMaterialRegistrationById(id);
    }
    
    @Override
    public MaterialRegistration selectMaterialRegistrationByBarCode(String barcode) {
        return materialRegistrationMapper.selectMaterialRegistrationByBarCode(barcode);
    }

    @Override
    public List<Map<String, Object>> selectMaterialStorageByWarehouse(String warehouse) {
        return materialRegistrationMapper.selectMaterialStorageByWarehouse(warehouse);
    }


}
