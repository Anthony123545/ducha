package com.supervision.web.Hikvision.carDoor.web.mapper;


import com.supervision.web.Hikvision.carDoor.web.entity.CarInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CarInfoMapper {

    List<CarInfo> searchByCondition(
            @Param("plateNumber") String plateNumber,
            @Param("plateType") String plateType,
            @Param("vehicleType") String vehicleType,
            @Param("vehicleBrand") String vehicleBrand
    );

    int insert(CarInfo carInfo);

    int update(CarInfo carInfo);

    int delete(Long id);

    int deleteBatch(List<Long> ids);

}
