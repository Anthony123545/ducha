package com.supervision.web.controller.workingArrangements;

import java.util.List;
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
import com.supervision.workingArrangements.domain.BdglPlanProgress;
import com.supervision.workingArrangements.service.IBdglPlanProgressService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 进度录入Controller
 * 
 * @author supervision
 * @date 2022-03-10
 */
@RestController
@RequestMapping("/workingArrangements/progress")
public class BdglPlanProgressController extends BaseController
{
    @Autowired
    private IBdglPlanProgressService bdglPlanProgressService;

    /**
     * 查询进度录入列表
     */
    @PreAuthorize("@ss.hasPermi('workingArrangements:progress:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglPlanProgress bdglPlanProgress)
    {
        startPage();
        List<BdglPlanProgress> list = bdglPlanProgressService.selectBdglPlanProgressList(bdglPlanProgress);
        return getDataTable(list);
    }

    /**
     * 导出进度录入列表
     */
    @PreAuthorize("@ss.hasPermi('workingArrangements:progress:export')")
    @Log(title = "进度录入", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglPlanProgress bdglPlanProgress)
    {
        List<BdglPlanProgress> list = bdglPlanProgressService.selectBdglPlanProgressList(bdglPlanProgress);
        ExcelUtil<BdglPlanProgress> util = new ExcelUtil<BdglPlanProgress>(BdglPlanProgress.class);
        return util.exportExcel(list, "进度录入数据");
    }

    /**
     * 获取进度录入详细信息
     */
    @PreAuthorize("@ss.hasPermi('workingArrangements:progress:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bdglPlanProgressService.selectBdglPlanProgressById(id));
    }

    /**
     * 新增进度录入
     */
    @PreAuthorize("@ss.hasPermi('workingArrangements:progress:add')")
    @Log(title = "进度录入", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglPlanProgress bdglPlanProgress)
    {
        return toAjax(bdglPlanProgressService.insertBdglPlanProgress(bdglPlanProgress));
    }

    /**
     * 修改进度录入
     */
    //@PreAuthorize("@ss.hasPermi('workingArrangements:progress:edit')")
    @Log(title = "进度录入", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglPlanProgress bdglPlanProgress)
    {
        return toAjax(bdglPlanProgressService.updateBdglPlanProgress(bdglPlanProgress));
    }


    /**
     * 删除进度录入
     */
    @PreAuthorize("@ss.hasPermi('workingArrangements:progress:remove')")
    @Log(title = "进度录入", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglPlanProgressService.deleteBdglPlanProgressByIds(ids));
    }
}
