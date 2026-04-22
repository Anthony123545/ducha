package com.supervision.web.controller.militaryvehicleManagement;

import java.util.Date;
import java.util.List;

import com.supervision.common.core.domain.entity.SysUser;
import com.supervision.common.core.domain.model.LoginUser;
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
import com.supervision.militaryvehicleManagement.domain.BdglThebusConfig;
import com.supervision.militaryvehicleManagement.service.IBdglThebusConfigService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 军车管理设置Controller
 * 
 * @author supervision
 * @date 2022-03-04
 */
@RestController
@RequestMapping("/militaryvehicleManagement/thebusConfig")
public class BdglThebusConfigController extends BaseController
{
    @Autowired
    private IBdglThebusConfigService bdglThebusConfigService;

    @Autowired
    private ISysUserService iSysUserService;

    /**
     * 查询军车管理设置列表
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:thebusConfig:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglThebusConfig bdglThebusConfig)
    {
        startPage();
        List<BdglThebusConfig> list = bdglThebusConfigService.selectBdglThebusConfigList(bdglThebusConfig);
        return getDataTable(list);
    }

    /**
     * 导出军车管理设置列表
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:thebusConfig:export')")
    @Log(title = "军车管理设置", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglThebusConfig bdglThebusConfig)
    {
        List<BdglThebusConfig> list = bdglThebusConfigService.selectBdglThebusConfigList(bdglThebusConfig);
        ExcelUtil<BdglThebusConfig> util = new ExcelUtil<BdglThebusConfig>(BdglThebusConfig.class);
        return util.exportExcel(list, "军车管理设置数据");
    }

    /**
     * 获取军车管理设置详细信息
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:thebusConfig:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bdglThebusConfigService.selectBdglThebusConfigById(id));
    }

    /**
     * 新增军车管理设置
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:thebusConfig:add')")
    @Log(title = "军车管理设置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody List<BdglThebusConfig> bdglThebusConfig)
    {
        //获取登录人信息
        LoginUser loginUser = getLoginUser();
        Long userId = loginUser.getUserId();
        //遍历插入审批模板
        for (BdglThebusConfig thebusConfig : bdglThebusConfig) {
            if(thebusConfig.getOnshenpirenid()!=null){
                thebusConfig.setOneshenpirenname(getNameById(thebusConfig.getOnshenpirenid())); //获取一级审批人
            }
            if(thebusConfig.getTwoshenpirenid()!=null){
                thebusConfig.setTwoshenpirenname(getNameById(thebusConfig.getTwoshenpirenid())); //获取二级审批人
            }
            if(thebusConfig.getThreeshenpirenid()!=null){
                thebusConfig.setThreeshenpirenname(getNameById(thebusConfig.getThreeshenpirenid())); //获取三级审批人
            }
            thebusConfig.setCreateid(Integer.parseInt(userId+"")); //获取创建人的ID
            thebusConfig.setCreatetime(new Date()); //获取创建时间
            thebusConfig.setUpdatetime(new Date()); //获取修改时间
            bdglThebusConfigService.insertBdglThebusConfig(thebusConfig);
        }
        return toAjax(200);
    }

    /**
     * 通过获取名称
     * @param userid
     * @return
     */
    public String getNameById(Long userid) {
        SysUser sysUser = iSysUserService.selectUserById(userid);
        return sysUser.getNickName();
    }

    /**
     * 修改军车管理设置
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:thebusConfig:edit')")
    @Log(title = "军车管理设置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglThebusConfig bdglThebusConfig)
    {
        bdglThebusConfig.setUpdatetime(new Date());
        if(bdglThebusConfig.getOnshenpirenid()!=null){
            bdglThebusConfig.setOneshenpirenname(getNameById(bdglThebusConfig.getOnshenpirenid())); //获取一级审批人
        }else{
            bdglThebusConfig.setOneshenpirenname("");
        }
        if(bdglThebusConfig.getTwoshenpirenid()!=null){
            bdglThebusConfig.setTwoshenpirenname(getNameById(bdglThebusConfig.getTwoshenpirenid())); //获取二级审批人
        }else{
            bdglThebusConfig.setTwoshenpirenname("");
        }
        if(bdglThebusConfig.getThreeshenpirenid()!=null){
            bdglThebusConfig.setThreeshenpirenname(getNameById(bdglThebusConfig.getThreeshenpirenid())); //获取三级审批人
        }else{
            bdglThebusConfig.setThreeshenpirenname("");
        }
        return toAjax(bdglThebusConfigService.updateBdglThebusConfig(bdglThebusConfig));
    }

    /**
     * 删除军车管理设置
     */
    @PreAuthorize("@ss.hasPermi('militaryvehicleManagement:thebusConfig:remove')")
    @Log(title = "军车管理设置", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglThebusConfigService.deleteBdglThebusConfigByIds(ids));
    }
}
