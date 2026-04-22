package com.supervision.web.controller.doormanManage;

import java.util.List;

import com.supervision.common.core.domain.model.LoginUser;
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
import com.supervision.doormanManage.domain.BdglVisit;
import com.supervision.doormanManage.service.IBdglVisitService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 人员来访登记Controller
 *
 * @author supervision
 * @date 2022-03-23
 */
@RestController
@RequestMapping("/doormanManage/bdglVisit")
public class BdglVisitController extends BaseController
{
    @Autowired
    private IBdglVisitService bdglVisitService;

    /**
     * 查询人员来访登记列表
     */
    @PreAuthorize("@ss.hasPermi('doormanManage:bdglVisit:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglVisit bdglVisit)
    {
        startPage();
        List<BdglVisit> list = bdglVisitService.selectBdglVisitList(bdglVisit);
        return getDataTable(list);
    }

    /**
     * 导出人员来访登记列表
     */
    @PreAuthorize("@ss.hasPermi('doormanManage:bdglVisit:export')")
    @Log(title = "人员来访登记", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglVisit bdglVisit)
    {
        List<BdglVisit> list = bdglVisitService.selectBdglVisitList(bdglVisit);
        ExcelUtil<BdglVisit> util = new ExcelUtil<BdglVisit>(BdglVisit.class);
        return util.exportExcel(list, "人员来访登记数据");
    }

    /**
     * 获取人员来访登记详细信息
     */
    @PreAuthorize("@ss.hasPermi('doormanManage:bdglVisit:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bdglVisitService.selectBdglVisitById(id));
    }

    /**
     * 新增人员来访登记
     */
    @PreAuthorize("@ss.hasPermi('doormanManage:bdglVisit:add')")
    @Log(title = "人员来访登记", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglVisit bdglVisit)
    {
        LoginUser loginUser = getLoginUser();
        return toAjax(bdglVisitService.insertBdglVisit(bdglVisit,loginUser));
    }

    /**
     * 修改人员来访登记
     */
    @PreAuthorize("@ss.hasPermi('doormanManage:bdglVisit:edit')")
    @Log(title = "人员来访登记", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglVisit bdglVisit)
    {
        return toAjax(bdglVisitService.updateBdglVisit(bdglVisit));
    }

    /**
     * 删除人员来访登记
     */
    @PreAuthorize("@ss.hasPermi('doormanManage:bdglVisit:remove')")
    @Log(title = "人员来访登记", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglVisitService.deleteBdglVisitByIds(ids));
    }
}