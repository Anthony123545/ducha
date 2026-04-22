package com.supervision.web.controller.grassrootsregistration;

import com.supervision.common.core.controller.BaseController;
import com.supervision.common.core.domain.AjaxResult;
import com.supervision.common.core.domain.entity.SysRole;
import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.common.core.page.TableDataInfo;
import com.supervision.common.utils.StringUtils;
import com.supervision.grassrootsregistration.resultVo.SysDeptDto;
import com.supervision.peopleManage.domain.BdglPeople;
import com.supervision.peopleManage.domain.BdglPeopleTypeStatistics;
import com.supervision.peopleManage.service.IBdglPeopleService;
import com.supervision.peopleManage.service.IBdglPeopleTypeStatisticsService;
import com.supervision.system.mapper.SysUserMapper;
import com.supervision.system.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**人员名册*/
@RestController
@RequestMapping("/grassrootsregistration/personnelRoster")
public class PersonnelRosterController extends BaseController {

    @Autowired
    private IBdglPeopleService peopleService;
    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private ISysDeptService deptService;

    @Resource
    private IBdglPeopleTypeStatisticsService bdglPeopleTypeStatisticsService;

    /**人员列表*/
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:personnelRoster:list')")
    @GetMapping("/getPeopleList")
    public TableDataInfo getPeopleList(BdglPeople bdglPeople){
        LoginUser loginUser = getLoginUser();
        List<SysRole> sysRoles = userMapper.selectRole(loginUser.getUserId());
        String role = CommonsController.getRole(sysRoles);
        //判断当前角色是否是最高权限   不是返回当前部门数据
        if(!role.equals("admin")){
            bdglPeople.setDeptId(loginUser.getDeptId());
        }
        startPage();
        List<BdglPeople> bdglPeople1 = peopleService.selectPowerPeoleLists(bdglPeople);
        return getDataTable(bdglPeople1);
    }



    /**获取当前部门人员信息*/
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:personnelRoster:getDeptXiangQi')")
    @GetMapping("/getDeptXiangQi")
    public AjaxResult getDeptXiangQi(BdglPeople bdglPeople){
//        Map<String,Object> map=peopleService.getDeptXiangQi(bdglPeople);
        BdglPeopleTypeStatistics bdglPeopleTypeStatistics = new BdglPeopleTypeStatistics();
        bdglPeopleTypeStatistics.setUnitId(bdglPeople.getDeptId());
        List<BdglPeopleTypeStatistics> selectBdglPeopleTypeStatisticsList = bdglPeopleTypeStatisticsService.selectBdglPeopleTypeStatisticsList(bdglPeopleTypeStatistics);
        if (selectBdglPeopleTypeStatisticsList.size()==0){
            bdglPeopleTypeStatistics.setIncumbentsGb("0");
            bdglPeopleTypeStatistics.setIncumbentsSg("0");
            bdglPeopleTypeStatistics.setIncumbentsYwb("0");

            bdglPeopleTypeStatistics.setInpreparationGb("0");
            bdglPeopleTypeStatistics.setInpreparationSg("0");
            bdglPeopleTypeStatistics.setInpreparationYwb("0");

            bdglPeopleTypeStatistics.setOutpreparationGb("0");
            bdglPeopleTypeStatistics.setOutpreparationSg("0");
            bdglPeopleTypeStatistics.setOutpreparationYwb("0");
        }else {
            bdglPeopleTypeStatistics = selectBdglPeopleTypeStatisticsList.get(0);
        }
        List<BdglPeople> bdglPeople1 = peopleService.selectPowerPeoleLists(bdglPeople);
        ArrayList<Object> list = new ArrayList<>();
        list.add(bdglPeopleTypeStatistics);
        list.add(bdglPeople1);
        return AjaxResult.success(list);
    }

    @PreAuthorize("@ss.hasPermi('grassrootsregistration:personnelRoster:list')")
    @GetMapping("/getPersonnelRosterList")
//    public TableDataInfo getPersonnelRosterList(String year)
//    {
//        if (StringUtils.isNotEmpty(year)){
//            startPage();
//            List<SysDeptDto> list = peopleService.getPeopleList(year);
//            return getDataTable(list);
//        }
//        List<List<SysDeptDto>> list = deptService.getMeetpeopleList();
//        return getDataTable(list);
//    }
    public TableDataInfo getPersonnelRosterList(@RequestParam(required = false) String year) {
        startPage(); // ✅ 建议统一放在上面，分页总是生效

        List<SysDeptDto> list;
        if (StringUtils.isNotEmpty(year)) {
            list = peopleService.getPeopleList(year);
        } else {
            // 如果 deptService.getMeetpeopleList() 返回的是 List<List<SysDeptDto>>
            // 就改成扁平化
            List<List<SysDeptDto>> temp = deptService.getMeetpeopleList();
            list = temp.stream().flatMap(Collection::stream).collect(Collectors.toList());
        }
        return getDataTable(list);
    }

    /**
     * 领导查询团支部会议登记本列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:personnelRoster:list')")
    @GetMapping("/getPersonnelRosterOnUnitList")
    public TableDataInfo getPersonnelRosterOnUnitList(Integer unitId)
    {
        startPage();
        List<BdglPeople> list = peopleService.getPeopleOnUnitList(unitId);
        return getDataTable(list);
    }

}
