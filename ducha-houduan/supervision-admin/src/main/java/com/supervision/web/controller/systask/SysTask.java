package com.supervision.web.controller.systask;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.supervision.common.utils.http.HttpUtils;
import com.supervision.grassrootsregistration.domain.BdglEmployee;
import com.supervision.grassrootsregistration.service.IBdglAttendanceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("sysTask")
public class SysTask {
    @Autowired
    private IBdglAttendanceRecordService bdglAttendanceRecordService;


    /**
     * 定时获取门禁人员数据
     */
    public void getemployeeinfo(){
        bdglAttendanceRecordService.getemployeeinfo();
    }

}
