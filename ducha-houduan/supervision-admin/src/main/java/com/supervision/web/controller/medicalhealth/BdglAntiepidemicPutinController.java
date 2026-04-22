package com.supervision.web.controller.medicalhealth;

import java.util.Date;
import java.util.List;

import com.supervision.common.annotation.Log;
import com.supervision.common.core.controller.BaseController;
import com.supervision.common.core.domain.AjaxResult;
import com.supervision.common.core.domain.entity.SysUser;
import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.common.core.page.TableDataInfo;
import com.supervision.common.enums.BusinessType;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.medicalhealth.domain.BdglAntiepidemicArchives;
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

import com.supervision.medicalhealth.domain.BdglAntiepidemicPutin;
import com.supervision.medicalhealth.service.IBdglAntiepidemicPutinService;


/**
 * 防疫用品入库登记Controller
 * 
 * @author hr
 * @date 2022-02-14
 */
@RestController
@RequestMapping("/medicalhealth/antiepidemicputin")
public class BdglAntiepidemicPutinController extends BaseController
{
    @Autowired
    private IBdglAntiepidemicPutinService bdglAntiepidemicPutinService;

    /**
     * 查询防疫用品入库登记列表
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:antiepidemicputin:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglAntiepidemicPutin bdglAntiepidemicPutin)
    {
        startPage();
        bdglAntiepidemicPutin.setExpiredstatus("0");
        bdglAntiepidemicPutin.setSurplus(null);
        List<BdglAntiepidemicPutin> list = bdglAntiepidemicPutinService.selectBdglAntiepidemicPutinList(bdglAntiepidemicPutin);
        return getDataTable(list);
    }
    @GetMapping("/getlist")
    public AjaxResult getlist(BdglAntiepidemicArchives bdglAntiepidemicArchives){
        BdglAntiepidemicArchives list=bdglAntiepidemicPutinService.selectlist(bdglAntiepidemicArchives);
        if(list==null){
            return AjaxResult.success("请输入正确条码");
        }
        return AjaxResult.success(list);
    }

    /**
     * 导出防疫用品入库登记列表
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:antiepidemicputin:export')")
    @Log(title = "防疫用品入库登记", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglAntiepidemicPutin bdglAntiepidemicPutin)
    {
        List<BdglAntiepidemicPutin> list = bdglAntiepidemicPutinService.selectBdglAntiepidemicPutinList(bdglAntiepidemicPutin);
        ExcelUtil<BdglAntiepidemicPutin> util = new ExcelUtil<BdglAntiepidemicPutin>(BdglAntiepidemicPutin.class);
        return util.exportExcel(list, "防疫用品入库登记数据");
    }

    /**
     * 获取防疫用品入库登记详细信息
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:antiepidemicputin:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(bdglAntiepidemicPutinService.selectBdglAntiepidemicPutinById(id));
    }

    /**
     * 新增防疫用品入库登记
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:antiepidemicputin:add')")
    @Log(title = "防疫用品入库登记", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglAntiepidemicPutin bdglAntiepidemicPutin)
    {
        LoginUser loginUser = getLoginUser();
        bdglAntiepidemicPutin.setAdminId(loginUser.getUserId());
        bdglAntiepidemicPutin.setCreatetime(new Date());
        return toAjax(bdglAntiepidemicPutinService.insertBdglAntiepidemicPutin(bdglAntiepidemicPutin));
    }

    @PostMapping("/insert")
    public AjaxResult insert(@RequestBody BdglAntiepidemicPutin bdglAntiepidemicPutin)
    {
        LoginUser loginUser = getLoginUser();
        bdglAntiepidemicPutin.setAdminId(loginUser.getUserId());
        bdglAntiepidemicPutin.setCreatetime(new Date());
        return toAjax(bdglAntiepidemicPutinService.add(bdglAntiepidemicPutin));
    }

    /**
     * 修改防疫用品入库登记
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:antiepidemicputin:edit')")
    @Log(title = "防疫用品入库登记", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglAntiepidemicPutin bdglAntiepidemicPutin)
    {
        return toAjax(bdglAntiepidemicPutinService.updateBdglAntiepidemicPutin(bdglAntiepidemicPutin));
    }
    @PutMapping("/update")
    public AjaxResult update(@RequestBody BdglAntiepidemicPutin bdglAntiepidemicPutin)
    {
        if(bdglAntiepidemicPutin.getPutIn()<bdglAntiepidemicPutin.getSurplus()){
            return AjaxResult.error("请输入正确的入库数量");
        }
        return toAjax(bdglAntiepidemicPutinService.update(bdglAntiepidemicPutin));
    }

    /**
     * 删除防疫用品入库登记
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:antiepidemicputin:remove')")
    @Log(title = "防疫用品入库登记", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bdglAntiepidemicPutinService.deleteBdglAntiepidemicPutinByIds(ids));
    }
}
