package com.supervision.web.Hikvision.carDoor.web.util;

import com.supervision.web.Hikvision.carDoor.connectSDK.NetSDKDemo.HCNetSDK;
import com.supervision.web.Hikvision.carDoor.connectSDK.hik.BarrierGateManage;
import com.supervision.web.Hikvision.carDoor.connectSDK.hik.LCDdisplayManage;
import com.supervision.web.Hikvision.carDoor.connectSDK.hik.VechileListManage;
import com.supervision.web.Hikvision.carDoor.connectSDK.hik.VoiceManage;
import com.supervision.web.Hikvision.carDoor.web.entity.NameList;
import com.supervision.web.Hikvision.util.DeviceSession;
import com.supervision.web.Hikvision.util.HikSDKManager;
import com.supervision.web.Hikvision.util.SdkType;
import com.supervision.web.Hikvision.carDoor.web.config.HkCameraProperty;
import com.supervision.web.Hikvision.peopleDoor.web.entity.Device;
import com.supervision.web.Hikvision.peopleDoor.web.service.DeviceService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CarDoorDeviceManager {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(CarDoorDeviceManager.class);

    @Autowired
    private HikSDKManager hikSDKManager;

    private HCNetSDK hCNetSDK;

    @Autowired
    private VechileListManage vechileListManage;

    @Autowired
    private BarrierGateManage barrierGateManage;

    @Autowired
    private VoiceManage voiceManage;

    @Autowired
    private LCDdisplayManage lCDdisplayManage;

    @Autowired
    private DeviceService deviceService;

    /** key: 设备IP, value: 登录会话信息 */
    private final Map<String, DeviceSession> deviceMap = new ConcurrentHashMap<>();

    @Autowired
    private HkCameraProperty hkCameraProperty;

    public Map<String, DeviceSession> getDeviceMap() {
        return deviceMap;
    }

    @PostConstruct
    public void init() {
        logger.info("🚀 开始初始化车闸设备");
        try {
            // 1. 初始化 SDK
            if (!initSDK()) {
                logger.error("❌ SDK 初始化失败，终止初始化流程");
                return;
            }

            // 2. 加载门禁设备列表
            Map<String, Object> params = new HashMap<>();
            params.put("deviceName", null);
            params.put("deviceType", "carDoor");
            params.put("enable", true);
            params.put("paginate", false);
            List<Device> devices = (List<Device>) deviceService.searchByCondition(params);
            if (devices == null || devices.isEmpty()) {
                logger.warn("⚠️ 未找到可用的车闸设备，初始化结束");
                return;
            }

            // 4. 登录所有门禁设备
            for (Device device : devices) {
                DeviceSession session = loginDevice(
                        device.getIp(),
                        device.getPort().shortValue(),
                        device.getAccount(),
                        device.getPassword()
                );

                if (session == null) {
                    logger.warn("⚠️ 车闸设备登录失败: {}", device.getIp());
                    continue;
                }

                logger.info("✅ 车闸设备登录成功: {} (userID={})", device.getIp(), session.getUserID());
            }

            logger.info("✅ 车闸设备初始化流程完成");
        } catch (Exception e) {
            logger.error("❌ 车闸设备初始化异常", e);
        }
    }

    /**
     * 初始化 CarDoor SDK
     */
    private boolean initSDK() {
        hikSDKManager.initSDK(SdkType.CAR_DOOR);
        hCNetSDK = hikSDKManager.getCarDoorSDK(); // 类型安全获取
        if (hCNetSDK == null) {
            return false;
        }
        logger.info("✅ CarDoor HCNetSDK 初始化成功");
        return true;
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

            // 创建设备登录信息和设备信息对象
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
            byte[] password = psw.getBytes();
            System.arraycopy(password, 0, loginInfo.sPassword, 0, Math.min(password.length, loginInfo.sPassword.length));

            // 设置端口和登录模式
            loginInfo.wPort = port;
            loginInfo.bUseAsynLogin = false; // 同步登录
            loginInfo.byLoginMode = 0; // 使用SDK私有协议

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
            DeviceSession session = new DeviceSession(ip, userID, SdkType.CAR_DOOR, device, loginInfo, deviceInfo, -1);
            deviceMap.put(ip, session);
            return session;
        }
    }

    /**
     * 获取一个有效的设备会话，如果失效则自动重连
     */
    public DeviceSession getValidSession(String ip) {
        DeviceSession session = deviceMap.get(ip);

        // 1. 如果没有登录过，直接去登录
        if (session == null) {
            Device device = deviceService.getByIp(ip); // 根据IP查数据库获取账号密码
            return loginDevice(ip, device.getPort().shortValue(), device.getAccount(), device.getPassword());
        }

        // 2. 如果登录过，检查句柄是否真的还有效 (保活测试)
        HCNetSDK.NET_DVR_TIME time = new HCNetSDK.NET_DVR_TIME();
        com.sun.jna.ptr.IntByReference lpBytesReturned = new com.sun.jna.ptr.IntByReference();
        boolean isAlive = hCNetSDK.NET_DVR_GetDVRConfig(session.getUserID(), HCNetSDK.NET_DVR_GET_TIMECFG, 0, time.getPointer(), time.size(), lpBytesReturned);

        if (!isAlive) {
            logger.warn("检测到设备 [{}] 句柄 {} 已失效，准备注销并重连...", ip, session.getUserID());
            // 清理旧的
            hCNetSDK.NET_DVR_Logout(session.getUserID());
            deviceMap.remove(ip);

            // 重新登录
            Device device = deviceService.getByIp(ip);
            return loginDevice(ip, device.getPort().shortValue(), device.getAccount(), device.getPassword());
        }

        return session;
    }

    // 远程控门
    public boolean BarrierGateCtrl(int userID, Integer gatewayIndex, Integer command) {
        return barrierGateManage.BarrierGateCtrl(hCNetSDK, userID, gatewayIndex, command);
    }

    /**
     * 获取设备 session
     */
    public DeviceSession getDeviceSession(String ip) {
        return deviceMap.get(ip);
    }

    /**
     * 获取车辆授权列表
     */
    public Map<String, Object> getVechileList(int userID, Map<String, Object> condition) {
        return vechileListManage.getVechileList(hCNetSDK, userID, condition);
    }

    /**
     * 添加车辆授权列表
     */
    public Map<String, Object> addVechileList(int userID, List<NameList> nameLists) {
        return vechileListManage.addVechileList(hCNetSDK, userID, nameLists);
    }

    /**
     * 添加车辆授权列表
     */
    public Map<String, Object> updateVechileList(int userID, List<NameList> nameLists) {
        return vechileListManage.updateVechileList(hCNetSDK, userID, nameLists);
    }

    /**
     * 删除车辆授权列表
     */
    public Map<String, Object> deleteVechileList(int userID, List<NameList> nameLists) {
        return vechileListManage.deleteVechileList(hCNetSDK, userID, nameLists);
    }

//        /**
//         * 海康全彩摄像头拍照
//         */
//        public List<String> takePhoto () {
//            List<String> imgList = Lists.newArrayList();
//            HkCameraProperty property;
//            List<HkCameraParam> cameraList = hkCameraProperty.getCamera();
//            for (HkCameraParam camera : cameraList) {
//                long start = System.currentTimeMillis();
//                String img = this.takePhoto(camera.getDeviceIp(),
//                        camera.getUsername(), camera.getPassword(), camera.getPort());
//                if (StrUtil.isNotBlank(img)) {
//                    imgList.add(img);
//                }
//                logger.info("【海康全彩摄像头】ip: {}, 拍照用时: {}ms", camera.getDeviceIp(),
//                        (System.currentTimeMillis() - start));
//            }
//            return imgList;
//        }
//
//        /**
//         * 海康全彩摄像头拍照
//         */
//        public String takePhoto (String deviceIp, String username, String password, Short port){
//            // 设备信息
//            int userId = this.cameraInit(deviceIp, username, password, port);
//            if (userId < 0) {
//                return null;
//            }
//            // 写到本地
//            return this.writeFile(this.takePhoto(userId), deviceIp.substring(deviceIp.lastIndexOf(".") + 1));
//        }
//
//        /**
//         * 海康全彩摄像头拍照
//         */
//        private byte[] takePhoto ( int userId){
//            // JPEG图像参数
//            HCNetSDK.NET_DVR_JPEGPARA lpJpegPara = new HCNetSDK.NET_DVR_JPEGPARA();
//            // 设置图片的分辨率
//            lpJpegPara.wPicSize = 2;
//            // 设置图片质量
//            lpJpegPara.wPicQuality = 0;
//            // 通道号、输入缓冲区大小
//            int channel = 1, dwPicSize = 1024 * 1024;
//            // 保存JPEG数据的缓冲区
//            Pointer sJpegPicBuffer = new Memory(dwPicSize);
//            // 返回图片数据的大小
//            IntByReference reference = new IntByReference();
//            // 抓拍
//            hCNetSDK.NET_DVR_CaptureJPEGPicture_NEW(userId, channel, lpJpegPara, sJpegPicBuffer, dwPicSize, reference);
//            // 图片大小
//            int value = reference.getValue();
//            logger.info("【海康全彩摄像头】图片大小: {}", value);
//            // 图片byte
//            return sJpegPicBuffer.getByteArray(0, value);
//        }
//
//        /**
//         * 写到本地
//         */
//        private String writeFile ( byte[] bs, String suffix){
//            String filename = UUID.randomUUID() + "_" + suffix + ".jpg", path =
//                    hkCameraProperty.getImgPath() + filename;
//            File file = new File(path);
//            try (FileOutputStream fos = new FileOutputStream(file);
//                 BufferedOutputStream bos = new BufferedOutputStream(fos)) {
//                bos.write(bs);
//            } catch (Exception e) {
//                logger.error("【海康摄像头写入图片失败】", e);
//                return null;
//            }
//            return path;
//        }
//    }

}

