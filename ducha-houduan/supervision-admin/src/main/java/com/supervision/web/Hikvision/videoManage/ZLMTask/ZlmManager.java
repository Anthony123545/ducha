package com.supervision.web.Hikvision.videoManage.ZLMTask;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * 高可用 ZLMediaKit Manager —— 支持 30 路以上 RTSP 拉流。
 * 功能：
 * 1. RTSP 拉流失败自动重试
 * 2. 自动关闭无人观看的流
 * 3. 串流缓存，避免重复 addStreamProxy
 * 4. 代理状态检测（tracks、readerCount）
 * 5. FLV 播放地址生成
 */
@Slf4j
@Component
public class ZlmManager {

    private static final Logger logger = LoggerFactory.getLogger(ZlmManager.class);

    private final RestTemplate rest = new RestTemplate();

    @Value("${zlm.host:http://127.0.0.1:8080}")
    private String zlmApiBase;

    @Value("${zlm.httpHost:http://127.0.0.1:8080}")
    private String zlmHttpHost;

    @Value("${zlm.secret:}")
    private String secret;

    @Value("${zlm.schema:rtmp}")
    private String schema;

    @Value("${zlm.app:live}")
    private String app;

    @Value("${zlm.vhost:__defaultVhost__}")
    private String vhost;

    private final Map<String, StreamInfo> streamMap = new ConcurrentHashMap<>();

    /**
     * 启动 RTSP → ZLM 的代理流。
     *
     * 核心流程：
     * 1. 若 ZLM 中该流已在线 → 直接返回 true（避免重复拉流）
     * 2. 若本地缓存存在但 ZLM 内不存在 → 正常重新拉流
     * 3. 调用 addStreamProxy 创建 ZLM 代理流
     * 4. 轮询等待流在 ZLM 中真正上线（tracks ≥ 1）
     * 5. 若未上线 → 自动执行 close_stream 清理代理
     *
     * 线程安全：方法加 synchronized，避免并发重复拉流。
     *
     * @param cameraId 摄像头 ID（如 "5_1"）
     * @param rtspUrl  摄像头 RTSP 地址
     * @return true = 启动成功；false = 启动失败
     */
    public synchronized boolean startStream(String cameraId, String rtspUrl) {
        // 获取或创建本地缓存
        StreamInfo info = streamMap.computeIfAbsent(cameraId,
                new Function<String, StreamInfo>() {
                    @Override
                    public StreamInfo apply(String k) {
                        return new StreamInfo(cameraId, rtspUrl);
                    }
                });

        // 若之前手动停止，则允许系统重新管理该流
        info.manualStop = false;

//        // ⭐ 启动前重置失败状态
//        info.failCount = 0;
//        info.lastRetryTime = System.currentTimeMillis();

        // 1. ZLM 内已存在 → 不需要 addStreamProxy
        if (isRunning(cameraId)) {
            logger.info("[ZLM] 流 {} 已在线，直接返回", cameraId);
            info.running = true;
            return true;
        }

        // 2. 发送 addStreamProxy 请求
        String url = buildAddProxyUrl(cameraId, rtspUrl);
        logger.info("[ZLM] addStreamProxy => {}", url);

        Map<String, Object> respBody = null;
        try {
            ResponseEntity<Map> resp = rest.getForEntity(url, Map.class);
            respBody = resp.getBody();
        } catch (Exception e) {
            logger.error("[ZLM] addStreamProxy 调用失败 camera={} url={}", cameraId, rtspUrl, e);
            info.running = false;
            return false;
        }

        // 校验 ZLM 返回值
        if (respBody == null || !Integer.valueOf(0).equals(respBody.get("code"))) {
            logger.warn("[ZLM] addStreamProxy 返回错误 camera={}", cameraId);
            info.running = false;
            return false;
        }

        // 3. 等待流真正上线（最多 4.5 秒，15 次 * 300ms）
        for (int i = 0; i < 15; i++) {
            Map<String, Object> zlmInfo = getInfo(cameraId);

            Object tracksObj = zlmInfo.get("tracks");
            if (tracksObj instanceof List) {
                List<?> tracks = (List<?>) tracksObj;
                if (!tracks.isEmpty()) {
                    logger.info("[ZLM] 流 {} 已成功拉起", cameraId);
                    info.running = true;
                    info.lastStartTime = System.currentTimeMillis();
                    // 只有成功后才清零失败计数
                    info.failCount = 0;
                    info.lastRetryTime = 0;
                    return true;
                }
            }

            try {
                Thread.sleep(300);
            } catch (InterruptedException ignore) {}
        }

        // 超时 → addStreamProxy 成功但 tracks 未出现
        logger.warn("[ZLM] 流 {} tracks 未上线，执行清理", cameraId);
        stopStream(cameraId);

        info.running = false;
        return false;
    }

    /**
     * 停止 RTSP 代理流（幂等）。
     *
     * 逻辑说明：
     * 1. 调用 ZLM close_stream
     * 2. 若失败 → 查询流是否实际已不存在
     * 3. 若已不存在 → 视为成功（幂等）
     * 4. 最终从本地缓存移除
     *
     * 保证重复调用 stopStream 不会出错。
     */
    public synchronized boolean stopStream(String cameraId) {
        StreamInfo info = streamMap.get(cameraId);
        if (info == null) return true;

        // 标记为手动停止，避免健康检查干预
        info.manualStop = true;

        try {
            String url = buildCloseStreamUrl(cameraId);
            logger.info("[ZLM] close_stream => {}", url);

            ResponseEntity<Map> resp = rest.getForEntity(url, Map.class);
            Map<String, Object> body = resp.getBody();

            boolean ok =
                    resp.getStatusCode().is2xxSuccessful()
                            && body != null
                            && Integer.valueOf(0).equals(body.get("code"));

            if (!ok) {
                // 若 ZLM 内已不存在 → 幂等成功
                Map<String, Object> m = getInfo(cameraId);
                if (m.isEmpty()) {
                    logger.info("[ZLM] 流 {} 不存在，视为正常关闭", cameraId);
                    streamMap.remove(cameraId);
                    return true;
                }
                return false;
            }

            streamMap.remove(cameraId);
            return true;

        } catch (Exception e) {

            // 关闭失败，但 ZLM 内已不存在 → 幂等成功
            Map<String, Object> m = getInfo(cameraId);
            if (m.isEmpty()) {
                streamMap.remove(cameraId);
                return true;
            }
            return false;
        }
    }

    /**
     * 查询 ZLM 内部是否存在指定 stream。
     *
     * @return 若存在则返回 ZLM 返回的 Map；否则返回空 Map。
     */
    public Map<String, Object> getInfo(String cameraId) {
        try {
            String url = String.format("%s/index/api/getMediaList?secret=%s",
                    zlmApiBase, encode(secret));

            ResponseEntity<Map> resp = rest.getForEntity(url, Map.class);
            Map body = resp.getBody();
            if (body == null) return Collections.emptyMap();

            Object data = body.get("data");
            if (data instanceof List) {
                List list = (List) data;
                for (Object o : list) {
                    if (o instanceof Map) {
                        Map m = (Map) o;
                        if (cameraId.equals(String.valueOf(m.get("stream")))) {
                            return m;
                        }
                    }
                }
            }

        } catch (Exception e) {
            logger.warn("[ZLM] getInfo error camera={}", cameraId, e);
        }
        return Collections.emptyMap();
    }

    /**
     * 是否在线（通过 ZLM 查询）
     */
    public boolean isRunning(String cameraId) {
        return !getInfo(cameraId).isEmpty();
    }

    /**
     * FLV 播放地址
     */
    public String getFlvUrl(String cameraId) {
        String host = zlmHttpHost.endsWith("/") ? zlmHttpHost.substring(0, zlmHttpHost.length() - 1) : zlmHttpHost;
        return String.format("%s/%s/%s.live.flv", host, app, cameraId);
    }

    // ==================== Health Check ====================
    /**
     * 健康检查：每 5 秒执行一次。
     *
     * 功能：
     * 1. 自动重启异常掉线的流（指数退避）
     * 2. 超过 20 次失败 → 自动移除缓存
     * 3. 在线的流 → 检查无人观看是否超过 60 秒
     * 4. 避免对手动停止的流进行干预
     */
    @Scheduled(fixedRate = 5000)
    public void healthCheck() {
        long now = System.currentTimeMillis();
        Iterator<Map.Entry<String, StreamInfo>> iter = streamMap.entrySet().iterator();

        while (iter.hasNext()) {
            Map.Entry<String, StreamInfo> entry = iter.next();
            StreamInfo info = entry.getValue();
            String cameraId = info.getCameraId();

            // ① 手动停止的流不处理
            if (info.manualStop) {
                logger.debug("[ZLM] 流 {} 手动停止，跳过", cameraId);
                continue;
            }

            boolean online = isRunning(cameraId);

            // ② 掉线
            if (!online) {
                // 首次掉线 → failCount 从 1 开始
                if (info.failCount == 0) {
                    info.failCount = 1;
                    info.lastRetryTime = now;
                    logger.warn("[ZLM] 流 {} 掉线，首次重拉", cameraId);
                    startStream(cameraId, info.rtspUrl);
                    continue;
                }

                // 指数退避
                long delaySec = retryDelay(info.failCount);
                long delayMs = delaySec * 1000;

                // 未到重试间隔
                if (now - info.lastRetryTime < delayMs) {
                    logger.debug("[ZLM] 流 {} 等待 {} 秒后重试", cameraId, delaySec);
                    continue;
                }

                // 到时间 → 尝试重拉
                info.lastRetryTime = now;
                info.failCount++; // 第二次及以后才增加失败次数
                logger.warn("[ZLM] 流 {} 重试 (failCount={}, delay={}s)",
                        cameraId, info.failCount, delaySec);

                if (info.failCount >= 10) {
                    logger.error("[ZLM] 流 {} 超过 10 次重试，移除", cameraId);
                    iter.remove();
                    continue;
                }

                startStream(cameraId, info.rtspUrl);
                continue;
            }

            // ③ 在线 → 状态恢复
            info.running = true;
            info.failCount = 0;
            info.lastRetryTime = 0;

            // readerCount 检查
            Map<String, Object> zlmInfo = getInfo(cameraId);
            int reader = 0;
            Object r = zlmInfo.get("readerCount");
            if (r instanceof Number) reader = ((Number) r).intValue();

            // ④ 无人观看超过 60 秒 → 自动关闭
            if (reader == 0 && now - info.lastStartTime > 60_000) {
                logger.info("[ZLM] 流 {} 超过 60 秒无人观看，自动关闭", cameraId);
                stopStream(cameraId);
            }
        }
    }

    private int retryDelay(int failCount) {
        if (failCount <= 1) return 1;
        if (failCount == 2) return 5;
        if (failCount == 3) return 5;
        if (failCount == 4) return 10;
        if (failCount == 5) return 10;
        if (failCount == 6) return 20;
        if (failCount == 7) return 20;
        return 30;
    }

    /**
     * 构建 ZLM addStreamProxy URL（包含所有可选参数）
     */
    private String buildAddProxyUrl(String cameraId, String rtspUrl) {
        UriComponentsBuilder builder = UriComponentsBuilder
                // -----------------------------
                // ⭐ 必选配置
                // -----------------------------
                .fromHttpUrl(zlmApiBase + "/index/api/addStreamProxy")
                .queryParam("secret", secret) // api 操作密钥(配置文件配置)
                .queryParam("vhost", vhost) // 添加的流的虚拟主机，例如__defaultVhost__
                .queryParam("app", app) // 添加的流的应用名，例如 live
                .queryParam("stream", cameraId) // 添加的流的 id 名，例如 test
                .queryParam("url", rtspUrl) // 拉流地址，例如 rtmp://live.hkstv.hk.lxdns.com/live/hks2
                // -----------------------------
                // ⭐ 拉流核心配置
                // -----------------------------
                .queryParam("retry_count", 5) // 拉流重试次数，默认为-1 无限重试
                .queryParam("rtp_type", 1) // rtsp 拉流时，拉流方式，0：tcp，1：udp，2：组播
                .queryParam("timeout_sec", 5) // 拉流超时时间，单位秒，float 类型
                .queryParam("auto_close", false) // 无人观看是否自动关闭流(不触发无人观看 hook)
                // -----------------------------
                // ⭐ 输出协议（哪些协议要转）
                // -----------------------------
                .queryParam("enable_hls", false)      // 是否转换成 hls-mpegts 协议
                .queryParam("enable_hls_fmp4", false) // 是否转换成 hls-fmp4 协议
                .queryParam("enable_mp4", false)      // 是否允许 mp4 录制
                .queryParam("enable_rtsp", false)      // 是否转 rtsp 协议
                .queryParam("enable_rtmp", true)      // 是否转 rtmp/flv 协议
                .queryParam("enable_ts", false)       // 是否转 http-ts/ws-ts 协议
                .queryParam("enable_fmp4", false)     // 是否转 http-fmp4/ws-fmp4 协议
                // -----------------------------
                // ⭐ 按需生成（是否需要观看者才生成协议）
                // -----------------------------
                .queryParam("hls_demand", false)      // 该协议是否有人观看才生成
                .queryParam("rtsp_demand", false)     // 该协议是否有人观看才生成
                .queryParam("rtmp_demand", false)     // 该协议是否有人观看才生成
                .queryParam("ts_demand", false)       // 该协议是否有人观看才生成
                .queryParam("fmp4_demand", false)     // f该协议是否有人观看才生成
                // -----------------------------
                // ⭐ 音频控制
                // -----------------------------
                .queryParam("enable_audio", false)    // 转协议时是否开启音频
                .queryParam("add_mute_audio", false)  // 转协议时，无音频是否添加静音 aac 音频
                // -----------------------------
                // ⭐ MP4 录制（已禁用，仅保留默认参数）
                // -----------------------------
                .queryParam("mp4_save_path", "")      // mp4 录制文件保存根目录，置空使用默认
                .queryParam("mp4_max_second", 60)     // mp4 录制切片大小，单位秒
                .queryParam("mp4_as_player", false)   // MP4 录制是否当作观看者参与播放人数计数
                // -----------------------------
                // ⭐ HLS 保存路径（禁用 HLS 时无效）
                // -----------------------------
                .queryParam("hls_save_path", "")      // hls 文件保存保存根目录，置空使用默认
                // -----------------------------
                // ⭐ 时间戳控制
                // -----------------------------
                .queryParam("modify_stamp", 0);       // 该流是否开启时间戳覆盖(0:绝对时间戳/1:系统时间戳/2:相对时间戳)
        return builder.toUriString();
    }

    /**
     * 构造 ZLM delStreamProxy（删除拉流代理）接口 URL
     *
     * 文档说明：
     *   /index/api/delStreamProxy
     *
     * 示例：
     *   http://127.0.0.1/index/api/delStreamProxy?secret=xxxx&key=__defaultVhost__/proxy/0
     *
     * 参数说明：
     *   secret：ZLM 配置文件中的 api 操作密钥
     *   key：addStreamProxy 接口返回的唯一标识（格式示例：__defaultVhost__/proxy/0）
     *
     * 返回说明（示例）：
     * {
     *   "code": 0,
     *   "data": {
     *     "flag": true   // 删除是否成功
     *   }
     * }
     *
     * 注意：
     *   如果流注册成功，也可以使用 close_stream 接口替代，但 delStreamProxy 是官方推荐关闭代理流的方式。
     */
    private String buildDelStreamProxy(String key) {
        return UriComponentsBuilder
                .fromHttpUrl(zlmApiBase + "/index/api/delStreamProxy")
                .queryParam("secret", secret) // api 操作密钥(配置文件配置)
                .queryParam("key", key) // addStreamProxy 接口返回的 key
                .toUriString();   // 自动构建与 URL 编码
    }

    /**
     * 构造 ZLM close_stream API URL（推荐使用 UriComponentsBuilder）
     *
     * ZLM 文档示例：
     *   http://127.0.0.1/index/api/close_stream?schema=rtmp&vhost=__defaultVhost__&app=live&stream=0&force=1
     *
     * 参数说明：
     *   secret：接口密钥
     *   schema：协议（rtmp/rtsp/http-flv/hls/rtc 等）
     *   vhost：虚拟主机，一般是 __defaultVhost__
     *   app：应用名（如 live）
     *   stream：流 ID（cameraId）
     *   force：强制关闭，即使有人观看也关闭
     */
    private String buildCloseStreamUrl(String cameraId) {
        return UriComponentsBuilder
                .fromHttpUrl(zlmApiBase + "/index/api/close_stream")
                .queryParam("secret", secret) // api 操作密钥(配置文件配置)
                .queryParam("schema", schema) // 协议，例如 rtsp 或 rtmp
                .queryParam("vhost", vhost) // 虚拟主机，例如__defaultVhost__
                .queryParam("app", app) // 应用名，例如 live
                .queryParam("stream", cameraId) // 流 id，例如 test
                .queryParam("force", true) // 是否强制关闭(有人在观看是否还关闭)
                .toUriString(); // 自动编码与构建 URL
    }

    private String encode(String s) throws Exception {
        return URLEncoder.encode(s == null ? "" : s, "UTF-8");
    }

    /**
     * 单路摄像头流的运行状态信息
     */
    @Data
    public static class StreamInfo {

        /** 摄像头唯一 ID（例如：5_1） */
        private final String cameraId;

        /** 摄像头 RTSP 地址 */
        private final String rtspUrl;

        /**
         * 是否为手动停止的流。
         * true → 用户主动 stopStream，不再自动重启
         * false → 异常停止可自动重启
         */
        public boolean manualStop = false;

        /**
         * 当前流是否在线（由 healthCheck 更新）
         */
        public boolean running = false;

        /**
         * 连续失败次数（用于指数退避）
         * 每次 isRunning=false → failCount++
         * 一旦成功 running=true → failCount 重置为 0
         */
        public int failCount = 0;

        /**
         * 上一次重试时间
         * healthCheck 中用于判断是否到达退避时间
         */
        public long lastRetryTime = 0;

        /**
         * 流最后一次成功启动的时间
         * 用于自动关闭“无人观看超过 60 秒”的流
         */
        public long lastStartTime = 0;

        /**
         * 构造器
         *
         * @param id 摄像头 ID
         * @param url 摄像头 RTSP 地址
         */
        StreamInfo(String id, String url) {
            this.cameraId = id;
            this.rtspUrl = url;
        }
    }

    public Map<String, StreamInfo> getStreamMap() {
        return streamMap;
    }
}