package com.supervision.workingArrangements.service;

import java.util.List;

import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.workingArrangements.domain.BdglWorkTask;
import org.apache.ibatis.annotations.Param;

/**
 * 工作任务Service接口
 * 
 * @author supervision
 * @date 2022-03-05
 */
public interface IBdglWorkTaskService 
{
    /**
     * 查询工作任务
     * 
     * @param id 工作任务主键
     * @return 工作任务
     */
    public BdglWorkTask selectBdglWorkTaskById(Integer id);

    /**
     * 查询工作任务列表
     * 
     * @param bdglWorkTask 工作任务
     * @return 工作任务集合
     */
    public List<BdglWorkTask> selectBdglWorkTaskList(BdglWorkTask bdglWorkTask);

    /**
     * 新增工作任务
     * 
     * @param bdglWorkTask 工作任务
     * @return 结果
     */
    public int insertBdglWorkTask(BdglWorkTask bdglWorkTask);

    /**
     * 修改工作任务
     * 
     * @param bdglWorkTask 工作任务
     * @return 结果
     */
    public int updateBdglWorkTask(BdglWorkTask bdglWorkTask);

    /**
     * 批量删除工作任务
     * 
     * @param ids 需要删除的工作任务主键集合
     * @return 结果
     */
    public int deleteBdglWorkTaskByIds(Integer[] ids);

    /**
     * 删除工作任务信息
     * 
     * @param id 工作任务主键
     * @return 结果
     */
    public int deleteBdglWorkTaskById(Integer id);

    List<BdglWorkTask> selectBdglWorkTaskLists(BdglWorkTask bdglWorkTask, LoginUser loginUser);

    /**
     * 查询当前时间、当前部门下是否有数据
     * @param starttime
     * @param endtime
     * @param unitid
     * @return
     */
    public List<BdglWorkTask> selectBdglWorkTaskListByTime(String starttime, String endtime, Long unitid);
}
