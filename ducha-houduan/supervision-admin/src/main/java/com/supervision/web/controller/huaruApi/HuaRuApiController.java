package com.supervision.web.controller.huaruApi;

import com.alibaba.fastjson.JSON;
import com.supervision.combatduty.domain.BdglBaseduty;
import com.supervision.combatduty.domain.BdglMilitaryduty;
import com.supervision.combatduty.domain.BdglTeamduty;
import com.supervision.combatduty.domain.BdglTeamdutyFu;
import com.supervision.combatduty.service.IBdglBasedutyService;
import com.supervision.combatduty.service.IBdglMilitarydutyService;
import com.supervision.combatduty.service.impl.BdglTeamdutyFuServiceImpl;
import com.supervision.combatduty.service.impl.BdglTeamdutyServiceImpl;
import com.supervision.common.core.domain.AjaxResult;
import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.common.core.domain.entity.SysDictData;
import com.supervision.grassrootsregistration.domain.BdglDailyInspect;
import com.supervision.grassrootsregistration.domain.BdglDiary;
import com.supervision.grassrootsregistration.domain.BdglDiaryWeekworkRegister;
import com.supervision.grassrootsregistration.domain.BdglEven;
import com.supervision.common.utils.DateUtils;
import com.supervision.grassrootsregistration.domain.*;
import com.supervision.grassrootsregistration.service.IBdglDiaryService;
import com.supervision.grassrootsregistration.service.IBdglEvenService;
import com.supervision.grassrootsregistration.service.impl.BdglMeetingServiceImpl;
import com.supervision.grassrootsregistration.service.impl.BdglPartyServiceImpl;
import com.supervision.grassrootsregistration.service.impl.BdglSoldierServiceImpl;
import com.supervision.huaruApi.*;
import com.supervision.militaryvehicleManagement.domain.BdglPrivateCar;
import com.supervision.militaryvehicleManagement.domain.BdglThebus;
import com.supervision.militaryvehicleManagement.service.IBdglPrivateCarService;
import com.supervision.militaryvehicleManagement.service.IBdglThebusService;
import com.supervision.peopleChuRu.domain.BdglLeave;
import com.supervision.peopleChuRu.domain.BdglLeaveOne;
import com.supervision.peopleChuRu.service.IBdglLeaveOneService;
import com.supervision.peopleChuRu.service.IBdglLeaveService;
import com.supervision.peopleManage.domain.BdglPeople;
import com.supervision.peopleManage.service.IBdglPeopleService;
import com.supervision.system.service.ISysDeptService;
import com.supervision.system.service.ISysDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

@RestController
@RequestMapping("/api/huaRuApi")
public class HuaRuApiController {


    @Autowired
    private ISysDeptService sysDeptService;

    @Autowired
    private IBdglPeopleService bdglPeopleService;

    @Autowired
    private IBdglLeaveOneService bdglLeaveOneService;

    @Autowired
    private IBdglLeaveService bdglLeaveService;

    @Autowired
    private ISysDictDataService dictDataService;

    @Autowired
    private IBdglBasedutyService bdglBasedutyService;

    @Autowired
    private IBdglThebusService bdglThebusService;

    @Autowired
    private IBdglEvenService bdglEvenService;

    @Autowired
    private IBdglMilitarydutyService bdglMilitarydutyService;

    @Autowired
    private IBdglPrivateCarService bdglPrivateCarService;

    @Autowired
    private IBdglDiaryService bdglDiaryService;

    /**
     * 1、人员基础信息接口
     * @return
     */
    @PostMapping("/ryxxList")
    public AjaxResult ryxxList(){
        return getAjaxResult();
    }

    /**
     * 2、人员动态接口 (记录人员在位情况，接口只查询当日的数据)
     * @return
     */
    @PostMapping("/rydtxxList")
    public AjaxResult rydtxxList(){
        BdglPeople bdglPeople = new BdglPeople();
        bdglPeople.setReign(0);
        return getAjaxResult(bdglPeople);
    }

    private AjaxResult getAjaxResult(BdglPeople bdglPeople) {
        List<BdglPeople> bdglPeoples = bdglPeopleService.selectPowerPeoleListsss(bdglPeople);

        SysDictData sysDictData = new SysDictData();
        List<SysDictData> sysDictDatas = dictDataService.selectDictDataList(sysDictData);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        List<RyxxList> ryxxLists = new ArrayList<>();
        for (BdglPeople people : bdglPeoples) {
            RyxxList ryxxList = new RyxxList();
            for (SysDictData dictData : sysDictDatas) {
                if(dictData.getDictType()!=null){
                    if (people.getMarryList() != null && dictData.getDictType().equals("marryList") && dictData.getDictValue().equals(people.getMarryList())){
                        ryxxList.setHyzk(dictData.getDictLabel());
                    }
                    if (people.getRankId() != null && dictData.getDictType().equals("sys_user_rank") && Integer.parseInt(dictData.getDictValue()) == people.getRankId()){
                        ryxxList.setJx(dictData.getDictLabel());
                    }
                    if(people.getClassId()!=null){
                        if (dictData.getDictType().equals("personnel_type") && Integer.parseInt(dictData.getDictValue()) == people.getClassId()){
                            ryxxList.setRyfl(dictData.getDictLabel());
                        }
                    }
                    if (people.getEducationId() != null && dictData.getDictType().equals("Degree_level") && Integer.parseInt(dictData.getDictValue()) == people.getEducationId()){
                        ryxxList.setXl(dictData.getDictLabel());
                    }
                    if (people.getPartyId() != null && dictData.getDictType().equals("Caucus") && Integer.parseInt(dictData.getDictValue()) == people.getPartyId()){
                        ryxxList.setZzmm(dictData.getDictLabel());
                    }
                }
            }
            ryxxList.setDwmc("应急发射大队");
            ryxxList.setIsBase(0);
            ryxxList.setIsBz(1);
            ryxxList.setLydw(people.getDeptName());
            ryxxList.setSfzh(people.getIdcard());
            ryxxList.setXm(people.getName());
            ryxxList.setZwrq(sdf.format(new Date()));
            ryxxList.setZwzt(people.getReign() == 0 ? "在位" : "不在位");
            ryxxLists.add(ryxxList);
        }

        Map<String, List<RyxxList>> stringBdglPeopleMap = new HashMap<>();

        stringBdglPeopleMap.put("应急发射大队",ryxxLists);

        return AjaxResult.success(stringBdglPeopleMap);
    }

    /**
     * 3、人员请假接口 (包含多日请假和单日请假数据)
     * @return
     */
    @PostMapping("/ryqjxxList")
    public AjaxResult ryqjxxList() throws ParseException {

        Map<String, List<RyqjxxList>> stringBdglPeopleMap = new HashMap<>();

        /*BdglLeaveOne bdglLeaveOne = new BdglLeaveOne();
        List<BdglLeaveOne> bdglLeaveOnes = bdglLeaveOneService.selectBdglLeaveOneList(bdglLeaveOne);*/

        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictType("people_state");
        List<SysDictData> sysDictDatas = dictDataService.selectDictDataList(sysDictData);

        List<RyqjxxList> ryqjxxLists = new ArrayList<>();

        /*for (BdglLeaveOne leaveOne : bdglLeaveOnes) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            RyqjxxList ryqjxxList = new RyqjxxList();
            ryqjxxList.setDwmc("大队");
            ryqjxxList.setIsBase(0);
            ryqjxxList.setIsBz(1);
            ryqjxxList.setCreateMonth(sdf.format(leaveOne.getStartTime()));
            ryqjxxList.setDrdr("单日");
            ryqjxxList.setXm(leaveOne.getPeopleName());
            ryqjxxList.setGdsj(leaveOne.getDepartureTime());
            ryqjxxList.setKssj(leaveOne.getDepartureTime());
            ryqjxxList.setJssj(leaveOne.getReturnTime());
            ryqjxxList.setLydw(leaveOne.getUnitName());
            for (SysDictData dictData : sysDictDatas) {
                if (dictData.getDictValue().equals(leaveOne.getLeaveType())){
                    ryqjxxList.setQjlx(dictData.getDictLabel());
                }
            }
            ryqjxxLists.add(ryqjxxList);
        }*/
        String s = LocalDate.now().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        BdglLeave bdglLeave = new BdglLeave();
        List<BdglLeave> bdglLeaves = bdglLeaveService.selectBdglLeaveListsss(bdglLeave);
        if (bdglLeaves.size() > 0) {
            for (BdglLeave bdglLeaf : bdglLeaves) {
                    RyqjxxList ryqjxxList = new RyqjxxList();
                    ryqjxxList.setDwmc("应急发射大队");
                    ryqjxxList.setIsBase(0);
                    ryqjxxList.setIsBz(1);
                    ryqjxxList.setCreateMonth(sdf.format(bdglLeaf.getStartTime()));
                    ryqjxxList.setDrdr("多日");
                    ryqjxxList.setXm(bdglLeaf.getPeopleName());
                    ryqjxxList.setGdsj(bdglLeaf.getDepartureTime());
                    ryqjxxList.setKssj(bdglLeaf.getDepartureTime());
                    ryqjxxList.setJssj(bdglLeaf.getReturnTime());
                    ryqjxxList.setLydw(bdglLeaf.getUnitName());
                    for (SysDictData dictData : sysDictDatas) {
                        if (dictData.getDictValue().equals(bdglLeaf.getLeaveType())){
                            ryqjxxList.setQjlx(dictData.getDictLabel());
                        }
                    }
                    ryqjxxLists.add(ryqjxxList);
            }
        }
        
        

        stringBdglPeopleMap.put("应急发射大队",ryqjxxLists);

        return AjaxResult.success(stringBdglPeopleMap);
    }

    /**
     * 4、车辆动态接口 (记录车辆在位数据，接口只查询当日的数据)
     * @return
     */
    @PostMapping("/cldtxxList")
    public AjaxResult cldtxxList(){

        HashMap<String, List<CldtxxList>> stringListHashMap = new HashMap<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        BdglThebus bdglThebus = new BdglThebus();
        bdglThebus.setStatus("1");
        List<BdglThebus> bdglThebuses = bdglThebusService.selectBdglThebusList(bdglThebus);

        List<CldtxxList> cldtxxLists = new ArrayList<>();

        for (BdglThebus thebus : bdglThebuses) {

            CldtxxList cldtxxList = new CldtxxList();
            cldtxxList.setDwmc("应急发射大队");
            cldtxxList.setIsBase(0);
            cldtxxList.setIsBz(1);
            cldtxxList.setCllx("公车");
            cldtxxList.setJcph(thebus.getVehiclenumber());
            cldtxxList.setJsy(thebus.getPeopleNames());
            cldtxxList.setZwsj(sdf.format(new Date()));
            cldtxxList.setZwzt(thebus.getStatus().equals("1") ? "在位" : "不在位");
            cldtxxLists.add(cldtxxList);
        }
        stringListHashMap.put("应急发射大队",cldtxxLists);
        return AjaxResult.success(stringListHashMap);
    }

    /**
     * 5、车辆信息接口
     * @return
     */
    @PostMapping("/clxxList")
    public AjaxResult clxxList(){

        HashMap<String, List<CldtxxList>> stringListHashMap = new HashMap<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        BdglThebus bdglThebus = new BdglThebus();
        List<BdglThebus> bdglThebuses = bdglThebusService.selectBdglThebusList(bdglThebus);

        List<CldtxxList> cldtxxLists = new ArrayList<>();

        SysDictData sysDictData = new SysDictData();
        List<SysDictData> sysDictDatas = dictDataService.selectDictDataList(sysDictData);

        for (BdglThebus thebus : bdglThebuses) {

            CldtxxList cldtxxList = new CldtxxList();
            cldtxxList.setDwmc("应急发射大队");
            cldtxxList.setIsBase(0);
            cldtxxList.setIsBz(1);
            cldtxxList.setCllx("公车");
            cldtxxList.setJcph(thebus.getVehiclenumber());
            cldtxxList.setJsy(thebus.getPeopleNames());
            cldtxxList.setZwsj(sdf.format(new Date()));
            cldtxxList.setCph(thebus.getVehiclenumber());
            for (SysDictData dictData : sysDictDatas) {
                if (thebus.getStatus() != null && dictData.getDictType().equals("military") && dictData.getDictValue().equals(thebus.getStatus())){
                    cldtxxList.setClzt(dictData.getDictLabel());
                }
                if (thebus.getChelaingleibie() != null && dictData.getDictType().equals("brand") && dictData.getDictValue().equals(thebus.getChelaingleibie())){
                    cldtxxList.setClxh(dictData.getDictLabel());
                }
            }
            cldtxxList.setCsys(thebus.getVehiclecolor());
            cldtxxList.setLydw(thebus.getUnitName());
            cldtxxList.setZrr(thebus.getPeopleNames());
            cldtxxList.setCx(thebus.getChelaingleibie());
            cldtxxLists.add(cldtxxList);
        }

        BdglPrivateCar bdglPrivateCar = new BdglPrivateCar();
        List<BdglPrivateCar> bdglPrivateCars = bdglPrivateCarService.selectBdglPrivateCarList(bdglPrivateCar);
        for (BdglPrivateCar privateCar : bdglPrivateCars) {
            CldtxxList cldtxxList = new CldtxxList();
            cldtxxList.setDwmc("应急发射大队");
            cldtxxList.setIsBase(0);
            cldtxxList.setIsBz(1);
            cldtxxList.setCllx("私家车");
            cldtxxList.setZwsj(sdf.format(new Date()));
            cldtxxList.setCph(privateCar.getVehiclenumber());
            for (SysDictData dictData : sysDictDatas) {
                if (privateCar.getState() != null && dictData.getDictType().equals("military") && dictData.getDictValue().equals(privateCar.getState())){
                    cldtxxList.setClzt(dictData.getDictLabel());
                }
                if (privateCar.getVehiclecategory() != null && dictData.getDictType().equals("brand") && dictData.getDictValue().equals(privateCar.getVehiclecategory())){
                    cldtxxList.setClxh(dictData.getDictLabel());
                }
            }
            cldtxxList.setCsys(privateCar.getVehiclecolor());
            cldtxxList.setLydw(privateCar.getUnitName());
            cldtxxList.setZrr(privateCar.getPeopleName());
            cldtxxList.setCx(privateCar.getVehiclemodel());
            cldtxxLists.add(cldtxxList);
        }
        stringListHashMap.put("应急发射大队",cldtxxLists);
        return AjaxResult.success(stringListHashMap);
    }

    @PostMapping("/getLdysrjList")
    public AjaxResult getLdysrjList() throws ParseException {

        List<GetLdysrjList> objects = new ArrayList<>();

        HashMap<String, List<GetLdysrjList>> objectObjectHashMap = new HashMap<>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        LocalDate localDate = LocalDate.now().minusDays(1);
        LocalDate localDate1 = localDate.with(TemporalAdjusters.previous(DayOfWeek.SUNDAY)).plusDays(1);
        LocalDate localDate2 = localDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).minusDays(1);
        Date from = Date.from(localDate1.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        Date from2 = Date.from(localDate2.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
        String date = localDate.toString();
        SysDept sysDept = new SysDept();
        List<SysDept> sysDepts = sysDeptService.selectAllDeptList(sysDept);
        BdglDiary bdglDiary = new BdglDiary();
        for (SysDept dept : sysDepts) {
            if (dept.getAncestors().split(",").length == 3){
                bdglDiary.setUnitId(dept.getDeptId().intValue());
                bdglDiary.setDiaryTime(date);

                List<BdglDiary> bdglDiaries = bdglDiaryService.selectBdglDiaryList(bdglDiary);
                GetLdysrjList getLdysrjList = new GetLdysrjList();
                if (bdglDiaries.isEmpty()){
                    getLdysrjList.setNullData(true);
                    objects.add(getLdysrjList);
                }else {
                    for (BdglDiary diary : bdglDiaries) {
                        if (isEffectiveDate(simpleDateFormat.parse(diary.getDiaryTime()), from, from2) && diary.getInternal() != null){
                            getLdysrjList.setNwws("0");
                        }if (diary.getWeekwork() == null){
                            getLdysrjList.setSfycj("否");
                        }else {
                            getLdysrjList.setIsBase(0);
                            getLdysrjList.setIsBz(1);
                            getLdysrjList.setByString("大队");
                            getLdysrjList.setDwjb("3");
                            getLdysrjList.setDwmc(dept.getDeptName());
                            getLdysrjList.setDwqc(dept.getDeptName());
                            getLdysrjList.setNullData(false);
                            getLdysrjList.setLdysrjrq(date);
                            getLdysrjList.setLastzhou(from + "至" + from2);
                            getLdysrjList.setNwws("1");
                            getLdysrjList.setSfycj("是");
                        }
                        objects.add(getLdysrjList);
                    }
                }
            }
        }
        objectObjectHashMap.put("大队",objects);

        return AjaxResult.success(objectObjectHashMap);
    }
    
    /**
     * 要事从记公共接口
     * @return
     * @throws ParseException
     */
    @PostMapping("/getLdysrjLists")
    public AjaxResult getLdysrjLists() throws ParseException {

        List<GetLdysrjList> objects = new ArrayList<>();

        HashMap<String, List<GetLdysrjList>> objectObjectHashMap = new HashMap<>();

        LocalDate localDate = LocalDate.now().minusDays(1);
        String date = localDate.toString();
        SysDept sysDept = new SysDept();
        List<SysDept> sysDepts = sysDeptService.selectAllDeptList(sysDept);
        BdglDiary bdglDiary = new BdglDiary();
        for (SysDept dept : sysDepts) {
            if (dept.getAncestors().split(",").length == 3){
                bdglDiary.setUnitId(dept.getDeptId().intValue());
                bdglDiary.setDiaryTime(date);

                List<BdglDiary> bdglDiaries = bdglDiaryService.selectBdglDiaryList(bdglDiary);
                if (bdglDiaries.size() > 0) {
                    GetLdysrjList getLdysrjList = new GetLdysrjList();
                    getLdysrjList.setIsBase(0);
                    getLdysrjList.setIsBz(0);
                    getLdysrjList.setByString("应急发射大队");
                    getLdysrjList.setDwjb("3");
                    getLdysrjList.setDwmc(dept.getDeptName());
                    sysDept.setDeptId(dept.getParentId());
                    List<SysDept> sysDepts1 = sysDeptService.selectAllDeptList(sysDept);
                    getLdysrjList.setDwqc(sysDepts1.get(0).getDeptName()+"-"+dept.getDeptName());
                    getLdysrjList.setLdysrjrq(localDate.toString());
                    for (BdglDiary diary : bdglDiaries) {
                        BdglDiary bdglDiary1 = bdglDiaryService.selectBdglDiaryxiangqing(diary.getId());
                        if (bdglDiary1.getInspect().size() > 0) {
                            BdglDailyInspect bdglInspect = bdglDiary1.getInspect().get(0);
                            if (bdglInspect.getExamineDate() != null && bdglInspect.getPeopleName() != null && bdglInspect.getDialogue() != null) {
                                getLdysrjList.setCpcs("检查时间"+":"+date+" "+bdglInspect.getExamineDate()+"，"+
                                        "检查人"+":"+bdglInspect.getPeopleName()+"，"+"检查情况"+":"+bdglInspect.getDialogue());
                            }
                        }
                        List<BdglDiaryWeekworkRegister> bdglDiaryWeekworkRegisterList = bdglDiary1.getBdglDiaryWeekworkRegisterList();
                        if (bdglDiaryWeekworkRegisterList.size() >= 4){
                            getLdysrjList.setSfycj("已填写");
                        } else {
                            getLdysrjList.setSfycj("未填写");
                        }
                        String s = "";
                        if (bdglDiaryWeekworkRegisterList.size() > 0){
                            for (BdglDiaryWeekworkRegister p : bdglDiaryWeekworkRegisterList) {
                                if (p.getDateline().equals("早晨")){
                                    s +="早晨" + "：" + p.getContents() + "；";
                                }
                                if (p.getDateline().equals("上午")){
                                    s +="上午" + "：" + p.getContents() + "；";
                                }
                                if (p.getDateline().equals("下午")){
                                    s +="下午" + "：" + p.getContents() + "；";
                                }
                                if (p.getDateline().equals("晚上")){
                                    s +="晚上" + "：" + p.getContents();
                                }
                            }
                            getLdysrjList.setYrshzd(s);
                        }
                        if (bdglDiary1.getInternal() != null) {
                            getLdysrjList.setNwws("0");
                        } else {
                            getLdysrjList.setNwws("1");
                        }
                        getLdysrjList.setNullData(false);
                        objects.add(getLdysrjList);
                    }
                }
            }
        }
        objectObjectHashMap.put("应急发射大队",objects);

        return AjaxResult.success(objectObjectHashMap);
    }


    /**
     * 判断当前时间是否在时间范围内
     *
     * @param nowTime
     * @param startTime
     * @param endTime
     * @return boolean
     * @author zxzhang
     * @date 2020/3/16
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 8、会议记录接口 （只取当日的数据）
     * @return
     */
//    @PostMapping("/getHyjlList")
//    public AjaxResult getHyjlList(){
//
//        HashMap<String, List<GetHyjlList>> stringListHashMap = new HashMap<>();
//
//        BdglEven bdglEven = new BdglEven();
//        List<BdglEven> list = bdglEvenService.selectBdglEvenLists(bdglEven);
//
//        List<GetHyjlList> getHyjlLists = new ArrayList<>();
//
//        for (BdglEven even : list) {
//            GetHyjlList getHyjlList = new GetHyjlList();
//            getHyjlList.setDwmc("大队");
//            getHyjlList.setIsBase(0);
//            getHyjlList.setIsBz(1);
//            getHyjlList.setLydw(even.getUnitName());
//            getHyjlList.setHyzhuti(even.getTitle());
//            getHyjlList.setHyjy(even.getContents());
//            getHyjlList.setHyzt(even.getConvokeTime().compareTo(new Date()) > 0 ? "未开始" : "以开始");
//            getHyjlLists.add(getHyjlList);
//        }
//
//        stringListHashMap.put("大队",getHyjlLists);
//
//        return AjaxResult.success(stringListHashMap);
//    }

    /**
     * 15、战备值班接口
     * @return
     */
//    @PostMapping("/getZbzbList")
//    public AjaxResult getZbzbList(){
//
//        HashMap<String, GetZbzbList> stringListHashMap = new HashMap<>();
//
//        BdglBaseduty bdglBaseduty = new BdglBaseduty();
//        List<BdglBaseduty> list = bdglBasedutyService.selectBdglBasedutyList(bdglBaseduty);
//
//        BdglMilitaryduty bdglMilitaryduty = new BdglMilitaryduty();
//        List<BdglMilitaryduty> bdglMilitaryduties = bdglMilitarydutyService.selectBdglMilitarydutyList(bdglMilitaryduty);
//
//        List<ZbzbDate> zbzbDates = new ArrayList<>();
//
//        for (BdglBaseduty baseduty : list) {
//            ZbzbDate zbzbDate = new ZbzbDate();
//            for (BdglMilitaryduty militaryduty : bdglMilitaryduties) {
//                if (militaryduty.getAttendedTime().equals(baseduty.getAttendedTime())){
//                    zbzbDate.setZbcl(militaryduty.getNumberPlateName());
//                }
//            }
//            zbzbDate.setDwmc("大队");
//            zbzbDate.setIsBase(0);
//            zbzbDate.setIsBz(1);
//            zbzbDate.setZbdh(baseduty.getChiefDutyPhone());
//            zbzbDate.setZbfd(baseduty.getDetachmentoneName());
//            zbzbDate.setZbsz(baseduty.getChiefDutyName());
//            zbzbDate.setZbydh(baseduty.getDutyOffierPhone());
//            zbzbDate.setZbyxm(baseduty.getDutyOffierName());
//            zbzbDates.add(zbzbDate);
//        }
//
//        String s = JSON.toJSON(zbzbDates).toString();
//
//        GetZbzbList getZbzbList = new GetZbzbList();
//        getZbzbList.setData(s);
//        getZbzbList.setDwmc("大队");
//        getZbzbList.setIsBase(0);
//        getZbzbList.setIsBz(1);
//
//        stringListHashMap.put("大队",getZbzbList);
//        return AjaxResult.success(stringListHashMap);
//    }

    private AjaxResult getAjaxResult() {
        BdglPeople bdglPeople = new BdglPeople();
        return getAjaxResult(bdglPeople);
    }
    @Autowired
    private BdglMeetingServiceImpl meetingService;

    @Autowired
    private BdglPartyServiceImpl bdglPartyService;

    @Autowired
    BdglSoldierServiceImpl soldierService;
    @PostMapping("/getHyjlList")
    public AjaxResult getHyjlList(){
        BdglMeeting bdglMeeting = new BdglMeeting();
        Date nowDate = DateUtils.getNowDate();
        String s = com.supervision.common.DateUtils.parseDateToStr("yyyy-MM-dd",nowDate);
        Date date = com.supervision.common.DateUtils.parseDate(s);
        bdglMeeting.setConvokeTime(date);
        List<BdglMeeting> bdglMeetings = meetingService.selectBdglMeetingList(bdglMeeting);
        ArrayList<Map<String, Object>> maps = new ArrayList<>();
        for (BdglMeeting meeting : bdglMeetings) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("dwmc","应急发射大队");
            map.put("isBase",0);
            map.put("isBz",1);
            map.put("lydw",meeting.getDeptName());
            map.put("hyzhuti",meeting.getTitle());
            map.put("hyjy",null);
            map.put("hyzq",null);
            map.put("hylx","团支部会议");
            map.put("hyzt","已开");
            map.put("hyrq",meeting.getConvokeTime());
            maps.add(map);
        }
        BdglParty bdglParty = new BdglParty();
        bdglParty.setConvokeTime(date);
        List<BdglParty> bdglParties = bdglPartyService.selectBdglPartyList(bdglParty);
        for (BdglParty party : bdglParties) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("dwmc","应急发射大队");
            map.put("isBase",0);
            map.put("isBz",1);
            map.put("lydw",party.getUnitName());
            map.put("hyzhuti",party.getTitle());
            map.put("hyjy",null);
            map.put("hyzq",null);
            map.put("hylx","党支部会议");
            map.put("hyzt","已开");
            map.put("hyrq",party.getConvokeTime());
            maps.add(map);
        }
        BdglSoldier bdglSoldier = new BdglSoldier();
        bdglSoldier.setConvokeTime(date);
        List<BdglSoldier> bdglSoldiers1 = soldierService.selectBdglSoldierList(bdglSoldier);
        for (BdglSoldier soldier : bdglSoldiers1) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("dwmc","应急发射大队");
            map.put("isBase",0);
            map.put("isBz",1);
            map.put("lydw",soldier.getDeptName());
            map.put("hyzhuti",soldier.getTitle());
            map.put("hyjy",null);
            map.put("hyzq",null);
            map.put("hylx","军人委员会会议");
            map.put("hyzt","已开");
            map.put("hyrq",soldier.getConvokeTime());
            maps.add(map);
        }
        return AjaxResult.success(maps);
    }

    @Autowired
    private BdglTeamdutyServiceImpl teamdutyFuService;
    @Autowired
    private BdglTeamdutyFuServiceImpl teamdutyFu1Service;
    @PostMapping("/getZbzbList")
    public Object getZbzbList(){
        Date nowDate = DateUtils.getNowDate();
        String s = com.supervision.common.DateUtils.parseDateToStr("yyyy-MM-dd",nowDate);
        BdglTeamduty bdglTeamduty = new BdglTeamduty();
        bdglTeamduty.setAttendedTime(s);
        List<BdglTeamduty> bdglTeamduties = teamdutyFuService.selectBdglTeamdutyList(bdglTeamduty);
        HashMap<String, Object> map = new HashMap<>();
        map.put("dwmc","应急发射大队");
        map.put("isBase",0);
        map.put("isBz",1);
        for (BdglTeamduty teamduty : bdglTeamduties) {
            HashMap<String, Object> map1 = new HashMap<>();
            map1.put("dwmc","应急发射大队");
            map1.put("isBase",0);
            map1.put("isBz",1);
            map1.put("zbcl",null);
            map1.put("zbdh",null);
            BdglTeamdutyFu bdglTeamdutyFu = new BdglTeamdutyFu();
            bdglTeamdutyFu.setBdglTeamdutyId(teamduty.getId());
            List<BdglTeamdutyFu> bdglTeamdutyFus = teamdutyFu1Service.selectBdglTeamdutyFuList(bdglTeamdutyFu);
            String deptName="";
            for (BdglTeamdutyFu teamdutyFus : bdglTeamdutyFus) {
                SysDept sysDept = sysDeptService.selectDeptById(teamdutyFus.getDetachmentId());
                deptName+=sysDept.getDeptName()+",";
            }
            map1.put("zbfd",deptName);
            map1.put("zbkssj",teamduty.getAttendedTime());
            map1.put("zbjssj",null);
            map1.put("zbsz",teamduty.getChiefDutyName());
            map1.put("zbyxm",teamduty.getCombatDutyName());
            map1.put("zbydh",teamduty.getCombatDutyPhone());
            BdglPeople bdglPeople = new BdglPeople();
            bdglPeople.setName(teamduty.getCombatDutyName());
            List<BdglPeople> people=null;
            if(teamduty.getCombatDutyName()!=null||teamduty.getCombatDutyPhone()!=null){
                bdglPeople.setPhone(teamduty.getCombatDutyPhone());
                people = bdglPeopleService.selectBdglPeopleList(bdglPeople);
            }
            if(people!=null&&people.size()>0){
                map1.put("zhytx",people.get(0).getAvatar());
            }else{

                map1.put("zhytx",null);
            }
            BdglPeople bdglPeople1 = new BdglPeople();
            bdglPeople1.setName(teamduty.getChiefDutyName());
            List<BdglPeople> people1=null;
            if(teamduty.getChiefDutyPhone()!=null||teamduty.getChiefDutyName()!=null){
                bdglPeople1.setPhone(teamduty.getChiefDutyPhone());
                people1 = bdglPeopleService.selectBdglPeopleList(bdglPeople1);
            }
            if(people1!=null&&people1.size()>0){
                map1.put("zhsztx",people1.get(0).getAvatar());
            }else {

                map1.put("zhsztx",null );
            }
            map.put("date",map1);
        }
        if(bdglTeamduties==null&&bdglTeamduties.size()>0){
           return null;
        }
        return map;
    }

}
