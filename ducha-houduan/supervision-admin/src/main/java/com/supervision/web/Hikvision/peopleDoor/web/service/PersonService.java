package com.supervision.web.Hikvision.peopleDoor.web.service;


import com.supervision.web.Hikvision.peopleDoor.web.entity.PeopleInfo;

import java.util.List;
public interface PersonService {
    List<PeopleInfo> getAllPersons();
//    List<PeopleInfo> getPersonsByDeviceId(Long deviceId);
    void addPerson(PeopleInfo person);
    void updatePerson(PeopleInfo person);
    void deletePerson(Long id);
}
