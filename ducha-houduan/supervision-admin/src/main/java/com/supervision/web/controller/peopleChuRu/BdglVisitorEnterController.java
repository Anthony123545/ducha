package com.supervision.web.controller.peopleChuRu;

import java.io.IOException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONObject;
import com.supervision.militaryvehicleManagement.domain.BdglPrivateCar;
import com.supervision.militaryvehicleManagement.domain.BdglThebus;
import com.supervision.militaryvehicleManagement.domain.BdglThebusApply;
import com.supervision.militaryvehicleManagement.mapper.BdglThebusApplyMapper;
import com.supervision.militaryvehicleManagement.service.IBdglPrivateCarService;
import com.supervision.militaryvehicleManagement.service.IBdglThebusService;
import com.supervision.web.Hikvision.peopleDoor.web.controller.FaceInfoManageController;
import com.supervision.web.Hikvision.peopleDoor.web.controller.PeopleInfoManageController;
import com.supervision.web.Hikvision.peopleDoor.web.entity.Device;
import com.supervision.web.Hikvision.peopleDoor.web.service.DeviceService;

import cn.hutool.core.bean.BeanUtil;
import com.supervision.common.core.domain.entity.SysUser;
import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.common.utils.DateUtils;
import com.supervision.peopleChuRu.domain.BdglLeave;
import com.supervision.peopleChuRu.domain.BdglLeaveOne;
import com.supervision.peopleChuRu.domain.BdglVisitorEnter;
import com.supervision.peopleChuRu.service.IBdglVisitorEnterService;
import com.supervision.peopleManage.domain.BdglPeople;
import com.supervision.peopleManage.domain.Visitor;
import com.supervision.peopleManage.service.IVisitorService;
import com.supervision.system.service.ISysDeptService;
import com.supervision.system.service.ISysUserService;
import com.supervision.web.controller.common.CommonController;
import org.springframework.data.redis.core.RedisTemplate;
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
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 访客进入Controller
 *
 * @author supervision
 * @date 2022-03-02
 */
@Transactional()
@RestController("webBdglVisitorEnterController") // 指定唯一的bean名称
@RequestMapping("/peopleChuRu/bdglVisitorEnter")
public class BdglVisitorEnterController extends BaseController {
    @Autowired
    private IBdglVisitorEnterService bdglVisitorEnterService;
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private IVisitorService visitorService;
    @Autowired
    ISysDeptService deptService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    private BdglThebusApplyMapper bdglThebusApplyMapper;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private PeopleInfoManageController peopleInfoManageController;
    @Autowired
    private FaceInfoManageController faceInfoManageController;
    
    @Autowired
    private IBdglPrivateCarService bdglPrivateCarService;
    
    @Autowired
    private IBdglThebusService bdglThebusService;

    /**
     * 查询访客进入列表
     * <p>
     * 备注  common=1  普通审批
     * common=2  特殊审批
     */
//    @PreAuthorize("@ss.hasPermi('peopleChuRu:bdglVisitorEnter:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglVisitorEnter bdglVisitorEnter) {
        if(bdglVisitorEnter.getName()!= null && bdglVisitorEnter.getName() != "") {
            // 这里可能需要根据实际情况调整实现
            // List<String> list = bdglPeopleService.selectPowerPeoleListes(bdglVisitorEnter.getName());
            // String str = list.stream().collect(Collectors.joining(","));
            // String[] split = str.split(",");
            // bdglVisitorEnter.setNames(split);
        }
        startPage();
        LoginUser loginUser = getLoginUser();
        Long userId = loginUser.getUserId();
        SysUser sysUser = sysUserService.selectUserById(userId);
        List<BdglVisitorEnter> list = bdglVisitorEnterService.selectBdglVisitorEnterList(bdglVisitorEnter);
        
        c:
        for (BdglVisitorEnter enter : list) {
            //如果当前登录用户有特殊审批权限  显示返回按钮

            if (enter.getStatus1().equals(2) || enter.getStatus2().equals(2) || enter.getStatus3().equals(2) || enter.getStatus4().equals(2) || (enter.getRejoin().equals(1) && enter.getIsReturn().equals("1"))
            ) {
                continue c;
            }
            boolean falg=false;
            if(sysUser.getPermissions()!=null){
                 falg = isFalg(sysUser,"3");
            }
            if (sysUser.getPermissions() != null) {
                if (enter.getStatus4().equals(1) || enter.getStatus4().equals(3) && falg) {
                    enter.setCommon("3");
                    continue c;
                }
                if (falg && enter.getPeopleId4() == null && (enter.getStatus3().equals(1) || enter.getStatus3().equals(3))) {
                    enter.setCommon("3");
                    continue c;
                }
                if (falg && enter.getPeopleId3() == null && enter.getPeopleId4() == null && (enter.getStatus2().equals(1) || enter.getStatus2().equals(3))) {
                    enter.setCommon("3");
                    continue c;
                }
                if (falg && enter.getPeopleId2() == null && enter.getPeopleId3() == null && enter.getPeopleId4() == null && (enter.getStatus1().equals(1) || enter.getStatus1().equals(3))) {
                    enter.setCommon("3");
                    continue c;
                }
            }
            if (enter.getStatus4() == 3 || enter.getStatus4() == 1) {
                continue c;
            }
            if (sysUser.getPermissions() != null) {
                //是否有特殊审批权限
                boolean falg2=false;
                if(sysUser.getPermissions()!=null){
                     falg2 = isFalg(sysUser,"0");
                }
                if (falg2) {
                    enter.setUnitId(loginUser.getUserId());
                    //如果特殊审批人和某级审批人相同   返回普通审批;
                    if (enter.getPeopleId1() != null) {
                        if (enter.getStatus1() == 0 && (enter.getStatus1() != 1 || enter.getStatus1() != 3)) {
                            enter.setShenPiJiBie("一级");
                            enter.setCommon("2");
                            if (loginUser.getUserId().equals(Long.valueOf(enter.getPeopleId1()))) {
                                enter.setCommon("1");
                            }
                            continue c;
                        }
                    }
                    if (enter.getPeopleId2() != null) {
                        if ((enter.getStatus2() == 0 && (enter.getStatus1() == 1 || enter.getStatus1() == 3)) || (enter.getPeopleId1() == null && enter.getStatus2() == 0)) {
                            enter.setShenPiJiBie("二级");
                            enter.setCommon("2");
                            if (loginUser.getUserId().equals(Long.valueOf(enter.getPeopleId2()))) {
                                enter.setCommon("1");
                            }
                            continue c;
                        }
                    }
                    if (enter.getPeopleId3() != null) {
                        if ((enter.getStatus3() == 0 && enter.getStatus3() != 1 && (enter.getStatus2() == 1 || enter.getStatus2() == 3)) || (enter.getPeopleId2() == null && enter.getStatus3() == 0)) {
                            enter.setShenPiJiBie("三级");
                            enter.setCommon("2");
                            if (loginUser.getUserId().equals(Long.valueOf(enter.getPeopleId3()))) {
                                enter.setCommon("1");
                            }
                            continue c;
                        }
                    }
                }
            }
            Long userId1 = loginUser.getUserId();
            if (enter.getPeopleId1() != null) {
                if (loginUser.getUserId().equals(Long.valueOf(enter.getPeopleId1()))) {
                    if (enter.getStatus1() == 0) {
                        enter.setCommon("1");
                        enter.setShenPiJiBie("一级");
                        continue c;
                    }
                }
            }
            if (enter.getPeopleId2() != null) {
                if (loginUser.getUserId().equals(Long.valueOf(enter.getPeopleId2()))) {
                    if(enter.getPeopleId1()==null&&enter.getStatus2() == 0){
                        enter.setCommon("1");
                        enter.setShenPiJiBie("二级");
                        continue c;
                    }
                    if (enter.getStatus2() == 0 && enter.getStatus1() == 1 || enter.getStatus1() == 3) {
                        enter.setCommon("1");
                        enter.setShenPiJiBie("二级");
                        continue c;
                    }
                }
            }
            if (enter.getPeopleId3() != null) {
                if (loginUser.getUserId().equals(Long.valueOf(enter.getPeopleId3()))) {
                    if(enter.getStatus3() == 0 && enter.getPeopleId2()==null && enter.getPeopleId1()==null){
                        enter.setCommon("1");
                        enter.setShenPiJiBie("三级");
                        continue c;
                    }
                    if (enter.getStatus3() == 0 && enter.getStatus2() == 1 || enter.getStatus2() == 3) {
                        enter.setCommon("1");
                        enter.setShenPiJiBie("三级");
                        continue c;

                    }
                }
            }
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
     * 导出访客进入列表
     */
//    @PreAuthorize("@ss.hasPermi('peopleChuRu:bdglVisitorEnter:export')")
    @Log(title = "访客进入", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglVisitorEnter bdglVisitorEnter) {
        List<BdglVisitorEnter> list = bdglVisitorEnterService.selectBdglVisitorEnterList(bdglVisitorEnter);
        ExcelUtil<BdglVisitorEnter> util = new ExcelUtil<BdglVisitorEnter>(BdglVisitorEnter.class);
        return util.exportExcel(list, "访客进入数据");
    }

    /**
     * 获取访客进入详细信息
     */
//    @PreAuthorize("@ss.hasPermi('peopleChuRu:bdglVisitorEnter:query')")
    @GetMapping(value = "/{id:[0-9]+}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        BdglVisitorEnter bdglVisitorEnter = bdglVisitorEnterService.selectBdglVisitorEnterById(id);
        //bdglVisitorEnter转Map
        Map<String, Object> map = BeanUtil.beanToMap(bdglVisitorEnter);
        map.put("leaveType",new Integer(bdglVisitorEnter.getLeaveType()));
        return AjaxResult.success(map);
    }

    /**
     * 新增访客进入
     */
//    @PreAuthorize("@ss.hasPermi('peopleChuRu:bdglVisitorEnter:add')")
    @Log(title = "访客进入", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglVisitorEnter bdglVisitorEnter) {
        bdglVisitorEnter.setCreatetime(DateUtils.getNowDate());
        bdglVisitorEnter.setRejoin(1);
        return toAjax(bdglVisitorEnterService.insertBdglVisitorEnter(bdglVisitorEnter));
    }

    /**
     * 修改访客进入
     */
//    @PreAuthorize("@ss.hasPermi('peopleChuRu:bdglVisitorEnter:edit')")
    @Log(title = "访客进入", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglVisitorEnter bdglVisitorEnter) {
        BdglVisitorEnter bdglVisitorEnter1 = bdglVisitorEnterService.selectBdglVisitorEnterById(bdglVisitorEnter.getId());
        updateBdglVisitorEnter(bdglVisitorEnter);
        return toAjax(bdglVisitorEnterService.updateBdglVisitorEnter(bdglVisitorEnter));
    }
    
    private void updateBdglVisitorEnter(BdglVisitorEnter bdglVisitorEnter) {
        String[] split = bdglVisitorEnter.getPeopleId().split(",");
        StringBuilder name = new StringBuilder();
        for (String s : split) {
            Visitor visitor = visitorService.selectVisitorById(Long.valueOf(s));
            name.append(visitor.getName()).append(",");
        }
        bdglVisitorEnter.setPeopleName(name.substring(0, name.length() - 1));
    }
    
    /**
     * 审批访客进入
     */
//    @PreAuthorize("@ss.hasPermi('peopleChuRu:bdglVisitorEnter:shenpi')")
    @Log(title = "访客进入", businessType = BusinessType.UPDATE)
    @PutMapping("/shenpi")
    public AjaxResult shenpi(@RequestBody BdglVisitorEnter bdglVisitorEnter) throws IOException {
        updateBdglVisitorEnter(bdglVisitorEnter);
        if (bdglVisitorEnter.getStatus1().equals(1) && bdglVisitorEnter.getStatus2().equals(1) && bdglVisitorEnter.getStatus3().equals(1) && bdglVisitorEnter.getIsReturn().equals("0") && bdglVisitorEnter.getRejoin() == 1) {
            String[] visitorIds = bdglVisitorEnter.getPeopleId().split(",");
            for (String visitorId : visitorIds) {
                Visitor visitor = visitorService.selectVisitorById(Long.valueOf(visitorId));
                if (visitor != null){
                    MultipartFile file = CommonController.getFile(visitor.getAvatar());
                    if(file == null){
                        return AjaxResult.error("请上传访客照片");
                    }

                    // 添加出口门闸信息
                    Map<String, Object> peopleInfoJson1 = new HashMap<>();
                    peopleInfoJson1.put("deviceId", bdglVisitorEnter.getDoorOut());
                    peopleInfoJson1.put("employeeNo", visitor.getEmployeeNumber());
                    peopleInfoJson1.put("userType", "visitor");
                    peopleInfoJson1.put("localUIRight", true);
                    peopleInfoJson1.put("doubleLockRight", true);
                    peopleInfoJson1.put("userVerifyMode", "face");
                    peopleInfoJson1.put("doorRight", "1,2");
                    JSONObject validJson1 = new JSONObject();
                    validJson1.put("enable", true);
                    validJson1.put("beginTime", bdglVisitorEnter.getFormattedStartTime());
                    validJson1.put("endTime", bdglVisitorEnter.getFormattedEndTime());
                    validJson1.put("timeType", "local");
                    peopleInfoJson1.put("Valid", validJson1);
                    peopleInfoManageController.add(peopleInfoJson1);

                    JSONObject faceInfoJson1 = new JSONObject();
                    faceInfoJson1.put("deviceId", bdglVisitorEnter.getDoorOut());
                    faceInfoJson1.put("faceLibType", "blackFD");
                    faceInfoJson1.put("FDID", 1);
                    faceInfoJson1.put("FPID", visitor.getEmployeeNumber());
                    faceInfoManageController.add(file,faceInfoJson1.toString());

                    // 添加入口门闸信息
                    Map<String, Object> peopleInfoJson2 = new HashMap<>();
                    peopleInfoJson2.put("deviceId", bdglVisitorEnter.getDoorIn());
                    peopleInfoJson2.put("employeeNo", visitor.getEmployeeNumber());
                    peopleInfoJson2.put("userType", "visitor");
                    peopleInfoJson2.put("localUIRight", true);
                    peopleInfoJson2.put("doubleLockRight", true);
                    peopleInfoJson2.put("userVerifyMode", "face");
                    peopleInfoJson2.put("doorRight", "1,2");
                    JSONObject validJson2 = new JSONObject();
                    validJson2.put("enable", true);
                    validJson2.put("beginTime", bdglVisitorEnter.getFormattedStartTime());
                    validJson2.put("endTime", bdglVisitorEnter.getFormattedEndTime());
                    validJson2.put("timeType", "local");
                    peopleInfoJson2.put("Valid", validJson2);
                    peopleInfoManageController.add(peopleInfoJson2);

                    JSONObject faceInfoJson2 = new JSONObject();
                    faceInfoJson2.put("deviceId", bdglVisitorEnter.getDoorIn());
                    faceInfoJson2.put("faceLibType", "blackFD");
                    faceInfoJson2.put("FDID", 1);
                    faceInfoJson2.put("FPID", visitor.getEmployeeNumber());
                    faceInfoManageController.add(file,faceInfoJson2.toString());
                }
            }
        }
        return toAjax(bdglVisitorEnterService.updateBdglVisitorEnter(bdglVisitorEnter));
    }
    
    /**
     * 特殊审批访客进入
     */
//    @PreAuthorize("@ss.hasPermi('peopleChuRu:bdglVisitorEnter:teshu')")
    @Log(title = "访客进入", businessType = BusinessType.UPDATE)
    @PutMapping("/teshu")
    public AjaxResult tshenpi(@RequestBody BdglVisitorEnter bdglVisitorEnter) {
        updateBdglVisitorEnter(bdglVisitorEnter);
        return toAjax(bdglVisitorEnterService.updateBdglVisitorEnter(bdglVisitorEnter));
    }

    /**
     * 删除访客进入
     */
//    @PreAuthorize("@ss.hasPermi('peopleChuRu:bdglVisitorEnter:remove')")
    @Log(title = "访客进入", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids:[0-9,]+}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(bdglVisitorEnterService.deleteBdglVisitorEnterByIds(ids));
    }

    public void enterVisitor(Long id) {
        //  获取当前时间
        LocalDateTime now = LocalDateTime.now();
        Visitor visitor = visitorService.selectVisitorById(id);
        List<BdglVisitorEnter> bdglVisitorEnters = bdglVisitorEnterService.selectBdglVisitorEnterByPeopleId(visitor.getId());
        for(BdglVisitorEnter bdglVisitorEnter : bdglVisitorEnters) {
            if(bdglVisitorEnter.getStatus1().equals(1) && bdglVisitorEnter.getStatus2().equals(1) && bdglVisitorEnter.getStatus3().equals(1) && bdglVisitorEnter.getIsReturn().equals("0") && bdglVisitorEnter.getRejoin() == 1) {
                LocalDateTime startTime = bdglVisitorEnter.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                LocalDateTime endTime = bdglVisitorEnter.getEndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() ;
                if(startTime.isBefore(now) && endTime.isAfter(now) ) {
                    String[] peopepleIds = bdglVisitorEnter.getPeopleId().split(",");
                    bdglVisitorEnter.setIsReturn("0");
                    bdglVisitorEnter.setRejoin(0);
                    bdglVisitorEnter.setReturnTime(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    bdglVisitorEnterService.updateBdglVisitorEnter(bdglVisitorEnter);
                }
            }
        }
    }

    public void leaveVisitor(Long id) {
        //  获取当前时间
        LocalDateTime now = LocalDateTime.now();
        Visitor visitor = visitorService.selectVisitorById(id);
        List<BdglVisitorEnter> bdglVisitorEnters = bdglVisitorEnterService.selectBdglVisitorEnterByPeopleId(visitor.getId());
        for(BdglVisitorEnter bdglVisitorEnter : bdglVisitorEnters) {
            if(bdglVisitorEnter.getStatus1().equals(1) && bdglVisitorEnter.getStatus2().equals(1) && bdglVisitorEnter.getStatus3().equals(1) && bdglVisitorEnter.getIsReturn().equals("0") && bdglVisitorEnter.getRejoin() == 0) {
                LocalDateTime startTime = bdglVisitorEnter.getStartTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                LocalDateTime endTime = bdglVisitorEnter.getEndTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime() ;
                if(startTime.isBefore(now) && endTime.isAfter(now) ) {
                    String[] peopepleIds = bdglVisitorEnter.getPeopleId().split(",");
                    boolean flag = false;
                    for(String peopleId : peopepleIds) {
                        int status = (Integer)(redisTemplate.opsForHash().get("visitorStatus",String.valueOf(peopleId)));
                        if (status == 1){
                            flag = true;
                        }
                    }
                    if (!flag){
                        bdglVisitorEnter.setIsReturn("1");
                        bdglVisitorEnter.setRejoin(1);
                        bdglVisitorEnter.setDepartureTime(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                        bdglVisitorEnterService.updateBdglVisitorEnter(bdglVisitorEnter);
                    }
                }
            }
        }
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
            privateCarQuery.setState("1"); // 在位状态

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
            thebusQuery.setStatus("1"); // 在位状态

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
}