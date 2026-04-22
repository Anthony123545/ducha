package com.supervision.peoplefiverates.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.peoplefiverates.mapper.BdglDutyProbMapper;
import com.supervision.peoplefiverates.domain.BdglDutyProb;
import com.supervision.peoplefiverates.service.IBdglDutyProbService;

/**
 * 人员五率Service业务层处理
 * 
 * @author supervision
 * @date 2022-03-19
 */
@Service
public class BdglDutyProbServiceImpl implements IBdglDutyProbService 
{
    @Autowired
    private BdglDutyProbMapper bdglDutyProbMapper;

    /**
     * 查询人员五率
     * 
     * @param id 人员五率主键
     * @return 人员五率
     */
    @Override
    public BdglDutyProb selectBdglDutyProbById(Integer id)
    {
        return bdglDutyProbMapper.selectBdglDutyProbById(id);
    }

    /**
     * 查询人员五率列表
     * 
     * @param bdglDutyProb 人员五率
     * @return 人员五率
     */
    @Override
    public List<BdglDutyProb> selectBdglDutyProbList(BdglDutyProb bdglDutyProb)
    {
        return bdglDutyProbMapper.selectBdglDutyProbList(bdglDutyProb);
    }

    /**
     * 新增人员五率
     * 
     * @param bdglDutyProb 人员五率
     * @return 结果
     */
    @Override
    public int insertBdglDutyProb(BdglDutyProb bdglDutyProb)
    {
        return bdglDutyProbMapper.insertBdglDutyProb(bdglDutyProb);
    }

    /**
     * 修改人员五率
     * 
     * @param bdglDutyProb 人员五率
     * @return 结果
     */
    @Override
    public int updateBdglDutyProb(BdglDutyProb bdglDutyProb)
    {
        return bdglDutyProbMapper.updateBdglDutyProb(bdglDutyProb);
    }

    /**
     * 批量删除人员五率
     * 
     * @param ids 需要删除的人员五率主键
     * @return 结果
     */
    @Override
    public int deleteBdglDutyProbByIds(Integer[] ids)
    {
        return bdglDutyProbMapper.deleteBdglDutyProbByIds(ids);
    }

    /**
     * 删除人员五率信息
     * 
     * @param id 人员五率主键
     * @return 结果
     */
    @Override
    public int deleteBdglDutyProbById(Integer id)
    {
        return bdglDutyProbMapper.deleteBdglDutyProbById(id);
    }
}
