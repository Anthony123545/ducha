package com.supervision.web.Hikvision.carDoor.connectSDK.hik;

import com.supervision.web.Hikvision.carDoor.connectSDK.NetSDKDemo.HCNetSDK;
import com.sun.jna.Pointer;
import com.supervision.web.Hikvision.carDoor.web.entity.NameList;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.supervision.web.Hikvision.carDoor.web.util.CarDoorDeviceManager;
import com.supervision.web.Hikvision.util.DeviceSession;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;

/**
 * 车辆黑白名单管理，功能:黑白名单的上传、删除
 */
@Component
public class VechileListManage {

    private static final Logger logger = LoggerFactory.getLogger(VechileListManage.class);

    private FVehicleCrtlCB fVehicleCrtlCB = null;

    /** 远程配置状态（由 FVehicleCrtlCB 回调更新） */
    private volatile int iStatus = -1; // -1 表示未开始，1000/1002 表示成功完成

    @Autowired
    public ISAPI isapi;

    @Autowired
    private CarDoorDeviceManager carDoorDeviceManager;

    /**
     * 下发车辆名单（支持单条或批量）
     *
     * @param hCNetSDK  海康 SDK 实例
     * @param lUserID   登录句柄
     * @param nameLists 车辆名单列表，支持单条/批量
     * @return Map<String, Object> 包含下发状态、消息、每条明细、总数、成功数等
     */
    public Map<String, Object> addVechileList(HCNetSDK hCNetSDK, int lUserID, List<NameList> nameLists) {
        Map<String, Object> res = new HashMap<>();
        List<Map<String, Object>> details = new ArrayList<>();
        try {

            // 1. 校验名单
            if (nameLists == null || nameLists.isEmpty()) {
                logger.warn("⚠️ 车辆名单为空，未执行下发");
                res.put("status", false);
                res.put("message", "车辆名单为空");
                res.put("details", details);
                res.put("totalCount", 0);
                res.put("successCount", 0);
                return res;
            }

            // 2. 初始化回调（如果未初始化）
            // ✅ 手动创建回调实例，并传入当前类（this），以便在回调中更新状态
            if (fVehicleCrtlCB == null) {
                fVehicleCrtlCB = new FVehicleCrtlCB(this); // ✅ 传入当前类，用于更新 iStatus
            }

            // 3. 启动远程配置（长连接）
            int lHandle = hCNetSDK.NET_DVR_StartRemoteConfig(
                    lUserID, // lUserID [in] NET_DVR_Login_V40等登录接口的返回值
                    HCNetSDK.NET_DVR_VEHICLELIST_CTRL_START, // dwCommand [in] 配置命令，不同的功能对应不同的命令号(dwCommand)，lpInBuffer等参数也对应不同的内容
                    // NET_DVR_VEHICLELIST_CTRL_START 设置车辆授权和非授权名单信息(批量) lpInBuffer 为 null
                    null, // [in] 输入参数，具体内容跟配置命令相关
                    0, // dwInBufferLen [in] 输入缓冲的大小
                    fVehicleCrtlCB, // cbStateCallback [in] 状态回调函数
                    Pointer.NULL // pUserData [in] 用户数据
            );
            // -1表示失败，其他值作为NET_DVR_GetNextRemoteConfig、NET_DVR_StopRemoteConfig的句柄。接口返回失败请调用NET_DVR_GetLastError获取错误码，通过错误码判断出错原因。
            if (lHandle <= -1) {
                int err = hCNetSDK.NET_DVR_GetLastError();
                String errorMsg = "NET_DVR_StartRemoteConfig 失败, errorCode=" + err;
                logger.error(errorMsg);
                res.put("status", false);
                res.put("message", errorMsg);
                res.put("details", details);
                res.put("totalCount", nameLists.size());
                res.put("successCount", 0);
                return res;
            }

            logger.info("✅ 启动车辆名单下发，总数：" + nameLists.size());

            // 4. 循环下发每一条记录
            for (int i = 0; i < nameLists.size(); i++) {
                NameList nameList = nameLists.get(i);
                HCNetSDK.NET_DVR_VEHICLE_CONTROL_LIST_INFO car = buildVehicleInfo(nameList);
                car.write();

                // 下发到设备
                boolean bSend = hCNetSDK.NET_DVR_SendRemoteConfig(
                        lHandle,
                        HCNetSDK.ENUM_SENDDATA,
                        car.getPointer(),
                        car.size()
                );

                Map<String, Object> detail = new HashMap<>();
                detail.put("index", i + 1);
                detail.put("licensePlate", nameList.getLicensePlate());
                detail.put("status", bSend);
                detail.put("message", bSend ? "下发成功" : ("失败，错误码：" + hCNetSDK.NET_DVR_GetLastError()));

                if (!bSend) {
                    int err = hCNetSDK.NET_DVR_GetLastError();
                    logger.error(String.format("❌ 第 %d 条下发失败（车牌：%s），错误码：%d", i + 1, nameList.getLicensePlate(), err));
                } else {
                    logger.info(String.format("✅ 第 %d 条下发成功（车牌：%s）", i + 1, nameList.getLicensePlate()));
                }

                details.add(detail);
                // 控制下发速度
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ignored) {}
            }

            // 5. 等待下发完成（通过 iStatus 判断）
            int timeoutMs = Math.min(30000 + nameLists.size() * 100, 60000);
            long startTime = System.currentTimeMillis();
            while (true) {
                if (iStatus == 1000 || iStatus == 1002) { // 假设 1000/1002 表示成功完成（根据海康文档确认）
                    logger.info("✅ 车辆名单下发完成，状态码：" + iStatus);
                    break;
                }
                if (System.currentTimeMillis() - startTime > timeoutMs) {
                    logger.warn("⏰ 车辆名单下发超时（" + timeoutMs + "ms），强制终止");
                    break;
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {}
            }

            // 6. 关闭远程配置连接
            if (!hCNetSDK.NET_DVR_StopRemoteConfig(lHandle)) {
                int err = hCNetSDK.NET_DVR_GetLastError();
                logger.warn("⚠️ 关闭远程配置连接失败，errorCode=" + err);
            } else {
                logger.info("🔚 远程配置连接已关闭");
            }
            // 7. 返回结果
            int successCount = (int) details.stream().filter(d -> Boolean.TRUE.equals(d.get("status"))).count();
            res.put("status", iStatus == 1000 || iStatus == 1002);
            res.put("message", "下发完成，状态码: " + iStatus);
            res.put("details", details);
            res.put("totalCount", nameLists.size());
            res.put("successCount", successCount);
        } catch (Exception e) {
            logger.error("车辆名单下发异常：", e);
            res.put("status", false);
            res.put("message", "下发异常: " + e.getMessage());
            res.put("details", details);
            res.put("totalCount", nameLists.size());
            res.put("successCount", 0);
        }
        return res;
    }


    /**
     * 更新车辆名单（支持单条或批量）
     *
     * @param hCNetSDK  海康 SDK 实例
     * @param lUserID   登录句柄
     * @param nameLists 车辆名单列表，支持单条/批量
     * @return Map<String, Object> 包含下发状态、消息、每条明细、总数、成功数等
     */
    public Map<String, Object> updateVechileList(HCNetSDK hCNetSDK, int lUserID, List<NameList> nameLists) {
        Map<String, Object> res = new HashMap<>();
        List<Map<String, Object>> details = new ArrayList<>();
        try {

            // 1. 校验名单
            if (nameLists == null || nameLists.isEmpty()) {
                logger.warn("⚠️ 车辆名单为空，未执行下发");
                res.put("status", false);
                res.put("message", "车辆名单为空");
                res.put("details", details);
                res.put("totalCount", 0);
                res.put("successCount", 0);
                return res;
            }

            // 2. 初始化回调（如果未初始化）
            // ✅ 手动创建回调实例，并传入当前类（this），以便在回调中更新状态
            if (fVehicleCrtlCB == null) {
                fVehicleCrtlCB = new FVehicleCrtlCB(this); // ✅ 传入当前类，用于更新 iStatus
            }

            // 3. 启动远程配置（长连接）
            int lHandle = hCNetSDK.NET_DVR_StartRemoteConfig(
                    lUserID, // lUserID [in] NET_DVR_Login_V40等登录接口的返回值
                    HCNetSDK.NET_DVR_VEHICLELIST_CTRL_START, // dwCommand [in] 配置命令，不同的功能对应不同的命令号(dwCommand)，lpInBuffer等参数也对应不同的内容
                    // NET_DVR_VEHICLELIST_CTRL_START 设置车辆授权和非授权名单信息(批量) lpInBuffer 为 null
                    null, // [in] 输入参数，具体内容跟配置命令相关
                    0, // dwInBufferLen [in] 输入缓冲的大小
                    fVehicleCrtlCB, // cbStateCallback [in] 状态回调函数
                    Pointer.NULL // pUserData [in] 用户数据
            );
            // -1表示失败，其他值作为NET_DVR_GetNextRemoteConfig、NET_DVR_StopRemoteConfig的句柄。接口返回失败请调用NET_DVR_GetLastError获取错误码，通过错误码判断出错原因。
            if (lHandle <= -1) {
                int err = hCNetSDK.NET_DVR_GetLastError();
                String errorMsg = "NET_DVR_StartRemoteConfig 失败, errorCode=" + err;
                logger.error(errorMsg);
                res.put("status", false);
                res.put("message", errorMsg);
                res.put("details", details);
                res.put("totalCount", nameLists.size());
                res.put("successCount", 0);
                return res;
            }

            logger.info("✅ 启动车辆名单下发，总数：" + nameLists.size());

            // 4. 循环下发每一条记录
            for (int i = 0; i < nameLists.size(); i++) {
                NameList nameList = nameLists.get(i);

                String reqBody = buildDeleteVehicleJson(nameList);
                reqBody = reqBody.replaceAll("\\s+", "");
                String resp = isapi.stdJSONConfig(hCNetSDK, lUserID, "", reqBody);
                logger.debug(" 删除返回json：{}", resp);

                HCNetSDK.NET_DVR_VEHICLE_CONTROL_LIST_INFO car = buildVehicleInfo(nameList);
                car.write();

                // 下发到设备
                boolean bSend = hCNetSDK.NET_DVR_SendRemoteConfig(
                        lHandle,
                        HCNetSDK.ENUM_SENDDATA,
                        car.getPointer(),
                        car.size()
                );

                Map<String, Object> detail = new HashMap<>();
                detail.put("index", i + 1);
                detail.put("licensePlate", nameList.getLicensePlate());
                detail.put("status", bSend);
                detail.put("message", bSend ? "下发成功" : ("失败，错误码：" + hCNetSDK.NET_DVR_GetLastError()));

                if (!bSend) {
                    int err = hCNetSDK.NET_DVR_GetLastError();
                    logger.error(String.format("❌ 第 %d 条下发失败（车牌：%s），错误码：%d", i + 1, nameList.getLicensePlate(), err));
                } else {
                    logger.info(String.format("✅ 第 %d 条下发成功（车牌：%s）", i + 1, nameList.getLicensePlate()));
                }

                details.add(detail);
                // 控制下发速度
                try {
                    Thread.sleep(50);
                } catch (InterruptedException ignored) {}
            }

            // 5. 等待下发完成（通过 iStatus 判断）
            int timeoutMs = Math.min(30000 + nameLists.size() * 100, 60000);
            long startTime = System.currentTimeMillis();
            while (true) {
                if (iStatus == 1000 || iStatus == 1002) { // 假设 1000/1002 表示成功完成（根据海康文档确认）
                    logger.info("✅ 车辆名单下发完成，状态码：" + iStatus);
                    break;
                }
                if (System.currentTimeMillis() - startTime > timeoutMs) {
                    logger.warn("⏰ 车辆名单下发超时（" + timeoutMs + "ms），强制终止");
                    break;
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {}
            }

            // 6. 关闭远程配置连接
            if (!hCNetSDK.NET_DVR_StopRemoteConfig(lHandle)) {
                int err = hCNetSDK.NET_DVR_GetLastError();
                logger.warn("⚠️ 关闭远程配置连接失败，errorCode=" + err);
            } else {
                logger.info("🔚 远程配置连接已关闭");
            }
            // 7. 返回结果
            int successCount = (int) details.stream().filter(d -> Boolean.TRUE.equals(d.get("status"))).count();
            res.put("status", iStatus == 1000 || iStatus == 1002);
            res.put("message", "下发完成，状态码: " + iStatus);
            res.put("details", details);
            res.put("totalCount", nameLists.size());
            res.put("successCount", successCount);
        } catch (Exception e) {
            logger.error("车辆名单下发异常：", e);
            res.put("status", false);
            res.put("message", "下发异常: " + e.getMessage());
            res.put("details", details);
            res.put("totalCount", nameLists.size());
            res.put("successCount", 0);
        }
        return res;
    }

    /**
     * 构建车辆名单结构体
     * 将业务对象 NameList 转为 SDK 所需结构体 NET_DVR_VEHICLE_CONTROL_LIST_INFO
     */
    private HCNetSDK.NET_DVR_VEHICLE_CONTROL_LIST_INFO buildVehicleInfo(NameList nameList) throws Exception {
        HCNetSDK.NET_DVR_VEHICLE_CONTROL_LIST_INFO car = new HCNetSDK.NET_DVR_VEHICLE_CONTROL_LIST_INFO();
        car.read(); // 初始化结构体（必须调用）

        // --- 基本属性 ---
        car.dwSize = car.size();
        car.dwChannel = (nameList.getChannel() != null ? nameList.getChannel() : 1); // 通道号，默认1  // 出入口抓拍机，一体机默认1，0xffffffff表示全部通道
//        car.dwDataIndex = (nameList.getDataIndex() != null ? nameList.getDataIndex() : 0); // 数据流水号，SDK会自动维护

        // --- 车牌号（16字节 GBK 编码）--- // 车牌号，固定16字节，GBK编码，不够补0，多余截断 // 车牌号码，最大16字节，GBK编码
        if (nameList.getLicensePlate() != null) {
            byte[] tmp = nameList.getLicensePlate().getBytes("GBK");
            System.arraycopy(tmp, 0, car.sLicense, 0, Math.min(tmp.length, car.sLicense.length));
        }

        // --- 名单类型 ---
        car.byListType = (byte) (nameList.getType() != null ? nameList.getType() : 1); // 名单类型：0-授权，1-非授权

        // --- 车牌类型/颜色 ---
        car.byPlateType = (byte) (nameList.getPlateType() != null ? nameList.getPlateType() : 0);  // 车牌类型，0-标准民用车（参考 SDK 枚举 VCA_PLATE_TYPE）
        car.byPlateColor = (byte) (nameList.getPlateColor() != null ? nameList.getPlateColor() : 0); // 车牌颜色，0-蓝色，1-黄色，2-白色，3-黑色，4-绿色，5-民航黑，0xff-其他
        Arrays.fill(car.byRes, (byte) 0); // 保留字段，填0

        // --- 卡号信息（48字节 GBK 编码）--- // 卡号信息，固定48字节，GBK编码 // 卡号信息，可选，置0
        if (nameList.getCardNo() != null) {
            byte[] tmp = nameList.getCardNo().getBytes("GBK");
            System.arraycopy(tmp, 0, car.sCardNo, 0, Math.min(tmp.length, car.sCardNo.length));
        }

        // --- 有效期  ---
        Calendar cal = Calendar.getInstance();
        // 开始时间
        if (nameList.getAccessStartTime() != null) {
            cal.setTime(nameList.getAccessStartTime());
        } else {
            cal.set(2025, Calendar.JANUARY, 1, 0, 0, 0);
        }
        car.struStartTime.wYear = (short) cal.get(Calendar.YEAR);
        car.struStartTime.byMonth = (byte) (cal.get(Calendar.MONTH) + 1);
        car.struStartTime.byDay = (byte) cal.get(Calendar.DAY_OF_MONTH);
        car.struStartTime.byHour = (byte) cal.get(Calendar.HOUR_OF_DAY);
        car.struStartTime.byMinute = (byte) cal.get(Calendar.MINUTE);
        car.struStartTime.bySecond = (byte) cal.get(Calendar.SECOND);

        // 结束时间
        if (nameList.getAccessEndTime() != null) {
            cal.setTime(nameList.getAccessEndTime());
        } else {
            cal.set(2099, Calendar.DECEMBER, 31, 23, 59, 59); // 默认长期有效
        }
        car.struStopTime.wYear = (short) cal.get(Calendar.YEAR);
        car.struStopTime.byMonth = (byte) (cal.get(Calendar.MONTH) + 1);
        car.struStopTime.byDay = (byte) cal.get(Calendar.DAY_OF_MONTH);
        car.struStopTime.byHour = (byte) cal.get(Calendar.HOUR_OF_DAY);
        car.struStopTime.byMinute = (byte) cal.get(Calendar.MINUTE);
        car.struStopTime.bySecond = (byte) cal.get(Calendar.SECOND);

        // --- 保留字段 ---
        Arrays.fill(car.byRes1, (byte) 0);

        car.write(); // 写入内存以供 SendRemoteConfig 使用 // 写入内存，准备下发
        return car;
    }


    /**
     * 下发车牌授权名单状态回调函数
     */
    static class FVehicleCrtlCB implements HCNetSDK.FRemoteConfigCallBack {

//        private int iStatus;

        private final VechileListManage outer; // 保存外部类引用

        // 构造函数传入当前类（即 VechileListManage）
        public FVehicleCrtlCB(VechileListManage outer) {
            this.outer = outer;
        }

        @Override
        public void invoke(
                int dwType, // dwType [out] 状态
                Pointer lpBuffer, // lpBuffer [out] 存放数据的缓冲区指针，具体内容跟dwType相关
                int dwBufLen, // dwBufLen [out] 缓冲区大小
                Pointer pUserData // pUserData [out] 用户数据
        ) {
            if (dwType != HCNetSDK.NET_SDK_CALLBACK_TYPE_STATUS) {
                return;
            }
            // 关键修正：直接取4字节状态码
            int status = lpBuffer.getInt(0);
            outer.iStatus = status;

            switch (dwType) {
                case HCNetSDK.NET_SDK_CALLBACK_TYPE_STATUS:

                    // 读取 SDK 返回的状态字节
                    HCNetSDK.BYTE_ARRAY struCallbackStatus = new HCNetSDK.BYTE_ARRAY(40);
                    struCallbackStatus.write();
                    Pointer pStatus = struCallbackStatus.getPointer();
                    pStatus.write(0, lpBuffer.getByteArray(0, struCallbackStatus.size()), 0, dwBufLen);
                    struCallbackStatus.read();

                    // 将前4个字节解析为状态值（小端序）
//                    int status = 0;
                    for (int i = 0; i < 4; i++) {
                        status |= (struCallbackStatus.byValue[i] & 0xff) << (8 * i);
                    }
                    outer.iStatus = status;

                    switch (outer.iStatus) {
                        case HCNetSDK.NET_SDK_CALLBACK_STATUS_SUCCESS:
                            logger.info("下发成功");
                            // 增加消息事件，查询成功之后调用NET_DVR_StopRemoteConfig释放资源
                            break;
                        case HCNetSDK.NET_SDK_CALLBACK_STATUS_PROCESSING:
                            logger.info("下发中...");
                            break;
                        case HCNetSDK.NET_SDK_CALLBACK_STATUS_FAILED:
                            logger.info("下发失败,错误码： dwStatus:" + outer.iStatus);
                            // 增加消息事件，查询失败之后调用NET_DVR_StopRemoteConfig释放资源
                            break;
                        default:
                            logger.warn("⚠️ 未知状态：" + outer.iStatus);
                            break;
                    }
                    break;
                default:
                    break;
            }
        }
//        public int getStatus() {
//            return iStatus;
//        }

    }

//    public static class FVehicleCrtlCB implements HCNetSDK.FRemoteConfigCallBack {
//        @Override
//        public void invoke(int dwType, Pointer lpBuffer, int dwBufLen, Pointer pUserData) {
//            // dwType: 回调类型
//            // lpBuffer: 数据内容
//            // pUserData: 用户数据，用不到
//
//            if (dwType == HCNetSDK.NET_SDK_CALLBACK_TYPE_DATA) {
//                HCNetSDK.NET_DVR_VEHICLE_CONTROL_LIST_RESULT result =
//                        new HCNetSDK.NET_DVR_VEHICLE_CONTROL_LIST_RESULT();
//                result.write();
//                Pointer p = result.getPointer();
//                p.write(0, lpBuffer.getByteArray(0, result.size()), 0, result.size());
//                result.read();
//
//                // 更新状态（关键）
//                iStatus = result.dwStatus;
//
//                logger.info("回调信息 → status=" + result.dwStatus);
//            }
//            else if (dwType == HCNetSDK.NET_SDK_CALLBACK_TYPE_STATUS) {
//                int status = lpBuffer.getInt(0);
//                logger.info("回调状态 → " + status);
//                iStatus = status;
//            }
//        }
//    }

    /**
     * 查询车牌授权/非授权名单（通过 ISAPI 接口）
     *
     * @param hCNetSDK  海康 SDK 实例
     * @param lUserID   登录成功的设备句柄
     * @param condition 查询条件，支持 maxResults, startIndex, licensePlate, plateColor 等
     * @return 包含状态、消息、原始响应等的 Map 结构
     */
    public Map<String, Object> getVechileList(HCNetSDK hCNetSDK, int lUserID, Map<String, Object> condition) {
        Map<String, Object> res = new HashMap<>();
        try {
            // 获取前端传来的 ID
            String searchID = String.valueOf(condition.getOrDefault("searchID", "0"));

            // 如果是 "0"，说明是新查询，生成一个时间戳 ID
            if ("0".equals(searchID) || searchID.isEmpty()) {
                searchID = String.valueOf(System.currentTimeMillis() / 100);
            }

            // 这一步非常重要：要把这个 ID 放回 condition，以便 buildVehicleSearchXml 使用
            condition.put("searchID", searchID);
            // 1. 给设备一点“喘息”时间，防止点击分页过快导致任务堆积
            Thread.sleep(200);

            String reqBody = buildVehicleSearchXml(condition);

            // 2. 优先尝试不带 channels/1 的路径，这种路径在翻页时兼容性更好
            String url = "POST /ISAPI/Traffic/searchLPListAudit";

            String resp = "";
            try {
                resp = isapi.stdXMLConfig(hCNetSDK, lUserID, url, reqBody);
            } catch (Exception e) {
                // 如果报错，自动切换回带通道的路径
                url = "POST /ISAPI/Traffic/channels/1/searchLPListAudit";
                resp = isapi.stdXMLConfig(hCNetSDK, lUserID, url, reqBody);
            }

            if (resp == null || resp.isEmpty()) {
                res.put("status", false);
                res.put("message", "设备响应为空");
                return res;
            }

            // 3. 轮询逻辑 (翻页本质上也是一次全新的搜索)
            int retry = 0;
            Map<String, Object> parsed = parseVehicleResponse(resp);
            while (retry < 5) {
                String status = (String) parsed.getOrDefault("responseStatusStrg", "");
                List<?> list = (List<?>) parsed.get("nameLists");

                if (list != null && !list.isEmpty()) {
                    parsed.put("status", true);
                    parsed.put("searchID", searchID);
                    return parsed;
                }
                if ("completed".equalsIgnoreCase(status)) break;

                Thread.sleep(600);
                resp = isapi.stdXMLConfig(hCNetSDK, lUserID, url, reqBody);
                parsed = parseVehicleResponse(resp);
                retry++;
            }

            res = parsed;
            res.put("status", true);
            res.put("searchID", searchID);
        } catch (Exception e) {
            logger.error("❌ 分页查询异常: {}", e.getMessage());
            res.put("status", false);
            res.put("message", "设备连接繁忙，请稍后再试");
        }
        return res;
    }


    /**
     * 删除车辆授权/非授权名单（支持单个或批量）
     *
     * @param hCNetSDK  海康 SDK 实例
     * @param lUserID   登录成功的设备或平台句柄
     * @param nameLists 车牌名单列表，包含 licensePlate 和 plateColor
     * @return 操作结果，包含状态、消息和详细结果
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> deleteVechileList2(HCNetSDK hCNetSDK, int lUserID, List<NameList> nameLists) {
        Map<String, Object> res = new HashMap<>();
        List<Map<String, Object>> results = new ArrayList<>();
        try {
            if (nameLists == null || nameLists.isEmpty()) {
                logger.warn("⚠️ 删除名单为空，未执行下发");
                res.put("status", false);
                res.put("message", "删除名单为空");
                res.put("data", results);
                return res;
            }

            // ISAPI 接口路径（不要带 HTTP 方法和 format 参数！）
            String url = "/ISAPI/Traffic/channels/1/DelLicensePlateAuditData";
//            String url = "/ISAPI/Traffic/channels/1/LicensePlateAuditData";

            int successCount = 0;
            int failCount = 0;
            for (NameList nameList : nameLists) {
                Map<String, Object> result = new HashMap<>();
                result.put("licensePlate", nameList.getLicensePlate());
                result.put("plateColor", nameList.getPlateColor());

                try {

                    // 构建删除请求 XML
                    String reqBody = buildDeleteVehicleXml(nameList);
                    logger.info("🗑 删除车牌请求报文(XML):\n{}", reqBody);

                    // 调用 ISAPI XML 接口
                    String resp = isapi.stdXMLConfig(hCNetSDK, lUserID, url, reqBody);
                    logger.info("📦 删除车牌返回报文(XML):\n{}", resp);

                    // 解析响应判断是否成功
                    boolean deleteSuccess = parseDeleteResponse(resp);

                    result.put("status", deleteSuccess);
                    result.put("message", deleteSuccess ? "删除成功" : "删除失败");
                    result.put("response", resp);
                    result.put("requestTime", new Date());

                    if (deleteSuccess) {
                        successCount++;
                    } else {
                        failCount++;
                    }
                } catch (Exception e) {
                    logger.error("删除车牌 {} 异常: {}", nameList.getLicensePlate(), e.getMessage(), e);
                    result.put("status", false);
                    result.put("message", "删除异常: " + e.getMessage());
                    result.put("error", e.getMessage());
                    failCount++;
                }
                results.add(result);
            }
            res.put("status", true);
            res.put("message", String.format("批量删除完成，成功：%d，失败：%d", successCount, failCount));
            res.put("successCount", successCount);
            res.put("failCount", failCount);
            res.put("totalCount", nameLists.size());
            res.put("data", results);
        } catch (Exception e) {
            logger.error("删除车辆名单异常：{}", e.getMessage(), e);
            res.put("status", false);
            res.put("message", "删除车辆名单异常: " + e.getMessage());
            res.put("data", results);
        }
        return res;
    }


    //============================================以下为修改之后的deleteVechileList========================================================

    public Map<String, Object> deleteVechileList(HCNetSDK hCNetSDK, int lUserID, List<NameList> nameLists) {
        Map<String, Object> res = new HashMap<>();
        List<Map<String, Object>> results = new ArrayList<>();
        try {
            if (nameLists == null || nameLists.isEmpty()) {
                logger.warn("⚠️ 删除名单为空，未执行下发");
                res.put("status", false);
                res.put("message", "删除名单为空");
                res.put("data", results);
                return res;
            }

            // 官方接口URL（之前已修正）
            String url = "/ISAPI/Traffic/channels/1/DelLicensePlateAuditData";
            // 新增：拼接format=json参数（对应截图中的query参数）
            url += "?format=json";

            int successCount = 0;
            int failCount = 0;
            for (NameList nameList : nameLists) {
                Map<String, Object> result = new HashMap<>();
                String licensePlate = nameList.getLicensePlate();
                result.put("licensePlate", licensePlate);


                try {
                    // 必传参数校验（车牌号）
                    if (licensePlate == null || licensePlate.isEmpty()) {
                        result.put("status", false);
                        result.put("message", "删除失败：车牌号不能为空");
                        results.add(result);
                        failCount++;
                        continue;
                    }

                    // 构建JSON请求体（替换原XML逻辑）
                    String reqBody = buildDeleteVehicleJson(nameList);
                    // 写死的reqBody改为单行紧凑格式（去掉所有换行/空格）
//                    String reqBody = "{\"deleteAllEnabled\":false,\"CompoundCond\":{\"plateColor\":\"other\",\"licensePlate\":\"晋A77777\"}}";
                    logger.info("🗑 删除车牌[{}]请求报文(JSON):\n{}", licensePlate, reqBody);
                    reqBody = reqBody.replaceAll("\\s+", "");

                    // 调用ISAPI接口：确保请求头是application/json
                    // （注意：需修改isapi.stdXMLConfig方法，或新增stdJSONConfig方法，设置Content-Type为application/json）
                    String resp = isapi.stdJSONConfig(hCNetSDK, lUserID, "", reqBody);
                    logger.info("📦 删除车牌[{}]返回报文(JSON):\n{}", licensePlate, resp);

                    // 解析JSON响应判断是否成功（替换原XML解析）
//                    boolean deleteSuccess = parseDeleteJsonResponse(resp);
//
//                    result.put("status", deleteSuccess);
//                    result.put("message", deleteSuccess ? "删除成功" : "删除失败");
//                    result.put("response", resp);
//                    result.put("requestTime", new Date());
//
//                    if (deleteSuccess) {
//                        successCount++;
//                    } else {
//                        failCount++;
//                    }
                } catch (Exception e) {
                    logger.error("删除车牌 {} 异常: {}", licensePlate, e.getMessage(), e);
                    result.put("status", false);
                    result.put("message", "删除异常: " + e.getMessage());
                    result.put("error", e.getMessage());
                    failCount++;
                }
                results.add(result);
            }
            res.put("status", true);
            res.put("message", String.format("批量删除完成，成功：%d，失败：%d", successCount, failCount));
            res.put("successCount", successCount);
            res.put("failCount", failCount);
            res.put("totalCount", nameLists.size());
            res.put("data", results);
        } catch (Exception e) {
            logger.error("删除车辆名单异常：{}", e.getMessage(), e);
            res.put("status", false);
            res.put("message", "删除车辆名单异常: " + e.getMessage());
            res.put("data", results);
        }
        return res;
    }
    // 新增：构建官方要求的JSON请求体
    private String buildDeleteVehicleJson(NameList nameList) {
        try {
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("deleteAllEnabled", false);

            Map<String, Object> compoundCond = new HashMap<>();
            // 车牌号（必填）
            if (nameList.getLicensePlate() != null && !nameList.getLicensePlate().isEmpty()) {
                compoundCond.put("licensePlate", nameList.getLicensePlate());
            }
            // 车牌颜色：用字符串（如"green"/"blue"/"yellow"，对应官方示例）
            if(nameList.getPlateColor() == 6){
                compoundCond.put("plateColor", "other");
            }
            if(nameList.getPlateColor() == 1){
                compoundCond.put("plateColor", "blue");
            }
            if(nameList.getPlateColor() == 2){
                compoundCond.put("plateColor", "yellow");
            }
            if(nameList.getPlateColor() == 3){
                compoundCond.put("plateColor", "white");
            }
            if(nameList.getPlateColor() == 4){
                compoundCond.put("plateColor", "black");
            }
            if(nameList.getPlateColor() == 5){
                compoundCond.put("plateColor", "green");
            }

            // 名单类型（如果官方需要，补充到CompoundCond中）
//            if (nameList.getType() != null) {
//                String typeStr = (nameList.getType() == 0) ? "whiteList" : "blackList";
//                compoundCond.put("type", typeStr);
//            }

            requestBody.put("CompoundCond", compoundCond);

            // 转JSON字符串（需引入Jackson/Gson等工具）
            return new JSONObject(requestBody).toString();
        } catch (Exception e) {
            logger.error("构建删除车辆JSON异常: {}", e.getMessage(), e);
            // 降级处理：返回最简合法JSON
            return String.format("{\"deleteAllEnabled\":false,\"CompoundCond\":{\"licensePlate\":\"%s\"}}",
                    nameList.getLicensePlate());
        }
    }

    /// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 构建车辆搜索 XML 请求体
     */
    private String buildVehicleSearchXml(Map<String, Object> condition) {
        try {
            StringBuilder xml = new StringBuilder();

            // XML 声明
//            xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");

            // 根元素
//            xml.append("<LPListAuditSearchDescription version=\"2.0\" xmlns=\"http://www.isapi.org/ver20/XMLSchema\">\n");
            xml.append("<LPListAuditSearchDescription>\n");

            // 必需字段
            xml.append("    <searchID>").append(escapeXml(condition.getOrDefault("searchID", UUID.randomUUID().toString()))).append("</searchID>\n");
            xml.append("    <searchResultPosition>").append(escapeXml(condition.getOrDefault("searchResultPosition", 0))).append("</searchResultPosition>\n");
            xml.append("    <maxResults>").append(escapeXml(condition.getOrDefault("maxResults", 30))).append("</maxResults>\n");

//            // LicensePlate 部分 - 根据条件动态构建
//            xml.append("    <LicensePlateList>\n");
//            xml.append("        <LicensePlate>\n");
//
//            // 车牌号（支持模糊匹配）
//            if (condition.containsKey("licensePlate")) {
//                String licensePlate = condition.get("licensePlate").toString();
//                xml.append("            <licensePlate>").append(escapeXml(licensePlate)).append("</licensePlate>\n");
//            }
//
//            // 车牌颜色
//            if (condition.containsKey("plateColor")) {
//                xml.append("            <plateColor>").append(escapeXml(condition.get("plateColor"))).append("</plateColor>\n");
//            }
//
//            // 车牌类型
//            if (condition.containsKey("plateType")) {
//                xml.append("            <plateType>").append(escapeXml(condition.get("plateType"))).append("</plateType>\n");
//            }
//
//            // 时间范围条件
//            if (condition.containsKey("startTime") || condition.containsKey("endTime")) {
//                xml.append("            <timeList>\n");
//                xml.append("                <timeSpan>\n");
//
//                if (condition.containsKey("startTime")) {
//                    xml.append("                    <startTime>").append(escapeXml(condition.get("startTime"))).append("</startTime>\n");
//                }
//
//                if (condition.containsKey("endTime")) {
//                    xml.append("                    <endTime>").append(escapeXml(condition.get("endTime"))).append("</endTime>\n");
//                }
//
//                xml.append("                </timeSpan>\n");
//                xml.append("            </timeList>\n");
//            }
//
//            xml.append("        </LicensePlate>\n");
//            xml.append("    </LicensePlateList>\n");
//
//            // 搜索条件扩展
//            if (condition.containsKey("enableAudit") || condition.containsKey("matchMode")) {
//                xml.append("    <searchCondition>\n");
//
//                if (condition.containsKey("enableAudit")) {
//                    xml.append("        <enableAudit>").append(escapeXml(condition.get("enableAudit"))).append("</enableAudit>\n");
//                }
//
//                if (condition.containsKey("matchMode")) {
//                    xml.append("        <matchMode>").append(escapeXml(condition.get("matchMode"))).append("</matchMode>\n");
//                }
//
//                xml.append("    </searchCondition>\n");
//            }
//
            xml.append("</LPListAuditSearchDescription>");

            return xml.toString();

        } catch (Exception e) {
            logger.error("构建车辆搜索 XML 异常: {}", e.getMessage(), e);
            // 返回一个基础的 XML 结构作为降级方案
            return getFallbackXml(condition);
        }
    }

    /**
     * XML 特殊字符转义
     */
    private String escapeXml(Object value) {
        if (value == null) {
            return "";
        }

        String str = value.toString();
        return str.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }

    /**
     * 降级方案：构建基础 XML 结构
     */
    private String getFallbackXml(Map<String, Object> condition) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<LPListAuditSearchDescription version=\"2.0\" xmlns=\"http://www.isapi.org/ver20/XMLSchema\">\n" +
                "    <searchID>" + condition.getOrDefault("searchID", UUID.randomUUID().toString()) + "</searchID>\n" +
                "    <searchResultPosition>0</searchResultPosition>\n" +
                "    <maxResults>" + condition.getOrDefault("maxResults", 30) + "</maxResults>\n" +
                "    <LicensePlateList>\n" +
                "        <LicensePlate>\n" +
                (condition.containsKey("licensePlate") ?
                        "            <licensePlate>" + condition.get("licensePlate") + "</licensePlate>\n" : "") +
                "        </LicensePlate>\n" +
                "    </LicensePlateList>\n" +
                "</LPListAuditSearchDescription>";
    }

    /**
     * 解析车辆搜索结果的 XML 响应，并转换为 NameList 实体列表
     */
    public Map<String, Object> parseVehicleResponse(String xmlResponse) {
        Map<String, Object> result = new HashMap<>();

        if (xmlResponse == null || xmlResponse.trim().isEmpty()) {
            logger.warn("XML 响应为空");
            result.put("error", "Empty response");
            return result;
        }

        try {
            // 安全配置，防止 XXE 攻击
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            factory.setXIncludeAware(false);
            factory.setExpandEntityReferences(false);

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new ByteArrayInputStream(xmlResponse.getBytes("UTF-8")));

            Element root = document.getDocumentElement();
            if (root != null) {
                // 解析基本信息
                parseBasicInfo(root, result);

                // 解析车牌信息列表并转换为 NameList 实体
                List<NameList> nameLists = parseLicensePlateInfoToEntity(root);
                result.put("nameLists", nameLists);
                result.put("totalCount", nameLists.size());
            }

        } catch (Exception e) {
            logger.error("XML 解析异常: {}", e.getMessage(), e);
            result.put("error", "XML解析失败: " + e.getMessage());
            result.put("rawResponse", xmlResponse);
        }

        return result;
    }

    /**
     * 解析基本信息（搜索ID、响应状态等）
     */
    private void parseBasicInfo(Element root, Map<String, Object> result) {
        Map<String, Object> searchInfo = new HashMap<>();

        String[] basicFields = {
                "searchID", "responseStatus", "responseStatusStrg",
                "numOfMatches", "totalMatches", "searchResultPosition", "maxResults"
        };

        for (String field : basicFields) {
            String value = getElementText(root, field);
            if (value != null && !value.isEmpty()) {
                searchInfo.put(field, value);
            }
        }

        result.put("searchInfo", searchInfo);
    }

    /**
     * 解析车牌信息并转换为 NameList 实体列表
     */
    private List<NameList> parseLicensePlateInfoToEntity(Element root) {
        List<NameList> nameLists = new ArrayList<>();

        NodeList licensePlateNodes = root.getElementsByTagName("LicensePlateInfo");
        if (licensePlateNodes.getLength() == 0) {
            return nameLists;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");

        for (int i = 0; i < licensePlateNodes.getLength(); i++) {
            Element plateElement = (Element) licensePlateNodes.item(i);

            NameList nameList = new NameList();

            try {
                // 设置车牌基本信息
                nameList.setLicensePlate(getElementText(plateElement, "LicensePlate"));

                // 转换名单类型：whiteList -> 0(授权), blackList -> 1(非授权)
                String typeStr = getElementText(plateElement, "type");
                if ("whiteList".equalsIgnoreCase(typeStr)) {
                    nameList.setType(0); // 授权名单
                } else if ("blackList".equalsIgnoreCase(typeStr)) {
                    nameList.setType(1); // 非授权名单
                } else {
                    nameList.setType(0); // 默认授权名单
                }

                // 转换车牌颜色
                String plateColorStr = getElementText(plateElement, "plateColor");
                nameList.setPlateColor(convertPlateColorToCode(plateColorStr));

                // 转换时间 - 处理不同的时间格式
                String effectiveTimeStr = getElementText(plateElement, "effectiveTime");
                String createTimeStr = getElementText(plateElement, "createTime");

                if (effectiveTimeStr != null && !effectiveTimeStr.isEmpty()) {
                    try {
                        Date effectiveTime = parseDateString(effectiveTimeStr);
                        nameList.setAccessEndTime(effectiveTime);
                    } catch (Exception e) {
                        logger.warn("有效时间格式解析失败: {} - {}", effectiveTimeStr, e.getMessage());
                    }
                }

                if (createTimeStr != null && !createTimeStr.isEmpty()) {
                    try {
                        Date createTime = parseDateString(createTimeStr);
                        nameList.setAccessStartTime(createTime);
                        nameList.setCreateTime(createTime);
                        nameList.setUpdateTime(createTime);
                    } catch (Exception e) {
                        logger.warn("创建时间格式解析失败: {} - {}", createTimeStr, e.getMessage());
                    }
                }

                // 设置其他字段
                nameList.setPlateType(0xff); // 默认车牌类型
                nameList.setChannel(1);   // 默认通道号

                nameLists.add(nameList);

            } catch (Exception e) {
                logger.error("解析车牌信息异常: {}", e.getMessage(), e);
            }
        }

        return nameLists;
    }

    /**
     * 解析日期字符串，支持多种格式
     */
    private Date parseDateString(String dateStr) throws Exception {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }

        // 移除时区信息中的冒号（如果存在），例如：+08:00 -> +0800
        String normalizedDateStr = dateStr.replaceAll("([+-]\\d{2}):(\\d{2})$", "$1$2");

        // 尝试不同的日期格式
        String[] patterns = {
                "yyyy-MM-dd'T'HH:mm:ssXXX",  // 2024-01-01T12:00:00+08:00
                "yyyy-MM-dd'T'HH:mm:ss",     // 2024-01-01T12:00:00
                "yyyy-MM-dd HH:mm:ss",        // 2024-01-01 12:00:00
                "yyyy/MM/dd HH:mm:ss",        // 2024/01/01 12:00:00
                "yyyy-MM-dd",                 // 2024-01-01
                "yyyy/MM/dd"                  // 2024/01/01
        };

        for (String pattern : patterns) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                sdf.setLenient(false); // 严格模式
                return sdf.parse(normalizedDateStr);
            } catch (Exception e) {
                // 尝试下一种格式
                continue;
            }
        }

        throw new Exception("无法解析日期格式: " + dateStr);
    }

    /**
     * 车牌颜色字符串转换为代码
     */
    private Integer convertPlateColorToCode(String plateColor) {
        if (plateColor == null) return 0;

        switch (plateColor.toLowerCase()) {
            case "blue": return 1;     // 蓝色
            case "yellow": return 2;   // 黄色
            case "white": return 3;    // 白色
            case "black": return 4;    // 黑色
            case "green": return 5;    // 绿色
            case "other": return 6;    // 其他
            default: return 0;         // 未知
        }
    }

    /**
     * 安全获取元素文本内容
     */
    private String getElementText(Element parent, String tagName) {
        try {
            NodeList nodes = parent.getElementsByTagName(tagName);
            if (nodes.getLength() > 0) {
                return nodes.item(0).getTextContent().trim();
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    // 辅助方法：从嵌套 Map 中安全获取值
    private String getStringValue(Map<String, Object> map, String key, String defaultValue) {
        try {
            String[] keys = key.split("\\.");
            Object current = map;
            for (String k : keys) {
                if (current instanceof Map) {
                    current = ((Map<String, Object>) current).get(k);
                } else {
                    return defaultValue;
                }
            }
            return current != null ? current.toString() : defaultValue;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    private int getIntValue(Map<String, Object> map, String key, int defaultValue) {
        try {
            String value = getStringValue(map, key, String.valueOf(defaultValue));
            return Integer.parseInt(value);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 构建删除车辆名单的 XML 请求体
     */
    private String buildDeleteVehicleXml(NameList nameList) {
        try {
            StringBuilder xml = new StringBuilder();

            // XML 声明
            xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");

            // 根元素
            xml.append("<DelLicensePlateAuditData version=\"2.0\" xmlns=\"http://www.isapi.org/ver20/XMLSchema\">\n");

            // 删除配置
            xml.append("    <deleteAllEnabled>false</deleteAllEnabled>\n");

            // 复合条件
            xml.append("    <CompoundCond>\n");

            // 车牌号（必填）
            if (nameList.getLicensePlate() != null && !nameList.getLicensePlate().isEmpty()) {
                xml.append("        <licensePlate>").append(escapeXml(nameList.getLicensePlate())).append("</licensePlate>\n");
            }

            // 车牌颜色 - 修正：Integer 类型判断 null
            if (nameList.getPlateColor() != null) {
                xml.append("        <plateColor>").append(escapeXml(nameList.getPlateColor().toString())).append("</plateColor>\n");
            }

            // 车牌类型 - 修正：Integer 类型判断 null
            if (nameList.getPlateType() != null) {
                xml.append("        <plateType>").append(escapeXml(nameList.getPlateType().toString())).append("</plateType>\n");
            }

            // 名单类型
            if (nameList.getType() != null) {
                // 将数字类型转换为字符串类型：0->whiteList, 1->blackList
                String typeStr = (nameList.getType() == 0) ? "whiteList" : "blackList";
                xml.append("        <type>").append(escapeXml(typeStr)).append("</type>\n");
            }

            xml.append("    </CompoundCond>\n");
            xml.append("</DelLicensePlateAuditData>");

            return xml.toString();

        } catch (Exception e) {
            logger.error("构建删除车辆 XML 异常: {}", e.getMessage(), e);
            return getFallbackDeleteXml(nameList);
        }
    }

    /**
     * 降级方案：基础删除 XML
     */
    private String getFallbackDeleteXml(NameList nameList) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<DelLicensePlateAuditData version=\"2.0\" xmlns=\"http://www.isapi.org/ver20/XMLSchema\">\n" +
                "    <deleteAllEnabled>false</deleteAllEnabled>\n" +
                "    <CompoundCond>\n" +
                "        <licensePlate>" + escapeXml(nameList.getLicensePlate()) + "</licensePlate>\n" +
                (nameList.getPlateColor() != null ?
                        "        <plateColor>" + escapeXml(nameList.getPlateColor()) + "</plateColor>\n" : "") +
                "    </CompoundCond>\n" +
                "</DelLicensePlateAuditData>";
    }

    /**
     * 解析删除操作的响应
     */
    private boolean parseDeleteResponse(String xmlResponse) {
        if (xmlResponse == null || xmlResponse.trim().isEmpty()) {
            return false;
        }

        try {
            // 安全配置
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);

            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new ByteArrayInputStream(xmlResponse.getBytes("UTF-8")));

            Element root = document.getDocumentElement();

            // 查找 status 或 responseStatus 字段
            String[] statusFields = {"status", "responseStatus", "result"};

            for (String field : statusFields) {
                NodeList nodes = root.getElementsByTagName(field);
                if (nodes.getLength() > 0) {
                    String status = nodes.item(0).getTextContent().trim().toLowerCase();
                    return "true".equals(status) || "ok".equals(status) || "success".equals(status);
                }
            }

            // 如果没有明确的 status 字段，检查是否有错误信息
            NodeList errorNodes = root.getElementsByTagName("error");
            if (errorNodes.getLength() > 0) {
                logger.warn("删除操作返回错误: {}", errorNodes.item(0).getTextContent());
                return false;
            }

            // 默认认为成功（某些设备可能不返回明确状态）
            return true;

        } catch (Exception e) {
            logger.warn("解析删除响应异常: {}", e.getMessage());
            return false;
        }
    }

}
