package com.supervision.web.controller.announcement;

import com.supervision.announcement.domain.Announcement;
import com.supervision.announcement.service.AnnouncementService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/web/announcement")
public class AnnouncementController {

    @Resource
    private AnnouncementService announcementService;

    /** 列表 */
    @GetMapping("/list")
    public List<Announcement> list() {
        return announcementService.listAll();
    }

    /** 新增 */
    @PostMapping("/add")
    public Map<String, Object> add(@RequestBody Announcement announcement) {
        announcementService.add(announcement);
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        return result;
    }

    /** 修改 */
    @PutMapping("/update")
    public Map<String, Object> update(@RequestBody Announcement announcement) {
        announcementService.update(announcement);
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        return result;
    }

    /** 删除 */
    @DeleteMapping("/delete/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        announcementService.delete(id);
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        return result;
    }

    /** 发布 */
    @PutMapping("/publish/{id}")
    public Map<String, Object> publish(@PathVariable Long id) {
        announcementService.publish(id);
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        return result;
    }
}
