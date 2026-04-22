package com.supervision.web.controller.peoplefiverates;

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
import com.supervision.peoplefiverates.domain.BdglDutyOutfit;
import com.supervision.peoplefiverates.service.IBdglDutyOutfitService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 装备五率Controller
 * 
 * @author supervision
 * @date 2022-03-19
 */
@RestController
@RequestMapping("/peoplefiverates/outfitinfo")
public class BdglDutyOutfitController extends BaseController
{
    @Autowired
    private IBdglDutyOutfitService bdglDutyOutfitService;

    /**
     * 查询装备五率列表
     */
   // @PreAuthorize("@ss.hasPermi('peoplefiverates:outfitinfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglDutyOutfit bdglDutyOutfit)
    {
        startPage();
        List<BdglDutyOutfit> list = bdglDutyOutfitService.selectBdglDutyOutfitList(bdglDutyOutfit);
        return getDataTable(list);
    }

    /**
     * 导出装备五率列表
     */
    @PreAuthorize("@ss.hasPermi('peoplefiverates:outfitinfo:export')")
    @Log(title = "装备五率", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglDutyOutfit bdglDutyOutfit)
    {
        List<BdglDutyOutfit> list = bdglDutyOutfitService.selectBdglDutyOutfitList(bdglDutyOutfit);
        ExcelUtil<BdglDutyOutfit> util = new ExcelUtil<BdglDutyOutfit>(BdglDutyOutfit.class);
        return util.exportExcel(list, "装备五率数据");
    }

    /**
     * 获取装备五率详细信息
     */
    @PreAuthorize("@ss.hasPermi('peoplefiverates:outfitinfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bdglDutyOutfitService.selectBdglDutyOutfitById(id));
    }

    /**
     * 新增装备五率
     */
    //@PreAuthorize("@ss.hasPermi('peoplefiverates:outfitinfo:add')")
    @Log(title = "装备五率", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglDutyOutfit bdglDutyOutfit)
    {
        BdglDutyOutfit bdo = new BdglDutyOutfit();
        List<BdglDutyOutfit> list = bdglDutyOutfitService.selectBdglDutyOutfitList(bdo);
        if(list.size() > 0) {
            for (BdglDutyOutfit dutyOutfit : list) {
                bdglDutyOutfitService.deleteBdglDutyOutfitById(dutyOutfit.getId());
            }
        }
        return toAjax(bdglDutyOutfitService.insertBdglDutyOutfit(bdglDutyOutfit));
    }

    /**
     * 修改装备五率
     */
    @PreAuthorize("@ss.hasPermi('peoplefiverates:outfitinfo:edit')")
    @Log(title = "装备五率", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglDutyOutfit bdglDutyOutfit)
    {
        return toAjax(bdglDutyOutfitService.updateBdglDutyOutfit(bdglDutyOutfit));
    }

    /**
     * 删除装备五率
     */
    @PreAuthorize("@ss.hasPermi('peoplefiverates:outfitinfo:remove')")
    @Log(title = "装备五率", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglDutyOutfitService.deleteBdglDutyOutfitByIds(ids));
    }
}
