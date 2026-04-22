package com.supervision.web.controller.grassrootsregistration;

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
import com.supervision.grassrootsregistration.domain.BdglWeekworkRegister;
import com.supervision.grassrootsregistration.service.IBdglWeekworkRegisterService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 一周安排工作分Controller
 * 
 * @author supervision
 * @date 2022-02-28
 */
@RestController
@RequestMapping("/grassrootsregistration/register")
public class BdglWeekworkRegisterController extends BaseController
{
    @Autowired
    private IBdglWeekworkRegisterService bdglWeekworkRegisterService;

    /**
     * 查询一周安排工作分列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:register:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglWeekworkRegister bdglWeekworkRegister)
    {
        startPage();
        List<BdglWeekworkRegister> list = bdglWeekworkRegisterService.selectBdglWeekworkRegisterList(bdglWeekworkRegister);
        return getDataTable(list);
    }

    /**
     * 导出一周安排工作分列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:register:export')")
    @Log(title = "一周安排工作分", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglWeekworkRegister bdglWeekworkRegister)
    {
        List<BdglWeekworkRegister> list = bdglWeekworkRegisterService.selectBdglWeekworkRegisterList(bdglWeekworkRegister);
        ExcelUtil<BdglWeekworkRegister> util = new ExcelUtil<BdglWeekworkRegister>(BdglWeekworkRegister.class);
        return util.exportExcel(list, "一周安排工作分数据");
    }

    /**
     * 获取一周安排工作分详细信息
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:register:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(bdglWeekworkRegisterService.selectBdglWeekworkRegisterById(id));
    }

    /**
     * 新增一周安排工作分
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:register:add')")
    @Log(title = "一周安排工作分", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglWeekworkRegister bdglWeekworkRegister)
    {
        return toAjax(bdglWeekworkRegisterService.insertBdglWeekworkRegister(bdglWeekworkRegister));
    }

    /**
     * 修改一周安排工作分
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:register:edit')")
    @Log(title = "一周安排工作分", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglWeekworkRegister bdglWeekworkRegister)
    {
        return toAjax(bdglWeekworkRegisterService.updateBdglWeekworkRegister(bdglWeekworkRegister));
    }

    /**
     * 删除一周安排工作分
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:register:remove')")
    @Log(title = "一周安排工作分", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bdglWeekworkRegisterService.deleteBdglWeekworkRegisterByIds(ids));
    }
}
