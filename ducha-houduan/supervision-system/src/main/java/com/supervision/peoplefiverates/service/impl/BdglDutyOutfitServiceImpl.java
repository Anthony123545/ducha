package com.supervision.peoplefiverates.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.peoplefiverates.mapper.BdglDutyOutfitMapper;
import com.supervision.peoplefiverates.domain.BdglDutyOutfit;
import com.supervision.peoplefiverates.service.IBdglDutyOutfitService;

/**
 * 装备五率Service业务层处理
 * 
 * @author supervision
 * @date 2022-03-19
 */
@Service
public class BdglDutyOutfitServiceImpl implements IBdglDutyOutfitService 
{
    @Autowired
    private BdglDutyOutfitMapper bdglDutyOutfitMapper;

    /**
     * 查询装备五率
     * 
     * @param id 装备五率主键
     * @return 装备五率
     */
    @Override
    public BdglDutyOutfit selectBdglDutyOutfitById(Integer id)
    {
        return bdglDutyOutfitMapper.selectBdglDutyOutfitById(id);
    }

    /**
     * 查询装备五率列表
     * 
     * @param bdglDutyOutfit 装备五率
     * @return 装备五率
     */
    @Override
    public List<BdglDutyOutfit> selectBdglDutyOutfitList(BdglDutyOutfit bdglDutyOutfit)
    {
        return bdglDutyOutfitMapper.selectBdglDutyOutfitList(bdglDutyOutfit);
    }

    /**
     * 新增装备五率
     * 
     * @param bdglDutyOutfit 装备五率
     * @return 结果
     */
    @Override
    public int insertBdglDutyOutfit(BdglDutyOutfit bdglDutyOutfit)
    {
        return bdglDutyOutfitMapper.insertBdglDutyOutfit(bdglDutyOutfit);
    }

    /**
     * 修改装备五率
     * 
     * @param bdglDutyOutfit 装备五率
     * @return 结果
     */
    @Override
    public int updateBdglDutyOutfit(BdglDutyOutfit bdglDutyOutfit)
    {
        return bdglDutyOutfitMapper.updateBdglDutyOutfit(bdglDutyOutfit);
    }

    /**
     * 批量删除装备五率
     * 
     * @param ids 需要删除的装备五率主键
     * @return 结果
     */
    @Override
    public int deleteBdglDutyOutfitByIds(Integer[] ids)
    {
        return bdglDutyOutfitMapper.deleteBdglDutyOutfitByIds(ids);
    }

    /**
     * 删除装备五率信息
     * 
     * @param id 装备五率主键
     * @return 结果
     */
    @Override
    public int deleteBdglDutyOutfitById(Integer id)
    {
        return bdglDutyOutfitMapper.deleteBdglDutyOutfitById(id);
    }
}
