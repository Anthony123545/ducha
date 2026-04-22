package com.supervision.peopleManage.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellExtra;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.supervision.common.DateUtils;
import com.supervision.common.core.domain.entity.SysDictData;
import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.peopleManage.domain.BdglPeople;
import com.supervision.peopleManage.domain.excel.BdglPeopleExcelData;
import com.supervision.peopleManage.service.IBdglPeopleService;
import com.supervision.peopleManage.service.impl.BdglPeopleServiceImpl;
import com.supervision.system.mapper.SysDeptMapper;
import com.supervision.system.service.ISysDeptService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.io.IOException;
import java.util.Map;
import java.util.List;

@Data
@Slf4j
public class BdglPeopleExcelDataListener extends AnalysisEventListener<Map<Integer, String>> {

    private IBdglPeopleService bdglPeopleService;

    private ISysDeptService sysDeptService;

    private Map<Integer, String> headerMap;

    private Long readCount = 0L;

    private Boolean status = true;

    // 添加事务管理器
    private PlatformTransactionManager transactionManager;

    // 事务状态
    private TransactionStatus transactionStatus;

    public BdglPeopleExcelDataListener(IBdglPeopleService bdglPeopleService,ISysDeptService sysDeptService, PlatformTransactionManager transactionManager) {
        this.bdglPeopleService = bdglPeopleService;
        this.sysDeptService = sysDeptService;
        this.transactionManager = transactionManager;
        // 开启事务
        startTransaction();
    }

    public BdglPeopleExcelDataListener() {
        // 无参构造函数
    }

    /**
     * 开启事务
     */
    private void startTransaction() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        this.transactionStatus = transactionManager.getTransaction(def);
        log.info("开启事务");
    }

    /**
     * 提交事务
     */
    public void commitTransaction() {
        if (transactionStatus != null && !transactionStatus.isCompleted()) {
            transactionManager.commit(transactionStatus);
            log.info("提交事务");
        }
    }

    /**
     * 回滚事务
     */
    public void rollbackTransaction() {
        if (transactionStatus != null && !transactionStatus.isCompleted()) {
            transactionManager.rollback(transactionStatus);
            log.info("回滚事务");
        }
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        this.headerMap = headMap;
        log.info("解析到表头:{}", headMap);
    }

    @Override
    public void invoke(Map<Integer, String> data, AnalysisContext context) {
        log.info("解析到一条数据:{}", data);
        readCount++;
        // 先转换Map中的数据值
        Map<Integer, String> convertedData = convertMapData(data);

        // 再构建BdglPeopleExcelData对象
        BdglPeopleExcelData excelData = buildBdglPeopleExcelData(convertedData);

        BdglPeople people = excelData.buildBdglPeople();
        // 调用BdglPeopleService的插入数据
        int result = 0;
        try {
            result = bdglPeopleService.insertBdglPeople(people);
        } catch (Exception e) {
            log.error("插入数据失败:{}", e.getMessage());
            status = false;
            // 发生异常时回滚事务
            rollbackTransaction();
            throw new RuntimeException("数据插入失败，事务已回滚", e);
        }finally {
            if (result < 0){
                log.info("数据插入失败");
                status = false;
                rollbackTransaction();
            }
        }
    }

    /**
     * 转换Map中的数据值，将文本标签转换为对应的键值
     *
     * @param data 原始Map数据
     * @return 转换后的Map数据
     */
    private Map<Integer, String> convertMapData(Map<Integer, String> data) {
        // 创建一个新的Map来存储转换后的数据
        new java.util.HashMap<Integer, String>(data);

        // 遍历数据Map，根据列索引和表头进行值转换
        for (Map.Entry<Integer, String> entry : data.entrySet()) {
            Integer columnIndex = entry.getKey();
            String value = entry.getValue();
            String header = headerMap.get(columnIndex);

            if (header == null || value == null || value.isEmpty()) {
                continue;
            }

            // 根据表头名称对值进行转换
            String convertedValue = convertValueByHeader(header, value);
            data.put(columnIndex, convertedValue);
        }

        return data;
    }

    /**
     * 根据表头名称将文本值转换为对应的键值
     *
     * @param header 表头名称
     * @param value  原始值
     * @return 转换后的值
     */
    private String convertValueByHeader(String header, String value) {
        switch (header) {
            case "民族":
                // 尝试将民族名称转换为ID
                String nationId = bdglPeopleService.getDictValueByLabel("sys_familyName", value);
                return nationId != null ? nationId : value;

            case "性别":
                // 尝试将性别名称转换为代码值
                String sexValue = bdglPeopleService.getDictValueByLabel("sys_user_sex", value);
                return sexValue != null ? sexValue : value;

            case "血型":
                // 尝试将血型名称转换为代码值
                String bloodTypeValue = bdglPeopleService.getDictValueByLabel("bloodType", value);
                return bloodTypeValue != null ? bloodTypeValue : value;

            case "政治面貌":
                // 尝试将政治面貌名称转换为ID
                String partyId = bdglPeopleService.getDictValueByLabel("Caucus", value);
                return partyId != null ? partyId : value;

            case "健康情况":
                // 尝试将健康情况名称转换为代码值
                String healthyValue = bdglPeopleService.getDictValueByLabel("healthy", value);
                return healthyValue != null ? healthyValue : value;

            case "学历":
                // 尝试将学历名称转换为ID
                String educationId = bdglPeopleService.getDictValueByLabel("Degree_level", value);
                return educationId != null ? educationId : value;

            case "学位":
                // 尝试将学位名称转换为ID
                String degreeId = bdglPeopleService.getDictValueByLabel("degree", value);
                return degreeId != null ? degreeId : value;

            case "人员类别":
                // 尝试将人员类别名称转换为ID
                String classId = bdglPeopleService.getDictValueByLabel("personnel_type", value);
                return classId != null ? classId : value;

            case "职务层级":
                // 尝试将职务层级名称转换为ID
                String postId = bdglPeopleService.getDictValueByLabel("post_Level", value);
                return postId != null ? postId : value;

            case "军衔":
                // 尝试将军衔名称转换为ID
                String rankId = bdglPeopleService.getDictValueByLabel("sys_user_rank", value);
                return rankId != null ? rankId : value;

            case "是否编制":
                // 尝试将是否编制名称转换为代码值
                String authorizedStrengthValue = bdglPeopleService.getDictValueByLabel("sys_yes_no", value);
                return authorizedStrengthValue != null ? authorizedStrengthValue : value;

            case "专业是否对口":
                // 尝试将专业是否对口名称转换为代码值
                String aboralValue = bdglPeopleService.getDictValueByLabel("sys_yes_no", value);
                return aboralValue != null ? aboralValue : value;

            case "单位":
                // 尝试将单位名称转换为ID
                Long deptId = bdglPeopleService.getDeptIdByName(value);
                return deptId != null ? String.valueOf(deptId) : value;

            default:
                // 其他字段不需要转换，直接返回原值
                return value;
        }
    }

    /**
     * 构建BdglPeopleExcelData对象
     *
     * @param data 转换后的Map数据
     * @return BdglPeopleExcelData对象
     */
    private BdglPeopleExcelData buildBdglPeopleExcelData(Map<Integer, String> data) {
        BdglPeopleExcelData excelData = new BdglPeopleExcelData();

        // 遍历数据Map，根据列索引和表头进行映射
        for (Map.Entry<Integer, String> entry : data.entrySet()) {
            Integer columnIndex = entry.getKey();
            String value = entry.getValue();
            String header = headerMap.get(columnIndex);

            if (header == null || value == null) {
                continue;
            }

            // 根据表头名称映射到对应的字段
            switch (header) {
                case "工号":
                    excelData.setEmployeeNumber(value);
                    break;
                case "姓名":
                    excelData.setName(value);
                    break;
                case "身份证号":
                    excelData.setIdcard(value);
                    break;
                case "民族":
                    try {
                        excelData.setNationId(Integer.valueOf(value));
                    } catch (NumberFormatException e) {
                        // 如果转换失败，保持为null
                    }
                    break;
                case "单位":
                    try {
                        excelData.setDeptId(Long.valueOf(value));
                        excelData.setDeptName(sysDeptService.selectDeptById(excelData.getDeptId()).getDeptName());
                    } catch (NumberFormatException e) {
                        // 如果转换失败，保持为null
                    }
                    break;
                case "性别":
                    excelData.setSex(value);
                    break;
                case "年龄":
                    try {
                        excelData.setAge(Integer.valueOf(value));
                    } catch (NumberFormatException e) {
                        // 如果转换失败，保持为null
                    }
                    break;
                case "出生年月":
                    excelData.setBirthDate(DateUtils.parseDate(value));
                    break;
                case "籍贯":
                    excelData.setOrigin(value);
                    break;
                case "家庭住址":
                    excelData.setLive(value);
                    break;
                case "血型":
                    excelData.setBloodType(value);
                    break;
                case "政治面貌":
                    try {
                        excelData.setPartyId(Integer.valueOf(value));
                    } catch (NumberFormatException e) {
                        // 如果转换失败，保持为null
                    }
                    break;
                case "党团时间":
                    excelData.setPartyDate(DateUtils.parseDate(value));
                    break;
                case "健康情况":
                    excelData.setHealthy(value);
                    break;
                case "保障卡号":
                    excelData.setGuaranteeCardNo(value);
                    break;
                case "工资卡号":
                    excelData.setPayrollCardNo(value);
                    break;
                case "婚姻情况":
                    excelData.setMarryList(value);
                    break;
                case "子女情况":
                    excelData.setChildrenList(value);
                    break;
                case "学历":
                    try {
                        excelData.setEducationId(Integer.valueOf(value));
                    } catch (NumberFormatException e) {
                        // 如果转换失败，保持为null
                    }
                    break;
                case "学位":
                    try {
                        excelData.setDegreeId(Integer.valueOf(value));
                    } catch (NumberFormatException e) {
                        // 如果转换失败，保持为null
                    }
                    break;
                case "入学时间":
                    excelData.setAdmissionTime(DateUtils.parseDate(value));
                    break;
                case "专业":
                    excelData.setMajor(value);
                    break;
                case "毕业时间":
                    excelData.setGraduationDate(DateUtils.parseDate(value));
                    break;
                case "毕业院校":
                    excelData.setSchool(value);
                    break;
                case "手机型号":
                    excelData.setMobilePhoneModel(value);
                    break;
                case "手机号码":
                    excelData.setPhone(value);
                    break;
                case "手机号码2":
                    excelData.setPhoneStandby(value);
                    break;
                case "军人证号":
                    excelData.setMilitary(value);
                    break;
                case "人员类别":
                    try {
                        excelData.setClassId(Integer.valueOf(value));
                    } catch (NumberFormatException e) {
                        // 如果转换失败，保持为null
                    }
                    break;
                case "部职别":
                    excelData.setDuty(value);
                    break;
                case "入伍时间":
                    excelData.setEnlistDate(DateUtils.parseDate(value));
                    break;
                case "入伍地点":
                    excelData.setEnlistaddress(value);
                    break;
                case "职务层级":
                    try {
                        excelData.setPostId(Integer.valueOf(value));
                    } catch (NumberFormatException e) {
                        // 如果转换失败，保持为null
                    }
                    break;
                case "职务等级时间":
                    excelData.setPostDate(DateUtils.parseDate(value));
                    break;
                case "军衔":
                    try {
                        excelData.setRankId(Integer.valueOf(value));
                    } catch (NumberFormatException e) {
                        // 如果转换失败，保持为null
                    }
                    break;
                case "现军衔":
                    excelData.setNowMilitaryRank(value);
                    break;
                case "现军衔时间":
                    excelData.setRankDate(DateUtils.parseDate(value));
                    break;
                case "是否编制":
                    excelData.setAuthorizedStrength(value);
                    break;
                case "命令号":
                    excelData.setCommand(value);
                    break;
                case "待遇级别":
                    excelData.setTreatmentLevel(value);
                    break;
                case "现待遇级别时间":
                    excelData.setRankUpgradeTime(DateUtils.parseDate(value));
                    break;
                case "专业是否对口":
                    excelData.setAboral(value);
                    break;
                case "工作时间":
                    excelData.setJobTime(DateUtils.parseDate(value));
                    break;
                case "在位备注":
                    excelData.setStatusrk(value);
                    break;
                case "亲属姓名":
                    excelData.setFamilyName1(value);
                    break;
                case "亲属与本人关系":
                    excelData.setCharacterRelationship1(value);
                    break;
                case "亲属联系电话":
                    excelData.setPhoneNumber1(value);
                    break;
                case "亲属单位名称":
                    excelData.setUnitName1(value);
                    break;
                case "培训经历":
                    excelData.setTraining(value);
                    break;
                case "奖惩情况":
                    excelData.setPrize(value);
                    break;
            }
        }

        return excelData;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("所有数据解析完成！");
        // 提交事务
        commitTransaction();
    }
}