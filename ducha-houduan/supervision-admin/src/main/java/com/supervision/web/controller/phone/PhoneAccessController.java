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
import com.supervision.phone.domain.PhoneAccess;
import com.supervision.phone.service.IPhoneAccessService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 存取记录Controller
 * 
 * @author supervision
 * @date 2022-04-23
 */
@RestController
@RequestMapping("/phone/access")
public class PhoneAccessController extends BaseController
{
    @Autowired
    private IPhoneAccessService phoneAccessService;

    /**
     * 查询存取记录列表
     */
    @PreAuthorize("@ss.hasPermi('phone:access:list')")
    @GetMapping("/list")
    public TableDataInfo list(PhoneAccess phoneAccess)
    {
        startPage();
        List<PhoneAccess> list = phoneAccessService.selectPhoneAccessList(phoneAccess);
        return getDataTable(list);
    }

    /**
     * 导出存取记录列表
     */
    @PreAuthorize("@ss.hasPermi('phone:access:export')")
    @Log(title = "存取记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(PhoneAccess phoneAccess)
    {
        List<PhoneAccess> list = phoneAccessService.selectPhoneAccessList(phoneAccess);
        ExcelUtil<PhoneAccess> util = new ExcelUtil<PhoneAccess>(PhoneAccess.class);
        return util.exportExcel(list, "存取记录数据");
    }

    /**
     * 获取存取记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('phone:access:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(phoneAccessService.selectPhoneAccessById(id));
    }

    /**
     * 新增存取记录
     */
    @PreAuthorize("@ss.hasPermi('phone:access:add')")
    @Log(title = "存取记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PhoneAccess phoneAccess)
    {
        return toAjax(phoneAccessService.insertPhoneAccess(phoneAccess));
    }

    /**
     * 修改存取记录
     */
    @PreAuthorize("@ss.hasPermi('phone:access:edit')")
    @Log(title = "存取记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PhoneAccess phoneAccess)
    {
        return toAjax(phoneAccessService.updatePhoneAccess(phoneAccess));
    }

    /**
     * 删除存取记录
     */
    @PreAuthorize("@ss.hasPermi('phone:access:remove')")
    @Log(title = "存取记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(phoneAccessService.deletePhoneAccessByIds(ids));
    }
}
