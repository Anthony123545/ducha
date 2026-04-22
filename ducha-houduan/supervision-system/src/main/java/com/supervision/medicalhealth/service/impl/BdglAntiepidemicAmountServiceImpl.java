package com.supervision.medicalhealth.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.medicalhealth.mapper.BdglAntiepidemicAmountMapper;
import com.supervision.medicalhealth.domain.BdglAntiepidemicAmount;
import com.supervision.medicalhealth.service.IBdglAntiepidemicAmountService;

/**
 * 防疫用品库存数据Service业务层处理
 * 
 * @author hr
 * @date 2022-02-14
 */
@Service
public class BdglAntiepidemicAmountServiceImpl implements IBdglAntiepidemicAmountService 
{
    @Autowired
    private BdglAntiepidemicAmountMapper bdglAntiepidemicAmountMapper;

    /**
     * 查询防疫用品库存数据
     * 
     * @param id 防疫用品库存数据主键
     * @return 防疫用品库存数据
     */
    @Override
    public BdglAntiepidemicAmount selectBdglAntiepidemicAmountById(Long id)
    {
        return bdglAntiepidemicAmountMapper.selectBdglAntiepidemicAmountById(id);
    }

    /**
     * 查询防疫用品库存数据列表
     * 
     * @param bdglAntiepidemicAmount 防疫用品库存数据
     * @return 防疫用品库存数据
     */
    @Override
    public List<BdglAntiepidemicAmount> selectBdglAntiepidemicAmountList(BdglAntiepidemicAmount bdglAntiepidemicAmount)
    {
        return bdglAntiepidemicAmountMapper.selectBdglAntiepidemicAmountList(bdglAntiepidemicAmount);
    }

    /**
     * 新增防疫用品库存数据
     * 
     * @param bdglAntiepidemicAmount 防疫用品库存数据
     * @return 结果
     */
    @Override
    public int insertBdglAntiepidemicAmount(BdglAntiepidemicAmount bdglAntiepidemicAmount)
    {
        return bdglAntiepidemicAmountMapper.insertBdglAntiepidemicAmount(bdglAntiepidemicAmount);
    }

    /**
     * 修改防疫用品库存数据
     * 
     * @param bdglAntiepidemicAmount 防疫用品库存数据
     * @return 结果
     */
    @Override
    public int updateBdglAntiepidemicAmount(BdglAntiepidemicAmount bdglAntiepidemicAmount)
    {
        return bdglAntiepidemicAmountMapper.updateBdglAntiepidemicAmount(bdglAntiepidemicAmount);
    }

    /**
     * 批量删除防疫用品库存数据
     * 
     * @param ids 需要删除的防疫用品库存数据主键
     * @return 结果
     */
    @Override
    public int deleteBdglAntiepidemicAmountByIds(Long[] ids)
    {
        return bdglAntiepidemicAmountMapper.deleteBdglAntiepidemicAmountByIds(ids);
    }

    /**
     * 删除防疫用品库存数据信息
     * 
     * @param id 防疫用品库存数据主键
     * @return 结果
     */
    @Override
    public int deleteBdglAntiepidemicAmountById(Long id)
    {
        return bdglAntiepidemicAmountMapper.deleteBdglAntiepidemicAmountById(id);
    }
}
