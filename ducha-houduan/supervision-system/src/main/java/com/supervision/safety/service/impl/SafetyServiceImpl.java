package com.supervision.safety.service.impl;

import com.supervision.safety.domain.*;
import com.supervision.safety.mapper.SafetyIssueMapper;
import com.supervision.safety.service.SafetyService;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SafetyServiceImpl implements SafetyService {

    @Resource
    private SafetyIssueMapper mapper;

    @Override
    public Long createIssue(CreateIssueReq req) {
        SafetyIssue i = new SafetyIssue();
        i.setCheckTarget(req.getCheckTarget());
        i.setHazardName(req.getHazardName());
        i.setDescription(req.getDescription());
        i.setRiskLevel(req.getRiskLevel());
        i.setStatus(IssueStatus.PENDING);
        i.setFoundAt(req.getFoundAt());
        i.setDeleted(false);
        mapper.insert(i);
        return i.getId();
    }

    @Override
    public void updateIssue(SafetyIssue issue) {
        mapper.update(issue);
    }

    @Override
    public void updateIssueStatus(Long id, IssueStatus status) {
        mapper.updateStatus(id, status, new Date());
    }

    @Override
    public void deleteIssue(Long id) {
        mapper.logicalDelete(id);
    }

    @Override
    public PageResult<SafetyIssue> search(String keyword, RiskLevel level, IssueStatus status, Date from, Date to, int pageNum, int pageSize) {
        int offset = (pageNum - 1) * pageSize;
        List<SafetyIssue> list = mapper.search(keyword, level, status, from, to, pageSize, offset);
        long total = mapper.count(keyword, level, status, from, to);
        return new PageResult<>(total, pageNum, pageSize, list);
    }

    @Override
    public void exportToWord(List<Long> ids, HttpServletResponse response) throws IOException {
        // 1. 获取数据
        List<SafetyIssue> list = mapper.findByIds(ids);

        // 2. 创建文档
        XWPFDocument doc = new XWPFDocument();

        // 设置标题
        XWPFParagraph p = doc.createParagraph();
        p.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun r = p.createRun();
        r.setText("安全隐患排查台账");
        r.setBold(true);
        r.setFontSize(18);
        r.addBreak();

        // 3. 定义完整的表头（与前端对齐，共 12 列示例）
        String[] titles = {
                "序号", "排查对象", "隐患名称", "隐患详情", "等级", "责任人",
                "整治措施", "验收时间", "验收单位", "验收结论", "销账时间", "备注"
        };

        // 4. 创建表格 (行数 = 数据量 + 1, 列数 = titles.length)
        XWPFTable table = doc.createTable(list.size() + 1, titles.length);
        table.setWidth("100%"); // 设置表格宽度占满页面

        // 5. 填充表头并设置背景色
        XWPFTableRow headerRow = table.getRow(0);
        for (int i = 0; i < titles.length; i++) {
            XWPFTableCell cell = headerRow.getCell(i);
            cell.setText(titles[i]);
            cell.setColor("F2F2F2"); // 浅灰色背景
        }

        // 6. 填充数据
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < list.size(); i++) {
            SafetyIssue item = list.get(i);
            XWPFTableRow row = table.getRow(i + 1);

            row.getCell(0).setText(String.valueOf(i + 1));                   // 序号
            row.getCell(1).setText(nvl(item.getCheckTarget()));              // 排查对象
            row.getCell(2).setText(nvl(item.getHazardName()));               // 隐患名称
            row.getCell(3).setText(nvl(item.getDescription()));              // 详情
            row.getCell(4).setText(nvl(item.getHazardCategory()));           // 类别
            row.getCell(5).setText(nvl(item.getHazardSource()));             // 来源

            // 等级转中文显示
            row.getCell(6).setText(translateRiskLevel(item.getRiskLevel()));

            row.getCell(7).setText(nvl(item.getDept()));                     // 责任单位
            row.getCell(8).setText(nvl(item.getPerson()));                   // 责任人

            // 时间格式化
            row.getCell(9).setText(item.getFoundAt() != null ? sdf.format(item.getFoundAt()) : "-");

            // 状态转中文显示
            row.getCell(10).setText(item.getStatus() == IssueStatus.RECTIFIED ? "已整改" : "未整改");
            // 新增字段填充
            row.getCell(11).setText(nvl(item.getRectificationMeasures())); // 整治措施
            row.getCell(12).setText(item.getAcceptanceTime() != null ? sdf.format(item.getAcceptanceTime()) : "-");
            row.getCell(13).setText(nvl(item.getAcceptanceUnit()));
            row.getCell(14).setText(nvl(item.getAcceptanceConclusion()));
            row.getCell(15).setText(item.getCloseTime() != null ? sdf.format(item.getCloseTime()) : "-");
            row.getCell(16).setText(nvl(item.getRemark()));
        }

        // 7. 输出文件流
        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        response.setHeader("Content-Disposition", "attachment; filename=Safety_Report.docx");
        doc.write(response.getOutputStream());
        doc.close();
    }

    // 辅助方法 1：处理空字符串，防止报错
    private String nvl(String str) {
        return str == null ? "-" : str;
    }

    // 辅助方法 2：等级转中文
    private String translateRiskLevel(RiskLevel level) {
        if (level == null) return "-";
        switch (level) {
//            case VERY_HIGH: return "重大隐患";
            case HIGH: return "重大隐患";
            case LOW: return "较大隐患";
            default: return "一般隐患";
        }
    }


    @Override
    public Map<String, Long> counters() {
        Map<String, Long> map = new HashMap<>();
        List<Map<String, Object>> rows = mapper.countPendingByLevel();
        for (Map<String, Object> r : rows) map.put(String.valueOf(r.get("lvl")), ((Number) r.get("cnt")).longValue());
        return map;
    }

    @Override
    public List<Map<String, Object>> weeklyTrend(int days) {
        // 简化实现...
        return new ArrayList<>();
    }

    @Override
    public List<SafetyIssue> top5High() { return mapper.top5High(); }
}
