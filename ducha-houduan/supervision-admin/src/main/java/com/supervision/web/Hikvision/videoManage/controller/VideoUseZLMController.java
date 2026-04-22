package com.supervision.web.Hikvision.videoManage.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.github.pagehelper.PageInfo;
import com.supervision.web.Hikvision.videoManage.ZLMTask.ZlmManager;
import com.supervision.web.Hikvision.videoManage.domain.Video;
import com.supervision.web.Hikvision.videoManage.service.VideoService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.supervision.web.Hikvision.videoManage.ZLMTask.ZlmManager.StreamInfo;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 使用 ZLMediaKit 的视频控制器（替代 FFmpegManager）
 *
 * 优化点：
 * - 入参校验
 * - 更友好的返回结构（status/message/data）
 * - start 后短轮询确认流是否在线（尽量保证返回时在线）
 * - status 返回更丰富的 ZLM 信息（readerCount / tracks / originUrl / aliveSecond）
 * - stop 操作即使失败也尽量清理本地 registry
 */
@RestController
@RequestMapping("/videobyzlm")
@RequiredArgsConstructor
public class VideoUseZLMController {

    private static final Logger logger = LoggerFactory.getLogger(VideoUseZLMController.class);

    @Autowired
    private VideoService videoService;

    @Autowired
    private ZlmManager zlmManager;

    // -------------------- helper response --------------------
    private Map<String, Object> ok(Object data, String msg) {
        Map<String, Object> r = new HashMap<>();
        r.put("status", true);
        r.put("message", msg == null ? "success" : msg);
        r.put("data", data);
        return r;
    }
    private Map<String, Object> okMsg(String msg) {
        return ok(null, msg);
    }
    private Map<String, Object> fail(String msg) {
        Map<String, Object> r = new HashMap<>();
        r.put("status", false);
        r.put("message", msg);
        return r;
    }

    @ApiOperation("分页查询视频列表（支持模糊查询）")
    @PostMapping("/list")
    public Map<String, Object> listVideos(@RequestBody Map<String, Object> params) {
        Map<String, Object> result = new HashMap<>();
        try {
            Object data = videoService.searchByCondition(params);
            // 统一返回结构: total / list / pageNum / pageSize
            if (data instanceof PageInfo) {
                PageInfo<?> p = (PageInfo<?>) data;
                result.put("total", p.getTotal());
                result.put("list", p.getList());
                result.put("pageNum", p.getPageNum());
                result.put("pageSize", p.getPageSize());
            } else if (data instanceof List) {
                List<?> list = (List<?>) data;
                result.put("total", list.size());
                result.put("list", list);
                result.put("pageNum", 1);
                result.put("pageSize", list.size());
            } else {
                result.put("total", 0);
                result.put("list", Collections.emptyList());
                result.put("pageNum", 1);
                result.put("pageSize", 0);
            }
            result.put("status", true);
            result.put("message", "获取视频信息成功");
            return result;
        } catch (Exception e) {
            logger.error("获取视频信息失败", e);
            return fail("获取视频信息失败: " + e.getMessage());
        }
    }

    @ApiOperation("根据ID查询视频")
    @GetMapping("/{id}")
    public Map<String, Object> getById(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        try {
            if (id == null) return fail("ID不能为空");
            Video video = videoService.getById(id);
            if (video == null) return fail("视频不存在");
            return ok(video, "查询视频成功");
        } catch (Exception e) {
            logger.error("查询视频失败", e);
            return fail("查询视频失败: " + e.getMessage());
        }
    }

    @ApiOperation("新增视频")
    @PostMapping("/add")
    public Map<String, Object> add(@RequestBody Video video) {
        try {
            if (video == null) return fail("视频信息为空");
            String ip = video.getIp();
            if (ip == null || ip.trim().isEmpty()) return fail("IP不能为空");

            // 检查 IP 是否存在（service 内部也应二次校验）
            if (videoService.existsByIp(ip)) return fail("添加视频失败：IP已存在");

            videoService.add(video);
            return okMsg("添加视频成功");
        } catch (Exception e) {
            logger.error("添加视频失败", e);
            return fail("添加视频失败: " + e.getMessage());
        }
    }

    /**
     * 高性能的 Excel 导入（先校验再批量插入）
     * - 将来可移到 Service 层；保留在 Controller 以兼容原路由
     */
    @PostMapping("/importExcel")
    @Transactional
    public Map<String, Object> importExcel(@RequestParam("file") MultipartFile file) {
        List<String> errorRows = new ArrayList<>();
        List<Video> toSave = new ArrayList<>();
        try {
            EasyExcel.read(file.getInputStream(), Video.class, new AnalysisEventListener<Video>() {
                int rowNum = 1;
                @Override
                public void invoke(Video video, AnalysisContext context) {
                    rowNum++;
                    List<String> rowErrors = new ArrayList<>();

                    // ------------------- 必填字段校验 -------------------
                    if (video.getName() == null || video.getName().trim().isEmpty()) rowErrors.add("视频名称不能为空");
                    if (video.getIp() == null || video.getIp().trim().isEmpty()) rowErrors.add("IP地址不能为空");
                    else if (!isValidIp(video.getIp())) rowErrors.add("IP格式不正确");

                    if (video.getUsername() == null || video.getUsername().trim().isEmpty()) rowErrors.add("用户名不能为空");
                    if (video.getPassword() == null || video.getPassword().trim().isEmpty()) rowErrors.add("密码不能为空");
                    if (video.getDeviceType() == null || video.getDeviceType().trim().isEmpty()) rowErrors.add("设备类型不能为空");

                    if (!rowErrors.isEmpty()) {
                        errorRows.add("第 " + rowNum + " 行：" + String.join("；", rowErrors));
                        return;
                    }

                    // ------------------- 可选字段默认值 -------------------
                    // default values
                    if (video.getPort() == null) video.setPort(80);
                    if (video.getChannel() == null) video.setChannel(1);
                    if (video.getStreamType() == null) video.setStreamType(1);
                    if (video.getStreamCode() == null || video.getStreamCode().trim().isEmpty()) video.setStreamCode("01");
                    if (video.getRtspPort() == null) video.setRtspPort(554);
                    if (video.getEnable() == null) video.setEnable(true);
                    LocalDateTime now = LocalDateTime.now();
                    if (video.getCreateTime() == null) video.setCreateTime(now);
                    video.setUpdateTime(now);

                    // ------------------- 自动生成 RTSP 地址 -------------------
                    // auto rtsp
                    if ((video.getRtspUrl() == null || video.getRtspUrl().trim().isEmpty())
                            && video.getIp() != null && video.getRtspPort() != null && video.getChannel() != null) {
                        String auth = video.getUsername() != null
                                ? (video.getPassword() != null ? video.getUsername() + ":" + video.getPassword() + "@"
                                : video.getUsername() + "@")
                                : "";
                        video.setRtspUrl(String.format("rtsp://%s%s:%d/Streaming/Channels/%d%s",
                                auth, video.getIp(), video.getRtspPort(), video.getChannel(), video.getStreamCode()));
                    }

                    // ------------------- 保存到数据库 -------------------
                    toSave.add(video);
                }

                @Override
                public void doAfterAllAnalysed(AnalysisContext context) { }
            }).sheet().doRead();

            // second pass: check duplicate IPs in DB and in file
            Set<String> ipsInFile = new HashSet<>();
            for (int i = 0; i < toSave.size(); i++) {
                Video v = toSave.get(i);
                if (!ipsInFile.add(v.getIp())) {
                    errorRows.add("导入文件中存在重复 IP：" + v.getIp());
                } else if (videoService.existsByIp(v.getIp())) {
                    errorRows.add("第 " + (i+2) + " 行： IP 已存在 数据库中：" + v.getIp());
                }
            }

            if (!errorRows.isEmpty()) {
                Map<String,Object> res = new HashMap<>();
                res.put("status", false);
                res.put("message", "部分行导入失败");
                res.put("errors", errorRows);
                return res;
            }

            // 批量插入：尽量使用 service 提供的 batch 方法；若无则逐条插入（注意事务）
            try {
                videoService.batchAdd(toSave); // 推荐：在 VideoService 实现批量插入
            } catch (NoSuchMethodError | AbstractMethodError ex) {
                // fallback 单条插入
                for (Video v : toSave) videoService.add(v);
            }
            return okMsg("批量导入视频成功");
        } catch (Exception e) {
            logger.error("批量视频导入失败", e);
            return fail("批量视频导入失败: " + e.getMessage());
        }
    }

    /**
     * 校验 IP 是否合法
     */
    private boolean isValidIp(String ip) {
        String regex = "^((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)\\.){3}"
                + "(25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)$";
        return ip.matches(regex);
    }

    @ApiOperation("更新视频")
    @PostMapping("/update")
    public Map<String, Object> update(@RequestBody Video video) {
        try {
            if (video == null || video.getId() == null) return fail("ID不能为空");
            Video existing = videoService.getById(video.getId());
            if (existing == null) return fail("未找到要更新的记录");

            String newIp = video.getIp();
            String oldIp = existing.getIp();
            if (newIp != null && !newIp.equals(oldIp)) {
                if (newIp.trim().isEmpty()) return fail("IP不能为空");
                if (videoService.existsByIp(newIp)) return fail("修改视频失败：IP已存在");
            }

            videoService.update(video);
            return okMsg("修改视频成功");
        } catch (Exception e) {
            logger.error("修改视频失败", e);
            return fail("修改视频失败: " + e.getMessage());
        }
    }

    @ApiOperation("删除视频")
    @DeleteMapping("/delete/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        try {
            if (id == null) return fail("ID不能为空");
            videoService.delete(id);
            return okMsg("删除视频成功");
        } catch (Exception e) {
            logger.error("删除视频失败", e);
            return fail("删除视频失败: " + e.getMessage());
        }
    }

    @PostMapping("/batchDelete")
    public Map<String, Object> batchDelete(@RequestBody Map<String, Object> params) {
        try {
            if (params == null || params.get("ids") == null) return fail("ID列表不能为空");
            List<Long> ids = ((List<?>) params.get("ids")).stream().map(o -> Long.valueOf(o.toString())).collect(
                    Collectors.toList());
            if (ids.isEmpty()) return fail("ID列表不能为空");
            int rows = videoService.batchDelete(ids);
            if (rows > 0) return okMsg("批量删除视频成功");
            else return fail("未找到对应ID的视频");
        } catch (Exception e) {
            logger.error("批量删除视频失败", e);
            return fail("批量删除视频失败: " + e.getMessage());
        }
    }

    /**
     * 启动流（添加 ZLM 代理）
     *
     * - 非阻塞（立即返回播放地址）
     * - 短轮询确认流是否在线
     */
    @GetMapping("/start/{id}")
    public Map<String, Object> startStream(@PathVariable Long id) {
        final int maxPoll = 10;      // 最多轮询次数
        final int pollInterval = 300; // 每次轮询间隔(ms)

        try {
            if (id == null) return fail("ID 不能为空");

            Video video = videoService.getById(id);
            if (video == null) return fail("视频不存在");

            String rtspUrl = video.getRtspUrl();
            if (rtspUrl == null || rtspUrl.isEmpty())
                return fail("RTSP 地址为空");

            String cameraId = video.getId() + "_" + video.getChannel();

            // 1️⃣ 发起 addStreamProxy
            boolean ok = zlmManager.startStream(cameraId, rtspUrl);
            if (!ok) return fail("向 ZLM 提交拉流失败");

            // 2️⃣ 获取 FLV 播放地址
            String playUrl = zlmManager.getFlvUrl(cameraId);

            // 3️⃣ 短轮询确认 tracks 是否已有
            boolean online = false;
            Map<String, Object> info = null;

            for (int i = 0; i < maxPoll; i++) {
                Thread.sleep(pollInterval);
                info = zlmManager.getInfo(cameraId);

                StreamInfo localInfo = zlmManager.getStreamMap().get(cameraId);

                if ((info != null && !info.isEmpty() && info.get("tracks") != null && ((List<?>)info.get("tracks")).size() > 0)
                        || (localInfo != null && localInfo.isRunning())) {
                    online = true;
                    break;
                }
            }

            Map<String, Object> data = new HashMap<>();
            data.put("playUrl", playUrl);
            data.put("online", online);
            data.put("zlmInfo", info == null ? new HashMap<>() : info);
            return ok(data, online ? "流已上线" : "ZLM 正在拉流…（需 1~3 秒）");
        } catch (Exception e) {
            logger.error("ZLM addStreamProxy 异常, 视频ID={}", id, e);
            Map<String, Object> data = new HashMap<>();
            data.put("playUrl", null);
            data.put("online", false);
            data.put("zlmInfo", new HashMap<>());
            return fail("ZLM addStreamProxy 异常: " + e.getMessage());
        }
    }

    /**
     * 查询流状态（支持更准确判断 FLV 可用）
     */
    @GetMapping("/status/{id}")
    public Map<String, Object> getStreamStatus(@PathVariable Long id) {
        try {
            if (id == null) return fail("ID 不能为空");

            Video video = videoService.getById(id);
            if (video == null) return fail("视频不存在");

            final String cameraId = video.getId() + "_" + video.getChannel();
            Map<String, Object> data = zlmManager.getInfo(cameraId);

            StreamInfo info = zlmManager.getStreamMap().get(cameraId);
            boolean running = info != null && info.isRunning();

            if (!running && data != null && !data.isEmpty()) {
                List tracks = (List) data.get("tracks");
                if (tracks != null && tracks.size() > 0) {
                    running = true;
                }
            }

            Map<String, Object> detail = new HashMap<>();
            if (running && data != null) {
                detail.put("readerCount", data.getOrDefault("readerCount", 0));
                detail.put("tracks", data.getOrDefault("tracks", new ArrayList<>()));
                detail.put("originUrl", data.getOrDefault("originUrl", ""));
                detail.put("aliveSecond", data.getOrDefault("aliveSecond", 0));
                detail.put("schema", data.getOrDefault("schema", ""));
                detail.put("bytesSpeed", data.getOrDefault("bytesSpeed", 0));
            }

            Map<String, Object> res = new HashMap<>();
            res.put("status", running);
            res.put("statusValue", running ? "RUNNING" : "STOPPED");
            res.put("message", running ? "流在线" : "流未在线");
            res.put("flvUrl", running ? zlmManager.getFlvUrl(cameraId) : null);
            res.put("detail", detail);
            res.put("zlmInfo", data == null ? new HashMap<>() : data);

            return res;
        } catch (Exception e) {
            logger.error("查询 ZLM 流状态失败, 视频ID={}", id, e);
            return fail("查询 ZLM 流状态失败: " + e.getMessage());
        }
    }

    /**
     * 停止流（删除 ZLM 代理）
     */
    @GetMapping("/stop/{id}")
    public Map<String, Object> stopStream(@PathVariable Long id) {
        final int maxPoll = 10;       // 最多轮询次数
        final int pollInterval = 300; // 每次轮询间隔(ms)

        try {
            if (id == null) return fail("ID 不能为空");

            Video video = videoService.getById(id);
            if (video == null) return fail("视频不存在");

            String cameraId = video.getId() + "_" + video.getChannel();

            // 1️⃣ 向 ZLM 发删除请求
            boolean ok = zlmManager.stopStream(cameraId);
            if (!ok) logger.warn("向 ZLM 提交删除流失败, cameraId={}", cameraId);

            // 2️⃣ 短轮询确认流已下线
            boolean offline = false;
            Map<String, Object> info = null;

            for (int i = 0; i < maxPoll; i++) {
                Thread.sleep(pollInterval);
                info = zlmManager.getInfo(cameraId);

                StreamInfo localInfo = zlmManager.getStreamMap().get(cameraId);

                if ((info == null || info.isEmpty() || info.get("tracks") == null || ((List<?>)info.get("tracks")).isEmpty())
                        && (localInfo == null || !localInfo.isRunning())) {
                    offline = true;
                    break;
                }
            }

            Map<String, Object> data = new HashMap<>();
            data.put("online", !offline);
            data.put("zlmInfo", info == null ? new HashMap<>() : info);
            return ok(data, offline ? "流已关闭" : "正在关闭中…");
        } catch (Exception e) {
            logger.error("ZLM delStreamProxy 异常, 视频ID={}", id, e);
            Map<String, Object> data = new HashMap<>();
            data.put("online", true);
            data.put("zlmInfo", new HashMap<>());
            return fail("ZLM delStreamProxy 异常: " + e.getMessage());
        }
    }
}