package com.supervision.web.Hikvision.peopleDoor.web.service.impl;

import com.supervision.web.Hikvision.peopleDoor.web.entity.PeopleInfo;
import com.supervision.web.Hikvision.peopleDoor.web.mapper.PeopleInfoMapper;
import com.supervision.web.Hikvision.peopleDoor.web.service.PeopleInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;


@Service
public class PeopleInfoServiceImpl implements PeopleInfoService {
    @Autowired
    private PeopleInfoMapper peopleInfoMapper;

    @Override
    public PeopleInfo getById(Long id) {
        return peopleInfoMapper.selectById(id);
    }

    @Override
    public List<PeopleInfo> getAll() {
        return peopleInfoMapper.selectAll();
    }
    @Override
    public int getPeopleTotal() {
        return peopleInfoMapper.selectTotal();
    }

    @Override
    public void add(PeopleInfo peopleInfo) {

        String prefix = Boolean.TRUE.equals(peopleInfo.getIsTemporary()) ? "1" : "0";



        // 第三步：拼接 personId（比如 "100001"）
//        String personId = prefix + String.format("%05d", peopleInfo.getId());

        String personId = prefix + UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        // 第四步：更新 personId
        peopleInfo.setPersonId(personId);

        peopleInfoMapper.insert(peopleInfo);

//        peopleInfoMapper.updatePersonId(peopleInfo.getId(), personId);
    }

    @Override
    public void update(PeopleInfo peopleInfo) {
        peopleInfoMapper.update(peopleInfo);
    }

    @Override
    public void delete(Long id) {
        peopleInfoMapper.delete(id);
    }

    @Override
    public List<PeopleInfo> getByIds(List<String> personIds) {
        if (personIds == null || personIds.isEmpty()) {
            return Collections.emptyList();
        }
        List<PeopleInfo>t = peopleInfoMapper.selectByIds(personIds);
        return peopleInfoMapper.selectByIds(personIds);
    }

}
