package com.supervision.web.controller.zbqk;

import java.util.List;

import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.zbqk.domain.BdglZbqkFileInfo;
import com.supervision.zbqk.service.IBdglZbqkFileInfoService;
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
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 战备情况Controller
 * 
 * @author supervision
 * @date 2022-11-16
 */
@RestController
@RequestMapping("/zbqk/fileinfo")
public class BdglZbqkFileInfoController extends BaseController
{
    @Autowired
    private IBdglZbqkFileInfoService bdglZbqkFileInfoService;

    /**
     * 查询战备情况列表
     */
    @PreAuthorize("@ss.hasPermi('zbqk:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglZbqkFileInfo bdglZbqkFileInfo)
    {
        startPage();
        List<BdglZbqkFileInfo> list = bdglZbqkFileInfoService.selectBdglZbqkFileInfoList(bdglZbqkFileInfo);
        return getDataTable(list);
    }

    /**
     * 导出战备情况列表
     */
    @PreAuthorize("@ss.hasPermi('zbqk:info:export')")
    @Log(title = "战备情况", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglZbqkFileInfo bdglZbqkFileInfo)
    {
        List<BdglZbqkFileInfo> list = bdglZbqkFileInfoService.selectBdglZbqkFileInfoList(bdglZbqkFileInfo);
        ExcelUtil<BdglZbqkFileInfo> util = new ExcelUtil<BdglZbqkFileInfo>(BdglZbqkFileInfo.class);
        return util.exportExcel(list, "战备情况数据");
    }

    /**
     * 获取战备情况详细信息
     */
    @PreAuthorize("@ss.hasPermi('zbqk:info:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(bdglZbqkFileInfoService.selectBdglZbqkFileInfoById(id));
    }

    /**
     * 新增战备情况
     */
    @PreAuthorize("@ss.hasPermi('zbqk:info:add')")
    @Log(title = "战备情况", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglZbqkFileInfo bdglZbqkFileInfo)
    {
        LoginUser loginUser = getLoginUser();
        return toAjax(bdglZbqkFileInfoService.insertBdglZbqkFileInfo(loginUser,bdglZbqkFileInfo));
    }

    /**
     * 修改战备情况
     */
    @PreAuthorize("@ss.hasPermi('zbqk:info:edit')")
    @Log(title = "战备情况", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglZbqkFileInfo bdglZbqkFileInfo)
    {
        //判断当前问价夹是否存在阅读记录  存在阅读数=+1
        bdglZbqkFileInfo.setReadVolume(bdglZbqkFileInfo.getReadVolume()+1);
        return toAjax(bdglZbqkFileInfoService.updateBdglZbqkFileInfo(bdglZbqkFileInfo));
    }

    /**
     * 删除战备情况
     */
    @PreAuthorize("@ss.hasPermi('zbqk:info:remove')")
    @Log(title = "战备情况", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bdglZbqkFileInfoService.deleteBdglZbqkFileInfoByIds(ids));
    }
}
