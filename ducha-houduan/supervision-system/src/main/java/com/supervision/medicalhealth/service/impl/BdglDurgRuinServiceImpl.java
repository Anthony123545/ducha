package com.supervision.medicalhealth.service.impl;

import java.util.List;

import com.supervision.medicalhealth.domain.BdglDurgRuin;
import com.supervision.medicalhealth.mapper.BdglDurgRuinMapper;
import com.supervision.medicalhealth.service.IBdglDurgRuinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 药品销毁记录Service业务层处理
 * 
 * @author hr
 * @date 2022-02-12
 */
@Service
public class BdglDurgRuinServiceImpl implements IBdglDurgRuinService
{
    @Autowired
    private BdglDurgRuinMapper bdglDurgRuinMapper;

    /**
     * 查询药品销毁记录
     * 
     * @param id 药品销毁记录主键
     * @return 药品销毁记录
     */
    @Override
    public BdglDurgRuin selectBdglDurgRuinById(Integer id)
    {
        return bdglDurgRuinMapper.selectBdglDurgRuinById(id);
    }

    /**
     * 查询药品销毁记录列表
     * 
     * @param bdglDurgRuin 药品销毁记录
     * @return 药品销毁记录
     */
    @Override
    public List<BdglDurgRuin> selectBdglDurgRuinList(BdglDurgRuin bdglDurgRuin)
    {
        return bdglDurgRuinMapper.selectBdglDurgRuinList(bdglDurgRuin);
    }

    /**
     * 新增药品销毁记录
     * 
     * @param bdglDurgRuin 药品销毁记录
     * @return 结果
     */
    @Override
    public int insertBdglDurgRuin(BdglDurgRuin bdglDurgRuin)
    {
        return bdglDurgRuinMapper.insertBdglDurgRuin(bdglDurgRuin);
    }

    /**
     * 修改药品销毁记录
     * 
     * @param bdglDurgRuin 药品销毁记录
     * @return 结果
     */
    @Override
    public int updateBdglDurgRuin(BdglDurgRuin bdglDurgRuin)
    {
        return bdglDurgRuinMapper.updateBdglDurgRuin(bdglDurgRuin);
    }

    /**
     * 批量删除药品销毁记录
     * 
     * @param ids 需要删除的药品销毁记录主键
     * @return 结果
     */
    @Override
    public int deleteBdglDurgRuinByIds(Integer[] ids)
    {
        return bdglDurgRuinMapper.deleteBdglDurgRuinByIds(ids);
    }

    /**
     * 删除药品销毁记录信息
     * 
     * @param id 药品销毁记录主键
     * @return 结果
     */
    @Override
    public int deleteBdglDurgRuinById(Integer id)
    {
        return bdglDurgRuinMapper.deleteBdglDurgRuinById(id);
    }
}
