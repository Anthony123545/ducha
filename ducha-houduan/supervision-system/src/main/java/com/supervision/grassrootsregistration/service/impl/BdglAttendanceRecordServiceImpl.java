package com.supervision.grassrootsregistration.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.supervision.common.core.redis.RedisCache;
import com.supervision.common.exception.ServiceException;
import com.supervision.common.utils.DateUtils;
import com.supervision.common.utils.http.HttpUtils;
import com.supervision.grassrootsregistration.domain.BdglAttendanceRecord;
import com.supervision.grassrootsregistration.domain.BdglEmployee;
import com.supervision.grassrootsregistration.domain.BdglIpaddressConfig;
import com.supervision.grassrootsregistration.mapper.BdglAttendanceRecordMapper;
import com.supervision.grassrootsregistration.mapper.BdglIpaddressConfigMapper;
import com.supervision.grassrootsregistration.service.IBdglAttendanceRecordService;
import com.supervision.grassrootsregistration.service.IBdglEmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 考勤记录Service业务层处理
 *
 * @author ruoyi
 * @date 2022-10-20
 */
@Service
public class BdglAttendanceRecordServiceImpl implements IBdglAttendanceRecordService {

    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${iclock.key}")
    private String iclockKey;

    @Resource
    private BdglAttendanceRecordMapper bdglAttendanceRecordMapper;

    @Resource
    private BdglIpaddressConfigMapper bdglIpaddressConfigMapper;

    @Resource
    private RedisCache redisCache;
    @Autowired
    private IBdglEmployeeService bdglEmployeeService;


    /**
     * 查询考勤记录
     *
     * @param id 考勤记录主键
     * @return 考勤记录
     */
    @Override
    public BdglAttendanceRecord selectBdglAttendanceRecordById(Long id)
    {
        return bdglAttendanceRecordMapper.selectBdglAttendanceRecordById(id);
    }

    /**
     * 查询考勤记录列表
     *
     * @param bdglAttendanceRecord 考勤记录
     * @return 考勤记录
     */
    @Override
    public List<BdglAttendanceRecord> selectBdglAttendanceRecordList(BdglAttendanceRecord bdglAttendanceRecord)
    {
        return bdglAttendanceRecordMapper.selectBdglAttendanceRecordList(bdglAttendanceRecord);
    }

    /**
     * 新增考勤记录
     *
     * @param bdglAttendanceRecord 考勤记录
     * @return 结果
     */
    @Override
    public int insertBdglAttendanceRecord(BdglAttendanceRecord bdglAttendanceRecord)
    {
        bdglAttendanceRecord.setCreateTime(DateUtils.getNowDate());
        return bdglAttendanceRecordMapper.insertBdglAttendanceRecord(bdglAttendanceRecord);
    }

    /**
     * 修改考勤记录
     *
     * @param bdglAttendanceRecord 考勤记录
     * @return 结果
     */
    @Override
    public int updateBdglAttendanceRecord(BdglAttendanceRecord bdglAttendanceRecord)
    {
        return bdglAttendanceRecordMapper.updateBdglAttendanceRecord(bdglAttendanceRecord);
    }

    /**
     * 批量删除考勤记录
     *
     * @param ids 需要删除的考勤记录主键
     * @return 结果
     */
    @Override
    public int deleteBdglAttendanceRecordByIds(Long[] ids)
    {
        return bdglAttendanceRecordMapper.deleteBdglAttendanceRecordByIds(ids);
    }

    /**
     * 删除考勤记录信息
     *
     * @param id 考勤记录主键
     * @return 结果
     */
    @Override
    public int deleteBdglAttendanceRecordById(Long id)
    {
        return bdglAttendanceRecordMapper.deleteBdglAttendanceRecordById(id);
    }

    @Override
    public List<BdglAttendanceRecord> getAttendanceRecordsListByMultipleIp(BdglAttendanceRecord workAttendance,int type){
        BdglIpaddressConfig bdglIpaddressConfig = new BdglIpaddressConfig();
        if (workAttendance!=null){
            bdglIpaddressConfig.setName(workAttendance.getDeptname());
        }
        List<BdglIpaddressConfig> bdglIpaddressConfigList = bdglIpaddressConfigMapper.selectBdglIpaddressConfigList(bdglIpaddressConfig);
        List<BdglAttendanceRecord> allList = new ArrayList<>();
        for (BdglIpaddressConfig ipaddressConfig : bdglIpaddressConfigList) {
            List<BdglAttendanceRecord> recordList = getAttendanceRecords(ipaddressConfig, workAttendance, type);
            if (type==2){
                allList.addAll(recordList);
            }
        }
        return allList;
    }
    
    @Override
    public List<BdglAttendanceRecord> selectBdglAttendanceRecordLists(Integer stateno) {
        return bdglAttendanceRecordMapper.selectBdglAttendanceRecordLists(stateno);
    }
    
    @Override
    public BdglAttendanceRecord selectBdglAttendanceRecords(String pin) {
        return bdglAttendanceRecordMapper.selectBdglAttendanceRecords(pin);
    }
    
    @Override
    public BdglAttendanceRecord select(String departureTime) {
        return bdglAttendanceRecordMapper.select(departureTime);
    }
    
    @Override
    public BdglAttendanceRecord selectBdglAttendanceRecord(BdglAttendanceRecord bdglAttendanceRecord1) {
        return bdglAttendanceRecordMapper.selectBdglAttendanceRecord(bdglAttendanceRecord1);
    }
    
    @Override
    public void getemployeeinfo(){

        String geturl="http://127.0.0.1:85/api/v2/employee/get/?key=2633p84hnnlavzcy9afjcbtj1jdnega0ftscjkka6h7a";
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("offduty",2);
        String param = JSONObject.toJSONString(map);
        logger.info("请求参数："+param);
        String result = HttpUtils.sendPost(geturl,param);
        JSONObject jsonObject = JSONObject.parseObject(result);
        JSONObject dataObject = jsonObject.getJSONObject("data");
        JSONArray jsonArray = dataObject.getJSONArray("items");
        List<BdglEmployee> jsonArraylist = jsonArray.toJavaList(BdglEmployee.class);
        BdglEmployee bdglEmployee = new BdglEmployee();
        List<BdglEmployee> BdglEmployeeList = bdglEmployeeService.selectBdglEmployeeList(bdglEmployee);

        jsonArraylist.removeIf(s -> BdglEmployeeList.contains(s));
        if(jsonArraylist.size()>0){
            for (BdglEmployee e : jsonArraylist) {
                if(bdglEmployeeService.selectBdglEmployeeByPin(e.getPin())==null){
                    bdglEmployeeService.insertBdglEmployee(e);
                }

            }
        }
    }

    public List<BdglAttendanceRecord> getAttendanceRecords(BdglIpaddressConfig bdglIpaddressConfig,BdglAttendanceRecord workAttendance,int type) {
        //设置IP地址
        String getRecordsUrl="http://127.0.0.1:85/api/v2/transaction/get/?key=2633p84hnnlavzcy9afjcbtj1jdnega0ftscjkka6h7a";
//        if (bdglIpaddressConfig.getIp().contains("http")){
//            getRecordsUrl=bdglIpaddressConfig.getIp()+"/api/v2/transaction/get?key="+iclockKey;
//        }else {
//            getRecordsUrl="http://"+bdglIpaddressConfig.getIp()+"/api/v2/transaction/get?key="+iclockKey;
//        }

        //先从redis里获取id值，如果存在，从id值开始查询，不存在，从1开始
        Long bdglAttendanceRecordNumber = (Long) redisCache.getCacheObject(bdglIpaddressConfig.getId()+"-bdglAttendanceRecordNumber");
        Map<String,Object> map = new HashMap<String,Object>();
        LocalDateTime currentDataTime = LocalDateTime.now();
        LocalDateTime endTime=currentDataTime.withHour(23).withMinute(59);
        LocalDateTime starttime =currentDataTime.withHour(00).withMinute(00);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startformater=starttime.format(formatter);
        String endformater=endTime.format(formatter);
        map.put("starttime", startformater);
        map.put("endtime", endformater);

//        map.put("starttime", "2023-02-01 00:00:00");
//       map.put("endtime", "2023-02-27 00:00:00");
        long idValue= 1L;
        long maxId=0;
        if (bdglAttendanceRecordNumber!=null){
            idValue=bdglAttendanceRecordNumber+1;
            maxId=bdglAttendanceRecordNumber;
        }
        int number=200;
        List<BdglAttendanceRecord> exportList = new ArrayList<>();
        while (true){

            String param = JSONObject.toJSONString(map);
            logger.info("请求参数："+param);
            String result = HttpUtils.sendPost(getRecordsUrl, param);
            JSONObject jsonObject = JSONObject.parseObject(result);
            if(jsonObject==null){
                return null;
            }
            System.out.println("考勤返回："+jsonObject.toJSONString());
            int ret = jsonObject.getIntValue("ret");
            if (ret!=0){
                logger.error("获取考勤记录失败："+jsonObject.getString("msg"));
                throw new ServiceException(jsonObject.getString("msg"));
            }
            JSONObject dataObject = jsonObject.getJSONObject("data");
            JSONArray jsonArray = dataObject.getJSONArray("items");
            List<BdglAttendanceRecord> attendanceRecordList = jsonArray.toJavaList(BdglAttendanceRecord.class);


            if (attendanceRecordList.size()==0){
                break;
            }
            int listSize=attendanceRecordList.size();
            if (type==2){
                for (BdglAttendanceRecord bdglAttendanceRecord : attendanceRecordList) {
                    bdglAttendanceRecord.setVerifyString();
                }
                exportList.addAll(attendanceRecordList);
            }

            //查询数据库中记录是否存在
            List<Long> sidList = attendanceRecordList.stream().map(BdglAttendanceRecord::getId).collect(Collectors.toList());
            BdglAttendanceRecord attendanceRecord = new BdglAttendanceRecord();
            attendanceRecord.setSidList(sidList);
            attendanceRecord.setIpAddressId(bdglIpaddressConfig.getId());
            List<BdglAttendanceRecord> selectBdglAttendanceRecordList = bdglAttendanceRecordMapper.selectBdglAttendanceRecordList(attendanceRecord);
            List<Long> selectSidList = selectBdglAttendanceRecordList.stream().map(BdglAttendanceRecord::getSid).collect(Collectors.toList());

            //根据sid判断数据库中如果存在就删掉，不添加到数据库
            attendanceRecordList.removeIf(record -> selectSidList.contains(record.getId()));
            if (attendanceRecordList.size()>0){
                Date nowDate = new Date();
                for (BdglAttendanceRecord bdglAttendanceRecord : attendanceRecordList) {
                    if (bdglAttendanceRecord.getId()>maxId){
                        maxId=bdglAttendanceRecord.getId();
                    }
                    bdglAttendanceRecord.setSid(bdglAttendanceRecord.getId());
                    bdglAttendanceRecord.setId(null);
                    bdglAttendanceRecord.setCreateTime(nowDate);
                    bdglAttendanceRecord.setIpAddressId(bdglIpaddressConfig.getId());
                }
                Map<String,Object> dataMap = new HashMap<>();
                dataMap.put("recordList",attendanceRecordList);
                dataMap.put("record",attendanceRecordList.get(0));
                bdglAttendanceRecordMapper.batchInsertBdglAttendanceRecord(dataMap);
            }
            if (listSize!=number){
                break;
            }
            idValue+=number;
        }
        redisCache.setCacheObject(bdglIpaddressConfig.getId()+"-bdglAttendanceRecordNumber",maxId);
        return exportList;
    }



}
