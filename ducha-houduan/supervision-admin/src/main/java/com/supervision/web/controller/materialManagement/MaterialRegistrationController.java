package com.supervision.web.controller.materialManagement;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.supervision.common.core.domain.entity.SysDictData;
import com.supervision.common.core.domain.entity.SysUser;
import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.common.utils.StringUtils;
import com.supervision.materialManagement.domain.MaterialArchives;
import com.supervision.materialManagement.service.IMaterialArchivesService;
import com.supervision.system.service.ISysDictDataService;
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
import com.supervision.materialManagement.domain.MaterialRegistration;
import com.supervision.materialManagement.service.IMaterialRegistrationService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 物资入库登记Controller
 * 
 * @author supervision
 * @date 2022-03-08
 */
@RestController
@RequestMapping("/materialManagement/materialRegistration")
public class  MaterialRegistrationController extends BaseController
{
    @Autowired
    private IMaterialRegistrationService materialRegistrationService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private IMaterialArchivesService materialArchivesService;

    /**
     * 查询物资入库登记列表
     */
    @PreAuthorize("@ss.hasPermi('materialManagement:materialRegistration:list')")
    @GetMapping("/list")
    public TableDataInfo list(MaterialRegistration materialRegistration)
    {
        startPage();
        List<MaterialRegistration> list = materialRegistrationService.selectMaterialRegistrationList(materialRegistration);
        return getDataTable(list);
    }

    @GetMapping("/getlist")
    public TableDataInfo getlist(MaterialArchives materialRegistration)
    {
        startPage();
        List<MaterialArchives> list = materialArchivesService.selectMaterialArchivesList(materialRegistration);
        String name=null;
        for (MaterialArchives registration : list) {
            name=registration.getName()+"-"+registration.getMaterialXh()+"-"+registration.getMaterialSpecification();
            registration.setNames(name);
        }
        return getDataTable(list);
    }

    /**
     * 导出物资入库登记列表
     */
    @PreAuthorize("@ss.hasPermi('materialManagement:materialRegistration:export')")
    @Log(title = "物资入库登记", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(MaterialRegistration materialRegistration)
    {
        List<MaterialRegistration> list = materialRegistrationService.selectMaterialRegistrationList(materialRegistration);
        ExcelUtil<MaterialRegistration> util = new ExcelUtil<MaterialRegistration>(MaterialRegistration.class);
        return util.exportExcel(list, "物资入库登记数据");
    }

    /**
     * 获取物资入库登记详细信息
     */
    @PreAuthorize("@ss.hasPermi('materialManagement:materialRegistration:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(materialRegistrationService.selectMaterialRegistrationById(id));
    }

    /**
     * 新增物资入库登记
     */
    @PreAuthorize("@ss.hasPermi('materialManagement:materialRegistration:add')")
    @Log(title = "物资入库登记", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MaterialRegistration materialRegistration)
    {
        LoginUser loginUser = getLoginUser();
        SysUser sysUser = userService.selectUserById(loginUser.getUserId());
        materialRegistration.setCreateName(sysUser.getNickName());
        materialRegistration.setCreateid(loginUser.getUserId());
        if (StringUtils.isNotEmpty(materialRegistration.getBarcode())){
            MaterialRegistration registrationId = materialRegistrationService.selectMaterialRegistrationByBarCode(materialRegistration.getBarcode());
            if (!ObjectUtils.isEmpty(registrationId)){
                return AjaxResult.error("当前物品已存在");
            }
        }
        return toAjax(materialRegistrationService.insertMaterialRegistration(materialRegistration));
    }

    /**
     * 修改物资入库登记
     */
    @PreAuthorize("@ss.hasPermi('materialManagement:materialRegistration:edit')")
    @Log(title = "物资入库登记", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MaterialRegistration materialRegistration)
    {
        return toAjax(materialRegistrationService.updateMaterialRegistration(materialRegistration));
    }

    /**
     * 删除物资入库登记
     */
    @PreAuthorize("@ss.hasPermi('materialManagement:materialRegistration:remove')")
    @Log(title = "物资入库登记", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(materialRegistrationService.deleteMaterialRegistrationByIds(ids));
    }


    @Autowired
    private ISysDictDataService dictDataService;
    @GetMapping("/storageStatistics")
    public AjaxResult storageStatistics(){

        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictType("storage_room");
        List<SysDictData> sysDictData1 = dictDataService.selectDictDataList(sysDictData);
        HashMap<String, Object> map = new HashMap<>();
        map.put("number",sysDictData1.size());
        map.put("date",sysDictData1);
        return AjaxResult.success(map);
    }

    @GetMapping("/echarts/storage/list")
    public AjaxResult earchartsStorageList(){

        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictType("storage_room");
        List<SysDictData> sysDictData1 = dictDataService.selectDictDataList(sysDictData);
        HashMap<String, Object> map = new HashMap<>();

        List<Map<String,Object>> result = new LinkedList<>();
        for (SysDictData dictData : sysDictData1){
            Map<String,Object> warehouseMap = new HashMap<>();
            String warehouse = dictData.getDictLabel();
            List<Map<String,Object>> list = materialRegistrationService.selectMaterialStorageByWarehouse(warehouse);
            warehouseMap.put("warehouse",warehouse);
            warehouseMap.put("data",list);
            result.add(warehouseMap);
        }
        map.put("data",result);
        map.put("number",sysDictData1.size());
        return AjaxResult.success(map);
    }
}
