package com.supervision.web.Hikvision.peopleDoor.web.service.impl;

import com.github.pagehelper.PageInfo;
import com.supervision.web.Hikvision.peopleDoor.web.util.PeopleDoorDeviceManager;
import com.supervision.web.Hikvision.peopleDoor.web.entity.PeopleDevice;
import com.supervision.web.Hikvision.peopleDoor.web.mapper.PeopleDeviceMapper;
import com.supervision.web.Hikvision.util.DeviceSession;
import com.supervision.web.Hikvision.peopleDoor.web.entity.Device;
import com.supervision.web.Hikvision.peopleDoor.web.service.DeviceService;
import com.supervision.web.Hikvision.peopleDoor.web.service.PeopleDeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class PeopleDeviceServiceImpl implements PeopleDeviceService {

    private static final Logger logger = LoggerFactory.getLogger(PeopleDeviceServiceImpl.class);

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private PeopleDeviceMapper peopleDeviceMapper;

    @Autowired
    private PeopleDoorDeviceManager peopleDoorDeviceManager;

    @Override
    public void bindPersonToDevice(String personId, Long deviceId) {
        PeopleDevice pd = new PeopleDevice();
        pd.setPersonId(personId);
        pd.setDeviceId(deviceId);
        pd.setCreateTime(LocalDateTime.now());
        pd.setUpdateTime(LocalDateTime.now());
        peopleDeviceMapper.insert(pd);
    }

    @Override
    public void bindPersonToDevices(String personId, List<Long> deviceIds) {
        for (Long deviceId : deviceIds) {
            bindPersonToDevice(personId, deviceId);
        }
    }

    @Override
    public void unbindPersonFromDevice(String personId, Long deviceId) {
        peopleDeviceMapper.delete(personId, deviceId);
    }

    @Override
    public List<Long> getDevicesByPerson(String personId) {
        return peopleDeviceMapper.selectDevicesByPerson(personId);
    }

    @Override
    public List<String> getPersonsByDevice(Long deviceId) {
        return peopleDeviceMapper.selectPersonsByDevice(deviceId);
    }

    public void addRelation(String personId, Long deviceId) {
        PeopleDevice relation = new PeopleDevice();
        relation.setPersonId(personId);
        relation.setDeviceId(deviceId);
        relation.setCreateTime(LocalDateTime.now());
        relation.setUpdateTime(LocalDateTime.now());
        peopleDeviceMapper.insertRelation(relation);
    }

    @Override
    public PageInfo<Map<String, Object>> searchByCondition(Map<String, Object> params){
        //        try {
        Object idObj = params.get("deviceId");
        Long deviceId = idObj == null ? null : Long.parseLong(idObj.toString());

        Device device = deviceService.getById(deviceId);
        if (device == null || device.getIp() == null) {
            logger.error("设备不存在");
            return null;
        }

        DeviceSession deviceSession = peopleDoorDeviceManager.getDeviceSession(device.getIp());

        if(deviceSession == null) {
            logger.error("设备未登录");
            return null;
        }


        int userID = deviceSession.getUserID();

//            peopleDoorDeviceManager.handleCase(userID, (HashMap<String, Object>) params);

        return null;
//        } catch (UnsupportedEncodingException | InterruptedException e) {
////            throw new RuntimeException(e);
//            return null;
//        }
    }

    @Override
    public PageInfo<Map<String, Object>> addByCondition(Map<String, Object> params) {
        //        try {
        Object idObj = params.get("deviceId");
        Long deviceId = idObj == null ? null : Long.parseLong(idObj.toString());

        Device device = deviceService.getById(deviceId);
        if (device == null || device.getIp() == null) {
            logger.error("设备不存在");
            return null;
        }

        DeviceSession deviceSession = peopleDoorDeviceManager.getDeviceSession(device.getIp());

        if(deviceSession == null) {
            logger.error("设备未登录");
            return null;
        }


        int userID = deviceSession.getUserID();

//            peopleDoorDeviceManager.handleCase(userID, (HashMap<String, Object>) params);

        return null;
//        } catch (UnsupportedEncodingException | InterruptedException e) {
////            throw new RuntimeException(e);
//            return null;
//        }
    }

    @Override
    public PageInfo<Map<String, Object>> updateByCondition(Map<String, Object> params) {
        //        try {
        Object idObj = params.get("deviceId");
        Long deviceId = idObj == null ? null : Long.parseLong(idObj.toString());

        Device device = deviceService.getById(deviceId);
        if (device == null || device.getIp() == null) {
            logger.error("设备不存在");
            return null;
        }

        DeviceSession deviceSession = peopleDoorDeviceManager.getDeviceSession(device.getIp());

        if(deviceSession == null) {
            logger.error("设备未登录");
            return null;
        }


        int userID = deviceSession.getUserID();

//            peopleDoorDeviceManager.handleCase(userID, (HashMap<String, Object>) params);

        return null;
//        } catch (UnsupportedEncodingException | InterruptedException e) {
////            throw new RuntimeException(e);
//            return null;
//        }
    }

    @Override
    public PageInfo<Map<String, Object>> deleteByCondition(Map<String, Object> params) {
//        try {
            Object idObj = params.get("deviceId");
            Long deviceId = idObj == null ? null : Long.parseLong(idObj.toString());

            Device device = deviceService.getById(deviceId);
            if (device == null || device.getIp() == null) {
                logger.error("设备不存在");
                return null;
            }

            DeviceSession deviceSession = peopleDoorDeviceManager.getDeviceSession(device.getIp());

            if(deviceSession == null) {
                logger.error("设备未登录");
                return null;
            }


            int userID = deviceSession.getUserID();

//            peopleDoorDeviceManager.handleCase(userID, (HashMap<String, Object>) params);

            return null;
//        } catch (UnsupportedEncodingException | InterruptedException e) {
////            throw new RuntimeException(e);
//            return null;
//        }
    }

}
