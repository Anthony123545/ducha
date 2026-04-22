package com.supervision.web.Hikvision.peopleDoor.web.controller;


import com.github.pagehelper.PageInfo;
import com.supervision.web.Hikvision.peopleDoor.web.entity.PersonAccessRecord;
import com.supervision.web.Hikvision.peopleDoor.web.service.PersonAccessRecordService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/peopleDoor/accessRecord")
public class PersonAccessRecordController {

    @Resource
    private PersonAccessRecordService personAccessRecordService;

    @PostMapping("/list")
    public Map<String, Object> list(@RequestBody Map<String, Object> params) {
        PageInfo<PersonAccessRecord> pageInfo = personAccessRecordService.searchByCondition(params);
        Map<String, Object> result = new HashMap<>();
        result.put("total", pageInfo.getTotal());
        result.put("list", pageInfo.getList());
        result.put("pageNum", pageInfo.getPageNum());
        result.put("pageSize", pageInfo.getPageSize());
        return result;
    }

    @PostMapping("/add")
    public Map<String, Object> add(@RequestBody PersonAccessRecord record) {
        boolean success = personAccessRecordService.add(record);
        Map<String, Object> map = new HashMap<>();
        map.put("success", success);
        map.put("msg", success ? "新增成功" : "新增失败");
        return map;
    }

    @PostMapping("/update")
    public Map<String, Object> update(@RequestBody PersonAccessRecord record) {
        boolean success = personAccessRecordService.update(record);
        Map<String, Object> map = new HashMap<>();
        map.put("success", success);
        map.put("msg", success ? "修改成功" : "修改失败");
        return map;
    }

    @PostMapping("/delete")
    public Map<String, Object> delete(@RequestBody Map<String, Object> params) {
        Long id = ((Number)params.get("id")).longValue();
        boolean success = personAccessRecordService.deleteById(id);
        Map<String, Object> map = new HashMap<>();
        map.put("success", success);
        map.put("msg", success ? "删除成功" : "删除失败");
        return map;
    }

    @PostMapping("/batchDelete")
    public Map<String, Object> batchDelete(@RequestBody Map<String, Object> params) {
        @SuppressWarnings("unchecked")
        List<Long> ids = (List<Long>) params.get("ids");
        boolean success = personAccessRecordService.batchDelete(ids);
        Map<String,Object> map = new HashMap<>();
        map.put("success", success);
        map.put("msg", success ? "批量删除成功" : "批量删除失败");
        return map;
    }
}
