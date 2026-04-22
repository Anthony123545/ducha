package com.supervision.militaryvehicleManagement.service.impl;

import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import com.supervision.militaryvehicleManagement.mapper.BdglAccessPeopleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.militaryvehicleManagement.mapper.BdglThebusThemplateMapper;
import com.supervision.militaryvehicleManagement.domain.BdglThebusThemplate;
import com.supervision.militaryvehicleManagement.service.IBdglThebusThemplateService;

/**
 * 军车审批模板Service业务层处理
 * 
 * @author supervision
 * @date 2022-03-04
 */
@Service
public class BdglThebusThemplateServiceImpl implements IBdglThebusThemplateService 
{
    @Autowired
    private BdglThebusThemplateMapper bdglThebusThemplateMapper;

    @Autowired
    private BdglAccessPeopleMapper bdglAccessPeopleMapper;
    /**
     * 查询军车审批模板
     * 
     * @param id 军车审批模板主键
     * @return 军车审批模板
     */
    @Override
    public BdglThebusThemplate selectBdglThebusThemplateById(Integer id)
    {
        return bdglThebusThemplateMapper.selectBdglThebusThemplateById(id);
    }
    private void setPersonNameIfExists(Integer peopleId, Consumer<String> setter) {
        if (peopleId != null) {
            String name = bdglAccessPeopleMapper.selectPersonNameById(peopleId);
            setter.accept(name);
        }
    }

    /**
     * 查询军车审批模板列表
     * 
     * @param bdglThebusThemplate 军车审批模板
     * @return 军车审批模板
     */
    @Override
    public List<BdglThebusThemplate> selectBdglThebusThemplateList(BdglThebusThemplate bdglThebusThemplate)
    {
        // 1. 查询列表
        List<BdglThebusThemplate> list = bdglThebusThemplateMapper.selectBdglThebusThemplateList(bdglThebusThemplate);

        // 2. 遍历列表，设置每条记录的 PeopleName1/2/3
        for (BdglThebusThemplate template : list) {
            setPersonNameIfExists(template.getPeople1Id(), template::setPeopleName1);
            setPersonNameIfExists(template.getPeople2Id(), template::setPeopleName2);
            setPersonNameIfExists(template.getPeople3Id(), template::setPeopleName3);
        }

        // 3. 返回处理后的列表
        return list;
    }

    /**
     * 新增军车审批模板
     * 
     * @param bdglThebusThemplate 军车审批模板
     * @return 结果
     */
    @Override
    public int insertBdglThebusThemplate(BdglThebusThemplate bdglThebusThemplate)
    {
        bdglThebusThemplate.setCreateTime(new Date());
        bdglThebusThemplate.setUpdateTime(new Date());
        return bdglThebusThemplateMapper.insertBdglThebusThemplate(bdglThebusThemplate);
    }

    /**
     * 修改军车审批模板
     * 
     * @param bdglThebusThemplate 军车审批模板
     * @return 结果
     */
    @Override
    public int updateBdglThebusThemplate(BdglThebusThemplate bdglThebusThemplate)
    {
        bdglThebusThemplate.setUpdateTime(new Date());
        return bdglThebusThemplateMapper.updateBdglThebusThemplate(bdglThebusThemplate);
    }

    /**
     * 批量删除军车审批模板
     * 
     * @param ids 需要删除的军车审批模板主键
     * @return 结果
     */
    @Override
    public int deleteBdglThebusThemplateByIds(Integer[] ids)
    {
        return bdglThebusThemplateMapper.deleteBdglThebusThemplateByIds(ids);
    }

    /**
     * 删除军车审批模板信息
     * 
     * @param id 军车审批模板主键
     * @return 结果
     */
    @Override
    public int deleteBdglThebusThemplateById(Integer id)
    {
        return bdglThebusThemplateMapper.deleteBdglThebusThemplateById(id);
    }
}
