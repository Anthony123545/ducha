package com.supervision.thought.service.impl;

import java.util.List;
import java.util.UUID;

import com.supervision.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.thought.mapper.ThoughtTeacherMapper;
import com.supervision.thought.domain.ThoughtTeacher;
import com.supervision.thought.service.IThoughtTeacherService;

/**
 * 教案库设置Service业务层处理
 *
 * @author supervision
 * @date 2022-04-11
 */
@Service
public class ThoughtTeacherServiceImpl implements IThoughtTeacherService {
    @Autowired
    private ThoughtTeacherMapper thoughtTeacherMapper;

    /**
     * 查询教案库设置
     *
     * @param id 教案库设置主键
     * @return 教案库设置
     */
    @Override
    public ThoughtTeacher selectThoughtTeacherById(Long id) {
        return thoughtTeacherMapper.selectThoughtTeacherById(id);
    }

    /**
     * 查询教案库设置列表
     *
     * @param thoughtTeacher 教案库设置
     * @return 教案库设置
     */
    @Override
    public List<ThoughtTeacher> selectThoughtTeacherList(ThoughtTeacher thoughtTeacher) {
        return thoughtTeacherMapper.selectThoughtTeacherList(thoughtTeacher);
    }

    /**
     * 新增教案库设置
     *
     * @param thoughtTeacher 教案库设置
     * @return 结果
     */
    @Override
    public int insertThoughtTeacher(ThoughtTeacher thoughtTeacher) {
        thoughtTeacher.setLevel("1");
        if (thoughtTeacher.getParentId() != null) {
            ThoughtTeacher thoughtTeacher1 = thoughtTeacherMapper.selectThoughtTeacherById(thoughtTeacher.getParentId());
            thoughtTeacher1.setSubclassification(thoughtTeacher1.getSubclassification() + 1);
            thoughtTeacherMapper.updateThoughtTeacher(thoughtTeacher1);
            thoughtTeacher.setAncestors(thoughtTeacher1.getAncestors() + "," + thoughtTeacher.getParentId());
            Integer length = thoughtTeacher.getAncestors().split(",").length;
            thoughtTeacher.setLevel(length.toString());
        } else {
            String s = UUID.randomUUID().toString();
            thoughtTeacher.setParentDirectory(s);
        }
        thoughtTeacher.setCreateTime(DateUtils.getNowDate());
        return thoughtTeacherMapper.insertThoughtTeacher(thoughtTeacher);
    }

    /**
     * 修改教案库设置
     *
     * @param thoughtTeacher 教案库设置
     * @return 结果
     */
    @Override
    public int updateThoughtTeacher(ThoughtTeacher thoughtTeacher) {
        thoughtTeacher.setUpdateTime(DateUtils.getNowDate());
        return thoughtTeacherMapper.updateThoughtTeacher(thoughtTeacher);
    }

    /**
     * 批量删除教案库设置
     *
     * @param ids 需要删除的教案库设置主键
     * @return 结果
     */
    @Override
    public int deleteThoughtTeacherByIds(Long[] ids) {
        for (Long id : ids) {
            ThoughtTeacher thoughtTeacher = thoughtTeacherMapper.selectThoughtTeacherById(id);
            if (thoughtTeacher.getParentId() != null) {
                ThoughtTeacher thoughtTeachers = thoughtTeacherMapper.selectThoughtTeacherById(thoughtTeacher.getParentId());
                if(thoughtTeachers!=null){

                    thoughtTeachers.setSubclassification(thoughtTeachers.getSubclassification() - 1);
                    thoughtTeacherMapper.updateThoughtTeacher(thoughtTeachers);
                }
            }
        }
        return thoughtTeacherMapper.deleteThoughtTeacherByIds(ids);
    }

    /**
     * 删除教案库设置信息
     *
     * @param id 教案库设置主键
     * @return 结果
     */
    @Override
    public int deleteThoughtTeacherById(Long id) {

        ThoughtTeacher thoughtTeacher = thoughtTeacherMapper.selectThoughtTeacherById(id);
        if (thoughtTeacher.getParentId() != null) {
            ThoughtTeacher thoughtTeacher1 = new ThoughtTeacher();
            thoughtTeacher1.setParentId(thoughtTeacher.getParentId());
            List<ThoughtTeacher> thoughtTeachers = thoughtTeacherMapper.selectThoughtTeacherList(thoughtTeacher1);
            for (ThoughtTeacher teacher : thoughtTeachers) {
                teacher.setSubclassification(teacher.getSubclassification() - 1);
                thoughtTeacherMapper.updateThoughtTeacher(teacher);
                break;
            }

        }
        return thoughtTeacherMapper.deleteThoughtTeacherById(id);
    }


}
