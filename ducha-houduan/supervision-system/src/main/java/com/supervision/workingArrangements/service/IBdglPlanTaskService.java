package com.supervision.workingArrangements.service;

import java.util.List;

import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.workingArrangements.domain.BdglPlanProgress;
import com.supervision.workingArrangements.domain.BdglPlanTask;

/**
 * 工作计划Service接口
 * 
 * @author supervision
 * @date 2022-03-05
 */
public interface IBdglPlanTaskService 
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
     * 批量删除工作计划
     * 
     * @param ids 需要删除的工作计划主键集合
     * @return 结果
     */
    public int deleteBdglPlanTaskByIds(Integer[] ids);

    /**
     * 删除工作计划信息
     * 
     * @param id 工作计划主键
     * @return 结果
     */
    public int deleteBdglPlanTaskById(Integer id);

    List<BdglPlanTask> selectBdglPlanTaskLists(BdglPlanTask bdglPlanTask);

    List<BdglPlanTask> selectBdglPlanTaskLists(BdglPlanTask bdglPlanTask, LoginUser loginUser);
    /**修改任务进度
     * @return*/

}
