package com.supervision.web.controller.combatduty;

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
import com.supervision.combatduty.domain.BdglTeamdutyFu;
import com.supervision.combatduty.service.IBdglTeamdutyFuService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 大队值班分表Controller
 * 
 * @author supervision
 * @date 2022-05-06
 */
@RestController
@RequestMapping("/combatduty/teamdutyFu")
public class BdglTeamdutyFuController extends BaseController
{
    @Autowired
    private IBdglTeamdutyFuService bdglTeamdutyFuService;

    /**
     * 查询大队值班分表列表
     */
    @PreAuthorize("@ss.hasPermi('combatduty:teamdutyFu:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglTeamdutyFu bdglTeamdutyFu)
    {
        startPage();
        List<BdglTeamdutyFu> list = bdglTeamdutyFuService.selectBdglTeamdutyFuList(bdglTeamdutyFu);
        return getDataTable(list);
    }

    /**
     * 导出大队值班分表列表
     */
    @PreAuthorize("@ss.hasPermi('combatduty:teamdutyFu:export')")
    @Log(title = "大队值班分表", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglTeamdutyFu bdglTeamdutyFu)
    {
        List<BdglTeamdutyFu> list = bdglTeamdutyFuService.selectBdglTeamdutyFuList(bdglTeamdutyFu);
        ExcelUtil<BdglTeamdutyFu> util = new ExcelUtil<BdglTeamdutyFu>(BdglTeamdutyFu.class);
        return util.exportExcel(list, "大队值班分表数据");
    }

    /**
     * 获取大队值班分表详细信息
     */
    @PreAuthorize("@ss.hasPermi('combatduty:teamdutyFu:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(bdglTeamdutyFuService.selectBdglTeamdutyFuById(id));
    }

    /**
     * 新增大队值班分表
     */
    @PreAuthorize("@ss.hasPermi('combatduty:teamdutyFu:add')")
    @Log(title = "大队值班分表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglTeamdutyFu bdglTeamdutyFu)
    {
        return toAjax(bdglTeamdutyFuService.insertBdglTeamdutyFu(bdglTeamdutyFu));
    }

    /**
     * 修改大队值班分表
     */
    @PreAuthorize("@ss.hasPermi('combatduty:teamdutyFu:edit')")
    @Log(title = "大队值班分表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglTeamdutyFu bdglTeamdutyFu)
    {
        return toAjax(bdglTeamdutyFuService.updateBdglTeamdutyFu(bdglTeamdutyFu));
    }

    /**
     * 删除大队值班分表
     */
    //@PreAuthorize("@ss.hasPermi('combatduty:teamdutyFu:remove')")
    @Log(title = "大队值班分表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bdglTeamdutyFuService.deleteBdglTeamdutyFuByIds(ids));
    }
}
