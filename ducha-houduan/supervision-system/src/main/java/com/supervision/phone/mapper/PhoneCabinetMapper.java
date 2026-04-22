package com.supervision.phone.mapper;

import java.util.List;
import com.supervision.phone.domain.PhoneCabinet;

/**
 * 手机柜Mapper接口
 * 
 * @author supervision
 * @date 2022-04-20
 */
public interface PhoneCabinetMapper 
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
     * 删除手机柜
     * 
     * @param id 手机柜主键
     * @return 结果
     */
    public int deletePhoneCabinetById(Long id);

    /**
     * 批量删除手机柜
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePhoneCabinetByIds(Long[] ids);
}
