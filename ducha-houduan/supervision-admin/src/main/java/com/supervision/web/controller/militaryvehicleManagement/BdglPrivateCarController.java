package com.supervision.web.controller.militaryvehicleManagement;

import java.util.List;
import java.util.Map;

import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.common.core.domain.entity.SysUser;
import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.common.utils.SecurityUtils;
import com.supervision.militaryvehicleManagement.domain.BdglThebus;
import com.supervision.peopleManage.domain.BdglPeople;
import com.supervision.peopleManage.service.IBdglPeopleService;
import com.supervision.system.service.ISysDeptService;
import com.supervision.system.service.ISysUserService;
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
import com.supervision.militaryvehicleManagement.domain.BdglPrivateCar;
import com.supervision.militaryvehicleManagement.service.IBdglPrivateCarService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 私家车管理Controller
 *
 * @author supervision
 * @date 2022-03-04
 */
@RestController
@RequestMapping("/militaryvehicleManagement/privateCar")
public class BdglPrivateCarController extends BaseController {
    @Autowired
    private IBdglPrivateCarService bdglPrivateCarService;
    @Autowired
    private ISysUserService iSysUserService;
    @Autowired
    private IBdglPeopleService peopleService;
    @Autowired
    private ISysDeptService deptService;

    /**
     * 查询私家车管理列表
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:privateCar:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglPrivateCar bdglPrivateCar) {

        LoginUser loginUser = SecurityUtils.getLoginUser();
        Long userId = loginUser.getUserId(); //获取到userID
        //通过userID 获取到当前登录人的角色  判断当前人员是否有初审的状态
        List<String> roleKeylist = iSysUserService.selectUserRoleKey(userId);
        boolean isteshu = false;
        boolean isguanli = false;
        for (String string : roleKeylist) {
            if ("SIJIACHE".equals(string)) {
                isteshu = true;
            }
            if ("admin".equals(string)) {
                isguanli = true;
            }
        }

        //判断是特殊权限还是个人
        if (isteshu) {
            startPage();
            List<BdglPrivateCar> list = bdglPrivateCarService.selectBdglPrivateCarList(bdglPrivateCar);
            return getDataTable(list);
        } else {

            if (!isguanli){
                bdglPrivateCar.setUnitId(loginUser.getDeptId());
            }
            startPage();
            List<BdglPrivateCar> list = bdglPrivateCarService.selectBdglPrivateCarLists(bdglPrivateCar);
            return getDataTable(list);
        }
    }

    /**
     * 导出私家车管理列表
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:privateCar:export')")
    @Log(title = "私家车管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglPrivateCar bdglPrivateCar) {
        List<BdglPrivateCar> list = bdglPrivateCarService.selectBdglPrivateCarList(bdglPrivateCar);
        ExcelUtil<BdglPrivateCar> util = new ExcelUtil<BdglPrivateCar>(BdglPrivateCar.class);
        return util.exportExcel(list, "私家车管理数据");
    }

    /**
     * 获取私家车管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:privateCar:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(bdglPrivateCarService.selectBdglPrivateCarById(id));
    }

    /**
     * 新增私家车管理
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:privateCar:add')")
    @Log(title = "私家车管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglPrivateCar bdglPrivateCar) {
        if(bdglPrivateCar.getVehiclenumber()!=null){
            bdglPrivateCar.setVehiclenumber(bdglPrivateCar.getVehiclenumber().replace(" ",""));
        }
        BdglPeople bdglPeople = peopleService.selectBdglPeopleById(Long.valueOf(bdglPrivateCar.getPeopleId()));
        bdglPrivateCar.setUnitId(bdglPeople.getDeptId());
        return toAjax(bdglPrivateCarService.insertBdglPrivateCar(bdglPrivateCar));
    }

    /**
     * 修改私家车管理
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:privateCar:edit')")
    @Log(title = "私家车管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglPrivateCar bdglPrivateCar) {
        if(bdglPrivateCar.getVehiclenumber()!=null){
            String trim = bdglPrivateCar.getVehiclenumber().replace(" ","");
            bdglPrivateCar.setVehiclenumber(trim);
        }
        BdglPeople bdglPeople = peopleService.selectBdglPeopleById(Long.valueOf(bdglPrivateCar.getPeopleId()));
        bdglPrivateCar.setUnitId(bdglPeople.getDeptId());
        return toAjax(bdglPrivateCarService.updateBdglPrivateCar(bdglPrivateCar));
    }

    /**
     * 删除私家车管理
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:privateCar:remove')")
    @Log(title = "私家车管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(bdglPrivateCarService.deleteBdglPrivateCarByIds(ids));
    }

    /**
     * 获取车辆类型
     * @return
     */
    @GetMapping("/getThePrivateBusType")
    public AjaxResult getThePrivateBusType()
    {
        List<Map<String, Object>> list = bdglPrivateCarService.selectVehicleModelList();
        return AjaxResult.success(list);
    }

    /**
     * 查询在位车辆
     * @param bdglPrivateCar
     * @return
     */
    @GetMapping("/zaiweiPrivateCar")
    public TableDataInfo zaiweiPrivateCar(BdglPrivateCar bdglPrivateCar)
    {
        bdglPrivateCar.setState("1");
        List<BdglPrivateCar> list = bdglPrivateCarService.selectBdglPrivateCarList(bdglPrivateCar);
        return getDataTable(list);
    }
}
