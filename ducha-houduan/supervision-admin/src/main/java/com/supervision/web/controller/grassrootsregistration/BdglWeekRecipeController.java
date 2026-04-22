package com.supervision.web.controller.grassrootsregistration;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

import com.supervision.common.core.domain.entity.SysRole;
import com.supervision.common.core.domain.model.LoginUser;
import com.supervision.system.mapper.SysUserMapper;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.supervision.common.annotation.Log;
import com.supervision.common.core.controller.BaseController;
import com.supervision.common.core.domain.AjaxResult;
import com.supervision.common.enums.BusinessType;
import com.supervision.grassrootsregistration.domain.BdglWeekRecipe;
import com.supervision.grassrootsregistration.service.IBdglWeekRecipeService;
import com.supervision.common.core.page.TableDataInfo;

import javax.servlet.http.HttpServletResponse;

/**
 * 一周食谱 Controller
 *
 * @author supervision
 * @date 2026-03-27
 */
@RestController
@RequestMapping("/grassrootsregistration/weekRecipe")
public class BdglWeekRecipeController extends BaseController
{
    @Autowired
    private IBdglWeekRecipeService bdglWeekRecipeService;
    @Autowired
    private SysUserMapper userMapper;

    /**
     * 查询一周食谱列表
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:weekRecipe:list')")
    @GetMapping("/list")
    public TableDataInfo list(BdglWeekRecipe bdglWeekRecipe)
    {
        LoginUser loginUser = getLoginUser();
        List<SysRole> sysRoles = userMapper.selectRole(loginUser.getUserId());
        String role = CommonsController.getRole(sysRoles);
        //判断当前角色是否是最高权限   不是返回当前部门数据
        if(!"admin".equals(role)){
            bdglWeekRecipe.setUnitId(Integer.parseInt(loginUser.getDeptId().toString()));
        }
        startPage();
        List<BdglWeekRecipe> list = bdglWeekRecipeService.selectBdglWeekRecipeList(bdglWeekRecipe);
        return getDataTable(list);
    }

    /**
     * 查询一周食谱详细
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:weekRecipe:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id)
    {
        BdglWeekRecipe weekRecipe = bdglWeekRecipeService.selectBdglWeekRecipeById(id);
        return AjaxResult.success(weekRecipe);
    }

    /**
     * 新增一周食谱
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:weekRecipe:add')")
    @Log(title = "一周食谱", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BdglWeekRecipe bdglWeekRecipe)
    {
        return toAjax(bdglWeekRecipeService.insertBdglWeekRecipe(bdglWeekRecipe));
    }

    /**
     * 修改一周食谱
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:weekRecipe:edit')")
    @Log(title = "一周食谱", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BdglWeekRecipe bdglWeekRecipe)
    {
        return toAjax(bdglWeekRecipeService.updateBdglWeekRecipe(bdglWeekRecipe));
    }

    /**
     * 删除一周食谱
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:weekRecipe:remove')")
    @Log(title = "一周食谱", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Integer[] ids)
    {
        return toAjax(bdglWeekRecipeService.deleteBdglWeekRecipeByIds(ids));
    }

    /**
     * 导出一周食谱 Word
     */
    @PreAuthorize("@ss.hasPermi('grassrootsregistration:weekRecipe:export')")
    @Log(title = "一周食谱", businessType = BusinessType.EXPORT)
    @GetMapping("/exportWord/{id}")
    public void exportWord(@PathVariable("id") Integer id, HttpServletResponse response) throws IOException {
        // 设置响应头
        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("一周食谱表", "UTF-8") + ".docx";
        response.setHeader("Content-Disposition", "attachment; filename*=UTF-8''" + fileName);

        // 获取数据
        BdglWeekRecipe recipe = bdglWeekRecipeService.selectBdglWeekRecipeById(id);

        // 创建 Word 文档
        XWPFDocument document = new XWPFDocument();

        // 创建标题
        XWPFParagraph titleParagraph = document.createParagraph();
        titleParagraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleRun = titleParagraph.createRun();
        titleRun.setText("一周食谱表");
        titleRun.setBold(true);
        titleRun.setFontSize(18);
        titleRun.setFontFamily("宋体");

        // 创建单位信息段落
        XWPFParagraph infoParagraph = document.createParagraph();
        infoParagraph.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun infoRun = infoParagraph.createRun();
        infoRun.setText("单位：" + (recipe.getUnitName() != null ? recipe.getUnitName() : ""));
        infoRun.setFontSize(12);
        infoRun.setFontFamily("宋体");

        // 创建时间信息
        XWPFRun timeRun = infoParagraph.createRun();
        String startTime = recipe.getStartTime() != null ? recipe.getStartTime().toString().substring(0, 10) : "";
        String endTime = recipe.getEndTime() != null ? recipe.getEndTime().toString().substring(0, 10) : "";
        timeRun.setText("    时间：" + startTime + " 至 " + endTime);
        timeRun.setFontSize(12);
        timeRun.setFontFamily("宋体");

        // 创建表格
        XWPFTable table = document.createTable(7, 8);

        // 设置表格样式
        for (XWPFTableRow row : table.getRows()) {
            for (XWPFTableCell cell : row.getTableCells()) {
                cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                XWPFParagraph paragraph = cell.getParagraphs().get(0);
                paragraph.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun run = paragraph.createRun();
                run.setFontSize(10);
                run.setFontFamily("宋体");
            }
        }

        // 第一行：表头
        XWPFTableRow headerRow = table.getRow(0);
        setTableCellText(headerRow.getCell(0), "餐次");
        setTableCellText(headerRow.getCell(1), "星期一");
        setTableCellText(headerRow.getCell(2), "星期二");
        setTableCellText(headerRow.getCell(3), "星期三");
        setTableCellText(headerRow.getCell(4), "星期四");
        setTableCellText(headerRow.getCell(5), "星期五");
        setTableCellText(headerRow.getCell(6), "星期六");
        setTableCellText(headerRow.getCell(7), "星期日");

        // 解析食谱内容 JSON
        String recipeContent = recipe.getRecipeContent();
        com.alibaba.fastjson.JSONObject contentJson = null;
        try {
            contentJson = com.alibaba.fastjson.JSON.parseObject(recipeContent);
        } catch (Exception e) {
            contentJson = new com.alibaba.fastjson.JSONObject();
        }

        // 早餐行
        XWPFTableRow breakfastRow = table.getRow(1);
        setTableCellText(breakfastRow.getCell(0), "早餐");
        setTableCellText(breakfastRow.getCell(1), getRecipeValue(contentJson, "monday", "breakfast"));
        setTableCellText(breakfastRow.getCell(2), getRecipeValue(contentJson, "tuesday", "breakfast"));
        setTableCellText(breakfastRow.getCell(3), getRecipeValue(contentJson, "wednesday", "breakfast"));
        setTableCellText(breakfastRow.getCell(4), getRecipeValue(contentJson, "thursday", "breakfast"));
        setTableCellText(breakfastRow.getCell(5), getRecipeValue(contentJson, "friday", "breakfast"));
        setTableCellText(breakfastRow.getCell(6), getRecipeValue(contentJson, "saturday", "breakfast"));
        setTableCellText(breakfastRow.getCell(7), getRecipeValue(contentJson, "sunday", "breakfast"));

        // 午餐行
        XWPFTableRow lunchRow = table.getRow(2);
        setTableCellText(lunchRow.getCell(0), "午餐");
        setTableCellText(lunchRow.getCell(1), getRecipeValue(contentJson, "monday", "lunch"));
        setTableCellText(lunchRow.getCell(2), getRecipeValue(contentJson, "tuesday", "lunch"));
        setTableCellText(lunchRow.getCell(3), getRecipeValue(contentJson, "wednesday", "lunch"));
        setTableCellText(lunchRow.getCell(4), getRecipeValue(contentJson, "thursday", "lunch"));
        setTableCellText(lunchRow.getCell(5), getRecipeValue(contentJson, "friday", "lunch"));
        setTableCellText(lunchRow.getCell(6), getRecipeValue(contentJson, "saturday", "lunch"));
        setTableCellText(lunchRow.getCell(7), getRecipeValue(contentJson, "sunday", "lunch"));

        // 晚餐行
        XWPFTableRow dinnerRow = table.getRow(3);
        setTableCellText(dinnerRow.getCell(0), "晚餐");
        setTableCellText(dinnerRow.getCell(1), getRecipeValue(contentJson, "monday", "dinner"));
        setTableCellText(dinnerRow.getCell(2), getRecipeValue(contentJson, "tuesday", "dinner"));
        setTableCellText(dinnerRow.getCell(3), getRecipeValue(contentJson, "wednesday", "dinner"));
        setTableCellText(dinnerRow.getCell(4), getRecipeValue(contentJson, "thursday", "dinner"));
        setTableCellText(dinnerRow.getCell(5), getRecipeValue(contentJson, "friday", "dinner"));
        setTableCellText(dinnerRow.getCell(6), getRecipeValue(contentJson, "saturday", "dinner"));
        setTableCellText(dinnerRow.getCell(7), getRecipeValue(contentJson, "sunday", "dinner"));

        // 签字行
        XWPFParagraph signParagraph = document.createParagraph();
        signParagraph.setAlignment(ParagraphAlignment.LEFT);
        signParagraph.setSpacingBefore(500);

        XWPFRun signRun1 = signParagraph.createRun();
        signRun1.setText("单位首长：" + (recipe.getLeaderSign() != null ? recipe.getLeaderSign() : ""));
        signRun1.setFontSize(12);
        signRun1.setFontFamily("宋体");
        signRun1.addBreak();

        XWPFRun signRun2 = signParagraph.createRun();
        signRun2.setText("军人委员会：" + (recipe.getCommitteeSign() != null ? recipe.getCommitteeSign() : ""));
        signRun2.setFontSize(12);
        signRun2.setFontFamily("宋体");
        signRun2.addBreak();

        XWPFRun signRun3 = signParagraph.createRun();
        signRun3.setText("司务长：" + (recipe.getMessOfficerSign() != null ? recipe.getMessOfficerSign() : ""));
        signRun3.setFontSize(12);
        signRun3.setFontFamily("宋体");

        // 写入响应流
        try (OutputStream outputStream = response.getOutputStream()) {
            document.write(outputStream);
            document.close();
        }
    }

    // 设置单元格文本
    private void setTableCellText(XWPFTableCell cell, String text) {
        XWPFParagraph paragraph = cell.getParagraphs().get(0);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run = paragraph.createRun();
        run.setText(text != null ? text : "");
        run.setFontSize(10);
        run.setFontFamily("宋体");
    }

    // 获取食谱值
    private String getRecipeValue(com.alibaba.fastjson.JSONObject contentJson, String day, String meal) {
        if (contentJson == null || contentJson.getJSONObject(day) == null) {
            return "";
        }
        String value1 = contentJson.getJSONObject(day).getString(meal);
        String value2 = contentJson.getJSONObject(day).getString(meal + "2");
        if (value1 == null) value1 = "";
        if (value2 == null) value2 = "";
        return value1 + (value2 != null && !value2.isEmpty() ? "\n" + value2 : "");
    }
}
