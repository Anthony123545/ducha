package com.supervision.web.controller.bdglSecret;

import java.util.*;
import javax.servlet.http.HttpServletResponse;

import com.supervision.bdglSecret.service.IBdglSecretService;
import com.supervision.common.core.domain.entity.SysDictData;
import com.supervision.system.service.ISysDictDataService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.supervision.common.annotation.Log;
import com.supervision.common.core.controller.BaseController;
import com.supervision.common.core.domain.AjaxResult;
import com.supervision.common.enums.BusinessType;
import com.supervision.bdglSecret.domain.SecretAccount;
import com.supervision.bdglSecret.service.ISecretAccountService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

/**
 * 涉密计算机台账Controller
 *
 * @author lanse
 * @date 2022-10-30
 */
@RestController
@RequestMapping("/bdglSecret/secretaccount")
public class SecretAccountController extends BaseController
{
    @Autowired
    private ISecretAccountService secretAccountService;

    @Autowired
    private IBdglSecretService bdglSecretService;

    @Autowired
    private ISysDictDataService sysDictDataService;

    /**
     * 查询涉密计算机台账列表
     */
    @PreAuthorize("@ss.hasPermi('bdglSecret:secretaccount:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecretAccount secretAccount)
    {
        startPage();
        List<SecretAccount> list = secretAccountService.selectSecretAccountList(secretAccount);
        return getDataTable(list);
    }

    /**
     * 导出涉密计算机台账列表
     */
    @PreAuthorize("@ss.hasPermi('bdglSecret:secretaccount:export')")
    @Log(title = "涉密计算机台账", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecretAccount secretAccount)
    {
        List<SecretAccount> list = secretAccountService.selectSecretAccountList(secretAccount);
        ExcelUtil<SecretAccount> util = new ExcelUtil<SecretAccount>(SecretAccount.class);
        util.exportExcel(response, list, "涉密计算机台账数据");
    }

    /**
     * 获取涉密计算机台账详细信息
     */
    @PreAuthorize("@ss.hasPermi('bdglSecret:secretaccount:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(secretAccountService.selectSecretAccountById(id));
    }

    /**
     * 新增涉密计算机台账
     */
    @PreAuthorize("@ss.hasPermi('bdglSecret:secretaccount:add')")
    @Log(title = "涉密计算机台账", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecretAccount secretAccount)
    {
        return toAjax(secretAccountService.insertSecretAccount(secretAccount));
    }

    /**
     * 修改涉密计算机台账
     */
    @PreAuthorize("@ss.hasPermi('bdglSecret:secretaccount:edit')")
    @Log(title = "涉密计算机台账", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecretAccount secretAccount)
    {
        return toAjax(secretAccountService.updateSecretAccount(secretAccount));
    }

    /**
     * 删除涉密计算机台账
     */
    @PreAuthorize("@ss.hasPermi('bdglSecret:secretaccount:remove')")
    @Log(title = "涉密计算机台账", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(secretAccountService.deleteSecretAccountByIds(ids));
    }


    @PreAuthorize("@ss.hasPermi('bdglSecret:secretaccount:list')")
    @GetMapping("/getNumber")
    public AjaxResult getNumberByType(){
        SysDictData sysDictData = new SysDictData();
        sysDictData.setDictType("electronic");
        List<SysDictData> sysDictDataList = sysDictDataService.selectDictDataList(sysDictData);
        Map<String,Integer> dataMap = new HashMap<>();
        for (SysDictData dictData : sysDictDataList) {
            dataMap.put(dictData.getDictValue(),0);
        }

        SecretAccount secretAccount = new SecretAccount();
        List<SecretAccount> secretAccountList = secretAccountService.selectSecretAccountList(secretAccount);
        for (SecretAccount account : secretAccountList) {
            int i = dataMap.get(account.getStype());
            dataMap.put(account.getStype(),++i);
        }
        List<Map> listMap = new ArrayList<>();
        Set<String> keySet = dataMap.keySet();
        for (String s : keySet) {
            Integer i = dataMap.get(s);
            Map<String,String> map = new HashMap<>();
            map.put("num",i+"");
            for (SysDictData dictData : sysDictDataList) {
                if (dictData.getDictValue().equals(s)){
                    map.put("name",dictData.getDictLabel());
                    break;
                }
            }
            listMap.add(map);
        }
        return AjaxResult.success(listMap);
    }

}
