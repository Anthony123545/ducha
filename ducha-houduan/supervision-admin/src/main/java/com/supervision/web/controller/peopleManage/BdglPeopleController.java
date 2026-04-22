package com.supervision.web.controller.peopleManage;

import java.io.IOException;
import java.util.*;

import com.alibaba.excel.EasyExcel;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.peopleManage.domain.BdglPeople;
import com.supervision.peopleManage.domain.excel.BdglPeopleExcelData;
import com.supervision.peopleManage.listener.BdglPeopleExcelDataListener;
import com.supervision.system.service.ISysDeptService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;
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

import com.supervision.peopleManage.service.IBdglPeopleService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 人员管理Controller
 *
 * @author supervision
 * @date 2022-01-22
 */
@Transactional
@RestController
@RequestMapping("/peopleManage/people")
public class BdglPeopleController extends BaseController {
    @Autowired
    private IBdglPeopleService bdglPeopleService;
    @Autowired
    private ISysDeptService deptService;
    
    // 注入事务管理器
    @Autowired
    private PlatformTransactionManager transactionManager;

    /**
     * 查询人员管理列表
     */
    //@PreAuthorize("@ss.hasPermi('peopleManage:people:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglPeople bdglPeople) {
        LoginUser loginUser = getLoginUser();
        startPage();
//        if (loginUser.getDeptId() == 100 || loginUser.getDeptId() == 203 || loginUser.getDeptId() == 204 || loginUser.getDeptId() == 205 || loginUser.getDeptId() == 206) {
//
//            List<BdglPeople> list = bdglPeopleService.selectBdglPeopleList(bdglPeople);
//            return getDataTable(list);
//        }
//        bdglPeople.setDeptId(loginUser.getDeptId());
        List<BdglPeople> list = bdglPeopleService.selectPowerPeoleLists(bdglPeople);
        return getDataTable(list);
    }
    @GetMapping("/getlist")
    public AjaxResult getlist(BdglPeople bdglPeople){
        List<BdglPeople> list = bdglPeopleService.selectBdglPeopleList(bdglPeople);
        return AjaxResult.success(list);
    }

    /**
     * 导出人员管理列表
     */
    @PreAuthorize("@ss.hasPermi('peopleManage:people:export')")
    @Log(title = "人员管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglPeople bdglPeople) {
        List<BdglPeople> list = bdglPeopleService.selectPowerPeoleLists(bdglPeople);
        ExcelUtil<BdglPeople> util = new ExcelUtil<BdglPeople>(BdglPeople.class);
        return util.exportExcel(list, "人员管理数据");
    }



    /**
     * 获取人员管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('peopleManage:people:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(bdglPeopleService.selectBdglPeopleById(id));
    }

    @GetMapping("/getPeopleExaminationList")
    public TableDataInfo getPeopleExaminationList(BdglPeople bdglPeople){
        startPage();
        List<BdglPeople> bdglPeople1 = bdglPeopleService.selectExaminationList(bdglPeople);
        return getDataTable(bdglPeople1);
    }

    @GetMapping("/getPeopleExaminationcount")
    public AjaxResult getPeopleExaminationcount(BdglPeople bdglPeople){
        BdglPeople bdglPeoplen = new BdglPeople();
        List<BdglPeople> bdglPeople1 = bdglPeopleService.selectPowerPeoleLists(bdglPeoplen);
        Map<String,Object> map=bdglPeopleService.examinationCountss(bdglPeople);
        Long number = (Long) map.get("job");

        Map numMap = new HashMap<>();
        numMap.put("counts",bdglPeople1.size());
        numMap.put("ytijian",number);

        return AjaxResult.success(numMap);
    }

    @PreAuthorize("@ss.hasPermi('peopleManage:people:Examinationedit')")
    @Log(title = "人员管理", businessType = BusinessType.UPDATE)
    @PutMapping("/Examinationedit")
    public AjaxResult Examinationedit(@RequestBody BdglPeople bdglPeople) {
        return toAjax(bdglPeopleService.updateBdglPeople(bdglPeople));
    }

    /**
     * 新增人员管理
     */
    @PreAuthorize("@ss.hasPermi('peopleManage:people:add')")
    @Log(title = "人员管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglPeople bdglPeople) {
        return toAjax(bdglPeopleService.insertBdglPeople(bdglPeople));
    }

    /**
     * 修改人员管理
     */
    @PreAuthorize("@ss.hasPermi('peopleManage:people:edit')")
    @Log(title = "人员管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglPeople bdglPeople) {
        return toAjax(bdglPeopleService.updateBdglPeople(bdglPeople));
    }

    /**
     * 删除人员管理
     */
    @PreAuthorize("@ss.hasPermi('peopleManage:people:remove')")
    @Log(title = "人员管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(bdglPeopleService.deleteBdglPeopleByIds(ids));
    }

    /**
     * 查询此人是否存在
     *
     * @param bdglPeople
     * @return
     */
    @GetMapping("/getStaffManagement")
    public AjaxResult getStaffManagement(BdglPeople bdglPeople) {
        return AjaxResult.success(bdglPeopleService.selectBdglPeopleList(bdglPeople));
    }
    @GetMapping("/countPeopletype")
    public AjaxResult countPeopletype(String deptName) {
        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        SysDept sysDept = new SysDept();
        sysDept.setDeptName(deptName);
        List<SysDept> sysDepts = deptService.selectDeptLists(sysDept);
        if (sysDepts != null) {
            BdglPeople bdglPeople = new BdglPeople();
            bdglPeople.setDeptId(sysDepts.get(0).getDeptId());
            List<HashMap<String, Object>> bdglPeople1 = bdglPeopleService.selectGroupBYPeope(bdglPeople);

            return AjaxResult.success(bdglPeople1);
        }
        return AjaxResult.success();
    }

    @GetMapping("/list2")
    public TableDataInfo selectListForAll(BdglPeople bdglPeople) {
        startPage();
        List<BdglPeople> list = bdglPeopleService.selectPowerPeoleLists(bdglPeople);
        return getDataTable(list);
    }


    /**
     * 统计
     * */
    @GetMapping("/countNumber")
    public AjaxResult countNumber(){
        LoginUser loginUser = getLoginUser();
        BdglPeople bdglPeople = new BdglPeople();
        bdglPeople.setDeptId(100L);

        Map<String, Object> deptXiangQi = bdglPeopleService.getDeptXiangQi2(bdglPeople);//军官人数,士兵人数,义务兵人数
        List<BdglPeople> peoples  = bdglPeopleService.countTypeNumber();//各单位人数
        Long aa=0L;
        for (BdglPeople people : peoples) {
            aa+=people.getId();
        }
        deptXiangQi.put("zongRenShu",aa);

        deptXiangQi.put("dept",peoples);
        SysDept sysDept1 = new SysDept();
        sysDept1.setParentId(100L);
        List<SysDept> sysDepts = deptService.selectAllDeptList(sysDept1);
        ArrayList<Map<String, Object>> list = new ArrayList<>();
        for (SysDept sysDept : sysDepts) {
            //营级统计
            bdglPeople.setDeptId(sysDept.getDeptId());
            Map<String, Object> deptXiangQi2 = bdglPeopleService.getDeptXiangQi2(bdglPeople);//军官人数,士兵人数,义务兵人数
            deptXiangQi2.put("name",sysDept.getDeptName());
            deptXiangQi2.put("type",1);
            list.add(deptXiangQi2);
            //当前营各连统计
            SysDept sysDept2 = new SysDept();
            sysDept2.setParentId(sysDept.getDeptId());
            List<SysDept> sysDepts1 = deptService.selectAllDeptList(sysDept2);
            for (SysDept dept : sysDepts1) {
                bdglPeople.setDeptId(dept.getDeptId());
                Map<String, Object> deptXiangQi5 = bdglPeopleService.getDeptXiangQi3(bdglPeople);//军官人数,士兵人数,义务兵人数
                deptXiangQi5.put("name",dept.getDeptName());
                deptXiangQi5.put("type",2);
                list.add(deptXiangQi5);
            }
        }
        deptXiangQi.put("各单位",list);

        return AjaxResult.success(deptXiangQi);
    }

    //生成excel导入模版，并将文件返回给前端
    @GetMapping("/excel/template")
    public void importTemplate(HttpServletResponse response){

        ExcelUtil<BdglPeopleExcelData> util = new ExcelUtil<BdglPeopleExcelData>(BdglPeopleExcelData.class);
        List<BdglPeopleExcelData> list = new ArrayList<>();
        List<Object[]> fields = util.getFields();
        List<Map<String, Object>> maps = new ArrayList<>();
        for (Object[] objects : fields) {
            Map<String, Object> map = new HashMap<>();
            BdglPeopleExcelData excelData = new BdglPeopleExcelData();
            Excel attr = (Excel) objects[1];
            map.put("items", bdglPeopleService.getExcelHeaderItems(attr));
            map.put("field", objects);
            maps.add(map);
        }
        util.exportDemo(list,maps,"人员管理模版",response);
    }

    //excel导入
    @PostMapping("/excel/import")
    public AjaxResult importData(MultipartFile file) throws IOException {
        // 通过构造函数传递service依赖和事务管理器，支持事务回滚
        BdglPeopleExcelDataListener listener = new BdglPeopleExcelDataListener(bdglPeopleService,deptService,transactionManager);
        try {
            EasyExcel.read(file.getInputStream(), listener).sheet().doRead();
            if (listener.getStatus() == true){
                return AjaxResult.success("导入成功");
            }else{
                return AjaxResult.error("导入第"+listener.getReadCount()+"行数据失败");
            }
        } catch (Exception e) {
            // 发生异常时回滚事务
            listener.rollbackTransaction();
            return AjaxResult.error("导入失败：" + e.getMessage());
        }
    }
}