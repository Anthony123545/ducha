package com.supervision.medicalhealth.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.medicalhealth.mapper.BdglConsumeRuinMapper;
import com.supervision.medicalhealth.domain.BdglConsumeRuin;
import com.supervision.medicalhealth.service.IBdglConsumeRuinService;

/**
 * 耗材销毁记录Service业务层处理
 * 
 * @author hr
 * @date 2022-02-12
 */
@Service
public class BdglConsumeRuinServiceImpl implements IBdglConsumeRuinService 
{
    @Autowired
    private BdglConsumeRuinMapper bdglConsumeRuinMapper;

    /**
     * 查询耗材销毁记录
     * 
     * @param id 耗材销毁记录主键
     * @return 耗材销毁记录
     */
    @Override
    public BdglConsumeRuin selectBdglConsumeRuinById(Integer id)
    {
        return bdglConsumeRuinMapper.selectBdglConsumeRuinById(id);
    }

    /**
     * 查询耗材销毁记录列表
     * 
     * @param bdglConsumeRuin 耗材销毁记录
     * @return 耗材销毁记录
     */
    @Override
    public List<BdglConsumeRuin> selectBdglConsumeRuinList(BdglConsumeRuin bdglConsumeRuin)
    {
        return bdglConsumeRuinMapper.selectBdglConsumeRuinList(bdglConsumeRuin);
    }

    /**
     * 新增耗材销毁记录
     * 
     * @param bdglConsumeRuin 耗材销毁记录
     * @return 结果
     */
    @Override
    public int insertBdglConsumeRuin(BdglConsumeRuin bdglConsumeRuin)
    {
        return bdglConsumeRuinMapper.insertBdglConsumeRuin(bdglConsumeRuin);
    }

    /**
     * 修改耗材销毁记录
     * 
     * @param bdglConsumeRuin 耗材销毁记录
     * @return 结果
     */
    @Override
    public int updateBdglConsumeRuin(BdglConsumeRuin bdglConsumeRuin)
    {
        return bdglConsumeRuinMapper.updateBdglConsumeRuin(bdglConsumeRuin);
    }

    /**
     * 批量删除耗材销毁记录
     * 
     * @param ids 需要删除的耗材销毁记录主键
     * @return 结果
     */
    @Override
    public int deleteBdglConsumeRuinByIds(Integer[] ids)
    {
        return bdglConsumeRuinMapper.deleteBdglConsumeRuinByIds(ids);
    }

    /**
     * 删除耗材销毁记录信息
     * 
     * @param id 耗材销毁记录主键
     * @return 结果
     */
    @Override
    public int deleteBdglConsumeRuinById(Integer id)
    {
        return bdglConsumeRuinMapper.deleteBdglConsumeRuinById(id);
    }
}
