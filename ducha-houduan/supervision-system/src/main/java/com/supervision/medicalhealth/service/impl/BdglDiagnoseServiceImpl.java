package com.supervision.medicalhealth.service.impl;

import java.util.Date;
import java.util.List;

import com.supervision.common.core.domain.entity.SysDept;
import com.supervision.peopleManage.domain.BdglPeople;
import com.supervision.peopleManage.mapper.BdglPeopleMapper;
import com.supervision.system.mapper.SysDeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.medicalhealth.mapper.BdglDiagnoseMapper;
import com.supervision.medicalhealth.domain.BdglDiagnose;
import com.supervision.medicalhealth.service.IBdglDiagnoseService;

/**
 * 外诊管理Service业务层处理
 *
 * @author hr
 * @date 2022-02-14
 */
@Service
public class BdglDiagnoseServiceImpl implements IBdglDiagnoseService
{
    @Autowired
    private BdglDiagnoseMapper bdglDiagnoseMapper;
    @Autowired
    private BdglPeopleMapper bdglPeopleMapper;
    @Autowired
    private SysDeptMapper deptMapper;

    /**
     * 查询外诊管理
     *
     * @param id 外诊管理主键
     * @return 外诊管理
     */
    @Override
    public BdglDiagnose selectBdglDiagnoseById(Integer id)
    {
        return bdglDiagnoseMapper.selectBdglDiagnoseById(id);
    }

    /**
     * 查询外诊管理列表
     *
     * @param bdglDiagnose 外诊管理
     * @return 外诊管理
     */
    @Override
    public List<BdglDiagnose> selectBdglDiagnoseList(BdglDiagnose bdglDiagnose)
    {
        return bdglDiagnoseMapper.selectBdglDiagnoseList(bdglDiagnose);
    }

    /**
     * 新增外诊管理
     *
     * @param bdglDiagnose 外诊管理
     * @return 结果
     */
    @Override
    public int insertBdglDiagnose(BdglDiagnose bdglDiagnose)
    {
        BdglPeople bdglPeople = bdglPeopleMapper.selectBdglPeopleById(Long.valueOf(bdglDiagnose.getPeopleId()));
        bdglPeople.setReign(6);
        bdglPeopleMapper.updateBdglPeople(bdglPeople);
        //bdglDiagnose.setPeopleName(bdglPeople.getPeopleName());
        SysDept sysDept = deptMapper.selectDeptById(Long.valueOf(bdglDiagnose.getUnitId()));
        bdglDiagnose.setUnitName(sysDept.getDeptName());
        bdglDiagnose.setCreatetime(new Date());
        return bdglDiagnoseMapper.insertBdglDiagnose(bdglDiagnose);
    }

    /**
     * 修改外诊管理
     *
     * @param bdglDiagnose 外诊管理
     * @return 结果
     */
    @Override
    public int updateBdglDiagnose(BdglDiagnose bdglDiagnose)
    {
        bdglDiagnose.setUpdatetime(new Date());
        return bdglDiagnoseMapper.updateBdglDiagnose(bdglDiagnose);
    }

    /**
     * 批量删除外诊管理
     *
     * @param ids 需要删除的外诊管理主键
     * @return 结果
     */
    @Override
    public int deleteBdglDiagnoseByIds(Integer[] ids)
    {
        return bdglDiagnoseMapper.deleteBdglDiagnoseByIds(ids);
    }

    /**
     * 删除外诊管理信息
     *
     * @param id 外诊管理主键
     * @return 结果
     */
    @Override
    public int deleteBdglDiagnoseById(Integer id)
    {
        return bdglDiagnoseMapper.deleteBdglDiagnoseById(id);
    }
}
