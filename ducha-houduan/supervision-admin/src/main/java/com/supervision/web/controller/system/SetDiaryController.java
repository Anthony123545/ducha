package com.supervision.web.controller.system;

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
import com.supervision.system.domain.SetDiary;
import com.supervision.system.service.ISetDiaryService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 要事日记设置 Controller
 *
 * @author ruoyi
 * @date 2022-09-14
 */
@RestController
@RequestMapping("/system/diary")
public class SetDiaryController extends BaseController
{
    @Autowired
    private ISetDiaryService setDiaryService;

    /**
     * 查询要事日记设置 列表
     */
//    @PreAuthorize("@ss.hasPermi('system:diary:list')")
    @GetMapping("/list")
    public TableDataInfo list(SetDiary setDiary)
    {
        startPage();
        List<SetDiary> list = setDiaryService.selectSetDiaryList(setDiary);
        return getDataTable(list);
    }

    /**
     * 导出要事日记设置 列表
     */
    @PreAuthorize("@ss.hasPermi('system:diary:export')")
    @Log(title = "要事日记设置 ", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SetDiary setDiary)
    {
        List<SetDiary> list = setDiaryService.selectSetDiaryList(setDiary);
        ExcelUtil<SetDiary> util = new ExcelUtil<SetDiary>(SetDiary.class);
        util.exportExcel(response, list, "要事日记设置 数据");
    }

    /**
     * 获取要事日记设置 详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:diary:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(setDiaryService.selectSetDiaryById(id));
    }

    /**
     * 新增要事日记设置
     */
    @PreAuthorize("@ss.hasPermi('system:diary:add')")
    @Log(title = "要事日记设置 ", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SetDiary setDiary)
    {
        return toAjax(setDiaryService.insertSetDiary(setDiary));
    }

    /**
     * 修改要事日记设置
     */
    @PreAuthorize("@ss.hasPermi('system:diary:edit')")
    @Log(title = "要事日记设置 ", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SetDiary setDiary)
    {
        return toAjax(setDiaryService.updateSetDiary(setDiary));
    }

    /**
     * 删除要事日记设置
     */
    @PreAuthorize("@ss.hasPermi('system:diary:remove')")
    @Log(title = "要事日记设置 ", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(setDiaryService.deleteSetDiaryByIds(ids));
    }
}
