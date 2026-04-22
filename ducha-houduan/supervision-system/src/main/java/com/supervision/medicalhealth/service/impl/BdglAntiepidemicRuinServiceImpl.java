package com.supervision.medicalhealth.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.medicalhealth.mapper.BdglAntiepidemicRuinMapper;
import com.supervision.medicalhealth.domain.BdglAntiepidemicRuin;
import com.supervision.medicalhealth.service.IBdglAntiepidemicRuinService;

/**
 * 防疫用品销毁记录Service业务层处理
 * 
 * @author supervision
 * @date 2022-04-07
 */
@Service
public class BdglAntiepidemicRuinServiceImpl implements IBdglAntiepidemicRuinService 
{
    @Autowired
    private BdglAntiepidemicRuinMapper bdglAntiepidemicRuinMapper;

    /**
     * 查询防疫用品销毁记录
     * 
     * @param id 防疫用品销毁记录主键
     * @return 防疫用品销毁记录
     */
    @Override
    public BdglAntiepidemicRuin selectBdglAntiepidemicRuinById(Integer id)
    {
        return bdglAntiepidemicRuinMapper.selectBdglAntiepidemicRuinById(id);
    }

    /**
     * 查询防疫用品销毁记录列表
     * 
     * @param bdglAntiepidemicRuin 防疫用品销毁记录
     * @return 防疫用品销毁记录
     */
    @Override
    public List<BdglAntiepidemicRuin> selectBdglAntiepidemicRuinList(BdglAntiepidemicRuin bdglAntiepidemicRuin)
    {
        return bdglAntiepidemicRuinMapper.selectBdglAntiepidemicRuinList(bdglAntiepidemicRuin);
    }

    /**
     * 新增防疫用品销毁记录
     * 
     * @param bdglAntiepidemicRuin 防疫用品销毁记录
     * @return 结果
     */
    @Override
    public int insertBdglAntiepidemicRuin(BdglAntiepidemicRuin bdglAntiepidemicRuin)
    {
        return bdglAntiepidemicRuinMapper.insertBdglAntiepidemicRuin(bdglAntiepidemicRuin);
    }

    /**
     * 修改防疫用品销毁记录
     * 
     * @param bdglAntiepidemicRuin 防疫用品销毁记录
     * @return 结果
     */
    @Override
    public int updateBdglAntiepidemicRuin(BdglAntiepidemicRuin bdglAntiepidemicRuin)
    {
        return bdglAntiepidemicRuinMapper.updateBdglAntiepidemicRuin(bdglAntiepidemicRuin);
    }

    /**
     * 批量删除防疫用品销毁记录
     * 
     * @param ids 需要删除的防疫用品销毁记录主键
     * @return 结果
     */
    @Override
    public int deleteBdglAntiepidemicRuinByIds(Integer[] ids)
    {
        return bdglAntiepidemicRuinMapper.deleteBdglAntiepidemicRuinByIds(ids);
    }

    /**
     * 删除防疫用品销毁记录信息
     * 
     * @param id 防疫用品销毁记录主键
     * @return 结果
     */
    @Override
    public int deleteBdglAntiepidemicRuinById(Integer id)
    {
        return bdglAntiepidemicRuinMapper.deleteBdglAntiepidemicRuinById(id);
    }
}
