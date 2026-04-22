package com.supervision.web.controller.barracksManagement;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.supervision.common.core.domain.entity.SysUser;
import com.supervision.common.utils.SecurityUtils;
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
import com.supervision.common.annotation.Log;
import com.supervision.common.core.controller.BaseController;
import com.supervision.common.core.domain.AjaxResult;
import com.supervision.common.enums.BusinessType;
import com.supervision.barracksManagement.domain.BdglRepair;
import com.supervision.barracksManagement.service.IBdglRepairService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 维修申请Controller
 *
 * @author ruoyi
 * @date 2023-03-10
 */
@RestController
@RequestMapping("/barracksManagement/repair")
public class BdglRepairController extends BaseController
{
    @Autowired
    private IBdglRepairService bdglRepairService;
    @Autowired
    private ISysUserService userService;


    /**
     * 查询维修申请列表
     */
    @PreAuthorize("@ss.hasPermi('barracksManagement:repair:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglRepair bdglRepair)
    {
        startPage();
        List<BdglRepair> list = bdglRepairService.selectBdglRepairList(bdglRepair);
        return getDataTable(list);
    }

    /**
     * 是否存在维修信息
     * @param
     * @return
     */
    @GetMapping(value = "/getInfoD")
    public AjaxResult getInfoD()
    {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        List<String> roleList= userService.selectUserRoleKey(user.getUserId());
        for (int i=0;i<roleList.size();i++){
            String role= roleList.get(i);
            if(role.equals("WXG")){
                BdglRepair bdglRepair = new BdglRepair();
                bdglRepair.setState(0);
                List<BdglRepair> list = bdglRepairService.selectBdglRepairList(bdglRepair);
                if(list.size()>0){
                    return AjaxResult.success("infoD","您有维修工作需要处理！");
                }else {
                    return AjaxResult.success("infoD","0");
                }
            }
        }
        return AjaxResult.success("infoD","0");
    }

    /**
     * 导出维修申请列表
     */
    @PreAuthorize("@ss.hasPermi('barracksManagement:repair:export')")
    @Log(title = "维修申请", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BdglRepair bdglRepair)
    {
        List<BdglRepair> list = bdglRepairService.selectBdglRepairList(bdglRepair);
        ExcelUtil<BdglRepair> util = new ExcelUtil<BdglRepair>(BdglRepair.class);
        util.exportExcel(response, list, "维修申请数据");
    }

    /**
     * 获取维修申请详细信息
     */
    @PreAuthorize("@ss.hasPermi('barracksManagement:repair:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(bdglRepairService.selectBdglRepairById(id));
    }

    /**
     * 新增维修申请
     */
    @PreAuthorize("@ss.hasPermi('barracksManagement:repair:add')")
    @Log(title = "维修申请", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglRepair bdglRepair)
    {
        return toAjax(bdglRepairService.insertBdglRepair(bdglRepair));
    }

    /**
     * 修改维修申请
     */
    @PreAuthorize("@ss.hasPermi('barracksManagement:repair:edit')")
    @Log(title = "维修申请", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglRepair bdglRepair)
    {
        return toAjax(bdglRepairService.updateBdglRepair(bdglRepair));
    }

    @PreAuthorize("@ss.hasPermi('barracksManagement:repair:edit')")
    @Log(title = "维修完成", businessType = BusinessType.UPDATE)
    @PutMapping("/repairSuccess")
    public AjaxResult repairSuccess(@RequestBody BdglRepair bdglRepair)
    {
        return toAjax(bdglRepairService.updateBdglRepair(bdglRepair));
    }

    /**
     * 删除维修申请
     */
    @PreAuthorize("@ss.hasPermi('barracksManagement:repair:remove')")
    @Log(title = "维修申请", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bdglRepairService.deleteBdglRepairByIds(ids));
    }
}
