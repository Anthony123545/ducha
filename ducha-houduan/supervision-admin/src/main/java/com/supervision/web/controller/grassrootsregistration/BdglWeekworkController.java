package com.supervision.web.controller.grassrootsregistration;

import java.io.*;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.hutool.core.date.DateUtil;
import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.common.core.domain.entity.SysRole;
import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.grassrootsregistration.domain.BdglWeekworkRegister;
import com.supervision.peopleManage.domain.BdglPeople;
import com.supervision.peopleManage.service.IBdglPeopleService;
import com.supervision.system.mapper.SysUserMapper;
import com.supervision.system.service.ISysDeptService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.supervision.common.annotation.Log;
import com.supervision.common.core.controller.BaseController;
import com.supervision.common.core.domain.AjaxResult;
import com.supervision.common.enums.BusinessType;
import com.supervision.grassrootsregistration .domain.BdglWeekwork;
import com.supervision.grassrootsregistration .service.IBdglWeekworkService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;



/**
 * 一周工作安排Controller
 *
 * @author supervision
 * @date 2022-02-28
 */
@RestController
@RequestMapping("/grassrootsregistration/bdglweekwork")
public class BdglWeekworkController extends BaseController
{
    @Autowired
    private IBdglWeekworkService bdglWeekworkService;
    @Autowired
    private ISysDeptService sysDeptService;
    @Autowired
    private IBdglPeopleService peopleService;
    @Autowired
    private SysUserMapper userMapper;

    /**
     * 查询一周工作安排列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglweekwork:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglWeekwork bdglWeekwork)
    {
        LoginUser loginUser = getLoginUser();
        List<SysRole> sysRoles = userMapper.selectRole(loginUser.getUserId());
        String role = CommonsController.getRole(sysRoles);
        //判断当前角色是否是最高权限   不是返回当前部门数据
        if(role.equals("admin") || role.equals("special_registration")){
            startPage();
            List<BdglWeekwork> list1 = bdglWeekworkService.selectBdglWeekworkList(bdglWeekwork);
            return getDataTable(list1);
        }else{
            bdglWeekwork.setUnitId(Integer.parseInt(loginUser.getDeptId().toString()));
        }
        startPage();
        List<BdglWeekwork> list2 = bdglWeekworkService.selectBdglWeekworkList(bdglWeekwork);
        return getDataTable(list2);
    }

    /**
     * 导出一周工作安排列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglweekwork:export')")
    @Log(title = "一周工作安排", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglWeekwork bdglWeekwork)
    {
        List<BdglWeekwork> list = bdglWeekworkService.selectBdglWeekworkList(bdglWeekwork);
        ExcelUtil<BdglWeekwork> util = new ExcelUtil<BdglWeekwork>(BdglWeekwork.class);
        return util.exportExcel(list, "一周工作安排数据");
    }

    /**
     * 获取一周工作安排详细信息
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglweekwork:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bdglWeekworkService.selectBdglWeekworkById(id));
    }

    @GetMapping("/exportWeekwork")
    public AjaxResult exportWeekwork(@RequestParam(value = "id")Integer id) throws Exception {

        BdglWeekwork bdglWeekwork = bdglWeekworkService.selectBdglWeekworkById(id);
        bdglWeekwork.setDate(DateUtil.format(bdglWeekwork.getStartTime(),"yyyy年MM月dd日"));
        bdglWeekwork.setDate1(DateUtil.format(bdglWeekwork.getEndTime(),"yyyy年MM月dd日"));
        bdglWeekwork.setDate2(DateUtil.format(bdglWeekwork.getEndTime(),"yyyy年MM月dd日"));
        bdglWeekwork.setPhone(bdglWeekwork.getPhone()+"\r\n\n\n\n\n\n\n\n\n\n");
        List<BdglWeekworkRegister> bdglWeekworkRegisterList = bdglWeekwork.getBdglWeekworkRegisterList();
        for (BdglWeekworkRegister bdglWeekworkRegister : bdglWeekworkRegisterList) {
            Date time = bdglWeekworkRegister.getTime();
            String format = DateUtil.format(time, "M.dd");
            bdglWeekworkRegister.setDate(format);
        }
        //获取本机ip
        InetAddress localHost = InetAddress.getLocalHost();
        String hostAddress = localHost.getHostAddress();

        Map<String, Object> params = new HashMap<>();
        params.put("TestList", bdglWeekworkRegisterList);
        params.put("item", bdglWeekwork);
        //params.put("time", bdglEquip1);
        String fileName = "一周工作安排.doc";

        generateWord(params, "D:/supervision/uploadPath/upload/" + fileName, "D:\\supervision\\moban", "周工作安排表1.xml");
        return AjaxResult.success("/dev-api/profile/upload/" + fileName);
    }

    public static void generateWord(Map<String, Object> dataMap, String fileName, String templatePath, String template) throws Exception {
        // 设置FreeMarker的版本和编码格式
        Configuration configuration = new Configuration(new Version("2.3.23"));
        configuration.setDefaultEncoding("UTF-8");
        configuration.setClassicCompatible(true);

        // 设置FreeMarker生成Word文档所需要的模板的路径
        configuration.setDirectoryForTemplateLoading(new File(templatePath));
        // 设置FreeMarker生成Word文档所需要的模板
        Template t = configuration.getTemplate(template, "UTF-8");
        // 创建一个Word文档的输出流
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(fileName)), "UTF-8"));
        //FreeMarker使用Word模板和数据生成Word文档
        t.process(dataMap, out);
        out.flush();
        out.close();
    }

    /**
     * 新增一周工作安排
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglweekwork:add')")
    @Log(title = "一周工作安排", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglWeekwork bdglWeekwork)
    {
        BdglWeekwork bdglWeekwork1 = new BdglWeekwork();
        bdglWeekwork1.setUnitId(bdglWeekwork.getUnitId());
//        List<BdglWeekwork> bdglWeekworks = bdglWeekworkService.selectBdglWeekworkLists(bdglWeekwork1);
//        for (BdglWeekwork weekwork : bdglWeekworks) {
//            long time = bdglWeekwork.getStartTime().getTime();//开始时间
//            long time3 = weekwork.getEndTime().getTime();//当前任务结束时间
//            if(time<=time3){
//                return AjaxResult.success("该部门当前时段已有周工作安排");
//            }
//
//        }
        return toAjax(bdglWeekworkService.insertBdglWeekwork(bdglWeekwork));
    }
    /**
     * 获取当前大队人员
     * */
    public AjaxResult getPeoole(){
        List<BdglPeople> bdglPeople = peopleService.selectPowerPeoleList(new BdglPeople());
        return AjaxResult.success(bdglPeople);
    }
    /**
     * 获取部门列表
     * */
    public AjaxResult getDept(){
        return AjaxResult.success(sysDeptService.selectDeptList(new SysDept()));
    }

    /**
     * 修改一周工作安排
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglweekwork:edit')")
    @Log(title = "一周工作安排", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglWeekwork bdglWeekwork)
    {
        Date endTime = bdglWeekwork.getEndTime();
        endTime.setHours(23);
        endTime.setMinutes(59);
        bdglWeekwork.setEndTime(endTime);
        return toAjax(bdglWeekworkService.updateBdglWeekwork(bdglWeekwork));
    }

    /**
     * 删除一周工作安排
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglweekwork:remove')")
    @Log(title = "一周工作安排", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglWeekworkService.deleteBdglWeekworkByIds(ids));
    }
    /**判断当前部门所选时段是否有工作安排*/
    @GetMapping("/getWeekWork")
    public AjaxResult getWeekWork( BdglWeekwork bdglWeekwork){
        BdglWeekwork bdglWeekwork1 = new BdglWeekwork();
        bdglWeekwork1.setUnitId(bdglWeekwork.getUnitId());
        List<BdglWeekwork> bdglWeekworks = bdglWeekworkService.selectBdglWeekworkLists(bdglWeekwork1);
        for (BdglWeekwork weekwork : bdglWeekworks) {
            long time = bdglWeekwork.getStartTime().getTime();//开始时间
            long time3 = weekwork.getEndTime().getTime();//当前任务结束时间
            if(time<=time3){
                return AjaxResult.success("该部门当前时段已有周工作安排");
            }
        }
        return null;
    }
    @GetMapping("/getweek")
    public AjaxResult getweek( Date date){
        SimpleDateFormat eeee = new SimpleDateFormat("EEEE");
        String format = eeee.format(date);
        return AjaxResult.success(format);
    }
}
