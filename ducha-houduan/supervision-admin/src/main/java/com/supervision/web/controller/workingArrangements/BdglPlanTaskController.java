package com.supervision.web.controller.workingArrangements;

import java.text.SimpleDateFormat;
import java.util.List;

import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.common.core.domain.entity.SysRole;
import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.common.utils.DateUtils;
import com.supervision.system.mapper.SysUserMapper;
import com.supervision.system.service.ISysDeptService;
import com.supervision.workingArrangements.domain.BdglPlanProgress;
import com.supervision.workingArrangements.domain.BdglWorkTask;
import com.supervision.workingArrangements.service.IBdglPlanProgressService;
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
import com.supervision.workingArrangements.domain.BdglPlanTask;
import com.supervision.workingArrangements.service.IBdglPlanTaskService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 工作计划Controller
 *
 * @author supervision
 * @date 2022-03-05
 */
@RestController
@RequestMapping("/workingArrangements/planTask")
public class BdglPlanTaskController extends BaseController {
    @Autowired
    private IBdglPlanTaskService bdglPlanTaskService;
    @Autowired
    private IBdglPlanProgressService planProgressService;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private ISysDeptService deptService;

    /**
     * 查询工作计划列表
     */
    @PreAuthorize("@ss.hasPermi('workingArrangements:planTask:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglPlanTask bdglPlanTask) {
        startPage();
        LoginUser loginUser = getLoginUser();
        List<BdglPlanTask> list = bdglPlanTaskService.selectBdglPlanTaskLists(bdglPlanTask,loginUser);
        return getDataTable(list);
    }




    /**
     * 导出工作计划列表
     */
    @PreAuthorize("@ss.hasPermi('workingArrangements:planTask:export')")
    @Log(title = "工作计划", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglPlanTask bdglPlanTask) {
        List<BdglPlanTask> list = bdglPlanTaskService.selectBdglPlanTaskList(bdglPlanTask);
        for (BdglPlanTask planTask : list) {
            for (BdglPlanTask workTask : list) {
                SysDept sysDept = deptService.selectDeptById(Long.valueOf(workTask.getUnitId()));
                workTask.setUnitName(sysDept.getDeptName());
            }
        }
        ExcelUtil<BdglPlanTask> util = new ExcelUtil<BdglPlanTask>(BdglPlanTask.class);
        return util.exportExcel(list, "工作计划数据");
    }

    /**
     * 获取工作计划详细信息
     */
    @PreAuthorize("@ss.hasPermi('workingArrangements:planTask:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id) {
        return AjaxResult.success(bdglPlanTaskService.selectBdglPlanTaskById(id));
    }

    //获取单人进度
    @GetMapping("/getJinDu")
    public AjaxResult getJinDu( Integer id){
        LoginUser loginUser = getLoginUser();
        BdglPlanTask planTask = bdglPlanTaskService.selectBdglPlanTaskById(id);
        BdglPlanProgress bdglPlanProgress=null;
        String[] split = planTask.getPeopleId().split(",");
        for (String s : split) {
            int d = Integer.parseInt(s);
            if (d == loginUser.getUserId()) {
                BdglPlanProgress bdglPlanProgress1 = new BdglPlanProgress();
                bdglPlanProgress1.setPeopleId(Long.valueOf(s));
                bdglPlanProgress1.setTaskId(planTask.getId());
                 bdglPlanProgress = planProgressService.select(bdglPlanProgress1);

            }

        }
        planTask.setPlanProgress(bdglPlanProgress);
        return AjaxResult.success(planTask);
    }
    //获取总进度
    @GetMapping("/getZJinDu")
    public AjaxResult getZJinDu( Integer id){
        LoginUser loginUser = getLoginUser();
        List<BdglPlanProgress> bdglPlanProgresses=null;
        BdglPlanTask planTask = bdglPlanTaskService.selectBdglPlanTaskById(id);
        Long deptId = loginUser.getDeptId();
        Integer adminId = planTask.getAdminId();
        List<SysRole> sysRoles=userMapper.selectRole(loginUser.getUserId());
        String role="";
        if(sysRoles!=null){
            for (SysRole sysRole : sysRoles) {
                if(sysRole.getRoleKey().equals("admin")){
                    role=sysRole.getRoleKey();
                    break;
                }
            }
        }
        if(role.equals("admin")){
            BdglPlanProgress bdglPlanProgress1 = new BdglPlanProgress();
            bdglPlanProgress1.setTaskId(planTask.getId());
            bdglPlanProgresses = planProgressService.selectBdglPlanProgressList(bdglPlanProgress1);
            for (BdglPlanProgress bdglPlanProgress : bdglPlanProgresses) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                bdglPlanProgress.setTaskCycle(simpleDateFormat.format(planTask.getStartTime())+"  至  "+simpleDateFormat.format(planTask.getEndTime()));
            }
        }else if(loginUser.getDeptId().equals(Long.valueOf(planTask.getAdminId()))){
            BdglPlanProgress bdglPlanProgress1 = new BdglPlanProgress();
            bdglPlanProgress1.setTaskId(planTask.getId());
            bdglPlanProgresses = planProgressService.selectBdglPlanProgressList(bdglPlanProgress1);
            for (BdglPlanProgress bdglPlanProgress : bdglPlanProgresses) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                bdglPlanProgress.setTaskCycle(simpleDateFormat.format(planTask.getStartTime())+"   至  "+simpleDateFormat.format(planTask.getEndTime()));
            }
        }
        planTask.setPlanProgresses(bdglPlanProgresses);
        return AjaxResult.success(planTask);
    }


    /**
     * 新增工作计划
     */
    @PreAuthorize("@ss.hasPermi('workingArrangements:planTask:add')")
    @Log(title = "工作计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglPlanTask bdglPlanTask) {
        return toAjax(bdglPlanTaskService.insertBdglPlanTask(bdglPlanTask));
    }

    /**
     * 修改工作计划
     */
    @PreAuthorize("@ss.hasPermi('workingArrangements:planTask:edit')")
    @Log(title = "工作计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglPlanTask bdglPlanTask) {
        return toAjax(bdglPlanTaskService.updateBdglPlanTask(bdglPlanTask));
    }


    /**
     * 删除工作计划
     */
    @PreAuthorize("@ss.hasPermi('workingArrangements:planTask:remove')")
    @Log(title = "工作计划", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax(bdglPlanTaskService.deleteBdglPlanTaskByIds(ids));
    }
}
