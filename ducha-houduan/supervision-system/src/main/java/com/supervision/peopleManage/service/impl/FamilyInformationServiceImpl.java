package com.supervision.peopleManage.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.peopleManage.mapper.FamilyInformationMapper;
import com.supervision.peopleManage.domain.FamilyInformation;
import com.supervision.peopleManage.service.IFamilyInformationService;

/**
 * 家庭关系Service业务层处理
 * 
 * @author supervision
 * @date 2022-03-01
 */
@Service
public class FamilyInformationServiceImpl implements IFamilyInformationService 
{
    @Autowired
    private FamilyInformationMapper familyInformationMapper;

    /**
     * 查询家庭关系
     * 
     * @param id 家庭关系主键
     * @return 家庭关系
     */
    @Override
    public FamilyInformation selectFamilyInformationById(Long id)
    {
        return familyInformationMapper.selectFamilyInformationById(id);
    }

    /**
     * 查询家庭关系列表
     * 
     * @param familyInformation 家庭关系
     * @return 家庭关系
     */
    @Override
    public List<FamilyInformation> selectFamilyInformationList(FamilyInformation familyInformation)
    {
        return familyInformationMapper.selectFamilyInformationList(familyInformation);
    }

    /**
     * 新增家庭关系
     * 
     * @param familyInformation 家庭关系
     * @return 结果
     */
    @Override
    public int insertFamilyInformation(FamilyInformation familyInformation)
    {
        return familyInformationMapper.insertFamilyInformation(familyInformation);
    }

    /**
     * 修改家庭关系
     * 
     * @param familyInformation 家庭关系
     * @return 结果
     */
    @Override
    public int updateFamilyInformation(FamilyInformation familyInformation)
    {
        return familyInformationMapper.updateFamilyInformation(familyInformation);
    }

    /**
     * 批量删除家庭关系
     * 
     * @param ids 需要删除的家庭关系主键
     * @return 结果
     */
    @Override
    public int deleteFamilyInformationByIds(Long[] ids)
    {
        return familyInformationMapper.deleteFamilyInformationByIds(ids);
    }

    /**
     * 删除家庭关系信息
     * 
     * @param id 家庭关系主键
     * @return 结果
     */
    @Override
    public int deleteFamilyInformationById(Long id)
    {
        return familyInformationMapper.deleteFamilyInformationById(id);
    }
}
