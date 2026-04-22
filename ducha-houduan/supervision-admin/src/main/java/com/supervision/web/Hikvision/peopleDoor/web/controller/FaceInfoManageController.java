package com.supervision.web.Hikvision.peopleDoor.web.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.supervision.utils.ImageCompressUtil;
import com.supervision.utils.MemoryMultipartFile;
import com.supervision.web.Hikvision.peopleDoor.connectSDK.Acs.FaceManage;
import com.supervision.web.Hikvision.peopleDoor.web.util.PeopleDoorDeviceManager;
import com.supervision.web.Hikvision.util.DeviceSession;
import com.supervision.web.Hikvision.peopleDoor.web.entity.Device;
import com.supervision.web.Hikvision.peopleDoor.web.service.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Transactional
@RestController
@RequestMapping("/faceInfo")
public class FaceInfoManageController {

    public static final Logger logger = LoggerFactory.getLogger(FaceInfoManageController.class);

    @Autowired
    private PeopleDoorDeviceManager peopleDoorDeviceManager;

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

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private FaceManage faceManage;

    /**
     * 查询人脸总数信息
     */
    @PostMapping("/count")
    public Map<String, Object> count(@RequestBody Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 1 获取 deviceId 并校验
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
            if (!isValidIp(ip)) {
                result.put("status", false);
                result.put("message", "无效的 IP 地址：" + ip);
                return result;
            }

            // 2获取设备 session
            DeviceSession session = peopleDoorDeviceManager.getDeviceSession(ip);
            if (session == null) {
                result.put("status", false);
                result.put("message", "设备未登录或不存在：" + ip);
                return result;
            }

            Map<String, Object> res = peopleDoorDeviceManager.getFaceCount(session.getUserID());
            if ((boolean) res.get("status")) {
                result.put("status", true);
                result.put("data", res.get("data"));
                result.put("message", "获取人脸总数信息成功");
            } else {
                result.put("status", false);
                result.put("message", "获取人脸总数信息失败");
            }
        } catch (Exception e) {
            logger.error("获取人脸总数信息失败", e);
            result.put("status", false);
            result.put("message", "获取人脸总数信息失败");
        }
        return result;
    }

    /**
     * 查询人脸信息
     */
    @PostMapping("/list")
    public Map<String, Object> list(@RequestBody Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 获取 deviceId 并校验
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

            // 2 获取设备 session
            DeviceSession session = peopleDoorDeviceManager.getDeviceSession(ip);
            if (session == null) {
                result.put("status", false);
                result.put("message", "设备未登录或不存在：" + ip);
                return result;
            }

            result = peopleDoorDeviceManager.getFaceInfo(session.getUserID(), params);
        } catch (Exception e) {
            logger.error("获取人脸信息失败", e);
            result.put("status", false);
            result.put("message", "获取人脸信息失败");
        }
        return result;
    }

    /**
     * 添加人脸信息
     */
    @PostMapping("/add")
    public Map<String, Object> add(@RequestParam("file") MultipartFile file, @RequestPart("params") String paramsJson) {
        Map<String, Object> result = new HashMap<>();
        List<String> failedList = new ArrayList<>();
        try {



            // 文件校验
            if (file == null || file.isEmpty()) {
                result.put("status", false);
                result.put("message", "人脸图片文件不能为空");
                return result;
            }

            // 解析 JSON 参数
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> params = objectMapper.readValue(paramsJson, Map.class);
            // 第一步：获取原始字节
            byte[] bytes = file.getBytes();

            // 第二步：如果文件大，调用工具类压缩
            if (bytes.length > 200 * 1024) {
                bytes = ImageCompressUtil.compressToJpg(bytes);
            }

            // 第三步：用 MemoryMultipartFile 包装
            // 参数说明：(表单参数名, 文件名, 文件类型, 字节数组)
            MultipartFile finalFile = new MemoryMultipartFile(
                    file.getName(),
                    file.getOriginalFilename(),
                    "image/jpeg",
                    bytes
            );

            // 第四步：放入 params 交给 SDK 下发
            params.put("file", finalFile);


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
                    peopleDoorDeviceManager.addFaceInfo(session.getUserID(), params);
                } catch (Exception e) {
                    logger.error("设备 {} 插入人脸信息失败", deviceId, e);
                    failedList.add("设备 " + deviceId + " 插入失败");
                }
            }

            /**
             * 3. 返回结果给前端
             *   - 全部成功：无错误
             *   - 部分失败：包含 failedList
             */
            if (failedList.isEmpty()) {
                return success("添加人脸信息成功");
            } else {
                result.put("message", "部分设备");
                result.put("failedList", failedList);
                return result;
            }
        } catch (Exception e) {
            logger.error("添加人脸信息失败", e);
            return fail("添加人脸信息失败");
        }
    }

    /**
     * 更新人脸信息
     */
    @PostMapping("/update")
    public Map<String, Object> update(@RequestParam("file") MultipartFile file, @RequestPart("params") String paramsJson) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 解析 JSON 参数
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> params = objectMapper.readValue(paramsJson, Map.class);

            // 关键修改：将文件对象添加到 params 中
            params.put("file", file);  // 这样你的 addFaceInfo 方法就能获取到了

            // 1 获取 deviceId 并校验
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
            if (!isValidIp(ip)) {
                result.put("status", false);
                result.put("message", "无效的 IP 地址：" + ip);
                return result;
            }

            // 2 获取设备 session
            DeviceSession session = peopleDoorDeviceManager.getDeviceSession(ip);
            if (session == null) {
                result.put("status", false);
                result.put("message", "设备未登录或不存在：" + ip);
                return result;
            }

            result = peopleDoorDeviceManager.addFaceInfo(session.getUserID(), params);
        } catch (Exception e) {
            logger.error("修改人脸信息失败", e);
            result.put("status", false);
            result.put("message", "修改人脸信息失败");
        }
        return result;
    }

    /**
     * 删除人脸信息
     */
    @PostMapping("/delete")
    public Map<String, Object> delete(@RequestBody Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 1 获取 deviceId 并校验
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
            if (!isValidIp(ip)) {
                result.put("status", false);
                result.put("message", "无效的 IP 地址：" + ip);
                return result;
            }

            // 2 获取设备 session
            DeviceSession session = peopleDoorDeviceManager.getDeviceSession(ip);
            if (session == null) {
                result.put("status", false);
                result.put("message", "设备未登录或不存在：" + ip);
                return result;
            }

            result = peopleDoorDeviceManager.deleteFaceInfo(session.getUserID(), params);
        } catch (Exception e) {
            logger.error("删除人脸信息失败", e);
            result.put("status", false);
            result.put("message", "删除人脸信息失败");
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
