package com.supervision.web.Hikvision.peopleDoor.web.service.impl;


import com.supervision.web.Hikvision.peopleDoor.web.entity.PeopleInfo;
import com.supervision.web.Hikvision.peopleDoor.web.mapper.PersonMapper;
import com.supervision.web.Hikvision.peopleDoor.web.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonMapper personMapper;

    @Override
    public List<PeopleInfo> getAllPersons() {
        return personMapper.getAllPersons();
    }



    @Override
    public void addPerson(PeopleInfo person) {
        personMapper.insertPerson(person);
    }

    @Override
    public void updatePerson(PeopleInfo person) {
        personMapper.updatePerson(person);
    }

    @Override
    public void deletePerson(Long id) {
        personMapper.deletePerson(id);
    }
}
