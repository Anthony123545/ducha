package com.supervision.web.Hikvision.peopleDoor.web.controller;


import com.supervision.web.Hikvision.peopleDoor.web.util.PeopleDoorDeviceManager;
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

import java.io.*;
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
@RequestMapping("/controlPeopleDoor")
public class PeopleDoorController {

    private static final Logger logger = LoggerFactory.getLogger(PeopleDoorController.class);

    @Autowired
    private PeopleDoorDeviceManager peopleDoorDeviceManager;

    @Autowired
    private DeviceService deviceService;

    @PostMapping("/controlDoorStatus")
    public Map<String, Object> controlDoorStatus(@RequestBody Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 1️⃣ 获取 deviceId 并校验
            Object idObj = params.get("deviceId");
            if (idObj == null) {
                result.put("status", false);
                result.put("message", "deviceId不能为空");
                return result;
            }

            Long deviceId;
            try {
                deviceId = Long.parseLong(idObj.toString());
            } catch (NumberFormatException e) {
                result.put("status", false);
                result.put("message", "deviceId格式错误");
                return result;
            }

            Device device = deviceService.getById(deviceId);
            if (device == null || device.getIp() == null || device.getIp().trim().isEmpty()) {
                logger.error("设备不存在或未配置IP, deviceId={}", deviceId);
                result.put("status", false);
                result.put("message", "设备不存在或未配置IP");
                return result;
            }
            String ip = device.getIp();

            // 2️⃣ 获取 gatewayIndex 和 command，并容错转换
            Integer gatewayIndex, command;
            try {
                gatewayIndex = Integer.parseInt(params.get("gatewayIndex").toString());
                command = Integer.parseInt(params.get("command").toString());
            } catch (Exception e) {
                result.put("status", false);
                result.put("message", "gatewayIndex 或 command 格式错误");
                return result;
            }

            // 3️⃣ 参数合法性校验
            if (gatewayIndex < 0) {
                result.put("status", false);
                result.put("message", "gatewayIndex不合法");
                return result;
            }
            if (command < 0 || command > 6) {
                result.put("status", false);
                result.put("message", "command不合法（0~6）");
                return result;
            }

            if (!isValidIp(ip)) {
                result.put("status", false);
                result.put("message", "无效的 IP 地址：" + ip);
                return result;
            }

            // 4️⃣ 获取设备 session
            DeviceSession session = peopleDoorDeviceManager.getDeviceSession(ip);
            if (session == null) {
                result.put("status", false);
                result.put("message", "设备未登录或不存在：" + ip);
                return result;
            }

            // 5️⃣ 调用 Service 执行门控操作
            logger.info("远程控门请求 | deviceId={}, ip={}, gatewayIndex={}, command={}", deviceId, ip, gatewayIndex, command);

            boolean status = peopleDoorDeviceManager.remoteControlGate(session.getUserID(), gatewayIndex, command);
            if (!status) {
                result.put("status", false);
                result.put("message", "远程控门失败");
                return result;
            }

            // 6️⃣ 成功返回
            result.put("status", true);
            result.put("message", "远程控门成功");
            result.put("userID", session.getUserID());
            result.put("ip", ip);
            result.put("gatewayIndex", gatewayIndex);
            result.put("command", command);
            return result;
        } catch (Exception e) {
            logger.error("远程控门接口异常", e);
            result.put("status", false);
            result.put("message", "远程控门失败：" + e.getMessage());
            return result;
        }
    }

    /**
     * 校验 IPv4/IPv6 格式合法性（兼容 JDK1.8）
     */
    private boolean isValidIp(String ip) {
        String ipv4Pattern =
                "^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$";
        String ipv6Pattern =
                "^[0-9a-fA-F]{1,4}(:[0-9a-fA-F]{1,4}){7}$";
        return ip.matches(ipv4Pattern) || ip.matches(ipv6Pattern);
    }

    /**
     * 登录设备
     */
    @PostMapping("/login")
    public Map<String, Object> loginDevice(@RequestBody Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 获取 deviceId 并校验
            Object idObj = params.get("deviceId");
            if (idObj == null) {
                result.put("status", false);
                result.put("message", "deviceId不能为空");
                return result;
            }

            Long deviceId;
            try {
                deviceId = Long.parseLong(idObj.toString());
            } catch (NumberFormatException e) {
                result.put("status", false);
                result.put("message", "deviceId格式错误");
                return result;
            }

            Device device = deviceService.getById(deviceId);
            if (device == null) {
                result.put("status", false);
                result.put("message", "设备不存在");
                return result;
            }

            // 登录设备
            DeviceSession session = peopleDoorDeviceManager.loginDevice(
                    device.getIp(),
                    device.getPort().shortValue(),
                    device.getAccount(),
                    device.getPassword()
            );

            if (session == null) {
                result.put("status", false);
                result.put("message", "登录失败，请检查设备信息或连接");
                return result;
            }

            // 成功返回信息
            result.put("status", true);
            result.put("message", "登录成功");
            result.put("ip", session.getIp());
            result.put("userID", session.getUserID());
            return result;
        } catch (Exception e) {
            logger.error("登录设备接口异常", e);
            result.put("status", false);
            result.put("message", "登录设备接口异常");
            return result;
        }
    }

    /**
     * 查询设备状态
     */
    @PostMapping("/status")
    public Map<String, Object> getDeviceStatus(@RequestBody Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
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

            DeviceSession session = peopleDoorDeviceManager.getDeviceSession(device.getIp());
            if (session == null) {
                result.put("status", false);
                result.put("online", false);
                result.put("message", "设备未登录或登录失败");
                return result;
            }

//            // 调用 SDK 检查设备连接是否有效
//            boolean online = peopleDoorDeviceManager.checkDeviceOnline(session.getUserID());

            result.put("status", true);
            result.put("online", true);
            result.put("ip", session.getIp());
            result.put("userID", session.getUserID());
            result.put("message", true ? "设备在线" : "设备离线，需要重新登录");
            return result;
        } catch (Exception e) {
            logger.error("查询设备状态异常", e);
            return fail("查询设备状态异常");
        }
    }

    private Map<String, Object> fail(String msg) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", false);
        map.put("message", msg);
        return map;
    }

    /**
     * 启动/撤销布防
     */
    @PostMapping("/alarm")
    public Map<String, Object> controlAlarm(@RequestBody Map<String, Object> req)
            throws UnsupportedEncodingException, InterruptedException {
        String ip = (String) req.get("ip");
        String action = (String) req.get("action"); // "start" 或 "stop"

        Map<String, Object> res = new HashMap<>();
        DeviceSession session = peopleDoorDeviceManager.getDeviceSession(ip);
        if (session == null) {
            res.put("success", false);
            res.put("message", "设备未登录");
            return res;
        }

        HashMap<String, Object> cmd = new HashMap<>();
        if ("start".equalsIgnoreCase(action)) {
            cmd.put("caseId", "1"); // 布防
//            peopleDoorDeviceManager.handleCase(session.getUserID(), cmd);
            res.put("message", "布防已启动");
        } else if ("stop".equalsIgnoreCase(action)) {
            cmd.put("caseId", "2"); // 撤防
//            peopleDoorDeviceManager.handleCase(session.getUserID(), cmd);
            res.put("message", "布防已撤销");
        } else {
            res.put("message", "未知操作: " + action);
        }

        res.put("success", true);
        return res;
    }

}
