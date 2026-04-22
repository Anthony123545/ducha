package com.supervision.grassrootsregistration.service.impl;

import com.supervision.grassrootsregistration.domain.DeviceRegister;
import com.supervision.grassrootsregistration.mapper.DeviceRegisterMapper;
import com.supervision.grassrootsregistration.service.DeviceRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceRegisterServiceImpl implements DeviceRegisterService {

    @Autowired
    private DeviceRegisterMapper mapper;

    @Override
    public List<DeviceRegister> list() {
        return mapper.selectList();
    }

    @Override
    public DeviceRegister detail(Long id) {
        return mapper.selectById(id);
    }

    @Override
    public void add(DeviceRegister record) {
        mapper.insert(record);
    }
}
