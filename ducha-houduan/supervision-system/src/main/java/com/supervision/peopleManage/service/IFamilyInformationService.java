package com.supervision.peopleManage.service;

import java.util.List;
import com.supervision.peopleManage.domain.FamilyInformation;

/**
 * 家庭关系Service接口
 * 
 * @author supervision
 * @date 2022-03-01
 */
public interface IFamilyInformationService 
{
    /**
     * 查询家庭关系
     * 
     * @param id 家庭关系主键
     * @return 家庭关系
     */
    public FamilyInformation selectFamilyInformationById(Long id);

    /**
     * 查询家庭关系列表
     * 
     * @param familyInformation 家庭关系
     * @return 家庭关系集合
     */
    public List<FamilyInformation> selectFamilyInformationList(FamilyInformation familyInformation);

    /**
     * 新增家庭关系
     * 
     * @param familyInformation 家庭关系
     * @return 结果
     */
    public int insertFamilyInformation(FamilyInformation familyInformation);

    /**
     * 修改家庭关系
     * 
     * @param familyInformation 家庭关系
     * @return 结果
     */
    public int updateFamilyInformation(FamilyInformation familyInformation);

    /**
     * 批量删除家庭关系
     * 
     * @param ids 需要删除的家庭关系主键集合
     * @return 结果
     */
    public int deleteFamilyInformationByIds(Long[] ids);

    /**
     * 删除家庭关系信息
     * 
     * @param id 家庭关系主键
     * @return 结果
     */
    public int deleteFamilyInformationById(Long id);
}
