package com.supervision.phone.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.supervision.phone.mapper.PhoneAccessMapper;
import com.supervision.phone.domain.PhoneAccess;
import com.supervision.phone.service.IPhoneAccessService;

/**
 * 存取记录Service业务层处理
 * 
 * @author supervision
 * @date 2022-04-23
 */
@Service
public class PhoneAccessServiceImpl implements IPhoneAccessService 
{
    @Autowired
    private PhoneAccessMapper phoneAccessMapper;

    /**
     * 查询存取记录
     * 
     * @param id 存取记录主键
     * @return 存取记录
     */
    @Override
    public PhoneAccess selectPhoneAccessById(Long id)
    {
        return phoneAccessMapper.selectPhoneAccessById(id);
    }

    /**
     * 查询存取记录列表
     * 
     * @param phoneAccess 存取记录
     * @return 存取记录
     */
    @Override
    public List<PhoneAccess> selectPhoneAccessList(PhoneAccess phoneAccess)
    {
        return phoneAccessMapper.selectPhoneAccessList(phoneAccess);
    }

    /**
     * 新增存取记录
     * 
     * @param phoneAccess 存取记录
     * @return 结果
     */
    @Override
    public int insertPhoneAccess(PhoneAccess phoneAccess)
    {
        return phoneAccessMapper.insertPhoneAccess(phoneAccess);
    }

    /**
     * 修改存取记录
     * 
     * @param phoneAccess 存取记录
     * @return 结果
     */
    @Override
    public int updatePhoneAccess(PhoneAccess phoneAccess)
    {
        return phoneAccessMapper.updatePhoneAccess(phoneAccess);
    }

    /**
     * 批量删除存取记录
     * 
     * @param ids 需要删除的存取记录主键
     * @return 结果
     */
    @Override
    public int deletePhoneAccessByIds(Long[] ids)
    {
        return phoneAccessMapper.deletePhoneAccessByIds(ids);
    }

    /**
     * 删除存取记录信息
     * 
     * @param id 存取记录主键
     * @return 结果
     */
    @Override
    public int deletePhoneAccessById(Long id)
    {
        return phoneAccessMapper.deletePhoneAccessById(id);
    }
}
