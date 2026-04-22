package com.supervision.safety.service;

import java.util.List;
import java.util.Map;
import com.supervision.safety.domain.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public interface SafetyService {

    Long createIssue(CreateIssueReq req);

    // 【新增】修改隐患全部信息
    void updateIssue(SafetyIssue issue);

    void updateIssueStatus(Long id, IssueStatus status);

    void deleteIssue(Long id);

    PageResult<SafetyIssue> search(String keyword,
                                   RiskLevel level,
                                   IssueStatus status,
                                   Date from, Date to,
                                   int pageNum, int pageSize);

    // 【新增】导出Word台账
    void exportToWord(List<Long> ids, HttpServletResponse response) throws IOException;

    Map<String, Long> counters();

    List<Map<String, Object>> weeklyTrend(int days);

    List<SafetyIssue> top5High();
}
