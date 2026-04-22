package com.supervision.web.controller.medicalhealth;

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
import com.supervision.medicalhealth.domain.BdglAntiepidemicRuin;
import com.supervision.medicalhealth.service.IBdglAntiepidemicRuinService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 防疫用品销毁记录Controller
 * 
 * @author supervision
 * @date 2022-04-07
 */
@RestController
@RequestMapping("/medicalhealth/ruin")
public class BdglAntiepidemicRuinController extends BaseController
{
    @Autowired
    private IBdglAntiepidemicRuinService bdglAntiepidemicRuinService;

    /**
     * 查询防疫用品销毁记录列表
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:ruin:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglAntiepidemicRuin bdglAntiepidemicRuin)
    {
        startPage();
        List<BdglAntiepidemicRuin> list = bdglAntiepidemicRuinService.selectBdglAntiepidemicRuinList(bdglAntiepidemicRuin);
        return getDataTable(list);
    }

    /**
     * 导出防疫用品销毁记录列表
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:ruin:export')")
    @Log(title = "防疫用品销毁记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglAntiepidemicRuin bdglAntiepidemicRuin)
    {
        List<BdglAntiepidemicRuin> list = bdglAntiepidemicRuinService.selectBdglAntiepidemicRuinList(bdglAntiepidemicRuin);
        ExcelUtil<BdglAntiepidemicRuin> util = new ExcelUtil<BdglAntiepidemicRuin>(BdglAntiepidemicRuin.class);
        return util.exportExcel(list, "防疫用品销毁记录数据");
    }

    /**
     * 获取防疫用品销毁记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:ruin:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bdglAntiepidemicRuinService.selectBdglAntiepidemicRuinById(id));
    }

    /**
     * 新增防疫用品销毁记录
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:ruin:add')")
    @Log(title = "防疫用品销毁记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglAntiepidemicRuin bdglAntiepidemicRuin)
    {
        return toAjax(bdglAntiepidemicRuinService.insertBdglAntiepidemicRuin(bdglAntiepidemicRuin));
    }

    /**
     * 修改防疫用品销毁记录
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:ruin:edit')")
    @Log(title = "防疫用品销毁记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglAntiepidemicRuin bdglAntiepidemicRuin)
    {
        return toAjax(bdglAntiepidemicRuinService.updateBdglAntiepidemicRuin(bdglAntiepidemicRuin));
    }

    /**
     * 删除防疫用品销毁记录
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:ruin:remove')")
    @Log(title = "防疫用品销毁记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglAntiepidemicRuinService.deleteBdglAntiepidemicRuinByIds(ids));
    }
}
