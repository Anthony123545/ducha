package com.supervision.web.Hikvision.carDoor.web.mapper;

import com.supervision.web.Hikvision.carDoor.web.entity.CarEvent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CarEventLogMapper {

    List<CarEvent> selectByCondition(
            @Param("plateNumber") String plateNumber,
            @Param("ownerName") String ownerName,
            @Param("phoneNumber") String phoneNumber,
            @Param("eventType") String eventType,
            @Param("carType") String carType,
            @Param("carGroup") String carGroup,
            @Param("startTime") String startTime,
            @Param("endTime") String endTime,
            @Param("offset") Integer offset,
            @Param("pageSize") Integer pageSize
    );

    int countByCondition(
            @Param("plateNumber") String plateNumber,
            @Param("ownerName") String ownerName,
            @Param("phoneNumber") String phoneNumber,
            @Param("eventType") String eventType,
            @Param("carType") String carType,
            @Param("carGroup") String carGroup,
            @Param("startTime") String startTime,
            @Param("endTime") String endTime
    );
}

