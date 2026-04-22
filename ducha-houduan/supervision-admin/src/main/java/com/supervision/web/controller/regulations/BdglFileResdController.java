package com.supervision.web.controller.regulations;

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
import com.supervision.regulations.domain.BdglFileResd;
import com.supervision.regulations.service.IBdglFileResdService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 规章制度阅读量统计Controller
 * 
 * @author supervision
 * @date 2022-03-18
 */
@RestController
@RequestMapping("/fileResd/regulations")
public class BdglFileResdController extends BaseController
{
    @Autowired
    private IBdglFileResdService bdglFileResdService;

    /**
     * 查询规章制度阅读量统计列表
     */
    @PreAuthorize("@ss.hasPermi('fileResd:regulations:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglFileResd bdglFileResd)
    {
        startPage();
        List<BdglFileResd> list = bdglFileResdService.selectBdglFileResdList(bdglFileResd);
        return getDataTable(list);
    }

    /**
     * 导出规章制度阅读量统计列表
     */
    @PreAuthorize("@ss.hasPermi('fileResd:regulations:export')")
    @Log(title = "规章制度阅读量统计", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglFileResd bdglFileResd)
    {
        List<BdglFileResd> list = bdglFileResdService.selectBdglFileResdList(bdglFileResd);
        ExcelUtil<BdglFileResd> util = new ExcelUtil<BdglFileResd>(BdglFileResd.class);
        return util.exportExcel(list, "规章制度阅读量统计数据");
    }

    /**
     * 获取规章制度阅读量统计详细信息
     */
    @PreAuthorize("@ss.hasPermi('fileResd:regulations:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(bdglFileResdService.selectBdglFileResdById(id));
    }

    /**
     * 新增规章制度阅读量统计
     */
    @PreAuthorize("@ss.hasPermi('fileResd:regulations:add')")
    @Log(title = "规章制度阅读量统计", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglFileResd bdglFileResd)
    {
        return toAjax(bdglFileResdService.insertBdglFileResd(bdglFileResd));
    }

    /**
     * 修改规章制度阅读量统计
     */
    @PreAuthorize("@ss.hasPermi('fileResd:regulations:edit')")
    @Log(title = "规章制度阅读量统计", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglFileResd bdglFileResd)
    {
        return toAjax(bdglFileResdService.updateBdglFileResd(bdglFileResd));
    }

    /**
     * 删除规章制度阅读量统计
     */
    @PreAuthorize("@ss.hasPermi('fileResd:regulations:remove')")
    @Log(title = "规章制度阅读量统计", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bdglFileResdService.deleteBdglFileResdByIds(ids));
    }
}
