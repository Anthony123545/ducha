package com.supervision.jobTask.service;

import com.supervision.jobTask.domain.JobTaskRecordExecutor;

import java.util.List;

/**
 * 任务与执行人关系Service接口
 *
 * @author gaoqiang
 * @date 2022-12-27
 */
public interface IJobTaskRecordExecutorService {
    
    /**
     * 查询任务与执行人关系
     *
     * @param workId 任务与执行人关系主键
     * @return 任务与执行人关系
     */
    List<JobTaskRecordExecutor> selectJobTaskRecordExecutorByWorkId(Integer workId);
    
    /**
     * 查询任务与执行人关系列表
     *
     * @param jobTaskRecordExecutor 任务与执行人关系
     * @return 任务与执行人关系集合
     */
    public List<JobTaskRecordExecutor> selectJobTaskRecordExecutorList(JobTaskRecordExecutor jobTaskRecordExecutor);
    
    /**
     * 新增任务与执行人关系
     *
     * @param jobTaskRecordExecutor 任务与执行人关系
     * @return 结果
     */
    public int insertJobTaskRecordExecutor(JobTaskRecordExecutor jobTaskRecordExecutor);
    
    /**
     * 修改任务与执行人关系
     *
     * @param jobTaskRecordExecutor 任务与执行人关系
     * @return 结果
     */
    public int updateJobTaskRecordExecutor(JobTaskRecordExecutor jobTaskRecordExecutor);
    
    /**
     * 批量删除任务与执行人关系
     *
     * @param workIds 需要删除的任务与执行人关系主键集合
     * @return 结果
     */
    public int deleteJobTaskRecordExecutorByWorkIds(Integer[] workIds);
    
    /**
     * 删除任务与执行人关系信息
     *
     * @param workId 任务与执行人关系主键
     * @return 结果
     */
    public int deleteJobTaskRecordExecutorByWorkId(Integer workId);
}
