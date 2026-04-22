package com.supervision.web.controller.peopleChuRu;

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
import com.supervision.peopleChuRu.domain.BdglPeopleAccess;
import com.supervision.peopleChuRu.service.IBdglPeopleAccessService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 人员出入记录Controller
 * 
 * @author supervision
 * @date 2023-07-01
 */
@RestController
@RequestMapping("/peopleChuRu/access")
public class BdglPeopleAccessController extends BaseController
{
    @Autowired
    private IBdglPeopleAccessService bdglPeopleAccessService;

    /**
     * 查询人员出入记录列表
     */
    @PreAuthorize("@ss.hasPermi('peopleChuRu:access:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglPeopleAccess bdglPeopleAccess)
    {
        startPage();
        List<BdglPeopleAccess> list = bdglPeopleAccessService.selectBdglPeopleAccessList(bdglPeopleAccess);
        return getDataTable(list);
    }

    /**
     * 导出人员出入记录列表
     */
    @PreAuthorize("@ss.hasPermi('peopleChuRu:access:export')")
    @Log(title = "人员出入记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglPeopleAccess bdglPeopleAccess)
    {
        List<BdglPeopleAccess> list = bdglPeopleAccessService.selectBdglPeopleAccessList(bdglPeopleAccess);
        ExcelUtil<BdglPeopleAccess> util = new ExcelUtil<BdglPeopleAccess>(BdglPeopleAccess.class);
        return util.exportExcel(list, "人员出入记录数据");
    }

    /**
     * 获取人员出入记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('peopleChuRu:access:query')")
    @GetMapping(value = "/{userId}")
    public AjaxResult getInfo(@PathVariable("userId") Long userId)
    {
        return AjaxResult.success(bdglPeopleAccessService.selectBdglPeopleAccessByUserId(userId));
    }

    /**
     * 新增人员出入记录
     */
    @PreAuthorize("@ss.hasPermi('peopleChuRu:access:add')")
    @Log(title = "人员出入记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglPeopleAccess bdglPeopleAccess)
    {
        return toAjax(bdglPeopleAccessService.insertBdglPeopleAccess(bdglPeopleAccess));
    }

    /**
     * 修改人员出入记录
     */
    @PreAuthorize("@ss.hasPermi('peopleChuRu:access:edit')")
    @Log(title = "人员出入记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglPeopleAccess bdglPeopleAccess)
    {
        return toAjax(bdglPeopleAccessService.updateBdglPeopleAccess(bdglPeopleAccess));
    }

    /**
     * 删除人员出入记录
     */
    @PreAuthorize("@ss.hasPermi('peopleChuRu:access:remove')")
    @Log(title = "人员出入记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds)
    {
        return toAjax(bdglPeopleAccessService.deleteBdglPeopleAccessByUserIds(userIds));
    }

    /**
     * 统计人员出入记录
     */
    @PreAuthorize("@ss.hasPermi('peopleChuRu:access:count')")
    @GetMapping("/count")
    public AjaxResult count(BdglPeopleAccess bdglPeopleAccess)
    {
        return AjaxResult.success(bdglPeopleAccessService.countBdglPeopleAccess(bdglPeopleAccess));
    }
}