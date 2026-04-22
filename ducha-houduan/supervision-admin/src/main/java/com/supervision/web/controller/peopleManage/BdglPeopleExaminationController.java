package com.supervision.web.controller.peopleManage;

import java.util.List;
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
import com.supervision.peopleManage.domain.BdglPeopleExamination;
import com.supervision.peopleManage.service.IBdglPeopleExaminationService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 人员政审Controller
 * 
 * @author supervision
 * @date 2022-02-23
 */
@RestController
@RequestMapping("/peopleManage/examination")
public class BdglPeopleExaminationController extends BaseController
{
    @Autowired
    private IBdglPeopleExaminationService bdglPeopleExaminationService;

    /**
     * 查询人员政审列表
     */
    @PreAuthorize("@ss.hasPermi('peopleManage:examination:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglPeopleExamination bdglPeopleExamination)
    {
        startPage();
        List<BdglPeopleExamination> list = bdglPeopleExaminationService.selectBdglPeopleExaminationList(bdglPeopleExamination);
        return getDataTable(list);
    }

    /**
     * 导出人员政审列表
     */
    @PreAuthorize("@ss.hasPermi('peopleManage:examination:export')")
    @Log(title = "人员政审", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglPeopleExamination bdglPeopleExamination)
    {
        List<BdglPeopleExamination> list = bdglPeopleExaminationService.selectBdglPeopleExaminationList(bdglPeopleExamination);
        ExcelUtil<BdglPeopleExamination> util = new ExcelUtil<BdglPeopleExamination>(BdglPeopleExamination.class);
        return util.exportExcel(list, "人员政审数据");
    }

    /**
     * 获取人员政审详细信息
     */
    @PreAuthorize("@ss.hasPermi('peopleManage:examination:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bdglPeopleExaminationService.selectBdglPeopleExaminationById(id));
    }

    /**
     * 新增人员政审
     */
    @PreAuthorize("@ss.hasPermi('peopleManage:examination:add')")
    @Log(title = "人员政审", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglPeopleExamination bdglPeopleExamination)
    {
        return toAjax(bdglPeopleExaminationService.insertBdglPeopleExamination(bdglPeopleExamination));
    }

    /**
     * 修改人员政审
     */
    @PreAuthorize("@ss.hasPermi('peopleManage:examination:edit')")
    @Log(title = "人员政审", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglPeopleExamination bdglPeopleExamination)
    {
        return toAjax(bdglPeopleExaminationService.updateBdglPeopleExamination(bdglPeopleExamination));
    }

    /**
     * 删除人员政审
     */
    @PreAuthorize("@ss.hasPermi('peopleManage:examination:remove')")
    @Log(title = "人员政审", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglPeopleExaminationService.deleteBdglPeopleExaminationByIds(ids));
    }
}
