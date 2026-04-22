package com.supervision.safety.domain;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DashboardVo {
    private Map<String, Long> counters;
    private List<Map<String, Object>> weeklyTrend;
    private List<SafetyIssue> top5High;
}