package com.supervision.web.controller.combatduty;

import java.text.SimpleDateFormat;
import java.util.*;

import com.supervision.bdglSecret.domain.SecretAccount;
import com.supervision.common.core.domain.entity.SysDictData;
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
import com.supervision.combatduty.domain.BdglRegisDuty;
import com.supervision.combatduty.service.IBdglRegisDutyService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 装备登记Controller
 *
 * @author supervision
 * @date 2022-03-11
 */
@RestController
@RequestMapping("/combatduty/regisdutyinfo")
public class BdglRegisDutyController extends BaseController
{
    @Autowired
    private IBdglRegisDutyService bdglRegisDutyService;

    /**
     * 查询装备登记列表
     */
    @PreAuthorize("@ss.hasPermi('combatduty:regisdutyinfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglRegisDuty bdglRegisDuty)
    {
        startPage();
        List<BdglRegisDuty> list = bdglRegisDutyService.selectBdglRegisDutyList(bdglRegisDuty);

        return getDataTable(list);
    }

    @GetMapping("/getNumber")
    public AjaxResult getNumberByType(){

        return AjaxResult.success(bdglRegisDutyService.selectBdglRegisDutyNumber());
    }

    /**
     * 导出装备登记列表
     */
    @PreAuthorize("@ss.hasPermi('combatduty:regisdutyinfo:export')")
    @Log(title = "装备登记", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglRegisDuty bdglRegisDuty)
    {
        List<BdglRegisDuty> list = bdglRegisDutyService.selectBdglRegisDutyList(bdglRegisDuty);
        ExcelUtil<BdglRegisDuty> util = new ExcelUtil<BdglRegisDuty>(BdglRegisDuty.class);
        return util.exportExcel(list, "装备登记数据");
    }

    /**
     * 获取装备登记详细信息
     */
    @PreAuthorize("@ss.hasPermi('combatduty:regisdutyinfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(bdglRegisDutyService.selectBdglRegisDutyById(id));
    }

    /**
     * 新增装备登记
     */
    @PreAuthorize("@ss.hasPermi('combatduty:regisdutyinfo:add')")
    @Log(title = "装备登记", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglRegisDuty bdglRegisDuty)
    {
//        List<BdglRegisDuty> bdglRegisDuties = bdglRegisDutyService.selectBdglRegisDutyList(new BdglRegisDuty());
//        for (BdglRegisDuty regisDuty : bdglRegisDuties) {
//            if(bdglRegisDuty.getAttendedTime() != null) {
//                if(regisDuty.getAttendedTime().equals(bdglRegisDuty.getAttendedTime())) {
//                    return AjaxResult.error(bdglRegisDuty.getAttendedTime() + "，当前时间已存在值班，无法重复添加！");
//                }
//            }
//        }
        List<BdglRegisDuty> regisDutyList = bdglRegisDuty.getRegisDutyList();
        int i=0;
        if(regisDutyList.size()>0){
            for (BdglRegisDuty regisDuty : regisDutyList) {
                if(regisDuty!=null){
                    i++;
                    regisDuty.setRemarks(bdglRegisDuty.getRemarks());
                    bdglRegisDutyService.insertBdglRegisDuty(regisDuty);
                }
            }
        }
        return toAjax(i);
    }

    /**
     * 修改装备登记
     */
    @PreAuthorize("@ss.hasPermi('combatduty:regisdutyinfo:edit')")
    @Log(title = "装备登记", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglRegisDuty bdglRegisDuty)
    {
        return toAjax(bdglRegisDutyService.updateBdglRegisDuty(bdglRegisDuty));
    }

    /**
     * 删除装备登记
     */
    @PreAuthorize("@ss.hasPermi('combatduty:regisdutyinfo:remove')")
    @Log(title = "装备登记", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bdglRegisDutyService.deleteBdglRegisDutyByIds(ids));
    }
    @GetMapping("/selectCount")
    public AjaxResult selectCount(){

        int zongSHu=0;
        int shiyan=0;
        int tongYong=0;
        BdglRegisDuty bdglRegisDuty1 = new BdglRegisDuty();
        bdglRegisDuty1.setIsonduty("0");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        bdglRegisDuty1.setAttendedTime(simpleDateFormat.format(new Date()));

        List<BdglRegisDuty> bdglRegisDuties = bdglRegisDutyService.selectBdglRegisDutyList(bdglRegisDuty1);
        if(bdglRegisDuties!=null){
            zongSHu= bdglRegisDuties.size();
            for (BdglRegisDuty bdglRegisDuty : bdglRegisDuties) {
                if(bdglRegisDuty.getType().equals("0")){
                    shiyan++;
                }
                if(bdglRegisDuty.getType().equals("1")){
                    tongYong++;
                }
            }
        }
        ArrayList<Integer> list = new ArrayList<>();
        list.add(zongSHu);
        list.add(shiyan);
        list.add(tongYong);
        return AjaxResult.success(list);

    }


}
