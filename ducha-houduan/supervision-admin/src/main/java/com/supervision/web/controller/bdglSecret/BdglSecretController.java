package com.supervision.web.controller.bdglSecret;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.supervision.common.annotation.Log;
import com.supervision.common.core.controller.BaseController;
import com.supervision.common.core.domain.AjaxResult;
import com.supervision.common.enums.BusinessType;
import com.supervision.bdglSecret.domain.BdglSecret;
import com.supervision.bdglSecret.service.IBdglSecretService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 保密设备外借记录Controller
 *
 * @author supervision
 * @date 2022-03-25
 */
@RestController
@RequestMapping("/bdglSecret/secret")
public class BdglSecretController extends BaseController
{
    @Autowired
    private IBdglSecretService bdglSecretService;

    /**
     * 查询保密设备外借记录列表
     */
    @PreAuthorize("@ss.hasPermi('bdglSecret:secret:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglSecret bdglSecret)
    {
        startPage();
        List<BdglSecret> list = bdglSecretService.selectBdglSecretList(bdglSecret);

        return getDataTable(list);
    }

    /**
     * 获取物品外借数量
     */
    @GetMapping("/getNumber")
    public AjaxResult getNumber(@RequestParam(value = "unitid", required = false) Integer unitid) {
        BdglSecret secret = new BdglSecret();
        secret.setUnitId(unitid);
        startPage();
        int a=0;
        int b=0;
        int c=0;
        int d=0;
        List<BdglSecret> list = bdglSecretService.selectBdglSecretList(secret);
        for (BdglSecret bdglSecret : list) {
            if(bdglSecret.getKind().equals("0")){
                a++;
            }
            if(bdglSecret.getKind().equals("1")){
                b++;
            }
            if(bdglSecret.getKind().equals("2")){
                c++;
            }
            if(bdglSecret.getKind().equals("3")){
                d++;
            }
        }
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        if(a!=0){
            stringObjectHashMap.put("0",a);
        }else{
            stringObjectHashMap.put("0",0);

        }
        if(b!=0){
            stringObjectHashMap.put("1",b);

        }else{
            stringObjectHashMap.put("1",0);

        }
        if(c!=0){
            stringObjectHashMap.put("2",c);

        }else{
            stringObjectHashMap.put("2",0);

        }
        if(d!=0){
            stringObjectHashMap.put("3",d);

        }else{
            stringObjectHashMap.put("3",0);
        }


        return AjaxResult.success(stringObjectHashMap);
    }

    /**
     * 导出保密设备外借记录列表
     */
    @PreAuthorize("@ss.hasPermi('bdglSecret:secret:export')")
    @Log(title = "保密设备外借记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglSecret bdglSecret)
    {
        List<BdglSecret> list = bdglSecretService.selectBdglSecretList(bdglSecret);
        ExcelUtil<BdglSecret> util = new ExcelUtil<BdglSecret>(BdglSecret.class);
        return util.exportExcel(list, "保密设备外借记录数据");
    }

    /**
     * 获取保密设备外借记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('bdglSecret:secret:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bdglSecretService.selectBdglSecretById(id));
    }

    /**
     * 新增保密设备外借记录
     */
    @PreAuthorize("@ss.hasPermi('bdglSecret:secret:add')")
    @Log(title = "保密设备外借记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglSecret bdglSecret)
    {
        return toAjax(bdglSecretService.insertBdglSecret(bdglSecret));
    }

    /**
     * 修改保密设备外借记录
     */
    @PreAuthorize("@ss.hasPermi('bdglSecret:secret:edit')")
    @Log(title = "保密设备外借记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglSecret bdglSecret)
    {
        return toAjax(bdglSecretService.updateBdglSecret(bdglSecret));
    }

    /**
     * 删除保密设备外借记录
     */
    @PreAuthorize("@ss.hasPermi('bdglSecret:secret:remove')")
    @Log(title = "保密设备外借记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglSecretService.deleteBdglSecretByIds(ids));
    }

    @PostMapping("/updateStatus")
    public AjaxResult updateStatus(@RequestBody BdglSecret bdglSecret) {
        return bdglSecretService.updateStatus(bdglSecret.getId(), bdglSecret.getStatus()) > 0
                ? AjaxResult.success()
                : AjaxResult.error();
    }
}
