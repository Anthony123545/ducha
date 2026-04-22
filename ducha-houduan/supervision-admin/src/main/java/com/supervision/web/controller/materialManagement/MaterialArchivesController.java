package com.supervision.web.controller.materialManagement;

import java.util.List;

import com.supervision.common.core.domain.entity.SysUser;
import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.common.utils.StringUtils;
import com.supervision.materialManagement.domain.MaterialRegistration;
import com.supervision.materialManagement.service.IMaterialRegistrationService;
import com.supervision.system.service.ISysUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
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
import com.supervision.materialManagement.domain.MaterialArchives;
import com.supervision.materialManagement.service.IMaterialArchivesService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 物资档案Controller
 * 
 * @author supervision
 * @date 2022-03-08
 */
@RestController
@RequestMapping("/materialManagement/archives")
public class MaterialArchivesController extends BaseController
{
    @Autowired
    private IMaterialArchivesService materialArchivesService;
    @Autowired
    private ISysUserService userService;
    
    @Autowired
    private IMaterialRegistrationService materialRegistrationService;

    /**
     * 查询物资档案列表
     */
    @PreAuthorize("@ss.hasPermi('materialManagement:archives:list')")
    @GetMapping("/list")
    public TableDataInfo list(MaterialArchives materialArchives)
    {
        startPage();
        List<MaterialArchives> list = materialArchivesService.selectMaterialArchivesList(materialArchives);
        return getDataTable(list);
    }

    /**
     * 导出物资档案列表
     */
    @PreAuthorize("@ss.hasPermi('materialManagement:archives:export')")
    @Log(title = "物资档案", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(MaterialArchives materialArchives)
    {
        List<MaterialArchives> list = materialArchivesService.selectMaterialArchivesList(materialArchives);
        ExcelUtil<MaterialArchives> util = new ExcelUtil<MaterialArchives>(MaterialArchives.class);
        return util.exportExcel(list, "物资档案数据");
    }

    /**
     * 获取物资档案详细信息
     */
    @PreAuthorize("@ss.hasPermi('materialManagement:archives:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(materialArchivesService.selectMaterialArchivesById(id));
    }
    
    /**
     * 根据条形码编号查询数据
     */
    @GetMapping(value = "/getBarCode")
    public AjaxResult getBarCode(String barcode)
    {
        if(StringUtils.isNotEmpty(barcode)){
            MaterialArchives archives = materialArchivesService.selectMaterialArchivesByBarCode(barcode);
            if (!ObjectUtils.isEmpty(archives)){
                //查询库存ID
                MaterialRegistration registration = materialRegistrationService.selectMaterialRegistrationByBarCode(archives.getBarcode());
                if (!ObjectUtils.isEmpty(registration)){
                    archives.setRegistrationId(registration.getId());
                    archives.setPrice(registration.getPrice());
                }
                return AjaxResult.success(archives);
            }
            return AjaxResult.error("库存档案不存在，请新增档案");
        }
        return AjaxResult.error("参数为空");
    }

    /**
     * 新增物资档案
     */
    @PreAuthorize("@ss.hasPermi('materialManagement:archives:add')")
    @Log(title = "物资档案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MaterialArchives materialArchives)
    {
        LoginUser loginUser = getLoginUser();
        SysUser sysUser = userService.selectUserById(loginUser.getUserId());
        materialArchives.setCreateName(sysUser.getNickName());
        materialArchives.setCreateid(loginUser.getUserId());
        return toAjax(materialArchivesService.insertMaterialArchives(materialArchives));
    }

    /**
     * 修改物资档案
     */
    @PreAuthorize("@ss.hasPermi('materialManagement:archives:edit')")
    @Log(title = "物资档案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MaterialArchives materialArchives)
    {
        return toAjax(materialArchivesService.updateMaterialArchives(materialArchives));
    }

    /**
     * 删除物资档案
     */
    @PreAuthorize("@ss.hasPermi('materialManagement:archives:remove')")
    @Log(title = "物资档案", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(materialArchivesService.deleteMaterialArchivesByIds(ids));
    }
}
