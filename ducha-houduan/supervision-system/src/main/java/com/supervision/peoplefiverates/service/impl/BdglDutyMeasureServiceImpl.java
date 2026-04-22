package com.supervision.peoplefiverates.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.peoplefiverates.mapper.BdglDutyMeasureMapper;
import com.supervision.peoplefiverates.domain.BdglDutyMeasure;
import com.supervision.peoplefiverates.service.IBdglDutyMeasureService;

/**
 * 六量Service业务层处理
 * 
 * @author supervision
 * @date 2022-03-19
 */
@Service
public class BdglDutyMeasureServiceImpl implements IBdglDutyMeasureService 
{
    @Autowired
    private BdglDutyMeasureMapper bdglDutyMeasureMapper;

    /**
     * 查询六量
     * 
     * @param id 六量主键
     * @return 六量
     */
    @Override
    public BdglDutyMeasure selectBdglDutyMeasureById(Integer id)
    {
        return bdglDutyMeasureMapper.selectBdglDutyMeasureById(id);
    }

    /**
     * 查询六量列表
     * 
     * @param bdglDutyMeasure 六量
     * @return 六量
     */
    @Override
    public List<BdglDutyMeasure> selectBdglDutyMeasureList(BdglDutyMeasure bdglDutyMeasure)
    {
        return bdglDutyMeasureMapper.selectBdglDutyMeasureList(bdglDutyMeasure);
    }

    /**
     * 新增六量
     * 
     * @param bdglDutyMeasure 六量
     * @return 结果
     */
    @Override
    public int insertBdglDutyMeasure(BdglDutyMeasure bdglDutyMeasure)
    {
        return bdglDutyMeasureMapper.insertBdglDutyMeasure(bdglDutyMeasure);
    }

    /**
     * 修改六量
     * 
     * @param bdglDutyMeasure 六量
     * @return 结果
     */
    @Override
    public int updateBdglDutyMeasure(BdglDutyMeasure bdglDutyMeasure)
    {
        return bdglDutyMeasureMapper.updateBdglDutyMeasure(bdglDutyMeasure);
    }

    /**
     * 批量删除六量
     * 
     * @param ids 需要删除的六量主键
     * @return 结果
     */
    @Override
    public int deleteBdglDutyMeasureByIds(Integer[] ids)
    {
        return bdglDutyMeasureMapper.deleteBdglDutyMeasureByIds(ids);
    }

    /**
     * 删除六量信息
     * 
     * @param id 六量主键
     * @return 结果
     */
    @Override
    public int deleteBdglDutyMeasureById(Integer id)
    {
        return bdglDutyMeasureMapper.deleteBdglDutyMeasureById(id);
    }
}
