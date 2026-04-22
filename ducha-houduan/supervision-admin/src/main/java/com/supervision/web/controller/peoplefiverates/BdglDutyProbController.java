package com.supervision.web.controller.peoplefiverates;

import java.util.List;

import com.supervision.common.annotation.Excel;
import com.supervision.peopleManage.domain.BdglPeople;
import com.supervision.peopleManage.service.IBdglPeopleService;
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
import com.supervision.peoplefiverates.domain.BdglDutyProb;
import com.supervision.peoplefiverates.service.IBdglDutyProbService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 人员五率Controller
 * 
 * @author supervision
 * @date 2022-03-19
 */
@RestController
@RequestMapping("/peoplefiverates/probinfo")
public class BdglDutyProbController extends BaseController
{
    @Autowired
    private IBdglDutyProbService bdglDutyProbService;
    @Autowired
    private IBdglPeopleService bdglPeopleService;

    /**
     * 查询人员五率列表
     */
   // @PreAuthorize("@ss.hasPermi('peoplefiverates:probinfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglDutyProb bdglDutyProb)
    {

        // 获取实际人数
        BdglPeople bp1 = new BdglPeople();
        List<BdglPeople> bdglPeople1 = bdglPeopleService.selectBdglPeopleList(bp1);

        //获取在位人数
        BdglPeople bp2 = new BdglPeople();
        bp2.setReign(0);
        List<BdglPeople> bdglPeople2 = bdglPeopleService.selectBdglPeopleList(bp2);

        //获取对口人数
        BdglPeople bp3 = new BdglPeople();
        bp3.setIsBianzhi("Y");
        List<BdglPeople> bdglPeople3 = bdglPeopleService.selectBdglPeopleList(bp3);

        //出动人数
        BdglPeople bp4 = new BdglPeople();
        bp4.setReign(5);
        List<BdglPeople> bdglPeople4 = bdglPeopleService.selectBdglPeopleList(bp4);

        //startPage();
        List<BdglDutyProb> list = bdglDutyProbService.selectBdglDutyProbList(bdglDutyProb);
        for (BdglDutyProb dutyProb : list) {
            dutyProb.setSjrs(bdglPeople1.size()); //实际
            dutyProb.setZwrs(bdglPeople2.size()); //在位
            dutyProb.setDkrs(bdglPeople3.size());// 对口
            dutyProb.setCdrs(bdglPeople4.size()); //出动
        }
        return getDataTable(list);
    }

    /**
     * 导出人员五率列表
     */
    @PreAuthorize("@ss.hasPermi('peoplefiverates:probinfo:export')")
    @Log(title = "人员五率", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglDutyProb bdglDutyProb)
    {
        List<BdglDutyProb> list = bdglDutyProbService.selectBdglDutyProbList(bdglDutyProb);
        ExcelUtil<BdglDutyProb> util = new ExcelUtil<BdglDutyProb>(BdglDutyProb.class);
        return util.exportExcel(list, "人员五率数据");
    }

    /**
     * 获取人员五率详细信息
     */
    @PreAuthorize("@ss.hasPermi('peoplefiverates:probinfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bdglDutyProbService.selectBdglDutyProbById(id));
    }

    /**
     * 新增人员五率
     */
    @Log(title = "人员五率", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglDutyProb bdglDutyProb)
    {
        BdglDutyProb bp = new BdglDutyProb();
        List<BdglDutyProb> list = bdglDutyProbService.selectBdglDutyProbList(bp);
        if(list.size() > 0){
            for (BdglDutyProb dutyProb : list) {
                bdglDutyProbService.deleteBdglDutyProbById(dutyProb.getId());
            }
        }
        return toAjax(bdglDutyProbService.insertBdglDutyProb(bdglDutyProb));
    }

    /**
     * 修改人员五率
     */
    @PreAuthorize("@ss.hasPermi('peoplefiverates:probinfo:edit')")
    @Log(title = "人员五率", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglDutyProb bdglDutyProb)
    {
        return toAjax(bdglDutyProbService.updateBdglDutyProb(bdglDutyProb));
    }

    /**
     * 删除人员五率
     */
    @PreAuthorize("@ss.hasPermi('peoplefiverates:probinfo:remove')")
    @Log(title = "人员五率", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglDutyProbService.deleteBdglDutyProbByIds(ids));
    }
}
