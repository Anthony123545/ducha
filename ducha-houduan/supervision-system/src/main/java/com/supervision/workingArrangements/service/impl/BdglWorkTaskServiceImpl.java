package com.supervision.workingArrangements.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.common.core.domain.entity.SysRole;
import com.supervision.common.core.domain.entity.SysUser;
import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.common.core.redis.RedisCache;
import com.supervision.common.utils.DateUtils;
import com.supervision.grassrootsregistration.domain.BdglWeekworkRegister;
import com.supervision.grassrootsregistration.mapper.BdglWeekworkRegisterMapper;
import com.supervision.peopleManage.domain.BdglPeople;
import com.supervision.peopleManage.mapper.BdglPeopleMapper;
import com.supervision.system.mapper.SysDeptMapper;
import com.supervision.system.mapper.SysUserMapper;
import com.supervision.workingArrangements.domain.BdglPlanTask;
import com.supervision.workingArrangements.domain.BdglWorkProgress;
import com.supervision.workingArrangements.mapper.BdglWorkProgressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.supervision.workingArrangements.mapper.BdglWorkTaskMapper;
import com.supervision.workingArrangements.domain.BdglWorkTask;
import com.supervision.workingArrangements.service.IBdglWorkTaskService;

/**
 * 工作任务Service业务层处理
 *
 * @author supervision
 * @date 2022-03-05
 */
@Service
public class BdglWorkTaskServiceImpl implements IBdglWorkTaskService {
    @Autowired
    private BdglWorkTaskMapper bdglWorkTaskMapper;
    @Autowired
    private BdglWorkProgressMapper workProgressMapper;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private SysDeptMapper deptMapper;


    @Autowired
    private RedisCache redisCache;

    @Autowired
    public RedisTemplate redisTemplate;

    /**
     * 查询工作任务
     *
     * @param id 工作任务主键
     * @return 工作任务
     */
    @Override
    public BdglWorkTask selectBdglWorkTaskById(Integer id) {
        return bdglWorkTaskMapper.selectBdglWorkTaskById(id);
    }

    /**
     * 查询工作任务列表
     *
     * @param bdglWorkTask 工作任务
     * @return 工作任务
     */
    @Override
    public List<BdglWorkTask> selectBdglWorkTaskList(BdglWorkTask bdglWorkTask) {
        return bdglWorkTaskMapper.selectBdglWorkTaskList(bdglWorkTask);
    }

    /**
     * 新增工作任务
     *
     * @param bdglWorkTask 工作任务
     * @return 结果
     */
    @Override
    public int insertBdglWorkTask(BdglWorkTask bdglWorkTask) {
        bdglWorkTask.setCreatetime(DateUtils.getNowDate());
        BdglWorkProgress workProgress = new BdglWorkProgress();
        int i =0;
//        for (String b : units){
//            bdglWorkTask.setUnitId(Integer.valueOf(b));
             i = bdglWorkTaskMapper.insertBdglWorkTask(bdglWorkTask);
       // }

        SysDept sysDept = deptMapper.selectDeptById(Long.valueOf(bdglWorkTask.getAdminId()));
        String[] split1 = bdglWorkTask.getPeopleId().split(",");
        SysUser sysUser1 = userMapper.selectUserById(bdglWorkTask.getUserId());
        for (String a : split1) {
            SysUser sysUser = userMapper.selectUserById(Long.valueOf(a));
            redisCache.setCacheObject("work_each_" + sysUser.getUserId(), bdglWorkTask.getId());//存入任务ID
            workProgress.setPeopleId(Long.valueOf(a));
            workProgress.setPeopleName(sysUser.getNickName());
            workProgress.setTaskId(bdglWorkTask.getId());
            workProgress.setFile(bdglWorkTask.getFile());
            workProgress.setFileName(bdglWorkTask.getFileName());
            workProgress.setRemark(bdglWorkTask.getRemark());
            workProgress.setCreatetime(DateUtils.getNowDate());
            workProgress.setTaskName(bdglWorkTask.getName());
            workProgress.setPublisherId(bdglWorkTask.getUserId());
            workProgress.setPublisher(sysUser1.getNickName());
            workProgress.setUnitname(sysDept.getDeptName());
            workProgressMapper.insertBdglWorkProgress(workProgress);
        }

        return i;
    }

    /**
     * 修改工作任务
     *
     * @param bdglWorkTask 工作任务
     * @return 结果
     */
    @Override
    public int updateBdglWorkTask(BdglWorkTask bdglWorkTask) {
        bdglWorkTask.setUpdatetime(DateUtils.getNowDate());
        BdglWorkProgress bdglWorkProgress = new BdglWorkProgress();
        bdglWorkProgress.setTaskId(bdglWorkTask.getId());
        SysDept sysDept = deptMapper.selectDeptById(Long.valueOf(bdglWorkTask.getAdminId()));
        int i = workProgressMapper.deleteBdglWorkProgressByTaskId(bdglWorkProgress);

        String[] split1 = bdglWorkTask.getPeopleId().split(",");
        for (String a : split1) {
            BdglWorkProgress bdglWorkProgresss = new BdglWorkProgress();
            bdglWorkProgresss.setTaskId(bdglWorkTask.getId());
            SysUser sysUser = userMapper.selectUserById(Long.valueOf(a));
            bdglWorkProgresss.setPeopleId(Long.valueOf(a));
            bdglWorkProgresss.setPeopleName(sysUser.getNickName());
            bdglWorkProgresss.setTaskId(bdglWorkTask.getId());
            bdglWorkProgresss.setFile(bdglWorkTask.getFile());
            bdglWorkProgresss.setFileName(bdglWorkTask.getFileName());
            bdglWorkProgresss.setRemark(bdglWorkTask.getRemark());
            bdglWorkProgresss.setCreatetime(DateUtils.getNowDate());
            bdglWorkProgresss.setTaskName(bdglWorkTask.getName());
            bdglWorkProgresss.setPublisherId(bdglWorkTask.getUserId());
            bdglWorkProgresss.setPublisher(sysUser.getNickName());
            bdglWorkProgresss.setUnitname(sysDept.getDeptName());
            workProgressMapper.insertBdglWorkProgress(bdglWorkProgresss);
        }
        return bdglWorkTaskMapper.updateBdglWorkTask(bdglWorkTask);
    }

    /**
     * 批量删除工作任务
     *
     * @param ids 需要删除的工作任务主键
     * @return 结果
     */
    @Override
    public int deleteBdglWorkTaskByIds(Integer[] ids) {
        for (Integer id : ids) {
            workProgressMapper.deleteBdglWorkProgress(id);
        }
        return bdglWorkTaskMapper.deleteBdglWorkTaskByIds(ids);
    }

    /**
     * 删除工作任务信息
     *
     * @param id 工作任务主键
     * @return 结果
     */
    @Override
    public int deleteBdglWorkTaskById(Integer id) {
        return bdglWorkTaskMapper.deleteBdglWorkTaskById(id);
    }

    @Override
    public List<BdglWorkTask> selectBdglWorkTaskLists(BdglWorkTask bdglWorkTask, LoginUser loginUser) {
        ArrayList<BdglWorkTask> workTasks = new ArrayList<>();

        bdglWorkTask.setUserId(loginUser.getUserId());
        List<BdglWorkTask> bdglWorkTasks = bdglWorkTaskMapper.selectBdglWorkTaskLists(bdglWorkTask);

        int a = 0;
        c:

        for (BdglWorkTask workTask : bdglWorkTasks) {
            a++;
            String[] split = workTask.getPeopleId().split(",");
            int unitid=0;
            String[] unitids= workTask.getUnitId().split(",");
            for (String u : unitids){
                if (loginUser.getDeptId().equals(Long.valueOf(u))){
                    unitid = Integer.parseInt(u);
                    break ;
                }
            }
            for (String s : split) {
                //如果当前登录用户是计划执行人   只返回可执行任务数据
                if (loginUser.getUserId() == Integer.parseInt(s)) {
                    workTask.setCommon("1");
                    BdglWorkProgress bdglWorkProgress = new BdglWorkProgress();

                    bdglWorkProgress.setPeopleId(Long.valueOf(s));
                    bdglWorkProgress.setTaskId(workTask.getId());
                    BdglWorkProgress bdglWorkProgress1 = workProgressMapper.selectBdglWorkProgressBYTaskId(bdglWorkProgress);
                    if(bdglWorkProgress1!=null){
                        workTask.setTaskProgress(bdglWorkProgress1.getSchedule().toString());
                    }

                    workTasks.add(workTask);
                    continue c;
                }

            }
            //如果当前等路人是计划发布人   返回发布任务数据
            if (loginUser.getDeptId().equals(Long.valueOf(workTask.getAdminId()))) {
                //显示查看任务进度按钮
                workTask.setCommon("2");
                workTasks.add(workTask);
            }

        }

        return workTasks;
    }

    /**
     * 查询当前时间、当前部门下是否有数据
     *
     * @param starttime
     * @param endtime
     * @param unitid
     * @return
     */
    @Override
    public List<BdglWorkTask> selectBdglWorkTaskListByTime(String starttime, String endtime, Long unitid) {
        return bdglWorkTaskMapper.selectBdglWorkTaskListByTime(starttime, endtime, unitid);
    }
}
