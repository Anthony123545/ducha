package com.supervision.web.Hikvision.carDoor.web.service;


import com.github.pagehelper.PageInfo;
import com.supervision.web.Hikvision.carDoor.web.entity.CarInfo;

import java.util.List;
import java.util.Map;

public interface CarInfoService {

    PageInfo<CarInfo> searchByCondition(Map<String, Object> params);

    int add(CarInfo carInfo);

    int update(CarInfo carInfo);

    int delete(Long id);

    int deleteBatch(List<Long> ids);

}
