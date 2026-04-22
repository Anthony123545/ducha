package com.supervision.jobTask.service.impl;

import com.supervision.jobTask.domain.JobTaskRecordExecutor;
import com.supervision.jobTask.mapper.JobTaskRecordExecutorMapper;
import com.supervision.jobTask.service.IJobTaskRecordExecutorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
/**
 * 任务与执行人关系Service业务层处理
 *
 * @author gaoqiang
 * @date 2022-12-27
 */
@Service
public class JobTaskRecordExecutorServiceImpl implements IJobTaskRecordExecutorService
{
    @Resource
    private JobTaskRecordExecutorMapper jobTaskRecordExecutorMapper;
    
    /**
     * 查询任务与执行人关系
     *
     * @param workId 任务与执行人关系主键
     * @return 任务与执行人关系
     */
    @Override
    public List<JobTaskRecordExecutor> selectJobTaskRecordExecutorByWorkId(Integer workId)
    {
        return jobTaskRecordExecutorMapper.selectJobTaskRecordExecutorByWorkId(workId);
    }
    
    /**
     * 查询任务与执行人关系列表
     *
     * @param jobTaskRecordExecutor 任务与执行人关系
     * @return 任务与执行人关系
     */
    @Override
    public List<JobTaskRecordExecutor> selectJobTaskRecordExecutorList(JobTaskRecordExecutor jobTaskRecordExecutor)
    {
        return jobTaskRecordExecutorMapper.selectJobTaskRecordExecutorList(jobTaskRecordExecutor);
    }
    
    /**
     * 新增任务与执行人关系
     *
     * @param jobTaskRecordExecutor 任务与执行人关系
     * @return 结果
     */
    @Override
    public int insertJobTaskRecordExecutor(JobTaskRecordExecutor jobTaskRecordExecutor)
    {
        return jobTaskRecordExecutorMapper.insertJobTaskRecordExecutor(jobTaskRecordExecutor);
    }
    
    /**
     * 修改任务与执行人关系
     *
     * @param jobTaskRecordExecutor 任务与执行人关系
     * @return 结果
     */
    @Override
    public int updateJobTaskRecordExecutor(JobTaskRecordExecutor jobTaskRecordExecutor)
    {
        return jobTaskRecordExecutorMapper.updateJobTaskRecordExecutor(jobTaskRecordExecutor);
    }
    
    /**
     * 批量删除任务与执行人关系
     *
     * @param workIds 需要删除的任务与执行人关系主键
     * @return 结果
     */
    @Override
    public int deleteJobTaskRecordExecutorByWorkIds(Integer[] workIds)
    {
        return jobTaskRecordExecutorMapper.deleteJobTaskRecordExecutorByWorkIds(workIds);
    }
    
    /**
     * 删除任务与执行人关系信息
     *
     * @param workId 任务与执行人关系主键
     * @return 结果
     */
    @Override
    public int deleteJobTaskRecordExecutorByWorkId(Integer workId)
    {
        return jobTaskRecordExecutorMapper.deleteJobTaskRecordExecutorByWorkId(workId);
    }
}