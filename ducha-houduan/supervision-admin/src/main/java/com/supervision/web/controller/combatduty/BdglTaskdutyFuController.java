package com.supervision.web.controller.combatduty;

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
import com.supervision.combatduty.domain.BdglTaskdutyFu;
import com.supervision.combatduty.service.IBdglTaskdutyFuService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 执行任务附表Controller
 * 
 * @author supervision
 * @date 2022-05-06
 */
@RestController
@RequestMapping("/combatduty/taskdutyfu")
public class BdglTaskdutyFuController extends BaseController
{
    @Autowired
    private IBdglTaskdutyFuService bdglTaskdutyFuService;

    /**
     * 查询执行任务附表列表
     */
    @PreAuthorize("@ss.hasPermi('combatduty:taskduty_fu:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglTaskdutyFu bdglTaskdutyFu)
    {
        startPage();
        List<BdglTaskdutyFu> list = bdglTaskdutyFuService.selectBdglTaskdutyFuList(bdglTaskdutyFu);
        return getDataTable(list);
    }

    /**
     * 导出执行任务附表列表
     */
    @PreAuthorize("@ss.hasPermi('combatduty:taskduty_fu:export')")
    @Log(title = "执行任务附表", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglTaskdutyFu bdglTaskdutyFu)
    {
        List<BdglTaskdutyFu> list = bdglTaskdutyFuService.selectBdglTaskdutyFuList(bdglTaskdutyFu);
        ExcelUtil<BdglTaskdutyFu> util = new ExcelUtil<BdglTaskdutyFu>(BdglTaskdutyFu.class);
        return util.exportExcel(list, "执行任务附表数据");
    }

    /**
     * 获取执行任务附表详细信息
     */
    @PreAuthorize("@ss.hasPermi('combatduty:taskduty_fu:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(bdglTaskdutyFuService.selectBdglTaskdutyFuById(id));
    }

    /**
     * 新增执行任务附表
     */
    @PreAuthorize("@ss.hasPermi('combatduty:taskduty_fu:add')")
    @Log(title = "执行任务附表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglTaskdutyFu bdglTaskdutyFu)
    {
        return toAjax(bdglTaskdutyFuService.insertBdglTaskdutyFu(bdglTaskdutyFu));
    }

    /**
     * 修改执行任务附表
     */
    @PreAuthorize("@ss.hasPermi('combatduty:taskduty_fu:edit')")
    @Log(title = "执行任务附表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglTaskdutyFu bdglTaskdutyFu)
    {
        return toAjax(bdglTaskdutyFuService.updateBdglTaskdutyFu(bdglTaskdutyFu));
    }

    /**
     * 删除执行任务附表
     */
    //@PreAuthorize("@ss.hasPermi('combatduty:taskduty_fu:remove')")
    @Log(title = "执行任务附表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bdglTaskdutyFuService.deleteBdglTaskdutyFuByIds(ids));
    }
}
