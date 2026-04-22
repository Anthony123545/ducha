package com.supervision.web.Hikvision.carDoor.web.mapper;


import com.supervision.web.Hikvision.carDoor.web.entity.VehicleAccessRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface VehicleAccessRecordMapper {

    List<VehicleAccessRecord> searchByCondition(
            @Param("deviceId") Long deviceId,
            @Param("plateNumber") String plateNumber,
            @Param("eventType") String eventType,
            @Param("startTime") String startTime,
            @Param("endTime") String endTime);

    int insert(VehicleAccessRecord record);

    int update(VehicleAccessRecord record);

    int deleteById(Long id);

    int batchDelete(@Param("ids") List<Long> ids);
}

