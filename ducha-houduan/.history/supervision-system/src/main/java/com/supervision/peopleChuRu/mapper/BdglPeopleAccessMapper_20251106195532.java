package com.supervision.peopleChuRu.mapper;

import java.util.List;
import com.supervision.peopleChuRu.domain.BdglPeopleAccess;

/**
 * 人员出入记录Mapper接口
 * 
 * @author supervision
 * @date 2023-07-01
 */
public interface BdglPeopleAccessMapper 
{
    /**
     * 查询人员出入记录列表
     * 
     * @param bdglPeopleAccess 人员出入记录
     * @return 人员出入记录集合
     */
    public List<BdglPeopleAccess> selectBdglPeopleAccessList(BdglPeopleAccess bdglPeopleAccess);

    /**
     * 查询所有人员出入记录
     * 
     * @return 人员出入记录集合
     */
    public List<BdglPeopleAccess> selectBdglPeopleAccessAll();

    /**
     * 通过用户ID查询人员出入记录
     * 
     * @param userId 用户ID
     * @return 人员出入记录
     */
    public BdglPeopleAccess selectBdglPeopleAccessByUserId(Long userId);

    /**
     * 新增人员出入记录
     * 
     * @param bdglPeopleAccess 人员出入记录
     * @return 结果
     */
    public int insertBdglPeopleAccess(BdglPeopleAccess bdglPeopleAccess);

    /**
     * 修改人员出入记录
     * 
     * @param bdglPeopleAccess 人员出入记录
     * @return 结果
     */
    public int updateBdglPeopleAccess(BdglPeopleAccess bdglPeopleAccess);

    /**
     * 删除人员出入记录
     * 
     * @param userId 用户ID
     * @return 结果
     */
    public int deleteBdglPeopleAccessByUserId(Long userId);

    /**
     * 批量删除人员出入记录
     * 
     * @param userIds 需要删除的用户ID集合
     * @return 结果
     */
    public int deleteBdglPeopleAccessByUserIds(Long[] userIds);

    /**
     * 统计人员出入记录
     * 
     * @param bdglPeopleAccess 人员出入记录
     * @return 结果
     */
    public Long countBdglPeopleAccess(BdglPeopleAccess bdglPeopleAccess);
}