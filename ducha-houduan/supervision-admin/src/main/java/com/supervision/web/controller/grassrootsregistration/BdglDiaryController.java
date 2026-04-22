package com.supervision.web.controller.grassrootsregistration;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.common.core.domain.entity.SysRole;
import com.supervision.common.core.domain.entity.SysUser;
import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.common.utils.DateUtils;
import com.supervision.common.utils.StringUtils;
import com.supervision.grassrootsregistration.domain.BdglDailyInspect;
import com.supervision.grassrootsregistration.domain.BdglWeekwork;
import com.supervision.grassrootsregistration.domain.BdglWeekworkRegister;
import com.supervision.grassrootsregistration.service.IBdglDailyInspectService;
import com.supervision.grassrootsregistration.service.IBdglWeekworkRegisterService;
import com.supervision.grassrootsregistration.service.IBdglWeekworkService;
import com.supervision.system.mapper.SysUserMapper;
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
import com.supervision.grassrootsregistration.domain.BdglDiary;
import com.supervision.grassrootsregistration.service.IBdglDiaryService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 要事日记Controller
 *
 * @author supervision
 * @date 2022-02-26
 */
@RestController
@RequestMapping("/grassrootsregistration/bdgldiary")
public class BdglDiaryController extends BaseController {
    @Autowired
    private IBdglDiaryService bdglDiaryService;
    @Autowired
    private ISysDeptService sysDeptService;
    @Autowired
    private SysUserMapper userMapper;
    @Autowired
    private ISysUserService userService;

    @Resource
    private IBdglDailyInspectService bdglDailyInspectService;

    /**
     * 查询要事日记列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdgldiary:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglDiary bdglDiary) {
        LoginUser loginUser = getLoginUser();
        SysUser sysUser = userService.selectUserById(loginUser.getUserId());
        List<SysRole> sysRoles = userMapper.selectRole(loginUser.getUserId());
        String role = "";
        if (sysRoles != null) {
            for (SysRole sysRole : sysRoles) {
                if (sysRole.getRoleKey().equals("admin")||sysRole.getRoleKey().equals("special_registration")) {
                    role = sysRole.getRoleKey();
                    break;
                }
            }
        }

//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        String format = simpleDateFormat.format(DateUtils.getStartEndTime(00, 1));
//        //如果要事日记日期为空   获取昨天要事日记
//        if (bdglDiary.getDiaryTime() == null && bdglDiary.getUnitId() == null) {
//            bdglDiary.setDiaryTime(format);
//        }
        if (!role.equals("admin")&&!role.equals("special_registration")) {
            bdglDiary.setUnitId(Integer.parseInt(loginUser.getDeptId().toString()));
        }

        startPage();
        List<BdglDiary> list = bdglDiaryService.selectBdglDiaryList(bdglDiary);
        for (BdglDiary diary : list) {
            //判断当前用户是否有审批权限
            boolean b = loginUser.getUserId() == diary.getLead();
            Boolean aBoolean = compareId(loginUser.getUserId(), diary.getLead());
            if (aBoolean && diary.getExamine() != 1) {
                diary.setCommon("1");
            } else {
                diary.setCommon("0");
            }
        }
        settAttr(list);
        return getDataTable(list);
    }

    private void settAttr(List<BdglDiary> bdglDiaryList){
        if (bdglDiaryList.isEmpty()){
            return;
        }
        //设置添加要事日记的完整度
        int total=10;
        for (BdglDiary bdglDiary : bdglDiaryList) {
            int num=0;
            if (StringUtils.isNotBlank(bdglDiary.getTolerance())){
                num+=1;
            }
            if (StringUtils.isNotBlank(bdglDiary.getTransfer())){
                num+=1;
            }
            if (StringUtils.isNotBlank(bdglDiary.getEquip())){
                num+=1;
            }
            if (StringUtils.isNotBlank(bdglDiary.getInternal())){
                num+=1;
            }
            int pbqk=0;
            if (StringUtils.isNotBlank(bdglDiary.getDutylead())){
                pbqk+=1;
            }
            if (StringUtils.isNotBlank(bdglDiary.getDutya())){
                pbqk+=1;
            }
            if (StringUtils.isNotBlank(bdglDiary.getDutyb())){
                pbqk+=1;
            }
            if (StringUtils.isNotBlank(bdglDiary.getDutyc())){
                pbqk+=1;
            }
            if (StringUtils.isNotBlank(bdglDiary.getDutyd())){
                pbqk+=1;
            }
            if (StringUtils.isNotBlank(bdglDiary.getDutye())){
                pbqk+=1;
            }
            if (StringUtils.isNotBlank(bdglDiary.getDutyf())){
                pbqk+=1;
            }
            if (bdglDiary.getDutyDate()!=null){
                pbqk+=1;
            }
            if (pbqk>=5){
                num+=1;
            }
            if (StringUtils.isNotBlank(bdglDiary.getDutyinfoa())){
                num+=1;
            }
            if (StringUtils.isNotBlank(bdglDiary.getDutyinfoc())){
                num+=1;
            }
            if (StringUtils.isNotBlank(bdglDiary.getDutyinfoe())){
                num+=1;
            }
            if (StringUtils.isNotBlank(bdglDiary.getInvalid())){
                num+=1;
            }
            if (StringUtils.isNotBlank(bdglDiary.getNotice())){
                num+=1;
            }
            BigDecimal bigDecimal = new BigDecimal(num).divide(new BigDecimal(total), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
            String s = new DecimalFormat("0").format(bigDecimal);
            bdglDiary.setCompletionRatio(s);
        }

        //设置是否添加了查铺查哨
        List<Integer> idList = bdglDiaryList.stream().map(BdglDiary::getId).collect(Collectors.toList());
        BdglDailyInspect bdglDailyInspect = new BdglDailyInspect();
        bdglDailyInspect.setDiaryIdList(idList);
        List<BdglDailyInspect> bdglDailyInspectList = bdglDailyInspectService.selectBdglDailyInspectList(bdglDailyInspect);
        if (bdglDailyInspectList.size()==0){
            return;
        }
        Map<Integer,List<BdglDailyInspect>> inspectMap = new HashMap<>();
        for (BdglDailyInspect dailyInspect : bdglDailyInspectList) {
            List<BdglDailyInspect> inspectList = inspectMap.get(dailyInspect.getDiaryId());
            if (inspectList==null){
                inspectList = new ArrayList<>();
            }
            inspectList.add(dailyInspect);
            inspectMap.put(dailyInspect.getDiaryId(),inspectList);
        }
        for (BdglDiary bdglDiary : bdglDiaryList) {
            if (inspectMap.get(bdglDiary.getId())==null){
                bdglDiary.setBdglDailyInspectLen(0);
            }else {
                bdglDiary.setBdglDailyInspectLen(inspectMap.get(bdglDiary.getId()).size());
            }
        }
    }

    public static Boolean compareId(Long userID, Long leadId) {
        if (leadId == null) {
            return false;
        }
        String userid = userID.toString().trim();
        String leadid = leadId.toString().trim();
        if (userID.equals(leadId)) {
            return true;
        }
        return false;
    }

    /**
     * 导出要事日记列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdgldiary:export')")
    @Log(title = "要事日记", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglDiary bdglDiary) {
        List<BdglDiary> list = bdglDiaryService.selectBdglDiaryList(bdglDiary);
        ExcelUtil<BdglDiary> util = new ExcelUtil<BdglDiary>(BdglDiary.class);
        return util.exportExcel(list, "要事日记数据");
    }

    /**
     * 获取要事日记详细信息
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdgldiary:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id) {
        BdglDiary bdglDiary = bdglDiaryService.selectBdglDiaryxiangqing(id);
        return AjaxResult.success(bdglDiary);
    }

    /**
     * 新增要事日记
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdgldiary:add')")
    @Log(title = "要事日记", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglDiary bdglDiary) {
        LoginUser loginUser = getLoginUser();
        bdglDiary.setCreateTime(DateUtils.getNowDate());
        bdglDiary.setCreateName(loginUser.getUsername());
        bdglDiary.setCreateId(loginUser.getUserId());
        return toAjax(bdglDiaryService.insertBdglDiary(bdglDiary));
    }

    /**
     * 修改要事日记
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdgldiary:edit')")
    @Log(title = "要事日记", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglDiary bdglDiary) {
        return toAjax(bdglDiaryService.updateBdglDiary(bdglDiary));
    }

    /**
     * y要事日记审批
     */
    @RequestMapping("/update")
    public AjaxResult update(@RequestBody BdglDiary bdglDiary) {
        return toAjax(bdglDiaryService.updateBdglDiarya(bdglDiary));
    }

    /**
     * 删除要事日记
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdgldiary:remove')")
    @Log(title = "要事日记", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax(bdglDiaryService.deleteBdglDiaryByIds(ids));
    }

    /**
     * 获取当前部门当日工作安排
     */
    @GetMapping("/getWeekWork")
    public AjaxResult getWeekWork(BdglDiary bdglDiary) {

        List<BdglWeekworkRegister> weekworkRegisters = bdglDiaryService.getWeekWork(bdglDiary);
        return AjaxResult.success(weekworkRegisters);
    }

    /**
     * 获取未填写要事日记  未审批
     */
    @GetMapping("/grtWeiSP")
    public AjaxResult grtWeiSP() {
        List<SysDept> sysDepts = sysDeptService.selectDeptList(new SysDept());
        for (SysDept sysDept : sysDepts) {
            Date dNow = new Date();   //当前时间
            Calendar calendar = Calendar.getInstance(); //得到日历
            calendar.setTime(dNow);//把当前时间赋给日历
            calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
            Date time = calendar.getTime();
            BdglDiary bdglDiary = new BdglDiary();
            bdglDiary.setUnitId(Integer.parseInt(sysDept.getDeptId().toString()));
            bdglDiary.setBlogDate(time);
            bdglDiary.setExamine(1);
            BdglDiary bdglDiaries = bdglDiaryService.selectBdglDiaryLists(bdglDiary);
            if (bdglDiaries != null) {
                //获取昨日00点
                Date startEndTime = DateUtils.getStartEndTime(00, 1);
                //获取当日晚上十点
                Date startEndTime1 = DateUtils.getStartEndTime(10, 0);
                //如果值班日期在昨日和当天晚上十点之内  要事日记日期为前一天  大于当天晚上十点  要事日记为当天
                if (bdglDiary.getBlogDate().getTime() > startEndTime.getTime() && bdglDiary.getBlogDate().getTime() < startEndTime1.getTime()) {
                    sysDept.setCommon("1");

                }
            } else {
                sysDept.setCommon("2");
            }
        }
        return AjaxResult.success(sysDepts);
    }

    /**
     * 获取当前部门  干部  战士  文职人数
     */
    @GetMapping("/getRenShu")
    public AjaxResult getRenShu(Long deptId) {
        Map<String, Object> map = bdglDiaryService.getRenShu(deptId);
        return AjaxResult.success(map);
    }

    /**
     * 获取当日要事日记审批数量  未审批数量
     */
    @GetMapping("/getNumber")
    public Map<String, Object> getNumber(String date) {
        BdglDiary bdglDiary = new BdglDiary();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(DateUtils.getStartEndTime(00, 1));
        if (date == null) {
            bdglDiary.setDiaryTime(format);
        } else {
            bdglDiary.setDiaryTime(date);
        }
        bdglDiary.setExamine(1);
        /**查看当日以审批部门*/
        HashMap<Integer, Object> longObjectHashMap1 = new HashMap<>();
        HashSet<Object> longs = new HashSet<>();
        String unitIds = "";
        List<BdglDiary> bdglDiaries = bdglDiaryService.selectBdglDiaryList(bdglDiary);
        for (BdglDiary diary : bdglDiaries) {
            Integer unitId = diary.getUnitId();
            if (!longs.contains(unitId)) {
                longs.add(unitId);
                longObjectHashMap1.put(diary.getUnitId(), diary.getUnitName());
                unitIds += unitId + ",";
            }
        }
        HashMap<Long, Object> longObjectHashMap = new HashMap<>();
        ArrayList<SysDept> sysDepts1 = new ArrayList<>();
        SysDept sysDept = new SysDept();
        sysDept.setIsRead("0");
        List<SysDept> sysDepts = sysDeptService.selectDeptLists(sysDept);
        for (int i = 0; i < sysDepts.size(); i++) {
            Long deptId = sysDepts.get(i).getDeptId();
            boolean b = sysDepts.get(i).getDeptId() != 203L;
            String deptName = sysDepts.get(i).getDeptName();

            if (sysDepts.get(i).getDeptId() != 203L && sysDepts.get(i).getDeptId() != 204 && sysDepts.get(i).getDeptId() != 205 && sysDepts.get(i).getDeptId() != 206 && sysDepts.get(i).getDeptId() != 100) {
                if (!sysDepts.get(i).getDeptName().equals("俱乐部") && !sysDepts.get(i).getDeptName().equals("未分配")&& !sysDepts.get(i).getDeptName().contains("营营部")
                        && !sysDepts.get(i).getDeptName().contains("生活服务中心")&& !sysDepts.get(i).getDeptName().contains("门诊部")) {

                    sysDepts1.add(sysDepts.get(i));
                    if (!unitIds.contains(sysDepts.get(i).getDeptId().toString())) {
                        longObjectHashMap.put(sysDepts.get(i).getDeptId(), sysDepts.get(i).getDeptName());
                    }
                }
            }
        }
        
        HashMap<String, Object> map = new HashMap<>();
        map.put("yiShenPi", longs.size());
        map.put("weiShenPi", longObjectHashMap.size());
       // map.put("weiShenPi", sysDepts1.size() - longs.size());
        //已提交部门名称
        map.put("cg", longObjectHashMap1);
        //未提交部门名称
        map.put("sb", longObjectHashMap);
        map.put("time", bdglDiary.getDiaryTime());
        return map;
    }

}
