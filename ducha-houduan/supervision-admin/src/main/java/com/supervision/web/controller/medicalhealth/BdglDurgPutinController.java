package com.supervision.web.controller.medicalhealth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.supervision.common.annotation.Log;
import com.supervision.common.core.controller.BaseController;
import com.supervision.common.core.domain.AjaxResult;
import com.supervision.common.core.page.TableDataInfo;
import com.supervision.common.enums.BusinessType;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.medicalhealth.domain.BdglDurgArchives;
import com.supervision.medicalhealth.domain.BdglDurgPutin;
import com.supervision.medicalhealth.service.IBdglDurgArchivesService;
import com.supervision.medicalhealth.service.IBdglDurgPutinService;
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


/**
 * 药品入库登记Controller
 * 
 * @author hr
 * @date 2022-02-12
 */
@RestController
@RequestMapping("/medicalhealth/bdgldurgputin")
public class BdglDurgPutinController extends BaseController
{
    @Autowired
    private IBdglDurgPutinService bdglDurgPutinService;
    @Autowired
    private IBdglDurgArchivesService bdglDurgArchivesService;
    /**
     * 查询药品入库登记列表
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:bdgldurgputin:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglDurgPutin bdglDurgPutin)
    {
        startPage();
        bdglDurgPutin.setExpiredstatus("0");
        List<BdglDurgPutin> list = bdglDurgPutinService.selectBdglDurgPutinList(bdglDurgPutin);
        return getDataTable(list);
    }
    /**获取药品列表*/
    @GetMapping("/getYaoPingList")
    public AjaxResult getYaoPingList( BdglDurgArchives bdglDurgArchives)
    {
        BdglDurgArchives yaoPingList = bdglDurgArchivesService.getYaoPingList(bdglDurgArchives);
        if(yaoPingList==null){
            return AjaxResult.success("");
        }
        return AjaxResult.success(yaoPingList);
    }

    /**
     * 导出药品入库登记列表
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:bdgldurgputin:export')")
    @Log(title = "药品入库登记", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglDurgPutin bdglDurgPutin)
    {
        List<BdglDurgPutin> list = bdglDurgPutinService.selectBdglDurgPutinList(bdglDurgPutin);
        ExcelUtil<BdglDurgPutin> util = new ExcelUtil<BdglDurgPutin>(BdglDurgPutin.class);
        return util.exportExcel(list, "药品入库登记数据");
    }

    /**
     * 获取药品入库登记详细信息
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:bdgldurgputin:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(bdglDurgPutinService.selectBdglDurgPutinById(id));
    }

    /**
     * 新增药品入库登记
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:bdgldurgputin:add')")
    @Log(title = "药品入库登记", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglDurgPutin bdglDurgPutin)
    {
        return toAjax(bdglDurgPutinService.insertBdglDurgPutin(bdglDurgPutin));
    }
    @PostMapping("/insert")
    public AjaxResult insert(@RequestBody BdglDurgPutin bdglDurgPutin)
    {
        return toAjax(bdglDurgPutinService.add(bdglDurgPutin));
    }

    /**
     * 修改药品入库登记
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:bdgldurgputin:edit')")
    @Log(title = "药品入库登记", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglDurgPutin bdglDurgPutin)
    {
        if(bdglDurgPutin.getPutIn()<bdglDurgPutin.getSurplus()){
            return AjaxResult.error("请输入正确的入库数量");
        }
        return toAjax(bdglDurgPutinService.updateBdglDurgPutin(bdglDurgPutin));
    }

    /**
     * 删除药品入库登记
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:bdgldurgputin:remove')")
    @Log(title = "药品入库登记", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bdglDurgPutinService.deleteBdglDurgPutinByIds(ids));
    }
}
