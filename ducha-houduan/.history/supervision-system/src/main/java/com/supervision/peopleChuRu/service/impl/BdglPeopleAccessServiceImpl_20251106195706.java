package com.supervision.peopleChuRu.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.peopleChuRu.mapper.BdglPeopleAccessMapper;
import com.supervision.peopleChuRu.domain.BdglPeopleAccess;
import com.supervision.peopleChuRu.service.IBdglPeopleAccessService;
import com.supervision.common.utils.text.Convert;

/**
 * 人员出入记录Service业务层处理
 * 
 * @author supervision
 * @date 2023-07-01
 */
@Service
public class BdglPeopleAccessServiceImpl implements IBdglPeopleAccessService 
{
    @Autowired
    private BdglPeopleAccessMapper bdglPeopleAccessMapper;

    /**
     * 查询人员出入记录列表
     * 
     * @param bdglPeopleAccess 人员出入记录
     * @return 人员出入记录
     */
    @Override
    public List<BdglPeopleAccess> selectBdglPeopleAccessList(BdglPeopleAccess bdglPeopleAccess)
    {
        return bdglPeopleAccessMapper.selectBdglPeopleAccessList(bdglPeopleAccess);
    }

    /**
     * 查询所有人员出入记录
     * 
     * @return 人员出入记录集合
     */
    @Override
    public List<BdglPeopleAccess> selectBdglPeopleAccessAll()
    {
        return bdglPeopleAccessMapper.selectBdglPeopleAccessAll();
    }

    /**
     * 通过用户ID查询人员出入记录
     * 
     * @param userId 用户ID
     * @return 人员出入记录
     */
    @Override
    public BdglPeopleAccess selectBdglPeopleAccessByUserId(Long userId)
    {
        return bdglPeopleAccessMapper.selectBdglPeopleAccessByUserId(userId);
    }

    /**
     * 新增人员出入记录
     * 
     * @param bdglPeopleAccess 人员出入记录
     * @return 结果
     */
    @Override
    public int insertBdglPeopleAccess(BdglPeopleAccess bdglPeopleAccess)
    {
        return bdglPeopleAccessMapper.insertBdglPeopleAccess(bdglPeopleAccess);
    }

    /**
     * 修改人员出入记录
     * 
     * @param bdglPeopleAccess 人员出入记录
     * @return 结果
     */
    @Override
    public int updateBdglPeopleAccess(BdglPeopleAccess bdglPeopleAccess)
    {
        return bdglPeopleAccessMapper.updateBdglPeopleAccess(bdglPeopleAccess);
    }

    /**
     * 删除人员出入记录
     * 
     * @param userId 用户ID
     * @return 结果
     */
    @Override
    public int deleteBdglPeopleAccessByUserId(Long userId)
    {
        return bdglPeopleAccessMapper.deleteBdglPeopleAccessByUserId(userId);
    }

    /**
     * 批量删除人员出入记录
     * 
     * @param userIds 需要删除的用户ID集合
     * @return 结果
     */
    @Override
    public int deleteBdglPeopleAccessByUserIds(Long[] userIds)
    {
        return bdglPeopleAccessMapper.deleteBdglPeopleAccessByUserIds(userIds);
    }

    /**
     * 统计人员出入记录
     * 
     * @param bdglPeopleAccess 人员出入记录
     * @return 结果
     */
    @Override
    public Long countBdglPeopleAccess(BdglPeopleAccess bdglPeopleAccess)
    {
        return bdglPeopleAccessMapper.countBdglPeopleAccess(bdglPeopleAccess);
    }
}