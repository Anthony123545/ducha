package com.supervision.phone.service;

import java.util.List;
import com.supervision.phone.domain.PhoneCabinet;

/**
 * 手机柜Service接口
 * 
 * @author supervision
 * @date 2022-04-20
 */
public interface IPhoneCabinetService 
{
    /**
     * 查询手机柜
     * 
     * @param id 手机柜主键
     * @return 手机柜
     */
    public PhoneCabinet selectPhoneCabinetById(Long id);

    /**
     * 查询手机柜列表
     * 
     * @param phoneCabinet 手机柜
     * @return 手机柜集合
     */
    public List<PhoneCabinet> selectPhoneCabinetList(PhoneCabinet phoneCabinet);

    /**
     * 新增手机柜
     * 
     * @param phoneCabinet 手机柜
     * @return 结果
     */
    public int insertPhoneCabinet(PhoneCabinet phoneCabinet);

    /**
     * 修改手机柜
     * 
     * @param phoneCabinet 手机柜
     * @return 结果
     */
    public int updatePhoneCabinet(PhoneCabinet phoneCabinet);

    /**
     * 批量删除手机柜
     * 
     * @param ids 需要删除的手机柜主键集合
     * @return 结果
     */
    public int deletePhoneCabinetByIds(Long[] ids);

    /**
     * 删除手机柜信息
     * 
     * @param id 手机柜主键
     * @return 结果
     */
    public int deletePhoneCabinetById(Long id);
}
