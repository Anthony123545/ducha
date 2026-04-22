package com.supervision.workingArrangements.mapper;

import java.util.List;
import com.supervision.workingArrangements.domain.BdglWorkTask;
import org.apache.ibatis.annotations.Param;

/**
 * 工作任务Mapper接口
 * 
 * @author supervision
 * @date 2022-03-05
 */
public interface BdglWorkTaskMapper 
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
     * 删除工作任务
     * 
     * @param id 工作任务主键
     * @return 结果
     */
    public int deleteBdglWorkTaskById(Integer id);

    /**
     * 批量删除工作任务
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglWorkTaskByIds(Integer[] ids);

    /**
     * 查询当前时间、当前部门下是否有数据
     * @param starttime
     * @param endtime
     * @param unitid
     * @return
     */
    public List<BdglWorkTask> selectBdglWorkTaskListByTime(@Param("starttime") String starttime, @Param("endtime") String endtime, @Param("unitid") Long unitid);

    List<BdglWorkTask> selectBdglWorkTaskLists(BdglWorkTask bdglWorkTask1);
}
