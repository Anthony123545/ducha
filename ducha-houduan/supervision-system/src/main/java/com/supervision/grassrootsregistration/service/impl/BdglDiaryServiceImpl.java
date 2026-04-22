package com.supervision.grassrootsregistration.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.common.core.domain.entity.SysDictData;
import com.supervision.common.utils.DateUtils;
import com.supervision.common.utils.StringUtils;
import com.supervision.grassrootsregistration.domain.*;
import com.supervision.grassrootsregistration.domain.vo.BdglDiaryVo;
import com.supervision.grassrootsregistration.mapper.*;
import com.supervision.grassrootsregistration.resultVo.BdglWeekworkRegisterVo;
import com.supervision.peopleChuRu.domain.BdglLeave;
import com.supervision.peopleChuRu.domain.BdglLeaveOne;
import com.supervision.peopleChuRu.mapper.BdglLeaveMapper;
import com.supervision.peopleChuRu.mapper.BdglLeaveOneMapper;
import com.supervision.peopleManage.domain.BdglPeople;
import com.supervision.peopleManage.mapper.BdglPeopleMapper;
import com.supervision.system.mapper.SysDeptMapper;
import com.supervision.system.service.ISysDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.grassrootsregistration.service.IBdglDiaryService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 要事日记Service业务层处理
 *
 * @author supervision
 * @date 2022-02-26
 */
@Transactional
@Service
public class BdglDiaryServiceImpl implements IBdglDiaryService {
    @Autowired
    private BdglDiaryMapper bdglDiaryMapper;
    @Autowired
    private BdglDiaryKinsfolkMapper bdglDiaryKinsfolkMapper;

    @Autowired
    private BdglDailyInspectMapper bdglDailyInspectMapper;

    @Autowired
    private BdglWeekworkMapper weekworkMapper;

    @Autowired
    private BdglWeekworkRegisterMapper weekworkRegisterMapper;
    @Autowired
    private BdglLeaveMapper bdglLeaveMapper;
    @Autowired
    private SysDeptMapper deptMapper;
    @Autowired
    private BdglPeopleMapper peopleMapper;

    @Resource
    private BdglDiaryLeaveMapper bdglDiaryLeaveMapper;

    @Resource
    private BdglDiaryWeekworkRegisterMapper bdglDiaryWeekworkRegisterMapper;

    @Resource
    private BdglLeaveOneMapper bdglLeaveOneMapper;

    @Resource
    private ISysDictDataService sysDictDataService;

    /**
     * 查询要事日记
     *
     * @param id 要事日记主键
     * @return 要事日记
     */
    @Override
    public BdglDiary selectBdglDiaryById(Integer id) {
        return bdglDiaryMapper.selectBdglDiaryById(id);
    }

    /**
     * 查询要事日记列表
     *
     * @param bdglDiary 要事日记
     * @return 要事日记
     */
    @Override
    public List<BdglDiary> selectBdglDiaryList(BdglDiary bdglDiary) {
        return bdglDiaryMapper.selectBdglDiaryList(bdglDiary);
    }

    /**
     * 新增要事日记
     *
     * @param bdglDiary 要事日记
     * @return 结果
     */
    @Override
    public int insertBdglDiary(BdglDiary bdglDiary) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //获取昨日00点
        Date startEndTime = getSpecifiedDayBefore(bdglDiary.getBlogDate(), -1, 00);
        //获取当日早上十点
        //改：要事日记录入时间改为第二天早上9点前
        Date startEndTime1 = getSpecifiedDayBefore(bdglDiary.getBlogDate(), 0, 9);
//        startEndTime1.setHours(6);
//        startEndTime1.setMinutes(40);
        String week = getWeek(bdglDiary.getBlogDate());

        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        String format1 = simpleDateFormat1.format(bdglDiary.getBlogDate());
        if(format1.equals(week)){
//            startEndTime1.setHours(7);
//            startEndTime1.setMinutes(40);
        }
        //如果值班日期在昨日和当天晚上十点之内  要事日记日期为前一天  大于当天晚上十点  要事日记为当天
        if (bdglDiary.getBlogDate().getTime() < startEndTime1.getTime()) {
            Calendar time = Calendar.getInstance();
            time.add(Calendar.DATE, -1);
            bdglDiary.setDiaryTime(simpleDateFormat.format(getSpecifiedDayBefore(bdglDiary.getBlogDate(), -1, 00)));
        } else {
            bdglDiary.setDiaryTime(simpleDateFormat.format(bdglDiary.getBlogDate()));
        }

        SysDept sysDept = deptMapper.selectDeptById(Long.valueOf(bdglDiary.getUnitId()));

        bdglDiary.setUnitName(sysDept.getDeptName());
        int i = bdglDiaryMapper.insertBdglDiary(bdglDiary);
        Integer id = bdglDiary.getId();
        //bdglDiary.getCreatetime()
        /*-----------------添加查铺查哨  临时来队亲属------------------------*/
        List<BdglDailyInspect> inspect = bdglDiary.getInspect();
        if (inspect != null && inspect.size() > 0) {
            for (BdglDailyInspect bdglDailyInspect : inspect) {
                bdglDailyInspect.setDiaryId(id);
                Date nowDate = DateUtils.getNowDate();
                bdglDailyInspect.setUnitId(bdglDiary.getUnitId());
                bdglDailyInspect.setCreatetime(bdglDiary.getBlogDate());
                bdglDailyInspect.setUnitId(bdglDiary.getUnitId());
                bdglDailyInspectMapper.insertBdglDailyInspect(bdglDailyInspect);
            }
        }
        List<BdglDiaryKinsfolk> kinsfolk = bdglDiary.getKinsfolk();
        if (kinsfolk != null && kinsfolk.size() > 0) {
            for (BdglDiaryKinsfolk bdglDiaryKinsfolk : kinsfolk) {
                if (bdglDiaryKinsfolk.getPeopleId() != null) {
                    bdglDiaryKinsfolk.setDiaryId(id);
                    bdglDiaryKinsfolk.setCreatetime(DateUtils.getNowDate());
                    BdglPeople bdglPeople = peopleMapper.selectBdglPeopleById(Long.valueOf(bdglDiaryKinsfolk.getPeopleId()));
                    bdglDiaryKinsfolk.setPeopleName(bdglPeople.getName());
                    bdglDiaryKinsfolkMapper.insertBdglDiaryKinsfolk(bdglDiaryKinsfolk);
                }
            }
        }
        /*-----------------添加查铺查哨  临时来队亲属------------------------*/
        /*--------------------------修改一周工作安排------------------------------*/
//        List<BdglWeekworkRegister> weekworkRegisters = bdglDiary.getWeekworkRegisters();
//        if (weekworkRegisters != null) {
//            for (BdglWeekworkRegister weekworkRegister : weekworkRegisters) {
//                weekworkRegisterMapper.updateBdglWeekworkRegister(weekworkRegister);
//            }
//        }
        /*--------------------------添加一周工作安排------------------------------*/
        List<BdglDiaryWeekworkRegister> bdglDiaryWeekworkRegisterList = bdglDiary.getBdglDiaryWeekworkRegisterList();
        if (bdglDiaryWeekworkRegisterList!=null&&bdglDiaryWeekworkRegisterList.size()>0){
            for (BdglDiaryWeekworkRegister bdglDiaryWeekworkRegister : bdglDiaryWeekworkRegisterList) {
                bdglDiaryWeekworkRegister.setBdglDiaryId(bdglDiary.getId());
                bdglDiaryWeekworkRegister.setCreateTime(new Date());
                bdglDiaryWeekworkRegisterMapper.insertBdglDiaryWeekworkRegister(bdglDiaryWeekworkRegister);
            }
        }
        /*----------------------------要事日记请假--------------------------*/
//        List<BdglDiaryLeave> bdglDiaryLeaveList = bdglDiary.getBdglDiaryLeaveList();
//        if (bdglDiaryLeaveList!=null){
//            for (BdglDiaryLeave bdglDiaryLeave : bdglDiaryLeaveList) {
//                bdglDiaryLeave.setBdgldiaryId(Long.valueOf(bdglDiary.getId()+""));
//                bdglDiaryLeave.setCreateTime(new Date());
//                bdglDiaryLeaveMapper.insertBdglDiaryLeave(bdglDiaryLeave);
//            }
//        }
        return i;
    }
    /**
     * 获取指定日期所在周的周日
     *
     * @param date 日期
     */
    private static String getWeek(Date date){


        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // 如果是周日直接返回
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            return df.format(date);
        }
        //System.out.println(c.get(Calendar.DAY_OF_WEEK));
        c.add(Calendar.DATE, 7 - c.get(Calendar.DAY_OF_WEEK) + 1);

        return df.format(c.getTime());
    }


    /**
     * 修改要事日记
     *
     * @param bdglDiary 要事日记
     * @return 结果
     */
    @Override
    public int updateBdglDiary(BdglDiary bdglDiary) {
        SysDept sysDept = deptMapper.selectDeptById(Long.valueOf(bdglDiary.getUnitId()));
        bdglDiary.setUnitName(sysDept.getDeptName());
        List<BdglDiaryKinsfolk> kinsfolk = bdglDiary.getKinsfolk();
        List<BdglDailyInspect> inspect = bdglDiary.getInspect();
        List<BdglWeekworkRegister> weekworkRegisters = bdglDiary.getWeekworkRegisters();
        if (kinsfolk != null && kinsfolk.size() > 0) {
            int i = bdglDiaryKinsfolkMapper.deleteBdglDiaryKinsfolk(bdglDiary.getId());
            for (BdglDiaryKinsfolk bdglDiaryKinsfolk : kinsfolk) {
                BdglPeople bdglPeople = peopleMapper.selectBdglPeopleById(Long.valueOf(bdglDiaryKinsfolk.getPeopleId()));
                bdglDiaryKinsfolk.setId(null);
                bdglDiaryKinsfolk.setPeopleName(bdglPeople.getName());
                bdglDiaryKinsfolk.setDiaryId(bdglDiary.getId());
                bdglDiaryKinsfolkMapper.insertBdglDiaryKinsfolk(bdglDiaryKinsfolk);

            }
        }
        if (inspect != null && inspect.size() > 0) {
            int i = bdglDailyInspectMapper.deleteBdglDailyInspect(bdglDiary.getId());
            for (BdglDailyInspect bdglDailyInspect : inspect) {
                bdglDailyInspect.setId(null);
                bdglDailyInspect.setUpdatetime(new Date());
                bdglDailyInspect.setCreatetime(bdglDiary.getBlogDate());
                bdglDailyInspect.setDiaryId(bdglDiary.getId());
                bdglDailyInspectMapper.insertBdglDailyInspect(bdglDailyInspect);
            }
        }
//        if (weekworkRegisters != null && weekworkRegisters.size() > 0) {
//            for (BdglWeekworkRegister weekworkRegister : weekworkRegisters) {
//                weekworkRegisterMapper.updateBdglWeekworkRegister(weekworkRegister);
//            }
//        }

        /*--------------------------修改一周工作安排------------------------------*/
        BdglDiaryWeekworkRegister bdglDiaryWeekworkRegister = new BdglDiaryWeekworkRegister();
        bdglDiaryWeekworkRegister.setBdglDiaryId(bdglDiary.getId());
        List<BdglDiaryWeekworkRegister> selectBdglDiaryWeekworkRegisterList = bdglDiaryWeekworkRegisterMapper.selectBdglDiaryWeekworkRegisterList(bdglDiaryWeekworkRegister);
        if (selectBdglDiaryWeekworkRegisterList.size()>0){
            List<Long> idList = selectBdglDiaryWeekworkRegisterList.stream().map(BdglDiaryWeekworkRegister::getId).collect(Collectors.toList());
            Long[] arr = idList.toArray(new Long[0]);
            bdglDiaryWeekworkRegisterMapper.deleteBdglDiaryWeekworkRegisterByIds(arr);
        }

        List<BdglDiaryWeekworkRegister> bdglDiaryWeekworkRegisterList = bdglDiary.getBdglDiaryWeekworkRegisterList();
        if (bdglDiaryWeekworkRegisterList!=null&&bdglDiaryWeekworkRegisterList.size()>0){
            for (BdglDiaryWeekworkRegister bdglDiaryWeekworkRegister1 : bdglDiaryWeekworkRegisterList) {
                bdglDiaryWeekworkRegister1.setBdglDiaryId(bdglDiary.getId());
                bdglDiaryWeekworkRegister1.setCreateTime(new Date());
                bdglDiaryWeekworkRegisterMapper.insertBdglDiaryWeekworkRegister(bdglDiaryWeekworkRegister1);
            }
        }

        /*----------------------------要事日记请假--------------------------*/
        /*BdglDiaryLeave bdglDiaryLeave = new BdglDiaryLeave();
        bdglDiaryLeave.setBdgldiaryId(Long.valueOf(bdglDiary.getId()+""));
        List<BdglDiaryLeave> selectBdglDiaryLeaveList = bdglDiaryLeaveMapper.selectBdglDiaryLeaveList(bdglDiaryLeave);
        if (selectBdglDiaryLeaveList.size()>0){
            List<Long> idList = selectBdglDiaryLeaveList.stream().map(BdglDiaryLeave::getId).collect(Collectors.toList());
            Long arr[]=new Long[idList.size()];
            for (int i = 0; i < idList.size(); i++) {
                arr[i]=Long.valueOf(idList.get(i)+"");
            }
            bdglDiaryLeaveMapper.deleteBdglDiaryLeaveByIds(arr);
        }
        List<BdglDiaryLeave> bdglDiaryLeaveList = bdglDiary.getBdglDiaryLeaveList();
        if (bdglDiaryLeaveList!=null){
            for (BdglDiaryLeave bdglDiaryLeave1 : bdglDiaryLeaveList) {
                bdglDiaryLeave1.setBdgldiaryId(Long.valueOf(bdglDiary.getId()+""));
                bdglDiaryLeave1.setCreateTime(new Date());
                bdglDiaryLeaveMapper.insertBdglDiaryLeave(bdglDiaryLeave1);
            }
        }*/

        return bdglDiaryMapper.updateBdglDiary(bdglDiary);
    }

    /**
     * 批量删除要事日记
     *
     * @param ids 需要删除的要事日记主键
     * @return 结果
     */
    @Override
    public int deleteBdglDiaryByIds(Integer[] ids) {
        return bdglDiaryMapper.deleteBdglDiaryByIds(ids);
    }

    /**
     * 删除要事日记信息
     *
     * @param id 要事日记主键
     * @return 结果
     */
    @Override
    public int deleteBdglDiaryById(Integer id) {
        return bdglDiaryMapper.deleteBdglDiaryById(id);
    }

    /**
     * 获取要事日记详细信息
     *
     * @return
     */
    @Override
    public BdglDiary selectBdglDiaryxiangqing(Integer id) {
        BdglDiary bdglDiary = bdglDiaryMapper.selectBdglDiaryById(id);
        /*----------------------------查询当前大队当日工作计划---------------------------------*/
        BdglWeekwork bdglWeekwork = new BdglWeekwork();
        //获取当日晚上十点
//        Date startEndTime1 = getSpecifiedDayBefore(bdglDiary.getBlogDate(), 0, 9);
//        startEndTime1.setHours(6);
//        startEndTime1.setMinutes(40);
//        String week = getWeek(bdglDiary.getBlogDate());
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
//        String format1 = simpleDateFormat1.format(bdglDiary.getBlogDate());
//        if(format1.equals(week)){
//            startEndTime1.setHours(7);
//            startEndTime1.setMinutes(40);
//        }
//        if (bdglDiary.getBlogDate().getTime() < startEndTime1.getTime()) {
//
//            bdglWeekwork.setTime(getSpecifiedDayBefore(bdglDiary.getBlogDate(), -1, 00));
//        } else if (bdglDiary.getBlogDate().getTime() > startEndTime1.getTime()) {
//            bdglWeekwork.setTime(bdglDiary.getBlogDate());
//        }
        try {
            bdglWeekwork.setTime(simpleDateFormat1.parse(bdglDiary.getDiaryTime()));
        } catch (ParseException e) {
        }
        bdglWeekwork.setUnitId(bdglDiary.getUnitId());
        BdglWeekwork bdglWeekwork1 = weekworkMapper.selectBdglWeekwork(bdglWeekwork);
        ArrayList<Object> list = new ArrayList<>();
        if (bdglWeekwork1 != null) {
            //旧的数据----不知道其他地方有没有用到，代码不动
            BdglWeekworkRegister bdglWeekworkRegister = new BdglWeekworkRegister();
            bdglWeekworkRegister.setWeekworkId(bdglWeekwork1.getId());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String format = simpleDateFormat.format(bdglWeekwork.getTime());
            bdglWeekworkRegister.setTime(DateUtils.parseDate(format));
            List<BdglWeekworkRegister> bdglWeekworkRegisters = weekworkRegisterMapper.selectBdglWeekworkRegisterList(bdglWeekworkRegister);
            bdglDiary.setWeekworkRegisters(bdglWeekworkRegisters);
            //一周工作安排分组数据
//            Long weekWorkId = Long.valueOf(bdglWeekwork1.getId());
            //分组合并数据
            List<BdglWeekworkRegisterVo> bdglWeekworkRegisterVo = weekworkRegisterMapper.getWeekworkRegisterList(bdglWeekworkRegister);
            bdglDiary.setContextData(bdglWeekworkRegisterVo);
            //单独数据
            List<BdglWeekworkRegisterVo> bdglWeekworkRegisterVo2 = weekworkRegisterMapper.getWeekworkRegisterList2(bdglWeekworkRegister);
            bdglDiary.setContextData2(bdglWeekworkRegisterVo2);
        }
        /*----------------------------查询当前大队当日工作计划---------------------------------*/
        /*-----------------查询查铺查哨  临时来队亲属------------------------*/
        BdglDailyInspect bdglDailyInspect = new BdglDailyInspect();
        bdglDailyInspect.setDiaryId(bdglDiary.getId());
        List<BdglDailyInspect> bdglDailyInspects = bdglDailyInspectMapper.selectBdglDailyInspectList(bdglDailyInspect);
        if (bdglDailyInspects.size() > 0) {

            bdglDiary.setInspect(bdglDailyInspects);

        }
        BdglDiaryKinsfolk bdglDiaryKinsfolk = new BdglDiaryKinsfolk();
        bdglDiaryKinsfolk.setDiaryId(bdglDiary.getId());
        List<BdglDiaryKinsfolk> bdglDiaryKinsfolks = bdglDiaryKinsfolkMapper.selectBdglDiaryKinsfolkList(bdglDiaryKinsfolk);
        if (bdglDiaryKinsfolks.size() > 0) {

            bdglDiary.setKinsfolk(bdglDiaryKinsfolks);

        }
        /*-----------------查询查铺查哨  临时来队亲属------------------------*/
        /*----------------------------获取当前部门请假外出人员--------------------------*/
        /*BdglLeave bdglLeave = new BdglLeave();
        if (bdglDiary.getUnitId() != null) {

            Integer unitId = bdglDiary.getUnitId();
            bdglLeave.setUnitId(Long.valueOf(bdglDiary.getUnitId()));
            bdglLeave.setStartTime(bdglDiary.getBlogDate());
            bdglLeave.setLeaveType("0");
            bdglLeave.setRejoin(0);
            bdglLeave.setStatus4(1);
            List<BdglLeave> bdglLeaves = bdglLeaveMapper.selectBdglLeavePeopless(bdglLeave);
            ArrayList<BdglLeave> bdglLeaves1 = new ArrayList<>();
            for (BdglLeave bdglLeaf : bdglLeaves) {
                String[] split = bdglLeaf.getPeopleId().split(",");
                if (split.length>0){
                    for (String s : split) {
                        BdglPeople bdglPeople = peopleMapper.selectBdglPeopleById(Long.valueOf(bdglLeaf.getPeopleId()));
                        bdglLeaf.setPostId(bdglPeople.getPostId());
                        bdglLeaf.setPeopleName(bdglPeople.getName());
                        bdglLeaf.setPeopleId(s);
                        bdglLeaves1.add(bdglLeaf);
                    }
                }
                bdglLeaves1.add(bdglLeaf);
            }
            if(bdglLeaves1.size()>0){

                bdglDiary.setBdglLeaves(bdglLeaves1);
            }else{
                bdglDiary.setBdglLeaves(null);

            }
        }*/

        /*----------------------------获取当前部门请假外出人员，从单日请假获取--------------------------*/
        BdglLeaveOne bdglLeaveOne = new BdglLeaveOne();
        try{
            bdglLeaveOne.setStartTimeF(bdglDiary.getDiaryTime());
        }catch (Exception e){
        }
        bdglLeaveOne.setUnitId(Long.valueOf(bdglDiary.getUnitId()+""));
        List<BdglLeaveOne> selectBdglLeaveOneList = bdglLeaveOneMapper.selectBdglLeaveOneList(bdglLeaveOne);

        Map<String,String> postLevelMap = new HashMap<>();
        if (selectBdglLeaveOneList.size()>0){
            SysDictData sysDictData = new SysDictData();
            sysDictData.setDictType("post_Level");
            List<SysDictData> selectDictDataList = sysDictDataService.selectDictDataList(sysDictData);
            for (SysDictData dictData : selectDictDataList) {
                postLevelMap.put(dictData.getDictValue(), dictData.getDictLabel());
            }
        }

        List<BdglLeaveOne> bdglLeaveOneList = new ArrayList<>();
        for (BdglLeaveOne leaveOne : selectBdglLeaveOneList) {
            String[] split = leaveOne.getPeopleId().split(",");
            if (split.length>0){
                for (String s : split) {
                    BdglPeople bdglPeople = peopleMapper.selectBdglPeopleById(Long.valueOf(s));
                    leaveOne.setPostId(bdglPeople.getPostId());
                    if (bdglPeople.getPostId()!=null){
                        leaveOne.setPostName(postLevelMap.get(bdglPeople.getPostId()+""));
                    }else {
                        leaveOne.setPostName("");
                    }
                    leaveOne.setPeopleName(bdglPeople.getName());
                    leaveOne.setPeopleId(s);
                    bdglLeaveOneList.add(leaveOne);
                }
            }
        }
        if (bdglLeaveOneList.size()>0){
            bdglDiary.setBdglLeaveOneList(bdglLeaveOneList);
        }else {
            bdglDiary.setBdglLeaveOneList(null);
        }

//        //封装一周工作安排时段和内容
//        List<BdglWeekworkRegister> weekworkRegisters = bdglDiary.getWeekworkRegisters();
//        Map<String, List<Object>> map = getStringListMap(weekworkRegisters);
//        bdglDiary.setContextData(map);

        /*----------------------------要事日记请假--------------------------*/
        /*BdglDiaryLeave bdglDiaryLeave = new BdglDiaryLeave();
        bdglDiaryLeave.setBdgldiaryId(Long.valueOf(bdglDiary.getId()+""));
        List<BdglDiaryLeave> selectBdglDiaryLeaveList = bdglDiaryLeaveMapper.selectBdglDiaryLeaveList(bdglDiaryLeave);
        bdglDiary.setBdglDiaryLeaveList(selectBdglDiaryLeaveList);*/


        /*----------------------------获取一周工作安排--------------------------*/
        BdglDiaryWeekworkRegister bdglDiaryWeekworkRegister = new BdglDiaryWeekworkRegister();
        bdglDiaryWeekworkRegister.setBdglDiaryId(bdglDiary.getId());
        List<BdglDiaryWeekworkRegister> selectBdglDiaryWeekworkRegisterList = bdglDiaryWeekworkRegisterMapper.selectBdglDiaryWeekworkRegisterList(bdglDiaryWeekworkRegister);
        bdglDiary.setBdglDiaryWeekworkRegisterList(selectBdglDiaryWeekworkRegisterList);
        return bdglDiary;
    }

    /**
     * 一周工作内容数据封装
     * 此方法已不再使用------------------------------暂时先不动
     * @param weekworkRegisters
     * @return
     */
    private Map<String, List<Object>> getStringListMap(List<BdglWeekworkRegister> weekworkRegisters) {
        Map<String,List<Object>> map = new HashMap<>();
        //早晨
        List<BdglWeekworkRegisterVo> contextList1 =new ArrayList<>();
        //上午
        List<BdglWeekworkRegisterVo> contextList2 =new ArrayList<>();
        //下午
        List<BdglWeekworkRegisterVo> contextList3 =new ArrayList<>();
        //晚上
        List<BdglWeekworkRegisterVo> contextList4 =new ArrayList<>();
        //遍历已有工作内容数据
        StringBuilder builder =new StringBuilder();
        BdglWeekworkRegisterVo bdglWeekworkRegisterVo = new BdglWeekworkRegisterVo();
        weekworkRegisters.forEach(item ->{
            String dateline = item.getDateline();
            switch (dateline){
                case "早晨":
                    String newContents= item.getStartTime()+"-"+ item.getEndTime()+" "+ item.getContents()+" (组织人:+"+ item.getOrganizer()+")";
                    builder.append(newContents);
                    getContents(item, bdglWeekworkRegisterVo, dateline);
                    contextList1.add(bdglWeekworkRegisterVo);
                    break;
                case "上午" :
                    getContents(item, bdglWeekworkRegisterVo, dateline);
                    contextList2.add(bdglWeekworkRegisterVo);
                    break;
                case "下午" :
                    getContents(item, bdglWeekworkRegisterVo, dateline);
                    contextList3.add(bdglWeekworkRegisterVo);
                    break;
                default:
                    getContents(item, bdglWeekworkRegisterVo, dateline);
                    contextList4.add(bdglWeekworkRegisterVo);
                    break;
            }
            map.put("早晨", Collections.singletonList(contextList1));
            map.put("上午", Collections.singletonList(contextList2));
            map.put("下午", Collections.singletonList(contextList3));
            map.put("晚上", Collections.singletonList(contextList4));
        });
        bdglWeekworkRegisterVo.setContents(builder.toString());
        return map;
    }

    /**
     * 此方法已不再使用---------------暂时先不动
     * @param item
     * @param bdglWeekworkRegisterVo
     * @param dateline
     */
    private void getContents(BdglWeekworkRegister item, BdglWeekworkRegisterVo bdglWeekworkRegisterVo, String dateline) {
        //组装新的文本
        //时段
        bdglWeekworkRegisterVo.setDateline(dateline);
        //新的文本内容
        String newContents= item.getStartTime()+"-"+ item.getEndTime()+" "+ item.getContents()+" (组织人:+"+ item.getOrganizer()+")";
        bdglWeekworkRegisterVo.setContents(newContents);
        //应到
        bdglWeekworkRegisterVo.setShouldArrive(item.getShouldArrive());
        //实到
        bdglWeekworkRegisterVo.setRealTo(item.getRealTo());
        //到课率
        bdglWeekworkRegisterVo.setPercentage(item.getPercentage());
    }

    /**
     * 获取当前部门当日工作安排
     */
    @Override
    public List<BdglWeekworkRegister> getWeekWork(BdglDiary bdglDiary) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //获取昨日00点
        Date startEndTime = getSpecifiedDayBefore(bdglDiary.getBlogDate(), -1, 00);
        //获取当日晚上十点
//        Date startEndTime1 = getSpecifiedDayBefore(bdglDiary.getBlogDate(), 0, 10);
        //改：要事日记录入时间改为第二天早上9点前
        Date startEndTime1 = getSpecifiedDayBefore(bdglDiary.getBlogDate(), 0, 9);
//        startEndTime1.setHours(6);
//        startEndTime1.setMinutes(40);
        String week = getWeek(bdglDiary.getBlogDate());
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        String format1 = simpleDateFormat1.format(bdglDiary.getBlogDate());
        if(format1.equals(week)){
//            startEndTime1.setHours(7);
//            startEndTime1.setMinutes(40);
        }
        BdglWeekwork bdglWeekwork = new BdglWeekwork();
        //判断值班日期是否在当日十点之前   十点之前值班日期为昨天   十点之后为当日
        if (bdglDiary.getBlogDate().getTime() > startEndTime.getTime() && bdglDiary.getBlogDate().getTime() < startEndTime1.getTime()) {

            bdglWeekwork.setTime(getSpecifiedDayBefore(bdglDiary.getBlogDate(), -1, 00));
        } else {
            bdglWeekwork.setTime(bdglDiary.getBlogDate());
        }
        String format = simpleDateFormat.format(bdglWeekwork.getTime());
        bdglWeekwork.setUnitId(bdglDiary.getUnitId());

        BdglWeekwork bdglWeekwork1 = weekworkMapper.selectBdglWeekwork(bdglWeekwork);
        ArrayList<Object> list = new ArrayList<>();
        if (bdglWeekwork1 != null) {
            BdglWeekworkRegister bdglWeekworkRegister = new BdglWeekworkRegister();
            //bdglWeekworkRegister.setWeekworkId(bdglWeekwork1.getId());
            bdglWeekworkRegister.setUnitId1(bdglWeekwork1.getUnitName());
            bdglWeekworkRegister.setTime(DateUtils.parseDate(format));
            List<BdglWeekworkRegister> bdglWeekworkRegisters = weekworkRegisterMapper.selectBdglWeekworkRegisterList(bdglWeekworkRegister);
            ArrayList<BdglWeekworkRegister> list1 = new ArrayList<>();
            return bdglWeekworkRegisters;
        }
        return null;
    }

    /**
     * 获取当前部门  干部  战士  文职人数
     */
    @Override
    public Map<String, Object> getRenShu(Long deptId) {
        BdglPeople bdglPeople = new BdglPeople();
        bdglPeople.setDeptId(deptId);
        List<BdglPeople> people = peopleMapper.selectPowerPeoleLists(bdglPeople);
        int shiBin = 0;
        int qita = 0;
        int ganbu = 0;
        int wenZhi = 0;
        int xianYouShiBin = 0;
        int xianYouGanBu = 0;
        int xianYouWenZhi = 0;
        for (BdglPeople person : people) {
            if (person.getAuthorizedStrength() != null && person.getClassId() != null) {
                if (person.getClassId() == 0 || person.getClassId() == 1 && person.getAuthorizedStrength().equals("Y")) {
                    shiBin++;
                }
                if (person.getClassId() == 2 || person.getClassId() == 3 && person.getAuthorizedStrength().equals("Y")) {
                    ganbu++;
                }
                if (person.getClassId() == 4 && person.getAuthorizedStrength().equals("Y")) {
                    wenZhi++;
                }
                else {
                    qita++;
                }
            }

            boolean y = person.getAuthorizedStrength().equals("Y");

            if (person.getClassId() != null) {
                if (person.getClassId() == 0 || person.getClassId() == 1) {
                    xianYouShiBin++;
                }
                if (person.getClassId() == 2 || person.getClassId() == 3) {
                    xianYouGanBu++;
                }
                if (person.getClassId() == 4) {
                    xianYouWenZhi++;
                }
            }
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("shiBin", shiBin);
        map.put("ganbu", ganbu);
        map.put("wenZhi", wenZhi);
        map.put("xianYouShiBin", xianYouShiBin);
        map.put("xianYouGanBu", xianYouGanBu);
        map.put("xianYouWenZhi", xianYouWenZhi);
        return map;
    }

    /**
     * 查询已审批部门
     */
    @Override
    public BdglDiary selectBdglDiaryLists(BdglDiary bdglDiary) {
        return bdglDiaryMapper.selectBdglDiaryLists(bdglDiary);
    }

    /**
     * 获取当日已审批部门数量
     */
    @Override
    public List<BdglDiary> selectBdglDiarys(BdglDiary bdglDiary) {
        return bdglDiaryMapper.selectBdglDiarys(bdglDiary);
    }

    /**
     * 查询当前时间、当前部门下是否有数据
     *
     * @param starttime
     * @param endtime
     * @param unitid
     * @return
     */
    @Override
    public List<BdglDiary> selectBdglDiaryListByTime(String starttime, String endtime, Long unitid) {
        return bdglDiaryMapper.selectBdglDiaryListByTime(starttime, endtime, unitid);
    }

    @Override
    public int updateBdglDiarya(BdglDiary bdglDiary) {
        return bdglDiaryMapper.updateBdglDiary(bdglDiary);
    }

    @Override
    public List<BdglDiary> selectBdglDiaryListss(BdglDiary bdglDiary) {
        return bdglDiaryMapper.selectBdglDiaryListss(bdglDiary);
    }

    @Override
    public int selectBdglDailyInspectCountByTime(Date startTime, Date endTime, Long unitId) {
        return bdglDiaryMapper.selectBdglDailyInspectCountByTime(startTime,endTime,unitId);
    }

    @Override
    public List<BdglDiaryVo> selectDirayStatList(BdglDiaryVo diaryVo) {
        return bdglDiaryMapper.selectDirayStatList(diaryVo);
    }

    /**
     * 获取指定日期的前后时间
     */
    public static Date getSpecifiedDayBefore(Date specifiedDay, int days, int hour) {
//SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(specifiedDay);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + days);//天
        c.set(Calendar.HOUR_OF_DAY, hour);//时
        c.set(Calendar.MINUTE, 0);//分
        c.set(Calendar.SECOND, 0);//秒
        c.set(Calendar.MILLISECOND, 0);//秒
        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
        return c.getTime();
    }

}
