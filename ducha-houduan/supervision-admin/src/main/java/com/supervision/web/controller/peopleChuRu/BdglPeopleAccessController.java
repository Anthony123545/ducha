package com.supervision.web.controller.peopleChuRu;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.PostConstruct;

import com.alibaba.fastjson.JSONObject;
import com.sun.xml.internal.bind.v2.TODO;
import com.supervision.doormanManage.service.IBdglVisitService;
import com.supervision.peopleChuRu.domain.BdglLeave;
import com.supervision.peopleChuRu.domain.BdglLeaveOne;
import com.supervision.peopleChuRu.domain.BdglVisitorEnter;
import com.supervision.peopleChuRu.service.IBdglLeaveOneService;
import com.supervision.peopleChuRu.service.IBdglLeaveService;
import com.supervision.peopleChuRu.service.IBdglVisitorEnterService;
import com.supervision.peopleChuRu.service.impl.BdglVisitorEnterServiceImpl;
import com.supervision.peopleManage.domain.Visitor;
import com.supervision.peopleManage.service.IVisitorService;
import com.supervision.web.Hikvision.peopleDoor.web.controller.FaceInfoManageController;
import com.supervision.web.Hikvision.peopleDoor.web.controller.PeopleInfoManageController;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.supervision.common.annotation.Log;
import com.supervision.common.core.controller.BaseController;
import com.supervision.common.core.domain.AjaxResult;
import com.supervision.common.enums.BusinessType;
import com.supervision.peopleChuRu.domain.BdglPeopleAccess;
import com.supervision.peopleChuRu.service.IBdglPeopleAccessService;
import com.supervision.peopleManage.domain.BdglPeople;
import com.supervision.peopleManage.service.IBdglPeopleService;

import cn.hutool.core.bean.BeanUtil;

import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 人员出入记录Controller
 * 
 * @author supervision
 * @date 2023-07-01
 */
@Transactional()
@RestController
@RequestMapping("/peopleChuRu/access")
public class BdglPeopleAccessController extends BaseController implements ApplicationContextAware
{
    @Autowired
    private IBdglPeopleAccessService bdglPeopleAccessService;
    
    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    private IBdglPeopleService bdglPeopleService;

    @Autowired
    private IBdglVisitorEnterService bdglVisitorEnterService;

    @Autowired
    private IVisitorService visitorService;

    @Autowired
    private IBdglLeaveOneService bdglLeaveOneService;

    @Autowired
    private IBdglLeaveService bdglLeaveService;

    @Autowired
    private BdglLeaveController bdglLeaveController;

    @Autowired
    private BdglVisitorEnterController bdglVisitorEnterController;
    
    // 应用上下文，用于获取当前bean的代理实例
    private ApplicationContext applicationContext;
    
    // 当前bean的代理实例
    private BdglPeopleAccessController self;

    @Autowired
    private PeopleInfoManageController peopleInfoManageController;
    @Autowired
    private FaceInfoManageController faceInfoManageController;

    //使用redis map存储所有人员的是否在营信息.如果人员不存在就创建
    @PostConstruct
    public void init(){
        // 初始化代理实例
        self = applicationContext.getBean(BdglPeopleAccessController.class);

        bdglPeopleService.selectBdglPeopleList(null).forEach(bdglPeopleBorrow -> {
            if(!redisTemplate.opsForHash().hasKey("peopleStatus",String.valueOf(bdglPeopleBorrow.getId()))){
                redisTemplate.opsForHash().put("peopleStatus",String.valueOf(bdglPeopleBorrow.getId()),1);
            }
        });

        visitorService.selectVisitorList(null).forEach(visitor -> {
            if(!redisTemplate.opsForHash().hasKey("visitorStatus",String.valueOf(visitor.getId()))){
                redisTemplate.opsForHash().put("visitorStatus",String.valueOf(visitor.getId()),0);
            }
        });
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 查询人员是否具有归离队权限（包含单日、多日请假进出、访客进出）
     */

    @GetMapping("/hasPower/")
    public Boolean hasPower(@RequestParam("employeeNumber") String employeeNumber,@RequestParam("status") Integer status) {
        Boolean flag = false;
        //  获取当前时间
        LocalDateTime now = LocalDateTime.now();
        if (employeeNumber.startsWith("VISITOR_")){
            Visitor visitor = visitorService.selectVisitorByEmployeeNumber(employeeNumber);
            List<BdglVisitorEnter> bdglVisitorEnters = bdglVisitorEnterService.selectBdglVisitorEnterByPeopleId(visitor.getId());
            for(BdglVisitorEnter bdglVisitorEnter : bdglVisitorEnters) {
                if(bdglVisitorEnter.getStatus1().equals(1) && bdglVisitorEnter.getStatus2().equals(1) && bdglVisitorEnter.getStatus3().equals(1) ) {
                    LocalDateTime startTime = bdglVisitorEnter.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                    LocalDateTime endTime = bdglVisitorEnter.getEndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() ;
                    if(startTime.isBefore(now) && endTime.isAfter(now) ) {
                        switch (status){
                            case 0:
                                //TODO 出所
                                if(bdglVisitorEnter.getIsReturn().equals("0") && bdglVisitorEnter.getRejoin() == 0){
                                    flag = true;
                                }
                                break;
                            case 1:
                                //TODO 入所
                                if(bdglVisitorEnter.getIsReturn().equals("0") && bdglVisitorEnter.getRejoin() == 1){
                                    flag = true;
                                }
                                break;
                            default:
                                break;

                        }

                    }
                }
            }
        }else {
            BdglPeople bdglPeople = bdglPeopleService.selectBdglPeopleByEmployeeNumber(employeeNumber);
            // 查看是否有单日请假
            List<BdglLeaveOne> bdglLeaveOnes = bdglLeaveOneService.selectBdglLeaveOneByPeopleId(bdglPeople.getId());
            for(BdglLeaveOne bdglLeaveOne : bdglLeaveOnes) {
                LocalDateTime startTime = bdglLeaveOne.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                LocalDateTime endTime = bdglLeaveOne.getEndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                if(startTime.isBefore(now) && endTime.isAfter(now) ) {
                    switch (status){
                        case 0:
                            //TODO 出所
                            if(bdglLeaveOne.getIsReturn().equals("0") && bdglLeaveOne.getRejoin() == 1){
                                flag = true;
                            }
                            break;
                        case 1:
                            //TODO 入所
                            if(bdglLeaveOne.getIsReturn().equals("0") && bdglLeaveOne.getRejoin() == 0){
                                flag = true;
                            }
                            break;
                        default:
                            break;

                    }
                }

            }
            // 查看是否有多日请假
            List<BdglLeave> bdglLeaves = bdglLeaveService.selectBdglLeaveByPeopleId(bdglPeople.getId());
            for(BdglLeave bdglLeave : bdglLeaves) {
                // 判断是否已完成审批并且处于返回状态为0
                if (bdglLeave.getStatus1().equals(1) && bdglLeave.getStatus2().equals(1) && bdglLeave.getStatus3().equals(1)) {
                    LocalDateTime startTime = bdglLeave.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                    LocalDateTime endTime = bdglLeave.getEndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                    if(startTime.isBefore(now) && endTime.isAfter(now) ) {
                        switch (status){
                            case 0:
                                //TODO 出所
                                if(bdglLeave.getIsReturn().equals("0") && bdglLeave.getRejoin() == 1){
                                    flag = true;
                                }
                                break;
                            case 1:
                                //TODO 入所
                                if(bdglLeave.getIsReturn().equals("0") && bdglLeave.getRejoin() == 0){
                                    flag = true;
                                }
                                break;
                            default:
                                break;
                        }
                    }
                }
            }

        }
        return flag;
    }

    /**
     * 设置人员是否在营
     * @param id
     * @param status 0:不在营 1:在营
     * @return
     */
    @PostMapping("/setPeopleStatus")
    public String setPeopleStatus(@RequestParam("id") Long id, @RequestParam("status") Integer status){
        BdglPeopleAccess bdglPeopleAccess = new BdglPeopleAccess();
        BdglPeople bdglPeople = bdglPeopleService.selectBdglPeopleById(id);

        bdglPeopleAccess.setUserId(id);
        bdglPeopleAccess.setUserName(bdglPeople.getName());
        bdglPeopleAccess.setEmployeeNumber(bdglPeople.getEmployeeNumber());
        bdglPeopleAccess.setPeopleType("员工");
        bdglPeopleAccess.setDeptId(bdglPeople.getDeptId());
        bdglPeopleAccess.setAction(status == 1 ? "入营" : "出营");
        bdglPeopleAccess.setDeptName(bdglPeople.getDeptName());
        bdglPeopleAccess.setTime(LocalDateTime.now());
        add(bdglPeopleAccess);
        redisTemplate.opsForHash().put("peopleStatus",String.valueOf(id),status);
        if (status == 1){
            bdglLeaveController.returnPeople(id);
        }else {
            bdglLeaveController.leavePeople(id);
        }
        return "success";
    }

    @PostMapping("/setVisitorStatus")
    public String setVisitorStatus(@RequestParam("id") Long id, @RequestParam("status") Integer status){
        Visitor visitor = visitorService.selectVisitorById(id);
        BdglPeopleAccess bdglPeopleAccess = new BdglPeopleAccess();
        bdglPeopleAccess.setUserId(id);
        bdglPeopleAccess.setUserName(visitor.getName());
        bdglPeopleAccess.setEmployeeNumber(visitor.getEmployeeNumber());
        bdglPeopleAccess.setPeopleType("访客");
        bdglPeopleAccess.setDeptId(null);
        bdglPeopleAccess.setDeptName(visitor.getCompany());
        bdglPeopleAccess.setAction(status == 1 ? "入营" : "出营");
        bdglPeopleAccess.setTime(LocalDateTime.now());
        add(bdglPeopleAccess);
        redisTemplate.opsForHash().put("visitorStatus",String.valueOf(id),status);
        if (status == 1){
            bdglVisitorEnterController.enterVisitor(id);
        }else {
            bdglVisitorEnterController.leaveVisitor(id);
        }
        return "success";
    }

    @PostMapping("/setStatusByEmployeeNumber")
    public String setStatusByEmployeeNumber(@RequestParam("employeeNumber") String employeeNumber, @RequestParam("status") Integer status) {
        // 拥有通行权限
        if (hasPower(employeeNumber, status)) {
            if (employeeNumber.startsWith("VISITOR_")) {
                Visitor visitor = visitorService.selectVisitorByEmployeeNumber(employeeNumber);
                if (visitor != null) {
                    // 通过代理实例调用，确保AOP增强生效
                    return self.setVisitorStatus(visitor.getId(), status);
                }
            } else {
                BdglPeople bdglPeople = bdglPeopleService.selectBdglPeopleByEmployeeNumber(employeeNumber);
                if (bdglPeople != null) {
                    // 通过代理实例调用，确保AOP增强生效
                    return self.setPeopleStatus(bdglPeople.getId(), status);
                }
            }
        }

        return "error";
    }

    public String setStatusByEmployeeNumber(@RequestParam("deviceId") Long deviceId, @RequestParam("employeeNumber") String employeeNumber, @RequestParam("status") Integer status) {
        // 拥有通行权限
        if (hasPower(employeeNumber, status)) {
            //删除闸机记录
            JSONObject peopleInfoJson = new JSONObject();
            peopleInfoJson.put("deviceId", deviceId);
            peopleInfoJson.put("employeeNo", employeeNumber);
            peopleInfoManageController.delete(BeanUtil.beanToMap(peopleInfoJson));

            JSONObject faceInfoJson = new JSONObject();
            faceInfoJson.put("deviceId", deviceId);
            faceInfoJson.put("FDID", employeeNumber);
            faceInfoManageController.delete(BeanUtil.beanToMap(faceInfoJson));

            if (employeeNumber.startsWith("VISITOR_")) {
                Visitor visitor = visitorService.selectVisitorByEmployeeNumber(employeeNumber);
                if (visitor != null) {
                    // 通过代理实例调用，确保AOP增强生效
                    return self.setVisitorStatus(visitor.getId(), status);
                }
            } else {
                BdglPeople bdglPeople = bdglPeopleService.selectBdglPeopleByEmployeeNumber(employeeNumber);
                if (bdglPeople != null) {
                    // 通过代理实例调用，确保AOP增强生效
                    return self.setPeopleStatus(bdglPeople.getId(), status);
                }
            }
        }

        return "error";
    }

    @GetMapping("/getPeopleStatus")
    public AjaxResult getPeopleStatus(Long id){
        List<Map<String,Object>> list= new CopyOnWriteArrayList<>();
        if(id != null){
            Map map = new HashMap<>();
            map.put("id",id);
            map.put("status",redisTemplate.opsForHash().get("peopleStatus",String.valueOf(id)));
            list.add(map);
        }else{
            bdglPeopleService.selectBdglPeopleList(null).parallelStream().forEach(bdglPeopleBorrow -> {
                if(!redisTemplate.opsForHash().hasKey("peopleStatus",String.valueOf(bdglPeopleBorrow.getId()))){
                    redisTemplate.opsForHash().put("peopleStatus",String.valueOf(bdglPeopleBorrow.getId()),0);
                }
                Map map = new HashMap<>();
                map.put("id",bdglPeopleBorrow.getId());
                map.put("status",redisTemplate.opsForHash().get("peopleStatus",String.valueOf(bdglPeopleBorrow.getId())));
                list.add(map);
            });
        }

        //遍历列表
        for (Map<String, Object> map : list){
            Long userId = (Long) map.get("id");
            BdglPeople bdglPeople = bdglPeopleService.selectBdglPeopleById(userId);
            map.put("name",bdglPeople.getName());
            map.put("employeeNumber",bdglPeople.getEmployeeNumber());
            map.put("deptName",bdglPeople.getDeptName());
            map.put("phone",bdglPeople.getPhone());
            map.put("idCard",bdglPeople.getIdcard());
            map.put("peopleType","员工");
        }
        return AjaxResult.success(list);
    }

    @GetMapping("/getVisitorStatus")
    public AjaxResult getVisitorStatus(Long id){
        List<Map<String,Object>> list= new CopyOnWriteArrayList<>();
        if(id != null){
            Map map = new HashMap<>();
            map.put("id",id);
            map.put("status",redisTemplate.opsForHash().get("visitorStatus",String.valueOf(id)));
            list.add(map);
        }else{
            visitorService.selectVisitorList(null).parallelStream().forEach(visitor -> {
                if(!redisTemplate.opsForHash().hasKey("visitorStatus",String.valueOf(visitor.getId()))){
                    redisTemplate.opsForHash().put("visitorStatus",String.valueOf(visitor.getId()),0);
                }
                Map map = new HashMap<>();
                map.put("id",visitor.getId());
                map.put("status",redisTemplate.opsForHash().get("visitorStatus",String.valueOf(visitor.getId())));
                list.add(map);
            });

        }

        //遍历列表
        for (Map<String, Object> map : list){
            Long userId = (Long) map.get("id");
            Visitor visitor = visitorService.selectVisitorById(userId);
            map.put("name",visitor.getName());
            map.put("employeeNumber",visitor.getEmployeeNumber());
            map.put("deptName",visitor.getUnitName());
            map.put("phone",visitor.getPhone());
            map.put("idCard",visitor.getIdCard());
            map.put("peopleType","访客");
        }
        return AjaxResult.success(list);
    }

    @GetMapping("/getStatusByEmployeeNumber")
    public AjaxResult getStatusByEmployeeNumber(@RequestParam("employeeNumber") String employeeNumber){
        if (employeeNumber.startsWith("VISITOR_")){
            Visitor visitor = visitorService.selectVisitorByEmployeeNumber(employeeNumber);
            // 通过代理实例调用，确保AOP增强生效
            return self.getVisitorStatus(visitor.getId());
        }else {
            BdglPeople bdglPeople = bdglPeopleService.selectBdglPeopleByEmployeeNumber(employeeNumber);
            // 通过代理实例调用，确保AOP增强生效
            return self.getPeopleStatus(bdglPeople.getId());
        }
    }


    /**
     * 查询人员出入记录列表
     */
    @GetMapping("/record/list")
    public TableDataInfo list(BdglPeopleAccess bdglPeopleAccess)
    {
        startPage();
        List<BdglPeopleAccess> list = bdglPeopleAccessService.selectBdglPeopleAccessList(bdglPeopleAccess);
        return getDataTable(list);
    }


    /**
     * 导出人员出入记录列表
     */

    @Log(title = "人员出入记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglPeopleAccess bdglPeopleAccess)
    {
        List<BdglPeopleAccess> list = bdglPeopleAccessService.selectBdglPeopleAccessList(bdglPeopleAccess);
        ExcelUtil<BdglPeopleAccess> util = new ExcelUtil<BdglPeopleAccess>(BdglPeopleAccess.class);
        return util.exportExcel(list, "人员出入记录数据");
    }

    /**
     * 获取人员出入记录详细信息
     */

    @GetMapping(value = "/")
    public AjaxResult getInfo(@RequestParam("employeeNumber") String employeeNumber)
    {
        return AjaxResult.success(bdglPeopleAccessService.selectBdglPeopleAccessByEmployeeNumber(employeeNumber));
    }

    /**
     * 新增人员出入记录
     */

    @PostMapping
    public AjaxResult add(@RequestBody BdglPeopleAccess bdglPeopleAccess)
    {
        return toAjax(bdglPeopleAccessService.insertBdglPeopleAccess(bdglPeopleAccess));
    }

    /**
     * 修改人员出入记录
     */
    @PutMapping
    public AjaxResult edit(@RequestBody BdglPeopleAccess bdglPeopleAccess)
    {
        return toAjax(bdglPeopleAccessService.updateBdglPeopleAccess(bdglPeopleAccess));
    }

    /**
     * 删除人员出入记录
     */
	@DeleteMapping("/{employeeNumbers}")
    public AjaxResult remove(@PathVariable Long[] employeeNumbers)
    {
        return toAjax(bdglPeopleAccessService.deleteBdglPeopleAccessByIds(employeeNumbers));
    }

    /**
     * 统计人员出入记录
     */
    @GetMapping("/count")
    public AjaxResult count(BdglPeopleAccess bdglPeopleAccess)
    {
        return AjaxResult.success(bdglPeopleAccessService.countBdglPeopleAccess(bdglPeopleAccess));
    }
}