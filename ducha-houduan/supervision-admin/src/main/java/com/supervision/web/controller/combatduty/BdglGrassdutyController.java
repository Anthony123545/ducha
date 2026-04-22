package com.supervision.web.controller.combatduty;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.supervision.combatduty.domain.BdglBaseduty;
import com.supervision.combatduty.domain.TodayBeonduty;
import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.common.utils.DateUtils;
import com.supervision.common.utils.StringUtils;
import com.supervision.system.service.ISysDeptService;
import com.supervision.system.service.ISysUserService;
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
import com.supervision.combatduty.domain.BdglGrassduty;
import com.supervision.combatduty.service.IBdglGrassdutyService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 基层值班Controller
 *
 * @author supervision
 * @date 2022-03-10
 */
@RestController
@RequestMapping("/combatduty/grassdutyinfo")
public class BdglGrassdutyController extends BaseController {
    @Autowired
    private IBdglGrassdutyService bdglGrassdutyService;
    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysDeptService deptService;

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 查询基层值班列表
     */
    @PreAuthorize("@ss.hasPermi('combatduty:grassdutyinfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglGrassduty bdglGrassduty) {
        SysDept sysDept=null;
        if(bdglGrassduty.getUnitId()!=null){

             sysDept = deptService.selectDeptById(bdglGrassduty.getUnitId());
        }

        LoginUser loginUser = getLoginUser();
        List<String> strings = userService.selectUserRoleKey(loginUser.getUserId());
        boolean falg=false;
        boolean falg1=false;
        for (String string : strings) {
            if("admin".equals(string)){
                falg1=true;
                break;
            } else if (!"admin".equals(string)) {
                if(sysDept!=null&&sysDept.getAncestors()!=null){
                    falg = isFalg(sysDept, loginUser, falg);
                }

            }
            if(string.equals("zbzbgl")){
                falg1=true;
                break;
            } else if (!string.equals("zbzbgl")) {
                if(sysDept!=null&&sysDept.getAncestors()!=null){
                    falg = isFalg(sysDept, loginUser, falg);
                }


            }
        }
        if(falg1!=true){

            if(falg==false){
                bdglGrassduty.setUnitId(loginUser.getDeptId());
            }
        }

        List<BdglGrassduty> lists = new ArrayList<>();
        //今日值班
        if ("1".equals(bdglGrassduty.getType())) {
            bdglGrassduty.setAttendedTime(sdf.format(new Date()));
            startPage();
            lists = bdglGrassdutyService.selectBdglGrassdutyList(bdglGrassduty);

            //明日值班
        } else if ("2".equals(bdglGrassduty.getType())) {
            LocalDate localDate = LocalDate.now().plusDays(1); //获取下一天
            String tomorrow = localDate + "";
            bdglGrassduty.setAttendedTime(tomorrow);
            startPage();
            lists = bdglGrassdutyService.selectBdglGrassdutyList(bdglGrassduty);

        } else {
//            if(falg==false){
//                bdglGrassduty.setUnitId(loginUser.getDeptId());
//            }
            startPage();
            lists = bdglGrassdutyService.selectBdglGrassdutyList(bdglGrassduty);
        }
        return getDataTable(lists);
    }

    /**
     * 今日基层值班
     * @return
     */
    @GetMapping("/todaydutyinfo")
    public AjaxResult todaydutyinfo(){
        TodayBeonduty todayBeonduty = new TodayBeonduty();
        BdglGrassduty bdglGrassduty = new BdglGrassduty();
        bdglGrassduty.setAttendedTime(sdf.format(new Date()));
        List<BdglGrassduty> lists = new ArrayList<>();
        List<BdglGrassduty> list = bdglGrassdutyService.selectBdglGrassdutyList(bdglGrassduty);
        String[] unitids = new String[]{"206","207","208","209","210","211","212","213","214","215","216","217","218","220","221","219","222","225","223","226","224","227","228","229"};
        for (String unitid : unitids){

            for (BdglGrassduty baseduty : list) {
                if(baseduty.getUnitId().equals(Long.valueOf(unitid))){
                    lists.add(baseduty);
                }
            }
        }
        return AjaxResult.success(lists);
    }

    private boolean isFalg(SysDept sysDept, LoginUser loginUser, boolean falg) {
        for (String s : sysDept.getAncestors().split(",")) {
            if(s.equals(loginUser.getDeptId().toString())){
                falg =true;
            }
        }
        return falg;
    }

    /**
     * 导出基层值班列表
     */
    @PreAuthorize("@ss.hasPermi('combatduty:grassdutyinfo:export')")
    @Log(title = "基层值班", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglGrassduty bdglGrassduty) {
        //如果不指定值班日期，则默认导出当天值班数据
        if (StringUtils.isEmpty(bdglGrassduty.getAttendedTime())){
            bdglGrassduty.setAttendedTime(DateUtils.getDate());
        }
        List<BdglGrassduty> list = bdglGrassdutyService.selectGrassdutyList(bdglGrassduty);
        ExcelUtil<BdglGrassduty> util = new ExcelUtil<>(BdglGrassduty.class);
        return util.exportExcel(list, "基层值班数据");
    }

    /**
     * 获取基层值班详细信息
     */
    @PreAuthorize("@ss.hasPermi('combatduty:grassdutyinfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(bdglGrassdutyService.selectBdglGrassdutyById(id));
    }



    /**
     * 新增基层值班
     */
    @PreAuthorize("@ss.hasPermi('combatduty:grassdutyinfo:add')")
    @Log(title = "基层值班", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglGrassduty bdglGrassduty) throws ParseException {
        List<String> timequjian = bdglGrassduty.getTimequjian();
        Date startDayDate = sdf.parse(timequjian.get(0));
        Date endDayDate = sdf.parse(timequjian.get(1));
        List<String> returntime = returntime(startDayDate, endDayDate);
        for (String times : returntime) {
            BdglGrassduty bdglGrassduty1 = new BdglGrassduty();
            bdglGrassduty1.setUnitId(bdglGrassduty.getUnitId());
            List<BdglGrassduty> bdglGrassduties = bdglGrassdutyService.selectBdglGrassdutyLists(bdglGrassduty1);
            for (BdglGrassduty grassduty : bdglGrassduties) {
                if (grassduty.getAttendedTime().equals(times)) {
                    return AjaxResult.error(times + "，当前时间已存在值班，无法重复添加！");
                }
            }
        }
        for (String times : returntime) {
            bdglGrassduty.setAttendedTime(times);
            bdglGrassdutyService.insertBdglGrassduty(bdglGrassduty);
        }
        return toAjax(200);
    }

    /**
     * 修改基层值班
     */
    @PreAuthorize("@ss.hasPermi('combatduty:grassdutyinfo:edit')")
    @Log(title = "基层值班", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglGrassduty bdglGrassduty) {
        List<BdglGrassduty> bdglGrassduties = bdglGrassdutyService.selectBdglGrassdutyList(new BdglGrassduty());
        for (BdglGrassduty grassduty : bdglGrassduties) {
            if (grassduty.getId() != bdglGrassduty.getId()) {
                if (grassduty.getAttendedTime().equals(bdglGrassduty.getAttendedTime())) {
                    return AjaxResult.error(bdglGrassduty.getAttendedTime() + "，当前时间已存在值班，无法重复添加！");
                }
            }
        }
        return toAjax(bdglGrassdutyService.updateBdglGrassduty(bdglGrassduty));
    }

    /**
     * 删除基层值班
     */
    @PreAuthorize("@ss.hasPermi('combatduty:grassdutyinfo:remove')")
    @Log(title = "基层值班", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(bdglGrassdutyService.deleteBdglGrassdutyByIds(ids));
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
