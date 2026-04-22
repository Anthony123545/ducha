package com.supervision.web.controller.grassrootsregistration;

import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.common.core.domain.entity.SysDictData;
import com.supervision.common.core.domain.entity.SysUser;
import com.supervision.grassrootsregistration.domain.BdglAttendanceRecord;
import com.supervision.grassrootsregistration.service.IBdglAttendanceRecordService;
import com.supervision.peopleChuRu.domain.BdglLeave;
import com.supervision.peopleChuRu.domain.BdglLeaveOne;
import com.supervision.peopleChuRu.service.IBdglLeaveOneService;
import com.supervision.peopleChuRu.service.IBdglLeaveService;
import com.supervision.peopleManage.domain.BdglPeople;
import com.supervision.peopleManage.service.IBdglPeopleService;
import com.supervision.system.service.ISysDeptService;
import com.supervision.system.service.ISysDictDataService;
import com.supervision.system.service.ISysUserService;
import io.swagger.models.auth.In;
import org.apache.catalina.User;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import com.supervision.common.annotation.Log;
import com.supervision.common.core.controller.BaseController;
import com.supervision.common.core.domain.AjaxResult;
import com.supervision.common.enums.BusinessType;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 考勤记录Controller
 *
 * @author ruoyi
 * @date 2022-10-20
 */
@RestController
@RequestMapping("/grassrootsregistration/attendancerecord")
public class BdglAttendanceRecordController extends BaseController
{
    @Autowired
    private IBdglAttendanceRecordService bdglAttendanceRecordService;
    
    @Autowired
    private ISysUserService sysUserService;
    
    @Autowired
    private ISysDeptService sysDeptService;
    
    @Autowired
    private IBdglLeaveOneService bdglLeaveOneService;
    
    @Autowired
    private IBdglPeopleService bdglPeopleService;
    
    @Autowired
    private ISysDictDataService sysDictDataService;
    
    @Autowired
    private IBdglLeaveService bdglLeaveService;
    
    /**
     * 查询考勤记录列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:attendancerecord:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglAttendanceRecord bdglAttendanceRecord)
    {
        bdglAttendanceRecordService.getAttendanceRecordsListByMultipleIp(bdglAttendanceRecord,1);
        startPage();
        List<BdglAttendanceRecord> list = bdglAttendanceRecordService.selectBdglAttendanceRecordList(bdglAttendanceRecord);
        return getDataTable(list);
    }

    /**
     * 导出考勤记录列表
     */
//    @PreAuthorize("@ss.hasPermi('grassrootsregistration:attendancerecord:export')")
    @Log(title = "考勤记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglAttendanceRecord bdglAttendanceRecord)
    {
        List<BdglAttendanceRecord> list = bdglAttendanceRecordService.getAttendanceRecordsListByMultipleIp(bdglAttendanceRecord,2);
       // List<BdglAttendanceRecord> list = bdglAttendanceRecordService.selectBdglAttendanceRecordList(bdglAttendanceRecord);
        ExcelUtil<BdglAttendanceRecord> util = new ExcelUtil<BdglAttendanceRecord>(BdglAttendanceRecord.class);
        return util.exportExcel(list, "考勤记录数据");
    }
    
    
    
    
}
