package com.supervision.web.Hikvision.carDoor.web.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.supervision.web.Hikvision.carCamera.web.util.CarCameraDeviceManager;
import com.supervision.web.Hikvision.carDoor.web.entity.NameList;
import com.supervision.web.Hikvision.carDoor.web.util.CarDoorDeviceManager;
import com.supervision.web.Hikvision.peopleDoor.web.entity.Device;
import com.supervision.web.Hikvision.peopleDoor.web.service.DeviceService;
import com.supervision.web.Hikvision.util.DeviceSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


/**
 * @description 名单管理控制器
 * 提供增删改查接口，包括批量删除和重复车牌校验
 */
@RestController
@RequestMapping("/nameList")
public class NameListController {

    private static final Logger logger = LoggerFactory.getLogger(NameListController.class);

    @Autowired
    private CarDoorDeviceManager carDoorDeviceManager;

    @Autowired
    private CarCameraDeviceManager carCameraDeviceManager;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private ObjectMapper objectMapper; // Spring 默认注入的 Jackson 解析器


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
     * 获取名单列表
     * @param params 前端传入的参数
     * @return success、msg、total、list、pageNum、pageSize
     */
    @PostMapping("/list")
    public Map<String, Object> list(@RequestBody Map<String, Object> params) {
        try {
            // 校验并获取 DeviceSession（包含 deviceId / IP / session 校验）
            DeviceSession session = validateAndGetDeviceSession(params);
            Map<String, Object> result = carDoorDeviceManager.getVechileList(session.getUserID(), params);
            if(result != null&&Boolean.FALSE.equals(result.get("status"))){
                logger.warn("⚠️ 获取授权名单列表失败：{},可能是僵尸句柄，尝试重连", result.get("message"));
                refreshDeviceSession(params);
                return fail("设备连接繁忙，正在重连...");
            }
            return success("获取授权名单列表成功",  result);
        } catch (Exception e) {
            logger.error("获取授权名单列表失败", e);
            refreshDeviceSession(params);
            return fail("获取授权名单列表失败");
        }
    }

    private void refreshDeviceSession(Map<String, Object> params) {
        try{
            Long deviceId = Long.parseLong(params.get("deviceId").toString());
            Device device = deviceService.getById(deviceId);
            if (device != null) {
                carCameraDeviceManager.loginDevice(device.getIp(), (short)8000, device.getAccount(), device.getPassword());
            }
        } catch (Exception e) {
            logger.error("刷新设备会话失败", e);
        }
    }

    /**
     * 添加名单
     * 遍历前端传来的设备列表和摄像头列表，向每个视频插入授权名单
     * 支持部分失败记录
     * @param params 添加参数，包含：
     *   - nameListData: 授权名单对象
     *   - deviceIds: 设备ID列表
     * @return Map<String,Object> 返回操作结果和失败列表（如果有）
     */
    @PostMapping("/add")
    public Map<String, Object> add(@RequestBody Map<String, Object> params) {
        List<String> failedList = new ArrayList<>(); // 用于记录失败的设备/摄像头
        try {
            // 1️⃣ 获取前端传来的名单数据
            Object nameListDataObj = params.get("nameListData");
            if (nameListDataObj == null) return fail("名单数据不能为空");

            // 2️⃣ 获取设备ID列表并转换为Integer集合
            Object deviceIdsObj = params.get("deviceIds");
            if (!(deviceIdsObj instanceof List)) return fail("设备列表不能为空");
            List<Integer> deviceIds = ((List<?>) deviceIdsObj).stream()
                    .map(id -> Integer.parseInt(id.toString()))
                    .collect(Collectors.toList());

            // 设备数量限制
            if (deviceIds.size() > 20) return fail("设备列表不能超过20个");

            // 3️⃣ 转成 NameList 对象（模板）
            NameList nameListTemplate = objectMapper.convertValue(nameListDataObj, NameList.class);

            // 4️⃣ 遍历每个设备
            for (Integer deviceId : deviceIds) {
                Device device = deviceService.getById(Long.valueOf(deviceId));
                String videosJson = device.getVideosJson();

                // 如果设备没有摄像头列表
                if (videosJson == null || videosJson.trim().isEmpty()) {
                    logger.warn("⚠️ 设备 {} 的 videosJson 为空", deviceId);
                    failedList.add("设备 " + deviceId + " 摄像头列表为空");
                    continue;
                }

                // 解析摄像头JSON数组
                JSONArray jsonArray = com.alibaba.fastjson2.JSON.parseArray(videosJson);
                if (jsonArray == null || jsonArray.isEmpty()) {
                    logger.warn("⚠️ 设备 {} 的摄像头列表为空", deviceId);
                    failedList.add("设备 " + deviceId + " 摄像头列表为空");
                    continue;
                }

                // 5️⃣ 遍历设备下每个摄像头
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject cameraObject = jsonArray.getJSONObject(i);
                    Integer videoId = cameraObject.getInteger("videoId");
                    if (videoId == null) {
                        logger.warn("⚠️ 设备 {} 第 {} 个摄像头 videoId 为空", deviceId, i);
                        failedList.add("设备 " + deviceId + " 第 " + (i + 1) + " 个摄像头 videoId 为空");
                        continue;
                    }

                    // 6️⃣ 获取对应 session
                    HashMap<String, Object> reqparams = new HashMap<>();
                    reqparams.put("deviceId", deviceId);
                    reqparams.put("videoId", videoId);
                    // 校验并获取 DeviceSession（包含 deviceId / videoId 校验）
                    DeviceSession session = validateAndGetDeviceSession(reqparams);
                    if (session == null) {
                        logger.warn("⚠️ 获取session失败: {} {}", deviceId, videoId);
                        failedList.add("设备 " + deviceId + " 视频 " + videoId + " session 无效");
                        continue;
                    }

                    // 7️⃣ 尝试插入名单
                    try {
                        carDoorDeviceManager.addVechileList(session.getUserID(), Collections.singletonList(nameListTemplate));
                    } catch (Exception e) {
                        logger.error("设备 {} 视频 {} 插入失败", deviceId, videoId, e);
                        failedList.add("设备 " + deviceId + " 视频 " + videoId + " 插入失败");
                    }
                }
            }

            // 8️⃣ 返回结果：全部成功 or 部分失败
            if (failedList.isEmpty()) {
                return success("添加授权名单成功");
            } else {
                Map<String, Object> result = success("部分设备/摄像头插入失败");
                result.put("failedList", failedList);
                return result;
            }
        } catch (Exception e) {
            logger.error("添加授权名单列表失败", e);
            return fail("添加授权名单列表失败");
        }
    }

    /**
     * 更新名单
     * 遍历前端传来的设备列表和摄像头列表，更新每个视频的授权名单
     * 支持部分失败记录
     * @param params 更新参数，包含：
     *   - nameListData: 授权名单对象
     *   - deviceIds: 设备ID列表
     * @return Map<String,Object> 返回操作结果和失败列表（如果有）
     */
    @PostMapping("/update")
    public Map<String, Object> update(@RequestBody Map<String, Object> params) {
        List<String> failedList = new ArrayList<>(); // 记录失败的设备/摄像头
        try {
            // 1️⃣ 获取名单数据
            Object nameListDataObj = params.get("nameListData");
            if (nameListDataObj == null) return fail("名单数据不能为空");

            // 2️⃣ 获取设备ID列表并转换
            Object deviceIdsObj = params.get("deviceIds");
            if (!(deviceIdsObj instanceof List)) return fail("设备列表不能为空");
            List<Integer> deviceIds = ((List<?>) deviceIdsObj).stream()
                    .map(id -> Integer.parseInt(id.toString()))
                    .collect(Collectors.toList());
            if (deviceIds.size() > 20) return fail("设备列表不能超过20个");

            // 3️⃣ 转成 NameList 对象
            NameList nameListTemplate = objectMapper.convertValue(nameListDataObj, NameList.class);

            // 4️⃣ 遍历设备
            for (Integer deviceId : deviceIds) {
                Device device = deviceService.getById(Long.valueOf(deviceId));
                String videosJson = device.getVideosJson();
                if (videosJson == null || videosJson.trim().isEmpty()) {
                    logger.warn("⚠️ 设备 {} 的 videosJson 为空", deviceId);
                    failedList.add("设备 " + deviceId + " 摄像头列表为空");
                    continue;
                }

                JSONArray jsonArray = com.alibaba.fastjson2.JSON.parseArray(videosJson);
                if (jsonArray == null || jsonArray.isEmpty()) {
                    logger.warn("⚠️ 设备 {} 的摄像头列表为空", deviceId);
                    failedList.add("设备 " + deviceId + " 摄像头列表为空");
                    continue;
                }

                // 5️⃣ 遍历摄像头
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject cameraObject = jsonArray.getJSONObject(i);
                    Integer videoId = cameraObject.getInteger("videoId");
                    if (videoId == null) {
                        logger.warn("⚠️ 设备 {} 第 {} 个摄像头 videoId 为空", deviceId, i);
                        failedList.add("设备 " + deviceId + " 第 " + (i + 1) + " 个摄像头 videoId 为空");
                        continue;
                    }

                    HashMap<String, Object> reqparams = new HashMap<>();
                    reqparams.put("deviceId", deviceId);
                    reqparams.put("videoId", videoId);
                    // 校验并获取 DeviceSession（包含 deviceId / videoId 校验）
                    DeviceSession session = validateAndGetDeviceSession(reqparams);
                    if (session == null) {
                        logger.warn("⚠️ 获取session失败: {} {}", deviceId, videoId);
                        failedList.add("设备 " + deviceId + " 视频 " + videoId + " session 无效");
                        continue;
                    }

                    // 6️⃣ 尝试更新名单
                    try {
                        carDoorDeviceManager.updateVechileList(session.getUserID(), Collections.singletonList(nameListTemplate));
                    } catch (Exception e) {
                        logger.error("设备 {} 视频 {} 更新失败", deviceId, videoId, e);
                        failedList.add("设备 " + deviceId + " 视频 " + videoId + " 更新失败");
                    }
                }
            }

            // 7️⃣ 返回结果
            if (failedList.isEmpty()) {
                return success("更新授权名单成功");
            } else {
                Map<String, Object> result = success("部分设备/摄像头更新失败");
                result.put("failedList", failedList);
                return result;
            }
        } catch (Exception e) {
            logger.error("更新授权名单列表失败", e);
            return fail("更新授权名单列表失败");
        }
    }

    /**
     * 删除名单
     * @param params 删除参数
     * @return success、msg
     */
    @PostMapping("/delete")
    public Map<String, Object> delete(@RequestBody Map<String, Object> params) {
        try {
            // 校验并获取 DeviceSession（包含 deviceId / IP / session 校验）
            DeviceSession session = validateAndGetDeviceSession(params);

            Object listObj = params.get("list");
            if (listObj == null) {
                return fail("名单列表不能为空");
            }

            // 显式将 LinkedHashMap 集合转成 List<NameList>
            List<NameList> nameLists = objectMapper.convertValue(
                    listObj,
                    new TypeReference<List<NameList>>() {}
            );

            if (nameLists == null || nameLists.isEmpty()) {
                return fail("名单列表不能为空");
            }

            if (nameLists.size() > 20) {
                return fail("名单列表不能超过20个");
            }

            Map<String, Object> result = carDoorDeviceManager.deleteVechileList(session.getUserID(), nameLists);
            return success("删除授权名单列表成功",  result);
        } catch (Exception e) {
            logger.error("删除授权名单列表失败", e);
            return fail("删除授权名单列表失败");
        }
    }

    /**
     * 校验设备信息并获取 DeviceSession
     * @param params 请求参数 Map
     * @return DeviceSession 如果校验通过
     * @throws IllegalArgumentException 如果校验失败，抛出异常（可由全局异常处理器捕获）
     */
    /**
     * 校验设备信息并获取 DeviceSession (支持失效自动重连)
     */
    private DeviceSession validateAndGetDeviceSession(Map<String, Object> params) {
        // 1. 校验 deviceId (保持原样)
        Object idObj = params.get("deviceId");
        if (idObj == null) throw new IllegalArgumentException("deviceId不能为空");
        Long deviceId = Long.parseLong(idObj.toString());

        // 2. 查询设备 (保持原样)
        Device device = deviceService.getById(deviceId);
        if (device == null || device.getIp() == null || device.getIp().trim().isEmpty()) {
            throw new IllegalArgumentException("设备不存在或未配置IP, deviceId=" + deviceId);
        }

        // 3. 解析 videoId (保持原样)
        int videoId = params.containsKey("videoId") ?
                (params.get("videoId") instanceof Number ?
                        ((Number) params.get("videoId")).intValue() :
                        Integer.parseInt(params.get("videoId").toString().trim()))
                : 0;

        String videosJson = device.getVideosJson();
        if (videosJson == null || videosJson.trim().isEmpty()) {
            throw new IllegalArgumentException("设备未配置视频通道信息");
        }

        JSONArray jsonArray = JSON.parseArray(videosJson);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            JSONObject video = jsonObject.getJSONObject("videoInfo");
            if (video != null ) {
                String ip = video.getString("ip");
                if (!isValidIp(ip)) throw new IllegalArgumentException("设备IP格式非法");

                // --- 仅修改这里：增加自动登录逻辑 ---
                DeviceSession session = carCameraDeviceManager.getDeviceSession(ip);
                if (session == null) {
                    logger.info("设备 {} 会话失效，尝试自动重连...", ip);
                    // 尝试登录
                    session = carCameraDeviceManager.loginDevice(ip, (short)8000, device.getAccount(), device.getPassword());
                }

                if (session == null) {
                    throw new IllegalArgumentException("设备连接已断开且自动登录失败：" + ip);
                }
                return session;
            }
        }
        return null;
    }




    private DeviceSession validateAndGetDeviceSession2(Map<String, Object> params) {
        // 1. 校验 deviceId
        Object idObj = params.get("deviceId");
        if (idObj == null) {
            throw new IllegalArgumentException("deviceId不能为空");
        }
        Long deviceId;
        try {
            deviceId = Long.parseLong(idObj.toString());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("deviceId格式错误");
        }

        // 2. 查询设备
        Device device = deviceService.getById(deviceId);
        if (device == null || device.getIp() == null || device.getIp().trim().isEmpty()) {
            throw new IllegalArgumentException("设备不存在或未配置IP, deviceId=" + deviceId);
        }
//        int videoId = params.containsKey("videoId") ? Integer.parseInt((String)params.get("videoId")) : 0;n
        //先判断是string还是int类型
        int videoId = params.containsKey("videoId") ?
                (params.get("videoId") instanceof Number ?
                        ((Number) params.get("videoId")).intValue() :
                        Integer.parseInt(params.get("videoId").toString().trim()))
                : 0;
        String videosJson = device.getVideosJson();

        if (videosJson == null || videosJson.trim().isEmpty()) {
            logger.warn("设备未配置视频通道信息，请先配置视频通道信息");
            throw new IllegalArgumentException("设备未配置视频通道信息，请先配置视频通道信息");
        }
        JSONArray jsonArray = JSON.parseArray(videosJson);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            JSONObject video = jsonObject.getJSONObject("videoInfo");
            if (video != null) {
                if (video.getInteger("id") == videoId) {
                    String ip = video.getString("ip");
                    if (!isValidIp(ip)) {
                        logger.warn("设备未配置视频通道信息，请先配置视频通道信息");
                        throw new IllegalArgumentException("设备未配置视频通道信息，请先配置视频通道信息");
                    }
                    DeviceSession session = carCameraDeviceManager.getDeviceSession(ip);
                    if (session == null) {
                        logger.warn("设备未登录或不存在：" + ip);
                        throw new IllegalArgumentException("设备未登录或不存在：" + ip);
                    }
                    return session;
                }
            }
        }
        return null;
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
