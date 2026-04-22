package com.supervision.web.controller.medicalhealth;

import java.text.SimpleDateFormat;
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
import com.supervision.medicalhealth.domain.BdglDurgPutout;
import com.supervision.medicalhealth.service.IBdglDurgPutinService;
import com.supervision.medicalhealth.service.IBdglDurgPutoutService;
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
 * 药品出库登记Controller
 *
 * @author hr
 * @date 2022-02-12
 */
@RestController
@RequestMapping("/medicalhealth/bdgldurgputout")
public class BdglDurgPutoutController extends BaseController
{
    @Autowired
    private IBdglDurgPutoutService bdglDurgPutoutService;
    @Autowired
    private IBdglDurgPutinService durgPutinService;

    /**
     * 查询药品出库登记列表
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:bdgldurgputout:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglDurgPutout bdglDurgPutout)
    {
        startPage();
        List<BdglDurgPutout> list = bdglDurgPutoutService.selectBdglDurgPutoutList(bdglDurgPutout);
        return getDataTable(list);
    }
    @GetMapping("/getList")
    public AjaxResult getList(){
        List<BdglDurgPutin> list = durgPutinService.selectBdglDurgPutinList(new BdglDurgPutin());
        Map<String,Object> map=null;
        ArrayList<BdglDurgPutin> bdglDurgPutins = new ArrayList<>();
        for (BdglDurgPutin bdglDurgArchives : list) {
            if(bdglDurgArchives.getSurplus()!=null){

                map=new HashMap<>();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String format="";
                if (bdglDurgArchives.getCreateTime()!=null){
                    format = "-" + simpleDateFormat.format(bdglDurgArchives.getCreateTime());
                }
                //品牌  厂家 规格 库房 单位
                String yaoPingName=bdglDurgArchives.getDurgName()+"-"+bdglDurgArchives.getManufacturer()+"-"+bdglDurgArchives.getSpecifications()+"-"+bdglDurgArchives.getStoreHouse()+"-"+bdglDurgArchives.getUnitName()+format;
                bdglDurgArchives.setYaoPinName(yaoPingName);
                bdglDurgPutins.add(bdglDurgArchives);
            }
        }
        return AjaxResult.success(bdglDurgPutins);
    }

    /**
     * 导出药品出库登记列表
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:bdgldurgputout:export')")
    @Log(title = "药品出库登记", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglDurgPutout bdglDurgPutout)
    {
        List<BdglDurgPutout> list = bdglDurgPutoutService.selectBdglDurgPutoutList(bdglDurgPutout);
        ExcelUtil<BdglDurgPutout> util = new ExcelUtil<BdglDurgPutout>(BdglDurgPutout.class);
        return util.exportExcel(list, "药品出库登记数据");
    }

    /**
     * 获取药品出库登记详细信息
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:bdgldurgputout:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(bdglDurgPutoutService.selectBdglDurgPutoutById(id));
    }

    /**
     * 新增药品出库登记
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:bdgldurgputout:add')")
    @Log(title = "药品出库登记", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglDurgPutout bdglDurgPutout)
    {
        return toAjax(bdglDurgPutoutService.insertBdglDurgPutout(bdglDurgPutout));
    }

    /**
     * 修改药品出库登记
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:bdgldurgputout:edit')")
    @Log(title = "药品出库登记", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglDurgPutout bdglDurgPutout)
    {
        return toAjax(bdglDurgPutoutService.updateBdglDurgPutout(bdglDurgPutout));
    }

    /**
     * 删除药品出库登记
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:bdgldurgputout:remove')")
    @Log(title = "药品出库登记", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bdglDurgPutoutService.deleteBdglDurgPutoutByIds(ids));
    }
}
