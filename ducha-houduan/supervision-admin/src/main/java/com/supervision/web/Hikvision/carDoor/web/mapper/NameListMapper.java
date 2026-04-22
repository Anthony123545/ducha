package com.supervision.web.Hikvision.carDoor.web.mapper;


import com.supervision.web.Hikvision.carDoor.web.entity.NameList;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NameListMapper {

    List<NameList> searchByCondition(@Param("deviceId") Long deviceId,
                                     @Param("type") Integer type,
                                     @Param("licensePlate") String licensePlate);

    List<NameList> getByIds(@Param("ids") List<Long> ids);

    NameList getById(@Param("id") Long id);

    int exists(@Param("deviceId") Long deviceId,
               @Param("type") Integer type,
               @Param("licensePlate") String licensePlate);

    int existsExcludeId(@Param("deviceId") Long deviceId,
                        @Param("type") Integer type,
                        @Param("licensePlate") String licensePlate,
                        @Param("id") Long id);

    int insert(NameList nameList);

    int update(NameList nameList);

    int delete(@Param("id") Long id);

    int batchDelete(@Param("ids") List<Long> ids);

}
