package com.supervision.workingArrangements.mapper;

import java.util.List;
import com.supervision.workingArrangements.domain.BdglPlanTask;

/**
 * 工作计划Mapper接口
 * 
 * @author supervision
 * @date 2022-03-05
 */
public interface BdglPlanTaskMapper 
{
    /**
     * 查询工作计划
     * 
     * @param id 工作计划主键
     * @return 工作计划
     */
    public BdglPlanTask selectBdglPlanTaskById(Integer id);

    /**
     * 查询工作计划列表
     * 
     * @param bdglPlanTask 工作计划
     * @return 工作计划集合
     */
    public List<BdglPlanTask> selectBdglPlanTaskList(BdglPlanTask bdglPlanTask);

    /**
     * 新增工作计划
     * 
     * @param bdglPlanTask 工作计划
     * @return 结果
     */
    public int insertBdglPlanTask(BdglPlanTask bdglPlanTask);

    /**
     * 修改工作计划
     * 
     * @param bdglPlanTask 工作计划
     * @return 结果
     */
    public int updateBdglPlanTask(BdglPlanTask bdglPlanTask);

    /**
     * 删除工作计划
     * 
     * @param id 工作计划主键
     * @return 结果
     */
    public int deleteBdglPlanTaskById(Integer id);

    /**
     * 批量删除工作计划
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglPlanTaskByIds(Integer[] ids);

    List<BdglPlanTask> selectBdglPlanTaskLists(BdglPlanTask bdglPlanTask);
}
