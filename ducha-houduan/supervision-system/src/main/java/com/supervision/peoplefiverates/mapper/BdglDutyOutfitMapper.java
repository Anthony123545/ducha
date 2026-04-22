package com.supervision.peoplefiverates.mapper;

import java.util.List;
import com.supervision.peoplefiverates.domain.BdglDutyOutfit;

/**
 * 装备五率Mapper接口
 * 
 * @author supervision
 * @date 2022-03-19
 */
public interface BdglDutyOutfitMapper 
{
    /**
     * 查询装备五率
     * 
     * @param id 装备五率主键
     * @return 装备五率
     */
    public BdglDutyOutfit selectBdglDutyOutfitById(Integer id);

    /**
     * 查询装备五率列表
     * 
     * @param bdglDutyOutfit 装备五率
     * @return 装备五率集合
     */
    public List<BdglDutyOutfit> selectBdglDutyOutfitList(BdglDutyOutfit bdglDutyOutfit);

    /**
     * 新增装备五率
     * 
     * @param bdglDutyOutfit 装备五率
     * @return 结果
     */
    public int insertBdglDutyOutfit(BdglDutyOutfit bdglDutyOutfit);

    /**
     * 修改装备五率
     * 
     * @param bdglDutyOutfit 装备五率
     * @return 结果
     */
    public int updateBdglDutyOutfit(BdglDutyOutfit bdglDutyOutfit);

    /**
     * 删除装备五率
     * 
     * @param id 装备五率主键
     * @return 结果
     */
    public int deleteBdglDutyOutfitById(Integer id);

    /**
     * 批量删除装备五率
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglDutyOutfitByIds(Integer[] ids);
}
