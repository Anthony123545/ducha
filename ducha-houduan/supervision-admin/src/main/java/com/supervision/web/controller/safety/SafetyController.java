package com.supervision.web.controller.safety;

import com.supervision.safety.domain.*;
import com.supervision.safety.service.SafetyService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/safety")
public class SafetyController {

    @Resource
    private SafetyService safetyService;

    @GetMapping("/issues")
    public PageResult<SafetyIssue> issues(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) RiskLevel level,
            @RequestParam(required = false) IssueStatus status,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date from,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date to,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        return safetyService.search(keyword, level, status, from, to, pageNum, pageSize);
    }

    @PostMapping("/issues")
    public Map<String, Object> create(@Validated @RequestBody CreateIssueReq req) {
        Long id = safetyService.createIssue(req);
        return Collections.singletonMap("issueId", id);
    }

    @PutMapping("/issues")
    public Map<String, Object> update(@RequestBody SafetyIssue issue) {
        safetyService.updateIssue(issue);
        return Collections.singletonMap("success", true);
    }

    @PutMapping("/issues/{id}/status")
    public Map<String, Object> updateStatus(@PathVariable Long id, @RequestBody UpdateStatusReq req) {
        safetyService.updateIssueStatus(id, req.getStatus());
        return Collections.singletonMap("success", true);
    }

    @PostMapping("/issues/export/word")
    public void exportWord(@RequestBody Map<String, List<Long>> params, HttpServletResponse response) throws IOException {
        safetyService.exportToWord(params.get("ids"), response);
    }

    @DeleteMapping("/issues/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        safetyService.deleteIssue(id);
        return Collections.singletonMap("success", true);
    }

    @GetMapping("/dashboard")
    public DashboardVo dashboard(@RequestParam(defaultValue = "7") int days) {
        DashboardVo vo = new DashboardVo();
        vo.setCounters(safetyService.counters());
        vo.setWeeklyTrend(safetyService.weeklyTrend(days));
        vo.setTop5High(safetyService.top5High());
        return vo;
    }
}
