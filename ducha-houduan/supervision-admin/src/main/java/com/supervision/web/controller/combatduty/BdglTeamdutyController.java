package com.supervision.web.controller.combatduty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.supervision.combatduty.domain.BdglTeamdutyFu;
import com.supervision.combatduty.service.IBdglTeamdutyFuService;
import com.supervision.common.utils.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
import com.supervision.combatduty.domain.BdglTeamduty;
import com.supervision.combatduty.service.IBdglTeamdutyService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 大队值班Controller
 *
 * @author supervision
 * @date 2022-03-10
 */
@Transactional
@RestController
@RequestMapping("/combatduty/teamdutyinfo")
public class BdglTeamdutyController extends BaseController {
    @Autowired
    private IBdglTeamdutyService bdglTeamdutyService;

    @Autowired
    private IBdglTeamdutyFuService teamdutyFuService;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 查询大队值班列表
     */
    @PreAuthorize("@ss.hasPermi('combatduty:teamdutyinfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglTeamduty bdglTeamduty) {
        startPage();
        List<BdglTeamduty> list = bdglTeamdutyService.selectBdglTeamdutyList(bdglTeamduty);
        return getDataTable(list);
    }

    /**
     * 导出大队值班列表
     */
    @PreAuthorize("@ss.hasPermi('combatduty:teamdutyinfo:export')")
    @Log(title = "大队值班", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglTeamduty bdglTeamduty) {
        List<BdglTeamduty> list = bdglTeamdutyService.selectBdglTeamdutyList(bdglTeamduty);
        ExcelUtil<BdglTeamduty> util = new ExcelUtil<BdglTeamduty>(BdglTeamduty.class);
        return util.exportExcel(list, "大队值班数据");
    }

    /**
     * 获取大队值班详细信息
     */
    @PreAuthorize("@ss.hasPermi('combatduty:teamdutyinfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        BdglTeamduty bdglTeamduty = bdglTeamdutyService.selectBdglTeamdutyById(id);
        BdglTeamdutyFu teamduty = new BdglTeamdutyFu();
        teamduty.setBdglTeamdutyId(id);
        List<BdglTeamdutyFu> bdglTeamdutyFus = teamdutyFuService.selectBdglTeamdutyFuList(teamduty);
        bdglTeamduty.setTeamduties(bdglTeamdutyFus);
        return AjaxResult.success(bdglTeamduty);
    }

    /**
     * 新增大队值班
     */
    @PreAuthorize("@ss.hasPermi('combatduty:teamdutyinfo:add')")
    @Log(title = "大队值班", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglTeamduty bdglTeamduty) throws ParseException {
        List<String> timequjian = bdglTeamduty.getTimequjian();
        Date startDayDate = sdf.parse(timequjian.get(0));
        Date endDayDate = sdf.parse(timequjian.get(1));
        List<String> returntime = returntime(startDayDate, endDayDate);
        String danqianshijian = sd.format(new Date());
        for (String times : returntime) {
            List<BdglTeamduty> bdglTeamduties = bdglTeamdutyService.selectBdglTeamdutyList(new BdglTeamduty());
            for (BdglTeamduty teamduty : bdglTeamduties) {
                if (sdf.parse(teamduty.getAttendedTime()).equals(sdf.parse(times))) {
                    return AjaxResult.error(times + "，当前时间已存在值班，无法重复添加！");
                }
            }
        }
        List<BdglTeamdutyFu> teamduties = bdglTeamduty.getTeamduties();
        for (String times : returntime) {
            bdglTeamduty.setAttendedTime(times + danqianshijian.substring(danqianshijian.length() - 9, danqianshijian.length()));
            bdglTeamduty.setId(null);
            bdglTeamdutyService.insertBdglTeamduty(bdglTeamduty);
            Long id = bdglTeamduty.getId();
            if(teamduties!=null){
                for (BdglTeamdutyFu teamduty : teamduties) {
                    if(StringUtils.isNotNull(teamduty)){
                        teamduty.setBdglTeamdutyId(id);
                        teamdutyFuService.insertBdglTeamdutyFu(teamduty);
                    }
                }
            }


        }
        return toAjax(200);
    }

    /**
     * 修改大队值班
     */
    @PreAuthorize("@ss.hasPermi('combatduty:teamdutyinfo:edit')")
    @Log(title = "大队值班", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglTeamduty bdglTeamduty) throws ParseException {
        List<BdglTeamduty> bdglTeamduties = bdglTeamdutyService.selectBdglTeamdutyList(new BdglTeamduty());
        for (BdglTeamduty teamduty : bdglTeamduties) {
            if (teamduty.getId() != bdglTeamduty.getId()) {
                if (sdf.parse(teamduty.getAttendedTime()).equals(sdf.parse(bdglTeamduty.getAttendedTime()))) {
                    return AjaxResult.error(bdglTeamduty.getAttendedTime() + "，当前时间已存在值班，无法重复添加！");
                }
            }
        }
        String danqianshijian = sd.format(new Date());
        bdglTeamduty.setAttendedTime(bdglTeamduty.getAttendedTime().substring(0, 10) + danqianshijian.substring(danqianshijian.length() - 9, danqianshijian.length()));
        int i = bdglTeamdutyService.updateBdglTeamduty(bdglTeamduty);
        List<BdglTeamdutyFu> teamduties = bdglTeamduty.getTeamduties();
        if(teamduties!=null){
            for (BdglTeamdutyFu teamduty : teamduties) {
                if(StringUtils.isNotNull(teamduty)) {

                    if (teamduty.getId() == null) {
                        teamduty.setBdglTeamdutyId(bdglTeamduty.getId());
                        teamdutyFuService.insertBdglTeamdutyFu(teamduty);
                    } else {
                        teamdutyFuService.updateBdglTeamdutyFu(teamduty);
                    }
                }
            }
        }
        return toAjax(i);
    }

    /**
     * 删除大队值班
     */
    @PreAuthorize("@ss.hasPermi('combatduty:teamdutyinfo:remove')")
    @Log(title = "大队值班", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(bdglTeamdutyService.deleteBdglTeamdutyByIds(ids));
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
