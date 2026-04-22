package com.supervision.web.controller.barracksManagement;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.supervision.barracksManagement.domain.SingleDormitory;
import com.supervision.barracksManagement.service.ISingleDormitoryService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

import javax.annotation.Resource;

/**
 * 单身干部宿舍Controller
 * 
 * @author gaoqiang
 * @date 2022-11-19
 */
@RestController
@RequestMapping("/single/dormitory")
public class SingleDormitoryController extends BaseController {
    
    @Resource
    private ISingleDormitoryService singleDormitoryService;

    /**
     * 查询单身干部宿舍列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SingleDormitory singleDormitory)
    {
        startPage();
        List<SingleDormitory> list = singleDormitoryService.selectSingleDormitoryList(singleDormitory);
        return getDataTable(list);
    }

    /**
     * 导出单身干部宿舍列表
     */
    @Log(title = "单身干部宿舍", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SingleDormitory singleDormitory) {
        List<SingleDormitory> list = singleDormitoryService.selectSingleDormitoryList(singleDormitory);
        ExcelUtil<SingleDormitory> util = new ExcelUtil<>(SingleDormitory.class);
        return util.exportExcel(list, "单身干部宿舍数据");
    }

    /**
     * 获取单身干部宿舍详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(singleDormitoryService.selectSingleDormitoryById(id));
    }

    /**
     * 新增单身干部宿舍
     */
    @Log(title = "单身干部宿舍", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SingleDormitory singleDormitory)
    {
        singleDormitory.setCreateBy(getUsername());
        return toAjax(singleDormitoryService.insertSingleDormitory(singleDormitory));
    }

    /**
     * 修改单身干部宿舍
     */
    @Log(title = "单身干部宿舍", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SingleDormitory singleDormitory)
    {
        singleDormitory.setUpdateBy(getUsername());
        return toAjax(singleDormitoryService.updateSingleDormitory(singleDormitory));
    }

    /**
     * 删除单身干部宿舍
     */
    @Log(title = "单身干部宿舍", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(singleDormitoryService.deleteSingleDormitoryByIds(ids));
    }
}
