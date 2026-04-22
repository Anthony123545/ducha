package com.supervision.web.Hikvision.peopleDoor.web.mapper;


import com.supervision.web.Hikvision.peopleDoor.web.entity.PersonAccessRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface PersonAccessRecordMapper {

    List<PersonAccessRecord> searchByCondition(
            @Param("deviceId") Long deviceId,
            @Param("majorType") Integer majorType,
            @Param("minorType") Integer minorType,
            @Param("employeeNo") Integer employeeNo,
            @Param("name") String name,
            @Param("cardNo") int cardNo,
            @Param("eventType") String eventType,
            @Param("startTime") String startTime,
            @Param("endTime") String endTime);

    int insert(PersonAccessRecord record);

    int update(PersonAccessRecord record);

    int deleteById(Long id);

    int batchDelete(@Param("ids") List<Long> ids);

}
