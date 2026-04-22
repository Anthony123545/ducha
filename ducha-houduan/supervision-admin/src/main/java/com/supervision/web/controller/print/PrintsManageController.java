package com.supervision.web.controller.print;

import java.io.IOException;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.supervision.common.config.SupervisionConfig;
import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.common.core.domain.entity.SysRole;
import com.supervision.common.core.domain.entity.SysUser;
import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.common.core.redis.RedisCache;

import com.supervision.common.utils.DateUtils;
import com.supervision.common.utils.SecurityUtils;
import com.supervision.common.utils.file.FileUploadUtils;
import com.supervision.framework.config.ServerConfig;
import com.supervision.framework.web.service.TokenService;

import com.supervision.print.domain.PrintsLog;
import com.supervision.print.service.IPrintsLogService;
import com.supervision.system.domain.SysUserRole;
import com.supervision.system.service.ISysDeptService;
import com.supervision.system.service.ISysRoleService;
import com.supervision.system.service.ISysUserService;
import net.sf.jsqlparser.expression.LongValue;
import org.apache.catalina.User;
import org.apache.poi.util.ArrayUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.supervision.common.annotation.Log;
import com.supervision.common.core.controller.BaseController;
import com.supervision.common.core.domain.AjaxResult;
import com.supervision.common.enums.BusinessType;
import com.supervision.print.domain.PrintsManage;
import com.supervision.print.service.IPrintsManageService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.management.relation.Role;
import javax.xml.crypto.Data;

/**
 * 集中文印主Controller
 *
 * @author supervision
 * @date 2022-03-18
 */
@RestController
@RequestMapping("/PrintsManage/manage")
public class PrintsManageController extends BaseController {
    @Autowired
    private IPrintsManageService printsManageService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ServerConfig serverConfig;

    @Autowired
    private TokenService tokenService;
    @Autowired
    public RedisTemplate redisTemplate;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysDeptService deptService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private IPrintsLogService printsLogService;



    /**
     * 查询集中文印主列表
     */
    @PreAuthorize("@ss.hasPermi('PrintsManage:manage:list')")
    @GetMapping("/list")
    public TableDataInfo list(PrintsManage printsManage) {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        List<String> roleList = userService.selectUserRoleKey(user.getUserId());
        for (int i = 0; i < roleList.size(); i++) {
            String role = roleList.get(i);
            if (role.equals("15wy")) {

                printsManage.setPrintRoom(0);
                startPage();
                List<PrintsManage> list = printsManageService.selectPrintsManageList(printsManage);
                return getDataTable(list);
            }
            if (role.equals("19wy")) {

                printsManage.setPrintRoom(1);
                startPage();
                List<PrintsManage> list = printsManageService.selectPrintsManageList(printsManage);
                return getDataTable(list);
            }
        }
        printsManage.setSendId(user.getUserId());
        printsManage.setIsdelete(0);
        startPage();
        List<PrintsManage> list = printsManageService.selectPrintsManageList(printsManage);
        return getDataTable(list);
    }


    /**
     * 导出集中文印主列表
     */
    @PreAuthorize("@ss.hasPermi('PrintsManage:manage:export')")
    @Log(title = "集中文印主", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(PrintsManage printsManage) {
        List<PrintsManage> list = printsManageService.selectPrintsManageList(printsManage);
        ExcelUtil<PrintsManage> util = new ExcelUtil<PrintsManage>(PrintsManage.class);
        return util.exportExcel(list, "集中文印主数据");
    }

    /**
     * 获取集中文印主详细信息
     */
    @PreAuthorize("@ss.hasPermi('PrintsManage:manage:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(printsManageService.selectPrintsManageById(id));
    }

    /**
     * 新增集中文印主
     */
    @PreAuthorize("@ss.hasPermi('PrintsManage:manage:add')")
    @Log(title = "集中文印主", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PrintsManage printsManage) {

        List<PrintsManage> printsManageList = printsManage.getPrintsManageList();
        for (PrintsManage manage : printsManageList) {
            manage.setSendId(printsManage.getSendId());
            manage.setSendName(printsManage.getSendName());
            manage.setExplainInfo(printsManage.getExplainInfo());
            manage.setPrintRoom(printsManage.getPrintRoom());
            int numb = 0;
            SysRole role = new SysRole();
            //1、取出所对应文印室  2、给相对应文印室发送消息
            if (manage.getPrintRoom().equals(0)) {
                role.setRoleKey("15wy");
                List<SysRole> lsitrole =roleService.selectRoleList(role);
                for (int j = 0; j < lsitrole.size(); j++) {
                    SysRole roleuserid = lsitrole.get(j);
                    Long roleId = roleuserid.getRoleId();
                    List<SysUserRole> roleUsers = roleService.roleSelectUserslist(roleuserid.getRoleId());
                    if(roleUsers!=null&&roleUsers.size()>0){

                        if (redisTemplate.hasKey("print_" + roleUsers.get(0).getUserId())) {
                            numb = Integer.valueOf(redisCache.getCacheObject("print_" + roleUsers.get(0).getUserId()).toString());
                            numb++;
                        }
                        redisCache.setCacheObject("print_" + roleUsers.get(0).getUserId(), numb);
                    }
                }

            }
            if (manage.getPrintRoom().equals(1)) {
                role.setRoleKey("19wy");
                List<SysRole> lsitrole =roleService.selectRoleList(role);
                for (int j = 0; j < lsitrole.size(); j++) {
                    SysRole roleuserid = lsitrole.get(j);
                    List<SysUserRole> roleUsers = roleService.roleSelectUserslist(roleuserid.getRoleId());
                    if (redisTemplate.hasKey("print_" + roleUsers.get(0).getUserId())) {
                        numb = Integer.valueOf(redisCache.getCacheObject("print_" + roleUsers.get(0).getUserId()).toString());
                        numb++;
                    }
                    redisCache.setCacheObject("print_" + roleUsers.get(0).getUserId(), numb);
                }
            }

            manage.setReturnTime(printsManage.getReturnTime());
            manage.setPrintStatus(1);
            manage.setUnitId(getLoginUser().getDeptId());
            SysDept sysDept = deptService.selectDeptById(getLoginUser().getDeptId());
            manage.setUnitName(sysDept.getDeptName());

            printsManageService.insertPrintsManage(manage);
        }
        return toAjax(1);
    }

    /**
     * 轮巡提醒调用(发送文印室)
     *
     * @return
     */
    @PostMapping("/printTrigger")
    public AjaxResult printTrigger() {
        AjaxResult ajab = AjaxResult.success();
        SysUser user = SecurityUtils.getLoginUser().getUser();
        try {
            if (redisTemplate.hasKey("print_" + user.getUserId())) { //用户存在返回提醒消息
                if (!redisCache.getCacheObject("print_" + user.getUserId()).equals(0)) {
                    String str = (redisCache.getCacheObject("print_" + user.getUserId()).toString());
                    int numb = Integer.valueOf(str);
                    ajab.put("numb", numb);
                    ajab.put("print", "print");
                } else {
                    ajab.put("numb", 0);
                }

            } else {
                ajab.put("printInfo", 0);
            }
        } catch (Exception e) {
            ajab.put("printInfo", 0);
        }

        return ajab;
    }

    @PostMapping("/printEachTrigger")
    public AjaxResult printEachTrigger() {
        AjaxResult ajab = AjaxResult.success();
        SysUser user = SecurityUtils.getLoginUser().getUser();
        try {
            if (redisTemplate.hasKey("print_info_" + user.getUserId())) {
                if (!redisCache.getCacheObject("print_info_" + user.getUserId()).equals(0)) {
                    String str = redisCache.getCacheObject("print_info_" + user.getUserId()).toString();
                    long printid = Integer.valueOf(str);
                    PrintsManage printEntity = printsManageService.selectPrintsManageById(printid);

                    ajab.put("printUserInfo", user.getUserId());
                    ajab.put("msg", "您的" + printEntity.getName() + "已成功打印，请到打印室取件！");
                    ajab.put("print", "printUserInfo");
                } else {
                    ajab.put("printUserInfo", 0);
                }
            } else {
                ajab.put("printInfo_u", 0);
            }
        } catch (Exception e) {

            ajab.put("printInfo_u", 0);
        }

        return ajab;
    }

    /**
     * 触发清除提醒
     *
     * @return
     */
    @PostMapping("/printTriggerClear")
    public AjaxResult printTriggerClear() {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        if (redisTemplate.hasKey("print_info_" + user.getUserId())) {
            if (!redisCache.getCacheObject("print_info_" + user.getUserId()).equals(0)) {
                redisCache.setCacheObject("print_info_" + user.getUserId(), 0);
            }
        }
        if (redisTemplate.hasKey("print_" + user.getUserId())) {
            if (!redisCache.getCacheObject("print_" + user.getUserId()).equals(0)) {
                redisCache.setCacheObject("print_" + user.getUserId(), 0);
            }
        }
        if (redisTemplate.hasKey("work_each_" + user.getUserId())) {
            if (!redisCache.getCacheObject("work_each_" + user.getUserId()).equals(0)) {
                redisCache.setCacheObject("work_each_" + user.getUserId(), 0);
            }
        }
        if (redisTemplate.hasKey("work_request_" + user.getUserId())) {
            if (!redisCache.getCacheObject("work_request_" + user.getUserId()).equals(0)) {
                redisCache.setCacheObject("work_request_" + user.getUserId(), 0);
            }
        }
        return AjaxResult.success();
    }


    @PostMapping("/printFile")
    public AjaxResult printFile(MultipartFile pfile) throws IOException {
        if (!pfile.isEmpty()) {
            LoginUser loginUser = getLoginUser();
            // 上传文件路径
            String filePath = SupervisionConfig.getPrintPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, pfile);
            String url = serverConfig.getUrl() + fileName;
            AjaxResult ajax = AjaxResult.success();
            ajax.put("fileName", fileName);
            ajax.put("url", url);

            // 更新缓存用户头像
            //loginUser.getUser().setAvatar(avatar);
            //tokenService.setLoginUser(loginUser);
            return ajax;

        }
        return AjaxResult.error("上传图片异常，请联系管理员");
    }

    /**
     * 修改集中文印主
     */
    @PreAuthorize("@ss.hasPermi('PrintsManage:manage:edit')")
    @Log(title = "集中文印主", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PrintsManage printsManage) {
        return toAjax(printsManageService.updatePrintsManage(printsManage));
    }

    /**
     * 删除集中文印主
     */
    @PreAuthorize("@ss.hasPermi('PrintsManage:manage:remove')")
    @Log(title = "集中文印主", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(printsManageService.deletePrintsManageByIds(ids));
    }

    /**
     * 几种文印统计
     * */
    @GetMapping("/printCount")
    @Log(title = "集中文印主", businessType = BusinessType.DELETE)
    public AjaxResult  printCount(){
//        PrintsManage printsManage = new PrintsManage();
//        SysUser user = SecurityUtils.getLoginUser().getUser();
//        List<String> roleList = userService.selectUserRoleKey(user.getUserId());
//        List<PrintsManage>list=null;
//        int a=0;
//        for (int i = 0; i < roleList.size(); i++) {
//            String role = roleList.get(i);
//            if (role.equals("15wy")) {
//                a++;
//                printsManage.setPrintRoom(0);
//                startPage();
//                list = printsManageService.selectPrintsManageList(printsManage);
//
//            }
//            if (role.equals("19wy")) {
//                a++;
//                printsManage.setPrintRoom(1);
//                startPage();
//                list = printsManageService.selectPrintsManageList(printsManage);
//
//            }
//        }
//        if(a==0){
//
//            printsManage.setSendId(user.getUserId());
//            printsManage.setIsdelete(0);
//            startPage();
//            list = printsManageService.selectPrintsManageList(printsManage);
//        }
//        if(list!=null){
//        String  aa="";
//            for (PrintsManage manage : list) {
//                aa+=manage.getId()+",";
//
//            }
//            aa = aa.substring(0, aa.length() - 1);
//            PrintsLog printsLog = new PrintsLog();
//            printsLog.setPrintCode(aa);
//            Map<String,Integer> map=printsLogService.printCount(printsLog);
//
//        }
        PrintsLog printsLog = new PrintsLog();
        Date nowDate = DateUtils.getNowDate();

        SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");
        String format = yyyy.format(nowDate);
        printsLog.setPrintCode(format);
        Map<String,Object> map=printsLogService.printCountss(printsLog);
        BigDecimal print_number = (BigDecimal) map.get("print_number");
        BigDecimal return_number = (BigDecimal) map.get("return_number");
        map.put("weiGuiHuan",print_number.subtract(return_number));
        return AjaxResult.success(map);
    }


}
