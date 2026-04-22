package com.supervision.web.controller.peopleChuRu;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
import com.supervision.peopleChuRu.domain.BdglLeaveFu;
import com.supervision.peopleChuRu.service.IBdglLeaveFuService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 特殊审批记录Controller
 * 
 * @author supervision
 * @date 2022-03-18
 */
@RestController
@RequestMapping("/leaveFu/peopleChuRu")
public class BdglLeaveFuController extends BaseController
{
    @Autowired
    private IBdglLeaveFuService bdglLeaveFuService;

    /**
     * 查询特殊审批记录列表
     */
    @PreAuthorize("@ss.hasPermi('leaveFu:peopleChuRu:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglLeaveFu bdglLeaveFu)
    {
        startPage();
        List<BdglLeaveFu> list = bdglLeaveFuService.selectBdglLeaveFuList(bdglLeaveFu);
        return getDataTable(list);
    }

    /**
     * 导出特殊审批记录列表
     */
    @PreAuthorize("@ss.hasPermi('leaveFu:peopleChuRu:export')")
    @Log(title = "特殊审批记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglLeaveFu bdglLeaveFu)
    {
        List<BdglLeaveFu> list = bdglLeaveFuService.selectBdglLeaveFuList(bdglLeaveFu);
        ExcelUtil<BdglLeaveFu> util = new ExcelUtil<BdglLeaveFu>(BdglLeaveFu.class);
        return util.exportExcel(list, "特殊审批记录数据");
    }

    /**
     * 获取特殊审批记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('leaveFu:peopleChuRu:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(bdglLeaveFuService.selectBdglLeaveFuById(id));
    }

    /**
     * 新增特殊审批记录
     */
    @PreAuthorize("@ss.hasPermi('leaveFu:peopleChuRu:add')")
    @Log(title = "特殊审批记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglLeaveFu bdglLeaveFu)
    {
        return toAjax(bdglLeaveFuService.insertBdglLeaveFu(bdglLeaveFu));
    }

    /**
     * 修改特殊审批记录
     */
    @PreAuthorize("@ss.hasPermi('leaveFu:peopleChuRu:edit')")
    @Log(title = "特殊审批记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglLeaveFu bdglLeaveFu)
    {
        return toAjax(bdglLeaveFuService.updateBdglLeaveFu(bdglLeaveFu));
    }

    /**
     * 删除特殊审批记录
     */
    @PreAuthorize("@ss.hasPermi('leaveFu:peopleChuRu:remove')")
    @Log(title = "特殊审批记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bdglLeaveFuService.deleteBdglLeaveFuByIds(ids));
    }
}
