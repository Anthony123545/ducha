package com.supervision.web.controller.combatduty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import cn.hutool.core.convert.Convert;
import com.supervision.common.utils.StringUtils;
import com.supervision.militaryvehicleManagement.domain.BdglThebus;
import com.supervision.militaryvehicleManagement.service.IBdglThebusService;
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
import com.supervision.combatduty.domain.BdglMilitaryduty;
import com.supervision.combatduty.service.IBdglMilitarydutyService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 军车值班Controller
 *
 * @author supervision
 * @date 2022-03-11
 */
@RestController
@RequestMapping("/combatduty/militarydutyinfo")
public class BdglMilitarydutyController extends BaseController
{
    @Autowired
    private IBdglMilitarydutyService bdglMilitarydutyService;
    @Autowired
    private IBdglThebusService thebusService;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 查询军车值班列表
     */
    @PreAuthorize("@ss.hasPermi('combatduty:militarydutyinfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglMilitaryduty bdglMilitaryduty)
    {
        startPage();
        List<BdglMilitaryduty> list = bdglMilitarydutyService.selectBdglMilitarydutyList(bdglMilitaryduty);
        return getDataTable(list);
    }

    /**
     * 导出军车值班列表
     */
    @PreAuthorize("@ss.hasPermi('combatduty:militarydutyinfo:export')")
    @Log(title = "军车值班", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglMilitaryduty bdglMilitaryduty)
    {
        List<BdglMilitaryduty> list = bdglMilitarydutyService.selectBdglMilitarydutyList(bdglMilitaryduty);
        ExcelUtil<BdglMilitaryduty> util = new ExcelUtil<BdglMilitaryduty>(BdglMilitaryduty.class);
        return util.exportExcel(list, "军车值班数据");
    }

    /**
     * 获取军车值班详细信息
     */
    @PreAuthorize("@ss.hasPermi('combatduty:militarydutyinfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        BdglMilitaryduty bdglMilitaryduty = bdglMilitarydutyService.selectBdglMilitarydutyById(id);
        if (StringUtils.isNotBlank(bdglMilitaryduty.getNumberPlateId())){
            List<String> numPid = Arrays.asList(bdglMilitaryduty.getNumberPlateId().split(","));
            List<Long> numPidToLong = numPid.stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
            bdglMilitaryduty.setNumberPlateIdList(numPidToLong);
        }
        //单位ID转换
        if (StringUtils.isNotEmpty(bdglMilitaryduty.getCarUnitId())){
            List<String> uid = Arrays.asList(bdglMilitaryduty.getCarUnitId().split(","));
            bdglMilitaryduty.setUnitId(uid);
        }
        //司机ID转换
        if (StringUtils.isNotEmpty(bdglMilitaryduty.getDriverId())){
            List<String> sjList = Arrays.asList(bdglMilitaryduty.getDriverId().split(","));
            bdglMilitaryduty.setSjId(sjList);
        }
        //手机号转换
        if (StringUtils.isNotEmpty(bdglMilitaryduty.getTelephone())){
            List<String> phoneList = Arrays.asList(bdglMilitaryduty.getTelephone().split(","));
            bdglMilitaryduty.setPhone(phoneList);
        }
        return AjaxResult.success(bdglMilitaryduty);
    }

    /**
     * 新增军车值班
     */
    @PreAuthorize("@ss.hasPermi('combatduty:militarydutyinfo:add')")
    @Log(title = "军车值班", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglMilitaryduty bdglMilitaryduty) throws ParseException {
        List<String> timequjian = bdglMilitaryduty.getTimequjian();
        Date startDayDate = sdf.parse(timequjian.get(0));
        Date endDayDate = sdf.parse(timequjian.get(1));
        List<String> returntime = returntime(startDayDate, endDayDate);
        BdglMilitaryduty selectBdglMilitaryduty = new BdglMilitaryduty();
        for (String s : returntime) {
            selectBdglMilitaryduty.setCarUnitId(bdglMilitaryduty.getCarUnitId());
            selectBdglMilitaryduty.setAttendedTime(s);
            List<BdglMilitaryduty> selectBdglMilitarydutyList = bdglMilitarydutyService.selectBdglMilitarydutyList(selectBdglMilitaryduty);
            if (selectBdglMilitarydutyList.size()>0){
                return AjaxResult.error(s + "，车属单位为："+bdglMilitaryduty.getCarUnitName()+"，已存在值班，无法重复添加！");
            }
        }

//        List<BdglMilitaryduty> bdglMilitaryduties = bdglMilitarydutyService.selectBdglMilitarydutyList(new BdglMilitaryduty());
//        for (String times : returntime) {
//            for (BdglMilitaryduty militaryduty : bdglMilitaryduties) {
//                if(militaryduty.getAttendedTime().equals(times)&&militaryduty.getCarUnitId().equals(bdglMilitaryduty.getCarUnitId())) {
//                    return AjaxResult.error(times + "，当前时间已存在值班，无法重复添加！");
//                }
//            }
//        }
        AjaxResult ajaxResult = setMilitarydutyPlateId(bdglMilitaryduty);
        if (ajaxResult!=null){
            return ajaxResult;
        }

        //新增值班数据
        for (String times : returntime) {
            bdglMilitaryduty.setAttendedTime(times);
            bdglMilitarydutyService.insertBdglMilitaryduty(bdglMilitaryduty);
        }

        return toAjax(200);
    }

    /**
     * 修改军车值班
     */
    @PreAuthorize("@ss.hasPermi('combatduty:militarydutyinfo:edit')")
    @Log(title = "军车值班", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglMilitaryduty bdglMilitaryduty)
    {
        /*List<BdglMilitaryduty> bdglMilitaryduties = bdglMilitarydutyService.selectBdglMilitarydutyList(new BdglMilitaryduty());
        for (BdglMilitaryduty militaryduty : bdglMilitaryduties) {
            if(militaryduty.getId() != bdglMilitaryduty.getId()) {
                if(militaryduty.getAttendedTime().equals(bdglMilitaryduty.getAttendedTime())) {
                    return AjaxResult.error(bdglMilitaryduty.getAttendedTime() + "，当前时间已存在值班，无法重复添加！");
                }
            }

        }
        AjaxResult ajaxResult = setMilitarydutyPlateId(bdglMilitaryduty);
        if (ajaxResult!=null){
            return ajaxResult;
        }*/
        setMilitarydutyPlateId(bdglMilitaryduty);
        return toAjax(bdglMilitarydutyService.updateBdglMilitaryduty(bdglMilitaryduty));
    }

    /**
     * 删除军车值班
     */
    @PreAuthorize("@ss.hasPermi('combatduty:militarydutyinfo:remove')")
    @Log(title = "军车值班", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bdglMilitarydutyService.deleteBdglMilitarydutyByIds(ids));
    }

    public AjaxResult setMilitarydutyPlateId(BdglMilitaryduty bdglMilitaryduty){
        List<Long> numberPlateIdList = bdglMilitaryduty.getNumberPlateIdList();
        if (numberPlateIdList==null||numberPlateIdList.size()==0){
            return AjaxResult.error("请选择号码号牌！");
        }

        //根据idList查询多个车
        BdglThebus bdglThebus = new BdglThebus();
        bdglThebus.setIdList(numberPlateIdList);
        List<BdglThebus> selectBdglThebusList = thebusService.selectBdglThebusList(bdglThebus);

        List<String> numberPlateNameList = selectBdglThebusList.stream().map(BdglThebus::getVehiclenumber).collect(Collectors.toList());
        bdglMilitaryduty.setNumberPlateId(StringUtils.join(numberPlateIdList,","));
        bdglMilitaryduty.setNumberPlateName(StringUtils.join(numberPlateNameList,","));

        //同步军车状态
        for (Long numberPlateId : bdglMilitaryduty.getNumberPlateIdList()) {
            BdglThebus updObj = new BdglThebus();
            updObj.setId(numberPlateId);
            updObj.setStatus("2");
            thebusService.updateBdglThebus(updObj);
        }
        return null;
    }

    public List<String> returntime(Date startDayDate, Date endDayDate) {
        //首先要获取到Calendar类，该类有对应的添加日期的方法！！
        Calendar calendar = Calendar.getInstance();
        List<String> listtime = new ArrayList<>();
        listtime.add(sdf.format(startDayDate));

        long betweenDays = (endDayDate.getTime() / (24 * 60 * 60 * 1000)) - (startDayDate.getTime() / (24 * 60 * 60 * 1000));

        //然后把相差的天数set到calendar类中，这样就改变日期了
        calendar.setTime(startDayDate);

        for (long i = 0; i < betweenDays; i++) {
            // 两个参数，第一个是要添加的日期(年月日)，第二个是要添加多少天！！！
            calendar.add(Calendar.DATE, 1); //加一天
            //calendar.add(Calendar.MONTH, 1); //  加一月
            //calendar.add(Calendar.YEAR,1);   //  加一年

            listtime.add(sdf.format(calendar.getTime()));
        }
        return listtime;
    }
}
