package com.supervision.web.controller.militaryvehicleManagement;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.supervision.common.annotation.Log;
import com.supervision.common.core.controller.BaseController;
import com.supervision.common.core.domain.AjaxResult;
import com.supervision.common.core.domain.entity.SysUser;
import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.common.core.page.TableDataInfo;
import com.supervision.common.enums.BusinessType;
import com.supervision.common.utils.SecurityUtils;
import com.supervision.common.utils.StringUtils;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.militaryvehicleManagement.domain.*;
import com.supervision.militaryvehicleManagement.service.*;
import com.supervision.system.service.ISysUserService;
import com.supervision.web.Hikvision.carDoor.web.controller.NameListController;
import com.supervision.web.Hikvision.carDoor.web.entity.NameList;
import com.supervision.web.Hikvision.peopleDoor.web.entity.Device;
import com.supervision.web.Hikvision.peopleDoor.web.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用车申请Controller
 *
 * @author supervision
 * @date 2022-03-04
 */
@RestController
@RequestMapping("/militaryvehicleManagement/thebusApply")
public class BdglThebusApplyController extends BaseController {
    @Autowired
    private IBdglThebusApplyService bdglThebusApplyService;

    @Autowired
    private ISysUserService iSysUserService;

    @Autowired
    private IBdglDepartureInfoService bdglDepartureInfoService;

    @Autowired
    private IBdglDriverService bdglDriverService;

    @Autowired
    private IBdglThebusService bdglThebusService;

    @Autowired
    private IBdglPrivateCarService bdglPrivateCarService;

    @Autowired
    private IBdglAccessPeopleService bdglAccessPeopleService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private NameListController nameListController;

    @DeleteMapping("/single/{id}")
    public AjaxResult remove(@PathVariable Long id) {
        return toAjax(bdglThebusApplyService.deleteBdglThebusApplyById(id));
    }

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 查询用车申请列表
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:thebusApply:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglThebusApply bdglThebusApply) {
        //获取当前登陆人的id
        LoginUser loginUser = getLoginUser();
        Long userId = loginUser.getUserId();
        //1、判断当前人员是不是营级审批人
        //2、判断当前人员是不是初审员
        //3、判断是不是一级审批
        //4、判断是不是二级审批
        //5、判断是不是三级审批
        //  if() {}

        bdglThebusApply.setIsTeshuYongChe("0"); //正常用车

        //定义取值List
        List<BdglThebusApply> list = new ArrayList<>();
        //判断当前用户权限
        SysUser sysUser = iSysUserService.selectUserById(userId);

        //通过userID 获取到当前登录人的角色  判断当前人员是否有初审的状态
        List<String> roleKeylist = iSysUserService.selectUserRoleKey(userId);
        boolean iscunzai = false;
        for (String string : roleKeylist) {
            if ("EXAMINER".equals(string)) {
                iscunzai = true;
            }

        }
        startPage();
        boolean falg=false;
        if(sysUser.getPermissions()!=null){
            String[] split = sysUser.getPermissions().split(",");
            for (String s : split) {
                if(s.equals("1")){
                    falg=true;
                    break;
                }
            }
        }
        if (sysUser.getPermissions()!=null&&falg) { //判断特殊权限
            list = bdglThebusApplyService.selectBdglThebusApplyList(bdglThebusApply);
            for (BdglThebusApply thebusApply : list) {
                IsShenpiType(userId, thebusApply, iscunzai);
                if ("3".equals(thebusApply.getShenpijiedian()) || "4".equals(thebusApply.getShenpijiedian()) || "5".equals(thebusApply.getShenpijiedian())) {
                    thebusApply.setIsTeShu("1"); //定义特殊审批
                    IsShenpiType(userId, thebusApply, iscunzai);
                }
            }
            return getDataTable(list);
        } else {
            //遍历审批信息
            list = bdglThebusApplyService.selectBdglThebusApplyList(bdglThebusApply);
            if (list.size() > 0) {
                for (BdglThebusApply thebusApply : list) {
                    IsShenpiType(userId, thebusApply, iscunzai);
                }
            }
            return getDataTable(list);
        }
    }

    /**
     * 审批方法1
     * @param userId
     * @param thebusApply
     * @param iscunzai
     */
    public void IsShenpiType(Long userId, BdglThebusApply thebusApply, boolean iscunzai) {
        Integer unitPeopleId = thebusApply.getUnitPeopleId();
        //根据accessPeopleId获取对应的userId
        AccessPeople people1 = null;
        AccessPeople people2 = null;
        AccessPeople people3 = null;
        if (thebusApply.getpeople1Id() != null) {
            people1 = bdglAccessPeopleService.getById(thebusApply.getpeople1Id());
        }
        if (thebusApply.getpeople2Id() != null) {
            people2 = bdglAccessPeopleService.getById(thebusApply.getpeople2Id());
        }
        if (thebusApply.getpeople3Id() != null) {
            people3 = bdglAccessPeopleService.getById(thebusApply.getpeople3Id());
        }
        if ((userId + "").equals(unitPeopleId + "") && !thebusApply.getUnitPeopleState().equals("2")) { //营级审批人
            thebusApply.setIsCaoZuo("0");
            thebusApply.setYingjicaozuoren("yingji"); //营级操作人
        } else if (iscunzai == true && thebusApply.getShenpijiedian().equals("2") && !thebusApply.getChushenState().equals("2")) { //初审员
            thebusApply.setIsCaoZuo("0");
            thebusApply.setChushenyuan("chushen"); //初审操作人
        } else if (people1 != null && (userId + "").equals(people1.getUserId() + "") && thebusApply.getShenpijiedian().equals("3") && (!thebusApply.getpeople1State().equals("0") || !thebusApply.getpeople1State().equals("4"))) { //一级审批人
            thebusApply.setIsCaoZuo("0");
            thebusApply.setYingjicaozuoren("oneji"); //一级审批人
            thebusApply.setIsTeShu(null);


        } else if (people2 != null && (userId + "").equals(people2.getUserId() + "") && thebusApply.getShenpijiedian().equals("4") && (!thebusApply.getpeople2State().equals("0") || !thebusApply.getpeople2State().equals("4"))) { //二级审批人
            thebusApply.setIsCaoZuo("0");
            thebusApply.setYingjicaozuoren("twoji"); //二级审批人
            thebusApply.setIsTeShu(null);

        } else if (people3 != null && (userId + "").equals(people3.getUserId()+ "") && thebusApply.getShenpijiedian().equals("5") && !thebusApply.getpeople3State().equals("0")) { //三级审批人

            thebusApply.setIsCaoZuo("0");
            thebusApply.setYingjicaozuoren("threeji"); //三级审批人
            thebusApply.setIsTeShu(null);
        } else { //其他人员
            thebusApply.setIsCaoZuo("1");
        }
    }

    /**
     * 营级审批
     */
    @GetMapping("/yingjishenpi")
    public AjaxResult yingjishenpi(BdglThebusApply bdglThebusApply) {
        //判断当前登录人是否有可以审批
        LoginUser loginUser = getLoginUser();
        Long userId = loginUser.getUserId();
        if (Long.valueOf(bdglThebusApply.getUnitPeopleId()).equals(userId) || "1".equals(bdglThebusApply.getIsTeShu())) {
            if ("0".equals(bdglThebusApply.getIsTongguo())) { //通过
                if ("1".equals(bdglThebusApply.getIsTeShu())) {
                    bdglThebusApply.setUnitPeopleState("4"); //特殊审批状态
                } else {
                    bdglThebusApply.setUnitPeopleState("2"); //审批状态
                }
                bdglThebusApply.setShenpijiedian("2"); //当前审批角色
                bdglThebusApplyService.updateBdglThebusApply(bdglThebusApply);
                return AjaxResult.success("审批成功！");
            } else if ("1".equals(bdglThebusApply.getIsTongguo())) { //不通过
                bdglThebusApply.setUnitPeopleState("3");
                bdglThebusApply.setStatus("3");
                bdglThebusApplyService.updateBdglThebusApply(bdglThebusApply);
                return AjaxResult.success("审批成功！");
            } else {
                return AjaxResult.error("无效审批，请联系管理员！");
            }
        } else {
            return AjaxResult.error("该人员无法审批当前数据！");
        }
    }

    /**
     * 初审审批
     *
     * @return
     */
    @GetMapping("/chushenshenpi")
    public AjaxResult chushenshenpi(BdglThebusApply bdglThebusApply) {
        //首先获取是不是初审员
        //获取当前人员的角色
        LoginUser loginUser = SecurityUtils.getLoginUser();
        Long userId = loginUser.getUserId(); //获取到userID
        //通过userID 获取到当前登录人的角色  判断当前人员是否有初审的状态
        List<String> roleKeylist = iSysUserService.selectUserRoleKey(userId);
        boolean iscunzai = false;
        for (String string : roleKeylist) {
            if ("EXAMINER".equals(string)) {
                iscunzai = true;
            }
        }
        if ("1".equals(bdglThebusApply.getIsTeShu())) {
            iscunzai = true;
        }
        //查询到所有数据
        BdglThebusApply applyinfo = bdglThebusApplyService.selectBdglThebusApplyById(bdglThebusApply.getId());
        //判断当前数据是不是未处理
        if (iscunzai == true) {
            if ("2".equals(applyinfo.getUnitPeopleState()) || "4".equals(applyinfo.getUnitPeopleState())) {
                if ("0".equals(bdglThebusApply.getIsTongguo())) { //通过
                    if ("1".equals(bdglThebusApply.getIsTeShu())) {
                        bdglThebusApply.setChushenState("4"); //特殊审批状态
                    } else {
                        bdglThebusApply.setChushenState("2"); //审批状态
                    }
                    if (bdglThebusApply.getpeople1Id() != null) {
                        bdglThebusApply.setShenpijiedian("3"); //当前审批角色
                    }
                    if (bdglThebusApply.getpeople2Id() != null && bdglThebusApply.getpeople1Id() == null) {
                        bdglThebusApply.setShenpijiedian("4"); //当前审批角色

                    }
                    if (bdglThebusApply.getpeople3Id() != null && bdglThebusApply.getpeople2Id() == null && bdglThebusApply.getpeople1Id() == null) {
                        bdglThebusApply.setShenpijiedian("5"); //当前审批角色

                    }
                    bdglThebusApplyService.updateBdglThebusApply(bdglThebusApply);
                    return AjaxResult.success("审批成功！");
                } else if ("1".equals(bdglThebusApply.getIsTongguo())) { //不通过
                    bdglThebusApply.setChushenState("3");
                    bdglThebusApply.setStatus("3");
                    bdglThebusApplyService.updateBdglThebusApply(bdglThebusApply);
                    return AjaxResult.success("审批成功！");
                } else {
                    return AjaxResult.error("无效审批，请联系管理员！");
                }
            } else {
                return AjaxResult.error("当前数据，营级审批人还未审核，无法审批！");
            }
        } else {
            return AjaxResult.error("该人员无法审批！");
        }
    }

    /**
     * 一级审批操作
     *
     * @param bdglThebusApply
     * @return
     */
    @GetMapping("/yijishenpi")
    public AjaxResult yijishenpi(BdglThebusApply bdglThebusApply) {
        //首先获取是不是初审员
        //获取当前人员的角色
        LoginUser loginUser = SecurityUtils.getLoginUser();
        Long userId = loginUser.getUserId(); //获取到userID
        AccessPeople people1 = null;
        if (bdglThebusApply.getpeople1Id() != null) {
            people1 = bdglAccessPeopleService.getById(bdglThebusApply.getpeople1Id());
        }
        //判断当前登录人是不是一级审批人
        if (Long.valueOf(people1.getUserId()).equals(userId) || "1".equals(bdglThebusApply.getIsTeShu())) {
            if ("2".equals(bdglThebusApply.getChushenState()) || "4".equals(bdglThebusApply.getChushenState())) {
                if ("0".equals(bdglThebusApply.getIsTongguo())) { //通过
                    if ("1".equals(bdglThebusApply.getIsTeShu())) {
                        bdglThebusApply.setpeople1State("4"); //特殊审批状态
                    } else {
                        bdglThebusApply.setpeople1State("2"); //审批状态
                    }
                    if (bdglThebusApply.getpeople2Id() != null) {

                        bdglThebusApply.setShenpijiedian("4"); //当前审批角色
                    }
                    if (bdglThebusApply.getpeople3Id() != null && bdglThebusApply.getpeople2Id() == null) {

                        bdglThebusApply.setShenpijiedian("5"); //当前审批角色
                    }
                    if (bdglThebusApply.getpeople3Id() == null && bdglThebusApply.getpeople2Id() == null) {
                        bdglThebusApply.setStatus("2"); //整体审批完成
//                        addVehicleAuthorization(bdglThebusApply);
                    }
                    bdglThebusApply.setpeople1Authtime(new Date());
                    bdglThebusApplyService.updateBdglThebusApply(bdglThebusApply);
                    return AjaxResult.success("审批成功！");
                } else if ("1".equals(bdglThebusApply.getIsTongguo())) { //不通过
                    bdglThebusApply.setpeople1State("3");
                    bdglThebusApply.setStatus("3");
                    bdglThebusApply.setpeople1Authtime(new Date());
                    bdglThebusApplyService.updateBdglThebusApply(bdglThebusApply);
                    return AjaxResult.success("审批成功！");
                } else {
                    return AjaxResult.error("无效审批，请联系管理员！");
                }
            } else {
                return AjaxResult.error("当前数据，初审还未审核，无法审批！");
            }
        } else {
            return AjaxResult.error("该人员无法审批当前数据！");
        }
    }

    /**
     * 二级审批人审批
     *
     * @param bdglThebusApply
     * @return
     */
    @GetMapping("/erjishenpi")
    public AjaxResult erjishenpi(BdglThebusApply bdglThebusApply) {
        //首先获取是不是初审员
        //获取当前人员的角色
        LoginUser loginUser = SecurityUtils.getLoginUser();
        Long userId = loginUser.getUserId(); //获取到userID
        AccessPeople people2 = null;
        if (bdglThebusApply.getpeople2Id() != null) {
            people2 = bdglAccessPeopleService.getById(bdglThebusApply.getpeople2Id());
        }
        //首先判断当前登录人是否是二级审批人员
        if (Long.valueOf(people2.getUserId()).equals(userId) || "1".equals(bdglThebusApply.getIsTeShu())) {
            //如果一级审批审批完毕
            if ("0".equals(bdglThebusApply.getIsTongguo())) { //通过
                if ("1".equals(bdglThebusApply.getIsTeShu())) {
                    bdglThebusApply.setpeople2State("4"); //特殊审批状态
                } else {
                    bdglThebusApply.setpeople2State("2"); //审批状态
                }
                if (bdglThebusApply.getpeople3Id() != null) {
                    bdglThebusApply.setShenpijiedian("5"); //当前审批角色
                }
                if (bdglThebusApply.getpeople3Id() == null) {
                    bdglThebusApply.setStatus("2"); //整体审批完成
//                    addVehicleAuthorization(bdglThebusApply);
                }

                bdglThebusApply.setpeople2Authtime(new Date());
                bdglThebusApplyService.updateBdglThebusApply(bdglThebusApply);
                return AjaxResult.success("审批成功！");
            } else if ("1".equals(bdglThebusApply.getIsTongguo())) { //不通过
                bdglThebusApply.setpeople2State("3");
                bdglThebusApply.setStatus("3");
                bdglThebusApply.setpeople2Authtime(new Date());
                bdglThebusApplyService.updateBdglThebusApply(bdglThebusApply);
                return AjaxResult.success("审批成功！");
            } else {
                return AjaxResult.error("无效审批，请联系管理员！");
            }

        } else {
            return AjaxResult.error("该人员无法审批当前数据！");
        }
    }

    /**
     * 三级审批人审批
     *
     * @param bdglThebusApply
     * @return
     */
    @GetMapping("/sanjishenpi")
    public AjaxResult sanjishenpi(BdglThebusApply bdglThebusApply) {
        //首先获取是不是初审员
        //获取当前人员的角色
        LoginUser loginUser = SecurityUtils.getLoginUser();
        Long userId = loginUser.getUserId(); //获取到userID
        AccessPeople people3 = null;
        if (bdglThebusApply.getpeople3Id() != null) {
            people3 = bdglAccessPeopleService.getById(bdglThebusApply.getpeople3Id());
        }
        //首先判断当前登录人是否是三级审批人员
        if (Long.valueOf(people3.getUserId()).equals(userId) || "1".equals(bdglThebusApply.getIsTeShu())) {
            //如果一级审批审批完毕
            if ("0".equals(bdglThebusApply.getIsTongguo())) { //通过
                if ("1".equals(bdglThebusApply.getIsTeShu())) {
                    bdglThebusApply.setpeople3State("4"); //特殊审批状态
                } else {
                    bdglThebusApply.setpeople3State("2"); //审批状态
                }
                bdglThebusApply.setStatus("2"); //整体审批完成
                // bdglThebusApply.setShenpijiedian("6"); //当前审批角色
                bdglThebusApply.setpeople3Authtime(new Date());
                bdglThebusApplyService.updateBdglThebusApply(bdglThebusApply);
//                addVehicleAuthorization(bdglThebusApply);

                return AjaxResult.success("审批成功！");
            } else if ("1".equals(bdglThebusApply.getIsTongguo())) { //不通过
                bdglThebusApply.setpeople3State("3");
                bdglThebusApply.setStatus("3");
                bdglThebusApply.setpeople3Authtime(new Date());
                bdglThebusApplyService.updateBdglThebusApply(bdglThebusApply);
                return AjaxResult.success("审批成功！");
            } else {
                return AjaxResult.error("无效审批，请联系管理员！");
            }

        } else {
            return AjaxResult.error("该人员无法审批当前数据！");
        }
    }
    /**
     * 为车辆添加授权名单
     * @param bdglThebusApply 用车申请信息
     */
    private void addVehicleAuthorization(BdglThebusApply bdglThebusApply) {
        // 获取所有设备
        List<Device> deviceList = deviceService.list();
        Long deviceId = null;
        for (Device device : deviceList) {
            // 查找出type为carDoor的设备id
            if ("carDoor".equals(device.getType())) {
                deviceId = device.getId();
            }
        }

        // 添加车辆授权名单
        NameList nameList = new NameList();
        nameList.setDeviceId(deviceId);
        nameList.setLicensePlate(bdglThebusApply.getCarIds());
        nameList.setType(0);
        nameList.setAccessStartTime(bdglThebusApply.getAuthStime());
        nameList.setAccessEndTime(bdglThebusApply.getAuthEtime());

        // 将nameList转成Map<String, Object>类型并添加
        Map<String, Object> nameListMap = BeanUtil.beanToMap(nameList);
        nameListController.add(nameListMap);
    }

    /**
     * 出车列表
     *
     * @param bdglThebusApply
     * @return
     */
    @GetMapping("/chuchelist")
    public TableDataInfo chuchelist(BdglThebusApply bdglThebusApply) {
        LoginUser loginUser = getLoginUser();
        SysUser sysUser = iSysUserService.selectUserById(loginUser.getUserId());
        startPage();
        List<BdglThebusApply> lists = new ArrayList<>();
        //今日值班
        if ("1".equals(bdglThebusApply.getChaxunType())) {
            bdglThebusApply.setStatus("2");
            //获取今日日期 yyyy-MM-dd
            Date date = DateUtil.parse(DateUtil.today(),"yyyy-MM-dd");
            bdglThebusApply.setOuttime(date);
            //当天出车数据
            List<BdglThebusApply> list = bdglThebusApplyService.selectBdglThebusApplyLists(bdglThebusApply);
            lists.addAll(list);
            /*for (BdglThebusApply bdglThebus : list) {
                String today = sdf.format(new Date());
                if (bdglThebus.getOuttime() != null) { //判断出车时间是否为空
                    if (today.equals(sdf.format(bdglThebus.getOuttime()))) {
                        lists.add(bdglThebus);
                    }
                }
            }*/
            //明日值班
        } else if ("2".equals(bdglThebusApply.getChaxunType())) {
            //获取明天值班日期
            DateTime time = DateUtil.tomorrow();
            Date tomorrow = DateUtil.beginOfDay(time);

            //出车状态
            bdglThebusApply.setStatus("2");
            bdglThebusApply.setOuttime(tomorrow);
            //明日出车数据
            List<BdglThebusApply> list = bdglThebusApplyService.selectBdglThebusApplyLists(bdglThebusApply);
            lists.addAll(list);
            /*for (BdglThebusApply bdglThebus : list) {
                if (bdglThebus.getOuttime() != null) {////判断出车时间是否为空
                    if (tomorrow.equals(sdf.format(bdglThebus.getOuttime()))) {
                        lists.add(bdglThebus);
                    }
                }

            }*/
        } else if ("0".equals(bdglThebusApply.getChaxunType())) {
            bdglThebusApply.setStatus("2");
            List<BdglThebusApply> list = bdglThebusApplyService.selectBdglThebusApplyLists(bdglThebusApply);
            lists = list;
        } else {
            return getDataTable(null);
        }
        boolean falg=false;
        if(sysUser.getPermissions()!=null){

            String[] split = sysUser.getPermissions().split(",");
            for (String s : split) {
                if(s.equals("1")){
                    falg=true;
                    break;
                }
            }
        }
        for (BdglThebusApply list : lists) {
            BdglDepartureInfo bdglDepartureInfo = new BdglDepartureInfo();
            bdglDepartureInfo.setShengqingId(list.getId());
            //查询车辆信息
            List<BdglDepartureInfo> bdglDepartureInfos = bdglDepartureInfoService.selectBdglDepartureInfoList(bdglDepartureInfo);
            if (bdglDepartureInfos != null && bdglDepartureInfos.size() > 0){
                list.setBdglDepartureInfos(bdglDepartureInfos);
            }
            if (sysUser.getPermissions() != null && falg) {
                if (list.getOutstatus().equals("1")) {
                    if ((list.getpeople3State().equals("2") || list.getpeople3State().equals("4"))) {
                        if (list.getPlaceState() != null && !list.getPlaceState().equals("1")) {

                            list.setPlaceState("2");
                        }
                    }
                    if ((list.getpeople2State().equals("2") || list.getpeople3State().equals("4")) && list.getpeople3Id() == null) {
                        if (list.getPlaceState() != null && !list.getPlaceState().equals("1")) {

                            list.setPlaceState("2");
                        }
                    }
                    if ((list.getpeople1State().equals("2") || list.getpeople1State().equals("4")) && list.getpeople1State() == null && list.getpeople2Id() == null) {
                        if (list.getPlaceState() != null && !list.getPlaceState().equals("1")) {

                            list.setPlaceState("2");
                        }
                    }

                    if (list.getIsTeshuYongChe() != null && list.getIsTeshuYongChe().equals("1")) {
                        if (list.getPlaceState() != null && !list.getPlaceState().equals("1")) {

                            list.setPlaceState("2");
                        }
                    }
                }
            }
        }

        return getDataTable(lists);
    }

    /**
     * 添加出车列表
     *
     * @param
     * @return
     */
    @PostMapping("/chuchequren")
    public AjaxResult chuchequren(@RequestBody BdglThebusApply bdglThebusApply) {
        List<String> carUintIdList =new ArrayList<>();
        List<BdglDepartureInfo> bdglDepartureInfos = bdglThebusApply.getBdglDepartureInfos();
        String vehicleType = bdglThebusApply.getVehicleType();
        if(bdglDepartureInfos!=null){

            for (BdglDepartureInfo bdglDepartureInfo : bdglDepartureInfos) {
                /**
                 * 获取司机信息
                 */
                BdglDriver bdglDriver = bdglDriverService.selectBdglDriverById(Integer.parseInt(bdglDepartureInfo.getDriverId() + ""));
                bdglDepartureInfo.setDriverName(bdglDriver.getPeopleName()); // 驾驶员姓名
                bdglDepartureInfo.setDriverPhone(bdglDriver.getContact()); // 驾驶员电话号
                bdglDepartureInfo.setShengqingId(bdglThebusApply.getId());

                /*
                 * 根据 vehicleType 获取对应车辆信息
                 */
                if ("MILITARY".equalsIgnoreCase(vehicleType)) {
                    // 处理军车
                    BdglThebus bus = bdglThebusService.selectBdglThebusById(bdglDepartureInfo.getBdglThebusId());
                    if (bus != null) {
                        if (bus.getVehiclenumber() != null) {
                            bdglDepartureInfo.setVehiclenumber(bus.getVehiclenumber());
                        }
                        if (bus.getVehiclemodel() != null) {
                            bdglDepartureInfo.setVehiclemodel(bus.getVehiclemodel());
                        }
                    }
                    // 更新军车状态
                    BdglThebus bt = new BdglThebus();
                    bt.setStatus("3"); // 出车中
                    bt.setId(bdglDepartureInfo.getBdglThebusId());
                    bdglThebusService.updateBdglThebus(bt);
                } else if ("PRIVATE".equalsIgnoreCase(vehicleType)) {
                    // 处理私家车
                    BdglPrivateCar privateCar = bdglPrivateCarService.selectBdglPrivateCarById(bdglDepartureInfo.getBdglThebusId());
                    if (privateCar != null) {
                        if (privateCar.getVehiclenumber() != null) {
                            bdglDepartureInfo.setVehiclenumber(privateCar.getVehiclenumber());
                        }
                        if (privateCar.getVehiclemodel() != null) {
                            bdglDepartureInfo.setVehiclemodel(privateCar.getVehiclemodel());
                        }
                    }
                    // 更新私家车状态
                    BdglPrivateCar bp = new BdglPrivateCar();
                    bp.setState("3"); // 出车中
                    bp.setId(bdglDepartureInfo.getBdglThebusId());
                    bdglPrivateCarService.updateBdglPrivateCar(bp);
                }
                /**
                 * 插入出车明细
                 */
                bdglDepartureInfoService.insertBdglDepartureInfo(bdglDepartureInfo);
                /**
                 * 封装用车单位ID
                 */
                if (bdglDepartureInfo.getCarUnitId() != null){
                    carUintIdList.add(String.valueOf(bdglDepartureInfo.getCarUnitId()));
                }
            }
        }else{
            return AjaxResult.error("请填写出车信息");
        }
        String carUnitId = StringUtils.join(carUintIdList, ",");
        bdglThebusApply.setCarUnitId(carUnitId);
        bdglThebusApply.setOuttime(new Date());
        bdglThebusApply.setCreatetime(new Date());
        bdglThebusApply.setUpdatetime(new Date());
        bdglThebusApply.setPlaceState("2");
        bdglThebusApplyService.updateBdglThebusApply(bdglThebusApply);
        return AjaxResult.success("200");
    }

    //修改出车列表
    @PostMapping("/updatechuchequren")
    public AjaxResult updatechuchequren(@RequestBody BdglThebusApply thebusApply) {
        int result = bdglThebusApplyService.updateKeChuChe(thebusApply);
        return result > 0 ? AjaxResult.success("修改成功") : AjaxResult.error("修改失败");
    }


    /**
     * 手动添加出车列表
     *
     * @param
     * @return
     */
    @PutMapping("/addchuchequren")
    public AjaxResult addchuchequren(@RequestBody BdglThebusApply bdglThebusApply) {
        String vehicleType = bdglThebusApply.getVehicleType();

        bdglThebusApply.setOuttime(new Date());
        bdglThebusApply.setStatus("2");
        bdglThebusApply.setIsTeshuYongChe("1");
        bdglThebusApply.setCreatetime(new Date());
        bdglThebusApply.setUpdatetime(new Date());
        bdglThebusApplyService.insertBdglThebusApply(bdglThebusApply);

        List<BdglDepartureInfo> bdglDepartureInfos = bdglThebusApply.getBdglDepartureInfos();
        for (BdglDepartureInfo bdglDepartureInfo : bdglDepartureInfos) {
            /**
             * 获取司机信息
             */
            BdglDriver bdglDriver = bdglDriverService.selectBdglDriverById(Integer.parseInt(bdglDepartureInfo.getDriverId() + ""));
            bdglDepartureInfo.setDriverName(bdglDriver.getPeopleName()); //驾驶员姓名
            bdglDepartureInfo.setDriverPhone(bdglDriver.getContact()); // 驾驶员电话号
            bdglDepartureInfo.setShengqingId(bdglThebusApply.getId());

            /*
             * 根据 vehicleType 获取对应车辆信息
             */
            if ("MILITARY".equalsIgnoreCase(vehicleType)) {
                // 处理军车
                BdglThebus bus = bdglThebusService.selectBdglThebusById(bdglDepartureInfo.getBdglThebusId());
                if (bus != null) {
                    if (bus.getVehiclenumber() != null) {
                        bdglDepartureInfo.setVehiclenumber(bus.getVehiclenumber());
                    }
                    if (bus.getVehiclemodel() != null) {
                        bdglDepartureInfo.setVehiclemodel(bus.getVehiclemodel());
                    }
                }
                // 更新军车状态
                BdglThebus bt = new BdglThebus();
                bt.setStatus("3"); // 出车中
                bt.setId(bdglDepartureInfo.getBdglThebusId());
                bdglThebusService.updateBdglThebus(bt);
            } else if ("PRIVATE".equalsIgnoreCase(vehicleType)) {
                // 处理私家车
                BdglPrivateCar privateCar = bdglPrivateCarService.selectBdglPrivateCarById(bdglDepartureInfo.getBdglThebusId());
                if (privateCar != null) {
                    if (privateCar.getVehiclenumber() != null) {
                        bdglDepartureInfo.setVehiclenumber(privateCar.getVehiclenumber());
                    }
                    if (privateCar.getVehiclemodel() != null) {
                        bdglDepartureInfo.setVehiclemodel(privateCar.getVehiclemodel());
                    }
                }
                // 更新私家车状态
                BdglPrivateCar bp = new BdglPrivateCar();
                bp.setState("3"); // 出车中
                bp.setId(bdglDepartureInfo.getBdglThebusId());
                bdglPrivateCarService.updateBdglPrivateCar(bp);
            }
            bdglDepartureInfoService.insertBdglDepartureInfo(bdglDepartureInfo);
        }
        return AjaxResult.success("200");
    }


    /**
     * 查看出车列表
     *
     * @param
     * @return
     */
    @GetMapping("/lookchuchelist/{id}")
    public AjaxResult lookchuchelist(@PathVariable Long id) {

        BdglThebusApply bdglThebusApplys = bdglThebusApplyService.selectBdglThebusApplyById(id);//查询用车申请对象
        BdglDepartureInfo bdglDepartureInfo = new BdglDepartureInfo();
        bdglDepartureInfo.setShengqingId(bdglThebusApplys.getId());
        List<BdglDepartureInfo> bdglDepartureInfos = bdglDepartureInfoService.selectBdglDepartureInfoList(bdglDepartureInfo);
        bdglThebusApplys.setBdglDepartureInfos(bdglDepartureInfos);
        return AjaxResult.success(bdglThebusApplys);
    }

    /**
     * 导出用车申请列表
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:thebusApply:export')")
    @Log(title = "用车申请", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglThebusApply bdglThebusApply) {
        List<BdglThebusApply> list = bdglThebusApplyService.selectBdglThebusApplyList(bdglThebusApply);
        ExcelUtil<BdglThebusApply> util = new ExcelUtil<BdglThebusApply>(BdglThebusApply.class);
        return util.exportExcel(list, "用车申请数据");
    }

    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:thebusApply:export')")
    @Log(title = "可出车列表", businessType = BusinessType.EXPORT)
    @GetMapping("/exportThebusAvailableList")
    public AjaxResult exportThebusAvailableList(BdglThebusApply bdglThebusApply) {
        bdglThebusApply.setStatus("2");
        List<BdglThebusApply> list = bdglThebusApplyService.selectBdglThebusApplyList(bdglThebusApply);
        ExcelUtil<BdglThebusApply> util = new ExcelUtil<BdglThebusApply>(BdglThebusApply.class);
        return util.exportExcel(list, "可出车列表数据");
    }
    /**
     * 获取用车申请详细信息
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:thebusApply:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(bdglThebusApplyService.selectBdglThebusApplyById(id));
    }

    /**
     * 新增用车申请
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:thebusApply:add')")
    @Log(title = "用车申请", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglThebusApply bdglThebusApply) {
        bdglThebusApply.setUnitPeopleState("1"); //营级审批人
        bdglThebusApply.setChushenState("1"); //初审审批状态
        bdglThebusApply.setpeople1State("1"); //一级审批人状态
        bdglThebusApply.setpeople2State("1"); //二级审批人状态
        bdglThebusApply.setpeople3State("1"); //三级审批人状态
        bdglThebusApply.setStatus("1"); //整体审批状态
        bdglThebusApply.setShenpijiedian("1"); //当前审批角色
        bdglThebusApply.setIsTeshuYongChe("0"); //正常用车
        bdglThebusApply.setCreatetime(new Date());
        bdglThebusApply.setUpdatetime(new Date());
        return toAjax(bdglThebusApplyService.insertBdglThebusApply(bdglThebusApply));
    }

    /**
     * 修改用车申请
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:thebusApply:edit')")
    @Log(title = "用车申请", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglThebusApply bdglThebusApply) {
        bdglThebusApply.setUpdatetime(new Date());
        return toAjax(bdglThebusApplyService.updateBdglThebusApply(bdglThebusApply));
    }

    /**
     * 删除用车申请
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:thebusApply:remove')")
    @Log(title = "用车申请", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(bdglThebusApplyService.deleteBdglThebusApplyByIds(ids));
    }
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:thebusApply:examine')")
    @PostMapping("/examine")
    public AjaxResult examine(@RequestBody List<BdglThebusApply> BdglThebusApplys) throws InterruptedException {
        LoginUser loginUser = getLoginUser();
        List<String> roleKeylist = iSysUserService.selectUserRoleKey(loginUser.getUserId());
        boolean iscunzai = false;
        for (String string : roleKeylist) {
            if ("EXAMINER".equals(string)) {
                iscunzai = true;
                break;
            }
        }
        c:
        for (BdglThebusApply bdglThebusApply : BdglThebusApplys) {

            if (bdglThebusApply.getUnitPeopleState().equals("2") &&iscunzai==true) {
                bdglThebusApply.setChushenState("2");
                if (bdglThebusApply.getpeople1Id() != null) {
                    bdglThebusApply.setShenpijiedian("3"); //当前审批角色
                }
                if (bdglThebusApply.getpeople2Id() != null && bdglThebusApply.getpeople1Id() == null) {
                    bdglThebusApply.setShenpijiedian("4"); //当前审批角色

                }
                if (bdglThebusApply.getpeople3Id() != null && bdglThebusApply.getpeople2Id() == null && bdglThebusApply.getpeople1Id() == null) {
                    bdglThebusApply.setShenpijiedian("5"); //当前审批角色

                }

            }
            bdglThebusApplyService.updateBdglThebusApply(bdglThebusApply);
        }
        Thread.sleep(3000);
        return AjaxResult.success();
    }
    @PutMapping("/guiDui")
    public AjaxResult guiDui(@RequestBody BdglThebusApply bdglThebusApply){
        String vehicleType = bdglThebusApply.getVehicleType();
        bdglThebusApply.setPlaceState("1");
        bdglThebusApplyService.updateBdglThebusApply(bdglThebusApply);
        BdglThebus bdglThebus = new BdglThebus();
        String[] split = bdglThebusApply.getThebusId().split(",");
        if(split.length>0){
            for (String s : split) {

                Long id = Long.valueOf(s);
                if ("MILITARY".equalsIgnoreCase(vehicleType)) {

                    // 军车归队
                    BdglThebus bus = new BdglThebus();
                    bus.setId(id);
                    bus.setStatus("1"); // 归队（空闲）
                    bdglThebusService.updateBdglThebus(bus);

                } else if ("PRIVATE".equalsIgnoreCase(vehicleType)) {

                    // 私家车归队
                    BdglPrivateCar privateCar = new BdglPrivateCar();
                    privateCar.setId(id);
                    privateCar.setState("1"); // 归队（空闲）
                    bdglPrivateCarService.updateBdglPrivateCar(privateCar);
                }
            }
        }
        return AjaxResult.success();
    }
}
