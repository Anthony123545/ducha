package com.supervision.phone.mapper;

import java.util.List;
import com.supervision.phone.domain.PhoneAccess;

/**
 * 存取记录Mapper接口
 * 
 * @author supervision
 * @date 2022-04-23
 */
public interface PhoneAccessMapper 
{
    /**
     * 查询存取记录
     * 
     * @param id 存取记录主键
     * @return 存取记录
     */
    public PhoneAccess selectPhoneAccessById(Long id);

    /**
     * 查询存取记录列表
     * 
     * @param phoneAccess 存取记录
     * @return 存取记录集合
     */
    public List<PhoneAccess> selectPhoneAccessList(PhoneAccess phoneAccess);

    /**
     * 新增存取记录
     * 
     * @param phoneAccess 存取记录
     * @return 结果
     */
    public int insertPhoneAccess(PhoneAccess phoneAccess);

    /**
     * 修改存取记录
     * 
     * @param phoneAccess 存取记录
     * @return 结果
     */
    public int updatePhoneAccess(PhoneAccess phoneAccess);

    /**
     * 删除存取记录
     * 
     * @param id 存取记录主键
     * @return 结果
     */
    public int deletePhoneAccessById(Long id);

    /**
     * 批量删除存取记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePhoneAccessByIds(Long[] ids);
}
