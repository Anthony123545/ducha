package com.supervision.web.Hikvision.carCamera.web.controller;

import com.supervision.web.Hikvision.carCamera.web.util.CarCameraDeviceManager;
import com.supervision.web.Hikvision.util.DeviceSession;
import com.supervision.web.Hikvision.videoManage.domain.Video;
import com.supervision.web.Hikvision.videoManage.service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/controlCarCamera")
public class CarCameraController {

    private static final Logger logger = LoggerFactory.getLogger(CarCameraController.class);

    @Autowired
    private CarCameraDeviceManager carCameraDeviceManager;

    @Autowired
    private VideoService videoService;

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
     * 登录设备
     */
    @PostMapping("/login")
    public Map<String, Object> loginDevice(@RequestBody Map<String, Object> params) {
        try {
            // 获取 videoId 并校验
            Object idObj = params.get("videoId");
            if (idObj == null) {
                return fail("videoId不能为空");
            }

            Long videoId;
            try {
                videoId = Long.parseLong(idObj.toString());
            } catch (NumberFormatException e) {
                return fail("videoId格式错误");
            }

            Video video = videoService.getById(videoId);
            if (video == null) return fail("设备不存在");

            // 登录设备
            DeviceSession session = carCameraDeviceManager.loginDevice(
                    video.getIp(),
                    video.getPort().shortValue(),
                    video.getUsername(),
                    video.getPassword()
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
     * 查询设备状态
     */
    @PostMapping("/status")
    public Map<String, Object> getDeviceStatus(@RequestBody Map<String, Object> params) {
        try {
            // 获取 videoId 并校验
            Object idObj = params.get("videoId");
            if (idObj == null) {
                return fail("videoId不能为空");
            }

            Long videoId;
            try {
                videoId = Long.parseLong(idObj.toString());
            } catch (NumberFormatException e) {
                return fail("videoId格式错误");
            }

            Video video = videoService.getById(videoId);
            if (video == null) return fail("设备不存在");

            DeviceSession session = carCameraDeviceManager.getDeviceSession(video.getIp());

            Map<String, Object> data = new HashMap<>();
            data.put("online", session != null);

            if (session != null) {
                data.put("ip", session.getIp());
                data.put("userID", session.getUserID());
                return success("设备在线", data);
            } else {
                return fail("设备未登录或登录失败");
            }
        } catch (Exception e) {
            logger.error("查询设备状态异常", e);
            return fail("查询设备状态异常");
        }
    }

    /**
     * 启动/撤销布防
     */
    @PostMapping("/alarm")
    public Map<String, Object> controlAlarm(@RequestBody Map<String, Object> req) {
        String ip = (String) req.get("ip");
        String action = (String) req.get("action"); // "start" 或 "stop"

        DeviceSession session = carCameraDeviceManager.getDeviceSession(ip);
        if (session == null) return fail("设备未登录");

        if ("start".equalsIgnoreCase(action)) {
            // TODO: 调用布防
            // carCameraDeviceManager.handleCase(session.getUserID(), Map.of("caseId", "1"));
            return success("布防已启动");
        }

        if ("stop".equalsIgnoreCase(action)) {
            // TODO: 调用撤防
            // carCameraDeviceManager.handleCase(session.getUserID(), Map.of("caseId", "2"));
            return success("布防已撤销");
        }

        return fail("未知操作: " + action);
    }

    /**
     * 工具：long 解析
     */
    private Long parseLong(Object obj) {
        if (obj == null) return null;
        try {
            return Long.parseLong(obj.toString());
        } catch (Exception e) {
            return null;
        }
    }
}
