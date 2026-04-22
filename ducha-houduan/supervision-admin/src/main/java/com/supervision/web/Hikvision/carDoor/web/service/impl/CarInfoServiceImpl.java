package com.supervision.web.Hikvision.carDoor.web.service.impl;


import com.github.pagehelper.PageInfo;
import com.supervision.web.Hikvision.carDoor.web.entity.CarInfo;
import com.supervision.web.Hikvision.carDoor.web.service.CarInfoService;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.supervision.web.Hikvision.carDoor.web.mapper.CarInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@Service
public class CarInfoServiceImpl implements CarInfoService {

    @Autowired
    private CarInfoMapper carInfoMapper;

    @Override
    public PageInfo<CarInfo> searchByCondition(Map<String, Object> params) {
        int page = (int) params.getOrDefault("page", 1);
        int size = (int) params.getOrDefault("size", 10);

        String plateNumber = (String) params.getOrDefault("plateNumber", null);
        String plateType = (String) params.getOrDefault("plateType", null);
        String vehicleType = (String) params.getOrDefault("vehicleType", null);
        String vehicleBrand = (String) params.getOrDefault("vehicleBrand", null);

        PageHelper.startPage(page, size);

        List<CarInfo> list = carInfoMapper.searchByCondition(plateNumber, plateType, vehicleType, vehicleBrand);

        return new PageInfo<>(list);
    }

    @Override
    public int add(CarInfo carInfo) {
        return carInfoMapper.insert(carInfo);
    }

    @Override
    public int update(CarInfo carInfo) {
        return carInfoMapper.update(carInfo);
    }

    @Override
    public int delete(Long id) {
        return carInfoMapper.delete(id);
    }

    @Override
    public int deleteBatch(List<Long> ids){
        return carInfoMapper.deleteBatch(ids);
    }

}
