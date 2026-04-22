package com.supervision.web.noticeManage.controller;

import com.supervision.web.noticeManage.entity.Notice;
import com.supervision.web.noticeManage.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/notice")

public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    /**
     * 新增
     * 前端传 JSON 示例：
     * {
     *   "title": "公告标题",
     *   "description": "公告内容",
     *   "attachment": "https://example.com/file.pdf"  // 前端传的 URL
     * }
     */
    @PostMapping("/add")
    public ResponseEntity<?> addNotice(@RequestBody Notice notice) {
        notice.setCreateTime(LocalDateTime.now());
        notice.setUpdateTime(LocalDateTime.now());
        boolean success = noticeService.addNotice(notice);
        if (success) {
            return ResponseEntity.ok().body("新增成功");
        } else {
            return ResponseEntity.status(500).body("新增失败");
        }
    }

    /**
     * 获取所有
     */
    @GetMapping("/list")
    public ResponseEntity<List<Notice>> listNotices() {
        List<Notice> list = noticeService.getAllNotices();
        return ResponseEntity.ok(list);
    }

    /**
     * 搜索：按标题模糊或按时间范围
     * 示例：
     * GET /notice/select?title=公告&start=2024-01-01T00:00:00&end=2024-12-31T23:59:59
     *
     * 参数说明：
     * - title 可选（模糊匹配）
     * - start 可选（ISO 本地时间格式）
     * - end 可选
     */
    @GetMapping("/select")
    public ResponseEntity<List<Notice>> selectNotices(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "start", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam(value = "end", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {

        List<Notice> results = noticeService.searchNotices(title, start, end);
        return ResponseEntity.ok(results);
    }

    /**
     * 删除
     * DELETE /notice/delete/{id}
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteNotice(@PathVariable("id") Integer id) {
        boolean success = noticeService.deleteNotice(id);
        if (success) {
            return ResponseEntity.ok("删除成功");
        } else {
            return ResponseEntity.status(404).body("未找到或删除失败");
        }
    }
}
