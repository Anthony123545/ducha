package com.supervision.web.api.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.supervision.barracksManagement.domain.dto.ApiFlatsDto;
import com.supervision.barracksManagement.mapper.BdglBarracksMapper;
import com.supervision.barracksManagement.mapper.BdglFlatsMapper;
import com.supervision.combatduty.domain.BdglBaseduty;
import com.supervision.combatduty.domain.BdglMilitaryduty;
import com.supervision.combatduty.domain.BdglTeamduty;
import com.supervision.combatduty.domain.dto.ApiDaduiDutyDto;
import com.supervision.combatduty.domain.dto.ApiStatDutyDto;
import com.supervision.combatduty.mapper.BdglGrassdutyMapper;
import com.supervision.combatduty.mapper.BdglTeamdutyMapper;
import com.supervision.combatduty.service.IBdglBasedutyService;
import com.supervision.combatduty.service.IBdglMilitarydutyService;
import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.common.exception.ServiceException;
import com.supervision.common.utils.DateUtils;
import com.supervision.common.utils.DictUtils;
import com.supervision.common.utils.StringUtils;
import com.supervision.peopleManage.domain.dto.StatOutPeopleDto;
import com.supervision.peopleManage.domain.dto.StatPeopleDto;
import com.supervision.peopleManage.domain.dto.StatPersonAgeDto;
import com.supervision.peopleManage.domain.dto.StatPersonZzmmDto;
import com.supervision.peopleManage.mapper.BdglPeopleMapper;
import com.supervision.system.mapper.SysDeptMapper;
import com.supervision.web.api.service.ITaiShiService;
import com.supervision.web.api.vo.ErrorCode;
import com.supervision.web.api.vo.SituationStatistics;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 态势分析API接口实现
 * @author gaoqiang
 */
@Service
public class TaiShiServiceImpl implements ITaiShiService {

    @Resource
    private BdglPeopleMapper peopleMapper;

    @Resource
    private SysDeptMapper deptMapper;

    @Resource
    private BdglBarracksMapper barracksMapper;

    @Resource
    private IBdglBasedutyService bdglBasedutyService;

    @Resource
    private IBdglMilitarydutyService bdglMilitarydutyService;
    
    @Resource
    private BdglFlatsMapper flatsMapper;
    
    @Resource
    private BdglGrassdutyMapper grassdutyMapper;
    
    @Resource
    private BdglTeamdutyMapper teamdutyMapper;

    /**
     * 营房态势统计
     */
    @Override
    public Map<String,List<ApiFlatsDto>> statisticsBarracks() {
        //查询顶级单位
        SysDept sysDeptTop = deptMapper.selectTopDept(0L);
        //查询一级单位
        List<SysDept> oneDeptList = getOneDeptList(sysDeptTop);
        //查询营房总数
        Integer barracksCount = barracksMapper.selectBdglBarracksCount();
        List<ApiFlatsDto> flatsDtoList = new ArrayList<>();
        Set<Long> deptId = new HashSet<>();
        //查询单位入住情况
        oneDeptList.forEach(oneDept->{
            ApiFlatsDto apiFlatsDto = new ApiFlatsDto();
            //查询二级单位ID
            List<Long> deptIdList = deptMapper.selectDeptTwoById(oneDept.getDeptId());
            deptIdList.add(oneDept.getDeptId());
            deptId.add(oneDept.getDeptId());
            //统计公寓房数据
            Integer count =  flatsMapper.selectFlatsStatCount(deptIdList);
            if (count >0){
                apiFlatsDto.setDwmc(oneDept.getDeptName());
                apiFlatsDto.setYfzs(barracksCount);
                apiFlatsDto.setIsBase(0);
                apiFlatsDto.setIsBz(0);
                apiFlatsDto.setNullData(false);
                apiFlatsDto.setRzzs(count);
                double rzl = (double) count / (double) barracksCount;
                apiFlatsDto.setRzl(Double.valueOf(NumberUtil.roundStr(rzl,2)));
                flatsDtoList.add(apiFlatsDto);
            }
        });
        //数据汇总
        List<Long> deptIdList = deptMapper.selectDeptTwoListById(deptId);
        deptId.addAll(deptIdList);
        //统计总的入住数据
        Integer totalCount =  flatsMapper.selectFlatsStatCount(new ArrayList<>(deptId));
        Map<String,List<ApiFlatsDto>> listMap = new HashMap<>(16);
        ApiFlatsDto apiFlatDto = new ApiFlatsDto();
        apiFlatDto.setDwmc(sysDeptTop.getDeptName());
        apiFlatDto.setYfzs(barracksCount);
        apiFlatDto.setIsBase(0);
        apiFlatDto.setIsBz(1);
        apiFlatDto.setNullData(false);
        apiFlatDto.setRzzs(totalCount);
        double rzl = (double) totalCount / (double) barracksCount;
        apiFlatDto.setRzl(Double.valueOf(NumberUtil.roundStr(rzl,2)));
        flatsDtoList.add(apiFlatDto);
        listMap.put(sysDeptTop.getDeptName(),flatsDtoList);
        return listMap;
    }

    @Override
    public List<Map<String, Object>> statisticsOnDuty(SituationStatistics situationStatistics) {
        if (StringUtils.isBlank(situationStatistics.getStartTime())||StringUtils.isBlank(situationStatistics.getEndTime())){
            throw new ServiceException("参数不完整", ErrorCode.ERROR_PARAMS_MISSING);
        }
        List<Map<String,Object>> listMap = new ArrayList<>();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        //根据开始时间和结束时间查询基地值班情况
        BdglBaseduty bdglBaseduty = new BdglBaseduty();
        bdglBaseduty.setAttendedTimeGe(situationStatistics.getStartTime());
        bdglBaseduty.setAttendedTimeLe(situationStatistics.getEndTime());
        List<BdglBaseduty> selectBdglBasedutyList = bdglBasedutyService.selectBdglBasedutyList(bdglBaseduty);

        //统计值班车辆
        BdglMilitaryduty bdglMilitaryduty = new BdglMilitaryduty();
        bdglMilitaryduty.setAttendedTimeGe(situationStatistics.getStartTime());
        bdglMilitaryduty.setAttendedTimeLe(situationStatistics.getEndTime());
        List<BdglMilitaryduty> bdglMilitarydutyList = bdglMilitarydutyService.selectBdglMilitarydutyList(bdglMilitaryduty);
        Map<String, List<BdglMilitaryduty>> bdglMilitarydutyMap = bdglMilitarydutyList.stream().collect(Collectors.groupingBy(BdglMilitaryduty::getAttendedTime));

        selectBdglBasedutyList.forEach(x->{
            //设置属性
            Map<String,Object> itemMap = new LinkedHashMap<>();
            itemMap.put("zbkssj",x.getAttendedTime());//值班开始时间
//            itemMap.put("zbjssj",getNextDay(x.getAttendedTime()));//值班结束时间
            itemMap.put("tjnd",year);
            itemMap.put("zbsz",x.getChiefDutyName());//值班首长
            itemMap.put("zbdh",x.getDutyOffierPhone());//总值班员电话

            //值班车辆
            List<BdglMilitaryduty> militarydutyList = bdglMilitarydutyMap.get(x.getAttendedTime());
            if (militarydutyList!=null){
                List<String> carList = militarydutyList.stream().map(BdglMilitaryduty::getNumberPlateName).collect(Collectors.toList());
                itemMap.put("zbcl",StringUtils.join(carList,","));
            }else {
                itemMap.put("zbcl","");
            }

            StringJoiner str = new StringJoiner(",");
            str.add(x.getDetachmentoneName()==null?"":x.getDetachmentoneName()).add(x.getDetachmentwoName()==null?"":x.getDetachmentwoName());
            itemMap.put("zbfd",str.toString());//值班分队

            //值班员情况，返回的是当前记录的作战值班员
            List<Map<String,Object>> dutyPeopleListMap = new ArrayList<>();
            Map<String,Object> dutyPeopleMap = new LinkedHashMap<>();
            dutyPeopleMap.put("xm",x.getCombatDutyName()==null?"":x.getCombatDutyName());
            dutyPeopleMap.put("zbdh",x.getCombatDutyPhone()==null?"":x.getCombatDutyPhone());
            dutyPeopleMap.put("zbzbid",x.getId());
            dutyPeopleListMap.add(dutyPeopleMap);
            itemMap.put("data",dutyPeopleListMap);//值班员

            listMap.add(itemMap);
        });
        return listMap;
    }
    
    /**
     * 人员在位统计
     * @return List
     */
    @Override
    public Map<String,List<StatPeopleDto>> statReignPerson() {
        List<StatPeopleDto> statPeopleDtoList = new ArrayList<>();
        //查询顶级单位
        SysDept sysDeptTop = deptMapper.selectTopDept(0L);
        //查询所有一级单位
        List<SysDept> sysDeptList = getOneDeptList(sysDeptTop);
        
        if (CollectionUtil.isNotEmpty(sysDeptList) && sysDeptList.size() > 0) {
            sysDeptList.forEach(sysDept -> {
                StatPeopleDto statPeopleDto = new StatPeopleDto();
                //查询一级单位在位人数
                int oneReignCount = peopleMapper.selectReginCountByDeptId(sysDept.getDeptId());
                //查询一级单位离位人数
                int oneReignCount2 = peopleMapper.selectReginCount2ByDeptId(sysDept.getDeptId());
                //查询二级单位
                List<SysDept> deptTwoList = getDeptTwoList(sysDept);
                //单位在位总人数
                int twoReignCount = 0;
                int twoOutReignCount = 0;
                for (SysDept dept : deptTwoList){
                    //二级单位在位人数
                    twoReignCount = peopleMapper.selectReginCountByDeptId(dept.getDeptId());
                    //查询单位离位人数
                    twoOutReignCount = peopleMapper.selectReginCount2ByDeptId(dept.getDeptId());
                }
                //单位在位总人数
                int reignCount = oneReignCount + twoReignCount;
                //单位离位总人数
                int outRcount = oneReignCount2 + twoOutReignCount;
                if (reignCount > 0 || outRcount > 0){
                    statPeopleDto.setIsBz(0);
                    statPeopleDto.setDwmc(sysDept.getDeptName());
                    statPeopleDto.setZwrs(reignCount);
                    statPeopleDto.setLwrs(outRcount);
                    statPeopleDto.setIsBase(0);
                    statPeopleDto.setNullData(false);
                    statPeopleDtoList.add(statPeopleDto);
                }
            });
        }
        //合计汇总信息
        Map<String,List<StatPeopleDto>> listMap = new HashMap<>(16);
        StatPeopleDto peopleDto = new StatPeopleDto();
        peopleDto.setIsBz(1);
        peopleDto.setIsBase(0);
        peopleDto.setDwmc(sysDeptTop.getDeptName());
        peopleDto.setNullData(false);
        //在位总人数
        int totalReignCount = statPeopleDtoList.stream().mapToInt(StatPeopleDto::getZwrs).sum();
        int totalOutCount = statPeopleDtoList.stream().mapToInt(StatPeopleDto::getLwrs).sum();
        peopleDto.setZwrs(totalReignCount);
        peopleDto.setLwrs(totalOutCount);
        statPeopleDtoList.add(peopleDto);
        listMap.put(sysDeptTop.getDeptName(),statPeopleDtoList);
        return listMap;
    }
    
    
    /**
     * 查询人员离位状况信息
     */
    @Override
    public Map<String,List<StatOutPeopleDto>> statOutReignPerson() {
        List<StatOutPeopleDto> statOutPeopleDtoList = new ArrayList<>();
        //查询顶级单位
        SysDept sysDeptTop = deptMapper.selectTopDept(0L);
        List<Long> deptId= new ArrayList<>();
        //查询所有一级单位
        List<SysDept> sysDeptList = getOneDeptList(sysDeptTop);
        if (CollectionUtil.isNotEmpty(sysDeptList) && sysDeptList.size() > 0) {
            sysDeptList.forEach(sysDept -> {
                deptId.add(sysDept.getDeptId());
                //查询离位人员状态
                List<StatOutPeopleDto> peopleReginList = peopleMapper.selectReginByDeptId(sysDept.getDeptId());
                //查询二级单位
                List<SysDept> deptTwoList = getDeptTwoList(sysDept);
                for (SysDept dept : deptTwoList){
                    deptId.add(dept.getDeptId());
                    //查询离位人员状态
                    List<StatOutPeopleDto> peopleRegin2List = peopleMapper.selectReginByDeptId(dept.getDeptId());
                    peopleReginList.addAll(peopleRegin2List);
                }
                peopleReginList.forEach(people->{
                    StatOutPeopleDto outPeopleDto = new StatOutPeopleDto();
                    outPeopleDto.setLwrs(people.getCount());
                    outPeopleDto.setLwlx(DictUtils.getDictLabel("people_state",String.valueOf(people.getReign())));
                    outPeopleDto.setDwmc(sysDept.getDeptName());
                    outPeopleDto.setIsBase(0);
                    outPeopleDto.setNullData(false);
                    outPeopleDto.setIsBz(0);
                    statOutPeopleDtoList.add(outPeopleDto);
                });
            });
        }
        // 数据汇总
        //查询离位状态和人数
        List<StatOutPeopleDto> peopleReginCountList = peopleMapper.selectReginCount(deptId);
        peopleReginCountList.forEach(item->{
            StatOutPeopleDto outPeopleDto = new StatOutPeopleDto();
            outPeopleDto.setLwrs(item.getCount());
            outPeopleDto.setLwlx(DictUtils.getDictLabel("people_state",String.valueOf(item.getReign())));
            outPeopleDto.setDwmc(sysDeptTop.getDeptName());
            outPeopleDto.setIsBase(0);
            outPeopleDto.setNullData(false);
            outPeopleDto.setIsBz(1);
            statOutPeopleDtoList.add(outPeopleDto);
        });
        Map<String,List<StatOutPeopleDto>> listMap = new HashMap<>(16);
        listMap.put(sysDeptTop.getDeptName(),statOutPeopleDtoList);
        return listMap;
    }
    
    /**
     * 年龄分布统计
     */
    @Override
    public Map<String,List<StatPersonAgeDto>> statAgePerson() {
        //查询顶级单位
        SysDept sysDeptTop = deptMapper.selectTopDept(0L);
        //查询所有一级单位
        List<SysDept> sysDeptList = getOneDeptList(sysDeptTop);
        List<StatPersonAgeDto> statPersonAgeDtoList = new ArrayList<>();
        Set<Long> deptId = new HashSet<>();
        //查询一级单位下所有子单位ID
        sysDeptList.forEach(dept->{
            List<Long> deptIdList = deptMapper.selectDeptTwoById(dept.getDeptId());
            deptIdList.add(dept.getDeptId());
            deptId.add(dept.getDeptId());
            //查询年龄分布
            List<StatPersonAgeDto> personAgeDtoList = peopleMapper.selectPersonAgeQjList(deptIdList);
            personAgeDtoList.forEach(ageStat->{
                StatPersonAgeDto personAgeDto = new StatPersonAgeDto();
                personAgeDto.setDwmc(dept.getDeptName());
                personAgeDto.setIsBase(0);
                personAgeDto.setNullData(false);
                personAgeDto.setNlqj(ageStat.getNlqj());
                personAgeDto.setRs(ageStat.getRs());
                personAgeDto.setIsBz(0);
                statPersonAgeDtoList.add(personAgeDto);
            });
        });
        //数据汇总
        List<Long> deptIdList = deptMapper.selectDeptTwoListById(deptId);
        deptId.addAll(deptIdList);
        //查询所有数据年龄分布
        List<StatPersonAgeDto> personAgeDtoAllList = peopleMapper.selectPersonAgeQjList(new ArrayList<>(deptId));
        personAgeDtoAllList.forEach(ageStat->{
            StatPersonAgeDto personAgeDto = new StatPersonAgeDto();
            personAgeDto.setDwmc(sysDeptTop.getDeptName());
            personAgeDto.setIsBase(0);
            personAgeDto.setNullData(false);
            personAgeDto.setNlqj(ageStat.getNlqj());
            personAgeDto.setRs(ageStat.getRs());
            personAgeDto.setIsBz(1);
            statPersonAgeDtoList.add(personAgeDto);
        });
        Map<String,List<StatPersonAgeDto>> listMap = new HashMap<>(16);
        listMap.put(sysDeptTop.getDeptName(),statPersonAgeDtoList);
        return listMap;
    }
    
    /**
     * 政治面貌统计
     */
    @Override
    public Map<String,List<StatPersonZzmmDto>> statZzmmPerson() {
        List<StatPersonZzmmDto> statPersonZzmmDtoList = new ArrayList<>();
        //查询顶级单位
        SysDept sysDeptTop = deptMapper.selectTopDept(0L);
        Set<Long> deptId = new HashSet<>();
        //查询所有一级单位
        List<SysDept> sysDeptList = getOneDeptList(sysDeptTop);
        if (CollectionUtil.isNotEmpty(sysDeptList) && sysDeptList.size() > 0) {
            sysDeptList.forEach(sysDept -> {
                List<Long> deptIdList = deptMapper.selectDeptTwoById(sysDept.getDeptId());
                deptIdList.add(sysDept.getDeptId());
                deptId.add(sysDept.getDeptId());
                //查询政治面貌分组数据
                List<StatPersonZzmmDto> personZzmmDtoList = peopleMapper.selectZzmmAndCountWithDeptId(deptIdList);
                personZzmmDtoList.forEach(zzmm->{
                    StatPersonZzmmDto personZzmmDto = new StatPersonZzmmDto();
                    personZzmmDto.setDwmc(sysDept.getDeptName());
                    personZzmmDto.setIsBase(0);
                    personZzmmDto.setRs(zzmm.getRs());
                    personZzmmDto.setZzmm(DictUtils.getDictLabel("Caucus",zzmm.getZzmm()));
                    personZzmmDto.setIsBz(0);
                    statPersonZzmmDtoList.add(personZzmmDto);
                });
            });
        }
        //汇总数据
        List<Long> deptIdList = deptMapper.selectDeptTwoListById(deptId);
        deptId.addAll(deptIdList);
        //查询政治面貌分组数据
        List<StatPersonZzmmDto> personZzmmDtoAllList = peopleMapper.selectZzmmAndCountAll(deptId);
        personZzmmDtoAllList.forEach(zzmm->{
            StatPersonZzmmDto personZzmmDto = new StatPersonZzmmDto();
            personZzmmDto.setDwmc(sysDeptTop.getDeptName());
            personZzmmDto.setIsBase(0);
            personZzmmDto.setRs(zzmm.getRs());
            personZzmmDto.setZzmm(DictUtils.getDictLabel("Caucus",zzmm.getZzmm()));
            personZzmmDto.setIsBz(1);
            statPersonZzmmDtoList.add(personZzmmDto);
        });
        Map<String,List<StatPersonZzmmDto>> listMap = new HashMap<>(16);
        listMap.put(sysDeptTop.getDeptName(),statPersonZzmmDtoList);
        return listMap;
    }
    
    /**
     * 基层值班统计
     */
    @Override
    public Map<String, List<ApiStatDutyDto>> statDuty() {
        //查询顶级单位
        SysDept sysDeptTop = deptMapper.selectTopDept(0L);
        //查询所有一级单位ID
        List<Long> deptIdList = deptMapper.selectOneDeptIdList(sysDeptTop.getDeptId());
        String date = DateUtils.getDate();
        date = "2022-11-17";
        //查询当天值班数据
        List<ApiStatDutyDto> statDutyDtoList = grassdutyMapper.selectDutyWithToday(date,deptIdList);
        statDutyDtoList.forEach(item->{
            if (sysDeptTop.getDeptName().equals(item.getDwmc())){
                item.setIsBz(1);
            }else {
                item.setIsBz(0);
            }
        });
        Map<String,List<ApiStatDutyDto>> listMap = new HashMap<>(16);
        listMap.put(sysDeptTop.getDeptName(),statDutyDtoList);
        return listMap;
    }
    
    /**
     * 大队值班统计
     */
    @Override
    public Map<String, List<ApiDaduiDutyDto>> statDaduiDuty() {
        String date = DateUtils.getDate();
        date = "2022-11-22";
        List<BdglTeamduty> teamdutyList = teamdutyMapper.selectTeamdutyList(date);
        List<ApiDaduiDutyDto> daduiDutyDtoList = new ArrayList<>();
        teamdutyList.forEach(item->{
            ApiDaduiDutyDto daduiDutyDto = new ApiDaduiDutyDto();
            daduiDutyDto.setZbTime(DateUtils.format(item.getAttendedTime(),DateUtils.YYYY_MM_DD));
            daduiDutyDto.setZbszNameAndPhone(item.getChiefDutyName()+","+item.getChiefDutyPhone());
            daduiDutyDto.setZzzbyNameAndPhone(item.getCombatDutyName()+","+item.getCombatDutyPhone());
            daduiDutyDto.setTxNameAndPhone(item.getDutyOffierName()+","+item.getDutyOffierPhone());
            daduiDutyDto.setJyzbyNameAndPhone(item.getConfidentialName()+","+item.getConfidentialPhone());
            daduiDutyDto.setZzbNameAndPhone(item.getPoliticalName()+","+item.getPoliticalPhone());
            daduiDutyDto.setBzbNameAndPhone(item.getGuaranteeName()+","+item.getGuaranteePhone());
            daduiDutyDto.setZbtxyNameAndPhone(item.getMessengerName()+","+item.getMessengerPhone());
            daduiDutyDto.setIsBz(1);
            daduiDutyDtoList.add(daduiDutyDto);
        });
        //查询顶级单位
        SysDept sysDeptTop = deptMapper.selectTopDept(0L);
        Map<String,List<ApiDaduiDutyDto>> listMap = new HashMap<>(16);
        listMap.put(sysDeptTop.getDeptName(),daduiDutyDtoList);
        return listMap;
    }
    
    /**
     * 一级单位
     */
    private List<SysDept> getOneDeptList(SysDept sysDeptTop) {
        SysDept sd = new SysDept();
        sd.setParentId(sysDeptTop.getDeptId());
        sd.setStatus("0");
        //查询所有一级单位
        return deptMapper.selectDeptList(sd);
    }
    
    /**
     * 二级单位
     */
    private List<SysDept> getDeptTwoList(SysDept sysDept){
        //查询二级单位
        SysDept dept = new SysDept();
        dept.setParentId(sysDept.getDeptId());
        dept.setStatus("0");
        return deptMapper.selectDeptList(dept);
    }
}
