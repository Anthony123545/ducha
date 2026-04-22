package com.supervision.grassrootsregistration.service;

import java.util.List;
import com.supervision.grassrootsregistration.domain.BdglDiaryWeekworkRegister;

/**
 * 要事日记一周工作内容Service接口
 * 
 * @author ruoyi
 * @date 2022-10-31
 */
public interface IBdglDiaryWeekworkRegisterService 
{
    /**
     * 查询要事日记一周工作内容
     * 
     * @param id 要事日记一周工作内容主键
     * @return 要事日记一周工作内容
     */
    public BdglDiaryWeekworkRegister selectBdglDiaryWeekworkRegisterById(Long id);

    /**
     * 查询要事日记一周工作内容列表
     * 
     * @param bdglDiaryWeekworkRegister 要事日记一周工作内容
     * @return 要事日记一周工作内容集合
     */
    public List<BdglDiaryWeekworkRegister> selectBdglDiaryWeekworkRegisterList(BdglDiaryWeekworkRegister bdglDiaryWeekworkRegister);

    /**
     * 新增要事日记一周工作内容
     * 
     * @param bdglDiaryWeekworkRegister 要事日记一周工作内容
     * @return 结果
     */
    public int insertBdglDiaryWeekworkRegister(BdglDiaryWeekworkRegister bdglDiaryWeekworkRegister);

    /**
     * 修改要事日记一周工作内容
     * 
     * @param bdglDiaryWeekworkRegister 要事日记一周工作内容
     * @return 结果
     */
    public int updateBdglDiaryWeekworkRegister(BdglDiaryWeekworkRegister bdglDiaryWeekworkRegister);

    /**
     * 批量删除要事日记一周工作内容
     * 
     * @param ids 需要删除的要事日记一周工作内容主键集合
     * @return 结果
     */
    public int deleteBdglDiaryWeekworkRegisterByIds(Long[] ids);

    /**
     * 删除要事日记一周工作内容信息
     * 
     * @param id 要事日记一周工作内容主键
     * @return 结果
     */
    public int deleteBdglDiaryWeekworkRegisterById(Long id);
}
