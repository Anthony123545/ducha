package com.supervision.web.controller.peopleChuRu;

import java.util.List;

import com.supervision.common.core.domain.entity.SysUser;
import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.system.service.ISysUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
import com.supervision.peopleChuRu.domain.BdglPeopleBorrow;
import com.supervision.peopleChuRu.service.IBdglPeopleBorrowService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 人员借调Controller
 * 
 * @author supervision
 * @date 2022-03-05
 */
@RestController
@RequestMapping("/peopleChuRu/peopleBorrow")
public class BdglPeopleBorrowController extends BaseController
{
    @Autowired
    private IBdglPeopleBorrowService bdglPeopleBorrowService;
    @Autowired
    private ISysUserService userService;

    /**
     * 查询人员借调列表
     */
    @PreAuthorize("@ss.hasPermi('peopleChuRu:peopleBorrow:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglPeopleBorrow bdglPeopleBorrow)
    {
        LoginUser loginUser = getLoginUser();
        SysUser sysUser = userService.selectUserById(loginUser.getUserId());
        startPage();
        List<BdglPeopleBorrow> list = bdglPeopleBorrowService.selectBdglPeopleBorrowList(bdglPeopleBorrow);
        for (BdglPeopleBorrow peopleBorrow : list) {


            if(sysUser.getPermissions()!=null){
                boolean falg = BdglLeaveController.isFalg(sysUser, "3");
                if(falg&&!peopleBorrow.getState().equals("1")){
                    peopleBorrow.setCommon("1");
                }
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出人员借调列表
     */
    @PreAuthorize("@ss.hasPermi('peopleChuRu:peopleBorrow:export')")
    @Log(title = "人员借调", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglPeopleBorrow bdglPeopleBorrow)
    {
        List<BdglPeopleBorrow> list = bdglPeopleBorrowService.selectBdglPeopleBorrowList(bdglPeopleBorrow);
        ExcelUtil<BdglPeopleBorrow> util = new ExcelUtil<BdglPeopleBorrow>(BdglPeopleBorrow.class);
        return util.exportExcel(list, "人员借调数据");
    }

    /**
     * 获取人员借调详细信息
     */
    @PreAuthorize("@ss.hasPermi('peopleChuRu:peopleBorrow:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        BdglPeopleBorrow bdglPeopleBorrow = bdglPeopleBorrowService.selectBdglPeopleBorrowById(id);
        return AjaxResult.success(bdglPeopleBorrow);
    }

    /**
     * 新增人员借调
     */
    @PreAuthorize("@ss.hasPermi('peopleChuRu:peopleBorrow:add')")
    @Log(title = "人员借调", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglPeopleBorrow bdglPeopleBorrow)
    {
        LoginUser loginUser = getLoginUser();
        bdglPeopleBorrow.setCreatName(loginUser.getUsername());
        return toAjax(bdglPeopleBorrowService.insertBdglPeopleBorrow(bdglPeopleBorrow));
    }

    /**
     * 修改人员借调
     */
    @PreAuthorize("@ss.hasPermi('peopleChuRu:peopleBorrow:edit')")
    @Log(title = "人员借调", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglPeopleBorrow bdglPeopleBorrow)
    {
        return toAjax(bdglPeopleBorrowService.updateBdglPeopleBorrow(bdglPeopleBorrow));
    }

    /**
     * 删除人员借调
     */
    @PreAuthorize("@ss.hasPermi('peopleChuRu:peopleBorrow:remove')")
    @Log(title = "人员借调", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bdglPeopleBorrowService.deleteBdglPeopleBorrowByIds(ids));
    }
}
