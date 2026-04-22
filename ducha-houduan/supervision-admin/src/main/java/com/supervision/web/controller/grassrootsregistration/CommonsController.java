package com.supervision.web.controller.grassrootsregistration;


import com.supervision.common.core.controller.BaseController;
import com.supervision.common.core.domain.AjaxResult;
import com.supervision.common.core.domain.TreeSelect;
import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.common.core.domain.entity.SysRole;
import com.supervision.common.core.domain.entity.SysUser;
import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.grassrootsregistration.domain.BdglDiary;
import com.supervision.grassrootsregistration.service.IBdglDiaryService;
import com.supervision.peopleManage.domain.BdglPeople;
import com.supervision.peopleManage.service.IBdglPeopleService;
import com.supervision.system.mapper.SysUserMapper;
import com.supervision.system.service.ISysDeptService;
import com.supervision.system.service.ISysUserService;
import com.supervision.web.controller.common.CommonController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.*;

@RestController
@RequestMapping("/grassrootsregistration/common")
public class CommonsController extends BaseController {
    @Autowired
    private IBdglPeopleService bdglPeopleService;
    @Autowired
    private ISysDeptService sysDeptService;
    @Autowired
    private ISysUserService sysUserService;
    @Autowired ISysUserService userService;
    @Autowired
    private IBdglDiaryService bdglDiaryService;
    @Autowired
    private SysUserMapper userMapper;

    /**
     * 获取主持人  出席人   记录人
     * */
    @GetMapping("/getZhuChiRen")
    public AjaxResult getZhuChiRen(Long deptId)
    {

        BdglPeople bdglPeople = new BdglPeople();
        bdglPeople.setDeptId(deptId);
        /*查询当前部门基夏季部门所有人员*/
        List<BdglPeople> list = bdglPeopleService.selectPowerPeoleLists(bdglPeople);
        return AjaxResult.success(list);
    }
    /**获取人员*/
    @GetMapping("/list")
    public AjaxResult list(BdglPeople bdglPeople)
    {

        /*查询当前部门基夏季部门所有人员*/
        List<BdglPeople> list = bdglPeopleService.selectPowerPeoleLists(bdglPeople);
        return AjaxResult.success(list);
    }
    /**
     * 获取部门列表
     * */
    @GetMapping("/getDept")
    public AjaxResult getDept(String type){
        LoginUser loginUser = getLoginUser();
        SysUser sysUser = userService.selectUserById(loginUser.getUserId());
        List<SysRole> sysRoles=userMapper.selectRole(loginUser.getUserId());
        String role="";
        if(sysRoles!=null){
            for (SysRole sysRole : sysRoles) {
                if(sysRole.getRoleKey().equals("admin")||sysRole.getRoleKey().equals("baomi")||sysRole.getRoleKey().equals("CLSPR")){
                    role=sysRole.getRoleKey();
                    break;
                }
            }
        }
        List<SysDept> sysDepts1=new ArrayList<>();
        if(sysUser.getUserName().equals("admin")||type!=null||role.equals("admin")||role.equals("baomi")||role.equals("CLSPR")){
            SysDept sysDept = new SysDept();
            List<SysDept> sysDepts = sysDeptService.selectDeptLists(sysDept);
//            for (SysDept dept : sysDepts) {
//                if (dept.getDeptId() != 203L && dept.getDeptId() != 204 && dept.getDeptId() != 205 && dept.getDeptId() != 206 && dept.getDeptId() != 100) {
//                    if (!dept.getDeptName().equals("俱乐部") && !dept.getDeptName().equals("未分配")&& !dept.getDeptName().contains("支援保障")
//                            && !dept.getDeptName().contains("生活服务中心")&& !dept.getDeptName().contains("门诊部")) {
//
//                        sysDepts1.add(dept);
//
//                    }
//                }
//            }
            return AjaxResult.success(sysDeptService.buildDeptTreeSelect(sysDepts));
        }
        SysDept sysDept = new SysDept();
        sysDept.setDeptId(loginUser.getDeptId());
        List<SysDept> sysDepts = sysDeptService.selectDeptList(sysDept);
//        for (SysDept dept : sysDepts) {
//            if (dept.getDeptId() != 203L && dept.getDeptId() != 204 && dept.getDeptId() != 205 && dept.getDeptId() != 206 && dept.getDeptId() != 100) {
//                if (!dept.getDeptName().equals("俱乐部") && !dept.getDeptName().equals("未分配")&& !dept.getDeptName().contains("支援保障")
//                        && !dept.getDeptName().contains("生活服务中心")&& !dept.getDeptName().contains("门诊部")) {
//
//                    sysDepts1.add(dept);
//
//                }
//            }
//        }
        return AjaxResult.success(sysDeptService.buildDeptTreeSelect(sysDepts));
    }

    @GetMapping("/getAllDept")
    public AjaxResult getAllDept(){
        List<SysDept> sysDeptList = sysDeptService.selectAllDeptList(new SysDept());
        return AjaxResult.success(sysDeptService.buildDeptTreeSelect(sysDeptList));
    }

    /**获取用户列表*/
    @GetMapping("/getUser")
    public AjaxResult getUser(){
        List<SysUser> sysUsers = sysUserService.selectUserList(new SysUser());
        return AjaxResult.success(sysUsers);
    }
    /**获取当前部门用户列表*/
    @GetMapping("/getUsers")
    public AjaxResult getUsers(Long deptId){
//        SysUser sysUser = new SysUser();
//        sysUser.setDeptId(deptId);
//        sysUser.setUserId(getLoginUser().getUserId());
//        List<SysUser> sysUsers = sysUserService.selectUserLists(sysUser);
        if(deptId==null){
            return AjaxResult.error("部门id为空");
        }
        return AjaxResult.success(findApprover(deptId));
    }

    private List<SysUser> findApprover(Long deptId){
        //如果是连级，查询连级及营级的审批人；如果是营级的，查询营级的人
        SysDept sysDept = sysDeptService.selectDeptById(deptId);
        SysUser sysUser = new SysUser();
        if (sysDept.getType()!=null&&sysDept.getType()==1){//0.营 1.连
            sysUser.setDeptIdList(Arrays.asList(sysDept.getDeptId(),sysDept.getParentId()));
        }else {
            sysUser.setDeptIdList(Arrays.asList(sysDept.getDeptId()));
        }
        List<SysUser> sysUserList = sysUserService.selectUserList(sysUser);
        return sysUserList;
    }

    /**获取多个部门下的用户数据列表*/
    @GetMapping("/getUnitUsers")
    public AjaxResult getUnitUsers(String deptIds){
        SysUser sysUser = new SysUser();
        if(deptIds==null){
            return AjaxResult.error("部门id为空"+deptIds);
        }
        String[] units= deptIds.split(",");
        List<SysUser> sysUserlist =new ArrayList();
        sysUserlist.clear();
        for (String u : units){
            sysUser.setDeptId(Long.valueOf(u));
            List<SysUser> sysUsers = sysUserService.selectUserLists(sysUser);
            for (int i=0;i<sysUsers.size();i++){
                SysUser s = sysUsers.get(i);
                sysUserlist.add(s);
            }
        }

        return AjaxResult.success(sysUserlist);
    }
    //单位树形结构删除  政治部  参谋部  技术部  保障部
    @GetMapping("/get")
    public AjaxResult get(){
        ArrayList<SysDept> sysDepts1 = new ArrayList<>();
        List<SysDept> sysDepts = sysDeptService.selectDeptLists(new SysDept());
        for (int i = 0; i < sysDepts.size(); i++) {
            Long deptId = sysDepts.get(i).getDeptId();
            boolean equals = deptId==204;
            if(sysDepts.get(i).getDeptId()==203||sysDepts.get(i).getDeptId()==204||sysDepts.get(i).getDeptId()==205||sysDepts.get(i).getDeptId()==206){
                //sysDepts.remove(i);
            }else {
                sysDepts1.add(sysDepts.get(i));
            }
            /*-------------------------------------------查询当前部门是否审批--------------------------------------------------------------*/
            Date dNow = new Date();   //当前时间
            Calendar calendar = Calendar.getInstance(); //得到日历
            calendar.setTime(dNow);//把当前时间赋给日历
            calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
            Date time = calendar.getTime();
            BdglDiary bdglDiary = new BdglDiary();
            bdglDiary.setUnitId(Integer.parseInt(sysDepts.get(i).getDeptId().toString()));
            bdglDiary.setBlogDate(time);
            bdglDiary.setExamine(1);
//            BdglDiary bdglDiaries =  bdglDiaryService.selectBdglDiaryLists(bdglDiary);
//            if(bdglDiaries!=null){
//                if (bdglDiaries.getBlogDate().getYear() == time.getYear()&&bdglDiaries.getBlogDate().getMonth() == time.getMonth()&&bdglDiaries.getBlogDate().getDay() == time.getDay()) {
//                    sysDepts.get(i).setCommon("1");
//
//                }
//            }else{
//                sysDepts.get(i).setCommon("2");
//            }
            /*-------------------------------------------查询当前部门是否审批--------------------------------------------------------------*/

        }
        List<TreeSelect> treeSelects1=new ArrayList<>();
        List<TreeSelect> treeSelects = sysDeptService.buildDeptTreeSelect(sysDepts1);
        for (TreeSelect treeSelect : treeSelects) {
            for (TreeSelect child : treeSelect.getChildren()) {
                if(!child.getIsShow().equals("N")){
                    treeSelects1.add(child);
                }
            }
            treeSelect.setChildren(treeSelects1);
        }

        return AjaxResult.success(treeSelects);
    }
    public static String getRole(List<SysRole> sysRoles){
        String role="";
        if(sysRoles!=null){
            for (SysRole sysRole : sysRoles) {
                if("admin".equals(sysRole.getRoleKey())){
                    role=sysRole.getRoleKey();
                    break;
                }
                if("special_registration".equals(sysRole.getRoleKey())){
                    role=sysRole.getRoleKey();
                    break;
                }
            }
        }
        return role;
    }



}
