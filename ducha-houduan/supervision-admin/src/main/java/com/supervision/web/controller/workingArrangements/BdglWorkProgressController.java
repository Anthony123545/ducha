package com.supervision.web.controller.workingArrangements;

import java.util.List;

import com.supervision.common.core.domain.entity.SysUser;
import com.supervision.common.core.redis.RedisCache;
import com.supervision.common.utils.SecurityUtils;
import org.springframework.data.redis.core.RedisTemplate;
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
import com.supervision.workingArrangements.domain.BdglWorkProgress;
import com.supervision.workingArrangements.service.IBdglWorkProgressService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 工作任务进度录入Controller
 *
 * @author supervision
 * @date 2022-03-10
 */
@RestController
@RequestMapping("/workingArrangements/workProgress")
public class BdglWorkProgressController extends BaseController
{
    @Autowired
    private IBdglWorkProgressService bdglWorkProgressService;
    @Autowired
    private RedisCache redisCache;

    @Autowired
    public RedisTemplate redisTemplate;
    /**
     * 查询工作任务进度录入列表
     */
    @PreAuthorize("@ss.hasPermi('workingArrangements:workProgress:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglWorkProgress bdglWorkProgress)
    {
        startPage();
        List<BdglWorkProgress> list = bdglWorkProgressService.selectBdglWorkProgressList(bdglWorkProgress);
        return getDataTable(list);
    }

    /**
     * 导出工作任务进度录入列表
     */
    @PreAuthorize("@ss.hasPermi('workingArrangements:workProgress:export')")
    @Log(title = "工作任务进度录入", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglWorkProgress bdglWorkProgress)
    {
        List<BdglWorkProgress> list = bdglWorkProgressService.selectBdglWorkProgressList(bdglWorkProgress);
        ExcelUtil<BdglWorkProgress> util = new ExcelUtil<BdglWorkProgress>(BdglWorkProgress.class);
        return util.exportExcel(list, "工作任务进度录入数据");
    }

    /**
     * 获取工作任务进度录入详细信息
     */
    @PreAuthorize("@ss.hasPermi('workingArrangements:workProgress:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bdglWorkProgressService.selectBdglWorkProgressById(id));
    }

    /**
     * 新增工作任务进度录入
     */
    @PreAuthorize("@ss.hasPermi('workingArrangements:workProgress:add')")
    @Log(title = "工作任务进度录入", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglWorkProgress bdglWorkProgress)
    {
        return toAjax(bdglWorkProgressService.insertBdglWorkProgress(bdglWorkProgress));
    }

    /**
     * 修改工作任务进度录入
     */
    //@PreAuthorize("@ss.hasPermi('workingArrangements:workProgress:edit')")
    @Log(title = "工作任务进度录入", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglWorkProgress bdglWorkProgress)
    {
        return toAjax(bdglWorkProgressService.updateBdglWorkProgress(bdglWorkProgress));
    }

    /**
     * 删除工作任务进度录入
     */
    @PreAuthorize("@ss.hasPermi('workingArrangements:workProgress:remove')")
    @Log(title = "工作任务进度录入", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglWorkProgressService.deleteBdglWorkProgressByIds(ids));
    }

    @PostMapping("/workTrigger")
    public AjaxResult workTrigger() {
        AjaxResult ajab = AjaxResult.success();
        SysUser user = SecurityUtils.getLoginUser().getUser();

        if (redisTemplate.hasKey("work_request_" + user.getUserId())) { //用户存在返回提醒消息
            if (!redisCache.getCacheObject("work_request_" + user.getUserId()).equals(0)) {
                String str = (redisCache.getCacheObject("work_request_" + user.getUserId()).toString());

                BdglWorkProgress workProgress = bdglWorkProgressService.selectBdglWorkProgressById(Integer.valueOf(str));

                ajab.put("workUserid",user.getUserId());
                ajab.put("msg", "您发布的任务--"+workProgress.getPeopleName()+"--已完成,请前往查看！");
            } else {
                ajab.put("workRequest", 0);
            }

        } else {
            ajab.put("workRequest", 0); //当前用户无任务
        }
        return ajab;
    }

    @PostMapping("/workEachTrigger")
    public AjaxResult workEachTrigger() {
        AjaxResult ajab = AjaxResult.success();
        SysUser user = SecurityUtils.getLoginUser().getUser();

        if (redisTemplate.hasKey("work_each_" + user.getUserId())) { //用户存在返回提醒消息
            if (!redisCache.getCacheObject("work_each_" + user.getUserId()).equals(0)) {
                String str = (redisCache.getCacheObject("work_each_" + user.getUserId()).toString());
                ajab.put("workUserid",user.getUserId());
                ajab.put("msg", "您有一条工作任务需要处理！");
            } else {
                ajab.put("workEach", 0);
            }

        } else {
            ajab.put("workEach", 0); //当前用户无任务
        }
        return ajab;
    }
}
