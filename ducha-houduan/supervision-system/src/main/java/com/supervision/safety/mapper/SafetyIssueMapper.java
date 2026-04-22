package com.supervision.safety.mapper;

import com.supervision.safety.domain.IssueStatus;
import com.supervision.safety.domain.RiskLevel;
import com.supervision.safety.domain.SafetyIssue;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.EnumTypeHandler;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface SafetyIssueMapper {

    @Select("SELECT * FROM safety_issue WHERE id=#{id} AND deleted=0")
    @Results(id = "IssueMap", value = {
            @Result(column = "id", property = "id"),
            @Result(column = "check_target", property = "checkTarget"),
            @Result(column = "hazard_name", property = "hazardName"),
            @Result(column = "hazard_category", property = "hazardCategory"),
            @Result(column = "hazard_source", property = "hazardSource"),
            @Result(column = "dept", property = "dept"),
            @Result(column = "person", property = "person"),
            @Result(column = "description", property = "description"),
            @Result(column = "risk_level", property = "riskLevel", javaType = RiskLevel.class, typeHandler = EnumTypeHandler.class),
            @Result(column = "status", property = "status", javaType = IssueStatus.class, typeHandler = EnumTypeHandler.class),
            @Result(column = "found_at", property = "foundAt"),
            @Result(column = "rectified_at", property = "rectifiedAt"),
            @Result(column = "deleted", property = "deleted"),
            @Result(column = "create_time", property = "createTime"),
            @Result(column = "update_time", property = "updateTime"),
            @Result(column = "rectification_measures", property = "rectificationMeasures"),
            @Result(column = "acceptance_time",       property = "acceptanceTime"),
            @Result(column = "acceptance_unit",       property = "acceptanceUnit"),
            @Result(column = "acceptance_conclusion", property = "acceptanceConclusion"),
            @Result(column = "close_time",            property = "closeTime"),
            @Result(column = "remark",                property = "remark"),
    })
    SafetyIssue findById(@Param("id") Long id);

    @Insert("INSERT INTO safety_issue (check_target, hazard_name, description, hazard_category, hazard_source, dept, person, risk_level, status, found_at, deleted, create_time, update_time) " +
            "VALUES (#{checkTarget}, #{hazardName}, #{description}, #{hazardCategory}, #{hazardSource}, #{dept}, #{person}, " +
            "#{riskLevel, typeHandler=org.apache.ibatis.type.EnumTypeHandler}, " +
            "#{status, typeHandler=org.apache.ibatis.type.EnumTypeHandler}, COALESCE(#{foundAt}, NOW()), 0, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    int insert(SafetyIssue issue);

    // 全量修改方法
    @Update("UPDATE safety_issue SET " +
            "check_target=#{checkTarget}, hazard_name=#{hazardName}, description=#{description}, " +
            "rectification_measures=#{rectificationMeasures}, " +
            "acceptance_time=#{acceptanceTime}, acceptance_unit=#{acceptanceUnit}, " +
            "acceptance_conclusion=#{acceptanceConclusion}, close_time=#{closeTime}, " +
            "remark=#{remark}, " +
            "dept=#{dept}, person=#{person}, risk_level=#{riskLevel, typeHandler=org.apache.ibatis.type.EnumTypeHandler}, " +
            "update_time=NOW() WHERE id=#{id} AND deleted=0")
    int update(SafetyIssue issue);

    @Update("UPDATE safety_issue SET status=#{status, typeHandler=org.apache.ibatis.type.EnumTypeHandler}, " +
            "rectified_at=(CASE WHEN #{status}='RECTIFIED' THEN NOW() ELSE NULL END), update_time=NOW() WHERE id=#{id}")
    int updateStatus(@Param("id") Long id, @Param("status") IssueStatus status, @Param("rectifiedAt") Date rectifiedAt);

    @Update("UPDATE safety_issue SET deleted=1 WHERE id=#{id}")
    int logicalDelete(@Param("id") Long id);

    // 支持勾选导出的查询
    @Select("<script>SELECT * FROM safety_issue WHERE deleted=0 " +
            "<if test='ids != null and ids.size() > 0'> AND id IN " +
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>#{id}</foreach></if> " +
            "ORDER BY found_at DESC</script>")
    @ResultMap("IssueMap")
    List<SafetyIssue> findByIds(@Param("ids") List<Long> ids);

    @SelectProvider(type = SafetyIssueSqlProvider.class, method = "searchSql")
    @ResultMap("IssueMap")
    List<SafetyIssue> search(@Param("keyword") String keyword, @Param("level") RiskLevel level, @Param("status") IssueStatus status, @Param("from") Date from, @Param("to") Date to, @Param("limit") int limit, @Param("offset") int offset);

    @SelectProvider(type = SafetyIssueSqlProvider.class, method = "countSql")
    long count(@Param("keyword") String keyword, @Param("level") RiskLevel level, @Param("status") IssueStatus status, @Param("from") Date from, @Param("to") Date to);

    @SelectProvider(type = SafetyIssueSqlProvider.class, method = "countPendingByLevelSql")
    List<Map<String, Object>> countPendingByLevel();

    @SelectProvider(type = SafetyIssueSqlProvider.class, method = "trendByDaySql")
    List<Map<String, Object>> trendByDay(@Param("from") Date from, @Param("to") Date to);

    @SelectProvider(type = SafetyIssueSqlProvider.class, method = "top5HighSql")
    @ResultMap("IssueMap")
    List<SafetyIssue> top5High();
}
