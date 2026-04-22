package com.supervision.barracksManagement.mapper;

import java.util.List;
import com.supervision.barracksManagement.domain.TemporaryCheckin;

/**
 * 临时来队Mapper接口
 * 
 * @author supervision
 * @date 2022-03-07
 */
public interface TemporaryCheckinMapper 
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
     * 删除临时来队
     * 
     * @param id 临时来队主键
     * @return 结果
     */
    public int deleteTemporaryCheckinById(Long id);

    /**
     * 批量删除临时来队
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTemporaryCheckinByIds(Long[] ids);
}
