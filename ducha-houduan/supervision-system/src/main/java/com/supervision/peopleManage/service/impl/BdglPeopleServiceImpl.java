package com.supervision.peopleManage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.supervision.common.DateUtils;
import com.supervision.common.annotation.DataScope;
import com.supervision.common.annotation.Excel;
import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.common.core.domain.entity.SysDictData;
import com.supervision.grassrootsregistration.resultVo.SysDeptDto;
import com.supervision.peopleManage.domain.BdglPeople;
import com.supervision.peopleManage.domain.FamilyInformation;
import com.supervision.peopleManage.domain.dto.StatOutPeopleDto;
import com.supervision.peopleManage.domain.excel.BdglPeopleExcelData;
import com.supervision.peopleManage.mapper.BdglPeopleMapper;
import com.supervision.peopleManage.mapper.FamilyInformationMapper;
import com.supervision.peopleManage.service.IBdglPeopleService;
import com.supervision.system.mapper.SysDeptMapper;
import com.supervision.system.service.ISysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 人员管理Service业务层处理
 *
 * @author supervision
 * @date 2022-01-22
 */
@Transactional()
@Service
public class BdglPeopleServiceImpl implements IBdglPeopleService
{
    @Autowired
    private BdglPeopleMapper bdglPeopleMapper;
    @Autowired
    private FamilyInformationMapper familyInformationMapper;

    @Resource
    private SysDeptMapper deptMapper;
    @Autowired
    ISysDictTypeService dictTypeService;

    /**
     * 根据字典类型和标签获取字典值
     *
     * @param dictType 字典类型
     * @param label 字典标签
     * @return 字典值
     */

    @Override
    public String getDictValueByLabel(String dictType, String label) {
        if (label == null || label.isEmpty()) {
            return null;
        }
        
        List<SysDictData> dictDatas = dictTypeService.selectDictDataByType(dictType);
        for (SysDictData dictData : dictDatas) {
            if (label.equals(dictData.getDictLabel())) {
                return dictData.getDictValue();
            }
        }
        return null;
    }

    /**
     * 根据部门名称获取部门ID
     *
     * @param deptName 部门名称
     * @return 部门ID
     */
    @Override
    public Long getDeptIdByName(String deptName) {
        if (deptName == null || deptName.isEmpty()) {
            return null;
        }
        
        SysDept dept = new SysDept();
        dept.setDeptName(deptName);
        List<SysDept> depts = deptMapper.selectDeptList(dept);
        if (!depts.isEmpty()) {
            return depts.get(0).getDeptId();
        }
        return null;
    }

    /**
     * 将BdglPeople对象转换为BdglPeopleExcelData对象（用于导出）
     *
     * @param people BdglPeople对象
     * @return BdglPeopleExcelData对象
     */
    public BdglPeopleExcelData convertToExcelData(BdglPeople people) {
        if (people == null) {
            return null;
        }
        
        BdglPeopleExcelData excelData = new BdglPeopleExcelData();
        
        // 基本信息
        excelData.setName(people.getName());
        excelData.setIdcard(people.getIdcard());
        excelData.setNationId(people.getNationId());
        excelData.setSex(people.getSex());
        excelData.setAge(people.getAge());
        excelData.setBirthDate(people.getBirthDate());
        excelData.setOrigin(people.getOrigin());
        excelData.setLive(people.getLive());
        excelData.setBloodType(people.getBloodType());
        excelData.setPartyId(people.getPartyId());
        excelData.setPartyDate(people.getPartyDate());
        excelData.setHealthy(people.getHealthy());
        excelData.setGuaranteeCardNo(people.getGuaranteeCardNo());
        excelData.setPayrollCardNo(people.getPayrollCardNo());
        
        // 婚姻信息
        excelData.setMarryList(people.getMarryList());
        excelData.setChildrenList(people.getChildrenList());
        
        // 学历信息
        excelData.setEducationId(people.getEducationId());
        excelData.setDegreeId(people.getDegreeId());
        excelData.setAdmissionTime(people.getAdmissionTime());
        excelData.setMajor(people.getMajor());
        excelData.setGraduationDate(people.getGraduationDate());
        excelData.setSchool(people.getSchool());
        
        // 联系方式
        excelData.setMobilePhoneModel(people.getMobilePhoneModel());
        excelData.setPhone(people.getPhone());
        excelData.setPhoneStandby(people.getPhoneStandby());
        
        // 职务信息
        excelData.setMilitary(people.getMilitary());
        excelData.setClassId(people.getClassId());
        excelData.setDeptId(people.getDeptId());
        excelData.setDuty(people.getDuty());
        excelData.setEnlistDate(people.getEnlistDate());
        excelData.setEnlistaddress(people.getEnlistaddress());
        excelData.setPostId(people.getPostId());
        excelData.setPostDate(people.getPostDate());
        excelData.setRankId(people.getRankId());
        excelData.setNowMilitaryRank(people.getNowMilitaryRank());
        excelData.setRankDate(people.getRankDate());
        excelData.setAuthorizedStrength(people.getAuthorizedStrength());
        excelData.setCommand(people.getCommand());
        excelData.setTreatmentLevel(people.getTreatmentLevel());
        excelData.setRankUpgradeTime(people.getRankUpgradeTime());
        excelData.setAboral(people.getAboral());
        excelData.setJobTime(people.getJobTime());
        excelData.setStatusrk(people.getStatusrk());
        
        // 家庭信息
        List<FamilyInformation> familyInfoList = people.getFamilyInformation();
        if (familyInfoList != null && !familyInfoList.isEmpty()) {
            FamilyInformation familyInfo = familyInfoList.get(0); // 只取第一个
            excelData.setFamilyName1(familyInfo.getName());
            excelData.setCharacterRelationship1(familyInfo.getCharacterRelationship());
            excelData.setPhoneNumber1(familyInfo.getPhoneNumber());
            excelData.setUnitName1(familyInfo.getUnitName());
        }
        
        // 培训经历和奖惩情况
        excelData.setTraining(people.getTraining());
        excelData.setPrize(people.getPrize());

        return excelData;
    }

    /**
     * 将Excel数据转换为BdglPeople对象（用于导入）
     *
     * @param excelData Excel数据对象
     * @return BdglPeople对象
     */
    public BdglPeople convertFromExcelData(BdglPeopleExcelData excelData) {
        return excelData.buildBdglPeople();
    }

    /**
     * 查询人员管理
     *
     * @param id 人员管理主键
     * @return 人员管理
     */
    @Override
    public BdglPeople selectBdglPeopleById(Long id)
    {
        /*----------------------------返回家庭信息------------------------------*/
        FamilyInformation familyInformation = new FamilyInformation();
        familyInformation.setPeopleId(id);
        List<FamilyInformation> familyInformations = familyInformationMapper.selectFamilyInformationList(familyInformation);
        
        /*----------------------------返回家庭信息------------------------------*/
        BdglPeople bdglPeople = bdglPeopleMapper.selectBdglPeopleById(id);
        if(familyInformations!=null&&familyInformations.size()>0&&bdglPeople!=null) {
        	bdglPeople.setFamilyInformation(familyInformations);
        }else{
            bdglPeople.setFamilyInformation(new ArrayList<>());
        }
        
        return bdglPeople;
    }

    /**
     * 权限查询数据列表
     * @param bdglPeople
     * @return
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "u")
    public List<BdglPeople> selectPowerPeoleList(BdglPeople bdglPeople) {
        return bdglPeopleMapper.selectPowerPeoleList(bdglPeople);
    }

    /**
     * 查询人员管理列表
     *
     * @param bdglPeople 人员管理
     * @return 人员管理
     */
    @Override
    public List<BdglPeople> selectBdglPeopleList(BdglPeople bdglPeople)
    {
        return bdglPeopleMapper.selectBdglPeopleList(bdglPeople);
    }

    /**
     * 新增人员管理
     *
     * @param bdglPeople 人员管理
     * @return 结果
     */
    @Override
    public int insertBdglPeople(BdglPeople bdglPeople)
    {
        int i = bdglPeopleMapper.insertBdglPeople(bdglPeople);
        /*--------------------------添加家庭信息-----------------------------*/
        List<FamilyInformation> familyInformation = bdglPeople.getFamilyInformation();
        if(familyInformation!=null&&familyInformation.size() > 0){
            for (FamilyInformation information : familyInformation) {
                if(information.getName()!=null){
                    information.setPeopleId(bdglPeople.getId());
                    familyInformationMapper.insertFamilyInformation(information);
                }
            }
        }
        /*--------------------------添加家庭信息-----------------------------*/
        return i;
    }

    /**
     * 修改人员管理
     *
     * @param bdglPeople 人员管理
     * @return 结果
     */
    @Override
    public int updateBdglPeople(BdglPeople bdglPeople)
    {
        List<FamilyInformation> familyInformation = bdglPeople.getFamilyInformation();
        if(familyInformation!=null&&familyInformation.size() > 0){
            for (FamilyInformation information : familyInformation) {
                information.setPeopleId(bdglPeople.getId());
                familyInformationMapper.updateFamilyInformation(information);
                if(information.getId()==null){
                    information.setPeopleId(bdglPeople.getId());
                    familyInformationMapper.insertFamilyInformation(information);
                }
            }
        }
        return bdglPeopleMapper.updateBdglPeople(bdglPeople);
    }

    /**
     * 批量删除人员管理
     *
     * @param ids 需要删除的人员管理主键
     * @return 结果
     */
    @Override
    public int deleteBdglPeopleByIds(Long[] ids)
    {
        return bdglPeopleMapper.deleteBdglPeopleByIds(ids);
    }

    /**
     * 删除人员管理信息
     *
     * @param id 人员管理主键
     * @return 结果
     */
    @Override
    public int deleteBdglPeopleById(Long id)
    {
        return bdglPeopleMapper.deleteBdglPeopleById(id);
    }
    /**获取当前部门  在位 在编 未在编  人员情况*/
    @Override
    public Map<String, Object> getDeptXiangQi(BdglPeople bdglPeople) {
        return bdglPeopleMapper.getDeptXiangQi(bdglPeople);
    }

    /**
     * 根据人员状态和是否在编 获取人员数量
     * @param type 是否在编
     * @return
     */
    public List<Map<String, String>> selectPeopleByRegin(String type) {
        return  bdglPeopleMapper.selectPeopleByRegin(type);
    }
    //根据人员id获取人员姓名
    @Override
    public List<BdglPeople> selectname(String[] split) {
        return bdglPeopleMapper.selectname(split);
    }

    @Override
    public Map<String, Object> examinationCountss(BdglPeople bdglPeople) {
        return bdglPeopleMapper.examinationCountss(bdglPeople);
    }

    @Override
    public List<BdglPeople> selectBdglPeopleInfo(BdglPeople bdglPeople) {
        return bdglPeopleMapper.selectBdglPeopleInfo(bdglPeople);
    }
    /**查询当前部门及下属部门人员*/
    @Override
    public List<BdglPeople> selectPowerPeoleLists(BdglPeople bdglPeople) {
        return bdglPeopleMapper.selectPowerPeoleLists(bdglPeople);
    }
    @Override
    public List<BdglPeople> selectExaminationList(BdglPeople bdglPeople){
        return bdglPeopleMapper.selectExaminationList(bdglPeople);
    }

    @Override
    public List<HashMap<String, Object>> selectGroupBYPeope(BdglPeople bdglPeople) {
        return bdglPeopleMapper.selectGroupBYPeope(bdglPeople);
    }

    @Override
    public int selectTotalCountByDeptId(Long deptId) {
        return bdglPeopleMapper.selectTotalCountByDeptId(deptId);
    }

    @Override
    public int selectReginCountByDeptId(Long deptId) {
        return bdglPeopleMapper.selectReginCountByDeptId(deptId);
    }

    @Override
    public List<SysDeptDto> getPeopleList(String year) {
        //所有营级单位
        List<Map<String, SysDept>> mapList = deptMapper.selectAllCamp();
        //根据营级单位ID查询当前单位下是否有数据
        List<SysDeptDto> dtoList = new ArrayList<>();
        mapList.forEach(item -> {
            SysDeptDto deptDto = BeanUtil.mapToBean(item, SysDeptDto.class, false);
            int deptId = deptDto.getDeptId().intValue();
            int countByunitId = bdglPeopleMapper.selectBdglPeopleByunitId(deptId,year);
            if (countByunitId > 0) {
                dtoList.add(deptDto);
            }
        });
        return dtoList;
    }

    @Override
    public List<BdglPeople> getPeopleOnUnitList(Integer unitId) {
        BdglPeople bdglPeople = new BdglPeople();
        bdglPeople.setUnitId(unitId+"");
        return bdglPeopleMapper.selectBdglPeopleOnUnitList(bdglPeople);
    }

    @Override
    public int selectReginCount2ByDeptId(Long deptId) {
        return bdglPeopleMapper.selectReginCount2ByDeptId(deptId);
    }

    @Override
    public List<StatOutPeopleDto> selectReginByDeptId(Long deptId) {
        return bdglPeopleMapper.selectReginByDeptId(deptId);
    }

    @Override
    public List<BdglPeople> countTypeNumber( ) {
        return bdglPeopleMapper.countTypeNumber();
    }

    @Override
    public Map<String, Object> getDeptXiangQi2(BdglPeople bdglPeople) {
        return bdglPeopleMapper.getDeptXiangQi2(bdglPeople);
    }

    @Override
    public Map<String, Object> getDeptXiangQi3(BdglPeople bdglPeople) {
        return bdglPeopleMapper.getDeptXiangQi3(bdglPeople);
    }
    
    @Override
    public BdglPeople selectBdglPeople(String ename) {
        return bdglPeopleMapper.selectBdglPeople(ename);
    }
    
    @Override
    public List<String> selectPowerPeoleListes(String peopleId) {
        return bdglPeopleMapper.selectPowerPeoleListes(peopleId);
    }
    
    @Override
    public List<BdglPeople> selectPowerPeoleListsss(BdglPeople bdglPeople) {
        return bdglPeopleMapper.selectPowerPeoleListsss(bdglPeople);
    }

    @Override
    public List<String> getExcelHeaderItems(Excel attr){
        List<String> items = new ArrayList<>();
        switch (attr.name()) {
            case "民族":
                List<SysDictData> sysDictDatas = dictTypeService.selectDictDataByType("sys_familyName");
                for (SysDictData sysDictData : sysDictDatas) {
                    items.add(sysDictData.getDictLabel());
                }
                break;
            case "性别":
                List<SysDictData> sysUserSex = dictTypeService.selectDictDataByType("sys_user_sex");
                for (SysDictData sysDictData : sysUserSex) {
                    items.add(sysDictData.getDictLabel());
                }
                break;
            case "血型":
                List<SysDictData> bloodType = dictTypeService.selectDictDataByType("bloodType");
                for (SysDictData sysDictData : bloodType) {
                    items.add(sysDictData.getDictLabel());
                }
                break;
            case "政治面貌":
                List<SysDictData> politicalOutlook = dictTypeService.selectDictDataByType("Caucus");
                for (SysDictData sysDictData : politicalOutlook) {
                    items.add(sysDictData.getDictLabel());
                }
                break;
            case "健康情况":
                List<SysDictData> healthy = dictTypeService.selectDictDataByType("healthy");
                for (SysDictData sysDictData : healthy) {
                    items.add(sysDictData.getDictLabel());
                }
                break;
            case "学历":
                List<SysDictData> education = dictTypeService.selectDictDataByType("Degree_level");
                for (SysDictData sysDictData : education) {
                    items.add(sysDictData.getDictLabel());
                }
                break;
            case "学位":
                List<SysDictData> degree = dictTypeService.selectDictDataByType("degree");
                for (SysDictData sysDictData : degree) {
                    items.add(sysDictData.getDictLabel());
                }
                break;


            case "人员类别":
                List<SysDictData> peopleType = dictTypeService.selectDictDataByType("personnel_type");
                for (SysDictData sysDictData : peopleType) {
                    items.add(sysDictData.getDictLabel());
                }
                break;
            case "单位":
                List<SysDept> sysDepts = deptMapper.selectDeptList(new SysDept());
                for (SysDept sysDept : sysDepts) {
                    items.add(sysDept.getDeptName());
                }
                break;
            case "职务层级":
                List<SysDictData> postLevel = dictTypeService.selectDictDataByType("post_Level");
                for (SysDictData sysDictData : postLevel) {
                    items.add(sysDictData.getDictLabel());
                }
                break;
            case "军衔":
                List<SysDictData> rank = dictTypeService.selectDictDataByType("sys_user_rank");
                for (SysDictData sysDictData : rank) {
                    items.add(sysDictData.getDictLabel());
                }
                break;
            case "现军衔":
                List<SysDictData> nowRank = dictTypeService.selectDictDataByType("sys_user_rank");
                for (SysDictData sysDictData : nowRank) {
                    items.add(sysDictData.getDictLabel());
                }
                break;
            case "是否编制":
                List<SysDictData> isCompile = dictTypeService.selectDictDataByType("sys_yes_no");
                for (SysDictData sysDictData : isCompile) {
                    items.add(sysDictData.getDictLabel());
                }
                break;
            case "专业是否对口":
                List<SysDictData> isDocking = dictTypeService.selectDictDataByType("sys_yes_no");
                for (SysDictData sysDictData : isDocking) {
                    items.add(sysDictData.getDictLabel());
                }
                break;
        };
        return items;
    }

    @Override
    public BdglPeople selectBdglPeopleByEmployeeNumber(String employeeNumber) {
        return bdglPeopleMapper.selectBdglPeopleByEmployeeNumber(employeeNumber);
    }


}
