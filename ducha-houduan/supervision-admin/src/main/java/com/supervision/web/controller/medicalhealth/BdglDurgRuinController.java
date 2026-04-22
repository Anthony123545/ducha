package com.supervision.web.controller.medicalhealth;

import java.util.List;

import com.supervision.common.annotation.Log;
import com.supervision.common.core.controller.BaseController;
import com.supervision.common.core.domain.AjaxResult;
import com.supervision.common.core.page.TableDataInfo;
import com.supervision.common.enums.BusinessType;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.medicalhealth.domain.BdglDurgRuin;
import com.supervision.medicalhealth.service.IBdglDurgRuinService;
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


/**
 * 药品销毁记录Controller
 * 
 * @author hr
 * @date 2022-02-12
 */
@RestController
@RequestMapping("/medicalhealth/bdgldurgruin")
public class BdglDurgRuinController extends BaseController
{
    @Autowired
    private IBdglDurgRuinService bdglDurgRuinService;

    /**
     * 查询药品销毁记录列表
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:bdgldurgruin:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglDurgRuin bdglDurgRuin)
    {
        startPage();
        List<BdglDurgRuin> list = bdglDurgRuinService.selectBdglDurgRuinList(bdglDurgRuin);
        return getDataTable(list);
    }

    /**
     * 导出药品销毁记录列表
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:bdgldurgruin:export')")
    @Log(title = "药品销毁记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglDurgRuin bdglDurgRuin)
    {
        List<BdglDurgRuin> list = bdglDurgRuinService.selectBdglDurgRuinList(bdglDurgRuin);
        ExcelUtil<BdglDurgRuin> util = new ExcelUtil<BdglDurgRuin>(BdglDurgRuin.class);
        return util.exportExcel(list, "药品销毁记录数据");
    }

    /**
     * 获取药品销毁记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:bdgldurgruin:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bdglDurgRuinService.selectBdglDurgRuinById(id));
    }

    /**
     * 新增药品销毁记录
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:bdgldurgruin:add')")
    @Log(title = "药品销毁记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglDurgRuin bdglDurgRuin)
    {
        return toAjax(bdglDurgRuinService.insertBdglDurgRuin(bdglDurgRuin));
    }

    /**
     * 修改药品销毁记录
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:bdgldurgruin:edit')")
    @Log(title = "药品销毁记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglDurgRuin bdglDurgRuin)
    {
        return toAjax(bdglDurgRuinService.updateBdglDurgRuin(bdglDurgRuin));
    }

    /**
     * 删除药品销毁记录
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:bdgldurgruin:remove')")
    @Log(title = "药品销毁记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglDurgRuinService.deleteBdglDurgRuinByIds(ids));
    }
}
