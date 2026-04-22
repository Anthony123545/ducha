package com.supervision.grassrootsregistration.mapper;

import com.supervision.grassrootsregistration.domain.DeviceRegister;

import java.util.List;


public interface DeviceRegisterMapper {

    List<DeviceRegister> selectList();

    DeviceRegister selectById(Long id);

    int insert(DeviceRegister record);
}
