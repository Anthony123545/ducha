package com.supervision.web.Hikvision.carDoor.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.supervision.web.Hikvision.carDoor.web.mapper.NameListMapper;
import com.supervision.web.Hikvision.carDoor.web.entity.NameList;
import com.supervision.web.Hikvision.carDoor.web.service.NameListService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class NameListServiceImpl implements NameListService {

    @Resource
    private NameListMapper nameListMapper;

//    @Autowired
//    private CarDoorDeviceManager carDoorDeviceManager;

    @Override
    public PageInfo<NameList> searchByCondition(Map<String, Object> params) {
        int page = (int) params.getOrDefault("page", 1);
        int size = (int) params.getOrDefault("size", 10);

        // 处理 deviceId 类型转换问题
        Object deviceIdObj = params.get("deviceId");
        Long deviceId = null;
        if (deviceIdObj != null) {
            if (deviceIdObj instanceof Number) {
                deviceId = ((Number) deviceIdObj).longValue();
            } else {
                deviceId = Long.valueOf(deviceIdObj.toString());
            }
        }

        Integer type = (Integer) params.getOrDefault("type", null);
        String licensePlate = (String) params.getOrDefault("licensePlate", null);

        PageHelper.startPage(page, size);

        List<NameList> list = nameListMapper.searchByCondition(deviceId, type, licensePlate);

        return new PageInfo<>(list);
    }

    @Override
    public int add(NameList nameList) {
//        try {
//            // 先同步设备
//            syncToDevice(nameList);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false; // 设备同步失败，不写数据库
//        }

        // 再写数据库
        return nameListMapper.insert(nameList);
    }

    @Override
    public int update(NameList nameList) {
//        try {
//            // 先同步设备（覆盖旧名单）
//            syncToDevice(nameList);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }

        // 再更新数据库
        return nameListMapper.update(nameList);
    }

    @Override
    public int delete(Long id) {
        NameList list = nameListMapper.getById(id);
        if (list == null) return 0;

//        try {
//            // 先删除设备名单
//            deleteFromDevice(list);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }

        // 再删除数据库
        return nameListMapper.delete(id);
    }

    @Override
    public int batchDelete(List<Long> ids) {
        if (ids == null || ids.isEmpty()) return 0;

        List<NameList> lists = nameListMapper.getByIds(ids);
        if (lists == null || lists.isEmpty()) return 0;

//        try {
//            // 先删除设备名单
//            for (NameList list : lists) {
//                deleteFromDevice(list);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }

        // 再批量删除数据库
        return nameListMapper.batchDelete(ids);
    }

    @Override
    public boolean exists(Long deviceId, Integer type, String licensePlate) {
        return nameListMapper.exists(deviceId, type, licensePlate) > 0;
    }

    @Override
    public boolean existsExcludeId(Long deviceId, Integer type, String licensePlate, Long id) {
        return nameListMapper.existsExcludeId(deviceId, type, licensePlate, id) > 0;
    }

//    @Override
//    public boolean syncToDevice(NameList nameList) throws Exception {
//        int lUserID = getDeviceUserID(nameList.getDeviceId());
//        List<NameList> nameLists = new ArrayList<>();
//        nameLists.add(nameList);
//        carDoorDeviceManager.addVechileList(lUserID, nameLists);
//        return true;
//    }
//
//    @Override
//    public boolean deleteFromDevice(NameList nameList) {
//        int lUserID = getDeviceUserID(nameList.getDeviceId());
//        List<NameList> nameLists = new ArrayList<>();
//        nameLists.add(nameList);
//        carDoorDeviceManager.deleteVechileList(lUserID, nameLists);
//        return true;
//    }
//
//    @Override
//    public int getDeviceUserID(Long deviceId) {
//        DeviceSession deviceSession = carDoorDeviceManager.getDeviceSession(deviceId.toString());
//        return deviceSession.getUserID();
//    }
}
