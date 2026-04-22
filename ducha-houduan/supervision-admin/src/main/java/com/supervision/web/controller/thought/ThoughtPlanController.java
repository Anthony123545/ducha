package com.supervision.web.controller.thought;

import java.util.*;

import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.common.utils.StringUtils;
import com.supervision.system.service.ISysDeptService;
import com.supervision.system.service.ISysUserService;
import com.supervision.thought.domain.*;
import com.supervision.thought.service.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 月教育计划Controller
 *
 * @author supervision
 * @date 2022-04-08
 */
@Transactional
@RestController
@RequestMapping("/thought/thoughtPlan")
public class ThoughtPlanController extends BaseController {
    @Autowired
    private IThoughtPlanService thoughtPlanService;

    @Autowired
    private IThoughtPlanFuService planFuService;

    @Autowired
    private IThoughtPlanFujianService planFujianService;

    @Autowired
    private IThoughtPlanResponsesService responsesService;

    @Autowired
    private IThoughtPlanResponsefuService responsefuService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private IThoughtResponseContentService responseContentService;

    @Autowired
    private ISysDeptService deptService;

    /**
     * 查询月教育计划列表
     */
    @PreAuthorize("@ss.hasPermi('thought:thoughtPlan:list')")
    @GetMapping("/list")
    public TableDataInfo list(ThoughtPlan thoughtPlan) {
        Long unitId = thoughtPlan.getUnitId();
        thoughtPlan.setUnitId(null);
        startPage();
        List<ThoughtPlan> list = thoughtPlanService.selectThoughtPlanList(thoughtPlan);
        for (ThoughtPlan plan : list) {
            ThoughtPlanResponses thoughtPlanResponses = new ThoughtPlanResponses();
            thoughtPlanResponses.setPlanId(plan.getId());
            thoughtPlanResponses.setUnitId(unitId);
            //判断当前堵门是否响应
            if (unitId != null) {
                List<ThoughtPlanResponses> thoughtPlanResponses1 = responsesService.selectThoughtPlanResponsesList(thoughtPlanResponses);
                if (thoughtPlanResponses1.size() > 0 && unitId != null) {
                    for (ThoughtPlanResponses planResponses : thoughtPlanResponses1) {
                        plan.setState(planResponses.getState().toString());
                        ThoughtResponseContent thoughtResponseContent = new ThoughtResponseContent();
                        thoughtResponseContent.setPlanId(planResponses.getId());
                        List<ThoughtResponseContent> thoughtResponseContents = responseContentService.selectThoughtResponseContentList(thoughtResponseContent);
                        if (thoughtResponseContents.size() > 0) {
                            int number = 0;
                            for (ThoughtResponseContent responseContent : thoughtResponseContents) {
                                if (responseContent.getResponseStatus().equals("1")) {
                                    number++;
                                }
                            }
                            plan.setResponseNumber(number);
                        }
                        //如果当签收件大于任务结束时间  任务状态为进行中  修改任务状态为已过期
                        if (planResponses.getPlanEndTime().getTime() < new Date().getTime() && planResponses.getState() == 4) {
                            plan.setState("5");
                            planResponses.setState(5);
                            responsesService.updateThoughtPlanResponses(planResponses);
                        }
                    }
                    plan.setCommon("1");
                } else {
                    plan.setState("0");
                    plan.setCommon("0");
                }
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出月教育计划列表
     */
    @PreAuthorize("@ss.hasPermi('thought:thoughtPlan:export')")
    @Log(title = "月教育计划", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(ThoughtPlan thoughtPlan) {
        List<ThoughtPlan> list = thoughtPlanService.selectThoughtPlanList(thoughtPlan);
        ExcelUtil<ThoughtPlan> util = new ExcelUtil<ThoughtPlan>(ThoughtPlan.class);
        return util.exportExcel(list, "月教育计划数据");
    }

    /**
     * 查看当前计划是否被响应
     */
    @PostMapping("/isResponse")
    public AjaxResult isResponse(@RequestBody Long id) {
        ThoughtPlanResponses thoughtPlanResponses = new ThoughtPlanResponses();
        thoughtPlanResponses.setPlanId(id);
        List<ThoughtPlanResponses> thoughtPlanResponses1 = responsesService.selectThoughtPlanResponsesList(thoughtPlanResponses);
        if (thoughtPlanResponses1.size() > 0) {
            return AjaxResult.error("该条计划已被响应,不可修改");
        }
        return AjaxResult.success();
    }

    /**
     * 获取月教育计划详细信息
     */
    @PreAuthorize("@ss.hasPermi('thought:thoughtPlan:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        ThoughtPlan thoughtPlan = thoughtPlanService.selectThoughtPlanById(id);
        ThoughtPlanFujian thoughtPlanFujian = new ThoughtPlanFujian();
        thoughtPlanFujian.setPlanId(id);
        List<ThoughtPlanFujian> thoughtPlanFujians = planFujianService.selectThoughtPlanFujianList(thoughtPlanFujian);
        thoughtPlan.setPlanFujians(thoughtPlanFujians);

        ThoughtPlanFu thoughtPlanFu = new ThoughtPlanFu();
        thoughtPlanFu.setPlanId(id);
        List<ThoughtPlanFu> thoughtPlanFus = planFuService.selectThoughtPlanFuList(thoughtPlanFu);
        thoughtPlan.setPlanFus(thoughtPlanFus);

        return AjaxResult.success(thoughtPlan);
    }

    /**
     * 获取月教育计划详细信息
     */
    @PreAuthorize("@ss.hasPermi('thought:thoughtPlan:getDetails')")
    @GetMapping(value = "/getDetails{id}")
    public AjaxResult getDetails(@PathVariable("id") Long id) {
        ThoughtPlan thoughtPlan = thoughtPlanService.selectThoughtPlanById(id);
        ThoughtPlanFujian thoughtPlanFujian = new ThoughtPlanFujian();
        thoughtPlanFujian.setPlanId(id);
        List<ThoughtPlanFujian> thoughtPlanFujians = planFujianService.selectThoughtPlanFujianList(thoughtPlanFujian);
        thoughtPlan.setPlanFujians(thoughtPlanFujians);

        ThoughtPlanFu thoughtPlanFu = new ThoughtPlanFu();
        thoughtPlanFu.setPlanId(id);
        List<ThoughtPlanFu> thoughtPlanFus = planFuService.selectThoughtPlanFuList(thoughtPlanFu);
        thoughtPlan.setPlanFus(thoughtPlanFus);

        return AjaxResult.success(thoughtPlan);
    }

    /**
     * 获取月教育计划详细信息
     */
    @PreAuthorize("@ss.hasPermi('thought:thoughtPlan:updatePlan')")
    @GetMapping(value = "/updatePlan{id}")
    public AjaxResult updatePlan(@PathVariable("id") Long id) {
        ThoughtPlan thoughtPlan = thoughtPlanService.selectThoughtPlanById(id);
        ThoughtPlanFujian thoughtPlanFujian = new ThoughtPlanFujian();
        thoughtPlanFujian.setPlanId(id);
        List<ThoughtPlanFujian> thoughtPlanFujians = planFujianService.selectThoughtPlanFujianList(thoughtPlanFujian);
        thoughtPlan.setPlanFujians(thoughtPlanFujians);

        ThoughtPlanFu thoughtPlanFu = new ThoughtPlanFu();
        thoughtPlanFu.setPlanId(id);
        List<ThoughtPlanFu> thoughtPlanFus = planFuService.selectThoughtPlanFuList(thoughtPlanFu);
        thoughtPlan.setPlanFus(thoughtPlanFus);

        return AjaxResult.success(thoughtPlan);
    }

    /**
     * 保存进度
     */
    @PostMapping("/addJinDu")
    public AjaxResult addJinDu(@RequestBody ThoughtPlanResponses thoughtPlanResponses) {
        int number = 0;//已响应数
        int numbers = 0;//需响应数
        if (thoughtPlanResponses.getCommon().equals("0")) {
            thoughtPlanResponses.setState(4);
        } else {
            thoughtPlanResponses.setState(1);
        }
        List<ThoughtResponseContent> responseContentss = thoughtPlanResponses.getResponseContents();
        if (responseContentss.size() > 0) {
            for (ThoughtResponseContent responseContent : responseContentss) {

                if (responseContent.getResponseStatus() != null) {
                    if (responseContent.getResponseStatus().equals("1")) {
                        number++;
                    }
                    if (responseContent.getIsresponse().equals("1")) {
                        numbers++;
                    }
                }
            }
        }
        thoughtPlanResponses.setResponseNumber(number);
        thoughtPlanResponses.setPlanNumber(numbers);
        int i = responsesService.insertThoughtPlanResponses(thoughtPlanResponses);
        List<ThoughtPlanResponsefu> planResponsefus = thoughtPlanResponses.getPlanResponsefus();
        if (planResponsefus.size() > 0) {
            for (ThoughtPlanResponsefu responsefus : planResponsefus) {
                if (responsefus.getUrl() != null) {
                    responsefus.setFileName(responsefus.getName());
                    responsefus.setFilePath(responsefus.getUrl());
                    responsefus.setPlanId(thoughtPlanResponses.getId());
                    responsefuService.insertThoughtPlanResponsefu(responsefus);
                }
            }
        }
        List<ThoughtResponseContent> responseContents = thoughtPlanResponses.getResponseContents();
        if (responseContents.size() > 0) {
            for (ThoughtResponseContent responseContent : responseContents) {
                if (responseContent.getPlanContent() != null) {
                    responseContent.setPlanId(thoughtPlanResponses.getId());
                    responseContentService.insertThoughtResponseContent(responseContent);
                }
                if (responseContent.getResponseStatus() != null) {

                    if (responseContent.getResponseStatus().equals("1")) {
                        number++;
                    }
                }
            }
        }
        return AjaxResult.success(i);
    }

    /**
     * 修改进度
     */
    @PostMapping("/updateJinDu")
    public AjaxResult updateJinDu(@RequestBody ThoughtPlanResponses thoughtPlanResponses) {
        int number = 0;
        if (thoughtPlanResponses.getCommon().equals("0")) {
            thoughtPlanResponses.setState(4);
        } else {
            thoughtPlanResponses.setState(1);
        }
        List<ThoughtResponseContent> responseContentss = thoughtPlanResponses.getResponseContents();
        if (responseContentss.size() > 0) {
            for (ThoughtResponseContent responseContent : responseContentss) {

                if (responseContent.getResponseStatus() != null) {
                    if (responseContent.getResponseStatus().equals("1")) {
                        number++;
                    }
                }
            }
        }
        thoughtPlanResponses.setResponseNumber(number);
        int i = responsesService.updateThoughtPlanResponses(thoughtPlanResponses);
        List<ThoughtPlanResponsefu> planResponsefus = thoughtPlanResponses.getPlanResponsefus();
        if (planResponsefus.size() > 0) {
            for (ThoughtPlanResponsefu responsefus : planResponsefus) {
                if (responsefus.getId() != null) {
                    responsefus.setPlanId(thoughtPlanResponses.getId());
                    responsefus.setFilePath(responsefus.getUrl());
                    responsefus.setFileName(responsefus.getName());
                    responsefus.setPlanId(thoughtPlanResponses.getId());
                    responsefuService.updateThoughtPlanResponsefu(responsefus);
                } else {
                    responsefus.setFilePath(responsefus.getUrl());
                    responsefus.setFileName(responsefus.getName());
                    responsefus.setFilePath(responsefus.getUrl());
                    responsefus.setPlanId(thoughtPlanResponses.getId());
                    responsefuService.insertThoughtPlanResponsefu(responsefus);
                }
            }
        }
        List<ThoughtResponseContent> responseContents = thoughtPlanResponses.getResponseContents();
        if (responseContents.size() > 0) {
            for (ThoughtResponseContent responseContent : responseContents) {
                if (responseContent.getPlanContent() != null) {
                    responseContent.setPlanId(thoughtPlanResponses.getId());
                    responseContentService.updateThoughtResponseContent(responseContent);
                }
                if (responseContent.getResponseStatus() != null) {

                    if (responseContent.getResponseStatus().equals("1")) {
                        number++;
                    }
                }
            }
        }
        return AjaxResult.success(i);
    }


    //获取当前部门计划进度详细信息
    @PreAuthorize("@ss.hasPermi('thought:thoughtPlan:get')")
    @GetMapping("/get")
    public AjaxResult getPlanJinDu(ThoughtPlanResponses thoughtPlanResponses) {

        List<ThoughtPlanResponses> thoughtPlanResponses1 = responsesService.selectThoughtPlanResponsesList(thoughtPlanResponses);
        if (thoughtPlanResponses1.size() > 0) {
            for (ThoughtPlanResponses planResponses : thoughtPlanResponses1) {
                ThoughtPlanResponsefu thoughtPlanResponsefu = new ThoughtPlanResponsefu();
                thoughtPlanResponsefu.setPlanId(planResponses.getId());
                //响应附件
                List<ThoughtPlanResponsefu> thoughtPlanResponsefus = responsefuService.selectThoughtPlanResponsefuList(thoughtPlanResponsefu);
                planResponses.setPlanResponsefus(thoughtPlanResponsefus);
                //计划内容
                ThoughtResponseContent thoughtPlanFu = new ThoughtResponseContent();
                thoughtPlanFu.setPlanId(planResponses.getId());
                List<ThoughtResponseContent> thoughtPlanFus = responseContentService.selectThoughtResponseContentList(thoughtPlanFu);
                planResponses.setResponseContents(thoughtPlanFus);

                return AjaxResult.success(planResponses);
            }
        }
        return null;
    }


    /**
     * 新增月教育计划
     */
    @PreAuthorize("@ss.hasPermi('thought:thoughtPlan:add')")
    @Log(title = "月教育计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ThoughtPlan thoughtPlan) {
        int number = 0;
        List<ThoughtPlanFu> planFuss = thoughtPlan.getPlanFus();
        if (planFuss.size() > 0) {
            for (ThoughtPlanFu fus : planFuss) {
                if (fus.getIsresponse() != null) {
                    if (fus.getIsresponse().equals("1")) {
                        number++;
                    }
                }
            }
        }
        thoughtPlan.setPlanNumber(number);
        LoginUser loginUser = getLoginUser();
        thoughtPlan.setCreateId(loginUser.getUserId());
        thoughtPlan.setCreateName(userService.selectUserById(loginUser.getUserId()).getNickName());
        int i = thoughtPlanService.insertThoughtPlan(thoughtPlan);
        //添加附件
        List<ThoughtPlanFujian> planFujians = thoughtPlan.getPlanFujians();
        if (planFujians.size() > 0) {
            for (ThoughtPlanFujian planFujian : planFujians) {
                if (planFujian.getUrl() != null) {
                    planFujian.setPlanId(thoughtPlan.getId());
                    planFujian.setFileName(planFujian.getName());
                    planFujian.setFilePath(planFujian.getUrl());
                    planFujianService.insertThoughtPlanFujian(planFujian);
                }
            }
        }
        //添加计划内容
        List<ThoughtPlanFu> planFus = thoughtPlan.getPlanFus();
        if (planFus.size() > 0) {
            for (ThoughtPlanFu fus : planFus) {
                if (fus.getPlanContent() != null) {
                    fus.setPlanId(thoughtPlan.getId());
                    planFuService.insertThoughtPlanFu(fus);
                }
            }
        }
        return toAjax(i);
    }

    /**
     * 修改月教育计划
     */
    @PreAuthorize("@ss.hasPermi('thought:thoughtPlan:edit')")
    @Log(title = "月教育计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ThoughtPlan thoughtPlan) {
        int number = 0;
        List<ThoughtPlanFu> planFuss = thoughtPlan.getPlanFus();
        if (planFuss.size() > 0) {
            for (ThoughtPlanFu fus : planFuss) {
                if (fus.getIsresponse().equals("1")) {
                    number++;
                }
            }
        }
        thoughtPlan.setPlanNumber(number);
        int i = thoughtPlanService.updateThoughtPlan(thoughtPlan);
        //添加附件
        List<ThoughtPlanFujian> planFujians = thoughtPlan.getPlanFujians();
        ThoughtPlanFujian thoughtPlanFujian = new ThoughtPlanFujian();
        thoughtPlanFujian.setPlanId(thoughtPlan.getId());
        planFujianService.deleteThoughtPlanFujian(thoughtPlanFujian);
        if (planFujians.size() > 0) {
            for (ThoughtPlanFujian planFujian : planFujians) {
                planFujian.setFileName(planFujian.getName());
                planFujian.setFilePath(planFujian.getUrl());
                planFujian.setPlanId(thoughtPlan.getId());
                planFujianService.insertThoughtPlanFujian(planFujian);
            }
        }
        ThoughtPlanFu thoughtPlanFu = new ThoughtPlanFu();
        thoughtPlanFu.setPlanId(thoughtPlan.getId());
        planFuService.deleteThoughtPlanFuBy(thoughtPlanFu);
        //添加计划内容
        List<ThoughtPlanFu> planFus = thoughtPlan.getPlanFus();
        if (planFus.size() > 0) {
            for (ThoughtPlanFu fus : planFus) {

                fus.setPlanId(thoughtPlan.getId());
                planFuService.insertThoughtPlanFu(fus);

            }
        }
        return toAjax(i);
    }


    /**
     * 删除月教育计划
     */
    @PreAuthorize("@ss.hasPermi('thought:thoughtPlan:remove')")
    @Log(title = "月教育计划", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        for (Long id : ids) {
            ThoughtPlanFu thoughtPlanFu = new ThoughtPlanFu();
            thoughtPlanFu.setPlanId(id);
            List<ThoughtPlanFu> thoughtPlanFus = planFuService.selectThoughtPlanFuList(thoughtPlanFu);
            for (ThoughtPlanFu planFus : thoughtPlanFus) {
                planFuService.deleteThoughtPlanFuById(planFus.getId());
            }
            ThoughtPlanFujian thoughtPlanFujian = new ThoughtPlanFujian();
            thoughtPlanFujian.setPlanId(id);
            List<ThoughtPlanFujian> thoughtPlanFujians = planFujianService.selectThoughtPlanFujianList(thoughtPlanFujian);
            for (ThoughtPlanFujian planFujian : thoughtPlanFujians) {
                planFujianService.deleteThoughtPlanFujianById(planFujian.getId());
            }
        }
        return toAjax(thoughtPlanService.deleteThoughtPlanByIds(ids));
    }

    /**
     * 获取已审批  卫审批
     */
    @GetMapping("/getnumber")
    public AjaxResult getnumber() {
        String unitIds = null;
        int sPNumber = 0;
        int wSPNumber = 0;
        int wXyNumber = 0;
        HashMap<String, Object> SP = new HashMap<>();
        HashMap<String, Object> wxy = new HashMap<>();
        HashMap<String, Object> spshu = new HashMap<>();
        HashMap<String, Object> wspshu = new HashMap<>();
        HashMap<String, Object> wxyshu = new HashMap<>();
        ThoughtPlan thoughtPlan = thoughtPlanService.selectNewPlan();
        if(StringUtils.isNotNull(thoughtPlan)){

            ThoughtPlanResponses thoughtPlanResponses = new ThoughtPlanResponses();
            thoughtPlanResponses.setPlanId(thoughtPlan.getId());
            List<ThoughtPlanResponses> thoughtPlanResponses1 = responsesService.selectThoughtPlanResponsesList(thoughtPlanResponses);
            for (ThoughtPlanResponses planResponses : thoughtPlanResponses1) {
                unitIds += planResponses.getUnitId() + ",";
                SysDept sysDept = deptService.selectDeptById(planResponses.getUnitId());
                if (planResponses.getState().equals(2)) {
                    SP.put(sysDept.getDeptId().toString(), sysDept.getDeptName());
                    sPNumber++;
                }
//            if (planResponses.getState().equals("1") || planResponses.getState().equals("0")) {
//                SP.put("0", sysDept.getDeptName());
//                wSPNumber++;
//            }

            }
            List<SysDept> sysDepts = deptService.selectDeptList(new SysDept());
            for (SysDept sysDept : sysDepts) {
                if (sysDept.getDeptId() != 203L && sysDept.getDeptId() != 204 && sysDept.getDeptId() != 205 && sysDept.getDeptId() != 206 && sysDept.getDeptId() != 100) {
                    if(unitIds==null){
                        wxy.put(sysDept.getDeptId().toString(), sysDept.getDeptName());
                        wXyNumber++;
                    }
                    else if(!unitIds.contains(sysDept.getDeptId().toString())) {
                        wxy.put(sysDept.getDeptId().toString(), sysDept.getDeptName());
                        wXyNumber++;
                    }
                }
            }
        }
        spshu.put("sPNumber", sPNumber);
        wspshu.put("wSPNumber", wSPNumber);
        wxyshu.put("wXyNumber", wXyNumber);
        ArrayList<Object> list = new ArrayList<>();
        list.add(SP);
        list.add(wxy);
        list.add(sPNumber);
        list.add(wXyNumber);

        return AjaxResult.success(list);

    }
}
