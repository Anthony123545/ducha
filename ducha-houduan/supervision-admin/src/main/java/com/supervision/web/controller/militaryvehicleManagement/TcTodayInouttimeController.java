package com.supervision.web.controller.militaryvehicleManagement;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.supervision.common.annotation.Log;
import com.supervision.common.core.controller.BaseController;
import com.supervision.common.core.domain.AjaxResult;
import com.supervision.common.enums.BusinessType;
import com.supervision.militaryvehicleManagement.domain.TcTodayInouttime;
import com.supervision.militaryvehicleManagement.service.ITcTodayInouttimeService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 车辆外接数据Controller
 * 
 * @author ruoyi
 * @date 2022-05-22
 */
@RestController
@RequestMapping("/militaryvehicleManagement/inouttime")
public class TcTodayInouttimeController extends BaseController
{
    @Autowired
    private ITcTodayInouttimeService tcTodayInouttimeService;

    /**
     * 查询车辆外接数据列表
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:inouttime:list')")
    @GetMapping("/list")
    public TableDataInfo list(TcTodayInouttime tcTodayInouttime)
    {
        startPage();
        List<TcTodayInouttime> list = tcTodayInouttimeService.selectTcTodayInouttimeList(tcTodayInouttime);
        return getDataTable(list);
    }


}