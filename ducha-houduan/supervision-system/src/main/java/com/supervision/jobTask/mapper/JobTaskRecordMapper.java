package com.supervision.jobTask.mapper;

import java.util.List;
import com.supervision.jobTask.domain.JobTaskRecord;
import com.supervision.jobTask.domain.vo.JobTaskRecordVo;
import org.apache.ibatis.annotations.Param;

/**
 * 工作任务计划Mapper接口
 *
 * @author ruoyi
 * @date 2022-12-27
 */
public interface JobTaskRecordMapper
{
    /**
     * 查询工作任务计划
     *
     * @param id 工作任务计划主键
     * @return 工作任务计划
     */
    public JobTaskRecord selectJobTaskRecordById(Integer id);

    /**
     * 查询工作任务计划列表
     *
     * @param jobTaskRecord 工作任务计划
     * @return 工作任务计划集合
     */
    public List<JobTaskRecord> selectJobTaskRecordList(JobTaskRecord jobTaskRecord);

    /**
     * 首页任务调用接口
     * @param jobTaskRecord
     * @return
     */
    public List<JobTaskRecord> syselectJobTaskRecordList(JobTaskRecord jobTaskRecord);
    /**
     * 新增工作任务计划
     *
     * @param jobTaskRecord 工作任务计划
     * @return 结果
     */
    public int insertJobTaskRecord(JobTaskRecord jobTaskRecord);

    /**
     * 修改工作任务计划
     *
     * @param jobTaskRecord 工作任务计划
     * @return 结果
     */
    public int updateJobTaskRecord(JobTaskRecord jobTaskRecord);

    /**
     * 删除工作任务计划
     *
     * @param id 工作任务计划主键
     * @return 结果
     */
    public int deleteJobTaskRecordById(Integer id);

    /**
     * 批量删除工作任务计划
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteJobTaskRecordByIds(Integer[] ids);

    List<JobTaskRecordVo> selectJobTaskList(@Param("nickName") String nickName, @Param("startTime") String startTime, @Param("endTime") String endTime);

    JobTaskRecordVo selectJobTask(@Param("nickName") String nickName, @Param("workId") Integer workId);

    /**
     * 首页任务调用接口
     * @param nickName
     * @return
     */
    public List<JobTaskRecordVo> syselectJobTaskRecordList(@Param("nickName") String nickName);
}
