package com.supervision.grassrootsregistration.service;
import com.supervision.grassrootsregistration.domain.DeviceRegister;

import java.util.List;

public interface DeviceRegisterService {
    List<DeviceRegister> list();

    DeviceRegister detail(Long id);

    void add(DeviceRegister record);
}
