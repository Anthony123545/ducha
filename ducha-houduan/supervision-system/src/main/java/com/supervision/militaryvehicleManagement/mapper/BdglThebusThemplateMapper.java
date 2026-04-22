package com.supervision.militaryvehicleManagement.mapper;

import java.util.List;
import com.supervision.militaryvehicleManagement.domain.BdglThebusThemplate;

/**
 * 军车审批模板Mapper接口
 * 
 * @author supervision
 * @date 2022-03-04
 */
public interface BdglThebusThemplateMapper 
{
    /**
     * 查询军车审批模板
     * 
     * @param id 军车审批模板主键
     * @return 军车审批模板
     */
    public BdglThebusThemplate selectBdglThebusThemplateById(Integer id);

    /**
     * 查询军车审批模板列表
     * 
     * @param bdglThebusThemplate 军车审批模板
     * @return 军车审批模板集合
     */
    public List<BdglThebusThemplate> selectBdglThebusThemplateList(BdglThebusThemplate bdglThebusThemplate);

    /**
     * 新增军车审批模板
     * 
     * @param bdglThebusThemplate 军车审批模板
     * @return 结果
     */
    public int insertBdglThebusThemplate(BdglThebusThemplate bdglThebusThemplate);

    /**
     * 修改军车审批模板
     * 
     * @param bdglThebusThemplate 军车审批模板
     * @return 结果
     */
    public int updateBdglThebusThemplate(BdglThebusThemplate bdglThebusThemplate);

    /**
     * 删除军车审批模板
     * 
     * @param id 军车审批模板主键
     * @return 结果
     */
    public int deleteBdglThebusThemplateById(Integer id);

    /**
     * 批量删除军车审批模板
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglThebusThemplateByIds(Integer[] ids);
}
