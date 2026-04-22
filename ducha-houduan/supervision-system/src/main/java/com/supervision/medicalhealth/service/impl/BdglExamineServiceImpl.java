package com.supervision.medicalhealth.service.impl;

import java.util.Date;
import java.util.List;

import com.supervision.peopleManage.domain.BdglPeople;
import com.supervision.peopleManage.mapper.BdglPeopleMapper;
import com.supervision.peopleManage.service.IBdglPeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.medicalhealth.mapper.BdglExamineMapper;
import com.supervision.medicalhealth.domain.BdglExamine;
import com.supervision.medicalhealth.service.IBdglExamineService;

/**
 * 人员体检信息Service业务层处理
 * 
 * @author hr
 * @date 2022-02-14
 */
@Service
public class BdglExamineServiceImpl implements IBdglExamineService 
{
    @Autowired
    private BdglExamineMapper bdglExamineMapper;
    @Autowired
    private BdglPeopleMapper peopleMapper;

    /**
     * 查询人员体检信息
     * 
     * @param id 人员体检信息主键
     * @return 人员体检信息
     */
    @Override
    public BdglExamine selectBdglExamineById(Integer id)
    {
        return bdglExamineMapper.selectBdglExamineById(id);
    }

    /**
     * 查询人员体检信息列表
     * 
     * @param bdglExamine 人员体检信息
     * @return 人员体检信息
     */
    @Override
    public List<BdglExamine> selectBdglExamineList(BdglExamine bdglExamine)
    {
        return bdglExamineMapper.selectBdglExamineList(bdglExamine);
    }

    /**
     * 新增人员体检信息
     * 
     * @param bdglExamine 人员体检信息
     * @return 结果
     */
    @Override
    public int insertBdglExamine(BdglExamine bdglExamine)
    {
        BdglPeople bdglPeople = peopleMapper.selectBdglPeopleById(Long.valueOf(bdglExamine.getPeopleId()));
        bdglExamine.setClassId(bdglPeople.getClassId());
        //bdglExamine.setJob(bdglPeople.getJob());
        bdglExamine.setCreateTime(new Date());
        return bdglExamineMapper.insertBdglExamine(bdglExamine);
    }

    /**
     * 修改人员体检信息
     * 
     * @param bdglExamine 人员体检信息
     * @return 结果
     */
    @Override
    public int updateBdglExamine(BdglExamine bdglExamine)
    {
        return bdglExamineMapper.updateBdglExamine(bdglExamine);
    }

    /**
     * 批量删除人员体检信息
     * 
     * @param ids 需要删除的人员体检信息主键
     * @return 结果
     */
    @Override
    public int deleteBdglExamineByIds(Integer[] ids)
    {
        return bdglExamineMapper.deleteBdglExamineByIds(ids);
    }

    /**
     * 删除人员体检信息信息
     * 
     * @param id 人员体检信息主键
     * @return 结果
     */
    @Override
    public int deleteBdglExamineById(Integer id)
    {
        return bdglExamineMapper.deleteBdglExamineById(id);
    }
}
