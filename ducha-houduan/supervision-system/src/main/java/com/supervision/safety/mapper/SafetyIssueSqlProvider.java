package com.supervision.safety.mapper;

import java.util.Map;

public class SafetyIssueSqlProvider {

    // 定义一个通用的字段列表常量，方便复用，减少出错
    private static final String FULL_FIELDS = "id, check_target, hazard_name, description, hazard_category, " +
            "hazard_source, risk_level, status, dept, person, found_at, rectified_at, " +
            "rectification_measures, acceptance_time, acceptance_unit, acceptance_conclusion, " +
            "close_time, remark, deleted, create_time, update_time ";

    // 1. 修改搜索 SQL：增加新字段到 SELECT 列表中
    public String searchSql(Map<String, Object> p) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ").append(FULL_FIELDS).append(" FROM safety_issue WHERE deleted=0 ");

        // 优化搜索：同时匹配 详情、名称、排查对象、责任单位、责任人
        if (p.get("keyword") != null && !String.valueOf(p.get("keyword")).trim().isEmpty()) {
            sb.append("AND (description LIKE CONCAT('%', #{keyword}, '%') ")
                    .append("OR hazard_name LIKE CONCAT('%', #{keyword}, '%') ")
                    .append("OR check_target LIKE CONCAT('%', #{keyword}, '%') ")
                    .append("OR dept LIKE CONCAT('%', #{keyword}, '%') ")
                    .append("OR person LIKE CONCAT('%', #{keyword}, '%')) ");
        }

        if (p.get("level")   != null) sb.append("AND risk_level = #{level} ");
        if (p.get("status")  != null) sb.append("AND status = #{status} ");
        if (p.get("from")    != null) sb.append("AND found_at >= #{from} ");
        if (p.get("to")      != null) sb.append("AND found_at <  #{to} ");

        sb.append("ORDER BY found_at DESC ");
        sb.append("LIMIT #{limit} OFFSET #{offset}");
        return sb.toString();
    }

    // 2. 修改计数 SQL：搜索逻辑要与 searchSql 保持严格一致
    public String countSql(Map<String, Object> p) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT COUNT(*) FROM safety_issue WHERE deleted=0 ");

        if (p.get("keyword") != null && !String.valueOf(p.get("keyword")).trim().isEmpty()) {
            sb.append("AND (description LIKE CONCAT('%', #{keyword}, '%') ")
                    .append("OR hazard_name LIKE CONCAT('%', #{keyword}, '%') ")
                    .append("OR check_target LIKE CONCAT('%', #{keyword}, '%') ")
                    .append("OR dept LIKE CONCAT('%', #{keyword}, '%') ")
                    .append("OR person LIKE CONCAT('%', #{keyword}, '%')) ");
        }

        if (p.get("level")   != null) sb.append("AND risk_level = #{level} ");
        if (p.get("status")  != null) sb.append("AND status = #{status} ");
        if (p.get("from")    != null) sb.append("AND found_at >= #{from} ");
        if (p.get("to")      != null) sb.append("AND found_at <  #{to} ");
        return sb.toString();
    }

    public String countPendingByLevelSql() {
        return "SELECT risk_level AS lvl, COUNT(*) AS cnt " +
                "FROM safety_issue WHERE deleted=0 AND status='PENDING' GROUP BY risk_level";
    }

    public String trendByDaySql(Map<String, Object> p) {
        return "SELECT DATE_FORMAT(found_at, '%Y-%m-%d') AS d, COUNT(*) AS c " +
                "FROM safety_issue WHERE deleted=0 AND found_at >= #{from} AND found_at < #{to} " +
                "GROUP BY d ORDER BY d";
    }

    // 3. 修改 Top5 SQL：同样增加新字段，确保首页仪表盘点击查看时有数据
    public String top5HighSql() {
        return "SELECT " + FULL_FIELDS +
                "FROM safety_issue WHERE deleted=0 AND risk_level='VERY_HIGH' AND status='PENDING' " +
                "ORDER BY found_at DESC LIMIT 5";
    }
}
