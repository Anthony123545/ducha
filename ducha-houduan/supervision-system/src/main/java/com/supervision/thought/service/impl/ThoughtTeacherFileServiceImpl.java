package com.supervision.thought.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.supervision.common.utils.DateUtils;
import com.supervision.common.utils.StringUtils;
import com.supervision.thought.domain.ThoughtTeacher;
import com.supervision.thought.mapper.ThoughtTeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.thought.mapper.ThoughtTeacherFileMapper;
import com.supervision.thought.domain.ThoughtTeacherFile;
import com.supervision.thought.service.IThoughtTeacherFileService;

/**
 * 教案库Service业务层处理
 * 
 * @author supervision
 * @date 2022-04-11
 */
@Service
public class ThoughtTeacherFileServiceImpl implements IThoughtTeacherFileService 
{
    @Autowired
    private ThoughtTeacherFileMapper thoughtTeacherFileMapper;
    @Autowired
    private ThoughtTeacherMapper thoughtTeacherMapper;

    /**
     * 查询教案库
     * 
     * @param id 教案库主键
     * @return 教案库
     */
    @Override
    public ThoughtTeacherFile selectThoughtTeacherFileById(Long id)
    {
        return thoughtTeacherFileMapper.selectThoughtTeacherFileById(id);
    }

    /**
     * 查询教案库列表
     * 
     * @param thoughtTeacherFile 教案库
     * @return 教案库
     */
    @Override
    public List<ThoughtTeacherFile> selectThoughtTeacherFileList(ThoughtTeacherFile thoughtTeacherFile)
    {
        return thoughtTeacherFileMapper.selectThoughtTeacherFileList(thoughtTeacherFile);
    }

    /**
     * 新增教案库
     * 
     * @param thoughtTeacherFile 教案库
     * @return 结果
     */
    @Override
    public int insertThoughtTeacherFile(ThoughtTeacherFile thoughtTeacherFile)
    {

        thoughtTeacherFile.setCreateTime(DateUtils.getNowDate());
        return thoughtTeacherFileMapper.insertThoughtTeacherFile(thoughtTeacherFile);
    }

    /**
     * 修改教案库
     * 
     * @param thoughtTeacherFile 教案库
     * @return 结果
     */
    @Override
    public int updateThoughtTeacherFile(ThoughtTeacherFile thoughtTeacherFile)
    {
        return thoughtTeacherFileMapper.updateThoughtTeacherFile(thoughtTeacherFile);
    }

    /**
     * 批量删除教案库
     * 
     * @param ids 需要删除的教案库主键
     * @return 结果
     */
    @Override
    public int deleteThoughtTeacherFileByIds(Long[] ids)
    {
        return thoughtTeacherFileMapper.deleteThoughtTeacherFileByIds(ids);
    }

    /**
     * 删除教案库信息
     * 
     * @param id 教案库主键
     * @return 结果
     */
    @Override
    public int deleteThoughtTeacherFileById(Long id)
    {
        return thoughtTeacherFileMapper.deleteThoughtTeacherFileById(id);
    }

    @Override
    public List<ThoughtTeacherFile> selectReadRanking() {
        return thoughtTeacherFileMapper.selectReadRanking();
    }
}
