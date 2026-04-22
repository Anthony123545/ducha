package com.supervision.web.controller.barracksManagement;

import java.util.Date;
import java.util.List;

import cn.hutool.core.bean.BeanUtil;
import com.supervision.barracksManagement.domain.BdglSingleCadreDorm;
import com.supervision.barracksManagement.domain.dto.BdglSingleCadreDormDto;
import com.supervision.barracksManagement.service.IBdglSingleCadreDormService;
import org.springframework.beans.BeanUtils;
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
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 单身干部宿舍Controller
 * 
 * @author ruoyi
 * @date 2022-08-08
 */
@RestController
@RequestMapping("/barracksManagement/scadred")
public class BdglSingleCadreDormController extends BaseController
{
    @Autowired
    private IBdglSingleCadreDormService bdglSingleCadreDormService;

    /**
     * 查询单身干部宿舍列表
     */
    @PreAuthorize("@ss.hasPermi('barracksManagement:scadre:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglSingleCadreDorm bdglSingleCadreDorm)
    {
        startPage();
        List<BdglSingleCadreDorm> list = bdglSingleCadreDormService.selectBdglSingleCadreDormList(bdglSingleCadreDorm);
        return getDataTable(list);
    }

    /**
     * 导出单身干部宿舍列表
     */
    @PreAuthorize("@ss.hasPermi('barracksManagement:scadre:export')")
    @Log(title = "单身干部宿舍", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglSingleCadreDorm bdglSingleCadreDorm)
    {
        List<BdglSingleCadreDorm> list = bdglSingleCadreDormService.selectBdglSingleCadreDormList(bdglSingleCadreDorm);
        List<BdglSingleCadreDormDto> dtoList = BeanUtil.copyToList(list, BdglSingleCadreDormDto.class);
        ExcelUtil<BdglSingleCadreDormDto> util = new ExcelUtil<>(BdglSingleCadreDormDto.class);
        return util.exportExcel(dtoList, "单身干部宿舍数据");
    }

    /**
     * 获取单身干部宿舍详细信息
     */
    @PreAuthorize("@ss.hasPermi('barracksManagement:scadre:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bdglSingleCadreDormService.selectBdglSingleCadreDormById(id));
    }

    /**
     * 新增单身干部宿舍
     */
    @PreAuthorize("@ss.hasPermi('barracksManagement:scadre:add')")
    @Log(title = "单身干部宿舍", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglSingleCadreDorm bdglSingleCadreDorm)
    {
        bdglSingleCadreDorm.setCreateTime(new Date());
        return toAjax(bdglSingleCadreDormService.insertBdglSingleCadreDorm(bdglSingleCadreDorm));
    }

    /**
     * 修改单身干部宿舍
     */
    @PreAuthorize("@ss.hasPermi('barracksManagement:scadre:edit')")
    @Log(title = "单身干部宿舍", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglSingleCadreDorm bdglSingleCadreDorm)
    {
        bdglSingleCadreDorm.setUpdateTime(new Date());
        return toAjax(bdglSingleCadreDormService.updateBdglSingleCadreDorm(bdglSingleCadreDorm));
    }

    /**
     * 删除单身干部宿舍
     */
    @PreAuthorize("@ss.hasPermi('barracksManagement:scadre:remove')")
    @Log(title = "单身干部宿舍", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglSingleCadreDormService.deleteBdglSingleCadreDormByIds(ids));
    }
}
