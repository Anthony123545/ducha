package com.supervision.web.controller.militaryvehicleManagement;

import com.github.pagehelper.PageInfo;
import com.supervision.common.core.domain.AjaxResult;
import com.supervision.militaryvehicleManagement.domain.AccessPeople;
import com.supervision.militaryvehicleManagement.service.IBdglAccessPeopleService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 审批人员管理
 */
@RestController
@RequestMapping("/militaryvehicleManagement/thebusConfig")
public class BdglAccessPeople {
    @Autowired
    private IBdglAccessPeopleService iBdglAccessPeopleService;

    /**
     * 新增审批人员
     *
     * @param accessPeople
     * @return
     */
    @PostMapping("/addAccessPeople")
    public AjaxResult addAccessPeople(@RequestBody AccessPeople accessPeople) {
        iBdglAccessPeopleService.add(accessPeople);
        return AjaxResult.success();
    }

    /**
     * 查询审批人员信息
     *
     * @param name
     * @param level
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/listAccessPeople")
    public AjaxResult listAccessPeople(@RequestParam(required = false) String name,
                                       @RequestParam(required = false) Integer level,
                                       @RequestParam(defaultValue = "1") int pageNum,
                                       @RequestParam(defaultValue = "10") int pageSize) {
        PageInfo<AccessPeople> pageInfo = iBdglAccessPeopleService.queryPage(name, level, pageNum, pageSize);
        Map<String, Object> result = new HashMap<>();
        result.put("total", pageInfo.getTotal());
        result.put("list", pageInfo.getList());

        return AjaxResult.success(result);
    }

    @GetMapping("/listAllAccessPeople")
    public AjaxResult listAllAccessPeople() {
        List<AccessPeople> result = iBdglAccessPeopleService.findAll();  // 查询所有人员
        return AjaxResult.success(result);
    }

    /**
     * 删除审批人员
     *
     * @param ids
     * @return
     */
    @DeleteMapping("/del")
    public AjaxResult delete(@RequestBody List<Integer> ids) {
        int result = iBdglAccessPeopleService.deleteByIds(ids);
        if (result > 0) {
            return AjaxResult.success(result);
        } else {
            return AjaxResult.error("删除失败");
        }
    }

    /**
     * 根据id获取审批人员信息
     *
     * @param id
     * @return
     */
    @GetMapping("/getAccessPeople/{id}")
    public AjaxResult getAccessPeople(@PathVariable int id) {
        AccessPeople accessPeople = iBdglAccessPeopleService.getById(id);
        if (accessPeople != null) {
            return AjaxResult.success(accessPeople);
        } else {
            return AjaxResult.error("未找到对应的审批人员");
        }
    }

    /**
     * 修改审批人员
     * @param accessPeople
     * @return
     */
    @PutMapping("/updateAccessPeople")
    public AjaxResult updateAccessPeople(@RequestBody AccessPeople accessPeople) {
        // 检查是否传入了 id
        if (accessPeople.getId() == 0) {
            return AjaxResult.error("缺少人员ID，无法更新");
        }

        // 调用业务层更新
        boolean success = iBdglAccessPeopleService.updateById(accessPeople);

        if (success) {
            return AjaxResult.success("修改成功");
        } else {
            return AjaxResult.error("修改失败，未找到对应人员");
        }
    }
}
