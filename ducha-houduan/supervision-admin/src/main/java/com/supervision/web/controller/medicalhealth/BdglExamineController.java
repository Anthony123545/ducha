package com.supervision.web.controller.medicalhealth;

import java.util.Date;
import java.util.List;

import com.supervision.common.annotation.Log;
import com.supervision.common.core.controller.BaseController;
import com.supervision.common.core.domain.AjaxResult;
import com.supervision.common.core.page.TableDataInfo;
import com.supervision.common.enums.BusinessType;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.medicalhealth.domain.BdglExamineDetails;
import com.supervision.medicalhealth.service.IBdglExamineDetailsService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.supervision.medicalhealth.domain.BdglExamine;
import com.supervision.medicalhealth.service.IBdglExamineService;


/**
 * 人员体检信息Controller
 * 
 * @author hr
 * @date 2022-02-14
 */
@Transactional
@RestController
@RequestMapping("/medicalhealth/examine")
public class BdglExamineController extends BaseController
{
    @Autowired
    private IBdglExamineService bdglExamineService;

    @Autowired
    private IBdglExamineDetailsService bdglExamineDetailsService;

    /**
     * 查询人员体检信息列表
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:examine:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglExamine bdglExamine)
    {
        startPage();
        List<BdglExamine> list = bdglExamineService.selectBdglExamineList(bdglExamine);
        return getDataTable(list);
    }

    /**
     * 导出人员体检信息列表
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:examine:export')")
    @Log(title = "人员体检信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglExamine bdglExamine)
    {
        List<BdglExamine> list = bdglExamineService.selectBdglExamineList(bdglExamine);
        ExcelUtil<BdglExamine> util = new ExcelUtil<BdglExamine>(BdglExamine.class);
        return util.exportExcel(list, "人员体检信息数据");
    }

    /**
     * 获取人员体检信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:examine:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        BdglExamine bdglExamine = bdglExamineService.selectBdglExamineById(id);
        BdglExamineDetails examineDetail=new BdglExamineDetails();
        examineDetail.setExamineId(id);
        List<BdglExamineDetails> bdglExamineDetails = bdglExamineDetailsService.selectBdglExamineDetailsList(examineDetail);
        bdglExamine.setExamineDetails(bdglExamineDetails);
        return AjaxResult.success(bdglExamine);
    }

    /**
     * 新增人员体检信息
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:examine:add')")
    @Log(title = "人员体检信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglExamine bdglExamine)
    {
        int i = bdglExamineService.insertBdglExamine(bdglExamine);
        List<BdglExamineDetails> examineDetails = bdglExamine.getExamineDetails();

        return toAjax(i);
    }

    /**
     * 修改人员体检信息
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:examine:edit')")
    @Log(title = "人员体检信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglExamine bdglExamine)
    {
        int i = bdglExamineService.updateBdglExamine(bdglExamine);
        List<BdglExamineDetails> examineDetails = bdglExamine.getExamineDetails();
        if(examineDetails!=null&&examineDetails.size()>0){

            for (BdglExamineDetails examineDetail : examineDetails) {
                if(examineDetail!=null){

                    if(examineDetail.getId()!=null){
                        bdglExamineDetailsService.updateBdglExamineDetails(examineDetail);
                    }else{
                        examineDetail.setExamineId(bdglExamine.getId());
                        examineDetail.setCreatetime(new Date());
                        bdglExamineDetailsService.insertBdglExamineDetails(examineDetail);
                    }
                }
            }
        }
        return toAjax(i);
    }

    /**
     * 删除人员体检信息
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:examine:remove')")
    @Log(title = "人员体检信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglExamineService.deleteBdglExamineByIds(ids));
    }
}
