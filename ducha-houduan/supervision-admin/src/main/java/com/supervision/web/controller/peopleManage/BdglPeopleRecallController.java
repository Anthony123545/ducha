package com.supervision.web.controller.peopleManage;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.supervision.framework.web.domain.server.Sys;
import com.supervision.peopleManage.domain.BdglPeople;
import com.supervision.peopleManage.domain.BdglPeopleRecallAuditlog;
import com.supervision.peopleManage.service.IBdglPeopleRecallAuditlogService;
import com.supervision.peopleManage.service.IBdglPeopleService;
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
import com.supervision.peopleManage.domain.BdglPeopleRecall;
import com.supervision.peopleManage.service.IBdglPeopleRecallService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 人员调出Controller
 *
 * @author ruoyi
 * @date 2023-01-10
 */
@RestController
@RequestMapping("/peopleManage/recall")
public class BdglPeopleRecallController extends BaseController
{
    @Autowired
    private IBdglPeopleRecallService bdglPeopleRecallService;

    //人员审批接口
    @Autowired
    private IBdglPeopleRecallAuditlogService bdglPeopleRecallAuditlogService;

    @Autowired
    private IBdglPeopleService bdglPeopleService;

    /**
     * 查询人员调出列表
     */
    @PreAuthorize("@ss.hasPermi('peopleManage:recall:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglPeopleRecall bdglPeopleRecall)
    {
        startPage();
        List<BdglPeopleRecall> list = bdglPeopleRecallService.selectBdglPeopleRecallList(bdglPeopleRecall);

        return getDataTable(list);
    }

    /**
     * 导出人员调出列表
     */
    @PreAuthorize("@ss.hasPermi('peopleManage:recall:export')")
    @Log(title = "人员调出", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BdglPeopleRecall bdglPeopleRecall)
    {
        List<BdglPeopleRecall> list = bdglPeopleRecallService.selectBdglPeopleRecallList(bdglPeopleRecall);
        ExcelUtil<BdglPeopleRecall> util = new ExcelUtil<BdglPeopleRecall>(BdglPeopleRecall.class);
        util.exportExcel(response, list, "人员调出数据");
    }

    /**
     * 获取单个审核人员信息
     * @param bdglPeopleRecall
     * @return
     */
    @GetMapping("/getAuthorization")
    public TableDataInfo getAuthorization(BdglPeopleRecall bdglPeopleRecall)
    {
        List<BdglPeopleRecall> list = bdglPeopleRecallService.selectBdglPeopleID(bdglPeopleRecall);
        return getDataTable(list);
    }

    /**
     * 获取人员调出详细信息
     */
    @PreAuthorize("@ss.hasPermi('peopleManage:recall:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bdglPeopleRecallService.selectBdglPeopleRecallById(id));
    }

    /**
     * 新增人员调出
     */
    @PreAuthorize("@ss.hasPermi('peopleManage:recall:add')")
    @Log(title = "人员调出", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglPeopleRecall bdglPeopleRecall)
    {
        BdglPeople peopleEntity =new BdglPeople();
        peopleEntity.setId(Long.valueOf(bdglPeopleRecall.getPeopleId()));
        peopleEntity.setStatus(1);
        bdglPeopleService.updateBdglPeople(peopleEntity);
        BdglPeopleRecall dc =new BdglPeopleRecall();
        dc.setPeopleId(bdglPeopleRecall.getPeopleId());
        List<BdglPeopleRecall> list = bdglPeopleRecallService.selectBdglPeopleRecallList(dc);
        if(list.size()>0){
            return AjaxResult.success("数据已经存！",300);
        }else{
            return toAjax(bdglPeopleRecallService.insertBdglPeopleRecall(bdglPeopleRecall));
        }

    }

    /**
     * 审核调用方法
     * @param bdglPeopleRecall
     */
    public void peopleOut(BdglPeopleRecall bdglPeopleRecall){
        BdglPeopleRecallAuditlog sh;
        switch (bdglPeopleRecall.getType()){
            case "transportation":
                sh = new BdglPeopleRecallAuditlog();
                sh.setContents("运输营房同意");
                sh.setType("transportation");
                sh.setPeopleId(bdglPeopleRecall.getPeopleId());
                bdglPeopleRecallAuditlogService.insertBdglPeopleRecallAuditlog(sh);
                break;
            case "finance":
                sh= new BdglPeopleRecallAuditlog();
                sh.setContents("财务同意");
                sh.setType("finance");
                sh.setPeopleId(bdglPeopleRecall.getPeopleId());
                bdglPeopleRecallAuditlogService.insertBdglPeopleRecallAuditlog(sh);
                break;
            case "guaranteez":
                sh= new BdglPeopleRecallAuditlog();
                sh.setContents("保障处处长同意");
                sh.setType("guaranteez");
                sh.setPeopleId(bdglPeopleRecall.getPeopleId());
                bdglPeopleRecallAuditlogService.insertBdglPeopleRecallAuditlog(sh);
            case "propaganda":
                sh= new BdglPeopleRecallAuditlog();
                sh.setContents("宣传同意");
                sh.setType("propaganda");
                sh.setPeopleId(bdglPeopleRecall.getPeopleId());
                bdglPeopleRecallAuditlogService.insertBdglPeopleRecallAuditlog(sh);
                break;
            case "tissueembolus":
                sh= new BdglPeopleRecallAuditlog();
                sh.setContents("组织纪检同意");
                sh.setType("tissueembolus");
                sh.setPeopleId(bdglPeopleRecall.getPeopleId());
                bdglPeopleRecallAuditlogService.insertBdglPeopleRecallAuditlog(sh);
                break;
            case "zzgzczr":
                sh= new BdglPeopleRecallAuditlog();
                sh.setContents("政治工作处主认同意");
                sh.setType("zzgzczr");
                sh.setPeopleId(bdglPeopleRecall.getPeopleId());
                bdglPeopleRecallAuditlogService.insertBdglPeopleRecallAuditlog(sh);
                break;
            case "bms":
                sh= new BdglPeopleRecallAuditlog();
                sh.setContents("保密室同意");
                sh.setType("bms");
                sh.setPeopleId(bdglPeopleRecall.getPeopleId());
                bdglPeopleRecallAuditlogService.insertBdglPeopleRecallAuditlog(sh);
                break;
            case "xlgl":
                sh= new BdglPeopleRecallAuditlog();
                sh.setContents("训练管理同意");
                sh.setType("xlgl");
                sh.setPeopleId(bdglPeopleRecall.getPeopleId());
                bdglPeopleRecallAuditlogService.insertBdglPeopleRecallAuditlog(sh);
                break;
            case "fcmz":
                sh= new BdglPeopleRecallAuditlog();
                sh.setContents("副参谋长同意");
                sh.setType("fcmz");
                sh.setPeopleId(bdglPeopleRecall.getPeopleId());
                bdglPeopleRecallAuditlogService.insertBdglPeopleRecallAuditlog(sh);
                break;
            case "zsz":
                sh= new BdglPeopleRecallAuditlog();
                sh.setContents("总师同意");
                sh.setType("zsz");
                sh.setPeopleId(bdglPeopleRecall.getPeopleId());
                bdglPeopleRecallAuditlogService.insertBdglPeopleRecallAuditlog(sh);
                break;
            case "fzz":
                sh= new BdglPeopleRecallAuditlog();
                sh.setContents("副站长同意");
                sh.setType("fzz");
                sh.setPeopleId(bdglPeopleRecall.getPeopleId());
                bdglPeopleRecallAuditlogService.insertBdglPeopleRecallAuditlog(sh);
                break;
            case "zz":
                sh= new BdglPeopleRecallAuditlog();
                sh.setContents("站长同意");
                sh.setType("zz");
                sh.setPeopleId(bdglPeopleRecall.getPeopleId());
                bdglPeopleRecallAuditlogService.insertBdglPeopleRecallAuditlog(sh);
                break;
            case "zb":
                sh= new BdglPeopleRecallAuditlog();
                sh.setContents("装备同意");
                sh.setType("zb");
                sh.setPeopleId(bdglPeopleRecall.getPeopleId());
                bdglPeopleRecallAuditlogService.insertBdglPeopleRecallAuditlog(sh);
                break;
            case "gy":
                sh= new BdglPeopleRecallAuditlog();
                sh.setContents("供应同意");
                sh.setType("gy");
                sh.setPeopleId(bdglPeopleRecall.getPeopleId());
                bdglPeopleRecallAuditlogService.insertBdglPeopleRecallAuditlog(sh);
                break;
            case "guaranteef":
                sh= new BdglPeopleRecallAuditlog();
                sh.setContents("保障处副处长同意");
                sh.setType("guaranteef");
                sh.setPeopleId(bdglPeopleRecall.getPeopleId());
                bdglPeopleRecallAuditlogService.insertBdglPeopleRecallAuditlog(sh);
                break;
            case "bw":
                sh= new BdglPeopleRecallAuditlog();
                sh.setContents("保卫同意");
                sh.setType("bw");
                sh.setPeopleId(bdglPeopleRecall.getPeopleId());
                bdglPeopleRecallAuditlogService.insertBdglPeopleRecallAuditlog(sh);
                break;
            case "rlzy":
                sh= new BdglPeopleRecallAuditlog();
                sh.setContents("人力资源同意");
                sh.setType("rlzy");
                sh.setPeopleId(bdglPeopleRecall.getPeopleId());
                bdglPeopleRecallAuditlogService.insertBdglPeopleRecallAuditlog(sh);
                break;
            case "zzggcfzr":
                sh= new BdglPeopleRecallAuditlog();
                sh.setContents("政治工作处副处长同意");
                sh.setType("zzggcfzr");
                sh.setPeopleId(bdglPeopleRecall.getPeopleId());
                bdglPeopleRecallAuditlogService.insertBdglPeopleRecallAuditlog(sh);
                break;
            case "tss":
                sh= new BdglPeopleRecallAuditlog();
                sh.setContents("图书室同意");
                sh.setType("tss");
                sh.setPeopleId(bdglPeopleRecall.getPeopleId());
                bdglPeopleRecallAuditlogService.insertBdglPeopleRecallAuditlog(sh);
                break;
            case "jyjxxbz":
                sh= new BdglPeopleRecallAuditlog();
                sh.setContents("机要兼信息保障同意");
                sh.setType("jyjxxbz");
                sh.setPeopleId(bdglPeopleRecall.getPeopleId());
                bdglPeopleRecallAuditlogService.insertBdglPeopleRecallAuditlog(sh);
                break;
            case "zs":
                sh= new BdglPeopleRecallAuditlog();
                sh.setContents("作战同意");
                sh.setType("zs");
                sh.setPeopleId(bdglPeopleRecall.getPeopleId());
                bdglPeopleRecallAuditlogService.insertBdglPeopleRecallAuditlog(sh);
                break;
            case "cmz":
                sh= new BdglPeopleRecallAuditlog();
                sh.setContents("参谋长同意");
                sh.setType("cmz");
                sh.setPeopleId(bdglPeopleRecall.getPeopleId());
                bdglPeopleRecallAuditlogService.insertBdglPeopleRecallAuditlog(sh);
                break;
            case "fzw":
                sh= new BdglPeopleRecallAuditlog();
                sh.setContents("副政委同意");
                sh.setType("fzw");
                sh.setPeopleId(bdglPeopleRecall.getPeopleId());
                bdglPeopleRecallAuditlogService.insertBdglPeopleRecallAuditlog(sh);
                break;
            case "zw":
                sh= new BdglPeopleRecallAuditlog();
                sh.setContents("政委同意");
                sh.setType("zw");
                sh.setPeopleId(bdglPeopleRecall.getPeopleId());
                bdglPeopleRecallAuditlogService.insertBdglPeopleRecallAuditlog(sh);
                break;
        }

    }
    /**
     * 修改人员调出
     */
    @PreAuthorize("@ss.hasPermi('peopleManage:recall:edit')")
    @Log(title = "人员调出", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    public AjaxResult edit(@RequestBody BdglPeopleRecall bdglPeopleRecall)
    {
        peopleOut(bdglPeopleRecall);
        return AjaxResult.success(bdglPeopleRecallService.selectBdglPeopleID(bdglPeopleRecall));
    }

    /**
     * 删除人员调出
     */
    @PreAuthorize("@ss.hasPermi('peopleManage:recall:remove')")
    @Log(title = "人员调出", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        for (int i=0;ids.length>i;i++){
            System.out.println(ids[i]);
            BdglPeopleRecall bdglPeopleRecall= bdglPeopleRecallService.selectBdglPeopleRecallById(ids[i]);
            if(bdglPeopleRecall!=null){
                BdglPeople peopleEntity =new BdglPeople();
                peopleEntity.setId(Long.valueOf(bdglPeopleRecall.getPeopleId()));
                peopleEntity.setStatus(0);
                bdglPeopleService.updateBdglPeople(peopleEntity);
            }
        }
        return toAjax(bdglPeopleRecallService.deleteBdglPeopleRecallByIds(ids));
    }
}
