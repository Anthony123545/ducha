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
import com.supervision.grassrootsregistration.domain.BdglIpaddressConfig;
import com.supervision.grassrootsregistration.service.IBdglIpaddressConfigService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 获取考勤记录的IP地址配置Controller
 *
 * @author ruoyi
 * @date 2022-11-09
 */
@RestController
@RequestMapping("/grassrootsregistration/ipaddressconfig")
public class BdglIpaddressConfigController extends BaseController
{
    @Autowired
    private IBdglIpaddressConfigService bdglIpaddressConfigService;

    /**
     * 查询获取考勤记录的IP地址配置列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:ipaddressconfig:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglIpaddressConfig bdglIpaddressConfig)
    {
        startPage();
        List<BdglIpaddressConfig> list = bdglIpaddressConfigService.selectBdglIpaddressConfigList(bdglIpaddressConfig);
        return getDataTable(list);
    }

    /**
     * 导出获取考勤记录的IP地址配置列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:ipaddressconfig:export')")
    @Log(title = "获取考勤记录的IP地址配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BdglIpaddressConfig bdglIpaddressConfig)
    {
        List<BdglIpaddressConfig> list = bdglIpaddressConfigService.selectBdglIpaddressConfigList(bdglIpaddressConfig);
        ExcelUtil<BdglIpaddressConfig> util = new ExcelUtil<BdglIpaddressConfig>(BdglIpaddressConfig.class);
        util.exportExcel(response, list, "获取考勤记录的IP地址配置数据");
    }

    /**
     * 获取获取考勤记录的IP地址配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:ipaddressconfig:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bdglIpaddressConfigService.selectBdglIpaddressConfigById(id));
    }

    /**
     * 新增获取考勤记录的IP地址配置
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:ipaddressconfig:add')")
    @Log(title = "获取考勤记录的IP地址配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglIpaddressConfig bdglIpaddressConfig)
    {
        return toAjax(bdglIpaddressConfigService.insertBdglIpaddressConfig(bdglIpaddressConfig));
    }

    /**
     * 修改获取考勤记录的IP地址配置
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:ipaddressconfig:edit')")
    @Log(title = "获取考勤记录的IP地址配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglIpaddressConfig bdglIpaddressConfig)
    {
        return toAjax(bdglIpaddressConfigService.updateBdglIpaddressConfig(bdglIpaddressConfig));
    }

    /**
     * 删除获取考勤记录的IP地址配置
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:ipaddressconfig:remove')")
    @Log(title = "获取考勤记录的IP地址配置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglIpaddressConfigService.deleteBdglIpaddressConfigByIds(ids));
    }
}
