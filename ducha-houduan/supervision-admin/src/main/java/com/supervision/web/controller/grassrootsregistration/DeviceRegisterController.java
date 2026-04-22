package com.supervision.web.controller.grassrootsregistration;

import com.supervision.grassrootsregistration.domain.DeviceRegister;
import com.supervision.grassrootsregistration.service.DeviceRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/grassrootsregistration/device/register")
public class DeviceRegisterController {

    @Autowired
    private DeviceRegisterService service;

    /** 列表 */
    @GetMapping("/list")
    public Map<String, Object> list() {
        List<DeviceRegister> list = service.list();
        Map<String, Object> res = new HashMap<>();
        res.put("rows", list);
        res.put("total", list.size());
        return res;
    }

    /** 新增 */
    @PostMapping("/add")
    public void add(@RequestBody DeviceRegister record) {
        System.out.println("!!!!!!"  +record);
        service.add(record);
    }

    /** 详情 */
    @GetMapping("/detail/{id}")
    public DeviceRegister detail(@PathVariable Long id) {
        return service.detail(id);
    }
}
