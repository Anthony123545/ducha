package com.supervision.web.controller.grassrootsregistration;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.supervision.thought.domain.ThoughtTeacher;
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
import com.supervision.grassrootsregistration.domain.BdglDrillType;
import com.supervision.grassrootsregistration.service.IBdglDrillTypeService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 训练类别Controller
 * 
 * @author ruoyi
 * @date 2022-05-21
 */
@RestController
@RequestMapping("/grassrootsregistration/type")
public class BdglDrillTypeController extends BaseController
{
    @Autowired
    private IBdglDrillTypeService bdglDrillTypeService;

    /**
     * 查询训练类别列表
     */
    //@PreAuthorize("@ss.hasPermi('grassrootsregistration:type:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglDrillType bdglDrillType)
    {
        startPage();
        List<BdglDrillType> list = bdglDrillTypeService.selectBdglDrillTypeList(bdglDrillType);
        return getDataTable(list);
    }

    /**
     * 导出训练类别列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:type:export')")
    @Log(title = "训练类别", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BdglDrillType bdglDrillType)
    {
        List<BdglDrillType> list = bdglDrillTypeService.selectBdglDrillTypeList(bdglDrillType);
        ExcelUtil<BdglDrillType> util = new ExcelUtil<BdglDrillType>(BdglDrillType.class);
        util.exportExcel(response, list, "训练类别数据");
    }
    @GetMapping("/treeselect")
    public TableDataInfo treeselect(BdglDrillType thoughtTeacher) {
        ArrayList<BdglDrillType> thoughtTeachers1 = new ArrayList<>();
        List<BdglDrillType> thoughtTeachers = buildDeptTreeSelect(thoughtTeacher);

        return getDataTable(thoughtTeachers);
    }

    private List<BdglDrillType> buildDeptTreeSelect(BdglDrillType thoughtTeacher) {
        ArrayList<BdglDrillType> chilMenus = new ArrayList<>();
        List<BdglDrillType> thoughtTeachers = bdglDrillTypeService.selectBdglDrillTypeList(thoughtTeacher);
        if (thoughtTeachers.size() > 0) {
            BdglDrillType thoughtTeacher1 = new BdglDrillType();
            for (BdglDrillType teacher : thoughtTeachers) {
                thoughtTeacher1.setParentId(teacher.getId());
                List<BdglDrillType> thoughtTeachers1 = buildDeptTreeSelect(thoughtTeacher1);
                teacher.setChildren(thoughtTeachers1);
            }
        }
        thoughtTeacher.setChildren(chilMenus);
        return thoughtTeachers;
    }
    /**
     * 获取训练类别详细信息
     */
    //@PreAuthorize("@ss.hasPermi('grassrootsregistration:type:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(bdglDrillTypeService.selectBdglDrillTypeById(id));
    }

    /**
     * 新增训练类别
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:type:add')")
    @Log(title = "训练类别", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglDrillType bdglDrillType)
    {
        return toAjax(bdglDrillTypeService.insertBdglDrillType(bdglDrillType));
    }

    /**
     * 修改训练类别
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:type:edit')")
    @Log(title = "训练类别", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglDrillType bdglDrillType)
    {
        return toAjax(bdglDrillTypeService.updateBdglDrillType(bdglDrillType));
    }

    /**
     * 删除训练类别
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:type:remove')")
    @Log(title = "训练类别", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bdglDrillTypeService.deleteBdglDrillTypeByIds(ids));
    }
}