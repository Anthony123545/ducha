package com.supervision.workingArrangements.mapper;

import java.util.List;
import com.supervision.workingArrangements.domain.BdglWorkTaskPlan;
import com.supervision.workingArrangements.domain.BdglWorkTaskPlanAnner;

/**
 * 任务计划基本信息Mapper接口
 * 
 * @author ruoyi
 * @date 2022-08-15
 */
public interface BdglWorkTaskPlanMapper 
{
    /**
     * 查询任务计划基本信息
     * 
     * @param id 任务计划基本信息主键
     * @return 任务计划基本信息
     */
    public BdglWorkTaskPlan selectBdglWorkTaskPlanById(Long id);

    /**
     * 查询任务计划基本信息列表
     * 
     * @param bdglWorkTaskPlan 任务计划基本信息
     * @return 任务计划基本信息集合
     */
    public List<BdglWorkTaskPlan> selectBdglWorkTaskPlanList(BdglWorkTaskPlan bdglWorkTaskPlan);
    
    /**
     * 查询任务附件
     */
    List<BdglWorkTaskPlanAnner> selectBdglWorkTaskPlanAnnerByTaskPlanId(Long taskPlanId);
    
    /**
     * 查询任务详情附件
     * @param taskPlanDetailId
     * @return
     */
    List<BdglWorkTaskPlanAnner> selectWorkTaskPlanAnnerByTaskPlanDetailId(Long taskPlanDetailId);

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
     * 删除任务计划基本信息
     * 
     * @param id 任务计划基本信息主键
     * @return 结果
     */
    public int deleteBdglWorkTaskPlanById(Long id);

    /**
     * 批量删除任务计划基本信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglWorkTaskPlanByIds(Long[] ids);

    /**
     * 批量删除任务附件
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglWorkTaskPlanAnnerByTaskPlanIds(Long[] ids);
    
    /**
     * 批量新增任务附件
     * 
     * @param bdglWorkTaskPlanAnnerList 任务附件列表
     * @return 结果
     */
    public int batchBdglWorkTaskPlanAnner(List<BdglWorkTaskPlanAnner> bdglWorkTaskPlanAnnerList);
    
    /**
     * 批量新增任务详情反馈附件
     * @param bdglWorkTaskPlanAnnerList
     * @return
     */
    int batchBdglWorkTaskPlanAnnerWithDetail(List<BdglWorkTaskPlanAnner> bdglWorkTaskPlanAnnerList);
    

    /**
     * 通过任务计划基本信息主键删除任务附件信息
     * 
     * @param id 任务计划基本信息ID
     * @return 结果
     */
    public int deleteBdglWorkTaskPlanAnnerByTaskPlanId(Long id);
}
