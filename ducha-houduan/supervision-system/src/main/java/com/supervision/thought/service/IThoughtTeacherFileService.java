package com.supervision.thought.service;

import java.util.List;
import com.supervision.thought.domain.ThoughtTeacherFile;

/**
 * 教案库Service接口
 * 
 * @author supervision
 * @date 2022-04-11
 */
public interface IThoughtTeacherFileService 
{
    /**
     * 查询教案库
     * 
     * @param id 教案库主键
     * @return 教案库
     */
    public ThoughtTeacherFile selectThoughtTeacherFileById(Long id);

    /**
     * 查询教案库列表
     * 
     * @param thoughtTeacherFile 教案库
     * @return 教案库集合
     */
    public List<ThoughtTeacherFile> selectThoughtTeacherFileList(ThoughtTeacherFile thoughtTeacherFile);

    /**
     * 新增教案库
     * 
     * @param thoughtTeacherFile 教案库
     * @return 结果
     */
    public int insertThoughtTeacherFile(ThoughtTeacherFile thoughtTeacherFile);

    /**
     * 修改教案库
     * 
     * @param thoughtTeacherFile 教案库
     * @return 结果
     */
    public int updateThoughtTeacherFile(ThoughtTeacherFile thoughtTeacherFile);

    /**
     * 批量删除教案库
     * 
     * @param ids 需要删除的教案库主键集合
     * @return 结果
     */
    public int deleteThoughtTeacherFileByIds(Long[] ids);

    /**
     * 删除教案库信息
     * 
     * @param id 教案库主键
     * @return 结果
     */
    public int deleteThoughtTeacherFileById(Long id);

    List<ThoughtTeacherFile> selectReadRanking();
}
