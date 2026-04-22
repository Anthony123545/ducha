package com.supervision.web.controller.materialManagement;

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
import com.supervision.materialManagement.domain.BdglPropertyChange;
import com.supervision.materialManagement.service.IBdglPropertyChangeService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 营具物资变更Controller
 * 
 * @author supervision
 * @date 2022-03-08
 */
@RestController
@RequestMapping("/materialManagement/propertyChange")
public class BdglPropertyChangeController extends BaseController
{
    @Autowired
    private IBdglPropertyChangeService bdglPropertyChangeService;

    /**
     * 查询营具物资变更列表
     */
    @PreAuthorize("@ss.hasPermi('materialManagement:propertyChange:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglPropertyChange bdglPropertyChange)
    {
        startPage();
        List<BdglPropertyChange> list = bdglPropertyChangeService.selectBdglPropertyChangeList(bdglPropertyChange);
        return getDataTable(list);
    }

    /**
     * 导出营具物资变更列表
     */
    @PreAuthorize("@ss.hasPermi('materialManagement:propertyChange:export')")
    @Log(title = "营具物资变更", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglPropertyChange bdglPropertyChange)
    {
        List<BdglPropertyChange> list = bdglPropertyChangeService.selectBdglPropertyChangeList(bdglPropertyChange);
        ExcelUtil<BdglPropertyChange> util = new ExcelUtil<BdglPropertyChange>(BdglPropertyChange.class);
        return util.exportExcel(list, "营具物资变更数据");
    }

    /**
     * 获取营具物资变更详细信息
     */
    @PreAuthorize("@ss.hasPermi('materialManagement:propertyChange:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bdglPropertyChangeService.selectBdglPropertyChangeById(id));
    }

    /**
     * 新增营具物资变更
     */
    @PreAuthorize("@ss.hasPermi('materialManagement:propertyChange:add')")
    @Log(title = "营具物资变更", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglPropertyChange bdglPropertyChange)
    {
        return toAjax(bdglPropertyChangeService.insertBdglPropertyChange(bdglPropertyChange));
    }

    /**
     * 修改营具物资变更
     */
    @PreAuthorize("@ss.hasPermi('materialManagement:propertyChange:edit')")
    @Log(title = "营具物资变更", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglPropertyChange bdglPropertyChange)
    {
        return toAjax(bdglPropertyChangeService.updateBdglPropertyChange(bdglPropertyChange));
    }

    /**
     * 删除营具物资变更
     */
    @PreAuthorize("@ss.hasPermi('materialManagement:propertyChange:remove')")
    @Log(title = "营具物资变更", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglPropertyChangeService.deleteBdglPropertyChangeByIds(ids));
    }
}
