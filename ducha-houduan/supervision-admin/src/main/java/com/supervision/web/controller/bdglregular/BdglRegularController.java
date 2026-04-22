package com.supervision.web.controller.bdglregular;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.supervision.bdglregular.domain.BdglRectificationRecord;
import com.supervision.bdglregular.service.IBdglRectificationRecordService;
import com.supervision.common.DateUtils;
import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.common.utils.Arith;
import com.supervision.common.utils.SecurityUtils;
import com.supervision.system.service.ISysDeptService;
import com.supervision.thought.domain.ThoughtWeekjob;
import com.supervision.web.controller.system.SysDeptController;
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
import com.supervision.bdglregular.domain.BdglRegular;
import com.supervision.bdglregular.service.IBdglRegularService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 经常性检查Controller
 * 
 * @author supervision
 * @date 2022-03-07
 */
@RestController
@RequestMapping("/bdglregular/regularinfo")
public class BdglRegularController extends BaseController
{
    @Autowired
    private IBdglRegularService bdglRegularService;

    @Autowired
    private IBdglRectificationRecordService bdglRectificationRecordService;

    @Autowired
    private ISysDeptService deptService;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 查询经常性检查列表
     */
    @PreAuthorize("@ss.hasPermi('bdglregular:regularinfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglRegular bdglRegular) throws ParseException {
        //获取当前时间
        BdglRegular bdglRegulars = new BdglRegular();
        String dangqianshijian = sdf.format(new Date());
        bdglRegulars.setCaoZuoType(bdglRegular.getCaoZuoType());
        List<BdglRegular> lists = bdglRegularService.selectBdglRegularLists(bdglRegulars);
        for (BdglRegular regular : lists) {
            if("0".equals(regular.getCaoZuoType())) {
                String rectificationTime = regular.getRectificationTime(); //整改时间
                if(sdf.parse(dangqianshijian).compareTo(sdf.parse(rectificationTime)) > 0) { //判断整改时间是否已过
                    if(regular.getCurrentState() != "已通过") {
                        regular.setPaixuValue(5);
                        bdglRegularService.updateBdglRegular(regular);
                    }
                }
            }
        }


        //获取登录人员信息
        LoginUser loginUser = SecurityUtils.getLoginUser();
        Long deptId = loginUser.getDeptId();
        startPage();
        List<BdglRegular> list = bdglRegularService.selectBdglRegularLists(bdglRegular);
        for (BdglRegular regular : list) {
            if("0".equals(regular.getCaoZuoType())) {
                //判断是不是当前操作部门
                if(deptId.equals(regular.getInspectedUnitId()) ) {
                    regular.setIsCaoZuoDept("0");  //如果是0 就可以问题整改
                }else {
                    regular.setIsCaoZuoDept("1"); //如果是1 就无法问题整改
                }

                //判断部门是否能够审批
                if(deptId .equals(regular.getUnitCheckId())) {
                    regular.setIsShenPi("0"); //如果是0  就可以进行审批
                }else {
                    regular.setIsShenPi("1"); //如果是1  无法进行审批
                }

                /*String rectificationTime = regular.getRectificationTime(); //整改时间
                if(sdf.parse(dangqianshijian).compareTo(sdf.parse(rectificationTime)) > 0) { //判断整改时间是否已过
                    if(regular.getCurrentState() != "已通过") {
                        regular.setCurrentState("已逾期");
                    }
                }*/
                if("待处理".equals(regular.getCurrentState())) {
                    Date date = DateUtils.parseDate(regular.getRectificationTime());
                    if (new Date().getTime() > date.getTime()) {
                        //判断整改时间是否已过
                        regular.setCurrentState("已逾期");
                        regular.setPaixuValue(6);
                        bdglRegularService.updateBdglRegular(regular);
                    }
                }
            }

            if("1".equals(regular.getCaoZuoType())) {
                //判断是不是当前操作部门
                if(deptId.equals(regular.getInspectedUnitId()) ) {
                    regular.setIsCaoZuoDept("0");  //如果是0 就可以问题整改
                }else {
                    regular.setIsCaoZuoDept("1"); //如果是1 就无法问题整改
                }

                //判断部门是否能够审批
                if(deptId .equals(regular.getUnitCheckId())) {
                    regular.setIsShenPi("0"); //如果是0  就可以进行审批
                }else {
                    regular.setIsShenPi("1"); //如果是1  无法进行审批
                }
            }
        }
        return getDataTable(list);
    }
    @GetMapping("/lists")
    public TableDataInfo lists(BdglRegular bdglRegular) throws ParseException {
        //获取当前时间

        BdglRegular bdglRegulars = new BdglRegular();
        String dangqianshijian = sdf.format(new Date());
        bdglRegulars.setCaoZuoType(bdglRegular.getCaoZuoType());
        List<BdglRegular> lists = bdglRegularService.selectBdglRegularLists(bdglRegulars);
        for (BdglRegular regular : lists) {
            if("0".equals(regular.getCaoZuoType())) {
                String rectificationTime = regular.getRectificationTime(); //整改时间
                if(sdf.parse(dangqianshijian).compareTo(sdf.parse(rectificationTime)) > 0) { //判断整改时间是否已过
                    if(regular.getCurrentState() != "已通过") {
                        regular.setPaixuValue(5);
                        bdglRegularService.updateBdglRegular(regular);
                    }
                }
            }
        }


        //获取登录人员信息
        LoginUser loginUser = SecurityUtils.getLoginUser();
        Long deptId = loginUser.getDeptId();
        startPage();
        List<BdglRegular> list = bdglRegularService.selectBdglRegularLists(bdglRegular);
        for (BdglRegular regular : list) {
            if("0".equals(regular.getCaoZuoType())) {
                //判断是不是当前操作部门
                if(deptId.equals(regular.getInspectedUnitId()) ) {
                    regular.setIsCaoZuoDept("0");  //如果是0 就可以问题整改
                }else {
                    regular.setIsCaoZuoDept("1"); //如果是1 就无法问题整改
                }

                //判断部门是否能够审批
                if(deptId .equals(regular.getUnitCheckId())) {
                    regular.setIsShenPi("0"); //如果是0  就可以进行审批
                }else {
                    regular.setIsShenPi("1"); //如果是1  无法进行审批
                }

                String rectificationTime = regular.getRectificationTime(); //整改时间
//                if(sdf.parse(dangqianshijian).compareTo(sdf.parse(rectificationTime)) > 0) { //判断整改时间是否已过
//                    if(regular.getCurrentState() != "已通过") {
//                        regular.setCurrentState("已逾期");
//                        BdglRegular bdglRegular1 = new BdglRegular();
//                        bdglRegular1.setCurrentState(regular.getCurrentState());
//                        bdglRegular1.setId(regular.getId());
//                        bdglRegularService.updateBdglRegular(regular);
//                    }
//                }
            }
        }
        return getDataTable(list);
    }


    /**
     * 查询当前部门下的所有数据
     * @param bdglRegular
     * @return
     */
    @GetMapping("/bumenlist")
    public TableDataInfo bumenlist(BdglRegular bdglRegular)  {
        List<BdglRegular> list = new ArrayList<>();
        List<BdglRegular> lists = bdglRegularService.selectBdglRegularList(bdglRegular);
        for (BdglRegular lista : lists) {
            if("待处理".equals(lista.getCurrentState()) || "待验收".equals(lista.getCurrentState())){
                list.add(lista);
            }
        }
        return getDataTable(list);
    }

    /**
     * 问题整改 接口
     * @param bdglRegular
     * @return
     */
    @PostMapping("/editquestion")
    public AjaxResult editQuestion(@RequestBody BdglRegular bdglRegular)
    {
        //获取登录人账号
        LoginUser loginUser = SecurityUtils.getLoginUser();
        Long userId = loginUser.getUserId();
        bdglRegular.setCurrentState("待验收"); //当前状态
        bdglRegular.setUpdateTime(new Date()); //修改时间
        bdglRegular.setCreateUserId(userId); //创建人ID
        bdglRegular.setPaixuValue(2);
        bdglRegularService.updateBdglRegular(bdglRegular);
        //调用添加记录接口
        addInfo(bdglRegular, userId);
        return AjaxResult.success("成功");
    }

    /**
     * 添加记录
     */
    public void addInfo(BdglRegular bdglRegular, Long userId) {
        //存储调整记录
        BdglRectificationRecord brd = new BdglRectificationRecord();
        brd.setWjId(bdglRegular.getId()); //经常性检查 ID
        brd.setCaozuoType(bdglRegular.getCaoZuoType()); //操作状态 加分 减分
        brd.setInspectedUnit(bdglRegular.getInspectedUnit());
        brd.setInspectedUnitId(bdglRegular.getInspectedUnitId());
        brd.setUnitCheck(bdglRegular.getUnitCheck());
        brd.setUnitCheckId(bdglRegular.getUnitCheckId());
        brd.setInspectionTime(bdglRegular.getInspectionTime());
        brd.setInspectionSite(bdglRegular.getInspectionSite());
        brd.setScore(bdglRegular.getScore());
        brd.setRectificationTime(bdglRegular.getRectificationTime());
        brd.setQuestion(bdglRegular.getQuestion());
        brd.setCreateTime(new Date());
        brd.setCreateUserId(userId);
        brd.setChargePeople(bdglRegular.getChargePeople());
        brd.setChargePeopleId(bdglRegular.getChargePeopleId());
        brd.setCorrectiveAction(bdglRegular.getCorrectiveAction());
        brd.setPictureProof(bdglRegular.getPictureProof());
        brd.setDocumentProof(bdglRegular.getDocumentProof());
        bdglRectificationRecordService.insertBdglRectificationRecord(brd);
    }

    /**
     * 审批通过的 接口
     * @param bdglRegular
     * @return
     */
    @PostMapping("/editissuccess")
    public AjaxResult editIsSuccess(@RequestBody BdglRegular bdglRegular)
    {
        bdglRegular.setCurrentState("已通过");
        bdglRegular.setPaixuValue(4);
        return AjaxResult.success(bdglRegularService.updateBdglRegular(bdglRegular));
    }

    /**
     * 审批失败的 接口
     * @param bdglRegular
     * @return
     */
    @PostMapping("/editisfalse")
    public AjaxResult editIsFalse(@RequestBody BdglRegular bdglRegular)
    {
        bdglRegular.setCurrentState("未通过");
        bdglRegular.setPaixuValue(3);
        return AjaxResult.success(bdglRegularService.updateBdglRegular(bdglRegular));
    }



    /**
     * 导出经常性检查列表
     */
   // @PreAuthorize("@ss.hasPermi('bdglregular:regularinfo:export')")
    @Log(title = "经常性检查", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglRegular bdglRegular)
    {
        List<BdglRegular> list = bdglRegularService.selectBdglRegularList(bdglRegular);
        ExcelUtil<BdglRegular> util = new ExcelUtil<BdglRegular>(BdglRegular.class);
        return util.exportExcel(list, "经常性检查数据");
    }

    /**
     * 获取经常性检查详细信息
     */
   // @PreAuthorize("@ss.hasPermi('bdglregular:regularinfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(bdglRegularService.selectBdglRegularById(id));
    }

    /**
     * 新增经常性检查
     */
   // @PreAuthorize("@ss.hasPermi('bdglregular:regularinfo:add')")
    @Log(title = "经常性检查", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglRegular bdglRegular)
    {
        Long inspectedUnitId = bdglRegular.getInspectedUnitId(); //受检单位
        SysDept sysDept = deptService.selectDeptById(inspectedUnitId);
        BigDecimal score = sysDept.getScore(); //总积分

        //获取父部门ID----start
        Long parentId = sysDept.getParentId();
        BigDecimal acnestorscore1 = BigDecimal.valueOf(0);
        if(parentId != 0) {
            SysDept sysDeptacnestor = deptService.selectDeptById(parentId);
            acnestorscore1 = sysDeptacnestor.getScore(); //父部门的总分数
        }

        //获取父部门ID----end

        BigDecimal scoreInfo = BigDecimal.valueOf(0); //定义本部门分值
        BigDecimal fuscoreInfo = BigDecimal.valueOf(0); //定义父部门分值
        if("0".equals(bdglRegular.getCaoZuoType())){
            scoreInfo = score.subtract(bdglRegular.getScore()); //扣分
            if(parentId != 0) {
                fuscoreInfo = acnestorscore1.subtract(bdglRegular.getScore());
            }
            bdglRegular.setCurrentState("待处理");
            bdglRegular.setPaixuValue(1);
        }else if("1".equals(bdglRegular.getCaoZuoType())){
            scoreInfo = bdglRegular.getScore().add(score); //加分
            if(parentId != 0) {
                fuscoreInfo = acnestorscore1.add(bdglRegular.getScore());
            }
        }

        // 修改当前部门分数 ------------
        SysDept sd = new SysDept();
        sd.setScore(scoreInfo);
        sd.setDeptId(inspectedUnitId);
        deptService.updateDept(sd);
        //-------------------------------
        //修改父部门分数 =================
        if(parentId != 0) {
            SysDept sdf = new SysDept();
            sdf.setScore(fuscoreInfo);
            sdf.setDeptId(parentId);
            deptService.updateDept(sdf);
        }
       //====================================
        bdglRegular.setParentId(parentId);
        return toAjax(bdglRegularService.insertBdglRegular(bdglRegular));
    }

    /**
     * 修改经常性检查
     */
   // @PreAuthorize("@ss.hasPermi('bdglregular:regularinfo:edit')")
    @Log(title = "经常性检查", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglRegular bdglRegular)
    {
        return toAjax(bdglRegularService.updateBdglRegular(bdglRegular));
    }

    /**
     * 删除经常性检查
     */
    //@PreAuthorize("@ss.hasPermi('bdglregular:regularinfo:remove')")
    @Log(title = "经常性检查", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        for (Long id : ids) {
            BdglRegular bdglRegular = bdglRegularService.selectBdglRegularById(id);
            //加分
            if("1".equals(bdglRegular.getCaoZuoType())) {
                SysDept sysDept = deptService.selectDeptById(bdglRegular.getInspectedUnitId());
                sysDept.setScore(sysDept.getScore().subtract(bdglRegular.getScore()));
                deptService.updateDept(sysDept);
                SysDept sysDept2 = deptService.selectDeptById(sysDept.getParentId());
                if(sysDept2!=null){
                    sysDept2.setScore(sysDept2.getScore().subtract(bdglRegular.getScore()));
                    deptService.updateDept(sysDept2);
                }
            }

            //减分
            if("0".equals(bdglRegular.getCaoZuoType())) {
                SysDept sysDept = deptService.selectDeptById(bdglRegular.getInspectedUnitId());
                sysDept.setScore(sysDept.getScore().add(bdglRegular.getScore()));
                deptService.updateDept(sysDept);
                SysDept sysDept2 = deptService.selectDeptById(sysDept.getParentId());
                if(sysDept2!=null){
                    sysDept2.setScore(sysDept2.getScore().add(bdglRegular.getScore()));
                    deptService.updateDept(sysDept2);
                }
            }


        }
        return toAjax(bdglRegularService.deleteBdglRegularByIds(ids));
    }
}
