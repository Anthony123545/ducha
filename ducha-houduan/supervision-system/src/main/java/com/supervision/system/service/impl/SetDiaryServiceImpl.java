package com.supervision.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.system.mapper.SetDiaryMapper;
import com.supervision.system.domain.SetDiary;
import com.supervision.system.service.ISetDiaryService;

/**
 * 要事日记设置 Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-09-14
 */
@Service
public class SetDiaryServiceImpl implements ISetDiaryService 
{
    @Autowired
    private SetDiaryMapper setDiaryMapper;

    /**
     * 查询要事日记设置 
     * 
     * @param id 要事日记设置 主键
     * @return 要事日记设置 
     */
    @Override
    public SetDiary selectSetDiaryById(Long id)
    {
        return setDiaryMapper.selectSetDiaryById(id);
    }

    /**
     * 查询要事日记设置 列表
     * 
     * @param setDiary 要事日记设置 
     * @return 要事日记设置 
     */
    @Override
    public List<SetDiary> selectSetDiaryList(SetDiary setDiary)
    {
        return setDiaryMapper.selectSetDiaryList(setDiary);
    }

    /**
     * 新增要事日记设置 
     * 
     * @param setDiary 要事日记设置 
     * @return 结果
     */
    @Override
    public int insertSetDiary(SetDiary setDiary)
    {
        return setDiaryMapper.insertSetDiary(setDiary);
    }

    /**
     * 修改要事日记设置 
     * 
     * @param setDiary 要事日记设置 
     * @return 结果
     */
    @Override
    public int updateSetDiary(SetDiary setDiary)
    {
        return setDiaryMapper.updateSetDiary(setDiary);
    }

    /**
     * 批量删除要事日记设置 
     * 
     * @param ids 需要删除的要事日记设置 主键
     * @return 结果
     */
    @Override
    public int deleteSetDiaryByIds(Long[] ids)
    {
        return setDiaryMapper.deleteSetDiaryByIds(ids);
    }

    /**
     * 删除要事日记设置 信息
     * 
     * @param id 要事日记设置 主键
     * @return 结果
     */
    @Override
    public int deleteSetDiaryById(Long id)
    {
        return setDiaryMapper.deleteSetDiaryById(id);
    }
}
