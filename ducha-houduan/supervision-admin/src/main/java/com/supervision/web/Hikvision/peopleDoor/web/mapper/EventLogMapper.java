package com.supervision.web.Hikvision.peopleDoor.web.mapper;
import com.supervision.web.Hikvision.peopleDoor.web.entity.EventLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EventLogMapper {

    List<EventLog> selectByCondition(
            @Param("eventType") String eventType,
            @Param("personId") String personId,
            @Param("jobNumber") String jobNumber,
            @Param("name") String name,
            @Param("remoteHost") String remoteHost,
            @Param("reader") String reader,
            @Param("detectorId") String detectorId,
            @Param("operation") String operation,
            @Param("startTime") String startTime,
            @Param("endTime") String endTime,
            @Param("offset") Integer offset,
            @Param("pageSize") Integer pageSize
    );

    int countByCondition(
            @Param("eventType") String eventType,
            @Param("personId") String personId,
            @Param("jobNumber") String jobNumber,
            @Param("name") String name,
            @Param("remoteHost") String remoteHost,
            @Param("reader") String reader,
            @Param("detectorId") String detectorId,
            @Param("operation") String operation,
            @Param("startTime") String startTime,
            @Param("endTime") String endTime
    );
}
