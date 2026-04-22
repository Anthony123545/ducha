package com.supervision.web.controller.materialManagement;

import java.util.List;

import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.materialManagement.domain.MaterialArchives;
import com.supervision.materialManagement.domain.MaterialRegistration;
import com.supervision.materialManagement.service.IMaterialRegistrationService;
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
import com.supervision.common.annotation.Log;
import com.supervision.common.core.controller.BaseController;
import com.supervision.common.core.domain.AjaxResult;
import com.supervision.common.enums.BusinessType;
import com.supervision.materialManagement.domain.MaterialDistribution;
import com.supervision.materialManagement.service.IMaterialDistributionService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 物资发放Controller
 * @author supervision
 * @date 2022-03-08
 */
@RestController
@RequestMapping("/materialManagement/distribution")
public class MaterialDistributionController extends BaseController
{
    @Autowired
    private IMaterialDistributionService materialDistributionService;
    @Autowired
    private IMaterialRegistrationService materialRegistrationService;
    @Autowired
    private ISysUserService userService;
    /**
     * 查询物资发放列表
     */
    @PreAuthorize("@ss.hasPermi('materialManagement:distribution:list')")
    @GetMapping("/list")
    public TableDataInfo list(MaterialDistribution materialDistribution)
    {
        LoginUser loginUser = getLoginUser();
        List<String> strings = userService.selectUserRoleKey(loginUser.getUserId());
        boolean role =false;
        for (String string : strings) {
            if(string.equals("jieshou")){
                role=true;
                break;
            }
        }
        startPage();
        List<MaterialDistribution> list = materialDistributionService.selectMaterialDistributionList(materialDistribution);
        for (MaterialDistribution distribution : list) {
            //判断是否有接收权限
            if(loginUser.getDeptId().toString().equals(distribution.getOutUnitId().toString())&&role==true&&!distribution.getOutPeopleId().toString().equals("1")){
                distribution.setCommon("1");
            }
        }
        return getDataTable(list);
    }

    @GetMapping("/getlist")
    public TableDataInfo getlist(MaterialRegistration materialRegistration)
    {
        startPage();
        List<MaterialRegistration> list = materialRegistrationService.selectMaterialRegistrationList(materialRegistration);
        String name=null;
        for (MaterialRegistration registration : list) {
            name=registration.getName()+"-"+registration.getMaterialXh()+"-"+registration.getMaterialSpecification();
            registration.setNames(name);
        }
        return getDataTable(list);
    }
    /**
     * 导出物资发放列表
     */
    @PreAuthorize("@ss.hasPermi('materialManagement:distribution:export')")
    @Log(title = "物资发放", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(MaterialDistribution materialDistribution)
    {
        List<MaterialDistribution> list = materialDistributionService.selectMaterialDistributionList(materialDistribution);
        ExcelUtil<MaterialDistribution> util = new ExcelUtil<MaterialDistribution>(MaterialDistribution.class);
        return util.exportExcel(list, "物资发放数据");
    }

    /**
     * 获取物资发放详细信息
     */
    @PreAuthorize("@ss.hasPermi('materialManagement:distribution:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(materialDistributionService.selectMaterialDistributionById(id));
    }

    /**
     * 新增物资发放
     */
    @PreAuthorize("@ss.hasPermi('materialManagement:distribution:add')")
    @Log(title = "物资发放", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MaterialDistribution materialDistribution)
    {
        if(materialDistribution.getOutUnitId()==null || "".equals(materialDistribution.getOutUnitId())){
            materialDistribution.setOutUnitId((long) 0);
        }
        return toAjax(materialDistributionService.insertMaterialDistribution(materialDistribution));
    }

    /**
     * 修改物资发放
     */
    @PreAuthorize("@ss.hasPermi('materialManagement:distribution:edit')")
    @Log(title = "物资发放", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MaterialDistribution materialDistribution)
    {
        return toAjax(materialDistributionService.updateMaterialDistribution(materialDistribution));
    }

    /**
     * 删除物资发放
     */
    @PreAuthorize("@ss.hasPermi('materialManagement:distribution:remove')")
    @Log(title = "物资发放", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(materialDistributionService.deleteMaterialDistributionByIds(ids));
    }
    @PutMapping("/jieshou")
    public AjaxResult jieshou(@RequestBody MaterialDistribution materialDistribution){
        return AjaxResult.success(materialDistributionService.jieshou( materialDistribution));
    }
}
