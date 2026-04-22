package com.supervision.web.controller.militaryvehicleManagement;

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
import com.supervision.militaryvehicleManagement.domain.BdglDepartureInfo;
import com.supervision.militaryvehicleManagement.service.IBdglDepartureInfoService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 出车信息Controller
 * 
 * @author supervision
 * @date 2022-03-19
 */
@RestController
@RequestMapping("/militaryvehicleManagement/departureinfo")
public class BdglDepartureInfoController extends BaseController
{
    @Autowired
    private IBdglDepartureInfoService bdglDepartureInfoService;

    /**
     * 查询出车信息列表
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:departureinfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglDepartureInfo bdglDepartureInfo)
    {
        startPage();
        List<BdglDepartureInfo> list = bdglDepartureInfoService.selectBdglDepartureInfoList(bdglDepartureInfo);
        return getDataTable(list);
    }

    /**
     * 导出出车信息列表
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:departureinfo:export')")
    @Log(title = "出车信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglDepartureInfo bdglDepartureInfo)
    {
        List<BdglDepartureInfo> list = bdglDepartureInfoService.selectBdglDepartureInfoList(bdglDepartureInfo);
        ExcelUtil<BdglDepartureInfo> util = new ExcelUtil<BdglDepartureInfo>(BdglDepartureInfo.class);
        return util.exportExcel(list, "出车信息数据");
    }

    /**
     * 获取出车信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:departureinfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(bdglDepartureInfoService.selectBdglDepartureInfoById(id));
    }

    /**
     * 新增出车信息
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:departureinfo:add')")
    @Log(title = "出车信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglDepartureInfo bdglDepartureInfo)
    {
        return toAjax(bdglDepartureInfoService.insertBdglDepartureInfo(bdglDepartureInfo));
    }

    /**
     * 修改出车信息
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:departureinfo:edit')")
    @Log(title = "出车信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglDepartureInfo bdglDepartureInfo)
    {
        return toAjax(bdglDepartureInfoService.updateBdglDepartureInfo(bdglDepartureInfo));
    }

    /**
     * 删除出车信息
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:departureinfo:remove')")
    @Log(title = "出车信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bdglDepartureInfoService.deleteBdglDepartureInfoByIds(ids));
    }
}
