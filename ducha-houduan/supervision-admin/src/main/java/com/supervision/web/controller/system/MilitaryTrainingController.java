package com.supervision.web.controller.system;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.supervision.system.domain.MilitaryTraining;
import com.supervision.system.service.IMilitaryTrainingService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 态势军事训练月统计情况Controller
 * 
 * @author ruoyi
 * @date 2022-05-21
 */
@RestController
@RequestMapping("/system/training")
public class MilitaryTrainingController extends BaseController
{
    @Autowired
    private IMilitaryTrainingService militaryTrainingService;

    /**
     * 查询态势军事训练月统计情况列表
     */
    //@PreAuthorize("@ss.hasPermi('system:training:list')")
    @GetMapping("/list")
    public AjaxResult list(MilitaryTraining militaryTraining)
    {
        MilitaryTraining list = militaryTrainingService.selectMilitaryTrainingList(militaryTraining);
        return AjaxResult.success(list);
    }

    /**
     * 导出态势军事训练月统计情况列表
     */
    @PreAuthorize("@ss.hasPermi('system:training:export')")
    @Log(title = "态势军事训练月统计情况", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MilitaryTraining militaryTraining)
    {
        MilitaryTraining list = militaryTrainingService.selectMilitaryTrainingList(militaryTraining);
        ExcelUtil<MilitaryTraining> util = new ExcelUtil<MilitaryTraining>(MilitaryTraining.class);
        //util.exportExcel(response, list, "态势军事训练月统计情况数据");
    }

    /**
     * 获取态势军事训练月统计情况详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:training:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(militaryTrainingService.selectMilitaryTrainingById(id));
    }

    /**
     * 新增态势军事训练月统计情况
     */
    @PreAuthorize("@ss.hasPermi('system:training:add')")
    @Log(title = "态势军事训练月统计情况", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MilitaryTraining militaryTraining)
    {
        militaryTraining.setCreatetime(new Date());
        return toAjax(militaryTrainingService.insertMilitaryTraining(militaryTraining));
    }

    /**
     * 修改态势军事训练月统计情况
     */
    @PreAuthorize("@ss.hasPermi('system:training:edit')")
    @Log(title = "态势军事训练月统计情况", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MilitaryTraining militaryTraining)
    {
        return toAjax(militaryTrainingService.updateMilitaryTraining(militaryTraining));
    }

    /**
     * 删除态势军事训练月统计情况
     */
    @PreAuthorize("@ss.hasPermi('system:training:remove')")
    @Log(title = "态势军事训练月统计情况", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(militaryTrainingService.deleteMilitaryTrainingByIds(ids));
    }
}