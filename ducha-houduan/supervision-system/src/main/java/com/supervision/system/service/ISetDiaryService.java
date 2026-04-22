package com.supervision.system.service;

import java.util.List;
import com.supervision.system.domain.SetDiary;

/**
 * 要事日记设置 Service接口
 * 
 * @author ruoyi
 * @date 2022-09-14
 */
public interface ISetDiaryService 
{
    /**
     * 查询要事日记设置 
     * 
     * @param id 要事日记设置 主键
     * @return 要事日记设置 
     */
    public SetDiary selectSetDiaryById(Long id);

    /**
     * 查询要事日记设置 列表
     * 
     * @param setDiary 要事日记设置 
     * @return 要事日记设置 集合
     */
    public List<SetDiary> selectSetDiaryList(SetDiary setDiary);

    /**
     * 新增要事日记设置 
     * 
     * @param setDiary 要事日记设置 
     * @return 结果
     */
    public int insertSetDiary(SetDiary setDiary);

    /**
     * 修改要事日记设置 
     * 
     * @param setDiary 要事日记设置 
     * @return 结果
     */
    public int updateSetDiary(SetDiary setDiary);

    /**
     * 批量删除要事日记设置 
     * 
     * @param ids 需要删除的要事日记设置 主键集合
     * @return 结果
     */
    public int deleteSetDiaryByIds(Long[] ids);

    /**
     * 删除要事日记设置 信息
     * 
     * @param id 要事日记设置 主键
     * @return 结果
     */
    public int deleteSetDiaryById(Long id);
}
