package com.supervision.phone.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.phone.mapper.PhoneCabinetMapper;
import com.supervision.phone.domain.PhoneCabinet;
import com.supervision.phone.service.IPhoneCabinetService;

/**
 * 手机柜Service业务层处理
 * 
 * @author supervision
 * @date 2022-04-20
 */
@Service
public class PhoneCabinetServiceImpl implements IPhoneCabinetService 
{
    @Autowired
    private PhoneCabinetMapper phoneCabinetMapper;

    /**
     * 查询手机柜
     * 
     * @param id 手机柜主键
     * @return 手机柜
     */
    @Override
    public PhoneCabinet selectPhoneCabinetById(Long id)
    {
        return phoneCabinetMapper.selectPhoneCabinetById(id);
    }

    /**
     * 查询手机柜列表
     * 
     * @param phoneCabinet 手机柜
     * @return 手机柜
     */
    @Override
    public List<PhoneCabinet> selectPhoneCabinetList(PhoneCabinet phoneCabinet)
    {
        return phoneCabinetMapper.selectPhoneCabinetList(phoneCabinet);
    }

    /**
     * 新增手机柜
     * 
     * @param phoneCabinet 手机柜
     * @return 结果
     */
    @Override
    public int insertPhoneCabinet(PhoneCabinet phoneCabinet)
    {
        return phoneCabinetMapper.insertPhoneCabinet(phoneCabinet);
    }

    /**
     * 修改手机柜
     * 
     * @param phoneCabinet 手机柜
     * @return 结果
     */
    @Override
    public int updatePhoneCabinet(PhoneCabinet phoneCabinet)
    {
        return phoneCabinetMapper.updatePhoneCabinet(phoneCabinet);
    }

    /**
     * 批量删除手机柜
     * 
     * @param ids 需要删除的手机柜主键
     * @return 结果
     */
    @Override
    public int deletePhoneCabinetByIds(Long[] ids)
    {
        return phoneCabinetMapper.deletePhoneCabinetByIds(ids);
    }

    /**
     * 删除手机柜信息
     * 
     * @param id 手机柜主键
     * @return 结果
     */
    @Override
    public int deletePhoneCabinetById(Long id)
    {
        return phoneCabinetMapper.deletePhoneCabinetById(id);
    }
}
