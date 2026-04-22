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
import com.supervision.grassrootsregistration.domain.BdglCookbookRegistration;
import com.supervision.grassrootsregistration.service.IBdglCookbookRegistrationService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 食谱登记Controller
 * 
 * @author supervision
 * @date 2022-03-02
 */
@RestController
@RequestMapping("/grassrootsregistration/registration")
public class BdglCookbookRegistrationController extends BaseController
{
    @Autowired
    private IBdglCookbookRegistrationService bdglCookbookRegistrationService;

    /**
     * 查询食谱登记列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:registration:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglCookbookRegistration bdglCookbookRegistration)
    {
        startPage();
        List<BdglCookbookRegistration> list = bdglCookbookRegistrationService.selectBdglCookbookRegistrationList(bdglCookbookRegistration);
        return getDataTable(list);
    }

    /**
     * 导出食谱登记列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:registration:export')")
    @Log(title = "食谱登记", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglCookbookRegistration bdglCookbookRegistration)
    {
        List<BdglCookbookRegistration> list = bdglCookbookRegistrationService.selectBdglCookbookRegistrationList(bdglCookbookRegistration);
        ExcelUtil<BdglCookbookRegistration> util = new ExcelUtil<BdglCookbookRegistration>(BdglCookbookRegistration.class);
        return util.exportExcel(list, "食谱登记数据");
    }

    /**
     * 获取食谱登记详细信息
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:registration:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bdglCookbookRegistrationService.selectBdglCookbookRegistrationById(id));
    }

    /**
     * 新增食谱登记
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:registration:add')")
    @Log(title = "食谱登记", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglCookbookRegistration bdglCookbookRegistration)
    {
        return toAjax(bdglCookbookRegistrationService.insertBdglCookbookRegistration(bdglCookbookRegistration));
    }

    /**
     * 修改食谱登记
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:registration:edit')")
    @Log(title = "食谱登记", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglCookbookRegistration bdglCookbookRegistration)
    {
        return toAjax(bdglCookbookRegistrationService.updateBdglCookbookRegistration(bdglCookbookRegistration));
    }

    /**
     * 删除食谱登记
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:registration:remove')")
    @Log(title = "食谱登记", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglCookbookRegistrationService.deleteBdglCookbookRegistrationByIds(ids));
    }
}
