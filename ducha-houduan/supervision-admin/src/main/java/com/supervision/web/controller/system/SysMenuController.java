package com.supervision.web.controller.system;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.supervision.bdglregular.domain.BdglRegular;
import com.supervision.bdglregular.domain.BdglSpecial;
import com.supervision.bdglregular.service.IBdglRegularService;
import com.supervision.bdglregular.service.IBdglSpecialService;
import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.grassrootsregistration.domain.BdglDiary;
import com.supervision.grassrootsregistration.service.IBdglDiaryService;
import com.supervision.system.service.impl.SysDeptServiceImpl;
import com.supervision.web.controller.bdglregular.BdglRegularController;
import com.supervision.workingArrangements.domain.BdglWorkProgress;
import com.supervision.workingArrangements.domain.BdglWorkTask;
import com.supervision.workingArrangements.service.IBdglPlanTaskService;
import com.supervision.workingArrangements.service.IBdglWorkProgressService;
import com.supervision.workingArrangements.service.IBdglWorkTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.supervision.common.annotation.Log;
import com.supervision.common.constant.UserConstants;
import com.supervision.common.core.controller.BaseController;
import com.supervision.common.core.domain.AjaxResult;
import com.supervision.common.core.domain.entity.SysMenu;
import com.supervision.common.enums.BusinessType;
import com.supervision.common.utils.StringUtils;
import com.supervision.system.service.ISysMenuService;

/**
 * 菜单信息
 *
 * @author supervision
 */
@RestController
@RequestMapping("/system/menu")
public class SysMenuController extends BaseController {
    @Autowired
    private ISysMenuService menuService;
    @Autowired
    private IBdglDiaryService diaryService;
    @Autowired
    private IBdglWorkTaskService workTaskService;
    @Autowired
    private IBdglPlanTaskService planTaskService;
    @Autowired
    private IBdglWorkProgressService workProgressService;
    @Autowired
    private IBdglRegularService bdglRegularService;
    @Autowired
    private IBdglSpecialService bdglSpecialService;

    @Autowired
    private SysDeptServiceImpl sysDeptService;


    /**
     * 获取菜单列表
     */
    @PreAuthorize("@ss.hasPermi('system:menu:list')")
    @GetMapping("/list")
    public AjaxResult list(SysMenu menu) {
        List<SysMenu> menus = menuService.selectMenuList(menu, getUserId());
        return AjaxResult.success(menus);
    }

    /**
     * 根据菜单编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:menu:query')")
    @GetMapping(value = "/{menuId}")
    public AjaxResult getInfo(@PathVariable Long menuId) {
        return AjaxResult.success(menuService.selectMenuById(menuId));
    }

    /**
     * 获取菜单下拉树列表
     */
    @GetMapping("/treeselect")
    public AjaxResult treeselect(SysMenu menu) {
        List<SysMenu> menus = menuService.selectMenuList(menu, getUserId());
        return AjaxResult.success(menuService.buildMenuTreeSelect(menus));
    }

    /**
     * 加载对应角色菜单列表树
     */
    @GetMapping(value = "/roleMenuTreeselect/{roleId}")
    public AjaxResult roleMenuTreeselect(@PathVariable("roleId") Long roleId) {
        List<SysMenu> menus = menuService.selectMenuList(getUserId());
        AjaxResult ajax = AjaxResult.success();
        ajax.put("checkedKeys", menuService.selectMenuListByRoleId(roleId));
        ajax.put("menus", menuService.buildMenuTreeSelect(menus));
        return ajax;
    }

    /**
     * 新增菜单
     */
    @PreAuthorize("@ss.hasPermi('system:menu:add')")
    @Log(title = "菜单管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysMenu menu) {
        if (UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu))) {
            return AjaxResult.error("新增菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        } else if (UserConstants.YES_FRAME.equals(menu.getIsFrame()) && !StringUtils.ishttp(menu.getPath())) {
            return AjaxResult.error("新增菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
        }
        menu.setCreateBy(getUsername());
        return toAjax(menuService.insertMenu(menu));
    }

    /**
     * 修改菜单
     */
    @PreAuthorize("@ss.hasPermi('system:menu:edit')")
    @Log(title = "菜单管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysMenu menu) {
        if (UserConstants.NOT_UNIQUE.equals(menuService.checkMenuNameUnique(menu))) {
            return AjaxResult.error("修改菜单'" + menu.getMenuName() + "'失败，菜单名称已存在");
        } else if (UserConstants.YES_FRAME.equals(menu.getIsFrame()) && !StringUtils.ishttp(menu.getPath())) {
            return AjaxResult.error("修改菜单'" + menu.getMenuName() + "'失败，地址必须以http(s)://开头");
        } else if (menu.getMenuId().equals(menu.getParentId())) {
            return AjaxResult.error("修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己");
        }
        menu.setUpdateBy(getUsername());
        return toAjax(menuService.updateMenu(menu));
    }

    /**
     * 删除菜单
     */
    @PreAuthorize("@ss.hasPermi('system:menu:remove')")
    @Log(title = "菜单管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{menuId}")
    public AjaxResult remove(@PathVariable("menuId") Long menuId) {
        if (menuService.hasChildByMenuId(menuId)) {
            return AjaxResult.error("存在子菜单,不允许删除");
        }
        if (menuService.checkMenuExistRole(menuId)) {
            return AjaxResult.error("菜单已分配,不允许删除");
        }
        return toAjax(menuService.deleteMenuById(menuId));
    }

    @GetMapping("/getmeun")
    public AjaxResult getmeun() {
        LoginUser loginUser = getLoginUser();
        String username = loginUser.getUsername();
        if (username.equals("admin")) {
            return AjaxResult.success("admin");
        }
        List<SysMenu> menus = menuService.selectRoleMeun(loginUser.getUserId());
        String meunss = "";
        for (SysMenu menu : menus) {
            meunss += menu.getMenuName() + ",";
        }
        return AjaxResult.success(meunss);
    }

    @GetMapping("/noReminder")
    public AjaxResult noReminder() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String date = simpleDateFormat.format(new Date());
        LoginUser loginUser = getLoginUser();
        int work = 0;
        int diary = 0;
        int inspect = 0;

        //查看当前登录人是否登记要事日记
        BdglDiary bdglDiary = new BdglDiary();
        bdglDiary.setDiaryTime(date);
        SysDept sysDept = sysDeptService.selectDeptById(loginUser.getDeptId());
        //TODO  哪些部门的人不需要登记
        bdglDiary.setUnitId(Integer.parseInt(loginUser.getDeptId().toString()));
        List<BdglDiary> bdglDiaries = diaryService.selectBdglDiaryList(bdglDiary);
        if (bdglDiaries.size() == 0&&sysDept.getIsRead().equals("1")) {
            diary++;
        }
        //查看当前登录人是否有工作任务活工作计划未执行
        BdglWorkProgress workProgress = new BdglWorkProgress();
        workProgress.setPeopleId(loginUser.getUserId());

        List<BdglWorkProgress> workProgress1 = workProgressService.selectBdglWorkProgressList(workProgress);
        for (BdglWorkProgress bdglWorkProgress : workProgress1) {
            if (!bdglWorkProgress.getSchedule().equals(100L)) {
                work++;
                break;
            }
        }



        //查询当前等历任经常性检查是否有未整改事项
        BdglRegular bdglRegular = new BdglRegular();
        bdglRegular.setInspectedUnitId(loginUser.getDeptId());
        bdglRegular.setCurrentState("待处理");
        BdglSpecial special = new BdglSpecial();
        special.setInspectedUnitId(loginUser.getDeptId());
        special.setCurrentState("待处理");
        List<BdglRegular> bdglRegulars = bdglRegularService.selectBdglRegularList(bdglRegular);
        List<BdglSpecial> bdglSpecials = bdglSpecialService.selectBdglSpecialList(special);
        if (bdglRegulars.size() > 0 || bdglSpecials.size() > 0) {
            inspect++;
        }

//        if(loginUser.getDeptId()==203||loginUser.getDeptId()==204||loginUser.getDeptId()==205||loginUser.getDeptId()==206||loginUser.getDeptId()==100){
//            work=0;
//            diary=0;
//            inspect=0;
//        }
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(diary);
        integers.add(work);
        integers.add(inspect);
        return AjaxResult.success(integers);

    }
}