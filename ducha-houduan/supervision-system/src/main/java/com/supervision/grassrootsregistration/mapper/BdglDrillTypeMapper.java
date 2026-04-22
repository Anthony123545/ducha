package com.supervision.grassrootsregistration.mapper;

import java.util.List;
import com.supervision.grassrootsregistration.domain.BdglDrillType;

/**
 * 训练类别Mapper接口
 * 
 * @author ruoyi
 * @date 2022-05-21
 */
public interface BdglDrillTypeMapper 
{
    /**
     * 查询训练类别
     * 
     * @param id 训练类别主键
     * @return 训练类别
     */
    public BdglDrillType selectBdglDrillTypeById(Long id);

    /**
     * 查询训练类别列表
     * 
     * @param bdglDrillType 训练类别
     * @return 训练类别集合
     */
    public List<BdglDrillType> selectBdglDrillTypeList(BdglDrillType bdglDrillType);

    /**
     * 新增训练类别
     * 
     * @param bdglDrillType 训练类别
     * @return 结果
     */
    public int insertBdglDrillType(BdglDrillType bdglDrillType);

    /**
     * 修改训练类别
     * 
     * @param bdglDrillType 训练类别
     * @return 结果
     */
    public int updateBdglDrillType(BdglDrillType bdglDrillType);

    /**
     * 删除训练类别
     * 
     * @param id 训练类别主键
     * @return 结果
     */
    public int deleteBdglDrillTypeById(Long id);

    /**
     * 批量删除训练类别
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglDrillTypeByIds(Long[] ids);
}