package com.supervision.web.controller.workingArrangements;

import java.text.SimpleDateFormat;
import java.util.List;

import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.common.core.domain.entity.SysRole;
import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.system.mapper.SysUserMapper;
import com.supervision.system.service.ISysDeptService;
import com.supervision.workingArrangements.domain.BdglPlanProgress;
import com.supervision.workingArrangements.domain.BdglPlanTask;
import com.supervision.workingArrangements.domain.BdglWorkProgress;
import com.supervision.workingArrangements.service.IBdglWorkProgressService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.supervision.common.annotation.Log;
import com.supervision.common.core.controller.BaseController;
import com.supervision.common.core.domain.AjaxResult;
import com.supervision.common.enums.BusinessType;
import com.supervision.workingArrangements.domain.BdglWorkTask;
import com.supervision.workingArrangements.service.IBdglWorkTaskService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 工作任务Controller
 *
 * @author supervision
 * @date 2022-03-05
 */
@RestController
@RequestMapping("/workingArrangements/workTask")
public class BdglWorkTaskController extends BaseController {
    @Autowired
    private IBdglWorkTaskService bdglWorkTaskService;

    @Autowired
    private IBdglWorkProgressService bdglWorkProgressService;

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private ISysDeptService deptService;

    /**
     * 查询工作任务列表
     */
    @PreAuthorize("@ss.hasPermi('workingArrangements:workTask:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglWorkTask bdglWorkTask) {
        LoginUser loginUser = getLoginUser();
        startPage();
        List<BdglWorkTask> list = bdglWorkTaskService.selectBdglWorkTaskLists(bdglWorkTask,loginUser);

        return getDataTable(list);
    }



    //获取单人进度
    @GetMapping("/getJinDu")
    public AjaxResult getJinDu(Integer id) {
        LoginUser loginUser = getLoginUser();
        BdglWorkTask workTask = bdglWorkTaskService.selectBdglWorkTaskById(id);
        BdglWorkProgress workProgress = null;
        String[] split = workTask.getPeopleId().split(",");
        for (String s : split) {
            int d = Integer.parseInt(s);
            if (d == loginUser.getUserId()) {
                //修改任务进度按钮
                BdglWorkProgress workProgress1 = new BdglWorkProgress();
                workProgress1.setPeopleId(Long.valueOf(s));
                workProgress1.setTaskId(workTask.getId());
                workProgress = bdglWorkProgressService.selectBdglWorkProgressBYTaskId(workProgress1);
            }
        }
        workTask.setWorkProgress(workProgress);
        return AjaxResult.success(workTask);
    }

    //获取总进度  getZJinDu
    @GetMapping("/getZJinDu")
    public AjaxResult getZJinDu(Integer id) {
        LoginUser loginUser = getLoginUser();
        List<BdglWorkProgress> workProgresses = null;
        BdglWorkTask workTask = bdglWorkTaskService.selectBdglWorkTaskById(id);
        List<SysRole> sysRoles=userMapper.selectRole(loginUser.getUserId());
        if(sysRoles!=null){
            for (SysRole sysRole : sysRoles) {
                if(sysRole.getRoleKey().equals("admin")){
                    BdglWorkProgress bdglPlanProgress1 = new BdglWorkProgress();
                    bdglPlanProgress1.setTaskId(workTask.getId());
                    workProgresses = bdglWorkProgressService.selectBdglWorkProgressList(bdglPlanProgress1);
                    for (BdglWorkProgress workProgress : workProgresses) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        workProgress.setTaskCycle(simpleDateFormat.format(workTask.getStartTime())+"   至  "+simpleDateFormat.format(workTask.getEndTime()));
                    }
                }
            }
        }
        if(loginUser.getDeptId().equals(workTask.getAdminId())) {
            //显示查看任务进度按钮
            BdglWorkProgress bdglPlanProgress1 = new BdglWorkProgress();
            bdglPlanProgress1.setTaskId(workTask.getId());
            workProgresses = bdglWorkProgressService.selectBdglWorkProgressList(bdglPlanProgress1);
            for (BdglWorkProgress workProgress : workProgresses) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                workProgress.setTaskCycle(simpleDateFormat.format(workTask.getStartTime())+"   至  "+simpleDateFormat.format(workTask.getEndTime()));
            }
        }
        workTask.setWorkProgresses(workProgresses);
        return AjaxResult.success(workTask);
    }

    /**
     * 导出工作任务列表
     */
    @PreAuthorize("@ss.hasPermi('workingArrangements:workTask:export')")
    @Log(title = "工作任务", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglWorkTask bdglWorkTask) {
        List<BdglWorkTask> list = bdglWorkTaskService.selectBdglWorkTaskList(bdglWorkTask);
        for (BdglWorkTask workTask : list) {
            SysDept sysDept = deptService.selectDeptById(Long.valueOf(workTask.getUnitId()));
            workTask.setUnitName(sysDept.getDeptName());
        }



        ExcelUtil<BdglWorkTask> util = new ExcelUtil<BdglWorkTask>(BdglWorkTask.class);


        return util.exportExcel(list, "工作任务数据");
    }

    /**
     * 获取工作任务详细信息
     */
    @PreAuthorize("@ss.hasPermi('workingArrangements:workTask:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id) {
        return AjaxResult.success(bdglWorkTaskService.selectBdglWorkTaskById(id));
    }


    /**
     * 新增工作任务
     */
    @PreAuthorize("@ss.hasPermi('workingArrangements:workTask:add')")
    @Log(title = "工作任务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglWorkTask bdglWorkTask) {
        return toAjax(bdglWorkTaskService.insertBdglWorkTask(bdglWorkTask));
    }

    /**
     * 修改工作任务
     */
    @PreAuthorize("@ss.hasPermi('workingArrangements:workTask:edit')")
    @Log(title = "工作任务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglWorkTask bdglWorkTask) {
        return toAjax(bdglWorkTaskService.updateBdglWorkTask(bdglWorkTask));
    }

    /**
     * 删除工作任务
     */
    @PreAuthorize("@ss.hasPermi('workingArrangements:workTask:remove')")
    @Log(title = "工作任务", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax(bdglWorkTaskService.deleteBdglWorkTaskByIds(ids));
    }
}
