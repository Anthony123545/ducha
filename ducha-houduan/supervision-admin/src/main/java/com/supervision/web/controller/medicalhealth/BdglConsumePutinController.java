package com.supervision.web.controller.medicalhealth;

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
import com.supervision.medicalhealth.domain.BdglConsumeArchives;
import com.supervision.system.service.ISysUserService;
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

import com.supervision.medicalhealth.domain.BdglConsumePutin;
import com.supervision.medicalhealth.service.IBdglConsumePutinService;


/**
 * 耗材入库登记Controller
 * 
 * @author hr
 * @date 2022-02-12
 */
@RestController
@RequestMapping("/medicalhealth/consumeputin")
public class BdglConsumePutinController extends BaseController
{
    @Autowired
    private IBdglConsumePutinService bdglConsumePutinService;
    @Autowired
    private ISysUserService userService;

    /**
     * 查询耗材入库登记列表
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:consumeputin:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglConsumePutin bdglConsumePutin)
    {
        startPage();
        List<BdglConsumePutin> list = bdglConsumePutinService.selectBdglConsumePutinList(bdglConsumePutin);
        return getDataTable(list);
    }
    @GetMapping("/getlist")
    public AjaxResult getlist(BdglConsumeArchives bdglConsumeArchives){
        BdglConsumeArchives list=bdglConsumePutinService.selectlist(bdglConsumeArchives);
        if(list==null){
            return AjaxResult.success("请输入正确条码");
        }
        return AjaxResult.success(list);
    }

    /**
     * 导出耗材入库登记列表
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:consumeputin:export')")
    @Log(title = "耗材入库登记", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglConsumePutin bdglConsumePutin)
    {
        List<BdglConsumePutin> list = bdglConsumePutinService.selectBdglConsumePutinList(bdglConsumePutin);
        ExcelUtil<BdglConsumePutin> util = new ExcelUtil<BdglConsumePutin>(BdglConsumePutin.class);
        return util.exportExcel(list, "耗材入库登记数据");
    }

    /**
     * 获取耗材入库登记详细信息
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:consumeputin:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(bdglConsumePutinService.selectBdglConsumePutinById(id));
    }

    /**
     * 新增耗材入库登记
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:consumeputin:add')")
    @Log(title = "耗材入库登记", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglConsumePutin bdglConsumePutin)
    {
        LoginUser loginUser = getLoginUser();
        bdglConsumePutin.setAdminId(loginUser.getUserId());
        SysUser sysUser = userService.selectUserById(loginUser.getUserId());
        bdglConsumePutin.setUserName(sysUser.getNickName());
        return toAjax(bdglConsumePutinService.insertBdglConsumePutin(bdglConsumePutin));
    }
    @PostMapping("/insert")
    public AjaxResult insert(@RequestBody BdglConsumePutin bdglConsumePutin)
    {
        LoginUser loginUser = getLoginUser();
        bdglConsumePutin.setAdminId(loginUser.getUserId());
        SysUser sysUser = userService.selectUserById(loginUser.getUserId());
        bdglConsumePutin.setUserName(sysUser.getNickName());
        return toAjax(bdglConsumePutinService.add(bdglConsumePutin));
    }

    /**
     * 修改耗材入库登记
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:consumeputin:edit')")
    @Log(title = "耗材入库登记", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglConsumePutin bdglConsumePutin)
    {
        return toAjax(bdglConsumePutinService.updateBdglConsumePutin(bdglConsumePutin));
    }
    @PutMapping("/update")
    public AjaxResult update(@RequestBody BdglConsumePutin bdglConsumePutin)
    {
        if(bdglConsumePutin.getPutIn()<bdglConsumePutin.getSurplus()&&bdglConsumePutin.getSurplus()<bdglConsumePutin.getPutIn()){
            return AjaxResult.error("请输入正确的入库数量");
        }
        return toAjax(bdglConsumePutinService.update(bdglConsumePutin));
    }

    /**
     * 删除耗材入库登记
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:consumeputin:remove')")
    @Log(title = "耗材入库登记", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bdglConsumePutinService.deleteBdglConsumePutinByIds(ids));
    }
}
