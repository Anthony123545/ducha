package com.supervision.web.Hikvision.videoManage.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.github.pagehelper.PageInfo;
import com.supervision.web.Hikvision.videoManage.domain.Video;
import com.supervision.web.Hikvision.videoManage.other.FFmpegManager;
import com.supervision.web.Hikvision.videoManage.other.StreamRegistry;
import com.supervision.web.Hikvision.videoManage.service.VideoService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

@Slf4j
@RestController
@RequestMapping("/video")
@RequiredArgsConstructor
public class VideoController {

    private static final Logger logger = LoggerFactory.getLogger(VideoController.class);

    @Autowired
    private VideoService videoService;

    @Autowired
    private FFmpegManager ffmpegManager;

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
            List<Long> ids = ((List<?>) params.get("ids")).stream().map(o -> Long.valueOf(o.toString())).collect(Collectors.toList());
            if (ids.isEmpty()) return fail("ID列表不能为空");
            int rows = videoService.batchDelete(ids);
            if (rows > 0) return okMsg("批量删除视频成功");
            else return fail("未找到对应ID的视频");
        } catch (Exception e) {
            logger.error("批量删除视频失败", e);
            return fail("批量删除视频失败: " + e.getMessage());
        }
    }

    /** 启动推流 */
    @GetMapping("/start/{id}")
    public Map<String, Object> startStream(@PathVariable Long id) {
        try {
            Video video = videoService.getById(id);
            if (video == null) return fail("视频不存在");
            String cameraId = video.getId() + "_" + video.getChannel();
            String playUrl = ffmpegManager.startStream(cameraId, video.getRtspUrl());
            // optional: register in registry for compatibility
            StreamRegistry.put(cameraId, video.getRtspUrl(), playUrl, ffmpegManager.getStatus(cameraId));
            Map<String,Object> res = new HashMap<>();
            res.put("status", true);
            res.put("message", "推流任务已提交");
            res.put("data", playUrl);
            return res;
        } catch (Exception e) {
            logger.error("FFmpeg 推流任务提交失败, 视频ID={}", id, e);
            return fail("FFmpeg 推流任务提交失败: " + e.getMessage());
        }
    }

    @GetMapping("/status/{id}")
    public Map<String, Object> getStreamStatus(@PathVariable Long id) {
        try {
            Video video = videoService.getById(id);
            if (video == null) return fail("视频不存在");
            String cameraId = video.getId() + "_" + video.getChannel();
            String status = ffmpegManager.getStatus(cameraId);
            Map<String,Object> data = ffmpegManager.getInfo(cameraId);
            Map<String,Object> res = new HashMap<>();
            res.put("status", "RUNNING".equals(status));
            res.put("statusValue", status);
            res.put("message", "RUNNING".equals(status) ? "推流成功" : ("FAILED".equals(status) ? "推流失败" : "推流未就绪"));
            res.put("hlsUrl", ffmpegManager.getPlayUrl(cameraId));
            res.put("detail", data);
            return res;
        } catch (Exception e) {
            logger.error("FFmpeg 推流状态获取失败, 视频ID={}", id, e);
            return fail("FFmpeg 推流状态获取失败: " + e.getMessage());
        }
    }

    /** 停止推流 */
    @GetMapping("/stop/{id}")
    public Map<String, Object> stopStream(@PathVariable Long id) {
        try {
            Video video = videoService.getById(id);
            if (video == null) return fail("视频不存在");
            String cameraKey = video.getId() + "_" + video.getChannel();
            boolean ok = ffmpegManager.stopStream(cameraKey);
            if (ok) {
                StreamRegistry.remove(cameraKey);
                return okMsg("FFmpeg 停止推流成功");
            } else {
                return fail("FFmpeg 停止推流失败：没有活动推流");
            }
        } catch (Exception e) {
            logger.error("FFmpeg 停止推流失败, 视频ID={}", id, e);
            return fail("FFmpeg 停止推流失败: " + e.getMessage());
        }
    }

}
