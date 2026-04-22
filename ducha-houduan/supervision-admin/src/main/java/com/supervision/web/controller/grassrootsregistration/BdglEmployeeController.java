package com.supervision.web.controller.grassrootsregistration;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.supervision.grassrootsregistration.domain.BdglEmployee;
import com.supervision.grassrootsregistration.service.IBdglEmployeeService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 门禁人员信息Controller
 * 
 * @author ruoyi
 * @date 2023-03-13
 */
@RestController
@RequestMapping("/grassrootsregistration/employee")
public class BdglEmployeeController extends BaseController
{
    @Autowired
    private IBdglEmployeeService bdglEmployeeService;

    /**
     * 查询门禁人员信息列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:employee:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglEmployee bdglEmployee)
    {
        startPage();
        List<BdglEmployee> list = bdglEmployeeService.selectBdglEmployeeList(bdglEmployee);
        return getDataTable(list);
    }

    /**
     * 导出门禁人员信息列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:employee:export')")
    @Log(title = "门禁人员信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BdglEmployee bdglEmployee)
    {
        List<BdglEmployee> list = bdglEmployeeService.selectBdglEmployeeList(bdglEmployee);
        ExcelUtil<BdglEmployee> util = new ExcelUtil<BdglEmployee>(BdglEmployee.class);
        util.exportExcel(response, list, "门禁人员信息数据");
    }

    /**
     * 获取门禁人员信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:employee:query')")
    @GetMapping(value = "/{pin}")
    public AjaxResult getInfo(@PathVariable("pin") Long pin)
    {
        return AjaxResult.success(bdglEmployeeService.selectBdglEmployeeByPin(pin));
    }

    /**
     * 新增门禁人员信息
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:employee:add')")
    @Log(title = "门禁人员信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglEmployee bdglEmployee)
    {
        return toAjax(bdglEmployeeService.insertBdglEmployee(bdglEmployee));
    }

    /**
     * 修改门禁人员信息
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:employee:edit')")
    @Log(title = "门禁人员信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglEmployee bdglEmployee)
    {
        return toAjax(bdglEmployeeService.updateBdglEmployee(bdglEmployee));
    }

    /**
     * 删除门禁人员信息
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:employee:remove')")
    @Log(title = "门禁人员信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{pins}")
    public AjaxResult remove(@PathVariable Long[] pins)
    {
        return toAjax(bdglEmployeeService.deleteBdglEmployeeByPins(pins));
    }
}
