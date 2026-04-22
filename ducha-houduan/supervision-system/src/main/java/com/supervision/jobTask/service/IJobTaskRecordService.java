package com.supervision.jobTask.service;

import java.util.List;
import com.supervision.jobTask.domain.JobTaskRecord;
import com.supervision.jobTask.domain.vo.JobTaskRecordVo;
import org.apache.ibatis.annotations.Param;

/**
 * 工作任务计划Service接口
 *
 * @author ruoyi
 * @date 2022-12-27
 */
public interface IJobTaskRecordService
{
    /**
     * 查询工作任务计划
     *
     * @param id 工作任务计划主键
     * @return 工作任务计划
     */
    JobTaskRecord selectJobTaskRecordById(Integer id);

    /**
     * 查询工作任务计划列表
     *
     * @param jobTaskRecord 工作任务计划
     * @return 工作任务计划集合
     */
    List<JobTaskRecord> selectJobTaskRecordList(JobTaskRecord jobTaskRecord);

    /**
     * 新增工作任务计划
     *
     * @param jobTaskRecord 工作任务计划
     * @return 结果
     */
    Boolean insertJobTaskRecord(JobTaskRecord jobTaskRecord);

    /**
     * 修改工作任务计划
     *
     * @param jobTaskRecord 工作任务计划
     * @return 结果
     */
    int updateJobTaskRecord(JobTaskRecord jobTaskRecord);

    /**
     * 批量删除工作任务计划
     *
     * @param ids 需要删除的工作任务计划主键集合
     * @return 结果
     */
    int deleteJobTaskRecordByIds(Integer[] ids);

    /**
     * 删除工作任务计划信息
     *
     * @param id 工作任务计划主键
     * @return 结果
     */
    int deleteJobTaskRecordById(Integer id);

    List<JobTaskRecordVo> selectJobTaskList(String nickName, String startTime, String endTime);

    JobTaskRecordVo selectJobTask(String nickName, Integer workId);

    public List<JobTaskRecordVo> syselectJobTaskRecordList(@Param("nickName") String nickName);
}
