package com.supervision.phone.service;

import java.util.List;
import com.supervision.phone.domain.PhoneAccess;

/**
 * 存取记录Service接口
 * 
 * @author supervision
 * @date 2022-04-23
 */
public interface IPhoneAccessService 
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
     * 批量删除存取记录
     * 
     * @param ids 需要删除的存取记录主键集合
     * @return 结果
     */
    public int deletePhoneAccessByIds(Long[] ids);

    /**
     * 删除存取记录信息
     * 
     * @param id 存取记录主键
     * @return 结果
     */
    public int deletePhoneAccessById(Long id);
}
