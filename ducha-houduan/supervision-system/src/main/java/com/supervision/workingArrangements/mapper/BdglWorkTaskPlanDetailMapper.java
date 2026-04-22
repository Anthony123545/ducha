package com.supervision.workingArrangements.mapper;

import java.util.List;
import com.supervision.workingArrangements.domain.BdglWorkTaskPlanDetail;

/**
 * 任务计划执行详情Mapper接口
 * 
 * @author ruoyi
 * @date 2022-08-15
 */
public interface BdglWorkTaskPlanDetailMapper 
{
    /**
     * 查询任务计划执行详情
     * 
     * @param id 任务计划执行详情主键
     * @return 任务计划执行详情
     */
    public BdglWorkTaskPlanDetail selectBdglWorkTaskPlanDetailById(Long id);

    /**
     * 查询任务计划执行详情列表
     * 
     * @param bdglWorkTaskPlanDetail 任务计划执行详情
     * @return 任务计划执行详情集合
     */
    public List<BdglWorkTaskPlanDetail> selectBdglWorkTaskPlanDetailList(BdglWorkTaskPlanDetail bdglWorkTaskPlanDetail);

    /**
     * 新增任务计划执行详情
     * 
     * @param bdglWorkTaskPlanDetail 任务计划执行详情
     * @return 结果
     */
    public int insertBdglWorkTaskPlanDetail(BdglWorkTaskPlanDetail bdglWorkTaskPlanDetail);

    /**
     * 修改任务计划执行详情
     * 
     * @param bdglWorkTaskPlanDetail 任务计划执行详情
     * @return 结果
     */
    public int updateBdglWorkTaskPlanDetail(BdglWorkTaskPlanDetail bdglWorkTaskPlanDetail);

    /**
     * 删除任务计划执行详情
     * 
     * @param id 任务计划执行详情主键
     * @return 结果
     */
    public int deleteBdglWorkTaskPlanDetailById(Long id);

    /**
     * 批量删除任务计划执行详情
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglWorkTaskPlanDetailByIds(Long[] ids);
    
    List<BdglWorkTaskPlanDetail> selectWorkTaskPlanDetailListByTaskPlanId(Long taskPlanId);
    
    List<BdglWorkTaskPlanDetail> selectWorkTaskPlanDetailListByUserId(Long userId);
    
    void deletePlanDetailByTaskPlanId(Long taskPlanId);
}
