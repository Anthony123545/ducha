package com.supervision.web.controller.combatduty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import com.supervision.combatduty.domain.BdglBaseduty;
import com.supervision.combatduty.service.IBdglBasedutyService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 基地值班Controller
 * 
 * @author supervision
 * @date 2022-03-10
 */
@RestController
@RequestMapping("/combatduty/basedutyinfo")
public class BdglBasedutyController extends BaseController
{
    @Autowired
    private IBdglBasedutyService bdglBasedutyService;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 查询基地值班列表
     */
    @PreAuthorize("@ss.hasPermi('combatduty:basedutyinfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglBaseduty bdglBaseduty)
    {
        startPage();
        List<BdglBaseduty> lists = new ArrayList<>();
        //今日值班
        if ("1".equals(bdglBaseduty.getType())) {
            List<BdglBaseduty> list = bdglBasedutyService.selectBdglBasedutyList(bdglBaseduty);
            for (BdglBaseduty baseduty : list) {
                String today = sdf.format(new Date());
                if(today.equals(baseduty.getAttendedTime())) {
                    lists.add(baseduty);
                }
            }
         //明日值班
        } else if("2".equals(bdglBaseduty.getType())) {
            LocalDate localDate = LocalDate.now().plusDays(1); //获取下一天
            //String tomorrow = sdf.format(localDate);
            String tomorrow = localDate + "";
            List<BdglBaseduty> list = bdglBasedutyService.selectBdglBasedutyList(bdglBaseduty);
            for (BdglBaseduty baseduty : list) {
                if(tomorrow.equals(baseduty.getAttendedTime())) {
                    lists.add(baseduty);
                }
            }
        } else {
            List<BdglBaseduty> list = bdglBasedutyService.selectBdglBasedutyList(bdglBaseduty);
            lists = list;
        }

        return getDataTable(lists);
    }

    /**
     * 导出基地值班列表
     */
    @PreAuthorize("@ss.hasPermi('combatduty:basedutyinfo:export')")
    @Log(title = "基地值班", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglBaseduty bdglBaseduty)
    {
        List<BdglBaseduty> list = bdglBasedutyService.selectBdglBasedutyList(bdglBaseduty);
        ExcelUtil<BdglBaseduty> util = new ExcelUtil<BdglBaseduty>(BdglBaseduty.class);
        return util.exportExcel(list, "基地值班数据");
    }

    /**
     * 获取基地值班详细信息
     */
    @PreAuthorize("@ss.hasPermi('combatduty:basedutyinfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(bdglBasedutyService.selectBdglBasedutyById(id));
    }

    /**
     * 新增基地值班
     */
    @PreAuthorize("@ss.hasPermi('combatduty:basedutyinfo:add')")
    @Log(title = "基地值班", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglBaseduty bdglBaseduty) throws ParseException {
        List<String> timequjian = bdglBaseduty.getTimequjian();
        Date startDayDate = sdf.parse(timequjian.get(0));
        Date endDayDate = sdf.parse(timequjian.get(1));
        List<String> returntime = returntime(startDayDate, endDayDate);
        for (String times : returntime) {
            List<BdglBaseduty> bdglBaseduties = bdglBasedutyService.selectBdglBasedutyList(new BdglBaseduty());
            for (BdglBaseduty baseduty : bdglBaseduties) {
                if(baseduty.getAttendedTime().equals(times)) {
                    return AjaxResult.error(times + "，当前时间已存在值班，无法重复添加！");
                }
            }
        }

        for (String times : returntime) {
            bdglBaseduty.setAttendedTime(times);
            bdglBasedutyService.insertBdglBaseduty(bdglBaseduty);
        }

        return toAjax(200);
    }

    /**
     * 修改基地值班
     */
    @PreAuthorize("@ss.hasPermi('combatduty:basedutyinfo:edit')")
    @Log(title = "基地值班", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglBaseduty bdglBaseduty)
    {
        List<BdglBaseduty> bdglBaseduties = bdglBasedutyService.selectBdglBasedutyList(new BdglBaseduty());
        for (BdglBaseduty baseduty : bdglBaseduties) {
            if(baseduty.getId() != bdglBaseduty.getId()){
                if(baseduty.getAttendedTime().equals(bdglBaseduty.getAttendedTime())) {
                    return AjaxResult.error(bdglBaseduty.getAttendedTime() + "，当前时间已存在值班，无法重复添加！");
                }
            }

        }
        return toAjax(bdglBasedutyService.updateBdglBaseduty(bdglBaseduty));
    }

    /**
     * 删除基地值班
     */
    @PreAuthorize("@ss.hasPermi('combatduty:basedutyinfo:remove')")
    @Log(title = "基地值班", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bdglBasedutyService.deleteBdglBasedutyByIds(ids));
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
