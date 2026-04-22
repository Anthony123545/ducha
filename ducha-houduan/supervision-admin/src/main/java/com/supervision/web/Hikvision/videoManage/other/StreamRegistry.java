package com.supervision.web.Hikvision.videoManage.other;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/**
 * 简单注册/读取 用于 Controller 展示
 */
public class StreamRegistry {

    public static class Info {
        public String cameraId;
        public String rtsp;
        public String hlsUrl;
        public String state;
        public long lastUpdated;
    }

    private static final ConcurrentHashMap<String, Info> REG = new ConcurrentHashMap<>();

    public static void put(String cameraId, String rtsp, String hlsUrl, String state) {
        Info info = new Info();
        info.cameraId = cameraId;
        info.rtsp = rtsp;
        info.hlsUrl = hlsUrl;
        info.state = state;
        info.lastUpdated = System.currentTimeMillis();
        REG.put(cameraId, info);
    }

    public static Info get(String cameraId) { return REG.get(cameraId); }

    public static Map<String, Info> all() { return REG; }

    public static void remove(String cameraId) { REG.remove(cameraId); }
}
