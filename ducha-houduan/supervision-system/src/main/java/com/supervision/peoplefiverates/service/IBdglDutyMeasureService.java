package com.supervision.peoplefiverates.service;

import java.util.List;
import com.supervision.peoplefiverates.domain.BdglDutyMeasure;

/**
 * 六量Service接口
 * 
 * @author supervision
 * @date 2022-03-19
 */
public interface IBdglDutyMeasureService 
{
    /**
     * 查询六量
     * 
     * @param id 六量主键
     * @return 六量
     */
    public BdglDutyMeasure selectBdglDutyMeasureById(Integer id);

    /**
     * 查询六量列表
     * 
     * @param bdglDutyMeasure 六量
     * @return 六量集合
     */
    public List<BdglDutyMeasure> selectBdglDutyMeasureList(BdglDutyMeasure bdglDutyMeasure);

    /**
     * 新增六量
     * 
     * @param bdglDutyMeasure 六量
     * @return 结果
     */
    public int insertBdglDutyMeasure(BdglDutyMeasure bdglDutyMeasure);

    /**
     * 修改六量
     * 
     * @param bdglDutyMeasure 六量
     * @return 结果
     */
    public int updateBdglDutyMeasure(BdglDutyMeasure bdglDutyMeasure);

    /**
     * 批量删除六量
     * 
     * @param ids 需要删除的六量主键集合
     * @return 结果
     */
    public int deleteBdglDutyMeasureByIds(Integer[] ids);

    /**
     * 删除六量信息
     * 
     * @param id 六量主键
     * @return 结果
     */
    public int deleteBdglDutyMeasureById(Integer id);
}
