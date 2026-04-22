package com.supervision.web.controller.peopleChuRu;

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
import com.supervision.peopleManage.domain.Visitor;
import com.supervision.peopleManage.service.IVisitorService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 访客管理Controller
 * 
 * @author supervision
 * @date 2025-11-19
 */
@RestController
@RequestMapping("/peopleManage/visitor")
public class VisitorController extends BaseController
{
    @Autowired
    private IVisitorService visitorService;

    /**
     * 查询访客管理列表
     */
//    @PreAuthorize("@ss.hasPermi('peopleManage:visitor:list')")
    @GetMapping("/list")
    public TableDataInfo list(Visitor visitor)
    {
        startPage();
        List<Visitor> list = visitorService.selectVisitorList(visitor);
        return getDataTable(list);
    }

    /**
     * 导出访客管理列表
     */
//    @PreAuthorize("@ss.hasPermi('peopleManage:visitor:export')")
    @Log(title = "访客管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Visitor visitor)
    {
        List<Visitor> list = visitorService.selectVisitorList(visitor);
        ExcelUtil<Visitor> util = new ExcelUtil<Visitor>(Visitor.class);
        return util.exportExcel(list, "访客管理数据");
    }

    /**
     * 获取访客管理详细信息
     */
//    @PreAuthorize("@ss.hasPermi('peopleManage:visitor:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(visitorService.selectVisitorById(id));
    }

    /**
     * 新增访客管理
     */
//    @PreAuthorize("@ss.hasPermi('peopleManage:visitor:add')")
    @Log(title = "访客管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Visitor visitor)
    {
        // 为工号添加前缀
        if (visitor.getEmployeeNumber() != null && !visitor.getEmployeeNumber().isEmpty()) {
            if (!visitor.getEmployeeNumber().startsWith("VISITOR_")) {
                visitor.setEmployeeNumber("VISITOR_" + visitor.getEmployeeNumber());
            }
        } else {
            // 如果没有提供工号，则生成一个默认的
            visitor.setEmployeeNumber("VISITOR_DEFAULT");
        }
        return toAjax(visitorService.insertVisitor(visitor));
    }

    /**
     * 修改访客管理
     */
//    @PreAuthorize("@ss.hasPermi('peopleManage:visitor:edit')")
    @Log(title = "访客管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Visitor visitor)
    {
        // 为工号添加前缀
        if (visitor.getEmployeeNumber() != null && !visitor.getEmployeeNumber().isEmpty()) {
            if (!visitor.getEmployeeNumber().startsWith("VISITOR_")) {
                visitor.setEmployeeNumber("VISITOR_" + visitor.getEmployeeNumber());
            }
        }
        return toAjax(visitorService.updateVisitor(visitor));
    }

    /**
     * 删除访客管理
     */
//    @PreAuthorize("@ss.hasPermi('peopleManage:visitor:remove')")
    @Log(title = "访客管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(visitorService.deleteVisitorByIds(ids));
    }
}