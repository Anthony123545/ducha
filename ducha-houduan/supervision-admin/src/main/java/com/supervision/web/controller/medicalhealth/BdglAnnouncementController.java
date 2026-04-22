package com.supervision.web.controller.medicalhealth;

import java.util.List;

import com.supervision.common.annotation.Log;
import com.supervision.common.core.controller.BaseController;
import com.supervision.common.core.domain.AjaxResult;
import com.supervision.common.core.page.TableDataInfo;
import com.supervision.common.enums.BusinessType;
import com.supervision.common.utils.poi.ExcelUtil;
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
import com.supervision.medicalhealth.domain.BdglAnnouncement;
import com.supervision.medicalhealth.service.IBdglAnnouncementService;


/**
 * 疫情通知公告Controller
 * 
 * @author hr
 * @date 2022-02-14
 */
@RestController
@RequestMapping("/medicalhealth/announcement")
public class BdglAnnouncementController extends BaseController
{
    @Autowired
    private IBdglAnnouncementService bdglAnnouncementService;

    /**
     * 查询疫情通知公告列表
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:announcement:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglAnnouncement bdglAnnouncement)
    {
        startPage();
        List<BdglAnnouncement> list = bdglAnnouncementService.selectBdglAnnouncementList(bdglAnnouncement);
        return getDataTable(list);
    }

    /**
     * 导出疫情通知公告列表
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:announcement:export')")
    @Log(title = "疫情通知公告", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglAnnouncement bdglAnnouncement)
    {
        List<BdglAnnouncement> list = bdglAnnouncementService.selectBdglAnnouncementList(bdglAnnouncement);
        ExcelUtil<BdglAnnouncement> util = new ExcelUtil<BdglAnnouncement>(BdglAnnouncement.class);
        return util.exportExcel(list, "疫情通知公告数据");
    }

    /**
     * 获取疫情通知公告详细信息
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:announcement:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bdglAnnouncementService.selectBdglAnnouncementById(id));
    }

    /**
     * 新增疫情通知公告
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:announcement:add')")
    @Log(title = "疫情通知公告", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglAnnouncement bdglAnnouncement)
    {
        return toAjax(bdglAnnouncementService.insertBdglAnnouncement(bdglAnnouncement));
    }

    /**
     * 修改疫情通知公告
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:announcement:edit')")
    @Log(title = "疫情通知公告", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglAnnouncement bdglAnnouncement)
    {
        return toAjax(bdglAnnouncementService.updateBdglAnnouncement(bdglAnnouncement));
    }

    /**
     * 删除疫情通知公告
     */
    @PreAuthorize("@ss.hasPermi('medicalhealth:announcement:remove')")
    @Log(title = "疫情通知公告", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglAnnouncementService.deleteBdglAnnouncementByIds(ids));
    }
}
