package com.supervision.web.controller.jobTask;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.supervision.common.utils.bean.BeanUtils;
import com.supervision.jobTask.domain.JobTaskRecordExecutor;
import com.supervision.jobTask.domain.vo.JobTaskProgressVo;
import com.supervision.jobTask.domain.vo.JobTaskRecordVo;
import com.supervision.jobTask.service.IJobTaskRecordExecutorService;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.supervision.jobTask.domain.JobTaskRecord;
import com.supervision.jobTask.service.IJobTaskRecordService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 工作任务计划Controller
 *
 * @author ruoyi
 * @date 2022-12-27
 */
@RestController
@RequestMapping("/jobTaskRecord/jobTask")
public class JobTaskRecordController extends BaseController
{
    @Resource
    private IJobTaskRecordService jobTaskRecordService;

    @Resource
    private IJobTaskRecordExecutorService jobTaskRecordExecutorService;

    /**
     * 查询工作任务计划列表
     */
    @PreAuthorize("@ss.hasPermi('jobTaskRecord:jobTask:list')")
    @GetMapping("/list")
    public TableDataInfo list(JobTaskRecord jobTaskRecord)
    {
        startPage();
        //获取当前登录用户
        String nickName = getLoginUser().getUser().getNickName();
        if ("admin".equals(getUsername())){
            nickName = null;
        }
        jobTaskRecord.setPublishPeople(nickName);
        List<JobTaskRecord> list = jobTaskRecordService.selectJobTaskRecordList(jobTaskRecord);
        return getDataTable(list);
    }

    /**
     * 日历展示计划列表
     */
    @PreAuthorize("@ss.hasPermi('jobTaskRecord:jobTask:list')")
    @GetMapping("/peopleList")
    public TableDataInfo peopleList()
    {
        startPage();
        //获取当前登录用户
        String nickName = getLoginUser().getUser().getNickName();
        List<JobTaskRecordVo> list = jobTaskRecordService.selectJobTaskList(nickName,null, null);
        return getDataTable(list);
    }

    /**
     * 任务总进度查看
     */
    @PreAuthorize("@ss.hasPermi('jobTaskRecord:jobTask:query')")
    @GetMapping("/queryTaskProgress/{id}")
    public AjaxResult queryTaskProgress(@PathVariable("id") Integer id)
    {
        JobTaskRecord jobTaskRecord = jobTaskRecordService.selectJobTaskRecordById(id);
        JobTaskProgressVo jobTaskProgressVo = new JobTaskProgressVo();
        BeanUtils.copyProperties(jobTaskRecord, jobTaskProgressVo);
        List<JobTaskRecordExecutor> jobTaskRecordExecutorList = jobTaskRecordExecutorService.selectJobTaskRecordExecutorByWorkId(id);
        //计算总进度  -- 各执行人执行进度相加除总的执行人
        int sum = jobTaskRecordExecutorList.stream().filter(item->item.getWorkRate() != null).mapToInt(JobTaskRecordExecutor::getWorkRate).sum();
        int progressRate = sum / jobTaskRecordExecutorList.size();
        jobTaskProgressVo.setProgressRate(progressRate);
        jobTaskProgressVo.setJobTaskRecordExecutorList(jobTaskRecordExecutorList);
        return AjaxResult.success(jobTaskProgressVo);
    }

    /**
     * 进度录入
     */
    @Log(title = "工作任务计划", businessType = BusinessType.UPDATE)
    @PutMapping("/progressInput")
    public AjaxResult progressInput(@RequestBody JobTaskRecordExecutor jobTaskRecordExecutor)
    {
        return toAjax(jobTaskRecordExecutorService.updateJobTaskRecordExecutor(jobTaskRecordExecutor));
    }


    /**
     * 日历展示计划列表查看
     */
    @GetMapping("/queryTask/{workId}")
    public AjaxResult queryTask(@PathVariable("workId") Integer workId)
    {
        //获取当前登录用户
        String nickName = getLoginUser().getUser().getNickName();
        JobTaskRecordVo jobTaskRecordVo = jobTaskRecordService.selectJobTask(nickName,workId);
        return AjaxResult.success(jobTaskRecordVo);
    }

    /**
     * 导出当前用户工作任务计划列表
     */
    @Log(title = "工作任务计划", businessType = BusinessType.EXPORT)
    @GetMapping("/exportCurrentUserTask")
    public AjaxResult exportCurrentUserTask(String startTime,String endTime) {
        //获取当前登录用户
        String nickName = getLoginUser().getUser().getNickName();
        List<JobTaskRecordVo> list = jobTaskRecordService.selectJobTaskList(nickName,startTime,endTime);
        ExcelUtil<JobTaskRecordVo> util = new ExcelUtil<>(JobTaskRecordVo.class);
        return util.exportExcel(list, "工作任务数据");
    }

    @GetMapping("/syTaskView")
    public TableDataInfo exportCurrentUserTask() {
        String nickName = getLoginUser().getUser().getNickName();
        List<JobTaskRecordVo> list = jobTaskRecordService.syselectJobTaskRecordList(nickName);
        return getDataTable(list);

    }

    /**
     * 导出工作任务计划列表
     */
    @PreAuthorize("@ss.hasPermi('jobTaskRecord:jobTask:export')")
    @Log(title = "工作任务计划", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public void export(HttpServletResponse response, JobTaskRecord jobTaskRecord)
    {
        List<JobTaskRecord> list = jobTaskRecordService.selectJobTaskRecordList(jobTaskRecord);
        ExcelUtil<JobTaskRecord> util = new ExcelUtil<>(JobTaskRecord.class);
        util.exportExcel(response, list, "工作任务计划数据");
    }

    /**
     * 获取工作任务计划详细信息
     */
    @PreAuthorize("@ss.hasPermi('jobTaskRecord:jobTask:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(jobTaskRecordService.selectJobTaskRecordById(id));
    }

    /**
     * 新增工作任务计划
     */
    @PreAuthorize("@ss.hasPermi('jobTaskRecord:jobTask:add')")
    @Log(title = "工作任务计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody JobTaskRecord jobTaskRecord)
    {
        return toAjax(jobTaskRecordService.insertJobTaskRecord(jobTaskRecord));
    }

    /**
     * 修改工作任务计划
     */
    @PreAuthorize("@ss.hasPermi('jobTaskRecord:jobTask:edit')")
    @Log(title = "工作任务计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody JobTaskRecord jobTaskRecord)
    {
        return toAjax(jobTaskRecordService.updateJobTaskRecord(jobTaskRecord));
    }

    /**
     * 删除工作任务计划
     */
    @PreAuthorize("@ss.hasPermi('jobTaskRecord:jobTask:remove')")
    @Log(title = "工作任务计划", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(jobTaskRecordService.deleteJobTaskRecordByIds(ids));
    }
}
