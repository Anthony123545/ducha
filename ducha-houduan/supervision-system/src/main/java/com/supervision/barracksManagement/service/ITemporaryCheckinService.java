package com.supervision.barracksManagement.service;

import java.util.List;
import com.supervision.barracksManagement.domain.TemporaryCheckin;

/**
 * 临时来队Service接口
 * 
 * @author supervision
 * @date 2022-03-07
 */
public interface ITemporaryCheckinService 
{
    /**
     * 查询临时来队
     * 
     * @param id 临时来队主键
     * @return 临时来队
     */
    public TemporaryCheckin selectTemporaryCheckinById(Long id);

    /**
     * 查询临时来队列表
     * 
     * @param temporaryCheckin 临时来队
     * @return 临时来队集合
     */
    public List<TemporaryCheckin> selectTemporaryCheckinList(TemporaryCheckin temporaryCheckin);

    /**
     * 新增临时来队
     * 
     * @param temporaryCheckin 临时来队
     * @return 结果
     */
    public int insertTemporaryCheckin(TemporaryCheckin temporaryCheckin);

    /**
     * 修改临时来队
     * 
     * @param temporaryCheckin 临时来队
     * @return 结果
     */
    public int updateTemporaryCheckin(TemporaryCheckin temporaryCheckin);

    /**
     * 批量删除临时来队
     * 
     * @param ids 需要删除的临时来队主键集合
     * @return 结果
     */
    public int deleteTemporaryCheckinByIds(Long[] ids);

    /**
     * 删除临时来队信息
     * 
     * @param id 临时来队主键
     * @return 结果
     */
    public int deleteTemporaryCheckinById(Long id);
}
