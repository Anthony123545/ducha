package com.supervision.web.controller.barracksManagement;

import java.util.List;

import cn.hutool.core.bean.BeanUtil;
import com.supervision.barracksManagement.domain.dto.BdglFlatsDto;
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
import com.supervision.barracksManagement.domain.BdglFlats;
import com.supervision.barracksManagement.service.IBdglFlatsService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 公寓住房申请人员资格审查登记Controller
 * 
 * @author supervision
 * @date 2022-03-02
 */
@RestController
@RequestMapping("/barracksManagement/flats")
public class BdglFlatsController extends BaseController
{
    @Autowired
    private IBdglFlatsService bdglFlatsService;

    /**
     * 查询公寓住房申请人员资格审查登记列表
     */
    @PreAuthorize("@ss.hasPermi('barracksManagement:flats:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglFlats bdglFlats)
    {
        startPage();
        List<BdglFlats> list = bdglFlatsService.selectBdglFlatsList(bdglFlats);
        return getDataTable(list);
    }

    /**
     * 导出公寓住房申请人员资格审查登记列表
     */
    @PreAuthorize("@ss.hasPermi('barracksManagement:flats:export')")
    @Log(title = "公寓住房申请人员资格审查登记", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglFlats bdglFlats)
    {
        List<BdglFlats> list = bdglFlatsService.selectBdglFlatsList(bdglFlats);
        List<BdglFlatsDto> flatsDtoList = BeanUtil.copyToList(list, BdglFlatsDto.class);
        ExcelUtil<BdglFlatsDto> util = new ExcelUtil<>(BdglFlatsDto.class);
        return util.exportExcel(flatsDtoList, "公寓住房申请人员资格审查登记数据");
    }

    /**
     * 获取公寓住房申请人员资格审查登记详细信息
     */
    @PreAuthorize("@ss.hasPermi('barracksManagement:flats:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bdglFlatsService.selectBdglFlatsById(id));
    }

    /**
     * 新增公寓住房申请人员资格审查登记
     */
    @PreAuthorize("@ss.hasPermi('barracksManagement:flats:add')")
    @Log(title = "公寓住房申请人员资格审查登记", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglFlats bdglFlats)
    {
        return toAjax(bdglFlatsService.insertBdglFlats(bdglFlats));
    }

    /**
     * 修改公寓住房申请人员资格审查登记
     */
    @PreAuthorize("@ss.hasPermi('barracksManagement:flats:edit')")
    @Log(title = "公寓住房申请人员资格审查登记", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglFlats bdglFlats)
    {
        return toAjax(bdglFlatsService.updateBdglFlats(bdglFlats));
    }

    /**
     * 删除公寓住房申请人员资格审查登记
     */
    @PreAuthorize("@ss.hasPermi('barracksManagement:flats:remove')")
    @Log(title = "公寓住房申请人员资格审查登记", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglFlatsService.deleteBdglFlatsByIds(ids));
    }
}
