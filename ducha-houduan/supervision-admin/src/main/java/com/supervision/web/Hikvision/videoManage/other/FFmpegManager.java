package com.supervision.web.Hikvision.videoManage.other;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 高性能 FFmpeg 管理器（可支持几十路并发）
 *
 * 主要功能：
 * - 固定线程池执行 ffmpeg 任务（避免频繁创建线程）
 * - 每路 task 单独管理（内含 Process、心跳、重试）
 * - 定时检查所有任务健康（自动重启）
 * - 提供 start/stop/getStatus/getPlayUrl 等方法供 Controller 调用
 */
@Slf4j
@Component
public class FFmpegManager {

    private static final Logger logger = LoggerFactory.getLogger(FFmpegManager.class);

    /**
     * 全局管理所有摄像头的推流任务
     * key : cameraId
     * val : FFmpegTask
     */
    private final ConcurrentHashMap<String, FFmpegTask> tasks = new ConcurrentHashMap<>();

    // 可配置线程池大小：默认 50（支持高并发）
    /**
     * 执行 FFmpeg 的线程池
     * 统一使用固定线程池，避免每个 ffmpeg 启动新线程导致线程爆炸
     */
    private final ExecutorService ffmpegExecutor;

    /**
     * 用于健康检查、HLS 垃圾清理的定时线程池
     */
    private final ScheduledExecutorService scheduler;

    @Value("${video.enableHlsInit:false}")
    private boolean enableHlsInit; // false 表示禁用 FFmpeg/HLS 推流

    /**
     * Nginx 访问前缀（用于前端播放）
     */
    @Value("${video.nginxProxyUrl}")
    private String nginxProxyUrl;

    /**
     * FFmpeg 执行文件路径
     */
    @Value("${video.ffmpegPath}")
    private String ffmpegPath;

    /**
     * HLS 输出目录
     */
    @Value("${video.filePath}")
    private String filePath;

    /**
     * 默认 HLS 输出文件格式
     */
    @Value("${video.videoFormat}")
    private String videoFormat;

    /**
     * 心跳超时阈值：超过 15 秒没有日志，则认为 ffmpeg 卡死
     */
    private final long heartbeatTimeoutMs = 15_000; // 15s 无日志认为卡死

    /**
     * 最大重试次数
     */
    private final int maxRetries = 3;

    /**
     * 退避延迟基数（自动重试时延迟）
     */
    private final long baseBackoffMs = 2_000L;

    public FFmpegManager() {
        // 默认最多同时执行 50 路（可调）
        this.ffmpegExecutor = Executors.newFixedThreadPool(50);
        this.scheduler = Executors.newScheduledThreadPool(2);
    }

    /**
     * 启动时执行：
     * 1. 创建 HLS 目录
     * 2. 启动任务健康检查（每 10 秒）
     */
    @PostConstruct
    public void init() {
        if (!enableHlsInit) {
//            logger.info("FFmpeg 推流功能已禁用，跳过初始化 HLS 目录与任务调度");
            return;
        }

        // 创建 HLS 基础目录
        try {
            Path base = Paths.get(filePath);
            if (!Files.exists(base)) Files.createDirectories(base);
            logger.info("HLS 基础目录已创建: {}", filePath);
        } catch (IOException e) {
            logger.warn("创建 HLS 基础目录失败: {}", filePath, e);
        }

        // 启动任务健康检查
        scheduler.scheduleAtFixedRate(this::healthCheckAll, 10, 10, TimeUnit.SECONDS);
        logger.info("FFmpegManager 初始化完成，FFmpeg={}", ffmpegPath);
    }

    /**
     * 启动/确保某路摄像头推流任务正在运行
     *
     * 📌 非阻塞：
     *     立即返回播放地址，不等待 ffmpeg 启动完成
     *
     * 📌 逻辑说明：
     *     1. 如果已有任务且健康 → 直接返回
     *     2. 否则停止旧任务
     *     3. 创建新任务并提交到线程池执行
     */
    public String startStream(String cameraId, String rtspUrl) {
        tasks.compute(cameraId, (k, existing) -> {
            if (existing != null && existing.isHealthy()) {
                // 如果已有健康任务，直接返回 existing
                return existing;
            }
            // 若旧任务异常 → 停止
            if (existing != null) {
                // 停掉旧的
                existing.stop();
            }
            // 创建新 ffmpeg Task
            FFmpegTask task = new FFmpegTask(cameraId, rtspUrl);
            // 提交到 ffmpegExecutor 执行（非阻塞）
            ffmpegExecutor.submit(() -> runTask(task));
            return task;
        });
        return getPlayUrl(cameraId);
    }

    /** stop */
    public boolean stopStream(String cameraId) {
        FFmpegTask task = tasks.remove(cameraId);
        if (task == null) return false;
        task.stop();
        // 不立即删文件，避免播放端正在读导致 404
        return true;
    }

    /** 返回播放地址（与 nginx 配置对应） */
    public String getPlayUrl(String cameraId) {
        String prefix = nginxProxyUrl.endsWith("/") ? nginxProxyUrl : nginxProxyUrl + "/";
        return prefix + cameraId + "/index.m3u8";
    }

    /** 获取状态字符串：RUNNING / STARTING / FAILED / STOPPED / PENDING */
    public String getStatus(String cameraId) {
        FFmpegTask t = tasks.get(cameraId);
        if (t == null) return "STOPPED";
        return t.getState().name();
    }

    /** 获取诊断信息（map） */
    public Map<String, Object> getInfo(String cameraId) {
        Map<String, Object> m = new HashMap<>();
        FFmpegTask t = tasks.get(cameraId);
        if (t == null) {
            m.put("state", "STOPPED");
            return m;
        }
        m.put("state", t.getState().name());
        m.put("rtsp", t.rtspUrl);
        m.put("playUrl", getPlayUrl(cameraId));
        m.put("lastHeartbeat", t.getLastHeartbeat());
        m.put("retries", t.retryCount.get());
        return m;
    }

    /**
     * 定时检查所有任务健康并自动重启
     */
    private void healthCheckAll() {
        // 注意：entrySet 遍历时不允许直接修改 Map
        // 这里只做判断，修改放线程池里执行
        for (Map.Entry<String, FFmpegTask> entry : tasks.entrySet()) {
            FFmpegTask task = entry.getValue();
            // 1. 用户主动停止（stopped = true） → 不做任何检查，不重启
            if (task.stopped.get()) {
                logger.debug("[healthCheck] camera={} 用户主动停止，不做自动重启", task.cameraId);
                continue;
            }
            // 2. FFmpeg 不健康 → 自动重启
            if (!task.isHealthy()) {
                logger.warn("[healthCheck] camera={} unhealthy (state={}), preparing to restart", task.cameraId, task.getState());
                ffmpegExecutor.submit(() -> {
                    tasks.compute(task.cameraId, (k, oldTask) -> {
                        // double-check：如果在提交任务过程中用户 stop 了，不再重启
                        if (oldTask != null && oldTask.stopped.get()) {
                            log.info("[healthCheck] camera={} 已被用户停止，取消自动重启", task.cameraId);
                            return oldTask; // 不重建
                        }
                        // 停掉旧任务
                        if (oldTask != null) {
                            oldTask.stop();
                        }
                        // 创建新任务并启动
                        FFmpegTask newTask = new FFmpegTask(task.cameraId, task.rtspUrl);
                        log.info("[healthCheck] camera={} 自动重启 FFmpeg", task.cameraId);

                        runTask(newTask); // 同步阻塞运行
                        return newTask;
                    });
                });
            }
        }
        // 3. 清理 HLS 目录
        garbageCollectHlsDirs();
    }

    /** delete directories with no active task older than 48 hours (configurable) */
    private void garbageCollectHlsDirs() {
        try {
            File base = new File(filePath);
            if (!base.exists() || !base.isDirectory()) return;
            long now = System.currentTimeMillis();
            File[] dirs = base.listFiles(File::isDirectory);
            if (dirs == null) return;
            for (File d : dirs) {
                String cam = d.getName();
                FFmpegTask t = tasks.get(cam);
                if (t != null && t.isHealthy()) continue;
                long lastMod = d.lastModified();
                if (now - lastMod > TimeUnit.HOURS.toMillis(48)) {
                    deleteDirRecursively(d.toPath());
                    log.info("garbageCollect: deleted old hls dir {}", d.getAbsolutePath());
                }
            }
        } catch (Exception ex) {
            log.warn("garbageCollectHlsDirs error", ex);
        }
    }

    private void deleteDirRecursively(Path dir) {
        try {
            if (!Files.exists(dir)) return;
            Files.walk(dir).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
        } catch (Exception e) {
            log.warn("删除目录失败: {}", dir, e);
        }
    }

    /**
     * 核心方法：执行 FFmpeg
     *
     * ⏳ 该方法会阻塞当前线程，直到 ffmpeg 退出（正常或异常）
     * 📝 线程由 ffmpegExecutor 提供
     *
     * 功能包括：
     * ------------------------------------------------------------
     * 1. 构建命令 + 启动 ffmpeg
     * 2. 读取 ffmpeg 输出日志（用于心跳判断）
     * 3. ffmpeg 异常退出 → 自动重试（指数退避）
     * 4. 达到最大失败次数 → 标记 FAILED
     */
    private void runTask(FFmpegTask task) {
        int attempt = 0;
        // 重试最多 maxRetries 次
        while (attempt <= maxRetries && !task.stopped.get()) {
            attempt++;
            task.retryCount.incrementAndGet();
            List<String> cmd = buildCommand(task.rtspUrl, task.cameraId);
            logger.info("[runTask] camera={} attempt={} cmd={}", task.cameraId, attempt, cmd);
            Process process = null;
            try {
                // 创建输出目录（HLS 分片 + m3u8）
                Path outDir = Paths.get(filePath, task.cameraId);
                if (!Files.exists(outDir)) Files.createDirectories(outDir);
                // 启动 ffmpeg
                ProcessBuilder pb = new ProcessBuilder(cmd);
                pb.redirectErrorStream(true); // 合并 stdout + stderr
                process = pb.start();

                task.bindProcess(process);
                task.setState(FFmpegState.RUNNING);

                // 读取 ffmpeg 输出作为心跳
                try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        // 任意输出即视为正常运行
                        task.updateHeartbeat();
                    }
                }
                // 等待 ffmpeg 结束
                int exit = process.waitFor();
                logger.warn("[runTask] camera={} ffmpeg exited code={}", task.cameraId, exit);
                // 如果不是 stop() 导致退出 → 失败
                if (!task.stopped.get()) {
                    task.setState(FFmpegState.FAILED);
                    // 指数退避：2s -> 4s -> 8s
                    long backoff = baseBackoffMs * (1L << (attempt - 1));
                    Thread.sleep(Math.min(backoff, 30_000L));
                }
            } catch (Exception e) {
                logger.error("[runTask] camera={} error", task.cameraId, e);
                task.setState(FFmpegState.FAILED);

                try { Thread.sleep(baseBackoffMs); } catch (InterruptedException ignored) {}
            } finally {
                // 杀进程
                if (process != null && process.isAlive()) {
                    process.destroyForcibly();
                }
                task.bindProcess(null);
            }
        }
        // 结束最终状态
        if (!task.stopped.get()) {
            task.setState(FFmpegState.FAILED);
        }
        logger.info("[runTask] finished camera={} finalState={}", task.cameraId, task.getState());
    }

    /**
     * 构建 FFmpeg 命令，将 RTSP 流转为 HLS（.m3u8 + .ts 切片）
     * 适合多路同时播放（30路左右），尽量减少 CPU 占用
     *
     * @param rtspUrl 摄像头 RTSP 地址
     * @param cameraId 摄像头 ID，用于生成文件目录
     * @return FFmpeg 命令参数列表
     */
    private List<String> buildCommand(String rtspUrl, String cameraId) {
        // TS 切片路径，例如: /your/path/<cameraId>/segment_0001.ts
        String outPattern = Paths.get(filePath, cameraId, "segment_%04d.ts").toString();
        // m3u8 播放列表路径，例如: /your/path/<cameraId>/index.m3u8
        String outM3u8 = Paths.get(filePath, cameraId, "index.m3u8").toString();

//        "-rtsp_transport udp " + // 使用 TCP 协议 拉取 RTSP 流（更可靠，不易丢包）
//        "-i \"%s\" " + //输入 RTSP 地址
//        "-an " + //  禁用音频（不处理音频流）
//        "-c:v libx264 " + // 视频编码 H.264
//        "-preset veryfast " + // 编码速度较快，延迟较低
//        "-crf 23 " + // 恒定质量模式，值越低画质越好（23 是常用平衡值）
//        "-force_key_frames \"expr:gte(t,n_forced*1)\" " + // 强制每 1 秒一个关键帧（时间间隔强制切）
//        "-f hls " + // 输出 HLS
//        "-hls_time 1 " + // 每个 TS 切片约 1秒（更细的切片）
//        "-hls_list_size 10 " + // 播放列表只保留最新 10个切片
//        "-hls_segment_filename \"%s/%s_%%04d.ts\" \"%s/%s.m3u8\"", // TS 文件名格式

        // Here we use copy to minimize CPU. If you need transcode, replace "-c:v","libx264" etc.
        // FFmpeg 命令
        return Arrays.asList(
                ffmpegPath,
                "-rtsp_transport", "udp",        // 使用 UDP 拉取 RTSP 流（TCP 可换成 tcp, UDP 延迟低但可能丢包）
                "-i", rtspUrl,                   // 输入 RTSP 地址
                "-an",                           // 禁用音频，节省 CPU
                "-c", "copy",                    // 直接拷贝视频流，无重新编码，CPU 占用低
                "-f", "hls",                     // 输出 HLS 格式
                "-hls_time", "2",                // 每个 TS 切片时长约 2 秒，低延迟 // 如果延迟可以容忍 2~3 秒，可以设置 -hls_time 2 或 3，减轻 I/O 压力。
                "-hls_list_size", "15",          // 播放列表只保留最新 15 个切片，避免磁盘占用过大 // 保留 10 个切片，1 秒/切片 → 延迟 ~10 秒。
                "-hls_flags", "delete_segments+append_list", // 保证旧切片自动删除并追加新切片
                "-hls_segment_filename", outPattern, // TS 切片文件名模式
                outM3u8                          // 输出 m3u8 文件
        );
    }

    // ---------- inner types ----------
    private enum FFmpegState { PENDING, STARTING, RUNNING, FAILED, STOPPED }

    /**
     * 单路摄像头对应的任务（全局唯一）
     *
     * 内部持有：
     * - ffmpeg 进程
     * - 停止标记
     * - 重试次数
     * - 心跳时间
     * - 当前状态
     *
     * 健康判断规则：
     * ------------------------------------------------------------
     * 1. 状态必须是 RUNNING
     * 2. 进程必须存活
     * 3. 最近心跳 < 15 秒
     */
    private class FFmpegTask {
        final String cameraId;
        final String rtspUrl;
        volatile Process process;

        // 是否主动停止
        final AtomicBoolean stopped = new AtomicBoolean(false);
        final AtomicInteger retryCount = new AtomicInteger(0);
        volatile long lastHeartbeat = System.currentTimeMillis();
        volatile FFmpegState state = FFmpegState.PENDING;

        FFmpegTask(String cameraId, String rtspUrl) {
            this.cameraId = cameraId;
            this.rtspUrl = rtspUrl;
            this.state = FFmpegState.STARTING;
        }

        void bindProcess(Process p) { this.process = p; }

        void updateHeartbeat() { this.lastHeartbeat = System.currentTimeMillis(); }

        long getLastHeartbeat() { return lastHeartbeat; }

        FFmpegState getState() { return state; }

        void setState(FFmpegState s) { this.state = s; }

        boolean isHealthy() {
            if (stopped.get()) return false;
            if (state != FFmpegState.RUNNING) return false;
            Process p = process;
            if (p == null || !p.isAlive()) return false;
            // heartbeat check
            return (System.currentTimeMillis() - lastHeartbeat) < heartbeatTimeoutMs;
        }

        void stop() {
            stopped.set(true);  // 标记为手动停止
            if (process != null) {
                process.destroy();
                process.destroyForcibly();
            }
            setState(FFmpegState.STOPPED);
        }
    }
}
