package com.supervision.peopleManage.mapper;

import java.util.List;
import com.supervision.peopleManage.domain.BdglPeopleExamination;

/**
 * 人员政审Mapper接口
 * 
 * @author supervision
 * @date 2022-02-23
 */
public interface BdglPeopleExaminationMapper 
{
    /**
     * 查询人员政审
     * 
     * @param id 人员政审主键
     * @return 人员政审
     */
    public BdglPeopleExamination selectBdglPeopleExaminationById(Integer id);

    /**
     * 查询人员政审列表
     * 
     * @param bdglPeopleExamination 人员政审
     * @return 人员政审集合
     */
    public List<BdglPeopleExamination> selectBdglPeopleExaminationList(BdglPeopleExamination bdglPeopleExamination);

    /**
     * 新增人员政审
     * 
     * @param bdglPeopleExamination 人员政审
     * @return 结果
     */
    public int insertBdglPeopleExamination(BdglPeopleExamination bdglPeopleExamination);

    /**
     * 修改人员政审
     * 
     * @param bdglPeopleExamination 人员政审
     * @return 结果
     */
    public int updateBdglPeopleExamination(BdglPeopleExamination bdglPeopleExamination);

    /**
     * 删除人员政审
     * 
     * @param id 人员政审主键
     * @return 结果
     */
    public int deleteBdglPeopleExaminationById(Integer id);

    /**
     * 批量删除人员政审
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBdglPeopleExaminationByIds(Integer[] ids);
}
