package com.supervision.web.controller.grassrootsregistration;

import java.util.List;

import com.supervision.common.utils.StringUtils;
import com.supervision.grassrootsregistration.domain.vo.BdglDiaryVo;
import com.supervision.grassrootsregistration.service.IBdglDiaryService;
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
import com.supervision.grassrootsregistration.domain.BdglDailyInspect;
import com.supervision.grassrootsregistration.service.IBdglDailyInspectService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

import javax.annotation.Resource;

/**
 * 查铺查哨Controller
 * 
 * @author supervision
 * @date 2022-02-28
 */
@RestController
@RequestMapping("/grassrootsregistration/inspect")
public class BdglDailyInspectController extends BaseController
{
    @Autowired
    private IBdglDailyInspectService bdglDailyInspectService;
    
    @Resource
    private IBdglDiaryService bdglDiaryService;
    
    /**
     * 查询查铺查哨列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:inspect:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglDailyInspect bdglDailyInspect)
    {
        startPage();
        List<BdglDailyInspect> list = bdglDailyInspectService.selectBdglDailyInspectList(bdglDailyInspect);
        return getDataTable(list);
    }

    /**
     * 导出查铺查哨列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:inspect:export')")
    @Log(title = "查铺查哨", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglDailyInspect bdglDailyInspect)
    {
        List<BdglDailyInspect> list = bdglDailyInspectService.selectBdglDailyInspectList(bdglDailyInspect);
        ExcelUtil<BdglDailyInspect> util = new ExcelUtil<BdglDailyInspect>(BdglDailyInspect.class);
        return util.exportExcel(list, "查铺查哨数据");
    }
    
    /**
     * 导出查铺查哨列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:inspect:export')")
    @Log(title = "查铺查哨", businessType = BusinessType.EXPORT)
    @GetMapping("/exportDaily")
    public AjaxResult exportDaily(BdglDiaryVo diaryVo)
    {
        String endTime = diaryVo.getEndTime();
        if (StringUtils.isNotEmpty(endTime) && !endTime.contains(":")){
            endTime = endTime + " 23:59:59";
            diaryVo.setEndTime(endTime);
        }
        //查询要事日记值班信息
        List<BdglDiaryVo> diaryVoList = bdglDiaryService.selectDirayStatList(diaryVo);
        ExcelUtil<BdglDiaryVo> util = new ExcelUtil<>(BdglDiaryVo.class);
        return util.exportExcel(diaryVoList, "查铺查哨数据");
    }

    /**
     * 获取查铺查哨详细信息
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:inspect:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bdglDailyInspectService.selectBdglDailyInspectById(id));
    }

    /**
     * 新增查铺查哨
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:inspect:add')")
    @Log(title = "查铺查哨", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglDailyInspect bdglDailyInspect)
    {
        return toAjax(bdglDailyInspectService.insertBdglDailyInspect(bdglDailyInspect));
    }

    /**
     * 修改查铺查哨
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:inspect:edit')")
    @Log(title = "查铺查哨", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglDailyInspect bdglDailyInspect)
    {
        return toAjax(bdglDailyInspectService.updateBdglDailyInspect(bdglDailyInspect));
    }

    /**
     * 删除查铺查哨
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:inspect:remove')")
    @Log(title = "查铺查哨", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglDailyInspectService.deleteBdglDailyInspectByIds(ids));
    }
}
