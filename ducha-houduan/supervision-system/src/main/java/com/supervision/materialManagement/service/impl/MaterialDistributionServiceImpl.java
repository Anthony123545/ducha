package com.supervision.materialManagement.service.impl;

import java.util.List;

import com.supervision.common.annotation.Excel;
import com.supervision.common.utils.DateUtils;
import com.supervision.materialManagement.domain.MaterialArchives;
import com.supervision.materialManagement.domain.MaterialRegistration;
import com.supervision.materialManagement.mapper.MaterialArchivesMapper;
import com.supervision.materialManagement.mapper.MaterialRegistrationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.materialManagement.mapper.MaterialDistributionMapper;
import com.supervision.materialManagement.domain.MaterialDistribution;
import com.supervision.materialManagement.service.IMaterialDistributionService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 物资发放Service业务层处理
 *
 * @author supervision
 * @date 2022-03-08
 */
@Transactional
@Service
public class MaterialDistributionServiceImpl implements IMaterialDistributionService {
    @Autowired
    private MaterialDistributionMapper materialDistributionMapper;
    @Autowired
    private MaterialArchivesMapper materialArchivesMapper;
    @Autowired
    private MaterialRegistrationMapper materialRegistrationMapper;

    /**
     * 查询物资发放
     *
     * @param id 物资发放主键
     * @return 物资发放
     */
    @Override
    public MaterialDistribution selectMaterialDistributionById(Long id) {
        return materialDistributionMapper.selectMaterialDistributionById(id);
    }

    /**
     * 查询物资发放列表
     *
     * @param materialDistribution 物资发放
     * @return 物资发放
     */
    @Override
    public List<MaterialDistribution> selectMaterialDistributionList(MaterialDistribution materialDistribution) {
        return materialDistributionMapper.selectMaterialDistributionList(materialDistribution);
    }

    /**
     * 新增物资发放
     *
     * @param materialDistribution 物资发放
     * @return 结果
     */
    @Override
    public int insertMaterialDistribution(MaterialDistribution materialDistribution) {
        //同步库存数据
        MaterialArchives materialArchives = materialArchivesMapper.selectMaterialArchivesById(materialDistribution.getArchivesId());
        materialArchives.setMaterialInventory(materialArchives.getMaterialInventory() - materialDistribution.getPutOut());//库存总数
        materialArchives.setInUseTotal(materialArchives.getInUseTotal() + materialDistribution.getPutOut());
        materialArchivesMapper.updateMaterialArchives(materialArchives);
        //同步仓库数据
        MaterialRegistration materialRegistration = materialRegistrationMapper.selectMaterialRegistrationById(Long.valueOf(materialDistribution.getRegistrationId()));
        long l = materialRegistration.getTotal() - materialDistribution.getPutOut();

        materialRegistration.setTotal(l);
        materialRegistrationMapper.updateMaterialRegistration(materialRegistration);
        //添加出库记录
        materialDistribution.setCreateTime(DateUtils.getNowDate());
        materialDistribution.setTotal(materialDistribution.getTotal() - materialDistribution.getPutOut());
        return materialDistributionMapper.insertMaterialDistribution(materialDistribution);
    }

    /**
     * 修改物资发放
     *
     * @param materialDistribution 物资发放
     * @return 结果
     */
    @Override
    public int updateMaterialDistribution(MaterialDistribution materialDistribution) {
        return materialDistributionMapper.updateMaterialDistribution(materialDistribution);
    }

    /**
     * 批量删除物资发放
     *
     * @param ids 需要删除的物资发放主键
     * @return 结果
     */
    @Override
    public int deleteMaterialDistributionByIds(Long[] ids) {
        return materialDistributionMapper.deleteMaterialDistributionByIds(ids);
    }

    /**
     * 删除物资发放信息
     *
     * @param id 物资发放主键
     * @return 结果
     */
    @Override
    public int deleteMaterialDistributionById(Long id) {
        return materialDistributionMapper.deleteMaterialDistributionById(id);
    }

    @Override
    public int jieshou(MaterialDistribution materialDistribution) {
        materialDistribution.setOutPeopleId(1L);
        int i1 = materialDistributionMapper.updateMaterialDistribution(materialDistribution);
        MaterialArchives materialArchives1 = new MaterialArchives();
        if (materialDistribution.getOutUnitId().toString() != null) {
            materialArchives1.setUnitId(Integer.parseInt(materialDistribution.getOutUnitId().toString()));
            materialArchives1.setName(materialDistribution.getName());
            MaterialArchives materialArchives = materialArchivesMapper.selectMaterialArchivesById(materialDistribution.getArchivesId());
            if (materialArchives != null) {
                materialArchives.setMaterialTotal(materialArchives.getMaterialTotal() + materialDistribution.getPutOut());
                materialArchives.setMaterialInventory(materialArchives.getMaterialInventory() + materialDistribution.getPutOut());
                int i = materialArchivesMapper.updateMaterialArchives(materialArchives);
            }else{
                MaterialArchives materialArchives2 = new MaterialArchives();
                /** 物品类型 */
                materialArchives2.setWuPinType(materialDistribution.getWuPinType());
                /** 物品名称 */
                materialArchives2.setName(materialDistribution.getName());
                /** 物资规格 */
                materialArchives2.setMaterialSpecification(materialDistribution.getMaterialSpecification());
                /** 物资型号 */
                materialArchives2.setMaterialXh(materialDistribution.getMaterialXh());
                /** 营具类型 */
                materialArchives2.setYingJuType(materialDistribution.getYingJuType());
                /** 责任单位 */
                materialArchives2.setResponsibilityUnit(materialDistribution.getResponsibilityUnit());
                /** 总数量 */
                materialArchives2.setMaterialTotal(materialDistribution.getPutOut());
                /** 库存数量 */
                materialArchives2.setMaterialInventory(materialDistribution.getPutOut());
                /** 库房 */
                materialArchives2.setWarehouse(materialDistribution.getWarehouse());
                materialArchivesMapper.insertMaterialArchives(materialArchives2);
            }
        }

        return i1;
    }
}
