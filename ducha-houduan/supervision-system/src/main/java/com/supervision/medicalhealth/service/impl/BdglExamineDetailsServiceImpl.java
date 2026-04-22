package com.supervision.medicalhealth.service.impl;

import java.util.List;
import com.supervision.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.medicalhealth.mapper.BdglExamineDetailsMapper;
import com.supervision.medicalhealth.domain.BdglExamineDetails;
import com.supervision.medicalhealth.service.IBdglExamineDetailsService;

/**
 * 体检信息Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-08-05
 */
@Service
public class BdglExamineDetailsServiceImpl implements IBdglExamineDetailsService 
{
    @Autowired
    private BdglExamineDetailsMapper bdglExamineDetailsMapper;

    /**
     * 查询体检信息
     * 
     * @param id 体检信息主键
     * @return 体检信息
     */
    @Override
    public BdglExamineDetails selectBdglExamineDetailsById(Integer id)
    {
        return bdglExamineDetailsMapper.selectBdglExamineDetailsById(id);
    }

    /**
     * 查询体检信息列表
     * 
     * @param bdglExamineDetails 体检信息
     * @return 体检信息
     */
    @Override
    public List<BdglExamineDetails> selectBdglExamineDetailsList(BdglExamineDetails bdglExamineDetails)
    {
        return bdglExamineDetailsMapper.selectBdglExamineDetailsList(bdglExamineDetails);
    }

    /**
     * 新增体检信息
     * 
     * @param bdglExamineDetails 体检信息
     * @return 结果
     */
    @Override
    public int insertBdglExamineDetails(BdglExamineDetails bdglExamineDetails)
    {
        bdglExamineDetails.setCreateTime(DateUtils.getNowDate());
        return bdglExamineDetailsMapper.insertBdglExamineDetails(bdglExamineDetails);
    }

    /**
     * 修改体检信息
     * 
     * @param bdglExamineDetails 体检信息
     * @return 结果
     */
    @Override
    public int updateBdglExamineDetails(BdglExamineDetails bdglExamineDetails)
    {
        return bdglExamineDetailsMapper.updateBdglExamineDetails(bdglExamineDetails);
    }

    /**
     * 批量删除体检信息
     * 
     * @param ids 需要删除的体检信息主键
     * @return 结果
     */
    @Override
    public int deleteBdglExamineDetailsByIds(Integer[] ids)
    {
        return bdglExamineDetailsMapper.deleteBdglExamineDetailsByIds(ids);
    }

    /**
     * 删除体检信息信息
     * 
     * @param id 体检信息主键
     * @return 结果
     */
    @Override
    public int deleteBdglExamineDetailsById(Integer id)
    {
        return bdglExamineDetailsMapper.deleteBdglExamineDetailsById(id);
    }
}