package com.supervision.web.controller.militaryvehicleManagement;

import java.text.SimpleDateFormat;
import java.util.*;

import com.supervision.common.utils.DateUtils;
import com.supervision.militaryvehicleManagement.domain.BdglPrivateAccessLog;
import com.supervision.militaryvehicleManagement.domain.BdglPrivateCar;
import com.supervision.militaryvehicleManagement.domain.BdglThebusAccessLog;
import com.supervision.militaryvehicleManagement.service.IBdglPrivateAccessLogService;
import com.supervision.militaryvehicleManagement.service.IBdglPrivateCarService;
import com.supervision.militaryvehicleManagement.service.IBdglThebusAccessLogService;
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
import com.supervision.militaryvehicleManagement.domain.BdglThebus;
import com.supervision.militaryvehicleManagement.service.IBdglThebusService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 军车管理Controller
 *
 * @author supervision
 * @date 2022-03-09
 */
@RestController
@RequestMapping("/militaryvehicleManagement/thebus")
public class BdglThebusController extends BaseController
{
    @Autowired
    private IBdglThebusService bdglThebusService;
    @Autowired
    private IBdglPrivateAccessLogService privateAccessLogService;
    @Autowired
    private IBdglPrivateCarService privateCarService;
    @Autowired
    private IBdglThebusAccessLogService thebusAccessLogService;

    /**
     * 查询军车管理列表
     */
    //@PreAuthorize("@ss.hasPermi('militaryvehicleManagement:thebus:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglThebus bdglThebus)
    {
        startPage();
        List<BdglThebus> list = bdglThebusService.selectBdglThebusList(bdglThebus);
        return getDataTable(list);
    }

    @GetMapping("/getThebusType")
    public AjaxResult getThebusType()
    {
        List<Map<String, Object>> list = bdglThebusService.selectVehicleModelList();
        return AjaxResult.success(list);
    }


    /**
     * 查询在位车辆
     * @param bdglThebus
     * @return
     */
    @GetMapping("/zaiweilistlist")
    public TableDataInfo zaiweilist(BdglThebus bdglThebus)
    {
        bdglThebus.setStatus("1");
        List<BdglThebus> list = bdglThebusService.selectBdglThebusList(bdglThebus);
        return getDataTable(list);
    }

    /**查看可出军车列表*/
    @GetMapping("/getBus")
    public TableDataInfo getBus(BdglThebus bdglThebus){
        startPage();
        bdglThebus.setStatus("1");
        return getDataTable(bdglThebusService.selectBdglThebusList(bdglThebus));
    }

    /**
     * 导出军车管理列表
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:thebus:export')")
    @Log(title = "军车管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglThebus bdglThebus)
    {
        List<BdglThebus> list = bdglThebusService.selectBdglThebusList(bdglThebus);
        ExcelUtil<BdglThebus> util = new ExcelUtil<BdglThebus>(BdglThebus.class);
        return util.exportExcel(list, "军车管理数据");
    }

    /**
     * 获取军车管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:thebus:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(bdglThebusService.selectBdglThebusById(id));
    }

    /**
     * 新增军车管理
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:thebus:add')")
    @Log(title = "军车管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglThebus bdglThebus)
    {
//        if(bdglThebus.getVehiclenumber().contains("YZ-")){
//            bdglThebus.setOrders(0);
//        }
//        if(bdglThebus.getVehiclenumber().contains("YZ·")){
//            bdglThebus.setOrders(1);
//        }
//        if(bdglThebus.getVehiclenumber().contains("特殊")){
//            bdglThebus.setOrders(2);
//        }
        bdglThebus.setCreatetime(new Date());
        return toAjax(bdglThebusService.insertBdglThebus(bdglThebus));
    }

    /**
     * 修改军车管理
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:thebus:edit')")
    @Log(title = "军车管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglThebus bdglThebus)
    {
        bdglThebus.setUpdatetime(new Date());
        return toAjax(bdglThebusService.updateBdglThebus(bdglThebus));
    }

    /**
     * 删除军车管理
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:thebus:remove')")
    @Log(title = "军车管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bdglThebusService.deleteBdglThebusByIds(ids));
    }
    @GetMapping("/selectCount")
    public AjaxResult selectCount(){
        ArrayList<Integer> list = new ArrayList<>();
        //私家车夜间进出 总计  军车总计   在位   外出
        int juunCheNumber=0;
        int juncheZaiWei=0;
        int juncheZhixingRenWU=0;
        int juncheChuChe=0;
        List<BdglThebus> bdglThebuses = bdglThebusService.selectBdglThebusList(new BdglThebus());
        if(bdglThebuses!=null){
            juunCheNumber=bdglThebuses.size();
            for (BdglThebus bdglThebus : bdglThebuses) {
                if(bdglThebus.getStatus().equals("1")){
                    juncheZaiWei++;
                }
                if(bdglThebus.getStatus().equals("3")){
                    juncheChuChe++;
                }
            }
        }
        BdglThebusAccessLog bdglThebusAccessLog = new BdglThebusAccessLog();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd ss:hh:mm");
        String format = simpleDateFormat.format(new Date());
        String jieshu = simpleDateFormat1.format(new Date());
        Date date = DateUtils.parseDate(format);
        Date datejieshu = DateUtils.parseDate(jieshu);
        bdglThebusAccessLog.setWaichushijian(date);
        List<BdglThebusAccessLog> bdglThebusAccessLogs = thebusAccessLogService.selectBdglThebusAccessLogLists(bdglThebusAccessLog);
        if(bdglThebusAccessLogs!=null){
            for (BdglThebusAccessLog thebusAccessLog : bdglThebusAccessLogs) {
                juncheZhixingRenWU++;
            }
        }
        list.add(juunCheNumber);
        list.add(juncheZaiWei);
        list.add(juncheChuChe);
        list.add(juncheZhixingRenWU);
        int siJisCheNumber=0;
        int yejianJinChu=0;
        int jinChu=0;
        List<BdglPrivateCar> bdglPrivateCars = privateCarService.selectBdglPrivateCarList(new BdglPrivateCar());
        if(bdglPrivateCars!=null){
            siJisCheNumber=bdglPrivateCars.size();
        }

        Date date1 =new Date();
        Calendar calendarj = Calendar.getInstance();
        calendarj.setTime(date1);
        // 将时分秒,毫秒域清零
        calendarj.set(Calendar.HOUR_OF_DAY, 22);
        calendarj.set(Calendar.MINUTE, 00);
        calendarj.set(Calendar.SECOND, 00);
        System.out.print(calendarj.getTime());
        Date date2 = new Date();
        Calendar calendarl = Calendar.getInstance();
        calendarl.setTime(date2);
        // 将时分秒,毫秒域清零
        calendarl.add(Calendar.DAY_OF_MONTH, 1);
        calendarl.set(Calendar.HOUR_OF_DAY, 6);
        calendarl.set(Calendar.MINUTE, 00);
        calendarl.set(Calendar.SECOND, 00);
        System.out.print(calendarl.getTime());
        BdglPrivateAccessLog privateAccessLog = new BdglPrivateAccessLog();
        privateAccessLog.setLikaishitian(calendarl.getTime());

        privateAccessLog.setJinrushiajin(calendarj.getTime());

        List<BdglPrivateAccessLog> bdglPrivateAccessLogs = privateAccessLogService.selectBdglPrivateAccessLogLists(privateAccessLog);
        if(bdglPrivateAccessLogs!=null){
            for (BdglPrivateAccessLog bdglPrivateAccessLog : bdglPrivateAccessLogs) {

                yejianJinChu++;
            }
        }
        BdglPrivateAccessLog privateAccessLog1 = new BdglPrivateAccessLog();
        privateAccessLog1.setJinrushiajin(date);
        privateAccessLog1.setLikaishitian(datejieshu);
        List<BdglPrivateAccessLog> bdglPrivateAccessLogss = privateAccessLogService.selectBdglPrivateAccessLog(privateAccessLog1);
        if(bdglPrivateAccessLogss!=null){
            jinChu=bdglPrivateAccessLogss.size();
        }
        list.add(siJisCheNumber);
        list.add(yejianJinChu);
        list.add(jinChu);
        return AjaxResult.success(list);
    }

    @Log(title = "军车管理", businessType = BusinessType.UPDATE)
    @PutMapping(value = "/updatestatus")
    public AjaxResult updateStatus() {
        return toAjax(bdglThebusService.updateBdglThebusStatus());
    }
}
