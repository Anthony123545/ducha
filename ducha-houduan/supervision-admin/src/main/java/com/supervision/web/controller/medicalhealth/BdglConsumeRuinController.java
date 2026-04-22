package com.supervision.web.controller.medicalhealth;

import java.util.List;

import com.supervision.common.annotation.Log;
import com.supervision.common.core.controller.BaseController;
import com.supervision.common.core.domain.AjaxResult;
import com.supervision.common.core.page.TableDataInfo;
import com.supervision.common.enums.BusinessType;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.medicalhealth.domain.BdglConsumeRuin;
import com.supervision.medicalhealth.service.IBdglConsumeRuinService;
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
 * 耗材销毁记录Controller
 * 
 * @author hr
 * @date 2022-02-12
 */
@RestController
@RequestMapping("/medicalhealth/consumeruin")
public class BdglConsumeRuinController extends BaseController
{
    @Autowired
    private IBdglConsumeRuinService bdglConsumeRuinService;

    /**
     * 查询耗材销毁记录列表
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:consumeruin:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglConsumeRuin bdglConsumeRuin)
    {
        startPage();
        List<BdglConsumeRuin> list = bdglConsumeRuinService.selectBdglConsumeRuinList(bdglConsumeRuin);
        return getDataTable(list);
    }

    /**
     * 导出耗材销毁记录列表
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:consumeruin:export')")
    @Log(title = "耗材销毁记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglConsumeRuin bdglConsumeRuin)
    {
        List<BdglConsumeRuin> list = bdglConsumeRuinService.selectBdglConsumeRuinList(bdglConsumeRuin);
        ExcelUtil<BdglConsumeRuin> util = new ExcelUtil<BdglConsumeRuin>(BdglConsumeRuin.class);
        return util.exportExcel(list, "耗材销毁记录数据");
    }

    /**
     * 获取耗材销毁记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:consumeruin:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bdglConsumeRuinService.selectBdglConsumeRuinById(id));
    }

    /**
     * 新增耗材销毁记录
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:consumeruin:add')")
    @Log(title = "耗材销毁记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglConsumeRuin bdglConsumeRuin)
    {
        return toAjax(bdglConsumeRuinService.insertBdglConsumeRuin(bdglConsumeRuin));
    }

    /**
     * 修改耗材销毁记录
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:consumeruin:edit')")
    @Log(title = "耗材销毁记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglConsumeRuin bdglConsumeRuin)
    {
        return toAjax(bdglConsumeRuinService.updateBdglConsumeRuin(bdglConsumeRuin));
    }

    /**
     * 删除耗材销毁记录
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:consumeruin:remove')")
    @Log(title = "耗材销毁记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglConsumeRuinService.deleteBdglConsumeRuinByIds(ids));
    }
}
