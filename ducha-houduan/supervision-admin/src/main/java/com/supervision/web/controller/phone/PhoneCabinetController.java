package com.supervision.web.controller.phone;

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
import com.supervision.phone.domain.PhoneCabinet;
import com.supervision.phone.service.IPhoneCabinetService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 手机柜Controller
 * 
 * @author supervision
 * @date 2022-04-20
 */
@RestController
@RequestMapping("/phone/cabinet")
public class PhoneCabinetController extends BaseController
{
    @Autowired
    private IPhoneCabinetService phoneCabinetService;

    /**
     * 查询手机柜列表
     */
    @PreAuthorize("@ss.hasPermi('phone:cabinet:list')")
    @GetMapping("/list")
    public TableDataInfo list(PhoneCabinet phoneCabinet)
    {
        startPage();
        List<PhoneCabinet> list = phoneCabinetService.selectPhoneCabinetList(phoneCabinet);
        return getDataTable(list);
    }

    /**
     * 导出手机柜列表
     */
    @PreAuthorize("@ss.hasPermi('phone:cabinet:export')")
    @Log(title = "手机柜", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(PhoneCabinet phoneCabinet)
    {
        List<PhoneCabinet> list = phoneCabinetService.selectPhoneCabinetList(phoneCabinet);
        ExcelUtil<PhoneCabinet> util = new ExcelUtil<PhoneCabinet>(PhoneCabinet.class);
        return util.exportExcel(list, "手机柜数据");
    }

    /**
     * 获取手机柜详细信息
     */
    @PreAuthorize("@ss.hasPermi('phone:cabinet:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(phoneCabinetService.selectPhoneCabinetById(id));
    }

    /**
     * 新增手机柜
     */
    @PreAuthorize("@ss.hasPermi('phone:cabinet:add')")
    @Log(title = "手机柜", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PhoneCabinet phoneCabinet)
    {
        return toAjax(phoneCabinetService.insertPhoneCabinet(phoneCabinet));
    }

    /**
     * 修改手机柜
     */
    @PreAuthorize("@ss.hasPermi('phone:cabinet:edit')")
    @Log(title = "手机柜", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PhoneCabinet phoneCabinet)
    {
        return toAjax(phoneCabinetService.updatePhoneCabinet(phoneCabinet));
    }

    /**
     * 删除手机柜
     */
    @PreAuthorize("@ss.hasPermi('phone:cabinet:remove')")
    @Log(title = "手机柜", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(phoneCabinetService.deletePhoneCabinetByIds(ids));
    }
}
