package com.supervision.web.controller.medicalhealth;

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
import com.supervision.medicalhealth.domain.BdglExamineDetails;
import com.supervision.medicalhealth.service.IBdglExamineDetailsService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 体检信息Controller
 * 
 * @author ruoyi
 * @date 2022-08-05
 */
@RestController
@RequestMapping("/medicalhealth/details")
public class BdglExamineDetailsController extends BaseController
{
    @Autowired
    private IBdglExamineDetailsService bdglExamineDetailsService;

    /**
     * 查询体检信息列表
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:details:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglExamineDetails bdglExamineDetails)
    {
        startPage();
        List<BdglExamineDetails> list = bdglExamineDetailsService.selectBdglExamineDetailsList(bdglExamineDetails);
        return getDataTable(list);
    }

    /**
     * 导出体检信息列表
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:details:export')")
    @Log(title = "体检信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BdglExamineDetails bdglExamineDetails)
    {
        List<BdglExamineDetails> list = bdglExamineDetailsService.selectBdglExamineDetailsList(bdglExamineDetails);
        ExcelUtil<BdglExamineDetails> util = new ExcelUtil<BdglExamineDetails>(BdglExamineDetails.class);
        util.exportExcel(response, list, "体检信息数据");
    }

    /**
     * 获取体检信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:details:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bdglExamineDetailsService.selectBdglExamineDetailsById(id));
    }

    /**
     * 新增体检信息
     */
    //@PreAuthorize("@ss.hasPermi('medicalhealth:details:add')")
    @Log(title = "体检信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglExamineDetails bdglExamineDetails)
    {
        return toAjax(bdglExamineDetailsService.insertBdglExamineDetails(bdglExamineDetails));
    }

    /**
     * 修改体检信息
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:details:edit')")
    @Log(title = "体检信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglExamineDetails bdglExamineDetails)
    {
        return toAjax(bdglExamineDetailsService.updateBdglExamineDetails(bdglExamineDetails));
    }

    /**
     * 删除体检信息
     */
    //@PreAuthorize("@ss.hasPermi('medicalhealth:details:remove')")
    @Log(title = "体检信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Integer id)
    {
        return toAjax(bdglExamineDetailsService.deleteBdglExamineDetailsById(id));
    }
}