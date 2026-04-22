package com.supervision.web.controller.combatduty;

import java.util.ArrayList;
import java.util.List;

import com.supervision.combatduty.domain.BdglTaskdutyFu;
import com.supervision.combatduty.service.IBdglTaskdutyFuService;
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
import com.supervision.combatduty.domain.BdglTaskduty;
import com.supervision.combatduty.service.IBdglTaskdutyService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 执行任务Controller
 * 
 * @author supervision
 * @date 2022-03-11
 */
@RestController
@RequestMapping("/combatduty/taskdutyinfo")
public class BdglTaskdutyController extends BaseController
{
    @Autowired
    private IBdglTaskdutyService bdglTaskdutyService;

    @Autowired
    private IBdglTaskdutyFuService taskdutyFuService;
    /**
     * 查询执行任务列表
     */
   // @PreAuthorize("@ss.hasPermi('combatduty:taskdutyinfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglTaskduty bdglTaskduty)
    {

        startPage();
        List<Integer> list1 =  null;
        List<BdglTaskduty> list = bdglTaskdutyService.selectBdglTaskdutyList(bdglTaskduty);
        for (BdglTaskduty taskduty : list) {
            list1 = new ArrayList<>();
            String participantsId = taskduty.getParticipantsId();
            taskduty.setParticipantsIds(list1);
        }
        return getDataTable(list);
    }

    /**
     * 导出执行任务列表
     */
    @PreAuthorize("@ss.hasPermi('combatduty:taskdutyinfo:export')")
    @Log(title = "执行任务", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglTaskduty bdglTaskduty)
    {
        List<BdglTaskduty> list = bdglTaskdutyService.selectBdglTaskdutyList(bdglTaskduty);
        ExcelUtil<BdglTaskduty> util = new ExcelUtil<BdglTaskduty>(BdglTaskduty.class);
        return util.exportExcel(list, "执行任务数据");
    }

    /**
     * 获取执行任务详细信息
     */
    @PreAuthorize("@ss.hasPermi('combatduty:taskdutyinfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        BdglTaskduty bdglTaskduty = bdglTaskdutyService.selectBdglTaskdutyById(id);
        List<Integer> list1 = new ArrayList<>();
        String participantsId = bdglTaskduty.getParticipantsId();
        bdglTaskduty.setParticipantsIds(list1);
        BdglTaskdutyFu fus=new BdglTaskdutyFu();
        fus.setBdglTaskdutyId(id);
        List<BdglTaskdutyFu> bdglTaskdutyFus = taskdutyFuService.selectBdglTaskdutyFuList(fus);
        bdglTaskduty.setTaskdutyFus(bdglTaskdutyFus);
        return AjaxResult.success(bdglTaskduty);
    }

    /**
     * 新增执行任务
     */
    @PreAuthorize("@ss.hasPermi('combatduty:taskdutyinfo:add')")
    @Log(title = "执行任务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglTaskduty bdglTaskduty)
    {
        int i = bdglTaskdutyService.insertBdglTaskduty(bdglTaskduty);
        List<BdglTaskdutyFu> taskdutyFus = bdglTaskduty.getTaskdutyFus();
        if(taskdutyFus!=null){

            if(taskdutyFus.size()>0){
                for (BdglTaskdutyFu fus : taskdutyFus) {
                    if(fus.getFilename()!=null){
                        fus.setBdglTaskdutyId(bdglTaskduty.getId());
                        taskdutyFuService.insertBdglTaskdutyFu(fus);
                    }
                }
            }
        }
        return toAjax(i);
    }

    /**
     * 修改执行任务
     */
    @PreAuthorize("@ss.hasPermi('combatduty:taskdutyinfo:edit')")
    @Log(title = "执行任务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglTaskduty bdglTaskduty)
    {
        List<Integer> participantsIds = bdglTaskduty.getParticipantsIds();
        List<BdglTaskdutyFu> taskdutyFus = bdglTaskduty.getTaskdutyFus();
        BdglTaskdutyFu bdglTaskdutyFu = new BdglTaskdutyFu();
        bdglTaskdutyFu.setBdglTaskdutyId(bdglTaskduty.getId());
        int i=taskdutyFuService.deleteBdglTaskdutyFu(bdglTaskdutyFu);
        if(taskdutyFus.size()>0){
            for (BdglTaskdutyFu fus : taskdutyFus) {
                if(fus!=null){
                    fus.setBdglTaskdutyId(bdglTaskduty.getId());
                    taskdutyFuService.insertBdglTaskdutyFu(fus);
                }
            }
        }
        return toAjax(bdglTaskdutyService.updateBdglTaskduty(bdglTaskduty));
    }

    /**
     * 删除执行任务
     */
    @PreAuthorize("@ss.hasPermi('combatduty:taskdutyinfo:remove')")
    @Log(title = "执行任务", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bdglTaskdutyService.deleteBdglTaskdutyByIds(ids));
    }
}
