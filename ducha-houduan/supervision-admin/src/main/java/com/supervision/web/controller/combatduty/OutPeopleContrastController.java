package com.supervision.web.controller.combatduty;

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
        import com.supervision.combatduty.domain.OutPeopleContrast;
        import com.supervision.combatduty.service.IOutPeopleContrastService;
        import com.supervision.common.utils.poi.ExcelUtil;
        import com.supervision.common.core.page.TableDataInfo;

/**
 * 外出人员对比设置Controller
 *
 * @author ruoyi
 * @date 2023-04-07
 */
@RestController
@RequestMapping("/combatduty/contrast")
public class OutPeopleContrastController extends BaseController
{
    @Autowired
    private IOutPeopleContrastService outPeopleContrastService;

    /**
     * 查询外出人员对比设置列表
     */
    //@PreAuthorize("@ss.hasPermi('combatduty:contrast:list')")
    @GetMapping("/list")
    public TableDataInfo list(OutPeopleContrast outPeopleContrast)
    {
        startPage();
        List<OutPeopleContrast> list = outPeopleContrastService.selectOutPeopleContrastList(outPeopleContrast);
        return getDataTable(list);
    }

    /**
     * 导出外出人员对比设置列表
     */
    //@PreAuthorize("@ss.hasPermi('combatduty:contrast:export')")
    @Log(title = "外出人员对比设置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OutPeopleContrast outPeopleContrast)
    {
        List<OutPeopleContrast> list = outPeopleContrastService.selectOutPeopleContrastList(outPeopleContrast);
        ExcelUtil<OutPeopleContrast> util = new ExcelUtil<OutPeopleContrast>(OutPeopleContrast.class);
        util.exportExcel(response, list, "外出人员对比设置数据");
    }

    /**
     * 获取外出人员对比设置详细信息
     */
    //@PreAuthorize("@ss.hasPermi('combatduty:contrast:query')")
    @GetMapping("/get")
    public AjaxResult getInfo()
    {
        return AjaxResult.success(outPeopleContrastService.selectOutPeopleContrastById(1));
    }

    /**
     * 新增外出人员对比设置
     */
    //@PreAuthorize("@ss.hasPermi('combatduty:contrast:add')")
    @Log(title = "外出人员对比设置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OutPeopleContrast outPeopleContrast)
    {
        return toAjax(outPeopleContrastService.insertOutPeopleContrast(outPeopleContrast));
    }

    /**
     * 修改外出人员对比设置
     */
    //@PreAuthorize("@ss.hasPermi('combatduty:contrast:edit')")
    @Log(title = "外出人员对比设置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OutPeopleContrast outPeopleContrast)
    {
        List<OutPeopleContrast> outPeopleContrasts = outPeopleContrastService.selectOutPeopleContrastList(new OutPeopleContrast());
        if(outPeopleContrasts==null||outPeopleContrasts.size()==0){
            return toAjax(outPeopleContrastService.insertOutPeopleContrast(outPeopleContrast));
        }
        return toAjax(outPeopleContrastService.updateOutPeopleContrast(outPeopleContrast));
    }

    /**
     * 删除外出人员对比设置
     */
    //@PreAuthorize("@ss.hasPermi('combatduty:contrast:remove')")
    @Log(title = "外出人员对比设置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(outPeopleContrastService.deleteOutPeopleContrastByIds(ids));
    }
}