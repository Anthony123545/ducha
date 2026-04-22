package com.supervision.web.controller.peopleManage;

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
import com.supervision.peopleManage.domain.BdglPeopleRecallAuditlog;
import com.supervision.peopleManage.service.IBdglPeopleRecallAuditlogService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 人员调出审核记录Controller
 *
 * @author ruoyi
 * @date 2023-01-10
 */
@RestController
@RequestMapping("/peopleManage/auditlog")
public class BdglPeopleRecallAuditlogController extends BaseController
{
    @Autowired
    private IBdglPeopleRecallAuditlogService bdglPeopleRecallAuditlogService;

    /**
     * 查询人员调出审核记录列表
     */
    @PreAuthorize("@ss.hasPermi('peopleManage:auditlog:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglPeopleRecallAuditlog bdglPeopleRecallAuditlog)
    {
        startPage();
        List<BdglPeopleRecallAuditlog> list = bdglPeopleRecallAuditlogService.selectBdglPeopleRecallAuditlogList(bdglPeopleRecallAuditlog);
        return getDataTable(list);
    }

    /**
     * 导出人员调出审核记录列表
     */
    @PreAuthorize("@ss.hasPermi('peopleManage:auditlog:export')")
    @Log(title = "人员调出审核记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BdglPeopleRecallAuditlog bdglPeopleRecallAuditlog)
    {
        List<BdglPeopleRecallAuditlog> list = bdglPeopleRecallAuditlogService.selectBdglPeopleRecallAuditlogList(bdglPeopleRecallAuditlog);
        ExcelUtil<BdglPeopleRecallAuditlog> util = new ExcelUtil<BdglPeopleRecallAuditlog>(BdglPeopleRecallAuditlog.class);
        util.exportExcel(response, list, "人员调出审核记录数据");
    }

    /**
     * 获取人员调出审核记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('peopleManage:auditlog:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bdglPeopleRecallAuditlogService.selectBdglPeopleRecallAuditlogById(id));
    }

    /**
     * 新增人员调出审核记录
     */
    @PreAuthorize("@ss.hasPermi('peopleManage:auditlog:add')")
    @Log(title = "人员调出审核记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglPeopleRecallAuditlog bdglPeopleRecallAuditlog)
    {
        return toAjax(bdglPeopleRecallAuditlogService.insertBdglPeopleRecallAuditlog(bdglPeopleRecallAuditlog));
    }

    /**
     * 修改人员调出审核记录
     */
    @PreAuthorize("@ss.hasPermi('peopleManage:auditlog:edit')")
    @Log(title = "人员调出审核记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglPeopleRecallAuditlog bdglPeopleRecallAuditlog)
    {
        return toAjax(bdglPeopleRecallAuditlogService.updateBdglPeopleRecallAuditlog(bdglPeopleRecallAuditlog));
    }

    /**
     * 删除人员调出审核记录
     */
    @PreAuthorize("@ss.hasPermi('peopleManage:auditlog:remove')")
    @Log(title = "人员调出审核记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglPeopleRecallAuditlogService.deleteBdglPeopleRecallAuditlogByIds(ids));
    }
}
