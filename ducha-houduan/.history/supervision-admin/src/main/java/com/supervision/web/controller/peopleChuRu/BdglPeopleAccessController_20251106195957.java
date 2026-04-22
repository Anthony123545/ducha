package com.supervision.web.controller.peopleChuRu;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.supervision.common.annotation.Log;
import com.supervision.common.core.controller.BaseController;
import com.supervision.common.core.domain.AjaxResult;
import com.supervision.common.enums.BusinessType;
import com.supervision.peopleChuRu.domain.BdglPeopleAccess;
import com.supervision.peopleChuRu.service.IBdglPeopleAccessService;
import com.supervision.peopleManage.domain.BdglPeople;
import com.supervision.peopleManage.service.IBdglPeopleService;

import cn.hutool.core.bean.BeanUtil;

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
    
     @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    private IBdglPeopleService bdglPeopleService;

    //使用redis map存储所有人员的是否在营信息.如果人员不存在就创建
    @PostConstruct
    public void init(){

        bdglPeopleService.selectBdglPeopleList(null).forEach(bdglPeopleBorrow -> {
            if(!redisTemplate.opsForHash().hasKey("peopleStatus",String.valueOf(bdglPeopleBorrow.getId()))){
                redisTemplate.opsForHash().put("peopleStatus",String.valueOf(bdglPeopleBorrow.getId()),0);
            }
        });

    }
    /**
     * 设置人员是否在营
     * @param id
     * @param status 0:不在营 1:在营
     * @return
     */
    @PostMapping("/setStatus")
    public String setStatus(@RequestParam("id") Long id,@RequestParam("status") Integer status){
        redisTemplate.opsForHash().put("peopleStatus",String.valueOf(id),status);
        return "success";
    }

    @GetMapping("/getStatus")
    public AjaxResult getStatus(Long id){
        List<Map<String,Object>> list= new LinkedList<>();
        if(id != null){
            Map map = new HashMap<>();
            map.put("id",id);
            map.put("peopleStatus",redisTemplate.opsForHash().get("peopleStatus",String.valueOf(id)));
            list.add(map);
        }else{
            bdglPeopleService.selectBdglPeopleList(null).forEach(bdglPeopleBorrow -> {
                if(!redisTemplate.opsForHash().hasKey("peopleStatus",String.valueOf(bdglPeopleBorrow.getId()))){
                    redisTemplate.opsForHash().put("peopleStatus",String.valueOf(bdglPeopleBorrow.getId()),0);
                }
                Map map = new HashMap<>();
                map.put("id",bdglPeopleBorrow.getId());
                map.put("peopleStatus",redisTemplate.opsForHash().get("peopleStatus",String.valueOf(bdglPeopleBorrow.getId())));
                list.add(map);
            });
        }

        //遍历列表
        for (Map<String, Object> map : list){
            Long userId = (Long) map.get("id");
            BdglPeople bdglPeople = bdglPeopleService.selectBdglPeopleById(userId);
            BeanUtil.copyProperties(bdglPeople,map);
        }
        return AjaxResult.success(list);
    }

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