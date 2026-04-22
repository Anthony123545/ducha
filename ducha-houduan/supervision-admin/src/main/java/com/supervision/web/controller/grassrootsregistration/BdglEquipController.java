package com.supervision.web.controller.grassrootsregistration;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.*;

import cn.hutool.core.date.DateUtil;
import com.supervision.common.DateUtils;
import com.supervision.common.core.domain.entity.SysRole;
import com.supervision.common.core.domain.model.LoginUser;

import com.supervision.common.utils.StringUtils;
import com.supervision.grassrootsregistration.resultVo.SysDeptDto;
import com.supervision.system.mapper.SysUserMapper;
import com.supervision.system.service.ISysDeptService;
import com.supervision.system.service.ISysDictDataService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.supervision.common.annotation.Log;
import com.supervision.common.core.controller.BaseController;
import com.supervision.common.core.domain.AjaxResult;
import com.supervision.common.enums.BusinessType;
import com.supervision.grassrootsregistration.domain.BdglEquip;
import com.supervision.grassrootsregistration.service.IBdglEquipService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

import javax.xml.crypto.Data;

import static com.supervision.web.controller.grassrootsregistration.BdglWeekworkController.generateWord;


/**
 * 训练器材/教材登记薄Controller
 *
 * @author supervision
 * @date 2022-02-26
 */
@RestController
@RequestMapping("/grassrootsregistration/bdglequip")
public class BdglEquipController extends BaseController {
    @Autowired
    private IBdglEquipService bdglEquipService;
    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private ISysDictDataService dictDataService;

    @Autowired
    private ISysDeptService deptService;

    /**
     * 查询训练器材/教材登记薄列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglequip:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglEquip bdglEquip) {
        LoginUser loginUser = getLoginUser();
        List<SysRole> sysRoles = userMapper.selectRole(loginUser.getUserId());
        String role = CommonsController.getRole(sysRoles);
        //判断当前角色是否是最高权限   不是返回当前部门数据
        if (!role.equals("admin")) {
            bdglEquip.setUnitId(Integer.parseInt(loginUser.getDeptId().toString()));
        }
        startPage();
        List<BdglEquip> list = bdglEquipService.selectBdglEquipList(bdglEquip);
        return getDataTable(list);
    }
    /**
     * 领导查询训练器材/教材登记薄列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglequips:list')")
    @GetMapping("/equipList")
    public TableDataInfo equipList(String year) {
        if (StringUtils.isNotEmpty(year)){
            startPage();
            List<SysDeptDto> list = bdglEquipService.selectEquipList(year);
            return getDataTable(list);
        }
        List<List<SysDeptDto>> list = deptService.getMeetRecordsList();
        return getDataTable(list);
    }



    /**
     * 导出训练器材/教材登记薄列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglequip:export')")
    @Log(title = "训练器材/教材登记薄", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglEquip bdglEquip) {
        List<BdglEquip> list = bdglEquipService.selectBdglEquipList(bdglEquip);
        ExcelUtil<BdglEquip> util = new ExcelUtil<BdglEquip>(BdglEquip.class);
        return util.exportExcel(list, "训练器材/教材登记薄数据");
    }

    /**
     * 获取训练器材/教材登记薄详细信息
     */
    @PreAuthorize("@ss.hasAnyPermi('grassrootsregistration:bdglequip:query,grassrootsregistration:bdglequips:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(bdglEquipService.selectBdglEquipById(id));
    }

    @GetMapping("/equipunits")
    public AjaxResult getInfoUnit(Integer unitid,String year) {

        List<BdglEquip> bdglEquip = bdglEquipService.selectEquIpByunitId(unitid,year);
        Long i = 0L;
        for (BdglEquip equip : bdglEquip) {
            equip.setId(++i);
            equip.setInfo(equip.getInfo()==null?"":equip.getInfo());
        }
        return AjaxResult.success(bdglEquip);
    }

    @GetMapping("/exportEquip")
    public AjaxResult exportEquip(@RequestParam(value = "unitid", required = false) Integer unitid
            , @RequestParam(value = "path", required = false) String path) throws Exception {
        List<BdglEquip> bdglEquip = bdglEquipService.selectBdglByunitid(unitid);
        ArrayList<String> strings = new ArrayList<>();
        Long i = 0L;
        Date date = new Date();


        for (BdglEquip equip : bdglEquip) {
            equip.setId(++i);
            if (equip.getInfo() != null) {

                strings.add(equip.getInfo());
            }
        }
        BdglEquip bdglEquip1 = new BdglEquip();
        String time = DateUtil.format(date, "yyyy年MM月dd日");

        Map<String, Object> params = new HashMap<>();
        //获取本机ip
        InetAddress localHost = InetAddress.getLocalHost();
        String hostAddress = localHost.getHostAddress();
        params.put("TestList", bdglEquip);
        params.put("String", strings);
        params.put("time", time);
        String fileName = "训练器材,教材登记簿.docx";
        generateWord(params, "D:/supervision/uploadPath/upload/" + fileName, "D:\\supervision\\moban", "equipTemplate.xml");
        return AjaxResult.success("/dev-api/profile/upload/" + fileName);

    }

    /**
     * 新增训练器材/教材登记薄
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglequip:add')")
    @Log(title = "训练器材/教材登记薄", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglEquip bdglEquip) {
        return toAjax(bdglEquipService.insertBdglEquip(bdglEquip));
    }

    /**
     * 修改训练器材/教材登记薄
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglequip:edit')")
    @Log(title = "训练器材/教材登记薄", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglEquip bdglEquip) {
        return toAjax(bdglEquipService.updateBdglEquip(bdglEquip));
    }

    /**
     * 删除训练器材/教材登记薄
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglequip:remove')")
    @Log(title = "训练器材/教材登记薄", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(bdglEquipService.deleteBdglEquipByIds(ids));
    }
}
