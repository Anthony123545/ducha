package com.supervision.jobTask.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.supervision.common.utils.DateUtils;
import com.supervision.common.utils.SecurityUtils;
import com.supervision.jobTask.domain.JobTaskRecordExecutor;
import com.supervision.jobTask.domain.vo.JobTaskRecordVo;
import com.supervision.jobTask.mapper.JobTaskRecordExecutorMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import com.supervision.jobTask.mapper.JobTaskRecordMapper;
import com.supervision.jobTask.domain.JobTaskRecord;
import com.supervision.jobTask.service.IJobTaskRecordService;

import javax.annotation.Resource;

/**
 * 工作任务计划Service业务层处理
 *
 * @author ruoyi
 * @date 2022-12-27
 */
@Service
public class JobTaskRecordServiceImpl implements IJobTaskRecordService
{
    @Resource
    private JobTaskRecordMapper jobTaskRecordMapper;

    @Resource
    private JobTaskRecordExecutorMapper jobTaskRecordExecutorMapper;

    /**
     * 查询工作任务计划
     *
     * @param id 工作任务计划主键
     * @return 工作任务计划
     */
    @Override
    public JobTaskRecord selectJobTaskRecordById(Integer id)
    {
        return jobTaskRecordMapper.selectJobTaskRecordById(id);
    }

    /**
     * 查询工作任务计划列表
     *
     * @param jobTaskRecord 工作任务计划
     * @return 工作任务计划
     */
    @Override
    public List<JobTaskRecord> selectJobTaskRecordList(JobTaskRecord jobTaskRecord)
    {
        return jobTaskRecordMapper.selectJobTaskRecordList(jobTaskRecord);
    }

    /**
     * 新增工作任务计划
     *
     * @param jobTaskRecord 工作任务计划
     * @return 结果
     */
    @Override
    public Boolean insertJobTaskRecord(JobTaskRecord jobTaskRecord) {
        //新增任务计划
        jobTaskRecord.setCreateBy(SecurityUtils.getLoginUser().getUsername());
        jobTaskRecord.setCreateTime(DateUtils.getNowDate());
        int i = jobTaskRecordMapper.insertJobTaskRecord(jobTaskRecord);
        //新增计划执行人信息
        executorPeople(jobTaskRecord);
        return i > 0;
    }

    private void executorPeople(JobTaskRecord jobTaskRecord) {
        //新增计划执行人信息
        List<JobTaskRecordExecutor> jobTaskRecordExecutorList = new ArrayList<>();
        List<String> people = jobTaskRecord.getExecutorPeople();
        people.forEach(item->{
            JobTaskRecordExecutor jobTaskRecordExecutor = new JobTaskRecordExecutor();
            jobTaskRecordExecutor.setWorkId(jobTaskRecord.getId());
            jobTaskRecordExecutor.setExecutorPeople(item);
            jobTaskRecordExecutor.setWorkRate(0);
            jobTaskRecordExecutorList.add(jobTaskRecordExecutor);
        });
        jobTaskRecordExecutorMapper.insertBatchJobTaskRecordExecutor(jobTaskRecordExecutorList);
    }

    /**
     * 修改工作任务计划
     *
     * @param jobTaskRecord 工作任务计划
     * @return 结果
     */
    @Override
    public int updateJobTaskRecord(JobTaskRecord jobTaskRecord) {
        if (jobTaskRecord.getId() != null){
            jobTaskRecordExecutorMapper.deleteJobTaskRecordExecutorByWorkId(jobTaskRecord.getId());
        }
        executorPeople(jobTaskRecord);
        return jobTaskRecordMapper.updateJobTaskRecord(jobTaskRecord);
    }

    /**
     * 批量删除工作任务计划
     *
     * @param ids 需要删除的工作任务计划主键
     * @return 结果
     */
    @Override
    public int deleteJobTaskRecordByIds(Integer[] ids)
    {
        int i = jobTaskRecordMapper.deleteJobTaskRecordByIds(ids);
        jobTaskRecordExecutorMapper.deleteJobTaskRecordExecutorByWorkIds(ids);
        return i;
    }

    /**
     * 删除工作任务计划信息
     *
     * @param id 工作任务计划主键
     * @return 结果
     */
    @Override
    public int deleteJobTaskRecordById(Integer id)
    {
        return jobTaskRecordMapper.deleteJobTaskRecordById(id);
    }

    @Override
    public List<JobTaskRecordVo> selectJobTaskList(String nickName, String startTime, String endTime) {
        return jobTaskRecordMapper.selectJobTaskList(nickName,startTime,endTime);
    }

    @Override
    public JobTaskRecordVo selectJobTask(String nickName, Integer workId) {
        return jobTaskRecordMapper.selectJobTask(nickName,workId);
    }

    public List<JobTaskRecordVo> syselectJobTaskRecordList(@Param("nickName") String nickName){
        return jobTaskRecordMapper.syselectJobTaskRecordList(nickName);
    }
}
