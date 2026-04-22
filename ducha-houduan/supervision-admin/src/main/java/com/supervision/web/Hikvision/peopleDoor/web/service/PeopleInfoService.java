package com.supervision.web.Hikvision.peopleDoor.web.service;

import com.supervision.web.Hikvision.peopleDoor.web.entity.PeopleInfo;

import java.util.List;


public interface PeopleInfoService {
    PeopleInfo getById(Long id);
    List<PeopleInfo> getAll();
    void add(PeopleInfo peopleInfo);
    void update(PeopleInfo peopleInfo);
    void delete(Long id);
    int getPeopleTotal();
    List<PeopleInfo> getByIds(List<String> personIds);
}
