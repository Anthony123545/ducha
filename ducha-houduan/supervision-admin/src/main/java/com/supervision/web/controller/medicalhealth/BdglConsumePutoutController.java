package com.supervision.web.controller.medicalhealth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.supervision.common.annotation.Log;
import com.supervision.common.core.controller.BaseController;
import com.supervision.common.core.domain.AjaxResult;
import com.supervision.common.core.domain.entity.SysUser;
import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.common.core.page.TableDataInfo;
import com.supervision.common.enums.BusinessType;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.medicalhealth.domain.BdglConsumeArchives;
import com.supervision.medicalhealth.domain.BdglConsumePutin;
import com.supervision.medicalhealth.service.IBdglConsumePutinService;
import com.supervision.system.service.ISysUserService;
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
import com.supervision.medicalhealth.domain.BdglConsumePutout;
import com.supervision.medicalhealth.service.IBdglConsumePutoutService;


/**
 * 耗材出库登记Controller
 * 
 * @author hr
 * @date 2022-02-12
 */
@RestController
@RequestMapping("/medicalhealth/consumeputout")
public class BdglConsumePutoutController extends BaseController
{
    @Autowired
    private IBdglConsumePutoutService bdglConsumePutoutService;
    @Autowired
    private ISysUserService userService;

    @Autowired
    private IBdglConsumePutinService consumePutinService;

    /**
     * 查询耗材出库登记列表
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:consumeputout:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglConsumePutout bdglConsumePutout)
    {
        startPage();
        List<BdglConsumePutout> list = bdglConsumePutoutService.selectBdglConsumePutoutList(bdglConsumePutout);
        return getDataTable(list);
    }
    @GetMapping("/getlist")
    public AjaxResult getlist(){
        List<BdglConsumePutin> list = consumePutinService.selectBdglConsumePutinList(new BdglConsumePutin());
        Map<String,Object> map=null;
        ArrayList<BdglConsumePutin> bdglConsumePutins = new ArrayList<>();
        for (BdglConsumePutin bdglDurgArchives : list) {
            if(bdglDurgArchives.getSurplus()!=0){
                map=new HashMap<>();
                //品牌   规格 库房 单位
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                String format = simpleDateFormat.format(bdglDurgArchives.getCreateTime());
                String yaoPingName=bdglDurgArchives.getDurgName()+"-"+bdglDurgArchives.getSpecifications()+"-"+bdglDurgArchives.getStoreHouse()+"-"+bdglDurgArchives.getUnitName()+"-"+bdglDurgArchives.getDurgBrand()+"-"+format;
                bdglDurgArchives.setName(yaoPingName);
                bdglConsumePutins.add(bdglDurgArchives);
            }
        }
        return AjaxResult.success(bdglConsumePutins);
    }
    /**
     * 导出耗材出库登记列表
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:consumeputout:export')")
    @Log(title = "耗材出库登记", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglConsumePutout bdglConsumePutout)
    {
        List<BdglConsumePutout> list = bdglConsumePutoutService.selectBdglConsumePutoutList(bdglConsumePutout);
        ExcelUtil<BdglConsumePutout> util = new ExcelUtil<BdglConsumePutout>(BdglConsumePutout.class);
        return util.exportExcel(list, "耗材出库登记数据");
    }

    /**
     * 获取耗材出库登记详细信息
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:consumeputout:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(bdglConsumePutoutService.selectBdglConsumePutoutById(id));
    }

    /**
     * 新增耗材出库登记
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:consumeputout:add')")
    @Log(title = "耗材出库登记", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglConsumePutout bdglConsumePutout)
    {
        LoginUser loginUser = getLoginUser();
        bdglConsumePutout.setAdminId(loginUser.getUserId());
        SysUser sysUser = userService.selectUserById(loginUser.getUserId());
        bdglConsumePutout.setUserName(sysUser.getNickName());
        return toAjax(bdglConsumePutoutService.insertBdglConsumePutout(bdglConsumePutout));
    }

    /**
     * 修改耗材出库登记
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:consumeputout:edit')")
    @Log(title = "耗材出库登记", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglConsumePutout bdglConsumePutout)
    {
        return toAjax(bdglConsumePutoutService.updateBdglConsumePutout(bdglConsumePutout));
    }

    /**
     * 删除耗材出库登记
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:consumeputout:remove')")
    @Log(title = "耗材出库登记", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bdglConsumePutoutService.deleteBdglConsumePutoutByIds(ids));
    }
}
