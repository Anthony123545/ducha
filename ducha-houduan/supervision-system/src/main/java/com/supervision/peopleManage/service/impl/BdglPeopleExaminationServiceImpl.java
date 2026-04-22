package com.supervision.peopleManage.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.peopleManage.mapper.BdglPeopleExaminationMapper;
import com.supervision.peopleManage.domain.BdglPeopleExamination;
import com.supervision.peopleManage.service.IBdglPeopleExaminationService;

/**
 * 人员政审Service业务层处理
 * 
 * @author supervision
 * @date 2022-02-23
 */
@Service
public class BdglPeopleExaminationServiceImpl implements IBdglPeopleExaminationService 
{
    @Autowired
    private BdglPeopleExaminationMapper bdglPeopleExaminationMapper;

    /**
     * 查询人员政审
     * 
     * @param id 人员政审主键
     * @return 人员政审
     */
    @Override
    public BdglPeopleExamination selectBdglPeopleExaminationById(Integer id)
    {
        return bdglPeopleExaminationMapper.selectBdglPeopleExaminationById(id);
    }

    /**
     * 查询人员政审列表
     * 
     * @param bdglPeopleExamination 人员政审
     * @return 人员政审
     */
    @Override
    public List<BdglPeopleExamination> selectBdglPeopleExaminationList(BdglPeopleExamination bdglPeopleExamination)
    {
        return bdglPeopleExaminationMapper.selectBdglPeopleExaminationList(bdglPeopleExamination);
    }

    /**
     * 新增人员政审
     * 
     * @param bdglPeopleExamination 人员政审
     * @return 结果
     */
    @Override
    public int insertBdglPeopleExamination(BdglPeopleExamination bdglPeopleExamination)
    {
        return bdglPeopleExaminationMapper.insertBdglPeopleExamination(bdglPeopleExamination);
    }

    /**
     * 修改人员政审
     * 
     * @param bdglPeopleExamination 人员政审
     * @return 结果
     */
    @Override
    public int updateBdglPeopleExamination(BdglPeopleExamination bdglPeopleExamination)
    {
        return bdglPeopleExaminationMapper.updateBdglPeopleExamination(bdglPeopleExamination);
    }

    /**
     * 批量删除人员政审
     * 
     * @param ids 需要删除的人员政审主键
     * @return 结果
     */
    @Override
    public int deleteBdglPeopleExaminationByIds(Integer[] ids)
    {
        return bdglPeopleExaminationMapper.deleteBdglPeopleExaminationByIds(ids);
    }

    /**
     * 删除人员政审信息
     * 
     * @param id 人员政审主键
     * @return 结果
     */
    @Override
    public int deleteBdglPeopleExaminationById(Integer id)
    {
        return bdglPeopleExaminationMapper.deleteBdglPeopleExaminationById(id);
    }
}
