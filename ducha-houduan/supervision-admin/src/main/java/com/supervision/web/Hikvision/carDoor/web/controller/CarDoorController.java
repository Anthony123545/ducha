package com.supervision.web.Hikvision.carDoor.web.controller;

import com.supervision.web.Hikvision.carDoor.web.util.CarDoorDeviceManager;
import com.supervision.web.Hikvision.carDoor.connectSDK.hik.BarrierGateManage;
import com.supervision.web.Hikvision.util.DeviceSession;
import com.supervision.web.Hikvision.peopleDoor.web.entity.Device;
import com.supervision.web.Hikvision.peopleDoor.web.service.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 海康车牌识别摄像机控制接口
 * ---------------------------------
 * 用于外部调用：
 *  - 登录设备
 *  - 启动/停止布防
 *  - 查看设备状态
 */
@RestController
@RequestMapping("/controlCarDoor")
public class CarDoorController {

    private static final Logger logger = LoggerFactory.getLogger(CarDoorController.class);

    @Autowired
    private CarDoorDeviceManager carDoorDeviceManager;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private BarrierGateManage barrierGateManage;

    /**
     * *****************************
     *   统一成功/失败返回方法
     * *****************************
     */
    private Map<String, Object> success(String msg) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", true);
        map.put("message", msg);
        return map;
    }

    private Map<String, Object> success(String msg, Map<String, Object> data) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", true);
        map.put("message", msg);
        map.putAll(data);
        return map;
    }

    private Map<String, Object> fail(String msg) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", false);
        map.put("message", msg);
        return map;
    }

    /**
     * ==============================
     *        控制闸机开关
     * ==============================
     */
    @PostMapping("/controlDoorStatus")
    public Map<String, Object> controlDoorStatus(@RequestBody Map<String, Object> params) {
        try {
            // 1️⃣ 获取 deviceId 并校验
            Object idObj = params.get("deviceId");
            if (idObj == null) {
                return fail("deviceId不能为空");
            }

            Long deviceId;
            try {
                deviceId = Long.parseLong(idObj.toString());
            } catch (NumberFormatException e) {
                return fail("deviceId格式错误");
            }

            Device device = deviceService.getById(deviceId);
            if (device == null || device.getIp() == null || device.getIp().trim().isEmpty()) {
                return fail("设备不存在或未配置IP");
            }

            String ip = device.getIp();

            // 2️⃣ 获取 gatewayIndex 和 command，并容错转换
            Integer gatewayIndex = parseInt(params.get("gatewayIndex"));
            Integer command = parseInt(params.get("command"));
            if (gatewayIndex == null || command == null) {
                return fail("gatewayIndex 或 command 格式错误");
            }

            // 3️⃣ 参数合法性校验
            if (gatewayIndex < 0) return fail("gatewayIndex不合法: " + gatewayIndex);
            if (command < 0 || command > 6) return fail("command不合法（0~6）: " + command);
            if (!isValidIp(ip)) return fail("无效的 IP 地址: " + ip);

            // 4️⃣ 获取设备 session
            DeviceSession session = carDoorDeviceManager.getDeviceSession(ip);
            if (session == null) return fail("设备未登录或不存在");

            // 5️⃣ 调用 Service 执行门控操作
            logger.info("远程控闸请求 | deviceId={}, ip={}, gatewayIndex={}, command={}",
                    deviceId, ip, gatewayIndex, command);

            boolean ok = carDoorDeviceManager.BarrierGateCtrl(session.getUserID(), gatewayIndex, command);
            if (!ok) return fail("远程控闸失败");

            // 6️⃣ 成功返回
            Map<String, Object> data = new HashMap<>();
            data.put("userID", session.getUserID());
            data.put("ip", ip);
            data.put("gatewayIndex", gatewayIndex);
            data.put("command", command);

            return success("操作成功", data);
        } catch (Exception e) {
            logger.error("远程控闸接口异常", e);
            return fail("远程控闸失败");
        }
    }

    /**
     * ==============================
     *          登录设备
     * ==============================
     */
    @PostMapping("/login")
    public Map<String, Object> loginDevice(@RequestBody Map<String, Object> params) {
        try {
            // 获取 deviceId 并校验
            Object idObj = params.get("deviceId");
            if (idObj == null) {
                return fail("deviceId不能为空");
            }

            Long deviceId;
            try {
                deviceId = Long.parseLong(idObj.toString());
            } catch (NumberFormatException e) {
                return fail("deviceId格式错误");
            }

            Device device = deviceService.getById(deviceId);
            if (device == null) return fail("设备不存在");

            // 登录设备
            DeviceSession session = carDoorDeviceManager.loginDevice(
                    device.getIp(),
                    device.getPort().shortValue(),
                    device.getAccount(),
                    device.getPassword()
            );

            if (session == null) return fail("登录失败，请检查设备信息或连接");

            Map<String, Object> data = new HashMap<>();
            data.put("ip", session.getIp());
            data.put("userID", session.getUserID());

            return success("登录成功", data);
        } catch (Exception e) {
            logger.error("登录设备接口异常", e);
            return fail("登录设备接口异常");
        }
    }

    /**
     * ==============================
     *        设备在线状态查询
     * ==============================
     */
    @PostMapping("/status")
    public Map<String, Object> getDeviceStatus(@RequestBody Map<String, Object> params) {
        try {
            Object idObj = params.get("deviceId");
            if (idObj == null) return fail("deviceId不能为空");

            Long deviceId;
            try {
                deviceId = Long.parseLong(idObj.toString());
            } catch (NumberFormatException e) {
                return fail("deviceId格式错误");
            }

            Device device = deviceService.getById(deviceId);
            if (device == null) return fail("设备不存在");

            DeviceSession session = carDoorDeviceManager.getDeviceSession(device.getIp());
            if (session == null) {
                Map<String, Object> data = new HashMap<>();
                data.put("online", false);
                return success("设备未登录或登录失败", data);
            }

            Map<String, Object> data = new HashMap<>();
            data.put("online", true);
            data.put("ip", session.getIp());
            data.put("userID", session.getUserID());

            return success("设备在线", data);
        } catch (Exception e) {
            logger.error("查询设备状态异常", e);
            return fail("查询设备状态异常");
        }
    }

    /**
     * ==============================
     *     车辆、名单统计（示例）
     * ==============================
     */
    @PostMapping("/getCarCount")
    public Map<String, Object> getCarCount(@RequestBody Map<String, Object> params) {
        try {
            Map<String, Object> data = new HashMap<>();
            data.put("total", 300);
            return success("获取车辆总数成功", data);
        } catch (Exception e) {
            return fail("获取车辆总数失败");
        }
    }

    @PostMapping("/getNameListCount")
    public Map<String, Object>  getNameListCount(@RequestBody Map<String, Object> params) {
        try {
            Map<String, Object> data = new HashMap<>();
            data.put("total", 150);
            return success("获取名单总数成功", data);
        } catch (Exception e) {
            return fail("获取名单总数失败");
        }
    }

    /**
     * ==============================
     *     查询门禁状态（示例）
     * ==============================
     */
    @PostMapping("/getDoorStatus")
    public Map<String, Object> getDoorStatus(@RequestBody Map<String, Object> params) {
        try {
            Object idObj = params.get("deviceId");
            if (idObj == null) {
                return fail("deviceId不能为空");
            }

            Long deviceId;
            try {
                deviceId = Long.parseLong(idObj.toString());
            } catch (NumberFormatException e) {
                return fail("deviceId格式错误");
            }

            Device device = deviceService.getById(deviceId);
            if (device == null || device.getIp() == null) return fail("设备不存在或未配置IP");

            DeviceSession session = carDoorDeviceManager.getDeviceSession(device.getIp());
            if (session == null) return fail("设备未登录");

            Map<String, Object> data = new HashMap<>();
            data.put("state", "TODO"); // 示例：你未来可填入真实状态
            return success("操作成功", data);
        } catch (Exception e) {
            logger.error("控制门禁异常", e);
            return fail("操作失败");
        }
    }

    /**
     * ==============================
     *          布防/撤防
     * ==============================
     */
    @PostMapping("/alarm")
    public Map<String, Object> controlAlarm(@RequestBody Map<String, Object> req) {
        String ip = (String) req.get("ip");
        String action = (String) req.get("action"); // "start" 或 "stop"

        DeviceSession session = carDoorDeviceManager.getDeviceSession(ip);
        if (session == null) return fail("设备未登录");

        HashMap<String, Object> cmd = new HashMap<>();
//        if ("start".equalsIgnoreCase(action)) {
//            cmd.put("caseId", "1"); // 布防
//            carDoorDeviceManager.handleCase(session.getUserID(), cmd);
//            res.put("message", "布防已启动");
//        } else if ("stop".equalsIgnoreCase(action)) {
//            cmd.put("caseId", "2"); // 撤防
//            carDoorDeviceManager.handleCase(session.getUserID(), cmd);
//            res.put("message", "布防已撤销");
//        } else {
//            res.put("message", "未知操作: " + action);
//        }

        return success("操作成功");
    }


    /**
     * ==============================
     *      工具方法：类型安全转换
     * ==============================
     */
    private Integer parseInt(Object obj) {
        if (obj == null) return null;
        try { return Integer.parseInt(obj.toString()); }
        catch (Exception e) { return null; }
    }

    private Long parseLong(Object obj) {
        if (obj == null) return null;
        try { return Long.parseLong(obj.toString()); }
        catch (Exception e) { return null; }
    }

    private boolean isValidIp(String ip) {
        String ipv4 = "^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$";
        String ipv6 = "^[0-9a-fA-F]{1,4}(:[0-9a-fA-F]{1,4}){7}$";
        return ip.matches(ipv4) || ip.matches(ipv6);
    }

}
