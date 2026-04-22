package com.supervision.web.controller.grassrootsregistration;

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
import com.supervision.grassrootsregistration.domain.BdglDiaryKinsfolk;
import com.supervision.grassrootsregistration.service.IBdglDiaryKinsfolkService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 临时来队亲属Controller
 * 
 * @author supervision
 * @date 2022-02-28
 */
@RestController
@RequestMapping("/grassrootsregistration/kinsfolk")
public class BdglDiaryKinsfolkController extends BaseController
{
    @Autowired
    private IBdglDiaryKinsfolkService bdglDiaryKinsfolkService;

    /**
     * 查询临时来队亲属列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:kinsfolk:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglDiaryKinsfolk bdglDiaryKinsfolk)
    {
        startPage();
        List<BdglDiaryKinsfolk> list = bdglDiaryKinsfolkService.selectBdglDiaryKinsfolkList(bdglDiaryKinsfolk);
        return getDataTable(list);
    }

    /**
     * 导出临时来队亲属列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:kinsfolk:export')")
    @Log(title = "临时来队亲属", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglDiaryKinsfolk bdglDiaryKinsfolk)
    {
        List<BdglDiaryKinsfolk> list = bdglDiaryKinsfolkService.selectBdglDiaryKinsfolkList(bdglDiaryKinsfolk);
        ExcelUtil<BdglDiaryKinsfolk> util = new ExcelUtil<BdglDiaryKinsfolk>(BdglDiaryKinsfolk.class);
        return util.exportExcel(list, "临时来队亲属数据");
    }

    /**
     * 获取临时来队亲属详细信息
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:kinsfolk:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bdglDiaryKinsfolkService.selectBdglDiaryKinsfolkById(id));
    }

    /**
     * 新增临时来队亲属
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:kinsfolk:add')")
    @Log(title = "临时来队亲属", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglDiaryKinsfolk bdglDiaryKinsfolk)
    {
        return toAjax(bdglDiaryKinsfolkService.insertBdglDiaryKinsfolk(bdglDiaryKinsfolk));
    }

    /**
     * 修改临时来队亲属
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:kinsfolk:edit')")
    @Log(title = "临时来队亲属", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglDiaryKinsfolk bdglDiaryKinsfolk)
    {
        return toAjax(bdglDiaryKinsfolkService.updateBdglDiaryKinsfolk(bdglDiaryKinsfolk));
    }

    /**
     * 删除临时来队亲属
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:kinsfolk:remove')")
    @Log(title = "临时来队亲属", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglDiaryKinsfolkService.deleteBdglDiaryKinsfolkByIds(ids));
    }
}
