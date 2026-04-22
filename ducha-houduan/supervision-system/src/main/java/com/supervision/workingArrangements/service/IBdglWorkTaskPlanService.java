package com.supervision.workingArrangements.service;

import java.util.List;
import com.supervision.workingArrangements.domain.BdglWorkTaskPlan;
import com.supervision.workingArrangements.resultVo.WorkTaskPlanVo;

/**
 * 任务计划基本信息Service接口
 * 
 * @author ruoyi
 * @date 2022-08-15
 */
public interface IBdglWorkTaskPlanService 
{
    /**
     * 查询任务计划基本信息
     * 
     * @param id 任务计划基本信息主键
     * @return 任务计划基本信息
     */
    public WorkTaskPlanVo selectBdglWorkTaskPlanById(Long id);

    /**
     * 查询任务计划基本信息列表
     * 
     * @param bdglWorkTaskPlan 任务计划基本信息
     * @return 任务计划基本信息集合
     */
    public List<BdglWorkTaskPlan> selectBdglWorkTaskPlanList(BdglWorkTaskPlan bdglWorkTaskPlan);

    /**
     * 新增任务计划基本信息
     * 
     * @param bdglWorkTaskPlan 任务计划基本信息
     * @return 结果
     */
    public int insertBdglWorkTaskPlan(BdglWorkTaskPlan bdglWorkTaskPlan);

    /**
     * 修改任务计划基本信息
     * 
     * @param bdglWorkTaskPlan 任务计划基本信息
     * @return 结果
     */
    public int updateBdglWorkTaskPlan(BdglWorkTaskPlan bdglWorkTaskPlan);

    /**
     * 批量删除任务计划基本信息
     * 
     * @param ids 需要删除的任务计划基本信息主键集合
     * @return 结果
     */
    public int deleteBdglWorkTaskPlanByIds(Long[] ids);

    /**
     * 删除任务计划基本信息信息
     * 
     * @param id 任务计划基本信息主键
     * @return 结果
     */
    public int deleteBdglWorkTaskPlanById(Long id);
    
    List<WorkTaskPlanVo> selectBdglWorkTaskPlanListVo(BdglWorkTaskPlan bdglWorkTaskPlan);
    
    List<WorkTaskPlanVo> selectWorkTaskPlanListVo(BdglWorkTaskPlan bdglWorkTaskPlan);
}
