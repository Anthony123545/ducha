package com.supervision.web.Hikvision.videoManage.util;

import java.util.concurrent.ConcurrentHashMap;

public class StreamManager {

    // 摄像头ID -> HLS文件名（如 camera1.m3u8）
    private static final ConcurrentHashMap<String, String> activeStreams = new ConcurrentHashMap<>();

    // 摄像头ID -> 推流状态：success / pending / failed
    private static final ConcurrentHashMap<String, String> streamStatus = new ConcurrentHashMap<>();

    // 摄像头ID -> HLS 完整 URL
    private static final ConcurrentHashMap<String, String> streamUrls = new ConcurrentHashMap<>();

    public static boolean isStreaming(String cameraId) {
        return activeStreams.containsKey(cameraId);
    }

    public static String getHlsFileName(String cameraId) {
        return activeStreams.get(cameraId);
    }

    public static String getHlsUrl(String cameraId) {
        return streamUrls.get(cameraId);
    }

    public static String getStreamStatus(String cameraId) {
        return streamStatus.getOrDefault(cameraId, "pending");
    }

    public static void addStream(String cameraId, String hlsFileName, String hlsUrl) {
        activeStreams.put(cameraId, hlsFileName);
        streamUrls.put(cameraId, hlsUrl);
        streamStatus.put(cameraId, "success");
    }

    public static void markFailed(String cameraId) {
        streamStatus.put(cameraId, "failed");
    }

    public static void removeStream(String cameraId) {
        activeStreams.remove(cameraId);
        streamUrls.remove(cameraId);
        streamStatus.remove(cameraId);
    }
}