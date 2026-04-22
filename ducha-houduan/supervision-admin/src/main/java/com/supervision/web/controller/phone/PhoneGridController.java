package com.supervision.web.controller.phone;

import java.util.HashMap;
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
import com.supervision.phone.domain.PhoneGrid;
import com.supervision.phone.service.IPhoneGridService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 手机柜格子Controller
 * 
 * @author supervision
 * @date 2022-04-20
 */
@RestController
@RequestMapping("/phone/grid")
public class PhoneGridController extends BaseController
{
    @Autowired
    private IPhoneGridService phoneGridService;

    /**
     * 查询手机柜格子列表
     */
    @PreAuthorize("@ss.hasPermi('phone:grid:list')")
    @GetMapping("/list")
    public TableDataInfo list(PhoneGrid phoneGrid)
    {
        startPage();
        List<PhoneGrid> list = phoneGridService.selectPhoneGridList(phoneGrid);
        return getDataTable(list);
    }
    @GetMapping("/getNumber")
    public AjaxResult getNumber(PhoneGrid phoneGrid){
        List<PhoneGrid> list = phoneGridService.selectPhoneGridList(phoneGrid);
        int cunfang=0;
        int weicunfang=0;
        int yichang=0;
        for (PhoneGrid grid : list) {
            if(grid.getLatticeState().equals("0")){
                cunfang++;
            }
            if(grid.getLatticeState().equals("1")){
                weicunfang++;
            }
            if(grid.getLatticeState().equals("2")){
                yichang++;
            }
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("cunfang",cunfang);
        map.put("weicunfang",weicunfang);
        map.put("yichang",yichang);

        return AjaxResult.success(map);

    }

    /**
     * 导出手机柜格子列表
     */
    @PreAuthorize("@ss.hasPermi('phone:grid:export')")
    @Log(title = "手机柜格子", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(PhoneGrid phoneGrid)
    {
        List<PhoneGrid> list = phoneGridService.selectPhoneGridList(phoneGrid);
        ExcelUtil<PhoneGrid> util = new ExcelUtil<PhoneGrid>(PhoneGrid.class);
        return util.exportExcel(list, "手机柜格子数据");
    }

    /**
     * 获取手机柜格子详细信息
     */
    @PreAuthorize("@ss.hasPermi('phone:grid:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(phoneGridService.selectPhoneGridById(id));
    }

    /**
     * 新增手机柜格子
     */
    @PreAuthorize("@ss.hasPermi('phone:grid:add')")
    @Log(title = "手机柜格子", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PhoneGrid phoneGrid)
    {
        return toAjax(phoneGridService.insertPhoneGrid(phoneGrid));
    }

    /**
     * 修改手机柜格子
     */
    @PreAuthorize("@ss.hasPermi('phone:grid:edit')")
    @Log(title = "手机柜格子", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PhoneGrid phoneGrid)
    {
        return toAjax(phoneGridService.updatePhoneGrid(phoneGrid));
    }

    /**
     * 删除手机柜格子
     */
    @PreAuthorize("@ss.hasPermi('phone:grid:remove')")
    @Log(title = "手机柜格子", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(phoneGridService.deletePhoneGridByIds(ids));
    }
}
