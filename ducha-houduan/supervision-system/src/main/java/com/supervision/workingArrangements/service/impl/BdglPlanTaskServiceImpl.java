package com.supervision.workingArrangements.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.common.core.domain.entity.SysUser;
import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.common.utils.DateUtils;
import com.supervision.peopleManage.mapper.BdglPeopleMapper;
import com.supervision.system.mapper.SysDeptMapper;
import com.supervision.system.mapper.SysUserMapper;
import com.supervision.workingArrangements.domain.BdglPlanProgress;
import com.supervision.workingArrangements.mapper.BdglPlanProgressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.workingArrangements.mapper.BdglPlanTaskMapper;
import com.supervision.workingArrangements.domain.BdglPlanTask;
import com.supervision.workingArrangements.service.IBdglPlanTaskService;

/**
 * 工作计划Service业务层处理
 *
 * @author supervision
 * @date 2022-03-05
 */
@Service
public class BdglPlanTaskServiceImpl implements IBdglPlanTaskService {
    @Autowired
    private BdglPlanTaskMapper bdglPlanTaskMapper;
    @Autowired
    private BdglPlanProgressMapper planProgressMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private SysDeptMapper deptMapper;

    /**
     * 查询工作计划
     *
     * @param id 工作计划主键
     * @return 工作计划
     */
    @Override
    public BdglPlanTask selectBdglPlanTaskById(Integer id) {
        return bdglPlanTaskMapper.selectBdglPlanTaskById(id);
    }

    /**
     * 查询工作计划列表
     *
     * @param bdglPlanTask 工作计划
     * @return 工作计划
     */
    @Override
    public List<BdglPlanTask> selectBdglPlanTaskList(BdglPlanTask bdglPlanTask) {
        return bdglPlanTaskMapper.selectBdglPlanTaskList(bdglPlanTask);
    }

    /**
     * 新增工作计划
     *
     * @param bdglPlanTask 工作计划
     * @return 结果
     */
    @Override
    public int insertBdglPlanTask(BdglPlanTask bdglPlanTask) {

        bdglPlanTask.setCreatetime(DateUtils.getNowDate());
        BdglPlanProgress bdglPlanProgress = new BdglPlanProgress();
        SysDept sysDept = deptMapper.selectDeptById(Long.valueOf(bdglPlanTask.getAdminId()));
        int i = bdglPlanTaskMapper.insertBdglPlanTask(bdglPlanTask);
        String[] split1 = bdglPlanTask.getPeopleId().split(",");
        for (String a : split1) {
            SysUser sysUser = sysUserMapper.selectUserById(Long.valueOf(a));
            bdglPlanProgress.setPeopleId(Long.valueOf(a));
            bdglPlanProgress.setPeopleName(sysUser.getNickName());
            bdglPlanProgress.setTaskId(bdglPlanTask.getId());
            bdglPlanProgress.setFile(bdglPlanTask.getFile());
            bdglPlanProgress.setFilename(bdglPlanTask.getFileName());
            bdglPlanProgress.setRemark(bdglPlanTask.getRemark());
            bdglPlanProgress.setCreatetime(DateUtils.getNowDate());
            bdglPlanProgress.setTaskName(bdglPlanTask.getName());
            bdglPlanProgress.setPublisherId(sysUser.getUserId());
            bdglPlanProgress.setPublisher(sysUser.getNickName());
            bdglPlanProgress.setUnitname(sysDept.getDeptName());
            planProgressMapper.insertBdglPlanProgress(bdglPlanProgress);

        }

        return i;
    }

    /**
     * 修改工作计划
     *
     * @param bdglPlanTask 工作计划
     * @return 结果
     */
    @Override
    public int updateBdglPlanTask(BdglPlanTask bdglPlanTask) {
        bdglPlanTask.setUpdatetime(DateUtils.getNowDate());
        SysDept sysDept = deptMapper.selectDeptById(Long.valueOf(bdglPlanTask.getAdminId()));
        int i = bdglPlanTaskMapper.updateBdglPlanTask(bdglPlanTask);
        BdglPlanProgress bdglPlanProgress = new BdglPlanProgress();
        bdglPlanProgress.setTaskId(bdglPlanTask.getId());
        int i1 = planProgressMapper.deleteBdglPlanProgress(bdglPlanProgress);
        String[] split1 = bdglPlanTask.getPeopleId().split(",");
        for (String a : split1) {
            BdglPlanProgress bdglPlanProgre = new BdglPlanProgress();
            bdglPlanProgre.setTaskId(bdglPlanTask.getId());
            SysUser sysUser = sysUserMapper.selectUserById(Long.valueOf(a));
            bdglPlanProgre.setPeopleId(Long.valueOf(a));
            bdglPlanProgre.setPeopleName(sysUser.getNickName());
            bdglPlanProgre.setTaskId(bdglPlanTask.getId());
            bdglPlanProgre.setFile(bdglPlanTask.getFile());
            bdglPlanProgre.setFilename(bdglPlanTask.getFileName());
            bdglPlanProgre.setRemark(bdglPlanTask.getRemark());
            bdglPlanProgre.setCreatetime(DateUtils.getNowDate());
            bdglPlanProgre.setTaskName(bdglPlanTask.getName());
            bdglPlanProgress.setPublisherId(sysUser.getUserId());
            bdglPlanProgress.setPublisher(sysUser.getNickName());
            bdglPlanProgre.setUnitname(sysDept.getDeptName());
            planProgressMapper.insertBdglPlanProgress(bdglPlanProgre);

        }


        return i;
    }

    /**
     * 批量删除工作计划
     *
     * @param ids 需要删除的工作计划主键
     * @return 结果
     */
    @Override
    public int deleteBdglPlanTaskByIds(Integer[] ids) {
        return bdglPlanTaskMapper.deleteBdglPlanTaskByIds(ids);
    }

    /**
     * 删除工作计划信息
     *
     * @param id 工作计划主键
     * @return 结果
     */
    @Override
    public int deleteBdglPlanTaskById(Integer id) {
        return bdglPlanTaskMapper.deleteBdglPlanTaskById(id);
    }

    @Override
    public List<BdglPlanTask> selectBdglPlanTaskLists(BdglPlanTask bdglPlanTask) {
        return null;
    }

    @Override
    public List<BdglPlanTask> selectBdglPlanTaskLists(BdglPlanTask bdglPlanTask, LoginUser loginUser) {

        //获取
        SysUser sysUser = sysUserMapper.selectUserById(loginUser.getUserId());
        ArrayList<BdglPlanTask> bdglPlanTasks = new ArrayList<>();
        bdglPlanTask.setUserId(loginUser.getUserId());
        List<BdglPlanTask> list = bdglPlanTaskMapper.selectBdglPlanTaskLists(bdglPlanTask);

        int a = 0;
        c:

        for (BdglPlanTask planTask : list) {
            String[] split = planTask.getPeopleId().split(",");
            int unitid=0;
            String[] unitids= planTask.getUnitId().split(",");
            for (String u : unitids){
                if (loginUser.getDeptId().equals(Long.valueOf(u))){
                    unitid = Integer.valueOf(u);
                    break ;
                }
            }
            for (String s : split) {

                //如果当前登录用户是计划执行人   只返回可执行任务数据
                if (loginUser.getUserId() == Integer.parseInt(s) ) {
                    planTask.setCommon("1");
                    BdglPlanProgress bdglPlanProgress = new BdglPlanProgress();
                    bdglPlanProgress.setPeopleId(Long.valueOf(s));
                    bdglPlanProgress.setTaskId(planTask.getId());
                    BdglPlanProgress select = planProgressMapper.select(bdglPlanProgress);
                    if(select!=null){
                        planTask.setTaskProgress(select.getSchedule().toString());
                    }

                    bdglPlanTasks.add(planTask);
                    continue c;
                }

            }
            //如果当前等路人是计划发布人   返回发布任务数据

            a++;
            if (loginUser.getDeptId().equals(Long.valueOf(planTask.getAdminId()))) {
                //显示查看任务进度按钮
                planTask.setCommon("2");
                bdglPlanTasks.add(planTask);
            }

        }

        return bdglPlanTasks;
    }


}
