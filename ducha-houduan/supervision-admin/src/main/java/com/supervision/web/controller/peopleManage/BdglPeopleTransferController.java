package com.supervision.web.controller.peopleManage;

import java.util.List;

import com.supervision.common.core.domain.entity.SysUser;
import com.supervision.peopleManage.domain.BdglPeople;
import com.supervision.peopleManage.service.IBdglPeopleService;
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
import com.supervision.peopleManage.domain.BdglPeopleTransfer;
import com.supervision.peopleManage.service.IBdglPeopleTransferService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 调动管理Controller
 *
 * @author supervision
 * @date 2022-02-23
 */
@RestController
@RequestMapping("/peopleManage/transfer")
public class BdglPeopleTransferController extends BaseController
{
    @Autowired
    private IBdglPeopleTransferService bdglPeopleTransferService;

    @Autowired
    private IBdglPeopleService bdglPeopleService;

    @Autowired
    private ISysUserService userService;

    /**
     * 查询调动管理列表
     */
    @PreAuthorize("@ss.hasPermi('peopleManage:transfer:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglPeopleTransfer bdglPeopleTransfer)
    {
        startPage();
        List<BdglPeopleTransfer> list = bdglPeopleTransferService.selectBdglPeopleTransferList(bdglPeopleTransfer);
        return getDataTable(list);
    }

    /**
     * 导出调动管理列表
     */
    @PreAuthorize("@ss.hasPermi('peopleManage:transfer:export')")
    @Log(title = "调动管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglPeopleTransfer bdglPeopleTransfer)
    {
        List<BdglPeopleTransfer> list = bdglPeopleTransferService.selectBdglPeopleTransferList(bdglPeopleTransfer);
        ExcelUtil<BdglPeopleTransfer> util = new ExcelUtil<BdglPeopleTransfer>(BdglPeopleTransfer.class);
        return util.exportExcel(list, "调动管理数据");
    }

    /**
     * 获取调动管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('peopleManage:transfer:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bdglPeopleTransferService.selectBdglPeopleTransferById(id));
    }

    /**
     * 新增调动管理
     */
    @PreAuthorize("@ss.hasPermi('peopleManage:transfer:add')")
    @Log(title = "调动管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglPeopleTransfer bdglPeopleTransfer)
    {
        bdglPeopleTransferService.insertBdglPeopleTransfer(bdglPeopleTransfer);
        BdglPeople bp = new BdglPeople();
        bp.setDeptId(Long.valueOf(bdglPeopleTransfer.getUnit2()));
        bp.setNowMilitaryRank(bdglPeopleTransfer.getMilitaryRank2());
        bp.setPostId(Integer.valueOf(bdglPeopleTransfer.getPost2()));
        bp.setId(Long.valueOf(bdglPeopleTransfer.getPeopleId()));
        SysUser user =new SysUser();
        user.setDeptId(Long.valueOf(bdglPeopleTransfer.getUnit2()));

        userService.updateUser(user);
        return toAjax(bdglPeopleService.updateBdglPeople(bp));
    }

    /**
     * 修改调动管理
     */
    @PreAuthorize("@ss.hasPermi('peopleManage:transfer:edit')")
    @Log(title = "调动管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglPeopleTransfer bdglPeopleTransfer)
    {
        BdglPeople bp = new BdglPeople();
        bp.setDeptId(Long.valueOf(bdglPeopleTransfer.getUnit2()));
        bp.setNowMilitaryRank(bdglPeopleTransfer.getMilitaryRank2());
        bp.setPostId(Integer.valueOf(bdglPeopleTransfer.getPost2()));
        bp.setId(Long.valueOf(bdglPeopleTransfer.getPeopleId()));
        bdglPeopleService.updateBdglPeople(bp);
        SysUser user =new SysUser();
        user.setDeptId(Long.valueOf(bdglPeopleTransfer.getUnit2()));

        userService.updateUser(user);
        return toAjax(bdglPeopleTransferService.updateBdglPeopleTransfer(bdglPeopleTransfer));
    }

    /**
     * 删除调动管理
     */
    @PreAuthorize("@ss.hasPermi('peopleManage:transfer:remove')")
    @Log(title = "调动管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {

        return toAjax(bdglPeopleTransferService.deleteBdglPeopleTransferByIds(ids));
    }
}
