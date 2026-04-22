package com.supervision.thought.service;

import java.util.List;
import com.supervision.thought.domain.ThoughtTeacher;

/**
 * 教案库设置Service接口
 * 
 * @author supervision
 * @date 2022-04-11
 */
public interface IThoughtTeacherService 
{
    /**
     * 查询教案库设置
     * 
     * @param id 教案库设置主键
     * @return 教案库设置
     */
    public ThoughtTeacher selectThoughtTeacherById(Long id);

    /**
     * 查询教案库设置列表
     * 
     * @param thoughtTeacher 教案库设置
     * @return 教案库设置集合
     */
    public List<ThoughtTeacher> selectThoughtTeacherList(ThoughtTeacher thoughtTeacher);

    /**
     * 新增教案库设置
     * 
     * @param thoughtTeacher 教案库设置
     * @return 结果
     */
    public int insertThoughtTeacher(ThoughtTeacher thoughtTeacher);

    /**
     * 修改教案库设置
     * 
     * @param thoughtTeacher 教案库设置
     * @return 结果
     */
    public int updateThoughtTeacher(ThoughtTeacher thoughtTeacher);

    /**
     * 批量删除教案库设置
     * 
     * @param ids 需要删除的教案库设置主键集合
     * @return 结果
     */
    public int deleteThoughtTeacherByIds(Long[] ids);

    /**
     * 删除教案库设置信息
     * 
     * @param id 教案库设置主键
     * @return 结果
     */
    public int deleteThoughtTeacherById(Long id);


}
