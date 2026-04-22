package com.supervision.web.controller.grassrootsregistration;

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
import com.supervision.grassrootsregistration.domain.BdglCookbookPrc;
import com.supervision.grassrootsregistration.service.IBdglCookbookPrcService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 一周工作食普图Controller
 *
 * @author ruoyi
 * @date 2023-03-08
 */
@RestController
@RequestMapping("/grassrootsregistration/cookbookPrc")
public class BdglCookbookPrcController extends BaseController
{
    @Autowired
    private IBdglCookbookPrcService bdglCookbookPrcService;

    /**
     * 查询一周工作食普图列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:cookbookPrc:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglCookbookPrc bdglCookbookPrc)
    {
        startPage();
        List<BdglCookbookPrc> list = bdglCookbookPrcService.selectBdglCookbookPrcList(bdglCookbookPrc);
        return getDataTable(list);
    }

    /**
     * 导出一周工作食普图列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:cookbookPrc:export')")
    @Log(title = "一周工作食普图", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BdglCookbookPrc bdglCookbookPrc)
    {
        List<BdglCookbookPrc> list = bdglCookbookPrcService.selectBdglCookbookPrcList(bdglCookbookPrc);
        ExcelUtil<BdglCookbookPrc> util = new ExcelUtil<BdglCookbookPrc>(BdglCookbookPrc.class);
        util.exportExcel(response, list, "一周工作食普图数据");
    }

    /**
     * 获取一周工作食普图详细信息
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:cookbookPrc:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(bdglCookbookPrcService.selectBdglCookbookPrcById(id));
    }

    /**
     * 新增一周工作食普图
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:cookbookPrc:add')")
    @Log(title = "一周工作食普图", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglCookbookPrc bdglCookbookPrc)
    {
        return toAjax(bdglCookbookPrcService.insertBdglCookbookPrc(bdglCookbookPrc));
    }

    /**
     * 修改一周工作食普图
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:cookbookPrc:edit')")
    @Log(title = "一周工作食普图", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglCookbookPrc bdglCookbookPrc)
    {
        return toAjax(bdglCookbookPrcService.updateBdglCookbookPrc(bdglCookbookPrc));
    }

    /**
     * 删除一周工作食普图
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:cookbookPrc:remove')")
    @Log(title = "一周工作食普图", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bdglCookbookPrcService.deleteBdglCookbookPrcByIds(ids));
    }
}
