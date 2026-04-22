package com.supervision.web.Hikvision.peopleDoor.web.controller;


import com.supervision.web.Hikvision.peopleDoor.web.util.PeopleDoorDeviceManager;
import com.supervision.web.Hikvision.util.DeviceSession;
import com.supervision.web.Hikvision.peopleDoor.web.entity.Device;
import com.supervision.web.Hikvision.peopleDoor.web.service.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/peopleEvent")
public class PersonEventManageController {

    public static final Logger logger = LoggerFactory.getLogger(PersonEventManageController.class);

    @Autowired
    private PeopleDoorDeviceManager peopleDoorDeviceManager;

    @Autowired
    private DeviceService deviceService;

    @PostMapping("/list")
    public Map<String, Object> list(@RequestBody Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        try {
            Long deviceId;
            Object idObj = params.get("deviceId");
            if (idObj == null) {
                result.put("status", false);
                result.put("message", "deviceId不能为空");
                return result;
            }

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
            if (!isValidIp(ip)) {
                result.put("status", false);
                result.put("message", "无效的 IP 地址：" + ip);
                return result;
            }

            DeviceSession session = peopleDoorDeviceManager.getDeviceSession(ip);
            if (session == null) {
                result.put("status", false);
                result.put("message", "设备未登录或不存在：" + ip);
                return result;
            }

            result = peopleDoorDeviceManager.searchAllEvent(session.getUserID(), params);
        } catch (Exception e) {
            logger.error("获取事件失败", e.getMessage());
            result.put("status", false);
            result.put("message", "获取事件失败");
        }
        return result;
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

}
