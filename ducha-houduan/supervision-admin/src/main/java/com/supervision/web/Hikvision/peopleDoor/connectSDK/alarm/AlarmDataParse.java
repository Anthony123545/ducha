package com.supervision.web.Hikvision.peopleDoor.connectSDK.alarm;


import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.sun.jna.Pointer;
import com.supervision.militaryvehicleManagement.domain.BdglPrivateCar;
import com.supervision.militaryvehicleManagement.domain.BdglThebus;
import com.supervision.militaryvehicleManagement.domain.BdglThebusApply;
import com.supervision.militaryvehicleManagement.service.IBdglPrivateCarService;
import com.supervision.militaryvehicleManagement.service.IBdglThebusApplyService;
import com.supervision.militaryvehicleManagement.service.IBdglThebusService;
import com.supervision.militaryvehicleManagement.service.impl.BdglThebusServiceImpl;
import com.supervision.peopleChuRu.domain.BdglLeave;
import com.supervision.peopleChuRu.service.IBdglLeaveService;
import com.supervision.peopleChuRu.service.impl.BdglLeaveServiceImpl;
import com.supervision.peopleManage.domain.BdglPeople;
import com.supervision.peopleManage.service.IBdglPeopleService;
import com.supervision.peopleManage.service.impl.BdglPeopleServiceImpl;
import com.supervision.web.Hikvision.carCamera.web.util.CarCameraDeviceManager;
import com.supervision.web.Hikvision.carDoor.web.controller.NameListController;
import com.supervision.web.Hikvision.carDoor.web.entity.NameList;
import com.supervision.web.Hikvision.carDoor.web.entity.VehicleAccessRecord;
import com.supervision.web.Hikvision.carDoor.web.service.VehicleAccessRecordService;
import com.supervision.web.Hikvision.carDoor.web.util.CarDoorDeviceManager;
import com.supervision.web.Hikvision.peopleDoor.connectSDK.CommonMethod.CommonUtil;
import com.supervision.web.Hikvision.peopleDoor.connectSDK.NetSDKDemo.HCNetSDK;
import com.supervision.web.Hikvision.peopleDoor.web.entity.Device;
import com.supervision.web.Hikvision.peopleDoor.web.entity.PersonAccessRecord;
import com.supervision.web.Hikvision.peopleDoor.web.service.DeviceService;
import com.supervision.web.Hikvision.peopleDoor.web.service.PersonAccessRecordService;
import com.supervision.web.Hikvision.peopleDoor.web.util.ACSAlarmJsonUtil;
import com.supervision.web.Hikvision.peopleDoor.web.util.PeopleDoorDeviceManager;
import com.supervision.web.Hikvision.util.DeviceSession;
import com.supervision.web.Hikvision.videoManage.domain.Video;
import com.supervision.web.Hikvision.videoManage.service.VideoService;
import com.supervision.web.Hikvision.webSocket.EventWebSocket;
import com.supervision.web.controller.militaryvehicleManagement.BdglThebusApplyController;
import com.supervision.web.controller.peopleChuRu.BdglLeaveController;
import com.supervision.web.controller.peopleChuRu.BdglPeopleAccessController;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class AlarmDataParse {

    private static final Logger logger = LoggerFactory.getLogger(AlarmDataParse.class);

    @Autowired
    public PeopleDoorDeviceManager peopleDoorDeviceManager;

    @Autowired
    public CarDoorDeviceManager carDoorDeviceManager;

    @Autowired
    public CarCameraDeviceManager carCameraDeviceManager;

    @Autowired
    private PersonAccessRecordService personAccessRecordService;

    @Autowired
    private VehicleAccessRecordService vehicleAccessRecordService;

    @Autowired
    BdglPeopleAccessController bdglPeopleAccessController;

    @Autowired
    public VideoService videoService;

    @Autowired
    public DeviceService deviceService;

    @Autowired
    private IBdglThebusApplyService bdglThebusApplyService;

    @Autowired
    private IBdglPrivateCarService bdglPrivateCarService;

    @Autowired
    private IBdglThebusService bdglThebusService;

    @Autowired
    private NameListController nameListController;

    @Autowired
    private IBdglLeaveService bdglLeaveService;

    @Autowired
    private IBdglPeopleService bdglPeopleService;

    public void alarmDataHandle(int lCommand, HCNetSDK.NET_DVR_ALARMER pAlarmer, Pointer pAlarmInfo, int dwBufLen, Pointer pUser) {
        logger.info("报警事件类型： lCommand:" + Integer.toHexString(lCommand));
        String sTime;
        String MonitoringSiteID;
        // lCommand是传的报警类型
        switch (lCommand) {
            case HCNetSDK.COMM_ALARM_ACS: //门禁主机报警信息
                HCNetSDK.NET_DVR_ACS_ALARM_INFO result = new HCNetSDK.NET_DVR_ACS_ALARM_INFO();
                result.write();
                Pointer pACSInfo = result.getPointer();
                pACSInfo.write(0, pAlarmInfo.getByteArray(0, result.size()), 0, result.size());
                result.read();

                // 开始解析所有信息
                try {
                    // 转换为JSON
                    JSONObject alarmJson = ACSAlarmJsonUtil.convertACSAlarmInfoToJson(result);
                    if (alarmJson == null) {
                        logger.warn("门禁报警信息转JSON失败");
                        break;
                    }

                    String deviceIp = new String(pAlarmer.sDeviceIP, StandardCharsets.UTF_8).trim();
                    if (StringUtils.isBlank(deviceIp)) {
                        logger.warn("门禁报警中设备IP为空");
                        break;
                    }

                    if (peopleDoorDeviceManager == null) {
                        break;
                    }

                    DeviceSession session = peopleDoorDeviceManager.getDeviceSession(deviceIp);
                    if (session == null) {
                        logger.warn("未找到设备会话，IP: {}", deviceIp);
                        break;
                    }

                    // 设备信息
                    Device device = session.getDevice();
                    EventWebSocket.broadcast("peopleGate", device.getId(), alarmJson.toString());

                    // 输出信息
                    logger.info("【门禁主机报警信息】报警主类型：" + alarmJson.getOrDefault("majorType", "") + "，报警次类型：" + alarmJson.getOrDefault("minorType", ""));
                    logger.info("【门禁主机报警信息】报警时间：" + alarmJson.getOrDefault("majorType", ""));
                    logger.info("【门禁主机报警信息】操作用户：" + alarmJson.getOrDefault("netUser", ""));
                    logger.info("【门禁主机报警信息】操作IP：" + alarmJson.getOrDefault("remoteIP", ""));
                    JSONObject eventInfoJson = (JSONObject) alarmJson.get("acsEventInfo");
                    logger.info("【门禁主机报警信息】工号：" + eventInfoJson.getOrDefault("employeeNo", ""));
                    logger.info("【门禁主机报警信息】卡号：" + eventInfoJson.getOrDefault("cardNo", ""));
                    // 门编号（或者梯控的楼层编号），为0表示无效（当接的设备为人员通道设备时，门1为进方向，门2为出方向）
                    logger.info("【门禁主机报警信息】门编号：" + eventInfoJson.getOrDefault("doorNo", ""));
                    logger.info("【门禁主机报警信息】人员通道号：" + eventInfoJson.getOrDefault("accessChannel", ""));
                    logger.info("【门禁主机报警信息】设备编号：" + eventInfoJson.getOrDefault("deviceNo", ""));

                    // 检查网络用户和IP（安全修复）
                    String netUser = alarmJson.getString("netUser");
                    String remoteIP = alarmJson.getString("remoteIP");
                    if (StringUtils.isBlank(netUser) && "0.0.0.0".equals(remoteIP)) {
                        logger.info("用户或IP为空: user={}, ip={}", netUser, remoteIP);
//                        break;
                    }

                    // 检查工号
                    String employeeNo = eventInfoJson.get("employeeNo") != null ? alarmJson.get("employeeNo").toString() : null;
                    if (StringUtils.isBlank(employeeNo)) {
                        logger.info("工号为空，跳过处理");
//                        break;
                    }
                //从设备人员表里查找对应姓名
                    String personName = "未知人员";
                    try {
                        // 构造查询参数，利用工号查闸机里的用户信息
                        Map<String, Object> searchParams = new HashMap<>();
                        searchParams.put("employeeNo",  eventInfoJson.getOrDefault("employeeNo", ""));
                        searchParams.put("maxResults", 1); // 只查这一个人
                        searchParams.put("searchResultPosition", 0);

                        // 调用你 Controller 里用到的那个 manager 方法
                        // 注意：这里需要传入之前根据 deviceIp 获取到的 session.getUserID()
                        Map<String, Object> userInfoResult = peopleDoorDeviceManager.getUserInfo(session.getUserID(), searchParams);

                        // 解析返回结果获取人名
                        if (userInfoResult != null && userInfoResult.containsKey("UserInfo")) {
                            List<Map<String, Object>> userList = (List<Map<String, Object>>) userInfoResult.get("UserInfo");
                            if (!userList.isEmpty()) {
                                personName = userList.get(0).get("name").toString();
                                logger.info("实时从设备获取到人名：{}", personName);
                            }
                        }
                    } catch (Exception e) {
                        logger.error("实时查询设备人员信息失败", e);
                    }

                    // 判断是否识别人脸成功，并生成事件消息
                    boolean recognized = false;
                    String eventMessage = "";
                    if (result.dwMajor == 0x5) {
                        switch (result.dwMinor) {
                            case 0x4b: // 人脸认证通过
                                recognized = true;
                                eventMessage = "人脸认证通过";
                                break;
                            case 0x4c: // 人脸认证失败
                                eventMessage = "人脸认证失败";
                                break;
                            case 0x4d: // 工号+人脸认证通过
                                recognized = true;
                                eventMessage = "工号+人脸认证通过";
                                break;
                            case 0x4e: // 工号+人脸认证失败
                                eventMessage = "工号+人脸认证失败";
                                break;
                            case 0x4f: // 工号+人脸认证超时
                                eventMessage = "工号+人脸认证超时";
                                break;
                            default:
                                eventMessage = "刷卡通行";
                        }
                    }

                    // 更新人员状态（如果设备启用并且识别成功）
                    Boolean isUpdateStatus = device.getIsUpdateStatus();
                    // 只有当设备启用了状态更新功能并且人脸识别成功时，才更新人员状态
                    if (isUpdateStatus != null && isUpdateStatus && recognized) {
                        // 解析设备上摄像头 JSON 配置
                        JSONArray jsonArray = com.alibaba.fastjson2.JSON.parseArray(device.getVideosJson());
                        // 替换原报错行：Integer accessChannel = (Integer) eventInfoJson.get("accessChannel");
                        Object accessChannelObj = eventInfoJson.get("accessChannel");
                        Integer accessChannel = (accessChannelObj instanceof Number) ? ((Number) accessChannelObj).intValue() : null;
                        // 遍历每个摄像头对象
                        for (int i = 0; i < jsonArray.size(); i++) {
                            JSONObject cameraObject = jsonArray.getJSONObject(i);
                            // 替换原报错行：Integer videoIndex = cameraObject.getInteger("videoIndex");
                            Object videoIndexObj = cameraObject.get("videoIndex");
                            Integer videoIndex = (videoIndexObj instanceof Number) ? ((Number) videoIndexObj).intValue() : null;
                            String direction = cameraObject.getString("direction");     // in/out
                            // 匹配当前事件对应的摄像头
                            if (Objects.equals(videoIndex, accessChannel)) {
                                if ("in".equals(direction)) {
                                    // 进入：设置状态为在岗（1）
                                    bdglPeopleAccessController.setStatusByEmployeeNumber(device.getId(), employeeNo, 1);
                                } else if ("out".equals(direction)) {
                                    // 外出：设置状态为外出（0）
                                    bdglPeopleAccessController.setStatusByEmployeeNumber(device.getId(), employeeNo, 0);
                                }
                                break; // 找到匹配摄像头后退出循环
                            }
                        }
                    } else {
                        // 状态更新未启用或人脸未识别成功，不做状态更新
                        logger.debug("未启用状态更新或人脸未识别成功，不更新人员状态");
                    }

                    // 插入用户进出记录
                    PersonAccessRecord record = new PersonAccessRecord();
                    record.setDeviceId(device.getId());
                    record.setMajorType((Integer) alarmJson.get("majorType"));
                    record.setMinorType((Integer) alarmJson.get("minorType"));
                    record.setEmployeeNo((Integer) eventInfoJson.get("employeeNo"));
                    record.setName("personName");
//                    record.setCardNo((Integer) eventInfoJson.get("cardNo"));
//                    record.setCaptureTime((Date) alarmJson.get("alarmTime"));
                    String dateStr = (String) alarmJson.get("alarmTime");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                        Date captureTime = sdf.parse(dateStr);
                        record.setCaptureTime(captureTime);
                    } catch (ParseException e) {
                        logger.error("解析时间失败: " + dateStr, e);
                        record.setCaptureTime(new Date()); // 使用当前时间，避免空指针
                    }
                    record.setPhotoUrl((String) alarmJson.getOrDefault("photoUrl", ""));
                    record.setEventType(device.getAccessPermission());
                    record.setEventMessage(eventMessage);
                    record.setRemark(eventMessage);
                    // 保存记录到数据库
                    boolean success = personAccessRecordService.add(record);
                    if (success) {
                        logger.info("【通行记录保存成功】");
                    } else {
                        logger.error("【通行记录保存失败】");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("解析门禁报警信息时出错: " + e.getMessage());
                }
                break;
            // COMM_UPLOAD_PLATE_RESULT（老报警信息）
            case HCNetSDK.COMM_UPLOAD_PLATE_RESULT:
                logger.info("COMM_UPLOAD_PLATE_RESULT（老报警信息）");
                break;
            // COMM_ITS_PLATE_RESULT（新报警信息）
            case HCNetSDK.COMM_ITS_PLATE_RESULT: // 交通抓拍结果(新报警信息)
                logger.info("COMM_ITS_PLATE_RESULT（新报警信息）");
                logger.info("【车牌摄像头回调信息】交通抓拍的终端图片上传");
                com.supervision.web.Hikvision.carCamera.connectSDK.NetSDKDemo.HCNetSDK.NET_ITS_PLATE_RESULT plateResult = new com.supervision.web.Hikvision.carCamera.connectSDK.NetSDKDemo.HCNetSDK.NET_ITS_PLATE_RESULT();
                plateResult.write();
                Pointer pItsPlateInfo = plateResult.getPointer();
                pItsPlateInfo.write(0, pAlarmInfo.getByteArray(0, plateResult.size()), 0, plateResult.size());
                plateResult.read();

                // 开始解析所有信息
                try {
                    // 转换为JSON
                    JSONObject alarmJson = ACSAlarmJsonUtil.convertPlateAlarmInfoToJson(plateResult);
                    if (alarmJson == null) {
                        logger.warn("车闸报警信息转JSON失败");
                        break;
                    }

                    String cameraDeviceIp = new String(pAlarmer.sDeviceIP, StandardCharsets.UTF_8).trim();
                    if (StringUtils.isBlank(cameraDeviceIp)) {
                        logger.warn("车闸报警中设备IP为空");
                        break;
                    }

                    if (carCameraDeviceManager == null) {
                        break;
                    }

                    DeviceSession session = carCameraDeviceManager.getDeviceSession(cameraDeviceIp);
                    if (session == null) {
                        logger.warn("未找到设备会话，IP: {}", cameraDeviceIp);
                        break;
                    }

                    // 设备信息
                    Video video = videoService.getByIp(cameraDeviceIp);
                    if (video == null) {
                        logger.warn("未找到视频信息，IP: {}", cameraDeviceIp);
                        break;
                    }
                    Long parentDeviceId = video.getParentDeviceId();

                    HashMap<?,?> plateMap2 = (HashMap<?,?>) alarmJson.get("plateInfo");
                    JSONObject plateInfo2 = new JSONObject(plateMap2);
                    String license2 = plateInfo2.getString("license");
                    alarmJson.put("plateNo", license2);
                    EventWebSocket.broadcast("carGate", parentDeviceId, alarmJson.toString());

                    // 车牌信息
//                    JSONObject plateInfo = (JSONObject) alarmJson.get("plateInfo");
                    // 修复后代码（把HashMap转成JSONObject）
                    HashMap<?,?> plateMap = (HashMap<?,?>) alarmJson.get("plateInfo");
                    JSONObject plateInfo = new JSONObject(plateMap);
                    logger.info("【车牌摄像头报警信息】车牌号：" + plateInfo.getOrDefault("license", ""));
                    logger.info("【车牌摄像头报警信息】车牌类型：" + plateInfo.getOrDefault("plateType", ""));
                    // 车辆信息
//                    JSONObject vehicleInfo = (JSONObject) alarmJson.get("plateInfo");
                    JSONObject vehicleInfo = plateInfo;
                    logger.info("【车牌摄像头报警信息】车辆序号：" + vehicleInfo.getOrDefault("dwIndex", ""));
                    logger.info("【车牌摄像头报警信息】车辆类型：" + vehicleInfo.getOrDefault("vehicleType", ""));
                    logger.info("【车牌摄像头报警信息】车辆具体行驶的方向：0- 从上往下，1- 从下往上：" + plateResult.byCarDirectionType);
                    logger.info("【车牌摄像头报警信息】道闸控制类型：0- 开闸，1- 未开闸：" + plateResult.byBarrierGateCtrlType);

                    // 检查网络用户和IP（安全修复）
                    String netUser = alarmJson.getString("netUser");
                    String remoteIP = alarmJson.getString("remoteIP");
                    if (StringUtils.isBlank(netUser) && "0.0.0.0".equals(remoteIP)) {
                        logger.info("用户或IP为空: user={}, ip={}", netUser, remoteIP);
                        break;
                    }

                    // 检查车牌号
                    String license = plateInfo.getString("license");
                    if (StringUtils.isBlank(license)) {
                        logger.info("车牌号为空");
                        break;
                    }
                    // 判断车牌是否识别成功
                    // 条件1: license 非空，即设备成功检测到车牌号码
                    // 条件2: byEntireBelieve >= 80，即整块车牌的识别置信度达到阈值（0~100），避免低置信度误判
                    boolean plateRecognized = StringUtils.isNotBlank(license) && plateInfo.getByte("byEntireBelieve")!=null && plateInfo.getByte("byEntireBelieve") >= 80;
                    // 更新车辆状态（如果设备启用并且识别成功）
                    Device device = deviceService.getById(parentDeviceId);
                    Boolean isUpdateStatus = device.getIsUpdateStatus();
                    // 只有当设备启用了状态更新功能并且车辆识别成功时，才更新人员状态
                    if (isUpdateStatus != null && isUpdateStatus && plateRecognized) {
                        // 解析设备上摄像头 JSON 配置
                        JSONArray jsonArray = com.alibaba.fastjson2.JSON.parseArray(device.getVideosJson());
                        // 遍历每个摄像头对象
                        for (int i = 0; i < jsonArray.size(); i++) {
                            JSONObject cameraObject = jsonArray.getJSONObject(i);
                            // 提取 videoInfo 对象
                            JSONObject videoInfoObject = cameraObject.getJSONObject("videoInfo");
                            if (videoInfoObject != null) {
                                Boolean enable = videoInfoObject.getBoolean("enable");
                                if (enable == null || !enable) {
                                    logger.debug("摄像头 {} 未启用，跳过", cameraDeviceIp);
                                    continue;
                                }
                                String ip = videoInfoObject.getString("ip");
                                if (ip != null && ip.equals(cameraDeviceIp)) {
                                    String direction = videoInfoObject.getString("direction");
                                    if ("in".equals(direction)) {
                                        // 进入：设置状态为在岗（1）
                                        // 进入时调用
                                        updateVehicleStatus(license, "1",device.getId());
                                    } else if ("out".equals(direction)) {
                                        // 外出：设置状态为外出（3）
                                        // 外出时调用
                                        updateVehicleStatus(license, "3",device.getId());
                                    }
                                    logger.info("已更新车辆状态: {} -> {}", license, direction);
                                    break;
                                }
                            }
                        }
                    } else {
                        logger.debug("未启用状态更新功能，或 isUpdateStatus=false");
                    }

                    // 插入车辆进出记录
                    VehicleAccessRecord record = new VehicleAccessRecord();
                    record.setDeviceId(device.getId());
                    record.setPlateNumber((String) plateInfo.get("license"));
                    record.setEventMessage("车辆通行记录");
                    String dateStr = (String) alarmJson.get("alarmTime");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                        Date captureTime = sdf.parse(dateStr);
                        record.setCaptureTime(captureTime);
                    } catch (ParseException e) {
                        logger.error("解析抓拍时间失败: " + dateStr, e);
                        record.setCaptureTime(new Date());
                    }
                    record.setPhotoUrl((String) alarmJson.getOrDefault("photoUrl", ""));
//                    record.setEventType(device.getAccessPermission());
//                     根据ip判断进入或离开
                    String eventType;
                    if ("34.131.62.51".equals(cameraDeviceIp)) {
                        eventType = "in"; // 进入
                    } else if ("34.131.62.52".equals(cameraDeviceIp)) {
                        eventType = "out"; // 离开
                    } else {
                        // 兼容其他IP的情况（可选：可设为默认值或日志告警）
                        eventType = "unknown";
                        logger.warn("未匹配的摄像头IP: {}，eventType设为unknown", cameraDeviceIp);
                    }
                    record.setEventType(eventType);
                    // 保存到数据库
                    boolean success = vehicleAccessRecordService.add(record);
                    if (success) {
                        logger.info("【通行记录保存成功】");
                    } else {
                        logger.error("【通行记录保存失败】");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("解析车牌摄像头报警信息时出错: " + e.getMessage());
                }
                break;
            // 异常行为检测信息
            case HCNetSDK.COMM_ALARM_RULE:
                HCNetSDK.NET_VCA_RULE_ALARM strVcaAlarm = new HCNetSDK.NET_VCA_RULE_ALARM();
                strVcaAlarm.write();
                Pointer pVCAInfo = strVcaAlarm.getPointer();
                pVCAInfo.write(0, pAlarmInfo.getByteArray(0, strVcaAlarm.size()), 0, strVcaAlarm.size());
                strVcaAlarm.read();

                switch (strVcaAlarm.struRuleInfo.wEventTypeEx) {
                    case 1: //穿越警戒面 (越界侦测)
                        logger.info("越界侦测报警发生");
                        strVcaAlarm.struRuleInfo.uEventParam.setType(HCNetSDK.NET_VCA_TRAVERSE_PLANE.class);
                        logger.info("检测目标："+strVcaAlarm.struRuleInfo.uEventParam.struTraversePlane.byDetectionTarget); //检测目标，0表示所有目标（表示不锁定检测目标，所有目标都将进行检测），其他取值按位表示不同的检测目标：0x01-人，0x02-车
                        //图片保存
                        if ((strVcaAlarm.dwPicDataLen > 0) && (strVcaAlarm.byPicTransType == 0)) {
                            SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                            String newName = sf.format(new Date());
                            FileOutputStream fout;
                            try {
                                String filename = "./pic/" + newName + "VCA_TRAVERSE_PLANE" + ".jpg";
                                fout = new FileOutputStream(filename);
                                //将字节写入文件
                                long offset = 0;
                                ByteBuffer buffers = strVcaAlarm.pImage.getByteBuffer(offset, strVcaAlarm.dwPicDataLen);
                                byte[] bytes = new byte[strVcaAlarm.dwPicDataLen];
                                buffers.rewind();
                                buffers.get(bytes);
                                fout.write(bytes);
                                fout.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case 2: //目标进入区域
                        logger.info("目标进入区域报警发生");
                        strVcaAlarm.struRuleInfo.uEventParam.setType(HCNetSDK.NET_VCA_AREA.class);
                        logger.info("检测目标："+strVcaAlarm.struRuleInfo.uEventParam.struArea.byDetectionTarget);
                        //图片保存
                        if ((strVcaAlarm.dwPicDataLen > 0) && (strVcaAlarm.byPicTransType == 0)) {
                            SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                            String newName = sf.format(new Date());
                            FileOutputStream fout;
                            try {
                                String filename = "./pic/" + newName + "_TargetEnter" + ".jpg";
                                fout = new FileOutputStream(filename);
                                //将字节写入文件
                                long offset = 0;
                                ByteBuffer buffers = strVcaAlarm.pImage.getByteBuffer(offset, strVcaAlarm.dwPicDataLen);
                                byte[] bytes = new byte[strVcaAlarm.dwPicDataLen];
                                buffers.rewind();
                                buffers.get(bytes);
                                fout.write(bytes);
                                fout.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case 3: //目标离开区域
                        logger.info("目标离开区域报警触发");
                        strVcaAlarm.struRuleInfo.uEventParam.setType(HCNetSDK.NET_VCA_AREA.class);
                        logger.info("检测目标："+strVcaAlarm.struRuleInfo.uEventParam.struArea.byDetectionTarget);
                        //图片保存
                        if ((strVcaAlarm.dwPicDataLen > 0) && (strVcaAlarm.byPicTransType == 0)) {
                            SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                            String newName = sf.format(new Date());
                            FileOutputStream fout;
                            try {
                                String filename = "./pic/" + newName + "_TargetLeave" + ".jpg";
                                fout = new FileOutputStream(filename);
                                //将字节写入文件
                                long offset = 0;
                                ByteBuffer buffers = strVcaAlarm.pImage.getByteBuffer(offset, strVcaAlarm.dwPicDataLen);
                                byte[] bytes = new byte[strVcaAlarm.dwPicDataLen];
                                buffers.rewind();
                                buffers.get(bytes);
                                fout.write(bytes);
                                fout.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case 4: //周界入侵
                        logger.info("周界入侵报警发生");
                        strVcaAlarm.struRuleInfo.uEventParam.setType(HCNetSDK.NET_VCA_INTRUSION.class);
                        logger.info("检测目标："+strVcaAlarm.struRuleInfo.uEventParam.struIntrusion.byDetectionTarget);
                        //图片保存
                        if ((strVcaAlarm.dwPicDataLen > 0) && (strVcaAlarm.byPicTransType == 0)) {
                            SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                            String newName = sf.format(new Date());
                            FileOutputStream fout;
                            try {
                                String filename = "./pic/" + newName + "VCA_INTRUSION" + ".jpg";
                                fout = new FileOutputStream(filename);
                                //将字节写入文件
                                long offset = 0;
                                ByteBuffer buffers = strVcaAlarm.pImage.getByteBuffer(offset, strVcaAlarm.dwPicDataLen);
                                byte[] bytes = new byte[strVcaAlarm.dwPicDataLen];
                                buffers.rewind();
                                buffers.get(bytes);
                                fout.write(bytes);
                                fout.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case 5: //徘徊
                        logger.info("徘徊事件触发");
                        break;
                    case 8: //快速移动(奔跑)，
                        logger.info("快速移动(奔跑)事件触发");
                        break;
                    case 15:
                        logger.info("事件触发");
                        strVcaAlarm.struRuleInfo.uEventParam.setType(HCNetSDK.NET_VCA_LEAVE_POSITION.class);
                        logger.info(String.valueOf(strVcaAlarm.struRuleInfo.uEventParam.struLeavePos.byOnPosition));
                        //图片保存
                        if ((strVcaAlarm.dwPicDataLen > 0) && (strVcaAlarm.byPicTransType == 0)) {
                            SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                            String newName = sf.format(new Date());
                            FileOutputStream fout;
                            try {
                                String filename = "./pic/" + newName + "VCA_LEAVE_POSITION_" + ".jpg";
                                fout = new FileOutputStream(filename);
                                //将字节写入文件
                                long offset = 0;
                                ByteBuffer buffers = strVcaAlarm.pImage.getByteBuffer(offset, strVcaAlarm.dwPicDataLen);
                                byte[] bytes = new byte[strVcaAlarm.dwPicDataLen];
                                buffers.rewind();
                                buffers.get(bytes);
                                fout.write(bytes);
                                fout.close();
                            } catch (FileNotFoundException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    case 20: //倒地检测
                        logger.info("倒地事件触发");
                        break;
                    case 44: //玩手机
                        logger.info("玩手机报警发生");
                        //图片保存
                        if ((strVcaAlarm.dwPicDataLen > 0) && (strVcaAlarm.byPicTransType == 0)) {
                            SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                            String newName = sf.format(new Date());
                            FileOutputStream fout;
                            try {
                                String filename = "./pic/" + newName + "PLAY_CELLPHONE_" + ".jpg";
                                fout = new FileOutputStream(filename);
                                //将字节写入文件
                                long offset = 0;
                                ByteBuffer buffers = strVcaAlarm.pImage.getByteBuffer(offset, strVcaAlarm.dwPicDataLen);
                                byte[] bytes = new byte[strVcaAlarm.dwPicDataLen];
                                buffers.rewind();
                                buffers.get(bytes);
                                fout.write(bytes);
                                fout.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case 45: //持续检测
                        logger.info("持续检测事件触发");
                    default:
                        logger.info("行为事件类型:" + strVcaAlarm.struRuleInfo.wEventTypeEx);
                        break;
                }
                break;
            case HCNetSDK.COMM_ID_INFO_ALARM: //身份证信息
                HCNetSDK.NET_DVR_ID_CARD_INFO_ALARM strIDCardInfo = new HCNetSDK.NET_DVR_ID_CARD_INFO_ALARM();
                strIDCardInfo.write();
                Pointer pIDCardInfo = strIDCardInfo.getPointer();
                pIDCardInfo.write(0, pAlarmInfo.getByteArray(0, strIDCardInfo.size()), 0, strIDCardInfo.size());
                strIDCardInfo.read();
                logger.info("报警主类型：" + Integer.toHexString(strIDCardInfo.dwMajor) + "，报警次类型：" + Integer.toHexString(strIDCardInfo.dwMinor));
                /**
                 * 身份证信息
                 */
                String IDnum = new String(strIDCardInfo.struIDCardCfg.byIDNum).trim();
                logger.info("【身份证信息】：身份证号码：" + IDnum + "，姓名：" +
                        new String(strIDCardInfo.struIDCardCfg.byName).trim() + "，住址：" + new String(strIDCardInfo.struIDCardCfg.byAddr));

                /**
                 * 报警时间
                 */
                String year1 = Integer.toString(strIDCardInfo.struSwipeTime.wYear);
                String SwipeTime1 = year1 + strIDCardInfo.struSwipeTime.byMonth + strIDCardInfo.struSwipeTime.byDay + strIDCardInfo.struSwipeTime.byHour + strIDCardInfo.struSwipeTime.byMinute +
                        strIDCardInfo.struSwipeTime.bySecond;
                /**
                 * 保存图片
                 */
                //身份证图片
                if (strIDCardInfo.dwPicDataLen > 0 || strIDCardInfo.pPicData != null) {
//                    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
//                    String nowtime = sf.format(new Date());
                    FileOutputStream fout;
                    try {
                        String filename = "./pic/" + SwipeTime1 + "_" + IDnum + ".jpg";
                        fout = new FileOutputStream(filename);
                        //将字节写入文件
                        long offset = 0;
                        ByteBuffer buffers = strIDCardInfo.pPicData.getByteBuffer(offset, strIDCardInfo.dwPicDataLen);
                        byte[] bytes = new byte[strIDCardInfo.dwPicDataLen];
                        buffers.rewind();
                        buffers.get(bytes);
                        fout.write(bytes);
                        fout.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (strIDCardInfo.dwCapturePicDataLen > 0 || strIDCardInfo.pCapturePicData != null) {
//                    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
//                    String nowtime = sf.format(new Date());
                    FileOutputStream fout;
                    try {
                        /**
                         * 人脸图片保存路径
                         */
                        String filename = "./pic/" + SwipeTime1 + "_CapturePic_" + ".jpg";
                        fout = new FileOutputStream(filename);
                        //将字节写入文件
                        long offset = 0;
                        ByteBuffer buffers = strIDCardInfo.pCapturePicData.getByteBuffer(offset, strIDCardInfo.dwCapturePicDataLen);
                        byte[] bytes = new byte[strIDCardInfo.dwCapturePicDataLen];
                        buffers.rewind();
                        buffers.get(bytes);
                        fout.write(bytes);
                        fout.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case HCNetSDK.COMM_ALARM_VIDEO_INTERCOM: //可视对讲报警信息
                logger.info("可视对讲报警触发");
                HCNetSDK.NET_DVR_VIDEO_INTERCOM_ALARM strVideoIntercomAlarm = new HCNetSDK.NET_DVR_VIDEO_INTERCOM_ALARM();
                strVideoIntercomAlarm.write();
                Pointer pVideoIntercomAlarm = strVideoIntercomAlarm.getPointer();
                pVideoIntercomAlarm.write(0, pAlarmInfo.getByteArray(0, strVideoIntercomAlarm.size()), 0, strVideoIntercomAlarm.size());
                strVideoIntercomAlarm.read();
                logger.info("byAlarmType:" + strVideoIntercomAlarm.byAlarmType);

                if (strVideoIntercomAlarm.byAlarmType == 1) {
                    strVideoIntercomAlarm.uAlarmInfo.setType(HCNetSDK.NET_DVR_VIDEO_INTERCOM_ALARM_INFO_UNION.class);
                    strVideoIntercomAlarm.uAlarmInfo.read();
                    logger.info("byZoneType :" + strVideoIntercomAlarm.uAlarmInfo.struZoneAlarm.byZoneType);
                }

                break;
            case HCNetSDK.COMM_UPLOAD_VIDEO_INTERCOM_EVENT: //可视对讲事件记录信息
                logger.info("可视对讲事件记录报警触发");
                HCNetSDK.NET_DVR_VIDEO_INTERCOM_EVENT strVideoIntercomEvent = new HCNetSDK.NET_DVR_VIDEO_INTERCOM_EVENT();
                strVideoIntercomEvent.write();
                Pointer pVideoIntercomEvent = strVideoIntercomEvent.getPointer();
                pVideoIntercomEvent.write(0, pAlarmInfo.getByteArray(0, strVideoIntercomEvent.size()), 0, strVideoIntercomEvent.size());
                strVideoIntercomEvent.read();
                logger.info("byEventType:" + strVideoIntercomEvent.byEventType);

                if (strVideoIntercomEvent.byEventType == 1) {
                    strVideoIntercomEvent.uEventInfo.setType(HCNetSDK.NET_DVR_UNLOCK_RECORD_INFO.class);
                    strVideoIntercomEvent.uEventInfo.read();
                    logger.info("byUnlockType:" + strVideoIntercomEvent.uEventInfo.struUnlockRecord.byUnlockType
                            + ",byControlSrc:" + new String(strVideoIntercomEvent.uEventInfo.struUnlockRecord.byControlSrc).trim());
                }
                break;
            case HCNetSDK.COMM_UPLOAD_FACESNAP_RESULT: //实时特征抓拍上传
                logger.info("UPLOAD_FACESNAP_Alarm");
                HCNetSDK.NET_VCA_FACESNAP_RESULT strFaceSnapInfo = new HCNetSDK.NET_VCA_FACESNAP_RESULT();
                strFaceSnapInfo.write();
                Pointer pFaceSnapInfo = strFaceSnapInfo.getPointer();
                pFaceSnapInfo.write(0, pAlarmInfo.getByteArray(0, strFaceSnapInfo.size()), 0, strFaceSnapInfo.size());
                strFaceSnapInfo.read();

                //事件时间
                int dwYear = (strFaceSnapInfo.dwAbsTime >> 26) + 2000;
                int dwMonth = (strFaceSnapInfo.dwAbsTime >> 22) & 15;
                int dwDay = (strFaceSnapInfo.dwAbsTime >> 17) & 31;
                int dwHour = (strFaceSnapInfo.dwAbsTime >> 12) & 31;
                int dwMinute = (strFaceSnapInfo.dwAbsTime >> 6) & 63;
                int dwSecond = (strFaceSnapInfo.dwAbsTime >> 0) & 63;

                String strAbsTime = "" + String.format("%04d", dwYear) +
                        String.format("%02d", dwMonth) +
                        String.format("%02d", dwDay) +
                        String.format("%02d", dwHour) +
                        String.format("%02d", dwMinute) +
                        String.format("%02d", dwSecond);

                //人脸属性信息
                String sFaceAlarmInfo = "Abs时间:" + strAbsTime + ",是否戴口罩：" +
                        strFaceSnapInfo.struFeature.byMask + ",是否微笑：" + strFaceSnapInfo.struFeature.bySmile;
                logger.info("人脸信息：" + sFaceAlarmInfo);

                //人脸测温信息
                if (strFaceSnapInfo.byAddInfo == 1) {
                    HCNetSDK.NET_VCA_FACESNAP_ADDINFO strAddInfo = new HCNetSDK.NET_VCA_FACESNAP_ADDINFO();
                    strAddInfo.write();
                    Pointer pAddInfo = strAddInfo.getPointer();
                    pAddInfo.write(0, strFaceSnapInfo.pAddInfoBuffer.getByteArray(0, strAddInfo.size()), 0, strAddInfo.size());
                    strAddInfo.read();

                    String sTemperatureInfo = "测温是否开启：" + strAddInfo.byFaceSnapThermometryEnabled + "人脸温度：" + strAddInfo.fFaceTemperature + "温度是否异常"
                            + strAddInfo.byIsAbnomalTemperature + "报警温度阈值：" + strAddInfo.fAlarmTemperature;
                    logger.info("人脸温度信息:" + sTemperatureInfo);

                }

                try {
                    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
                    String time = df.format(new Date());// new Date()为获取当前系统时间

                    //人脸图片写文件
                    FileOutputStream small = new FileOutputStream("./pic/" + time + "small.jpg");
                    FileOutputStream big = new FileOutputStream("./pic/" + time + "big.jpg");
                    try {
                        small.write(strFaceSnapInfo.pBuffer1.getByteArray(0, strFaceSnapInfo.dwFacePicLen), 0, strFaceSnapInfo.dwFacePicLen);
                        small.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        big.write(strFaceSnapInfo.pBuffer2.getByteArray(0, strFaceSnapInfo.dwBackgroundPicLen), 0, strFaceSnapInfo.dwBackgroundPicLen);
                        big.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                break;

            //  客流量报警信息
            case HCNetSDK.COMM_ALARM_PDC:
                HCNetSDK.NET_DVR_PDC_ALRAM_INFO strPDCResult = new HCNetSDK.NET_DVR_PDC_ALRAM_INFO();
                strPDCResult.write();
                Pointer pPDCInfo = strPDCResult.getPointer();
                pPDCInfo.write(0, pAlarmInfo.getByteArray(0, strPDCResult.size()), 0, strPDCResult.size());
                strPDCResult.read();
                // byMode=0-实时统计结果(联合体中struStatFrame有效)，
                if (strPDCResult.byMode == 0) {
                    strPDCResult.uStatModeParam.setType(HCNetSDK.NET_DVR_STATFRAME.class);
                    String sAlarmPDC0Info = "实时客流量统计，进入人数：" + strPDCResult.dwEnterNum + "，离开人数：" + strPDCResult.dwLeaveNum +
                            ", byMode:" + strPDCResult.byMode + ", dwRelativeTime:" + strPDCResult.uStatModeParam.struStatFrame.dwRelativeTime +
                            ", dwAbsTime:" + strPDCResult.uStatModeParam.struStatFrame.dwAbsTime;
                }
                // byMode=1-周期统计结果(联合体中struStatTime有效)，
                if (strPDCResult.byMode == 1) {
                    strPDCResult.uStatModeParam.setType(HCNetSDK.NET_DVR_STATTIME.class);
                    String strtmStart = "" + String.format("%04d", strPDCResult.uStatModeParam.struStatTime.tmStart.dwYear) +
                            String.format("%02d", strPDCResult.uStatModeParam.struStatTime.tmStart.dwMonth) +
                            String.format("%02d", strPDCResult.uStatModeParam.struStatTime.tmStart.dwDay) +
                            String.format("%02d", strPDCResult.uStatModeParam.struStatTime.tmStart.dwHour) +
                            String.format("%02d", strPDCResult.uStatModeParam.struStatTime.tmStart.dwMinute) +
                            String.format("%02d", strPDCResult.uStatModeParam.struStatTime.tmStart.dwSecond);
                    String strtmEnd = "" + String.format("%04d", strPDCResult.uStatModeParam.struStatTime.tmEnd.dwYear) +
                            String.format("%02d", strPDCResult.uStatModeParam.struStatTime.tmEnd.dwMonth) +
                            String.format("%02d", strPDCResult.uStatModeParam.struStatTime.tmEnd.dwDay) +
                            String.format("%02d", strPDCResult.uStatModeParam.struStatTime.tmEnd.dwHour) +
                            String.format("%02d", strPDCResult.uStatModeParam.struStatTime.tmEnd.dwMinute) +
                            String.format("%02d", strPDCResult.uStatModeParam.struStatTime.tmEnd.dwSecond);
                    String sAlarmPDC1Info = "周期性客流量统计，进入人数：" + strPDCResult.dwEnterNum + "，离开人数：" + strPDCResult.dwLeaveNum +
                            ", byMode:" + strPDCResult.byMode + ", tmStart:" + strtmStart + ",tmEnd :" + strtmEnd;
                }
                break;
            case HCNetSDK.COMM_ALARM_V30:  //移动侦测、视频丢失、遮挡、IO信号量等报警信息(V3.0以上版本支持的设备)
                HCNetSDK.NET_DVR_ALARMINFO_V30 struAlarmInfo = new HCNetSDK.NET_DVR_ALARMINFO_V30();
                struAlarmInfo.write();
                Pointer pAlarmInfo_V30 = struAlarmInfo.getPointer();
                pAlarmInfo_V30.write(0, pAlarmInfo.getByteArray(0, struAlarmInfo.size()), 0, struAlarmInfo.size());
                struAlarmInfo.read();
                logger.info("报警类型：" + struAlarmInfo.dwAlarmType);  // 3-移动侦测
                break;
            case HCNetSDK.COMM_ALARM_V40: //移动侦测、视频丢失、遮挡、IO信号量等报警信息，报警数据为可变长

                HCNetSDK.NET_DVR_ALARMINFO_V40 struAlarmInfoV40 = new HCNetSDK.NET_DVR_ALARMINFO_V40();
                struAlarmInfoV40.write();
                Pointer pAlarmInfoV40 = struAlarmInfoV40.getPointer();
                pAlarmInfoV40.write(0, pAlarmInfo.getByteArray(0, struAlarmInfoV40.size()), 0, struAlarmInfoV40.size());
                struAlarmInfoV40.read();
                switch (struAlarmInfoV40.struAlarmFixedHeader.dwAlarmType) {
                    case 0:
                        struAlarmInfoV40.struAlarmFixedHeader.ustruAlarm.setType(HCNetSDK.struIOAlarm.class);
                        struAlarmInfoV40.read();
                        logger.info("信号量报警，报警输入口："+struAlarmInfoV40.struAlarmFixedHeader.ustruAlarm.struioAlarm.dwAlarmInputNo);
                        break;
                    case 1:
                        logger.info("硬盘满");
                        break;
                    case 2:
                        logger.info("信号丢失");
                        break;
                    case 3:
                        String sAlarmType="";
                        struAlarmInfoV40.struAlarmFixedHeader.ustruAlarm.setType(HCNetSDK.struAlarmChannel.class);
                        struAlarmInfoV40.read();
                        int iChanNum = struAlarmInfoV40.struAlarmFixedHeader.ustruAlarm.strualarmChannel.dwAlarmChanNum;
                        sAlarmType = sAlarmType + new String("：移动侦测") + "，" + "报警通道个数：" + iChanNum + "，" + "报警通道号：";

                        for (int i = 0; i < iChanNum; i++) {
                            byte[] byChannel = struAlarmInfoV40.pAlarmData.getByteArray(i * 4, 4);

                            int iChanneNo = 0;
                            for (int j = 0; j < 4; j++) {
                                int ioffset = j * 8;
                                int iByte = byChannel[j] & 0xff;
                                iChanneNo = iChanneNo + (iByte << ioffset);
                            }

                            sAlarmType = sAlarmType + "+ch[" + iChanneNo + "]";
                        }
                        logger.info(sAlarmType);
                        break;
                    case 4:
                        logger.info("硬盘未格式化");
                        break;
                    case 5:
                        logger.info("读写硬盘出错");
                        break;
                    case 6:
                        logger.info("遮挡报警");
                        break;
                    case 7:
                        logger.info("制式不匹配");
                        break;
                    case 8:
                        logger.info("非法访问");
                        break;
                }
                break;
            case HCNetSDK.COMM_ALARM_TFS: //道路违章取证报警
                HCNetSDK.NET_DVR_TFS_ALARM strTfsAlarm = new HCNetSDK.NET_DVR_TFS_ALARM();
                strTfsAlarm.write();
                Pointer pTfsAlarm = strTfsAlarm.getPointer();
                pTfsAlarm.write(0, pAlarmInfo.getByteArray(0, strTfsAlarm.size()), 0, strTfsAlarm.size());
                strTfsAlarm.read();
                sTime = CommonUtil.parseTime(strTfsAlarm.dwAbsTime); //报警绝对时间
                int IllegalType = strTfsAlarm.dwIllegalType; // 违章类型
                MonitoringSiteID = strTfsAlarm.byMonitoringSiteID.toString(); //布防点编号
                // 车牌信息
                try {
                    String PlateInfo = "车牌号：" + new String(strTfsAlarm.struPlateInfo.sLicense, "GBK");
                    logger.info("【道路违章取证报警】时间：" + sTime + "违章类型：" + IllegalType + "车牌信息：" + PlateInfo);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                //报警图片信息
                for (int i = 0; i < strTfsAlarm.dwPicNum; i++) {
                    if (strTfsAlarm.struPicInfo[i].dwDataLen > 0) {
                        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                        String newName = sf.format(new Date());
                        FileOutputStream fout;
                        try {
                            String filename = "./pic/" + newName + "_type[" + strTfsAlarm.struPicInfo[i].byType + "]_TfsPlate.jpg";
                            fout = new FileOutputStream(filename);
                            //将字节写入文件
                            long offset = 0;
                            ByteBuffer buffers = strTfsAlarm.struPicInfo[i].pBuffer.getByteBuffer(offset, strTfsAlarm.struPicInfo[i].dwDataLen);
                            byte[] bytes = new byte[strTfsAlarm.struPicInfo[i].dwDataLen];
                            buffers.rewind();
                            buffers.get(bytes);
                            fout.write(bytes);
                            fout.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case HCNetSDK.COMM_ALARM_AID_V41: //道路事件检测
                HCNetSDK.NET_DVR_AID_ALARM_V41 strAIDAlarmV41 = new HCNetSDK.NET_DVR_AID_ALARM_V41();
                strAIDAlarmV41.write();
                Pointer pstrAIDAlarmV41 = strAIDAlarmV41.getPointer();
                pstrAIDAlarmV41.write(0, pAlarmInfo.getByteArray(0, strAIDAlarmV41.size()), 0, strAIDAlarmV41.size());
                strAIDAlarmV41.read();
                sTime = CommonUtil.parseTime(strAIDAlarmV41.dwAbsTime); //报警触发绝对时间
                MonitoringSiteID = strAIDAlarmV41.byMonitoringSiteID.toString(); //布防点编号
                int AIDType = strAIDAlarmV41.struAIDInfo.dwAIDType; //    交通事件类型
                int AIDTypeEx = strAIDAlarmV41.struAIDInfo.dwAIDTypeEx; //交通事件类型扩展
                logger.info("【道路事件检测】" + "时间：" + sTime + "布防点：" + MonitoringSiteID + "交通事件类型：" + AIDType + "交通事件类型扩展：" + AIDTypeEx);
                //报警图片信息
                if (strAIDAlarmV41.dwPicDataLen > 0 && strAIDAlarmV41.pImage != null) {
                    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String newName = sf.format(new Date());
                    FileOutputStream fout;
                    try {
                        String filename = "./pic/" + newName + "_AIDalarm.jpg";
                        fout = new FileOutputStream(filename);
                        //将字节写入文件
                        long offset = 0;
                        ByteBuffer buffers = strAIDAlarmV41.pImage.getByteBuffer(offset, strAIDAlarmV41.dwPicDataLen);
                        byte[] bytes = new byte[strAIDAlarmV41.dwPicDataLen];
                        buffers.rewind();
                        buffers.get(bytes);
                        fout.write(bytes);
                        fout.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case HCNetSDK.COMM_ALARM_TPS_V41://交通数据统计的报警
                HCNetSDK.NET_DVR_TPS_ALARM_V41 strTPSalarmV41 = new HCNetSDK.NET_DVR_TPS_ALARM_V41();
                strTPSalarmV41.write();
                Pointer pstrTPSalarmV41 = strTPSalarmV41.getPointer();
                pstrTPSalarmV41.write(0, pAlarmInfo.getByteArray(0, strTPSalarmV41.size()), 0, strTPSalarmV41.size());
                strTPSalarmV41.read();
                sTime = CommonUtil.parseTime(strTPSalarmV41.dwAbsTime);
                MonitoringSiteID = strTPSalarmV41.byMonitoringSiteID.toString(); //布防点编号
                String StartTime = CommonUtil.parseTime(strTPSalarmV41.dwStartTime); //开始统计时间；
                String StopTime = CommonUtil.parseTime(strTPSalarmV41.dwStopTime); //结束统计时间；
                logger.info("【交通数据统计】" + "时间：" + sTime + "布防点编号：" + MonitoringSiteID + "开始统计时间：" + StartTime + "结束统计时间：" + StopTime);
                //车道统计参数信息
                for (int i = 0; i <= HCNetSDK.MAX_TPS_RULE; i++) {
                    byte LaneNo = strTPSalarmV41.struTPSInfo.struLaneParam[i].byLaneNo; //车道号
                    byte TrafficState = strTPSalarmV41.struTPSInfo.struLaneParam[i].byTrafficState; //车道状态 0-无效，1-畅通，2-拥挤，3-堵塞
                    int TpsType = strTPSalarmV41.struTPSInfo.struLaneParam[i].dwTpsType; //数据变化类型标志，表示当前上传的统计参数中，哪些数据有效，按位区分
                    int LaneVolume = strTPSalarmV41.struTPSInfo.struLaneParam[i].dwLaneVolume; //车道流量
                    int LaneVelocity = strTPSalarmV41.struTPSInfo.struLaneParam[i].dwLaneVelocity; //车道平均速度
                    float SpaceOccupyRation = strTPSalarmV41.struTPSInfo.struLaneParam[i].fSpaceOccupyRation;  //车道占有率，百分比计算（空间上，车辆长度与布防路段总长度的比值)
                    logger.info("车道号：" + LaneNo + "车道状态：" + TrafficState + "车道流量：" + LaneVolume + "车道占有率：" + SpaceOccupyRation + "\n");
                }
                break;
            case HCNetSDK.COMM_ALARM_TPS_REAL_TIME: //实时过车数据数据
                HCNetSDK.NET_DVR_TPS_REAL_TIME_INFO netDvrTpsParam = new HCNetSDK.NET_DVR_TPS_REAL_TIME_INFO();
                netDvrTpsParam.write();
                Pointer pItsParkVehicle = netDvrTpsParam.getPointer();
                pItsParkVehicle.write(0, pAlarmInfo.getByteArray(0, netDvrTpsParam.size()), 0, netDvrTpsParam.size());
                netDvrTpsParam.read();
                String struTime = "" + String.format("%04d", netDvrTpsParam.struTime.wYear) +
                        String.format("%02d", netDvrTpsParam.struTime.byMonth) +
                        String.format("%02d", netDvrTpsParam.struTime.byDay) +
                        String.format("%02d", netDvrTpsParam.struTime.byDay) +
                        String.format("%02d", netDvrTpsParam.struTime.byHour) +
                        String.format("%02d", netDvrTpsParam.struTime.byMinute) +
                        String.format("%02d", netDvrTpsParam.struTime.bySecond);
                Short wDeviceID = new Short(netDvrTpsParam.struTPSRealTimeInfo.wDeviceID);//设备ID
                int channel = netDvrTpsParam.dwChan; //触发报警通道号
                String byLane = new String(String.valueOf(netDvrTpsParam.struTPSRealTimeInfo.byLane)).trim();// 对应车道号
                String bySpeed = new String(String.valueOf(netDvrTpsParam.struTPSRealTimeInfo.bySpeed)).trim();// 对应车速（KM/H)
                int dwDownwardFlow = netDvrTpsParam.struTPSRealTimeInfo.dwDownwardFlow;//当前车道 从上到下车流量
                int dwUpwardFlow = netDvrTpsParam.struTPSRealTimeInfo.dwUpwardFlow;       //当前车道 从下到上车流量
                logger.info("通道号：" + channel + "; 时间：" + struTime + ";对应车道号：" + byLane + ";当前车道 从上到下车流量：" + dwDownwardFlow + ";dwUpwardFlow：" + dwUpwardFlow);
                break;

            case HCNetSDK.COMM_ALARM_TPS_STATISTICS: //统计过车数据
                HCNetSDK.NET_DVR_TPS_STATISTICS_INFO netDvrTpsStatisticsInfo = new HCNetSDK.NET_DVR_TPS_STATISTICS_INFO();
                netDvrTpsStatisticsInfo.write();
                Pointer pTpsVehicle = netDvrTpsStatisticsInfo.getPointer();
                pTpsVehicle.write(0, pAlarmInfo.getByteArray(0, netDvrTpsStatisticsInfo.size()), 0, netDvrTpsStatisticsInfo.size());
                netDvrTpsStatisticsInfo.read();
                int Tpschannel = netDvrTpsStatisticsInfo.dwChan; //触发报警通道号
                //统计开始时间
                String struStartTime = "" + String.format("%04d", netDvrTpsStatisticsInfo.struTPSStatisticsInfo.struStartTime.wYear) +
                        String.format("%02d", netDvrTpsStatisticsInfo.struTPSStatisticsInfo.struStartTime.byMonth) +
                        String.format("%02d", netDvrTpsStatisticsInfo.struTPSStatisticsInfo.struStartTime.byDay) +
                        String.format("%02d", netDvrTpsStatisticsInfo.struTPSStatisticsInfo.struStartTime.byDay) +
                        String.format("%02d", netDvrTpsStatisticsInfo.struTPSStatisticsInfo.struStartTime.byHour) +
                        String.format("%02d", netDvrTpsStatisticsInfo.struTPSStatisticsInfo.struStartTime.byMinute) +
                        String.format("%02d", netDvrTpsStatisticsInfo.struTPSStatisticsInfo.struStartTime.bySecond);
                byte TotalLaneNum = netDvrTpsStatisticsInfo.struTPSStatisticsInfo.byTotalLaneNum; //有效车道总数
                logger.info("通道号：" + Tpschannel + "; 开始统计时间：" + struStartTime + "有效车道总数：" + TotalLaneNum);
                break;
            case HCNetSDK.COMM_ITS_PARK_VEHICLE: //停车场数据上传
                HCNetSDK.NET_ITS_PARK_VEHICLE strParkVehicle = new HCNetSDK.NET_ITS_PARK_VEHICLE();
                strParkVehicle.write();
                Pointer pstrParkVehicle = strParkVehicle.getPointer();
                pstrParkVehicle.write(0, pAlarmInfo.getByteArray(0, strParkVehicle.size()), 0, strParkVehicle.size());
                strParkVehicle.read();
                try {
                    byte ParkError = strParkVehicle.byParkError; //停车异常：0- 正常，1- 异常
                    String ParkingNo = new String(strParkVehicle.byParkingNo, "UTF-8"); //车位编号
                    byte LocationStatus = strParkVehicle.byLocationStatus; //车位车辆状态 0- 无车，1- 有车
                    MonitoringSiteID = strParkVehicle.byMonitoringSiteID.toString();
                    String plateNo = new String(strParkVehicle.struPlateInfo.sLicense, "GBK"); //车牌号
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                //报警图片信息
                for (int i = 0; i < strParkVehicle.dwPicNum; i++) {
                    if (strParkVehicle.struPicInfo[i].dwDataLen > 0) {
                        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                        String newName = sf.format(new Date());
                        FileOutputStream fout;
                        try {
                            String filename = "./pic/" + newName + "_ParkVehicle.jpg";
                            fout = new FileOutputStream(filename);
                            //将字节写入文件
                            long offset = 0;
                            ByteBuffer buffers = strParkVehicle.struPicInfo[i].pBuffer.getByteBuffer(offset, strParkVehicle.struPicInfo[i].dwDataLen);
                            byte[] bytes = new byte[strParkVehicle.struPicInfo[i].dwDataLen];
                            buffers.rewind();
                            buffers.get(bytes);
                            fout.write(bytes);
                            fout.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
            case HCNetSDK.COMM_ALARMHOST_CID_ALARM://报警主机CID报告报警上传
                HCNetSDK.NET_DVR_CID_ALARM strCIDalarm = new HCNetSDK.NET_DVR_CID_ALARM();
                strCIDalarm.write();
                Pointer pstrCIDalarm = strCIDalarm.getPointer();
                pstrCIDalarm.write(0, pAlarmInfo.getByteArray(0, strCIDalarm.size()), 0, strCIDalarm.size());
                strCIDalarm.read();
                try {
                    String TriggerTime = "" + String.format("%04d", strCIDalarm.struTriggerTime.wYear) +
                            String.format("%02d", strCIDalarm.struTriggerTime.byMonth) +
                            String.format("%02d", strCIDalarm.struTriggerTime.byDay) +
                            String.format("%02d", strCIDalarm.struTriggerTime.byDay) +
                            String.format("%02d", strCIDalarm.struTriggerTime.byHour) +
                            String.format("%02d", strCIDalarm.struTriggerTime.byMinute) +
                            String.format("%02d", strCIDalarm.struTriggerTime.bySecond);  //触发报警时间
                    String sCIDCode = new String(strCIDalarm.sCIDCode, "GBK"); //CID事件号
                    String sCIDDescribe = new String(strCIDalarm.sCIDDescribe, "GBK"); //CID事件名
                    byte bySubSysNo = strCIDalarm.bySubSysNo; //子系统号
                    if (strCIDalarm.wDefenceNo != 0xff)
                    {
                        logger.info("防区号："+Integer.sum(strCIDalarm.wDefenceNo,1));
                    }
                    logger.info("【CID事件】" + "触发时间：" + TriggerTime + "CID事件号：" + sCIDCode + "CID事件名：" + sCIDDescribe + "子系统号：" +
                            bySubSysNo);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                break;
            case HCNetSDK.COMM_IPC_AUXALARM_RESULT: //PIR报警、无线报警、呼救报警信息
                logger.info("PIR报警、无线报警、呼救报警触发");
                break;
            case HCNetSDK.COMM_ISAPI_ALARM: //ISAPI协议报警信息
                HCNetSDK.NET_DVR_ALARM_ISAPI_INFO struEventISAPI = new HCNetSDK.NET_DVR_ALARM_ISAPI_INFO();
                struEventISAPI.write();
                Pointer pEventISAPI = struEventISAPI.getPointer();
                pEventISAPI.write(0, pAlarmInfo.getByteArray(0, struEventISAPI.size()), 0, struEventISAPI.size());
                struEventISAPI.read();
                String sAlarmInfo = new String(pAlarmer.sDeviceIP).trim();
                //报警数据类型：0- invalid，1- xml，2- json
                sAlarmInfo = "报警设备IP：" + sAlarmInfo + "：ISAPI协议报警信息, 数据格式:" + struEventISAPI.byDataType +
                        ", 图片个数:" + struEventISAPI.byPicturesNumber;
                logger.info(sAlarmInfo);
                SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMddHHmmss");
                String curTime1 = sf1.format(new Date());
                //报警数据打印
                HCNetSDK.BYTE_ARRAY m_strISAPIData = new HCNetSDK.BYTE_ARRAY(struEventISAPI.dwAlarmDataLen);
                m_strISAPIData.write();
                Pointer pPlateInfo = m_strISAPIData.getPointer();
                pPlateInfo.write(0, struEventISAPI.pAlarmData.getByteArray(0, m_strISAPIData.size()), 0, m_strISAPIData.size());
                m_strISAPIData.read();
                logger.info(new String(m_strISAPIData.byValue).trim() +"\n");
/*
                FileOutputStream foutdata;
                try {
                    String jsonfilename = "./pic/" + new String(pAlarmer.sDeviceIP).trim() + curTime1 + "_ISAPI_Alarm_" + ".json";
                    foutdata = new FileOutputStream(jsonfilename);
                    //将字节写入文件
                    ByteBuffer jsonbuffers = struEventISAPI.pAlarmData.getByteBuffer(0, struEventISAPI.dwAlarmDataLen);
                    byte[] jsonbytes = new byte[struEventISAPI.dwAlarmDataLen];
                    jsonbuffers.rewind();
                    jsonbuffers.get(jsonbytes);
                    foutdata.write(jsonbytes);
                    foutdata.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                //图片数据保存
                for (int i = 0; i < struEventISAPI.byPicturesNumber; i++) {
                    HCNetSDK.NET_DVR_ALARM_ISAPI_PICDATA struPicData = new HCNetSDK.NET_DVR_ALARM_ISAPI_PICDATA();
                    struPicData.write();
                    Pointer pPicData = struPicData.getPointer();
                    pPicData.write(0, struEventISAPI.pPicPackData.getByteArray(i * struPicData.size(), struPicData.size()), 0, struPicData.size());
                    struPicData.read();

                    FileOutputStream fout;
                    try {
                        String filename = "./pic/" + new String(pAlarmer.sDeviceIP).trim() + curTime1 +
                                "_ISAPIPic_" + i + "_" + new String(struPicData.szFilename).trim() + ".jpg";
                        fout = new FileOutputStream(filename);
                        //将字节写入文件
                        long offset = 0;
                        ByteBuffer buffers = struPicData.pPicData.getByteBuffer(offset, struPicData.dwPicLen);
                        byte[] bytes = new byte[struPicData.dwPicLen];
                        buffers.rewind();
                        buffers.get(bytes);
                        fout.write(bytes);
                        fout.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case HCNetSDK.COMM_VCA_ALARM:  // 智能检测通用报警(Json或者XML数据结构)
                sAlarmInfo = new String(pAlarmer.sDeviceIP);
                //报警数据类型：0- invalid，1- xml，2- json
                sAlarmInfo = "报警设备IP：" + sAlarmInfo;
                logger.info(sAlarmInfo);

                SimpleDateFormat sf0 = new SimpleDateFormat("yyyyMMddHHmmss");
                String curTime0 = sf0.format(new Date());
                FileOutputStream Data;
                String jsonfile = "../pic" + new String(pAlarmer.sDeviceIP).trim() + curTime0 + "_VCA_ALARM_" + ".json";
                try {
                    Data = new FileOutputStream(jsonfile);
                    //将字节写入文件
                    ByteBuffer dataBuffer = pAlarmInfo.getByteBuffer(0, dwBufLen);
                    byte[] dataByte = new byte[dwBufLen];
                    dataBuffer.rewind();
                    dataBuffer.get(dataByte);
                    Data.write(dataByte);
                    Data.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case HCNetSDK.COMM_SNAP_MATCH_ALARM:    //人脸比对报警
                HCNetSDK.NET_VCA_FACESNAP_MATCH_ALARM strFaceSnapMatch = new HCNetSDK.NET_VCA_FACESNAP_MATCH_ALARM();
                strFaceSnapMatch.write();
                Pointer pFaceSnapMatch = strFaceSnapMatch.getPointer();
                pFaceSnapMatch.write(0, pAlarmInfo.getByteArray(0, strFaceSnapMatch.size()), 0, strFaceSnapMatch.size());
                strFaceSnapMatch.read();
                //比对结果，0-保留，1-比对成功，2-比对失败
                String sFaceSnapMatchInfo = "比对结果：" + strFaceSnapMatch.byContrastStatus + ",相似度：" + strFaceSnapMatch.fSimilarity;
                logger.info(sFaceSnapMatchInfo);
                if (strFaceSnapMatch.struBlockListInfo.dwFDIDLen > 0) {
                    long offset1 = 0;
                    ByteBuffer buffers1 = strFaceSnapMatch.struBlockListInfo.pFDID.getByteBuffer(offset1, strFaceSnapMatch.struBlockListInfo.dwFDIDLen);
                    byte[] bytes1 = new byte[strFaceSnapMatch.struBlockListInfo.dwFDIDLen];
                    buffers1.get(bytes1);
                    logger.info("人脸库ID:" + new String(bytes1));
                }
                if (strFaceSnapMatch.struBlockListInfo.dwPIDLen > 0) {
                    long offset2 = 0;
                    ByteBuffer buffers2 = strFaceSnapMatch.struBlockListInfo.pPID.getByteBuffer(offset2, strFaceSnapMatch.struBlockListInfo.dwPIDLen);
                    byte[] bytes2 = new byte[strFaceSnapMatch.struBlockListInfo.dwPIDLen];
                    buffers2.get(bytes2);
                    logger.info("图片ID：" + new String(bytes2));
                }
                if (strFaceSnapMatch.struBlockListInfo.struBlockListInfo.dwFCAdditionInfoLen > 0) {
                    //抓拍库附加信息解析（解析人脸测温温度,人脸温度存放在附件信息的XML报文中，节点：  <currTemperature> ）
                    SimpleDateFormat sf2 = new SimpleDateFormat("yyyyMMddHHmmss");
                    String curTime2 = sf2.format(new Date());
                    FileOutputStream AddtionData;
                    String AddtionFile = "../pic" + new String(pAlarmer.sDeviceIP).trim() + curTime2 + "_FCAdditionInfo_" + ".xml";
                    try {
                        Data = new FileOutputStream(AddtionFile);
                        //将字节写入文件
                        ByteBuffer dataBuffer = strFaceSnapMatch.struBlockListInfo.struBlockListInfo.pFCAdditionInfoBuffer.getByteBuffer(0, strFaceSnapMatch.struBlockListInfo.struBlockListInfo.dwFCAdditionInfoLen);
                        byte[] dataByte = new byte[dwBufLen];
                        dataBuffer.rewind();
                        dataBuffer.get(dataByte);
                        Data.write(dataByte);
                        Data.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //人脸比对报警图片保存，图片格式二进制
                if ((strFaceSnapMatch.dwSnapPicLen > 0) && (strFaceSnapMatch.byPicTransType == 0)) {
                    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String newName = sf.format(new Date());
                    FileOutputStream fout;
                    try {
                        String filename = "./pic/" + newName + "_pSnapPicBuffer" + ".jpg";
                        fout = new FileOutputStream(filename);
                        //将字节写入文件
                        long offset = 0;
                        ByteBuffer buffers = strFaceSnapMatch.pSnapPicBuffer.getByteBuffer(offset, strFaceSnapMatch.dwSnapPicLen);
                        byte[] bytes = new byte[strFaceSnapMatch.dwSnapPicLen];
                        buffers.rewind();
                        buffers.get(bytes);
                        fout.write(bytes);
                        fout.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if ((strFaceSnapMatch.struSnapInfo.dwSnapFacePicLen > 0) && (strFaceSnapMatch.byPicTransType == 0)) {
                    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String newName = sf.format(new Date());
                    FileOutputStream fout;
                    try {
                        String filename = "./pic/" + newName + "_struSnapInfo_pBuffer1" + ".jpg";
                        fout = new FileOutputStream(filename);
                        //将字节写入文件
                        long offset = 0;
                        ByteBuffer buffers = strFaceSnapMatch.struSnapInfo.pBuffer1.getByteBuffer(offset, strFaceSnapMatch.struSnapInfo.dwSnapFacePicLen);
                        byte[] bytes = new byte[strFaceSnapMatch.struSnapInfo.dwSnapFacePicLen];
                        buffers.rewind();
                        buffers.get(bytes);
                        fout.write(bytes);
                        fout.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if ((strFaceSnapMatch.struBlockListInfo.dwBlockListPicLen > 0) && (strFaceSnapMatch.byPicTransType == 0)) {
                    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String newName = sf.format(new Date());
                    FileOutputStream fout;
                    try {
                        String filename = "./pic/" + newName + "_fSimilarity_" + strFaceSnapMatch.fSimilarity + "_struBlackListInfo_pBuffer1" + ".jpg";
                        fout = new FileOutputStream(filename);
                        //将字节写入文件
                        long offset = 0;
                        ByteBuffer buffers = strFaceSnapMatch.struBlockListInfo.pBuffer1.getByteBuffer(offset, strFaceSnapMatch.struBlockListInfo.dwBlockListPicLen);
                        byte[] bytes = new byte[strFaceSnapMatch.struBlockListInfo.dwBlockListPicLen];
                        buffers.rewind();
                        buffers.get(bytes);
                        fout.write(bytes);
                        fout.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //人脸比对报警图片保存，图片格式URL格式
                if ((strFaceSnapMatch.dwSnapPicLen > 0) && (strFaceSnapMatch.byPicTransType == 1)) {
                    long offset = 0;
                    ByteBuffer buffers = strFaceSnapMatch.pSnapPicBuffer.getByteBuffer(offset, strFaceSnapMatch.dwSnapPicLen);
                    byte[] bytes = new byte[strFaceSnapMatch.dwSnapPicLen];
                    buffers.rewind();
                    buffers.get(bytes);
                    String SnapPicUrl = new String(bytes);
                    logger.info("抓拍图URL：" + SnapPicUrl);
                }
                if ((strFaceSnapMatch.struSnapInfo.dwSnapFacePicLen > 0) && (strFaceSnapMatch.byPicTransType == 1)) {
                    long offset = 0;
                    ByteBuffer buffers = strFaceSnapMatch.struSnapInfo.pBuffer1.getByteBuffer(offset, strFaceSnapMatch.struSnapInfo.dwSnapFacePicLen);
                    byte[] bytes = new byte[strFaceSnapMatch.struSnapInfo.dwSnapFacePicLen];
                    buffers.rewind();
                    buffers.get(bytes);
                    String SnapPicUrl = new String(bytes);
                    logger.info("抓拍人脸子图URL：" + SnapPicUrl);
                }
                if ((strFaceSnapMatch.struBlockListInfo.dwBlockListPicLen > 0) && (strFaceSnapMatch.byPicTransType == 1)) {
                    long offset = 0;
                    ByteBuffer buffers = strFaceSnapMatch.struBlockListInfo.pBuffer1.getByteBuffer(offset, strFaceSnapMatch.struBlockListInfo.dwBlockListPicLen);
                    byte[] bytes = new byte[strFaceSnapMatch.struBlockListInfo.dwBlockListPicLen];
                    buffers.rewind();
                    buffers.get(bytes);
                    String SnapPicUrl = new String(bytes);
                    logger.info("人脸库人脸图的URL：" + SnapPicUrl);
                }
                break;

            case HCNetSDK.COMM_THERMOMETRY_ALARM:  //温度报警信息
                HCNetSDK.NET_DVR_THERMOMETRY_ALARM struTemInfo = new HCNetSDK.NET_DVR_THERMOMETRY_ALARM();
                struTemInfo.write();
                Pointer pTemInfo = struTemInfo.getPointer();
                pTemInfo.write(0, pAlarmInfo.getByteArray(0, struTemInfo.size()), 0, struTemInfo.size());
                struTemInfo.read();
                String sThermAlarmInfo = "规则ID:" + struTemInfo.byRuleID + "预置点号：" + struTemInfo.wPresetNo + "报警等级：" + struTemInfo.byAlarmLevel + "报警类型：" +
                        struTemInfo.byAlarmType + "当前温度：" + struTemInfo.fCurrTemperature;
                logger.info(sThermAlarmInfo);
                //可见光图片保存
                if ((struTemInfo.dwPicLen > 0) && (struTemInfo.byPicTransType == 0)) {
                    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String newName = sf.format(new Date());
                    FileOutputStream fout;

                    try {
                        String filename = "./pic/" + newName + "_" + struTemInfo.fCurrTemperature + ".jpg";
                        fout = new FileOutputStream(filename);
                        //将字节写入文件
                        long offset = 0;
                        ByteBuffer buffers = struTemInfo.pPicBuff.getByteBuffer(offset, struTemInfo.dwPicLen);
                        byte[] bytes = new byte[struTemInfo.dwPicLen];
                        buffers.rewind();
                        buffers.get(bytes);
                        fout.write(bytes);
                        fout.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if ((struTemInfo.dwThermalPicLen > 0) && (struTemInfo.byPicTransType == 0)) {
                    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String newName = sf.format(new Date());
                    FileOutputStream fout;

                    try {
                        String filename = "./pic/" + newName + "_" + "_ThermalPiC" + ".jpg";
                        fout = new FileOutputStream(filename);
                        //将字节写入文件
                        long offset = 0;
                        ByteBuffer buffers = struTemInfo.pThermalPicBuff.getByteBuffer(offset, struTemInfo.dwThermalPicLen);
                        byte[] bytes = new byte[struTemInfo.dwThermalPicLen];
                        buffers.rewind();
                        buffers.get(bytes);
                        fout.write(bytes);
                        fout.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case HCNetSDK.COMM_THERMOMETRY_DIFF_ALARM: //温差检测报警
                HCNetSDK.NET_DVR_THERMOMETRY_DIFF_ALARM strThermDiffAlarm = new HCNetSDK.NET_DVR_THERMOMETRY_DIFF_ALARM();
                strThermDiffAlarm.write();
                Pointer pTemDiffInfo = strThermDiffAlarm.getPointer();
                pTemDiffInfo.write(0, pAlarmInfo.getByteArray(0, strThermDiffAlarm.size()), 0, strThermDiffAlarm.size());
                strThermDiffAlarm.read();
                String sThremDiffInfo = "通道号：" + strThermDiffAlarm.dwChannel + ",报警规则：" + strThermDiffAlarm.byAlarmRule + "，当前温差：" + strThermDiffAlarm.fCurTemperatureDiff;
                logger.info(sThremDiffInfo);
                //可见光图片保存
                if ((strThermDiffAlarm.dwPicLen > 0) && (strThermDiffAlarm.byPicTransType == 0)) {
                    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String newName = sf.format(new Date());
                    FileOutputStream fout;

                    try {
                        String filename = "./pic/" + newName + "_" + strThermDiffAlarm.fCurTemperatureDiff + ".jpg";
                        fout = new FileOutputStream(filename);
                        //将字节写入文件
                        long offset = 0;
                        ByteBuffer buffers = strThermDiffAlarm.pPicBuff.getByteBuffer(offset, strThermDiffAlarm.dwPicLen);
                        byte[] bytes = new byte[strThermDiffAlarm.dwPicLen];
                        buffers.rewind();
                        buffers.get(bytes);
                        fout.write(bytes);
                        fout.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //热成像图片保存
                if ((strThermDiffAlarm.dwThermalPicLen > 0) && (strThermDiffAlarm.byPicTransType == 0)) {
                    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String newName = sf.format(new Date());
                    FileOutputStream fout;

                    try {
                        String filename = "./pic/" + newName + "_" + "_ThermalDiffPiC" + ".jpg";
                        fout = new FileOutputStream(filename);
                        //将字节写入文件
                        long offset = 0;
                        ByteBuffer buffers = strThermDiffAlarm.pThermalPicBuff.getByteBuffer(offset, strThermDiffAlarm.dwThermalPicLen);
                        byte[] bytes = new byte[strThermDiffAlarm.dwThermalPicLen];
                        buffers.rewind();
                        buffers.get(bytes);
                        fout.write(bytes);
                        fout.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case HCNetSDK.COMM_ALARM_SHIPSDETECTION: //船只检测报警
                HCNetSDK.NET_DVR_SHIPSDETECTION_ALARM struShipAlarm = new HCNetSDK.NET_DVR_SHIPSDETECTION_ALARM();
                struShipAlarm.write();
                Pointer pShipAlarm = struShipAlarm.getPointer();
                pShipAlarm.write(0, pAlarmInfo.getByteArray(0, struShipAlarm.size()), 0, struShipAlarm.size());
                struShipAlarm.read();
                String sShipAlarm = "绝对时间：" + struShipAlarm.dwAbsTime + ",正跨越检测线的船只数:" + struShipAlarm.byShipsNum + ",船头检测的船只数 :" + struShipAlarm.byShipsNumHead
                        + ", 船尾检测的船只数 :" + struShipAlarm.byShipsNumEnd;
                logger.info(sShipAlarm);
                //可见光图片保存
                if ((struShipAlarm.dwPicLen > 0) && (struShipAlarm.byPicTransType == 0)) {
                    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String newName = sf.format(new Date());
                    FileOutputStream fout;

                    try {
                        String filename = "./pic/" + newName + "_ShipAlarm" + ".jpg";
                        fout = new FileOutputStream(filename);
                        //将字节写入文件
                        long offset = 0;
                        ByteBuffer buffers = struShipAlarm.pPicBuffer.getByteBuffer(offset, struShipAlarm.dwPicLen);
                        byte[] bytes = new byte[struShipAlarm.dwPicLen];
                        buffers.rewind();
                        buffers.get(bytes);
                        fout.write(bytes);
                        fout.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //热成像图片保存
                if ((struShipAlarm.dwThermalPicLen > 0) && (struShipAlarm.byPicTransType == 0)) {
                    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String newName = sf.format(new Date());
                    FileOutputStream fout;

                    try {
                        String filename = "./pic/" + newName + "_" + "_ThermalShipAlarm" + ".jpg";
                        fout = new FileOutputStream(filename);
                        //将字节写入文件
                        long offset = 0;
                        ByteBuffer buffers = struShipAlarm.pThermalPicBuffer.getByteBuffer(offset, struShipAlarm.dwThermalPicLen);
                        byte[] bytes = new byte[struShipAlarm.dwThermalPicLen];
                        buffers.rewind();
                        buffers.get(bytes);
                        fout.write(bytes);
                        fout.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case HCNetSDK.COMM_FIREDETECTION_ALARM://烟火检测
                HCNetSDK.NET_DVR_FIREDETECTION_ALARM struFireDecAlarm = new HCNetSDK.NET_DVR_FIREDETECTION_ALARM();
                struFireDecAlarm.write();
                Pointer pFireDecAlarm = struFireDecAlarm.getPointer();
                pFireDecAlarm.write(0, pAlarmInfo.getByteArray(0, struFireDecAlarm.size()), 0, struFireDecAlarm.size());
                struFireDecAlarm.read();
                String sFireDecAlarmInfo = "绝对时间：" + struFireDecAlarm.dwAbsTime + ",报警子类型：" + struFireDecAlarm.byAlarmSubType + ",火点最高温度 :" +
                        struFireDecAlarm.wFireMaxTemperature + ",火点目标距离：" + struFireDecAlarm.wTargetDistance;
                logger.info(sFireDecAlarmInfo);
                //可见光图片保存
                if ((struFireDecAlarm.dwVisiblePicLen > 0) && (struFireDecAlarm.byPicTransType == 0)) {
                    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String newName = sf.format(new Date());
                    FileOutputStream fout;

                    try {
                        String filename = "./pic/" + newName + "_FireDecAlarm" + ".jpg";
                        fout = new FileOutputStream(filename);
                        //将字节写入文件
                        long offset = 0;
                        ByteBuffer buffers = struFireDecAlarm.pVisiblePicBuf.getByteBuffer(offset, struFireDecAlarm.dwVisiblePicLen);
                        byte[] bytes = new byte[struFireDecAlarm.dwVisiblePicLen];
                        buffers.rewind();
                        buffers.get(bytes);
                        fout.write(bytes);
                        fout.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //热成像图片保存
                if ((struFireDecAlarm.dwPicDataLen > 0) && (struFireDecAlarm.byPicTransType == 0)) {
                    SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
                    String newName = sf.format(new Date());
                    FileOutputStream fout;

                    try {
                        String filename = "./pic/" + newName + "_" + "_ThermalFireAlarm" + ".jpg";
                        fout = new FileOutputStream(filename);
                        //将字节写入文件
                        long offset = 0;
                        ByteBuffer buffers = struFireDecAlarm.pBuffer.getByteBuffer(offset, struFireDecAlarm.dwPicDataLen);
                        byte[] bytes = new byte[struFireDecAlarm.dwPicDataLen];
                        buffers.rewind();
                        buffers.get(bytes);
                        fout.write(bytes);
                        fout.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case HCNetSDK.COMM_UPLOAD_AIOP_VIDEO: //AI开放平台接入视频检测报警信息
                logger.info("AI开放平台接入视频检测报警上传");
                HCNetSDK.NET_AIOP_VIDEO_HEAD struAIOPVideo = new HCNetSDK.NET_AIOP_VIDEO_HEAD();
                struAIOPVideo.write();
                Pointer pAIOPVideo = struAIOPVideo.getPointer();
                pAIOPVideo.write(0, pAlarmInfo.getByteArray(0, struAIOPVideo.size()), 0, struAIOPVideo.size());
                struAIOPVideo.read();
                logger.info("视频任务ID" + new String(struAIOPVideo.szTaskID));
                logger.info("通道号：" + struAIOPVideo.dwChannel);
                logger.info("检测模型包ID" + new String(struAIOPVideo.szMPID));
                String strTime = String.format("%04d", struAIOPVideo.struTime.wYear) +
                        String.format("%02d", struAIOPVideo.struTime.wMonth) +
                        String.format("%02d", struAIOPVideo.struTime.wDay) +
                        String.format("%02d", struAIOPVideo.struTime.wHour) +
                        String.format("%02d", struAIOPVideo.struTime.wMinute) +
                        String.format("%02d", struAIOPVideo.struTime.wSecond) +
                        String.format("%03d", struAIOPVideo.struTime.wMilliSec);
                //AIOPData数据
                if (struAIOPVideo.dwAIOPDataSize > 0) {
                    FileOutputStream fout;
                    try {
                        String filename = "./pic/" + new String(pAlarmer.sDeviceIP).trim() +
                                "_" + strTime + "_VideoData.json";
                        fout = new FileOutputStream(filename);
                        //将字节写入文件
                        long offset = 0;
                        ByteBuffer buffers = struAIOPVideo.pBufferAIOPData.getByteBuffer(offset, struAIOPVideo.dwAIOPDataSize);
                        byte[] bytes = new byte[struAIOPVideo.dwAIOPDataSize];
                        buffers.rewind();
                        buffers.get(bytes);
                        fout.write(bytes);
                        fout.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                // 图片数据保存
                if (struAIOPVideo.dwPictureSize > 0) {
                    FileOutputStream fout;
                    try {
                        String filename = "./pic/" + new String(pAlarmer.sDeviceIP).trim() +
                                "_" + strTime + "_VideoPic.jpg";
                        fout = new FileOutputStream(filename);
                        //将字节写入文件
                        long offset = 0;
                        ByteBuffer buffers = struAIOPVideo.pBufferPicture.getByteBuffer(offset, struAIOPVideo.dwPictureSize);
                        byte[] bytes = new byte[struAIOPVideo.dwPictureSize];
                        buffers.rewind();
                        buffers.get(bytes);
                        fout.write(bytes);
                        fout.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case HCNetSDK.COMM_UPLOAD_AIOP_PICTURE: //AI开放平台接入图片检测报警信息
                logger.info("AI开放平台接入图片检测报警上传");
                HCNetSDK.NET_AIOP_PICTURE_HEAD struAIOPPic = new HCNetSDK.NET_AIOP_PICTURE_HEAD();
                struAIOPPic.write();
                Pointer pAIOPPic = struAIOPPic.getPointer();
                pAIOPPic.write(0, pAlarmInfo.getByteArray(0, struAIOPPic.size()), 0, struAIOPPic.size());
                struAIOPPic.read();
                logger.info("图片ID：" + new String(struAIOPPic.szPID));
                logger.info("检测模型包ID：" + new String(struAIOPPic.szMPID));
                String strPicTime = "" + String.format("%04d", struAIOPPic.struTime.wYear) +
                        String.format("%02d", struAIOPPic.struTime.wMonth) +
                        String.format("%02d", struAIOPPic.struTime.wDay) +
                        String.format("%02d", struAIOPPic.struTime.wHour) +
                        String.format("%02d", struAIOPPic.struTime.wMinute) +
                        String.format("%02d", struAIOPPic.struTime.wSecond) +
                        String.format("%03d", struAIOPPic.struTime.wMilliSec);
                //AIOPData数据
                if (struAIOPPic.dwAIOPDataSize > 0) {
                    FileOutputStream fout;
                    try {
                        String filename = "./pic/" + new String(pAlarmer.sDeviceIP).trim() +
                                "_" + strPicTime + "_AIO_PicData.json";
                        fout = new FileOutputStream(filename);
                        //将字节写入文件
                        long offset = 0;
                        ByteBuffer buffers = struAIOPPic.pBufferAIOPData.getByteBuffer(offset, struAIOPPic.dwAIOPDataSize);
                        byte[] bytes = new byte[struAIOPPic.dwAIOPDataSize];
                        buffers.rewind();
                        buffers.get(bytes);
                        fout.write(bytes);
                        fout.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            // AI开放平台接入轮询抓图检测报警信息
            case HCNetSDK.COMM_UPLOAD_AIOP_POLLING_SNAP:
                logger.info("AI开放平台接入轮询抓图检测报警事件上传");
                HCNetSDK.NET_AIOP_POLLING_SNAP_HEAD strAiopPollingPic = new HCNetSDK.NET_AIOP_POLLING_SNAP_HEAD();
                strAiopPollingPic.write();
                Pointer pAiopPollingPic = strAiopPollingPic.getPointer();
                pAiopPollingPic.write(0, pAlarmInfo.getByteArray(0, strAiopPollingPic.size()), 0, strAiopPollingPic.size());
                strAiopPollingPic.read();
                logger.info("通道号：" + strAiopPollingPic.dwChannel);
                logger.info("轮询抓图任务ID：" + new String(strAiopPollingPic.szTaskID));
                String strPollingPicTime = "" + String.format("%04d", strAiopPollingPic.struTime.wYear) +
                        String.format("%02d", strAiopPollingPic.struTime.wMonth) +
                        String.format("%02d", strAiopPollingPic.struTime.wDay) +
                        String.format("%02d", strAiopPollingPic.struTime.wHour) +
                        String.format("%02d", strAiopPollingPic.struTime.wMinute) +
                        String.format("%02d", strAiopPollingPic.struTime.wSecond) +
                        String.format("%03d", strAiopPollingPic.struTime.wMilliSec);
                //AIOPData数据保存
                if (strAiopPollingPic.dwAIOPDataSize > 0) {
                    FileOutputStream fout;
                    try {
                        String filename = "./pic/" + new String(pAlarmer.sDeviceIP).trim() +
                                "_" + strPollingPicTime + "_PollingPicData.json";
                        fout = new FileOutputStream(filename);
                        //将字节写入文件
                        long offset = 0;
                        ByteBuffer buffers = strAiopPollingPic.pBufferAIOPData.getByteBuffer(offset, strAiopPollingPic.dwAIOPDataSize);
                        byte[] bytes = new byte[strAiopPollingPic.dwAIOPDataSize];
                        buffers.rewind();
                        buffers.get(bytes);
                        fout.write(bytes);
                        fout.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //轮询抓图图片保存
                if (strAiopPollingPic.dwPictureSize > 0) {
                    FileOutputStream fout;
                    try {
                        String filename = "./pic/" + new String(pAlarmer.sDeviceIP).trim() +
                                "_" + strPollingPicTime + "_PollingPic.jpg";
                        fout = new FileOutputStream(filename);
                        //将字节写入文件
                        long offset = 0;
                        ByteBuffer buffers = strAiopPollingPic.pBufferPicture.getByteBuffer(offset, strAiopPollingPic.dwPictureSize);
                        byte[] bytes = new byte[strAiopPollingPic.dwPictureSize];
                        buffers.rewind();
                        buffers.get(bytes);
                        fout.write(bytes);
                        fout.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                break;
            // AI开放平台接入轮询视频检测报警信息
            case HCNetSDK.COMM_UPLOAD_AIOP_POLLING_VIDEO:
                logger.info("AI开放平台接入轮询视频检测报警事件上传");
                HCNetSDK.NET_AIOP_POLLING_VIDEO_HEAD strAiopPollingVideo = new HCNetSDK.NET_AIOP_POLLING_VIDEO_HEAD();
                strAiopPollingVideo.write();
                Pointer pAiopPollingVideo = strAiopPollingVideo.getPointer();
                pAiopPollingVideo.write(0, pAlarmInfo.getByteArray(0, strAiopPollingVideo.size()), 0, strAiopPollingVideo.size());
                strAiopPollingVideo.read();
                logger.info("通道号：" + strAiopPollingVideo.dwChannel);
                logger.info("轮询视频任务ID：" + new String(strAiopPollingVideo.szTaskID));
                String AiopPollingVideoTime = "" + String.format("%04d", strAiopPollingVideo.struTime.wYear) +
                        String.format("%02d", strAiopPollingVideo.struTime.wMonth) +
                        String.format("%02d", strAiopPollingVideo.struTime.wDay) +
                        String.format("%02d", strAiopPollingVideo.struTime.wHour) +
                        String.format("%02d", strAiopPollingVideo.struTime.wMinute) +
                        String.format("%02d", strAiopPollingVideo.struTime.wSecond) +
                        String.format("%03d", strAiopPollingVideo.struTime.wMilliSec);
                //AIOPData数据保存
                if (strAiopPollingVideo.dwAIOPDataSize > 0) {
                    FileOutputStream fout;
                    try {
                        String filename = "./pic/" + new String(pAlarmer.sDeviceIP).trim() +
                                "_" + AiopPollingVideoTime + "_PollingVideoData.json";
                        fout = new FileOutputStream(filename);
                        //将字节写入文件
                        long offset = 0;
                        ByteBuffer buffers = strAiopPollingVideo.pBufferAIOPData.getByteBuffer(offset, strAiopPollingVideo.dwAIOPDataSize);
                        byte[] bytes = new byte[strAiopPollingVideo.dwAIOPDataSize];
                        buffers.rewind();
                        buffers.get(bytes);
                        fout.write(bytes);
                        fout.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //对应分析图片数据
                if (strAiopPollingVideo.dwPictureSize > 0) {
                    FileOutputStream fout;
                    try {
                        String filename = "./pic/" + new String(pAlarmer.sDeviceIP).trim() +
                                "_" + AiopPollingVideoTime + "_PollingVideo.jpg";
                        fout = new FileOutputStream(filename);
                        //将字节写入文件
                        long offset = 0;
                        ByteBuffer buffers = strAiopPollingVideo.pBufferPicture.getByteBuffer(offset, strAiopPollingVideo.dwPictureSize);
                        byte[] bytes = new byte[strAiopPollingVideo.dwPictureSize];
                        buffers.rewind();
                        buffers.get(bytes);
                        fout.write(bytes);
                        fout.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                logger.info("报警类型" + Integer.toHexString(lCommand));
                break;
        }
    }
    /**
     * 根据车牌号和状态更新车辆信息
     *
     * @param license 车牌号
     * @param status 状态值("1"表示进入,"3"表示外出)
     */
    private void updateVehicleStatus(String license, String status,Long deviceId) {
        try {

            // 根据车牌号从用车申请中获取车辆类别
            BdglThebusApply bdglThebusApply = bdglThebusApplyService.selectBdglThebusApplyByCarIds(license);
            if (bdglThebusApply == null) {
                logger.warn("未找到车牌号对应的用车申请记录: {}", license);
                return;
            }

            String vehicleType = bdglThebusApply.getVehicleType();

            // 根据车辆类别更改对应车辆的状态(军车 or 私家车)
            if ("MILITARY".equals(vehicleType)) {
                BdglThebus bdglThebus = bdglThebusService.selectBdglThebusByVehicleNumber(license);
                if (bdglThebus != null) {
                    bdglThebus.setStatus(status);
                    bdglThebusService.updateBdglThebus(bdglThebus);
                }
            } else if ("PRIVATE".equals(vehicleType)) {
                BdglPrivateCar bdglPrivateCar = bdglPrivateCarService.selectBdglPrivateCarByVehicleNumber(license);
                if (bdglPrivateCar != null) {
                    bdglPrivateCar.setState(status);
                    bdglPrivateCarService.updateBdglPrivateCar(bdglPrivateCar);
                }
                // 删除车闸记录
                Map<String, Object> params = new HashMap<>();
                List<NameList> list = new ArrayList<>();
                NameList nameList = new NameList();
                nameList.setLicensePlate(license);
                nameList.setType(1);
                nameList.setDeviceId(deviceId);
                list.add(nameList);
                params.put("list", list);
                // 注意：这里可能还需要添加deviceId参数，根据实际情况确定
                nameListController.delete(params);
            }
            // 更新人的状态
            // 获取审核通过，并且车牌对应，以及创建时间最新的一条记录
            BdglLeave bdglLeave = bdglLeaveService.selectNewLeaveByCarNumber(license);
            if (bdglLeave != null) {
                String[] peopleIds = bdglLeave.getPeopleId().split(",");
                for (String peopleId : peopleIds) {
                    BdglPeople bdglPeople = bdglPeopleService.selectBdglPeopleById(Long.parseLong(peopleId));
                    if (bdglPeople != null) {
                        bdglPeopleAccessController.setStatusByEmployeeNumber(bdglPeople.getEmployeeNumber(), status.equals("1") ? 1 : 0 );
                    }
                }
            }
        } catch (Exception e) {
            logger.error("更新车辆状态失败，车牌号: {}, 状态: {}", license, status, e);
        }
    }
}
