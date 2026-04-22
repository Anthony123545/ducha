package com.supervision.web.Hikvision.peopleDoor.web.controller;

import com.supervision.web.Hikvision.peopleDoor.web.util.PeopleDoorDeviceManager;
import com.supervision.web.Hikvision.util.DeviceSession;
import com.supervision.web.Hikvision.peopleDoor.web.entity.Device;
import com.supervision.web.Hikvision.peopleDoor.web.service.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Transactional
@RestController
@RequestMapping("/peopleInfo")
public class PeopleInfoManageController {

    public static final Logger logger = LoggerFactory.getLogger(PeopleInfoManageController.class);

    @Autowired
    private PeopleDoorDeviceManager peopleDoorDeviceManager;

    @Autowired
    private DeviceService deviceService;

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
     * 查询人员总数信息
     */
    @PostMapping("/count")
    public Map<String, Object> count(@RequestBody Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 1 获取 deviceId 并校验
            Object idObj = params.get("deviceId");
            if (idObj == null) {
                return fail( "deviceId不能为空");
            }

            Long deviceId;
            try {
                deviceId = Long.parseLong(idObj.toString());
            } catch (NumberFormatException e) {
                return fail( "deviceId格式错误");
            }

            Device device = deviceService.getById(deviceId);
            if (device == null || device.getIp() == null || device.getIp().trim().isEmpty()) {
                logger.error("设备不存在或未配置IP, deviceId={}", deviceId);
                return fail( "设备不存在或未配置IP");
            }

            String ip = device.getIp();
            if (!isValidIp(ip)) {
                return fail( "无效的 IP 地址：" + ip);
            }

            // 2 获取设备 session
            DeviceSession session = peopleDoorDeviceManager.getDeviceSession(ip);
            if (session == null) {
                return fail("设备未登录或不存在：" + ip);
            }

            Map<String, Object> res = peopleDoorDeviceManager.getUserCount(session.getUserID());
            if ((boolean) res.get("status")) {
                return success("获取人员总数信息成功",  res);
            } else {
                return fail("获取人员总数信息失败");
            }
        } catch (Exception e) {
            logger.error("获取人员总数信息失败", e);
            return fail("获取人员总数信息失败");
        }
    }

    /**
     * 查询人员信息
     */
    @PostMapping("/list")
    public Map<String, Object> list(@RequestBody Map<String, Object> params) {
        try {
            // 获取 deviceId 并校验
            Long deviceId;
            Object idObj = params.get("deviceId");
            if (idObj == null) {
                return fail("deviceId不能为空");
            }

            try {
                deviceId = Long.parseLong(idObj.toString());
            } catch (NumberFormatException e) {
                return fail("deviceId格式错误");
            }

            Device device = deviceService.getById(deviceId);
            if (device == null || device.getIp() == null || device.getIp().trim().isEmpty()) {
                logger.error("设备不存在或未配置IP, deviceId={}", deviceId);
                return fail("设备不存在或未配置IP");
            }

            String ip = device.getIp();
            if (!isValidIp(ip)) {
                return fail("无效的 IP 地址：" + ip);
            }

            // 2 获取设备 session
            DeviceSession session = peopleDoorDeviceManager.getDeviceSession(ip);
            if (session == null) {
                return fail("设备未登录或不存在：" + ip);
            }

            Map<String, Object> result = peopleDoorDeviceManager.getUserInfo(session.getUserID(), params);
            return success("获取人员信息成功", result);
        } catch (Exception e) {
            logger.error("获取人员信息失败", e);
            return fail("获取人员信息失败");
        }
    }

    /**
     * 添加人员信息
     * 批量向多个设备写入人员信息
     * 支持部分失败，最终返回失败设备列表
     */
    @PostMapping("/add")
    public Map<String, Object> add(@RequestBody Map<String, Object> params) {
        // 用于记录所有失败的设备，最终返回给前端
        List<String> failedList = new ArrayList<>();
        try {
            /**
             * 1. 获取并校验设备 ID 列表
             */
            Object deviceIdsObj = params.get("deviceIds");
            if (!(deviceIdsObj instanceof List)) {
                return fail("设备列表不能为空");
            }

            // 转换为 Integer 列表
            List<Integer> deviceIds = ((List<?>) deviceIdsObj).stream()
                    .map(id -> Integer.parseInt(id.toString()))
                    .collect(Collectors.toList());

            // 限制设备数量
            if (deviceIds.size() > 20) {
                return fail("设备列表不能超过20个");
            }

            /**
             * 2. 遍历每个设备，单独插入人员信息
             *    ——注意：某个设备失败时不能 return，只能 continue
             */
            for (Integer deviceId : deviceIds) {
                // 2.1 获取设备信息
                Device device = deviceService.getById(Long.valueOf(deviceId));
                if (device == null || device.getIp() == null || device.getIp().trim().isEmpty()) {
                    // 设备本身不存在或未配置 IP
                    logger.error("设备不存在或未配置IP, deviceId={}", deviceId);
                    failedList.add("设备 " + deviceId + " 不存在或未配置IP");
                    continue;
                }

                String ip = device.getIp();

                // 2.2 校验设备 IP
                if (!isValidIp(ip)) {
                    logger.error("无效的 IP 地址：" + ip);
                    failedList.add("设备 " + deviceId + " 无效的 IP：" + ip);
                    continue;
                }

                /**
                 * 2.3 获取设备 Session
                 *      如果 session 为空，说明设备未登录/不在线
                 */
                DeviceSession session = peopleDoorDeviceManager.getDeviceSession(ip);
                if (session == null) {
                    logger.error("设备未登录或不存在：" + ip);
                    failedList.add("设备 " + deviceId + " 未登录");
                    continue;
                }

                /**
                 * 2.4 调用 SDK 写入人员信息
                 */
                try {
                    peopleDoorDeviceManager.addUserInfo(session.getUserID(), params);
                } catch (Exception e) {
                    logger.error("设备 {} 插入人员信息失败", deviceId, e);
                    failedList.add("设备 " + deviceId + " 插入失败");
                }
            }

            /**
             * 3. 返回结果给前端
             *   - 全部成功：无错误
             *   - 部分失败：包含 failedList
             */
            if (failedList.isEmpty()) {
                return success("添加人员信息成功");
            } else {
                Map<String, Object> result = success("部分设备人员信息插入失败");
                result.put("failedList", failedList);
                return result;
            }
        } catch (Exception e) {
            logger.error("添加人员信息失败", e);
            return fail("添加人员信息失败");
        }
    }

    /**
     * 更新人员信息
     * 批量向多个设备更新人员信息
     * 支持部分失败，最终返回失败设备列表
     */
    @PostMapping("/update")
    public Map<String, Object> update(@RequestBody Map<String, Object> params) {
        // 用于记录所有失败的设备，最终返回给前端
        List<String> failedList = new ArrayList<>();
        try {
            /**
             * 1. 获取并校验设备 ID 列表
             */
            Object deviceIdsObj = params.get("deviceIds");
            if (!(deviceIdsObj instanceof List)) {
                return fail("设备列表不能为空");
            }

            // 转换为 Integer 列表
            List<Integer> deviceIds = ((List<?>) deviceIdsObj).stream()
                    .map(id -> Integer.parseInt(id.toString()))
                    .collect(Collectors.toList());

            // 限制设备数量
            if (deviceIds.size() > 20) {
                return fail("设备列表不能超过20个");
            }

            /**
             * 2. 遍历每个设备，单独插入人员信息
             *    ——注意：某个设备失败时不能 return，只能 continue
             */
            for (Integer deviceId : deviceIds) {
                // 2.1 获取设备信息
                Device device = deviceService.getById(Long.valueOf(deviceId));
                if (device == null || device.getIp() == null || device.getIp().trim().isEmpty()) {
                    // 设备本身不存在或未配置 IP
                    logger.error("设备不存在或未配置IP, deviceId={}", deviceId);
                    failedList.add("设备 " + deviceId + " 不存在或未配置IP");
                    continue;
                }

                String ip = device.getIp();

                // 2.2 校验设备 IP
                if (!isValidIp(ip)) {
                    logger.error("无效的 IP 地址：" + ip);
                    failedList.add("设备 " + deviceId + " 无效的 IP：" + ip);
                    continue;
                }

                /**
                 * 2.3 获取设备 Session
                 *      如果 session 为空，说明设备未登录/不在线
                 */
                DeviceSession session = peopleDoorDeviceManager.getDeviceSession(ip);
                if (session == null) {
                    logger.error("设备未登录或不存在：" + ip);
                    failedList.add("设备 " + deviceId + " 未登录");
                    continue;
                }

                /**
                 * 2.4 调用 SDK 写入人员信息
                 */
                try {
                    peopleDoorDeviceManager.updateUserInfo(session.getUserID(), params);
                } catch (Exception e) {
                    logger.error("设备 {} 更新人员信息失败", deviceId, e);
                    failedList.add("设备 " + deviceId + " 插入失败");
                }
            }

            /**
             * 3. 返回结果给前端
             *   - 全部成功：无错误
             *   - 部分失败：包含 failedList
             */
            if (failedList.isEmpty()) {
                return success("更新人员信息成功");
            } else {
                Map<String, Object> result = success("部分设备人员信息更新失败");
                result.put("failedList", failedList);
                return result;
            }
        } catch (Exception e) {
            logger.error("更新人员信息失败", e);
            return fail("更新人员信息失败");
        }
    }

    /**
     * 删除人员信息
     */
    @PostMapping("/delete")
    public Map<String, Object> delete(@RequestBody Map<String, Object> params) {
        try {
            // 1 获取 deviceId 并校验
            Object idObj = params.get("deviceId");
            if (idObj == null) {
                return fail( "deviceId不能为空");
            }

            Long deviceId;
            try {
                deviceId = Long.parseLong(idObj.toString());
            } catch (NumberFormatException e) {
                return fail( "deviceId格式错误");
            }

            Device device = deviceService.getById(deviceId);
            if (device == null || device.getIp() == null || device.getIp().trim().isEmpty()) {
                logger.error("设备不存在或未配置IP, deviceId={}", deviceId);
                return fail( "设备不存在或未配置IP");
            }

            String ip = device.getIp();
            if (!isValidIp(ip)) {
                return fail(  "无效的 IP 地址：" + ip);
            }

            // 2 获取设备 session
            DeviceSession session = peopleDoorDeviceManager.getDeviceSession(ip);
            if (session == null) {
                return fail( "设备未登录或不存在：" + ip);
            }

            Map<String, Object> result = peopleDoorDeviceManager.deleteUserInfo(session.getUserID(), params);
            return success("删除人员信息成功", result);
        } catch (Exception e) {
            logger.error("删除人员信息失败", e);
            return fail( "删除人员信息失败");
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

}
