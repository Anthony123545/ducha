package com.supervision.web.controller.medicalhealth;

import java.text.SimpleDateFormat;
import java.util.*;

import com.supervision.common.annotation.Log;
import com.supervision.common.core.controller.BaseController;
import com.supervision.common.core.domain.AjaxResult;
import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.common.core.page.TableDataInfo;
import com.supervision.common.enums.BusinessType;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.medicalhealth.domain.BdglAntiepidemicArchives;
import com.supervision.medicalhealth.domain.BdglAntiepidemicPutin;
import com.supervision.medicalhealth.service.IBdglAntiepidemicPutinService;
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

import com.supervision.medicalhealth.domain.BdglAntiepidemicPutout;
import com.supervision.medicalhealth.service.IBdglAntiepidemicPutoutService;


/**
 * 防疫用品发放登记Controller
 * 
 * @author hr
 * @date 2022-02-14
 */
@RestController
@RequestMapping("/medicalhealth/antiepidemicputout")
public class BdglAntiepidemicPutoutController extends BaseController
{
    @Autowired
    private IBdglAntiepidemicPutoutService bdglAntiepidemicPutoutService;
    @Autowired
    private IBdglAntiepidemicPutinService antiepidemicPutinService;

    /**
     * 查询防疫用品发放登记列表
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:antiepidemicputout:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglAntiepidemicPutout bdglAntiepidemicPutout)
    {
        startPage();
        List<BdglAntiepidemicPutout> list = bdglAntiepidemicPutoutService.selectBdglAntiepidemicPutoutList(bdglAntiepidemicPutout);
        return getDataTable(list);
    }

    @GetMapping("/getlist")
    public AjaxResult getlist(){
        List<BdglAntiepidemicPutin> list = antiepidemicPutinService.selectBdglAntiepidemicPutinList(new BdglAntiepidemicPutin());
        Map<String,Object> map=null;
        ArrayList<BdglAntiepidemicPutin> bdglAntiepidemicPutins = new ArrayList<>();
        for (BdglAntiepidemicPutin bdglDurgArchives : list) {
            if(bdglDurgArchives.getSurplus()!=0){
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                String format = simpleDateFormat.format(bdglDurgArchives.getCreatetime());
                map=new HashMap<>();
                //品牌   规格 库房 单位
                String yaoPingName=bdglDurgArchives.getDurgName()+"-"+bdglDurgArchives.getSpecifications()+"-"+bdglDurgArchives.getStoreHouse()+"-"+bdglDurgArchives.getUnitName()+"-"+bdglDurgArchives.getDurgBrand()+"-"+format;
                bdglDurgArchives.setName(yaoPingName);
                bdglAntiepidemicPutins.add(bdglDurgArchives);
            }
        }
        return AjaxResult.success(bdglAntiepidemicPutins);
    }

    /**
     * 导出防疫用品发放登记列表
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:antiepidemicputout:export')")
    @Log(title = "防疫用品发放登记", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglAntiepidemicPutout bdglAntiepidemicPutout)
    {
        List<BdglAntiepidemicPutout> list = bdglAntiepidemicPutoutService.selectBdglAntiepidemicPutoutList(bdglAntiepidemicPutout);
        ExcelUtil<BdglAntiepidemicPutout> util = new ExcelUtil<BdglAntiepidemicPutout>(BdglAntiepidemicPutout.class);
        return util.exportExcel(list, "防疫用品发放登记数据");
    }

    /**
     * 获取防疫用品发放登记详细信息
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:antiepidemicputout:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(bdglAntiepidemicPutoutService.selectBdglAntiepidemicPutoutById(id));
    }

    /**
     * 新增防疫用品发放登记
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:antiepidemicputout:add')")
    @Log(title = "防疫用品发放登记", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglAntiepidemicPutout bdglAntiepidemicPutout)
    {
        LoginUser loginUser = getLoginUser();
        bdglAntiepidemicPutout.setAdminId(loginUser.getUserId());
        bdglAntiepidemicPutout.setCreatetime(new Date());
        return toAjax(bdglAntiepidemicPutoutService.insertBdglAntiepidemicPutout(bdglAntiepidemicPutout));
    }

    /**
     * 修改防疫用品发放登记
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:antiepidemicputout:edit')")
    @Log(title = "防疫用品发放登记", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglAntiepidemicPutout bdglAntiepidemicPutout)
    {
        return toAjax(bdglAntiepidemicPutoutService.updateBdglAntiepidemicPutout(bdglAntiepidemicPutout));
    }

    /**
     * 删除防疫用品发放登记
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:antiepidemicputout:remove')")
    @Log(title = "防疫用品发放登记", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bdglAntiepidemicPutoutService.deleteBdglAntiepidemicPutoutByIds(ids));
    }
}
