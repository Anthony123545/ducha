package com.supervision.web.controller.grassrootsregistration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.common.utils.DateUtils;
import com.supervision.common.utils.StringUtils;
import com.supervision.grassrootsregistration.domain.*;
import com.supervision.grassrootsregistration.resultVo.SysDeptDto;
import com.supervision.grassrootsregistration.service.*;
import com.supervision.peopleManage.domain.BdglPeople;
import com.supervision.peopleManage.service.IBdglPeopleService;
import com.supervision.system.service.ISysDeptService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.supervision.common.annotation.Log;
import com.supervision.common.core.controller.BaseController;
import com.supervision.common.core.domain.AjaxResult;
import com.supervision.common.enums.BusinessType;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 军事训练登记簿Controller
 *
 * @author supervision
 * @date 2022-02-26
 */
@RestController
@RequestMapping("/grassrootsregistration/bdgldrill")
public class BdglDrillController extends BaseController {
    @Autowired
    private IBdglDrillService bdglDrillService;
    @Autowired
    private IBdglDrillFuService drillFuService;
    @Autowired
    private IBdglWeekworkService weekworkService;
    @Autowired
    private IBdglWeekworkRegisterService weekworkRegisterService;
    @Autowired
    private IBdglPeopleService peopleService;
    @Autowired
    private IBdglDrillTypeService typeService;
    @Autowired
    private ISysDeptService deptService;


    /**
     * 查询军事训练登记簿列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdgldrill:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglDrill bdglDrill) {
        startPage();
        List<BdglDrill> list = bdglDrillService.selectBdglDrillList(bdglDrill);
        for (BdglDrill drill : list) {
            if (drill.getUnitId() != null) {
                SysDept sysDept = deptService.selectDeptById(Long.valueOf(drill.getUnitId()));
                drill.setUnitname(sysDept.getDeptName());
            }
            if (drill.getTrainingCategory() != null) {
                BdglDrillType bdglDrillType = typeService.selectBdglDrillTypeById(Long.valueOf(drill.getTrainingCategory()));
                drill.setTrainingCategory(bdglDrillType.getAncestors());
            }
        }
        return getDataTable(list);
    }

    /**
     * 领导查看军事训练登记簿列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdgldrills:list')")
    @GetMapping("/drillList")
    public TableDataInfo drillList(String year) {
        if (StringUtils.isNotEmpty(year)){
            startPage();
            List<SysDeptDto> list = bdglDrillService.selectDrillList(year);
            return getDataTable(list);
        }
        List<List<SysDeptDto>> list = deptService.getMeetRecordsList();
        return getDataTable(list);
    }

    /**
     * 获取军事训练登记簿详细信息单位下的所有信息
     */
    @GetMapping(value = "/getInfoByUnitId")
    public TableDataInfo getInfoByUnitId(@RequestParam String unitId,String year) {
        List<BdglDrill> drillList = bdglDrillService.selectDrillByUnitId(Integer.parseInt(unitId),year);
        drillList.forEach(drill->{
            BdglWeekworkRegister bdglWeekworkRegister = new BdglWeekworkRegister();
            StringBuilder neiRong = new StringBuilder();
//            //下询一周工作安排
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//            String format = simpleDateFormat.format(drill.getDrillTime());
//            bdglWeekworkRegister.setStartTime(format);
//            bdglWeekworkRegister.setEndTime(simpleDateFormat.format(drill.getDrillEndtime()));
//            bdglWeekworkRegister.setUnitId(Integer.parseInt(unitId));
//            List<BdglWeekworkRegister> bdglWeekworkRegisters = weekworkRegisterService.selectBdglWeekworkRegisterLists(bdglWeekworkRegister);
//            if (bdglWeekworkRegisters != null) {
//                for (BdglWeekworkRegister weekworkRegister : bdglWeekworkRegisters) {
//                    neiRong.append(weekworkRegister.getContents()).append(",");
//                }
//            }
//            drill.setWeekworkRegisters(bdglWeekworkRegisters);
//            if (!"".equals(neiRong.toString())) {
//
//                drill.setCommon(neiRong.substring(0, neiRong.length() - 1));
//            }

            if (drill.getTrainingCategory() != null) {
                BdglDrillType bdglDrillType = typeService.selectBdglDrillTypeById(Long.valueOf(drill.getTrainingCategory()));
                drill.setName(bdglDrillType.getAncestors());
            }
        });
        return getDataTable(drillList);
    }

    /**
     * 导出军事训练登记簿列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdgldrill:export')")
    @Log(title = "军事训练登记簿", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglDrill bdglDrill) {
        List<BdglDrill> list = bdglDrillService.selectBdglDrillList(bdglDrill);
        ExcelUtil<BdglDrill> util = new ExcelUtil<BdglDrill>(BdglDrill.class);
        return util.exportExcel(list, "军事训练登记簿数据");
    }

    /**
     * 获取军事训练登记簿详细信息
     */
    @PreAuthorize("@ss.hasAnyPermi('grassrootsregistration:bdgldrill:query,grassrootsregistration:bdgldrills:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id) {
        BdglDrill bdglDrill = bdglDrillService.selectBdglDrillById(id);
        BdglDrillFu bdglDrillFu = new BdglDrillFu();
        bdglDrillFu.setDrillid(Long.valueOf(id));
        List<BdglDrillFu> bdglDrillFus = drillFuService.selectBdglDrillFuList(bdglDrillFu);
        bdglDrill.setDrillFus(bdglDrillFus);

        BdglWeekworkRegister bdglWeekworkRegister = new BdglWeekworkRegister();
        StringBuilder neiRong = new StringBuilder();
        //下询一周工作安排
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(bdglDrill.getDrillTime());
        bdglWeekworkRegister.setStartTime(format);
        bdglWeekworkRegister.setEndTime(simpleDateFormat.format(bdglDrill.getDrillEndtime()));
        bdglWeekworkRegister.setUnitId(Integer.parseInt(bdglDrill.getUnitId()));
        List<BdglWeekworkRegister> bdglWeekworkRegisters = weekworkRegisterService.selectBdglWeekworkRegisterLists(bdglWeekworkRegister);
        if (bdglWeekworkRegisters != null) {
            for (BdglWeekworkRegister weekworkRegister : bdglWeekworkRegisters) {
                neiRong.append(weekworkRegister.getContents()).append(",");
            }
        }
        bdglDrill.setWeekworkRegisters(bdglWeekworkRegisters);

        if (!"".equals(neiRong.toString())) {

            bdglDrill.setCommon(neiRong.substring(0, neiRong.length() - 1));
        }
        if (bdglDrill.getTrainingCategory() != null) {
            BdglDrillType bdglDrillType = typeService.selectBdglDrillTypeById(Long.valueOf(bdglDrill.getTrainingCategory()));
            bdglDrill.setName(bdglDrillType.getAncestors());
        }
        return AjaxResult.success(bdglDrill);
    }

    /**
     * 新增军事训练登记簿
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdgldrill:add')")
    @Log(title = "军事训练登记簿", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglDrill bdglDrill) {
        int i = bdglDrillService.insertBdglDrill(bdglDrill);
        if (bdglDrill.getDrillFus().size() > 0) {
            for (BdglDrillFu drillFus : bdglDrill.getDrillFus()) {
                if (drillFus.getPeopleId() != null) {
                    BdglPeople bdglPeople = peopleService.selectBdglPeopleById(drillFus.getPeopleId());
                    drillFus.setPeopleName(bdglPeople.getName());
                    drillFus.setDrillid(Long.valueOf(bdglDrill.getId()));
                    drillFuService.insertBdglDrillFu(drillFus);
                }
            }
        }
        return toAjax(i);
    }

    /**
     * 修改军事训练登记簿
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdgldrill:edit')")
    @Log(title = "军事训练登记簿", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglDrill bdglDrill) {
        List<BdglDrillFu> drillFus = bdglDrill.getDrillFus();
        if (drillFus != null && drillFus.size() > 0) {
            for (BdglDrillFu fus : drillFus) {
                if (fus.getPeopleId() != null) {
                    BdglPeople bdglPeople = peopleService.selectBdglPeopleById(fus.getPeopleId());
                    if (fus.getId() == null) {
                        fus.setPeopleName(bdglPeople.getName());
                        fus.setDrillid(Long.valueOf(bdglDrill.getId()));
                        drillFuService.insertBdglDrillFu(fus);
                    } else {
                        fus.setPeopleName(bdglPeople.getName());
                        fus.setDrillid(Long.valueOf(bdglDrill.getId()));
                        drillFuService.updateBdglDrillFu(fus);
                    }
                }
            }
        }
        return toAjax(bdglDrillService.updateBdglDrill(bdglDrill));
    }

    /**
     * 删除军事训练登记簿`
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdgldrill:remove')")
    @Log(title = "军事训练登记簿", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids) {
        return toAjax(bdglDrillService.deleteBdglDrillByIds(ids));
    }

    @GetMapping("/getweekwork")
    public AjaxResult getweekwork(BdglDrill bdglDrill) {
        BdglWeekworkRegister bdglWeekworkRegister = new BdglWeekworkRegister();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(bdglDrill.getDrillTime());
        bdglWeekworkRegister.setStartTime(format);
        bdglWeekworkRegister.setEndTime(simpleDateFormat.format(bdglDrill.getDrillEndtime()));
        bdglWeekworkRegister.setUnitId(Integer.parseInt(bdglDrill.getUnitId()));
        List<BdglWeekworkRegister> bdglWeekworkRegisters = weekworkRegisterService.selectBdglWeekworkRegisterLists(bdglWeekworkRegister);
        return AjaxResult.success(bdglWeekworkRegisters);
    }
}
