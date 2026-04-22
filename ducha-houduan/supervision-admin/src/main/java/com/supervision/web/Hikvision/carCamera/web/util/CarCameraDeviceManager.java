package com.supervision.web.Hikvision.carCamera.web.util;


import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.supervision.web.Hikvision.carCamera.connectSDK.NetSDKDemo.HCNetSDK;
import com.supervision.web.Hikvision.carCamera.connectSDK.alarm.Alarm;
import com.supervision.web.Hikvision.carCamera.connectSDK.alarm.FMSGCallBack;
import com.supervision.web.Hikvision.carCamera.connectSDK.alarm.CarCameraFMSGCallBack_V31;
import com.supervision.web.Hikvision.util.DeviceSession;
import com.supervision.web.Hikvision.util.HikSDKManager;
import com.supervision.web.Hikvision.util.SdkType;
import com.supervision.web.Hikvision.peopleDoor.web.entity.Device;
import com.supervision.web.Hikvision.peopleDoor.web.service.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 海康威视车牌识别摄像机统一管理器
 * ------------------------------------
 * 职责：
 *  1. 初始化并加载海康 HCNetSDK
 *  2. 管理设备登录、登出、布防等操作
 *  3. 注册报警回调，接收车牌识别事件
 */
@Component
public class CarCameraDeviceManager {

    private static final Logger logger = LoggerFactory.getLogger(CarCameraDeviceManager.class);

    @Autowired
    private HikSDKManager hikSDKManager;

    private HCNetSDK hCNetSDK;

    @Autowired
    private Alarm alarm;

    @Autowired
    private DeviceService deviceService;

    /** 报警监听句柄（一般用于多设备监听） */
    private int lListenHandle = -1;

    /** 报警布防回调 */
    private CarCameraFMSGCallBack_V31 fMSFCallBack_V31;

    /** 报警监听回调（不常用） */
    private FMSGCallBack fMSFCallBack;

    /** key: 设备IP, value: 登录会话信息 */
    private final Map<String, DeviceSession> deviceMap = new ConcurrentHashMap<>();

    public Map<String, DeviceSession> getDeviceMap() {
        return deviceMap;
    }

    /**
     * Spring Bean 初始化后执行
     * ----------------------------------------
     * 1. 初始化 HCNetSDK（仅一次）
     * 2. 设置连接参数与报警回调
     * 3. 登录设备并布防
     */
    @PostConstruct
    public void init() {
        logger.info("🚀 开始初始化车闸摄像头设备");
        try {
            // 1. 初始化 SDK
            if (!initSDK()) {
                logger.error("❌ SDK 初始化失败，终止初始化流程");
                return;
            }

            // 2. 配置 SDK 参数
            // 设置连接时间与重连策略
            this.setupConnectionConfig();
//            // 设置报警回调函数（全局仅一次）
//            this.setAlarmCallback();
            // 设置本地SDK配置项（JSON与图片分离）
            this.setupLocalJsonConfig();

            // 3. 加载门禁设备列表
            Map<String, Object> params = new HashMap<>();
            params.put("deviceName", null);
            params.put("deviceType", "carDoor");
            params.put("enable", true);
            params.put("paginate", false);
            List<Device> devices = (List<Device>) deviceService.searchByCondition(params);
            if (devices == null || devices.isEmpty()) {
                logger.warn("⚠️ 未找到可用的车闸摄像头设备，初始化结束");
                return;
            }

            // 4. 登录所有门禁设备
            for (Device device : devices) {
                String videosJson = device.getVideosJson();
                if (videosJson == null || videosJson.trim().isEmpty()) {
                    logger.warn("⚠️ 设备 {} 的 videosJson 为空", device.getId());
                    continue;
                }
                // 使用 Fastjson2 解析 JSON 字符串为 JSONArray
                JSONArray jsonArray = com.alibaba.fastjson2.JSON.parseArray(videosJson);
                if (jsonArray == null || jsonArray.isEmpty()) {
                    logger.warn("⚠️ 设备 {} 的摄像头列表为空", device.getId());
                    continue;
                }
                // 遍历每个摄像头对象
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject cameraObject = jsonArray.getJSONObject(i);
                    // 提取 videoInfo 对象
                    JSONObject videoInfoObject = cameraObject.getJSONObject("videoInfo");
                    if (videoInfoObject != null) {
                        Boolean enable = videoInfoObject.getBoolean("enable");
                        if (enable == null || !enable) {
                            logger.debug("摄像头 {} 未启用，跳过");
                            continue;
                        }

                        String ip = videoInfoObject.getString("ip");
                        short port = videoInfoObject.getInteger("port").shortValue();
                        String username = videoInfoObject.getString("username");
                        String password = videoInfoObject.getString("password");
                        DeviceSession session = loginDevice(
                                ip,
                                port,
                                username,
                                password
                        );
                        if (session == null) {
                            logger.warn("⚠️ 车闸摄像头设备登录失败: {}", ip);
                            continue;
                        }
                        // 保存设备登录信息
                        deviceMap.put(ip, session);
                        logger.info("✅ 车闸摄像头设备登录成功: {} (userID={})", ip, session.getUserID());

                        // 对该设备进行布防（传入设备IP，用于关联DeviceSession）
                        this.setupAlarm(session.getUserID(), ip);
                    }
                }
            }

            logger.info("✅ 车闸摄像头设备初始化流程完成");
        } catch (Exception e) {
            logger.error("❌ 车闸摄像头设备初始化异常", e);
        }
    }

    /**
     * 初始化 CarCamera SDK
     */
    private boolean initSDK() {
        hikSDKManager.initSDK(SdkType.CAR_CAMERA);
        hCNetSDK = hikSDKManager.getCarCameraSDK(); // 类型安全获取
        if (hCNetSDK == null) {
            return false;
        }
        logger.info("✅ CarCamera HCNetSDK 初始化成功");
        return true;
    }

    /**
     * 设置报警回调函数（全局只需设置一次）
     * ------------------------------------------------
     * SDK 初始化后立即调用。
     * 所有设备报警数据都会通过该回调统一上报。
     */
    private void setAlarmCallback() {
        /*注：多台设备对接时也只需要调用一次设置一个回调函数，不支持不同设备的事件在不同的回调函数里面返回*/
        // 先调用NET_DVR_SetDVRMessageCallBack_V50设置报警回调函数（V31、V30接口也支持，新接口兼容老接口），
        // 在SDK初始化之后即可以调用，多台设备对接时也只需要调用一次设置一个回调函数，回调函数里面接收数据之后可以通过报警设备信息(NET_DVR_ALARMER)中lUserID等参数判断区分设备。
        if (fMSFCallBack_V31 != null) return; // 防止重复注册

        fMSFCallBack_V31 = new CarCameraFMSGCallBack_V31();
        boolean ok = hCNetSDK.NET_DVR_SetDVRMessageCallBack_V31(
                fMSFCallBack_V31,
                null
        );

        if (!ok) {
            logger.error("❌ 设置报警回调函数失败, 错误码={}", hCNetSDK.NET_DVR_GetLastError());
        } else {
            logger.info("✅ 报警回调函数设置成功");
        }
    }

    /**
     * 设置 SDK 本地配置
     * ------------------------------------------------
     * byAlarmJsonPictureSeparate = 1 表示 JSON 和图片数据分离上传
     * 这样回调类型为 COMM_ISAPI_ALARM(0x6009)，
     * 报警结构为 NET_DVR_ALARM_ISAPI_INFO，更易解析。
     */
    private void setupLocalJsonConfig() {
        /** 设备上传的报警信息是COMM_VCA_ALARM(0x4993)类型，
         在SDK初始化之后增加调用NET_DVR_SetSDKLocalCfg(enumType为NET_DVR_LOCAL_CFG_TYPE_GENERAL)设置通用参数NET_DVR_LOCAL_GENERAL_CFG的byAlarmJsonPictureSeparate为1，
         将Json数据和图片数据分离上传，这样设置之后，报警布防回调函数里面接收到的报警信息类型为COMM_ISAPI_ALARM(0x6009)，
         报警信息结构体为NET_DVR_ALARM_ISAPI_INFO（与设备无关，SDK封装的数据结构），更便于解析。*/

        HCNetSDK.NET_DVR_LOCAL_GENERAL_CFG cfg = new HCNetSDK.NET_DVR_LOCAL_GENERAL_CFG();
        cfg.byAlarmJsonPictureSeparate = 1;   //设置JSON透传报警数据和图片分离
        cfg.write();
        hCNetSDK.NET_DVR_SetSDKLocalCfg(17, cfg.getPointer()); // 17 通用参数配置
    }

    /**
     * 设置连接时间与重连策略
     */
    private void setupConnectionConfig() {
        hCNetSDK.NET_DVR_SetConnectTime(2000, 1);
        hCNetSDK.NET_DVR_SetReconnect(10000, true);
    }

    /**
     * 登录设备，支持 V40 和 V30 版本，功能一致。
     *
     * @param ip   设备IP地址
     * @param port SDK端口
     * @param user 设备用户名
     * @param psw  设备密码
     * @return 登录成功返回用户Session
     */
    public DeviceSession loginDevice(String ip, short port, String user, String psw) {
        // 先查 map，避免重复登录
        DeviceSession existing = deviceMap.get(ip);
        if (existing != null) {
            logger.debug("设备 [{}] 已登录，返回现有会话", ip);
            return existing;
        }

        synchronized (this) { // 双重检查锁，防止并发登录
            existing = deviceMap.get(ip);
            if (existing != null) {
                return existing;
            }

            HCNetSDK.NET_DVR_USER_LOGIN_INFO loginInfo = new HCNetSDK.NET_DVR_USER_LOGIN_INFO();
            HCNetSDK.NET_DVR_DEVICEINFO_V40 deviceInfo = new HCNetSDK.NET_DVR_DEVICEINFO_V40();

            // 设置设备IP地址
            byte[] deviceAddress = new byte[HCNetSDK.NET_DVR_DEV_ADDRESS_MAX_LEN];
            byte[] ipBytes = ip.getBytes();
            System.arraycopy(ipBytes, 0, deviceAddress, 0, Math.min(ipBytes.length, deviceAddress.length));
            loginInfo.sDeviceAddress = deviceAddress;

            // 设置用户名
            byte[] userName = new byte[HCNetSDK.NET_DVR_LOGIN_USERNAME_MAX_LEN];
            System.arraycopy(user.getBytes(), 0, userName, 0, Math.min(user.length(), userName.length));
            loginInfo.sUserName = userName;

            // 设置密码
            byte[] pwd = psw.getBytes();
            System.arraycopy(pwd, 0, loginInfo.sPassword, 0, Math.min(pwd.length, loginInfo.sPassword.length));

            // 设置端口和登录模式
            loginInfo.wPort = port;
            loginInfo.bUseAsynLogin = false; // 同步登录 // 是否异步登录：0- 否，1- 是
            loginInfo.byLoginMode = 0;

            // 执行登录操作
            int userID = hCNetSDK.NET_DVR_Login_V40(
                    loginInfo, // pLoginInfo[in] 登录参数，包括设备地址、登录用户、密码等
                    deviceInfo // lpDeviceInfo[out] 设备信息(同步登录即pLoginInfo中bUseAsynLogin为0时有效)
            );
            if (userID < 0) {
                int errCode = hCNetSDK.NET_DVR_GetLastError();
                logger.error("❌ 设备登录失败 [{}], 错误码={}", ip, errCode);
                return null;
            }

            logger.info("✅ 设备登录成功 [{}], userID={}", ip, userID);
            Device device = deviceService.getByIp(ip);
            DeviceSession session = new DeviceSession(ip, userID, SdkType.CAR_CAMERA, device, loginInfo, deviceInfo, -1);
            deviceMap.put(ip, session);
            return session;
        }
    }

    /**
     * 启用布防（报警上传）
     * ------------------------------------------------
     * 若设备已布防则先关闭旧通道再重新布防。
     */
    private void setupAlarm(int userID, String deviceIp) {
        if (hCNetSDK == null) {
            logger.error("SDK未初始化");
            return;
        }

        // 如果该设备已经布防，先关闭旧的布防通道
        DeviceSession session = deviceMap.get(deviceIp);
        if (session != null && session.getAlarmHandle() >= 0) {
            hCNetSDK.NET_DVR_CloseAlarmChan_V30(session.getAlarmHandle());
            session.setAlarmHandle(-1); // 清空旧句柄
            logger.info("设备[{}] 关闭旧布防通道，句柄={}", deviceIp, session.getAlarmHandle());
        }

        // 报警布防参数结构体。
        HCNetSDK.NET_DVR_SETUPALARM_PARAM lpSetupParam = new HCNetSDK.NET_DVR_SETUPALARM_PARAM();
        lpSetupParam.dwSize = lpSetupParam.size(); // 结构体大小
        lpSetupParam.byLevel = 1;                  // 布防优先级：0- 一等级（高），1- 二等级（中）
        lpSetupParam.byAlarmInfoType = 1;          // 智能交通报警信息上传类型：0- 老报警信息（NET_DVR_PLATE_RESULT），1- 新报警信息(NET_ITS_PLATE_RESULT)
        lpSetupParam.byRetAlarmTypeV40 = 1;        // 0- 移动侦测、视频丢失、遮挡、IO信号量等报警信息以普通方式上传（报警类型：COMM_ALARM_V30，报警信息结构体：NET_DVR_ALARMINFO_V30），1- 报警信息以数据可变长方式上传（报警类型：COMM_ALARM_V40，报警信息结构体：NET_DVR_ALARMINFO_V40，设备若不支持则仍以普通方式上传）
        lpSetupParam.byRetDevInfoVersion = 1;      // CVR上传报警信息类型(仅对接CVR时有效)：// 0- COMM_ALARM_DEVICE（对应报警信息结构体：NET_DVR_ALARMINFO_DEV），1- COMM_ALARM_DEVICE_V40（对应报警信息结构体：NET_DVR_ALARMINFO_DEV_V40）
        lpSetupParam.byDeployType = 0;             // 布防类型：0-客户端布防，1-实时布防
        lpSetupParam.write();                      // 写入内存
        // alarmParam.byLevel 布防优先级：0- 一等级（高），1- 二等级（中），2- 三等级（低）
        // alarmParam.byAlarmInfoType 智能交通报警信息上传类型：0- 老报警信息（NET_DVR_PLATE_RESULT），1- 新报警信息(NET_ITS_PLATE_RESULT)
        // alarmParam.byRetAlarmTypeV40  0- 移动侦测、视频丢失、遮挡、IO信号量等报警信息以普通方式上传（报警类型：COMM_ALARM_V30，报警信息结构体：NET_DVR_ALARMINFO_V30），1- 报警信息以数据可变长方式上传（报警类型：COMM_ALARM_V40，报警信息结构体：NET_DVR_ALARMINFO_V40，设备若不支持则仍以普通方式上传）
        // alarmParam.byRetDevInfoVersion  CVR上传报警信息类型(仅对接CVR时有效)：0- COMM_ALARM_DEVICE（对应报警信息结构体：NET_DVR_ALARMINFO_DEV），1- COMM_ALARM_DEVICE_V40（对应报警信息结构体：NET_DVR_ALARMINFO_DEV_V40）
        // alarmParam.byRetVQDAlarmType  VQD报警上传类型(仅对接VQD诊断功能的设备有效)：0- COMM_ALARM_VQD（对应报警信息结构体：NET_DVR_VQD_DIAGNOSE_INFO），1- COMM_ALARM_VQD_EX（对应报警信息结构体：NET_DVR_VQD_ALARM，包含前端设备信息和抓拍图片）
        // alarmParam.byFaceAlarmDetection  人脸报警信息类型：1- 人脸侦测报警(报警类型：COMM_ALARM_FACE_DETECTION，NET_DVR_FACE_DETECTION)，0- 人脸抓拍报警(报警类型：COMM_UPLOAD_FACESNAP_RESULT，NET_VCA_FACESNAP_RESULT)
        // alarmParam.bySupport  按位表示，每一位取值表示不同的能力
        // bit0- 表示二级布防是否上传图片，值：0-上传，1-不上传
        // Bit1- 表示是否启用断网重传数据确认机制，值：0-不开启，1-开启
        // alarmParam.byBrokenNetHttp  断网重传类型（设备目前只支持一个断网重传布防连接），按位表示，值：0- 不重传，1- 重传
        // bit0- 车牌检测（IPC）
        // bit1- 客流统计（IPC）
        // bit2- 热度图统计（IPC）
        // bit3- 人脸抓拍（IPC）
        // bit4- 人脸对比（IPC）
        // bit5- JSON报警透传（IPC）
        // 例如：byBrokenNetHttp&0x1==0 表示车牌检测结果不重传
        // alarmParam.wTaskNo  任务处理号
        // alarmParam.byRes1  保留，置为0
        // alarmParam.byAlarmTypeURL  报警图片数据类型，按位表示：
        // bit0- 人脸抓拍(报警类型为COMM_UPLOAD_FACESNAP_RESULT)中图片数据上传类型：0- 二进制传输，1- URL传输
        // bit1- EVENT_JSON(报警类型为COMM_VCA_ALARM)中图片数据上传类型：0- 二进制传输，1- URL传输
        // bit2- 人脸比对(报警类型为COMM_SNAP_MATCH_ALARM)中图片数据上传类型：0- 二进制传输，1- URL传输
        // 如果设备同时支持URL和二进制传输方式，可以布防的时候通过该参数指定上传的数据格式（二进制或者URL），选择URL传输方式时需要设备配置和启用云存储，否则仍默认以二进制数据格式传输。如果设备只支持URL方式，该参数赋值无效。
        // alarmParam.byCustomCtrl  按位表示，bit0表示是否上传副驾驶人脸子图: 0- 不上传，1- 上传

        // 调用布防接口 // 启用布防 // 建立报警上传通道，获取报警等信息。
        int alarmHandle = hCNetSDK.NET_DVR_SetupAlarmChan_V41(
                userID, // lUserID [in] NET_DVR_Login或者NET_DVR_Login_V30的返回值
                lpSetupParam // lpSetupParam [in] 报警布防参数
        );
        // -1表示失败，其他值作为NET_DVR_CloseAlarmChan_V30函数的句柄参数。接口返回失败请调用NET_DVR_GetLastError获取错误码，通过错误码判断出错原因。
        if (alarmHandle < 0) {
            int err = hCNetSDK.NET_DVR_GetLastError();
            logger.error("设备[{}] 布防失败，错误码={}", deviceIp, err);
            return;
        }

        // 保存布防句柄到对应的 DeviceSession
        session = deviceMap.get(deviceIp);
        if (session != null) {
            session.setAlarmHandle(alarmHandle);
            logger.info("设备[{}] 布防成功，alarmHandle={}", deviceIp, alarmHandle);
        } else {
            logger.warn("设备[{}] 未找到对应Session，无法保存布防句柄", deviceIp);
            hCNetSDK.NET_DVR_CloseAlarmChan_V30(alarmHandle); // 避免句柄泄露
        }

//        logger.info("布防成功，开始抓拍...");
//        // 手动抓拍（使用三参数接口）
//        HCNetSDK.NET_DVR_MANUALSNAP snapCfg = new HCNetSDK.NET_DVR_MANUALSNAP();
////                snapCfg.dwSize = snapCfg.size();
////                snapCfg.bySnapTimes = 1;
////                snapCfg.byTriggerMode = 1;
////                snapCfg.byJointSnap = 0;
//
//        HCNetSDK.NET_DVR_PLATE_RESULT plateResult = new HCNetSDK.NET_DVR_PLATE_RESULT();
//
//        boolean triggered = hCNetSDK.NET_DVR_ManualSnap(userID, snapCfg, plateResult);
//
//        if (triggered) {
//            license = new String(plateResult.struPlateInfo.sLicense, Charset.forName("GBK")).trim();
//            logger.info("识别到车牌号：" + license);
//        } else {
//            logger.error("手动抓拍失败，错误码: " + hCNetSDK.NET_DVR_GetLastError());
//        }
    }

    /**
     * 开启监听
     *
     * @param ip   监听IP
     * @param port 监听端口
     */
    public int startListen(String ip, short port) {
        if (lListenHandle <= 0) {
            if (fMSFCallBack == null) {
                fMSFCallBack = new FMSGCallBack();
            }
            lListenHandle = hCNetSDK.NET_DVR_StartListen_V30(ip, port, fMSFCallBack, null);
            if (lListenHandle == -1) {
                logger.info("监听失败" + hCNetSDK.NET_DVR_GetLastError());
                return -1;
            } else {
                logger.info("监听成功");
                return lListenHandle;
            }
        } else {
            logger.info("监听已经开启，请先停止监听！");
            return lListenHandle;
        }
    }

    /**
     * 停止监听
     *
     * @param Handle 监听句柄
     */
    public void stopListen(int Handle) {
        if (Handle <= -1) {
            logger.info("监听未开启");
            return;
        }
        if (!hCNetSDK.NET_DVR_StopListen_V30(Handle)) {
            System.err.println("停止监听失败，err: " + hCNetSDK.NET_DVR_GetLastError());
            return;
        }
        logger.info("停止监听成功");
    }

    /**
     * 退出程序时注销所有设备
     * ------------------------------------------------
     * SDK 的 Cleanup 在 HikSDKManager 中统一执行。
     */
    @PreDestroy
    public synchronized void logoutAll() {
        deviceMap.values().forEach(session -> {
            if (session.getUserID() >= 0) {
                boolean result = hCNetSDK.NET_DVR_Logout(session.getUserID());
                if (result) {
                    logger.info("注销设备 [{}] 成功", session.getIp());
                } else {
                    int err = hCNetSDK.NET_DVR_GetLastError();
                    logger.error("注销设备 [{}] 失败, 错误码={}", session.getIp(), err);
                }
            }
        });
        deviceMap.clear();
    }

    /**
     * 获取当前设备 Session
     */
    public DeviceSession getDeviceSession(String ip) {
        return deviceMap.get(ip);
    }

}
