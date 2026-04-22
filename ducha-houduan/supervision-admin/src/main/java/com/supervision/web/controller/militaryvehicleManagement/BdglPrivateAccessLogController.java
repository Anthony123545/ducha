package com.supervision.web.controller.militaryvehicleManagement;

import java.util.List;

import com.supervision.common.utils.StringUtils;
import com.supervision.militaryvehicleManagement.domain.vo.PersonalCarStat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
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
import com.supervision.militaryvehicleManagement.domain.BdglPrivateAccessLog;
import com.supervision.militaryvehicleManagement.service.IBdglPrivateAccessLogService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 私家车进出查询Controller
 * 
 * @author supervision
 * @date 2022-03-09
 */
@RestController
@RequestMapping("/militaryvehicleManagement/privateAccessLog")
public class BdglPrivateAccessLogController extends BaseController
{
    @Autowired
    private IBdglPrivateAccessLogService bdglPrivateAccessLogService;

    /**
     * 查询私家车进出查询列表
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:privateAccessLog:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglPrivateAccessLog bdglPrivateAccessLog)
    {
        startPage();
        List<BdglPrivateAccessLog> list = bdglPrivateAccessLogService.selectBdglPrivateAccessLogList(bdglPrivateAccessLog);
        return getDataTable(list);
    }
    
    /**
     * 进出私家车统计
     */
//    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:privateAccessLog:list')")
    @GetMapping("/personalCarStat")
    public AjaxResult personalCarStat(PersonalCarStat personalCarStat)
    {
        String endTime = personalCarStat.getEndTime();
        if (StringUtils.isNotEmpty(personalCarStat.getEndTime()) && !personalCarStat.getEndTime().contains(":")){
            endTime = endTime + " 23:59:59";
        }
        personalCarStat.setEndTime(endTime);
        List<PersonalCarStat> carStatList = getCarStatList(personalCarStat);
        if (CollectionUtils.isEmpty(carStatList)){
            return AjaxResult.success(null);
        }
        return AjaxResult.success(carStatList);
    }
    
    private List<PersonalCarStat> getCarStatList(PersonalCarStat personalCarStat) {
        //查询进入次数
        List<PersonalCarStat> inList = bdglPrivateAccessLogService.selectPersonalCarInStat(personalCarStat);
        inList.forEach(item->{
            //查询离开次数
            PersonalCarStat carStat = new PersonalCarStat();
            carStat.setChePaiNum(item.getChePaiNum());
            carStat.setStartTime(personalCarStat.getStartTime());
            carStat.setEndTime(personalCarStat.getEndTime());
            long outCount = bdglPrivateAccessLogService.selectPersonalCarOutStat(carStat);
            item.setOutCount(outCount);
            item.setStartTime(personalCarStat.getStartTime());
            item.setEndTime(personalCarStat.getEndTime());
        });
        return inList;
    }
    
    /**
     * 导出统计数据
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:privateAccessLog:export')")
    @Log(title = "私家车进出查询", businessType = BusinessType.EXPORT)
    @GetMapping("/exportCarStat")
    public AjaxResult exportCarStat(PersonalCarStat personalCarStat)
    {
        List<PersonalCarStat> carStatList = getCarStatList(personalCarStat);
        ExcelUtil<PersonalCarStat> util = new ExcelUtil<>(PersonalCarStat.class);
        return util.exportExcel(carStatList, "私家车进出统计数据");
    }
    

    /**
     * 导出私家车进出查询列表
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:privateAccessLog:export')")
    @Log(title = "私家车进出查询", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglPrivateAccessLog bdglPrivateAccessLog)
    {
        List<BdglPrivateAccessLog> list = bdglPrivateAccessLogService.selectBdglPrivateAccessLogList(bdglPrivateAccessLog);
        ExcelUtil<BdglPrivateAccessLog> util = new ExcelUtil<BdglPrivateAccessLog>(BdglPrivateAccessLog.class);
        return util.exportExcel(list, "私家车进出查询数据");
    }

    /**
     * 获取私家车进出查询详细信息
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:privateAccessLog:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bdglPrivateAccessLogService.selectBdglPrivateAccessLogById(id));
    }

    /**
     * 新增私家车进出查询
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:privateAccessLog:add')")
    @Log(title = "私家车进出查询", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglPrivateAccessLog bdglPrivateAccessLog)
    {
        return toAjax(bdglPrivateAccessLogService.insertBdglPrivateAccessLog(bdglPrivateAccessLog));
    }

    /**
     * 修改私家车进出查询
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:privateAccessLog:edit')")
    @Log(title = "私家车进出查询", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglPrivateAccessLog bdglPrivateAccessLog)
    {
        return toAjax(bdglPrivateAccessLogService.updateBdglPrivateAccessLog(bdglPrivateAccessLog));
    }

    /**
     * 删除私家车进出查询
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:privateAccessLog:remove')")
    @Log(title = "私家车进出查询", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglPrivateAccessLogService.deleteBdglPrivateAccessLogByIds(ids));
    }
}
