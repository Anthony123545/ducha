package com.supervision.web.Hikvision.peopleDoor.web.service;

import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface PeopleDeviceService {

    void addRelation(String personId, Long deviceId);

    void bindPersonToDevice(String personId, Long deviceId);

    void bindPersonToDevices(String personId, List<Long> deviceIds);

    void unbindPersonFromDevice(String personId, Long deviceId);

    List<Long> getDevicesByPerson(String personId);

    List<String> getPersonsByDevice(Long deviceId);

    PageInfo<Map<String, Object>> searchByCondition(Map<String, Object> params);

    PageInfo<Map<String, Object>> addByCondition(Map<String, Object> request);

    PageInfo<Map<String, Object>> updateByCondition(Map<String, Object> request);

    PageInfo<Map<String, Object>> deleteByCondition(Map<String, Object> request);

}
