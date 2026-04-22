package com.supervision.grassrootsregistration.service;

import java.util.List;
import com.supervision.grassrootsregistration.domain.BdglDiaryLeave;

/**
 * 要事日记请假Service接口
 * 
 * @author ruoyi
 * @date 2022-10-19
 */
public interface IBdglDiaryLeaveService 
{
    /**
     * 查询要事日记请假
     * 
     * @param id 要事日记请假主键
     * @return 要事日记请假
     */
    public BdglDiaryLeave selectBdglDiaryLeaveById(Long id);

    /**
     * 查询要事日记请假列表
     * 
     * @param bdglDiaryLeave 要事日记请假
     * @return 要事日记请假集合
     */
    public List<BdglDiaryLeave> selectBdglDiaryLeaveList(BdglDiaryLeave bdglDiaryLeave);

    /**
     * 新增要事日记请假
     * 
     * @param bdglDiaryLeave 要事日记请假
     * @return 结果
     */
    public int insertBdglDiaryLeave(BdglDiaryLeave bdglDiaryLeave);

    /**
     * 修改要事日记请假
     * 
     * @param bdglDiaryLeave 要事日记请假
     * @return 结果
     */
    public int updateBdglDiaryLeave(BdglDiaryLeave bdglDiaryLeave);

    /**
     * 批量删除要事日记请假
     * 
     * @param ids 需要删除的要事日记请假主键集合
     * @return 结果
     */
    public int deleteBdglDiaryLeaveByIds(Long[] ids);

    /**
     * 删除要事日记请假信息
     * 
     * @param id 要事日记请假主键
     * @return 结果
     */
    public int deleteBdglDiaryLeaveById(Long id);
}
