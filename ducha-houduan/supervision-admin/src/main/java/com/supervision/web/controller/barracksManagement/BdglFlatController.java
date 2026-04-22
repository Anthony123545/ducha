package com.supervision.web.controller.barracksManagement;

import java.util.List;

import cn.hutool.core.bean.BeanUtil;
import com.supervision.barracksManagement.domain.dto.BdglSingleCadreDormDto;
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
import com.supervision.barracksManagement.domain.BdglFlat;
import com.supervision.barracksManagement.service.IBdglFlatService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 公寓房档案Controller
 * 
 * @author supervision
 * @date 2022-03-02
 */
@RestController
@RequestMapping("/barracksManagement/flat")
public class BdglFlatController extends BaseController
{
    @Autowired
    private IBdglFlatService bdglFlatService;

    /**
     * 查询公寓房档案列表
     */
    @PreAuthorize("@ss.hasPermi('barracksManagement:flat:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglFlat bdglFlat)
    {
        startPage();
        List<BdglFlat> list = bdglFlatService.selectBdglFlatList(bdglFlat);
        return getDataTable(list);
    }

    /**
     * 导出公寓房档案列表
     */
    @PreAuthorize("@ss.hasPermi('barracksManagement:flat:export')")
    @Log(title = "公寓房档案", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglFlat bdglFlat)
    {
        List<BdglFlat> list = bdglFlatService.selectBdglFlatList(bdglFlat);
        List<BdglSingleCadreDormDto> dtoList = BeanUtil.copyToList(list, BdglSingleCadreDormDto.class);
        ExcelUtil<BdglSingleCadreDormDto> util = new ExcelUtil<>(BdglSingleCadreDormDto.class);
        return util.exportExcel(dtoList, "公寓房档案数据");
    }

    /**
     * 获取公寓房档案详细信息
     */
    @PreAuthorize("@ss.hasPermi('barracksManagement:flat:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bdglFlatService.selectBdglFlatById(id));
    }

    /**
     * 新增公寓房档案
     */
    @PreAuthorize("@ss.hasPermi('barracksManagement:flat:add')")
    @Log(title = "公寓房档案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglFlat bdglFlat)
    {
        return toAjax(bdglFlatService.insertBdglFlat(bdglFlat));
    }

    /**
     * 修改公寓房档案
     */
    @PreAuthorize("@ss.hasPermi('barracksManagement:flat:edit')")
    @Log(title = "公寓房档案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglFlat bdglFlat)
    {
        return toAjax(bdglFlatService.updateBdglFlat(bdglFlat));
    }

    /**
     * 删除公寓房档案
     */
    @PreAuthorize("@ss.hasPermi('barracksManagement:flat:remove')")
    @Log(title = "公寓房档案", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglFlatService.deleteBdglFlatByIds(ids));
    }
}
