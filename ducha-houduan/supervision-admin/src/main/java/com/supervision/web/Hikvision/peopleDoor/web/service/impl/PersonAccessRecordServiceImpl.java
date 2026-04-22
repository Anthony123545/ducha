package com.supervision.web.Hikvision.peopleDoor.web.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.supervision.web.Hikvision.peopleDoor.web.entity.PersonAccessRecord;
import com.supervision.web.Hikvision.peopleDoor.web.mapper.PersonAccessRecordMapper;
import com.supervision.web.Hikvision.peopleDoor.web.service.PersonAccessRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class PersonAccessRecordServiceImpl implements PersonAccessRecordService {

    @Autowired
    private PersonAccessRecordMapper personAccessRecordMapper;

    @Override
    public PageInfo<PersonAccessRecord> searchByCondition(Map<String, Object> params) {
        int page = (int) params.getOrDefault("page", 1);
        int size = (int) params.getOrDefault("size", 10);

        Object deviceIdObj = params.get("deviceId");
        long deviceId = 0L;
        if (deviceIdObj != null) {
            deviceId = ((Number) deviceIdObj).longValue();
        }

//        int majorType = (int) params.getOrDefault("majorType", 5);
//        int minorType = (int) params.getOrDefault("minorType", 75);
//        int employeeNo = (int) params.getOrDefault("employeeNo", null);
        Integer majorType = (params.get("employeeNo") instanceof Integer)?( Integer)params.get("emplyeeNo"):null;
        Integer minorType = (params.get("employeeNo") instanceof Integer)?( Integer)params.get("emplyeeNo"):null;
        Integer employeeNo = (params.get("employeeNo") instanceof Integer)?( Integer)params.get("emplyeeNo"):null;
        String name = (String) params.getOrDefault("name", null);
        int cardNo = (int) params.getOrDefault("cardNo", null);
        String eventType = (String) params.getOrDefault("eventType", null);
        String startTime = (String) params.getOrDefault("startTime", null);
        String endTime = (String) params.getOrDefault("endTime", null);

        PageHelper.startPage(page, size);
        List<PersonAccessRecord> list = personAccessRecordMapper.searchByCondition(deviceId, majorType, minorType, employeeNo, name, cardNo, eventType, startTime, endTime);
        return new PageInfo<>(list);
    }

    @Override
    public boolean add(PersonAccessRecord record) {
        return personAccessRecordMapper.insert(record) > 0;
    }

    @Override
    public boolean update(PersonAccessRecord record) {
        return personAccessRecordMapper.update(record) > 0;
    }

    @Override
    public boolean deleteById(Long id) {
        return personAccessRecordMapper.deleteById(id) > 0;
    }

    @Override
    public boolean batchDelete(List<Long> ids) {
        return personAccessRecordMapper.batchDelete(ids) > 0;
    }
}

