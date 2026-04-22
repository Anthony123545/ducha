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
import com.supervision.peoplefiverates.domain.BdglDutyMeasure;
import com.supervision.peoplefiverates.service.IBdglDutyMeasureService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 六量Controller
 * 
 * @author supervision
 * @date 2022-03-19
 */
@RestController
@RequestMapping("/peoplefiverates/measureinfo")
public class BdglDutyMeasureController extends BaseController
{
    @Autowired
    private IBdglDutyMeasureService bdglDutyMeasureService;

    /**
     * 查询六量列表
     */
    //@PreAuthorize("@ss.hasPermi('peoplefiverates:measureinfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglDutyMeasure bdglDutyMeasure)
    {
        startPage();
        List<BdglDutyMeasure> list = bdglDutyMeasureService.selectBdglDutyMeasureList(bdglDutyMeasure);
        return getDataTable(list);
    }

    /**
     * 导出六量列表
     */
    @PreAuthorize("@ss.hasPermi('peoplefiverates:measureinfo:export')")
    @Log(title = "六量", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglDutyMeasure bdglDutyMeasure)
    {
        List<BdglDutyMeasure> list = bdglDutyMeasureService.selectBdglDutyMeasureList(bdglDutyMeasure);
        ExcelUtil<BdglDutyMeasure> util = new ExcelUtil<BdglDutyMeasure>(BdglDutyMeasure.class);
        return util.exportExcel(list, "六量数据");
    }

    /**
     * 获取六量详细信息
     */
    @PreAuthorize("@ss.hasPermi('peoplefiverates:measureinfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bdglDutyMeasureService.selectBdglDutyMeasureById(id));
    }

    /**
     * 新增六量
     */
   // @PreAuthorize("@ss.hasPermi('peoplefiverates:measureinfo:add')")
    @Log(title = "六量", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglDutyMeasure bdglDutyMeasure)
    {
        BdglDutyMeasure dm = new BdglDutyMeasure();
        List<BdglDutyMeasure> list = bdglDutyMeasureService.selectBdglDutyMeasureList(dm);
        if(list.size() > 0){
            for (BdglDutyMeasure dutyMeasure : list) {
                bdglDutyMeasureService.deleteBdglDutyMeasureById(dutyMeasure.getId());
            }
        }
        return toAjax(bdglDutyMeasureService.insertBdglDutyMeasure(bdglDutyMeasure));
    }

    /**
     * 修改六量
     */
    @PreAuthorize("@ss.hasPermi('peoplefiverates:measureinfo:edit')")
    @Log(title = "六量", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglDutyMeasure bdglDutyMeasure)
    {
        return toAjax(bdglDutyMeasureService.updateBdglDutyMeasure(bdglDutyMeasure));
    }

    /**
     * 删除六量
     */
    @PreAuthorize("@ss.hasPermi('peoplefiverates:measureinfo:remove')")
    @Log(title = "六量", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglDutyMeasureService.deleteBdglDutyMeasureByIds(ids));
    }
}
