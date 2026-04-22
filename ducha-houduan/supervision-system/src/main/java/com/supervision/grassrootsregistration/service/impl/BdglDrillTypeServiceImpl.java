package com.supervision.grassrootsregistration.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.grassrootsregistration.mapper.BdglDrillTypeMapper;
import com.supervision.grassrootsregistration.domain.BdglDrillType;
import com.supervision.grassrootsregistration.service.IBdglDrillTypeService;

/**
 * 训练类别Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-05-21
 */
@Service
public class BdglDrillTypeServiceImpl implements IBdglDrillTypeService 
{
    @Autowired
    private BdglDrillTypeMapper bdglDrillTypeMapper;

    /**
     * 查询训练类别
     * 
     * @param id 训练类别主键
     * @return 训练类别
     */
    @Override
    public BdglDrillType selectBdglDrillTypeById(Long id)
    {
        return bdglDrillTypeMapper.selectBdglDrillTypeById(id);
    }

    /**
     * 查询训练类别列表
     * 
     * @param bdglDrillType 训练类别
     * @return 训练类别
     */
    @Override
    public List<BdglDrillType> selectBdglDrillTypeList(BdglDrillType bdglDrillType)
    {
        return bdglDrillTypeMapper.selectBdglDrillTypeList(bdglDrillType);
    }

    /**
     * 新增训练类别
     * 
     * @param bdglDrillType 训练类别
     * @return 结果
     */
    @Override
    public int insertBdglDrillType(BdglDrillType bdglDrillType)
    {
        return bdglDrillTypeMapper.insertBdglDrillType(bdglDrillType);
    }

    /**
     * 修改训练类别
     * 
     * @param bdglDrillType 训练类别
     * @return 结果
     */
    @Override
    public int updateBdglDrillType(BdglDrillType bdglDrillType)
    {
        return bdglDrillTypeMapper.updateBdglDrillType(bdglDrillType);
    }

    /**
     * 批量删除训练类别
     * 
     * @param ids 需要删除的训练类别主键
     * @return 结果
     */
    @Override
    public int deleteBdglDrillTypeByIds(Long[] ids)
    {
        return bdglDrillTypeMapper.deleteBdglDrillTypeByIds(ids);
    }

    /**
     * 删除训练类别信息
     * 
     * @param id 训练类别主键
     * @return 结果
     */
    @Override
    public int deleteBdglDrillTypeById(Long id)
    {
        return bdglDrillTypeMapper.deleteBdglDrillTypeById(id);
    }
}