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
import com.supervision.medicalhealth.domain.BdglAntiepidemicArchives;
import com.supervision.medicalhealth.service.IBdglAntiepidemicArchivesService;


/**
 * 防疫用品库存档案Controller
 * 
 * @author hr
 * @date 2022-02-14
 */
@RestController
@RequestMapping("/medicalhealth/antiepidemicarchives")
public class BdglAntiepidemicArchivesController extends BaseController
{
    @Autowired
    private IBdglAntiepidemicArchivesService bdglAntiepidemicArchivesService;
    @Autowired
    private ISysUserService userService;

    /**
     * 查询防疫用品库存档案列表
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:antiepidemicarchives:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglAntiepidemicArchives bdglAntiepidemicArchives)
    {
        startPage();
        List<BdglAntiepidemicArchives> list = bdglAntiepidemicArchivesService.selectBdglAntiepidemicArchivesList(bdglAntiepidemicArchives);
        return getDataTable(list);
    }

    /**
     * 导出防疫用品库存档案列表
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:antiepidemicarchives:export')")
    @Log(title = "防疫用品库存档案", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglAntiepidemicArchives bdglAntiepidemicArchives)
    {
        List<BdglAntiepidemicArchives> list = bdglAntiepidemicArchivesService.selectBdglAntiepidemicArchivesList(bdglAntiepidemicArchives);
        ExcelUtil<BdglAntiepidemicArchives> util = new ExcelUtil<BdglAntiepidemicArchives>(BdglAntiepidemicArchives.class);
        return util.exportExcel(list, "防疫用品库存档案数据");
    }

    /**
     * 获取防疫用品库存档案详细信息
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:antiepidemicarchives:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(bdglAntiepidemicArchivesService.selectBdglAntiepidemicArchivesById(id));
    }

    /**
     * 新增防疫用品库存档案
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:antiepidemicarchives:add')")
    @Log(title = "防疫用品库存档案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglAntiepidemicArchives bdglAntiepidemicArchives)
    {
        LoginUser loginUser = getLoginUser();
        SysUser sysUser = userService.selectUserById(loginUser.getUserId());
        bdglAntiepidemicArchives.setAdminId(loginUser.getUserId());
        bdglAntiepidemicArchives.setUserName(sysUser.getNickName());
        return toAjax(bdglAntiepidemicArchivesService.insertBdglAntiepidemicArchives(bdglAntiepidemicArchives));
    }

    /**
     * 修改防疫用品库存档案
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:antiepidemicarchives:edit')")
    @Log(title = "防疫用品库存档案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglAntiepidemicArchives bdglAntiepidemicArchives)
    {
        return toAjax(bdglAntiepidemicArchivesService.updateBdglAntiepidemicArchives(bdglAntiepidemicArchives));
    }

    /**
     * 删除防疫用品库存档案
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:antiepidemicarchives:remove')")
    @Log(title = "防疫用品库存档案", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bdglAntiepidemicArchivesService.deleteBdglAntiepidemicArchivesByIds(ids));
    }
}
