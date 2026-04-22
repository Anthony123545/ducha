package com.supervision.web.controller.peopleChuRu;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.pagehelper.PageInfo;
import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.common.core.domain.entity.SysUser;
import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.common.utils.DateUtils;
import com.supervision.common.utils.StringUtils;
import com.supervision.militaryvehicleManagement.domain.AccessPeople;
import com.supervision.militaryvehicleManagement.domain.BdglPrivateCar;
import com.supervision.militaryvehicleManagement.domain.BdglThebus;
import com.supervision.militaryvehicleManagement.domain.BdglThebusApply;
import com.supervision.militaryvehicleManagement.mapper.BdglThebusApplyMapper;
import com.supervision.militaryvehicleManagement.service.IBdglAccessPeopleService;
import com.supervision.militaryvehicleManagement.service.IBdglPrivateCarService;
import com.supervision.militaryvehicleManagement.service.IBdglThebusService;
import com.supervision.peopleChuRu.domain.BdglLeaveFu;
import com.supervision.peopleChuRu.domain.BdglLeaveOne;
import com.supervision.peopleChuRu.service.IBdglLeaveFuService;
import com.supervision.peopleChuRu.service.IBdglLeaveOneService;
import com.supervision.peopleManage.domain.BdglPeople;
import com.supervision.peopleManage.service.IBdglPeopleService;
import com.supervision.system.service.ISysDeptService;
import com.supervision.system.service.ISysUserService;
import com.supervision.web.Hikvision.carDoor.web.controller.NameListController;
import com.supervision.web.Hikvision.carDoor.web.entity.NameList;
import com.supervision.web.Hikvision.peopleDoor.web.controller.FaceInfoManageController;
import com.supervision.web.Hikvision.peopleDoor.web.controller.PeopleInfoManageController;
import com.supervision.web.Hikvision.peopleDoor.web.entity.Device;
import com.supervision.web.Hikvision.peopleDoor.web.entity.PeopleInfo;
import com.supervision.web.Hikvision.peopleDoor.web.service.DeviceService;
import com.supervision.web.Hikvision.peopleDoor.web.service.PeopleInfoService;
import com.supervision.web.Hikvision.peopleDoor.web.util.PeopleDoorDeviceManager;
import com.supervision.web.controller.common.CommonController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
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
import com.supervision.peopleChuRu.domain.BdglLeave;
import com.supervision.peopleChuRu.service.IBdglLeaveService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 人员请假Controller
 *
 * @author supervision
 * @date 2022-03-02
 */
@Transactional()
@RestController
@RequestMapping("/peopleChuRu/bdglLeave")
public class BdglLeaveController extends BaseController {
    @Autowired
    private IBdglLeaveService bdglLeaveService;
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private IBdglPeopleService bdglPeopleService;
    @Autowired
    private IBdglLeaveFuService leaveFuService;
    @Autowired
    ISysDeptService deptService;
    @Autowired
    IBdglAccessPeopleService iBdglAccessPeopleService;
    @Autowired
    private IBdglLeaveOneService bdglLeaveOneService;
    @Autowired
    private PeopleInfoManageController peopleInfoManageController;
    @Autowired
    private FaceInfoManageController faceInfoManageController;
    @Autowired
    private NameListController nameListController;
    @Autowired
    // 执行查询
    private BdglThebusApplyMapper bdglThebusApplyService;

    @Autowired
    private IBdglPrivateCarService bdglPrivateCarService;

    @Autowired
    private IBdglThebusService bdglThebusService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private IBdglPrivateCarService privateCarService;


    /**
    * 查询人员是否具有归离队权限（包含单日、多日请假）
     */

    @GetMapping("/hasLeavePower/{id}")
    public Boolean hasLeavePower(@PathVariable("id") Long id) {
        Boolean flag = false;
        //  获取当前时间
        LocalDateTime now = LocalDateTime.now();
        BdglPeople bdglPeople = bdglPeopleService.selectBdglPeopleById(id);
        // 查看是否有单日请假
        List<BdglLeaveOne> bdglLeaveOnes = bdglLeaveOneService.selectBdglLeaveOneByPeopleId(bdglPeople.getId());
        for(BdglLeaveOne bdglLeaveOne : bdglLeaveOnes) {
            LocalDateTime startTime = bdglLeaveOne.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime endTime = bdglLeaveOne.getEndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            // 判断是否返回状态为0
            if(bdglLeaveOne.getIsReturn().equals("0")) {
                if(startTime.isBefore(now) && endTime.isAfter(now)) {
                    flag = true;
                    break;
                }
            }

        }
        // 查看是否有多日请假
        List<BdglLeave> bdglLeaves = bdglLeaveService.selectBdglLeaveByPeopleId(bdglPeople.getId());
        for(BdglLeave bdglLeave : bdglLeaves) {
            // 判断是否已完成审批并且处于返回状态为0
            if (bdglLeave.getStatus1().equals(1) && bdglLeave.getStatus2().equals(1) && bdglLeave.getStatus3().equals(1) && bdglLeave.getIsReturn().equals("0")) {
                LocalDateTime startTime = bdglLeave.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                LocalDateTime endTime = bdglLeave.getEndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                if(startTime.isBefore(now) && endTime.isAfter(now) ) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * 按照人员id归队(单日和多日)
     */
    @GetMapping("/returnPeople/{id}")
    public AjaxResult returnPeople(@PathVariable("id") Long id) {
        //  获取当前时间
        LocalDateTime now = LocalDateTime.now();
        BdglPeople bdglPeople = bdglPeopleService.selectBdglPeopleById(id);
        bdglPeople.setReign(0);
        bdglPeopleService.updateBdglPeople(bdglPeople);
        // 查看是否有单日请假
        List<BdglLeaveOne> bdglLeaveOnes = bdglLeaveOneService.selectBdglLeaveOneByPeopleId(bdglPeople.getId());
        for(BdglLeaveOne bdglLeaveOne : bdglLeaveOnes) {
            LocalDateTime startTime = bdglLeaveOne.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime endTime = bdglLeaveOne.getEndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            // 判断是否返回状态为0
            if(bdglLeaveOne.getIsReturn().equals("0")) {
                if(startTime.isBefore(now) && endTime.isAfter(now)) {
                    bdglLeaveOne.setIsReturn("1");
                    bdglLeaveOne.setReturnTime(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    bdglLeaveOneService.updateBdglLeaveOne(bdglLeaveOne);
                }
            }

        }
        // 查看是否有多日请假
        List<BdglLeave> bdglLeaves = bdglLeaveService.selectBdglLeaveByPeopleId(bdglPeople.getId());
        for(BdglLeave bdglLeave : bdglLeaves) {
            // 判断是否已完成审批并且处于返回状态为0
            if (bdglLeave.getStatus1().equals(1) && bdglLeave.getStatus2().equals(1) && bdglLeave.getStatus3().equals(1) && bdglLeave.getIsReturn().equals("0") && bdglLeave.getRejoin() == 0) {
                LocalDateTime startTime = bdglLeave.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                LocalDateTime endTime = bdglLeave.getEndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                if(startTime.isBefore(now) && endTime.isAfter(now) ) {
                    bdglPeople.setReign(0);
                    bdglPeopleService.updateBdglPeople(bdglPeople);
                    String[] peopepleIds = bdglLeave.getPeopleId().split(",");
                    boolean flag = false;
                    for(String peopleId : peopepleIds) {
                        BdglPeople bdglPeople1 = bdglPeopleService.selectBdglPeopleById(Long.parseLong(peopleId));
                        if (bdglPeople1.getReign() != 0){
                            flag = true;
                        }
                    }
                    if (!flag){
                        bdglLeave.setIsReturn("1");
                        bdglLeave.setRejoin(1);
                        bdglLeave.setReturnTime(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                        bdglLeaveService.updateBdglLeave(bdglLeave);
                    }
                }
            }
        }
        return AjaxResult.success();
    }
    /**
     * 按照人员id离队(单日和多日)
     */
    @GetMapping("/leavePeople/{id}")
    public AjaxResult leavePeople(Long id) {
        //  获取当前时间
        LocalDateTime now = LocalDateTime.now();
        BdglPeople bdglPeople = bdglPeopleService.selectBdglPeopleById(id);
        bdglPeople.setReign(1);
        // 查看是否有单日请假
        List<BdglLeaveOne> bdglLeaveOnes = bdglLeaveOneService.selectBdglLeaveOneByPeopleId(bdglPeople.getId());
        for(BdglLeaveOne bdglLeaveOne : bdglLeaveOnes) {
            LocalDateTime startTime = bdglLeaveOne.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime endTime = bdglLeaveOne.getEndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            // 判断是否返回状态为0
            if(bdglLeaveOne.getIsReturn().equals("0")) {
                if(startTime.isBefore(now) && endTime.isAfter(now)) {
                    bdglLeaveOne.setIsReturn("0");
                    bdglLeaveOne.setRejoin(0);
                    bdglLeaveOne.setDepartureTime(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    bdglLeaveOneService.updateBdglLeaveOne(bdglLeaveOne);
                }
            }

        }
        // 查看是否有多日请假
        List<BdglLeave> bdglLeaves = bdglLeaveService.selectBdglLeaveByPeopleId(bdglPeople.getId());
        for(BdglLeave bdglLeave : bdglLeaves) {
            // 判断是否已完成审批并且bdglLeave.getIsReturn().equals("0") && bdglLeave.getRejoin() == 1   0,1 审批通过 0,0 进行中 1,1 已完成
            if (bdglLeave.getStatus1().equals(1) && bdglLeave.getStatus2().equals(1) && bdglLeave.getStatus3().equals(1) && bdglLeave.getIsReturn().equals("0") && bdglLeave.getRejoin() == 1) {
                LocalDateTime startTime = bdglLeave.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                LocalDateTime endTime = bdglLeave.getEndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                if(startTime.isBefore(now) && endTime.isAfter(now)) {
                    bdglLeave.setIsReturn("0");
                    bdglLeave.setRejoin(0);
                    bdglLeave.setDepartureTime(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    bdglLeaveService.updateBdglLeave(bdglLeave);
                }
            }
        }
        return AjaxResult.success(bdglPeopleService.updateBdglPeople(bdglPeople));
    }

    /**
     * 按照记录id归队按钮
     */
    @GetMapping("/return/{id}")
    public AjaxResult hasReturnPower(@PathVariable("id") Long id) {
        BdglLeave bdglLeave = bdglLeaveService.selectBdglLeaveById(id);
        bdglLeave.setReturnTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        bdglLeave.setReturnStatus("1");
        return AjaxResult.success(bdglLeaveService.updateBdglLeave(bdglLeave));
    }




    /**
     * 查询人员请假列表
     * <p>
     * 备注  common=1  普通审批
     * common=2  特殊审批
     */
//    @PreAuthorize("@ss.hasPermi('peopleChuRu:bdglLeave:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglLeave bdglLeave) {
        if(bdglLeave.getName()!= null && bdglLeave.getName() != "") {
            List<String> list = bdglPeopleService.selectPowerPeoleListes(bdglLeave.getName());
            String str = list.stream().collect(Collectors.joining(","));
            String[] split = str.split(",");
            bdglLeave.setNames(split);
        }
        startPage();
        LoginUser loginUser = getLoginUser();
        Long userId = loginUser.getUserId();
        SysUser sysUser = sysUserService.selectUserById(userId);
        startPage();
        List<BdglLeave> list = bdglLeaveService.selectBdglLeaveList(bdglLeave);
        int a = 0;
        c:
        for (BdglLeave leave : list) {
            //如果当前登录用户有特殊审批权限  显示归队按钮

            a++;
            if (leave.getStatus1().equals(2) || leave.getStatus2().equals(2) || leave.getStatus3().equals(2) || leave.getStatus4().equals(2) || (leave.getRejoin().equals(1) && leave.getIsReturn().equals("1"))
            ) {
                continue c;
            }
            boolean falg=false;
            if(sysUser.getPermissions()!=null){

                 falg = isFalg(sysUser,"3");
            }
            Integer status4 = leave.getStatus4();
            if (sysUser.getPermissions() != null) {
                if (leave.getStatus4().equals(1) || leave.getStatus4().equals(3) && falg) {
                    leave.setCommon("3");
                    continue c;
                }
                if (falg && leave.getPeopleId4() == null && (leave.getStatus3().equals(1) || leave.getStatus3().equals(3))) {
                    leave.setCommon("3");
                    continue c;
                }
                if (falg && leave.getPeopleId3() == null && leave.getPeopleId4() == null && (leave.getStatus2().equals(1) || leave.getStatus2().equals(3))) {
                    leave.setCommon("3");
                    continue c;
                }
                if (falg && leave.getPeopleId2() == null && leave.getPeopleId3() == null && leave.getPeopleId4() == null && (leave.getStatus1().equals(1) || leave.getStatus1().equals(3))) {
                    leave.setCommon("3");
                    continue c;
                }
            }
            if (leave.getStatus4() == 3 || leave.getStatus4() == 1) {
                continue c;
            }
            if (sysUser.getPermissions() != null) {
                //是否有特殊审批权限
                boolean falg2=false;
                if(sysUser.getPermissions()!=null){

                     falg2 = isFalg(sysUser,"0");
                }
                if (falg2) {

                    leave.setUnitId(loginUser.getUserId());
                    //如果特殊审批人和某级审批人相同   返回普通审批;
//                    if (loginUser.getUserId().equals(Long.valueOf(leave.getPeopleId1())) || loginUser.getUserId().equals(Long.valueOf(leave.getPeopleId2())) || loginUser.getUserId().equals(Long.valueOf(leave.getPeopleId3())) || loginUser.getUserId().equals(Long.valueOf(leave.getPeopleId4()))) {
//                        leave.setCommon("1");
//                    }
                    if (leave.getPeopleId1() != null) {
                        if (leave.getStatus1() == 0 && (leave.getStatus1() != 1 || leave.getStatus1() != 3)) {
                            leave.setShenPiJiBie("一级");
                            leave.setCommon("2");
                            if (loginUser.getUserId().equals(Long.valueOf(leave.getPeopleId1()))) {
                                leave.setCommon("1");
                            }
                            continue c;
                        }
                    }
                    if (leave.getPeopleId2() != null) {
                        if ((leave.getStatus2() == 0 && (leave.getStatus1() == 1 || leave.getStatus1() == 3)) || (leave.getPeopleId1() == null && leave.getStatus2() == 0)) {
                            leave.setShenPiJiBie("二级");
                            leave.setCommon("2");
                            if (loginUser.getUserId().equals(Long.valueOf(leave.getPeopleId2()))) {
                                leave.setCommon("1");
                            }
                            continue c;
                        }
                    }
                    if (leave.getPeopleId3() != null) {
                        if ((leave.getStatus3() == 0 && leave.getStatus3() != 1 && (leave.getStatus2() == 1 || leave.getStatus2() == 3)) || (leave.getPeopleId2() == null && leave.getStatus3() == 0)) {
                            leave.setShenPiJiBie("三级");
                            leave.setCommon("2");
                            if (loginUser.getUserId().equals(Long.valueOf(leave.getPeopleId3()))) {
                                leave.setCommon("1");
                            }
                            continue c;
                        }
                    }
//                    if (leave.getPeopleId4() != null) {
//
//                        if ((leave.getStatus4() == 0 && leave.getStatus4() != 1 && (leave.getStatus3() == 1 || leave.getStatus3() == 3)) || (leave.getPeopleId3() == null && leave.getStatus4() == 0)) {
//
//                            leave.setCommon("2");
//                            leave.setShenPiJiBie("四级");
//                            if (loginUser.getUserId().equals(Long.valueOf(leave.getPeopleId4()))) {
//                                leave.setCommon("1");
//                            }
//                            continue c;
//
//                        }
//                    }


                }
            }
            Long userId1 = loginUser.getUserId();
            Integer peopleId1 = leave.getPeopleId1();
            if (leave.getPeopleId1() != null) {
                if (loginUser.getUserId().equals(Long.valueOf(leave.getPeopleId1()))) {
                    if (leave.getStatus1() == 0) {
                        leave.setCommon("1");
                        leave.setShenPiJiBie("一级");
                        continue c;
                    }
                }
            }
            if (leave.getPeopleId2() != null) {
                if (loginUser.getUserId().equals(Long.valueOf(leave.getPeopleId2()))) {
                    if(leave.getPeopleId1()==null&&leave.getStatus2() == 0){
                        leave.setCommon("1");
                        leave.setShenPiJiBie("二级");
                        continue c;
                    }
                    if (leave.getStatus2() == 0 && leave.getStatus1() == 1 || leave.getStatus1() == 3) {
                        leave.setCommon("1");
                        leave.setShenPiJiBie("二级");
                        continue c;
                    }
                }
            }
            if (leave.getPeopleId3() != null) {
                if (loginUser.getUserId().equals(Long.valueOf(leave.getPeopleId3()))) {
                    if(leave.getStatus3() == 0 && leave.getPeopleId2()==null && leave.getPeopleId1()==null){
                        leave.setCommon("1");
                        leave.setShenPiJiBie("三级");
                        continue c;
                    }
                    if (leave.getStatus3() == 0 && leave.getStatus2() == 1 || leave.getStatus2() == 3) {
                        leave.setCommon("1");
                        leave.setShenPiJiBie("三级");
                        continue c;

                    }
                }
            }
//            if (leave.getPeopleId4() != null) {
//                if (loginUser.getUserId().equals(Long.valueOf(leave.getPeopleId4()))) {
//                    boolean b = leave.getStatus4() == 0;
//                    boolean b1 = leave.getStatus3() == 1;
//                    if (leave.getStatus4() == 0 && leave.getStatus3() == 1 || leave.getStatus3() == 3 || leave.getPeopleId3() == null) {
//                        leave.setCommon("1");
//                        leave.setShenPiJiBie("四级");
//                        continue c;
//                    }
//                }
//            }

        }
        return getDataTable(list);
    }

    public static boolean isFalg(SysUser sysUser, String aa) {
        boolean falg=false;
        String[] split = sysUser.getPermissions().split(",");
        for (String s : split) {
            if(s.equals(aa)){
                falg=true;
                break;
            }
        }
        return falg;
    }



    /**
     * 导出人员请假列表
     */
//    @PreAuthorize("@ss.hasPermi('peopleChuRu:bdglLeave:export')")
    @Log(title = "人员请假", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglLeave bdglLeave) {
        List<BdglLeave> list = bdglLeaveService.selectBdglLeaveList(bdglLeave);
        ExcelUtil<BdglLeave> util = new ExcelUtil<BdglLeave>(BdglLeave.class);
        return util.exportExcel(list, "人员请假数据");
    }

    /**
     * 获取人员请假详细信息
     */
//    @PreAuthorize("@ss.hasPermi('peopleChuRu:bdglLeave:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(bdglLeaveService.selectBdglLeaveById(id));
    }

    /**
     * 新增人员请假
     */
//    @PreAuthorize("@ss.hasPermi('peopleChuRu:bdglLeave:add')")
    @Log(title = "人员请假", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglLeave bdglLeave) {
        BdglPeople bdglPeople = new BdglPeople();
        bdglPeople.setReign(Integer.parseInt(bdglLeave.getLeaveType()));
        bdglLeave.setCreatetime(DateUtils.getNowDate());
        bdglLeave.setRejoin(1);
        return toAjax(bdglLeaveService.insertBdglLeave(bdglLeave));
    }

//    @PreAuthorize("@ss.hasPermi('peopleChuRu:bdglLeave:guidui')")
    //@Log(title = "人员请假", businessType = BusinessType.INSERT)
    @PutMapping(value = "guidui")
    public AjaxResult guidui(@RequestBody BdglLeave bdglLeave) {
        //  获取完整的假单信息（拿到车牌号）
        BdglLeave fullLeave = bdglLeaveService.selectBdglLeaveById(bdglLeave.getId());
        BdglLeave bdglLeave1 = bdglLeaveService.selectBdglLeaveById(bdglLeave.getId());
        String[] split = bdglLeave.getPeopleId().split(",");
        String name = "";
        for (String s : split) {
            BdglPeople bdglPeople1 = bdglPeopleService.selectBdglPeopleById(Long.valueOf(s));
            bdglPeople1.setReign(0);
            name += bdglPeople1.getName() + ",";
            if (bdglLeave.getRejoin() != 0) {
                bdglPeopleService.updateBdglPeople(bdglPeople1);
                bdglLeave.setActualreturntime(new Date());
            }

        }
        BdglLeaveFu bdglLeaveFu = new BdglLeaveFu();
        if (bdglLeave.getCommon() != null) {
            if (bdglLeave.getCommon().equals("1") || bdglLeave.getCommon().equals("2") || bdglLeave.getCommon().equals("3") || bdglLeave.getCommon().equals("4")) {
                bdglLeaveFu.setPeople(bdglLeave.getCommon());
                bdglLeaveFu.setLeaveId(bdglLeave.getId());
                bdglLeaveFu.setSpecialApproval(bdglLeave.getSpecialApproval());
                leaveFuService.insertBdglLeaveFu(bdglLeaveFu);
            }
        }
        bdglLeave.setReturnStatus("1");
        bdglLeave.setIsReturn("1");
        bdglLeave.setPeopleName(name.substring(0, name.length() - 1));

        // ---【新增：车辆联动逻辑】---
        if (fullLeave != null && StringUtils.isNotEmpty(fullLeave.getPlateNumber())) {
            // 手动归队，直接设为 1
            updateCarStateByPlate(fullLeave.getPlateNumber(), "1");
        }
        // ---【车辆逻辑结束】---

        return toAjax(bdglLeaveService.updateBdglLeave(bdglLeave));
    }
    /**
     * 根据车牌号更新车辆状态的辅助方法
     */
    private void updateCarStateByPlate(String plate, String state) {
        if (com.supervision.common.utils.StringUtils.isEmpty(plate)) return;

        com.supervision.militaryvehicleManagement.domain.BdglPrivateCar query = new com.supervision.militaryvehicleManagement.domain.BdglPrivateCar();
        query.setVehiclenumber(plate);
        // 注意：这里的 privateCarService 必须已经在类上方通过 @Autowired 注入了
        List<com.supervision.militaryvehicleManagement.domain.BdglPrivateCar> cars = privateCarService.selectBdglPrivateCarList(query);

        if (cars != null && !cars.isEmpty()) {
            for (com.supervision.militaryvehicleManagement.domain.BdglPrivateCar car : cars) {
                if (!state.equals(car.getState())) {
                    car.setState(state);
                    privateCarService.updateBdglPrivateCar(car);
                }
            }
        }
    }
    /**
     * 修改人员请假
     */
//    @PreAuthorize("@ss.hasPermi('peopleChuRu:bdglLeave:edit')")
    @Log(title = "人员请假", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglLeave bdglLeave) {   //判断人员是否归位  归位修改人员在位状态
        BdglLeave bdglLeave1 = bdglLeaveService.selectBdglLeaveById(bdglLeave.getId());
        updateBdglLeave(bdglLeave);
        return toAjax(bdglLeaveService.updateBdglLeave(bdglLeave));
    }
 //更新在位情况
 private void updateBdglLeave(BdglLeave bdglLeave) {
     String[] split = bdglLeave.getPeopleId().split(",");
     Date now = new Date();
     StringBuilder name = new StringBuilder();

     // 【核心映射】：请假类型(0-6) 对应 在位状态(1-7)
     // 假设数据库存的是 "0", "1"... 字符串
     int leaveTypeVal = Integer.parseInt(bdglLeave.getLeaveType());
     int targetReign = leaveTypeVal + 1;

     for (String s : split) {
         BdglPeople p = bdglPeopleService.selectBdglPeopleById(Long.valueOf(s));
         if (p != null) {
             // --- 逻辑1：判定在位状态 ---
             // 只要没归队，且当前时间在请假时段内，就设置为对应的状态 (1-7)
             if ("0".equals(bdglLeave.getIsReturn()) && now.after(bdglLeave.getStartTime()) && now.before(bdglLeave.getEndTime())) {
                 p.setReign(targetReign);
             } else {
                 // 如果已经归队，或者没到时间，或者时间已过，统统是在位(0)
                 p.setReign(0);
             }

             // 特殊情况：如果手动触发了归队（通过 rejoin 标识或其他逻辑）
             if ("1".equals(bdglLeave.getIsReturn())) {
                 p.setReign(0);
                 // 只有在没设置过实际归队时间的情况下才设为当前时间
                 if (bdglLeave.getActualreturntime() == null) {
                     bdglLeave.setActualreturntime(new Date());
                 }
             }

             bdglPeopleService.updateBdglPeople(p);
             name.append(p.getName()).append(",");
         }
     }

     // ---【新增：车辆联动逻辑】---
     if (StringUtils.isNotEmpty(bdglLeave.getPlateNumber())) {
         String carState = "1"; // 默认在位

         // 逻辑：没归队 + 时间已到开始时间 + 时间没到结束时间 = 出车
         if ("0".equals(bdglLeave.getIsReturn())
                 && now.after(bdglLeave.getStartTime())
                 && now.before(bdglLeave.getEndTime())) {
             carState = "3";
         }

         updateCarStateByPlate(bdglLeave.getPlateNumber(), carState);
     }
     // ---【车辆逻辑结束】---

     // --- 逻辑2：附属表处理 ---
     if (bdglLeave.getCommon() != null) {
         String common = bdglLeave.getCommon();
         if ("1".equals(common) || "2".equals(common) || "3".equals(common) || "4".equals(common)) {
             BdglLeaveFu bdglLeaveFu = new BdglLeaveFu();
             bdglLeaveFu.setPeople(common);
             bdglLeaveFu.setLeaveId(bdglLeave.getId());
             bdglLeaveFu.setSpecialApproval(bdglLeave.getSpecialApproval());
             leaveFuService.insertBdglLeaveFu(bdglLeaveFu);
         }
     }

     // --- 逻辑3：更新人名字段 ---
     if (name.length() > 0) {
         bdglLeave.setPeopleName(name.substring(0, name.length() - 1));
     }
 }

    /**
     * 审批人员请假
     */
//    @PreAuthorize("@ss.hasPermi('peopleChuRu:bdglLeave:shenpi')")
    @Log(title = "人员请假", businessType = BusinessType.UPDATE)
    @PutMapping("/shenpi")
    public AjaxResult shenpi(@RequestBody BdglLeave bdglLeave) throws IOException {   //判断人员是否归位  归位修改人员在位状态
        updateBdglLeave(bdglLeave);
        int dbResult = bdglLeaveService.updateBdglLeave(bdglLeave);
        // 用于收集没有照片的人员名单
        StringBuilder photoWarning = new StringBuilder();
//        if (bdglLeave.getStatus1().equals(1) && bdglLeave.getStatus2().equals(1) && bdglLeave.getStatus3().equals(1) && bdglLeave.getIsReturn().equals("0") && bdglLeave.getRejoin() == 1) {
        if(bdglLeave.getDoorIn()!=null||bdglLeave.getDoorOut()!=null) {
            String[] peopleIds = bdglLeave.getPeopleId().split(",");
            for (String peopleId : peopleIds) {
                BdglPeople bdglPeople = bdglPeopleService.selectBdglPeopleById(Long.valueOf(peopleId));
                if (bdglPeople != null) {
                    // 【核心改动】：检查照片，如果缺失则记录并跳过此人的同步，但不终止整个方法
                    if (StringUtils.isEmpty(bdglPeople.getAvatar())) {
                        photoWarning.append("【").append(bdglPeople.getName()).append("】");
                        continue;
                    }
                    MultipartFile file = CommonController.getFile(bdglPeople.getAvatar());
                    if (file == null) {
//                        return AjaxResult.error("请上传人员照片");
                        photoWarning.append("【").append(bdglPeople.getName()).append("(文件缺失)】");
                        continue;
                    }
                    // 先给对象赋值（后续复用）
                    bdglPeople.setEmployeeNumber(bdglPeople.getId() != null ? bdglPeople.getId().toString() : String.valueOf(System.currentTimeMillis()));
                    // 添加出口门闸信息
                    Map<String, Object> peopleInfoJson1 = new HashMap<>();
//                    peopleInfoJson1.put("deviceId", bdglLeave.getDoorOut());
                    List<Integer> deviceIds = new ArrayList<>();
                    // 将Long类型的doorIn转为Integer
                    deviceIds.add(bdglLeave.getDoorOut().intValue());
                    peopleInfoJson1.put("deviceIds", deviceIds);
                    peopleInfoJson1.put("employeeNo", bdglPeople.getEmployeeNumber());
                    peopleInfoJson1.put("userType", "normal");
                    // 【新增：构造 RightPlan】
                    List<Map<String, Object>> rightPlanList1 = new ArrayList<>();
                    Map<String, Object> rightPlan1 = new HashMap<>();
                    rightPlan1.put("planTemplateNo", "1"); // "1" 通常代表全天候通行模板（不填则周末无法通行）
                    rightPlanList1.add(rightPlan1);
                    peopleInfoJson1.put("RightPlan", rightPlanList1); // 塞入嵌套结构
//                    peopleInfoJson1.put("localUIRight", true);
//                    peopleInfoJson1.put("doubleLockRight", true);
                    peopleInfoJson1.put("userVerifyMode", "");
                    peopleInfoJson1.put("doorRight", "1,2");
                    JSONObject validJson1 = new JSONObject();
                    validJson1.put("enable", true);
                    validJson1.put("beginTime", bdglLeave.getFormattedStartTime());
                    validJson1.put("endTime", bdglLeave.getFormattedEndTime());
                    validJson1.put("timeType", "local");
                    peopleInfoJson1.put("Valid", validJson1);
                    peopleInfoManageController.add(peopleInfoJson1);

                    JSONObject faceInfoJson1 = new JSONObject();
                    List<Integer> deviceIds2 = new ArrayList<>();
                    // 将Long类型的doorIn转为Integer
                    deviceIds2.add(bdglLeave.getDoorOut().intValue());
                    faceInfoJson1.put("deviceIds", deviceIds2);
                    faceInfoJson1.put("faceLibType", "blackFD");
                    faceInfoJson1.put("FDID", 1);
                    faceInfoJson1.put("FPID", bdglPeople.getEmployeeNumber());
                    MultipartFile file1 = CommonController.getFile(bdglPeople.getAvatar());
                    faceInfoManageController.add(file1, faceInfoJson1.toString());

                    // 添加入口门闸信息
                    Map<String, Object> peopleInfoJson2 = new HashMap<>();
//                    peopleInfoJson2.put("deviceId", bdglLeave.getDoorIn());
                    List<Integer> deviceIds3 = new ArrayList<>();
                    // 将Long类型的doorIn转为Integer
                    deviceIds3.add(bdglLeave.getDoorIn().intValue());
                    peopleInfoJson2.put("deviceIds", deviceIds3);
                    peopleInfoJson2.put("employeeNo", bdglPeople.getEmployeeNumber());
                    peopleInfoJson2.put("userType", "normal");
                    // 【新增：构造 RightPlan】
                    List<Map<String, Object>> rightPlanList2 = new ArrayList<>();
                    Map<String, Object> rightPlan2 = new HashMap<>();
                    rightPlan2.put("planTemplateNo", "1"); // "1" 通常代表全天候通行模板
                    rightPlanList2.add(rightPlan2);
                    peopleInfoJson2.put("RightPlan", rightPlanList2); // 塞入嵌套结构
//                    peopleInfoJson2.put("localUIRight", true);
//                    peopleInfoJson2.put("doubleLockRight", true);
                    peopleInfoJson2.put("userVerifyMode", "");
                    peopleInfoJson2.put("doorRight", "1,2");
                    JSONObject validJson2 = new JSONObject();
                    validJson2.put("enable", true);
                    validJson2.put("beginTime", bdglLeave.getFormattedStartTime());
                    validJson2.put("endTime", bdglLeave.getFormattedEndTime());
                    validJson2.put("timeType", "local");
                    peopleInfoJson2.put("Valid", validJson2);
                    peopleInfoManageController.add(peopleInfoJson2);

                    JSONObject faceInfoJson2 = new JSONObject();
//                    faceInfoJson2.put("deviceId", bdglLeave.getDoorIn());
                    List<Integer> deviceIds4 = new ArrayList<>();
                    // 将Long类型的doorIn转为Integer
                    deviceIds4.add(bdglLeave.getDoorIn().intValue());
                    faceInfoJson2.put("deviceIds", deviceIds4);
                    faceInfoJson2.put("faceLibType", "blackFD");
                    faceInfoJson2.put("FDID", 1);
                    faceInfoJson2.put("FPID", bdglPeople.getEmployeeNumber());
                    MultipartFile file2 = CommonController.getFile(bdglPeople.getAvatar());
                    faceInfoManageController.add(file2, faceInfoJson2.toString());
                }

            }
        }

            // 如果为自驾，还需插入车闸
            if (bdglLeave.getTravelMode().equals("1")) {
                //  判断车辆类型 是否为 私车
                if(bdglLeave.getCarType() == 0 ){

                    // 构建添加名单所需的参数
                    Map<String, Object> params = new HashMap<>();

                    // 构建nameListData对象
                    NameList nameListData = new NameList();
                    nameListData.setLicensePlate(bdglLeave.getPlateNumber());
                    nameListData.setType(1);
                    nameListData.setAccessStartTime(bdglLeave.getStartTime());
                    nameListData.setAccessEndTime(bdglLeave.getEndTime());
                    nameListData.setChannel(1);
                    nameListData.setPlateType(0xff);
                    nameListData.setPlateColor(0xff);
                    params.put("nameListData", nameListData);

                    // 构建设备ID列表
                    List<Integer> deviceIds = new ArrayList<>();
                    deviceIds.add(bdglLeave.getCarIn().intValue()); // 使用入口车闸设备ID
                    deviceIds.add(bdglLeave.getCarOut().intValue()); // 使用出口车闸设备ID
                    params.put("deviceIds", deviceIds);

                    // 调用添加名单方法
                    nameListController.add(params);
                }
            }
//        }
        if(bdglLeaveService.updateBdglLeave(bdglLeave)<=0){
            return AjaxResult.error("审批失败，请联系管理员");
        }
        if (photoWarning.length() > 0) {
            // 如果有人员没上传照片，返回成功状态，但带上警告文字
            return AjaxResult.success("审批已通过。注意：" + photoWarning.toString() + "尚未上传照片，暂时无法扫脸。");
        } else {
            // 一切完美
            return AjaxResult.success("审批通过，人员权限已成功下发至闸机。");
        }
//        return toAjax(bdglLeaveService.updateBdglLeave(bdglLeave));
    }

    /**
     * 特殊审批人员请假
     */
//    @PreAuthorize("@ss.hasPermi('peopleChuRu:bdglLeave:teshu')")
    @Log(title = "人员请假", businessType = BusinessType.UPDATE)
    @PutMapping("/teshu")
    public AjaxResult tshenpi(@RequestBody BdglLeave bdglLeave) {   //判断人员是否归位  归位修改人员在位状态
        updateBdglLeave(bdglLeave);
        return toAjax(bdglLeaveService.updateBdglLeave(bdglLeave));
    }

    /**
     * 删除人员请假
     */
//    @PreAuthorize("@ss.hasPermi('peopleChuRu:bdglLeave:remove')")
    @Log(title = "人员请假", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(bdglLeaveService.deleteBdglLeaveByIds(ids));
    }

//    @GetMapping("/getYingJIUSer")
//    public AjaxResult getYingJIUSer(Long deptId) {
//        Long deptId1 = getDeptId();
//        SysDept sysDept = deptService.selectDeptById(deptId1);
//        SysUser sysUser = new SysUser();
//        if (sysDept.getAncestors().split(",").length == 2) {
//            sysUser.setDeptId(deptId1);
//        } else if (sysDept.getAncestors().split(",").length == 3) {
//            sysUser.setDeptId(sysDept.getParentId());
//        }
//        List<SysUser> sysUsers = sysUserService.selectUsers(sysUser);
//        return AjaxResult.success(sysUsers);
//    }
//    @GetMapping("/getLIanJIUSer")
//    public AjaxResult getLIanJIUSer(Long deptId) {
//        Long deptId1 = getDeptId();
//        SysDept sysDept = deptService.selectDeptById(deptId1);
//        SysUser sysUser = new SysUser();
//        if (sysDept.getAncestors().split(",").length == 3) {
//            sysUser.setDeptId(deptId1);
//            List<SysUser> sysUsers = sysUserService.selectUsers(sysUser);
//            return AjaxResult.success(sysUsers);
//        }
//        return null;
//    }
//    @GetMapping("/getZhengZhiUSer")
//    public AjaxResult getZhengZhiUSer() {
//        List<SysDept> sysDepts = deptService.selectDeptList(new SysDept());
//        Long deptId=0L;
//        for (SysDept sysDept : sysDepts) {
//            if(sysDept.getDeptName().contains("政治工作部")){
//                deptId=sysDept.getDeptId();
//                break;
//            }
//        }
//        SysUser sysUser = new SysUser();
//        sysUser.setDeptId(deptId);
//        List<SysUser> sysUsers = sysUserService.selectUsers(sysUser);
//        return AjaxResult.success(sysUsers);
//    }
    @GetMapping("/getYingJIUSer")
    public AjaxResult getYingJIUSer(Long deptId) {
        PageInfo<AccessPeople> accessPeoplePageInfo = iBdglAccessPeopleService.queryPage(null, 2, null, null);
        List<SysUser> sysUsers = new LinkedList<>();
        for (AccessPeople accessPeople : accessPeoplePageInfo.getList()) {
            SysUser sysUser = sysUserService.selectUserByUserName(accessPeople.getName());
            sysUser.setPassword( null);
            sysUser.setSalt(null);
            sysUsers.add(sysUser);
        }
        return AjaxResult.success(sysUsers);
    }
    @GetMapping("/getLIanJIUSer")
    public AjaxResult getLIanJIUSer(Long deptId) {
        PageInfo<AccessPeople> accessPeoplePageInfo = iBdglAccessPeopleService.queryPage(null, 1, null, null);
        List<SysUser> sysUsers = new LinkedList<>();
        for (AccessPeople accessPeople : accessPeoplePageInfo.getList()) {
            SysUser sysUser = sysUserService.selectUserByUserName(accessPeople.getName());
            sysUser.setPassword(null);
            sysUser.setSalt(null);
            sysUsers.add(sysUser);
        }
        return AjaxResult.success(sysUsers);
    }

    @GetMapping("/getZhengZhiUSer")
    public AjaxResult getZhengZhiUSer() {
        PageInfo<AccessPeople> accessPeoplePageInfo = iBdglAccessPeopleService.queryPage(null, 3, null, null);
        List<SysUser> sysUsers = new LinkedList<>();
        for (AccessPeople accessPeople : accessPeoplePageInfo.getList()) {
            SysUser sysUser = sysUserService.selectUserByUserName(accessPeople.getName());
            sysUser.setPassword(null);
            sysUser.setSalt(null);
            sysUsers.add(sysUser);
        }
        return AjaxResult.success(sysUsers);
    }


    /**
     * 获取车牌号列表
     * @param keyword 关键字（对car_ids进行模糊查询）
     * @param peopleId 人员ID
     * @param vehicleType 车辆类型
     * @param status 状态
     * @return 车牌号列表
     */
    @GetMapping("/getPlateNumbers")
    public AjaxResult getPlateNumbers(String keyword, String peopleId, String vehicleType, String status) {
        List<Map<String, Object>> plateNumbers = new ArrayList<>();

        // 不再判断是否审核通过，直接查询两个表
        if ("PRIVATE".equals(vehicleType)) {
            // 查询私家车表 bdgl_private_car
            BdglPrivateCar privateCarQuery = new BdglPrivateCar();
//            privateCarQuery.setState("1"); // 在位状态

            List<BdglPrivateCar> privateCars = bdglPrivateCarService.selectBdglPrivateCarList(privateCarQuery);

            for (BdglPrivateCar car : privateCars) {
                // 如果有关键词过滤
                if (keyword != null && !keyword.isEmpty()) {
                    if (car.getVehiclenumber() != null && car.getVehiclenumber().toLowerCase().contains(keyword.toLowerCase())) {
                        Map<String, Object> item = new HashMap<>();
                        item.put("id", car.getId());
                        item.put("carIds", car.getVehiclenumber());
                        item.put("vehicleType", "PRIVATE");
                        item.put("peopleName", car.getPeopleName());
                        plateNumbers.add(item);
                    }
                } else {
                    Map<String, Object> item = new HashMap<>();
                    item.put("id", car.getId());
                    item.put("carIds", car.getVehiclenumber());
                    item.put("vehicleType", "PRIVATE");
                    item.put("peopleName", car.getPeopleName());
                    plateNumbers.add(item);
                }
            }
        } else {
            // 查询公家车表 bdgl_thebus
            BdglThebus thebusQuery = new BdglThebus();
//            thebusQuery.setStatus("1"); // 在位状态

            List<BdglThebus> thebusList = bdglThebusService.selectBdglThebusList(thebusQuery);

            for (BdglThebus car : thebusList) {
                // 如果有关键词过滤
                if (keyword != null && !keyword.isEmpty()) {
                    if (car.getVehiclenumber() != null && car.getVehiclenumber().toLowerCase().contains(keyword.toLowerCase())) {
                        Map<String, Object> item = new HashMap<>();
                        item.put("id", car.getId());
                        item.put("carIds", car.getVehiclenumber());
                        item.put("vehicleType", "MILITARY");
                        item.put("peopleName", car.getPeopleNames());
                        plateNumbers.add(item);
                    }
                } else {
                    Map<String, Object> item = new HashMap<>();
                    item.put("id", car.getId());
                    item.put("carIds", car.getVehiclenumber());
                    item.put("vehicleType", "MILITARY");
                    item.put("peopleName", car.getPeopleNames());
                    plateNumbers.add(item);
                }
            }
        }

        // 构造与前端期望一致的数据格式，包含rows属性
        Map<String, Object> result = new HashMap<>();
        result.put("rows", plateNumbers);
        return AjaxResult.success(result);
    }

    /**
     * 根据闸类型查询对应的门闸信息
     * @param gateType 闸类型 ("door_in"-入口门闸, "door_out"-出口门闸, "car_in"-入口车闸, "car_out"-出口车闸)
     * @return 门闸设备信息列表
     */
    @GetMapping("/getGateDevices")
    public AjaxResult getGateDevices(String gateType) {
        String deviceType = null;
        String action = null;

        // 根据闸类型确定设备类型
        switch (gateType) {
            case "door_in":
                action = "in";
                deviceType = "peopleDoor"; // 人员门闸
                break;
            case "door_out":
                action = "out";
                deviceType = "peopleDoor"; // 人员门闸
                break;
            case "car_in":
                action = "all";
                deviceType = "carDoor"; // 车辆门闸
                break;
            case "car_out":
                action = "all";
                deviceType = "carDoor"; // 车辆门闸
                break;
            default:
                return AjaxResult.error("无效的闸类型参数");
        }

        // 查询对应类型的设备
        List<Device> devices = deviceService.list();

        // 过滤符合类型的设备 accessPermission 和 type
        String finalAction = action;
        String finalDeviceType = deviceType;
        List<Device> filteredDevices = devices.stream()
                .filter(device -> device.getAccessPermission().equals(finalAction) && device.getType().equals(finalDeviceType))
                .collect(Collectors.toList());

        return AjaxResult.success(filteredDevices);
    }

    /**
     * 创建车牌号map对象
     */
    private Map<String, Object> createPlateNumberMap(String plateNumber, String carType) {
        Map<String, Object> map = new HashMap<>();
        map.put("plateNumber", plateNumber);
        map.put("carNum", plateNumber);
        map.put("vehicleType", carType);
        return map;
    }
}
