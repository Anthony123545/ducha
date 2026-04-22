package com.supervision.web.controller.barracksManagement;

import java.util.Date;
import java.util.List;

import cn.hutool.core.bean.BeanUtil;
import com.supervision.barracksManagement.domain.dto.TemporaryCheckinDto;
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
import com.supervision.barracksManagement.domain.TemporaryCheckin;
import com.supervision.barracksManagement.service.ITemporaryCheckinService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 临时来队Controller
 * 
 * @author supervision
 * @date 2022-03-07
 */
@RestController
@RequestMapping("/barracksManagement/checkin")
public class TemporaryCheckinController extends BaseController
{
    @Autowired
    private ITemporaryCheckinService temporaryCheckinService;

    /**
     * 查询临时来队列表
     */
    @PreAuthorize("@ss.hasPermi('barracksManagement:checkin:list')")
    @GetMapping("/list")
    public TableDataInfo list(TemporaryCheckin temporaryCheckin)
    {
        startPage();
        List<TemporaryCheckin> list = temporaryCheckinService.selectTemporaryCheckinList(temporaryCheckin);
        for (TemporaryCheckin checkin : list) {
            checkin.setCommon(checkin.getPosition()+"#"+checkin.getPosition2());
        }
        return getDataTable(list);
    }

    /**
     * 导出临时来队列表
     */
    @PreAuthorize("@ss.hasPermi('barracksManagement:checkin:export')")
    @Log(title = "临时来队", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TemporaryCheckin temporaryCheckin)
    {
        List<TemporaryCheckin> list = temporaryCheckinService.selectTemporaryCheckinList(temporaryCheckin);
        List<TemporaryCheckinDto> dtoList = BeanUtil.copyToList(list, TemporaryCheckinDto.class);
        ExcelUtil<TemporaryCheckinDto> util = new ExcelUtil<>(TemporaryCheckinDto.class);
        return util.exportExcel(dtoList, "临时来队数据");
    }

    /**
     * 获取临时来队详细信息
     */
    @PreAuthorize("@ss.hasPermi('barracksManagement:checkin:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        TemporaryCheckin temporaryCheckin = temporaryCheckinService.selectTemporaryCheckinById(id);
        temporaryCheckin.setCommon(temporaryCheckin.getPosition()+"#"+temporaryCheckin.getPosition2());
        return AjaxResult.success(temporaryCheckin);
    }

    /**
     * 新增临时来队
     */
    @PreAuthorize("@ss.hasPermi('barracksManagement:checkin:add')")
    @Log(title = "临时来队", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TemporaryCheckin temporaryCheckin)
    {
        String position = temporaryCheckin.getCommon();
        String[] split = position.split("#");
        temporaryCheckin.setPosition(Integer.parseInt(split[0]));
        temporaryCheckin.setPosition2(Integer.parseInt(split[1]));
        temporaryCheckin.setCreatTime(new Date());
        return toAjax(temporaryCheckinService.insertTemporaryCheckin(temporaryCheckin));
    }

    /**
     * 修改临时来队
     */
    @PreAuthorize("@ss.hasPermi('barracksManagement:checkin:edit')")
    @Log(title = "临时来队", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TemporaryCheckin temporaryCheckin)
    {
        String position = temporaryCheckin.getCommon();
        String[] split = position.split("#");
        temporaryCheckin.setPosition(Integer.parseInt(split[0]));
        temporaryCheckin.setPosition2(Integer.parseInt(split[1]));
        return toAjax(temporaryCheckinService.updateTemporaryCheckin(temporaryCheckin));
    }

    /**
     * 删除临时来队
     */
    @PreAuthorize("@ss.hasPermi('barracksManagement:checkin:remove')")
    @Log(title = "临时来队", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(temporaryCheckinService.deleteTemporaryCheckinByIds(ids));
    }
}
