package com.supervision.web.Hikvision.peopleDoor.web.mapper;

import com.supervision.web.Hikvision.peopleDoor.web.entity.Device;
import com.supervision.web.Hikvision.peopleDoor.web.entity.PeopleDevice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PeopleDeviceMapper {

    int insert(PeopleDevice peopleDevice);

    int delete(@Param("personId") String personId, @Param("deviceId") Long deviceId);

    List<Long> selectDevicesByPerson(@Param("personId") String personId);

    List<String> selectPersonsByDevice(@Param("deviceId") Long deviceId);

    void insertRelation(PeopleDevice peopleDevice);


    Device getPeopleDeviceById(Long deviceId);

}
