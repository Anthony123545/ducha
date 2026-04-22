package com.supervision.workingArrangements.service.impl;

import java.util.List;

import com.supervision.common.core.domain.entity.SysUser;
import com.supervision.common.core.redis.RedisCache;
import com.supervision.common.utils.SecurityUtils;
import com.supervision.system.service.ISysRoleService;
import com.supervision.workingArrangements.domain.BdglWorkTask;
import com.supervision.workingArrangements.mapper.BdglWorkTaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.supervision.workingArrangements.mapper.BdglWorkProgressMapper;
import com.supervision.workingArrangements.domain.BdglWorkProgress;
import com.supervision.workingArrangements.service.IBdglWorkProgressService;

/**
 * 工作任务进度录入Service业务层处理
 *
 * @author supervision
 * @date 2022-03-10
 */
@Service
public class BdglWorkProgressServiceImpl implements IBdglWorkProgressService
{
    @Autowired
    private BdglWorkProgressMapper bdglWorkProgressMapper;
    @Autowired
    private BdglWorkTaskMapper workTaskMapper;

    @Autowired
    private RedisCache redisCache;


    /**
     * 查询工作任务进度录入
     *
     * @param id 工作任务进度录入主键
     * @return 工作任务进度录入
     */
    @Override
    public BdglWorkProgress selectBdglWorkProgressById(Integer id)
    {
        return bdglWorkProgressMapper.selectBdglWorkProgressById(id);
    }

    /**
     * 查询工作任务进度录入列表
     *
     * @param bdglWorkProgress 工作任务进度录入
     * @return 工作任务进度录入
     */
    @Override
    public List<BdglWorkProgress> selectBdglWorkProgressList(BdglWorkProgress bdglWorkProgress)
    {
        return bdglWorkProgressMapper.selectBdglWorkProgressList(bdglWorkProgress);
    }

    /**
     * 新增工作任务进度录入
     *
     * @param bdglWorkProgress 工作任务进度录入
     * @return 结果
     */
    @Override
    public int insertBdglWorkProgress(BdglWorkProgress bdglWorkProgress)
    {
        return bdglWorkProgressMapper.insertBdglWorkProgress(bdglWorkProgress);
    }

    /**
     * 修改工作任务进度录入
     *
     * @param bdglWorkProgress 工作任务进度录入
     * @return 结果
     */
    @Override
    public int updateBdglWorkProgress(BdglWorkProgress bdglWorkProgress)
    {
        BdglWorkTask workTask = workTaskMapper.selectBdglWorkTaskById(bdglWorkProgress.getTaskId());
        if(bdglWorkProgress.getFile()!=null){
            workTask.setFile(bdglWorkProgress.getFile());
            workTask.setRemark(bdglWorkProgress.getRemark());
            workTaskMapper.updateBdglWorkTask(workTask);
        }
        if(bdglWorkProgress.getSchedule()==100){
            bdglWorkProgress.setStauts(1);

            redisCache.setCacheObject("work_request_"+bdglWorkProgress.getPeopleId(),workTask.getId());
        }
        return bdglWorkProgressMapper.updateBdglWorkProgress(bdglWorkProgress);
    }

    /**
     * 批量删除工作任务进度录入
     *
     * @param ids 需要删除的工作任务进度录入主键
     * @return 结果
     */
    @Override
    public int deleteBdglWorkProgressByIds(Integer[] ids)
    {
        return bdglWorkProgressMapper.deleteBdglWorkProgressByIds(ids);
    }

    /**
     * 删除工作任务进度录入信息
     *
     * @param id 工作任务进度录入主键
     * @return 结果
     */
    @Override
    public int deleteBdglWorkProgressById(Integer id)
    {
        return bdglWorkProgressMapper.deleteBdglWorkProgressById(id);
    }

    @Override
    public BdglWorkProgress selectBdglWorkProgressBYTaskId(BdglWorkProgress workProgress) {

        return bdglWorkProgressMapper.selectBdglWorkProgressBYTaskId(workProgress);
    }

    @Override
    public List<BdglWorkProgress> selectBdglWorkProgressLists(String[] split) {
        return bdglWorkProgressMapper.selectBdglWorkProgressLists(split);
    }
}
