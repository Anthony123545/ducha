package com.supervision.web.controller.grassrootsregistration;

import java.io.File;
import java.util.List;

import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.common.core.domain.entity.SysRole;
import com.supervision.common.core.domain.entity.SysUser;
import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.common.utils.StringUtils;
import com.supervision.grassrootsregistration.resultVo.SysDeptDto;
import com.supervision.peopleManage.domain.BdglPeople;
import com.supervision.peopleManage.service.IBdglPeopleService;
import com.supervision.system.mapper.SysUserMapper;
import com.supervision.system.service.ISysDeptService;
import com.supervision.system.service.ISysUserService;
import org.springframework.http.HttpRequest;
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
import com.supervision.grassrootsregistration .domain.BdglEven;
import com.supervision.grassrootsregistration .service.IBdglEvenService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * 连务会记录本Controller
 *
 * @author supervision
 * @date 2022-02-26
 */
@RestController
@RequestMapping("/grassrootsregistration/bdgleven")
public class BdglEvenController extends BaseController
{
    @Autowired
    private IBdglEvenService bdglEvenService;
    @Autowired
    private IBdglPeopleService bdglPeopleService;
    @Autowired
    private ISysDeptService sysDeptService;
    @Autowired
    private ISysUserService userService;
    @Autowired
    private SysUserMapper userMapper;
    /**
     * 查询连务会记录本列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdgleven:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglEven bdglEven, HttpServletRequest request)
    {
        LoginUser loginUser = getLoginUser();
        SysUser sysUser = userService.selectUserById(loginUser.getUserId());
        List<SysRole> sysRoles=userMapper.selectRole(loginUser.getUserId());
        String role="";
        if(sysRoles!=null){
            for (SysRole sysRole : sysRoles) {
                if(sysRole.getRoleKey().equals("admin")){
                    role=sysRole.getRoleKey();
                    break;
                }
            }
        }
        boolean admin = role.equals("admin");
        if(sysUser.getUserName().equals("admin")||role.equals("admin")){

        }else{
            bdglEven.setUnitId(Integer.parseInt(loginUser.getDeptId().toString())  );
        }
        startPage();
        List<BdglEven> list = bdglEvenService.selectBdglEvenLists(bdglEven);
        return getDataTable(list);
    }

    /**
     * 领导查询连务会记录本
     */
//    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglevens:list')")
    @GetMapping("/getMeetRecordList")

    public TableDataInfo getMeetRecordList(String year)
    {
        if (StringUtils.isNotEmpty(year)){
            startPage();
            List<SysDeptDto> list = bdglEvenService.getMeetRecordList(year);
            return getDataTable(list);
        }
        List<List<SysDeptDto>> list = sysDeptService.getMeetRecordsList();
        return getDataTable(list);
    }

    /**
     * 领导查询连务会记录本列表s
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglevens:list')")
    @GetMapping("/getMeetRecordOnUnitList")
    public TableDataInfo getMeetRecordOnUnitList(Integer unitId,String year)
    {
        startPage();
        List<BdglEven> list = bdglEvenService.getMeetRecordOnUnitList(unitId,year);
        return getDataTable(list);
    }


    /**
     * 导出连务会记录本列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdgleven:export')")
    @Log(title = "连务会记录本", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglEven bdglEven)
    {
        List<BdglEven> list = bdglEvenService.selectBdglEvenList(bdglEven);
        ExcelUtil<BdglEven> util = new ExcelUtil<BdglEven>(BdglEven.class);
        return util.exportExcel(list, "连务会记录本数据");
    }

    /**
     * 获取连务会记录本详细信息
     */
    @PreAuthorize("@ss.hasAnyPermi('grassrootsregistration:bdgleven:query,grassrootsregistration:bdglevens:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
           return AjaxResult.success(bdglEvenService.selectBdglEvenById(id));
    }

    /**
     * 新增连务会记录本
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdgleven:add')")
    @Log(title = "连务会记录本", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglEven bdglEven)
    {
        return toAjax(bdglEvenService.insertBdglEven(bdglEven));
    }

    /**
     * 修改连务会记录本
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdgleven:edit')")
    @Log(title = "连务会记录本", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglEven bdglEven)
    {
        return toAjax(bdglEvenService.updateBdglEven(bdglEven));
    }

    /**
     * 删除连务会记录本
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdgleven:remove')")
    @Log(title = "连务会记录本", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglEvenService.deleteBdglEvenByIds(ids));
    }
}
