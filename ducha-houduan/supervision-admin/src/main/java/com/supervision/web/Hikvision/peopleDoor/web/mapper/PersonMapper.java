package com.supervision.web.Hikvision.peopleDoor.web.mapper;


import com.supervision.web.Hikvision.peopleDoor.web.entity.PeopleInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PersonMapper {
    List<PeopleInfo> getAllPersons();
//    List<PeopleInfo> getPersonsByDeviceId(@Param("deviceId") Long deviceId);


    int insertPerson(PeopleInfo person);

    void updatePerson(PeopleInfo person);

    void deletePerson(Long id);
}
