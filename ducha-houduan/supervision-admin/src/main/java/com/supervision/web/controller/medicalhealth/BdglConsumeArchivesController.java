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

import com.supervision.medicalhealth.domain.BdglConsumeArchives;
import com.supervision.medicalhealth.service.IBdglConsumeArchivesService;


/**
 * 耗材库存档案Controller
 * 
 * @author hr
 * @date 2022-02-12
 */
@RestController
@RequestMapping("/medicalhealth/archives")
public class BdglConsumeArchivesController extends BaseController
{
    @Autowired
    private IBdglConsumeArchivesService bdglConsumeArchivesService;
    @Autowired
    private ISysUserService userService;

    /**
     * 查询耗材库存档案列表
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:archives:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglConsumeArchives bdglConsumeArchives)
    {
        startPage();
        List<BdglConsumeArchives> list = bdglConsumeArchivesService.selectBdglConsumeArchivesList(bdglConsumeArchives);
        return getDataTable(list);
    }

    /**
     * 导出耗材库存档案列表
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:archives:export')")
    @Log(title = "耗材库存档案", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglConsumeArchives bdglConsumeArchives)
    {
        List<BdglConsumeArchives> list = bdglConsumeArchivesService.selectBdglConsumeArchivesList(bdglConsumeArchives);
        ExcelUtil<BdglConsumeArchives> util = new ExcelUtil<BdglConsumeArchives>(BdglConsumeArchives.class);
        return util.exportExcel(list, "耗材库存档案数据");
    }

    /**
     * 获取耗材库存档案详细信息
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:archives:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(bdglConsumeArchivesService.selectBdglConsumeArchivesById(id));
    }

    /**
     * 新增耗材库存档案
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:archives:add')")
    @Log(title = "耗材库存档案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglConsumeArchives bdglConsumeArchives)
    {
        LoginUser loginUser = getLoginUser();
        bdglConsumeArchives.setAdminId(loginUser.getUserId());
        SysUser sysUser = userService.selectUserById(loginUser.getUserId());
        bdglConsumeArchives.setUserName(sysUser.getNickName());
        return toAjax(bdglConsumeArchivesService.insertBdglConsumeArchives(bdglConsumeArchives));
    }

    /**
     * 修改耗材库存档案
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:archives:edit')")
    @Log(title = "耗材库存档案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglConsumeArchives bdglConsumeArchives)
    {
        return toAjax(bdglConsumeArchivesService.updateBdglConsumeArchives(bdglConsumeArchives));
    }

    /**
     * 删除耗材库存档案
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:archives:remove')")
    @Log(title = "耗材库存档案", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bdglConsumeArchivesService.deleteBdglConsumeArchivesByIds(ids));
    }
}
