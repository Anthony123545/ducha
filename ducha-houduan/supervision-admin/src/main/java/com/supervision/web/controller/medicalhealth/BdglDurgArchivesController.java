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
import com.supervision.medicalhealth.domain.BdglDurgArchives;
import com.supervision.medicalhealth.service.IBdglDurgArchivesService;
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


/**
 * 药品库存档案Controller
 * 
 * @author hr
 * @date 2022-02-12
 */
@RestController
@RequestMapping("/medicalhealth/bdgldurgarchives")
public class BdglDurgArchivesController extends BaseController
{
    @Autowired
    private IBdglDurgArchivesService bdglDurgArchivesService;
    @Autowired
    private ISysUserService userService;

    /**
     * 查询药品库存档案列表
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:bdgldurgarchives:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglDurgArchives bdglDurgArchives)
    {
        startPage();
        List<BdglDurgArchives> list = bdglDurgArchivesService.selectBdglDurgArchivesList(bdglDurgArchives);
        return getDataTable(list);
    }

    /**
     * 导出药品库存档案列表
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:bdgldurgarchives:export')")
    @Log(title = "药品库存档案", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglDurgArchives bdglDurgArchives)
    {
        List<BdglDurgArchives> list = bdglDurgArchivesService.selectBdglDurgArchivesList(bdglDurgArchives);
        ExcelUtil<BdglDurgArchives> util = new ExcelUtil<BdglDurgArchives>(BdglDurgArchives.class);
        return util.exportExcel(list, "药品库存档案数据");
    }

    /**
     * 获取药品库存档案详细信息
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:bdgldurgarchives:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(bdglDurgArchivesService.selectBdglDurgArchivesById(id));
    }

    /**
     * 新增药品库存档案
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:bdgldurgarchives:add')")
    @Log(title = "药品库存档案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglDurgArchives bdglDurgArchives)
    {
        LoginUser loginUser = getLoginUser();
        bdglDurgArchives.setAdminId(loginUser.getUserId());
        SysUser sysUser = userService.selectUserById(loginUser.getUserId());
        bdglDurgArchives.setUnitName(sysUser.getNickName());
        return toAjax(bdglDurgArchivesService.insertBdglDurgArchives(bdglDurgArchives));
    }

    /**
     * 修改药品库存档案
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:bdgldurgarchives:edit')")
    @Log(title = "药品库存档案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglDurgArchives bdglDurgArchives)
    {
        return toAjax(bdglDurgArchivesService.updateBdglDurgArchives(bdglDurgArchives));
    }

    /**
     * 删除药品库存档案
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:bdgldurgarchives:remove')")
    @Log(title = "药品库存档案", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bdglDurgArchivesService.deleteBdglDurgArchivesByIds(ids));
    }
}
