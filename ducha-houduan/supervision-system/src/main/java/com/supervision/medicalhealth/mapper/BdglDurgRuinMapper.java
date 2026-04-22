package com.supervision.medicalhealth.mapper;

import java.util.List;
import com.supervision.medicalhealth.domain.BdglDurgRuin;

/**
 * 药品销毁记录Mapper接口
 * 
 * @author hr
 * @date 2022-02-12
 */
public interface BdglDurgRuinMapper 
{
    /**
     * 查询药品销毁记录
     * 
     * @param id 药品销毁记录主键
     * @return 药品销毁记录
     */
    public BdglDurgRuin selectBdglDurgRuinById(Integer id);

    /**
     * 查询药品销毁记录列表
     * 
     * @param bdglDurgRuin 药品销毁记录
     * @return 药品销毁记录集合
     */
    public List<BdglDurgRuin> selectBdglDurgRuinList(BdglDurgRuin bdglDurgRuin);

    /**
     * 新增药品销毁记录
     * 
     * @param bdglDurgRuin 药品销毁记录
     * @return 结果
     */
    public int insertBdglDurgRuin(BdglDurgRuin bdglDurgRuin);

    /**
     * 修改药品销毁记录
     * 
     * @param bdglDurgRuin 药品销毁记录
     * @return 结果
     */
    public int updateBdglDurgRuin(BdglDurgRuin bdglDurgRuin);

    /**
     * 删除药品销毁记录
     * 
     * @param id 药品销毁记录主键
     * @return 结果
     */
    public int deleteBdglDurgRuinById(Integer id);

    /**
     * 批量删除药品销毁记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglDurgRuinByIds(Integer[] ids);
}
