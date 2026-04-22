package com.supervision.web.controller.bdglregular;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.supervision.bdglregular.domain.BdglRectificationRecord;
import com.supervision.bdglregular.domain.BdglRegular;
import com.supervision.bdglregular.service.IBdglRectificationRecordService;
import com.supervision.common.DateUtils;
import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.common.utils.SecurityUtils;
import com.supervision.system.service.ISysDeptService;
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
import com.supervision.bdglregular.domain.BdglSpecial;
import com.supervision.bdglregular.service.IBdglSpecialService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 专项检查Controller
 * 
 * @author supervision
 * @date 2022-03-07
 */
@RestController
@RequestMapping("/bdglregular/specialinfo")
public class BdglSpecialController extends BaseController
{
    @Autowired
    private IBdglSpecialService bdglSpecialService;

    @Autowired
    private IBdglRectificationRecordService bdglRectificationRecordService;

    @Autowired
    private ISysDeptService deptService;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 查询专项检查列表
     */
    @PreAuthorize("@ss.hasPermi('bdglregular:specialinfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglSpecial bdglSpecial) throws ParseException {
        //获取当前时间
        String dangqianshijian = sdf.format(new Date());
        BdglSpecial bdglRegulars = new BdglSpecial();
        bdglRegulars.setCaoZuoType(bdglRegulars.getCaoZuoType());
        List<BdglSpecial> lists = bdglSpecialService.selectBdglSpecialLists(bdglRegulars);
        for (BdglSpecial regular : lists) {
            if("0".equals(regular.getCaoZuoType())) {
                String rectificationTime = regular.getRectificationTime(); //整改时间
                if(sdf.parse(dangqianshijian).compareTo(sdf.parse(rectificationTime)) > 0) { //判断整改时间是否已过
                    if(regular.getCurrentState() != "已通过") {
                        regular.setPaixuValue(5);
                        bdglSpecialService.updateBdglSpecial(regular);
                    }
                }
            }
        }

        startPage();
        LoginUser loginUser = SecurityUtils.getLoginUser();
        Long deptId = loginUser.getDeptId();
        List<BdglSpecial> list = bdglSpecialService.selectBdglSpecialLists(bdglSpecial);
        for (BdglSpecial special : list) {
            if("0".equals(special.getCaoZuoType())) {
                //判断是不是当前操作部门
                if(deptId .equals(special.getInspectedUnitId())) {
                    special.setIsCaoZuoDept("0");  //如果是0 就可以问题整改
                }else {
                    special.setIsCaoZuoDept("1"); //如果是1 就无法问题整改
                }

                //判断部门是否能够审批
                if(deptId.equals(special.getUnitCheckId())) {
                    special.setIsShenPi("0"); //如果是0  就可以进行审批
                }else {
                    special.setIsShenPi("1"); //如果是1  无法进行审批
                }

               /* String rectificationTime = special.getRectificationTime(); //整改时间
                if(sdf.parse(dangqianshijian).compareTo(sdf.parse(rectificationTime)) > 0) { //判断整改时间是否已过
                    if(special.getCurrentState() != "已通过") {
                        special.setCurrentState("已逾期");
                    }
                }*/
                if("待处理".equals(special.getCurrentState())) {
                    Date date = DateUtils.parseDate(special.getRectificationTime());
                    if (new Date().getTime() > date.getTime()) {
                        //判断整改时间是否已过
                        special.setCurrentState("已逾期");
                        special.setPaixuValue(6);
                        bdglSpecialService.updateBdglSpecial(special);
                    }
                }
            }

            if("1".equals(special.getCaoZuoType())) {
                //判断是不是当前操作部门
                if(deptId .equals(special.getInspectedUnitId())) {
                    special.setIsCaoZuoDept("0");  //如果是0 就可以问题整改
                }else {
                    special.setIsCaoZuoDept("1"); //如果是1 就无法问题整改
                }

                //判断部门是否能够审批
                if(deptId.equals(special.getUnitCheckId())) {
                    special.setIsShenPi("0"); //如果是0  就可以进行审批
                }else {
                    special.setIsShenPi("1"); //如果是1  无法进行审批
                }
            }
        }
        return getDataTable(list);
    }

    @GetMapping("/lists")
    public TableDataInfo lists(BdglSpecial bdglSpecial) throws ParseException {
        //获取当前时间
        String dangqianshijian = sdf.format(new Date());
        BdglSpecial bdglRegulars = new BdglSpecial();
        bdglRegulars.setCaoZuoType(bdglRegulars.getCaoZuoType());
        List<BdglSpecial> lists = bdglSpecialService.selectBdglSpecialLists(bdglRegulars);
        for (BdglSpecial regular : lists) {
            if("0".equals(regular.getCaoZuoType())) {
                String rectificationTime = regular.getRectificationTime(); //整改时间
                if(sdf.parse(dangqianshijian).compareTo(sdf.parse(rectificationTime)) > 0) { //判断整改时间是否已过
                    if(regular.getCurrentState() != "已通过") {
                        regular.setPaixuValue(5);
                        bdglSpecialService.updateBdglSpecial(regular);
                    }
                }
            }
        }

        startPage();
        LoginUser loginUser = SecurityUtils.getLoginUser();
        Long deptId = loginUser.getDeptId();
        List<BdglSpecial> list = bdglSpecialService.selectBdglSpecialLists(bdglSpecial);
        for (BdglSpecial special : list) {
            if("0".equals(special.getCaoZuoType())) {
                //判断是不是当前操作部门
                if(deptId .equals(special.getInspectedUnitId())) {
                    special.setIsCaoZuoDept("0");  //如果是0 就可以问题整改
                }else {
                    special.setIsCaoZuoDept("1"); //如果是1 就无法问题整改
                }

                //判断部门是否能够审批
                if(deptId.equals(special.getUnitCheckId())) {
                    special.setIsShenPi("0"); //如果是0  就可以进行审批
                }else {
                    special.setIsShenPi("1"); //如果是1  无法进行审批
                }

                /*String rectificationTime = special.getRectificationTime(); //整改时间
                if(sdf.parse(dangqianshijian).compareTo(sdf.parse(rectificationTime)) > 0) { //判断整改时间是否已过
                    String currentState = special.getCurrentState();
                    boolean b = special.getCurrentState() != "已通过";
                    if(special.getCurrentState() != "已通过") {
                        special.setCurrentState("已逾期");
                    }
                }*/
            }
        }
        return getDataTable(list);
    }

    /**
     * 查询当前部门下的所有数据
     * @param bdglSpecial
     * @return
     */
    @GetMapping("/bumenlist")
    public TableDataInfo bumenlist(BdglSpecial bdglSpecial)  {
        List<BdglSpecial> list = new ArrayList<>();
        List<BdglSpecial> bdglSpecials = bdglSpecialService.selectBdglSpecialList(bdglSpecial);
        for (BdglSpecial lista : bdglSpecials) {
            if("待处理".equals(lista.getCurrentState()) || "待验收".equals(lista.getCurrentState())){
                list.add(lista);
            }
        }
        return getDataTable(list);
    }


    /**
     * 问题整改 接口
     * @param bdglSpecial
     * @return
     */
    @PostMapping("/editquestion")
    public AjaxResult editQuestion(@RequestBody BdglSpecial bdglSpecial)
    {
        //获取登录人账号
        LoginUser loginUser = SecurityUtils.getLoginUser();
        Long userId = loginUser.getUserId();
        bdglSpecial.setCurrentState("待验收"); //当前状态
        bdglSpecial.setUpdateTime(new Date()); //修改时间
        bdglSpecial.setCreateUserId(userId); //创建人ID
        bdglSpecial.setPaixuValue(2);
        bdglSpecialService.updateBdglSpecial(bdglSpecial);
        //调用添加记录接口
        addInfo(bdglSpecial, userId);
        return AjaxResult.success("成功");
    }

    /**
     * 添加记录
     */
    public void addInfo(BdglSpecial bdglSpecial, Long userId) {
        //存储调整记录
        BdglRectificationRecord brd = new BdglRectificationRecord();
        brd.setWjId(bdglSpecial.getId()); //经常性检查 ID
        brd.setCaozuoType(bdglSpecial.getCaoZuoType()); //操作状态 加分 减分
        brd.setInspectedUnit(bdglSpecial.getInspectedUnit());
        brd.setInspectedUnitId(bdglSpecial.getInspectedUnitId());
        brd.setInspectionTime(bdglSpecial.getInspectionTime());
        brd.setScore(bdglSpecial.getScore());
        brd.setRectificationTime(bdglSpecial.getRectificationTime());
        brd.setQuestion(bdglSpecial.getQuestion());
        brd.setCreateTime(new Date());
        brd.setCreateUserId(userId);
        brd.setChargePeople(bdglSpecial.getChargePeople());
        brd.setChargePeopleId(bdglSpecial.getChargePeopleId());
        brd.setCorrectiveAction(bdglSpecial.getCorrectiveAction());
        brd.setPictureProof(bdglSpecial.getPictureProof());
        brd.setDocumentProof(bdglSpecial.getDocumentProof());
        bdglRectificationRecordService.insertBdglRectificationRecord(brd);
    }

    /**
     * 审批通过的 接口
     * @param bdglSpecial
     * @return
     */
    @PostMapping("/editissuccess")
    public AjaxResult editIsSuccess(@RequestBody BdglSpecial bdglSpecial)
    {
        bdglSpecial.setCurrentState("已通过");
        bdglSpecial.setPaixuValue(4);
        return AjaxResult.success(bdglSpecialService.updateBdglSpecial(bdglSpecial));
    }

    /**
     * 审批失败的 接口
     * @param bdglSpecial
     * @return
     */
    @PostMapping("/editisfalse")
    public AjaxResult editIsFalse(@RequestBody BdglSpecial bdglSpecial)
    {
        bdglSpecial.setCurrentState("未通过");
        bdglSpecial.setPaixuValue(3);
        return AjaxResult.success(bdglSpecialService.updateBdglSpecial(bdglSpecial));
    }

    /**
     * 导出专项检查列表
     */
   // @PreAuthorize("@ss.hasPermi('bdglregular:specialinfo:export')")
    @Log(title = "专项检查", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglSpecial bdglSpecial)
    {
        List<BdglSpecial> list = bdglSpecialService.selectBdglSpecialList(bdglSpecial);
        ExcelUtil<BdglSpecial> util = new ExcelUtil<BdglSpecial>(BdglSpecial.class);
        return util.exportExcel(list, "专项检查数据");
    }

    /**
     * 获取专项检查详细信息
     */
   // @PreAuthorize("@ss.hasPermi('bdglregular:specialinfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(bdglSpecialService.selectBdglSpecialById(id));
    }

    /**
     * 新增专项检查
     */
   // @PreAuthorize("@ss.hasPermi('bdglregular:specialinfo:add')")
    @Log(title = "专项检查", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglSpecial bdglSpecial)
    {
        //获取登录人员信息
        LoginUser loginUser = SecurityUtils.getLoginUser();
        Long deptId = loginUser.getDeptId();
        //bdglSpecial.setUnitCheckId(deptId);

        Long inspectedUnitId = bdglSpecial.getInspectedUnitId(); //受检单位
        SysDept sysDept = deptService.selectDeptById(inspectedUnitId);
        BigDecimal score = sysDept.getScore(); //总积分

        //获取父部门ID----start
        Long parentId = sysDept.getParentId();
        BigDecimal acnestorscore = BigDecimal.valueOf(0);
        if(parentId != 0){
            SysDept sysDeptacnestor = deptService.selectDeptById(parentId);
            acnestorscore = sysDeptacnestor.getScore(); //父部门的总分数
        }

        //获取父部门ID----end

        BigDecimal scoreInfo = BigDecimal.valueOf(0); //定义本部门分值
        BigDecimal fuscoreInfo = BigDecimal.valueOf(0); //定义父部门分值
        if("0".equals(bdglSpecial.getCaoZuoType())){
            scoreInfo = score.subtract(bdglSpecial.getScore()); //扣分
            if(parentId != 0) {
                fuscoreInfo = acnestorscore.subtract(bdglSpecial.getScore());
            }
            bdglSpecial.setCurrentState("待处理");
            bdglSpecial.setPaixuValue(1);
        }else if("1".equals(bdglSpecial.getCaoZuoType())){
            scoreInfo = bdglSpecial.getScore().add(score); //加分
            if(parentId != 0) {
                fuscoreInfo = acnestorscore.add(bdglSpecial.getScore());
            }
        }

        // 修改当前部门分数 ------------
        SysDept sd = new SysDept();
        sd.setScore(scoreInfo);
        sd.setDeptId(inspectedUnitId);
        deptService.updateDept(sd);
        //-------------------------------
        //修改父部门分数 =================
        if(parentId != 0){
            SysDept sdf = new SysDept();
            sdf.setScore(fuscoreInfo);
            sdf.setDeptId(parentId);
            deptService.updateDept(sdf);
            //====================================
            bdglSpecial.setParentId(parentId);
        }
        // 还差修改 上级部门的分数
        return toAjax(bdglSpecialService.insertBdglSpecial(bdglSpecial));
    }

    /**
     * 修改专项检查
     */
   // @PreAuthorize("@ss.hasPermi('bdglregular:specialinfo:edit')")
    @Log(title = "专项检查", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglSpecial bdglSpecial)
    {
        return toAjax(bdglSpecialService.updateBdglSpecial(bdglSpecial));
    }

    /**
     * 删除专项检查
     */
   // @PreAuthorize("@ss.hasPermi('bdglregular:specialinfo:remove')")
    @Log(title = "专项检查", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        for (Long id : ids) {
            BdglSpecial bdglRegular = bdglSpecialService.selectBdglSpecialById(id);
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
        return toAjax(bdglSpecialService.deleteBdglSpecialByIds(ids));
    }
}
