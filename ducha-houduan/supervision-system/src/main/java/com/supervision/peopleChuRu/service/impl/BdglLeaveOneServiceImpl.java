package com.supervision.peopleChuRu.service.impl;

import java.util.Date;
import java.util.List;

import com.supervision.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.peopleChuRu.mapper.BdglLeaveOneMapper;
import com.supervision.peopleChuRu.domain.BdglLeaveOne;
import com.supervision.peopleChuRu.service.IBdglLeaveOneService;

/**
 * 人员请假Service业务层处理
 * 
 * @author supervision
 * @date 2022-03-31
 */
@Service
public class BdglLeaveOneServiceImpl implements IBdglLeaveOneService 
{
    @Autowired
    private BdglLeaveOneMapper bdglLeaveOneMapper;

    /**
     * 查询人员请假
     * 
     * @param id 人员请假主键
     * @return 人员请假
     */
    @Override
    public BdglLeaveOne selectBdglLeaveOneById(Long id)
    {
        return bdglLeaveOneMapper.selectBdglLeaveOneById(id);
    }

    /**
     * 查询人员请假列表
     * 
     * @param bdglLeaveOne 人员请假
     * @return 人员请假
     */
    @Override
    public List<BdglLeaveOne> selectBdglLeaveOneList(BdglLeaveOne bdglLeaveOne)
    {
        return bdglLeaveOneMapper.selectBdglLeaveOneList(bdglLeaveOne);
    }

    /**
     * 新增人员请假
     * 
     * @param bdglLeaveOne 人员请假
     * @return 结果
     */
    @Override
    public int insertBdglLeaveOne(BdglLeaveOne bdglLeaveOne)
    {
        bdglLeaveOne.setCreatetime(DateUtils.getNowDate());
        return bdglLeaveOneMapper.insertBdglLeaveOne(bdglLeaveOne);
    }

    /**
     * 修改人员请假
     * 
     * @param bdglLeaveOne 人员请假
     * @return 结果
     */
    @Override
    public int updateBdglLeaveOne(BdglLeaveOne bdglLeaveOne)
    {
        return bdglLeaveOneMapper.updateBdglLeaveOne(bdglLeaveOne);
    }

    /**
     * 批量删除人员请假
     * 
     * @param ids 需要删除的人员请假主键
     * @return 结果
     */
    @Override
    public int deleteBdglLeaveOneByIds(Long[] ids)
    {
        return bdglLeaveOneMapper.deleteBdglLeaveOneByIds(ids);
    }

    /**
     * 删除人员请假信息
     * 
     * @param id 人员请假主键
     * @return 结果
     */
    @Override
    public int deleteBdglLeaveOneById(Long id)
    {
        return bdglLeaveOneMapper.deleteBdglLeaveOneById(id);
    }
    
    @Override
    public List<BdglLeaveOne> selectBdglLeaveOneListes() {
        return bdglLeaveOneMapper.selectBdglLeaveOneListes();
    }
    
    @Override
    public List<BdglLeaveOne> selectBdglLeaveOneLists() {
        return bdglLeaveOneMapper.selectBdglLeaveOneLists();
    }

    @Override
    public List<BdglLeaveOne> selectBdglLeaveOneByPeopleId(Long pepoleId) {
        return bdglLeaveOneMapper.selectBdglLeaveOneByPeopleId(pepoleId);
    }
}
