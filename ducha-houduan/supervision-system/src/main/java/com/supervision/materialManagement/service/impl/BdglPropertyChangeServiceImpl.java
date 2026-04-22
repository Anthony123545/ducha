package com.supervision.materialManagement.service.impl;

import java.util.List;

import com.supervision.materialManagement.domain.MaterialArchives;
import com.supervision.materialManagement.mapper.MaterialArchivesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.materialManagement.mapper.BdglPropertyChangeMapper;
import com.supervision.materialManagement.domain.BdglPropertyChange;
import com.supervision.materialManagement.service.IBdglPropertyChangeService;

/**
 * 营具物资变更Service业务层处理
 * 
 * @author supervision
 * @date 2022-03-08
 */
@Service
public class BdglPropertyChangeServiceImpl implements IBdglPropertyChangeService 
{
    @Autowired
    private BdglPropertyChangeMapper bdglPropertyChangeMapper;
    @Autowired
    private MaterialArchivesMapper materialArchivesMapper;

    /**
     * 查询营具物资变更
     * 
     * @param id 营具物资变更主键
     * @return 营具物资变更
     */
    @Override
    public BdglPropertyChange selectBdglPropertyChangeById(Integer id)
    {
        return bdglPropertyChangeMapper.selectBdglPropertyChangeById(id);
    }

    /**
     * 查询营具物资变更列表
     * 
     * @param bdglPropertyChange 营具物资变更
     * @return 营具物资变更
     */
    @Override
    public List<BdglPropertyChange> selectBdglPropertyChangeList(BdglPropertyChange bdglPropertyChange)
    {
        return bdglPropertyChangeMapper.selectBdglPropertyChangeList(bdglPropertyChange);
    }

    /**
     * 新增营具物资变更
     * 
     * @param bdglPropertyChange 营具物资变更
     * @return 结果
     */
    @Override
    public int insertBdglPropertyChange(BdglPropertyChange bdglPropertyChange)
    {
        //同步档案信息
        MaterialArchives materialArchives = materialArchivesMapper.selectMaterialArchivesById(Long.valueOf(bdglPropertyChange.getPropertyId()));
        materialArchives.setUnitId(bdglPropertyChange.getAfterUnitId());
        materialArchives.setResponsibilityUnit(bdglPropertyChange.getAfterUnitName());//责任单位
        materialArchives.setPersonLiable(bdglPropertyChange.getAfterPeopleName());//责任人
        materialArchives.setPersonLiableId(bdglPropertyChange.getAfterPeopleId());//责任人id
        materialArchives.setUnitId(bdglPropertyChange.getAfterUnitId());//责任单位id
        materialArchivesMapper.updateMaterialArchives(materialArchives);

        return bdglPropertyChangeMapper.insertBdglPropertyChange(bdglPropertyChange);
    }

    /**
     * 修改营具物资变更
     * 
     * @param bdglPropertyChange 营具物资变更
     * @return 结果
     */
    @Override
    public int updateBdglPropertyChange(BdglPropertyChange bdglPropertyChange)
    {
        return bdglPropertyChangeMapper.updateBdglPropertyChange(bdglPropertyChange);
    }

    /**
     * 批量删除营具物资变更
     * 
     * @param ids 需要删除的营具物资变更主键
     * @return 结果
     */
    @Override
    public int deleteBdglPropertyChangeByIds(Integer[] ids)
    {
        return bdglPropertyChangeMapper.deleteBdglPropertyChangeByIds(ids);
    }

    /**
     * 删除营具物资变更信息
     * 
     * @param id 营具物资变更主键
     * @return 结果
     */
    @Override
    public int deleteBdglPropertyChangeById(Integer id)
    {
        return bdglPropertyChangeMapper.deleteBdglPropertyChangeById(id);
    }
}
