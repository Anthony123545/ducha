package com.supervision.web.controller.print;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.supervision.common.core.domain.entity.SysUser;
import com.supervision.common.core.redis.RedisCache;
import com.supervision.common.utils.DateUtils;
import com.supervision.common.utils.SecurityUtils;
import com.supervision.common.utils.StringUtils;
import com.supervision.framework.web.domain.server.Sys;
import com.supervision.print.domain.PrintsManage;
import com.supervision.print.service.IPrintsManageService;
import com.supervision.system.service.ISysUserService;
import com.supervision.web.controller.common.PrintCommon;
import org.apache.catalina.User;
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
import com.supervision.print.domain.PrintsLog;
import com.supervision.print.service.IPrintsLogService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 集中文印记录Controller
 *
 * @author supervision
 * @date 2022-03-18
 */
@RestController
@RequestMapping("/PrintsLog/printlog")
public class PrintsLogController extends BaseController {
    @Autowired
    private IPrintsLogService printsLogService;

    @Autowired
    private IPrintsManageService printsManageService;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ISysUserService userService;
    
    /**
     * 个人打印记录表
     */
    @PreAuthorize("@ss.hasPermi('PrintsLog:printlog:list')")
    @GetMapping("/personalList")
    public TableDataInfo personalList(PrintsLog printsLog) {
        //获取当前用户昵称
        String nickName = getLoginUser().getUser().getNickName();
        printsLog.setPrintfileName(nickName);
        startPage();
        List<PrintsLog> list = printsLogService.selectPrintsLogList(printsLog);
        return getDataTable(list);
    }

    /**
     * 查询集中文印记录列表
     */
    @PreAuthorize("@ss.hasPermi('PrintsLog:printlog:list')")
    @GetMapping("/list")
    public TableDataInfo list(PrintsLog printsLog) {

        SysUser user = SecurityUtils.getLoginUser().getUser();
        List<String> roleList= userService.selectUserRoleKey(user.getUserId());
        for (int i=0;i<roleList.size();i++){
            String role= roleList.get(i);
            if(role.equals("15wy")){
                printsLog.setPrintRoom(0);
            }
            if(role.equals("19wy")){
                printsLog.setPrintRoom(1);
            }
        }
        startPage();
        List<PrintsLog> list = printsLogService.selectPrintsLogList(printsLog);
        return getDataTable(list);
    }

    /**
     * 附加二维码打印调用
     *
     * @param printsLog
     * @return
     * @throws IOException
     */
    @PostMapping("/printCode")
    public AjaxResult printCode(@RequestBody PrintsLog printsLog) throws IOException {
        AjaxResult ajab = AjaxResult.success();
        String printFileName = null;
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String rm = String.valueOf(Math.round((Math.random() + 1) * 1000));
        String printNumber = StringUtils.remove(dateStr, "/") + rm;

        //获取打印实体
        PrintsManage printEntity = printsManageService.selectPrintsManageById(printsLog.getPrintId());

        printEntity.setIsShow("1"); //是否显示确认按钮
        printsManageService.updatePrintsManage(printEntity);
        //插入redis打印人数据  ----毕波 注释 该行插入redis代码---
        // redisCache.setCacheObject("print_info_"+printEntity.getSendId(),printEntity.getId());
        //判断文件类型
        if (printEntity.getType().equals("doc") || printEntity.getType().equals("docx")) {
            String filename =printEntity.getFile();
            filename= filename.substring(filename.lastIndexOf("print"));
            filename= "D:\\Supervision\\uploadPath\\"+filename;
            printFileName = PrintCommon.printWord(filename, printNumber);
        }
        if (printEntity.getType().equals("xls") || printEntity.getType().equals("xlsx")) {
            String filename =printEntity.getFile();
            filename= filename.substring(filename.lastIndexOf("print"));
            filename= "D:\\Supervision\\uploadPath\\"+filename;
            printFileName = PrintCommon.printExcle(filename, printNumber);
        }
        if (printEntity.getType().equals("pdf")) {
            String filename =printEntity.getFile();
            filename= filename.substring(filename.lastIndexOf("print"));
            filename= "D:\\Supervision\\uploadPath\\"+filename;
            printFileName = PrintCommon.printPDF(filename, printNumber);
        }
        printsLog.setStatusG(0);
        printsLog.setPrintCode(printNumber);
        printsLog.setReturnTime(printEntity.getReturnTime());
        String dates = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(printEntity.getReturnTime());
       //获取三天前存入库
        printsLog.setGotothree(DateUtils.getTimeBeforeDay(dates,3));
        printsLogService.insertPrintsLog(printsLog);
        ajab.put("printFileName", printFileName);
        return ajab;
    }

    /**
     * 获取需要归还的文件
     * @return
     */
    @GetMapping("/remindPrint")
    public AjaxResult remindPrint() {
        AjaxResult ajab = AjaxResult.success();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date =new Date();
        Timestamp now = new Timestamp(date.getTime());
        String str = df.format(now);
        Date newDate = null;
        try {
            newDate = df.parse(str);

        } catch (ParseException e) {
            //throw new BusinessException("转换日期去掉毫秒异常");
        }
        SysUser user =userService.selectUserById(SecurityUtils.getUserId());
        PrintsLog printsLog = new PrintsLog();
        printsLog.setReturnTime(newDate);

        printsLog.setPrintfileName(user.getNickName());
        List<PrintsLog> list=  printsLogService.selectprintremind(printsLog);
        PrintsLog printsLog2 = new PrintsLog();
        printsLog2.setPrintfileName(user.getNickName());
        printsLog2.setGotothree(newDate);
        List<PrintsLog> list2=  printsLogService.selectprintremind(printsLog2);
        int num = list.size()+list2.size();
        ajab.put("num",num);
        ajab.put("msg",user.getNickName()+"您今日有"+num+"打印文件需要归还！");
        return ajab;
    }


    /**
     * 实现点击确定按钮时实现弹框
     *
     * @Author 毕波
     * @return
     * @throws
     */
    @PostMapping("/printinsertredis")
    public AjaxResult printInsertRedis(@RequestBody PrintsManage printEntity) {
        //获取打印实体
       // PrintsManage printEntity = printsManageService.selectPrintsManageById(printsLog.getPrintId());
        printEntity.setPrintStatus(2);
        printEntity.setIsShow("0"); //是否显示确认按钮
        printsManageService.updatePrintsManage(printEntity);
        //插入redis打印人数据
        redisCache.setCacheObject("print_info_"+printEntity.getSendId(),printEntity.getId());
        return AjaxResult.success("200");
    }

    /**
     * 导出集中文印记录列表
     */
    @PreAuthorize("@ss.hasPermi('PrintsLog:printlog:export')")
    @Log(title = "集中文印记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(PrintsLog printsLog) {
        System.out.println(printsLog.getStartt());

        List<PrintsLog> list= new ArrayList<>();
        List<PrintsLog> lists = new ArrayList<>();
        String[] unitids =null;
        if(printsLog.getUnitIds()!=null){
            unitids=printsLog.getUnitIds().split(",");
        }

        if(printsLog.getStartt()==null){
             if(unitids!=null){
                 for (int i=0;i<unitids.length;i++){
                     printsLog.setUnitId(Long.valueOf(unitids[i]));
                     list = printsLogService.selectPrintsLogLists(printsLog);
                     for (int j=0;j<list.size();j++){
                         lists.add(list.get(j));
                     }
                 }
             }else{
                 list = printsLogService.selectPrintsLogLists(printsLog);
             }

        }else{

            if(unitids!=null){
                for (int i=0;i<unitids.length;i++){
                    printsLog.setUnitId(Long.valueOf(unitids[i]));
                    list = printsLogService.selectPrintsLogListss(printsLog);
                    for (int j=0;j<list.size();j++){
                        lists.add(list.get(j));
                    }
                }
            }else {
                list = printsLogService.selectPrintsLogListss(printsLog);
            }

        }

        for (PrintsLog log : list) {
            if(log.getIsReturn()!=null&&log.getIsReturn()==1){
                log.setBooleanReturn("需归还");
            }
            if(log.getIsReturn()!=null&&log.getIsReturn()==0){
                log.setBooleanReturn("无需归还");
            }
        }

        ExcelUtil<PrintsLog> util = new ExcelUtil<PrintsLog>(PrintsLog.class);
        if(unitids!=null){
            return util.exportExcel(lists, "集中文印记录数据");
        }else {
            return util.exportExcel(list, "集中文印记录数据");
        }

    }

    /**
     * 获取集中文印记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('PrintsLog:printlog:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {

        return AjaxResult.success(printsLogService.selectPrintsLogById(id));
    }

    /**
     * 新增集中文印记录
     */
    @PreAuthorize("@ss.hasPermi('PrintsLog:printlog:add')")
    @Log(title = "集中文印记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PrintsLog printsLog) {
        return toAjax(printsLogService.insertPrintsLog(printsLog));
    }

    /**
     * 修改集中文印记录
     */
    @PreAuthorize("@ss.hasPermi('PrintsLog:printlog:edit')")
    @Log(title = "集中文印记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PrintsLog printsLog) {
        //查询是否已经归还
        PrintsLog printsLog1 = printsLogService.selectPrintsLogById(printsLog.getId());
        //初始化归还分数
        long newNumber = 0L;
        //总需归还的数量
        Long printNumber = printsLog1.getPrintNumber();
        //获取当前已归还数量
        Long number = printsLog1.getReturnNumber();
        //判断当前数据库已归还数量值---此类型数据 建议设置默认值为0，而不是NULL
        if (number == null){
            number = 0L;
        }
        if (number >=0){
            newNumber = number + printsLog.getReturnNumber();
        }
        //判断如果新的归还数量超过总需归还。则说明归还数据有误，需重新填写
        if (newNumber > printNumber){
            return error("超过总归还数，请核查！");
        }
        //如果当前归还总数 == 总需归还的数量，则修改为已归还
        if (newNumber == printNumber){
            printsLog.setStatusG(1);
        }
        Date now = new Date();
        printsLog.setReturnTime(now);
        printsLog.setReturnNumber(newNumber);
        return toAjax(printsLogService.updatePrintsLog(printsLog));
    }

    /**
     * 删除集中文印记录
     */
    @PreAuthorize("@ss.hasPermi('PrintsLog:printlog:remove')")
    @Log(title = "集中文印记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(printsLogService.deletePrintsLogByIds(ids));
    }
}
