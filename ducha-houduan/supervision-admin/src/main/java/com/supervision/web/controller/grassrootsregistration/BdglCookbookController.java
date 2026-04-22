package com.supervision.web.controller.grassrootsregistration;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.supervision.common.core.domain.entity.SysRole;
import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.grassrootsregistration.domain.BdglCookbookRegistration;
import com.supervision.system.mapper.SysUserMapper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.supervision.common.annotation.Log;
import com.supervision.common.core.controller.BaseController;
import com.supervision.common.core.domain.AjaxResult;
import com.supervision.common.enums.BusinessType;
import com.supervision.grassrootsregistration.domain.BdglCookbook;
import com.supervision.grassrootsregistration.service.IBdglCookbookService;
import com.supervision.common.utils.poi.ExcelUtil;
import com.supervision.common.core.page.TableDataInfo;

import javax.servlet.http.HttpServletResponse;

/**
 * 一周食谱Controller
 * 
 * @author supervision
 * @date 2022-02-26
 */
@RestController
@RequestMapping("/grassrootsregistration/bdglcookbook")
public class BdglCookbookController extends BaseController
{
    @Autowired
    private IBdglCookbookService bdglCookbookService;
    @Autowired
    private SysUserMapper userMapper;

    /**
     * 查询一周食谱列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglcookbook:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglCookbook bdglCookbook)
    {
        LoginUser loginUser = getLoginUser();
        List<SysRole> sysRoles = userMapper.selectRole(loginUser.getUserId());
        String role = CommonsController.getRole(sysRoles);
        //判断当前角色是否是最高权限   不是返回当前部门数据
        if(!"admin".equals(role)){
            bdglCookbook.setUnitId(Integer.parseInt(loginUser.getDeptId().toString()));
        }
        startPage();
        List<BdglCookbook> list = bdglCookbookService.selectBdglCookbookList(bdglCookbook);
        return getDataTable(list);
    }

    /**
     * 导出一周食谱列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglcookbook:export')")
    @Log(title = "一周食谱", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(BdglCookbook bdglCookbook)
    {
        List<BdglCookbook> list = bdglCookbookService.selectBdglCookbookList(bdglCookbook);
        ExcelUtil<BdglCookbook> util = new ExcelUtil<BdglCookbook>(BdglCookbook.class);
        return util.exportExcel(list, "一周食谱数据");
    }

    /**
     * 导出指定食谱列表excel
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglcookbook:export')")
    /**
     * 导出一周食谱Excel
     * 完全按照表格格式实现导出功能
     */
    @GetMapping("/exportExcel/{id}")
    public void exportExcel(@PathVariable("id") Integer id, HttpServletResponse response) throws IOException {
        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("食谱表", "UTF-8") + ".xlsx";
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + fileName);

        // 创建Workbook和Sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("食谱表");

        // 创建样式
        CellStyle headerStyle = createHeaderStyle(workbook);
        CellStyle contentStyle = createContentStyle(workbook);

        // 从数据库获取数据
        BdglCookbook cookbookData = bdglCookbookService.selectBdglCookbookById(id);
        List<BdglCookbookRegistration> cookbookRegistrations = cookbookData.getCookbookRegistrations();

        // 构建数据映射
        Map<String, Map<String, Map<String, String>>> dataMap = buildDataMap(cookbookRegistrations);

        // 创建表头行
        Row headerRow = sheet.createRow(0);
        createHeaderCells(headerRow, headerStyle);

        // 创建数据行 - 按餐别和食物类型组织
        int rowIndex = 1;
        String[] mealTypes = {"早餐", "午餐", "晚餐"};
        String[] foodTypes = {"主食", "副食"};
        String[] weekdays = {"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};

        for (String mealType : mealTypes) {
            // 创建餐别行（合并行）
            Row mealRow = sheet.createRow(rowIndex);
            Cell mealCell = mealRow.createCell(0);
            mealCell.setCellValue(mealType);
            mealCell.setCellStyle(headerStyle);
            sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, 0, 0));

            for (int i = 0; i < foodTypes.length; i++) {
                String foodType = foodTypes[i];
                Row foodRow = (i == 0) ? mealRow : sheet.createRow(rowIndex + 1);

                // 设置食物类型单元格
                Cell foodCell = foodRow.createCell(1);
                foodCell.setCellValue(foodType);
                foodCell.setCellStyle(headerStyle);

                // 填充每个星期的数据
                for (int j = 0; j < weekdays.length; j++) {
                    String weekday = weekdays[j];
                    int colIndex = j + 2; // 从第3列开始是星期数据

                    // 获取对应的数据
                    String cookName = "";
                    String foodName = "";

                    if (dataMap.containsKey(weekday) && dataMap.get(weekday).containsKey(mealType)) {
                        Map<String, String> mealData = dataMap.get(weekday).get(mealType);
                        if ("主食".equals(foodType)) {
                            cookName = mealData.getOrDefault("stapleCook", "");
                            foodName = mealData.getOrDefault("stapleFood", "");
                        } else {
                            cookName = mealData.getOrDefault("nonStapleCook", "");
                            foodName = mealData.getOrDefault("nonStapleFood", "");
                        }
                    }

                    // 创建炊事员单元格
                    Cell cookCell = foodRow.createCell(colIndex);
                    cookCell.setCellValue("炊事员：" + (cookName == null? "" : cookName));
                    cookCell.setCellStyle(contentStyle);

                    // 创建食物单元格（在下方的行）
                    Row foodValueRow = (i == 0) ? sheet.createRow(rowIndex + 1) : foodRow;
                    Cell foodValueCell = foodValueRow.createCell(colIndex);
                    foodValueCell.setCellValue(foodName);
                    foodValueCell.setCellStyle(contentStyle);
                }
            }

            rowIndex += 2; // 每个餐别占用2行
        }

        // 设置列宽
        sheet.setColumnWidth(0, 15 * 256); // 时间列
        sheet.setColumnWidth(1, 15 * 256); // 食物类型列
        for (int i = 2; i <= 8; i++) { // 星期列
            sheet.setColumnWidth(i, 25 * 256);
        }

        // 写入响应流
        try (OutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
            workbook.close();
        }
    }

    // 创建表头样式
    private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    // 创建内容样式
    private CellStyle createContentStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        return style;
    }

    // 创建表头单元格
    private void createHeaderCells(Row headerRow, CellStyle headerStyle) {
        // 第一列
        Cell cell0 = headerRow.createCell(0);
        cell0.setCellValue("时间");
        cell0.setCellStyle(headerStyle);

        // 第二列
        Cell cell1 = headerRow.createCell(1);
        cell1.setCellValue("");
        cell1.setCellStyle(headerStyle);

        // 星期列
        String[] weekdays = {"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
        for (int i = 0; i < weekdays.length; i++) {
            Cell cell = headerRow.createCell(i + 2);
            cell.setCellValue(weekdays[i]);
            cell.setCellStyle(headerStyle);
        }
    }

    // 构建数据映射
    private Map<String, Map<String, Map<String, String>>> buildDataMap(List<BdglCookbookRegistration> cookbookRegistrations) {
        Map<String, Map<String, Map<String, String>>> dataMap = new HashMap<>();

        for (BdglCookbookRegistration registration : cookbookRegistrations) {
            String week = (String) registration.getWeeklist();
            String time = (String) registration.getTimelist();
            String type = (String) registration.getTypelist();
            String foodNames = (String) registration.getCookcategoryNames();
            String peopleNames = (String) registration.getPeopleNames();

            // 初始化映射结构
            dataMap.putIfAbsent(week, new HashMap<>());
            dataMap.get(week).putIfAbsent(time, new HashMap<>());

            // 存储食物和炊事员信息
            if ("主食".equals(type)) {
                dataMap.get(week).get(time).put("stapleFood", foodNames);
                dataMap.get(week).get(time).put("stapleCook", peopleNames);
            } else if ("副食".equals(type)) {
                dataMap.get(week).get(time).put("nonStapleFood", foodNames);
                dataMap.get(week).get(time).put("nonStapleCook", peopleNames);
            }
        }

        return dataMap;
    }

    /**
     * 获取一周食谱详细信息
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglcookbook:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        return AjaxResult.success(bdglCookbookService.selectBdglCookbookById(id));
    }

    /**
     * 新增一周食谱
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglcookbook:add')")
    @Log(title = "一周食谱", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglCookbook bdglCookbook)
    {
        return toAjax(bdglCookbookService.insertBdglCookbook(bdglCookbook));
    }

    /**
     * 修改一周食谱
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglcookbook:edit')")
    @Log(title = "一周食谱", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglCookbook bdglCookbook)
    {
        return toAjax(bdglCookbookService.updateBdglCookbook(bdglCookbook));
    }

    /**
     * 删除一周食谱
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:bdglcookbook:remove')")
    @Log(title = "一周食谱", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglCookbookService.deleteBdglCookbookByIds(ids));
    }

    /**获取食品*/
    @GetMapping("/getfood")
    public AjaxResult getFood(){
        HashMap<String, Object> cookcategorys=bdglCookbookService.getFood();
        return AjaxResult.success(cookcategorys);
    }
}
