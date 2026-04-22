package com.supervision.web.controller.situationanalysis;

import cn.hutool.core.collection.CollectionUtil;
import com.supervision.barracksManagement.domain.BdglBarracks;
import com.supervision.barracksManagement.domain.BdglFlat;
import com.supervision.barracksManagement.service.IBdglBarracksService;
import com.supervision.barracksManagement.service.IBdglFlatService;
import com.supervision.bdglregular.domain.BdglRegular;
import com.supervision.bdglregular.service.IBdglRegularService;
import com.supervision.combatduty.domain.BdglGrassduty;
import com.supervision.combatduty.domain.BdglTeamduty;
import com.supervision.combatduty.domain.PeopleStatistics;
import com.supervision.combatduty.service.IBdglGrassdutyService;
import com.supervision.combatduty.service.IBdglTeamdutyService;
import com.supervision.combatduty.service.IPeopleStatisticsService;
import com.supervision.common.core.controller.BaseController;
import com.supervision.common.core.domain.AjaxResult;
import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.common.utils.DateUtils;
import com.supervision.grassrootsregistration.domain.BdglDailyInspect;
import com.supervision.grassrootsregistration.domain.BdglDiary;
import com.supervision.grassrootsregistration.domain.BdglWeekworkRegister;
import com.supervision.grassrootsregistration.service.IBdglDailyInspectService;
import com.supervision.grassrootsregistration.service.IBdglDiaryService;
import com.supervision.grassrootsregistration.service.IBdglWeekworkRegisterService;
import com.supervision.materialManagement.domain.MaterialArchives;
import com.supervision.materialManagement.service.IMaterialArchivesService;
import com.supervision.medicalhealth.domain.BdglAntiepidemicArchives;
import com.supervision.medicalhealth.service.IBdglAntiepidemicArchivesService;
import com.supervision.militaryvehicleManagement.domain.BdglPrivateCar;
import com.supervision.militaryvehicleManagement.domain.BdglThebus;
import com.supervision.militaryvehicleManagement.service.IBdglPrivateCarService;
import com.supervision.militaryvehicleManagement.service.IBdglThebusService;
import com.supervision.peopleChuRu.domain.BdglLeave;
import com.supervision.peopleChuRu.service.impl.BdglLeaveServiceImpl;
import com.supervision.peopleManage.domain.BdglPeople;
import com.supervision.peopleManage.service.IBdglPeopleService;
import com.supervision.print.service.IPrintsLogService;
import com.supervision.system.service.ISysDeptService;
import com.supervision.workingArrangements.service.IBdglWorkTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 态势分析Controller
 *
 * @author bobi
 * @date 2022-03-16
 */
@RestController
@RequestMapping("/situation/situationinfo")
public class SituationController extends BaseController {

    @Autowired
    private IBdglPeopleService bdglPeopleService;

    @Autowired
    private ISysDeptService deptService;

    @Autowired
    private IBdglDailyInspectService bdglDailyInspectService;

    @Autowired
    private IBdglTeamdutyService bdglTeamdutyService;

    @Autowired
    private IBdglDiaryService bdglDiaryService;

    @Autowired
    private IBdglWorkTaskService bdglWorkTaskService;

    @Autowired
    private IPrintsLogService printsLogService;

    @Autowired
    private IBdglThebusService bdglThebusService;

    @Autowired
    private IBdglPrivateCarService bdglPrivateCarService;

    @Autowired
    private IBdglRegularService bdglRegularService;

    @Autowired
    private IBdglAntiepidemicArchivesService antiepidemicArchivesService;

    @Autowired
    private IMaterialArchivesService materialArchivesService;

    @Autowired
    private BdglLeaveServiceImpl leaveService;

    @Autowired
    private IBdglWeekworkRegisterService bdglWeekworkRegisterService;

    @Autowired
    private IBdglBarracksService bdglBarracksService;

    @Autowired
    private IBdglFlatService bdglFlatService;

    @Autowired
    private IBdglGrassdutyService grassdutyService;

    @Autowired
    private RedisTemplate redisTemplate;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat sfmouth = new SimpleDateFormat("yyyy-MM");
    private DecimalFormat df = new DecimalFormat("0.00");


    @Autowired
    private IPeopleStatisticsService peopleStatisticsService;

    /**
     * 查询大队人员在位情况
     *
     * @return
     */
    @GetMapping("/zaiweipeople")
    public AjaxResult zaiWeiPeople() {


        /**
         * 查询人员变动列表
         */
        PeopleStatistics peopleStatistics = new PeopleStatistics();
        List<PeopleStatistics> list1 = peopleStatisticsService.selectPeopleStatisticsList(peopleStatistics);
        List<Map<String, Integer>> list = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        if(list1!=null&&list1.size()>0){
            for (PeopleStatistics statistics : list1) {
                if(statistics!=null){
                    if(statistics.getToggle().equals("1")){
                        map.put("zaiweizongshu", statistics.getTotalNumber());
                        map.put("nabianzaiwei", statistics.getNabianPlace());
                        map.put("weinabianzaiwei", statistics.getNotInPlace());
                        map.put("nabianwaichu", statistics.getNabianOut());
                        map.put("weinabianwaichu", statistics.getNotNabianOut());
                        list.add(map);
                        return AjaxResult.success(list);
                    }
                }
            }
        }


        //在位总数
        BdglPeople bql = new BdglPeople();
        bql.setReign(0);
        List<BdglPeople> zaiweizongshu = bdglPeopleService.selectBdglPeopleList(bql);
        Integer zaiweizongsu = 0;
        if (zaiweizongshu.size() > 0) {
            zaiweizongsu = zaiweizongshu.size();
        }
        map.put("zaiweizongshu", zaiweizongsu);
        //纳编在位
        BdglPeople bp = new BdglPeople();
        bp.setReign(0);
        bp.setAuthorizedStrength("Y");
        List<BdglPeople> nabianzaiwei = bdglPeopleService.selectBdglPeopleList(bp);
        Integer nabianzaiweis = 0;
        if (nabianzaiwei.size() > 0) {
            nabianzaiweis = nabianzaiwei.size();
        }
        map.put("nabianzaiwei", nabianzaiweis);

        //纳编总数
        BdglPeople bpn = new BdglPeople();
        bpn.setAuthorizedStrength("Y");
        List<BdglPeople> nabianzongshu = bdglPeopleService.selectBdglPeopleList(bpn);
        Integer nabainzs = 0;
        if (nabianzongshu.size() > 0) {
            nabainzs = nabianzongshu.size();
        }
        map.put("nabianwaichu", nabainzs - nabianzaiweis);

        //未纳编在位
        BdglPeople bps = new BdglPeople();
        bps.setReign(0);
        bps.setAuthorizedStrength("N");
        List<BdglPeople> weinabianzaiwei = bdglPeopleService.selectBdglPeopleList(bps);
        Integer wenabianzais = 0;
        if (weinabianzaiwei.size() > 0) {
            wenabianzais = weinabianzaiwei.size();
        }
        map.put("weinabianzaiwei", wenabianzais);

        //未纳编总数
        BdglPeople bpq = new BdglPeople();
        bpq.setAuthorizedStrength("N");
        List<BdglPeople> weinabianzongshu = bdglPeopleService.selectBdglPeopleList(bps);
        Integer weinabianzongshus = 0;
        if (weinabianzongshu.size() > 0) {
            weinabianzongshus = weinabianzongshu.size();
        }
        map.put("weinabianwaichu", weinabianzongshus - wenabianzais);
        list.add(map);
        return AjaxResult.success(list);
    }

    /**
     * 各单位在位情况
     *
     * @return
     */
    @GetMapping("/gedanweizaiweiqingkaung")
    public AjaxResult geDanWeiZaiWeiQingKuang() {
        Boolean hasKey = redisTemplate.hasKey("dept:zaiwei:list");
        if (hasKey){
            List<Map<String,Object>> list = (List<Map<String, Object>>) redisTemplate.opsForValue().get("dept:zaiwei:list");
            return AjaxResult.success(list);
        }
        List<Map<String,Object>> list = new ArrayList<>();
        SysDept sd = new SysDept();
        sd.setParentId(100L);
        sd.setIsShow("Y");
        //查询所有一级单位
        List<SysDept> sysDeptList = deptService.selectDeptList(sd);
        if (CollectionUtil.isNotEmpty(sysDeptList) && sysDeptList.size() > 0) {
            sysDeptList.forEach(sysDept -> {
                Map<String,Object> map = new HashMap<>(2);
                //查询单位总人数
                int oneTotal = bdglPeopleService.selectTotalCountByDeptId(sysDept.getDeptId());
                //查询单位在位人数
                int oneReignCount = bdglPeopleService.selectReginCountByDeptId(sysDept.getDeptId());
                //查询二级单位
                List<SysDept> deptTwoList = getDeptTwoList(sysDept);
                //查询二级单位总人数
                int twoTotal = 0;
                int twoReignCount = 0;
                for (SysDept dept : deptTwoList){
                    //查询单位总人数
                    int count = bdglPeopleService.selectTotalCountByDeptId(dept.getDeptId());
                    twoTotal = count + twoTotal;
                    //查询单位在位人数
                    int rCount = bdglPeopleService.selectReginCountByDeptId(dept.getDeptId());
                    twoReignCount = rCount + twoReignCount;
                }
                //单位总人数
                int total = oneTotal + twoTotal;
                //单位在位总人数
                int reignCount = oneReignCount + twoReignCount;
                //计算 在位百分比
                double percent = 0;
                if (reignCount != 0 || total != 0){
                    percent = (double) reignCount / (double) total;
                }
                map.put(sysDept.getDeptName(), percent * 100);
                list.add(map);
            });
        }
        //6小时缓存失效
        redisTemplate.opsForValue().set("dept:zaiwei:list", list, Duration.ofHours(6));
        return AjaxResult.success(list);
    }

    private List<SysDept> getDeptTwoList(SysDept sysDept){
        //查询二级单位
        SysDept dept = new SysDept();
        dept.setParentId(sysDept.getDeptId());
        dept.setIsShow("Y");
        return deptService.selectDeptList(dept);
    }

    /**
     * 外出人员占比
     *
     * @return
     */
    @GetMapping("/waichurenyuanzhanbi")
    public AjaxResult wiachurrenyuanzhanbi() {

        Map<String, List<Map>> map = new HashMap();
        List<Map> list = null;
        Map map1 = null;

        List<BdglPeople> bdglPeople = bdglPeopleService.selectBdglPeopleList(new BdglPeople());  //查出所有人员
        if (bdglPeople.size() > 0) {
            //查询休假人员
            BdglPeople xjbp = new BdglPeople();
            xjbp.setReign(1);
            List<BdglPeople> bdglPeople1 = bdglPeopleService.selectBdglPeopleList(xjbp);
            Integer xiujiarenyuan = 0;
            if (bdglPeople1.size() > 0) {
                xiujiarenyuan = bdglPeople1.size();
            }

            map1 = new HashMap();
            list = new ArrayList<>();
            map1.put("renshu", xiujiarenyuan);
            map1.put("zhanbi", xiujiarenyuan / bdglPeople.size());
            list.add(map1);
            map.put("休假", list);

            //查询出差人员
            BdglPeople ccbp = new BdglPeople();
            ccbp.setReign(2);
            List<BdglPeople> bdglPeople2 = bdglPeopleService.selectBdglPeopleList(ccbp);
            Integer chuchairenyuan = 0;
            if (bdglPeople2.size() > 0) {
                chuchairenyuan = bdglPeople2.size();
            }
            map1 = new HashMap();
            list = new ArrayList<>();
            map1.put("renshu", chuchairenyuan);
            map1.put("zhanbi", df.format((double) chuchairenyuan / bdglPeople.size()));
            list.add(map1);
            map.put("出差", list);

            //查询请假人员
            BdglPeople qjbp = new BdglPeople();
            qjbp.setReign(8);
            List<BdglPeople> bdglPeople3 = bdglPeopleService.selectBdglPeopleList(qjbp);
            Integer qingjiarenyuan = 0;
            if (bdglPeople3.size() > 0) {
                qingjiarenyuan = bdglPeople3.size();
            }
            map1 = new HashMap();
            list = new ArrayList<>();
            map1.put("renshu", qingjiarenyuan);
            map1.put("zhanbi", df.format((double) qingjiarenyuan / bdglPeople.size()));
            list.add(map1);
            map.put("请假", list);

            //查询学习人员
            BdglPeople xxbp = new BdglPeople();
            xxbp.setReign(4);
            List<BdglPeople> bdglPeople4 = bdglPeopleService.selectBdglPeopleList(xxbp);
            Integer xuexirenyuan = 0;
            if (bdglPeople4.size() > 0) {
                xuexirenyuan = bdglPeople4.size();
            }
            map1 = new HashMap();
            list = new ArrayList<>();
            map1.put("renshu", xuexirenyuan);
            map1.put("zhanbi", df.format((double) xuexirenyuan / bdglPeople.size()));
            list.add(map1);
            map.put("学习", list);

            //查询特殊类型人员
            BdglPeople tsbp = new BdglPeople();
            tsbp.setReign(7);
            List<BdglPeople> bdglPeople5 = bdglPeopleService.selectBdglPeopleList(tsbp);
            Integer teshurenyuan = 0;
            if (bdglPeople5.size() > 0) {
                teshurenyuan = bdglPeople5.size();
            }
            map1 = new HashMap();
            list = new ArrayList<>();
            map1.put("renshu", teshurenyuan);
            map1.put("zhanbi", df.format((double) teshurenyuan / bdglPeople.size()));
            list.add(map1);
            map.put("特殊类型", list);
        }
        return AjaxResult.success(map);
    }


    /**
     * 登记落实
     *
     * @return
     */
    @GetMapping("/tongjiluoshi")
    public AjaxResult tongjiluoshi() throws ParseException {
        //获取落实单位
        SysDept sd = new SysDept();
//        sd.setParentId(100l);
//        sd.setIsShow("Y"); //是否展示
        List<SysDept> sysDepts = new ArrayList<>();
        List<SysDept> sysDeptn = deptService.selectDeptList(sd);
        Long parentId = 0l;
        for (SysDept sysDept : sysDeptn) {
            if (!"应急机动发射大队".equals(sysDept.getDeptName()) && !"参谋部".equals(sysDept.getDeptName()) &&
                    !"政治工作部".equals(sysDept.getDeptName()) && !"保障部".equals(sysDept.getDeptName()) &&
                    !"技术室".equals(sysDept.getDeptName()) && !"俱乐部".equals(sysDept.getDeptName()) && !"未分配".equals(sysDept.getDeptName())) {
                sysDepts.add(sysDept);
            }
            if (sysDept.getParentId()==0){
                parentId=sysDept.getDeptId();
            }
        }

        //定义接收值
        Map map = null;
        List list = new ArrayList();
        Map map1 = null;

        //bdgl_daily_inspect 茶铺查岗的表   bdgl_diary 要是日记的表
        //获取要是日记是否登记 值班登记 工作安排 查铺查岗
        for (SysDept sysDept : sysDepts) {
            map = new HashMap();
            map1 = new HashMap();
            Map map2 = new HashMap();
            //获取今天10点的时间
            Date date = new Date();
            String todayTime = sf.format(date);
            todayTime = todayTime + " 06:00:00";

            //获取昨天零点的时候
            Calendar time = Calendar.getInstance();
            time.add(Calendar.DAY_OF_YEAR, -1);
            String nextDateTime = sf.format(time.getTime());
            nextDateTime = nextDateTime + " 10:00:00";

//            //获取当前大队值班的数据
//            List<BdglTeamduty> bdglTeamduties = bdglTeamdutyService.selectBdglTeamdutyListByTime(nextDateTime, todayTime, sysDept.getDeptId());
//            if (bdglTeamduties.size() > 0) {
//                map.put("daduizhiban", "0");
//            } else {
//                map.put("daduizhiban", "1");
//            }

            String timeParameter ="";

            //判断当前时间是否大于6点
            if(date.getTime()>DateUtils.parseDate(todayTime).getTime()){
                timeParameter=sf.format(new Date());
            }else{
                timeParameter=sf.format(time.getTime());
            }

            //查询基层值班数据
            BdglGrassduty bdglGrassduty = new BdglGrassduty();
            bdglGrassduty.setUnitId(sysDept.getDeptId());
            bdglGrassduty.setAttendedTime(timeParameter);
            List<BdglGrassduty> bdglGrassduties = grassdutyService.selectBdglGrassdutyList(bdglGrassduty);
            if (bdglGrassduties.size() > 0) {
                map.put("daduizhiban", "0");
            } else {
                map.put("daduizhiban", "1");
            }
            //获取到要是登记的数据
            BdglDiary bdglDiary = new BdglDiary();
            bdglDiary.setUnitId(Integer.parseInt(sysDept.getDeptId().toString()));
            bdglDiary.setDiaryTime(sf.format(time.getTime()));
            List<BdglDiary> bdglDiaries = bdglDiaryService.selectBdglDiaryList(bdglDiary);
            if (bdglDiaries.size() > 0) {
                map.put("yaoshidengji", "0");
            } else {
                map.put("yaoshidengji", "1");
            }
            Integer id=null;
            for (BdglDiary diary : bdglDiaries) {
                 id = diary.getId();
                break;
            }
            //获取到当前查岗查哨的数据


            /*BdglDailyInspect bdglDailyInspect = new BdglDailyInspect();
            bdglDailyInspect.setDiaryId(id);
            List<BdglDailyInspect> bdglDailyInspects = bdglDailyInspectService.selectBdglDailyInspectList(bdglDailyInspect);
            if (bdglDailyInspects.size() > 0&&id!=null) {
                map.put("chagangchashao", "0");
            } else {
                map.put("chagangchashao", "1");
            }*/
            //营级上周2次 连级一天俩次
            if (sysDept.getParentId()==parentId){
                Map<String, Date> stMap = getStartTimeAndTime();
                int count = bdglDiaryService.selectBdglDailyInspectCountByTime(stMap.get("startTime"), stMap.get("endTime"), sysDept.getDeptId());
                if (count>1) {
                    map.put("chagangchashao", "0");
                } else {
                    map.put("chagangchashao", "1");
                }
            }else {
                Date startTime = getFormatDate(-1);
                Date endTime = getFormatDate(0);
                int count = bdglDiaryService.selectBdglDailyInspectCountByTime(startTime, endTime, sysDept.getDeptId());
                if (count>1) {
                    map.put("chagangchashao", "0");
                } else {
                    map.put("chagangchashao", "1");
                }
            }

            //获取到工作安排的数据
            BdglWeekworkRegister bdglWeekworkRegister = new BdglWeekworkRegister();
//            LocalDate localDate = LocalDate.now().minusDays(1);
//            String qianyitian = localDate + "";
            bdglWeekworkRegister.setTime(DateUtils.parseDate(timeParameter));
            bdglWeekworkRegister.setUnitId1(sysDept.getDeptName());
            List<BdglWeekworkRegister> bdglWeekworkRegisters = bdglWeekworkRegisterService.selectBdglWeekworkRegisterList(bdglWeekworkRegister);
            if (bdglWeekworkRegisters.size() > 0) {
                map.put("gongzuoanpai", "0");
            } else {
                map.put("gongzuoanpai", "1");
            }

            map1.put("unitname", sysDept.getDeptName());
            map1.put("unitvalue", map);
            list.add(map1);
        }
        return AjaxResult.success(list);
    }

    public Date getFormatDate(int day) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, day);//天
        c.set(Calendar.HOUR_OF_DAY, 0);//时
        c.set(Calendar.MINUTE, 0);//分
        c.set(Calendar.SECOND, 0);//秒
        c.set(Calendar.MILLISECOND, 0);//秒
        return c.getTime();
    }

    public Map<String,Date> getStartTimeAndTime(){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);//时
        c.set(Calendar.MINUTE, 0);//分
        c.set(Calendar.SECOND, 0);//秒
        c.set(Calendar.MILLISECOND, 0);//秒

        if (c.get(Calendar.DAY_OF_WEEK)==1){
            c.add(Calendar.DAY_OF_MONTH,-6);
        }else {
            c.set(Calendar.DAY_OF_WEEK,2);
        }
        Map<String,Date> map = new HashMap<>();
        map.put("endTime",c.getTime());

        c.add(Calendar.DAY_OF_MONTH,-7);
        map.put("startTime",c.getTime());
        return map;
    }


    /**
     * 今日值班
     *
     * @return
     */
    @GetMapping("/jinrizhiban")
    public AjaxResult jinrizhiban() {

        List<BdglTeamduty> list = new ArrayList<>();
        BdglTeamduty bdglTeamduty = new BdglTeamduty();
        List<BdglTeamduty> bdglTeamduties = bdglTeamdutyService.selectBdglTeamdutyList(bdglTeamduty);
        if (bdglTeamduties.size() > 0) {
            for (BdglTeamduty teamduty : bdglTeamduties) {
                if (teamduty.getAttendedTime().substring(0, 10).equals(sf.format(new Date()))) {
                    list.add(teamduty);
                }
            }
        }
        if (list.size() > 0) {
            return AjaxResult.success(list.get(0));
        } else {
            return AjaxResult.success("");
        }

    }

    //月统计打印量 已归还 未归还 无需归还
    @GetMapping("/rizongwenyin")
    public AjaxResult rizongwenyin() {


        //获取当月时间
        Map<String, Date> week = getWeek1();
        Date monday = week.get("Monday");
        Date sunday = week.get("sunday");
        Date lastMonday = week.get("lastMonday");
        String format1 = sdf.format(lastMonday);
        String format2 = sdf.format(sunday);
        String format = sfmouth.format(new Date());
        List<Map<String, Object>> lists = printsLogService.selectPrintsLogByMouth(format1,format2);
        Map<String, Object>map=printsLogService.selectCount(format1,format2);
        System.out.println("2222222222222222222222222222222");
       if(map!=null){

           if(map.get("total")==null){
               map.put("total",0);
           }
           if(map.get("returnNumber")==null){
               map.put("returnNumber",0);
           }
           if(map.get("noReturnNumber")==null){
               map.put("noReturnNumber",0);
           }
           if(map.get("weiReturnNumber")==null){
               map.put("weiReturnNumber",0);
           }



       }else{
           map=new HashMap<String, Object>();
           map.put("total",0);
           map.put("returnNumber",0);
           map.put("noReturnNumber",0);
           map.put("weiReturnNumber",0);
       }


        return AjaxResult.success(map);

    }
    private static Map<String,Date> getWeek() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        // 一周第一天为周日，所以此处日+1
        calendar.setWeekDate(calendar.getWeekYear(), calendar.get(Calendar.WEEK_OF_YEAR), 2);
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                0, 0, 0);
        Date time = calendar.getTime();
        calendar.add(Calendar.DATE,-7);
        Date time1 = calendar.getTime();
        //一周第一天为周日，所以此处为下一周第一天
        calendar.setWeekDate(calendar.getWeekYear(), calendar.get(Calendar.WEEK_OF_YEAR)+1, 1);
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                23, 59, 59);
        Date time2 = calendar.getTime();
        calendar.add(Calendar.DATE,+7);
        Date time3 = calendar.getTime();

        HashMap<String, Date> stringDateHashMap = new HashMap<>();
        stringDateHashMap.put("Monday",time);
        stringDateHashMap.put("sunday",time3);
        stringDateHashMap.put("lastMonday",time1);
        stringDateHashMap.put("lastsunday",time2);
        return stringDateHashMap;
    }
    private static Map<String,Date> getWeek1() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        // 一周第一天为周日，所以此处日+1
        calendar.setWeekDate(calendar.getWeekYear(), calendar.get(Calendar.WEEK_OF_YEAR), 2);
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                0, 0, 0);

        Date time = calendar.getTime();
        calendar.add(Calendar.DATE,-7);
        Date time1 = calendar.getTime();
        //一周第一天为周日，所以此处为下一周第一天
        calendar.setWeekDate(calendar.getWeekYear(), calendar.get(Calendar.WEEK_OF_YEAR), 1);
        calendar.set(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                23, 59, 59);
        Date time2 = calendar.getTime();
        calendar.add(Calendar.DATE,+7);
        Date time3 = calendar.getTime();

        HashMap<String, Date> stringDateHashMap = new HashMap<>();
        stringDateHashMap.put("Monday",time);
        stringDateHashMap.put("sunday",time3);
        stringDateHashMap.put("lastMonday",time1);
        stringDateHashMap.put("lastsunday",time2);
        return stringDateHashMap;
    }
    /**
     * 军车管理
     *
     * @return
     */
    @GetMapping("/juncheguanli")
    public AjaxResult juncheguanli() {

        Map<String, Integer> map = new HashMap<>();
        //军车总计
        /** 车辆在位情况1：在位，2：值班，3：出车，4待修 */
        List<BdglThebus> bdglThebuses = bdglThebusService.selectBdglThebusList(new BdglThebus());
        if (bdglThebuses.size() > 0) {
            map.put("zongji", bdglThebuses.size());
        } else {
            map.put("zongji", 0);
        }

        BdglThebus bdglThebus = new BdglThebus();
        bdglThebus.setStatus("1");
        List<BdglThebus> zaiwaithebus = bdglThebusService.selectBdglThebusList(bdglThebus);
        if (zaiwaithebus.size() > 0) {
            map.put("zaiwei", zaiwaithebus.size());
        } else {
            map.put("zaiwei", 0);
        }


        bdglThebus.setStatus("3");
        List<BdglThebus> waichuthebus = bdglThebusService.selectBdglThebusList(bdglThebus);
        if (waichuthebus.size() > 0) {
            map.put("waichu", waichuthebus.size());
        } else {
            map.put("zaiwei", 0);
        }

        return AjaxResult.success(map);
    }

    /**
     * 私家车管理
     *
     * @return
     */
    @GetMapping("/sijiacheguanli")
    public AjaxResult sijiacheguanli() {
        //私家车总数
        List<BdglPrivateCar> bdglPrivateCars = bdglPrivateCarService.selectBdglPrivateCarList(new BdglPrivateCar());

        BdglPrivateCar bp = new BdglPrivateCar();
        //  bp.set
        return AjaxResult.success("aaa");
    }

    /**
     * 检查督导统计
     */
    @GetMapping("/unitrank")
    public AjaxResult unitRank() {
        //获取二级部门
        List<SysDept> sysDepts = twoJiDept();

        //返回值定义
        List<String> namelist = new ArrayList<>();
        List<String> sorcelist = new ArrayList<>();
        Map<String, List<String>> map = new HashMap<>();
        for (SysDept sysDept : sysDepts) {

            Long deptId = sysDept.getDeptId();
            List<BdglRegular> bdglRegulars = bdglRegularService.selectDeptById(deptId);
            BigDecimal addscore = BigDecimal.valueOf(0); //定义加分
            BigDecimal subscore = BigDecimal.valueOf(0); //定义减分
            for (BdglRegular regular : bdglRegulars) {
                //计算减分数据
                if ("0".equals(regular.getCaoZuoType())) {
                    subscore = subscore.add(regular.getScore());
                }
                //计算加分数据
                if ("1".equals(regular.getCaoZuoType())) {
                    addscore = addscore.add(regular.getScore());
                }
            }
            namelist.add(sysDept.getDeptName());
            sorcelist.add(sysDept.getScore() + "");
        }
        map.put("name", namelist);
        map.put("score", sorcelist);
        return AjaxResult.success(map);
    }

    /**
     * 获取到二级部门
     *
     * @return
     */
    public List<SysDept> twoJiDept() {
        List<SysDept> returnDept = new ArrayList<>();
        //获取二级部门
        SysDept sd = new SysDept();
        sd.setParentId(Long.valueOf(100));
        List<SysDept> sysDepts = deptService.selectDeptList(sd);
        for (SysDept sysDept : sysDepts) {
            if ("Y".equals(sysDept.getIsShow())) {
                returnDept.add(sysDept);
            }
        }
        return returnDept;
    }

    /**
     * 获取防疫品列表
     */
    @GetMapping("/antiepidemicArchivesList")
    public AjaxResult antiepidemicArchivesList() {
        List<BdglAntiepidemicArchives> bdglAntiepidemicArchives = antiepidemicArchivesService.selectBdglAntiepidemicArchivesList(new BdglAntiepidemicArchives());
        return AjaxResult.success(bdglAntiepidemicArchives);
    }

    /**
     * 营具使用情况
     */
    @GetMapping("/getantiepidemic")
    public AjaxResult getantiepidemic() {
        int total = 0;
        int putout = 0;
        HashMap<String, Object> map = new HashMap<>();
        List<MaterialArchives> bdglAntiepidemicArchives = materialArchivesService.selectMaterialArchivesList(new MaterialArchives());
        if (bdglAntiepidemicArchives.size() > 0) {
            for (MaterialArchives bdglAntiepidemicArchive : bdglAntiepidemicArchives) {
                total += bdglAntiepidemicArchive.getMaterialTotal();
                putout += bdglAntiepidemicArchive.getInUseTotal();
            }
            map.put("total", total);
            map.put("putout", putout);
        } else {
            map.put("total", total);
            map.put("putout", putout);
        }
        return AjaxResult.success(map);

    }

    /**
     * 统计请假人数
     */
    @GetMapping("/getLeavlNumber")
    public AjaxResult getLeavlNumber() {
        int chaoShi = 0;
        int weiGui = 0; // 查人员表中不在位的人数
        int yingGuiDui = 0;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        HashMap<String, Object> map = new HashMap<>();

        List<BdglPeople> bdglPeople = bdglPeopleService.selectBdglPeopleList(new BdglPeople());
        if (bdglPeople.size() > 0) {
            for (BdglPeople bdglPerson : bdglPeople) {
                if (!bdglPerson.getReign().equals(0)) {
                    weiGui += 1;
                }
            }
            map.put("weiGui", weiGui);
        } else {
            weiGui = 0;
        }

        List<BdglLeave> bdglLeaves = leaveService.selectBdglLeaveList(new BdglLeave());
        if (bdglLeaves.size() > 0) {
            for (BdglLeave bdglLeaf : bdglLeaves) {
                if (bdglLeaf.getRejoin().equals(0) && bdglLeaf.getActualreturntime() != null) {
                    String format = simpleDateFormat.format(bdglLeaf.getActualreturntime());
                    Date date = DateUtils.parseDate(format);
                    if (date.getTime() > bdglLeaf.getEndTime().getTime()) {
                        chaoShi++;
                    }
                }
                String format = simpleDateFormat.format(new Date());
                if (bdglLeaf.getEndTime().getTime() == DateUtils.parseDate(format).getTime()) {
                    yingGuiDui++;
                }
            }
            map.put("chaoShi", chaoShi);
            map.put("yingGuiDui", yingGuiDui);
        } else {
            map.put("chaoShi", chaoShi);
            map.put("weiGui", weiGui);
            map.put("yingGuiDui", yingGuiDui);
        }
        return AjaxResult.success(map);
    }

    /**
     * 公寓房统计 营房统计
     */
    @GetMapping("/getBarracks")
    public AjaxResult get() {
        ArrayList<Integer> integers = new ArrayList<>();
        int size = bdglBarracksService.selectBdglBarracksList(new BdglBarracks()).size();
        int size1 = bdglFlatService.selectBdglFlatList(new BdglFlat()).size();
        integers.add(size);
        integers.add(size1);
        return AjaxResult.success(integers);
    }

}
