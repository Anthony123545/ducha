package com.supervision.web.controller.grassrootsregistration;

import com.supervision.common.core.domain.entity.SysDictData;
import com.supervision.grassrootsregistration.domain.BdglAttendanceRecord;
import com.supervision.grassrootsregistration.service.IBdglAttendanceRecordService;
import com.supervision.peopleChuRu.domain.BdglLeave;
import com.supervision.peopleChuRu.domain.BdglLeaveOne;
import com.supervision.peopleChuRu.service.IBdglLeaveOneService;
import com.supervision.peopleChuRu.service.IBdglLeaveService;
import com.supervision.peopleManage.domain.BdglPeople;
import com.supervision.peopleManage.service.IBdglPeopleService;
import com.supervision.system.service.ISysDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

@Configuration
@EnableScheduling
@Component
public class GrassrootsregistrationController {

    @Autowired
    private IBdglAttendanceRecordService bdglAttendanceRecordService;

    @Autowired
    private IBdglLeaveOneService bdglLeaveOneService;

    @Autowired
    private IBdglPeopleService bdglPeopleService;

    @Autowired
    private ISysDictDataService sysDictDataService;

    @Autowired
    private IBdglLeaveService bdglLeaveService;
    
    
    


    /**
     * 定时任务打卡签到
     * 单日请假
     */
    @Scheduled(fixedRate = 90000)
    public void punchIn()
    {
        try{
            System.out.println("执行时间----------------------------------"+ new Date());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //查询请假的人 且离队时间为空的
            List<BdglLeaveOne> bdglLeaveOneList = bdglLeaveOneService.selectBdglLeaveOneListes();
            //查询外出打卡记录   今天打卡且考勤状态是2外出的
            Integer stateno = 2;
            List<BdglAttendanceRecord> bdglAttendanceRecordList = bdglAttendanceRecordService.selectBdglAttendanceRecordLists(stateno);
            for(BdglAttendanceRecord bdglAttendanceRecord1:bdglAttendanceRecordList) {
            	//外出 更新请假表离队时间
                if (bdglAttendanceRecord1.getStateno()==2) {
                	if(bdglAttendanceRecord1.getEname().equals("吴翼飞")) {
                  		 System.out.println("");
                  	}
                	for(BdglLeaveOne bdglLeaveOne1:bdglLeaveOneList) {
                		if(bdglLeaveOne1.getPeopleId()!=null&&!bdglLeaveOne1.getPeopleId().equals("")) {
                			String aa=bdglLeaveOne1.getPeopleId();
                			Long bb=Long.valueOf(aa);
                			BdglPeople bdglPeople1=new BdglPeople(); 
                			
                			 bdglPeople1 = bdglPeopleService.selectBdglPeopleById(Long.valueOf(bdglLeaveOne1.getPeopleId()));
                		 
                            if (bdglPeople1!=null&&bdglPeople1.getName().equals(bdglAttendanceRecord1.getEname())){
                            	try{
                            		if(bdglPeople1.getName().equals("吴翼飞")) {
                               		 System.out.println("");
                               	}
                            		String time=sdf.format(bdglAttendanceRecord1.getChecktime());
                            	bdglLeaveOne1.setDepartureTime(sdf.format(bdglAttendanceRecord1.getChecktime()));
                                bdglLeaveOneService.updateBdglLeaveOne(bdglLeaveOne1);
                            	 } catch (Exception e) {
                     	        	System.out.println(e);
                     	        };
                                BdglPeople bdglPeople = new BdglPeople();
                                bdglPeople.setId(Long.valueOf(bdglLeaveOne1.getPeopleId()));
                                bdglPeople.setReign(Integer.valueOf(bdglLeaveOne1.getLeaveType()));
                                bdglPeopleService.updateBdglPeople(bdglPeople);
                           
                            }
                		}
                		
                	}
//                    bdglLeaveOneList.forEach(bd -> {
//                        
//                    });
                }
            }
			/*
			 * bdglAttendanceRecordList.forEach(p -> { //外出 更新请假表离队时间 if (p.getStateno()==2)
			 * { bdglLeaveOneList.forEach(bd -> { BdglPeople bdglPeople1 =
			 * bdglPeopleService.selectBdglPeopleById(Long.valueOf(bd.getPeopleId())); if
			 * (bdglPeople1.getName().equals(p.getEname())){
			 * bd.setDepartureTime(sdf.format(p.getChecktime()));
			 * bdglLeaveOneService.updateBdglLeaveOne(bd); BdglPeople bdglPeople = new
			 * BdglPeople(); bdglPeople.setId(Long.valueOf(bd.getPeopleId()));
			 * bdglPeople.setReign(Integer.valueOf(bd.getLeaveType()));
			 * bdglPeopleService.updateBdglPeople(bdglPeople); } }); } });
			 */
            //查询有外出时间没有回归时间打卡记录
            List<BdglLeaveOne> bdglLeaveOnelist = bdglLeaveOneService.selectBdglLeaveOneLists();
            System.out.println(bdglLeaveOnelist);
            System.out.println(bdglLeaveOnelist);
            System.out.println(bdglLeaveOnelist);
            System.out.println(bdglLeaveOnelist);
            System.out.println(bdglLeaveOnelist);
            System.out.println(bdglLeaveOnelist);
            System.out.println(bdglLeaveOnelist);
            System.out.println(bdglLeaveOnelist);
            System.out.println(bdglLeaveOnelist);
           
            //bdglLeaveOnelist.forEach(bd -> {
            	for(BdglLeaveOne BdglLeaveOne1:bdglLeaveOnelist ) {
            		if(BdglLeaveOne1.getPeopleId().equals("2477")) {
            			System.out.println("");
            		}
            		
            		//查询人名
                    BdglPeople bdglPeople1 = bdglPeopleService.selectBdglPeopleById(Long.valueOf(BdglLeaveOne1.getPeopleId()));
                    //根据离队时间查询设备编号
                    BdglAttendanceRecord bdglAttendanceRecord = bdglAttendanceRecordService.select(BdglLeaveOne1.getDepartureTime());
                    if (bdglAttendanceRecord != null) {
                        //查询设备号对应的字典项
                        SysDictData sysDictData = new SysDictData();
                        sysDictData.setDictType("sys_equipment");
                        List<SysDictData> sysDictData1 = sysDictDataService.selectDictDataList(sysDictData);
                        int a = 0;
                        for (SysDictData dd : sysDictData1) {
                            if (bdglAttendanceRecord.getSn().equals(dd.getDictLabel())){
                                a = Integer.parseInt(dd.getDictValue())+1;
                                sysDictData.setDictValue(a+"");
                                break;
                            }
                        }
                        List<SysDictData> sysDictData2 = sysDictDataService.selectDictDataList(sysDictData);
                        //查询归队人的最后一次打卡时间
                        BdglAttendanceRecord bdglAttendanceRecord1 = new BdglAttendanceRecord();
                        bdglAttendanceRecord1.setSn(sysDictData2.get(0).getDictLabel());
                        bdglAttendanceRecord1.setEname(bdglPeople1.getName());
                        try {       
                        	Date date=sdf.parse(BdglLeaveOne1.getDepartureTime());
                        	String time=sdf.format(date);
                        	bdglAttendanceRecord1.setChecktime(sdf.parse(BdglLeaveOne1.getDepartureTime()));
    					} catch (Exception e) {
    						// TODO: handle exception
    						e.printStackTrace();
    					}
                        
                       
                        BdglAttendanceRecord bdglAttendanceRecord2 = bdglAttendanceRecordService.selectBdglAttendanceRecord(bdglAttendanceRecord1);
                        if(bdglAttendanceRecord2!=null){
                        	
                        	BdglLeaveOne1.setReturnTime(sdf.format(bdglAttendanceRecord2.getChecktime()));
                             bdglLeaveOneService.updateBdglLeaveOne(BdglLeaveOne1);
                             BdglPeople bdglPeople = new BdglPeople();
                             bdglPeople.setId(bdglPeople1.getId());
                             bdglPeople.setReign(0);
                             bdglPeopleService.updateBdglPeople(bdglPeople);
                        }                   
                    }
            	}
                
            //});
        } catch (Exception e) {
        	System.out.println(e);
        };
    }

    /**
     * 定时任务打卡签到  0 0 23 1/1 * ?
     * 多日请假
     */
    @Scheduled(cron = " 0 0 23 1/1 * ?")
    public void punchInMultiplayer()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //查询当天请假的人
        List<BdglLeave> bdglLeaveList = bdglLeaveService.selectBdglLeaveListes();
        //查询外出打卡记录
        Integer stateno = 2;
        List<BdglAttendanceRecord> bdglAttendanceRecordList = bdglAttendanceRecordService.selectBdglAttendanceRecordLists(stateno);
        bdglAttendanceRecordList.forEach(p -> {
            //外出 更新请假表离队时间
            if (p.getStateno()==2) {
                bdglLeaveList.forEach(bd -> {
                    //三级审批人人通过后
                    if (bd.getPeopleId1() != null) {
                        if (("1,3").contains(bd.getStatus1() + "")) {
                        } else {
                            return;
                        }
                    }
                    if (bd.getPeopleId2() != null) {
                        if (("1,3").contains(bd.getStatus2() + "")) {
                        } else {
                            return;
                        }
                    }
                    if (bd.getPeopleId3() != null) {
                        if (("1,3").contains(bd.getStatus3() + "")) {
                        } else {
                            return;
                        }
                    }
                    if (bd.getPeopleId4() != null) {
                        if (("1,3").contains(bd.getStatus4() + "")) {
                        } else {
                            return;
                        }
                    }
                    BdglPeople bdglPeople1 = bdglPeopleService.selectBdglPeopleById(Long.valueOf(bd.getPeopleId()));
                    if (bdglPeople1.getName().equals(p.getEname())){
                        bd.setDepartureTime(sdf.format(p.getChecktime()));
                        bdglLeaveService.updateBdglLeave(bd);
                        BdglPeople bdglPeople = new BdglPeople();
                        bdglPeople.setId(Long.valueOf(bd.getPeopleId()));
                        bdglPeople.setReign(Integer.valueOf(bd.getLeaveType()));
                        bdglPeopleService.updateBdglPeople(bdglPeople);
                    }
                });
            }
        });
        //查询有外出时间没有回归时间打卡记录
        List<BdglLeave> bdglLeavelist = bdglLeaveService.selectBdglLeaveLists();
        bdglLeavelist.forEach(bd -> {
            //查询人名
            BdglPeople bdglPeople1 = bdglPeopleService.selectBdglPeopleById(Long.valueOf(bd.getPeopleId()));
            //根据离队时间查询设备编号
            BdglAttendanceRecord bdglAttendanceRecord = bdglAttendanceRecordService.select(bd.getDepartureTime());
            if (bdglAttendanceRecord != null) {
                //查询设备号对应的字典项
                SysDictData sysDictData = new SysDictData();
                sysDictData.setDictType("sys_equipment");
                List<SysDictData> sysDictData1 = sysDictDataService.selectDictDataList(sysDictData);
                int a = 0;
                for (SysDictData dd : sysDictData1) {
                    if (bdglAttendanceRecord.getSn().equals(dd.getDictLabel())){
                        a = Integer.parseInt(dd.getDictValue()) + 1;
                        sysDictData.setDictValue(a+"");
                        break;
                    }
                }
                List<SysDictData> sysDictData2 = sysDictDataService.selectDictDataList(sysDictData);
                //查询归队人的最后一次打卡时间
                BdglAttendanceRecord bdglAttendanceRecord1 = new BdglAttendanceRecord();
                bdglAttendanceRecord1.setSn(sysDictData2.get(0).getDictLabel());
                try {                    	
                	bdglAttendanceRecord1.setChecktime(sdf.parse(bd.getDepartureTime()));
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
                bdglAttendanceRecord1.setEname(bdglPeople1.getName());
                BdglAttendanceRecord bdglAttendanceRecord2 = bdglAttendanceRecordService.selectBdglAttendanceRecord(bdglAttendanceRecord1);
                if(bdglAttendanceRecord2!=null){
                	 bd.setReturnTime(sdf.format(bdglAttendanceRecord2.getChecktime()));
                     bd.setIsReturn("1");
                     bdglLeaveService.updateBdglLeave(bd);
                     BdglPeople bdglPeople = new BdglPeople();
                     bdglPeople.setId(bdglPeople1.getId());
                     bdglPeople.setReign(0);
                     bdglPeopleService.updateBdglPeople(bdglPeople);
                }
               
            }
        });
    }

    /**
     * 定时获取门禁人员数据
     */
    @Scheduled(cron = " 0 0/5 * * * ?")
    public void getemployeeinfo(){
        bdglAttendanceRecordService.getemployeeinfo();
    }
}
