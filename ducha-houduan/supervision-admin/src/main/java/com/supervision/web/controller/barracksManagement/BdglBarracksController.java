package com.supervision.web.controller.barracksManagement;

import java.util.HashMap;
import java.util.List;

import cn.hutool.core.bean.BeanUtil;
import com.supervision.barracksManagement.domain.BdglFlat;
import com.supervision.barracksManagement.domain.SingleDormitory;
import com.supervision.barracksManagement.domain.dto.BdglBarracksDto;
import com.supervision.barracksManagement.service.IBdglFlatService;
import com.supervision.barracksManagement.service.ISingleDormitoryService;
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
import com.supervision.barracksManagement.domain.BdglBarracks;
import com.supervision.barracksManagement.service.IBdglBarracksService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

import javax.annotation.Resource;

/**
 * 营房档案Controller
 * 
 * @author supervision
 * @date 2022-03-02
 */
@RestController
@RequestMapping("/barracksManagement/barracks")
public class BdglBarracksController extends BaseController
{
    @Autowired
    private IBdglBarracksService bdglBarracksService;

    /**
     * 查询营房档案列表
     */
    @PreAuthorize("@ss.hasPermi('barracksManagement:barracks:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglBarracks bdglBarracks)
    {
        startPage();
        List<BdglBarracks> list = bdglBarracksService.selectBdglBarracksList(bdglBarracks);
        return getDataTable(list);
    }

    /**
     * 导出营房档案列表
     */
    @PreAuthorize("@ss.hasPermi('barracksManagement:barracks:export')")
    @Log(title = "营房档案", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglBarracks bdglBarracks)
    {
        List<BdglBarracks> list = bdglBarracksService.selectBdglBarracksList(bdglBarracks);
        List<BdglBarracksDto> dtoList = BeanUtil.copyToList(list, BdglBarracksDto.class);
        ExcelUtil<BdglBarracksDto> util = new ExcelUtil<>(BdglBarracksDto.class);
        return util.exportExcel(dtoList, "营房档案数据");
    }

    /**
     * 获取营房档案详细信息
     */
    @PreAuthorize("@ss.hasPermi('barracksManagement:barracks:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bdglBarracksService.selectBdglBarracksById(id));
    }

    /**
     * 新增营房档案
     */
    @PreAuthorize("@ss.hasPermi('barracksManagement:barracks:add')")
    @Log(title = "营房档案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglBarracks bdglBarracks)
    {
        return toAjax(bdglBarracksService.insertBdglBarracks(bdglBarracks));
    }

    /**
     * 修改营房档案
     */
    @PreAuthorize("@ss.hasPermi('barracksManagement:barracks:edit')")
    @Log(title = "营房档案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglBarracks bdglBarracks)
    {
        return toAjax(bdglBarracksService.updateBdglBarracks(bdglBarracks));
    }

    /**
     * 删除营房档案
     */
    @PreAuthorize("@ss.hasPermi('barracksManagement:barracks:remove')")
    @Log(title = "营房档案", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglBarracksService.deleteBdglBarracksByIds(ids));
    }

    @Resource
    private ISingleDormitoryService singleDormitoryService;

    @Autowired
    private IBdglFlatService bdglFlatService;


    @GetMapping("/getnumber")
    public AjaxResult getnumber(){
        List<BdglBarracks> bdglBarracks = bdglBarracksService.selectBdglBarracksList(new BdglBarracks());
        List<SingleDormitory> singleDormitories = singleDormitoryService.selectSingleDormitoryList(new SingleDormitory());
        List<BdglFlat> bdglFlats = bdglFlatService.selectBdglFlatList(new BdglFlat());
        HashMap<String, Integer> map = new HashMap<>();
        map.put("yingFang",bdglBarracks.size());
        map.put("suShe",singleDormitories.size());
        map.put("gongYu",bdglFlats.size());
        return AjaxResult.success(map);
    }
}
